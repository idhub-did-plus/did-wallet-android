package com.idhub.wallet.network;


import android.util.Base64;

import com.idhub.wallet.utils.LogUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by testp11 on 2017/4/12.
 */
public class ApiInterceptor implements Interceptor{

    private static class SingletonHolder {
        static final ApiInterceptor instance = new ApiInterceptor();
    }

    public static ApiInterceptor getInstance() {
        return SingletonHolder.instance;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
//        Request original = chain.request();
//        HttpUrl.Builder builder = original.url().newBuilder();
//        encrypt(original);
//        Request request = original.newBuilder()
//                .addHeader("xnt-x-token","")
//                .addHeader("xnt-x-sign","")
//                .method(original.method(),original.body())
//                .url(builder.build())
//                .build();

////        HttpUrl.Builder builder = oldRequest.url().newBuilder()
////                .setQueryParameter("token", C.API_BASE);
////        builder.
//        // 新的请求
        Request original = chain.request();
//        Request.Builder  newRequestBuilder = original.newBuilder();
//        Request request = newRequestBuilder.build();
        HttpUrl.Builder builder = original.url().newBuilder();
        HttpUrl url = builder.build();
        Request newRequest = null;
        long time;
        String urlString = url.url().getPath();
        //TODO:
//        if(C.USERS_LOGIN_PATH.equals(urlString)||C.USERS_PASSWORD_PATH.equals(urlString)){
//            time = PsdSingleton.getInstance().getTime();
//        }else{
//            time = System.currentTimeMillis();
//        }
//        LogUtils.e("nyl",time+"");
//        LogUtils.e("nyl",original.body().contentLength()+"");
//        newRequest = original.newBuilder()
//                .method(original.method(),original.body())
//                .url(url)
//                .headers(MobileInformation.getInstance().getMobilePhoneHeaders())
//                .addHeader("xnt-x-token",AccountManager.getInstance().getAccessToken())
//                .addHeader("xnt-x-sign",encrypt(original,time))
//                .addHeader("xnt-x-time",time+"")
//                .build();
        return chain.proceed(newRequest);
    }

    private String encrypt(Request request, long time) throws IOException {
        String encryptStr = "";
        //TODO:
        LogUtils.e("nyl",encryptStr);
        RequestBody requestBody = request.body();
        if(requestBody != null){
            okio.Buffer buffer = new okio.Buffer();
            requestBody.writeTo(buffer);
            LogUtils.e("nyl","body:"+buffer.toString());
            Charset charset = Charset.forName("UTF-8");
            MediaType contentType = requestBody.contentType();
            LogUtils.e("nyl","body:"+(contentType != null));
            LogUtils.e("nyl","body:"+charset.toString());
            if(contentType != null){
                charset = contentType.charset(charset);
            }
            String string = buffer.readString(charset);
            LogUtils.e("nyl","body:"+string);
            encryptStr = encrypt(string,time);
        }
        return encryptStr;
    }

    private String encrypt(String body, long time){
        String base64AndMd5 = "";
        //TODO:
//        if(AccountManager.getInstance().isLogin()) {
//            base64AndMd5 = MD5Util.md5(Base64.encodeToString(body.getBytes(),Base64.NO_WRAP).trim() + AccountManager.getInstance().getPrivateKey() + time);
//        }else{
//            base64AndMd5 = MD5Util.md5(Base64.encodeToString(body.getBytes(),Base64.NO_WRAP).trim() + "login" + time);
//        }
        LogUtils.e("nyl",base64AndMd5);
        return base64AndMd5;
    }
}
