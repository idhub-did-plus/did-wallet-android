package com.idhub.wallet.hository.message.moretransaction.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.R;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.hository.message.moretransaction.MoreTransactionMessageActivity;
import com.idhub.wallet.hository.message.transaction.TransactionMessageAdapter;
import com.idhub.wallet.network.C;
import com.idhub.wallet.utils.ToastUtils;

import java.util.List;

import io.api.etherscan.core.impl.EtherScanApi;
import io.api.etherscan.core.impl.TransactionApiProvider;
import io.api.etherscan.model.EthNetwork;
import io.api.etherscan.model.Tx;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class EthTransactionFragment extends Fragment implements TransactionMessageAdapter.TransactionMesageLoadListener {


    private RecyclerView mRecyclerView;
    private TransactionMessageAdapter mAdapter;
    private View mEmptyView;
    private LoadingAndErrorView mLoadingAndErrorView;
    private MoreTransactionMessageActivity mActivity;
    private static final int MAX_END_BLOCK = 999999999;
    private int mPage;
    private static final int mOffset = 100;
    private String mAddress;
    private EtherScanApi mApi = new EtherScanApi(C.ROPSTEN);

    public EthTransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MoreTransactionMessageActivity) {
            mActivity = (MoreTransactionMessageActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_eth_transaction, container, false);
        mRecyclerView = view.findViewById(R.id.transaction_message_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new TransactionMessageAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setTransactionMesageLoadListener(this);
        mEmptyView = view.findViewById(R.id.message_empty);
        mLoadingAndErrorView = view.findViewById(R.id.loading_and_error);
        mLoadingAndErrorView.setVisibility(View.VISIBLE);
        initData();
        return view;
    }

    private void initData() {
        mPage = 0;
        if (mActivity != null)
            mAddress = mActivity.getWalletAddress();
        loadData();
    }

    private void loadData() {

        Observable.create((ObservableOnSubscribe<List<Tx>>) emitter -> {
            mLoadingAndErrorView.setVisibility(View.VISIBLE);
            List<Tx> txs = mApi.account().txs(mAddress, 0, MAX_END_BLOCK, String.valueOf(mPage), mOffset);
            emitter.onNext(txs);
            emitter.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<Tx>>() {
                    @Override
                    public void onNext(List<Tx> txes) {

//                        mAdapter.addDatas();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mLoadingAndErrorView.setVisibility(View.GONE);
                        ToastUtils.showShortToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mLoadingAndErrorView.setVisibility(View.GONE);
                    }
                });

    }

    @Override
    public void Load() {
        //next page
        mPage++;
        loadData();
    }
}
