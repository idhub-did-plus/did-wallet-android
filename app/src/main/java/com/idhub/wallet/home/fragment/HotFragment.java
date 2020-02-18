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

import com.idhub.base.greendao.entity.AssetsModel;
import com.idhub.base.ui.ViewCalculateUtil;
import com.idhub.wallet.assets.fragment.STMainView;
import com.idhub.wallet.main.MainBaseFragment;
import com.idhub.wallet.R;
import com.idhub.wallet.home.adapter.MainEpidemicAdapter;
import com.idhub.wallet.home.adapter.MainEpidemicBean;
import com.idhub.wallet.home.adapter.STRecommendAdapter;
import com.idhub.wallet.home.adapter.STRecommendBean;

import java.util.ArrayList;
import java.util.List;

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

        STMainView stView = view.findViewById(R.id.st_view);
        List<AssetsModel> assetsModels = new ArrayList<>();
        assetsModels.add(new AssetsModel());
        assetsModels.add(new AssetsModel());
//        assetsModels.add(new AssetsModel());
//        assetsModels.add(new AssetsModel());
//        assetsModels.add(new AssetsModel());
//        assetsModels.add(new AssetsModel());
//        assetsModels.add(new AssetsModel());
        stView.setData(assetsModels);
//        ViewCalculateUtil.setViewLinearLayoutParam(newsContainer, 358, 111, 14, 0, 8, 8);
//        ViewCalculateUtil.setViewConstraintLayoutParam(newsTitle, ViewGroup.LayoutParams.WRAP_CONTENT, 22, 34, 0, 24, 0);
//        ViewCalculateUtil.setViewConstraintLayoutParam(newsMoreImage, 29, 12, 10, 0, 0, 0);
//        ViewCalculateUtil.setTextSize(newsTitle,16);
    }

    @Override
    protected void loadData() {

    }
}
