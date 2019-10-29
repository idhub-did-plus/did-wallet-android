package com.idhub.wallet.common.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.idhub.base.App;

public class Identity1484To1056BindSharedPreferences {
    private static Identity1484To1056BindSharedPreferences upgradeInitializeSharepreferences;
    private static SharedPreferences sharedPreferences;
    private static String FILE_NAME = "wallet_upgrade";
    private final String WALLET_INITIALIZE_IS_SUCCESS = "wallet_initialize_is_success";
    private final String WALLET_IDENTITY_RESET_IS_SUCCESS = "wallet_identity_reset_is_success";
    private final String WALLET_UPGRADE_ACTION = "wallet_upgrade_action";

    private Identity1484To1056BindSharedPreferences() {
        sharedPreferences = App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized Identity1484To1056BindSharedPreferences getInstance() {
        if (upgradeInitializeSharepreferences == null) {
            upgradeInitializeSharepreferences = new Identity1484To1056BindSharedPreferences();
        }
        return upgradeInitializeSharepreferences;
    }

    public boolean getUpgradeInitializeIsSuccess() {
        return sharedPreferences.getBoolean(WALLET_INITIALIZE_IS_SUCCESS, false);
    }

    public void setUpgradeInitializeIsSuccess(boolean isSuccess) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(WALLET_INITIALIZE_IS_SUCCESS, isSuccess).apply();
    }


    public boolean getIsUpgradeAction() {
        return sharedPreferences.getBoolean(WALLET_UPGRADE_ACTION, false);
    }

    public void setUpgradeAction(boolean isSuccess) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(WALLET_UPGRADE_ACTION, isSuccess).apply();
    }

}
