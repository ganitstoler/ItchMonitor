package itche.home.itchmonitore.contents;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import itche.home.itchmonitore.BR;
import itche.home.itchmonitore.Currency;


public class AccountBalanceContent extends BaseObservable {

    private static final String TAG = AccountBalanceContent.class.getSimpleName();

    private float usd;
    private float btc;
    private List<Currency> currencies;
    private String updatedAt = "";

    @Bindable
    public float getUsd() {
        return usd;
    }

    public void setUsd(float usd) {
        this.usd = usd;
        notifyPropertyChanged(BR.usd);
    }

    @Bindable
    public float getBtc() {
        return btc;
    }

    public void setBtc(float btc) {
        this.btc = btc;
        notifyPropertyChanged(BR.btc);
    }

    @Bindable
    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
        notifyPropertyChanged(BR.currencies);
    }

    @Bindable
    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        notifyPropertyChanged(BR.updatedAt);
    }

    public void reset(){
        Log.d(TAG, "reset() called");
        setBtc(0);
        setUsd(0);
        setCurrencies(new ArrayList<Currency>());
        setUpdatedAt("");
    }
}
