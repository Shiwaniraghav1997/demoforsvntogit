package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;

public interface UploadDocumentType extends DocumentType{
	
	public String getPermissionUpload();

	public void checkForUpload(Errors errors, DocumentUC documentUC, String partnerNumber, String objectId, Map<String, String> attributeValues,Map<String,String> viewAttributes)throws Doc41BusinessException;
	
}
