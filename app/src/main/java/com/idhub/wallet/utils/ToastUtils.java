package com.idhub.wallet.utils;

import android.widget.Toast;
import com.idhub.wallet.App;

public class ToastUtils {
    public static void showShortToast(String message){
        Toast.makeText(App.getInstance(),message, Toast.LENGTH_SHORT).show();
    }
    public static void showLongToast(String message){
        Toast.makeText(App.getInstance(),message, Toast.LENGTH_LONG).show();
    }
}
