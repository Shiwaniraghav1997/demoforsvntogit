package com.bayer.bhc.doc41webui.container;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Attribute implements Serializable {
	
	private static final long serialVersionUID = -6971406455274185279L;
	
	private String name;
	private Integer seqNumber;
	private String desc;
	private Boolean mandatory;
	private Map<String,String> translations;
	private List<String> values;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSeqNumber() {
		return seqNumber;
	}

	public void setSeqNumber(Integer seqNumber) {
		this.seqNumber = seqNumber;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Boolean getMandatory() {
		return mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}
	
	public void setTranslations(Map<String, String> translations) {
		this.translations = translations;
	}
	
	public String getTranslation(String language){
		if(translations!=null){
			return translations.get(language);
		}
		return null;
	}
	
	public List<String> getValues() {
		return values;
	}
	
	public void setValues(List<String> values) {
		this.values = values;
	}
}
