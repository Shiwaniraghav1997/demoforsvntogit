package com.bayer.bhc.doc41webui.container;

import java.io.Serializable;

public class UserLookupForm implements Serializable {

	private static final long serialVersionUID = 8098557230379149218L;
	private String cwid;
	
	public String getCwid() {
		return cwid;
	}
	public void setCwid(String cwid) {
		this.cwid = cwid;
	}
}
