package com.bayer.bhc.doc41webui.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.Attribute;
import com.bayer.bhc.doc41webui.domain.SapCustomer;
import com.bayer.bhc.doc41webui.domain.SapVendor;
import com.bayer.ecim.foundation.basic.StringTool;

public abstract class CustomizedDocumentForm {

	private String type;
	private String objectId;

	private Map<String,String> attributeLabels;
	private Map<String, String> attributeValues;
	private Map<String, List<String>> attributePredefValues;
	private Map<String, Boolean> attributeMandatory;
	
	private Map<String, String> viewAttributes = new HashMap<String, String>();
	
	private String customerNumber;
	
	private boolean isCustomerNumberUsed;
	
	private List<SapCustomer> customers;
	
	private String vendorNumber;
	
	private boolean isVendorNumberUsed;
	
	private List<SapVendor> vendors;	
	
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

	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	public boolean isCustomerNumberUsed() {
		return isCustomerNumberUsed;
	}
	private void initCustomerNumber(boolean isCustomerNumberUsed,String lastCustomerNumberFromSession) {
		this.isCustomerNumberUsed = isCustomerNumberUsed;
		if(isCustomerNumberUsed){
			customers = UserInSession.get().getCustomers();
				
			if(StringTool.isTrimmedEmptyOrNull(getCustomerNumber())){
				if(!StringTool.isTrimmedEmptyOrNull(lastCustomerNumberFromSession)){
					setCustomerNumber(lastCustomerNumberFromSession);
				}
			}
		}
	}
	public List<SapCustomer> getCustomers() {
		return customers;
	}
	
	public String getVendorNumber() {
		return vendorNumber;
	}
	public void setVendorNumber(String vendorNumber) {
		this.vendorNumber = vendorNumber;
	}
	public boolean isVendorNumberUsed() {
		return isVendorNumberUsed;
	}
	private void initVendorNumber(boolean isVendorNumberUsed,String lastVendorNumberFromSession) {
		this.isVendorNumberUsed = isVendorNumberUsed;
		if(isVendorNumberUsed){
			vendors = UserInSession.get().getVendors();
				
			if(StringTool.isTrimmedEmptyOrNull(getVendorNumber())){
				if(!StringTool.isTrimmedEmptyOrNull(lastVendorNumberFromSession)){
					setVendorNumber(lastVendorNumberFromSession);
				}
			}
		}
	}
	public List<SapVendor> getVendors() {
		return vendors;
	}
	
	public void initPartnerNumbers(boolean isCustomerNumberUsed,String lastCustomerNumberFromSession,boolean isVendorNumberUsed,String lastVendorNumberFromSession) {
		initCustomerNumber(isCustomerNumberUsed, lastCustomerNumberFromSession);
		initVendorNumber(isVendorNumberUsed, lastVendorNumberFromSession);
	}

	
	public void initAttributes(List<Attribute> attributeDefinitions,String languageCode) {
		attributeLabels = new LinkedHashMap<String, String>();
		Map<String, String> oldAttributeValuesMap = null;
		if(attributeValues!=null){
			oldAttributeValuesMap = attributeValues;
		}
		attributeValues = new LinkedHashMap<String, String>();
		attributePredefValues = new HashMap<String, List<String>>();
		attributeMandatory = new HashMap<String, Boolean>();
		
		for (Attribute attribute : attributeDefinitions) {
			String key = attribute.getName();
			String label = attribute.getTranslation(languageCode);
			attributeLabels.put(key, label);
			if(oldAttributeValuesMap==null || !oldAttributeValuesMap.containsKey(key)){
				attributeValues.put(key, "");
			} else {
				attributeValues.put(key, oldAttributeValuesMap.get(key));
			}
			List<String> predefValues = attribute.getValues();
			attributePredefValues.put(key,predefValues);
			attributeMandatory.put(key, attribute.getMandatory());
		}
		if(oldAttributeValuesMap!=null){
			for (String oldAttrKey : oldAttributeValuesMap.keySet()) {
				if(!attributeValues.containsKey(oldAttrKey)){
					throw new IllegalArgumentException("attribute "+oldAttrKey+" no longer in customizing");
				}
			}
		}
	}

	
	
	@Override
	public String toString() {
		return "CustomizedDocumentForm [type=" + type + ", objectId="
				+ objectId + ", attributeLabels=" + attributeLabels
				+ ", attributeValues=" + attributeValues
				+ ", attributePredefValues=" + attributePredefValues
				+ ", attributeMandatory=" + attributeMandatory
				+ ", viewAttributes=" + viewAttributes + ", customerNumber="
				+ customerNumber + ", isCustomerNumberUsed="
				+ isCustomerNumberUsed + ", customers=" + customers
				+ ", vendorNumber=" + vendorNumber + ", isVendorNumberUsed="
				+ isVendorNumberUsed + ", vendors=" + vendors + "]";
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
