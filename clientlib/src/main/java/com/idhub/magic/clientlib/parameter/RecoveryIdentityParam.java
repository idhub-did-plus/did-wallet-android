package com.idhub.magic.clientlib.parameter;

import java.math.BigInteger;

public class RecoveryIdentityParam {
    public BigInteger ein;
    public String privateKey;
    public String newAssociationAddress;
    public BigInteger v;
    public byte[] r;
    public byte[] s;
    public BigInteger timestamp;

}
