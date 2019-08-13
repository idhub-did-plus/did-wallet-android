package com.idhub.wallet.didhub.crypto;

public class EncPair {
  private String encStr;
  private String nonce;

  public String getNonce() {
    return nonce;
  }

  public void setNonce(String nonce) {
    this.nonce = nonce;
  }

  public String getEncStr() {
    return encStr;
  }

  public void setEncStr(String encStr) {
    this.encStr = encStr;
  }

  @Override
  public String toString() {
    return "EncPair{" +
            "encStr='" + encStr + '\'' +
            ", nonce='" + nonce + '\'' +
            '}';
  }
}
