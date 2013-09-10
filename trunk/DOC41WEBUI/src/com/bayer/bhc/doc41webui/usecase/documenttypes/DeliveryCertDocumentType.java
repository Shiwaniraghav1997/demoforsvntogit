package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.ecim.foundation.basic.StringTool;

public class DeliveryCertDocumentType implements DownloadDocumentType, UploadDocumentType {
	
	private static final Object COUNTRY_ISO_CODE = "COUNTRYISOCODE";
	private static final Object MATERIAL_NUMBER = "MATERIALNUMBER";
	
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
		String countryCode = attributeValues.get(COUNTRY_ISO_CODE);
		if(StringTool.isTrimmedEmptyOrNull(countryCode)){
			errors.rejectValue("attributeValues['"+COUNTRY_ISO_CODE+"']","CountryMissing");
		}
		
		boolean hasCountry = UserInSession.get().hasCountry(countryCode);
		
		if(!hasCountry){
			errors.rejectValue("attributeValues['"+COUNTRY_ISO_CODE+"']","Country does not belong to User");
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

		String matNumber = attributeValues.get(MATERIAL_NUMBER);
		if(StringTool.isTrimmedEmptyOrNull(matNumber)){
			errors.rejectValue("attributeValues['"+MATERIAL_NUMBER+"']","MaterialNumberMissing");
		}
		
		if(errors.hasErrors()){
			return null;
		}
		
		//TODO getBatchObjectsForSupplier
		
		
		//TODO SAP OBJECT
		return null;
	}

}
