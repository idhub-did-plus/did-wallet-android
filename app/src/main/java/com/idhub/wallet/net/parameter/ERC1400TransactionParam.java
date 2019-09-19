package com.idhub.wallet.net.parameter;

public class ERC1400TransactionParam {

    public ERC1400TransactionParam(String password, String contratAddress, String gasPrice, String gasLimit, byte[] paratition, String fromAddress, String toAddress, String value, String decimals,byte[] data) {
        this.password = password;
        this.contratAddress = contratAddress;
        this.gasPrice = gasPrice;
        this.gasLimit = gasLimit;
        this.paratition = paratition;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.value = value;
        this.decimals = decimals;
        this.data = data;
    }

    public String password;
    public String contratAddress;
    public String gasPrice;
    public String gasLimit;
    public byte[] paratition;
    public String fromAddress;
    public String toAddress;
    public String value;
    public String decimals;
    public byte[] data;


}
