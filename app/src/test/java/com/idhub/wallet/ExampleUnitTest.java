package com.idhub.wallet;

import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.network.DIDApiUseCase;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
//        BigInteger bigInteger = new BigInteger("152000010000000000000");
//        float v = NumericUtil.bigInteger18ToFloat(bigInteger);
//        System.out.println(v);
        DIDApiUseCase.searchTokenBalance("");

    }
}