package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.ecim.foundation.basic.StringTool;


public class DeliveryCertDownCountryDocumentType extends
		AbstractDeliveryCertDocumentType implements DownloadDocumentType {
	
	@Override
	public boolean isPartnerNumberUsed() {
		return false;
	}
	
	@Override
	public String getTypeConst() {
		return "DELCERTDOWNCOUNTRY";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_DELCERT_DOWN_COUNTRY";
	}
	
	@Override
	public void checkForDownload(Errors errors, DocumentUC documentUC,
			String partnerNumber, List<String> objectIds,
			Map<String, String> attributeValues) throws Doc41BusinessException {
		
		String countryCode = attributeValues.get(VIEW_ATTRIB_COUNTRY);
		if(StringTool.isTrimmedEmptyOrNull(countryCode)){
			errors.rejectValue("attributeValues['"+VIEW_ATTRIB_COUNTRY+"']","CountryMissing");
		}
		
		boolean hasCountry = UserInSession.get().hasCountry(countryCode);
		
		if(!hasCountry){
			errors.rejectValue("attributeValues['"+VIEW_ATTRIB_COUNTRY+"']","Country does not belong to User");
		}
		
		String material = attributeValues.get(VIEW_ATTRIB_MATERIAL);
		String batch = attributeValues.get(VIEW_ATTRIB_BATCH);
		if(StringTool.isTrimmedEmptyOrNull(material) && StringTool.isTrimmedEmptyOrNull(batch)){
			errors.rejectValue("attributeValues['"+VIEW_ATTRIB_MATERIAL+"']","MaterialAndBatchMissing");
			errors.rejectValue("attributeValues['"+VIEW_ATTRIB_BATCH+"']","MaterialAndBatchMissing");
		}
	}
}
