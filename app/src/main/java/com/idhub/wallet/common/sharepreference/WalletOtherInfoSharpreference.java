package com.idhub.wallet.common.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.idhub.base.App;
import com.idhub.base.node.WalletNodeManager;
import com.idhub.base.node.WalletNoteSharedPreferences;

public class WalletOtherInfoSharpreference {
    private static WalletOtherInfoSharpreference sWalletOtherInfoSharpreference;
    private static SharedPreferences sharedPreferences;
    private String FILE_NAME = "wallet_other_info";
    private String WALLET_SELECT_ID = "wallet_select_id";


    private WalletOtherInfoSharpreference() {
        sharedPreferences = App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized WalletOtherInfoSharpreference getInstance() {
        if (sWalletOtherInfoSharpreference == null) {
            sWalletOtherInfoSharpreference = new WalletOtherInfoSharpreference();
        }
        return sWalletOtherInfoSharpreference;
    }


    public String getSelectedId() {
        return sharedPreferences.getString(WALLET_SELECT_ID, "");
    }

    public boolean setSelectedId(String id) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        boolean commit = edit.putString(WALLET_SELECT_ID, id).commit();
        return commit;
    }
}
