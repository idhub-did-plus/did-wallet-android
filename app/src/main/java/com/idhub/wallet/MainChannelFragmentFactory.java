package com.idhub.wallet;

import android.content.Context;
import androidx.fragment.app.Fragment;

import com.idhub.wallet.dapp.DappFragment;
import com.idhub.wallet.setting.SettingFragment;
import com.idhub.wallet.me.MeFragment;
import com.idhub.wallet.wallet.mainfragment.WalletFragment;

public class MainChannelFragmentFactory {
    private static String TAG = MainChannelFragmentFactory.class.getName();

    public static Fragment createChannelFragment(Context context,String title) {
        if (context.getResources().getString(R.string.wallet_me).equals(title)) {
            return new MeFragment();
        } else if (context.getResources().getString(R.string.wallet_wallet).equals(title)) {
            return new WalletFragment();
        } else if (context.getResources().getString(R.string.wallet_dapp).equals(title)) {
            return new DappFragment();
        } else if (context.getResources().getString(R.string.wallet_setting).equals(title)) {
            return new SettingFragment();
        } else {
            return new MeFragment();
        }
    }

}
