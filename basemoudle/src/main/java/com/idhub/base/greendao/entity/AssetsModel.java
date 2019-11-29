package com.idhub.base.greendao.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

@Entity
public class AssetsModel implements Parcelable {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String address;
    private String mainContractAddress;
    private String ropstenContractAddress;
    private String symbol;
    private String balance;
    private String decimals;
    private String type;
    @Transient
    public byte[] partition;


    public AssetsModel() {
    }
    


    protected AssetsModel(Parcel in) {
        id = in.readLong();
        name = in.readString();
        address = in.readString();
        mainContractAddress = in.readString();
        ropstenContractAddress = in.readString();
        symbol = in.readString();
        balance = in.readString();
        decimals = in.readString();
        type = in.readString();
        partition = in.createByteArray();
    }



    @Generated(hash = 201016777)
    public AssetsModel(Long id, String name, String address,
            String mainContractAddress, String ropstenContractAddress,
            String symbol, String balance, String decimals, String type) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mainContractAddress = mainContractAddress;
        this.ropstenContractAddress = ropstenContractAddress;
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

    public String getRopstenContractAddress() {
        return this.ropstenContractAddress;
    }

    public void setRopstenContractAddress(String ropstenContractAddress) {
        this.ropstenContractAddress = ropstenContractAddress;
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

    public String getMainContractAddress() {
        return this.mainContractAddress;
    }

    public void setMainContractAddress(String mainContractAddress) {
        this.mainContractAddress = mainContractAddress;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong((id == null ? 0l : id));
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(mainContractAddress);
        dest.writeString(ropstenContractAddress);
        dest.writeString(symbol);
        dest.writeString(balance);
        dest.writeString(decimals);
        dest.writeString(type);
        dest.writeByteArray(partition);
    }



    public Long getId() {
        return this.id;
    }



    public void setId(Long id) {
        this.id = id;
    }
}
