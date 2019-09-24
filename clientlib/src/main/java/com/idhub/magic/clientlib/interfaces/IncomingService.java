package com.idhub.magic.clientlib.interfaces;

import java.util.List;

import io.api.etherscan.model.EthNetwork;
import io.api.etherscan.model.Tx;
import io.api.etherscan.model.TxToken;

public interface IncomingService {

	void setCurrentNetwork(EthNetwork c);

	void setAccounts(List<String> accounts);

	void subscribeTransaction(IncomingListener<Tx> txl);

	void subscribeTransfer(IncomingListener<TxToken> txl);

}