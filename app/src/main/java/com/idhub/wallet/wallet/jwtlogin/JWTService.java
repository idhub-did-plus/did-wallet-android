package com.idhub.wallet.wallet.jwtlogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface JWTService {
    @POST
    Call<Object> jwtLogin(@Body String signMessage);
}
