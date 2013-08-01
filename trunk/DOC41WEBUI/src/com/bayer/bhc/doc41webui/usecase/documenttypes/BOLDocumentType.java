package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;

//TODO remove download
public class BOLDocumentType extends SDDocumentType implements DownloadDocumentType{

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

	//TODO remove
	@Override
	public String getPermissionDownload() {
		// TODO Auto-generated method stub
		return "DOC_BOL_UP";
	}

	//TODO remove
	@Override
	public void checkForDownload(Errors errors, DocumentUC documentUC,
			String partnerNumber, String objectId,
			Map<String, String> attributeValues) throws Doc41BusinessException {
		// TODO Auto-generated method stub
		
	}

}
