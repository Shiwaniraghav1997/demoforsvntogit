package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Map;

import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.ecim.foundation.basic.StringTool;

public class LayoutDocumentType implements DownloadDocumentType,
		UploadDocumentType {

	@Override
	public boolean isPartnerNumberUsed() {
		return true;
	}

	@Override
	public String getTypeConst() {
		return "LAYOUT";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.49";
	}

	@Override
	public String getPermissionUpload() {
		return "DOC_LAYOUT_UP";
	}

	@Override
	public void checkForUpload(Errors errors, DocumentUC documentUC,
			MultipartFile file, String fileId, String partnerNumber,
			String objectId, Map<String, String> attributeValues)
			throws Doc41BusinessException {
		//TODO check mandatory fields
		
		// no RFC check needed

	}

	@Override
	public String getPermissionDownload() {
		return "DOC_LAYOUT_DOWN";
	}

	@Override
	public void checkForDownload(Errors errors, DocumentUC documentUC,
			String partnerNumber, String objectId,
			Map<String, String> attributeValues) throws Doc41BusinessException {
		if(StringTool.isTrimmedEmptyOrNull(partnerNumber)){
			errors.rejectValue("partnerNumber","PartnerNumberMissing");
		} else {
			if(!UserInSession.get().hasPartner(partnerNumber)){
				errors.rejectValue("partnerNumber","PartnerNotAssignedToUser");
			}
		}
		
		if(errors.hasErrors()){
			return;
		}
		
		String deliveryCheck = documentUC.checkLayoutForVendor(partnerNumber);
		if(deliveryCheck != null){
			errors.reject(""+deliveryCheck);
		}
	}

}
