package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Map;

public class CheckForUpdateResult {

	private String sapObject;
	private String vkOrg;
	private Map<String, String> additionalAttributes;
	
	
	public CheckForUpdateResult(String sapObject, String vkOrg,
			Map<String, String> additionalAttributes) {
		super();
		this.sapObject = sapObject;
		this.vkOrg = vkOrg;
		this.additionalAttributes = additionalAttributes;
	}
	public String getSapObject() {
		return sapObject;
	}
	public String getVkOrg() {
		return vkOrg;
	}
	public Map<String, String> getAdditionalAttributes() {
		return additionalAttributes;
	}
	public void setSapObject(String sapObject) {
		this.sapObject = sapObject;
	}
}
