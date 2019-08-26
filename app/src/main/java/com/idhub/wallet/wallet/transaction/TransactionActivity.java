package com.idhub.wallet.wallet.transaction;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.greendao.entity.AssetsModel;

import java.math.BigInteger;

public class TransactionActivity extends AppCompatActivity implements View.OnClickListener {

    private AssetsModel mAssetsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_transaction);
        Intent intent = getIntent();
        Object data = intent.getParcelableExtra("data");
        if (data == null || !(data instanceof AssetsModel)) {
            finish();
            return;
        }
        mAssetsModel = (AssetsModel) data;
        init();
    }

    private void init() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_transaction));
        findViewById(R.id.receive).setOnClickListener(this);
        findViewById(R.id.send).setOnClickListener(this);
        TextView balanceAndName = findViewById(R.id.balance_and_name);
        String balance = mAssetsModel.getBalance();
        balanceAndName.setText(String.valueOf(NumericUtil.ethBigIntegerToNumberViewPointAfterFour(new BigInteger(balance)) + " " + mAssetsModel.getName()));
    }

    public static void srartAction(Context context, AssetsModel assetsModel) {
        Intent intent = new Intent(context, TransactionActivity.class);
        intent.putExtra("data", assetsModel);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.receive:
                ReceiveActivity.startAction(this,mAssetsModel);
                break;
            case R.id.send:
                SendActivity.startAction(this, mAssetsModel);
                break;
        }
    }
}
