package com.idhub.wallet.home;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idhub.wallet.MainBaseFragment;
import com.idhub.wallet.R;
import com.idhub.wallet.home.fragment.ClaimFragment;
import com.idhub.wallet.home.fragment.HotFragment;

import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends MainBaseFragment implements View.OnClickListener {


    private TextView tvClaimBtn;
    private TextView tvHotBtn;
    private ViewPager viewPager;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_home, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tvClaimBtn = view.findViewById(R.id.tv_claim_btn);
        tvHotBtn = view.findViewById(R.id.tv_hot_btn);
        tvClaimBtn.setOnClickListener(this);
        tvHotBtn.setOnClickListener(this);

        viewPager = view.findViewById(R.id.view_pager);
        LinkedList<Fragment> fragments = new LinkedList<>();
        fragments.add(new HotFragment());
        fragments.add(new ClaimFragment());
        viewPager.setAdapter(new HomePagerAdapter(getChildFragmentManager(),fragments));
        viewPager.canScrollHorizontally(0);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_claim_btn:
                setFocusBtn(true, tvClaimBtn);
                setFocusBtn(false, tvHotBtn);
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv_hot_btn:
                setFocusBtn(true, tvHotBtn);
                setFocusBtn(false, tvClaimBtn);
                viewPager.setCurrentItem(0);
                break;
        }
    }

    private void setFocusBtn(boolean focus, TextView view) {
        if (focus) {
            view.setTextSize(24);
        } else {
            view.setTextSize(16);
        }
        TextPaint paint = view.getPaint();
        paint.setFakeBoldText(focus);
    }
}
