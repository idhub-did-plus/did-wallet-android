package wallet.idhub.com.clientlib.interfaces;

import com.idhub.magic.center.parameter.MagicResponse;
import com.idhub.magic.center.service.TemplateData;
import com.idhub.magic.center.ustorage.entity.IdentityArchive;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TemplateService {
	 @GET("template/template_data")
	 Call<MagicResponse<TemplateData>>  templateData(@Query("identity") String identity);

}
