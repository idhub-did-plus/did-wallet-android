package com.idhub.wallet;



import android.util.Log;

import com.idhub.wallet.utils.QRcodeAnalysisUtils;
import com.idhub.wallet.wallet.mainfragment.QRCodeType;

import org.java_websocket.util.Base64;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Locale;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        System.out.println(Locale.ENGLISH.toLanguageTag());
        System.out.println(Locale.forLanguageTag("en"));
    }
}