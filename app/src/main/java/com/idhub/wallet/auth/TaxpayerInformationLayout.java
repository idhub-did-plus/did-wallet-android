package com.idhub.wallet.auth;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.idhub.base.greendao.entity.UploadFileEntity;
import com.idhub.base.greendao.entity.UploadIDHubInfoEntity;
import com.idhub.wallet.R;

import java.util.Map;

public class TaxpayerInformationLayout extends LinearLayout {

    private InformationItemLayout taxView;
    private InformationItemLayout ssnView;
    private InformationFileLayout taxFileLayout;

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
        taxView = findViewById(R.id.tax_id);
        taxView.setContent(context.getString(R.string.wallet_tax_number), context.getString(R.string.wallet_please_input) + " " + context.getString(R.string.wallet_tax_number));
        ssnView = findViewById(R.id.ssn);
        ssnView.setContent(context.getString(R.string.wallet_ssn_number), context.getString(R.string.wallet_please_input) + " " + context.getString(R.string.wallet_ssn_number));
        taxFileLayout = findViewById(R.id.tax_file);
        taxFileLayout.setNameValue(getContext().getString(R.string.wallet_tax_document));
        taxFileLayout.setThisOrientation(LinearLayout.HORIZONTAL);
    }

    public void setUploadInfo(UploadIDHubInfoEntity uploadInfo) {
        taxView.setValue(uploadInfo.getTaxNumber());
        ssnView.setValue(uploadInfo.getSsnNumber());

    }

    public void setFileInfo(Map<String, UploadFileEntity> map) {
        //税表文件
        UploadFileEntity taxEntity = map.get(getContext().getString(R.string.wallet_tax_document));
        if (taxEntity != null) {
            String filePath = taxEntity.getFilePath();
            taxFileLayout.setFile(filePath);
        }
    }
}
