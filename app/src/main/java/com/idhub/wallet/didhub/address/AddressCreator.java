package com.idhub.wallet.didhub.address;

public interface AddressCreator {
    String fromPrivateKey(String prvKeyHex);
    String fromPrivateKey(byte[] prvKeyBytes);
}
