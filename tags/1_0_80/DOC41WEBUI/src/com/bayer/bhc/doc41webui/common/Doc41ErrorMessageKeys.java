/**
 * (C) Copyright 2008 Bayer AG Leverkusen, Bayer Business Services
 * All rights reserved.
 */
package com.bayer.bhc.doc41webui.common;


/**
 * Doc41ErrorMessageKeys. Keys for translation of exceptions.
 * 
 * @author ezzqc
 */
public interface Doc41ErrorMessageKeys {
    
	String  UNPARSABLE_TIME              = "UnparsableTime";
	
    // General
    String  TRANS_TAG_EXISTS                 = "TagAlreadyExist";//  
    String  UADM_CG_PERMISSION_EXISTS        = "CGPermissionAlreadyExist"; //"CarrierGroupPermissionAlreadyExist"
    String  UADM_PERMISSION_EXISTS           = "PermissionAlreadyExist";//permissionAlreadyExist
    String  SERVICE_NOT_FOUND                = "ServiceNotFound";
    String  USER_NOT_FOUND                   = "UserNotFound";

    String  SAVE_FILE_FAILED                 = "SaveFileFailed";

    String  USR_CREATE_FAILED               = "USRCreateFailed";//UserManagementUCImpl.createUser
    String  USR_EDIT_FAILED                 = "USREditFailed";//UserManagementUCImpl.editUser
    String  USR_FIND_FAILED                 = "USRFindFailed";//UserManagementUCImpl.findUser
    String  USR_FIND_ALL_FAILED             = "USRFindAllFailed";//UserManagementUCImpl.findUsers
    String  USR_TOGGLE_ACTIVATION_FAILED    = "USRToggleActivationFailed";//UserManagementUCImpl.toggleUserActivation
    
    String  USR_MGT_LDAP_SETPASSWORD_FAILED     = "USRMGT.ldapSetPassword";
    String  USR_MGT_LDAP_CREATEUSER_FAILED      = "USRMGT.ldapCreateUser";
    String  USR_MGT_LDAP_UPDATEUSER_FAILED      = "USRMGT.ldapUpdateUser";
    String  USR_MGT_LDAP_IMPORTUSER_FAILED      = "USRMGT.ldapImportUser";
    String  USR_MGT_LDAP_UPDATEUSERGROUPS_FAILED = "USRMGT.ldapUpdateUserGroups";
    String  USR_MGT_LDAP_LOOKUPUSER_FAILED      = "USRMGT.ldapLookupUser";
    String  USR_MGT_LDAP_AUTHINTUSER_FAILED     = "USRMGT.ldapAuthIntUser";
    String  USR_MGT_INSERT_USER_FAILED          = "USRMGT.insertUser";
    String  USR_MGT_UPDATE_USER_FAILED          = "USRMGT.updateUser";
    String  USR_MGT_LOADING_USER_PROFILES_FAILED= "USRMGT.LoadingProfiles";
    String  USR_MGT_COPY_USER_DOMAIN_DC_FAILED  = "USRMGT.CopyUserDomainDC";
    String  USR_MGT_USER_NOT_FOUND_UPDATE_FAILED= "USRMGT.UserForUpdateNotFound";

    
    //constants for monitoring
    String  MONITOR_FIND_LATEST_FAILED              = "MONITOR.findLatestMonitoringEntries";
    String  MONITOR_FIND_HISTORY_FAILED             = "MONITOR.findMonitoringHistoryByInterface";
    String  MONITOR_EDIT_CONTACT_FAILED             = "MONITOR.editContactPerson";
    String  MONITOR_FIND_CONTACT_FAILED             = "MONITOR.findEBCContactPersonByInterface";
    String  MONITOR_FIND_USER_FAILED                = "MONITOR.findUserById";
    String  MONITOR_FIND_DETAIL_FAILED              = "MONITOR.findInterfaceDetailsByName";
    String  MONITOR_ADD_CONTACT_FAILED              = "MONITOR.addContactPerson";
    String  MONITOR_ADD_INTERFACE_FAILED            = "MONITOR.addInterface";
    String  MONITOR_FIND_BACKEND_CONTACT_FAILED     = "MONITOR.findBackendContactPersonByInterface";
    String  MONITOR_INTERFACE_ALREADY_EXISTS       	= "MONITOR.interfaceAlreadyExists";
    
    // translations
    String TRANSL_FIND_TAGS_FAILED                  = "TRANSL.findTags";
    String TRANSL_CREATE_TAG_FAILED                 = "TRANSL.createTag";
    String TRANSL_EDIT_TAG_FAILED                   = "TRANSL.editTag";
    String TRANSL_DELETE_TAG_FAILED                 = "TRANSL.deleteTagById";
    String TRANSL_GET_TAG_FAILED                    = "TRANSL.getTagById";
    String TRANSL_GET_COMPONENTLIST_FAILED          = "TRANSL.getComponentList";
    String TRANSL_GET_PAGELIST_FAILED               = "TRANSL.getPageList";
    String TRANSL_DISTRIBUTE_TAGS_FAILED            = "TRANSL.distributeTags";
    String TRANSL_TAG_EXISTS_FAILED                 = "TRANSL.isTagExist";
    String TRANSL_FIND_COMPONENT_TAGS_FAILED        = "TRANSL.findComponentTags";
    

    // RFC
    String RFC_CHECKIN_CANCEL_NOT_AVAILABLE         = "RFC.checkinCancelNotAvailable";
    String RFC_CHECKOUT_CANCEL_NOT_AVAILABLE        = "RFC.checkoutCancelNotAvailable";
    String RFC_LOADING_NOT_AVAILABLE                = "RFC.loadingNotAvailable";
    String RFC_LOADING_CANCEL_NOT_AVAILABLE         = "RFC.loadingCancelNotAvailable";

   
    
}
