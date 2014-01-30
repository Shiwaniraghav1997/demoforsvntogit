package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.List;
import java.util.Map;

public class CheckForDownloadResult {

	private Map<String, String> additionalAttributes;
	private List<String> additionalObjectIds;

	public CheckForDownloadResult(Map<String, String> additionalAttributes, List<String> additionalObjectIds) {
		super();
		this.additionalAttributes = additionalAttributes;
		this.additionalObjectIds = additionalObjectIds;
	}
	
	public Map<String, String> getAdditionalAttributes() {
		return additionalAttributes;
	}
	
	public List<String> getAdditionalObjectIds() {
		return additionalObjectIds;
	}
	
	public void setAdditionalAttributes(Map<String, String> additionalAttributes) {
		this.additionalAttributes = additionalAttributes;
	}
	
	public void setAdditionalObjectIds(List<String> additionalObjectIds) {
		this.additionalObjectIds = additionalObjectIds;
	}
}
