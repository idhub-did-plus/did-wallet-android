package com.idhub.magic.clientlib.local;


import android.text.TextUtils;
import android.util.Log;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
//import org.web3j.model.Greeter;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
//import org.web3j.tx.gas.ContractGasProvider;
//import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import com.idhub.base.node.WalletChangeContractObservable;
import com.idhub.magic.clientlib.ProviderFactory;
import com.idhub.magic.common.contracts.ERC1056ResolverInterface;
import com.idhub.magic.common.contracts.EthereumClaimsRegistryInterface;
import com.idhub.magic.common.contracts.EthereumDIDRegistryInterface;
import com.idhub.magic.common.contracts.IdentityRegistryInterface;
import com.idhub.magic.common.service.DeployedContractAddress;

public class ContractManager {

    private static final Logger log = LoggerFactory.getLogger(ContractManager.class);

    static  Web3j web3j = ProviderFactory.getProvider().web3j();

	static IdentityRegistryInterface registry1484;
	static  ERC1056ResolverInterface resolver1056;
	static  EthereumDIDRegistryInterface registry1056;
	static  EthereumClaimsRegistryInterface registry780;
	static  Credentials credentials;

   static{
       initContract();
       WalletChangeContractObservable.getInstance().addObserver((o, arg) -> initContract());
   }

    private static void initContract() {
        Credentials credentials = ProviderFactory.getProvider().getDefaultCredentials();
        Log.e("LYW", "static initializer: " +credentials.getAddress() );
        ContractGasProvider contractGasProvider = new DefaultGasProvider();
        String identityRegistryInterface = DeployedContractAddress.IdentityRegistryInterface;
        Log.e("LYW", "initContract: " +identityRegistryInterface );
        if (!TextUtils.isEmpty(identityRegistryInterface)) {
            registry1484 = IdentityRegistryInterface.load(identityRegistryInterface,
                    web3j,
                    credentials,
                    contractGasProvider
            );
        }else {
            registry1484 = null;
        }

        String erc1056ResolverInterface = DeployedContractAddress.ERC1056ResolverInterface;
        if (!TextUtils.isEmpty(erc1056ResolverInterface)) {
            resolver1056 = ERC1056ResolverInterface.load(erc1056ResolverInterface,
                    web3j,
                    credentials,
                    contractGasProvider
            );
        }else {
            resolver1056 = null;
        }

        String ethereumDIDRegistryInterface = DeployedContractAddress.EthereumDIDRegistryInterface;
        if (!TextUtils.isEmpty(ethereumDIDRegistryInterface)) {
            registry1056 = EthereumDIDRegistryInterface.load(ethereumDIDRegistryInterface,
                    web3j,
                    credentials,
                    contractGasProvider
            );
        }else {
            registry1056 = null;
        }

        String ethereumClaimsRegistryInterface = DeployedContractAddress.EthereumClaimsRegistryInterface;
        if (!TextUtils.isEmpty(ethereumClaimsRegistryInterface)){
            registry780 = EthereumClaimsRegistryInterface.load(ethereumClaimsRegistryInterface,
                    web3j,
                    credentials,
                    contractGasProvider
            );
        }else {
            registry780 = null;
        }
    }

    static public EthereumDIDRegistryInterface getRegistry1056() {
        return registry1056;
    }


    static public EthereumClaimsRegistryInterface getRegistry780() {
		return registry780;
	}


    static public ERC1056ResolverInterface getResolver1056() {
		return resolver1056;
	}

    static public IdentityRegistryInterface getRegistry1484() {
		return registry1484;
	}

	void send() throws Exception{
       

        // FIXME: Request some Ether for the Rinkeby test network at https://www.rinkeby.io/#faucet
        log.info("Sending 1 Wei ("
                + Convert.fromWei("1", Convert.Unit.ETHER).toPlainString() + " Ether)");
        TransactionReceipt transferReceipt = Transfer.sendFunds(
                web3j, credentials,
                "0x19e03255f667bdfd50a32722df860b1eeaf4d635",  // you can put any address here
                BigDecimal.ONE, Convert.Unit.WEI)  // 1 wei = 10^-18 Ether
                .send();
        log.info("Transaction complete, view it at https://rinkeby.etherscan.io/tx/"
                + transferReceipt.getTransactionHash());

      
    }
}