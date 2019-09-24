package com.idhub.magic.clientlib.interfaces;

import com.idhub.magic.common.parameter.CreateIdentityDelegatedParam;
import com.idhub.magic.common.parameter.MagicResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DelegationService {
	 @Headers({"Content-Type: application/json", "Cache-Control: max-age=640000"})
	 @POST("delegation/create_identity")
	Call<MagicResponse> createEntity(@Body CreateIdentityDelegatedParam param, @Query("identity") String identity);
}
