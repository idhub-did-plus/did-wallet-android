package com.idhub.wallet.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.idhub.wallet.R;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.common.title.TitleLayout;

import java.util.List;

public class NodeSettingActivity extends AppCompatActivity {

    private NodeSettingAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_node_setting);
        initView();
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, NodeSettingActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_node_setup));
        titleLayout.setFirstTextAndClickCallBack(getString(R.string.wallet_save), () -> {
           //保存节点
            String node = mAdapter.getNode();
            WalletOtherInfoSharpreference.getInstance().setNode(node);
            finish();
        });
        List<String> nodes = WalletNodeManager.nodes;
        String node = WalletOtherInfoSharpreference.getInstance().getNode();
        RecyclerView recyclerView = findViewById(R.id.node_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NodeSettingAdapter(this, node);
        recyclerView.setAdapter(mAdapter);
        mAdapter.addDatas(nodes);
    }
}
