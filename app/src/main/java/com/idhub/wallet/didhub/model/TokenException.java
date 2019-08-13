package com.idhub.wallet.didhub.model;

public class TokenException extends RuntimeException {

  public TokenException(String message) {
    super(message);
  }

  public TokenException(String message, Exception e) {
    super(message, e);
  }

}
