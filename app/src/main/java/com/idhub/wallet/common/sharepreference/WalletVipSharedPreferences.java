package com.idhub.wallet.common.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.idhub.wallet.App;
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
        return sharedPreferences.getString(WALLET_IDHHUB_VIP_STATE, VipStateType.HAVE_APPLY_FOR);
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

}
