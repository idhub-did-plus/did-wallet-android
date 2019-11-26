package com.idhub.base.greendao.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.idhub.base.node.AssetsModel;

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
        public final static Property MainContractAddress = new Property(3, String.class, "mainContractAddress", false, "MAIN_CONTRACT_ADDRESS");
        public final static Property RopstenContractAddress = new Property(4, String.class, "ropstenContractAddress", false, "ROPSTEN_CONTRACT_ADDRESS");
        public final static Property Symbol = new Property(5, String.class, "symbol", false, "SYMBOL");
        public final static Property Balance = new Property(6, String.class, "balance", false, "BALANCE");
        public final static Property Decimals = new Property(7, String.class, "decimals", false, "DECIMALS");
        public final static Property Type = new Property(8, String.class, "type", false, "TYPE");
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
                "\"MAIN_CONTRACT_ADDRESS\" TEXT," + // 3: mainContractAddress
                "\"ROPSTEN_CONTRACT_ADDRESS\" TEXT," + // 4: ropstenContractAddress
                "\"SYMBOL\" TEXT," + // 5: symbol
                "\"BALANCE\" TEXT," + // 6: balance
                "\"DECIMALS\" TEXT," + // 7: decimals
                "\"TYPE\" TEXT);"); // 8: type
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
 
        String mainContractAddress = entity.getMainContractAddress();
        if (mainContractAddress != null) {
            stmt.bindString(4, mainContractAddress);
        }
 
        String ropstenContractAddress = entity.getRopstenContractAddress();
        if (ropstenContractAddress != null) {
            stmt.bindString(5, ropstenContractAddress);
        }
 
        String symbol = entity.getSymbol();
        if (symbol != null) {
            stmt.bindString(6, symbol);
        }
 
        String balance = entity.getBalance();
        if (balance != null) {
            stmt.bindString(7, balance);
        }
 
        String decimals = entity.getDecimals();
        if (decimals != null) {
            stmt.bindString(8, decimals);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(9, type);
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
 
        String mainContractAddress = entity.getMainContractAddress();
        if (mainContractAddress != null) {
            stmt.bindString(4, mainContractAddress);
        }
 
        String ropstenContractAddress = entity.getRopstenContractAddress();
        if (ropstenContractAddress != null) {
            stmt.bindString(5, ropstenContractAddress);
        }
 
        String symbol = entity.getSymbol();
        if (symbol != null) {
            stmt.bindString(6, symbol);
        }
 
        String balance = entity.getBalance();
        if (balance != null) {
            stmt.bindString(7, balance);
        }
 
        String decimals = entity.getDecimals();
        if (decimals != null) {
            stmt.bindString(8, decimals);
        }
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(9, type);
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
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // mainContractAddress
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // ropstenContractAddress
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // symbol
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // balance
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // decimals
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8) // type
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AssetsModel entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAddress(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMainContractAddress(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setRopstenContractAddress(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setSymbol(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setBalance(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setDecimals(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setType(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
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
