package com.idhub.base.launage;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;

import java.util.Locale;

public class LocalUtils {

    public static void setApplicationLanguage(Context context) {

        String localLanguage = LocaleSharpreferences.getInstance(context).getLocalLanguage();
        Locale locale;
        if (TextUtils.isEmpty(localLanguage)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locale = LocaleList.getDefault().get(0);
            } else {
                locale = Locale.getDefault();
            }
            String language = locale.getLanguage();
            if (language.contains(Locale.CHINESE.toLanguageTag())) {
                LocaleSharpreferences.getInstance(context).setLocalLanguage(Locale.CHINESE.toLanguageTag());
                locale = Locale.forLanguageTag(Locale.CHINESE.toLanguageTag());
            }else {
                LocaleSharpreferences.getInstance(context).setLocalLanguage(Locale.ENGLISH.toLanguageTag());
                locale = Locale.forLanguageTag(Locale.ENGLISH.toLanguageTag());
            }
        } else {
            locale = Locale.forLanguageTag(localLanguage);//获取sp里面保存的语言
        }

        // 更新 app 环境
        Configuration conf = context.getResources().getConfiguration();
        conf.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            conf.setLocales(localeList);
            context.createConfigurationContext(conf);
            Locale.setDefault(locale);
        } else {
            conf.setLocale(locale);
            context.createConfigurationContext(conf);
            Locale.setDefault(locale);
        }
        context.getResources().updateConfiguration(conf, context.getResources().getDisplayMetrics());
    }

}
