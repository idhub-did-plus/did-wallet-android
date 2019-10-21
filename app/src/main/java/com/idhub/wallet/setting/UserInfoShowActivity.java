package com.idhub.wallet.setting;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.idhub.magic.common.service.DeployedContractAddress;
import com.idhub.wallet.R;
import com.idhub.wallet.common.activity.BaseActivity;
import com.idhub.wallet.common.dialog.InputDialogFragment;
import com.idhub.wallet.common.dialog.SelectUploadFileTypeDialogFragment;
import com.idhub.wallet.common.sharepreference.UserBasicInfoSharpreference;
import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;
import com.idhub.wallet.common.title.TitleLayout;
import com.idhub.wallet.common.walletobservable.WalletUpdateUserInfoObservable;
import com.idhub.wallet.createmanager.UserBasicInfoEntity;
import com.idhub.wallet.didhub.util.NumericUtil;
import com.idhub.wallet.utils.StringUtils;
import com.idhub.wallet.utils.ToastUtils;

import org.web3j.crypto.Keys;

import java.math.BigInteger;

public class UserInfoShowActivity extends BaseActivity implements View.OnClickListener, SelectUploadFileTypeDialogFragment.SelectUploadFileTypeDialogFragmentListener, InputDialogFragment.InputDialogFragmentListener {

    private TextView recoveryView;
    private TextView didView;
    private SelectUploadFileTypeDialogFragment mDialogFragment;
    private ImageView headView;
    private TextView nameView;
    private TextView signatureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_user_info_show);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        100);
            }
        }
        init();
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, UserInfoShowActivity.class);
        context.startActivity(intent);
    }
    private void init() {
        TitleLayout titleLayout = findViewById(R.id.title);
        titleLayout.setTitle(getString(R.string.wallet_user_info));
        headView = findViewById(R.id.iv_user_head);
        UserBasicInfoEntity userBasicInfo = UserBasicInfoSharpreference.getInstance().getUserBasicInfo();
        Glide.with(this).load(userBasicInfo.headPath).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(headView);

        nameView = findViewById(R.id.tv_user_name);
        nameView.setText(userBasicInfo.name);
        signatureView = findViewById(R.id.tv_user_signature);
        signatureView.setText(userBasicInfo.signature);

        View einLayoutView = findViewById(R.id.ll_EIN);
        TextView einView = findViewById(R.id.tv_user_ein);
        View didLayoutView = findViewById(R.id.ll_did);
        didView = findViewById(R.id.tv_user_did);
        String ein = WalletOtherInfoSharpreference.getInstance().getEIN();
        if (TextUtils.isEmpty(ein)) {
            einLayoutView.setVisibility(View.GONE);
            didLayoutView.setVisibility(View.GONE);
        }else {
            einLayoutView.setVisibility(View.VISIBLE);
            didLayoutView.setVisibility(View.VISIBLE);
            einView.setText(ein);
            didView.setText("did:erc1484:" + DeployedContractAddress.IdentityRegistryInterface + ":" + NumericUtil.bigIntegerToHexWithZeroPadded(new BigInteger(ein), 64));

        }

        View recoveryLayoutView = findViewById(R.id.ll_recovery_address);
        recoveryView = findViewById(R.id.tv_user_recovery_address);
        recoveryView.setOnClickListener(this);
        String recoverAddress = WalletOtherInfoSharpreference.getInstance().getRecoverAddress();
        if (TextUtils.isEmpty(recoverAddress)) {
            recoveryLayoutView.setVisibility(View.GONE);
        }else {
            recoveryLayoutView.setVisibility(View.VISIBLE);
            recoveryView.setText(Keys.toChecksumAddress(recoverAddress));
        }


        findViewById(R.id.ll_head).setOnClickListener(this);
        findViewById(R.id.ll_name).setOnClickListener(this);
        findViewById(R.id.ll_signature).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.ll_head:
               //修改头像
                mDialogFragment = SelectUploadFileTypeDialogFragment.getInstance(SelectUploadFileTypeDialogFragment.SOURCE_USER_BASIC_INFO);
                mDialogFragment.show(getSupportFragmentManager(), "select_upload_file");
                mDialogFragment.setUploadFileTypeDialogFragmentListeneristener(this);
                break;
            case R.id.ll_name:
                //修改名称
                InputDialogFragment instance = InputDialogFragment.getInstance("update_name", getString(R.string.wallet_update_user_name), InputType.TYPE_CLASS_TEXT);
                instance.show(getSupportFragmentManager(), "input_dialog_fragment");
                instance.setInputDialogFragmentListener(this);
                break;
            case R.id.ll_signature:
                //修改签名
                InputDialogFragment dialogFragment = InputDialogFragment.getInstance("update_signature", getString(R.string.wallet_update_signature), InputType.TYPE_CLASS_TEXT);
                dialogFragment.show(getSupportFragmentManager(), "input_dialog_fragment");
                dialogFragment.setInputDialogFragmentListener(this);
                break;
            case R.id.tv_user_recovery_address:
                String s = recoveryView.getText().toString();
                StringUtils.copy(this, s);
                ToastUtils.showShortToast(getString(R.string.wallet_copy_success));
                break;
            case R.id.tv_user_did:
                String did = didView.getText().toString();
                StringUtils.copy(this, did);
                ToastUtils.showShortToast(getString(R.string.wallet_copy_success));
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                ToastUtils.showShortToast(getString(R.string.scan_camera_permission));
                finish();
            }
        }
    }

    @Override
    public void selectFileResult(String path, String source) {
        Glide.with(this).load(path).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(headView);
        WalletUpdateUserInfoObservable.getInstance().update();
        mDialogFragment.dismiss();
    }

    @Override
    public void inputConfirm(String data, String source) {
        if ("update_name".equals(source)) {
            UserBasicInfoSharpreference.getInstance().setUserName(data);
            WalletUpdateUserInfoObservable.getInstance().update();
            nameView.setText(data);
        } else if ("update_signature".equals(source)) {
            UserBasicInfoSharpreference.getInstance().setUserSignature(data);
            WalletUpdateUserInfoObservable.getInstance().update();
            signatureView.setText(data);
        }
    }
}
