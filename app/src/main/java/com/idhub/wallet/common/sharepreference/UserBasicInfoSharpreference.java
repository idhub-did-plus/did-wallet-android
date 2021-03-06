package com.idhub.wallet.common.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.idhub.base.App;
import com.idhub.wallet.createmanager.UserBasicInfoEntity;

public class UserBasicInfoSharpreference {
    private static UserBasicInfoSharpreference sUserBasicInfoSharpreference;
    private static SharedPreferences sharedPreferences;
    private String FILE_NAME = "wallet_user_basic_info";
    private final String WALLET_BASIC_USER_HEAD_PATH = "basic_user_head_path";
    private final String WALLET_BASIC_USER_NAME = "basic_user_head";
    private final String WALLET_BASIC_USER_SIGNATURE = "basic_user_signature";

    private UserBasicInfoSharpreference() {
        sharedPreferences = App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized UserBasicInfoSharpreference getInstance() {
        if (sUserBasicInfoSharpreference == null) {
            sUserBasicInfoSharpreference = new UserBasicInfoSharpreference();
        }
        return sUserBasicInfoSharpreference;
    }

    public boolean setUserBasicInfo(UserBasicInfoEntity userBasicInfoEntity) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(WALLET_BASIC_USER_HEAD_PATH, userBasicInfoEntity.headPath);
        edit.putString(WALLET_BASIC_USER_NAME, userBasicInfoEntity.name);
        edit.putString(WALLET_BASIC_USER_SIGNATURE, userBasicInfoEntity.signature);
        return edit.commit();
    }

    public UserBasicInfoEntity getUserBasicInfo() {
        UserBasicInfoEntity userBasicInfoEntity = new UserBasicInfoEntity();
        userBasicInfoEntity.headPath = sharedPreferences.getString(WALLET_BASIC_USER_HEAD_PATH, "");
        userBasicInfoEntity.name = sharedPreferences.getString(WALLET_BASIC_USER_NAME, "");
        userBasicInfoEntity.signature = sharedPreferences.getString(WALLET_BASIC_USER_SIGNATURE, "");
        return userBasicInfoEntity;
    }

    public void setUserName(String name) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(WALLET_BASIC_USER_NAME, name);
        edit.apply();
    }

    public void setUserSignature(String signature) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(WALLET_BASIC_USER_SIGNATURE, signature);
        edit.apply();
    }

}
