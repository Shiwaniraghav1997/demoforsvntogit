package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;

public class COCDocumentType implements DownloadDocumentType {

	//TODO
	@Override
	public boolean isPartnerNumberUsed() {
		return false;
	}

	@Override
	public String getTypeConst() {
		return "COC";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.39";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_COC_DOWN";
	}

	@Override
	public void checkForDownload(Errors errors, DocumentUC documentUC,
			String partnerNumber, String objectId,
			Map<String, String> attributeValues) throws Doc41BusinessException {
		// TODO Auto-generated method stub

	}

}
