package com.idhub.wallet.auth;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.idhub.base.greendao.entity.UploadFileEntity;
import com.idhub.wallet.R;

import java.util.Map;

public class CreditInformationLayout extends LinearLayout {

    private InformationFileLayout creditFileLayout;

    public CreditInformationLayout(Context context) {
        super(context);
        init();
    }

    public CreditInformationLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CreditInformationLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.wallet_credit_information_layout, this);
        creditFileLayout = findViewById(R.id.credit_report_file);
        creditFileLayout.setNameValue(getContext().getString(R.string.wallet_credit_report));
        creditFileLayout.setThisOrientation(LinearLayout.HORIZONTAL);

    }

    public void setFileInfo(Map<String, UploadFileEntity> map) {

        //征信报告
        UploadFileEntity creditEntity = map.get(getContext().getString(R.string.wallet_credit_report));
        if (creditEntity != null) {
            String filePath = creditEntity.getFilePath();
            creditFileLayout.setFile(filePath);
        }
    }
}
