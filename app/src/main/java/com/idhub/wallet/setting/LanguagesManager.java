package com.idhub.wallet.setting;

import com.idhub.wallet.App;
import com.idhub.wallet.R;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LanguagesManager {
    public static Map<String, String> languages = new HashMap<>();
    static {
        languages.put(Locale.ENGLISH.toLanguageTag(), "English");
        languages.put(Locale.CHINA.toLanguageTag(), "简体中文");
    }
}
