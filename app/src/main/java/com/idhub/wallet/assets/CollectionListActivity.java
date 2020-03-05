package com.idhub.wallet.assets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.idhub.wallet.R;
import com.idhub.wallet.assets.adapter.CollectionItemAdapter;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.recyclerview.BaseRecyclerAdapter;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.net.collectiables.model.AssetsCollectionItem;

import java.io.Serializable;

public class CollectionListActivity extends BaseActivity {

    private AssetsCollectionItem mAssetsCollectionItem;

    public static void startAction(Context context, AssetsCollectionItem assetsCollectionItem) {
        Intent intent = new Intent(context, CollectionListActivity.class);
        intent.putExtra("data", (Serializable) assetsCollectionItem);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Object o = intent.getSerializableExtra("data");
        if (!(o instanceof AssetsCollectionItem)) {
            finish();
            return;
        }
        mAssetsCollectionItem = (AssetsCollectionItem) o;
        setContentView(R.layout.activity_collection_list);
        initView();
    }

    private void initView() {
        TitleLayout titleView = findViewById(R.id.title);
        titleView.setTitle(mAssetsCollectionItem.asset_contract.name);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CollectionItemAdapter collectionItemAdapter = new CollectionItemAdapter(this);
        collectionItemAdapter.addData(mAssetsCollectionItem);
        recyclerView.setAdapter(collectionItemAdapter);
        collectionItemAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {
                AssetsCollectionItem itemObject = collectionItemAdapter.getItemObject(pos);
                CollectionDetailActivity.startAction(CollectionListActivity.this,itemObject);
            }
        });

    }
}
