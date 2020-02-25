package com.idhub.base.net;


import java.io.Serializable;

public class HttpNoResult implements Serializable {
  private int code;
  private String message;


  public int getCode() {
    return code;
  }

  public HttpNoResult setCode(int code) {
    this.code = code;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public HttpNoResult setMessage(String message) {
    this.message = message;
    return this;
  }

  @Override
  public String toString() {
    return "HttpNoResult{" + "code=" + code + ", msg='" + message + '\'' + '}';
  }
}
