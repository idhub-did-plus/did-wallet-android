package wallet.idhub.com.clientlib.etherscan;

import android.util.Log;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


import io.api.etherscan.core.impl.EtherScanApi;
import io.api.etherscan.model.EthNetwork;
import io.api.etherscan.model.Tx;
import io.api.etherscan.model.TxToken;
import wallet.idhub.com.clientlib.ProviderFactory;
import wallet.idhub.com.clientlib.interfaces.IncomingListener;
import wallet.idhub.com.clientlib.interfaces.IncomingService;

public class Etherscan implements IncomingService {
	static Etherscan instance;
	static {
		instance = new Etherscan();
		
		instance.init();
	}

	public static IncomingService getInstance() {
		return instance;
	}

	static public void main(String[] ss) throws Exception {

		IncomingService scan = Etherscan.getInstance();
		List<String> accs = new ArrayList<String>();
		accs.add("0xddbd2b932c763ba5b1b7ae3b362eac3e8d40121a");
		scan.setAccounts(accs);
		scan.subscribeTransaction(txs -> {
			System.out.println(txs);

		});
		scan.subscribeTransfer(txs -> {
			System.out.println(txs);

		});
	}

	ScheduledExecutorService pool;
	List<String> accounts = new ArrayList<String>();

	Map<EthNetwork, EtherScanApi> apis = new HashMap<EthNetwork, EtherScanApi>();
	IncomingListener<Tx> txlistener;
	IncomingListener<TxToken> transferlistener;
	
	EthNetwork current = EthNetwork.ROPSTEN;
	@Override
	public void setCurrentNetwork(EthNetwork c) {
		current = c;
	}
	
	void init() {
		try {
			
			disableSslCheck();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(EthNetwork v : EthNetwork.values()) {
			apis.put(v, new EtherScanApi(v));
		}
		
		pool = Executors.newScheduledThreadPool(1);
		pool.scheduleAtFixedRate(() -> {
			try {
				once();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}, 0, 15, TimeUnit.SECONDS);
	}

	private void disableSslCheck() throws Exception {
		SSLContext sc = SSLContext.getInstance("TLS");
		sc.init(null, new TrustManager[] { new TrustAllX509TrustManager() }, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String string, SSLSession ssls) {
				return true;
			}
		});

	}
	EtherScanApi api() {
		return this.apis.get(current);
	}
	void once() throws Exception {
		long start = 0;// ProviderFactory.getProvider().getLastEndBlockNumber();
		long end = 99999999l;// ProviderFactory.getProvider().web3j().ethBlockNumber().send().getBlockNumber().longValue();
		TransactionSession sess = new TransactionSession(accounts, txlistener, transferlistener, api(), start, end);
		try {
			sess.execute();
			ProviderFactory.getProvider().storeLastEndBlockNumber(end);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setAccounts(List<String> accounts) {
		this.accounts = accounts;
	}

	@Override
	public void subscribeTransaction(IncomingListener<Tx> txl) {
		txlistener = txl;
	}

	@Override
	public void subscribeTransfer(IncomingListener<TxToken> txl) {
		transferlistener = txl;
	}

}

class TrustAllX509TrustManager implements X509TrustManager {
	public X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[0];
	}

	public void checkClientTrusted(X509Certificate[] certs, String authType) {
	}

	public void checkServerTrusted(X509Certificate[] certs, String authType) {
	}

}