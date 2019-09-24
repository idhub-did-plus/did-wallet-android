package com.idhub.magic.clientlib.interfaces;

import java.util.List;

public class Identity {
	String recoveryAddress;
	List<String> associateAddresses;
	List<String> providers;
	List<String> resolvers;
	public Identity(String recoveryAddress, List<String> associateAddresses, List<String> providers,
			List<String> resolvers) {
		super();
		this.recoveryAddress = recoveryAddress;
		this.associateAddresses = associateAddresses;
		this.providers = providers;
		this.resolvers = resolvers;
	}
	public String getRecoveryAddress() {
		return recoveryAddress;
	}
	public List<String> getAssociateAddresses() {
		return associateAddresses;
	}
	public List<String> getProviders() {
		return providers;
	}
	public List<String> getResolvers() {
		return resolvers;
	}
}
