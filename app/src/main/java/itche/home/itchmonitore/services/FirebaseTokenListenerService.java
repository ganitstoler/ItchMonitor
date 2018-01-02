package itche.home.itchmonitore.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import itche.home.itchmonitore.managers.AppDataManager;

public class FirebaseTokenListenerService extends FirebaseInstanceIdService {

    private static final String TAG = FirebaseTokenListenerService.class.getSimpleName();

    public FirebaseTokenListenerService() {}

    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "onTokenRefresh: new token is " + token);

        AppDataManager.getInstance().setPushId(token);

    }
}
