package com.bayer.bhc.doc41webui.domain;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DocTypeDef {
	
	private String d41id;
	private String technicalId;
	private String description;
	private List<String> sapObjList;
	private Map<String, String> translations;
	private boolean isDvs;
	public String getD41id() {
		return d41id;
	}
	public void setD41id(String d41id) {
		this.d41id = d41id;
	}
	public String getTechnicalId() {
		return technicalId;
	}
	public void setTechnicalId(String technicalId) {
		this.technicalId = technicalId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<String> getSapObjList() {
		return sapObjList;
	}
	public void setSapObjList(List<String> sapObjList) {
		this.sapObjList = sapObjList;
	}
	public String getTranslation(String language) {
		if(translations==null){
			return null;
		}
		return translations.get(language);
	}
	public Map<String, String> getTranslations() {
		if(translations==null){
			return null;
		}
		return Collections.unmodifiableMap(translations);
	}
	public void setTranslations(Map<String, String> translations) {
		this.translations = translations;
	}
	public boolean isDvs() {
		return isDvs;
	}
	public void setDvs(boolean isDvs) {
		this.isDvs = isDvs;
	}
	@Override
	public String toString() {
		return "DocTypeDef [d41id=" + d41id + ", technicalId=" + technicalId
				+ ", description=" + description + ", sapObjList=" + sapObjList
				+ ", translations=" + translations + ", isDvs=" + isDvs + "]";
	}

	
}
