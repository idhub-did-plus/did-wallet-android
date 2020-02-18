package com.idhub.wallet.assets;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.idhub.base.ui.UIUtils;
import com.idhub.base.ui.ViewCalculateUtil;
import com.idhub.wallet.R;
import com.idhub.wallet.assets.fragment.CollectiblesFragment;
import com.idhub.wallet.assets.fragment.STAssetsFragment;
import com.idhub.wallet.assets.fragment.TokenFragment;
import com.idhub.wallet.common.sharepreference.UserBasicInfoSharpreference;
import com.idhub.wallet.common.tablayout.TabLayout;
import com.idhub.wallet.createmanager.UserBasicInfoEntity;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.main.MainActivity;

import org.web3j.crypto.Keys;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssetsFragment extends Fragment {


    private MainActivity mainActivity;
    private WalletKeystore mDidHubMnemonicKeyStore;
    private TextView walletName;
    private TextView walletAddress;

    public AssetsFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        if (activity instanceof MainActivity) {
            mainActivity = (MainActivity) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assets, container, false);
        initView(view);
        mDidHubMnemonicKeyStore = WalletManager.getCurrentKeyStore();
        initData();
        return view;
    }

    private void initData() {
        if (mDidHubMnemonicKeyStore == null) {
            return;
        }
        walletName.setText(mDidHubMnemonicKeyStore.getWallet().getName());
        walletAddress.setText(Keys.toChecksumAddress(NumericUtil.prependHexPrefix(mDidHubMnemonicKeyStore.getAddress())));
    }

    private void initView(View view) {
        View topBg = view.findViewById(R.id.assets_top_bg);
        ImageView headView = view.findViewById(R.id.head);
        //设置头像
        UserBasicInfoEntity userBasicInfo = UserBasicInfoSharpreference.getInstance().getUserBasicInfo();
        Glide.with(this).load(userBasicInfo.headPath).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(headView);

        walletName = view.findViewById(R.id.wallet_name);
        ViewCalculateUtil.setTextSize(walletName, 12);
        ViewCalculateUtil.setViewLinearLayoutParam(walletName, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 6, 0, 16, 16);
        walletAddress = view.findViewById(R.id.wallet_address);
        ViewCalculateUtil.setTextSize(walletAddress, 16);
        ViewCalculateUtil.setViewLinearLayoutParam(walletAddress, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT,4,0,16,16);
        View lineView = view.findViewById(R.id.line);
        ViewCalculateUtil.setViewLinearLayoutParam(lineView, ViewGroup.LayoutParams.MATCH_PARENT,1,5,0,16,16);

        View upgradeBtn = view.findViewById(R.id.upgrade_btn_layout);
        View upgradeIcon = view.findViewById(R.id.upgrade_icon);
        TextView upgradeValue = view.findViewById(R.id.upgrade_value);
        View claimsLayout = view.findViewById(R.id.claims_layout);
        View walletInfoLayout = view.findViewById(R.id.wallet_info);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        TabLayout tabLayout2 = view.findViewById(R.id.tab_layout2);
        View tabLayoutLayout = view.findViewById(R.id.tab_layout_view);
        View tabLayoutLayout2 = view.findViewById(R.id.tan_layout_view2);
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout2.setupWithViewPager(viewPager);
        String[] strings = {getString(R.string.wallet_securities), getString(R.string.wallet_token), getString(R.string.wallet_collectibles)};
        AssetsFragmentPagerAdapter adapter = new AssetsFragmentPagerAdapter(getChildFragmentManager(), getContext(), strings);
        viewPager.setAdapter(adapter);
        TextView assetsName = view.findViewById(R.id.assets_name);


        //适配
        ViewCalculateUtil.setViewConstraintLayoutParam(viewPager, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 8, 0, 0, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(assetsName, ViewGroup.LayoutParams.WRAP_CONTENT, 22, 16, 0, 16, 0);
        ViewCalculateUtil.setTextSize(assetsName, 16);
        ViewCalculateUtil.setViewConstraintLayoutParam(tabLayoutLayout, ViewGroup.LayoutParams.MATCH_PARENT, 60, 9, 0, 6, 6);
        ViewCalculateUtil.setViewLinearLayoutParam(tabLayoutLayout2, ViewGroup.LayoutParams.MATCH_PARENT, 60, 9, 0, 6, 6);
        ViewCalculateUtil.setViewLinearLayoutParam(tabLayout, ViewGroup.LayoutParams.MATCH_PARENT, 40, 0, 0, 10, 10);
        ViewCalculateUtil.setViewPadding(tabLayout, 5, 7, 0, 0);

        ViewCalculateUtil.setViewLinearLayoutParam(tabLayout2, ViewGroup.LayoutParams.MATCH_PARENT, 40, 0, 0, 10, 10);
        ViewCalculateUtil.setViewPadding(tabLayout2, 5, 7, 0, 0);

        ViewCalculateUtil.setViewConstraintLayoutParam(walletInfoLayout, ViewGroup.LayoutParams.MATCH_PARENT, 61, 16, 0, 16, 16);
        ViewCalculateUtil.setViewConstraintLayoutParam(claimsLayout, ViewGroup.LayoutParams.MATCH_PARENT, 23, 16, 0, 0, 0);
        ViewCalculateUtil.setTextSize(upgradeValue, 12);
        ViewCalculateUtil.setViewLinearLayoutParam(upgradeValue, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0, 8, 10);
        ViewCalculateUtil.setViewLinearLayoutParam(upgradeIcon, 17, 16, 0, 0, 12, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(upgradeBtn, ViewGroup.LayoutParams.WRAP_CONTENT,30, 0, 0, 0, 16);
        ViewCalculateUtil.setViewConstraintLayoutParam(headView, 72, 72, 24, 0, 0, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(topBg, ViewGroup.LayoutParams.MATCH_PARENT, 177);

        //滑动的距离，判断当滑动到顶部的时候，计算viewpager的高度进行设置。
        NestedScrollView nestedScrollView = view.findViewById(R.id.scroll_view);

        View topTabLayoutLayout  = view.findViewById(R.id.tab_layout_layout2);
        ViewCalculateUtil.setViewPadding(topTabLayoutLayout, 6, 0, 0, 0);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int[] location = new int[2];
                tabLayout.getLocationOnScreen(location);
                int y = location[1];
                int visibleY = getTopViewHeight();
                if (y <= visibleY) {
                    topTabLayoutLayout.setVisibility(View.VISIBLE);
                    tabLayoutLayout.setVisibility(View.INVISIBLE);
                } else {
                    topTabLayoutLayout.setVisibility(View.GONE);
                    tabLayoutLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private int getTopViewHeight() {
        int height = UIUtils.getInstance().getHeight(80);
        return height;
    }

}
