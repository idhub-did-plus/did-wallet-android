package com.idhub.base.greendao.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.idhub.base.greendao.entity.IdHubMessageEntity;
import com.idhub.base.greendao.entity.TransactionRecordEntity;
import com.idhub.base.greendao.entity.UploadFileEntity;
import com.idhub.base.greendao.entity.UploadIDHubInfoEntity;
import com.idhub.base.node.AssetsModel;

import com.idhub.base.greendao.db.IdHubMessageEntityDao;
import com.idhub.base.greendao.db.TransactionRecordEntityDao;
import com.idhub.base.greendao.db.UploadFileEntityDao;
import com.idhub.base.greendao.db.UploadIDHubInfoEntityDao;
import com.idhub.base.greendao.db.AssetsModelDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig idHubMessageEntityDaoConfig;
    private final DaoConfig transactionRecordEntityDaoConfig;
    private final DaoConfig uploadFileEntityDaoConfig;
    private final DaoConfig uploadIDHubInfoEntityDaoConfig;
    private final DaoConfig assetsModelDaoConfig;

    private final IdHubMessageEntityDao idHubMessageEntityDao;
    private final TransactionRecordEntityDao transactionRecordEntityDao;
    private final UploadFileEntityDao uploadFileEntityDao;
    private final UploadIDHubInfoEntityDao uploadIDHubInfoEntityDao;
    private final AssetsModelDao assetsModelDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        idHubMessageEntityDaoConfig = daoConfigMap.get(IdHubMessageEntityDao.class).clone();
        idHubMessageEntityDaoConfig.initIdentityScope(type);

        transactionRecordEntityDaoConfig = daoConfigMap.get(TransactionRecordEntityDao.class).clone();
        transactionRecordEntityDaoConfig.initIdentityScope(type);

        uploadFileEntityDaoConfig = daoConfigMap.get(UploadFileEntityDao.class).clone();
        uploadFileEntityDaoConfig.initIdentityScope(type);

        uploadIDHubInfoEntityDaoConfig = daoConfigMap.get(UploadIDHubInfoEntityDao.class).clone();
        uploadIDHubInfoEntityDaoConfig.initIdentityScope(type);

        assetsModelDaoConfig = daoConfigMap.get(AssetsModelDao.class).clone();
        assetsModelDaoConfig.initIdentityScope(type);

        idHubMessageEntityDao = new IdHubMessageEntityDao(idHubMessageEntityDaoConfig, this);
        transactionRecordEntityDao = new TransactionRecordEntityDao(transactionRecordEntityDaoConfig, this);
        uploadFileEntityDao = new UploadFileEntityDao(uploadFileEntityDaoConfig, this);
        uploadIDHubInfoEntityDao = new UploadIDHubInfoEntityDao(uploadIDHubInfoEntityDaoConfig, this);
        assetsModelDao = new AssetsModelDao(assetsModelDaoConfig, this);

        registerDao(IdHubMessageEntity.class, idHubMessageEntityDao);
        registerDao(TransactionRecordEntity.class, transactionRecordEntityDao);
        registerDao(UploadFileEntity.class, uploadFileEntityDao);
        registerDao(UploadIDHubInfoEntity.class, uploadIDHubInfoEntityDao);
        registerDao(AssetsModel.class, assetsModelDao);
    }
    
    public void clear() {
        idHubMessageEntityDaoConfig.clearIdentityScope();
        transactionRecordEntityDaoConfig.clearIdentityScope();
        uploadFileEntityDaoConfig.clearIdentityScope();
        uploadIDHubInfoEntityDaoConfig.clearIdentityScope();
        assetsModelDaoConfig.clearIdentityScope();
    }

    public IdHubMessageEntityDao getIdHubMessageEntityDao() {
        return idHubMessageEntityDao;
    }

    public TransactionRecordEntityDao getTransactionRecordEntityDao() {
        return transactionRecordEntityDao;
    }

    public UploadFileEntityDao getUploadFileEntityDao() {
        return uploadFileEntityDao;
    }

    public UploadIDHubInfoEntityDao getUploadIDHubInfoEntityDao() {
        return uploadIDHubInfoEntityDao;
    }

    public AssetsModelDao getAssetsModelDao() {
        return assetsModelDao;
    }

}
