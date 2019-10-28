package com.idhub.base.node;


import java.util.ArrayList;
import java.util.List;

public class WalletNodeManager {
    public static List<String> nodes;
    public static final String  MAINNET = "https://mainnet.infura.io";
    public static final String  ROPSTEN = "https://ropsten.infura.io";
    static {
        nodes = new ArrayList<>();
        nodes.add(MAINNET);
        nodes.add(ROPSTEN);
    }

    public static String assetsGetContractAddressToNode(AssetsModel model) {
        String contractAddress = "";
        String node = WalletNoteSharedPreferences.getInstance().getNode();
        if (node.equals(MAINNET)) {
            contractAddress = model.getMainContractAddress();
        } else if (node.equals(ROPSTEN)) {
            contractAddress = model.getRopstenContractAddress();
        }
        return contractAddress;
    }

}
