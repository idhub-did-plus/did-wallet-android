package com.idhub.wallet.greendao;

import com.idhub.wallet.App;
import com.idhub.wallet.greendao.db.DaoSession;
import com.idhub.wallet.greendao.entity.IdHubMessageEntity;

import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;

import java.util.List;

public class IdHubMessageDbManager implements ModelDbManager<IdHubMessageEntity> {

    @Override
    public void insertData(IdHubMessageEntity idHubMessageEntity, AsyncOperationListener asyncOperationListener) {
        AsyncSession asyncSession = App.getInstance().getmDaoSession().startAsyncSession();
        asyncSession.setListenerMainThread(asyncOperationListener);
        asyncSession.insertOrReplace(idHubMessageEntity);
    }

    @Override
    public void insertListData(List<IdHubMessageEntity> list, AsyncOperationListener listener) {

    }

    @Override
    public void queryById(long id, AsyncOperationListener listener) {

    }

    @Override
    public void queryByName(String name, AsyncOperationListener listener) {

    }

    @Override
    public void queryAll(AsyncOperationListener listener) {
        DaoSession daoSession = App.getInstance().getmDaoSession();
        AsyncSession asyncSession = daoSession.startAsyncSession();
        asyncSession.setListenerMainThread(listener);
        asyncSession.loadAll(IdHubMessageEntity.class);
    }

    @Override
    public void update(IdHubMessageEntity idHubMessageEntity) {

    }
}
