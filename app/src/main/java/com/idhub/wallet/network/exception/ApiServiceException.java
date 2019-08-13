package com.idhub.wallet.network.exception;

/**
 * Created by testp11 on 2017/4/12.
 */

/**
 * api请求 异常
 */
public class ApiServiceException extends ApiException {

    public ApiServiceException(int code, String message) {
        super(code,message);
    }

}
