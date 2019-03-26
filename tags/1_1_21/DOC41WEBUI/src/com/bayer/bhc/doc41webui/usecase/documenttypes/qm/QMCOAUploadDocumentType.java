package com.bayer.bhc.doc41webui.usecase.documenttypes.qm;

import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.common.util.Doc41ValidationUtils;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForUpdateResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.UploadDocumentType;
import com.bayer.ecim.foundation.basic.StringTool;

/**
 * @author ETZAJ
 * @version 13.03.2019
 *
 *          Class for QMCoA upload document types.
 * 
 */
public class QMCOAUploadDocumentType extends AbstractQMDocumentType implements UploadDocumentType {

	private static final String TYPE_CONST = "QMCoA";
	private static final String PERMISSION_UPLOAD = "DOC_QMCOA_UP";

	/**
	 * Gets document type constant used for identification of document type.
	 * 
	 * @return document type constant used for identification of document type.
	 */
	@Override
	public String getTypeConst() {
		return TYPE_CONST;
	}

	/**
	 * Gets permission upload identifier of document type used for setting upload
	 * permissions for document type.
	 * 
	 * @return permission upload identifier of document type.
	 */
	@Override
	public String getPermissionUpload() {
		return PERMISSION_UPLOAD;
	}

	/**
	 * Checks if document is ready for upload by calling specific SAP function for
	 * checking if customer/vendor, material or order for document exists.
	 * 
	 * @param errors
	 *            used for error handling if input parameters are wrong or check of
	 *            document failed.
	 * @param documentUC
	 *            used for handling use cases for document and calling specific SAP
	 *            function.
	 * @param customerNumber
	 *            used for identification of document in SAP.
	 * @param vendorNumber
	 *            used for identification of document in SAP.
	 * @param objectId
	 *            used for identification of document in SAP. Represents purchase
	 *            order number as input parameter for specific document types.
	 * @param attributeValues
	 *            contains values of input parameters used for identification of
	 *            document in SAP.
	 */
	@Override
	public CheckForUpdateResult checkForUpload(Errors errors, DocumentUC documentUC, String customerNumber,
			String vendorNumber, String objectId, Map<String, String> attributeValues,
			Map<String, String> viewAttributes) throws Doc41BusinessException {
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
				vendorBatch);
		return new CheckForUpdateResult(SAP_OBJECT_BATCH_OBJ, null, null);
	}

	private void validateResults(List<String> results, Errors errors) {
		if (results == null || results.isEmpty()) {
			errors.reject(Doc41Constants.ERROR_MESSAGE_NO_RETURN_CODE);
		} else if (results.get(0) != null && results.get(0).equals(Doc41Constants.ERROR_MESSAGE_ORDER_NOT_EXISTING)) {
			errors.reject(Doc41Constants.ERROR_MESSAGE_ORDER_NOT_EXISTING);
		} else if (results.get(0) != null && results.get(0).equals(Doc41Constants.ERROR_MESSAGE_ORDER_NOT_FOUND)) {
			errors.reject(Doc41Constants.ERROR_MESSAGE_ORDER_NOT_FOUND);
		} else if (results.get(1) == null) {
			errors.reject(ERR_MESS_PLANT_NOT_RETURNED);
		}
	}

	private void setAttributeValues(Map<String, String> attributeValues, String purchaseOrderNumber,
			String vendorNumber, String plant, String materialNumber, String vendorBatch) {
		attributeValues.put(Doc41Constants.ATTRIB_NAME_PURCHASE_ORDER, purchaseOrderNumber);
		attributeValues.put(ATTRIB_NAME_VENDOR_NUMBER, vendorNumber);
		attributeValues.put(Doc41Constants.ATTRIB_NAME_PLANT, plant);
		attributeValues.put(ATTRIB_OBJECT_TYPE_2, VALUE_OBJECT_TYPE_2);
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
		attributeValues.put(ATTRIB_OBJECT_ID_2, materialNumber
				+ (vendorBatch.length() < 10 ? StringTool.minRString(vendorBatch, 10, ' ') : vendorBatch) + plant);
		attributeValues.put(ATTRIB_DOCUMENT_TYPE_2, SAP_TYPE_ID);
	}

}
