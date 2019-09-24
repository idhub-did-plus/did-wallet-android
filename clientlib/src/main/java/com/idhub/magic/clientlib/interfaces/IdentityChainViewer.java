package com.idhub.magic.clientlib.interfaces;

import java.math.BigInteger;

public interface IdentityChainViewer {
	public Listen<BigInteger> getEIN(String associate);
	public Listen<Identity> getIdentity(long ein);
}
