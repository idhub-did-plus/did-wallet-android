package com.idhub.wallet;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            datas.add(String.valueOf(i));
        }
        List<String> entities = datas.subList(datas.size() - 50, datas.size());
        for (String entity : entities) {
            System.out.println(entity);
        }
    }
}