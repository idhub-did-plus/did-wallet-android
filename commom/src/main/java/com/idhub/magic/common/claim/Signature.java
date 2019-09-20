package com.idhub.magic.common.claim;

public class Signature {
	String type;
	String creator;
	String created;
	String domain;
	String nonce;
	String signatureValue;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getNonce() {
		return nonce;
	}
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}
	public String getSignatureValue() {
		return signatureValue;
	}
	public void setSignatureValue(String signatureValue) {
		this.signatureValue = signatureValue;
	}
}
