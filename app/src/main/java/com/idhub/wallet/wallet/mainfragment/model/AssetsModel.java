package com.idhub.wallet.wallet.mainfragment.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AssetsModel implements Parcelable {

    private String name;
    private String address;
    private String token;
    private String symble;
    private String balance;

    public AssetsModel() {
    }

    protected AssetsModel(Parcel in) {
        name = in.readString();
        address = in.readString();
        token = in.readString();
        symble = in.readString();
        balance = in.readString();
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSymble() {
        return symble;
    }

    public void setSymble(String symble) {
        this.symble = symble;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(token);
        dest.writeString(symble);
        dest.writeString(balance);
    }
}
