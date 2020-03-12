package com.idhub.wallet.main;


import android.os.Handler;
import android.os.Message;

import com.idhub.magic.common.event.MagicEvent;
import com.idhub.magic.common.kvc.entity.ClaimType;
import com.idhub.wallet.common.sharepreference.WalletVipSharedPreferences;
import com.idhub.wallet.common.walletobservable.WalletVipStateObservable;
import com.idhub.wallet.me.VipStateType;
import com.idhub.wallet.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class ClaimEventHandler extends Handler {
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg.what == 1) {
            MagicEvent magicEvent = (MagicEvent) msg.obj;
            String event = magicEvent.event;
            String eventClass = magicEvent.eventClass;
            String eventType = magicEvent.eventType;
            if (eventType.equals("claim_issued_event")) {
                if (event.startsWith("refused")) {
                    String[] events = event.split("@");
                    String claimType = events[1];
                    if (ClaimType.IDHub_VIP.name().equals(claimType)) {
                        WalletVipSharedPreferences.getInstance().setIdhubVipState(VipStateType.REFUSED_APPLY_FOR);
                    } else if (ClaimType.IDHub_SVIP.name().equals(claimType)) {
                        WalletVipSharedPreferences.getInstance().setIdhubSuperVipState(VipStateType.REFUSED_APPLY_FOR);
                    } else if (ClaimType.SEC_Accredited_Investor.name().equals(claimType)) {
                        WalletVipSharedPreferences.getInstance().setQualifiedInvestorVipState(VipStateType.REFUSED_APPLY_FOR);
                    } else if (ClaimType.SEC_Accredited_Purchaser.name().equals(claimType)) {
                        WalletVipSharedPreferences.getInstance().setQualifiedPurchaserVipState(VipStateType.REFUSED_APPLY_FOR);
                    } else if (ClaimType.ST_Compliant_Investor.name().equals(claimType)) {
                        WalletVipSharedPreferences.getInstance().setComplianceInvestorVipState(VipStateType.REFUSED_APPLY_FOR);
                    }
                } else {
                    JSONObject jsonObject = null;
                    try {
                        String replace = event.replace("\\", "");
                        String s = replace.substring(replace.indexOf("{"), replace.lastIndexOf("}") + 1);
                        jsonObject = new JSONObject(s);
                        JSONObject claim = jsonObject.getJSONObject("claim");
                        String claimType = claim.getString("claimType");
                        if (ClaimType.IDHub_VIP.name().equals(claimType)) {
                            WalletVipSharedPreferences.getInstance().setIdhubVipState(VipStateType.HAVE_APPLY_FOR);
                            WalletVipSharedPreferences.getInstance().setIdHubVipClaim(event);
                        } else if (ClaimType.IDHub_SVIP.name().equals(claimType)) {
                            WalletVipSharedPreferences.getInstance().setIdhubSuperVipState(VipStateType.HAVE_APPLY_FOR);
                            WalletVipSharedPreferences.getInstance().setIdHubSVipClaim(event);
                        } else if (ClaimType.SEC_Accredited_Investor.name().equals(claimType)) {
                            WalletVipSharedPreferences.getInstance().setQualifiedInvestorVipState(VipStateType.HAVE_APPLY_FOR);
                            WalletVipSharedPreferences.getInstance().setQualifiedInvestorVipClaim(event);
                        } else if (ClaimType.SEC_Accredited_Purchaser.name().equals(claimType)) {
                            WalletVipSharedPreferences.getInstance().setQualifiedPurchaserVipState(VipStateType.HAVE_APPLY_FOR);
                            WalletVipSharedPreferences.getInstance().setQualifiedPurchaserVipClaim(event);
                        } else if (ClaimType.ST_Compliant_Investor.name().equals(claimType)) {
                            WalletVipSharedPreferences.getInstance().setComplianceInvestorVipState(VipStateType.HAVE_APPLY_FOR);
                            WalletVipSharedPreferences.getInstance().setComplianceInvestorVipClaim(event);
                        }
                    } catch (JSONException ex) {
                        LogUtils.e("did", "requestNetData:message " + ex.getMessage());
                        ex.printStackTrace();
                    }
                }
                //更新展示信息
                WalletVipStateObservable.getInstance().update();
            }
        }
    }
}
