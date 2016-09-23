package com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.ls;

import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.Doc41ValidationUtils;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForDownloadResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;

public class PackMatSpecForLayoutSupplierDocumentType extends LayoutSupplierDocumentType
implements DownloadDocumentType{

	@Override
	public String getTypeConst() {
		return "PMS_LS";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.02";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_PMS_DOWN_LS";
	}
	
	@Override
	protected boolean checkExistingDocs() {
	    // TODO Auto-generated method stub
	    return false;
	}
	
}
