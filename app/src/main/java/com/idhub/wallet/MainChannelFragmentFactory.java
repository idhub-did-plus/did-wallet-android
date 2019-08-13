package com.idhub.wallet;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.idhub.wallet.dapp.DappFragment;
import com.idhub.wallet.hository.HositoryFragment;
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
        } else if (context.getResources().getString(R.string.wallet_history).equals(title)) {
            return new HositoryFragment();
        } else {
            return new MeFragment();
        }
    }

}
