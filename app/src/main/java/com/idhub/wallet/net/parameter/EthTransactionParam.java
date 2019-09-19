package com.idhub.wallet.net.parameter;

public class EthTransactionParam {

    public EthTransactionParam(String password, String fromAddress, String toAddress, String value, String gasLimit, String gasPrice) {
        this.password = password;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.value = value;
        this.gasLimit = gasLimit;
        this.gasPrice = gasPrice;
    }

    public String password;
    public String fromAddress;
    public String toAddress;
    public String value;
    public String gasLimit;
    public String gasPrice;

}
