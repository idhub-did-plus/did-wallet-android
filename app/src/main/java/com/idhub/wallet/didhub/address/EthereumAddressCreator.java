package com.idhub.wallet.didhub.address;

import com.idhub.wallet.didhub.crypto.Hash;
import com.idhub.wallet.didhub.util.NumericUtil;

import org.bitcoinj.core.ECKey;

import java.math.BigInteger;
import java.util.Arrays;



public class EthereumAddressCreator implements AddressCreator {

  private static final int PUBLIC_KEY_SIZE = 64;
  private static final int PUBLIC_KEY_LENGTH_IN_HEX = PUBLIC_KEY_SIZE << 1;
  private static final int ADDRESS_LENGTH = 20;
  private static final int ADDRESS_LENGTH_IN_HEX = ADDRESS_LENGTH << 1;


  public String fromPublicKey(BigInteger publicKey) {
    byte[] pubKeyBytes = NumericUtil.bigIntegerToBytesWithZeroPadded(publicKey, PUBLIC_KEY_SIZE);
    return publicKeyToAddress(pubKeyBytes);
  }

  private String publicKeyToAddress(byte[] pubKeyBytes) {
    byte[] hashedBytes = Hash.keccak256(pubKeyBytes);
    byte[] addrBytes = Arrays.copyOfRange(hashedBytes, hashedBytes.length - 20, hashedBytes.length);
    return NumericUtil.bytesToHex(addrBytes);
  }

  @Override
  public String fromPrivateKey(String prvKeyHex) {
    ECKey key = ECKey.fromPrivate(NumericUtil.hexToBytes(prvKeyHex), false);
    return fromECKey(key);
  }

  @Override
  public String fromPrivateKey(byte[] prvKeyBytes) {
    ECKey key = ECKey.fromPrivate(prvKeyBytes, false);
    return fromECKey(key);
  }

  private String fromECKey(ECKey key) {
    byte[] pubKeyBytes = key.getPubKey();
    return publicKeyToAddress(Arrays.copyOfRange(pubKeyBytes, 1, pubKeyBytes.length));
  }
}
