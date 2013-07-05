package com.bayer.bhc.doc41webui.container;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attribute implements Serializable {
	
	private static final long serialVersionUID = -6971406455274185279L;
	
	private String name;
	private Integer seqNumber;
	private String desc;
	private Boolean mandatory;
	private Map<String,String> translations = new HashMap<String, String>();
	private List<String> values;
	private String tempLabel;
	
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
	
	public void addTranslation(String label,String language){
		translations.put(language, label);
	}
	
	public String getTranslation(String language){
		if(translations!=null && translations.containsKey(language)){
			return translations.get(language);
		}
		return "["+name+"]";
	}
	
	public List<String> getValues() {
		return values;
	}
	
	public void setValues(List<String> values) {
		this.values = values;
	}
	
	public String getTempLabel() {
		return tempLabel;
	}
	public void setTempLabel(String tempLabel) {
		this.tempLabel = tempLabel;
	}
}
