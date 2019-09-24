package com.idhub.magic.clientlib.interfaces;

import java.util.List;

import com.idhub.magic.common.parameter.MagicResponse;
import com.idhub.magic.common.ustorage.entity.FinancialProfile;
import com.idhub.magic.common.ustorage.entity.IdentityArchive;
import com.idhub.magic.common.ustorage.entity.Material;
import com.idhub.magic.common.ustorage.entity.ext.ExtensionField;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface IdentityStorage {
	 @Headers({"Content-Type: application/json", "Cache-Control: max-age=640000"})
	 @POST("storage/store_archive")
     Call<MagicResponse> storeArchive(@Body IdentityArchive archive, @Query("identity") String identity);
	 @Headers({"Content-Type: application/json", "Cache-Control: max-age=640000"})
	 @POST("storage/store_financial_profile")
     Call<MagicResponse> storeFinancialProfile(@Body FinancialProfile profile, @Query("identity") String identity);
	 
	 @GET("storage/retrieve_archive")
	 Call<MagicResponse<IdentityArchive>>  retrieveArchive(@Query("identity") String identity);
	 @GET("storage/retrieve_materials")
	 Call<MagicResponse<List<Material>>>  retrieveMaterials(@Query("identity") String identity);
	 @GET("storage/remove_material")
	 Call<MagicResponse<List<Material>>>  removeMaterial(@Query("identity") String identity, @Query("type") String type, @Query("name") String name);
     @POST("storage/upload_material")
     @Multipart
     Call<MagicResponse> uploadMaterial(@Query("identity") String identity, @Query("type") String type, @Query("name") String name, @Part MultipartBody.Part file);
     @GET("storage/extension_meta")
     Call<MagicResponse<List<ExtensionField>>>  extensionMeta(@Query("identity") String identity);

     
}
