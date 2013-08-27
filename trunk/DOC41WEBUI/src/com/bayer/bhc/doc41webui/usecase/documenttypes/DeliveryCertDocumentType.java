package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Map;

import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;

public class DeliveryCertDocumentType implements DownloadDocumentType, UploadDocumentType {

	//TODO
	@Override
	public boolean isPartnerNumberUsed() {
		return false;
	}

	@Override
	public String getTypeConst() {
		return "DELCERT";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.39";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_DELCERT_DOWN";
	}

	@Override
	public void checkForDownload(Errors errors, DocumentUC documentUC,
			String partnerNumber, String objectId,
			Map<String, String> attributeValues) throws Doc41BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getPermissionUpload() {
		return "DOC_DELCERT_UP";
	}

	@Override
	public void checkForUpload(Errors errors, DocumentUC documentUC,
			MultipartFile file, String fileId, String partnerNumber,
			String objectId, Map<String, String> attributeValues)
			throws Doc41BusinessException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean isShowOpenDeliveries() {
		return false;
	}

}
