package com.idhub.wallet.me.information;

import com.idhub.base.App;
import com.idhub.wallet.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileType {
    public static Map<String, String> fileTypes ;
    public static List<String> types ;

    static {
        types = new ArrayList<>();
        types.add(App.getInstance().getString(R.string.wallet_id_photo_positive));
        types.add(App.getInstance().getString(R.string.wallet_id_photo_negative));
        types.add(App.getInstance().getString(R.string.wallet_passport_photo));
        types.add(App.getInstance().getString(R.string.wallet_bank_balance_document));
        types.add(App.getInstance().getString(R.string.wallet_bank_flow_file));
        types.add(App.getInstance().getString(R.string.wallet_evaluation_agency_is_assessment_document));
        types.add(App.getInstance().getString(R.string.wallet_cfa_net_assets_certification_document));
        types.add(App.getInstance().getString(R.string.wallet_tax_document));
        types.add(App.getInstance().getString(R.string.wallet_credit_report));
        types.add(App.getInstance().getString(R.string.wallet_address_proof_document));
        types.add(App.getInstance().getString(R.string.wallet_property_certificate_for_non_self_occupied_housing));

        fileTypes = new HashMap<>();
        fileTypes.put("身份证件正面照片", "IDFront");
        fileTypes.put("身份证件反面照片", "IDBack");
        fileTypes.put("护照照片", "Passport");
        fileTypes.put("银行余额证明文件", "BankBalanceStatement");
        fileTypes.put("银行流水文件", "BankStatement");
        fileTypes.put("评估机构的评估文件", "AssetsAssessmentDocumentsFromSpecialistAgencies");
        fileTypes.put("CFA净资产证明文件", "CFAStatementAssets-basedVerificationDocument");
        fileTypes.put("税表文件", "TaxReport");
        fileTypes.put("征信报告", "CreditReport");
        fileTypes.put("住址证明文件", "AddressProof");
        fileTypes.put("非自住房的房产证明", "RealEstateAssetsOtherThanPrimaryResidence");
        fileTypes.put("ID front", "IDFront");
        fileTypes.put("ID back", "IDBack");
        fileTypes.put("Passport", "Passport");
        fileTypes.put("Bank balance statement", "BankBalanceStatement");
        fileTypes.put("Bank statement", "BankStatement");
        fileTypes.put("Assets Assessment documents from specialist Agencies", "AssetsAssessmentDocumentsFromSpecialistAgencies");
        fileTypes.put("CFA statement Assets-based Verification document", "CFAStatementAssets-basedVerificationDocument");
        fileTypes.put("Tax report", "TaxReport");
        fileTypes.put("Credit report", "CreditReport");
        fileTypes.put("Address proof", "AddressProof");
        fileTypes.put("Real estate assets other than primary residence", "RealEstateAssetsOtherThanPrimaryResidence");
    }
}
