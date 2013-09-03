package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;

public class CMROutDocumentType implements DownloadDocumentType {

	@Override
	public boolean isPartnerNumberUsed() {
		return true;
	}

	@Override
	public String getTypeConst() {
		return "CMROUT";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.08";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_CMROUT_DOWNLOAD";
	}

	@Override
	public void checkForDownload(Errors errors, DocumentUC documentUC,
			String partnerNumber, String objectId,
			Map<String, String> attributeValues) throws Doc41BusinessException {
		// TODO Auto-generated method stub

	}

}
