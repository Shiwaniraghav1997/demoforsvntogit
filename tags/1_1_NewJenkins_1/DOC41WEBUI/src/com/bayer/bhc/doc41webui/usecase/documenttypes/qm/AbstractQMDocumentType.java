package com.bayer.bhc.doc41webui.usecase.documenttypes.qm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.Doc41ValidationUtils;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForUpdateResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DocumentType;
import com.bayer.ecim.foundation.basic.StringTool;

/**
 * @author ETZAJ
 * @version 13.03.2019
 *
 *          Base class for QM document types.
 *
 */
public abstract class AbstractQMDocumentType implements DocumentType {

	private static final String SAP_OBJECT_BATCH_OBJ = "BUS2012";
	private static final String VALUE_OBJECT_TYPE_2 = "BUS1001002";

	/**
	 * Checks if document type is uploaded using customer number.
	 * 
	 * @return true, if document type is uploaded using customer number.
	 */
	@Override
	public boolean hasCustomerNumber() {
		return false;
	}

	/**
	 * Checks if document type is uploaded using vendor number.
	 * 
	 * @return true, if document type is uploaded using vendor number.
	 */
	@Override
	public boolean hasVendorNumber() {
		return true;
	}

	/**
	 * Gets specific fill length of object ID determined for document type.
	 * 
	 * @return fill length of object ID determined for document type.
	 */
	@Override
	public int getObjectIdFillLength() {
		return 0;
	}

	/**
	 * Gets specific excluded attributes determined for document type. These
	 * attributes will be excluded from input parameters of document type.
	 * 
	 * @return set of attribute names as defined in KGS customizing to be excluded
	 *         from input parameters.
	 */
	@Override
	public Set<String> getExcludedAttributes() {
		return new HashSet<String>(
				Arrays.asList(Doc41Constants.ATTRIB_NAME_COUNTRY, Doc41Constants.ATTRIB_NAME_PURCHASE_ORDER,
						Doc41Constants.ATTRIB_NAME_XVERSION, Doc41Constants.ATTRIB_NAME_VENDOR_NUMBER));
	}

	/**
	 * Gets specific group for document type.
	 * 
	 * @return specific group for document type.
	 */
	@Override
	public String getGroup() {
		return GROUP_QM;
	}

	/**
	 * Checks if document type is using DIRS document store.
	 * 
	 * @return true, if document type is using DIRS document store.
	 */
	@Override
	public boolean isDirs() {
		return false;
	}

	/**
	 * Checks if document type is using KGS document store.
	 * 
	 * @return true, if document type is using KGS document store.
	 */
	@Override
	public boolean isKgs() {
		return true;
	}

	protected CheckForUpdateResult checkForUpload(Errors errors, DocumentUC documentUC, String customerNumber,
			String vendorNumber, String objectId, Map<String, String> attributeValues,
			Map<String, String> viewAttributes, String sapTypeId) throws Doc41BusinessException {
		String purchaseOrderNumber = Doc41ValidationUtils.validatePurchaseOrderNumber(objectId, errors);
		String materialNumber = Doc41ValidationUtils
				.validateMaterialNumber(attributeValues.get(Doc41Constants.ATTRIB_NAME_MATERIAL), errors);
		String vendorBatch = Doc41ValidationUtils
				.validateVendorBatch(attributeValues.get(Doc41Constants.ATTRIB_NAME_VENDOR_BATCH), errors);
		if (errors.hasErrors()) {
			return null;
		}
		List<String> results = documentUC.checkPOAndMaterialForVendor(vendorNumber, purchaseOrderNumber, materialNumber,
				null);
		validateResults(results, errors);
		if (errors.hasErrors()) {
			return null;
		}
		setAttributeValues(attributeValues, purchaseOrderNumber, vendorNumber, results.get(1), materialNumber,
				vendorBatch, sapTypeId);
		return new CheckForUpdateResult(SAP_OBJECT_BATCH_OBJ, null, null);
	}

	private void validateResults(List<String> results, Errors errors) {
		if (results == null || results.isEmpty()) {
			errors.reject(Doc41ValidationUtils.ERROR_MESSAGE_NO_RETURN_CODE);
		} else if (results.get(0) != null
				&& results.get(0).equals(Doc41ValidationUtils.ERROR_MESSAGE_ORDER_NOT_EXISTING)) {
			errors.reject(Doc41ValidationUtils.ERROR_MESSAGE_ORDER_NOT_EXISTING);
		} else if (results.get(0) != null
				&& results.get(0).equals(Doc41ValidationUtils.ERROR_MESSAGE_ORDER_NOT_FOUND)) {
			errors.reject(Doc41ValidationUtils.ERROR_MESSAGE_ORDER_NOT_FOUND);
		} else if (results.get(1) == null) {
			errors.reject(Doc41ValidationUtils.ERR_MESS_PLANT_NOT_RETURNED);
		}
	}

	private void setAttributeValues(Map<String, String> attributeValues, String purchaseOrderNumber,
			String vendorNumber, String plant, String materialNumber, String vendorBatch, String sapTypeId) {
		attributeValues.put(Doc41Constants.ATTRIB_NAME_PURCHASE_ORDER, purchaseOrderNumber);
		attributeValues.put(Doc41Constants.ATTRIB_NAME_VENDOR_NUMBER, vendorNumber);
		attributeValues.put(Doc41Constants.ATTRIB_NAME_PLANT, plant);
		attributeValues.put(Doc41Constants.ATTRIB_OBJECT_TYPE_2, VALUE_OBJECT_TYPE_2);
		/**
		 * Object ID value must be formatted as: [MATERIAL_NUMBER]_[BATCH]_[PLANT].
		 * [MATERIAL_NUMBER] must have exactly 18 characters. If the [MATERIAL_NUMBER]
		 * has less than 18 characters, the missing characters must be filled with zero
		 * ('0') on the left side (leading the actual material number value). This is
		 * already previously done in the function "checkForUpload". Material number by
		 * default can not have more than 18 characters. [BATCH] must have exactly 10
		 * characters. If the [BATCH] has less than 10 characters, the missing
		 * characters must be filled with spaces (' ') on the right side (following the
		 * actual vendor batch value). If [BATCH] has more than 10 characters, it is
		 * reduced to 10 characters.
		 */
		attributeValues.put(Doc41Constants.ATTRIB_OBJECT_ID_2, materialNumber
				+ (vendorBatch.length() < 10 ? StringTool.minRString(vendorBatch, 10, ' ') : vendorBatch) + plant);
		attributeValues.put(Doc41Constants.ATTRIB_DOCUMENT_TYPE_2, sapTypeId);
	}

}
