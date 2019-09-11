package wallet.idhub.com.clientlib.local;

import android.util.Log;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;

import com.idhub.magic.center.contracts.IdentityRegistryInterface.IdentityCreatedEventResponse;
import com.idhub.magic.center.service.DeployedContractAddress;

import java8.util.concurrent.CompletableFuture;
import wallet.idhub.com.clientlib.ProviderFactory;
import wallet.idhub.com.clientlib.interfaces.ExceptionListener;
import wallet.idhub.com.clientlib.interfaces.Identity;
import wallet.idhub.com.clientlib.interfaces.IdentityChain;
import wallet.idhub.com.clientlib.interfaces.IdentityChainViewer;
import wallet.idhub.com.clientlib.interfaces.Listen;
import wallet.idhub.com.clientlib.interfaces.ResultListener;

public class IdentityChainLocal implements IdentityChain, IdentityChainViewer {

	public BigInteger getEINSync(String associate) throws Exception {
		return ContractManager.getRegistry1484().getEIN(associate).send();
	}

	public Listen<Long> getEIN(String associate) {

		CompletableFuture<BigInteger> data = ContractManager.getRegistry1484().getEIN(associate).sendAsync();
		return new Listen<Long>() {

			@Override
			public void listen(ResultListener<Long> l, ExceptionListener el) {

				data.thenAccept(ein -> {
					Log.e("LYW", "listen: "+ein);
					l.result(ein.longValue());
				}).exceptionally(transactionReceipt -> {
					String message = transactionReceipt.getMessage();
					Log.e("LYW", "listen:error " + message);
					el.error("error!");
					
					return null;
				});
			}
		};
	}

	public Listen<Identity> getIdentity(long ein) {

		CompletableFuture<Tuple4<String, List<String>, List<String>, List<String>>> data = ContractManager
				.getRegistry1484().getIdentity(BigInteger.valueOf(ein)).sendAsync();
		return new Listen<Identity>() {

			@Override
			public void listen(ResultListener<Identity> l, ExceptionListener el) {
				data.thenAccept(id -> {
					Identity identity = new Identity(id.getValue1(), id.getValue2(), id.getValue3(), id.getValue4());
					l.result(identity);

				}).exceptionally(transactionReceipt -> {
					l.result(null);
					return null;
				});

			}
		};

	}

	@Override
	public Listen<IdentityCreatedEventResponse> createIdentity() {
		String rec = ProviderFactory.getProvider().getRecoverAddress();
		String asso = ProviderFactory.getProvider().getDefaultCredentials().getAddress();
		List<String> ps = new ArrayList<String>();
		List<String> rs = new ArrayList<String>();
		rs.add(DeployedContractAddress.IdentityRegistryInterface);

		return createIdentity(rec, asso, ps, rs);

	}

	@Override
	public Listen<IdentityCreatedEventResponse> createIdentity(String recovery, String associate,
			List<String> providers, List<String> resolvers) {
		CompletableFuture<TransactionReceipt> data = ContractManager.getRegistry1484()
				.createIdentity(recovery, providers, resolvers).sendAsync();
		return new Listen<IdentityCreatedEventResponse>() {

			@Override
			public void listen(ResultListener listener, ExceptionListener el) {
				data.thenAccept(transactionReceipt -> {
					List<IdentityCreatedEventResponse> es = ContractManager.getRegistry1484()
							.getIdentityCreatedEvents(transactionReceipt);
					listener.result(es.get(0));

				}).exceptionally(transactionReceipt -> {
					Log.e("LYW", "listen: " );
					transactionReceipt.printStackTrace();
					Log.e("LYW", "listen: ");
					listener.result(null);
					return null;
				});

			}

		};

	}
}