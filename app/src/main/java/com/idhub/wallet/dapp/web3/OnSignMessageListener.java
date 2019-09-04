package com.idhub.wallet.dapp.web3;

import com.idhub.wallet.dapp.entity.Message;

public interface OnSignMessageListener {
    void onSignMessage(Message<String> message);
}
