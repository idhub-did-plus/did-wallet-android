package com.idhub.base.greendao.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;
import org.greenrobot.greendao.DaoException;
import com.idhub.base.greendao.db.DaoSession;
import com.idhub.base.greendao.db.AssetsContractAddressDao;
import com.idhub.base.greendao.db.AssetsModelDao;
import com.idhub.base.node.WalletNoteSharedPreferences;

@Entity
public class AssetsModel implements Parcelable {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String address;
    private String symbol;
    private String balance;
    private String decimals;
    private String type;

    @ToMany(referencedJoinProperty = "assetsId")
    private List<AssetsContractAddress> contractAddresses;
    @Transient
    public byte[] partition;


    public AssetsModel() {
    }


    public String getCurrentContractAddress(){
        String node = WalletNoteSharedPreferences.getInstance().getNode();
        String contractAddressStr = "";
        if (contractAddresses == null) {
            return contractAddressStr;
        }
        for (AssetsContractAddress contractAddress : contractAddresses) {
            if (node.equals(contractAddress.getNode())) {
                contractAddressStr = contractAddress.getContractAddress();
            }
        }
        return contractAddressStr;
    }

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 393511282)
    private transient AssetsModelDao myDao;

    protected AssetsModel(Parcel in) {
        if (in.readByte() == 0) {
            id = 0L;
        } else {
            id = in.readLong();
        }
        name = in.readString();
        address = in.readString();
        symbol = in.readString();
        balance = in.readString();
        decimals = in.readString();
        type = in.readString();
        contractAddresses = in.createTypedArrayList(AssetsContractAddress.CREATOR);
        partition = in.createByteArray();
    }

    @Generated(hash = 49857927)
    public AssetsModel(Long id, String name, String address, String symbol, String balance,
            String decimals, String type) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.symbol = symbol;
        this.balance = balance;
        this.decimals = decimals;
        this.type = type;
    }

    public static final Creator<AssetsModel> CREATOR = new Creator<AssetsModel>() {
        @Override
        public AssetsModel createFromParcel(Parcel in) {
            return new AssetsModel(in);
        }

        @Override
        public AssetsModel[] newArray(int size) {
            return new AssetsModel[size];
        }
    };

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getSymbol() {
        return this.symbol;
    }

    public void setSymbol(String symble) {
        this.symbol = symble;
    }

    public String getBalance() {
        return this.balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getDecimals() {
        return this.decimals;
    }

    public void setDecimals(String decimals) {
        this.decimals = decimals;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Long getId() {
        return this.id;
    }



    public void setId(Long id) {
        this.id = id;
    }



    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 646399115)
    public List<AssetsContractAddress> getContractAddresses() {
        if (contractAddresses == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AssetsContractAddressDao targetDao = daoSession
                    .getAssetsContractAddressDao();
            List<AssetsContractAddress> contractAddressesNew = targetDao
                    ._queryAssetsModel_ContractAddresses(id);
            synchronized (this) {
                if (contractAddresses == null) {
                    contractAddresses = contractAddressesNew;
                }
            }
        }
        return contractAddresses;
    }



    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 250142789)
    public synchronized void resetContractAddresses() {
        contractAddresses = null;
    }



    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }



    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }



    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }



    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 963086403)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAssetsModelDao() : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(symbol);
        dest.writeString(balance);
        dest.writeString(decimals);
        dest.writeString(type);
        dest.writeTypedList(contractAddresses);
        dest.writeByteArray(partition);
    }
}
