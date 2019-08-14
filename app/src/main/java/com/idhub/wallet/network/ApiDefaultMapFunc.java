package com.idhub.wallet.network;

import android.accounts.AccountManager;
import android.content.Intent;

import com.idhub.wallet.network.exception.ApiResultException;
import com.idhub.wallet.network.exception.ApiServiceException;

import rx.functions.Func1;

/**
 * Created by testp11 on 2017/4/12.
 */
public class ApiDefaultMapFunc<T> implements Func1<ApiResultEntity<T>, T>{
    @Override
    public T call(ApiResultEntity<T> apiResult) {
        T result = apiResult.result;
        ErrorEntity error = apiResult.error;
        if (result != null) {
            return result;
        } else if (error != null) {
            throw new ApiResultException(error.code, error.message);
        }else {
            throw new ApiResultException(0, "网络请求出错");
        }
    }

    private void handleError(int errorCode){
        switch (errorCode){
            case 700:
                //TODO:
//                AccountManager.getInstance().clearLoginInfo();
//                Intent intent = new Intent(App.getAppContext(), MainActivity.class);
//                intent.putExtra("data", MainActivity.PERSONAL_INFO);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                App.getAppContext().startActivity(intent);
                break;
            case 800:
                break;
            default:
                break;
        }
    }

}

