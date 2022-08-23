package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.List;
import java.util.Map;

public class CheckForDownloadResult {

	private Map<String, String> additionalAttributes;
	private List<String> additionalObjectIds;
	private List<String> materialList;
	public CheckForDownloadResult(Map<String, String> additionalAttributes, List<String> additionalObjectIds, List<String> materialList) {
		super();
		this.additionalAttributes = additionalAttributes;
		this.additionalObjectIds = additionalObjectIds;
		this.materialList=materialList;
	}
	
	public CheckForDownloadResult() {
		// TODO Auto-generated constructor stub
	}

	public List<String> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<String> materialList) {
		this.materialList = materialList;
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
