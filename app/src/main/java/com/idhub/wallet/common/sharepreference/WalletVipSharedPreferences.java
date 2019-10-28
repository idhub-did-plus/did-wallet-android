package com.idhub.wallet.common.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.idhub.base.App;
import com.idhub.wallet.me.VipStateType;

public class WalletVipSharedPreferences {
    private static WalletVipSharedPreferences mVipSharedPreferences;
    private static SharedPreferences sharedPreferences;
    private String FILE_NAME = "wallet_vip";
    private final String WALLET_IDHHUB_VIP_STATE = "wallet_idhub_vip_state";
    private final String WALLET_IDHHUB_SUPER_VIP_STATE = "wallet_idhub_super_vip_state";
    private final String WALLET_QUALIFIED_INVESTOR_VIP_STATE = "wallet_qualified_investor_vip_state";
    private final String WALLET_QUALIFIED_INVESTOR_VIP_CONTENT = "wallet_qualified_investor_vip_content";
    private final String WALLET_QUALIFIED_PURCHASER_VIP_STATE = "wallet_qualified_purchaser_vip_state";
    private final String WALLET_QUALIFIED_PURCHASER_VIP_CONTENT = "wallet_qualified_purchaser_vip_content";
    private final String WALLET_COMLIANCE_INVESTOR_VIP_STATE = "wallet_compliance_investor_vip_state";

    private WalletVipSharedPreferences() {
        sharedPreferences = App.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized WalletVipSharedPreferences getInstance() {
        if (mVipSharedPreferences == null) {
            mVipSharedPreferences = new WalletVipSharedPreferences();
        }
        return mVipSharedPreferences;
    }

    public String getIdhubVipState() {
        return sharedPreferences.getString(WALLET_IDHHUB_VIP_STATE, VipStateType.NO_APPLY_FOR);
    }

    public void setIdhubVipState(String state) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(WALLET_IDHHUB_VIP_STATE, state).apply();
    }

    public String getIdhubSuperVipState() {
        return sharedPreferences.getString(WALLET_IDHHUB_SUPER_VIP_STATE, VipStateType.NO_APPLY_FOR);
    }

    public void setIdhubSuperVipState(String state) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(WALLET_IDHHUB_SUPER_VIP_STATE, state).apply();
    }

    public String getQualifiedInvestorVipState() {
        return sharedPreferences.getString(WALLET_QUALIFIED_INVESTOR_VIP_STATE, VipStateType.NO_APPLY_FOR);
    }

    public void setQualifiedInvestorVipState(String state) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(WALLET_QUALIFIED_INVESTOR_VIP_STATE, state).apply();
    }

    public String getQualifiedInvestorVipContent() {
        return sharedPreferences.getString(WALLET_QUALIFIED_INVESTOR_VIP_CONTENT, "");
    }

    public void setQualifiedInvestorVipContent(String content) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(WALLET_QUALIFIED_INVESTOR_VIP_CONTENT, content).apply();
    }

    public String getQualifiedPurchaserVipState() {
        return sharedPreferences.getString(WALLET_QUALIFIED_PURCHASER_VIP_STATE, VipStateType.NO_APPLY_FOR);
    }

    public void setQualifiedPurchaserVipState(String state) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(WALLET_QUALIFIED_PURCHASER_VIP_STATE, state).apply();
    }

    public String getQualifiedPurchaserVipContent() {
        return sharedPreferences.getString(WALLET_QUALIFIED_PURCHASER_VIP_CONTENT, "");
    }

    public void setQualifiedPurchaserVipContent(String content) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(WALLET_QUALIFIED_PURCHASER_VIP_CONTENT, content).apply();
    }


    public String getComplianceInvestorVipState() {
        return sharedPreferences.getString(WALLET_COMLIANCE_INVESTOR_VIP_STATE, VipStateType.NO_APPLY_FOR);
    }

    public void setComplianceInvestorVipState(String state) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(WALLET_COMLIANCE_INVESTOR_VIP_STATE, state).apply();
    }

    private final String IDHUB_VIP_CLAIM = "wallet_idhub_vip_claim";

    public String getIdHubVipClaim() {
        return sharedPreferences.getString(IDHUB_VIP_CLAIM, "");
    }

    public void setIdHubVipClaim(String claim) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(IDHUB_VIP_CLAIM, claim).apply();
    }

    private final String IDHUB_SVIP_CLAIM = "wallet_idhub_svip_claim";

    public String getIdHubSVipClaim() {
        return sharedPreferences.getString(IDHUB_SVIP_CLAIM, "");
    }

    public void setIdHubSVipClaim(String claim) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(IDHUB_SVIP_CLAIM, claim).apply();
    }

    private final String QUALIFIED_INVESTOR_VIP_CLAIM = "wallet_qualified_investor_claim";

    public String getQualifiedInvestorVipClaim() {
        return sharedPreferences.getString(QUALIFIED_INVESTOR_VIP_CLAIM, "");
    }

    public void setQualifiedInvestorVipClaim(String claim) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(QUALIFIED_INVESTOR_VIP_CLAIM, claim).apply();
    }

    private final String QUALIFIED_PURCHASER_VIP_CLAIM = "wallet_qualified_purchaser_vip_claim";

    public String getQualifiedPurchaserVipClaim() {
        return sharedPreferences.getString(QUALIFIED_PURCHASER_VIP_CLAIM, "");
    }

    public void setQualifiedPurchaserVipClaim(String claim) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(QUALIFIED_PURCHASER_VIP_CLAIM, claim).apply();
    }

    private final String COMLIANCE_INVESTOR_VIP_CLAIM = "wallet_compliance_investor_vip_claim";

    public String getComplianceInvestorVipClaim() {
        return sharedPreferences.getString(COMLIANCE_INVESTOR_VIP_CLAIM, "");
    }

    public void setComplianceInvestorVipClaim(String claim) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(COMLIANCE_INVESTOR_VIP_CLAIM, claim).apply();
    }


}
