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
import com.idhub.wallet.assets.model.CollectionTokenBean;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.recyclerview.BaseRecyclerAdapter;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.net.collectiables.model.AssetsCollectionItem;

import java.io.Serializable;
import java.util.List;

public class CollectionListActivity extends BaseActivity {

    private CollectionTokenBean mCollectionTokenBean;

    public static void startAction(Context context, CollectionTokenBean collectionTokenBean) {
        Intent intent = new Intent(context, CollectionListActivity.class);
        intent.putExtra("data", collectionTokenBean);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Object o = intent.getParcelableExtra("data");
        if (!(o instanceof CollectionTokenBean)) {
            finish();
            return;
        }
        mCollectionTokenBean  = (CollectionTokenBean) o;
        setContentView(R.layout.activity_collection_list);
        initView();
    }

    private void initView() {
        TitleLayout titleView = findViewById(R.id.title);
        titleView.setTitle(mCollectionTokenBean.name);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CollectionItemAdapter collectionItemAdapter = new CollectionItemAdapter(this);
        collectionItemAdapter.addAll(mCollectionTokenBean.assetsCollectionItem);
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
