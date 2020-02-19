package com.idhub.wallet.greendao;


import com.idhub.base.greendao.db.AssetsContractAddressDao;
import com.idhub.base.greendao.db.AssetsModelDao;
import com.idhub.base.greendao.db.DaoSession;
import com.idhub.base.App;
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

public class AssetsModelDbManager implements ModelDbManager<AssetsModel> {

    public List<AssetsModel> querySTAssets() {
        DaoSession daoSession = App.getInstance().getmDaoSession();
        WhereCondition eq = AssetsModelDao.Properties.Type.eq(TransactionTokenType.ERC1400);
        List<AssetsModel> assetsModels = daoSession.queryBuilder(AssetsModel.class).where(eq).build().list();
        return assetsModels;
    }

    public void queryAll(AsyncOperationListener listener) {
        Observable.create((ObservableOnSubscribe<AssetsModel>) emitter -> {
            DaoSession daoSession = App.getInstance().getmDaoSession();
            WhereCondition eq = AssetsModelDao.Properties.Name.eq(TransactionTokenType.ETH_NAME);
            AssetsModel unique = daoSession.queryBuilder(AssetsModel.class).where(eq).unique();
            if (unique == null) {
                AssetsModel assetsModel = new AssetsModel();
                assetsModel.setType(TransactionTokenType.ETH_NAME);
                assetsModel.setName(TransactionTokenType.ETH_NAME);
                assetsModel.setDecimals("18");
                assetsModel.setSymbol(TransactionTokenType.ETH_NAME);
                daoSession.insert(assetsModel);
            }

            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<AssetsModel>() {
                    @Override
                    public void onNext(AssetsModel assetsModel) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        DaoSession daoSession = App.getInstance().getmDaoSession();
                        AsyncSession asyncSession = daoSession.startAsyncSession();
                        asyncSession.setListenerMainThread(listener);
                        asyncSession.loadAll(AssetsModel.class);
                    }
                });
    }

    @Override
    public void update(AssetsModel assetsModel) {

    }

    @Override
    public void insertData(AssetsModel assetsModel, AsyncOperationListener asyncOperationListener) {
        AsyncSession asyncSession = App.getInstance().getmDaoSession().startAsyncSession();
        asyncSession.setListenerMainThread(asyncOperationListener);
        asyncSession.insertOrReplace(assetsModel);
    }


    public long insertDatasync(AssetsModel assetsModel) {
        AssetsModelDao assetsModelDao = App.getInstance().getmDaoSession().getAssetsModelDao();
        return assetsModelDao.insertOrReplace(assetsModel);
    }

    public void deleteDataSync(AssetsModel assetsModel) {
        AssetsModelDao assetsModelDao = App.getInstance().getmDaoSession().getAssetsModelDao();
        assetsModelDao.delete(assetsModel);
    }


    public void deleteData(AssetsModel assetsModel, AsyncOperationListener asyncOperationListener) {
//        assetsModel.delete();
        AsyncSession asyncSession = App.getInstance().getmDaoSession().startAsyncSession();
        asyncSession.setListenerMainThread(asyncOperationListener);
        asyncSession.delete(assetsModel);
    }

    @Override
    public void insertListData(List<AssetsModel> list, AsyncOperationListener listener) {
        if (list != null && list.size() > 0) {
            AsyncSession asyncSession = App.getInstance().getmDaoSession().startAsyncSession();
            asyncSession.setListenerMainThread(listener);
            asyncSession.insertInTx(AssetsModel.class, list);
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
        WhereCondition eq = AssetsModelDao.Properties.Name.eq(key);
        Query<AssetsModel> build = daoSession.queryBuilder(AssetsModel.class).where(eq).build();
        asyncSession.queryUnique(build);
    }

    public void queryCurrentNodeContractAddressKey(String key, AsyncOperationListener listener) {
        DaoSession daoSession = App.getInstance().getmDaoSession();
        AsyncSession asyncSession = daoSession.startAsyncSession();
        asyncSession.setListenerMainThread(listener);

        QueryBuilder<AssetsModel> assetsModelQueryBuilder = daoSession.queryBuilder(AssetsModel.class);
        assetsModelQueryBuilder.join(AssetsContractAddress.class, AssetsContractAddressDao.Properties.AssetsId)
                .where(AssetsContractAddressDao.Properties.ContractAddress.eq(key));

        asyncSession.queryList(assetsModelQueryBuilder.build());
    }

//    public void queryByRopstenContractAddressKey(String key, AsyncOperationListener listener) {
//        DaoSession daoSession = App.getInstance().getmDaoSession();
//        AsyncSession asyncSession = daoSession.startAsyncSession();
//        asyncSession.setListenerMainThread(listener);
//        WhereCondition eq = AssetsModelDao.Properties.RopstenContractAddress.eq(key);
//        Query<AssetsModel> build = daoSession.queryBuilder(AssetsModel.class).where(eq).build();
//        asyncSession.queryList(build);
//    }

//    public AssetsModel queryByMainContractAddressKeysync(String key) {
//        AssetsModelDao assetsModelDao = App.getInstance().getmDaoSession().getAssetsModelDao();
//        WhereCondition eq = AssetsModelDao.Properties.MainContractAddress.eq(key);
//        return assetsModelDao.queryBuilder().where(eq).build().unique();
//    }
//
//    public AssetsModel queryByRopstenContractAddressKeysync(String key) {
//        AssetsModelDao assetsModelDao = App.getInstance().getmDaoSession().getAssetsModelDao();
//        WhereCondition eq = AssetsModelDao.Properties.RopstenContractAddress.eq(key);
//        return assetsModelDao.queryBuilder().where(eq).build().unique();
//    }
}
