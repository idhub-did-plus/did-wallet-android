package com.idhub.wallet.didhub.model;

public class Wallet  {

     boolean associate;//是否关联地址
     String name;
     String passwordHint;
     long timestamp;
     boolean isDefaultAddress;

    public Wallet() {
    }

    public boolean isDefaultAddress() {
        return isDefaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        isDefaultAddress = defaultAddress;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isAssociate() {
        return associate;
    }

    public void setAssociate(boolean associate) {
        this.associate = associate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswordHint() {
        return passwordHint;
    }

    public void setPasswordHint(String passwordHint) {
        this.passwordHint = passwordHint;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "associate=" + associate +
                ", name='" + name + '\'' +
                ", passwordHint='" + passwordHint + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
