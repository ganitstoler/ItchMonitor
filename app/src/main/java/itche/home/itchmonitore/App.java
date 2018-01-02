package itche.home.itchmonitore;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

import itche.home.itchmonitore.utils.AppExecutors;
import itche.home.itchmonitore.utils.MyUncaughtUEH;


/**
 * Class extending application to have access to application context from static methods
 * Created by eyal on 20/09/2016.
 */
public class App extends Application {

    private static final String TAG = App.class.getSimpleName();

    private static App sInstance;

    @Nullable
    public static Context getAppContext() {
        return sInstance;
    }

    public static void initUncaughtExceptionHandlers() {
        // DO NOT MOVE - must be before MyUncaughtUEH.setUEH() to set the correct default UncaughtExceptionHandler to executors
        AppExecutors.init();
        Log.d(TAG, "onCreate: setting new DefaultUEH");
        MyUncaughtUEH.setUEH();
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate() called");
        super.onCreate();
        sInstance = this;
		initUncaughtExceptionHandlers();
        FirebaseMessaging.getInstance().subscribeToTopic("news");


        //        try {
//            FirebaseApp.initializeApp(this.getApplicationContext());
//            FirebaseInstanceId.getInstance().deleteInstanceId();
//        } catch (IOException e) {
//            Log.e(TAG, "onCreate: " + e );
//            e.printStackTrace();
//        }
    }
}
