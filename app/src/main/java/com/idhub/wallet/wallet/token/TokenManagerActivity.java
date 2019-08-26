package com.idhub.wallet.wallet.token;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idhub.wallet.R;

public class TokenManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_token_manager);
        initView();
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, TokenManagerActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.token_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TokenManagerAdapter(this));
    }

}
