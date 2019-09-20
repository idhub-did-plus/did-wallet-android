package wallet.idhub.com.clientlib.interfaces;

import java.util.List;

import com.idhub.magic.common.kvc.entity.ClaimOrder;
import com.idhub.magic.common.parameter.MagicResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ClaimService {
	@POST("claim/order")
	Call<MagicResponse> order(@Body ClaimOrder order, @Query("identity") String identity);
	
	

}
