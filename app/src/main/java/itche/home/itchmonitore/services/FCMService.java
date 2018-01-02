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
import java.util.List;
import java.util.Map;

import itche.home.itchmonitore.Currency;
import itche.home.itchmonitore.contents.AccountBalanceContent;
import itche.home.itchmonitore.managers.AppDataManager;


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
        Log.i(TAG, "onMessageReceived: " + remoteMessage.getMessageType());
        String message_tag = remoteMessage.getNotification().getTag();
        Log.i(TAG, "onMessageReceived: " + message_tag);
        Log.i(TAG, "onMessageReceived: " + remoteMessage.getNotification());
        Log.i(TAG, "onMessageReceived: " + remoteMessage.getData());

        if (from.equals("/topics/news") && data.size() > 0) {

            if (message_tag.equals(MessageConstants.MESSAGE_TAG_ACCOUNT_BALANCE)) {

                AccountBalanceContent content = new AccountBalanceContent();

                if (data.containsKey(MessageConstants.ACCOUNT_BALANCE_CURRENCIES)) {
                    Log.i(TAG, "onMessageReceived, ACCOUNT_BALANCE_CURRENCIES: " + data.get(MessageConstants.ACCOUNT_BALANCE_CURRENCIES));
                    content.setCurrencies(parseCurrencies((List<String>)data.get(MessageConstants.ACCOUNT_BALANCE_CURRENCIES)));
                } else {
                    // TODO: 31/12/2017 Toast missing field
                    Log.i(TAG, "onMessageReceived: missing " + MessageConstants.ACCOUNT_BALANCE_CURRENCIES);
                    return;
                }

                if (data.containsKey(MessageConstants.ACCOUNT_BALANCE_USD)) {
                    content.setUsd(Float.valueOf(data.get(MessageConstants.ACCOUNT_BALANCE_USD)));
                } else {
                    // TODO: 31/12/2017 Toast missing field
                    Log.i(TAG, "onMessageReceived: missing " + MessageConstants.ACCOUNT_BALANCE_USD);
                    return;
                }

                if (data.containsKey(MessageConstants.ACCOUNT_BALANCE_BTC)) {
                    content.setBtc(Float.valueOf(data.get(MessageConstants.ACCOUNT_BALANCE_BTC)));
                } else {
                    // TODO: 31/12/2017 Toast missing field
                    Log.i(TAG, "onMessageReceived: missing " + MessageConstants.ACCOUNT_BALANCE_BTC);
                    return;
                }

                if (data.containsKey(MessageConstants.ACCOUNT_BALANCE_UPDATE)) {
                    content.setUpdatedAt(data.get(MessageConstants.ACCOUNT_BALANCE_UPDATE));
                } else {
                    // TODO: 31/12/2017 Toast missing field
                    Log.i(TAG, "onMessageReceived: missing " + MessageConstants.ACCOUNT_BALANCE_UPDATE);
                    return;
                }

                AppDataManager.getInstance().setAccountBalanceContent(content);
            }
        }
    }

    private List<Currency> parseCurrencies(List<String> rawCurrenciesData) {
//
        for (String currencyRaw: rawCurrenciesData) {
            Log.d(TAG, "parseCurrencies() called with: rawCurrenciesData = [" + rawCurrenciesData + "] [" + currencyRaw.replace("\n","").replace("\\", "") + "]");
        }
        return null;
    }

//    ["{\n    " +
//            "\"available_balance\": \"available_balance1\"," +
//            "\n    \"change_pct\": -0.2," +
//            "\n    \"est_btc_val\": 3.55555," +
//            "\n    \"name\": \"test_intent\"," +
//            "\n    \"pending\": \"pending_deposit1\"," +
//            "\n    \"reserved\": \"reserved1\"," +
//            "\n    \"symbol\": \"symbol1\"," +
//            "\n    \"total\": 5.222\n}",\
//
//            "{\n   " +
//            " \"available_balance\": \"available_balance1\"," +
//            "\n    \"change_pct\": 0.6," +
//            "\n    \"est_btc_val\": 18000," +
//            "\n    \"name\": \"name2\"," +
//            "\n    \"pending\": \"pending_deposit1\"," +
//            "\n    \"reserved\": \"reserved1\"," +
//            "\n    \"symbol\": \"symbol2\"," +
//            "\n    \"total\": 5.32\n}"
//            ]
//
//


    private static final class MessageConstants {
        private static final String MESSAGE_TAG_ACCOUNT_BALANCE = "account_balance";
        private static final String ACCOUNT_BALANCE_CURRENCIES = "currencies";
        private static final String ACCOUNT_BALANCE_USD = "usd";
        private static final String ACCOUNT_BALANCE_BTC = "btc";
        private static final String ACCOUNT_BALANCE_UPDATE = "update_time";
    }

}
