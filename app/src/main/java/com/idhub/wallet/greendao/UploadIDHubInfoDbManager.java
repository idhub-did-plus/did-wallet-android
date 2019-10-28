package com.idhub.wallet.greendao;

import com.idhub.base.greendao.db.DaoSession;
import com.idhub.base.greendao.db.UploadIDHubInfoEntityDao;
import com.idhub.base.App;
import com.idhub.base.greendao.entity.UploadIDHubInfoEntity;
import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

public class UploadIDHubInfoDbManager implements ModelDbManager<UploadIDHubInfoEntity> {


    @Override
    public void insertData(UploadIDHubInfoEntity uploadIDHubInfoEntity, AsyncOperationListener asyncOperationListener) {
        AsyncSession asyncSession = App.getInstance().getmDaoSession().startAsyncSession();
        asyncSession.setListenerMainThread(asyncOperationListener);
        asyncSession.insertOrReplace(uploadIDHubInfoEntity);
    }

    @Override
    public void insertListData(List<UploadIDHubInfoEntity> list, AsyncOperationListener listener) {

    }

    @Override
    public void queryById(long id, AsyncOperationListener listener) {
        DaoSession daoSession = App.getInstance().getmDaoSession();
        AsyncSession asyncSession = daoSession.startAsyncSession();
        asyncSession.setListenerMainThread(listener);
        WhereCondition eq = UploadIDHubInfoEntityDao.Properties.Id.eq(id);
        Query<UploadIDHubInfoEntity> build = daoSession.queryBuilder(UploadIDHubInfoEntity.class).where(eq).build();
        asyncSession.queryUnique(build);
    }

    @Override
    public void queryByName(String name, AsyncOperationListener listener) {

    }

    @Override
    public void queryAll(AsyncOperationListener listener) {

    }

    @Override
    public void update(UploadIDHubInfoEntity uploadIDHubInfoEntity) {
        DaoSession daoSession = App.getInstance().getmDaoSession();
        AsyncSession asyncSession = daoSession.startAsyncSession();
        asyncSession.setListenerMainThread(null);
        asyncSession.update(uploadIDHubInfoEntity);
    }

}
