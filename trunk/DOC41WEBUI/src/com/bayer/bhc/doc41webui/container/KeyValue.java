package com.bayer.bhc.doc41webui.container;

import java.io.Serializable;

public class KeyValue implements Serializable {

	private static final long serialVersionUID = -6018502012376761790L;
	private String key;
	private String value;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
