package com.idhub.wallet.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Patterns;
import android.webkit.URLUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static String formatUrl(String url) {
        if (URLUtil.isHttpsUrl(url) || URLUtil.isHttpUrl(url)) {
            return url;
        } else {
            if (isValidUrl(url)) {
                return "https://" + url;
            } else {
                return url;
            }
        }
    }

    public static boolean isValidUrl(String url) {
        Pattern p = Patterns.WEB_URL;
        Matcher m = p.matcher(url.toLowerCase());
        return m.matches();
    }

    public static void copy(Context context,String copyStr) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("CopyStr", copyStr);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }
}
