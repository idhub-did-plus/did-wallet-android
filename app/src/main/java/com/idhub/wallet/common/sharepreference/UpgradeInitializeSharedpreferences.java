package com.idhub.wallet.common.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.idhub.wallet.App;

public class UpgradeInitializeSharedpreferences {
    private static UpgradeInitializeSharedpreferences upgradeInitializeSharepreferences;
    private static SharedPreferences sharedPreferences;
    private static String FILE_NAME = "wallet_upgrade";
    private final String WALLET_INITIALIZE_IS_SUCCESS = "wallet_initialize_is_success";
    private final String WALLET_IDENTITY_RESET_IS_SUCCESS = "wallet_identity_reset_is_success";
    private final String WALLET_UPGRADE_ACTION = "wallet_upgrade_action";

    private UpgradeInitializeSharedpreferences() {
        sharedPreferences = App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized UpgradeInitializeSharedpreferences getInstance() {
        if (upgradeInitializeSharepreferences == null) {
            upgradeInitializeSharepreferences = new UpgradeInitializeSharedpreferences();
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

    public boolean getIdentityResetIsSuccess() {
        return sharedPreferences.getBoolean(WALLET_IDENTITY_RESET_IS_SUCCESS, true);
    }

    public void setIdentityResetIsSuccess(boolean isSuccess) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(WALLET_IDENTITY_RESET_IS_SUCCESS, isSuccess).apply();
    }
}
