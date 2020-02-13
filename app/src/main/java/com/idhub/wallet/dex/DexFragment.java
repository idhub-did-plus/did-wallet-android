package com.idhub.wallet.dex;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.idhub.base.ui.ViewCalculateUtil;
import com.idhub.wallet.main.MainBaseFragment;
import com.idhub.wallet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DexFragment extends MainBaseFragment {


    public DexFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_dex, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        View containerView = view.findViewById(R.id.main_dex_content_container);
        TextView payNameView = view.findViewById(R.id.pay_name);
        TextView getNameView = view.findViewById(R.id.get_name);
        TextView payBalanceView = view.findViewById(R.id.pay_balance);
        TextView getBalanceView = view.findViewById(R.id.get_balance);
        EditText payValueView = view.findViewById(R.id.pay_value);
        EditText getValueView = view.findViewById(R.id.get_value);
        ImageView payCoinImgView = view.findViewById(R.id.pay_coin_img);
        ImageView getCoinImgView = view.findViewById(R.id.get_coin_img);
        TextView payCoinNameView = view.findViewById(R.id.pay_coin_name);
        TextView getCoinNameView = view.findViewById(R.id.get_coin_name);
        ImageView payRightArrowView = view.findViewById(R.id.dex_right_arrow);
        ImageView getRightArrowView = view.findViewById(R.id.get_right_arrow);
        View lineView = view.findViewById(R.id.line);
        ImageView changeView = view.findViewById(R.id.dex_change);
        TextView coinExchangeRateView = view.findViewById(R.id.coin_exchange_rate);
        View btnView = view.findViewById(R.id.next);
        ViewCalculateUtil.setViewConstraintLayoutParam(btnView, 343, 40, 24, 0, 0, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(coinExchangeRateView, 0, ViewGroup.LayoutParams.WRAP_CONTENT, 24, 0, 0, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(changeView, 38, 38, 6, 0, 58, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(lineView, ViewGroup.LayoutParams.MATCH_PARENT, 1, 7, 0, 0, 0);
        ViewCalculateUtil.setViewLinearLayoutParam(payRightArrowView, 9, 14, 0, 0, 0, 14);
        ViewCalculateUtil.setViewLinearLayoutParam(getRightArrowView, 9, 14, 0, 0, 0, 14);
        ViewCalculateUtil.setTextSize(payCoinNameView, 14);
        ViewCalculateUtil.setViewLinearLayoutParam(payCoinNameView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0, 0, 21);
        ViewCalculateUtil.setTextSize(getCoinNameView, 14);
        ViewCalculateUtil.setViewLinearLayoutParam(getCoinNameView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0, 0, 21);
        ViewCalculateUtil.setViewLinearLayoutParam(payCoinImgView, 31, 31, 6, 7, 0, 16);
        ViewCalculateUtil.setViewLinearLayoutParam(getCoinImgView, 31, 31, 6, 7, 0, 16);
        ViewCalculateUtil.setTextSize(payValueView, 19);
        ViewCalculateUtil.setViewLinearLayoutParam(payValueView, 0, ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0, 16, 0);
        ViewCalculateUtil.setTextSize(getValueView, 19);
        ViewCalculateUtil.setViewLinearLayoutParam(getValueView, 0, ViewGroup.LayoutParams.WRAP_CONTENT, 0, 0, 16, 0);
        ViewCalculateUtil.setTextSize(payNameView, 12);
        ViewCalculateUtil.setViewConstraintLayoutParam(payNameView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 16, 0, 16, 0);
        ViewCalculateUtil.setTextSize(getNameView, 12);
        ViewCalculateUtil.setViewConstraintLayoutParam(getNameView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 16, 0, 16, 0);
        ViewCalculateUtil.setTextSize(payBalanceView, 12);
        ViewCalculateUtil.setViewConstraintLayoutParam(payBalanceView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 10, 0, 16, 0);
        ViewCalculateUtil.setTextSize(getBalanceView, 12);
        ViewCalculateUtil.setViewConstraintLayoutParam(getBalanceView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 10, 8, 16, 0);
        ViewCalculateUtil.setViewConstraintLayoutParam(containerView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 10, 0, 16, 16);

    }

    @Override
    protected void loadData() {

    }
}
