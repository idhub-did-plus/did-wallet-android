package com.idhub.wallet.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.base.greendao.entity.TransactionRecordEntity;

import java.math.BigInteger;

public class TransactionDetailActivity extends BaseActivity implements View.OnClickListener {

    private TransactionRecordEntity mTransactionRecordEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Object o = getIntent().getParcelableExtra("data");
        if (o == null || !(o instanceof TransactionRecordEntity)) {
            finish();
            return;
        }
        mTransactionRecordEntity = (TransactionRecordEntity) o;
        setContentView(R.layout.wallet_activity_transaction_detail);
        init();
    }

    public static void startAction(Context context, TransactionRecordEntity recordEntity) {
        Intent intent = new Intent(context, TransactionDetailActivity.class);
        intent.putExtra("data", recordEntity);
        context.startActivity(intent);
    }

    private void init() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_transaction_detail_title));

        TextView nameView = findViewById(R.id.name);
        String tokenName = mTransactionRecordEntity.getTokenName();
        if (TextUtils.isEmpty(tokenName)) {
            nameView.setText(getString(R.string.wallet_ETH));
        } else {
            nameView.setText(tokenName);
        }

        TextView timeView = findViewById(R.id.time);
        timeView.setText(mTransactionRecordEntity.getTimeStamp());

        TextView fromAddressView = findViewById(R.id.tv_from_address);
        fromAddressView.setText(mTransactionRecordEntity.getFrom());

        TextView toAddressView = findViewById(R.id.tv_to_address);
        toAddressView.setText(mTransactionRecordEntity.getTo());


        TextView contractAddressView = findViewById(R.id.contract_address);
        TextView contractAddressNameView = findViewById(R.id.contract_address_name);
        TextView valueView = findViewById(R.id.value);
        String contractAddress = mTransactionRecordEntity.getContractAddress();
        if (TextUtils.isEmpty(contractAddress)) {
            //不是合约
            contractAddressNameView.setVisibility(View.GONE);
            contractAddressView.setVisibility(View.GONE);
            valueView.setText(NumericUtil.ethBigIntegerToNumberViewPointAfterEight(new BigInteger(mTransactionRecordEntity.getValue()),String.valueOf(Math.pow(10, 18))));
        } else {
            //合约
            valueView.setText(NumericUtil.tokenValueFormatViewPointAfterEight(new BigInteger(mTransactionRecordEntity.getValue()), String.valueOf(Math.pow(10, Double.valueOf(mTransactionRecordEntity.getTokenDecimal())))));
            contractAddressView.setText(contractAddress);
        }


        TextView gasView = findViewById(R.id.gas);
        TextView gasUsedView = findViewById(R.id.gas_used);
        TextView gasPriceView = findViewById(R.id.gas_price);
        gasView.setText(mTransactionRecordEntity.getGas());
        gasPriceView.setText(mTransactionRecordEntity.getGasPrice());
        gasUsedView.setText(mTransactionRecordEntity.getGasUsed());

        findViewById(R.id.tv_detail).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //查看更多详情
    }
}
