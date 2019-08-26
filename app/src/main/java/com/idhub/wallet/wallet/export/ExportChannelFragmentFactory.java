package com.idhub.wallet.wallet.export;

import android.content.Context;
import androidx.fragment.app.Fragment;

import com.idhub.wallet.R;
import com.idhub.wallet.wallet.export.fragment.ExportShowContentFragment;
import com.idhub.wallet.wallet.export.fragment.ExportShowQRCodeFragment;

public class ExportChannelFragmentFactory {
    private static String TAG = ExportChannelFragmentFactory.class.getName();

    public static Fragment createChannelFragment(Context context, String title,String data) {
        if (context.getResources().getString(R.string.wallet_keystore).equals(title)) {
            ExportShowContentFragment keystore = ExportShowContentFragment.newInstance("Keystore",data);
            return keystore;
        } else if (context.getResources().getString(R.string.wallet_QR_code).equals(title)) {
            return ExportShowQRCodeFragment.newInstance(data);
        } else if (context.getResources().getString(R.string.wallet_private_key).equals(title)) {
            ExportShowContentFragment privateKey = ExportShowContentFragment.newInstance("Private Key",data);
            return privateKey;
        } else {
            return new ExportShowContentFragment();
        }
    }

}
