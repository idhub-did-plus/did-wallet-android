package com.idhub.wallet.network;

import com.google.gson.annotations.SerializedName;

public class ErrorEntity {
    @SerializedName("code")
    public int code;
    @SerializedName("message")
    public String message;
}
