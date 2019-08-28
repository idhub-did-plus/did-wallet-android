package com.idhub.wallet.createmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.idhub.wallet.MainActivity;
import com.idhub.wallet.R;
import com.idhub.wallet.common.dialog.SelectUploadFileTypeDialogFragment;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.common.sharepreference.UserBasicInfoSharpreference;
import com.idhub.wallet.me.information.view.InformationInputItemView;
import com.idhub.wallet.utils.ToastUtils;

public class UploadUserBasicInfoActivity extends AppCompatActivity implements View.OnClickListener, SelectUploadFileTypeDialogFragment.SelectUploadFileTypeDialogFragmentListener {

    private ImageView mUserHeaderView;
    private InformationInputItemView mUserNameView;
    private InformationInputItemView mUserNickNameView;
    private String mUserHeadPath;
    private View mUploadView;
    private LoadingAndErrorView mLoadingAndErrorView;
    private SelectUploadFileTypeDialogFragment mDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_upload_user_basic_info);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        100);
            }
        }
        initView();
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, UploadUserBasicInfoActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        mUserHeaderView = findViewById(R.id.iv_user_head);
        mUserHeaderView.setOnClickListener(this);
        mUserNameView = findViewById(R.id.user_name);
        mUserNameView.setData(getString(R.string.wallet_user_name),getString(R.string.wallet_input_user_name));
        mUserNickNameView = findViewById(R.id.user_nick_name);
        mUserNickNameView.setData(getString(R.string.wallet_user_signature),getString(R.string.wallet_input_user_signature));
        mUploadView = findViewById(R.id.tv_upload);
        mUploadView.setOnClickListener(this);
        mLoadingAndErrorView = findViewById(R.id.loading_and_error);
    }

    @Override
    public void onClick(View v) {
        if (v == mUserHeaderView) {
            mDialogFragment = SelectUploadFileTypeDialogFragment.getInstance(SelectUploadFileTypeDialogFragment.SOURCE_USER_BASIC_INFO);
            mDialogFragment.show(getSupportFragmentManager(), "select_upload_file");
            mDialogFragment.setUploadFileTypeDialogFragmentListeneristener(this);
        } else if (v == mUploadView) {
            if (TextUtils.isEmpty(mUserHeadPath)) {
                ToastUtils.showShortToast(getString(R.string.wallet_not_empty_user_head));
                return;
            }
            String userName = mUserNameView.getInputData();
            if (TextUtils.isEmpty(userName)) {
                ToastUtils.showShortToast(getString(R.string.wallet_not_empty_user_name));
                return;
            }
            String userSignature = mUserNickNameView.getInputData();
            if (TextUtils.isEmpty(userSignature)) {
                ToastUtils.showShortToast(getString(R.string.wallet_not_empty_user_signature));
                return;
            }
            mLoadingAndErrorView.setVisibility(View.VISIBLE);
            //上传服务器，成功保存sp，头像保存到app sp commit成功跳转到首页

            UserBasicInfoEntity userBasicInfoEntity = new UserBasicInfoEntity();
            userBasicInfoEntity.signature = userSignature;
            userBasicInfoEntity.name = userName;
            userBasicInfoEntity.headPath = mUserHeadPath;
            if (UserBasicInfoSharpreference.getInstance().setUserBasicInfo(userBasicInfoEntity)) {
                MainActivity.startAction(this,"basic_user_info");
            }
            mLoadingAndErrorView.setVisibility(View.GONE);

        }
    }

    @Override
    public void selectFileResult(String path, String source) {
        mUserHeadPath = path;
        Glide.with(this).load(path).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(mUserHeaderView);
        mDialogFragment.dismiss();
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
}
