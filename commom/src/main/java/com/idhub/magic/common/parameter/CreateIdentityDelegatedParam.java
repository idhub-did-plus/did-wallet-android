package com.idhub.magic.common.parameter;
// public RemoteCall<TransactionReceipt> createIdentityDelegated(String recoveryAddress, String associatedAddress, List<String> providers, List<String> resolvers, BigInteger v, byte[] r, byte[] s, BigInteger timestamp) {
import java.util.List;

public class CreateIdentityDelegatedParam {
	public String recoveryAddress;//address的 的hexcode,可以带0x
	public String associatedAddress;
	public List<String> providers;
	public List<String> resolvers;
	public String v;//hex
	public String  r;//hex
	public String s;//hex
	public String timestamp;//hex
	public boolean async = true;
}
