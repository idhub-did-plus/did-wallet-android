package com.idhub.wallet.wallet.token;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.idhub.wallet.R;
import com.idhub.wallet.common.ImagesList.StringsListView;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.net.Web3Api;
import com.idhub.wallet.utils.ToastUtils;

import java.util.List;

import io.reactivex.subscribers.DisposableSubscriber;

public class WalletControllersDialogFragment extends DialogFragment implements View.OnClickListener {

    private StringsListView mRecyclerView;
    private String mContractAddress;
    private LoadingAndErrorView mLoadingAndErrorView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mContractAddress = arguments.getString("contractAddress");
        }
        View view = inflater.inflate(R.layout.wallet_controllers_dialog_fragment, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        Web3Api.searchERC1400Controllers(mContractAddress, new DisposableSubscriber<List>() {
            @Override
            protected void onStart() {
                super.onStart();
                mLoadingAndErrorView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNext(List list) {
                if (list != null && list.size() > 0) {
                    List<String> datas = list;
                    for (String data : datas) {
                        Log.e("LYW", "onNext: " + data);
                    }
                    mRecyclerView.addAll(datas);
                } else {
                    ToastUtils.showShortToast(getString(R.string.wallet_no_controllers));
                }
            }

            @Override
            public void onError(Throwable t) {
                ToastUtils.showShortToast(t.getMessage());
                mLoadingAndErrorView.setVisibility(View.GONE);
            }

            @Override
            public void onComplete() {
                mLoadingAndErrorView.setVisibility(View.GONE);
            }
        });
    }

    public static WalletControllersDialogFragment getInstance(String contractAddress) {
        WalletControllersDialogFragment walletListDialogFragment = new WalletControllersDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("contractAddress", contractAddress);
        walletListDialogFragment.setArguments(bundle);
        return walletListDialogFragment;
    }


    private void initView(View view) {
        view.findViewById(R.id.iv_cancel).setOnClickListener(this);
        mRecyclerView = view.findViewById(R.id.controllers_list);
        mLoadingAndErrorView = view.findViewById(R.id.loading_and_error);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_cancel:
                dismiss();
                break;
        }
    }


}
