package com.idhub.base.node;


import com.idhub.base.App;
import com.idhub.base.greendao.entity.AssetsModel;
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
}
