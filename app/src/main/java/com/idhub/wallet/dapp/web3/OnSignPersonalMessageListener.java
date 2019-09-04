package com.idhub.wallet.dapp.web3;

import com.idhub.wallet.dapp.entity.Message;

public interface OnSignPersonalMessageListener {
    void onSignPersonalMessage(Message<String> message);
}
