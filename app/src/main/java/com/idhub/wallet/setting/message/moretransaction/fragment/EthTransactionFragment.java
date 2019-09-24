package com.idhub.wallet.setting.message.moretransaction.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.greendao.entity.TransactionRecordEntity;
import com.idhub.wallet.net.Web3Api;
import com.idhub.wallet.setting.WalletNodeManager;
import com.idhub.wallet.setting.message.moretransaction.EthTransactionMessageAdapter;
import com.idhub.wallet.setting.message.moretransaction.MoreTransactionMessageActivity;
import com.idhub.wallet.setting.message.moretransaction.TransactionObservable;
import com.idhub.wallet.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import io.api.etherscan.core.impl.EtherScanApi;
import io.api.etherscan.model.EthNetwork;
import io.api.etherscan.model.Tx;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class EthTransactionFragment extends Fragment implements EthTransactionMessageAdapter.TransactionMesageLoadListener, Observer {


    private RecyclerView mRecyclerView;
    private EthTransactionMessageAdapter mAdapter;
    private TextView mEmptyView;
    private LoadingAndErrorView mLoadingAndErrorView;
    private MoreTransactionMessageActivity mActivity;
    private static final int MAX_END_BLOCK = 999999999;
    private int mPage;
    private static final int mOffset = 3;
    private boolean hasNextPage;
    private String mAddress;
    private EtherScanApi etherScanApi = new EtherScanApi(Web3Api.ethNetwork);

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
        mAdapter = new EthTransactionMessageAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setTransactionMesageLoadListener(this);
        mEmptyView = view.findViewById(R.id.message_empty);
        mLoadingAndErrorView = view.findViewById(R.id.loading_and_error);
        mRecyclerView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mLoadingAndErrorView.setVisibility(View.VISIBLE);
        TransactionObservable.getInstance().addObserver(this);
        initData();
        return view;
    }

    private void initData() {
        mPage = 1;
        hasNextPage = true;
        if (mActivity != null)
            mAddress = mActivity.getWalletAddress();
        loadData();
    }

    private void loadData() {
        Observable.create((ObservableOnSubscribe<List<Tx>>) emitter -> {
            List<Tx> txs = etherScanApi.account().txs(mAddress, 0, MAX_END_BLOCK, String.valueOf(mPage), mOffset);
            emitter.onNext(txs);
            emitter.onComplete();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<Tx>>() {
                    @Override
                    protected void onStart() {
                        super.onStart();
                        mLoadingAndErrorView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onNext(List<Tx> txes) {
                        List<TransactionRecordEntity> recordEntities = new ArrayList<>();
                        int size = txes.size();
                        for (int i = 0; i < size; i++) {
                            TransactionRecordEntity recordEntity = new TransactionRecordEntity();
                            recordEntity.setTx(txes.get(i));
                            recordEntities.add(recordEntity);
                        }
                        if (mPage == 1) {
                            if (size <= 0) {
                                mEmptyView.setVisibility(View.VISIBLE);
                                mRecyclerView.setVisibility(View.GONE);
                            } else {
                                mEmptyView.setVisibility(View.GONE);
                                mRecyclerView.setVisibility(View.VISIBLE);
                                mAdapter.addAllDatas(recordEntities);
                            }
                        } else {
                            if (size <= 0) {
                                ToastUtils.showShortToast( getString(R.string.wallet_no_data));
                            }
                            mAdapter.addDatas(recordEntities);
                        }
                        if (size < mOffset) {
                            //不到一整页数据
                            hasNextPage = false;
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        mEmptyView.setText(e.getMessage());
                        mEmptyView.setVisibility(View.VISIBLE);
                        mLoadingAndErrorView.setVisibility(View.GONE);
                        e.printStackTrace();
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
        if (hasNextPage) {
            mPage++;
            loadData();
        } else {
            ToastUtils.showShortToast( getString(R.string.wallet_no_data));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        TransactionObservable.getInstance().deleteObserver(this);
    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        initData();
    }
}
