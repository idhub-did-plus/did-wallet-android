package com.idhub.wallet.hository.message.transaction;


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
import com.idhub.wallet.greendao.TransactionRecordDbManager;
import com.idhub.wallet.greendao.entity.TransactionRecordEntity;
import com.idhub.wallet.hository.message.moretransaction.MoreTransactionMessageActivity;

import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionMessageFragment extends Fragment implements TransactionMessageAdapter.TransactionMesageLoadListener {


    private TransactionMessageAdapter mAdapter;
    private LoadingAndErrorView mLoadingAndErrorView;
    private View mEmptyView;
    private RecyclerView mRecyclerView;

    public TransactionMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_transaction_message, container, false);
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
        new TransactionRecordDbManager().queryAll(new AsyncOperationListener() {
            @Override
            public void onAsyncOperationCompleted(AsyncOperation operation) {
                List<TransactionRecordEntity> entities = (List<TransactionRecordEntity>) operation.getResult();
                if (entities != null && entities.size() > 0) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mEmptyView.setVisibility(View.GONE);
                    mAdapter.addDatas(entities);
                } else {
                    mRecyclerView.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.VISIBLE);
                }
                mLoadingAndErrorView.setVisibility(View.GONE);

            }
        });
    }


    @Override
    public void Load() {
//        MoreTransactionMessageActivity.startAction(getContext());
    }
}
