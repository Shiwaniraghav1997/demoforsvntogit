package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;

public interface DownloadDocumentType extends DocumentType{

	public String getPermissionDownload();

	/**
	 * Set the profile type for download permissions (DOC_SD/QM/LS/PT), see DocumentType.GROUP_* constants.
	 * @param pPermType
	 */
	public void setDownloadPermissionType(String pPermType);

	/**
     * Get the profile type for download permissions (DOC_SD/QM/LS/PT), see DocumentType.GROUP_* constants.
     * @return
     */
	public String getDownloadPermissionType();

	public CheckForDownloadResult checkForDownload(Errors errors, DocumentUC documentUC, String customerNumber, String vendorNumber, String objectId, Map<String, String> attributeValues,Map<String, String> viewAttributes)throws Doc41BusinessException;
}
