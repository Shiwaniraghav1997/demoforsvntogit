package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;

public interface DownloadDocumentType extends DocumentType{

	public String getPermissionDownload();
	public void checkForDownload(Errors errors, DocumentUC documentUC, String partnerNumber, String objectId, Map<String, String> attributeValues)throws Doc41BusinessException;
}
