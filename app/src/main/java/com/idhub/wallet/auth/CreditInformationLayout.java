package com.idhub.wallet.auth;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.idhub.base.greendao.entity.UploadFileEntity;
import com.idhub.wallet.R;

import java.io.File;
import java.util.Map;

public class CreditInformationLayout extends InformationItemLayout {

    private InformationFileLayout creditFileLayout;
    private UploadFileEntity creditEntity;

    public CreditInformationLayout(Context context) {
        super(context);
    }

    public CreditInformationLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CreditInformationLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void init() {
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.wallet_credit_information_layout, this);
        creditFileLayout = findViewById(R.id.credit_report_file);
//        creditFileLayout.setNameValue(getContext().getString(R.string.wallet_credit_report));
        creditFileLayout.setThisOrientation(LinearLayout.HORIZONTAL);
        findViewById(R.id.base_info_save).setOnClickListener(saveBtnClickListener);
    }

    public void setFileInfo(Map<String, UploadFileEntity> map) {
        //征信报告
        creditEntity = map.get(getContext().getString(R.string.wallet_credit_report));
        if (creditEntity != null) {
            String filePath = creditEntity.getFilePath();
            creditFileLayout.setFile(filePath);
        }
    }

    @Override
    public void setInformation() {

    }

    @Override
    public boolean saveData() {
        //文件
        String creditFilePath = creditFileLayout.getFilePath();
        if (!addFileEntity(creditEntity,getContext().getString(R.string.wallet_credit_report),creditFilePath)) {
            return false;
        }
        return true;
    }
}
