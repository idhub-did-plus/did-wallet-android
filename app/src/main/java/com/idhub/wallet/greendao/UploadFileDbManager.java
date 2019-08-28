package com.idhub.wallet.greendao;

import com.idhub.wallet.App;
import com.idhub.wallet.greendao.db.AssetsModelDao;
import com.idhub.wallet.greendao.db.DaoSession;
import com.idhub.wallet.greendao.db.UploadFileEntityDao;
import com.idhub.wallet.greendao.entity.AssetsModel;
import com.idhub.wallet.me.information.entity.UploadFileEntity;

import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

public class UploadFileDbManager implements ModelDbManager<UploadFileEntity> {

    @Override
    public void insertData(UploadFileEntity uploadFileEntity, AsyncOperationListener asyncOperationListener) {
        AsyncSession asyncSession = App.getInstance().getmDaoSession().startAsyncSession();
        asyncSession.setListenerMainThread(asyncOperationListener);
        asyncSession.insertOrReplace(uploadFileEntity);
    }

    @Override
    public void insertListData(List<UploadFileEntity> list, AsyncOperationListener listener) {
        if (list != null && list.size() > 0) {
            AsyncSession asyncSession = App.getInstance().getmDaoSession().startAsyncSession();
            asyncSession.setListenerMainThread(listener);
            asyncSession.insertInTx(UploadFileEntity.class, list);
        }
    }

    public void deleteByType(String type){
        DaoSession daoSession = App.getInstance().getmDaoSession();
        UploadFileEntityDao uploadFileEntityDao = daoSession.getUploadFileEntityDao();
        UploadFileEntity uploadFileEntity = uploadFileEntityDao.queryBuilder().where(UploadFileEntityDao.Properties.Type.eq(type)).build().unique();
        if(uploadFileEntity != null){
            uploadFileEntityDao.deleteByKey(uploadFileEntity.getId());
        }
    }

    @Override
    public void queryById(long id, AsyncOperationListener listener) {

    }

    @Override
    public void queryByName(String key, AsyncOperationListener listener) {
        DaoSession daoSession = App.getInstance().getmDaoSession();
        AsyncSession asyncSession = daoSession.startAsyncSession();
        asyncSession.setListenerMainThread(listener);
        WhereCondition eq = UploadFileEntityDao.Properties.Name.eq(key);
        Query<UploadFileEntity> build = daoSession.queryBuilder(UploadFileEntity.class).where(eq).build();
        asyncSession.queryUnique(build);
    }

    public void queryByType(String key, AsyncOperationListener listener) {
        DaoSession daoSession = App.getInstance().getmDaoSession();
        AsyncSession asyncSession = daoSession.startAsyncSession();
        asyncSession.setListenerMainThread(listener);
        WhereCondition eq = UploadFileEntityDao.Properties.Type.eq(key);
        Query<UploadFileEntity> build = daoSession.queryBuilder(UploadFileEntity.class).where(eq).build();
        asyncSession.queryUnique(build);
    }

    @Override
    public void queryAll(AsyncOperationListener listener) {
        DaoSession daoSession = App.getInstance().getmDaoSession();
        AsyncSession asyncSession = daoSession.startAsyncSession();
        asyncSession.setListenerMainThread(listener);
        asyncSession.loadAll(UploadFileEntity.class);
    }
}
