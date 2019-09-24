package com.idhub.magic.clientlib.event;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.EventValues;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Int;
import org.web3j.abi.datatypes.IntType;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.tx.Contract;

import com.idhub.magic.clientlib.ProviderFactory;
import com.idhub.magic.common.service.DeployedContractAddress;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class ChainEventListener {

	static {

		final Event event = new Event("IdentityCreated",
				Arrays.<TypeReference<?>>asList(
				new TypeReference<Uint256>(true) {				}, 
				new TypeReference<Address>(true) {				}, 
			
				new TypeReference<Address>(false) {				},
				new TypeReference<Address>(false) {				},
				new TypeReference<DynamicArray<Address>>(false) {				}, 
				new TypeReference<DynamicArray<Address>>(false) {				}, 
				new TypeReference<Bool>(false) {				}
				)
				);
		EthFilter filter = new EthFilter(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST,
				DeployedContractAddress.IdentityRegistryInterface.substring(2));
		filter.addSingleTopic(EventEncoder.encode(event));
		String address = "0x" + TypeEncoder.encode(new Address("0xC8Efc3D648862eDcad4e222bDc073DAEf0203409"));//ProviderFactory.getProvider().getDefaultCredentials().getAddress()));
		String ein = "0x" + TypeEncoder.encode(new Uint256(1));
		
		// optTopicAddress=null; optTopicHash=null; if you want not to filter on that
		// optional topic
		filter.addOptionalTopics(ein, address);
		Disposable dis = ProviderFactory.getProvider().web3j().ethLogFlowable(filter).subscribe(log -> {
			
			 EventValues eventValues = Contract.staticExtractEventParameters(event, log);
			 List<Type> vs = eventValues.getIndexedValues();
			  String ini = (String) vs.get(1).getValue();
			  BigInteger ee = (BigInteger) vs.get(0).getValue();
			 
			  Object rec = eventValues.getNonIndexedValues().get(0).getValue();
			  Object asso =eventValues.getNonIndexedValues().get(1).getValue();
			  Object ps =  eventValues.getNonIndexedValues().get(2).getValue();
			  Object dd =  eventValues.getNonIndexedValues().get(3).getValue();
			  Object de = eventValues.getNonIndexedValues().get(4).getValue(); 
			
		});

	}

	static public void main(String[] ss) {

	}
}
