package com.bayer.bhc.doc41webui.common.util;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.ecim.foundation.basic.StringTool;

/**
 * 
 * @author ETZAJ
 * @version 20.03.2019
 * 
 *          Utility class for validation of input parameters for document type
 *          upload/download.
 *
 */
// Can not be extended.
public final class Doc41ValidationUtils {

	private static final String PURCHASE_ORDER_NUMBER_MISSING_ERROR_MESSAGE = "PurchaseOrderNumberIsMissing";
	private static final String PURCHASE_ORDER_NUMBER_TOO_LONG_ERROR_MESSAGE = "PurchaseOrderNumberIsTooLong";
	private static final int PURCHASE_ORDER_NUMBER_MAX_LENGTH = 10;
	private static final String MATERIAL_NUMBER_MISSING_ERROR_MESSAGE = "MaterialNumberIsMissing";
	private static final String MATERIAL_NUMBER_TOO_LONG_ERROR_MESSAGE = "MaterialNumberIsTooLong";
	private static final int MATERIAL_NUMBER_MAX_LENGTH = 18;
	private static final String VENDOR_BATCH_MISSING_ERROR_MESSAGE = "VendorBatchIsMissing";
	private static final String VENDOR_BATCH_TOO_LONG_ERROR_MESSAGE = "VendorBatchIsTooLong";
	private static final int VENDOR_BATCH_MAX_LENGTH = 10;

	// Can not be instantiated.
	private Doc41ValidationUtils() {
	}

	public static void checkMaterialNumber(String value, String fieldName, Errors errors, boolean isMandatory) {
		if (StringTool.isTrimmedEmptyOrNull(value)) {
			if (isMandatory) {
				errors.rejectValue(fieldName, "MatNoMissing");
			}
		} else {
			String trimmedValue = value.trim();
			if (trimmedValue.length() > Doc41Constants.FIELD_SIZE_MATNR) {
				errors.rejectValue(fieldName, "TooLong");
			} else {
				numberCheck(trimmedValue, fieldName, errors, 8);
			}
		}
	}

	private static void numberCheck(String value, String fieldName, Errors errors, int maxSignificant) {
		try {
			Integer intValue = Integer.valueOf(value);
			int length = String.valueOf(intValue).length();
			if (length > maxSignificant) {
				errors.rejectValue(fieldName, "TooManySignificant" + maxSignificant);
			}
		} catch (NumberFormatException e) {
			errors.rejectValue(fieldName, "OnlyNumbers");
		}
	}

	/**
	 * Validates that purchase order number: is provided; is not longer than 10
	 * characters; contains only ciphers; if is shorter than 10 characters, is
	 * filled with leading zeros on left side.
	 * 
	 * @param purchaseOrderNumber
	 *            is provided by user.
	 * @param errors
	 *            is used for error handling.
	 */
	public static String validatePurchaseOrderNumber(String purchaseOrderNumber, Errors errors) {
		if (StringTool.isTrimmedEmptyOrNull(purchaseOrderNumber)) {
			errors.rejectValue(String.format("attributeValues['%s']", Doc41Constants.ATTRIB_NAME_PURCHASE_ORDER),
					PURCHASE_ORDER_NUMBER_MISSING_ERROR_MESSAGE);
		} else if (purchaseOrderNumber.length() > PURCHASE_ORDER_NUMBER_MAX_LENGTH) {
			errors.rejectValue(String.format("attributeValues['%s']", Doc41Constants.ATTRIB_NAME_PURCHASE_ORDER),
					PURCHASE_ORDER_NUMBER_TOO_LONG_ERROR_MESSAGE);
		}
		return Doc41Utils.fillNumberField(purchaseOrderNumber, PURCHASE_ORDER_NUMBER_MAX_LENGTH);
	}

	/**
	 * Validates that material number: is provided; is not longer than 18
	 * characters; contains only ciphers; if is shorter than 18 characters, is
	 * filled with leading zeros on left side.
	 * 
	 * @param materialNumber
	 *            is provided by user.
	 * @param errors
	 *            is used for error handling.
	 */
	public static String validateMaterialNumber(String materialNumber, Errors errors) {
		if (StringTool.isTrimmedEmptyOrNull(materialNumber)) {
			errors.rejectValue(String.format("attributeValues['%s']", Doc41Constants.ATTRIB_NAME_MATERIAL),
					MATERIAL_NUMBER_MISSING_ERROR_MESSAGE);
		} else if (materialNumber.length() > MATERIAL_NUMBER_MAX_LENGTH) {
			errors.rejectValue(String.format("attributeValues['%s']", Doc41Constants.ATTRIB_NAME_MATERIAL),
					MATERIAL_NUMBER_TOO_LONG_ERROR_MESSAGE);
		}
		return Doc41Utils.fillNumberField(materialNumber, MATERIAL_NUMBER_MAX_LENGTH);
	}

	/**
	 * Validates that vendor batch: is provided; is not longer than 10
	 * characters; if is shorter than 10 characters, is
	 * filled with following spaces on right side.
	 * 
	 * @param materialNumber
	 *            is provided by user.
	 * @param errors
	 *            is used for error handling.
	 */
	public static String validateVendorBatch(String vendorBatch, Errors errors) {
		if (StringTool.isTrimmedEmptyOrNull(vendorBatch)) {
			errors.rejectValue(String.format("attributeValues['%s']", Doc41Constants.ATTRIB_NAME_VENDOR_BATCH),
					VENDOR_BATCH_MISSING_ERROR_MESSAGE);
		} else if (vendorBatch.length() > VENDOR_BATCH_MAX_LENGTH) {
			errors.rejectValue(String.format("attributeValues['%s']", Doc41Constants.ATTRIB_NAME_VENDOR_BATCH),
					VENDOR_BATCH_TOO_LONG_ERROR_MESSAGE);
		}
		return vendorBatch;
	}

}
