package com.bayer.bhc.doc41webui.usecase.documenttypes.ptms.pm;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.Doc41Utils;
import com.bayer.bhc.doc41webui.common.util.Doc41ValidationUtils;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForDownloadResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DownloadDocumentType;

public abstract class PMSupplierDownloadDocumentType implements DownloadDocumentType {
	// kgs-mode:
	public static final String VIEW_ATTRIB_PO_NUMBER = "poNumber";

	// global:
	public static final String VIEW_ATTRIB_DOC_TYPE = "docType";

	// dirs-mode only:
	public static final String VIEW_ATTRIB_FILENAME = "FILENAME";

	@Override
	public boolean hasCustomerNumber() {
		return false;
	}

	@Override
	public boolean hasVendorNumber() {
		return true;
	}

	@Override
	public CheckForDownloadResult checkForDownload(Errors errors, DocumentUC documentUC, String customerNumber, String vendorNumber, String objectId, String customVersion, Date timeFrame, Map<String, String> attributeValues, Map<String, String> viewAttributes) throws Doc41BusinessException {
		Doc41ValidationUtils.checkMaterialNumber(objectId, "objectId", errors, true);
		Map<String, String> additionalAttributes = new HashMap<String, String>();
		if (!errors.hasErrors()) {
			List<String> deliveryCheck = documentUC.checkMaterialForVendor(vendorNumber, objectId, customVersion, timeFrame);
			if (deliveryCheck.get(0) != null) {
				errors.reject("" + deliveryCheck.get(0));
			}
			if (deliveryCheck.size() >= 2) {
				additionalAttributes.put("IV_PLANT_BOM", deliveryCheck.get(1));
				additionalAttributes.put("IV_VERID_BOM", customVersion);
				additionalAttributes.put("IV_TIMEFRAME_DATE", Doc41Utils.convertDateToString(timeFrame));
			}
		}
		return new CheckForDownloadResult(additionalAttributes, null);

	}

	@Override
	public int getObjectIdFillLength() {
		return Doc41Constants.FIELD_SIZE_MATNR;
	}

	@Override
	public Set<String> getExcludedAttributes() {
		return Collections.emptySet();
	}

	// kgs-mode / dirs-mode:

	/**
	 * Flag to determine, if document uses DIRS store.
	 * 
	 * @return true, if using DIRS.
	 */
	@Override
	public boolean isDirs() {
		return false; // true;
	}

	/**
	 * Flag to determine, if document uses KGS store.
	 * 
	 * @return true, if using KGS.
	 */
	@Override
	public boolean isKgs() {
		return true; // false;
	}

	/**
	 * Set the profile type for download permissions (DOC_SD/QM/LS/PM), see
	 * DocumentType.GROUP_* constants.
	 * 
	 * @param pPermType
	 */
	public void setDownloadPermissionType(String pPermType) {
		cDownloadPermType = pPermType;
	}

	/**
	 * Get the profile type for download permissions (DOC_SD/QM/LS/PM), see
	 * DocumentType.GROUP_* constants.
	 * 
	 * @return
	 */
	public String getDownloadPermissionType() {
		return cDownloadPermType;
	}

	/** Type of the Profile for Download Permissions. */
	String cDownloadPermType = null;

	/**
	 * The permissions allowing download for all documents of the same document
	 * group (= permission type). Take care, permission type is DOC_xx,
	 * corresponding permission is DOC_GLO_xx...
	 * 
	 * @return the permission code, may be null, if this document is not allowed to
	 *         download by group membership.
	 */
	@Override
	public String getGroupPermissionDownload() {
		return GROUP_PM_PERM_DOWNL;
	}

	/**
	 * Get the profile type for download permissions (DOC_SD/QM/LS/PM), see
	 * DocumentType.GROUP_* constants.
	 * 
	 * @return
	 */
	public String getGroup() {
		return GROUP_PM;
	}

	@Override
	public boolean isNotificationEMailHidden() {
		return false;
	}
}