package com.idhub.wallet.auth;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.idhub.base.greendao.entity.UploadFileEntity;
import com.idhub.base.greendao.entity.UploadIDHubInfoEntity;
import com.idhub.wallet.R;
import com.idhub.wallet.common.country.Country;
import com.idhub.wallet.common.country.CountryPickerCallbacks;
import com.idhub.wallet.common.country.CountryPickerDialog;

import java.io.File;
import java.util.Locale;
import java.util.Map;

public class AddressInformationLayout extends InformationItemLayout {

    private InformationKeyValueLayout addressDetailView;
    private InformationKeyValueLayout countryView;
    private InformationKeyValueLayout postalCodeView;
    private InformationKeyValueLayout provinceView;
    private InformationKeyValueLayout cityView;
    private InformationKeyValueLayout neighborhoodView;
    private InformationFileLayout addressFileView;
    private UploadFileEntity addressEntity;
    private String countryCode;

    public AddressInformationLayout(Context context) {
        super(context);
    }

    public AddressInformationLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AddressInformationLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void init() {
        setOrientation(VERTICAL);
        Context context = getContext();
        inflate(context, R.layout.wallet_address_information_layout, this);
        countryView = findViewById(R.id.country);
        countryView.setContent(context.getString(R.string.wallet_address_country), context.getString(R.string.wallet_address_country_hint));
        countryView.setEditable(false);
        countryView.setClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCountry(countryView);
            }
        });
        postalCodeView = findViewById(R.id.postal_code);
        postalCodeView.setContent(context.getString(R.string.wallet_postal_code), context.getString(R.string.wallet_please_input) + " " + context.getString(R.string.wallet_postal_code));
        provinceView = findViewById(R.id.province);
        provinceView.setContent(context.getString(R.string.wallet_city), context.getString(R.string.wallet_please_input) + " " + context.getString(R.string.wallet_city));
        cityView = findViewById(R.id.city);
        cityView.setContent(context.getString(R.string.wallet_state), context.getString(R.string.wallet_please_input) + " " + context.getString(R.string.wallet_state));
        neighborhoodView = findViewById(R.id.neighborhood);
        neighborhoodView.setContent(context.getString(R.string.wallet_neighborhood), context.getString(R.string.wallet_please_input) + context.getString(R.string.wallet_neighborhood));
        addressDetailView = findViewById(R.id.address_detail);
        addressDetailView.setContent(context.getString(R.string.wallet_street), context.getString(R.string.wallet_street_hint));
        addressFileView = findViewById(R.id.address_file);
        addressFileView.setThisOrientation(LinearLayout.HORIZONTAL);

        findViewById(R.id.base_info_save).setOnClickListener(saveBtnClickListener);
    }


    public void setFileInfo(Map<String, UploadFileEntity> map) {
        //住址证明文件
        addressEntity = map.get(getContext().getString(R.string.wallet_address_proof_document));
        if (addressEntity != null) {
            String filePath = addressEntity.getFilePath();
            addressFileView.setFile(filePath);
        }
    }

    @Override
    public void setInformation() {
        countryView.setValue(uploadInfo.getAddressCountry());
        postalCodeView.setValue(uploadInfo.getPostalCode());
        provinceView.setValue(uploadInfo.getCity());
        cityView.setValue(uploadInfo.getState());
        neighborhoodView.setValue(uploadInfo.getNeighborhood());
        addressDetailView.setValue(uploadInfo.getStreet());
    }

    @Override
    public boolean saveData() {
        String country = countryView.getValue();
        String postalCode = postalCodeView.getValue();
        String province = provinceView.getValue();
        String city = cityView.getValue();
        String neighborhood = neighborhoodView.getValue();
        String addressDetail = addressDetailView.getValue();
        if (uploadInfo == null) {
            uploadInfo = new UploadIDHubInfoEntity();
        }
        uploadInfo.setAddressCountry(country);
        uploadInfo.setAddressCountryCode(countryCode);
        uploadInfo.setPostalCode(postalCode);
        uploadInfo.setCity(province);
        uploadInfo.setState(city);
        uploadInfo.setNeighborhood(neighborhood);
        uploadInfo.setStreet(addressDetail);

        String addressFilePath = addressFileView.getFilePath();
        if (!addFileEntity(addressEntity,getContext().getString(R.string.wallet_address_proof_document),addressFilePath)) {
            return false;
        }
        return true;
    }


    private void selectCountry(InformationKeyValueLayout informationKeyValueLayout) {
        CountryPickerDialog countryPicker =
                new CountryPickerDialog(getContext(), new CountryPickerCallbacks() {
                    @Override
                    public void onCountrySelected(Country country) {
                        if (informationKeyValueLayout == countryView) {
                            countryCode = country.getIsoCode();
                        }
                        informationKeyValueLayout.setValue(country.getCountryName(getContext()));
                    }
                }, false, 0);
        countryPicker.show();
    }
}
