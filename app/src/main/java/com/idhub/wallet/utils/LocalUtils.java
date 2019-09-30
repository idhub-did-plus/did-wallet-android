package com.idhub.wallet.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;

import com.idhub.wallet.common.sharepreference.LocaleSharpreferences;

import java.util.Locale;

public class LocalUtils {

//    public static Context setLocal(Context context) {
//        return updateLocale(context);
//    }
//
//    /**
//     * 更新Locale
//     *
//     */
//    public static Context updateLocale(Context context) {
//        Configuration _Configuration = context.getResources().getConfiguration();
//        String localLanguage = LocaleSharpreferences.getInstance(context).getLocalLanguage();
//        Locale locale = Locale.forLanguageTag(localLanguage);
//        Locale.setDefault(locale);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            _Configuration.setLocale(locale);
//            context = context.createConfigurationContext(_Configuration);
//        } else {
//            _Configuration.locale = locale;
//        }
//        DisplayMetrics _DisplayMetrics =context.getResources().getDisplayMetrics();
//        context.getResources().updateConfiguration(_Configuration, _DisplayMetrics);
//        return context;
//    }

    public static void setApplicationLanguage(Context context){
        //        Resources resources = context.getResources();
//        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        String localLanguage = LocaleSharpreferences.getInstance(context).getLocalLanguage();
        Locale locale = Locale.forLanguageTag(localLanguage);//获取sp里面保存的语言


        // 更新 app 环境
        Configuration conf = context.getResources().getConfiguration();
        conf.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            conf.setLocales(localeList);
            context.createConfigurationContext(conf);
            Locale.setDefault(locale);
        }else {
            conf.setLocale(locale);
            context.createConfigurationContext(conf);
            Locale.setDefault(locale);
        }
        context.getResources().updateConfiguration(conf, context.getResources().getDisplayMetrics());

//        // 更新系统 环境
//        Configuration config = Resources.getSystem().getConfiguration();
//        config.locale = locale;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            LocaleList localeList = new LocaleList(locale);
//            LocaleList.setDefault(localeList);
//            config.setLocales(localeList);
//            context.createConfigurationContext(config);
//            conf.setLocales(localeList);
//            context.createConfigurationContext(conf);
//            Locale.setDefault(locale);
//        }else {
//            config.setLocale(locale);
//            context.createConfigurationContext(config);
//            conf.setLocale(locale);
//            context.createConfigurationContext(conf);
//            Locale.setDefault(locale);
//        }
//        Configuration configuration = Resources.getSystem().getConfiguration();
//
//        Configuration configuration1 = context.getResources().getConfiguration();
//
//        Resources.getSystem().updateConfiguration(config, context.getResources().getDisplayMetrics());

    }
//    public static ContextWrapper wrap(Context context) {
//        String localLanguage = LocaleSharpreferences.getInstance().getLocalLanguage();
//        Locale locale = new Gson().fromJson(localLanguage, Locale.class);
//        context = getLanContext(context, locale);
//        return new ContextWrapper(context);
//    }
//
//    private static Context getLanContext(Context context, Locale pNewLocale) {
//        Resources res = context.getApplicationContext().getResources();//1、获取Resources
//        Configuration configuration = res.getConfiguration();//2、获取Configuration
//        //3、设置Locale并初始化Context
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            configuration.setLocale(pNewLocale);
//            LocaleList localeList = new LocaleList(pNewLocale);
//            LocaleList.setDefault(localeList);
//            configuration.setLocales(localeList);
//            context = context.createConfigurationContext(configuration);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            configuration.setLocale(pNewLocale);
//            context = context.createConfigurationContext(configuration);
//        }
//        return context;
//    }

}
