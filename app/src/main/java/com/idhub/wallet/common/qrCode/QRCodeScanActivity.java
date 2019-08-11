package com.idhub.wallet.common.qrCode;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.idhub.wallet.R;

public class QRCodeScanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_qrcode_scan);
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, QRCodeScanActivity.class);
        context.startActivity(intent);
    }
}