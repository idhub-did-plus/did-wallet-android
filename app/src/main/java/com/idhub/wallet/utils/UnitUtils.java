package com.idhub.wallet.utils;

import android.content.res.Resources;

public class UnitUtils {
    public static int dp2px(float dpVal){
        float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpVal * scale + 0.5f);
    }
}
