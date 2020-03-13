package com.idhub.wallet.dex;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.idhub.wallet.main.MainBaseFragment;
import com.idhub.wallet.R;
import com.idhub.wallet.main.MainTopTitleView;

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
        View lineView = view.findViewById(R.id.line);
        ImageView changeView = view.findViewById(R.id.dex_change);
        TextView coinExchangeRateView = view.findViewById(R.id.coin_exchange_rate);
        View btnView = view.findViewById(R.id.next);

        MainTopTitleView mainTopTitleView = view.findViewById(R.id.top_view);
        mainTopTitleView.setTitle(getString(R.string.wallet_dex));
        mainTopTitleView.setMenuIsVisible(View.GONE);
        mainTopTitleView.setScanIsVisible(View.GONE);

    }

    @Override
    protected void loadData() {

    }
}
