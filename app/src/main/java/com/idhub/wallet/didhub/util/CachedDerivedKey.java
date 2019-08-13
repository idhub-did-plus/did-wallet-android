package com.idhub.wallet.didhub.util;


import com.idhub.wallet.didhub.crypto.Hash;
import com.idhub.wallet.didhub.util.NumericUtil;

public class CachedDerivedKey {
  private String hashedPassword;
  private byte[] derivedKey;


  public CachedDerivedKey(String password, byte[] derivedKey) {
    this.hashedPassword = hash(password);
    this.derivedKey = derivedKey;
  }

  private String hash(String password) {
    return NumericUtil.bytesToHex(Hash.sha256(Hash.sha256(password.getBytes())));
  }

  public byte[] getDerivedKey(String password) {
    if (hashedPassword != null && hashedPassword.equals(hash(password))) {
      return derivedKey;
    }
    return null;
  }
}
