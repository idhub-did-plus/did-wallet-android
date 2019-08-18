package com.idhub.wallet.greendao;

import android.util.Log;

import com.idhub.wallet.App;
import com.idhub.wallet.greendao.db.AssetsModelDao;
import com.idhub.wallet.greendao.db.DaoSession;
import com.idhub.wallet.greendao.entity.AssetsModel;
import com.idhub.wallet.utils.ToastUtils;

import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class AssetsModelDbManager {

    public static void insertData(AssetsModel assetsModel, AsyncOperationListener operationListener) {
        AsyncSession asyncSession = App.getInstance().getmDaoSession().startAsyncSession();
        asyncSession.setListenerMainThread(operationListener);
        asyncSession.insertOrReplace(assetsModel);
    }

    /**
     * @desc 将数据实体通过事务添加至数据库
     **/
    public static void insertListData(List<AssetsModel> list, AsyncOperationListener listener) {
        if (list != null && list.size() > 0) {
            AsyncSession asyncSession = App.getInstance().getmDaoSession().startAsyncSession();
            asyncSession.setListenerMainThread(listener);
            asyncSession.insertInTx(AssetsModel.class, list);
        }
    }

    public static void queryById(long id, AsyncOperationListener listener) {
        DaoSession daoSession = App.getInstance().getmDaoSession();
        AsyncSession asyncSession = daoSession.startAsyncSession();
        asyncSession.setListenerMainThread(listener);
        WhereCondition eq = AssetsModelDao.Properties.Id.eq(id);
        Query<AssetsModel> build = daoSession.queryBuilder(AssetsModel.class).where(eq).build();
        asyncSession.queryUnique(build);

    }

    public static void queryByName(String name, AsyncOperationListener listener) {
        DaoSession daoSession = App.getInstance().getmDaoSession();
        AsyncSession asyncSession = daoSession.startAsyncSession();
        asyncSession.setListenerMainThread(listener);
        WhereCondition eq = AssetsModelDao.Properties.Name.eq(name);
        Query<AssetsModel> build = daoSession.queryBuilder(AssetsModel.class).where(eq).build();
        asyncSession.queryUnique(build);
    }

    public static void queryAll(AsyncOperationListener listener) {
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
}
