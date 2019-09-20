package com.idhub.magic.common.ustorage.entity;

public class Material {

	public Material(String identity, String type, String name, byte[] data) {
		super();

		this.identity = identity;
		this.type = type;
		this.name = name;
		this.data = data;
	}
	public Material() {
		// TODO Auto-generated constructor stub
	}


	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	String identity;
	String type;
	String name;
	byte[] data;
	

}
