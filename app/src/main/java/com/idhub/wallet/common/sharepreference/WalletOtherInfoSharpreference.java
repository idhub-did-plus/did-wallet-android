package com.idhub.wallet.common.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.idhub.wallet.App;
import com.idhub.wallet.setting.WalletNodeManager;

public class WalletOtherInfoSharpreference {
    private static WalletOtherInfoSharpreference sWalletOtherInfoSharpreference;
    private static SharedPreferences sharedPreferences;
    private String FILE_NAME = "wallet_other_info";
    private String WALLET_SELECT_ID = "wallet_select_id";
    private String WALLET_RECOVER_ADDRESS = "wallet_recover_address";
    private String WALLET_EIN = "wallet_ein";
    private String WALLET_NODE = "wallet_node";

    private WalletOtherInfoSharpreference() {
        sharedPreferences = App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

    }

    public static synchronized WalletOtherInfoSharpreference getInstance() {
        if (sWalletOtherInfoSharpreference == null) {
            sWalletOtherInfoSharpreference = new WalletOtherInfoSharpreference();
        }
        return sWalletOtherInfoSharpreference;
    }

    public String getEIN() {
        return sharedPreferences.getString(WALLET_EIN, "");
    }

    public void setEIN(String ein) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(WALLET_EIN, ein).apply();
    }

    public String getRecoverAddress() {
        return sharedPreferences.getString(WALLET_RECOVER_ADDRESS, "");
    }

    public void setRecoverAddress(String address) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(WALLET_RECOVER_ADDRESS, address).apply();
    }

    public String getSelectedId() {
        return sharedPreferences.getString(WALLET_SELECT_ID, "");
    }

    public boolean setSelectedId(String id) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        boolean commit = edit.putString(WALLET_SELECT_ID, id).commit();
        return commit;
    }
    public String getNode() {
        return sharedPreferences.getString(WALLET_NODE, WalletNodeManager.nodes.get(1));
    }

    public boolean setNode(String node) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
      return   edit.putString(WALLET_NODE, node).commit();
    }

}
