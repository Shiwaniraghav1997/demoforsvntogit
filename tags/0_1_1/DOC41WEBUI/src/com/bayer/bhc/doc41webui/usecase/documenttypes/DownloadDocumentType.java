package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;

public interface DownloadDocumentType extends DocumentType{

	public String getPermissionDownload();
	public CheckForDownloadResult checkForDownload(Errors errors, DocumentUC documentUC, String partnerNumber, List<String> objectIds, Map<String, String> attributeValues,Map<String, String> viewAttributes)throws Doc41BusinessException;
}
