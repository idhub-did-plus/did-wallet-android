package com.idhub.wallet.network;


import android.text.TextUtils;
import android.util.Log;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class DIDApiUseCase {

    private static void transfer(String address, BigInteger amount) {
        ArrayList<Type> parameters = new ArrayList<>();
        parameters.add(new Address(address));
        parameters.add(new Uint256(amount));
        ArrayList<TypeReference<?>> outputParameters = new ArrayList<>();
        outputParameters.add(TypeReference.create(Bool.class));
        org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function("transfer", parameters, outputParameters);
    }

    public static Observable<String> searchBalance(String address, String token) {
        if (TextUtils.isEmpty(token)) {
            return searchBalance(address);
        }
        ArrayList<Type> parameters = new ArrayList<>();
        parameters.add(new Address(address));
        ArrayList<TypeReference<?>> outputParameters = new ArrayList<>();
        outputParameters.add(TypeReference.create(Uint256.class));
        org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function("balanceOf", parameters, outputParameters);
        String encode = FunctionEncoder.encode(function);
        ETHCallParam ethCallParam = new ETHCallParam();
        ethCallParam.to = token;
        ethCallParam.data = encode;
        ETHPostEntityParam<List<Object>> entityParam = new ETHPostEntityParam<>();
        List<Object> paramsList = new ArrayList<>();
        paramsList.add(ethCallParam);
        paramsList.add("latest");
        entityParam.params = paramsList;
        entityParam.method = "eth_call";
        return ApiRequest.observableHandle(api().searchTokenBanlance(entityParam));
    }

    public static Observable<String> transferETH(String address, BigInteger amount) {
        ETHPostEntityParam<List<String>> entityParam = new ETHPostEntityParam<>();
        List<String> params = new ArrayList<>();
        params.add(address);
        params.add("latest");
        entityParam.params = params;
        entityParam.method = "eth_getBalance";
        return ApiRequest.observableHandle(api().searchETHBanlance(entityParam));
    }

    public static Observable<String> searchBalance(String address) {
        ETHPostEntityParam<List<String>> entityParam = new ETHPostEntityParam<>();
        List<String> params = new ArrayList<>();
        params.add(address);
        params.add("latest");
        entityParam.params = params;
        entityParam.id = 1;
        entityParam.jsonrpc = "2.0";
        entityParam.method = "eth_getBalance";
        return ApiRequest.observableHandle(api().searchETHBanlance(entityParam));
    }


    private static class XNTApiSingletonHolder {
        static final XNTApi instance = ApiRequest.getInstance().create(XNTApi.class);
    }

    public static XNTApi api() {
        return XNTApiSingletonHolder.instance;
    }

    public interface XNTApi {
        @Headers("Content-Type:application/json")
        @POST("/")
        Observable<ApiResultEntity<String>> searchETHBanlance(@Body ETHPostEntityParam ethPostEntityParam);

        @Headers("Content-Type:application/json")
        @POST("/")
        Observable<ApiResultEntity<String>> searchTokenBanlance(@Body ETHPostEntityParam ethPostEntityParam);


    }
}
