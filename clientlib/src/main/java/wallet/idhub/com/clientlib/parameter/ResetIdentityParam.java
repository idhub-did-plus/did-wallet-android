package wallet.idhub.com.clientlib.parameter;

import java.math.BigInteger;

public class ResetIdentityParam {

    public String identity;
    public BigInteger noce;
    public String privateKey;
    public BigInteger v;
    public byte[] r;
    public byte[] s;
}
