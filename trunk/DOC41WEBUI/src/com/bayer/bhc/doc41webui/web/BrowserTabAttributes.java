package com.bayer.bhc.doc41webui.web;

import java.io.Serializable;

public class BrowserTabAttributes implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String tabId;

	private long timestamp =System.currentTimeMillis();
	
	public BrowserTabAttributes(){
	}
	
	public String getTabId() {
		return tabId;
	}

	public void setTabId(String tabId) {
		this.tabId = tabId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	
	

}
