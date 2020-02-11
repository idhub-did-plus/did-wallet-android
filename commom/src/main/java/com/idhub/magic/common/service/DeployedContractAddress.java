package com.idhub.magic.common.service;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.idhub.base.App;
import com.idhub.base.node.WalletChangeContractObservable;
import com.idhub.base.node.WalletNodeManager;
import com.idhub.base.node.WalletNodeSelectedObservable;
import com.idhub.base.node.WalletNoteSharedPreferences;
import com.idhub.config.ConfigPropertiesUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeployedContractAddress {
    static public String IdentityRegistryInterface = "";
    static public String ERC1056ResolverInterface = "";
    static public String EthereumDIDRegistryInterface = "";
    static public String EthereumClaimsRegistryInterface = "";
    private static Map<String, ContractAddress> maps = new HashMap<>();

    static {
        List<ContractAddress> contractAddresses = new Gson().fromJson(ConfigPropertiesUtils.getContractAddress(App.getInstance()), new TypeToken<List<ContractAddress>>() {
        }.getType());
        for (ContractAddress contractAddress : contractAddresses) {
            maps.put(contractAddress.getNode(), contractAddress);
        }
        initAddress();
        WalletNodeSelectedObservable.getInstance().addObserver((o, arg) -> {
            initAddress();
        });
    }

    static void initAddress() {
        String node = WalletNoteSharedPreferences.getInstance().getNode();
        ContractAddress contractAddress = maps.get(node);
        if (contractAddress != null) {
            IdentityRegistryInterface = contractAddress.getERC1484();
            ERC1056ResolverInterface = contractAddress.getResolver1056();
            EthereumDIDRegistryInterface = contractAddress.getRegistry1056();
            EthereumClaimsRegistryInterface = contractAddress.getERC780();
        } else {
            IdentityRegistryInterface = "";
            ERC1056ResolverInterface = "";
            EthereumDIDRegistryInterface = "";
            EthereumClaimsRegistryInterface = "";
        }
        Log.e("LYW", "initAddress: " + IdentityRegistryInterface + " "+ ERC1056ResolverInterface+" " +EthereumDIDRegistryInterface+" "+EthereumClaimsRegistryInterface);
        WalletChangeContractObservable.getInstance().update();
    }


    public class ContractAddress {
        private String node;
        private String ERC1484;
        private String resolver1056;
        private String registry1056;
        private String ERC780;

        public String getNode() {
            return node;
        }

        public void setNode(String node) {
            this.node = node;
        }

        public String getERC1484() {
            return ERC1484;
        }

        public void setERC1484(String ERC1484) {
            this.ERC1484 = ERC1484;
        }

        public String getResolver1056() {
            return resolver1056;
        }

        public void setResolver1056(String resolver1056) {
            this.resolver1056 = resolver1056;
        }

        public String getRegistry1056() {
            return registry1056;
        }

        public void setRegistry1056(String registry1056) {
            this.registry1056 = registry1056;
        }

        public String getERC780() {
            return ERC780;
        }

        public void setERC780(String ERC780) {
            this.ERC780 = ERC780;
        }

        @Override
        public String toString() {
            return "ContractAddress{" +
                    "node='" + node + '\'' +
                    ", ERC1484='" + ERC1484 + '\'' +
                    ", resolver1056='" + resolver1056 + '\'' +
                    ", registry1056='" + registry1056 + '\'' +
                    ", ERC780='" + ERC780 + '\'' +
                    '}';
        }
    }
}
