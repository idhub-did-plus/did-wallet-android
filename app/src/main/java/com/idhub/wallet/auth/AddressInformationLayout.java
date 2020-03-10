package com.idhub.wallet.auth;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.idhub.base.greendao.entity.UploadFileEntity;
import com.idhub.base.greendao.entity.UploadIDHubInfoEntity;
import com.idhub.wallet.R;

import java.util.Locale;
import java.util.Map;

public class AddressInformationLayout extends LinearLayout {

    private boolean mIsLocalzh;
    private InformationItemLayout addressDetailView;
    private InformationItemLayout countryView;
    private InformationItemLayout postalCodeView;
    private InformationItemLayout provinceView;
    private InformationItemLayout cityView;
    private InformationItemLayout neighborhoodView;
    private InformationFileLayout addressFileView;

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
        countryView = findViewById(R.id.country);
        countryView.setContent(context.getString(R.string.wallet_address_country), context.getString(R.string.wallet_address_country_hint));
        postalCodeView = findViewById(R.id.postal_code);
        postalCodeView.setContent(context.getString(R.string.wallet_postal_code), context.getString(R.string.wallet_please_input) + " " + context.getString(R.string.wallet_postal_code));
        provinceView = findViewById(R.id.province);
        provinceView.setContent(context.getString(R.string.wallet_city), context.getString(R.string.wallet_please_input) + " " + context.getString(R.string.wallet_city));
        cityView = findViewById(R.id.city);
        cityView.setContent(context.getString(R.string.wallet_state), context.getString(R.string.wallet_please_input) + " " + context.getString(R.string.wallet_state));
        neighborhoodView = findViewById(R.id.neighborhood);
        neighborhoodView.setContent(context.getString(R.string.wallet_neighborhood), context.getString(R.string.wallet_please_input) + context.getString(R.string.wallet_neighborhood));
        addressDetailView = findViewById(R.id.address_detail);
        addressFileView = findViewById(R.id.address_file);
        addressFileView.setNameValue(getContext().getString(R.string.wallet_address_proof_document));

        addressFileView.setThisOrientation(LinearLayout.HORIZONTAL);
        String locale = Locale.getDefault().toString();
        mIsLocalzh = locale.contains("zh");
        addressDetailView.setContent(context.getString(R.string.wallet_street), context.getString(R.string.wallet_street_hint));
    }

    public void setUploadInfo(UploadIDHubInfoEntity uploadInfo) {
        countryView.setValue(uploadInfo.getAddressCountry());
        postalCodeView.setValue(uploadInfo.getPostalCode());
        provinceView.setValue(uploadInfo.getCity());
        cityView.setValue(uploadInfo.getState());
        neighborhoodView.setValue(uploadInfo.getNeighborhood());
        addressDetailView.setValue(uploadInfo.getStreet());
    }

    public void setFileInfo(Map<String, UploadFileEntity> map) {
        //住址证明文件
        UploadFileEntity addressEntity = map.get(getContext().getString(R.string.wallet_address_proof_document));
        if (addressEntity != null) {
            String filePath = addressEntity.getFilePath();
            addressFileView.setFile(filePath);
        }
    }
}
