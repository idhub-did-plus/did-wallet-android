package com.idhub.wallet.greendao;

import android.content.Context;

import com.idhub.wallet.greendao.db.DaoMaster;

import org.greenrobot.greendao.database.Database;


public class DbUpdateHelper extends DaoMaster.DevOpenHelper {
    public DbUpdateHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
            case 2:
                break;
            default:
                super.onUpgrade(db, oldVersion, newVersion);
                break;
        }
    }
}
