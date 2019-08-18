package com.idhub.wallet.common.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.idhub.wallet.App;

public class WalletOtherSharpreference {
    private static WalletOtherSharpreference sWalletOtherSharpreference;
    private static SharedPreferences sharedPreferences;
    private String FILE_NAME = "wallet_other_info";
    private String WALLET_SELECT = "wallet_select";

    private WalletOtherSharpreference() {
        sharedPreferences = App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

    }

    public static synchronized WalletOtherSharpreference getInstance() {
        if (sWalletOtherSharpreference == null) {
            sWalletOtherSharpreference = new WalletOtherSharpreference();
        }
        return sWalletOtherSharpreference;
    }

    public String getSelectedId() {
        return sharedPreferences.getString(WALLET_SELECT, "");
    }

    public boolean setSelectedId(String id) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        boolean commit = edit.putString(WALLET_SELECT, id).commit();
        return commit;
    }


}
