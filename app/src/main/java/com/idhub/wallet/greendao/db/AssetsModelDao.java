package com.idhub.wallet.greendao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.idhub.wallet.greendao.entity.AssetsModel;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ASSETS_MODEL".
*/
public class AssetsModelDao extends AbstractDao<AssetsModel, Long> {

    public static final String TABLENAME = "ASSETS_MODEL";

    /**
     * Properties of entity AssetsModel.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Address = new Property(2, String.class, "address", false, "ADDRESS");
        public final static Property Token = new Property(3, String.class, "token", false, "TOKEN");
        public final static Property Symble = new Property(4, String.class, "symble", false, "SYMBLE");
        public final static Property Balance = new Property(5, String.class, "balance", false, "BALANCE");
    }


    public AssetsModelDao(DaoConfig config) {
        super(config);
    }
    
    public AssetsModelDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ASSETS_MODEL\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"NAME\" TEXT," + // 1: name
                "\"ADDRESS\" TEXT," + // 2: address
                "\"TOKEN\" TEXT," + // 3: token
                "\"SYMBLE\" TEXT," + // 4: symble
                "\"BALANCE\" TEXT);"); // 5: balance
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ASSETS_MODEL\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AssetsModel entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(3, address);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(4, token);
        }
 
        String symble = entity.getSymble();
        if (symble != null) {
            stmt.bindString(5, symble);
        }
 
        String balance = entity.getBalance();
        if (balance != null) {
            stmt.bindString(6, balance);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AssetsModel entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(3, address);
        }
 
        String token = entity.getToken();
        if (token != null) {
            stmt.bindString(4, token);
        }
 
        String symble = entity.getSymble();
        if (symble != null) {
            stmt.bindString(5, symble);
        }
 
        String balance = entity.getBalance();
        if (balance != null) {
            stmt.bindString(6, balance);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public AssetsModel readEntity(Cursor cursor, int offset) {
        AssetsModel entity = new AssetsModel( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // address
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // token
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // symble
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // balance
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AssetsModel entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAddress(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setToken(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setSymble(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setBalance(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AssetsModel entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AssetsModel entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AssetsModel entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
