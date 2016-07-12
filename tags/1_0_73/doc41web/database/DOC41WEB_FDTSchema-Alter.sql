
------------------------------------
-- Alter-Script: CVS v1.0 -> v1.1 --
------------------------------------
-- New versioning
-- Enhancements for DOC41WEB

INSERT INTO  doc41web_fdt.Versions VALUES ( 'Foundation-X', 1, 1);

GRANT                                 REFERENCES ON UM_USERS       TO DOC41WEB_MGR WITH GRANT OPTION;
GRANT                                 REFERENCES ON UM_PROFILES    TO DOC41WEB_MGR WITH GRANT OPTION;
GRANT DELETE                                     ON UM_PERMISSIONS TO MXDOC41WEB;


------------------------------------
-- Alter-Script: CVS v1.1 -> v1.2 --
------------------------------------

UPDATE um_permissions set TYPE = 'LS' where code like '%\_LS' ESCAPE '\';
UPDATE um_permissions set TYPE = 'PM' where code like '%\_PM' ESCAPE '\';
UPDATE um_permissions set TYPE = 'SD', assign_Profile = 0 where code like '%\_DOWN' ESCAPE '\' AND code LIKE '%DIRECT%';
UPDATE um_permissions set TYPE = 'TOPNAV' where code like 'TOPNAV%';
UPDATE um_permissions set TYPE = 'NAV' where code like 'NAV%';
UPDATE um_permissions set TYPE = 'UM' where code like 'USER%';
UPDATE um_permissions set TYPE = 'UM' where code like 'PROFILE%';
UPDATE um_permissions set TYPE = 'UM' where code like 'ADDINT%';
UPDATE um_permissions set TYPE = 'QM' where code like '%\_DOWN\_%' ESCAPE '\' AND code NOT LIKE '%DIRECT%' AND TYPE IS NULL;
UPDATE um_permissions set TYPE = 'QM' where code IN ('DOC_DELCERT_UP','DOC_SUPCOA_UP');
UPDATE um_permissions set TYPE = 'SD' where code like '%\_UP' ESCAPE '\' AND code NOT LIKE '%DIRECT%' AND TYPE IS NULL;
UPDATE um_permissions set TYPE = 'SD' where code like '%\_DOWN' ESCAPE '\' AND code NOT LIKE '%DIRECT%' AND TYPE IS NULL;
UPDATE um_permissions set TYPE = 'RO' where code = 'READ_ONLY';
--select * from um_permissions where type IS NULL;
UPDATE um_permissions set TYPE = 'ADM' where type IS NULL;

UPDATE um_permissions set TYPE = 'ADM_OTH' where type='ADM';
UPDATE um_permissions set TYPE = 'ADM_UM' where type='UM';
UPDATE um_permissions set TYPE = 'DOC_LS' where type='LS';
UPDATE um_permissions set TYPE = 'DOC_QM' where type='QM';
UPDATE um_permissions set TYPE = 'DOC_SD' where type='SD';
UPDATE um_permissions set TYPE = 'DOC_PM' where type='PM';
UPDATE um_permissions set TYPE = 'NAV_OTH' where type='NAV';
UPDATE um_permissions set TYPE = 'NAV_TOP' where type='TOPNAV';

Insert into UM_PERMISSIONS
   (PERMISSIONNAME, PERMISSIONDESCRIPTION, CODE, ASSIGN_USER, ASSIGN_GROUP, ASSIGN_PROFILE, TYPE, IS_DELETED)
Values
   ('DocumentAntiCounterfeitingSpecificationPM', 'Download Anti-Counterfeiting Specification PM', 'DOC_ACS_DOWN_PM', 0, 0, 1, 'DOC_PM', 0)
;
COMMIT;

Insert into UM_PGU_PERMISSIONS
   (PERMISSION_ID, PROFILE_ID, IS_DELETED)
 Values
   ( (SELECT object_Id FROM UM_PERMISSIONS WHERE CODE='DOC_ACS_DOWN_PM'),
     (SELECT object_Id FROM UM_PROFILES WHERE PROFILENAME = 'doc41_pmsup'),
     0
   );
COMMIT;

UPDATE UM_Profiles SET is_Deleted = 1 WHERE profilename in ('adm','int','exu');
UPDATE UM_Profiles SET isExternal = NULL WHERE profilename = 'doc41_obsv';
INSERT INTO DisplayText d ( texttype_Id, text_Id, language_Iso_Code, code, orderBy, text, description ) VALUES ( 9500, 1, 'en', 'ADM', 1, 'Admin', 'Administration Profiles (intern)');
INSERT INTO DisplayText d ( texttype_Id, text_Id, language_Iso_Code, code, orderBy, text, description ) VALUES ( 9500, 2, 'en', 'SD', 2, 'Admin', 'SD Profiles (extern)');
INSERT INTO DisplayText d ( texttype_Id, text_Id, language_Iso_Code, code, orderBy, text, description ) VALUES ( 9500, 3, 'en', 'QM', 3, 'Admin', 'QM Profiles (extern)');
INSERT INTO DisplayText d ( texttype_Id, text_Id, language_Iso_Code, code, orderBy, text, description ) VALUES ( 9500, 4, 'en', 'PT', 4, 'Admin', 'PT Profiles (extern)');
INSERT INTO DisplayText d ( texttype_Id, text_Id, language_Iso_Code, code, orderBy, text, description ) VALUES ( 9500, 5, 'en', 'OTH', 5, 'Admin', 'other Profiles');
INSERT INTO DisplayText d ( texttype_Id, text_Id, language_Iso_Code, code, orderBy, text, description, is_Active ) VALUES ( 9500, 9, 'en', 'USR', 9, 'User', 'User Profiles (deleted)', 0);
UPDATE UM_Profiles set type = 'ADM' WHERE profilename in ('adm','doc41_badm','doc41_tadm');
UPDATE UM_Profiles set type = 'USR' WHERE profilename in ('int','exu');
UPDATE UM_Profiles set type = 'SD' WHERE profilename in ('doc41_carr');
UPDATE UM_Profiles set type = 'QM' WHERE profilename in ('doc41_delcertvcountry','doc41_delcertvcust','doc41_matsup','doc41_prodsup');
UPDATE UM_Profiles set type = 'PT' WHERE profilename in ('doc41_laysup','doc41_pmsup');
UPDATE UM_Profiles set type = 'OTH' WHERE profilename in ('doc41_obsv');

ALTER TABLE UM_Profiles ADD d41_Order_By NUMBER(3);

UPDATE UM_Profiles set d41_Order_By = 20 WHERE profilename in ('doc41_badm');
UPDATE UM_Profiles set d41_Order_By = 22 WHERE profilename in ('doc41_tadm');
UPDATE UM_Profiles set d41_Order_By = 40 WHERE profilename in ('doc41_carr');
UPDATE UM_Profiles set d41_Order_By = 60 WHERE profilename in ('doc41_matsup');
UPDATE UM_Profiles set d41_Order_By = 62 WHERE profilename in ('doc41_prodsup');
UPDATE UM_Profiles set d41_Order_By = 64 WHERE profilename in ('doc41_delcertvcountry');
UPDATE UM_Profiles set d41_Order_By = 66 WHERE profilename in ('doc41_delcertvcust');
UPDATE UM_Profiles set d41_Order_By = 80 WHERE profilename in ('doc41_laysup');
UPDATE UM_Profiles set d41_Order_By = 82 WHERE profilename in ('doc41_pmsup');
UPDATE UM_Profiles set d41_Order_By = 100 WHERE profilename in ('doc41_obsv');

ALTER TABLE UM_Permissions ADD has_Customer NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT UMPerm_HasCust_Chk CHECK (has_Customer IN (0, 1));
ALTER TABLE UM_Permissions ADD has_Vendor   NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT UMPerm_HasVend_Chk CHECK (has_Vendor   IN (0, 1));
ALTER TABLE UM_Permissions ADD has_Country  NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT UMPerm_HasCoun_Chk CHECK (has_Country  IN (0, 1));
ALTER TABLE UM_Permissions ADD has_Plant    NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT UMPerm_HasPlan_Chk CHECK (has_Plant    IN (0, 1));

UPDATE UM_Permissions SET has_Customer = 1 WHERE code = 'DOC_DELCERT_DOWN_CUSTOMER';
UPDATE UM_Permissions SET has_Country  = 1 WHERE code = 'DOC_DELCERT_DOWN_COUNTRY';
UPDATE UM_Permissions SET has_Plant    = 1 WHERE code IN ('DOC_SUPCOA_UP','DOC_DELCERT_UP');
UPDATE UM_Permissions SET has_Vendor   = 1 WHERE TYPE IN ('DOC_SD','DOC_QM','DOC_LS','DOC_PM');
UPDATE UM_Permissions SET has_Vendor   = 0 WHERE code IN ('DOC_DELCERT_DOWN_CUSTOMER','DOC_DELCERT_DOWN_COUNTRY');

INSERT INTO UM_Users ( cwid, createdBy, changedBy, firstname, lastname, language_Code, country_Code, display_Language_Code, display_Country_Code, isExternal )
  VALUES ( 'DS_CARR', '_DOC41_', '_DOC41_', 'DocService', 'DocService', 'de', 'DE', 'de', 'DE', 1 );
INSERT INTO UM_USERS_PROFILES (user_Id, profile_Id, createdBy, changedBy)
  SELECT u.object_Id, pr.object_Id, '_DOC41_', '_DOC41_' FROM UM_Users u, UM_Profiles pr WHERE (u.cwid = 'DS_CARR') AND (pr.profilename = 'doc41_carr');

UPDATE Versions SET subVersion = 2 WHERE ( module = 'Foundation-X' ) AND ( subVersion = 1 );
COMMIT WORK;



------------------------------------
-- Alter-Script: CVS v1.2 -> v1.3 --
------------------------------------


INSERT INTO UM_PERMISSIONS (permissionName, permissionDescription, code, assign_User, assign_Group, assign_Profile, type, is_Deleted) VALUES
 ( 'DocumentGlobalPM', 'Document Global PM Search', 'DOC_GLO_PM', 0, 0, 1, 'DOC_PM', 0 );
INSERT INTO UM_PERMISSIONS (permissionName, permissionDescription, code, assign_User, assign_Group, assign_Profile, type, is_Deleted) VALUES
 ( 'DocumentGlobalLS', 'Document Global LS Search', 'DOC_GLO_LS', 0, 0, 1, 'DOC_LS', 0 );
INSERT INTO UM_PERMISSIONS (permissionName, permissionDescription, code, assign_User, assign_Group, assign_Profile, type, is_Deleted) VALUES
 ( 'DocumentGlobalSD', 'Document Global SD Search', 'DOC_GLO_SD', 0, 0, 1, 'DOC_SD', 0 );
INSERT INTO UM_PGU_PERMISSIONS (permission_Id, profile_Id, is_Deleted)
  SELECT pe.object_Id AS permission_Id, pr.object_Id AS profile_Id, 0 AS is_Deleted FROM UM_Permissions pe, UM_Profiles pr WHERE (pe.code = 'DOC_GLO_PM') AND (pr.profileName = 'doc41_pmsup');
INSERT INTO UM_PGU_PERMISSIONS (permission_Id, profile_Id, is_Deleted)
  SELECT pe.object_Id AS permission_Id, pr.object_Id AS profile_Id, 1 AS is_Deleted FROM UM_Permissions pe, UM_Profiles pr WHERE (pe.code = 'DOC_GLO_LS') AND (pr.profileName = 'doc41_laysup');
INSERT INTO UM_PGU_PERMISSIONS (permission_Id, profile_Id, is_Deleted)
  SELECT pe.object_Id AS permission_Id, pr.object_Id AS profile_Id, 0 AS is_Deleted FROM UM_Permissions pe, UM_Profiles pr WHERE (pe.code = 'DOC_GLO_SD') AND (pr.profileName = 'doc41_carr');

-- GENERATOR:
-- set lines 1000
-- set pages 1000
-- select
--  'UPDATE UM_Permissions SET permissionDescription = '''||SUBSTR(permissionDescription||'''                                                                             ',1,70)||
--  'WHERE code = '''||SUBSTR(code||'''                                             ',1,40)||'; -- ' || type || ' - ' || permissionName AS " "
-- from um_permissions order by type, code

SET DEFINE OFF
UPDATE UM_Permissions SET permissionDescription = 'Admin General KGS Customizing'                   WHERE code = 'KGSCUSTOMIZING'                         ; -- ADM_OTH - KGSCustomizing
UPDATE UM_Permissions SET permissionDescription = 'Admin General Monitoring'                        WHERE code = 'MONITORING'                             ; -- ADM_OTH - Monitoring
UPDATE UM_Permissions SET permissionDescription = 'Admin General RFC Metadata View'                 WHERE code = 'RFCMETADATA'                            ; -- ADM_OTH - RFCMetadata
UPDATE UM_Permissions SET permissionDescription = 'Admin General Translation'                       WHERE code = 'TRANSLATION'                            ; -- ADM_OTH - Translation
UPDATE UM_Permissions SET permissionDescription = 'Admin General Untranslated Labels View'          WHERE code = 'UNTRANSLATEDLABELS'                     ; -- ADM_OTH - UntranslatedLabels
UPDATE UM_Permissions SET permissionDescription = 'Admin UM Internal User To Log Group'             WHERE code = 'ADDINTERNALUSERTOLOGGROUP'              ; -- ADM_UM - AddInternalUserToLogGroup
UPDATE UM_Permissions SET permissionDescription = 'Admin UM Profile Permissions View'               WHERE code = 'PROFILEPERMISSIONS'                     ; -- ADM_UM - ProfilePermissions
UPDATE UM_Permissions SET permissionDescription = 'Admin UM User Create'                            WHERE code = 'USER_CREATE'                            ; -- ADM_UM - UserCreate
UPDATE UM_Permissions SET permissionDescription = 'Admin UM User Edit'                              WHERE code = 'USER_EDIT'                              ; -- ADM_UM - UserEdit
UPDATE UM_Permissions SET permissionDescription = 'Admin UM User Import'                            WHERE code = 'USER_IMPORT'                            ; -- ADM_UM - UserImport
UPDATE UM_Permissions SET permissionDescription = 'Admin UM User List'                              WHERE code = 'USER_LIST'                              ; -- ADM_UM - UserList
UPDATE UM_Permissions SET permissionDescription = 'Admin UM User Lookup'                            WHERE code = 'USER_LOOKUP'                            ; -- ADM_UM - UserLookup
UPDATE UM_Permissions SET permissionDescription = 'Doc LS Download Artwork Low Res.'                WHERE code = 'DOC_ARTWORK_DOWN_LS'                    ; -- DOC_LS - DocumentArtworkLowResLS
UPDATE UM_Permissions SET permissionDescription = 'Doc LS Upload Artwork Low Res.'                  WHERE code = 'DOC_ARTWORK_UP_LS'                      ; -- DOC_LS - DocumentArtworkLowResUploadLS
UPDATE UM_Permissions SET permissionDescription = 'Doc LS Downl. Global Search'                     WHERE code = 'DOC_GLO_LS'                             ; -- DOC_LS - DocumentGlobalLS
UPDATE UM_Permissions SET permissionDescription = 'Doc LS Download Layout High Res.'                WHERE code = 'DOC_LAYOUT_DOWN_LS'                     ; -- DOC_LS - DocumentLayoutHighResLS
UPDATE UM_Permissions SET permissionDescription = 'Doc LS Upload Layout High Res.'                  WHERE code = 'DOC_LAYOUT_UP_LS'                       ; -- DOC_LS - DocumentLayoutHighResUploadLS
UPDATE UM_Permissions SET permissionDescription = 'Doc LS Download Packaging Material Spec.'        WHERE code = 'DOC_PMS_DOWN_LS'                        ; -- DOC_LS - DocumentPackagingMaterialSpecificationLS
UPDATE UM_Permissions SET permissionDescription = 'Doc LS Download Technical Drawing PZ'            WHERE code = 'DOC_PZ_DOWN_LS'                         ; -- DOC_LS - DocumentTechnicalDrawingPZLS
UPDATE UM_Permissions SET permissionDescription = 'Doc PM Download Anti-Counterfeiting Spec.'       WHERE code = 'DOC_ACS_DOWN_PM'                        ; -- DOC_PM - DocumentAntiCounterfeitingSpecificationPM
UPDATE UM_Permissions SET permissionDescription = 'Doc PM Download Artwork Low Res.'                WHERE code = 'DOC_ARTWORK_DOWN_PM'                    ; -- DOC_PM - DocumentArtworkLowResPM
UPDATE UM_Permissions SET permissionDescription = 'Doc PM Downl. Global Search'                     WHERE code = 'DOC_GLO_PM'                             ; -- DOC_PM - DocumentGlobalPM
UPDATE UM_Permissions SET permissionDescription = 'Doc PM Download Layout High Res.'                WHERE code = 'DOC_LAYOUT_DOWN_PM'                     ; -- DOC_PM - DocumentLayoutHighResPM
UPDATE UM_Permissions SET permissionDescription = 'Doc PM Download Packaging Material Spec.'        WHERE code = 'DOC_PMS_DOWN_PM'                        ; -- DOC_PM - DocumentPackagingMaterialSpecificationPM
UPDATE UM_Permissions SET permissionDescription = 'Doc PM Download Technical Drawing PZ'            WHERE code = 'DOC_PZ_DOWN_PM'                         ; -- DOC_PM - DocumentTechnicalDrawingPZPM
UPDATE UM_Permissions SET permissionDescription = 'Doc PM Download Tech. Pack. & Deliv. Requirem.'  WHERE code = 'DOC_TPACKDELREQ_DOWN_PM'                ; -- DOC_PM - DocumentTechPackandDelivRequirementsPM
UPDATE UM_Permissions SET permissionDescription = 'Doc QM Download Delivery Certificate Country'    WHERE code = 'DOC_DELCERT_DOWN_COUNTRY'               ; -- DOC_QM - DocumentDeliveryCertificateCountry
UPDATE UM_Permissions SET permissionDescription = 'Doc QM Download Delivery Certificate Customer'   WHERE code = 'DOC_DELCERT_DOWN_CUSTOMER'              ; -- DOC_QM - DocumentDeliveryCertificateCustomer
UPDATE UM_Permissions SET permissionDescription = 'Doc QM Upload Delivery Certificate'              WHERE code = 'DOC_DELCERT_UP'                         ; -- DOC_QM - DocumentDeliveryCertificateUpload
UPDATE UM_Permissions SET permissionDescription = 'Doc QM Upload Supplier CoA'                      WHERE code = 'DOC_SUPCOA_UP'                          ; -- DOC_QM - DocumentSupplierCoAUpload
UPDATE UM_Permissions SET permissionDescription = 'Doc SD Download Air Waybill Direct'              WHERE code = 'DOC_AWB_DIRECT_DOWN'                    ; -- DOC_SD - DocumentAirWaybillDirectDown
UPDATE UM_Permissions SET permissionDescription = 'Doc SD Download Air Waybill'                     WHERE code = 'DOC_AWB_DOWN'                           ; -- DOC_SD - DocumentAirWaybill
UPDATE UM_Permissions SET permissionDescription = 'Doc SD Upload Air Waybill'                       WHERE code = 'DOC_AWB_UP'                             ; -- DOC_SD - DocumentAirWaybillUpload
UPDATE UM_Permissions SET permissionDescription = 'Doc SD Download Bill of Lading Direct'           WHERE code = 'DOC_BOL_DIRECT_DOWN'                    ; -- DOC_SD - DocumentBillofLadingDirectDown
UPDATE UM_Permissions SET permissionDescription = 'Doc SD Download Bill of Lading'                  WHERE code = 'DOC_BOL_DOWN'                           ; -- DOC_SD - DocumentBillofLading
UPDATE UM_Permissions SET permissionDescription = 'Doc SD Upload Bill of Lading'                    WHERE code = 'DOC_BOL_UP'                             ; -- DOC_SD - DocumentBillOfLadingUpload
UPDATE UM_Permissions SET permissionDescription = 'Doc SD Download CMR Outgoing'                    WHERE code = 'DOC_CMROUT_DOWN'                        ; -- DOC_SD - DocumentCMROutgoing
UPDATE UM_Permissions SET permissionDescription = 'Doc SD Upload CMR'                               WHERE code = 'DOC_CMR_UP'                             ; -- DOC_SD - DocumentCMRUpload
UPDATE UM_Permissions SET permissionDescription = 'Doc SD Download Certificate of Origin'           WHERE code = 'DOC_COO_DOWN'                           ; -- DOC_SD - DocumentCertificateOfOriginDownload
UPDATE UM_Permissions SET permissionDescription = 'Doc SD Download FDA Certificate Direct'          WHERE code = 'DOC_FDACERT_DIRECT_DOWN'                ; -- DOC_SD - DocumentFDACertificateDirectDown
UPDATE UM_Permissions SET permissionDescription = 'Doc SD Download FDA Certificate'                 WHERE code = 'DOC_FDACERT_DOWN'                       ; -- DOC_SD - DocumentFDACertificate
UPDATE UM_Permissions SET permissionDescription = 'Doc SD Downl. Global Search'                     WHERE code = 'DOC_GLO_SD'                             ; -- DOC_SD - DocumentGlobalSD
UPDATE UM_Permissions SET permissionDescription = 'Doc SD Download Shippers Declaration Direct'     WHERE code = 'DOC_SHIPDECL_DIRECT_DOWN'               ; -- DOC_SD - DocumentShippersDeclarationDirectDown
UPDATE UM_Permissions SET permissionDescription = 'Doc SD Download Shippers Declaration'            WHERE code = 'DOC_SHIPDECL_DOWN'                      ; -- DOC_SD - DocumentShippersDeclaration
UPDATE UM_Permissions SET permissionDescription = 'Doc SD Download Waybill DirectDown'              WHERE code = 'DOC_WB_DIRECT_DOWN'                     ; -- DOC_SD - DocumentWaybillDirectDown
UPDATE UM_Permissions SET permissionDescription = 'Doc SD Download Waybill'                         WHERE code = 'DOC_WB_DOWN'                            ; -- DOC_SD - DocumentWaybill
UPDATE UM_Permissions SET permissionDescription = 'Navigation Entry Application Logs'               WHERE code = 'NAV_APPLOGS'                            ; -- NAV_OTH - NavApplogs
UPDATE UM_Permissions SET permissionDescription = 'Navigation Entry KGS Customizing'                WHERE code = 'NAV_KGSCUSTOMIZING'                     ; -- NAV_OTH - NavKGSCustomizing
UPDATE UM_Permissions SET permissionDescription = 'Navigation Entry Monitoring'                     WHERE code = 'NAV_MONITORING'                         ; -- NAV_OTH - NavMonitoring
UPDATE UM_Permissions SET permissionDescription = 'Navigation Entry Profile Permissions'            WHERE code = 'NAV_PROFILEPERMISSIONS'                 ; -- NAV_OTH - NavProfilePermissions
UPDATE UM_Permissions SET permissionDescription = 'Navigation Entry RFC Metadata'                   WHERE code = 'NAV_RFCMETADATA'                        ; -- NAV_OTH - NavRFCMetadata
UPDATE UM_Permissions SET permissionDescription = 'Navigation Entry Support Console'                WHERE code = 'NAV_SUPPORTCONSOLE'                     ; -- NAV_OTH - NavSupportConsole
UPDATE UM_Permissions SET permissionDescription = 'Navigation Entry Translations'                   WHERE code = 'NAV_TRANSLATIONS'                       ; -- NAV_OTH - NavTranslations
UPDATE UM_Permissions SET permissionDescription = 'Navigation Entry Untranslated Labels'            WHERE code = 'NAV_UNTRANSLATEDLABELS'                 ; -- NAV_OTH - NavUntranslatedLabels
UPDATE UM_Permissions SET permissionDescription = 'Navigation Top Entry Download'                   WHERE code = 'TOPNAV_DOWNLOAD'                        ; -- NAV_TOP - TopNavDownload
UPDATE UM_Permissions SET permissionDescription = 'Navigation Top Entry Maintenance'                WHERE code = 'TOPNAV_MAINTENANCE'                     ; -- NAV_TOP - TopNavMaintenance
UPDATE UM_Permissions SET permissionDescription = 'Navigation Top Entry Management'                 WHERE code = 'TOPNAV_MANAGEMENT'                      ; -- NAV_TOP - TopNavManagement
UPDATE UM_Permissions SET permissionDescription = 'Navigation Top Entry My Profile'                 WHERE code = 'TOPNAV_MYPROFILE'                       ; -- NAV_TOP - TopNavMyProfile
UPDATE UM_Permissions SET permissionDescription = 'Navigation Top Entry Upload'                     WHERE code = 'TOPNAV_UPLOAD'                          ; -- NAV_TOP - TopNavUpload
UPDATE UM_Permissions SET permissionDescription = 'Restriction Allow only read access'              WHERE code = 'READ_ONLY'                              ; -- RO - ReadOnly
commit;

UPDATE UM_Permissions SET is_Deleted = 1 WHERE (code = 'DOC_GLO_LS');
UPDATE UM_PGU_PERMISSIONS SET is_Deleted = 0 WHERE (is_Deleted = 1) AND (permission_Id = (SELECT object_Id FROM UM_Permissions WHERE (code = 'DOC_GLO_LS'))); 
commit;

UPDATE Versions SET subVersion = 3 WHERE ( module = 'Foundation-X' ) AND ( subVersion = 2 );
COMMIT WORK;


------------------------------------
-- Alter-Script: CVS v1.3 -> v1.4 --
------------------------------------


-- create / assign permissions for navigation separated from permissions for doc access (with sync)

INSERT INTO UM_Permissions (Permissionname, PermissionDescription, code, type, createdBy, changedBy, Assign_Profile, is_Deleted) 
SELECT
  'Nav'||Permissionname             AS Permissionname,
  'Navigation ' || PermissionDescription    AS PermissionDescription,
  'NAV' || SUBSTR(code,4)           AS code,
  'NAV_DOC' || SUBSTR(type,4)           AS type,
  'BDS'                     AS createdBy,
  'BDS'                     AS changedBy,
  assign_Profile,
  is_Deleted
FROM
  UM_Permissions pe
WHERE
  type in ('DOC_SD', 'DOC_LS','DOC_QM','DOC_PM')
;
COMMIT;

INSERT INTO UM_PGU_Permissions pgup (permission_Id, profile_Id, createdBy, changedBy, is_Deleted)
SELECT
  pen.object_Id     AS permission_Id,
  --pe.code, pen.code, PE.TYPE, pen.type,
  pgup.profile_Id,
  'BDS' AS createdBy,
  'BDS' AS changedBy,
  pgup.is_Deleted
FROM
  UM_PGU_Permissions pgup
  INNER JOIN UM_Permissions pe  ON
    ( pgup.permission_Id    = pe.object_Id      )
  INNER JOIN UM_Permissions pen ON
    ( SUBSTR(pe.code,4)     = SUBSTR(pen.code,4)    ) AND
    ( pe.code           <> pen.code     )
WHERE
  ( pgup.profile_Id IS NOT NULL                         ) AND
  ( pe.type     IN ('DOC_SD', 'DOC_LS','DOC_QM','DOC_PM')           ) AND
  ( pen.type        IN ('NAV_DOC_SD', 'NAV_DOC_LS','NAV_DOC_QM','NAV_DOC_PM')   )
;
COMMIT;

UPDATE um_Permissions SET is_Deleted = 1 WHERE (is_Deleted = 0) AND (code IN ('DOC_LAYOUT_DOWN_PM','DOC_ACS_DOWN_PM','NAV_LAYOUT_DOWN_PM','NAV_ACS_DOWN_PM'));
COMMIT;

CREATE OR REPLACE VIEW PROFILE_PERMISSION_STAT AS
SELECT
  'PF: ' || pf.type || '--' || pf.profilename || ', PE: ' || pe.type || '--' || pe.code || ', DEL: '||pf.is_Deleted||pgup.is_Deleted||pe.is_Deleted AS Prof_Perm
FROM
  UM_Profiles pf
  INNER JOIN UM_PGU_Permissions pgup    ON ( pf.object_Id   = pgup.profile_Id   )
  INNER JOIN UM_Permissions pe      ON ( pgup.permission_Id = pe.object_Id      )
ORDER BY
  1
;
GRANT SELECT ON PROFILE_PERMISSION_STAT TO MXDOC41WEB;

UPDATE UM_Permissions SET is_Deleted = 1 WHERE is_Deleted = 0 AND type = 'DOC_LS';
COMMIT;
UPDATE UM_Permissions SET is_Deleted = 1 WHERE is_Deleted = 0 AND type = 'NAV_DOC_LS';
COMMIT;
UPDATE UM_Profiles SET is_Deleted = 1 WHERE is_Deleted = 0 AND profilename = 'doc41_laysup';
COMMIT;
UPDATE UM_Profiles SET is_Deleted = 1 WHERE is_Deleted = 0 AND profilename = 'doc41_pmsup';
COMMIT;

UPDATE Versions SET subVersion = 4 WHERE ( module = 'Foundation-X' ) AND ( subVersion = 3 );
COMMIT WORK;


------------------------------------
-- Alter-Script: CVS v1.4 -> v1.5 --
------------------------------------

UPDATE UM_PGU_Permissions SET is_deleted = 0 WHERE (is_Deleted = 1) AND (permission_Id IS NOT NULL) AND (permission_Id IN (
  SELECT object_Id FROM UM_Permissions WHERE (code LIKE 'NAV_GLO%')
));
-- should activate 3 assignments, LS perm is deleted so is ok, done on dev & qa
COMMIT;

UPDATE UM_Permissions SET is_Deleted = 0 WHERE (is_Deleted = 1) AND ((code LIKE 'NAV%PM') OR (code LIKE 'DOC%PM')) AND (code NOT LIKE '%LAYOUT%PM') AND (code NOT LIKE '%ACS%PM');
-- should hit 8 permissions (DOC/NAV*ACS/LAYOUT excluded), done on dev & qa
COMMIT;

UPDATE UM_Profiles SET is_Deleted = 0 WHERE is_Deleted = 1 AND profilename = 'doc41_pmsup';
-- hit 1, done on DEV & QA
COMMIT;

UPDATE Versions SET subVersion = 5 WHERE ( module = 'Foundation-X' ) AND ( subVersion = 4 );
COMMIT WORK;


------------------------------------
-- Alter-Script: CVS v1.5 -> v1.6 --
------------------------------------

UPDATE UM_PGU_Permissions SET is_deleted = 1 WHERE (is_Deleted = 0) AND (permission_Id IS NOT NULL) AND (permission_Id IN (
  SELECT object_Id FROM UM_Permissions WHERE (type IN ('NAV_DOC_PM', 'NAV_DOC_SD')) AND (code <> 'NAV_GLO_PM') AND (code <> 'NAV_GLO_SD') AND (code not LIKE '%UP')
));
-- should deactivate 12 single down assignments, LS perm is deleted so is ok, done on dev ONLY
COMMIT;

UPDATE um_Permissions SET is_Deleted = 0 WHERE (is_Deleted = 1) AND (code IN ('DOC_LAYOUT_DOWN_PM','DOC_ACS_DOWN_PM','NAV_LAYOUT_DOWN_PM','NAV_ACS_DOWN_PM'));
COMMIT;


UPDATE Versions SET subVersion = 6 WHERE ( module = 'Foundation-X' ) AND ( subVersion = 5 );
COMMIT WORK;

------------------------------------
-- Alter-Script: CVS v1.6 -> v1.7 --
------------------------------------

UPDATE UM_PGU_Permissions SET is_Deleted = 0 WHERE (is_Deleted = 1) AND (permission_Id IN ( 
SELECT object_Id FROM UM_Permissions WHERE (is_Deleted = 0) AND (type = 'NAV_DOC_SD') AND (permissionname NOT LIKE '%Direct%') AND (permissionname NOT LIKE '%GlobalSD') AND (permissionname NOT LIKE '%Upload%')
))
;

UPDATE UM_PGU_Permissions SET is_Deleted = 1 WHERE (is_Deleted = 0) AND (permission_Id IN ( 
SELECT object_Id from UM_Permissions WHERE (is_Deleted = 0) AND (type = 'NAV_DOC_SD') AND (permissionname LIKE '%GlobalSD')
)) AND (profile_Id IN(
SELECT object_Id FROM UM_Profiles WHERE profilename NOT LIKE '%glo'
))
;

UPDATE UM_PGU_Permissions SET is_deleted = 0 WHERE (is_Deleted = 1) AND (permission_Id IS NOT NULL) AND (permission_Id IN (
  SELECT object_Id FROM UM_Permissions WHERE (code LIKE 'NAV_GLO_PM')
));


UPDATE Versions SET subVersion = 7 WHERE ( module = 'Foundation-X' ) AND ( subVersion = 6 );
COMMIT WORK;
