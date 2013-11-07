package com.bayer.bhc.doc41webui.usecase.documenttypes;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;

public interface DirectDownloadDocumentType extends DocumentType{

	public String getPermissionDirectDownload();
	public CheckForDownloadResult checkForDirectDownload(DocumentUC documentUC, String objectId)throws Doc41BusinessException;
}
