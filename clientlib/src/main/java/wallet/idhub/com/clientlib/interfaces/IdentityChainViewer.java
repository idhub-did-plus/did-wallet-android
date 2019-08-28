package wallet.idhub.com.clientlib.interfaces;

public interface IdentityChainViewer {
	public Listen<Long> getEIN(String associate);
	public Listen<Identity> getIdentity(long ein);
}
