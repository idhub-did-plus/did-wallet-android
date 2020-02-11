package com.idhub.wallet.wallet.token.view;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.idhub.base.greendao.entity.AssetsContractAddress;
import com.idhub.base.node.WalletNoteSharedPreferences;
import com.idhub.wallet.R;
import com.idhub.wallet.common.walletobservable.WalletAddAssetsObservable;
import com.idhub.wallet.greendao.AssetsContractAddressDbManager;
import com.idhub.wallet.greendao.AssetsModelDbManager;
import com.idhub.base.greendao.entity.AssetsModel;
import com.idhub.base.node.WalletNodeManager;
import com.idhub.wallet.utils.ToastUtils;
import com.idhub.wallet.wallet.token.TokenTypeManager;

import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;

import java.util.List;

public class TokenManagerItemView extends ConstraintLayout implements View.OnClickListener, AsyncOperationListener {

    private ImageView mTokenIcon;
    private TextView mTokenName;
    private TextView mContractName;
    private ImageView mTokenAdd;
    private AssetsModel assetsModel;

    public TokenManagerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTokenIcon = findViewById(R.id.iv_token_icon);
        mTokenName = findViewById(R.id.tv_token_name);
        mContractName = findViewById(R.id.tv_contract_address);
        mTokenAdd = findViewById(R.id.iv_token_add);
    }

    public void setData(AssetsModel assetsModel) {
        this.assetsModel = assetsModel;
        String symble = assetsModel.getSymbol();
        Integer integer = TokenTypeManager.assetsMipmap.get(symble);
        if (integer != null) {
            mTokenIcon.setImageResource(integer);
        }
        mTokenName.setText(symble);

        //查询设置
        AssetsModelDbManager assetsModelDbManager = new AssetsModelDbManager();
        String currentContractAddress = assetsModel.getCurrentContractAddress();
        mContractName.setText(currentContractAddress);
        assetsModelDbManager.queryCurrentNodeContractAddressKey(currentContractAddress, this);

    }


    @Override
    public void onClick(View v) {
        if (v == mTokenAdd) {

            long id =   new AssetsModelDbManager().insertDatasync(assetsModel);
            List<AssetsContractAddress> contractAddresses = assetsModel.getContractAddresses();
            for (AssetsContractAddress contractAddress : contractAddresses) {
                contractAddress.setAssetsId(id);
            }
            new AssetsContractAddressDbManager().insertInTxDatasync(contractAddresses);
            WalletAddAssetsObservable.getInstance().update();
            ToastUtils.showShortToast(getContext().getString(R.string.wallet_assets_add_success));
            mTokenAdd.setImageResource(R.mipmap.wallet_assets_selected);
            mTokenAdd.setClickable(false);

        }
    }


    private void setAddIcon(Object o) {
        List<AssetsModel> models = (List<AssetsModel>) o;
        if (models.size() > 0)
            mTokenAdd.setImageResource(R.mipmap.wallet_assets_selected);
        else {
            mTokenAdd.setImageResource(R.mipmap.wallet_add_assets);
            mTokenAdd.setOnClickListener(TokenManagerItemView.this);
        }
    }

    @Override
    public void onAsyncOperationCompleted(AsyncOperation operation) {
        Object o = operation.getResult();
        if (o != null)
            setAddIcon(o);
    }
}
