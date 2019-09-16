package com.idhub.wallet.wallet.token;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.greendao.entity.AssetsModel;
import com.idhub.wallet.net.Web3Api;
import com.idhub.wallet.setting.WalletNodeManager;
import com.idhub.wallet.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subscribers.DisposableSubscriber;

public class Erc1400DetailActivity extends AppCompatActivity {

    private AssetsModel mAssetsModel;
    private PartitionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Object o = getIntent().getParcelableExtra("data");
        if (o == null) {
            finish();
            return;
        } else {
            mAssetsModel = (AssetsModel) o;
        }
        setContentView(R.layout.wallet_activity_erc1400_detail);
        initView();
        initData();
    }

    private void initData() {
        String node = WalletOtherInfoSharpreference.getInstance().getNode();
        String contraceAddress = "";
        if (WalletNodeManager.MAINNET.equals(node)) {
            contraceAddress = mAssetsModel.getMainContractAddress();
        } else if (WalletNodeManager.ROPSTEN.equals(node)) {
            contraceAddress = mAssetsModel.getRopstenContractAddress();
        }
        Web3Api.searchERC1400Partition(contraceAddress, new DisposableSubscriber<List>() {
            @Override
            public void onNext(List list) {
                List<PartitionEntity> partitionEntities = new ArrayList<>();
                for (byte[] data : (List<byte[]>) list) {
                    PartitionEntity partitionEntity = new PartitionEntity();
                    partitionEntity.name = data;
                    partitionEntities.add(partitionEntity);
                }
                adapter.addAll(partitionEntities);
            }

            @Override
            public void onError(Throwable t) {
                ToastUtils.showShortToast(t.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_detail));
        RecyclerView mPartitionList = findViewById(R.id.list_partition);
        mPartitionList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PartitionAdapter(this,mAssetsModel);
        mPartitionList.setAdapter(adapter);
    }

    public static void startAction(Context context, AssetsModel assetsModel) {
        Intent intent = new Intent(context, Erc1400DetailActivity.class);
        intent.putExtra("data", assetsModel);
        context.startActivity(intent);
    }
}
