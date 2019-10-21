package com.idhub.wallet.wallet.mainfragment.view;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.didhub.keystore.DidHubMnemonicKeyStore;
import com.idhub.wallet.didhub.keystore.WalletKeystore;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.utils.StringUtils;
import com.idhub.wallet.utils.ToastUtils;
import com.idhub.wallet.wallet.manager.WalletManagerActivity;

import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;

public class WalletItemView extends ConstraintLayout implements View.OnClickListener {

    private TextView mNameTv;
    private TextView mAddressTv;
    private ImageView mMenuIv;
    private WalletKeystore mKeyStore;
    private View mAssociatedAddress;
    private boolean clicked = true; //设置点击当前item是否跳转
    private TextView mEINView;

    public WalletItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mNameTv = findViewById(R.id.tv_wallet_name);
        mAddressTv = findViewById(R.id.tv_wallet_address);
        mMenuIv = findViewById(R.id.iv_wallet_menu);
        mAssociatedAddress = findViewById(R.id.tv_associated_address);
        mEINView = findViewById(R.id.tv_default_address);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked) {
                    WalletManagerActivity.startAction(getContext(), mKeyStore.getId());
                }
            }
        });
    }

    public void setData(WalletKeystore keyStore) {
        this.mKeyStore = keyStore;
        mNameTv.setText(keyStore.getWallet().getName());
        mAddressTv.setText(Keys.toChecksumAddress(NumericUtil.prependHexPrefix(keyStore.getAddress())));
        boolean isgl = keyStore.getWallet().isAssociate();
        if (isgl) {
            mAddressTv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.wallet_associated),null,null,null);
            mAssociatedAddress.setVisibility(GONE);
        } else {
            mAddressTv.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.mipmap.wallet_no_associated),null,null,null);
            mAssociatedAddress.setVisibility(VISIBLE);
            mAssociatedAddress.setOnClickListener(this);
        }
        if (keyStore.getWallet().isDefaultAddress()) {
            mEINView.setText(getContext().getString(R.string.wallet_default_address));
        }else{
            mEINView.setText("");
        }
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public void setMenuIv(int menuIv) {
        mMenuIv.setImageResource(menuIv);
    }

    public void setMenuIvVisible(int visible) {
        mMenuIv.setVisibility(visible);
    }

//    public void setAddressTextDrawable(){
//        Drawable drawable = getContext().getDrawable(R.mipmap.wallet_address_copy);
//        mAddressTv.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
//        mAddressTv.setOnClickListener(this);
//    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
            switch (id) {
                case R.id.tv_associated_address:
                    //关联地址
                    WalletManagerActivity.startAction(getContext(), mKeyStore.getId());
                    break;
            }
    }
}
