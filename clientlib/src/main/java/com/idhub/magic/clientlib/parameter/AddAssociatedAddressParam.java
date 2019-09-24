package com.idhub.magic.clientlib.parameter;

import java.math.BigInteger;

public class AddAssociatedAddressParam {
    public String approvingAddress;
    public String privateKey;
    public String addressToAdd;
    public BigInteger v;
    public BigInteger ein;
    public byte[] r;
    public byte[] s;
    public BigInteger timestamp;
}
