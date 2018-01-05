package itche.home.itchmonitore.utils;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import itche.home.itchmonitore.Currency;
import itche.home.itchmonitore.contents.AccountBalanceContent;
import itche.home.itchmonitore.services.FCMService;

/**
 * Created by ganitstoler on 05/01/2018.
 */

public class MessagesParser {
    private static final String TAG = MessagesParser.class.getSimpleName();
    
    public static void updateAccountBalanceContent(AccountBalanceContent content, Map<String, String> rawData){
        if (rawData.containsKey(MessageConstants.ACCOUNT_BALANCE_CURRENCIES)) {
            String s = rawData.get(MessageConstants.ACCOUNT_BALANCE_CURRENCIES);
            Log.i(TAG, "onMessageReceived, ACCOUNT_BALANCE_CURRENCIES: " + s);

            content.setCurrencies(parseCurrencies(s));
        } else {
            // TODO: 31/12/2017 Toast missing field
            Log.i(TAG, "onMessageReceived: missing " + MessageConstants.ACCOUNT_BALANCE_CURRENCIES);
            return;
        }

        if (rawData.containsKey(MessageConstants.ACCOUNT_BALANCE_USD)) {
            content.setUsd(Float.valueOf(rawData.get(MessageConstants.ACCOUNT_BALANCE_USD)));
        } else {
            // TODO: 31/12/2017 Toast missing field
            Log.i(TAG, "onMessageReceived: missing " + MessageConstants.ACCOUNT_BALANCE_USD);
            return;
        }

        if (rawData.containsKey(MessageConstants.ACCOUNT_BALANCE_BTC)) {
            content.setBtc(Float.valueOf(rawData.get(MessageConstants.ACCOUNT_BALANCE_BTC)));
        } else {
            // TODO: 31/12/2017 Toast missing field
            Log.i(TAG, "onMessageReceived: missing " + MessageConstants.ACCOUNT_BALANCE_BTC);
            return;
        }

        if (rawData.containsKey(MessageConstants.ACCOUNT_BALANCE_UPDATE)) {
            content.setUpdatedAt(rawData.get(MessageConstants.ACCOUNT_BALANCE_UPDATE));
        } else {
            // TODO: 31/12/2017 Toast missing field
            Log.i(TAG, "onMessageReceived: missing " + MessageConstants.ACCOUNT_BALANCE_UPDATE);
            return;
        }       
    }

    public static void updateAccountBalanceContent(AccountBalanceContent content, Bundle rawData, IGetStringOperator getStringOperator){
        if (rawData.containsKey(MessageConstants.ACCOUNT_BALANCE_CURRENCIES)) {
            String s = rawData.getString(MessageConstants.ACCOUNT_BALANCE_CURRENCIES);
            content.setCurrencies(parseCurrencies(s));
        } else {
            // TODO: 31/12/2017 Toast missing field
            Log.i(TAG, "onMessageReceived: missing " + MessageConstants.ACCOUNT_BALANCE_CURRENCIES);
            return;
        }

        if (rawData.containsKey(MessageConstants.ACCOUNT_BALANCE_USD)) {
            content.setUsd(Float.valueOf(rawData.getString(MessageConstants.ACCOUNT_BALANCE_USD)));
        } else {
            // TODO: 31/12/2017 Toast missing field
            Log.i(TAG, "onMessageReceived: missing " + MessageConstants.ACCOUNT_BALANCE_USD);
            return;
        }

        if (rawData.containsKey(MessageConstants.ACCOUNT_BALANCE_BTC)) {
            content.setBtc(Float.valueOf(rawData.getString(MessageConstants.ACCOUNT_BALANCE_BTC)));
        } else {
            // TODO: 31/12/2017 Toast missing field
            Log.i(TAG, "onMessageReceived: missing " + MessageConstants.ACCOUNT_BALANCE_BTC);
            return;
        }

        if (rawData.containsKey(MessageConstants.ACCOUNT_BALANCE_UPDATE)) {
            content.setUpdatedAt(rawData.getString(MessageConstants.ACCOUNT_BALANCE_UPDATE));
        } else {
            // TODO: 31/12/2017 Toast missing field
            Log.i(TAG, "onMessageReceived: missing " + MessageConstants.ACCOUNT_BALANCE_UPDATE);
            return;
        }
    }

    private static List<Currency> parseCurrencies(String rawCurrenciesData) {
        Log.d(TAG, "parseCurrencies() called with: rawCurrenciesData = [" + rawCurrenciesData + "]");
        List<Currency> currencies = new ArrayList<>();

        try {
            int j;
            String s = rawCurrenciesData;
            for (int i = 2; i< rawCurrenciesData.length(); ) {
                j = s.indexOf("\",\"");
                int addJ = 3;
                if (j<0) {
                    j = s.indexOf("\"]");
                    addJ = 2;
                }
                JSONObject object = new JSONObject(rawCurrenciesData.substring(i,i+j).replace("\\", ""));
                i = j+addJ;
                s = s.substring(j+addJ);
                Currency c = new Currency();
                c.setName(object.getString("name"));
                c.setSymbol(object.getString("symbol"));
                c.setAvailableBalance(Float.valueOf(object.getString("available_balance")));
                c.setChangeRate(Float.valueOf(object.getString("change_pct")));
                c.setExtBtcVal(Float.valueOf(object.getString("est_btc_val")));
                c.setPendingDeposit(Float.valueOf(object.getString("pending")));
                c.setReserved(Float.valueOf(object.getString("reserved")));
                c.setTotal(Float.valueOf(object.getString("total")));
                currencies.add(c);
            }

        } catch (Exception e){
            Log.e(TAG, "parseCurrencies: " + e.getMessage() );
        }
        return currencies;
    }

    public static boolean isNewMessage(@NonNull AccountBalanceContent content, Map<String, String> rawData) {
        String update = rawData.get(MessageConstants.ACCOUNT_BALANCE_UPDATE);
        return update != null && content.getUpdatedAt().compareTo(update) < 0;
    }

    public static boolean isNewMessage(@NonNull AccountBalanceContent content, Bundle rawData) {
        String updateStr = rawData.getString(MessageConstants.ACCOUNT_BALANCE_UPDATE);
        return updateStr != null && content.getUpdatedAt().compareTo(updateStr) < 0;
    }

    public interface IGetStringOperator{
        String getString(Object container, String key);
    }

    private static final class MessageConstants {
        private static final String MESSAGE_TAG_ACCOUNT_BALANCE = "account_balance";
        private static final String ACCOUNT_BALANCE_CURRENCIES = "currencies";
        private static final String ACCOUNT_BALANCE_USD = "usd";
        private static final String ACCOUNT_BALANCE_BTC = "btc";
        private static final String ACCOUNT_BALANCE_UPDATE = "update_time";
    }
}
