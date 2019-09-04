package com.idhub.wallet.dapp.web3;

import com.idhub.wallet.dapp.entity.Message;
import com.idhub.wallet.dapp.entity.TypedData;


public interface OnSignTypedMessageListener {
    void onSignTypedMessage(Message<TypedData[]> message);
}
