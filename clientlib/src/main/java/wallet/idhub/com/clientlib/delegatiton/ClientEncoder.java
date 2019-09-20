package wallet.idhub.com.clientlib.delegatiton;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import wallet.idhub.com.clientlib.ProviderFactory;
import com.idhub.magic.common.parameter.CreateIdentityDelegatedParam;
import com.idhub.magic.common.service.DeployedContractAddress;
import com.idhub.magic.common.util.CryptoUtil;

public class ClientEncoder {

	static public CreateIdentityDelegatedParam encode(CreateIdentityDelegatedParam param) {
		
		Address asso = new Address(param.associatedAddress);
		Address rec = new Address(param.recoveryAddress);
		List<BigInteger> providers = toAddresses(param.providers);
		List<BigInteger> resolvers = toAddresses(param.resolvers);
		BigInteger tst = Numeric.toBigInt(param.timestamp);
		Address contract = new Address(DeployedContractAddress.IdentityRegistryInterface);
		byte[] hexMessage = CryptoUtil.encodePacked((byte) 0x19, (byte) 0x00,
				contract,
				"I authorize the creation of an Identity on my behalf.", 
				rec, 
				asso,
				providers,
				resolvers,
				tst

		); 
		
		/*
		 * byte[] hexMessage1 = CryptoUtil.soliditySha3((byte) 0x19, (byte) 0x00, new
		 * Address(DeployedContractAddress.IdentityRegistryInterface),
		 * "I authorize the creation of an Identity on my behalf.", asso, asso,
		 * Numeric.toBigInt(DeployedContractAddress.ERC1056ResolverInterface), tst
		 * 
		 * );
		 */
		Credentials credentials = ProviderFactory.getProvider().getByAddress(param.associatedAddress);
		ECKeyPair pair = credentials.getEcKeyPair();

		Sign.SignatureData sm = Sign.signMessage(hexMessage, pair);
		String r = Numeric.toHexString(sm.getR());
		String s = Numeric.toHexString(sm.getS());
		String v = String.valueOf(sm.getV());
		param.r = r;
		param.s = s;
		param.v = v;
		return param;
	}
	static List<BigInteger> toAddresses(List<String> as){
		List<BigInteger> rst = new ArrayList<BigInteger>();
		for(String s : as) {
			rst.add(Numeric.toBigInt(s));
		}
		return rst;
	}

}
