package com.idhub.wallet.didhub.model;

public class Messages {

  public static final String MNEMONIC_BAD_WORD = "助记词无效";
  public static final String MNEMONIC_INVALID_LENGTH = "助记词长度无效";
  public static final String MNEMONIC_CHECKSUM = "助记词校验和无效";

  public static final String KDF_PARAMS_INVALID = "kdf_params_invalid";
  public static final String PRF_UNSUPPORTED = "prf_unsupported";
  public static final String SCRYPT_PARAMS_INVALID = "scrypt_params_invalid";
  public static final String CIPHER_FAIL = "cipher_unsupported";

  public static final String WALLET_SHA256 = "sha256";
  public static final String WALLET_INVALID = "keystore_invalid";
  public static final String WALLET_INVALID_PASSWORD = "password_incorrect";
  public static final String WALLET_STORE_FAIL = "store_wallet_failed";
  public static final String WALLET_INVALID_KEYSTORE = "keystore_invalid";

  public static final String ILLEGAL_OPERATION = "illegal_operation";
  public static final String WALLET_EXISTS = "address_already_exist";
//  public static final String WALLET_INVALID_ADDRESS = "address_invalid";

  public static final String MAC_UNMATCH = "mac_unmatch";
  public static final String PRIVATE_KEY_ADDRESS_NOT_MATCH = "private_key_address_not_match";
  public static final String PRIVATE_KEY_INVALID = "privatekey_invalid";
  public static final String KEYSTORE_CONTAINS_INVALID_PRIVATE_KEY = "keystore_contains_invalid_private_key";

}
