package com.idhub.wallet.common.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.idhub.wallet.App;

import java.util.Locale;

public class LocaleSharpreferences {

    private static LocaleSharpreferences mLocaleSharpreferences;
    private static SharedPreferences sharedPreferences;
    private String FILE_NAME = "wallet_local_info";
    private final String WALLET_BLOCK_NUMBERS = "wallet_local_language";

    private LocaleSharpreferences() {
        sharedPreferences = App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized LocaleSharpreferences getInstance() {
        if (mLocaleSharpreferences == null) {
            mLocaleSharpreferences = new LocaleSharpreferences();
        }
        return mLocaleSharpreferences;
    }

    public String getLocalLanguage() {
        return sharedPreferences.getString(WALLET_BLOCK_NUMBERS, Locale.ENGLISH.toLanguageTag());
    }

    public boolean setLocalLanguage(String language) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
     return    edit.putString(WALLET_BLOCK_NUMBERS, language).commit();
    }
}
