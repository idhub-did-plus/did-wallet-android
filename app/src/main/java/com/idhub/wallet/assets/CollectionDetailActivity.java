package com.idhub.wallet.assets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.net.Web3Api;
import com.idhub.wallet.net.collectiables.model.AssetsCollectionItem;

import org.web3j.utils.Convert;

import java.io.Serializable;
import java.math.BigInteger;

public class CollectionDetailActivity extends BaseActivity implements View.OnClickListener {

    private AssetsCollectionItem mAssetsCollectionItem;

    public static void startAction(Context context, AssetsCollectionItem assetsCollectionItem) {
        Intent intent = new Intent(context, CollectionDetailActivity.class);
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
        setContentView(R.layout.activity_collection_detail);
        initView();
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(mAssetsCollectionItem.name);
        ImageView imageView = findViewById(R.id.iamge);
        Glide.with(this).load(mAssetsCollectionItem.image_original_url).into(imageView);
        TextView lastSalePriceView = findViewById(R.id.last_sale_price);
        String value = NumericUtil.tokenValueFormatViewPointAfterEight(new BigInteger(mAssetsCollectionItem.last_sale.total_price), String.valueOf(Math.pow(10, 18d)));
        lastSalePriceView.setText(getString(R.string.wallet_last_sold_for) + ": "+ value +" ETH");
        TextView tokenIdView = findViewById(R.id.token_id);
        tokenIdView.setText(getString(R.string.wallet_token_id) + ": "+ mAssetsCollectionItem.token_id);
        TextView descriptionView = findViewById(R.id.description);
        descriptionView.setText(mAssetsCollectionItem.description);
        findViewById(R.id.send).setOnClickListener(this);
        findViewById(R.id.options).setOnClickListener(this);
        //获取以太坊gasPrice
        Web3Api.getGasPrice();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send:
                //交易
                CollectionTransactionActivity.startAction(this,mAssetsCollectionItem);
                break;
            case R.id.options:
                //选项
                CollectionOptionsActivity.startAction(this,mAssetsCollectionItem);
                break;
        }
    }
}
