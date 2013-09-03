package com.bayer.bhc.doc41webui.usecase.documenttypes;

import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.UserInSession;
import com.bayer.bhc.doc41webui.domain.TestLot;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.ecim.foundation.basic.StringTool;

public class SupplierCOADocumentType implements UploadDocumentType {

	public static final String VIEW_ATTRIB_BATCH = "batch";

	@Override
	public boolean isPartnerNumberUsed() {
		return true;
	}

	@Override
	public String getTypeConst() {
		return "SUPCOA";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.22";
	}

	@Override
	public String getPermissionUpload() {
		return "DOC_SUPCOA_UP";
	}

	@Override
	public void checkForUpload(Errors errors, DocumentUC documentUC,
			MultipartFile file, String fileId, String partnerNumber,
			String objectId, Map<String, String> attributeValues,Map<String,String> viewAttributes)
			throws Doc41BusinessException {
		boolean isfileEmpty = (file==null||file.getSize()==0);
		if(isfileEmpty && StringTool.isTrimmedEmptyOrNull(fileId)){
			errors.rejectValue("file", "uploadFileMissing", "upload file is missing");
		}
		if(isPartnerNumberUsed()){
			if(StringTool.isTrimmedEmptyOrNull(partnerNumber)){
				errors.rejectValue("partnerNumber","PartnerNumberMissing");
			} else {
				if(!UserInSession.get().hasPartner(partnerNumber)){
					errors.rejectValue("partnerNumber","PartnerNotAssignedToUser");
				}
			}
		}
		String batch = viewAttributes.get(VIEW_ATTRIB_BATCH);
		if(StringTool.isTrimmedEmptyOrNull(batch)){
			errors.rejectValue("objectId","BatchMissing");
		}
		
		if(StringTool.isTrimmedEmptyOrNull(objectId)){
			errors.reject("TestLotMissing");
		}
		
		if(!isfileEmpty && !StringTool.isTrimmedEmptyOrNull(fileId)){
			errors.rejectValue("file", "FileAndFileId", "both file and fileId filled");
		}
		
		if(errors.hasErrors()){
			return;
		}
		
		checkTestLot(errors, documentUC, partnerNumber, batch, objectId);

	}

	private void checkTestLot(Errors errors, DocumentUC documentUC,
			String partnerNumber, String batch, String objectId) throws Doc41BusinessException {
		List<TestLot> deliveryCheck = documentUC.getTestLotsForVendorBatch(partnerNumber, batch);
		for (TestLot testLot : deliveryCheck) {
			if(StringTool.equals(testLot.getNumber(), objectId)){
				return;
			}
		}
		errors.reject("testLot does not belong to vendor and batch");
	}

}
