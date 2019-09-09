package com.idhub.wallet;


import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(1567668125 * 1000L);
        System.out.println(date.toString());
        String s = sdf.format(date);
        System.out.println(s);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String format = sdf.format(date);
        System.out.println(format);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        System.out.println(sdf.format(date));
    }
}