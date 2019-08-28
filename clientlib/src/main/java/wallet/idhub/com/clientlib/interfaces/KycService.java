package wallet.idhub.com.clientlib.interfaces;

import java.util.List;

import com.idhub.magic.center.kvc.entity.KycOrder;
import com.idhub.magic.center.parameter.MagicResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface KycService {
	@POST("kyc/order")
	Call<MagicResponse> order(@Body KycOrder order, @Query("identity") String entity);
	
	

}
