package com.idhub.wallet.didhub.util;

import java.util.Calendar;
import java.util.TimeZone;

public class DateUtil {
  public static long getUTCTime() {
    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
    return cal.getTimeInMillis()/1000;
  }
}