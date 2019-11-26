package com.idhub.base.node;


import com.idhub.base.App;
import com.idhub.config.ConfigPropertiesUtils;

import java.util.ArrayList;
import java.util.List;

public class WalletNodeManager {
    public static List<String> nodes = new ArrayList<>();
    public static final String  MAINNET = "https://mainnet.infura.io";
    static {
        nodes.clear();
        nodes.addAll(ConfigPropertiesUtils.getNotes(App.getInstance()));
    }

    public static String assetsGetContractAddressToNode(AssetsModel model) {
        String contractAddress = "";
        String node = WalletNoteSharedPreferences.getInstance().getNode();
        if (node.equals(MAINNET)) {
            contractAddress = model.getMainContractAddress();
        } else {
            contractAddress = model.getRopstenContractAddress();
        }
        return contractAddress;
    }

}
