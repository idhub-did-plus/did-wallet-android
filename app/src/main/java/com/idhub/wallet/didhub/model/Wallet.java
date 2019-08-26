package com.idhub.wallet.didhub.model;

public class Wallet  {

     boolean isgl;//是否关联地址
     String identifer;//身份标识符
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

    public boolean isIsgl() {
        return isgl;
    }

    public void setIsgl(boolean isgl) {
        this.isgl = isgl;
    }

    public String getIdentifer() {
        return identifer;
    }

    public void setIdentifer(String identifer) {
        this.identifer = identifer;
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
                "isgl=" + isgl +
                ", identifer='" + identifer + '\'' +
                ", name='" + name + '\'' +
                ", passwordHint='" + passwordHint + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
