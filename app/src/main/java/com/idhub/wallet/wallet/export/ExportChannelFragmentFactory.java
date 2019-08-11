package com.idhub.wallet.wallet.export;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.idhub.wallet.R;
import com.idhub.wallet.wallet.export.fragment.ExportShowContentFragment;
import com.idhub.wallet.wallet.export.fragment.ExportShowQRCodeFragment;

public class ExportChannelFragmentFactory {
    private static String TAG = ExportChannelFragmentFactory.class.getName();

    public static Fragment createChannelFragment(Context context, String title) {
        if (context.getResources().getString(R.string.wallet_keystore).equals(title)) {
            ExportShowContentFragment keystore = ExportShowContentFragment.newInstance("Keystore");
            return keystore;
        } else if (context.getResources().getString(R.string.wallet_QR_code).equals(title)) {
            return new ExportShowQRCodeFragment();
        } else if (context.getResources().getString(R.string.wallet_private_key).equals(title)) {
            ExportShowContentFragment privateKey = ExportShowContentFragment.newInstance("Private Key");
            return privateKey;
        } else {
            return new ExportShowContentFragment();
        }
    }

}
