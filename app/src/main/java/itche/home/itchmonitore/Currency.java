package itche.home.itchmonitore;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import itche.home.itchmonitore.BR;

/**
 * Created by ganitstoler on 30/12/2017.
 */

public class Currency extends BaseObservable{

    private String name;
    private String symbol;
    private float availableBalance;
    private float pendingDeposit;
    private float reserved;
    private float total;
    private float extBtcVal;
    private float changeRate;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
        notifyPropertyChanged(BR.symbol);
    }

    @Bindable
    public float getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(float availableBalance) {
        this.availableBalance = availableBalance;
          notifyPropertyChanged(BR.availableBalance);
  }

    @Bindable
    public float getPendingDeposit() {
        return pendingDeposit;
    }

    public void setPendingDeposit(float pendingDeposit) {
        this.pendingDeposit = pendingDeposit;
           notifyPropertyChanged(BR.pendingDeposit);
    }

    @Bindable
    public float getReserved() {
        return reserved;
    }

    public void setReserved(float reserved) {
        this.reserved = reserved;
        notifyPropertyChanged(BR.reserved);
    }

    @Bindable
    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
        notifyPropertyChanged(BR.total);
    }

    @Bindable
    public float getExtBtcVal() {
        return extBtcVal;
    }

    public void setExtBtcVal(float extBtcVal) {
        this.extBtcVal = extBtcVal;
        notifyPropertyChanged(BR.extBtcVal);
    }

    @Bindable
    public float getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(float changeRate) {
        this.changeRate = changeRate;
        notifyPropertyChanged(BR.changeRate);
    }
}
