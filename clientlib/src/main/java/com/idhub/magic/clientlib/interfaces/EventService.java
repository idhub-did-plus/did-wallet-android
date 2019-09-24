package com.idhub.magic.clientlib.interfaces;

import java.util.List;

import com.idhub.magic.common.event.MagicEvent;
import com.idhub.magic.common.parameter.MagicResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface EventService {
	 @Headers({"pass_auth: true"})
	@GET("event/query_events")
	Call<MagicResponse<List<MagicEvent>>> queryEvents(@Query("identity") String identity);
	@GET("event/dummy_event")
	Call<MagicResponse> dummyEvent(@Query("identity") String identity);
}
