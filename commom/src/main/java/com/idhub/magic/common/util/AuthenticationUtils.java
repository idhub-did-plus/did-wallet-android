package com.idhub.magic.common.util;

import java.io.IOException;
import java.security.SignatureException;


import org.java_websocket.util.Base64;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;


public class AuthenticationUtils {
	
	static public Signature sig(String plainMessage,Credentials credentials) {
		byte[] hexMessage = Hash.sha3(plainMessage.getBytes());
		System.out.println(new String(hexMessage));
		ECKeyPair pair = credentials.getEcKeyPair();
		Sign.SignatureData signMessage = Sign.signMessage(hexMessage, pair);
		String r = Base64.encodeBytes(signMessage.getR());
		String s = Base64.encodeBytes(signMessage.getS());
		int v = (int)signMessage.getV();
		return new Signature(r, s, v);
	}
	
	public static boolean authenticate(Signature sig, String original, String address) throws IOException {
		
		byte v = (byte) sig.v;
		byte[] r = Base64.decode(sig.r);
		
		byte[] s = Base64.decode(sig.s);
		
		byte[] hexMessage = Hash.sha3(original.getBytes());
		
		Sign.SignatureData signMessage = new Sign.SignatureData((byte)sig.v, r, s);
		
		
		String pubKey;
		try {
			pubKey = Sign.signedMessageToKey(hexMessage, signMessage).toString(16);
		
			String signerAddress = "0x" + Keys.getAddress(pubKey);
			
			return signerAddress.equalsIgnoreCase(address);
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	
	}


}
