package com.idhub.wallet.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.math.BigInteger;

import org.greenrobot.greendao.annotation.Generated;

import io.api.etherscan.model.Tx;
import io.api.etherscan.model.TxToken;

@Entity
public class TransactionRecordEntity {

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

    @Generated(hash = 1366843823)
    public TransactionRecordEntity(Long id, String from, String to,
                                   long blockNumber, String timeStamp, long nonce, String value,
                                   String gas, String gasUsed, String gasPrice, String tokenName,
                                   String tokenSymbol, String tokenDecimal, String contractAddress) {
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
    }

    @Generated(hash = 472688448)
    public TransactionRecordEntity() {
    }

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
        this.timeStamp = tx.getTimeStamp().toString();
        this.nonce = tx.getNonce();
        this.value = String.valueOf(tx.getValue());
        this.gas = String.valueOf(tx.getGas());
        this.gasUsed = String.valueOf(tx.getGasUsed());
        this.gasPrice = String.valueOf(tx.getGasPrice());
        this.tokenName = "";
        this.tokenSymbol = "";
        this.tokenDecimal = "";
        this.contractAddress = "";
    }

    public void setTxToken(TxToken txToken) {
        from = txToken.getFrom();
        to = txToken.getTo();
        this.blockNumber = txToken.getBlockNumber();
        this.timeStamp = txToken.getTimeStamp().toString();
        this.nonce = txToken.getNonce();
        this.value = String.valueOf(txToken.getValue());
        this.gas = String.valueOf(txToken.getGas());
        this.gasUsed = String.valueOf(txToken.getGasUsed());
        this.gasPrice = String.valueOf(txToken.getGasPrice());
        this.tokenName = txToken.getTokenName();
        this.tokenSymbol = txToken.getTokenSymbol();
        this.tokenDecimal = txToken.getTokenDecimal();
        this.contractAddress = txToken.getContractAddress();
    }


}
