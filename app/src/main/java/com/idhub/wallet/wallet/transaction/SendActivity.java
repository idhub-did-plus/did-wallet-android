package com.idhub.wallet.wallet.transaction;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.idhub.wallet.R;
import com.idhub.wallet.wallet.mainfragment.model.AssetsModel;

public class SendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_send);
    }

    public static void startAction(Context context, AssetsModel assetsModel) {
        Intent intent = new Intent(context, SendActivity.class);
        intent.putExtra("data", assetsModel);
        context.startActivity(intent);
    }
}
