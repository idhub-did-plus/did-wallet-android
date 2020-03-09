package com.idhub.wallet.auth;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.idhub.wallet.R;

public class IdentityInformationLayout extends LinearLayout {
    public IdentityInformationLayout(Context context) {
        super(context);
        init();
    }

    public IdentityInformationLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IdentityInformationLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        Context context = getContext();
        inflate(context, R.layout.wallet_identity_information_layout, this);
        InformationItemLayout idView = findViewById(R.id.identity_number);
        idView.setContent(context.getString(R.string.wallet_id_number), context.getString(R.string.wallet_please_input) + context.getString(R.string.wallet_id_number));
        InformationItemLayout passportView = findViewById(R.id.passport_number);
        passportView.setContent(context.getString(R.string.wallet_passport_number), context.getString(R.string.wallet_please_input) + context.getString(R.string.wallet_passport_number));

        InformationFileLayout idCardFontFileView = findViewById(R.id.id_card_font);
        InformationFileLayout idCardBackFileView = findViewById(R.id.id_card_back);
        InformationFileLayout passportFileView = findViewById(R.id.passport_file);

    }

}
