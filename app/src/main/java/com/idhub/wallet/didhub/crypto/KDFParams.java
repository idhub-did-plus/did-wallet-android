package com.idhub.wallet.didhub.crypto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by xyz on 2018/2/2.
 */


interface KDFParams {
  int DK_LEN = 32;

  int getDklen();

  String getSalt();

  @JsonIgnore
  void validate();
}
