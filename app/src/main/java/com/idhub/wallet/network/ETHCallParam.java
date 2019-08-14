package com.idhub.wallet.network;

import com.google.gson.annotations.SerializedName;

public class ETHCallParam {
    @SerializedName("from")
    public String from;
    @SerializedName("to")
    public String to;
    @SerializedName("gas")
    public String gas;
    @SerializedName("gasPrice")
    public String gasPrice;
    @SerializedName("value")
    public String value;
    @SerializedName("data")
    public String data;
}
