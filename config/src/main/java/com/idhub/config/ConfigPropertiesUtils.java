package com.idhub.config;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class ConfigPropertiesUtils {
    private static Properties getProperties(Context context) {
        Properties props = new Properties();
        try {
            InputStream in = context.getAssets().open("config.properties");
            props.load(in);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return props;
    }

    public static List<String> getNotes(Context context) {
        Properties properties = getProperties(context);
        String notes = properties.getProperty("notes");
        List<String> notesList = new LinkedList<>();
        if (!TextUtils.isEmpty(notes)) {
            notesList = new Gson().fromJson(notes, new TypeToken<List<String>>() {
            }.getType());
        }
        return notesList;
    }

    public static String getBaseUrl(Context context) {
        Properties properties = getProperties(context);
        String baseUrl = properties.getProperty("baseUrl");
        return baseUrl;
    }

    public static String getAssets(Context context) {
        Properties properties = getProperties(context);
        String assets = properties.getProperty("tokens");
        return assets;
    }

    public static String getContractAddress(Context context) {
        Properties properties = getProperties(context);
        String contractAddress = properties.getProperty("contractAddress");
        return contractAddress;
    }
}
