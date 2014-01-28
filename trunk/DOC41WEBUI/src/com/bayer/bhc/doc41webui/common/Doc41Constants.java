package com.bayer.bhc.doc41webui.common;

public interface Doc41Constants {

    public static final String USERNAME_TASK				= "*TASK*";
    public static final String USERNAME_TEST				= "*TEST*";

    public static final String PERMISSION_ADDINTERNALUSERTOLOGGROUP	= "ADDINTERNALUSERTOLOGGROUP";
    public static final String PERMISSION_MONITORING				= "MONITORING";
    public static final String PERMISSION_TRANSLATION				= "TRANSLATION";
    public static final String PERMISSION_PROFILEPERMISSIONS		= "PROFILEPERMISSIONS";
    public static final String PERMISSION_RFCMETADATA				= "RFCMETADATA";
    public static final String PERMISSION_UNTRANSLATEDLABELS		= "UNTRANSLATEDLABELS";
    public static final String PERMISSION_USER_CREATE				= "USER_CREATE";
    public static final String PERMISSION_USER_EDIT					= "USER_EDIT";
    public static final String PERMISSION_USER_IMPORT				= "USER_IMPORT";
    public static final String PERMISSION_USER_LIST					= "USER_LIST";
    public static final String PERMISSION_USER_LOOKUP				= "USER_LOOKUP";
    
    public static final String PERMISSION_READ_ONLY					= "READ_ONLY";

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
	
	public static final String ATTRIB_NAME_FILENAME = "FILENAME";
	public static final String ATTRIB_NAME_VENDOR = "VENDOR";
	public static final String ATTRIB_NAME_VKORG = "VKORG";
	public static final String ATTRIB_NAME_PLANT = "PLANT";
	public static final String ATTRIB_NAME_WEBUI_USER = "WEBUI_USER";
	public static final String ATTRIB_NAME__I_DVSOBJTYPE = "_I_DVSOBJTYPE";
	public static final String ATTRIB_NAME__I_DVSDOCTYPE = "_I_DVSDOCTYPE";
	public static final String ATTRIB_NAME__I_NAMETYPE = "_I_NAMETYPE";
}
