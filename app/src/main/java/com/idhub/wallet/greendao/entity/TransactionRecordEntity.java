package com.idhub.wallet.greendao.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.math.BigInteger;

import org.greenrobot.greendao.annotation.Generated;

import io.api.etherscan.model.Tx;
import io.api.etherscan.model.TxToken;

@Entity
public class TransactionRecordEntity implements Parcelable {

    @Id(autoincrement = true)
    private Long id;
    private String from;
    private String to;
    private long blockNumber;
    private String timeStamp;
    private long nonce;
    private String value;
    private String gas;
    private String gasUsed;
    private String gasPrice;
    private String tokenName;
    private String tokenSymbol;
    private String tokenDecimal;
    private String contractAddress;
    private String hash;

    @Generated(hash = 588260950)
    public TransactionRecordEntity(Long id, String from, String to, long blockNumber, String timeStamp,
            long nonce, String value, String gas, String gasUsed, String gasPrice, String tokenName,
            String tokenSymbol, String tokenDecimal, String contractAddress, String hash) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.blockNumber = blockNumber;
        this.timeStamp = timeStamp;
        this.nonce = nonce;
        this.value = value;
        this.gas = gas;
        this.gasUsed = gasUsed;
        this.gasPrice = gasPrice;
        this.tokenName = tokenName;
        this.tokenSymbol = tokenSymbol;
        this.tokenDecimal = tokenDecimal;
        this.contractAddress = contractAddress;
        this.hash = hash;
    }

    @Generated(hash = 472688448)
    public TransactionRecordEntity() {
    }

    protected TransactionRecordEntity(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        from = in.readString();
        to = in.readString();
        blockNumber = in.readLong();
        timeStamp = in.readString();
        nonce = in.readLong();
        value = in.readString();
        gas = in.readString();
        gasUsed = in.readString();
        gasPrice = in.readString();
        tokenName = in.readString();
        tokenSymbol = in.readString();
        tokenDecimal = in.readString();
        contractAddress = in.readString();
        hash = in.readString();
    }

    public static final Creator<TransactionRecordEntity> CREATOR = new Creator<TransactionRecordEntity>() {
        @Override
        public TransactionRecordEntity createFromParcel(Parcel in) {
            return new TransactionRecordEntity(in);
        }

        @Override
        public TransactionRecordEntity[] newArray(int size) {
            return new TransactionRecordEntity[size];
        }
    };

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public long getBlockNumber() {
        return this.blockNumber;
    }

    public void setBlockNumber(long blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getNonce() {
        return this.nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getGas() {
        return this.gas;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    public String getGasUsed() {
        return this.gasUsed;
    }

    public void setGasUsed(String gasUsed) {
        this.gasUsed = gasUsed;
    }

    public String getGasPrice() {
        return this.gasPrice;
    }

    public void setGasPrice(String gasPrice) {
        this.gasPrice = gasPrice;
    }

    public String getTokenName() {
        return this.tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getTokenSymbol() {
        return this.tokenSymbol;
    }

    public void setTokenSymbol(String tokenSymbol) {
        this.tokenSymbol = tokenSymbol;
    }

    public String getTokenDecimal() {
        return this.tokenDecimal;
    }

    public void setTokenDecimal(String tokenDecimal) {
        this.tokenDecimal = tokenDecimal;
    }

    public String getContractAddress() {
        return this.contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }


    public void setTx(Tx tx) {
        from = tx.getFrom();
        to = tx.getTo();
        this.blockNumber = tx.getBlockNumber();
        this.timeStamp = tx.getTimeStamp();
        this.nonce = tx.getNonce();
        this.value = String.valueOf(tx.getValue());
        this.gas = String.valueOf(tx.getGas());
        this.gasUsed = String.valueOf(tx.getGasUsed());
        this.gasPrice = String.valueOf(tx.getGasPrice());
        this.tokenName = "";
        this.tokenSymbol = "";
        this.tokenDecimal = "";
        this.contractAddress = "";
        this.hash = tx.getHash();
    }

    public void setTxToken(TxToken txToken) {
        from = txToken.getFrom();
        to = txToken.getTo();
        this.blockNumber = txToken.getBlockNumber();
        this.timeStamp = txToken.getTimeStamp();
        this.nonce = txToken.getNonce();
        this.value = String.valueOf(txToken.getValue());
        this.gas = String.valueOf(txToken.getGas());
        this.gasUsed = String.valueOf(txToken.getGasUsed());
        this.gasPrice = String.valueOf(txToken.getGasPrice());
        this.tokenName = txToken.getTokenName();
        this.tokenSymbol = txToken.getTokenSymbol();
        this.tokenDecimal = txToken.getTokenDecimal();
        this.contractAddress = txToken.getContractAddress();
        this.hash = txToken.getHash();
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
        dest.writeString(from);
        dest.writeString(to);
        dest.writeLong(blockNumber);
        dest.writeString(timeStamp);
        dest.writeLong(nonce);
        dest.writeString(value);
        dest.writeString(gas);
        dest.writeString(gasUsed);
        dest.writeString(gasPrice);
        dest.writeString(tokenName);
        dest.writeString(tokenSymbol);
        dest.writeString(tokenDecimal);
        dest.writeString(contractAddress);
        dest.writeString(hash);
    }

    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
