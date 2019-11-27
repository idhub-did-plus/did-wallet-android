package com.idhub.wallet.wallet.token.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.idhub.base.node.AssetsModel;
import com.idhub.base.node.WalletNodeManager;
import com.idhub.wallet.R;
import com.idhub.wallet.common.walletobservable.WalletAddAssetsObservable;
import com.idhub.wallet.greendao.AssetsModelDbManager;
import com.idhub.wallet.utils.ToastUtils;
import com.idhub.wallet.wallet.token.TokenTypeManager;

public class TokenRemoveItemView extends ConstraintLayout implements View.OnClickListener {

    private ImageView mTokenIcon;
    private TextView mTokenName;
    private TextView mContractName;
    private ImageView mTokenRemove;
    private AssetsModel assetsModel;

    public TokenRemoveItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTokenIcon = findViewById(R.id.iv_token_icon);
        mTokenName = findViewById(R.id.tv_token_name);
        mContractName = findViewById(R.id.tv_contract_address);
        mTokenRemove = findViewById(R.id.iv_token_remove);
        mTokenRemove.setOnClickListener(this);
    }

    public void setData(AssetsModel assetsModel) {
        this.assetsModel = assetsModel;
        String symble = assetsModel.getSymbol();
        Integer integer = TokenTypeManager.assetsMipmap.get(symble);
        if (integer != null) {
            mTokenIcon.setImageResource(integer);
        }else {
            mTokenIcon.setImageResource(R.mipmap.wallet_default_token_icon);
        }
        mTokenName.setText(symble);
        String contractAddressToNode = WalletNodeManager.assetsGetContractAddressToNode(assetsModel);
        mContractName.setText(contractAddressToNode);
    }


    @Override
    public void onClick(View v) {
        if (v == mTokenRemove) {
            new AssetsModelDbManager().deleteData(assetsModel, operation -> {
                if (operation.isCompleted()) {
                    WalletAddAssetsObservable.getInstance().update();
                    ToastUtils.showShortToast(getContext().getString(R.string.wallet_assets_remove_success));
                }
            });
        }
    }
}
