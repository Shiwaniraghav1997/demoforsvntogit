package com.bayer.bhc.doc41webui.usecase.documenttypes;

public class CheckForUpdateResult {

	private String sapObject;
	private String vkOrg;
	
	public CheckForUpdateResult(String sapObject, String vkOrg) {
		super();
		this.sapObject = sapObject;
		this.vkOrg = vkOrg;
	}
	public String getSapObject() {
		return sapObject;
	}
	public String getVkOrg() {
		return vkOrg;
	}
}
