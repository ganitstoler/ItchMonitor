package itche.home.itchmonitore.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import itche.home.itchmonitore.App;
import itche.home.itchmonitore.contents.AccountBalanceContent;

/**
 * Created by ganitstoler on 26/12/2017.
 */

public class AppDataManager {
    private static final String TAG = AppDataManager.class.getSimpleName();

    private static final String PREF_DB_NAME = "itch_prefs_db";
    private static final String PREF_DB_KEY_PUSH_ID = "push_id";
    private final SharedPreferences mPrefs;
    private String mPushId;
    private AccountBalanceContent mAccountBalanceContent;

    public static AppDataManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private AppDataManager(Context context) {
        mPushId = FirebaseInstanceId.getInstance().getToken();
        mPrefs = context.getSharedPreferences(PREF_DB_NAME, Context.MODE_PRIVATE);
    }

    @Nullable
    public AccountBalanceContent getAccountBalanceContent() {
        return mAccountBalanceContent;
    }

    public void setAccountBalanceContent(AccountBalanceContent mAccountBalanceContent) {
        this.mAccountBalanceContent = mAccountBalanceContent;
    }

    public String getPushId() {
        if (mPushId == null) {
            mPushId = mPrefs.getString(PREF_DB_KEY_PUSH_ID, null);
        }
        return mPushId;
    }

    public void setPushId(@NonNull String pushId) {
        Log.d(TAG, "setPushId() called with: pushId = [" + pushId + "]");
        if (!pushId.equals(mPushId)) {
            mPrefs.edit().putString(PREF_DB_KEY_PUSH_ID, pushId).apply();
            mPushId = pushId;
        }


    }

    private static final class InstanceHolder {
        private static final AppDataManager INSTANCE = new AppDataManager(App.getAppContext());
    }
}
