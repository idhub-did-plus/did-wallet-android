package com.idhub.wallet.setting;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.idhub.base.App;
import com.idhub.wallet.R;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class LanguagesManager {

    public static Map<String, String> getLanguages(Context context) {
        Map<String, String> languages = new LinkedHashMap<>();
        String string = context.getString(R.string.wallet_auto);
        languages.put("", string);
        languages.put(Locale.ENGLISH.toLanguageTag(), context.getString(R.string.wallet_english));
        languages.put(Locale.CHINESE.toLanguageTag(), context.getString(R.string.wallet_chinese));
        return languages;
    }
}
