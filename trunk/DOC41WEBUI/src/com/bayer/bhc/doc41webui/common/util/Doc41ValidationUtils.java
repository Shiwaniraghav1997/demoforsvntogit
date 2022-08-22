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
	private static final String VENDOR_BATCH_DOCUMENT_IDENTIFICATION_MISSING_ERROR_MESSAGE = "VendorBatchMissingForDocumentIdentification";
	private static final String VENDOR_BATCH_TOO_LONG_ERROR_MESSAGE = "VendorBatchIsTooLong";
	private static final int VENDOR_BATCH_MAX_LENGTH = 10;
	/**
	 * Error message for return code mapping in RFC call when no return code is
	 * returned from SAP.
	 */
	public static final String ERROR_MESSAGE_NO_RETURN_CODE = "NoReturnCode";
	/**
	 * Error message for return code mapping in RFC call when order is not existing
	 * in SAP.
	 */
	public static final String ERROR_MESSAGE_ORDER_NOT_EXISTING = "OrderNotExisting";
	/**
	 * Error message for return code mapping in RFC call when order is not found for
	 * given material or vendor in SAP.
	 */
	public static final String ERROR_MESSAGE_ORDER_NOT_FOUND = "OrderNotFoundForGivenMaterialOrVendor";
	/**
	 * Error message for return code mapping in RFC call when unknown return code is
	 * returned from SAP.
	 */
	public static final String ERROR_MESSAGE_UNKNOWN_RETURN_CODE = "UnknownReturnCode";
	/**
	 * Error message for return code mapping in RFC call when plant is not returned
	 * from SAP.
	 */
	public static final String ERR_MESS_PLANT_NOT_RETURNED = "PlantNotReturned";
	/**
	 * Error message for return code mapping in RFC call when do documents are returned
	 * from SAP.
	 */
	public static final String ERROR_MESSAGE_NO_PROCESS_ORDER = "NoProcessOrder";
	
	
	//Added for SPecification by ELERJ
	public static final String ERROR_MESSAGE_PV_NOT_FOUND = "NoPvFound"; //1
	public static final String ERROR_MESSAGE_NO_MAT_FOR_PO = "NoMatForPo";
	public static final String ERROR_MESSAGE_PO_NOT_FOUND = "NoPoFound";
	
	

	// Can not be instantiated.
	private Doc41ValidationUtils() {
	}

	public static void checkMaterialNumber(String value, String fieldName, Errors errors, boolean isMandatory) {
		if (StringTool.isTrimmedEmptyOrNull(value)) {
//			System.out.println("isMandatory::"+isMandatory);
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
	
	public static void checkPurchaseOrder(String value, String fieldName, Errors errors,int maxSignificant ) {
		try {
		Long intValue=Long.parseLong(value);
		int length = String.valueOf(intValue).length();
		if (length > Doc41Constants.FIELD_SIZE_PURCHASE_ORDER) {
			errors.rejectValue(fieldName, "TooLong");
			/*errors.rejectValue(fieldName, "TooManySignificant" + maxSignificant);*/
		}
		}
		 catch (NumberFormatException e) {
				errors.rejectValue(fieldName, "OnlyNumbers");
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
//		System.out.println("material validation::"+materialNumber);
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
	public static String validateVendorBatch(String vendorBatch, String documentIdentification, Errors errors) {
		
		if (StringTool.isTrimmedEmptyOrNull(vendorBatch) && documentIdentification == null) {
			errors.rejectValue(String.format("attributeValues['%s']", Doc41Constants.ATTRIB_NAME_VENDOR_BATCH),
					VENDOR_BATCH_MISSING_ERROR_MESSAGE);
		} else if (StringTool.isTrimmedEmptyOrNull(vendorBatch) && 
				(documentIdentification != null && !documentIdentification.equalsIgnoreCase(Doc41Constants.NAME_DOCUMENT_IDENTIFICATION_VENDOR_PACKING_LIST))) {
			errors.rejectValue(String.format("attributeValues['%s']", Doc41Constants.ATTRIB_NAME_VENDOR_BATCH),
					VENDOR_BATCH_DOCUMENT_IDENTIFICATION_MISSING_ERROR_MESSAGE);
		}else if (!StringTool.isTrimmedEmptyOrNull(vendorBatch) && vendorBatch.length() > VENDOR_BATCH_MAX_LENGTH) {
			errors.rejectValue(String.format("attributeValues['%s']", Doc41Constants.ATTRIB_NAME_VENDOR_BATCH),
					VENDOR_BATCH_TOO_LONG_ERROR_MESSAGE);
		}
		return vendorBatch;
	}
}
