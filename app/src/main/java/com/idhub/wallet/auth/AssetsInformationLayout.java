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

public class AssetsInformationLayout extends InformationItemLayout {

    private InformationFileLayout bankBalanceFileLayout;
    private InformationFileLayout bankFlowFileLayout;
    private InformationFileLayout agencyFileLayout;
    private InformationFileLayout cfaFileLayout;
    private InformationFileLayout housingFileLayout;
    private UploadFileEntity houseEntity;
    private UploadFileEntity cfaEntity;
    private UploadFileEntity angencyEntity;
    private UploadFileEntity bankFlowEntity;
    private UploadFileEntity bankBalanceEntity;

    public AssetsInformationLayout(Context context) {
        super(context);
    }

    public AssetsInformationLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AssetsInformationLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void init() {
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.wallet_assets_information_layout, this);
        bankBalanceFileLayout = findViewById(R.id.bank_balance_file);
        bankBalanceFileLayout.setThisOrientation(LinearLayout.HORIZONTAL);
//        bankBalanceFileLayout.setNameValue(getContext().getString(R.string.wallet_bank_balance_document));

        bankFlowFileLayout = findViewById(R.id.bank_flow_file);
        bankFlowFileLayout.setThisOrientation(LinearLayout.HORIZONTAL);
//        bankFlowFileLayout.setNameValue(getContext().getString(R.string.wallet_bank_flow_file));


        agencyFileLayout = findViewById(R.id.evaluation_agency_is_assessment_file);
        agencyFileLayout.setThisOrientation(LinearLayout.HORIZONTAL);
//        agencyFileLayout.setNameValue(getContext().getString(R.string.wallet_evaluation_agency_is_assessment_document));

        cfaFileLayout = findViewById(R.id.cfa_net_assets_file);
        cfaFileLayout.setThisOrientation(LinearLayout.HORIZONTAL);
//        cfaFileLayout.setNameValue(getContext().getString(R.string.wallet_cfa_net_assets_certification_document));

        housingFileLayout = findViewById(R.id.non_self_occupied_housing_file);
        housingFileLayout.setThisOrientation(LinearLayout.HORIZONTAL);
//        housingFileLayout.setNameValue(getContext().getString(R.string.wallet_property_certificate_for_non_self_occupied_housing));

        findViewById(R.id.info_save).setOnClickListener(saveBtnClickListener);
    }

    public void setFileInfo(Map<String, UploadFileEntity> map) {

        //银行余额证明文件
        bankBalanceEntity = map.get(getContext().getString(R.string.wallet_bank_balance_document));
        if (bankBalanceEntity != null) {
            String filePath = bankBalanceEntity.getFilePath();
            bankBalanceFileLayout.setFile(filePath);
        }
        //银行流水文件
        bankFlowEntity = map.get(getContext().getString(R.string.wallet_bank_flow_file));
        if (bankFlowEntity != null) {
            String filePath = bankFlowEntity.getFilePath();
            bankFlowFileLayout.setFile(filePath);
        }
        //评估机构的评估文件
        angencyEntity = map.get(getContext().getString(R.string.wallet_evaluation_agency_is_assessment_document));
        if (angencyEntity != null) {
            String filePath = angencyEntity.getFilePath();
            agencyFileLayout.setFile(filePath);
        }
        //CFA净资产证明文件
        cfaEntity = map.get(getContext().getString(R.string.wallet_cfa_net_assets_certification_document));
        if (cfaEntity != null) {
            String filePath = cfaEntity.getFilePath();
            cfaFileLayout.setFile(filePath);
        }
        //CFA净资产证明文件
        houseEntity = map.get(getContext().getString(R.string.wallet_property_certificate_for_non_self_occupied_housing));
        if (houseEntity != null) {
            String filePath = houseEntity.getFilePath();
            housingFileLayout.setFile(filePath);
        }


    }

    @Override
    public void setInformation() {

    }

    @Override
    public boolean saveData() {

        String bankBalancePath = bankBalanceFileLayout.getFilePath();
        if (!addFileEntity(bankBalanceEntity, getContext().getString(R.string.wallet_bank_balance_document), bankBalancePath)) {
            return false;
        }

        String bankFlowFileLayoutFilePath = bankFlowFileLayout.getFilePath();
        if (!addFileEntity(bankFlowEntity, getContext().getString(R.string.wallet_bank_flow_file), bankFlowFileLayoutFilePath)) {
            return false;
        }

        String agencyFilePath = agencyFileLayout.getFilePath();
        if (!addFileEntity(angencyEntity, getContext().getString(R.string.wallet_evaluation_agency_is_assessment_document), agencyFilePath)) {
            return false;
        }

        String cfaFilePath = cfaFileLayout.getFilePath();
        if (!addFileEntity(cfaEntity, getContext().getString(R.string.wallet_cfa_net_assets_certification_document), cfaFilePath)) {
            return false;
        }
        String housingFilePath = housingFileLayout.getFilePath();
        if (!addFileEntity(houseEntity, getContext().getString(R.string.wallet_property_certificate_for_non_self_occupied_housing), housingFilePath)) {
            return false;
        }


        return true;
    }
}
