package itche.home.itchmonitore.activities.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import itche.home.itchmonitore.R;
import itche.home.itchmonitore.databinding.FragmentAccountBalanceBinding;
import itche.home.itchmonitore.managers.AppDataManager;

/**
 * Created by ganitstoler on 04/01/2018.
 */

public class AccountBalanceFragment extends Fragment {

    private FragmentAccountBalanceBinding mBinding;

    public static AccountBalanceFragment createFragment(){
        return new AccountBalanceFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_balance, container, false);
        mBinding.setContent(AppDataManager.getInstance().getAccountBalanceContent());
        return mBinding.getRoot();


    }
}
