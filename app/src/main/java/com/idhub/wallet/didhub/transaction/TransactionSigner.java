package com.idhub.wallet.didhub.transaction;


public interface TransactionSigner {
  TxSignResult signTransaction(String chainID,String privateKey );
}
