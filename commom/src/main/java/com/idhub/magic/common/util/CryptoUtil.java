package com.idhub.magic.common.util;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint64;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetCode;
import org.web3j.rlp.RlpEncoder;
import org.web3j.rlp.RlpList;
import org.web3j.rlp.RlpString;
import org.web3j.utils.Numeric;

import com.google.common.base.Preconditions;



public class CryptoUtil {
    private static final BigInteger MASK_256 = BigInteger.ONE.shiftLeft(256).subtract(BigInteger.ONE);
    private static final String ALGORITHM = "ECDSA";
    private static final String CURVE_NAME = "secp256k1";
    
    private static final ECDomainParameters dp;
    private static final ECCurve curve;

    private static final ECNamedCurveSpec p;
    public static final int SIGNATURE_LENGTH = 65;

    static {
        X9ECParameters xp = ECUtil.getNamedCurveByName(CURVE_NAME);
        p = new ECNamedCurveSpec(CURVE_NAME, xp.getCurve(), xp.getG(), xp.getN(), xp.getH(), null);
        curve = EC5Util.convertCurve(p.getCurve());
        org.bouncycastle.math.ec.ECPoint g = EC5Util.convertPoint(curve, p.getGenerator(), false);
        BigInteger n = p.getOrder();
        BigInteger h = BigInteger.valueOf(p.getCofactor());
        dp = new ECDomainParameters(curve, g, n, h);
    }

    public static String getContractAddress(String fromAddress, long nonce) {
        byte[] bytes = RlpEncoder.encode(new RlpList(RlpString.create(Numeric.hexStringToByteArray(Numeric.cleanHexPrefix(fromAddress))), RlpString.create(nonce)));
        byte[] sha3 = Hash.sha3(bytes);
        String hash = Numeric.toHexStringNoPrefix(sha3);
        return hash.substring(24);
    }

    public static String getContractCode(Web3j web3j, String contractAddress) throws IOException {
        EthGetCode ethGetCode = web3j
            .ethGetCode(contractAddress, DefaultBlockParameterName.LATEST)
            .send();

        if (ethGetCode.hasError()) {
            throw new IllegalStateException("Failed to get code for " + contractAddress + ": " + ethGetCode.getError().getMessage());
        }

        return Numeric.cleanHexPrefix(ethGetCode.getCode());
    }

    public static byte[] sign(ECKeyPair keyPair, byte[] hash) {
        Sign.SignatureData signatureData = Sign.signMessage(hash, keyPair);
        return signatureEncode(signatureData);
    }

    public static Address verifySignature(byte[] signature, byte[] hash) throws SignatureException {
        Sign.SignatureData signatureData = signatureSplit(signature);
        BigInteger publicKey = Sign.signedMessageToKey(hash, signatureData);
        return getAddress(publicKey);
    }

    public static Address getAddress(BigInteger publicKey) {
        //TODO get rid of conversions string<->byte[]
        return new Address(Keys.getAddress(publicKey));
    }

    public static byte[] toBytes(Object obj) {
    	if (obj instanceof List) {
    		List ll = (List)obj;
    		if(ll.isEmpty())
    			return new byte[0];
    		List<byte[]> arrays = new ArrayList<byte[]>();
    		for(Object o : ll)
    		{
    			arrays.add(toBytes(o));
    		}
            int sum = 0;
            for (byte[] array : arrays) {
                int length = array.length;
                sum += length;
            }
            ByteBuffer buffer = ByteBuffer.allocate(sum);
            for (byte[] a : arrays) {
                buffer.put(a);
            }
   		 	return buffer.array();
   	 	}
    	 if (obj instanceof String) {
    		 return ((String)obj).getBytes();
    	 }
    	  if (obj instanceof Byte) {
    		  return new byte[] {(byte)(obj)};
    	  }
        if (obj instanceof byte[]) {
            int length = ((byte[]) obj).length;
            Preconditions.checkArgument(length <= 32);
            if (length < 32) {
                return Arrays.copyOf((byte[]) obj, 32);
            }
            return (byte[]) obj;
        } else if (obj instanceof BigInteger) {
            BigInteger value = (BigInteger) obj;
            if (value.signum() < 0) {
                value = MASK_256.and(value);
            }
            return Numeric.toBytesPadded(value, 32);
        } else if (obj instanceof Address) {
            Uint uint = ((Address) obj).toUint160();
            return Numeric.toBytesPadded(uint.getValue(), 20);
        } else if (obj instanceof Uint256) {
            Uint uint = (Uint) obj;
            return Numeric.toBytesPadded(uint.getValue(), 32);
        } else if (obj instanceof Uint64) {
            Uint uint = (Uint) obj;
            return Numeric.toBytesPadded(uint.getValue(), 8);
        } else if (obj instanceof Number) {
            long l = ((Number) obj).longValue();
            return toBytes(BigInteger.valueOf(l));
        }
        throw new IllegalArgumentException(obj.getClass().getName());
    }

    public static byte[] signatureEncode(Sign.SignatureData signatureData) {
        Preconditions.checkArgument(signatureData.getR().length == 32);
        Preconditions.checkArgument(signatureData.getS().length == 32);
        Preconditions.checkArgument(signatureData.getV() == 27 || signatureData.getV() == 28);
        ByteBuffer buffer = ByteBuffer.allocate(SIGNATURE_LENGTH);
        buffer.put(signatureData.getR());
        buffer.put(signatureData.getS());
        buffer.put(signatureData.getV());
        assert buffer.position() == SIGNATURE_LENGTH;
        return buffer.array();
    }

    public static Sign.SignatureData signatureSplit(byte[] signature) {
        Preconditions.checkArgument(signature.length == SIGNATURE_LENGTH);
        byte v = signature[64];
        Preconditions.checkArgument(v == 27 || v ==28);
        byte[] r = Arrays.copyOfRange(signature, 0, 32);
        byte[] s = Arrays.copyOfRange(signature, 32, 64);
        return new Sign.SignatureData(v, r, s);
    }

    public static byte[] encodePacked(Object... data) {
        if (data.length == 1) {
            return Hash.sha3(toBytes(data[0]));
        }
        List<byte[]> arrays = new ArrayList<>();
        for (Object datum : data) {
            byte[] bytes = toBytes(datum);
            arrays.add(bytes);
        }
        int sum = 0;
        for (byte[] bytes : arrays) {
            int length = bytes.length;
            sum += length;
        }
        ByteBuffer buffer = ByteBuffer.allocate(sum);
        for (byte[] a : arrays) {
            buffer.put(a);
        }
        byte[] array = buffer.array();
        assert buffer.position() == array.length;
        System.out.println(Numeric.toHexString(array));
       // return Hash.sha3(array);
        return array;
    }
    public static byte[] soliditySha3(Object... data) {
    	 byte[] array = encodePacked(data);
    	 return Hash.sha3(array);
       
    }
    public static KeyPair decodeKeyPair(ECKeyPair ecKeyPair) {
        byte[] bytes = Numeric.toBytesPadded(ecKeyPair.getPublicKey(), 64);
        BigInteger x = Numeric.toBigInt(Arrays.copyOfRange(bytes, 0, 32));
        BigInteger y = Numeric.toBigInt(Arrays.copyOfRange(bytes, 32, 64));
        ECPoint q = curve.createPoint(x, y);
        BCECPublicKey publicKey = new BCECPublicKey(ALGORITHM, new ECPublicKeyParameters(q, dp), BouncyCastleProvider.CONFIGURATION);
        BCECPrivateKey privateKey = new BCECPrivateKey(ALGORITHM, new ECPrivateKeyParameters(ecKeyPair.getPrivateKey(), dp), publicKey, p, BouncyCastleProvider.CONFIGURATION);
        return new KeyPair(publicKey, privateKey);
    }

    public static void main(String[] args) throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        ECKeyPair ecKeyPair = Keys.createEcKeyPair();
        KeyPair keyPair1 = decodeKeyPair(ecKeyPair);
        System.out.println(toString(keyPair1));
    }

    public static String toString(KeyPair keyPair) {
        return keyPair.getPrivate() + ":" + keyPair.getPublic();
    }
    

   
    public static byte[] sha3(byte[]... input) {
        Keccak.DigestKeccak kecc = new Keccak.Digest256();
        for (byte[] bytes : input) {
            kecc.update(bytes, 0, bytes.length);
        }
        return kecc.digest();
    }

}