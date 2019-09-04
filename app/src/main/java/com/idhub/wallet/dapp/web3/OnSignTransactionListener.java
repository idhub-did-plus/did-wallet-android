package com.idhub.wallet.dapp.web3;

import com.idhub.wallet.dapp.entity.Transaction;

public interface OnSignTransactionListener {
    void onSignTransaction(Transaction transaction);
}
