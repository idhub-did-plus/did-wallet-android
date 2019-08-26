package com.idhub.wallet.me.information;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.idhub.wallet.R;

public class Level5Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level5);
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, Level5Activity.class);
        context.startActivity(intent);
    }
}
