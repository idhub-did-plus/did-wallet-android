package com.idhub.wallet.auth;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.idhub.base.greendao.entity.UploadFileEntity;
import com.idhub.base.greendao.entity.UploadIDHubInfoEntity;
import com.idhub.wallet.R;
import com.idhub.wallet.utils.FileUtils;
import com.idhub.wallet.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class InformationItemLayout extends LinearLayout {

    protected SaveClickListener saveClickListener;
    protected UploadIDHubInfoEntity uploadInfo;
    protected List<UploadFileEntity> fileEntities = new ArrayList<>();

    public InformationItemLayout(Context context) {
        super(context);
        init();
    }

    public InformationItemLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InformationItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected abstract void init();

    //设置文字信息
    public void setUploadInfo(UploadIDHubInfoEntity uploadInfo) {
        this.uploadInfo = uploadInfo;
        setInformation();
    }

    ;

    //设置文件信息
    public abstract void setFileInfo(Map<String, UploadFileEntity> map);

    public abstract void setInformation();

    protected void save() {
        fileEntities.clear();
        if (!saveData()) {
            return;
        }
        if (saveClickListener != null) {
            UploadDataParam uploadDataParam = new UploadDataParam(uploadInfo, fileEntities);
            saveClickListener.click(uploadDataParam);
        }
    }

    public abstract boolean saveData();

    OnClickListener saveBtnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            save();
        }
    };


    protected boolean addFileEntity(UploadFileEntity uploadFileEntity, String typeName, String filePath) {
        //判断数据库中已经有数据进行更改，没有数据进行添加
        if (!TextUtils.isEmpty(filePath)) {
            File file = new File(filePath);
            double fileSize = getFileSize(file);
            if (fileSize > 20) {
                ToastUtils.showShortToast(typeName + getContext().getString(R.string.wallet_file_size_20));
                //文件大于20M不执行下一步
                return false;
            }

            if (uploadFileEntity == null) {
                uploadFileEntity = new UploadFileEntity();
            }
            uploadFileEntity.setFilePath(filePath);
            uploadFileEntity.setName(file.getName());
            uploadFileEntity.setType(typeName);
            fileEntities.add(uploadFileEntity);
        }
        return true;
    }


    public double getFileSize(File file) {
        return FileUtils.FormetMBFileSize(FileUtils.getFileSize(file));
    }

    public void setSaveClickListener(SaveClickListener saveClickListener) {
        this.saveClickListener = saveClickListener;
    }
}
