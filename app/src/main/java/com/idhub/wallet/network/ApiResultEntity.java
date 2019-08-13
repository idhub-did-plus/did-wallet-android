package com.idhub.wallet.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by testp11 on 2017/4/12.
 */

public class ApiResultEntity<T> {
    @SerializedName("status")
    public int status;
    @SerializedName("msg")
    public String msg;
    @SerializedName("biz")
    public ApiServiceEntity<T> biz;
}
