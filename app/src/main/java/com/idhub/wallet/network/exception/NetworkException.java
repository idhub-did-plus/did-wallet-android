package com.idhub.wallet.network.exception;

import android.util.MalformedJsonException;

import com.google.gson.JsonParseException;
import com.idhub.wallet.App;
import com.idhub.wallet.R;
import com.idhub.wallet.utils.NetStatusUtils;

import org.json.JSONException;

import java.io.EOFException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import javax.net.ssl.SSLHandshakeException;

import retrofit2.adapter.rxjava.HttpException;

public class NetworkException extends Throwable {

    public static final int UNKNOWN = 0;
    public static final int NOT_NET = -1;
    public static final int TIMEOUT = -2;
    public static final int SSL_ERROR = -3;
    public static final int NET_ERROR = -4;
    public static final int JSON_ERROR = -5;
    public static final int PERMISSION = -6;

    private int code;
    private String message;

    //处理其他错误
    public NetworkException(Throwable e) {
//        MobclickAgent.reportError(App.getInstance(),e);
        handle(e);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void handle(Throwable throwable){
        if (NetStatusUtils.inNoNetworkStatus()) {
            code = NOT_NET;
            message = App.getInstance().getResources().getString(R.string.network_not_avail);
            return;
        }
        if(throwable instanceof ApiException){
            ApiException ae = (ApiException) throwable;
            code = ae.code;
            message = ae.message;
            return;
        }
        if (throwable instanceof HttpException) {
            HttpException ex = (HttpException) throwable;
            code = ex.code();
            message = App.getInstance().getResources().getString(R.string.network_error);
        } else if (throwable instanceof SocketTimeoutException
                || throwable instanceof ConnectException) {
            code = TIMEOUT;
            message = App.getInstance().getResources().getString(R.string.network_timeout);
        } else if( throwable instanceof UnknownHostException){
            code = NET_ERROR;
            message = "网络现在不给力";
        } else if(throwable instanceof JsonParseException
                || throwable instanceof JSONException
                || throwable instanceof EOFException
                || throwable instanceof MalformedJsonException
                || throwable instanceof ParseException){
            message = "数据没处理好";
            code = JSON_ERROR;
        } else if( throwable instanceof SSLHandshakeException){
            code = SSL_ERROR;
            message = "系统时间貌似不对";
        } else if( throwable instanceof SocketException){
            code = PERMISSION;
            message = "应用被禁止联网了，开启权限后，再试试";
        } else {
            code = UNKNOWN;
            if(throwable != null && throwable.getMessage() != null){
                message = throwable.getMessage();
            }else {
                message = "";
            }
        }
    };
}
