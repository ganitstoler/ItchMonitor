package itche.home.itchmonitore.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.TimeUnit;

import itche.home.itchmonitore.App;
import itche.home.itchmonitore.R;

/**
 * Class responsible for handling uncaught exceptions
 * Created by eyal on 15/02/2017.
 */
public final class MyUncaughtUEH implements Thread.UncaughtExceptionHandler {

    private static final String TAG = MyUncaughtUEH.class.getSimpleName();

    // android has a
    private static final long RESTART_AFTER_CRASH_DELAY_MILLIS = TimeUnit.MINUTES.toMillis(2);
    private static final String INTENT_EXTRA_EXCEPTION = "intent_extra_exception";

    private final Thread.UncaughtExceptionHandler defaultUEH;

    public static @Nullable
    String extractStackTrace(Intent intent) {
        Log.d(TAG, "extractStackTrace() called with: intent = [" + intent + "]");
        String stackTrace = null;
        if (intent.hasExtra(INTENT_EXTRA_EXCEPTION)) {
            Throwable e = (Throwable) intent.getSerializableExtra(INTENT_EXTRA_EXCEPTION);
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            stackTrace = sw.toString();
        }

        return stackTrace;
    }

    public static void setUEH() {
        Log.d(TAG, "setUEH() called");
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (!(defaultUncaughtExceptionHandler instanceof MyUncaughtUEH)) {
            Log.d(TAG, "setUEH: setting uncaughtUEH");
            Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtUEH(Thread.getDefaultUncaughtExceptionHandler()));
        }
    }

    /* package */ MyUncaughtUEH(Thread.UncaughtExceptionHandler defaultUEH) {
        this.defaultUEH = defaultUEH;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Log.d(TAG, "uncaughtException() called with: t = [" + t + "], e = [" + e + "]");

        Context appContext = App.getAppContext();
        if (appContext != null) {
            Intent intent = new Intent(appContext.getString(R.string.intent_action_crash));
            intent.putExtra(INTENT_EXTRA_EXCEPTION, e);
            Log.d(TAG, "uncaughtException: scheduling auto restart receiver");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(appContext, 0, intent, 0);

            AlarmManager alarmManager = (AlarmManager) appContext.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + RESTART_AFTER_CRASH_DELAY_MILLIS, pendingIntent);
        }

        defaultUEH.uncaughtException(t, e);
        System.exit(2);
    }
}
