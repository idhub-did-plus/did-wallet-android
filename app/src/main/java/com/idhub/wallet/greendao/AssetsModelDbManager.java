package com.idhub.wallet.greendao;


import com.idhub.wallet.App;
import com.idhub.wallet.greendao.db.AssetsModelDao;
import com.idhub.wallet.greendao.db.DaoSession;
import com.idhub.wallet.greendao.entity.AssetsModel;
import com.idhub.wallet.utils.ToastUtils;

import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class AssetsModelDbManager implements ModelDbManager<AssetsModel> {
    
    public void queryAll(AsyncOperationListener listener) {
        Observable.create((ObservableOnSubscribe<AssetsModel>) emitter -> {
            DaoSession daoSession = App.getInstance().getmDaoSession();
            WhereCondition eq = AssetsModelDao.Properties.Name.eq(AssetsDefaultType.ETH_NAME);
            AssetsModel unique = daoSession.queryBuilder(AssetsModel.class).where(eq).unique();
            if (unique == null) {
                AssetsModel assetsModel = new AssetsModel();
                assetsModel.setName(AssetsDefaultType.ETH_NAME);
                daoSession.insert(assetsModel);
            }
            WhereCondition idhubWhere = AssetsModelDao.Properties.Name.eq(AssetsDefaultType.IDHUB_NAME);
            AssetsModel idhub = daoSession.queryBuilder(AssetsModel.class).where(idhubWhere).unique();
            if (idhub == null) {
                AssetsModel assetsModel = new AssetsModel();
                assetsModel.setName(AssetsDefaultType.IDHUB_NAME);
                assetsModel.setToken("0x4ede434043c47e9774cd7d28a040c28dd757ddfa");
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
                        ToastUtils.showShortToast("初始化资产失败");
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
        DaoSession daoSession = App.getInstance().getmDaoSession();
        AsyncSession asyncSession = daoSession.startAsyncSession();
        asyncSession.setListenerMainThread(listener);
        WhereCondition eq = AssetsModelDao.Properties.Id.eq(id);
        Query<AssetsModel> build = daoSession.queryBuilder(AssetsModel.class).where(eq).build();
        asyncSession.queryUnique(build);
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
}
