package com.idhub.magic.common.eip712;




public class TypedData {
    public TypedData(String n, String t, Object v) {
		name = n;
		type = t;
		value = v;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	private String name;
    private String type;
    private Object value; 
}
