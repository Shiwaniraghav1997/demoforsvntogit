package com.bayer.bhc.doc41webui.usecase.documenttypes.sd;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForDownloadResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DirectDownloadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;
import com.bayer.bhc.doc41webui.usecase.documenttypes.UploadDocumentType;


public class BOLDocumentType extends SDDocumentType implements DownloadDocumentType,UploadDocumentType,DirectDownloadDocumentType{

	@Override
	public String getTypeConst() {
		return "BOL";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.14";
	}

	@Override
	public String getPermissionUpload() {
		return "DOC_BOL_UP";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_BOL_DOWN";
	}
	
	@Override
	public String getPermissionDirectDownload() {
		return "DOC_BOL_DIRECT_DOWN";
	}

	@Override
	public Set<String> getMandatoryAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	//ELERj by force added
	@Override
	public CheckForDownloadResult checkForDownload(Errors errors, DocumentUC documentUC, String customerNumber,
			String vendorNumber, String objectId, String customVersion, Date timeFrame,
			Map<String, String> attributeValues, Map<String, String> viewAttributes, String puchaseOrder)
			throws Doc41BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
