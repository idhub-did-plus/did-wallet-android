package com.idhub.magic.clientlib.interfaces;

import java.math.BigInteger;
import java.util.List;


import com.idhub.magic.common.contracts.ERC1056ResolverInterface;
import com.idhub.magic.common.contracts.IdentityRegistryInterface;
import com.idhub.magic.common.contracts.IdentityRegistryInterface.IdentityCreatedEventResponse;
import io.reactivex.Observable;

public interface IdentityChain {
	Listen<IdentityCreatedEventResponse> createIdentity();

	Listen<IdentityCreatedEventResponse> createIdentity(String recovery, String associate, List<String> providers, List<String> rssolvers);

	public Listen<BigInteger> getEIN(String associate);
	public Listen<Identity> getIdentity(long ein);
	public Identity getIdentitySync(long ein) throws Exception;

	BigInteger getEINSync(String associate) throws Exception;
	Boolean hasIdentity(String address) throws Exception;
	String einToDID(String ein) throws Exception;

	Observable<IdentityRegistryInterface.AssociatedAddressAddedEventResponse> addAssociatedAddress(BigInteger ein, String approvingAddress, String addressToAdd, String associationPrivateKey);
	Observable<IdentityRegistryInterface.RecoveryTriggeredEventResponse> recoveryIdentity(String ein, String newAssociationAddress, String newAssociationAddressPrivateKey);
	Observable<ERC1056ResolverInterface.IdentityResetedEventResponse> reset(String newIdentity, String privateKey);
	Observable< ERC1056ResolverInterface.IdentityInitializedEventResponse> initialize(String identity);

}
