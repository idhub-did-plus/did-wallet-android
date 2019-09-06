package com.idhub.wallet.hository.message.transaction;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.idhub.wallet.R;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.greendao.entity.TransactionRecordEntity;

import java.math.BigInteger;

public class TransactionMessageItemView extends ConstraintLayout {


    private TextView mTimeView;
    private TextView mValueView;
    private TextView mNameView;
    private TextView mToAddressView;
    private TextView mFromAddressView;

    public TransactionMessageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mFromAddressView = findViewById(R.id.tv_from_address);
        mToAddressView = findViewById(R.id.tv_to_address);
        mTimeView = findViewById(R.id.time);
        mValueView = findViewById(R.id.value);
        mNameView = findViewById(R.id.name);
    }

    public void setData(TransactionRecordEntity transactionRecordEntity){
        String tokenName = transactionRecordEntity.getTokenName();
        String value = transactionRecordEntity.getValue();
        if (TextUtils.isEmpty(tokenName)) {
            mNameView.setText(getContext().getString(R.string.wallet_ETH));
            mValueView.setText(NumericUtil.ethBigIntegerToNumberViewPointAfterEight(new BigInteger(value)));
        } else {
            mNameView.setText(tokenName);
            String tokenDecimal = transactionRecordEntity.getTokenDecimal();
            mValueView.setText(NumericUtil.tokenValueFormatViewPointAfterEight(new BigInteger(value), String.valueOf(Math.pow(10, Double.valueOf(tokenDecimal)))));
        }
        String timeStamp = transactionRecordEntity.getTimeStamp();
        mTimeView.setText(timeStamp);
        mFromAddressView.setText(transactionRecordEntity.getFrom());
        mToAddressView.setText(transactionRecordEntity.getTo());
    }
}
