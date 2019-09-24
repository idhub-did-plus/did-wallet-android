package com.idhub.magic.clientlib.delegatiton;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.web3j.utils.Numeric;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idhub.magic.clientlib.ProviderFactory;
import com.idhub.magic.clientlib.http.RetrofitAccessor;
import com.idhub.magic.clientlib.interfaces.IdentityChainDelegate;
import com.idhub.magic.common.parameter.CreateIdentityDelegatedParam;
import com.idhub.magic.common.service.DeployedContractAddress;

public class IdentityChainDelegateImpl implements IdentityChainDelegate{
	ObjectMapper mapper = new ObjectMapper();
	public void createIdentity(String recovery, String associate, List<String> providers, List<String> resolvers) {

		CreateIdentityDelegatedParam rst = new CreateIdentityDelegatedParam();
		rst.associatedAddress = associate;
		rst.recoveryAddress = recovery;
		rst.providers = providers;
		rst.resolvers = resolvers;

		rst.timestamp = Numeric.toHexString(BigInteger.valueOf(System.currentTimeMillis() / 1000).toByteArray());
		rst = ClientEncoder.encode(rst);
		
		RetrofitAccessor.getInstance().getDelegationService().createEntity(rst, associate);
		


	}

	static CreateIdentityDelegatedParam param(String recovery, String associate, List<String> providers, List<String> resolvers) {
		CreateIdentityDelegatedParam rst = new CreateIdentityDelegatedParam();
		rst.associatedAddress = associate;
		rst.recoveryAddress = recovery;
		rst.providers = providers;
		rst.resolvers = resolvers;

		rst.timestamp = Numeric.toHexString(BigInteger.valueOf(System.currentTimeMillis() / 1000).toByteArray());
		rst = ClientEncoder.encode(rst);
		return rst;

	}

	@Override
	public void createIdentity() {
		String rec = ProviderFactory.getProvider().getRecoverAddress();
		String asso = ProviderFactory.getProvider().getDefaultCredentials().getAddress();
		List<String> ps = new ArrayList<String>();
		List<String> rs = new ArrayList<String>();
		rs.add(DeployedContractAddress.IdentityRegistryInterface);
		
		createIdentity(rec, asso, ps, rs);
	}

}
