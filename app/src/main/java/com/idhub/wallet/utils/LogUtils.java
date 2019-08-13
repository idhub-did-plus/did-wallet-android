package com.idhub.wallet.utils;

import android.util.Log;

public class LogUtils {
    public static boolean isDebug = true;

    public static void i(String tag, String message) {
        if (isDebug)
            Log.i(tag, message);
    }

    public static void e(String tag, String message) {
        if (isDebug)
            Log.e(tag, message);
    }

    public static void e(String tag, String message, Throwable t) {
        if (isDebug)
            Log.e(tag, message, t);
    }

    public static void d(String tag, String message) {
        if (isDebug)
            Log.d(tag, message);
    }

    public static void v(String tag, String message) {
        if (isDebug)
            Log.v(tag, message);
    }

    public static void w(String tag, String message) {
        if (isDebug)
            Log.w(tag, message);
    }


}
