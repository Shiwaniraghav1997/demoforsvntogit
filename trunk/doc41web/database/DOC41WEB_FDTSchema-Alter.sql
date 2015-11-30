
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
  VALUES ( 'SD_CARR', '_DOC41_', '_DOC41_', 'DocService', 'DocService', 'de', 'DE', 'de', 'DE', 1 );
INSERT INTO UM_USERS_PROFILES (user_Id, profile_Id, createdBy, changedBy)
  SELECT u.object_Id, pr.object_Id, '_DOC41_', '_DOC41_' FROM UM_Users u, UM_Profiles pr WHERE (u.cwid = 'SD_CARR') AND (pr.profilename = 'doc41_carr');

UPDATE Versions SET subVersion = 2 WHERE ( module = 'Foundation-X' ) AND ( subVersion = 1 );
COMMIT WORK;
