package com.idhub.wallet.common.zxinglib.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by liwenming on 17/7/9.
 */

public class TimeUtil {

    public static String getTimeYYYYMMDDHHMMSS() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date d1 = new Date(System.currentTimeMillis());
        return format.format(d1);
    }

    public static String getTimeYYYYMMDD() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = new Date(System.currentTimeMillis());
        return format.format(d1);
    }

    public static String getTimeYYYYMMDD(long date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = new Date(date);
        return format.format(d1);
    }


    public static String getTimeYYYYMMDDHHmm(long date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d1 = new Date(date);
        return format.format(d1);
    }

    public static String getTimeYYYYMMDDHHmm() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
        Date d1 = new Date(System.currentTimeMillis());
        return format.format(d1)+":00";
    }


    public static String getTimeHmmss(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        Date d1 = new Date(time);
        return format.format(d1);
    }

    public static long getTime(String timeString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        try {
            d = sdf.parse(timeString);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getLongTimeYYYYMMDD(String timeString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        try {
            d = sdf.parse(timeString);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static long getLongTime(String timeString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:00");
        Date d;
        try {
            d = sdf.parse(timeString);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
