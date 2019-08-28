package wallet.idhub.com.clientlib.interfaces;

import java.util.List;

import com.idhub.magic.center.event.MagicEvent;
import com.idhub.magic.center.parameter.MagicResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EventService {
	@GET("event/query_events")
	Call<MagicResponse<List<MagicEvent>>> queryEvents(@Query("identity") String identity);
	@GET("event/dummy_event")
	Call<MagicResponse> dummyEvent(@Query("identity") String identity);
}
