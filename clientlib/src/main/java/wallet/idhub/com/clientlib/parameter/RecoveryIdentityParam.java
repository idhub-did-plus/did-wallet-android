package wallet.idhub.com.clientlib.parameter;

import java.math.BigInteger;

public class RecoveryIdentityParam {
    public BigInteger ein;
    public String newAssociationAddressPrivateKey;
    public String newAssociationAddress;
    public BigInteger v;
    public byte[] r;
    public byte[] s;
    public BigInteger timestamp;

}
