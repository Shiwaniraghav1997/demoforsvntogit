package com.bayer.bhc.doc41webui.common;

public interface Doc41Constants {

    public static final String USERNAME_TASK				= "*TASK*";
    public static final String USERNAME_TEST				= "*TEST*";

    // TODO make proper Permissions: in Constants and DB
    public static final String PERMISSION_TECHNICAL_ADMIN	= "CREATE_USER";
    public static final String PERMISSION_BUSINESS_ADMIN	= "CREATE_USER";
    public static final String PERMISSION_READ_ONLY			= "READ_ONLY";
    public static final String PERMISSION_CARRIER			= "CREATE_USER";
    

    public static final String MONITORING_SUCCESS = "Success";
    public static final String MONITORING_FAILURE = "Failure";
    
    
    public static final String SAP_DOC_TYPE_AIRWAY ="DOC41.16";
    public static final String SAP_DOC_TYPE_BOL ="DOC41.14";
    public static final String[] SUPPORTED_DOC_TYPES ={SAP_DOC_TYPE_AIRWAY,SAP_DOC_TYPE_BOL};
    public static final String DOC_TYPE_AIRWAY ="AWB";
    public static final String DOC_TYPE_BOL ="BOL";
}
