package com.idhub.wallet;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;

import com.idhub.wallet.greendao.DbUpdateHelper;
import com.idhub.wallet.greendao.db.DaoMaster;
import com.idhub.wallet.greendao.db.DaoSession;

public class App extends Application {
    private static App mApp;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initGreenDao();
        MultiDex.install(this);
    }

    private void initGreenDao() {
        DbUpdateHelper helper = new DbUpdateHelper(this, "idhub_db");
        SQLiteDatabase writableDatabase = helper.getWritableDatabase();
        mDaoSession = new DaoMaster(writableDatabase).newSession();
    }

    public static App getInstance() {
        return mApp;
    }

    public DaoSession getmDaoSession() {
        return mDaoSession;
    }

}
