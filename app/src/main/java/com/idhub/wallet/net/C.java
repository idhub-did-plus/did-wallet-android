package com.idhub.wallet.net;

import com.idhub.wallet.common.sharepreference.WalletOtherInfoSharpreference;

import io.api.etherscan.model.EthNetwork;

public class C {

    public static final String API_BASE = WalletOtherInfoSharpreference.getInstance().getNode();
    public static final EthNetwork ROPSTEN ;

    static {
        ROPSTEN = EthNetwork.ROPSTEN;
    }

}
