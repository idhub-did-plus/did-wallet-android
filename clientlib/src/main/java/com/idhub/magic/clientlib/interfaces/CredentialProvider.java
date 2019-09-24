package com.idhub.magic.clientlib.interfaces;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

public interface CredentialProvider {
	Credentials getByAddress(String address);
	Credentials getDefaultCredentials();
	String getRecoverAddress();
	Web3j web3j();
	long getLastEndBlockNumber();
	void storeLastEndBlockNumber(long lebn);
}
