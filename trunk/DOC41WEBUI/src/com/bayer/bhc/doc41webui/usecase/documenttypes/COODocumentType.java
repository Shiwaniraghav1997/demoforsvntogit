package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.ecim.foundation.basic.StringTool;

public class COODocumentType implements DownloadDocumentType {

	@Override
	public boolean isPartnerNumberUsed() {
		return false;
	}

	@Override
	public String getTypeConst() {
		return "COO";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.06";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_COO_DOWN";
	}

	@Override
	public void checkForDownload(Errors errors, DocumentUC documentUC,
			String partnerNumber, String objectId,
			Map<String, String> attributeValues) throws Doc41BusinessException {
		if(StringTool.isTrimmedEmptyOrNull(objectId)){
			errors.rejectValue("objectId","DeliveryNumberMissing");
		}
		boolean deliveryCheck = documentUC.checkDeliveryNumberExists(objectId);
		if(!deliveryCheck){
			errors.reject("DeliveryDoesNotExist");
		}
	}



}
