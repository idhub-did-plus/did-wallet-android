package com.idhub.wallet.assets.fragment;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.idhub.base.node.WalletNodeSelectedObservable;
import com.idhub.wallet.R;
import com.idhub.wallet.assets.AssetsFragment;
import com.idhub.wallet.assets.CollectionListActivity;
import com.idhub.wallet.assets.adapter.CollectionAdapter;
import com.idhub.wallet.assets.adapter.StaggeredDividerItemDecoration;
import com.idhub.wallet.assets.model.CollectionTokenBean;
import com.idhub.wallet.common.recyclerview.BaseRecyclerAdapter;
import com.idhub.wallet.dapp.Web3Activity;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.net.collectiables.CollectionContent;
import com.idhub.wallet.net.collectiables.CollectionHttpMethod;
import com.idhub.wallet.net.collectiables.model.AssetContractBean;
import com.idhub.wallet.net.collectiables.model.AssetsCollectionItem;
import com.idhub.wallet.net.collectiables.model.AssetsCollections;
import com.idhub.wallet.net.collectiables.model.Collections;
import com.idhub.wallet.utils.LogUtils;

import org.web3j.crypto.Keys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectiblesFragment extends Fragment implements View.OnClickListener {


    private CollectionAdapter adapter;
    private TextView mOpenSeaLinkView;

    public CollectiblesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_collectibles, container, false);
        initView(view);
        initData();
        WalletNodeSelectedObservable.getInstance().addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                //节点切换
                initData();
            }
        });

        return view;
    }

    private void initView(View view) {
        mOpenSeaLinkView = view.findViewById(R.id.opensea_link);
        mOpenSeaLinkView.setOnClickListener(this);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new StaggeredDividerItemDecoration(getContext(), 16));
        recyclerView.setItemAnimator(null);
        adapter = new CollectionAdapter(getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                if (pos >= 0) {
                    CollectionTokenBean tokenBean = adapter.getItemObject(pos);
                    CollectionListActivity.startAction(getContext(), tokenBean);
                }
            }
        });
    }

    private void initData() {
        Fragment fragment = getParentFragment();
        if (fragment instanceof AssetsFragment) {
            AssetsFragment assetsFragment = (AssetsFragment) fragment;
            WalletKeystore mDidHubMnemonicKeyStore = assetsFragment.mDidHubMnemonicKeyStore;
            if (mDidHubMnemonicKeyStore == null) {
                return;
            }
            CollectionHttpMethod.getInstance().assets(new ResourceSubscriber<AssetsCollections>() {
                @Override
                public void onNext(AssetsCollections collections) {
                    List<AssetsCollectionItem> assets = collections.assets;
                    //处理数据，将相同合约合并展示一次
                    if (assets != null && assets.size() > 0) {
                        HashMap<String, CollectionTokenBean> map = new HashMap<String, CollectionTokenBean>();
                        for (AssetsCollectionItem asset : assets) {
                            String address = asset.asset_contract.address;
                            CollectionTokenBean collectionTokenBean = map.get(address);
                            if (collectionTokenBean == null) {
                                AssetContractBean contractBean = asset.asset_contract;
                                ArrayList<AssetsCollectionItem> assetsCollectionItem = new ArrayList<>();
                                assetsCollectionItem.add(asset);
                                CollectionTokenBean tokenBean = new CollectionTokenBean(contractBean.address, contractBean.image_url, contractBean.description, contractBean.name, assetsCollectionItem);
                                map.put(address, tokenBean);
                            } else {
                                collectionTokenBean.assetsCollectionItem.add(asset);
                            }
                        }

                        adapter.addAll(new ArrayList<>(map.values()));
                        mOpenSeaLinkView.setVisibility(View.VISIBLE);
                    } else {
                        mOpenSeaLinkView.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onError(Throwable t) {
                    LogUtils.e("LYW", "onError: " + t.getMessage());
                }

                @Override
                public void onComplete() {

                }
            }, String.valueOf(300), Keys.toChecksumAddress(NumericUtil.prependHexPrefix(mDidHubMnemonicKeyStore.getAddress())));
        }


    }

    @Override
    public void onClick(View v) {
        if (v == mOpenSeaLinkView) {
            //goto opensea
            Web3Activity.startAction(getContext(), CollectionContent.OPEN_SEA_LINK);
        }
    }
}
