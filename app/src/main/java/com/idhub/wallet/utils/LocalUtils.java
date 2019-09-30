package com.idhub.wallet.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.util.DisplayMetrics;

import com.idhub.wallet.App;
import com.idhub.wallet.common.sharepreference.LocaleSharpreferences;

import java.util.Locale;

public class LocalUtils {
    /**
     * 更新Locale
     *
     */
    public static void updateLocale(Context context) {
        Configuration _Configuration = context.getResources().getConfiguration();
        String localLanguage = LocaleSharpreferences.getInstance().getLocalLanguage();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            _Configuration.setLocale(Locale.forLanguageTag(localLanguage));
        } else {
            _Configuration.locale = Locale.forLanguageTag(localLanguage);
        }
        DisplayMetrics _DisplayMetrics =context.getResources().getDisplayMetrics();
        context.getResources().updateConfiguration(_Configuration, _DisplayMetrics);
    }
}
