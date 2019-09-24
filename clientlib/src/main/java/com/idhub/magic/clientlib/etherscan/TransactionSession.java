package com.idhub.magic.clientlib.etherscan;

import java.util.ArrayList;
import java.util.List;

import com.idhub.magic.clientlib.interfaces.IncomingListener;

import io.api.etherscan.core.impl.EtherScanApi;
import io.api.etherscan.model.Tx;
import io.api.etherscan.model.TxToken;

public class TransactionSession {
	 List<String> accounts;

	    EtherScanApi api = new EtherScanApi();
	    IncomingListener<Tx> txlistener;
	    IncomingListener<TxToken> transferlistener;
	    long start;
	    long end;
	    
	    
	    void once() {
	    	 
	    }
	    
	      public TransactionSession(List<String> accounts,IncomingListener<Tx> txlistener, IncomingListener<TxToken> transferlistener, EtherScanApi api,  long start,
				long end) {
			super();
			this.accounts = accounts;

			this.api = api;
			this.start = start;
			this.end = end;
			this.txlistener = txlistener;
			this.transferlistener = transferlistener;
					
		}
	    void execute() {
	    	List<Tx> incomingTxs = new ArrayList<Tx>();
	    	List<TxToken> incomingTokens = new ArrayList<TxToken>();
	    	for(String addr : accounts) {
	    		waiting();
	    		List<Tx> intx = incomingTxs(addr);
	    		incomingTxs.addAll(intx);
	    		waiting();
	    		List<TxToken> ine = incomingTokens(addr);
	    		incomingTokens.addAll(ine);
	    	}
	    
	    	if(txlistener != null)
	    		this.txlistener.income(incomingTxs);
	    	if(this.transferlistener != null)
	    		this.transferlistener.income(incomingTokens);
	    }

		private void waiting() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<Tx> incomingTxs(String address){
	    	 
	    	   List<Tx> txs = api.account().txs(address,start, end);
//	    	   List<Tx> rst = new ArrayList<Tx>();
//
//	    	   for(Tx t : txs) {
//
//	    		  if( t.getTo().equals(address)){
//	    			   rst.add(t);
//	    		   }
//	    	   }
	    	   return txs;
	      }
		List<TxToken> incomingTokens(String address){
	    	 
	    	    List<TxToken> es = api.account().txsToken(address, start, end);
//	    	   List<TxToken> rst = new ArrayList<TxToken>();
//
//	    	   for(TxToken t : es) {
//
//	    		  if( t.getTo().equals(address)){
//	    			   rst.add(t);
//	    		   }
//	    	   }
	    	   return es;
	      }
		
}
