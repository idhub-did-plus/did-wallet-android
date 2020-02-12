package com.idhub.wallet;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.idhub.wallet.dapp.DappFragment;
import com.idhub.wallet.dex.DexFragment;
import com.idhub.wallet.home.HomeFragment;
import com.idhub.wallet.setting.SettingFragment;
import com.idhub.wallet.me.MeFragment;
import com.idhub.wallet.wallet.mainfragment.WalletFragment;

public class MainChannelFragmentFactory {
    private static String TAG = MainChannelFragmentFactory.class.getName();

    public static Fragment createChannelFragment(Context context, String title) {

        if (context.getResources().getString(R.string.wallet_dex).equals(title)) {
            return new DexFragment();
        } else if (context.getResources().getString(R.string.wallet_home).equals(title)) {
            return new HomeFragment();
        } else if (context.getResources().getString(R.string.wallet_assets).equals(title)) {
            return new WalletFragment();
        } else if (context.getResources().getString(R.string.wallet_dapp).equals(title)) {
            return new DappFragment();
        } else if (context.getResources().getString(R.string.wallet_setting).equals(title)) {
            return new SettingFragment();
        } else {
            return new HomeFragment();
        }
    }

}
