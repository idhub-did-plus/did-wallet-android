package com.idhub.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;

import androidx.multidex.MultiDex;

import com.idhub.base.greendao.DbUpdateHelper;
import com.idhub.base.launage.LocalUtils;
import com.idhub.base.greendao.db.DaoMaster;
import com.idhub.base.greendao.db.DaoSession;

public class App extends Application {
    private static App mApp;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        LocalUtils.setApplicationLanguage(this);
        initGreenDao();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 当切换横竖屏是 重置语言
        LocalUtils.setApplicationLanguage(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
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
