package com.idhub.wallet.greendao.db;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.idhub.wallet.greendao.entity.AssetsModel;
import com.idhub.wallet.greendao.entity.TransactionRecordEntity;
import com.idhub.wallet.greendao.entity.UploadFileEntity;
import com.idhub.wallet.greendao.entity.UploadIDHubInfoEntity;
import com.idhub.wallet.greendao.entity.IdHubMessageEntity;

import com.idhub.wallet.greendao.db.AssetsModelDao;
import com.idhub.wallet.greendao.db.TransactionRecordEntityDao;
import com.idhub.wallet.greendao.db.UploadFileEntityDao;
import com.idhub.wallet.greendao.db.UploadIDHubInfoEntityDao;
import com.idhub.wallet.greendao.db.IdHubMessageEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig assetsModelDaoConfig;
    private final DaoConfig transactionRecordEntityDaoConfig;
    private final DaoConfig uploadFileEntityDaoConfig;
    private final DaoConfig uploadIDHubInfoEntityDaoConfig;
    private final DaoConfig idHubMessageEntityDaoConfig;

    private final AssetsModelDao assetsModelDao;
    private final TransactionRecordEntityDao transactionRecordEntityDao;
    private final UploadFileEntityDao uploadFileEntityDao;
    private final UploadIDHubInfoEntityDao uploadIDHubInfoEntityDao;
    private final IdHubMessageEntityDao idHubMessageEntityDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        assetsModelDaoConfig = daoConfigMap.get(AssetsModelDao.class).clone();
        assetsModelDaoConfig.initIdentityScope(type);

        transactionRecordEntityDaoConfig = daoConfigMap.get(TransactionRecordEntityDao.class).clone();
        transactionRecordEntityDaoConfig.initIdentityScope(type);

        uploadFileEntityDaoConfig = daoConfigMap.get(UploadFileEntityDao.class).clone();
        uploadFileEntityDaoConfig.initIdentityScope(type);

        uploadIDHubInfoEntityDaoConfig = daoConfigMap.get(UploadIDHubInfoEntityDao.class).clone();
        uploadIDHubInfoEntityDaoConfig.initIdentityScope(type);

        idHubMessageEntityDaoConfig = daoConfigMap.get(IdHubMessageEntityDao.class).clone();
        idHubMessageEntityDaoConfig.initIdentityScope(type);

        assetsModelDao = new AssetsModelDao(assetsModelDaoConfig, this);
        transactionRecordEntityDao = new TransactionRecordEntityDao(transactionRecordEntityDaoConfig, this);
        uploadFileEntityDao = new UploadFileEntityDao(uploadFileEntityDaoConfig, this);
        uploadIDHubInfoEntityDao = new UploadIDHubInfoEntityDao(uploadIDHubInfoEntityDaoConfig, this);
        idHubMessageEntityDao = new IdHubMessageEntityDao(idHubMessageEntityDaoConfig, this);

        registerDao(AssetsModel.class, assetsModelDao);
        registerDao(TransactionRecordEntity.class, transactionRecordEntityDao);
        registerDao(UploadFileEntity.class, uploadFileEntityDao);
        registerDao(UploadIDHubInfoEntity.class, uploadIDHubInfoEntityDao);
        registerDao(IdHubMessageEntity.class, idHubMessageEntityDao);
    }
    
    public void clear() {
        assetsModelDaoConfig.clearIdentityScope();
        transactionRecordEntityDaoConfig.clearIdentityScope();
        uploadFileEntityDaoConfig.clearIdentityScope();
        uploadIDHubInfoEntityDaoConfig.clearIdentityScope();
        idHubMessageEntityDaoConfig.clearIdentityScope();
    }

    public AssetsModelDao getAssetsModelDao() {
        return assetsModelDao;
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

    public IdHubMessageEntityDao getIdHubMessageEntityDao() {
        return idHubMessageEntityDao;
    }

}
