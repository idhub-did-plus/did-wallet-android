package com.idhub.wallet.auth;

import com.idhub.base.greendao.entity.UploadFileEntity;
import com.idhub.base.greendao.entity.UploadIDHubInfoEntity;

import java.util.List;

public class UploadDataParam {
    public UploadIDHubInfoEntity uploadIDHubInfoEntity;
    public List<UploadFileEntity> entities;

    public UploadDataParam(UploadIDHubInfoEntity uploadIDHubInfoEntity, List<UploadFileEntity> entities) {
        this.uploadIDHubInfoEntity = uploadIDHubInfoEntity;
        this.entities = entities;
    }
}
