package com.idhub.wallet.assets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.dapp.Web3Activity;
import com.idhub.wallet.net.collectiables.model.AssetsCollectionItem;

public class CollectionOptionsActivity extends BaseActivity implements View.OnClickListener {

    private AssetsCollectionItem mAssetsCollectionItem;

    public static void startAction(Context context, AssetsCollectionItem assetsCollectionItem) {
        Intent intent = new Intent(context, CollectionOptionsActivity.class);
        intent.putExtra("data",  assetsCollectionItem);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Object o = intent.getParcelableExtra("data");
        if (!(o instanceof AssetsCollectionItem)) {
            finish();
            return;
        }
        mAssetsCollectionItem = (AssetsCollectionItem) o;
        setContentView(R.layout.activity_collection_options);
        initView();
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(mAssetsCollectionItem.name);
        TextView viewOnTextView = findViewById(R.id.view_on);
        viewOnTextView.setText(getString(R.string.wallet_View_on) +" "+ mAssetsCollectionItem.asset_contract.name);
        findViewById(R.id.sell_on_opensea).setOnClickListener(this);
        viewOnTextView.setOnClickListener(this);
        String externalLink = mAssetsCollectionItem.external_link;
        if (TextUtils.isEmpty(externalLink)) {
            viewOnTextView.setVisibility(View.GONE);
        } else {
            viewOnTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sell_on_opensea:
                String permalink = mAssetsCollectionItem.permalink;
                Web3Activity.startAction(this, permalink);
                break;
            case R.id.view_on:
                String externalLink = mAssetsCollectionItem.external_link;
                Web3Activity.startAction(this, externalLink);
                break;
        }
    }
}
