package com.idhub.magic.common.claim;

import java.util.ArrayList;
import java.util.List;

public class VerifiableClaim {
	/*"@context" : [ "https://w3id.org/credentials/v1", "https://trafi.fi/credentials/v1", "https://w3id.org/security/v1" ],
	  "type" : [ "Credential", "DriversLicenseCredential" ],
	  "claim" : {
	    "id" : "did:sov:21tDAKCERh95uGgKbJNHYp",
	    "driversLicense" : {
	      "licenseClass" : "trucks"
	    }
	  },
	  "issuer" : "did:sov:1yvXbmgPoUm4dl66D7KhyD",
	  "issued" : "2018-01-01",
	  "signature" : {
	    "type" : "RsaSignature2017",
	    "creator" : "did:sov:1yvXbmgPoUm4dl66D7KhyD#key1",
	    "created" : "2018-01-01T21:19:10Z",
	    "domain" : null,
	    "nonce" : "c0ae1c8e-c7e7-469f-b252-86e6a0e7387e",
	    "signatureValue" : "eyJhbGciOiJSUzI1NiIsImI2NCI6ZmFsc2UsImNyaXQiOlsiYjY0Il19..LuQEd67YJH8auxrU7_SGeCW77tPOmd2CAR41MxG_gSkHbQVOiWtMshtY71AGRgCUu5EVkOHNHLDU4EWSLaPCGOApoEc4TPn2srOi3DyDYZkgPRlUiGGNiy8bBk8Gfli_7qFA053wtAdHNf0VGVrXn0QBxSd7PSN5g2g0CM6TKJWp96WmhwdXAWgpqAhK8qe9tIXORr2TZB4ANR9bCtlcTk8haJawbLda2DHtPY_zSJqAaXzr7qC_vqa2jYRQgS65UA2dsm4du-ajVProniyV1p6Iu82coqDQPELW30hEXyintNRMjK8e_6z-wEsNpUH5ir_H97ciX60vV7e7nKjDRQ"
	  }*/
	String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	List<String> context = new ArrayList<String>();
	String type;
	Claim claim = new Claim();
	String issuer;
	String issued;
	Signature signature = new Signature();
	public Claim getClaim() {
		return claim;
	}
	public List<String> getContext() {
		return context;
	}
	public String getIssued() {
		return issued;
	}
	public String getIssuer() {
		return issuer;
	}
	public Signature getSignature() {
		return signature;
	}
	public String getType() {
		return type;
	}
	public void setClaim(Claim claim) {
		this.claim = claim;
	}
	public void setContext(List<String> context) {
		this.context = context;
	}
	public void setIssued(String issued) {
		this.issued = issued;
	}
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
	public void setSignature(Signature signature) {
		this.signature = signature;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
