package com.idhub.wallet.createmanager.walletimport;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.idhub.wallet.R;
import com.idhub.wallet.createmanager.walletimport.fragment.ImportWalletFromKeystoreFragment;
import com.idhub.wallet.createmanager.walletimport.fragment.ImportWalletFromMnemonicFragment;
import com.idhub.wallet.createmanager.walletimport.fragment.ImportWalletFromPrivateKeyFragment;

public class ImportWalletFragmentFactory {
    private static String TAG = ImportWalletFragmentFactory.class.getName();

    public static Fragment createChannelFragment(Context context, String title) {
        if (context.getResources().getString(R.string.wallet_private_key).equals(title)) {
            return new ImportWalletFromPrivateKeyFragment();
        } else if (context.getResources().getString(R.string.wallet_mnemonic).equals(title)) {
            return new ImportWalletFromMnemonicFragment();
        } else if (context.getString(R.string.wallet_keystore).equals(title)) {
            return new ImportWalletFromKeystoreFragment();
        } else {
            return new ImportWalletFromKeystoreFragment();
        }
    }

}
