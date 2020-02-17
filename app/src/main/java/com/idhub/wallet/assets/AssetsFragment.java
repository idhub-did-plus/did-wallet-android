package com.idhub.wallet.assets;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.idhub.base.ui.ViewCalculateUtil;
import com.idhub.wallet.R;
import com.idhub.wallet.assets.fragment.CollectiblesFragment;
import com.idhub.wallet.assets.fragment.STAssetsFragment;
import com.idhub.wallet.assets.fragment.TokenFragment;
import com.idhub.wallet.common.tablayout.TabLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssetsFragment extends Fragment {


    public AssetsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assets, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        View topBg = view.findViewById(R.id.assets_top_bg);
        ImageView headView = view.findViewById(R.id.head);
        View upgradeBtn = view.findViewById(R.id.upgrade_btn_layout);
        View upgradeIcon = view.findViewById(R.id.upgrade_icon);
        TextView upgradeValue = view.findViewById(R.id.upgrade_value);
        View claimsLayout = view.findViewById(R.id.claims_layout);
        View walletInfoLayout = view.findViewById(R.id.wallet_info);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        tabLayout.setupWithViewPager(viewPager);
        String[] strings = {getString(R.string.wallet_securities), getString(R.string.wallet_token), getString(R.string.wallet_collectibles)};
        AssetsFragmentPagerAdapter adapter = new AssetsFragmentPagerAdapter(getChildFragmentManager(), getContext(),strings );
        viewPager.setAdapter(adapter);
        TextView assetsName = view.findViewById(R.id.assets_name);

        ViewCalculateUtil.setViewConstraintLayoutParam(viewPager, ViewGroup.LayoutParams.MATCH_PARENT, 470);
        ViewCalculateUtil.setViewConstraintLayoutParam(assetsName, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 16, 0, 16, 0);
        ViewCalculateUtil.setTextSize(assetsName, 20);
        ViewCalculateUtil.setViewConstraintLayoutParam(tabLayout, ViewGroup.LayoutParams.MATCH_PARENT, 30, 16, 0, 16, 16);
        ViewCalculateUtil.setViewPadding(tabLayout, 5, 5, 0, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(walletInfoLayout, ViewGroup.LayoutParams.MATCH_PARENT, 61, 16, 0, 16, 16);
        ViewCalculateUtil.setViewConstraintLayoutParam(claimsLayout, ViewGroup.LayoutParams.MATCH_PARENT, 23, 16, 0, 0, 0);
        ViewCalculateUtil.setTextSize(upgradeValue, 12);
        ViewCalculateUtil.setViewLinearLayoutParam(upgradeValue, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0, 8, 10);
        ViewCalculateUtil.setViewLinearLayoutParam(upgradeIcon, 17, 16, 0, 0, 12, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(upgradeBtn, 30, ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0, 0, 16);
        ViewCalculateUtil.setViewConstraintLayoutParam(headView, 72, 72, 23, 0, 0, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(topBg, ViewGroup.LayoutParams.MATCH_PARENT, 177);

    }

}
