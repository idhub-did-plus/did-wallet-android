package wallet.idhub.com.clientlib.interfaces;

import java.math.BigInteger;
import java.util.List;

import com.idhub.magic.center.contracts.IdentityRegistryInterface.IdentityCreatedEventResponse;

public interface IdentityChain {
	Listen<IdentityCreatedEventResponse> createIdentity();

	Listen<IdentityCreatedEventResponse> createIdentity(String recovery, String associate, List<String> providers, List<String> rssolvers);

	public Listen<Long> getEIN(String associate);
	public Listen<Identity> getIdentity(long ein);

	public BigInteger getEINSync(String associate) throws Exception;
	public Boolean hasIdentity(String address) throws Exception;
}
