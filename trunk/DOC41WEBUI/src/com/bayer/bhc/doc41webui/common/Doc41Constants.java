package com.bayer.bhc.doc41webui.common;

public interface Doc41Constants {

    public static final String USERNAME_TASK				= "*TASK*";
    public static final String USERNAME_TEST				= "*TEST*";

    public static final String PERMISSION_ADDINTERNALUSERTOLOGGROUP	= "ADDINTERNALUSERTOLOGGROUP";
    public static final String PERMISSION_MONITORING				= "MONITORING";
    public static final String PERMISSION_TRANSLATION				= "TRANSLATION";
    public static final String PERMISSION_PROFILEPERMISSIONS		= "PROFILEPERMISSIONS";
    public static final String PERMISSION_RFCMETADATA				= "RFCMETADATA";
    public static final String PERMISSION_USER_CREATE				= "USER_CREATE";
    public static final String PERMISSION_USER_EDIT					= "USER_EDIT";
    public static final String PERMISSION_USER_IMPORT				= "USER_IMPORT";
    public static final String PERMISSION_USER_LIST					= "USER_LIST";
    public static final String PERMISSION_USER_LOOKUP				= "USER_LOOKUP";
    
    public static final String PERMISSION_READ_ONLY					= "READ_ONLY";
    public static final String PERMISSION_DOC_BOL_UP				= "DOC_BOL_UP";
    public static final String PERMISSION_DOC_AWB_UP				= "DOC_AWB_UP";
    

    public static final String MONITORING_SUCCESS = "Success";
    public static final String MONITORING_FAILURE = "Failure";
    
    
    public static final String SAP_DOC_TYPE_AIRWAY ="DOC41.16";
    public static final String SAP_DOC_TYPE_BOL ="DOC41.14";
    public static final String[] SUPPORTED_DOC_TYPES ={SAP_DOC_TYPE_AIRWAY,SAP_DOC_TYPE_BOL};
    public static final String DOC_TYPE_AIRWAY ="AWB";
    public static final String DOC_TYPE_BOL ="BOL";
    
    public static final int FIELD_SIZE_PARTNER_NUMBER = 10;
}
