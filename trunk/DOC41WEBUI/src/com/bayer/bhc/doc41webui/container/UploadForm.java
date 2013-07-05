package com.bayer.bhc.doc41webui.container;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class UploadForm {
	
	private static final Object SHIPPING_UNIT_NUMBER = "ShippingUnitNumber";

	private String deliveryNumber;

	private Map<String,String> attributeLabels;
	private Map<String, String> attributeValues;
	private String fileId;
	private String type;
	private String typeLabel;
	private transient MultipartFile file;
	
	
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
	public String getTypeLabel() {
		return typeLabel;
	}
	public void setTypeLabel(String typeLabel) {
		this.typeLabel = typeLabel;
	}
	
	public void initAttributes(List<Attribute> attributeDefinitions,String languageCode) {
		attributeLabels = new HashMap<String, String>();
		attributeValues = new LinkedHashMap<String, String>();
		for (Attribute attribute : attributeDefinitions) {
			String key = attribute.getName();
			String label = attribute.getTranslation(languageCode);
			//TODO attribute.getValues()
			attributeLabels.put(key, label);
			attributeValues.put(key, "");
		}

		
	}
	public String getShippingUnitNumber() {
		return attributeValues.get(SHIPPING_UNIT_NUMBER);
	}
	
}
