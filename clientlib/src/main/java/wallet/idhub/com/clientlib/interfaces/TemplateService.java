package wallet.idhub.com.clientlib.interfaces;

import com.idhub.magic.common.parameter.MagicResponse;
import com.idhub.magic.common.service.TemplateData;
import com.idhub.magic.common.ustorage.entity.IdentityArchive;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TemplateService {
	 @GET("template/template_data")
	 Call<MagicResponse<TemplateData>>  templateData(@Query("identity") String identity);

}
