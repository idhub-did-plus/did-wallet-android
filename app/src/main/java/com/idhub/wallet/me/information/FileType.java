package com.idhub.wallet.me.information;

import java.util.HashMap;
import java.util.Map;

public class FileType {
    public static Map<String, String> fileTypes ;
    static {
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
