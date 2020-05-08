package com.idhub.wallet.assets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;

public class IdentityOrStIntroduceActivity extends BaseActivity {

    private ImageView view;

    /**
     * @param context
     * @param type    0 identity 1 st
     */
    public static void startAction(Context context, int type) {
        Intent intent = new Intent(context, IdentityOrStIntroduceActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_or_st_introduce);
        int type = getIntent().getIntExtra("type", 0);
        view = findViewById(R.id.content_iamge);
        if (type == 0) {
            view.setImageResource(R.mipmap.identity_process);
        } else {
            view.setImageResource(R.mipmap.st_process);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Bitmap bitmap = view.getDrawingCache();
        if (bitmap != null && !bitmap.isRecycled())
            bitmap.recycle();
    }
}
