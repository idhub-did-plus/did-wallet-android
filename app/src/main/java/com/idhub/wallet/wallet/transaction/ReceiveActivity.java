package com.idhub.wallet.wallet.transaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.setting.WalletNodeManager;
import com.idhub.wallet.utils.LogUtils;
import com.idhub.wallet.utils.QRCodeEncoder;
import com.idhub.wallet.utils.StringUtils;
import com.idhub.wallet.utils.ToastUtils;
import com.idhub.wallet.greendao.entity.AssetsModel;

import org.web3j.crypto.Keys;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ReceiveActivity extends BaseActivity implements View.OnClickListener, InputDialogFragment.InputDialogFragmentListener {

    private ImageView mQrCodeView;
    private AssetsModel mAssetsModel;
    private float value;
    private TextView mAmountView;
    private TextView mAddressView;
    private TextView mNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_receive);
        Parcelable data = getIntent().getParcelableExtra("data");
        if (data == null || !(data instanceof AssetsModel)) {
            finish();
        }
        mAssetsModel = (AssetsModel) data;
        initView();
        initData();
    }

    private void initData() {
        mNameView.setText(mAssetsModel.getName());
        mAddressView.setText(Keys.toChecksumAddress(mAssetsModel.getAddress()));
        updateQRCode();
    }

    private void updateQRCode() {
        StringBuilder codeBuilder = new StringBuilder();
        String address = mAssetsModel.getAddress();
        String contractAddress = WalletNodeManager.assetsGetContractAddressToNode(mAssetsModel);
        codeBuilder.append("ethereum:").append(address).append("?");
        if (!TextUtils.isEmpty(contractAddress)) {
            codeBuilder.append("contractAddress=").append(contractAddress).append("&");
        }
        codeBuilder.append("decimal=18&").append("value=").append(value);
        String content = codeBuilder.toString();
        LogUtils.e("LYW", "updateQRCode: " + content);
        Observable.create((ObservableOnSubscribe<Bitmap>) emitter -> {
            Bitmap bitmap = QRCodeEncoder.createQRImage(content, 150, 150);
            emitter.onNext(bitmap);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<Bitmap>() {
                    @Override
                    public void onNext(Bitmap bitmap) {
                        if (bitmap != null) {
                            mQrCodeView.setImageBitmap(bitmap);
                        } else {
                            ToastUtils.showShortToast("生成二维码失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_receive));
        mNameView = findViewById(R.id.name);
        mAddressView = findViewById(R.id.address);
        mAddressView.setOnClickListener(this);
        mAmountView = findViewById(R.id.amount);
        mQrCodeView = findViewById(R.id.qr_code);
        findViewById(R.id.tv_designated_Amount).setOnClickListener(this);
    }

    private void updateAmountView() {
        if (value == 0) {
            mAmountView.setVisibility(View.INVISIBLE);
        } else {
            mAmountView.setVisibility(View.VISIBLE);
            mAmountView.setText("请转入 " + value + " " + mAssetsModel.getName());
        }
    }

    public static void startAction(Context context, AssetsModel assetsModel) {
        Intent intent = new Intent(context, ReceiveActivity.class);
        intent.putExtra("data", assetsModel);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_designated_Amount:
                InputDialogFragment dialogFragment = InputDialogFragment.getInstance("receive", getString(R.string.wallet_input_amount), InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                dialogFragment.show(getSupportFragmentManager(), "input_dialog_fragment");
                dialogFragment.setInputDialogFragmentListener(this);
                break;
            case R.id.address:
                String s = mAddressView.getText().toString();
                StringUtils.copy(this, s);
                ToastUtils.showShortToast(getString(R.string.wallet_copy_address_success));
                break;
        }
    }

    @Override
    public void inputConfirm(String data, String source) {
        value = Float.valueOf(data);
        updateAmountView();
        updateQRCode();
    }
}
