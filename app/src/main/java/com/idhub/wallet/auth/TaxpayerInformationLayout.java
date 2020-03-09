package com.idhub.wallet.auth;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.idhub.wallet.R;

public class TaxpayerInformationLayout extends LinearLayout  {
    public TaxpayerInformationLayout(Context context) {
        super(context);
        init();
    }

    public TaxpayerInformationLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TaxpayerInformationLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        Context context = getContext();
        inflate(context, R.layout.wallet_taxpayer_information_layout, this);
        InformationItemLayout taxView = findViewById(R.id.tax_id);
        taxView.setContent(context.getString(R.string.wallet_tax_number), context.getString(R.string.wallet_please_input) + " " + context.getString(R.string.wallet_tax_number));
         InformationItemLayout ssnView = findViewById(R.id.ssn);
        ssnView.setContent(context.getString(R.string.wallet_ssn_number), context.getString(R.string.wallet_please_input) + " " + context.getString(R.string.wallet_ssn_number));
        InformationFileLayout informationFileLayout = findViewById(R.id.tax_file);

    }

}
