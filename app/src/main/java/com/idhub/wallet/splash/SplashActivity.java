package com.idhub.wallet.splash;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.idhub.wallet.MainActivity;
import com.idhub.wallet.R;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.identitymanager.identitycreate.InputPasswordActivity;
import com.idhub.wallet.utils.ToastUtils;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_splash);
        checkReadPhonePremission();
    }

    private void init() {
        //init , scan wallet keystores
        WalletManager.scanWallets();
        int walletNum = WalletManager.getWalletNum();
        if (walletNum > 0) {
            MainActivity.startAction(SplashActivity.this);
        } else {
            InputPasswordActivity.startAction(SplashActivity.this);
        }
        finish();
    }

    private void checkReadPhonePremission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            } else {
                init();
            }
        } else {
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                init();
            } else {
                ToastUtils.showShortToast("在未授予权限的情况下，程序无法正常工作");
                finish();
//                checkReadPhonePremission();
            }
        }
    }
}
