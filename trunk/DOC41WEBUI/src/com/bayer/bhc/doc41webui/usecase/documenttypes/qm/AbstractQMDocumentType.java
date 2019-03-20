package com.bayer.bhc.doc41webui.usecase.documenttypes.qm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.bayer.bhc.doc41webui.common.Doc41Constants;
import com.bayer.bhc.doc41webui.usecase.documenttypes.DocumentType;

/**
 * @author ETZAJ
 * @version 13.03.2019
 *
 *          Base class for QM document types.
 *
 */
public abstract class AbstractQMDocumentType implements DocumentType {

	protected static final String SAP_TYPE_ID = "DOC41.39";
	protected static final String ERR_MESS_PLANT_NOT_RETURNED = "PlantNotReturned";
	/**
	 * Attribute name of country defined in KGS customizing.
	 */
	public static final String ATTRIB_NAME_COUNTRY = "COUNTRY";
	/**
	 * Attribute name of automatic version number defined in KGS customizing.
	 */
	public static final String ATTRIB_NAME_XVERSION = "XVERSION";
	/**
	 * Attribute name of vendor number defined in KGS customizing.
	 */
	public static final String ATTRIB_NAME_VENDOR_NUMBER = "VENDOR NUMBER";
	/**
	 * SAP document type identifier defined in KGS customizing.
	 */
	public static final String SAP_OBJECT_BATCH_OBJ = "BUS2012";
	/**
	 * Attribute name of object type 2 required for document type linking in SAP.
	 */
	public static final String ATTRIB_OBJECT_TYPE_2 = "OBJTYPE_2";
	/**
	 * Attribute value of document type 2 required for document type linking in SAP.
	 */
	public static final String VALUE_OBJECT_TYPE_2 = "BUS1001002";
	/**
	 * Attribute name of object id 2 required for document type linking in SAP.
	 */
	public static final String ATTRIB_OBJECT_ID_2 = "OBJECTID_2";
	/**
	 * Attribute name of document type 2 required for document type linking in SAP.
	 */
	public static final String ATTRIB_DOCUMENT_TYPE_2 = "DOCTYPE_2";

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
	 * Gets specific SAP type ID determined for document type.
	 * 
	 * @return SAP type ID determined for document type.
	 */
	@Override
	public String getSapTypeId() {
		return SAP_TYPE_ID;
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
		return new HashSet<String>(Arrays.asList(ATTRIB_NAME_COUNTRY, Doc41Constants.ATTRIB_NAME_PURCHASE_ORDER,
				ATTRIB_NAME_XVERSION, ATTRIB_NAME_VENDOR_NUMBER));
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

}
