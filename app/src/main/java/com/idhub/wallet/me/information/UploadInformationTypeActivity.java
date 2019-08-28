package com.idhub.wallet.me.information;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.idhub.wallet.R;

public class UploadInformationTypeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_upload_information_type);
        initView();
    }

    private void initView() {
         findViewById(R.id.tv_upload_idhub_information).setOnClickListener(this);
         findViewById(R.id.tv_upload_file).setOnClickListener(this);
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, UploadInformationTypeActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_upload_idhub_information:
                UploadIDHubInformationActivity.startAction(this);
                break;
            case R.id.tv_upload_file:
                UploadFileActivity.startAction(this);
                break;
        }
    }
}
