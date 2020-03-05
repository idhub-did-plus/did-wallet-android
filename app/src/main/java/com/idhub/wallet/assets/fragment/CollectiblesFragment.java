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

import com.idhub.wallet.R;
import com.idhub.wallet.assets.AssetsFragment;
import com.idhub.wallet.assets.CollectionListActivity;
import com.idhub.wallet.assets.adapter.CollectionAdapter;
import com.idhub.wallet.assets.adapter.StaggeredDividerItemDecoration;
import com.idhub.wallet.common.recyclerview.BaseRecyclerAdapter;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.net.collectiables.CollectionHttpMethod;
import com.idhub.wallet.net.collectiables.model.AssetsCollectionItem;
import com.idhub.wallet.net.collectiables.model.AssetsCollections;
import com.idhub.wallet.net.collectiables.model.Collections;
import com.idhub.wallet.utils.LogUtils;

import org.web3j.crypto.Keys;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectiblesFragment extends Fragment {


    private CollectionAdapter adapter;

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
        return view;
    }

    private void initView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new StaggeredDividerItemDecoration(getContext(),16));

        recyclerView.setItemAnimator(null);
        adapter = new CollectionAdapter(getContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                AssetsCollectionItem itemObject = adapter.getItemObject(pos);
                CollectionListActivity.startAction(getContext(),itemObject);
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
                    List<AssetsCollectionItem> objects = new ArrayList<>();
                    List<AssetsCollectionItem> assets = collections.assets;
                    for (int i = 0; i < 20; i++) {
                        objects.addAll(assets);
                    }
                    adapter.addAll(objects);
                }

                @Override
                public void onError(Throwable t) {
                    LogUtils.e("LYW", "onError: " + t.getMessage() );
                }

                @Override
                public void onComplete() {

                }
            },String.valueOf(300), Keys.toChecksumAddress(NumericUtil.prependHexPrefix(mDidHubMnemonicKeyStore.getAddress())));
        }


    }

}
