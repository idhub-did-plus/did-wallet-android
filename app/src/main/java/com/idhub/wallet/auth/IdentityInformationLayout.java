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

import java.util.List;
import java.util.Map;

public class IdentityInformationLayout extends LinearLayout {

    private InformationItemLayout idView;
    private InformationItemLayout passportView;
    private SaveClickListener saveClickListener;
    private UploadIDHubInfoEntity uploadInfo;
    private InformationFileLayout idCardFontFileView;
    private InformationFileLayout idCardBackFileView;
    private InformationFileLayout passportFileView;

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

        idView = findViewById(R.id.identity_number);
        idView.setContent(context.getString(R.string.wallet_id_number), context.getString(R.string.wallet_please_input) + context.getString(R.string.wallet_id_number));
        passportView = findViewById(R.id.passport_number);
        passportView.setContent(context.getString(R.string.wallet_passport_number), context.getString(R.string.wallet_please_input) + context.getString(R.string.wallet_passport_number));

        idCardFontFileView = findViewById(R.id.id_card_font);
        idCardFontFileView.setNameValue(getContext().getString(R.string.wallet_id_photo_positive));
        idCardBackFileView = findViewById(R.id.id_card_back);
        idCardBackFileView.setNameValue(getContext().getString(R.string.wallet_id_photo_negative));
        passportFileView = findViewById(R.id.passport_file);
        passportFileView.setNameValue(getContext().getString(R.string.wallet_passport_photo));

        findViewById(R.id.info_save).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
    }

    private void save() {
        String idNumber = idView.getValue();
        String passport = passportView.getValue();
        if (uploadInfo == null) {
            uploadInfo = new UploadIDHubInfoEntity();
        }
        uploadInfo.setIdcardNumber(idNumber);
        uploadInfo.setPassportNumber(passport);

        //文件
        String filePath = idCardFontFileView.getFilePath();
        if (!TextUtils.isEmpty(filePath)) {

        }

        if (saveClickListener != null) {
            saveClickListener.click(uploadInfo);
        }
    }

    public void setFileInfo(Map<String, UploadFileEntity> map) {
        //身份证正面
        UploadFileEntity idFontEntity = map.get(getContext().getString(R.string.wallet_id_photo_positive));
        if (idFontEntity != null) {
            String filePath = idFontEntity.getFilePath();
            idCardFontFileView.setFile(filePath);
        }
        //身份证反面
        UploadFileEntity idBackEntity = map.get(getContext().getString(R.string.wallet_id_photo_negative));
        if (idBackEntity != null) {
            String filePath = idBackEntity.getFilePath();
            idCardBackFileView.setFile(filePath);
        }
        //护照
        UploadFileEntity passportEntity = map.get(getContext().getString(R.string.wallet_passport_photo));
        if (passportEntity != null) {
            String filePath = passportEntity.getFilePath();
            passportFileView.setFile(filePath);
        }
    }


    public void setUploadInfo(UploadIDHubInfoEntity uploadInfo) {
        this.uploadInfo = uploadInfo;
        idView.setValue(uploadInfo.getIdcardNumber());
        passportView.setValue(uploadInfo.getPassportNumber());
    }

    public void setSaveClickListener(SaveClickListener saveClickListener) {
        this.saveClickListener = saveClickListener;
    }
}
