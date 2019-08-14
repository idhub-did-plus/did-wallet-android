package com.idhub.wallet.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by testp11 on 2017/4/12.
 */

public class ApiResultEntity<T> {
    @SerializedName("jsonrpc")
    public String jsonrpc;
    @SerializedName("id")
    public int id;
    @SerializedName("result")
    public T result;
    @SerializedName("error")
    public ErrorEntity error;
}
