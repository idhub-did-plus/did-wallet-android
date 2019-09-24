package com.idhub.magic.clientlib.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;


public class MockProvider implements CredentialProvider {
	 private static final Logger log = LoggerFactory.getLogger(MockProvider.class);
	//static Credentials credentials;
	 Web3j web3j = Web3j.build(new HttpService("http://localhost:7545"));
	static Credentials client;
	static {
	
			client = Credentials.create("0f2e67d493a271e2421929cb56f58bce05b27c081f18ab1b9491b4394e0116a2");
			log.info("Credentials loaded");
		
	}
	@Override
	public Credentials getByAddress(String address) {
		// TODO Auto-generated method stub
		return client;
	}
	@Override
	public Credentials getDefaultCredentials() {
		// TODO Auto-generated method stub
		return client;
	}
	@Override
	public Web3j web3j() {
		// TODO Auto-generated method stub
		return web3j;
	}
	@Override
	public String getRecoverAddress() {
		// TODO Auto-generated method stub
		return client.getAddress();
	}

	@Override
	public long getLastEndBlockNumber() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void storeLastEndBlockNumber(long lebn) {
		// TODO Auto-generated method stub
		
	}

}
