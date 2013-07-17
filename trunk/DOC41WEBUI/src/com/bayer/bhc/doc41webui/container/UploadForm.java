package com.bayer.bhc.doc41webui.container;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import com.bayer.ecim.foundation.basic.StringTool;

public class UploadForm {
	
	private static final Object SHIPPING_UNIT_NUMBER = "SHIPPINGUNIT";

	private String deliveryNumber;

	private Map<String,String> attributeLabels;
	private Map<String, String> attributeValues;
	private Map<String, List<String>> attributePredefValues;
	private String fileId;
	private String type;
//	private String typeLabel;
	private transient MultipartFile file;
	private String partnerNumber;
	
	
	public Map<String, String> getAttributeLabels() {
		return attributeLabels;
	}
	public void setAttributeLabels(Map<String, String> attributeLabels) {
		this.attributeLabels = attributeLabels;
	}
	public Map<String, String> getAttributeValues() {
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
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getDeliveryNumber() {
		return deliveryNumber;
	}
	public void setDeliveryNumber(String deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}
//	public String getTypeLabel() {
//		return typeLabel;
//	}
//	public void setTypeLabel(String typeLabel) {
//		this.typeLabel = typeLabel;
//	}
	
	public void initAttributes(List<Attribute> attributeDefinitions,String languageCode) {
		attributeLabels = new HashMap<String, String>();
		attributeValues = new LinkedHashMap<String, String>();
		attributePredefValues = new HashMap<String, List<String>>();
		for (Attribute attribute : attributeDefinitions) {
			String key = attribute.getName();
			String label = attribute.getTranslation(languageCode);
			attributeLabels.put(key, label);
			attributeValues.put(key, "");
			List<String> predefValues = attribute.getValues();
			attributePredefValues.put(key,predefValues);
		}

		
	}
	public String getShippingUnitNumber() {
		return attributeValues.get(SHIPPING_UNIT_NUMBER);
	}
	
	public String getPartnerNumber() {
		return partnerNumber;
	}
	public void setPartnerNumber(String partnerNumber) {
		this.partnerNumber = partnerNumber;
	}
	
	@Override
	public String toString() {
		return "UploadForm [deliveryNumber=" + deliveryNumber
				+ ", attributeLabels=" + attributeLabels + ", attributeValues="
				+ attributeValues + ", fileId=" + fileId + ", type=" + type
				+ ", partnerNumber=" + partnerNumber + "]";
	}
	
	public void validate(Errors errors) {
		boolean isfileEmpty = (file==null||file.getSize()==0);
		if(isfileEmpty && StringTool.isTrimmedEmptyOrNull(fileId)){
			errors.rejectValue("file", "uploadFileMissing", "upload file is missing");
		}
		if(StringTool.isTrimmedEmptyOrNull(partnerNumber)){
			errors.rejectValue("partnerNumber", "PartnerNumberMissing", "partner number is missing");
		}
		if(StringTool.isTrimmedEmptyOrNull(deliveryNumber)){
			errors.rejectValue("deliveryNumber", "DeliveryNumberMissing", "delivery number is missing");
		}
		if(StringTool.isTrimmedEmptyOrNull(getShippingUnitNumber())){
			errors.rejectValue("attributeValues['SHIPPINGUNIT']", "ShippingUnitNumberMissing", "shipment unit number is missing");
		}
		if(!isfileEmpty && !StringTool.isTrimmedEmptyOrNull(fileId)){
			errors.rejectValue("file", "FileAndFileId", "both file and fileId filled");
		}
		
	}
	
	
}
