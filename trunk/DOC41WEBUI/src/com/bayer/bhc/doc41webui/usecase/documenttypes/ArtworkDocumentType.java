package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;

public class ArtworkDocumentType implements DownloadDocumentType {

	@Override
	public String getPartnerNumberType() {
		return Doc41Constants.PARTNER_TYPE_VENDOR;
	}

	@Override
	public String getTypeConst() {
		return "ARTWORK";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.48";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_ARTWORK_DOWN";
	}

	@Override
	public void checkForDownload(Errors errors, DocumentUC documentUC,
			String partnerNumber, List<String> objectIds,
			Map<String, String> attributeValues,Map<String, String> viewAttributes) throws Doc41BusinessException {
		
		String deliveryCheck = documentUC.checkArtworkForVendor(partnerNumber);
		if(deliveryCheck != null){
			errors.reject(""+deliveryCheck);
		}
	}

}
