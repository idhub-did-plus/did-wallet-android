package com.idhub.wallet;

import android.app.Application;
import android.support.multidex.MultiDex;

public class App extends Application {
    private static App mApp;
    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        MultiDex.install(this);
    }

    public static App getInstance(){
        return mApp;
    }
}
