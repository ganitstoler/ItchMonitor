package itche.home.itchmonitore.services;


import android.content.Intent;
import android.support.annotation.StringDef;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import itche.home.itchmonitore.Currency;
import itche.home.itchmonitore.contents.AccountBalanceContent;
import itche.home.itchmonitore.managers.AppDataManager;
import itche.home.itchmonitore.utils.MessagesParser;


public class FCMService extends FirebaseMessagingService {

    private static final String TAG = FCMService.class.getSimpleName();

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({MessageType.VALVE_COMMAND})
    private @interface MessageType {
        String VALVE_COMMAND = "valve_command";
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived() called with: remoteMessage = [" + remoteMessage + "] from " + remoteMessage.getFrom());

        Map<String, String> data = remoteMessage.getData();
        String from = remoteMessage.getFrom();
        String message_tag = remoteMessage.getNotification().getTag();
        Log.i(TAG, "onMessageReceived: " + data);
        if (from.equals("/topics/news") && data.size() > 0) {
            if (message_tag.equals(MessageConstants.MESSAGE_TAG_ACCOUNT_BALANCE)) {
                AccountBalanceContent content = AppDataManager.getInstance().getAccountBalanceContent();
                if (MessagesParser.isNewMessage(content, data)) {
                    MessagesParser.updateAccountBalanceContent(content, data);
                }
            }
        }
    }

    private static final class MessageConstants {
        private static final String MESSAGE_TAG_ACCOUNT_BALANCE = "account_balance";
    }

}
