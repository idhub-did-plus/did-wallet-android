package com.idhub.wallet.didhub.model;

public class Wallet {

    String name;
    String passwordHint;
    long timestamp;


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
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
                "name='" + name + '\'' +
                ", passwordHint='" + passwordHint + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
