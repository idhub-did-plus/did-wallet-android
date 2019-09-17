package wallet.idhub.com.clientlib.http;

import java.io.File;
import java.io.IOException;

import org.web3j.crypto.Credentials;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.idhub.magic.center.parameter.MagicResponse;
import com.idhub.magic.center.ustorage.entity.FinancialProfile;
import com.idhub.magic.center.ustorage.entity.IdentityArchive;
import com.idhub.magic.center.ustorage.entity.IdentityInfo;
import com.idhub.magic.center.util.AuthenticationUtils;
import com.idhub.magic.center.util.Signature;
import wallet.idhub.com.clientlib.ProviderFactory;
import wallet.idhub.com.clientlib.interfaces.DelegationService;
import wallet.idhub.com.clientlib.interfaces.EventService;
import wallet.idhub.com.clientlib.interfaces.IdentityStorage;
import wallet.idhub.com.clientlib.interfaces.TemplateService;
import wallet.idhub.com.clientlib.interfaces.ClaimService;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitAccessor {
	static RetrofitAccessor instance = new RetrofitAccessor();
	static public RetrofitAccessor getInstance() {
		return instance;
	}
	 RetrofitAccessor() {
		 
		super();
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		init();
		// TODO Auto-generated constructor stub
	}
	IdentityStorage identityStorage;
	EventService eventService;
	DelegationService delegationService;
	ClaimService kycService;
	ObjectMapper mapper = new ObjectMapper();
	TemplateService templateService;
	
	 public TemplateService getTemplateService() {
		return templateService;
	}
	public IdentityStorage getIdentityStorage() {
		return identityStorage;
	}

	void init() {
		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
			
			@Override
			public Response intercept(Chain chain) throws IOException {
				Request request = chain.request();
				HttpUrl url = request.url();
				String identity = url.queryParameter("identity");
				long tp = System.currentTimeMillis();
				long ts = System.currentTimeMillis();
				String sgt = authenticate(identity, ts);
				String nurl = url.url().toString() + "&timestamp=" + ts;

				Request.Builder builder = request.newBuilder().addHeader("signature", sgt).url(nurl);
				return chain.proceed(builder.build());
			}
		}).build();
		
		
		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://localhost:8080")
				.addConverterFactory(JacksonConverterFactory.create(mapper)).client(client).build();

		identityStorage = retrofit.create(IdentityStorage.class);
		eventService = retrofit.create(EventService.class);
		delegationService = retrofit.create(DelegationService.class);
		kycService =  retrofit.create(ClaimService.class);
	}

	public ClaimService getKycService() {
		return kycService;
	}
	public DelegationService getDelegationService() {
		return delegationService;
	}
	public EventService getEventService() {
		return eventService;
	}

	 private String authenticate(String identity, long ts) {
		String pt = identity + ts;
		Credentials cre = ProviderFactory.getProvider().getByAddress(identity);
		Signature sig = AuthenticationUtils.sig(pt, cre);
		
		try {
			String sgt = mapper.writeValueAsString(sig);
			
			return sgt;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	 static public void main(String[] ss) throws Exception {
			RetrofitAccessor ra = new RetrofitAccessor();
			String identity = ProviderFactory.getProvider().getDefaultCredentials().getAddress();
			IdentityArchive ida = new IdentityArchive();
			IdentityInfo ii = new IdentityInfo();
			//ii.setBirthday(new Date());
			ii.setCountry("china");
		//	ii.setFirstName("yuqi");
		//	ii.setLastName("bai");
			ii.setPassportNumber("ggggg");
			ida.setIdentityInfo(ii);
		    //   RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), ida);
			  MagicResponse mss = ra.getIdentityStorage().storeFinancialProfile(new FinancialProfile(), identity).execute().body();
		//	  MagicResponse ms = ra.getIdentityStorage().removeMaterial(identity, "kkk", "lll").execute().body();
			// MagicResponse<IdentityArchive> user = ra.getIdentityStorage().retrieveArchive(identity).execute().body();
		//	 System.out.println(user);
			  File file = new File("c:\\timg.jpg");
			  MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));
			  ra.getIdentityStorage().uploadMaterial(identity, "ddd", "ddd", filePart).execute().body();
	
	}
}