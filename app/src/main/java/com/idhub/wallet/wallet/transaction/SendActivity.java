package com.idhub.wallet.wallet.transaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import androidx.annotation.Nullable;

import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.common.zxinglib.QrCodeActivity;
import com.idhub.wallet.didhub.address.ETHAddressValidator;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.greendao.TransactionTokenType;
import com.idhub.wallet.net.Web3Api;
import com.idhub.wallet.utils.ToastUtils;
import com.idhub.base.greendao.entity.AssetsModel;
import com.idhub.wallet.wallet.mainfragment.QRCodeType;

import java.math.BigInteger;

public class SendActivity extends BaseActivity implements View.OnClickListener {

    private AssetsModel mAssetsModel;
    private TextView mNameView;
    private TextView mBalanceView;
    private EditText mAmountView;
    private EditText mAddressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_send);
        Parcelable data = getIntent().getParcelableExtra("data");
        if (data == null || !(data instanceof AssetsModel)) {
            finish();
        }
        mAssetsModel = (AssetsModel) data;
        init();
    }

    private void init() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_send));
        titleLayout.setFirstImageAndClickCallBack(R.mipmap.wallet_qrcode_scan, () -> {
            QrCodeActivity.startAction(this, 100);
        });
        mNameView = findViewById(R.id.name);
        mBalanceView = findViewById(R.id.balance);
        mAmountView = findViewById(R.id.input_amount);
        if (TransactionTokenType.ERC1400.equals(mAssetsModel.getType())) {
            mAmountView.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        mAddressView = findViewById(R.id.input_address);
        mNameView.setText(mAssetsModel.getSymbol());
        mBalanceView.setText("余额：" + NumericUtil.ethBigIntegerToNumberViewPointAfterFour(new BigInteger(mAssetsModel.getBalance()), String.valueOf(Math.pow(10, Double.parseDouble(mAssetsModel.getDecimals())))) + "" + mAssetsModel.getSymbol());
        findViewById(R.id.tv_next).setOnClickListener(this);
        Web3Api.getGasPrice();
    }

    public static void startAction(Context context, AssetsModel assetsModel) {
        Intent intent = new Intent(context, SendActivity.class);
        intent.putExtra("data", assetsModel);
        context.startActivity(intent);
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
            mAddressView.setText(address);
        }else {
            ToastUtils.showLongToast(qrcode);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_next:
                checkInput();
                break;
        }
    }

    private void checkInput() {
        String amount = mAmountView.getText().toString();
        if (TextUtils.isEmpty(amount)) {
            ToastUtils.showShortToast(getString(R.string.wallet_send_amount_not_empty));
            return;
        }
        String toAddress = mAddressView.getText().toString();
        if (TextUtils.isEmpty(toAddress)) {
            ToastUtils.showShortToast(getString(R.string.wallet_send_address_not_empty));
            return;
        }
        if (!ETHAddressValidator.isValidAddress(toAddress)) {
            ToastUtils.showShortToast(getString(R.string.wallet_send_address_false));
            return;
        }
        TransactionParam transactionParam = new TransactionParam();
        transactionParam.value = amount;
        transactionParam.toAddress = toAddress;
        transactionParam.type = mAssetsModel.getType();
        transactionParam.assetsModel = mAssetsModel;
        SendConfirmActivity.startAction(this,transactionParam,100);
    }

}
