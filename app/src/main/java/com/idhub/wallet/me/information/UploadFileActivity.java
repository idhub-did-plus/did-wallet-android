package com.idhub.wallet.me.information;

import android.Manifest;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idhub.wallet.R;
import com.idhub.wallet.common.dialog.MessageDialogFragment;
import com.idhub.wallet.common.loading.LoadingAndErrorView;
import com.idhub.wallet.greendao.UploadFileDbManager;
import com.idhub.wallet.me.information.entity.UploadFileEntity;
import com.idhub.wallet.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadFileActivity extends AppCompatActivity implements View.OnClickListener {

    private UploadFileAdapter mUploadFileAdapter;
    private UploadFileDbManager mUploadFileDbManager;
    private List<String> mCheckNamesList = new ArrayList<>();//检查名字是否有重复
    private List<String> mCheckTypesList = new ArrayList<>();//检查类型是否有重复
    private List<String> mRepeatTypesList = new ArrayList<>();//如果有重复的type进行存储
    private LoadingAndErrorView mLoadingAndErrorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity_upload_file);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        100);
            }
        }
        initView();

    }

    private void initView() {
        mUploadFileDbManager = new UploadFileDbManager();
        mUploadFileDbManager.queryAll(operation -> {
            List<UploadFileEntity> entities = (List<UploadFileEntity>) operation.getResult();
            if (entities != null && entities.size() > 0) {
                for (UploadFileEntity entity : entities) {
                    mCheckNamesList.add(entity.getName());
                    mCheckTypesList.add(entity.getType());
                }
            }
        });
        RecyclerView recyclerView = findViewById(R.id.rv_upload_file);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mUploadFileAdapter = new UploadFileAdapter(this);
        recyclerView.setAdapter(mUploadFileAdapter);
        findViewById(R.id.tv_upload).setOnClickListener(this);
        mLoadingAndErrorView = findViewById(R.id.loading_and_error);

    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, UploadFileActivity.class);
        context.startActivity(intent);
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
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_upload:
                //上传
                List<UploadFileEntity> datas = mUploadFileAdapter.getDatas();
                if (datas.size() > 0) {
                    for (UploadFileEntity fileEntity : datas) {
                        String filePath = fileEntity.getFilePath();
                        String name = fileEntity.getName();
                        String type = fileEntity.getType();
                        //检查所有数据是否有遗漏未填写
                        if (TextUtils.isEmpty(filePath) || TextUtils.isEmpty(name) || TextUtils.isEmpty(type)) {
                            ToastUtils.showLongToast(getString(R.string.wallet_upload_file_incomplete_tip));
                            return;
                        }
                    }
                    //没有空数据，提交
                   if (checkNameNotRepeat(datas)){
                        //重复的四种type给出提示
                        if (mRepeatTypesList.size() > 0) {
                            StringBuilder builder = new StringBuilder();
                            for (String s : mRepeatTypesList) {
                                builder.append(s).append(",");
                            }
                            String message = builder.toString() + getString(R.string.wallet_type_repeat_tip);
                            MessageDialogFragment dialogFragment = MessageDialogFragment.getInstance(message, getString(R.string.wallet_confirm));
                            dialogFragment.show(getSupportFragmentManager(), "message_dialog_fragment");
                            dialogFragment.setMessagePasswordDialogFragmentListener(()->{
                                uploadFile(datas);
                            });
                        } else {
                            uploadFile(datas);
                        }
                    }
                }
                break;
        }
    }

    private boolean checkNameNotRepeat(List<UploadFileEntity> datas) {
        //检查name是否有重复
        //检查type身份证 护照 住址 独一份 。重复的进行记录
        mRepeatTypesList.clear();
        List<String> backupNameList = new ArrayList<>(mCheckNamesList);
        List<String> backupTypeList = new ArrayList<>();
        for (UploadFileEntity data : datas) {
            String name = data.getName();
            if (!backupNameList.contains(name)) {
                backupNameList.add(name);
            } else {
                //name重复
                ToastUtils.showLongToast(getString(R.string.wallet_file_name) + name + getString(R.string.wallet_repeat) + getString(R.string.wallet_edit_after_again_upload));
                return false;
            }
            String type = data.getType();
            if (getString(R.string.wallet_id_photo_positive).equals(type) || getString(R.string.wallet_id_photo_negative).equals(type) || getString(R.string.wallet_passport_photo).equals(type) || getString(R.string.wallet_address_proof_document).equals(type)) {
                if (mCheckTypesList.contains(type)) {
                    backupTypeList.add(type);
                }
            }
        }

        mRepeatTypesList.addAll(backupTypeList);
        return true;
    }

    private void uploadFile(List<UploadFileEntity> datas) {
        //接口提交成功之后
        mLoadingAndErrorView.setVisibility(View.VISIBLE);
        if (mRepeatTypesList.size() > 0) {
            for (String type : mRepeatTypesList) {
                mUploadFileDbManager.deleteByType(type);
            }
        }
        mUploadFileDbManager.insertListData(datas,operation ->{
            boolean completed = operation.isCompleted();
            mLoadingAndErrorView.setVisibility(View.GONE);
        });
    }
}
