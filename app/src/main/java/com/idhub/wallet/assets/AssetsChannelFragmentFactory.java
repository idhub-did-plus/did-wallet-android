package com.idhub.wallet.assets;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.idhub.wallet.R;
import com.idhub.wallet.assets.fragment.CollectiblesFragment;
import com.idhub.wallet.assets.fragment.STAssetsFragment;
import com.idhub.wallet.assets.fragment.TokenFragment;
import com.idhub.wallet.dapp.DappFragment;
import com.idhub.wallet.dex.DexFragment;
import com.idhub.wallet.home.HomeFragment;
import com.idhub.wallet.setting.SettingFragment;

public class AssetsChannelFragmentFactory {
    private static String TAG = AssetsChannelFragmentFactory.class.getName();

    public static Fragment createChannelFragment(Context context, String title) {

        if (context.getResources().getString(R.string.wallet_securities).equals(title)) {
            return new STAssetsFragment();
        } else if (context.getResources().getString(R.string.wallet_token).equals(title)) {
            return new TokenFragment();
        } else if (context.getResources().getString(R.string.wallet_collectibles).equals(title)) {
            return new CollectiblesFragment();
        }  else {
            return new STAssetsFragment();
        }
    }

}
