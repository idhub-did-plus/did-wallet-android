package com.idhub.wallet.me.information;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

import com.idhub.wallet.R;

public class UploadFileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_upload_file);
        initView();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.rv_upload_file);

    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, UploadFileActivity.class);
        context.startActivity(intent);
    }


}
