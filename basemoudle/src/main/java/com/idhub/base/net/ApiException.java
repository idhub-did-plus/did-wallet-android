package com.idhub.base.net;


public class ApiException extends Exception {
  private int code;

  @SuppressWarnings("unused")
  public ApiException(int code) {
    this.code = code;
  }

  public ApiException(int code, String message) {
    super(message);
    this.code = code;
  }

  public int getCode() {
    return code;
  }

  public ApiException setCode(int code) {
    this.code = code;
    return this;
  }
}
