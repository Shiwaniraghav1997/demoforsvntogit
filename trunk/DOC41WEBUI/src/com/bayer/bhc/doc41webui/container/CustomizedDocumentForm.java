package com.bayer.bhc.doc41webui.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.Attribute;
import com.bayer.bhc.doc41webui.domain.UserPartner;
import com.bayer.ecim.foundation.basic.StringTool;

public abstract class CustomizedDocumentForm {

	private String type;
	private String objectId;

	private Map<String,String> attributeLabels;
	private Map<String, String> attributeValues;
	private Map<String, List<String>> attributePredefValues;
	private Map<String, Boolean> attributeMandatory;
	
	private Map<String, String> viewAttributes = new HashMap<String, String>();
	
	private String partnerNumber;
	
	private boolean partnerNumberUsed;
	
	private List<UserPartner> partners;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public Map<String, String> getAttributeLabels() {
		return attributeLabels;
	}
	public void setAttributeLabels(Map<String, String> attributeLabels) {
		this.attributeLabels = attributeLabels;
	}

	public Map<String, String> getAttributeValues() {
		if(attributeValues==null){
			attributeValues = new HashMap<String, String>();
		}
		return attributeValues;
	}
	public void setAttributeValues(Map<String, String> attributeValues) {
		this.attributeValues = attributeValues;
	}

	public Map<String, List<String>> getAttributePredefValues() {
		return attributePredefValues;
	}
	public void setAttributePredefValues(
			Map<String, List<String>> attributePredefValues) {
		this.attributePredefValues = attributePredefValues;
	}
	
	public Map<String, Boolean> getAttributeMandatory() {
		return attributeMandatory;
	}

	public String getPartnerNumber() {
		return partnerNumber;
	}
	public void setPartnerNumber(String partnerNumber) {
		this.partnerNumber = partnerNumber;
	}
	public boolean isPartnerNumberUsed() {
		return partnerNumberUsed;
	}
	public void initPartnerNumber(String partnerNumberType) {
		this.partnerNumberUsed = !StringTool.isTrimmedEmptyOrNull(partnerNumberType);
		if(partnerNumberUsed){
			partners = UserInSession.get().getPartnersByType(partnerNumberType);
		}
	}
	public List<UserPartner> getPartners() {
		return partners;
	}
	public void setPartners(List<UserPartner> partners) {
		this.partners = partners;
	}

	
	public void initAttributes(List<Attribute> attributeDefinitions,String languageCode) {
		attributeLabels = new LinkedHashMap<String, String>();
		if(attributeValues==null){
			attributeValues = new LinkedHashMap<String, String>();
		}
		attributePredefValues = new HashMap<String, List<String>>();
		attributeMandatory = new HashMap<String, Boolean>();
		
		for (Attribute attribute : attributeDefinitions) {
			String key = attribute.getName();
			String label = attribute.getTranslation(languageCode);
			attributeLabels.put(key, label);
			if(!attributeValues.containsKey(key)){
				attributeValues.put(key, "");
			}
			List<String> predefValues = attribute.getValues();
			attributePredefValues.put(key,predefValues);
			attributeMandatory.put(key, attribute.getMandatory());
		}
	}

	
	
	@Override
	public String toString() {
		return "CustomizedDocumentForm [type=" + type + ", objectId="
				+ objectId + ", attributeLabels=" + attributeLabels
				+ ", attributeValues=" + attributeValues
				+ ", attributePredefValues=" + attributePredefValues
				+ ", partnerNumber=" + partnerNumber + ", partnerNumberUsed="
				+ partnerNumberUsed + "]";
	}
	public List<String> getCustomizedValuesLabels(){
		Collection<String> labels = attributeLabels.values();
		List<String> labelList = new ArrayList<String>(labels);
		return labelList;
	}
	public int getCustColPercent(){
		int colCount = getCustAttributeCount();
		return 50/colCount;
	}
	public int getCustAttributeCount() {
		return attributeLabels.size();
	}
	
	public Map<String, String> getViewAttributes(){
		return viewAttributes;
	}
	
}
