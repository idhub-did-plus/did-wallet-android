package com.idhub.wallet.me;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idhub.wallet.MainBaseFragment;
import com.idhub.wallet.R;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.model.Wallet;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.me.adapter.LevelAdapter;
import com.idhub.wallet.me.model.MeLevelEntity;
import com.idhub.wallet.me.view.MeTopView;
import com.idhub.wallet.net.IDHubCredentialProvider;
import com.idhub.wallet.utils.LogUtils;
import com.idhub.wallet.utils.ToastUtils;

import org.web3j.crypto.Credentials;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import wallet.idhub.com.clientlib.ApiFactory;
import wallet.idhub.com.clientlib.interfaces.ExceptionListener;
import wallet.idhub.com.clientlib.interfaces.ResultListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends MainBaseFragment {


    private MeTopView mTopView;

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wallet_fragment_me, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        TitleLayout titleLayout = view.findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_profile));
        titleLayout.setBackImgVisible(View.INVISIBLE);
        mTopView = view.findViewById(R.id.top_view);
        RecyclerView recyclerView = view.findViewById(R.id.rv_level);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LevelAdapter adapter = new LevelAdapter(getContext());
        recyclerView.setAdapter(adapter);
        MeLevelEntity meLevelEntity = new MeLevelEntity();
        meLevelEntity.level = "1";
        MeLevelEntity meLevelEntity1 = new MeLevelEntity();
        meLevelEntity1.level = "2";
        MeLevelEntity meLevelEntity2 = new MeLevelEntity();
        meLevelEntity2.level = "3";
        MeLevelEntity meLevelEntity3 = new MeLevelEntity();
        meLevelEntity3.level = "4";
        MeLevelEntity meLevelEntity4 = new MeLevelEntity();
        meLevelEntity4.level = "5";
        List<MeLevelEntity> meLevelEntities = new ArrayList<>();
        meLevelEntities.add(meLevelEntity);
        meLevelEntities.add(meLevelEntity1);
        meLevelEntities.add(meLevelEntity2);
        meLevelEntities.add(meLevelEntity3);
        meLevelEntities.add(meLevelEntity4);
        adapter.addAll(meLevelEntities);
    }

    @Override
    protected void loadData() {
        String defaultAddress = WalletManager.getDefaultAddress();
        if (TextUtils.isEmpty(defaultAddress)) {
            //显示1056
            mTopView.setEIN1056(WalletManager.getCurrentKeyStore().getAddress());
        } else {
            //先获取sp里是否有存储，没有则进行网络请求
            String ein = WalletOtherInfoSharpreference.getInstance().getEIN();
            if (TextUtils.isEmpty(ein)) {
                Credentials credentials = Credentials.create("0");
                BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();
                IDHubCredentialProvider.setDefaultCredentials(String.valueOf(privateKey));
                ApiFactory.getIdentityChainLocal().getEIN(defaultAddress).listen(new ResultListener<Long>() {
                    @Override
                    public void result(Long aLong) {
                        String einStr = NumericUtil.bigIntegerToHexWithZeroPadded(new BigInteger(aLong.toString()), 64);
                        WalletOtherInfoSharpreference.getInstance().setEIN(einStr);
                        mTopView.setEIN1484(einStr);
                    }
                }, new ExceptionListener() {
                    @Override
                    public void error(String s) {
                        mTopView.setEIN1484(ein);
                    }
                });
            }else {
                mTopView.setEIN1484(ein);
            }
        }
    }
}
