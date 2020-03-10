package com.idhub.wallet.auth;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.idhub.base.greendao.entity.UploadFileEntity;
import com.idhub.wallet.R;

import java.util.Map;

public class AssetsInformationLayout extends LinearLayout {

    private InformationFileLayout bankBalanceFileLayout;
    private InformationFileLayout bankFlowFileLayout;
    private InformationFileLayout agencyFileLayout;
    private InformationFileLayout cfaFileLayout;
    private InformationFileLayout housingFileLayout;

    public AssetsInformationLayout(Context context) {
        super(context);
        init();
    }

    public AssetsInformationLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AssetsInformationLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.wallet_assets_information_layout, this);
        bankBalanceFileLayout = findViewById(R.id.bank_balance_file);
        bankBalanceFileLayout.setThisOrientation(LinearLayout.HORIZONTAL);
        bankBalanceFileLayout.setNameValue(getContext().getString(R.string.wallet_bank_balance_document));

        bankFlowFileLayout = findViewById(R.id.bank_flow_file);
        bankFlowFileLayout.setThisOrientation(LinearLayout.HORIZONTAL);
        bankFlowFileLayout.setNameValue(getContext().getString(R.string.wallet_bank_flow_file));


        agencyFileLayout = findViewById(R.id.evaluation_agency_is_assessment_file);
        agencyFileLayout.setThisOrientation(LinearLayout.HORIZONTAL);
        agencyFileLayout.setNameValue(getContext().getString(R.string.wallet_evaluation_agency_is_assessment_document));

        cfaFileLayout = findViewById(R.id.cfa_net_assets_file);
        cfaFileLayout.setThisOrientation(LinearLayout.HORIZONTAL);
        cfaFileLayout.setNameValue(getContext().getString(R.string.wallet_cfa_net_assets_certification_document));

        housingFileLayout = findViewById(R.id.non_self_occupied_housing_file);
        housingFileLayout.setThisOrientation(LinearLayout.HORIZONTAL);
        housingFileLayout.setNameValue(getContext().getString(R.string.wallet_property_certificate_for_non_self_occupied_housing));

    }

    public void setFileInfo(Map<String, UploadFileEntity> map) {

        //银行余额证明文件
        UploadFileEntity bankBalanceEntity = map.get(getContext().getString(R.string.wallet_bank_balance_document));
        if (bankBalanceEntity != null) {
            String filePath = bankBalanceEntity.getFilePath();
            bankBalanceFileLayout.setFile(filePath);
        }
        //银行流水文件
        UploadFileEntity bankFlowEntity = map.get(getContext().getString(R.string.wallet_bank_flow_file));
        if (bankFlowEntity != null) {
            String filePath = bankFlowEntity.getFilePath();
            bankFlowFileLayout.setFile(filePath);
        }
        //评估机构的评估文件
        UploadFileEntity angencyEntity = map.get(getContext().getString(R.string.wallet_evaluation_agency_is_assessment_document));
        if (angencyEntity != null) {
            String filePath = angencyEntity.getFilePath();
            agencyFileLayout.setFile(filePath);
        }
        //CFA净资产证明文件
        UploadFileEntity cfaEntity = map.get(getContext().getString(R.string.wallet_cfa_net_assets_certification_document));
        if (cfaEntity != null) {
            String filePath = cfaEntity.getFilePath();
            cfaFileLayout.setFile(filePath);
        }
        //CFA净资产证明文件
        UploadFileEntity houseEntity = map.get(getContext().getString(R.string.wallet_property_certificate_for_non_self_occupied_housing));
        if (houseEntity != null) {
            String filePath = houseEntity.getFilePath();
            housingFileLayout.setFile(filePath);
        }


    }
}
