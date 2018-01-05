package itche.home.itchmonitore.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import java.util.Objects;

import itche.home.itchmonitore.R;
import itche.home.itchmonitore.activities.ItchTraderActivity;

/**
 * Receiver used to restart the app.
 * currently listens to BOOT_COMPLETED
 * Created by eyal on 15/02/2017.
 */

public final class AutoRestartReceiver extends BroadcastReceiver {

    private static final String TAG = AutoRestartReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.i(TAG, "onReceive: received BOOT_COMPLETED");
            restartAppIfNeeded(context, ItchTraderActivity.createRebootIntent(context));
        }
        else if (Objects.equals(context.getString(R.string.intent_action_crash), intent.getAction())) {
            Log.i(TAG, "onReceive: received crash intent");

//            String stackTrace = MyUncaughtUEH.extractStackTrace(intent);
//            String dataStr = String.format(Report.EventConstants.DATA_APP_CRASHED_FORMAT, stackTrace);
//            Device mockDevice = DeviceManager.getMockDevice();
//            mockDevice.sendEventToServer(new Report(Report.EventConstants.EVENT_APP_CRASHED, Report.EventConstants.TYPE_ERROR, dataStr, false, false));

            restartAppIfNeeded(context, ItchTraderActivity.createCrashRecoveryIntent(context));
        }
    }

    private void restartAppIfNeeded(Context context, Intent intent) {
        Log.d(TAG, "restartAppIfNeeded() called with: context = [" + context + "]");
//        MyUncaughtUEH.setUEH();
//        GeneralSettings generalSettings = GeneralSettings.getInstance();
//        boolean userLoggedIn = generalSettings.isUserLoggedIn();
//        boolean exitPressed = generalSettings.isExitPressed();
//        Log.d(TAG, String.format("restartAppIfNeeded: isExitPressed = %s, isUserLoggedIn = %s", exitPressed, userLoggedIn));
//        if (!exitPressed && userLoggedIn) {
            Log.i(TAG, "restartAppIfNeeded: user logged in, restarting App");
            context.startActivity(intent);
//        }
    }
}
