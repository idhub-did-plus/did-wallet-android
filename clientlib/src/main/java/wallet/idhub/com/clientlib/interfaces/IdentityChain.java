package wallet.idhub.com.clientlib.interfaces;

import java.math.BigInteger;
import java.util.List;

import com.idhub.magic.center.contracts.ERC1056ResolverInterface;
import com.idhub.magic.center.contracts.IdentityRegistryInterface;
import com.idhub.magic.center.contracts.IdentityRegistryInterface.IdentityCreatedEventResponse;
import io.reactivex.Observable;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public interface IdentityChain {
	Listen<IdentityCreatedEventResponse> createIdentity();

	Listen<IdentityCreatedEventResponse> createIdentity(String recovery, String associate, List<String> providers, List<String> rssolvers);

	public Listen<BigInteger> getEIN(String associate);
	public Listen<Identity> getIdentity(long ein);

	BigInteger getEINSync(String associate) throws Exception;
	Boolean hasIdentity(String address) throws Exception;

	Listen<IdentityRegistryInterface.AssociatedAddressAddedEventResponse> addAssociatedAddress(BigInteger ein, String approvingAddress, String addressToAdd);
	Listen<IdentityRegistryInterface.RecoveryTriggeredEventResponse> recoveryIdentity(String ein, String newAssociationAddress);
	Observable<ERC1056ResolverInterface.IdentityResetedEventResponse> reset(String newIdentity);
	Observable< ERC1056ResolverInterface.IdentityInitializedEventResponse> initialize(String identity);

}
