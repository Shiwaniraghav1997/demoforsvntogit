package com.bayer.bhc.doc41webui.usecase.documenttypes.qm;

import java.util.Map;

import org.springframework.validation.Errors;

import com.bayer.bhc.doc41webui.common.exception.Doc41BusinessException;
import com.bayer.bhc.doc41webui.usecase.DocumentUC;
import com.bayer.bhc.doc41webui.usecase.documenttypes.CheckForUpdateResult;
import com.bayer.bhc.doc41webui.usecase.documenttypes.UploadDocumentType;

/**
 * @author ETZAJ
 * @version 13.03.2019
 *
 *          Class for QMCoA upload document types.
 * 
 */
public class QMCOAUploadDocumentType extends AbstractQMDocumentType implements UploadDocumentType {

	private static final String SAP_TYPE_ID = "DOC41.39";
	private static final String TYPE_CONST = "QMCoA";
	private static final String PERMISSION_UPLOAD = "DOC_QMCOA_UP";
	
	/**
	 * Gets specific SAP type ID determined for document type.
	 * 
	 * @return SAP type ID determined for document type.
	 */
	@Override
	public String getSapTypeId() {
		return SAP_TYPE_ID;
	}

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
		return super.checkForUpload(errors, documentUC, customerNumber, vendorNumber, objectId, attributeValues,
				viewAttributes, SAP_TYPE_ID);
	}

}
