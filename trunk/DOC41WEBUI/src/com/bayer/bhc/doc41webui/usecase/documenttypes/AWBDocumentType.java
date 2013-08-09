package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;

public class AWBDocumentType extends SDUploadDocumentType implements DownloadDocumentType{

	@Override
	public String getTypeConst() {
		return "AWB";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.16";
	}

	@Override
	public String getPermissionUpload() {
		return "DOC_AWB_UP";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_AWB_DOWN";
	}

	@Override
	public void checkForDownload(Errors errors, DocumentUC documentUC,
			String partnerNumber, String objectId,
			Map<String, String> attributeValues) throws Doc41BusinessException {
		// TODO Auto-generated method stub
		
	}

}
