package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Map;

public class CheckForDownloadResult {

	private Map<String, String> additionalAttributes;

	public CheckForDownloadResult(Map<String, String> additionalAttributes) {
		super();
		this.additionalAttributes = additionalAttributes;
	}
	
	public Map<String, String> getAdditionalAttributes() {
		return additionalAttributes;
	}
}
