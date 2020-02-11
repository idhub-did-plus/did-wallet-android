package com.idhub.wallet.greendao;


import com.idhub.base.App;
import com.idhub.base.greendao.db.AssetsContractAddressDao;
import com.idhub.base.greendao.db.AssetsModelDao;
import com.idhub.base.greendao.db.DaoSession;
import com.idhub.base.greendao.db.IdentityEntityDao;
import com.idhub.base.greendao.entity.AssetsContractAddress;
import com.idhub.base.greendao.entity.AssetsModel;
import com.idhub.base.greendao.entity.IdentityEntity;
import com.idhub.base.node.WalletNodeManager;
import com.idhub.base.node.WalletNoteSharedPreferences;

import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;
import org.web3j.crypto.Keys;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class IdentityDbManager implements ModelDbManager<IdentityEntity> {

    public String getEIN(String identityAddress) {
        String address = Keys.toChecksumAddress(identityAddress);
        String node = WalletNoteSharedPreferences.getInstance().getNode();
        IdentityEntityDao identityEntityDao = App.getInstance().getmDaoSession().getIdentityEntityDao();
        QueryBuilder<IdentityEntity> queryBuilder = identityEntityDao.queryBuilder();
        IdentityEntity identityEntity = queryBuilder.where(queryBuilder.and(IdentityEntityDao.Properties.IdentityAddress.eq(address), IdentityEntityDao.Properties.Node.eq(node))).build().unique();
        String ein = "";
        if (identityEntity != null) {
            ein = identityEntity.getEIN();
        }
        return ein;
    }

    public IdentityEntity searchIdentity(String identityAddress) {
        String address = Keys.toChecksumAddress(identityAddress);
        String node = WalletNoteSharedPreferences.getInstance().getNode();
        IdentityEntityDao identityEntityDao = App.getInstance().getmDaoSession().getIdentityEntityDao();
        QueryBuilder<IdentityEntity> queryBuilder = identityEntityDao.queryBuilder();
        IdentityEntity identityEntity = queryBuilder.where(queryBuilder.and(IdentityEntityDao.Properties.IdentityAddress.eq(address), IdentityEntityDao.Properties.Node.eq(node))).build().unique();
        return identityEntity;
    }

    public String getRecoveryAddress(String identityAddress) {
        String address = Keys.toChecksumAddress(identityAddress);
        String node = WalletNoteSharedPreferences.getInstance().getNode();
        IdentityEntityDao identityEntityDao = App.getInstance().getmDaoSession().getIdentityEntityDao();
        QueryBuilder<IdentityEntity> queryBuilder = identityEntityDao.queryBuilder();
        IdentityEntity identityEntity = queryBuilder.where(queryBuilder.and(IdentityEntityDao.Properties.IdentityAddress.eq(address), IdentityEntityDao.Properties.Node.eq(node))).build().unique();
        String recoveryAddress = "";
        if (identityEntity != null) {
            recoveryAddress = identityEntity.getRecoveryAddress();
        }
        return recoveryAddress;
    }

    public String getDefaultAddress() {
        String node = WalletNoteSharedPreferences.getInstance().getNode();
        IdentityEntityDao identityEntityDao = App.getInstance().getmDaoSession().getIdentityEntityDao();
        QueryBuilder<IdentityEntity> queryBuilder = identityEntityDao.queryBuilder();
        IdentityEntity identityEntity = queryBuilder.where(queryBuilder.and(IdentityEntityDao.Properties.IsDefaultAddress.eq(true), IdentityEntityDao.Properties.Node.eq(node))).build().unique();
        String defaultAddress = "";
        if (identityEntity != null) {
            defaultAddress = identityEntity.getIdentityAddress();
        }
        return defaultAddress;
    }

    public IdentityEntity getDefaultIdentity() {
        String node = WalletNoteSharedPreferences.getInstance().getNode();
        IdentityEntityDao identityEntityDao = App.getInstance().getmDaoSession().getIdentityEntityDao();
        QueryBuilder<IdentityEntity> queryBuilder = identityEntityDao.queryBuilder();
        IdentityEntity identityEntity = queryBuilder.where(queryBuilder.and(IdentityEntityDao.Properties.IsDefaultAddress.eq(true), IdentityEntityDao.Properties.Node.eq(node))).build().unique();
        return identityEntity;
    }

    @Override
    public void update(IdentityEntity identityEntity) {
        IdentityEntityDao identityEntityDao = App.getInstance().getmDaoSession().getIdentityEntityDao();
        identityEntityDao.update(identityEntity);
    }

    @Override
    public void insertData(IdentityEntity identityEntity, AsyncOperationListener asyncOperationListener) {
        AsyncSession asyncSession = App.getInstance().getmDaoSession().startAsyncSession();
        asyncSession.setListenerMainThread(asyncOperationListener);
        asyncSession.insertOrReplace(identityEntity);
    }


    public long insertDatasync(IdentityEntity identityEntity) {
        IdentityEntityDao identityEntityDao = App.getInstance().getmDaoSession().getIdentityEntityDao();
        return identityEntityDao.insertOrReplace(identityEntity);
    }


    @Override
    public void insertListData(List<IdentityEntity> list, AsyncOperationListener listener) {
        if (list != null && list.size() > 0) {
            AsyncSession asyncSession = App.getInstance().getmDaoSession().startAsyncSession();
            asyncSession.setListenerMainThread(listener);
            asyncSession.insertInTx(IdentityEntity.class, list);
        }
    }

    @Override
    public void queryById(long id, AsyncOperationListener listener) {
    }

    @Override
    public void queryByName(String key, AsyncOperationListener listener) {

    }

    @Override
    public void queryAll(AsyncOperationListener listener) {

    }

}
