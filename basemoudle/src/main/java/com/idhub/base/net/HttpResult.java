package com.idhub.base.net;

import java.io.Serializable;


public class HttpResult<T> implements Serializable {
  private int status;

  private T data;

  private String message;


  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "HttpResult{" + "status=" + status + ", msg='" + message + '\'' + ", data=" + data + '}';
  }
}
