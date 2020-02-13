package com.idhub.wallet.home.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.idhub.base.ui.ViewCalculateUtil;
import com.idhub.wallet.main.MainBaseFragment;
import com.idhub.wallet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClaimFragment extends MainBaseFragment {


    public ClaimFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_claim, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        View infoBgContainer = view.findViewById(R.id.claim_upload_information_bg);
        TextView uploadTitleView = view.findViewById(R.id.claim_upload_info_title);
        ImageView arrowView = view.findViewById(R.id.claim_upload_info_arrow);
        TextView idhubClaimsView = view.findViewById(R.id.idhub_claims);
        TextView stClaimsView = view.findViewById(R.id.security_token_claims);
        ViewCalculateUtil.setViewConstraintLayoutParam(infoBgContainer, 355, 132, 21, 0, 10, 10);
        ViewCalculateUtil.setViewLinearLayoutParam(uploadTitleView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 43, 0, 22, 0);
        ViewCalculateUtil.setViewLinearLayoutParam(arrowView, 40, 20, 8, 0, 22, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(idhubClaimsView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 14, 0, 16, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(stClaimsView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 24, 0, 16, 0);
        //idhub vip
        View idhubVipBgContainer = view.findViewById(R.id.idhub_claim_vip_bg);
        TextView idhubClaimTitleView = view.findViewById(R.id.idhub_claim_vip_title);
        TextView idhubClaimStateView = view.findViewById(R.id.idhub_claim_vip_state);
        ViewCalculateUtil.setViewConstraintLayoutParam(idhubVipBgContainer, 343, 100, 16, 0, 14, 14);
        ViewCalculateUtil.setViewLinearLayoutParam(idhubClaimTitleView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 26, 0, 22, 0);
        ViewCalculateUtil.setTextSize(idhubClaimTitleView, 16);
        ViewCalculateUtil.setViewLinearLayoutParam(idhubClaimStateView, 64, 20, 8, 0, 23, 0);
        ViewCalculateUtil.setTextSize(idhubClaimStateView, 10);
        //idhub svip
        View idhubSVipBgContainer = view.findViewById(R.id.idhub_claim_svip_bg);
        TextView idhubSvipClaimTitleView = view.findViewById(R.id.idhub_claim_svip_title);
        TextView idhubSvipClaimStateView = view.findViewById(R.id.idhub_claim_svip_state);
        ViewCalculateUtil.setViewConstraintLayoutParam(idhubSVipBgContainer, 343, 100, 16, 0, 14, 14);
        ViewCalculateUtil.setViewLinearLayoutParam(idhubSvipClaimTitleView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 26, 0, 22, 0);
        ViewCalculateUtil.setTextSize(idhubSvipClaimTitleView, 16);
        ViewCalculateUtil.setViewLinearLayoutParam(idhubSvipClaimStateView, 64, 20, 8, 0, 23, 0);
        ViewCalculateUtil.setTextSize(idhubSvipClaimStateView, 10);
        //accredited investor
        View stAccreditedContainer = view.findViewById(R.id.st_accredited_investor_bg);
        TextView stAccreditedTitleView = view.findViewById(R.id.st_accredited_investor_title);
        TextView stAccreditedStateView = view.findViewById(R.id.st_accredited_investor_state);
        ViewCalculateUtil.setViewConstraintLayoutParam(stAccreditedContainer, 343, 100, 16, 0, 14, 14);
        ViewCalculateUtil.setViewLinearLayoutParam(stAccreditedTitleView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 26, 0, 22, 0);
        ViewCalculateUtil.setTextSize(stAccreditedTitleView, 16);
        ViewCalculateUtil.setViewLinearLayoutParam(stAccreditedStateView, 64, 20, 8, 0, 23, 0);
        ViewCalculateUtil.setTextSize(stAccreditedStateView, 10);
        //accredited purchaser
        View stAccreditedPurchaserContainer = view.findViewById(R.id.st_accredited_purchaser_bg);
        TextView stAccreditedPurchaserTitleView = view.findViewById(R.id.st_accredited_purchaser_title);
        TextView stAccreditedPurchaserStateView = view.findViewById(R.id.st_accredited_purchaser_state);
        ViewCalculateUtil.setViewConstraintLayoutParam(stAccreditedPurchaserContainer, 343, 100, 16, 0, 14, 14);
        ViewCalculateUtil.setViewLinearLayoutParam(stAccreditedPurchaserTitleView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 26, 0, 22, 0);
        ViewCalculateUtil.setTextSize(stAccreditedPurchaserTitleView, 16);
        ViewCalculateUtil.setViewLinearLayoutParam(stAccreditedPurchaserStateView, 64, 20, 8, 0, 23, 0);
        ViewCalculateUtil.setTextSize(stAccreditedPurchaserStateView, 10);
        //st compliant investor
        View stCompliantContainer = view.findViewById(R.id.st_compliant_investor_bg);
        TextView stCompliantTitleView = view.findViewById(R.id.st_compliant_investor_title);
        TextView stCompliantStateView = view.findViewById(R.id.st_compliant_investor_state);
        ViewCalculateUtil.setViewConstraintLayoutParam(stCompliantContainer, 343, 100, 16, 20, 14, 14);
        ViewCalculateUtil.setViewLinearLayoutParam(stCompliantTitleView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 26, 0, 22, 0);
        ViewCalculateUtil.setTextSize(stCompliantTitleView, 16);
        ViewCalculateUtil.setViewLinearLayoutParam(stCompliantStateView, 64, 20, 8, 0, 23, 0);
        ViewCalculateUtil.setTextSize(stCompliantStateView, 10);

    }

    @Override
    protected void loadData() {

    }
}
