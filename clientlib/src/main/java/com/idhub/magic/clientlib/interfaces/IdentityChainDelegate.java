package com.idhub.magic.clientlib.interfaces;

import java.util.List;

import com.idhub.magic.common.contracts.IdentityRegistryInterface.IdentityCreatedEventResponse;

public interface IdentityChainDelegate {
	void createIdentity();

	void createIdentity(String recovery, String associate, List<String> providers, List<String> rssolvers);

}
