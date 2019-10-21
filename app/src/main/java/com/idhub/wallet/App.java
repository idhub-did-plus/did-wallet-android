package com.idhub.wallet;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.multidex.MultiDex;


import com.dreame.apk_update.ApkUpdate;
import com.idhub.wallet.greendao.DbUpdateHelper;
import com.idhub.wallet.greendao.db.DaoMaster;
import com.idhub.wallet.greendao.db.DaoSession;
import com.idhub.wallet.utils.LocalUtils;
import com.tencent.bugly.crashreport.CrashReport;

public class App extends Application {
    private static App mApp;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        LocalUtils.setApplicationLanguage(this);
        ApkUpdate.initBugly(getApplicationContext());
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
