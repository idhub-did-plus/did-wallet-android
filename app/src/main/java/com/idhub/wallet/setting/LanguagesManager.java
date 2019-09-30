package com.idhub.wallet.setting;

import com.google.gson.Gson;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class LanguagesManager {
    public static Map<String, String> languages = new LinkedHashMap<>();
    static {
        languages.put(Locale.ENGLISH.toLanguageTag(), "English");
        languages.put(Locale.CHINESE.toLanguageTag(), "简体中文");
    }
}
