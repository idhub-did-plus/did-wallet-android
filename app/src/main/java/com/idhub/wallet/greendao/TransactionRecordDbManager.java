package com.idhub.wallet.greendao;

import com.idhub.wallet.App;
import com.idhub.wallet.greendao.db.AssetsModelDao;
import com.idhub.wallet.greendao.db.DaoSession;
import com.idhub.wallet.greendao.db.TransactionRecordEntityDao;
import com.idhub.wallet.greendao.entity.AssetsModel;
import com.idhub.wallet.greendao.entity.TransactionRecordEntity;

import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * 数据库存储交易数据50条
 * 先查询数据库已有数据个数+ 需要存储数据 <= 50 直接存
 * >50  判断需要存储数据个数 >50 删除数据库所有数据 截取50条存入。  <=50 删除数据库的数据个数 （= 需要存储个数） 然后存储。
 */
public class TransactionRecordDbManager implements ModelDbManager<TransactionRecordEntity> {

    public void insertListDataTo50Datas(List<TransactionRecordEntity> datas){
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                DaoSession daoSession = App.getInstance().getmDaoSession();
                TransactionRecordEntityDao transactionRecordEntityDao = daoSession.getTransactionRecordEntityDao();
                List<TransactionRecordEntity> transactionRecordEntities = transactionRecordEntityDao.loadAll();
                int datasSize = datas.size();
                int total = transactionRecordEntities.size() + datasSize;
                if (total <= 50) {
                    transactionRecordEntityDao.insertInTx(datas);
                } else {
                    if (datasSize >= 50) {
                        transactionRecordEntityDao.deleteAll();
                        List<TransactionRecordEntity> entities = datas.subList(datasSize - 50, datasSize);
                        transactionRecordEntityDao.insertInTx(entities);
                    } else {
                        for (int i = 0; i < datasSize; i++) {
                            transactionRecordEntityDao.delete(transactionRecordEntities.get(i));
                        }
                        transactionRecordEntityDao.insertInTx(datas);
                    }
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void insertData(TransactionRecordEntity transactionRecordEntity, AsyncOperationListener asyncOperationListener) {

    }

    @Override
    public void insertListData(List<TransactionRecordEntity> list, AsyncOperationListener listener) {

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
        Query<TransactionRecordEntity> build = daoSession.queryBuilder(TransactionRecordEntity.class).orderDesc(TransactionRecordEntityDao.Properties.TimeStamp).build();
        asyncSession.queryList(build);
    }

    @Override
    public void update(TransactionRecordEntity transactionRecordEntity) {

    }

}
