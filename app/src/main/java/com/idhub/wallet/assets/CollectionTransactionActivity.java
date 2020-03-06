package com.idhub.wallet.assets;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.sharepreference.WalletTransactionSharpreference;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.common.zxinglib.QrCodeActivity;
import com.idhub.wallet.didhub.WalletInfo;
import com.idhub.wallet.didhub.WalletManager;
import com.idhub.wallet.didhub.address.ETHAddressValidator;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.greendao.TransactionTokenType;
import com.idhub.wallet.net.Web3Api;
import com.idhub.wallet.net.collectiables.model.AssetsCollectionItem;
import com.idhub.wallet.utils.ToastUtils;
import com.idhub.wallet.wallet.mainfragment.QRCodeType;
import com.idhub.wallet.wallet.transaction.InputGasDialogFragment;
import com.idhub.wallet.wallet.transaction.SendConfirmActivity;
import com.idhub.wallet.wallet.transaction.TransactionParam;

import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.Serializable;
import java.math.BigInteger;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.subscribers.DisposableSubscriber;

public class CollectionTransactionActivity extends BaseActivity implements View.OnClickListener {


    private AssetsCollectionItem mAssetsCollectionItem;
    private EditText mAddressEditView;

    public static void startAction(Context context, AssetsCollectionItem assetsCollectionItem) {
        Intent intent = new Intent(context, CollectionTransactionActivity.class);
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
        setContentView(R.layout.activity_collection_transaction);
        initView();
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        ImageView imageView = findViewById(R.id.image);
        mAddressEditView = findViewById(R.id.address);
        titleLayout.setTitle(mAssetsCollectionItem.asset_contract.name);
        titleLayout.setFirstImageAndClickCallBack(R.mipmap.wallet_qrcode_scan, new TitleLayout.OnImageClickCallbackListener() {
            @Override
            public void onImageClick() {
                QrCodeActivity.startAction(CollectionTransactionActivity.this, 100);
            }
        });
        Glide.with(this).load(mAssetsCollectionItem.image_original_url).into(imageView);
        findViewById(R.id.transfer).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.transfer:
                //输入密码确认交易
                String toAddress = mAddressEditView.getText().toString();
                if (TextUtils.isEmpty(toAddress)) {
                    ToastUtils.showShortToast(getString(R.string.wallet_send_address_not_empty));
                    return;
                }
                if (!ETHAddressValidator.isValidAddress(toAddress)) {
                    ToastUtils.showShortToast(getString(R.string.wallet_send_address_false));
                    return;
                }
                TransactionParam transactionParam = new TransactionParam();
                transactionParam.toAddress = toAddress;
                transactionParam.type = TransactionTokenType.ERC721;
                transactionParam.assetsCollectionItem = mAssetsCollectionItem;
                SendConfirmActivity.startAction(this, transactionParam, 100);
                break;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String qrcode = data.getStringExtra("qrcode").trim();
                handleQrCodeStr(qrcode);
            }
        }
    }
    private void handleQrCodeStr(String qrcode) {
        String address;
        if (qrcode.startsWith(QRCodeType.ETHERUM_TRANSACTION)) {
            if (qrcode.contains("?")) {
                address = qrcode.substring(QRCodeType.ETHERUM_TRANSACTION.length(), qrcode.indexOf("?"));
            }else {
                address = qrcode.substring(QRCodeType.ETHERUM_TRANSACTION.length());
            }
        }else {
            address = qrcode;
        }
        boolean validAddress = ETHAddressValidator.isValidAddress(address);
        if (validAddress) {
            mAddressEditView.setText(address);
        }else {
            ToastUtils.showLongToast(qrcode);
        }
    }
}
