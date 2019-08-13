package com.idhub.wallet.network;

import com.google.gson.annotations.SerializedName;

public class ApiServiceEntity<T> {
    @SerializedName("code")
    public int code;
    @SerializedName("msg")
    public String msg;
    @SerializedName("data")
    public T data;
}
