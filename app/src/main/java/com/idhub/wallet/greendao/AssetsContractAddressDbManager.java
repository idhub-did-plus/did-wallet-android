package com.idhub.wallet.greendao;


import com.idhub.base.App;
import com.idhub.base.greendao.db.AssetsContractAddressDao;
import com.idhub.base.greendao.db.AssetsModelDao;
import com.idhub.base.greendao.db.DaoSession;
import com.idhub.base.greendao.entity.AssetsContractAddress;
import com.idhub.base.greendao.entity.AssetsModel;

import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class AssetsContractAddressDbManager implements ModelDbManager<AssetsContractAddress> {


    @Override
    public void update(AssetsContractAddress assetsContractAddress) {

    }

    @Override
    public void insertData(AssetsContractAddress assetsContractAddress, AsyncOperationListener asyncOperationListener) {

    }


    public long insertDatasync(AssetsContractAddress assetsContractAddress) {
        AssetsContractAddressDao assetsContractAddressDao = App.getInstance().getmDaoSession().getAssetsContractAddressDao();
        return assetsContractAddressDao.insert(assetsContractAddress);
    }

    public void insertInTxDatasync(List<AssetsContractAddress> assetsContractAddress) {
        AssetsContractAddressDao assetsContractAddressDao = App.getInstance().getmDaoSession().getAssetsContractAddressDao();
        assetsContractAddressDao.insertInTx(assetsContractAddress);
    }

    public void deleteInTxDatasync(List<AssetsContractAddress> assetsContractAddress) {
        AssetsContractAddressDao assetsContractAddressDao = App.getInstance().getmDaoSession().getAssetsContractAddressDao();
        assetsContractAddressDao.deleteInTx(assetsContractAddress);
    }


    @Override
    public void insertListData(List<AssetsContractAddress> list, AsyncOperationListener listener) {

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
