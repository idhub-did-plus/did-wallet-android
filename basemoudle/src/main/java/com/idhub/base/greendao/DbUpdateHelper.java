package com.idhub.base.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.idhub.base.greendao.db.AssetsModelDao;
import com.idhub.base.greendao.db.DaoMaster;
import com.idhub.base.greendao.db.IdHubMessageEntityDao;
import com.idhub.base.greendao.db.TransactionRecordEntityDao;
import com.idhub.base.greendao.db.UploadFileEntityDao;
import com.idhub.base.greendao.db.UploadIDHubInfoEntityDao;

import org.greenrobot.greendao.database.Database;


public class DbUpdateHelper extends DaoMaster.DevOpenHelper {
    public DbUpdateHelper(Context context, String name) {
        super(context, name);
    }

    public DbUpdateHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    @SuppressWarnings("all")
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        Log.e("MyGreenDaoDbHelper", "----"+oldVersion + "---先前和更新之后的版本---" + newVersion+"----");
        if (oldVersion < newVersion) {
            Log.e("MyGreenDaoDbHelper","进行数据库升级");
            new GreenDaoCompatibleUpdateHelper()
                    .setCallBack(
                            new GreenDaoCompatibleUpdateHelper.GreenDaoCompatibleUpdateCallBack() {
                                @Override
                                public void onFinalSuccess() {
                                    Log.e("MyGreenDaoDbHelper","进行数据库升级 ===> 成功");
                                }

                                @Override
                                public void onFailedLog(String errorMsg) {
                                    Log.e("MyGreenDaoDbHelper","升级失败日志 ===> "+errorMsg);
                                }
                            }
                    )
                    .compatibleUpdate(
                            db,
                            AssetsModelDao.class,
                            IdHubMessageEntityDao.class,
                            TransactionRecordEntityDao.class,
                            UploadFileEntityDao.class,
                            UploadIDHubInfoEntityDao.class
                    );
            Log.e("MyGreenDaoDbHelper","进行数据库升级--完成");
        }
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        // 不要调用父类的，它默认是先删除全部表再创建
        // super.onUpgrade(db, oldVersion, newVersion);

    }
//    @Override
//    public void onUpgrade(Database db, int oldVersion, int newVersion) {
//        switch (oldVersion) {
//            case 1:
//            case 2:
//                break;
//            default:
//                super.onUpgrade(db, oldVersion, newVersion);
//                break;
//        }
//    }
}
