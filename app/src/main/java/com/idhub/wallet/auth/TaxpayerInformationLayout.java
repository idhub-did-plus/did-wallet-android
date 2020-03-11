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
import com.idhub.wallet.utils.FileUtils;
import com.idhub.wallet.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaxpayerInformationLayout extends InformationItemLayout {

    private InformationKeyValueLayout taxView;
    private InformationKeyValueLayout ssnView;
    private InformationFileLayout taxFileLayout;
    private UploadFileEntity taxEntity;

    public TaxpayerInformationLayout(Context context) {
        super(context);
    }

    public TaxpayerInformationLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TaxpayerInformationLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
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
        findViewById(R.id.info_save).setOnClickListener(saveBtnClickListener);
    }

    @Override
    public boolean saveData() {
        String taxValue = taxView.getValue();
        String ssnValue = ssnView.getValue();
        if (uploadInfo == null) {
            uploadInfo = new UploadIDHubInfoEntity();
        }
        uploadInfo.setTaxNumber(taxValue);
        uploadInfo.setSsnNumber(ssnValue);

        //判断数据库中已经有数据进行更改，没有数据进行添加
        //文件
        String taxFilePath = taxFileLayout.getFilePath();
        if (!addFileEntity(taxEntity,getContext().getString(R.string.wallet_tax_document),taxFilePath)) {
            return false;
        }
        return true;
    }


    public void setFileInfo(Map<String, UploadFileEntity> map) {
        //税表文件
        taxEntity = map.get(getContext().getString(R.string.wallet_tax_document));
        if (taxEntity != null) {
            String filePath = taxEntity.getFilePath();
            taxFileLayout.setFile(filePath);
        }
    }

    @Override
    public void setInformation() {
        taxView.setValue(uploadInfo.getTaxNumber());
        ssnView.setValue(uploadInfo.getSsnNumber());
    }

}
