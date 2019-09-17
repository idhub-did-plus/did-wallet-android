package wallet.idhub.com.clientlib;

import wallet.idhub.com.clientlib.interfaces.CredentialProvider;
import wallet.idhub.com.clientlib.interfaces.MockProvider;
public class ProviderFactory {
	static CredentialProvider provider;
	static {
		try {
			provider = (CredentialProvider) Class.forName("wallet.idhub.com.clientlib.interfaces.MockProvider").newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static public CredentialProvider getProvider() {
		return provider;
	}

}
