package com.idhub.wallet.network;

import io.api.etherscan.model.EthNetwork;

public class C {

    public static final String API_BASE = "https://ropsten.infura.io/";
    public static final EthNetwork ROPSTEN ;

    static {
        ROPSTEN = EthNetwork.ROPSTEN;
    }

}
