package com.idhub.wallet.assets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.idhub.wallet.R;
import com.idhub.wallet.home.fragment.ClaimFragment;

public class ClaimsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_claims);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.claims, new ClaimFragment())
                .commit();
    }
    public static void startAction(Context context) {
        Intent intent = new Intent(context, ClaimsActivity.class);
        context.startActivity(intent);
    }
}
