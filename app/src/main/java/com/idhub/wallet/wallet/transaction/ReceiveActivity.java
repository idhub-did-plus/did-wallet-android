package com.idhub.wallet.wallet.transaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.idhub.wallet.R;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.utils.LogUtils;
import com.idhub.wallet.utils.ToastUtils;
import com.idhub.wallet.wallet.mainfragment.model.AssetsModel;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReceiveActivity extends AppCompatActivity implements View.OnClickListener, InputDialogFragment.InputDialogFragmentListener {

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
        mAddressView.setText(mAssetsModel.getAddress());
        updateQRCode();
    }

    private void updateQRCode() {
        StringBuilder codeBuilder = new StringBuilder();
        String address = mAssetsModel.getAddress();
        String token = mAssetsModel.getToken();
        codeBuilder.append("ethereum:").append(address).append("?");
        if (!TextUtils.isEmpty(token)) {
            codeBuilder.append("contractAddress=").append(token).append("&");
        }
        codeBuilder.append("decimal=18&").append("value=").append(value);
        String content = codeBuilder.toString();
        LogUtils.e("LYW", "updateQRCode: " + content);
        Observable.create((Observable.OnSubscribe<Bitmap>) subscriber -> {
            Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(content, BGAQRCodeUtil.dp2px(ReceiveActivity.this, 150));
            subscriber.onNext(bitmap);
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Bitmap>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Bitmap bitmap) {
                if (bitmap != null) {
                    mQrCodeView.setImageBitmap(bitmap);
                } else {
                    ToastUtils.showShortToast("生成二维码失败");
                }
            }
        });
    }

    private void initView() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_receive));
        mNameView = findViewById(R.id.name);
        mAddressView = findViewById(R.id.address);
        mAmountView = findViewById(R.id.amount);
        mQrCodeView = findViewById(R.id.qr_code);
        findViewById(R.id.tv_designated_Amount).setOnClickListener(this);
    }

    private void updateAmountView() {
        if (value == 0) {
            mAmountView.setVisibility(View.INVISIBLE);
        } else{
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
                InputDialogFragment dialogFragment = InputDialogFragment.getInstance("receive", getString(R.string.network_input_amount), InputType.TYPE_CLASS_NUMBER| InputType.TYPE_NUMBER_FLAG_DECIMAL);
                dialogFragment.show(getSupportFragmentManager(), "input_dialog_fragment");
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
