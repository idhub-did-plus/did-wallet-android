package com.idhub.wallet.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String dateFormatYMD(String time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd");
        String formatTime = "";
        try {
            Date date = simpleDateFormat.parse(time);
            formatTime = simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatTime;
    }
}
