package com.idhub.wallet.network.exception;

/**
 * Created by testp11 on 2017/4/12.
 */


/**
 * api请求 异常
 */
public class ApiResultException extends ApiException {

    public ApiResultException(int code, String message) {
        super(code,message);
    }

}
