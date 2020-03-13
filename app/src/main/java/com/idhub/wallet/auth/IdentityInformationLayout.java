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

public class IdentityInformationLayout extends InformationItemLayout {

    private InformationKeyValueLayout idView;
    private InformationKeyValueLayout passportView;
    private InformationFileLayout idCardFontFileView;
    private InformationFileLayout idCardBackFileView;
    private InformationFileLayout passportFileView;
    private UploadFileEntity idFontEntity;
    private UploadFileEntity idBackEntity;
    private UploadFileEntity passportEntity;

    public IdentityInformationLayout(Context context) {
        super(context);
    }

    public IdentityInformationLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IdentityInformationLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void init() {
        setOrientation(VERTICAL);
        Context context = getContext();
        inflate(context, R.layout.wallet_identity_information_layout, this);

        idView = findViewById(R.id.identity_number);
        idView.setContent(context.getString(R.string.wallet_id_number), context.getString(R.string.wallet_please_input) + context.getString(R.string.wallet_id_number));
        passportView = findViewById(R.id.passport_number);
        passportView.setContent(context.getString(R.string.wallet_passport_number), context.getString(R.string.wallet_please_input) + context.getString(R.string.wallet_passport_number));

        idCardFontFileView = findViewById(R.id.id_card_font);
//        idCardFontFileView.setNameValue(getContext().getString(R.string.wallet_id_photo_positive));
        idCardBackFileView = findViewById(R.id.id_card_back);
//        idCardBackFileView.setNameValue(getContext().getString(R.string.wallet_id_photo_negative));
        passportFileView = findViewById(R.id.passport_file);
//        passportFileView.setNameValue(getContext().getString(R.string.wallet_passport_photo));

        findViewById(R.id.info_save).setOnClickListener(saveBtnClickListener);
    }


    @Override
    public boolean saveData() {
        String idNumber = idView.getValue();
        String passport = passportView.getValue();
        if (uploadInfo == null) {
            uploadInfo = new UploadIDHubInfoEntity();
        }
        uploadInfo.setIdcardNumber(idNumber);
        uploadInfo.setPassportNumber(passport);

        //判断数据库中已经有数据进行更改，没有数据进行添加
        //文件
        String idFontFilePath = idCardFontFileView.getFilePath();
        if (!addFileEntity(idFontEntity, getContext().getString(R.string.wallet_id_photo_positive), idFontFilePath)) {
            return false;
        }


        String idBackFilePath = idCardBackFileView.getFilePath();
        if (!addFileEntity(idBackEntity,getContext().getString(R.string.wallet_id_photo_negative),idBackFilePath)) {
            return false;
        }

        String passportFilePath = passportFileView.getFilePath();
        if (!addFileEntity(passportEntity,getContext().getString(R.string.wallet_passport_photo),passportFilePath)) {
            return false;
        }

        return true;
    }


    public void setFileInfo(Map<String, UploadFileEntity> map) {
        //保留数据库查询的实例
        //身份证正面
        idFontEntity = map.get(getContext().getString(R.string.wallet_id_photo_positive));
        if (idFontEntity != null) {
            String filePath = idFontEntity.getFilePath();
            idCardFontFileView.setFile(filePath);
        }
        //身份证反面
        idBackEntity = map.get(getContext().getString(R.string.wallet_id_photo_negative));
        if (idBackEntity != null) {
            String filePath = idBackEntity.getFilePath();
            idCardBackFileView.setFile(filePath);
        }
        //护照
        passportEntity = map.get(getContext().getString(R.string.wallet_passport_photo));
        if (passportEntity != null) {
            String filePath = passportEntity.getFilePath();
            passportFileView.setFile(filePath);
        }
    }

    @Override
    public void setInformation() {
        idView.setValue(uploadInfo.getIdcardNumber());
        passportView.setValue(uploadInfo.getPassportNumber());
    }



}
