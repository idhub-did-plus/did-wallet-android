package com.idhub.wallet.auth;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.idhub.wallet.R;

public class AddressInformationLayout extends LinearLayout {
    public AddressInformationLayout(Context context) {
        super(context);
        init();
    }

    public AddressInformationLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AddressInformationLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        Context context = getContext();
        inflate(context, R.layout.wallet_address_information_layout, this);
        InformationItemLayout countryView = findViewById(R.id.country);
        countryView.setContent(context.getString(R.string.wallet_address_country), context.getString(R.string.wallet_address_country_hint));
        InformationItemLayout postalCodeView = findViewById(R.id.postal_code);
        postalCodeView.setContent(context.getString(R.string.wallet_postal_code), context.getString(R.string.wallet_please_input) + " " + context.getString(R.string.wallet_postal_code));
        InformationItemLayout provinceView = findViewById(R.id.province);
        provinceView.setContent(context.getString(R.string.wallet_city), context.getString(R.string.wallet_please_input) + " " + context.getString(R.string.wallet_city));
        InformationItemLayout cityView = findViewById(R.id.city);
        cityView.setContent(context.getString(R.string.wallet_state), context.getString(R.string.wallet_please_input) + " " + context.getString(R.string.wallet_state));
        InformationItemLayout neighborhoodView = findViewById(R.id.neighborhood);
        neighborhoodView.setContent(context.getString(R.string.wallet_neighborhood), context.getString(R.string.wallet_please_input) + context.getString(R.string.wallet_neighborhood));
        InformationItemLayout addressDetailView = findViewById(R.id.address_detail);
        addressDetailView.setContent(context.getString(R.string.wallet_address_detail), context.getString(R.string.wallet_address_detail_hint));
        InformationFileLayout addressFileView = findViewById(R.id.address_file);


    }

}
