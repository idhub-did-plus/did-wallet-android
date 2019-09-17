package wallet.idhub.com.clientlib;

import wallet.idhub.com.clientlib.delegatiton.IdentityChainDelegateImpl;
import wallet.idhub.com.clientlib.etherscan.Etherscan;
import wallet.idhub.com.clientlib.event.EventFetcher;
import wallet.idhub.com.clientlib.http.RetrofitAccessor;
import wallet.idhub.com.clientlib.interfaces.EventListenerService;
import wallet.idhub.com.clientlib.interfaces.IdentityChain;
import wallet.idhub.com.clientlib.interfaces.IdentityChainDelegate;
import wallet.idhub.com.clientlib.interfaces.IdentityChainViewer;
import wallet.idhub.com.clientlib.interfaces.IdentityStorage;
import wallet.idhub.com.clientlib.interfaces.IncomingService;
import wallet.idhub.com.clientlib.interfaces.TemplateService;
import wallet.idhub.com.clientlib.interfaces.ClaimService;
import wallet.idhub.com.clientlib.local.IdentityChainLocal;

public class ApiFactory {
	static IdentityChainLocal local = new IdentityChainLocal();
	static IdentityChainDelegate delegation = new IdentityChainDelegateImpl();

	static RetrofitAccessor retrofitAccessor = RetrofitAccessor.getInstance();
	static IncomingService incomingService =  Etherscan.getInstance();
	public static IncomingService getIncomingService() {
		return incomingService;
	}
	public static IdentityStorage getArchiveStorage() {
		return retrofitAccessor.getIdentityStorage();
	}
	static public IdentityChainDelegate getIdentityChainDelegate() {
		return delegation;
	}
	static public EventListenerService getEventListenerService() {
		return EventFetcher.getInstance();
	}
	
	static public ClaimService getKycService() {
		return retrofitAccessor.getKycService();
	}
	//上面是跟后端访问接口
	//后面不访问后端，仅供参考
	static public IdentityChain getIdentityChainLocal() {
		return local;
	}
	static public IdentityChainViewer getIdentityChainViewer() {
		return local;
	}
	static public TemplateService getTemplateService() {
		return retrofitAccessor.getTemplateService();
	}
	
}
