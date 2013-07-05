package com.bayer.bhc.doc41webui.container;

import java.util.Map;

public class DocTypeDef {
	
	private String d41id;
	private String technicalId;
	private String description;
	private String sapObj;
	private Map<String, String> translations;
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
	public String getSapObj() {
		return sapObj;
	}
	public void setSapObj(String sapObj) {
		this.sapObj = sapObj;
	}
	public String getTranslation(String language) {
		if(translations==null){
			return null;
		}
		return translations.get(language);
	}
	public void setTranslations(Map<String, String> translations) {
		this.translations = translations;
	}

	
}
