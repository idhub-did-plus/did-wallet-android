package com.idhub.wallet.assets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.idhub.base.greendao.entity.AssetsModel;
import com.idhub.wallet.R;
import com.idhub.wallet.assets.adapter.StListItemAdapter;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.greendao.AssetsModelDbManager;
import com.idhub.wallet.wallet.token.activity.TokenManagerActivity;
import com.idhub.wallet.wallet.token.adapter.TokenManagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class STListActivity extends BaseActivity {

    private StListItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_t_list);
        initView();
        initData();
    }

    private void initData() {
        List<AssetsModel> assetsModels = new AssetsModelDbManager().querySTAssets();
        if (assetsModels != null && assetsModels.size() > 0) {
            for (AssetsModel assetsModel : assetsModels) {
                assetsModel.getContractAddresses();
            }
            ArrayList<AssetsModel> list = new ArrayList<>();
            for (AssetsModel assetsModel : assetsModels) {
                if (!TextUtils.isEmpty(assetsModel.getCurrentContractAddress())) {
                    list.add(assetsModel);
                }
            }
            int size = list.size();
            if (size > 0) {
                adapter.addAll(list);
            }
        }
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, STListActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        RecyclerView recyclerView = findViewById(R.id.token_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StListItemAdapter(this);
        recyclerView.setAdapter(adapter);

    }
}
