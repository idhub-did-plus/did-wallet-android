package com.idhub.wallet.home.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.idhub.wallet.auth.AuthInfoActivity;
import com.idhub.wallet.common.sharepreference.WalletVipSharedPreferences;
import com.idhub.wallet.main.MainBaseFragment;
import com.idhub.wallet.R;
import com.idhub.wallet.me.VipStateType;
import com.idhub.wallet.me.information.AccreditedInvestorActivity;
import com.idhub.wallet.me.information.AccreditedPurchaserActivity;
import com.idhub.wallet.me.information.IDHubSVIPActivity;
import com.idhub.wallet.me.information.IDHubVIPActivity;
import com.idhub.wallet.me.information.STCompliantInvestorActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClaimFragment extends MainBaseFragment implements View.OnClickListener {


    private TextView idhubVipClaimStateView;
    private TextView idhubSvipClaimStateView;
    private TextView stAccreditedStateView;
    private TextView stAccreditedPurchaserStateView;
    private TextView stCompliantStateView;
    private View idhubVipBgContainer;
    private View idhubSVipBgContainer;
    private View stAccreditedContainer;
    private View stAccreditedPurchaserContainer;
    private View stCompliantContainer;
    private View infoBgContainer;

    public ClaimFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_claim, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        WalletVipSharedPreferences vipSharedPreferences = WalletVipSharedPreferences.getInstance();
        String idhubVipState = vipSharedPreferences.getIdhubVipState();
        idhubVipClaimStateView.setText(getState(idhubVipState));
        String idhubSuperVipState = vipSharedPreferences.getIdhubSuperVipState();
        idhubSvipClaimStateView.setText(getState(idhubSuperVipState));
        String investorVipState = vipSharedPreferences.getQualifiedInvestorVipState();
        stAccreditedStateView.setText(getState(investorVipState));
        String purchaserVipState = vipSharedPreferences.getQualifiedPurchaserVipState();
        stAccreditedPurchaserStateView.setText(getState(purchaserVipState));
        String complianceInvestorVipState = vipSharedPreferences.getComplianceInvestorVipState();
        stCompliantStateView.setText(getState(complianceInvestorVipState));
    }

    private void initView(View view) {
        infoBgContainer = view.findViewById(R.id.claim_upload_information_bg);
        infoBgContainer.setOnClickListener(this);
        TextView uploadTitleView = view.findViewById(R.id.claim_upload_info_title);
        ImageView arrowView = view.findViewById(R.id.claim_upload_info_arrow);
        TextView idhubClaimsView = view.findViewById(R.id.idhub_claims);
        TextView stClaimsView = view.findViewById(R.id.security_token_claims);
        //idhub vip
        idhubVipBgContainer = view.findViewById(R.id.idhub_claim_vip_bg);
        idhubVipBgContainer.setOnClickListener(this);
        TextView idhubClaimTitleView = view.findViewById(R.id.idhub_claim_vip_title);
        idhubVipClaimStateView = view.findViewById(R.id.idhub_claim_vip_state);
        //idhub svip
        idhubSVipBgContainer = view.findViewById(R.id.idhub_claim_svip_bg);
        idhubSVipBgContainer.setOnClickListener(this);
        TextView idhubSvipClaimTitleView = view.findViewById(R.id.idhub_claim_svip_title);
        idhubSvipClaimStateView = view.findViewById(R.id.idhub_claim_svip_state);
        //accredited investor
        stAccreditedContainer = view.findViewById(R.id.st_accredited_investor_bg);
        stAccreditedContainer.setOnClickListener(this);
        TextView stAccreditedTitleView = view.findViewById(R.id.st_accredited_investor_title);
        stAccreditedStateView = view.findViewById(R.id.st_accredited_investor_state);
        //accredited purchaser
        stAccreditedPurchaserContainer = view.findViewById(R.id.st_accredited_purchaser_bg);
        stAccreditedPurchaserContainer.setOnClickListener(this);
        TextView stAccreditedPurchaserTitleView = view.findViewById(R.id.st_accredited_purchaser_title);
        stAccreditedPurchaserStateView = view.findViewById(R.id.st_accredited_purchaser_state);
        //st compliant investor
        stCompliantContainer = view.findViewById(R.id.st_compliant_investor_bg);
        stCompliantContainer.setOnClickListener(this);
        TextView stCompliantTitleView = view.findViewById(R.id.st_compliant_investor_title);
        stCompliantStateView = view.findViewById(R.id.st_compliant_investor_state);
    }

    public String getState(String state) {
        String value = "";
        if (VipStateType.NO_APPLY_FOR.equals(state)) {
            value = getContext().getString(R.string.wallet_no_appply_for);
        } else if (VipStateType.APPLY_FOR_ING.equals(state)) {
            value = getContext().getString(R.string.wallet_apply_for_ing);
        } else if (VipStateType.HAVE_APPLY_FOR.equals(state)) {
            value = getContext().getString(R.string.wallet_have_apply_for);
        } else if (VipStateType.REFUSED_APPLY_FOR.equals(state)) {
            value = getContext().getString(R.string.wallet_refused_apply);
        }
        return value;
    }

    @Override
    public void onClick(View v) {
        if (v == idhubVipBgContainer) {
            IDHubVIPActivity.startAction(getContext());
        } else if (v == idhubSVipBgContainer) {
            IDHubSVIPActivity.startAction(getContext());
        } else if (v == stAccreditedContainer) {
            AccreditedInvestorActivity.startAction(getContext());
        } else if (v == stAccreditedPurchaserContainer) {
            AccreditedPurchaserActivity.startAction(getContext());
        } else if (v == stCompliantContainer) {
            STCompliantInvestorActivity.startAction(getContext());
        } else if (v == infoBgContainer) {
            //上传信息
            AuthInfoActivity.startAction(getContext());
//            UploadInformationTypeActivity.startAction(getContext());
        }
    }
    @Override
    protected void loadData() {

    }
}
