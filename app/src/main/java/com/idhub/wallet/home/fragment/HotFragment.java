package com.idhub.wallet.home.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.idhub.base.ui.ViewCalculateUtil;
import com.idhub.wallet.MainBaseFragment;
import com.idhub.wallet.R;
import com.idhub.wallet.home.adapter.MainEpidemicAdapter;
import com.idhub.wallet.home.adapter.MainEpidemicBean;
import com.idhub.wallet.home.adapter.STRecommendAdapter;
import com.idhub.wallet.home.adapter.STRecommendBean;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends MainBaseFragment {


    public HotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_hot, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        RecyclerView stRecommendRecyclerView = view.findViewById(R.id.st_recommend_recycler);
        stRecommendRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        STRecommendAdapter stRecommendAdapter = new STRecommendAdapter(getContext());
        stRecommendRecyclerView.setAdapter(stRecommendAdapter);
        ViewCalculateUtil.setViewConstraintLayoutParam(view.findViewById(R. id.scroll_view), ViewGroup.LayoutParams.MATCH_PARENT,  ViewGroup.LayoutParams.WRAP_CONTENT, 8, 0, 8, 8  );

        ArrayList<STRecommendBean> items = new ArrayList<>();
        items.add(new STRecommendBean("Ning De Shi Dai","+10.00%"));
        items.add(new STRecommendBean("Ning De Shi Dai","+10.00%"));
        items.add(new STRecommendBean("Ning De Shi Dai","+10.00%"));
        items.add(new STRecommendBean("Ning De Shi Dai","+10.00%"));
        stRecommendAdapter.setItems(items);

        RecyclerView mainEpidemicRecyclerView = view.findViewById(R.id.main_epidemic_recycler);
        mainEpidemicRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        MainEpidemicAdapter mainEpidemicAdapter = new MainEpidemicAdapter(getContext());
        mainEpidemicRecyclerView.setAdapter(mainEpidemicAdapter);
        ViewCalculateUtil.setViewConstraintLayoutParam(mainEpidemicRecyclerView, ViewGroup.LayoutParams.MATCH_PARENT,  ViewGroup.LayoutParams.WRAP_CONTENT, 8, 16, 8, 8);
        ArrayList<MainEpidemicBean> mainEpidemicBeans = new ArrayList<>();
        mainEpidemicBeans.add(new MainEpidemicBean("Bitcoin", "≈2854.77（USD）", "+36.68", "+1.30%"));
        mainEpidemicBeans.add(new MainEpidemicBean("Ethereum", "≈2854.77（USD）", "+36.68", "+1.30%"));
        mainEpidemicBeans.add(new MainEpidemicBean("Shen Zhen Zhi Shu", "≈2854.77（USD）", "+36.68", "+1.30%"));
        mainEpidemicBeans.add(new MainEpidemicBean("Ethereum", "≈2854.77（USD）", "+36.68", "+1.30%"));
        mainEpidemicAdapter.setItems(mainEpidemicBeans);


        View newsContainer = view.findViewById(R.id.news_container);
        TextView newsTitle = view.findViewById(R.id.news_title);
        ImageView newsMoreImage = view.findViewById(R.id.news_more_image);
        View firstStView = view.findViewById(R.id.first_st);
        View secondStView = view.findViewById(R.id.second_st);
        View thirdStView = view.findViewById(R.id.third_st);
        View fourthStView = view.findViewById(R.id.fourth_st);
        View fifthStView = view.findViewById(R.id.fifth_st);
        View sixthStView = view.findViewById(R.id.sixth_st);
        View seventhStView = view.findViewById(R.id.seventh_st);
        ViewCalculateUtil.setViewConstraintLayoutParam(firstStView, 140, 87, 8, 0, 16, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(secondStView, 109, 64, 5, 0, 0, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(thirdStView, 107, 64, 0, 0, 5, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(fourthStView, 0, 0, 0, 0, 5, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(fifthStView, 56, 95, 0, 0, 5, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(sixthStView, 56, 95, 0, 0, 5, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(seventhStView, 0, 56, 0, 0, 0, 0);

//        ViewCalculateUtil.setViewLinearLayoutParam(newsContainer, 358, 111, 14, 0, 8, 8);
//        ViewCalculateUtil.setViewConstraintLayoutParam(newsTitle, ViewGroup.LayoutParams.WRAP_CONTENT, 22, 34, 0, 24, 0);
//        ViewCalculateUtil.setViewConstraintLayoutParam(newsMoreImage, 29, 12, 10, 0, 0, 0);
//        ViewCalculateUtil.setTextSize(newsTitle,16);
    }

    @Override
    protected void loadData() {

    }
}
