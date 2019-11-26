package com.idhub.magic.common.service;

import com.idhub.base.node.WalletChangeContractObservable;
import com.idhub.base.node.WalletNodeManager;
import com.idhub.base.node.WalletNodeSelectedObservable;
import com.idhub.base.node.WalletNoteSharedPreferences;

public class DeployedContractAddress {
    static public String IdentityRegistryInterface = "";
    static public String ERC1056ResolverInterface = "";
    static public String EthereumDIDRegistryInterface = "";
    static public String EthereumClaimsRegistryInterface = "";

    static {
        initAddress();
        WalletNodeSelectedObservable.getInstance().addObserver((o, arg) -> {
            initAddress();
        });
    }

    static void initAddress() {
        String node = WalletNoteSharedPreferences.getInstance().getNode();
        if (WalletNodeManager.MAINNET.equals(node)) {
            IdentityRegistryInterface = "";
            ERC1056ResolverInterface = "";
            EthereumDIDRegistryInterface = "";
            EthereumClaimsRegistryInterface = "";
        } else {
            IdentityRegistryInterface = "0x90e1B1C7B8C829b3d0b1C09eD961e46f5AeeD184";
            ERC1056ResolverInterface = "0x6d0f04B6Ca0217323af7fB7147a63C97Ef910617";
            EthereumDIDRegistryInterface = "0x42197FC11ad0765567e1b552d4063464DE938923";
            EthereumClaimsRegistryInterface = "0xaC7A17ecBab2694FB0b3dE4a93dA76be99E58604";
        }
        WalletChangeContractObservable.getInstance().update();
    }


}
