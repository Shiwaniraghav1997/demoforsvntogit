package com.bayer.bhc.doc41webui.common;

public interface Doc41Constants {

	public static final String USERNAME_TASK = "*TASK*";
	public static final String USERNAME_TEST = "*TEST*";

	public static final String PERMISSION_ADDINTERNALUSERTOLOGGROUP = "ADDINTERNALUSERTOLOGGROUP";
	public static final String PERMISSION_MONITORING = "MONITORING";
	public static final String PERMISSION_TRANSLATION = "TRANSLATION";
	public static final String PERMISSION_PROFILEPERMISSIONS = "PROFILEPERMISSIONS";
	public static final String PERMISSION_RFCMETADATA = "RFCMETADATA";
	public static final String PERMISSION_KGSCUSTOMIZING = "KGSCUSTOMIZING";
	public static final String PERMISSION_UNTRANSLATEDLABELS = "UNTRANSLATEDLABELS";
	public static final String PERMISSION_USER_CREATE = "USER_CREATE";
	public static final String PERMISSION_USER_EDIT = "USER_EDIT";
	public static final String PERMISSION_USER_IMPORT = "USER_IMPORT";
	public static final String PERMISSION_USER_LIST = "USER_LIST";
	public static final String PERMISSION_USER_LOOKUP = "USER_LOOKUP";

	public static final String PERMISSION_READ_ONLY = "READ_ONLY";

	public static final String MONITORING_SUCCESS = "Success";
	public static final String MONITORING_FAILURE = "Failure";

	public static final int FIELD_SIZE_CUSTOMER_NUMBER = 10;
	public static final int FIELD_SIZE_VENDOR_NUMBER = 10;
	public static final int FIELD_SIZE_MATNR = 18;
	public static final int FIELD_SIZE_PLANT = 4;
	public static final int FIELD_SIZE_BATCH = 10;
	public static final int FIELD_SIZE_SD_REF_NO = 10;

	public static final int CUSTOMIZATION_VALUES_COUNT = 9;

	public static final String URL_PARAM_TYPE = "type";
	public static final String URL_PARAM_DOC_ID = "docId";
	public static final String URL_PARAM_CWID = "cwid";
	public static final String URL_PARAM_FILENAME = "filename";
	public static final String URL_PARAM_SAP_OBJ_ID = "sapObjId";
	public static final String URL_PARAM_SAP_OBJ_TYPE = "sapObjType";

	public static final String ATTRIB_NAME_FILENAME = "FILENAME";
	public static final String ATTRIB_NAME_VENDOR = "VENDOR";
	public static final String ATTRIB_NAME_MATERIAL = "MATERIAL";
	public static final String ATTRIB_NAME_VKORG = "VKORG";
	public static final String ATTRIB_NAME_PLANT = "PLANT";
	public static final String ATTRIB_NAME_WEBUI_USER = "WEBUI_USER";
	public static final String ATTRIB_NAME_I_DVSOBJTYPE = "_I_DVSOBJTYPE";
	public static final String ATTRIB_NAME_I_DVSDOCTYPE = "_I_DVSDOCTYPE";
	public static final String ATTRIB_NAME_I_NAMETYPE = "_I_NAMETYPE";

	/**
	 * Attribute name of purchase order defined in KGS customizing.
	 */
	public static final String ATTRIB_NAME_PURCHASE_ORDER = "PURCHASE ORDER";
	/**
	 * Attribute name of vendor batch defined in KGS customizing.
	 */
	public static final String ATTRIB_NAME_VENDOR_BATCH = "BATCH";
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
	 * Attribute name of object type 2 required for document type linking in SAP.
	 */
	public static final String ATTRIB_OBJECT_TYPE_2 = "OBJTYPE_2";
	/**
	 * Attribute name of object id 2 required for document type linking in SAP.
	 */
	public static final String ATTRIB_OBJECT_ID_2 = "OBJECTID_2";
	/**
	 * Attribute name of document type 2 required for document type linking in SAP.
	 */
	public static final String ATTRIB_DOCUMENT_TYPE_2 = "DOCTYPE_2";
	/**
	 * Attribute name of document identification required for document type linking
	 * in SAP.
	 */
	public static final String ATTRIB_NAME_I_DOCUMENT_IDENTIFICATION = "DOCUMENT-ID";
	/**
	 * Name of document identification type vendor packing list.
	 */
	public static final String NAME_DOCUMENT_IDENTIFICATION_VENDOR_PACKING_LIST = "VENDOR PACKING LIST";
	/**
	 * Name of SAP parameter containing user CWID.
	 */
	public static final String ATTRIB_NAME_EV_REQUESTER = "EV_REQUESTER";
	/**
	 * The ID used for identifying the corresponding persistent session for storing
	 * email notification bundles (DW-18).
	 */
	public static final String PERSISTENT_SESSION_ID = "DOC41";
	/**
	 * The component used for identifying the corresponding persistent session for
	 * storing email notification bundles (DW-18).
	 */
	public static final String PERSISTENT_SESSION_COMPONENT = "UpsMail";
	/**
	 * The flag used for choosing if exception should be thrown when the
	 * corresponding persistent session for storing email notification bundles
	 * cannot be initiated (DW-18).
	 */
	public static final Boolean PERSISTENT_SESSION_FLAG = true;
	/**
	 * The property name used for storing an email notification bundle in the
	 * corresponding persistent session for storing email notification bundles
	 * (DW-18).
	 */
	public static final String PROPERTY_NAME_EMAIL_NOTIFICATION_BUNDLE = "emailNotificationBundle";
	/**
	 * The property name used for storing the number of objects (email notification
	 * bundles or email notifications) in the corresponding persistent session for
	 * storing email notification bundles (DW-18).
	 */
	public static final String PROPERTY_NAME_NUMBER = "number";
	/**
	 * The property name used for storing the email address of the email
	 * notification bundle in the corresponding persistent session for storing email
	 * notification bundles (DW-18).
	 */
	public static final String PROPERTY_NAME_EMAIL_ADDRESS = "emailAddress";
	/**
	 * The property name used for storing an email notification in the corresponding
	 * persistent session for storing email notification bundles (DW-18).
	 */
	public static final String PROPERTY_NAME_NOTIFICATION = "emailNotification";
	/**
	 * The property name used for storing the content of an email notification in
	 * the corresponding persistent session for storing email notification bundles
	 * (DW-18).
	 */
	public static final String PROPERTY_NAME_CONTENT = "content";

}
