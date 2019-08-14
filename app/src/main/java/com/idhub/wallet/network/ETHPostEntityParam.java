package com.idhub.wallet.network;

import com.google.gson.annotations.SerializedName;

public class ETHPostEntityParam<T> {
    @SerializedName("jsonrpc")
    public String jsonrpc = "2.0";
    @SerializedName("method")
    public String method;
    @SerializedName("params")
    public T params;
    @SerializedName("id")
    public int id = 1;
}
