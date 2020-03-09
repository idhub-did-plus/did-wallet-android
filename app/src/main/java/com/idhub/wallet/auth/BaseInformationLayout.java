package com.idhub.wallet.auth;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.idhub.wallet.R;

public class BaseInformationLayout extends LinearLayout {
    public BaseInformationLayout(Context context) {
        super(context);
        init();
    }

    public BaseInformationLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseInformationLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        Context context = getContext();
        inflate(context, R.layout.wallet_base_information_layout, this);
        InformationItemLayout firstNameView = findViewById(R.id.first_name);
        firstNameView.setContent(context.getString(R.string.wallet_first_name), context.getString(R.string.wallet_please_input) + context.getString(R.string.wallet_first_name));
        InformationItemLayout middleNameView = findViewById(R.id.middle_name);
        middleNameView.setContent(context.getString(R.string.wallet_middle_name), context.getString(R.string.wallet_please_input) + context.getString(R.string.wallet_middle_name));
        InformationItemLayout lastNameView = findViewById(R.id.last_name);
        lastNameView.setContent(context.getString(R.string.wallet_last_name), context.getString(R.string.wallet_please_input) + context.getString(R.string.wallet_last_name));
        InformationItemLayout birthdayView = findViewById(R.id.birthday);
        birthdayView.setContent(context.getString(R.string.wallet_birthday), context.getString(R.string.wallet_please_select) + context.getString(R.string.wallet_birthday));
        InformationItemLayout emailView = findViewById(R.id.email);
        emailView.setContent(context.getString(R.string.wallet_email), context.getString(R.string.wallet_please_input) + " " + context.getString(R.string.wallet_email));
        InformationItemLayout nationalityView = findViewById(R.id.nationality);
        nationalityView.setContent(context.getString(R.string.wallet_country), context.getString(R.string.wallet_please_select)+" "+ context.getString(R.string.wallet_country));
        InformationItemLayout countryView = findViewById(R.id.country_of_residence);
        countryView.setContent(context.getString(R.string.wallet_residence_country), context.getString(R.string.wallet_please_select)+" "+context.getString(R.string.wallet_residence_country));

        EditText phoneView = findViewById(R.id.phone);
        TextView phoneCodeView = findViewById(R.id.tv_phone_dialing_code);

    }

}
