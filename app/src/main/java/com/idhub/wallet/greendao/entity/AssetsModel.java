package com.idhub.wallet.greendao.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class AssetsModel implements Parcelable {

    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String address;
    private String mainContractAddress;
    private String ropstenContractAddress;
    private String symble;
    private String balance;
    private String decimals;

    public AssetsModel() {
    }

    protected AssetsModel(Parcel in) {
        name = in.readString();
        address = in.readString();
        mainContractAddress = in.readString();
        ropstenContractAddress = in.readString();
        decimals = in.readString();
        symble = in.readString();
        balance = in.readString();
    }

    @Generated(hash = 1668248179)
    public AssetsModel(Long id, String name, String address,
            String mainContractAddress, String ropstenContractAddress,
            String symble, String balance, String decimals) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mainContractAddress = mainContractAddress;
        this.ropstenContractAddress = ropstenContractAddress;
        this.symble = symble;
        this.balance = balance;
        this.decimals = decimals;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(mainContractAddress);
        dest.writeString(ropstenContractAddress);
        dest.writeString(symble);
        dest.writeString(balance);
        dest.writeString(decimals);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getSymble() {
        return this.symble;
    }

    public void setSymble(String symble) {
        this.symble = symble;
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


}
