package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.ecim.foundation.basic.StringTool;

public class DeliveryCertDocumentType implements DownloadDocumentType, UploadDocumentType {
	
	//TODO
	public static final String VIEW_ATTRIB_COUNTRY = "country";
	public static final String VIEW_ATTRIB_MATERIAL = "material";
	public static final String VIEW_ATTRIB_BATCH = "batch";
	public static final String VIEW_ATTRIB_PLANT = "plant";
	
	
	
	//TODO SAP OBJECT???

	//TODO
	@Override
	public boolean isPartnerNumberUsed() {
		return false;
	}

	@Override
	public String getTypeConst() {
		return "DELCERT";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.39";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_DELCERT_DOWN";
	}

	@Override
	public void checkForDownload(Errors errors, DocumentUC documentUC,
			String partnerNumber, String objectId,
			Map<String, String> attributeValues) throws Doc41BusinessException {
		
		//TODO 2 implementations for different roles
		String countryCode = attributeValues.get(VIEW_ATTRIB_COUNTRY);
		if(StringTool.isTrimmedEmptyOrNull(countryCode)){
			errors.rejectValue("attributeValues['"+VIEW_ATTRIB_COUNTRY+"']","CountryMissing");
		}
		
		boolean hasCountry = UserInSession.get().hasCountry(countryCode);
		
		if(!hasCountry){
			errors.rejectValue("attributeValues['"+VIEW_ATTRIB_COUNTRY+"']","Country does not belong to User");
		}
	}

	@Override
	public String getPermissionUpload() {
		return "DOC_DELCERT_UP";
	}

	@Override
	public String checkForUpload(Errors errors, DocumentUC documentUC,
			String partnerNumber,
			String objectId, Map<String, String> attributeValues,Map<String,String> viewAttributes)
			throws Doc41BusinessException {

		String country = attributeValues.get(VIEW_ATTRIB_COUNTRY);
		if(StringTool.isTrimmedEmptyOrNull(country)){
			errors.rejectValue("attributeValues['"+VIEW_ATTRIB_COUNTRY+"']","CountryMissing");
		}
		
		if(errors.hasErrors()){
			return null;
		}
		
		//TODO getBatchObjectsForSupplier
		
		
		//TODO SAP OBJECT
		return null;
	}

}
