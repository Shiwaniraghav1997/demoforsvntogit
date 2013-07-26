package com.bayer.bhc.doc41webui.container;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bayer.ecim.foundation.basic.StringTool;

public class UploadForm extends CustomizedDocumentForm{
	
	private static final Object SHIPPING_UNIT_NUMBER = "SHIPPINGUNIT";


	private String fileId;
//	private String typeLabel;
	private transient MultipartFile file;
	
	
	
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getDeliveryNumber() {
		return getObjectId();
	}
	public void setDeliveryNumber(String deliveryNumber) {
		setObjectId(deliveryNumber);
	}


	public String getShippingUnitNumber() {
		return getAttributeValues().get(SHIPPING_UNIT_NUMBER);
	}
	
	
	@Override
	public String toString() {
		return "UploadForm [fileId=" + fileId + ", toString()="
				+ super.toString() + "]";
	}
	
	public void validate(Errors errors) {
		boolean isfileEmpty = (file==null||file.getSize()==0);
		if(isfileEmpty && StringTool.isTrimmedEmptyOrNull(fileId)){
			errors.rejectValue("file", "uploadFileMissing", "upload file is missing");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "partnerNumber", "PartnerNumberMissing", "partner number is missing");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deliveryNumber", "DeliveryNumberMissing", "delivery number is missing");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "attributeValues['SHIPPINGUNIT']", "ShippingUnitNumberMissing", "shipment unit number is missing");
		if(!isfileEmpty && !StringTool.isTrimmedEmptyOrNull(fileId)){
			errors.rejectValue("file", "FileAndFileId", "both file and fileId filled");
		}
		
	}
	
	
}
