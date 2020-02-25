package com.idhub.base.net;


public interface HttpCode {
  /**
   * 成功.
   */
  int SUCCESS = 200;
  /**
   * 参数为空.
   */
  int NO_PARAMETER = 1;
  /**
   * 服务器错误.
   */
  int SERVER_ERR = 3;
  /**
   *
   */
  int NET_ERRRO = 4;
  /**
   *
   */
  int LOAD_FAILD = 5;
  /**
   *
   */
  int LOAD_NO_DATA = 6;
  /**
   *
   */
  int LOGIN_FAILD = 7;
  /**
   *
   */
  int VERSION_NO_UPDATE = 8;

  /**
   *
   */
  int TOKEN_INVALID = 700;


  int GET_VERIFICATION_CODE_ERRO = 9000;
}
