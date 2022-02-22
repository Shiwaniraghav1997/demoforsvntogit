package com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.ls;

import java.util.Set;

import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;

public class PZTecDrawingForLayoutSupplierDocumentType extends LayoutSupplierDocumentType
implements DownloadDocumentType{

	@Override
	public String getTypeConst() {
		return "PZ_LS";
	}

	@Override
	public String getSapTypeId() {
		return "DOC41.03";
	}

	@Override
	public String getPermissionDownload() {
		return "DOC_PZ_DOWN_LS";
	}

    @Override
    protected boolean checkExistingDocs() {
        return false;
    }

	@Override
	public Set<String> getMandatoryAttributes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean isNotificationEMailHidden() {
		return false;
	}

}
