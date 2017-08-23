-- WATCH FOR TS: DOC41WEB_DAT, DOC41WEB_IDX, MXDOC41WEB, DOC41WEB_MGR, Schema-Ref MXDOC41WEB_FDT.*, INITIAL-Sizes of IDX/TABS
--
-- Create Schema Script 
--   Database Version   : 10.2.0.1.0 
--   Toad Version       : 10.5.1.3 
--   DB Connect String  : byz2f8:1521/XE 
--   Schema             : UMGMT 
--   Script Created by  : GBO_ADMIN 
--   Script Created at  : 19.01.2012 12:43:29 
--   Physical Location  :  
--   Notes              :  
--

-- Object Counts: 
--   Directories: 0 
--   Functions: 1       Lines of Code: 12 
--   Indexes: 44        Columns: 80         
--   Object Privileges: 12 
--   Tables: 12         Columns: 182        Constraints: 41     
--   Triggers: 18 


-- "Set define off" turns off substitution variables. 
Set define off; 

--
-- UM_PROFILES  (Table) 
--
CREATE TABLE UM_PROFILES
(
  OBJECT_ID           NUMBER(27)                NOT NULL,
  PROFILENAME         VARCHAR2(60 CHAR)         NOT NULL,
  PROFILEDESCRIPTION  VARCHAR2(500 CHAR),
  CREATED             DATE,
  CHANGED             DATE,
  CREATEDBY           VARCHAR2(15 CHAR),
  CHANGEDBY           VARCHAR2(15 CHAR),
  ISEXTERNAL          NUMBER(1),
  USERCHANGED         DATE,
  QUICK               VARCHAR2(1000 CHAR),
  CREATEDON           VARCHAR2(100 CHAR),
  CHANGEDON           VARCHAR2(100 CHAR)
)
TABLESPACE DOC41WEB_DAT
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_PERMISSIONS  (Table) 
--
CREATE TABLE UM_PERMISSIONS
(
  OBJECT_ID              NUMBER(27)             NOT NULL,
  PERMISSIONNAME         VARCHAR2(60 CHAR),
  PERMISSIONDESCRIPTION  VARCHAR2(500 CHAR),
  CODE                   VARCHAR2(150 CHAR),
  CREATED                DATE,
  CHANGED                DATE,
  CREATEDBY              VARCHAR2(15 CHAR),
  CHANGEDBY              VARCHAR2(15 CHAR),
  ASSIGN_USER            NUMBER(1)              DEFAULT 0                     NOT NULL,
  ASSIGN_GROUP           NUMBER(1)              DEFAULT 0                     NOT NULL,
  ASSIGN_PROFILE         NUMBER(1)              DEFAULT 1                     NOT NULL,
  TYPE                   VARCHAR2(20 CHAR),
  ORDERBY                NUMBER(4),
  USERCHANGED            DATE,
  QUICK                  VARCHAR2(1000 CHAR),
  CREATEDON              VARCHAR2(100 CHAR),
  CHANGEDON              VARCHAR2(100 CHAR)
)
TABLESPACE DOC41WEB_DAT
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_OBJECTSTATE  (Table) 
--
CREATE TABLE UM_OBJECTSTATE
(
  OBJECTSTATE_ID  NUMBER(2)                     NOT NULL,
  OBJECTSTATE     VARCHAR2(10 CHAR)
)
TABLESPACE DOC41WEB_DAT
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_GROUP_TYPE  (Table) 
--
CREATE TABLE UM_GROUP_TYPE
(
  TOPTYPE  VARCHAR2(20 CHAR)                    NOT NULL
)
TABLESPACE DOC41WEB_DAT
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- GROUP_TYPE_PK  (Index) 
--
CREATE UNIQUE INDEX GROUP_TYPE_PK ON UM_GROUP_TYPE
(TOPTYPE)
TABLESPACE DOC41WEB_DAT
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- KEY_PROFILES  (Index) 
--
CREATE UNIQUE INDEX KEY_PROFILES ON UM_PROFILES
(PROFILENAME)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- KEY_UMPERMISSIONS  (Index) 
--
CREATE UNIQUE INDEX KEY_UMPERMISSIONS ON UM_PERMISSIONS
(PERMISSIONNAME)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- PK_UMOBJECTSTATE  (Index) 
--
CREATE UNIQUE INDEX PK_UMOBJECTSTATE ON UM_OBJECTSTATE
(OBJECTSTATE_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- PK_UMPERMISSIONS  (Index) 
--
CREATE UNIQUE INDEX PK_UMPERMISSIONS ON UM_PERMISSIONS
(OBJECT_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- PK_UMPROFILES  (Index) 
--
CREATE UNIQUE INDEX PK_UMPROFILES ON UM_PROFILES
(OBJECT_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


----
---- FDT_NORM_QUICK  (Function) 
----
--CREATE OR REPLACE FUNCTION
--  FDT_Norm_Quick(
--    val VARCHAR2
--  )
--RETURN
--  VARCHAR2
--DETERMINISTIC
--IS
----  result VARCHAR2(100) := SUBSTR( phone, 1, 100 );
--BEGIN
--  RETURN '|' || REPLACE( UPPER( TRIM( REPLACE( REPLACE( REPLACE( REPLACE( TRANSLATE( val, '.,;:@+-_#*/\', '           '), '    ', ' ' ), '  ',' '), '  ', ' '), 'ß', 'SS' ))), ' ', '||' ) || '|';
--END FDT_Norm_Quick;
--/
--
--
----
---- UM_PROFILES_QCK  (Trigger) 
----
--CREATE OR REPLACE TRIGGER UM_PROFILES_QCK
--BEFORE INSERT OR UPDATE ON
--  UM_PROFILES
--FOR EACH ROW
--DECLARE
--  val VARCHAR2(1000 CHAR);
--BEGIN
--  val := FDT_Norm_Quick(
--    NVL( :NEW.profileName, '' )       || ' ' ||
--    NVL( :NEW.profileDescription, '' )
--  );
--  IF ( val <> NVL( :NEW.quick, ' ' ) ) THEN
--    :NEW.quick := val;
--  END IF;
--END UM_PROFILES_QCK;
--/
--
--
----
---- UM_PERMISSIONS_QCK  (Trigger) 
----
--CREATE OR REPLACE TRIGGER UM_PERMISSIONS_QCK
--BEFORE INSERT OR UPDATE ON
--  UM_PERMISSIONS
--FOR EACH ROW
--DECLARE
--  val VARCHAR2(1000 CHAR);
--BEGIN
--  val := FDT_Norm_Quick(
--    NVL( :NEW.permissionName, '')         || ' ' ||
--    NVL( :NEW.permissionDescription, '' ) || ' ' ||
--    NVL( :NEW.type, '' )
--  );
--  IF ( val <> NVL( :NEW.quick, ' ' ) ) THEN
--    :NEW.quick := val;
--  END IF;
--END UM_PERMISSIONS_QCK;
--/
--

--
-- UM_PROFILES_CDT  (Trigger) 
--
CREATE OR REPLACE TRIGGER UM_PROFILES_CDT
BEFORE INSERT OR UPDATE ON
  UM_PROFILES
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :NEW.created := DOC41WEB_FDT.get_Normalized_Time();
  END IF;
  IF (UPDATING) THEN
    :NEW.created   := :OLD.created;
    :NEW.createdBy := :OLD.createdBy;
    :NEW.createdOn := :OLD.createdOn;
  END IF;
  :NEW.changed := DOC41WEB_FDT.get_Normalized_Time();
  IF (:NEW.userChanged > (DOC41WEB_FDT.get_Normalized_Time() + 365750) OR INSERTING) THEN
    :NEW.userChanged := :NEW.changed;
  ELSE
    :NEW.userChanged := :OLD.userChanged;
  END IF;
  IF (INSERTING AND :NEW.object_Id IS NULL) THEN
    :NEW.object_Id := DOC41WEB_FDT.Get_Next_Oid();
  ELSIF (UPDATING) THEN
    :NEW.object_Id := :OLD.object_Id;
  END IF;
END UM_PROFILES_CDT;
/


--
-- UM_PERMISSIONS_CDT  (Trigger) 
--
CREATE OR REPLACE TRIGGER UM_PERMISSIONS_CDT
BEFORE INSERT OR UPDATE ON
  UM_PERMISSIONS
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :NEW.created := DOC41WEB_FDT.get_Normalized_Time();
  END IF;
  IF (UPDATING) THEN
    :NEW.created   := :OLD.created;
    :NEW.createdBy := :OLD.createdBy;
    :NEW.createdOn := :OLD.createdOn;
  END IF;
  :NEW.changed := DOC41WEB_FDT.get_Normalized_Time();
  IF (:NEW.userChanged > (DOC41WEB_FDT.get_Normalized_Time() + 365750) OR INSERTING) THEN
    :NEW.userChanged := :NEW.changed;
  ELSE
    :NEW.userChanged := :OLD.userChanged;
  END IF;
  IF (INSERTING AND :NEW.object_Id IS NULL) THEN
    :NEW.object_Id := DOC41WEB_FDT.Get_Next_Oid();
  ELSIF (UPDATING) THEN
    :NEW.object_Id := :OLD.object_Id;
  END IF;
END UM_PERMISSIONS_CDT;
/


--
-- UM_USERS  (Table) 
--
CREATE TABLE UM_USERS
(
  OBJECT_ID                 NUMBER(27)          NOT NULL,
  OBJECTSTATE_ID            NUMBER(2)           DEFAULT 1                     NOT NULL,
  OBJECTSTATEINTERNAL_ID    NUMBER(2)           DEFAULT 1                     NOT NULL,
  CWID                      VARCHAR2(10 CHAR),
  LOGIN_ID                  VARCHAR2(15 CHAR),
  LOGIN_PASSWORD            VARCHAR2(15 CHAR),
  SALUTATION_ID             NUMBER(4),
  TITLE                     VARCHAR2(100 CHAR),
  LASTNAME                  VARCHAR2(70 CHAR),
  MIDDLEINITIAL             VARCHAR2(30 CHAR),
  FIRSTNAME                 VARCHAR2(50 CHAR),
  EMAIL                     VARCHAR2(70 CHAR),
  PHONE1                    VARCHAR2(50 CHAR),
  PHONE2                    VARCHAR2(35 CHAR),
  FAX                       VARCHAR2(35 CHAR),
  ADDRESS                   VARCHAR2(500 CHAR),
  TIMEZONE_ID               NUMBER(4),
  LANGUAGE_CODE             VARCHAR2(2 CHAR),
  COUNTRY_CODE              VARCHAR2(2 CHAR),
  DISPLAY_LANGUAGE_CODE     VARCHAR2(2 CHAR),
  DISPLAY_COUNTRY_CODE      VARCHAR2(2 CHAR),
  STATE_PROVINCE            VARCHAR2(70 CHAR),
  TERMSOFUSE                DATE,
  TERMSOFUSELAST            DATE,
  CREATED                   DATE,
  CHANGED                   DATE,
  CREATEDBY                 VARCHAR2(10 CHAR),
  CHANGEDBY                 VARCHAR2(10 CHAR),
  LASTLOGIN                 DATE,
  ISEXTERNAL                NUMBER(1),
  REPORTS_SHOW_LOGS         NUMBER(1)           DEFAULT 0                     NOT NULL,
  USERCHANGED               DATE,
  QUICK                     VARCHAR2(1000 CHAR),
  CREATEDON                 VARCHAR2(100 CHAR),
  CHANGEDON                 VARCHAR2(100 CHAR),
  EXT_ID                    VARCHAR2(50 CHAR),
  SK_REFRESH                NUMBER(1),
  SPONSOREDBY               VARCHAR2(100 CHAR),
  EXPIRY_DATE               DATE,
  EXPIRY_MAIL_SENT          DATE,
  EXPIRY_REMINDER_SENT      DATE,
  WAS_LOCKED_INACTIVE_DATE  DATE,
  WAS_LOCKED_REASON         VARCHAR2(30 BYTE)
)
TABLESPACE DOC41WEB_DAT
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          2M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_GROUP_SUBTYPE  (Table) 
--
CREATE TABLE UM_GROUP_SUBTYPE
(
  SUBTYPE  VARCHAR2(30 CHAR)                    NOT NULL,
  TOPTYPE  VARCHAR2(20 CHAR)                    NOT NULL
)
TABLESPACE DOC41WEB_DAT
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- FK_UMUSERS_UMOBJECTSTATE2_IDX  (Index) 
--
CREATE INDEX FK_UMUSERS_UMOBJECTSTATE2_IDX ON UM_USERS
(OBJECTSTATEINTERNAL_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          128K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- KEY_USERS  (Index) 
--
CREATE UNIQUE INDEX KEY_USERS ON UM_USERS
(CWID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- PK_UMUSERS  (Index) 
--
CREATE UNIQUE INDEX PK_UMUSERS ON UM_USERS
(OBJECT_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UMUSERS_EXTID_IDX_U  (Index) 
--
CREATE UNIQUE INDEX UMUSERS_EXTID_IDX_U ON UM_USERS
(EXT_ID)
TABLESPACE DOC41WEB_DAT
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          2M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UMUSERS_PK_NAM_OI_IDX  (Index) 
--
CREATE UNIQUE INDEX UMUSERS_PK_NAM_OI_IDX ON UM_USERS
(OBJECT_ID, OBJECTSTATE_ID, OBJECTSTATEINTERNAL_ID, CWID, LOGIN_ID, 
EXT_ID, FIRSTNAME, LASTNAME, ISEXTERNAL, EMAIL)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UMUSERS_SK_NAM_OI_IDX  (Index) 
--
CREATE UNIQUE INDEX UMUSERS_SK_NAM_OI_IDX ON UM_USERS
(CWID, OBJECTSTATE_ID, OBJECTSTATEINTERNAL_ID, OBJECT_ID, LOGIN_ID, 
EXT_ID, FIRSTNAME, LASTNAME, ISEXTERNAL, EMAIL)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_GROUP_SUBTYPE_PK  (Index) 
--
CREATE UNIQUE INDEX UM_GROUP_SUBTYPE_PK ON UM_GROUP_SUBTYPE
(SUBTYPE)
TABLESPACE DOC41WEB_DAT
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          64K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_USERS_EXPIRY_IDX  (Index) 
--
CREATE INDEX UM_USERS_EXPIRY_IDX ON UM_USERS
(EXPIRY_DATE)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


----
---- UM_USERS_QCK  (Trigger) 
----
--CREATE OR REPLACE TRIGGER UM_USERS_QCK
--BEFORE INSERT OR UPDATE ON
--  UM_USERS
--FOR EACH ROW
--DECLARE
--  val VARCHAR2(1000 CHAR);
--BEGIN
--  val := FDT_Norm_Quick(
--    NVL( :NEW.cwid, '')             || ' ' ||
--    NVL( :NEW.login_Id, '' )        || ' ' ||
--    NVL( :NEW.title, '' )           || ' ' ||
--    NVL( :NEW.lastName, '' )        || ' ' ||
--    NVL( :NEW.middleInitial, '' )   || ' ' ||
--    NVL( :NEW.firstName, '' )       || ' ' ||
--    NVL( :NEW.email, '' )           || ' ' ||
--    NVL( :NEW.phone1, '' )          || ' ' ||
--    NVL( :NEW.phone2, '' )          || ' ' ||
--    NVL( :NEW.fax, '' )             || ' ' ||
--    NVL( :NEW.address, '' )         || ' ' ||
--    NVL( :NEW.state_Province, '' )
--  );
--  IF ( val <> NVL( :NEW.quick, ' ' ) ) THEN
--    :NEW.quick := val;
--  END IF;
--END UM_USERS_QCK;
--/
--

--
-- FK_UMUSERS_DISPLAYTEXT  (Trigger) 
--
CREATE OR REPLACE TRIGGER FK_UMUSERS_DISPLAYTEXT
BEFORE INSERT OR UPDATE ON
  UM_USERS
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
  IF ( ( :old.salutation_Id <> :new.salutation_Id ) OR
       ( (:old.salutation_Id IS     NULL) AND (:new.salutation_Id IS NOT NULL) ) OR
       ( (:old.salutation_Id IS NOT NULL) AND (:new.salutation_Id IS     NULL) )    ) THEN
    DOC41WEB_FDT.DT_Check_FK( :new.salutation_Id, 1, 'FK_USERS_DISPLAYTEXT', 'Users.salutation_Id' );
  END IF;
  IF ( ( :old.timezone_Id   <> :new.timezone_Id   ) OR
       ( (:old.timezone_Id   IS     NULL) AND (:new.timezone_Id   IS NOT NULL) ) OR
       ( (:old.timezone_Id   IS NOT NULL) AND (:new.timezone_Id   IS     NULL) )    ) THEN
    DOC41WEB_FDT.DT_Check_FK( :new.timezone_Id  , 7, 'FK_USERS_DISPLAYTEXT', 'Users.timezone_Id  ' );
  END IF;
  IF ( ( :old.language_Code <> :new.language_Code ) OR
       ( (:old.language_Code IS     NULL) AND (:new.language_Code IS NOT NULL) ) OR
       ( (:old.language_Code IS NOT NULL) AND (:new.language_Code IS     NULL) )    ) THEN
    DOC41WEB_FDT.DT_Check_Code_FK( :new.language_Code, 5, 'FK_USERS_DISPLAYTEXT', 'Users.language_Code' );
  END IF;
  IF ( ( :old.country_Code <> :new.country_Code ) OR
       ( (:old.country_Code IS     NULL) AND (:new.country_Code IS NOT NULL) ) OR
       ( (:old.country_Code IS NOT NULL) AND (:new.country_Code IS     NULL) )    ) THEN
    DOC41WEB_FDT.DT_Check_Code_FK( :new.country_Code, 4, 'FK_USERS_DISPLAYTEXT', 'Users.country_Code' );
  END IF;
  IF ( ( :old.display_Language_Code <> :new.display_Language_Code ) OR
       ( (:old.display_Language_Code IS     NULL) AND (:new.display_Language_Code IS NOT NULL) ) OR
       ( (:old.display_Language_Code IS NOT NULL) AND (:new.display_Language_Code IS     NULL) )    ) THEN
    DOC41WEB_FDT.DT_Check_Code_FK( :new.display_Language_Code, 5, 'FK_USERS_DISPLAYTEXT', 'Users.display_Language_Code' );
  END IF;
  IF ( ( :old.display_Country_Code <> :new.display_Country_Code ) OR
       ( (:old.display_Country_Code IS     NULL) AND (:new.display_Country_Code IS NOT NULL) ) OR
       ( (:old.display_Country_Code IS NOT NULL) AND (:new.display_Country_Code IS     NULL) )    ) THEN
    DOC41WEB_FDT.DT_Check_Code_FK( :new.display_Country_Code, 4, 'FK_USERS_DISPLAYTEXT', 'Users.display_Country_Code' );
  END IF;
END;
/


--
-- UM_USERS_CDT  (Trigger) 
--
CREATE OR REPLACE TRIGGER UM_USERS_CDT
BEFORE INSERT OR UPDATE ON
  UM_USERS
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :NEW.created := DOC41WEB_FDT.get_Normalized_Time();
  END IF;
  IF (UPDATING) THEN
    :NEW.created   := :OLD.created;
    :NEW.createdBy := :OLD.createdBy;
    :NEW.createdOn := :OLD.createdOn;
  END IF;
  :NEW.changed := DOC41WEB_FDT.get_Normalized_Time();
  IF (:NEW.userChanged > (DOC41WEB_FDT.get_Normalized_Time() + 365750) OR INSERTING) THEN
    :NEW.userChanged := :NEW.changed;
  ELSE
    :NEW.userChanged := :OLD.userChanged;
  END IF;
  IF (INSERTING AND :NEW.object_Id IS NULL) THEN
    :NEW.object_Id := DOC41WEB_FDT.Get_Next_Oid();
  ELSIF (UPDATING) THEN
    :NEW.object_Id := :OLD.object_Id;
  END IF;
END UM_USERS_CDT;
/


----
---- UM_USERS_SK_BUDR  (Trigger) 
----
--CREATE OR REPLACE TRIGGER UM_USERS_SK_BUDR
--BEFORE UPDATE OR DELETE ON
--  UM_USERS
--FOR EACH ROW
-----------------------------------------------------------------------------------------------------
---- "WHEN" DISABBLED FOR FDT-Triggers for high extensibility... ENABLE for your OWN appl. tables!!!
-----------------------------------------------------------------------------------------------------
----WHEN (
----  ( NVL( OLD.CWID,		' ' ) <> NVL( NEW.CWID,			' ' ) ) OR
----  ( NVL( OLD.login_Id,	' ' ) <> NVL( NEW.login_Id,		' ' ) ) OR
----  ( NVL( OLD.ext_Id,		' ' ) <> NVL( NEW.ext_Id,		' ' ) ) OR
----  ( NVL( OLD.title,		' ' ) <> NVL( NEW.title,		' ' ) ) OR
----  ( NVL( OLD.lastName,	' ' ) <> NVL( NEW.lastName,		' ' ) ) OR
----  ( NVL( OLD.firstName,	' ' ) <> NVL( NEW.firstName,		' ' ) ) OR
----  ( NVL( OLD.email,		' ' ) <> NVL( NEW.email,		' ' ) ) OR
----  ( NVL( OLD.phone1,		' ' ) <> NVL( NEW.phone1,		' ' ) ) OR
----  ( NVL( OLD.phone2,		' ' ) <> NVL( NEW.phone2,		' ' ) ) OR
----  ( NVL( OLD.fax,		' ' ) <> NVL( NEW.fax,			' ' ) ) OR
----  ( NVL( OLD.address,		' ' ) <> NVL( NEW.address,		' ' ) ) OR
----  ( NVL( OLD.state_Province,	' ' ) <> NVL( NEW.state_Province,	' ' ) ) OR
----  ( NVL( OLD.language_Code,	' ' ) <> NVL( NEW.language_Code,	' ' ) ) OR
----  ( NVL( OLD.country_Code,	' ' ) <> NVL( NEW.country_Code,		' ' ) ) OR
----  ( NEW.sk_Refresh		      >  0				      )
----      )
--DECLARE
----  declare variables...
--BEGIN
--  IF ( NOT (UPDATING AND (:OLD.SK_REFRESH IS NOT NULL) AND (:NEW.SK_REFRESH IS NULL) ) ) THEN
--    DOC41WEB_FDT.sk_Reset_TableRow( 'USER', :OLD.object_Id );
--  END IF;
--END UM_USERS_SK_BUDR;
--/
--
--
----
---- UM_USERS_SK_AIUR  (Trigger) 
----
--CREATE OR REPLACE TRIGGER UM_USERS_SK_AIUR
--AFTER INSERT OR UPDATE ON
--  UM_USERS
--FOR EACH ROW
-----------------------------------------------------------------------------------------------------
---- "WHEN" DISABBLED FOR FDT-Triggers for high extensibility... ENABLE for your OWN appl. tables!!!
-----------------------------------------------------------------------------------------------------
----WHEN (
----  ( NVL( OLD.CWID,		' ' ) <> NVL( NEW.CWID,			' ' ) ) OR
----  ( NVL( OLD.login_Id,	' ' ) <> NVL( NEW.login_Id,		' ' ) ) OR
----  ( NVL( OLD.ext_Id,		' ' ) <> NVL( NEW.ext_Id,		' ' ) ) OR
----  ( NVL( OLD.title,		' ' ) <> NVL( NEW.title,		' ' ) ) OR
----  ( NVL( OLD.lastName,	' ' ) <> NVL( NEW.lastName,		' ' ) ) OR
----  ( NVL( OLD.firstName,	' ' ) <> NVL( NEW.firstName,		' ' ) ) OR
----  ( NVL( OLD.email,		' ' ) <> NVL( NEW.email,		' ' ) ) OR
----  ( NVL( OLD.phone1,		' ' ) <> NVL( NEW.phone1,		' ' ) ) OR
----  ( NVL( OLD.phone2,		' ' ) <> NVL( NEW.phone2,		' ' ) ) OR
----  ( NVL( OLD.fax,		' ' ) <> NVL( NEW.fax,			' ' ) ) OR
----  ( NVL( OLD.address,		' ' ) <> NVL( NEW.address,		' ' ) ) OR
----  ( NVL( OLD.state_Province,	' ' ) <> NVL( NEW.state_Province,	' ' ) ) OR
----  ( NVL( OLD.language_Code,	' ' ) <> NVL( NEW.language_Code,	' ' ) ) OR
----  ( NVL( OLD.country_Code,	' ' ) <> NVL( NEW.country_Code,		' ' ) ) OR
----  ( NEW.sk_Refresh		      >  0				      )
----      )
--DECLARE
----  declare variables...
--BEGIN
--  IF ( NOT (UPDATING AND (:OLD.SK_REFRESH IS NOT NULL) AND (:NEW.SK_REFRESH IS NULL) ) ) THEN
--    DOC41WEB_FDT.sk_Add_Filtered_Value(	'USER' , :NEW.object_Id, 'CWID'			, :NEW.cwid,			:NEW.object_Id, 'u' );
--    DOC41WEB_FDT.sk_Add_Filtered_Value(	'USER' , :NEW.object_Id, 'LOGIN_ID'		, :NEW.login_Id,		:NEW.object_Id, 'u' );
--    DOC41WEB_FDT.sk_Add_Filtered_Value(	'USER' , :NEW.object_Id, 'EXT_ID'		, :NEW.ext_Id,			:NEW.object_Id, 'u' );
--    DOC41WEB_FDT.sk_Add_Column(		'USER' , :NEW.object_Id, 'TITLE'		, :NEW.title,			:NEW.object_Id, 'U' );
--    DOC41WEB_FDT.sk_Add_Column(		'USER' , :NEW.object_Id, 'LASTNAME'		, :NEW.lastName,		:NEW.object_Id, 'U' );
--    DOC41WEB_FDT.sk_Add_Column(		'USER' , :NEW.object_Id, 'FIRSTNAME'		, :NEW.firstName,		:NEW.object_Id, 'U' );
--    DOC41WEB_FDT.sk_Add_Column(		'USER' , :NEW.object_Id, 'EMAIL'		, :NEW.email,			:NEW.object_Id, 'U' );
--    DOC41WEB_FDT.sk_Add_Column(		'USER' , :NEW.object_Id, 'PHONE1'		, :NEW.phone1,			:NEW.object_Id, 'U' );
--    DOC41WEB_FDT.sk_Add_Column(		'USER' , :NEW.object_Id, 'PHONE2'		, :NEW.phone2,			:NEW.object_Id, 'U' );
--    DOC41WEB_FDT.sk_Add_Column(		'USER' , :NEW.object_Id, 'FAX'			, :NEW.fax,			:NEW.object_Id, 'U' );
--    DOC41WEB_FDT.sk_Add_Column(		'USER' , :NEW.object_Id, 'ADDRESS'		, :NEW.address,			:NEW.object_Id, 'U' );
--    DOC41WEB_FDT.sk_Add_Column(		'USER' , :NEW.object_Id, 'STATE_PROVINCE'	, :NEW.state_Province,		:NEW.object_Id, 'U' );
--    DOC41WEB_FDT.sk_Add_DPT(		'USER' , :NEW.object_Id, 5, NULL		, :NEW.language_Code,	'',	:NEW.object_Id, 'U' );
--    DOC41WEB_FDT.sk_Add_DPT(		'USER' , :NEW.object_Id, 4, NULL		, :NEW.country_Code,	'',	:NEW.object_Id, 'U' );
--  END IF;
--END UM_USERS_SK_AIUR;
--/
--

--
-- UM_GROUPS  (Table) 
--
CREATE TABLE UM_GROUPS
(
  OBJECT_ID                   NUMBER(27)        NOT NULL,
  CREATED                     DATE,
  CHANGED                     DATE,
  CREATEDBY                   VARCHAR2(15 CHAR),
  CHANGEDBY                   VARCHAR2(15 CHAR),
  GROUPNUMBER                 VARCHAR2(50 CHAR),
  GROUPNAME                   VARCHAR2(100 CHAR),
  GROUPDESCRIPTION            VARCHAR2(200 CHAR),
  OBJECTSTATE_ID              NUMBER(2)         DEFAULT 1                     NOT NULL,
  SUBTYPE                     VARCHAR2(30 CHAR),
  TOPTYPE                     VARCHAR2(20 CHAR),
  USERCHANGED                 DATE,
  QUICK                       VARCHAR2(1000 CHAR),
  CREATEDON                   VARCHAR2(100 CHAR),
  CHANGEDON                   VARCHAR2(100 CHAR),
  MD_SYSTEM_ID_GROUP_NUMBER   VARCHAR2(20 CHAR),
  MD_IS_DELETE_MARKED         NUMBER(1)         DEFAULT 0                     NOT NULL,
  MD_VAT_REG_NUMBER           VARCHAR2(20 CHAR),
  AD_NAME1                    VARCHAR2(35 CHAR),
  AD_NAME2                    VARCHAR2(35 CHAR),
  AD_NAME3                    VARCHAR2(35 CHAR),
  AD_NAME4                    VARCHAR2(35 CHAR),
  AD_STREET                   VARCHAR2(100 CHAR),
  AD_ST_POSTAL_CODE           VARCHAR2(20 CHAR),
  AD_ST_CITY                  VARCHAR2(40 CHAR),
  AD_ST_CITY_AREA             VARCHAR2(40 CHAR),
  AD_POST_BOX                 VARCHAR2(20 CHAR),
  AD_PB_POSTAL_CODE           VARCHAR2(20 CHAR),
  AD_PB_CITY                  VARCHAR2(40 CHAR),
  AD_PB_CITY_AREA             VARCHAR2(40 CHAR),
  AD_STATE_PROVINCE           VARCHAR2(50 CHAR),
  AD_FORMATTED_ADDRESS_LINES  VARCHAR2(560 CHAR),
  AD_LANGUAGE_CODE            VARCHAR2(2 CHAR),
  AD_COUNTRY_CODE             VARCHAR2(2 CHAR),
  AD_WEBSITE                  VARCHAR2(250 CHAR),
  AD_PHONE1                   VARCHAR2(50 CHAR),
  AD_FAX                      VARCHAR2(50 CHAR),
  AD_EMAIL                    VARCHAR2(250 CHAR),
  SK_REFRESH                  NUMBER(1),
  REGIONAL_SCOPE              VARCHAR2(30 CHAR),
  USER_ID_RESPONSIBLE         NUMBER(27),
  USER_ID_RESPONSIBLE2        NUMBER(27),
  USER_ID_RESPONSIBLE3        NUMBER(27)
)
TABLESPACE DOC41WEB_DAT
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_USERS_PROFILES  (Table) 
--
CREATE TABLE UM_USERS_PROFILES
(
  OBJECT_ID    NUMBER(27)                       NOT NULL,
  USER_ID      NUMBER(27),
  PROFILE_ID   NUMBER(27),
  CREATED      DATE,
  CHANGED      DATE,
  CREATEDBY    VARCHAR2(15 CHAR),
  CHANGEDBY    VARCHAR2(15 CHAR),
  USERCHANGED  DATE,
  CREATEDON    VARCHAR2(100 CHAR),
  CHANGEDON    VARCHAR2(100 CHAR)
)
TABLESPACE DOC41WEB_DAT
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_DPT_PERMISSION  (Table) 
--
CREATE TABLE UM_DPT_PERMISSION
(
  OBJECT_ID    NUMBER(27)                       NOT NULL,
  TEXT_ID      NUMBER(4),
  CODE         VARCHAR2(30 CHAR),
  TEXTTYPE_ID  NUMBER(2)                        NOT NULL,
  PERMISSION   VARCHAR2(30 CHAR)                NOT NULL,
  PROFILE_ID   NUMBER(27),
  USER_ID      NUMBER(27),
  GROUP_ID     NUMBER(27),
  CREATED      DATE,
  CHANGED      DATE,
  CREATEDBY    VARCHAR2(15 CHAR),
  CHANGEDBY    VARCHAR2(15 CHAR),
  USERCHANGED  DATE,
  CREATEDON    VARCHAR2(100 CHAR),
  CHANGEDON    VARCHAR2(100 CHAR)
)
TABLESPACE DOC41WEB_DAT
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_OBJ_PERMISSION  (Table) 
--
CREATE TABLE UM_OBJ_PERMISSION
(
  OBJECT_ID     NUMBER(27)                      NOT NULL,
  OBJ_TABLE_ID  NUMBER(27),
  OBJ_TABLE     VARCHAR2(50 CHAR)               NOT NULL,
  OBJ_TYPE      VARCHAR2(30 CHAR)               NOT NULL,
  PERMISSION    VARCHAR2(30 CHAR)               NOT NULL,
  PROFILE_ID    NUMBER(27),
  USER_ID       NUMBER(27),
  GROUP_ID      NUMBER(27),
  CREATED       DATE,
  CHANGED       DATE,
  CREATEDBY     VARCHAR2(15 CHAR),
  CHANGEDBY     VARCHAR2(15 CHAR),
  USERCHANGED   DATE,
  CREATEDON     VARCHAR2(100 CHAR),
  CHANGEDON     VARCHAR2(100 CHAR)
)
TABLESPACE DOC41WEB_DAT
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- PK_UMGROUPS  (Index) 
--
CREATE UNIQUE INDEX PK_UMGROUPS ON UM_GROUPS
(OBJECT_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- PK_UMUSERS_PROFILES  (Index) 
--
CREATE UNIQUE INDEX PK_UMUSERS_PROFILES ON UM_USERS_PROFILES
(OBJECT_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- PK_UM_DPT_PERMISSION  (Index) 
--
CREATE UNIQUE INDEX PK_UM_DPT_PERMISSION ON UM_DPT_PERMISSION
(OBJECT_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- PK_UM_OBJ_PERMISSION  (Index) 
--
CREATE UNIQUE INDEX PK_UM_OBJ_PERMISSION ON UM_OBJ_PERMISSION
(OBJECT_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- SK_UMGROUPS  (Index) 
--
CREATE INDEX SK_UMGROUPS ON UM_GROUPS
(GROUPNUMBER)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UMGROUPS_PK_NAM_OI_IDX  (Index) 
--
CREATE UNIQUE INDEX UMGROUPS_PK_NAM_OI_IDX ON UM_GROUPS
(OBJECT_ID, TOPTYPE, SUBTYPE, OBJECTSTATE_ID, MD_IS_DELETE_MARKED, 
GROUPNUMBER, MD_SYSTEM_ID_GROUP_NUMBER, GROUPNAME, GROUPDESCRIPTION, AD_COUNTRY_CODE)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


----
---- UMGROUPS_SK_NAM_OI_IDX  (Index) 
----
--CREATE UNIQUE INDEX UMGROUPS_SK_NAM_OI_IDX ON UM_GROUPS
--(GROUPNUMBER, TOPTYPE, SUBTYPE, OBJECTSTATE_ID, MD_IS_DELETE_MARKED, 
--OBJECT_ID, MD_SYSTEM_ID_GROUP_NUMBER, GROUPNAME, GROUPDESCRIPTION, AD_COUNTRY_CODE)
--TABLESPACE DOC41WEB_IDX
--PCTFREE    10
--INITRANS   2
--MAXTRANS   255
--STORAGE    (
--            INITIAL          1M
--            MINEXTENTS       1
--            MAXEXTENTS       UNLIMITED
--            PCTINCREASE      0
--            BUFFER_POOL      DEFAULT
--           );
--
--
--
-- UMGROUPS_UMUSERS_RESP2_IDX  (Index) 
--
CREATE INDEX UMGROUPS_UMUSERS_RESP2_IDX ON UM_GROUPS
(USER_ID_RESPONSIBLE2)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UMGROUPS_UMUSERS_RESP3_IDX  (Index) 
--
CREATE INDEX UMGROUPS_UMUSERS_RESP3_IDX ON UM_GROUPS
(USER_ID_RESPONSIBLE3)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UMGROUPS_UMUSERS_RESP_IDX  (Index) 
--
CREATE INDEX UMGROUPS_UMUSERS_RESP_IDX ON UM_GROUPS
(USER_ID_RESPONSIBLE)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_DPT_PERMISSION_CODE_IDX  (Index) 
--
CREATE INDEX UM_DPT_PERMISSION_CODE_IDX ON UM_DPT_PERMISSION
(CODE)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_DPT_PERMISSION_GROUP_FK  (Index) 
--
CREATE INDEX UM_DPT_PERMISSION_GROUP_FK ON UM_DPT_PERMISSION
(GROUP_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_DPT_PERMISSION_PROF_FK  (Index) 
--
CREATE INDEX UM_DPT_PERMISSION_PROF_FK ON UM_DPT_PERMISSION
(PROFILE_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_DPT_PERMISSION_TEXTID_IDX  (Index) 
--
CREATE INDEX UM_DPT_PERMISSION_TEXTID_IDX ON UM_DPT_PERMISSION
(TEXT_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_DPT_PERMISSION_TTYPE_FK  (Index) 
--
CREATE INDEX UM_DPT_PERMISSION_TTYPE_FK ON UM_DPT_PERMISSION
(TEXTTYPE_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_DPT_PERMISSION_USER_FK  (Index) 
--
CREATE INDEX UM_DPT_PERMISSION_USER_FK ON UM_DPT_PERMISSION
(USER_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_OBJ_PERMISSION_GROUP_FK  (Index) 
--
CREATE INDEX UM_OBJ_PERMISSION_GROUP_FK ON UM_OBJ_PERMISSION
(GROUP_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_OBJ_PERMISSION_OBJ_TABLE_FK  (Index) 
--
CREATE INDEX UM_OBJ_PERMISSION_OBJ_TABLE_FK ON UM_OBJ_PERMISSION
(OBJ_TABLE_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_OBJ_PERMISSION_PROF_FK  (Index) 
--
CREATE INDEX UM_OBJ_PERMISSION_PROF_FK ON UM_OBJ_PERMISSION
(PROFILE_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_OBJ_PERMISSION_USER_FK  (Index) 
--
CREATE INDEX UM_OBJ_PERMISSION_USER_FK ON UM_OBJ_PERMISSION
(USER_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_USERSPROF_PROF_FK  (Index) 
--
CREATE INDEX UM_USERSPROF_PROF_FK ON UM_USERS_PROFILES
(PROFILE_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_USERSPROF_USER_FK  (Index) 
--
CREATE INDEX UM_USERSPROF_USER_FK ON UM_USERS_PROFILES
(USER_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_USERS_PROFILES_CDT  (Trigger) 
--
CREATE OR REPLACE TRIGGER UM_USERS_PROFILES_CDT
BEFORE INSERT OR UPDATE ON
  UM_USERS_PROFILES
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :NEW.created := DOC41WEB_FDT.get_Normalized_Time();
  END IF;
  IF (UPDATING) THEN
    :NEW.created   := :OLD.created;
    :NEW.createdBy := :OLD.createdBy;
    :NEW.createdOn := :OLD.createdOn;
  END IF;
  :NEW.changed := DOC41WEB_FDT.get_Normalized_Time();
  IF (:NEW.userChanged > (DOC41WEB_FDT.get_Normalized_Time() + 365750) OR INSERTING) THEN
    :NEW.userChanged := :NEW.changed;
  ELSE
    :NEW.userChanged := :OLD.userChanged;
  END IF;
  IF (INSERTING AND :NEW.object_Id IS NULL) THEN
    :NEW.object_Id := DOC41WEB_FDT.Get_Next_Oid();
  ELSIF (UPDATING) THEN
    :NEW.object_Id := :OLD.object_Id;
  END IF;
END UM_USERS_PROFILES_CDT;
/


--
-- UM_GROUPS_CDT  (Trigger) 
--
CREATE OR REPLACE TRIGGER UM_GROUPS_CDT
BEFORE INSERT OR UPDATE ON
  UM_GROUPS
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :NEW.created := DOC41WEB_FDT.get_Normalized_Time();
  END IF;
  IF (UPDATING) THEN
    :NEW.created   := :OLD.created;
    :NEW.createdBy := :OLD.createdBy;
    :NEW.createdOn := :OLD.createdOn;
  END IF;
  :NEW.changed := DOC41WEB_FDT.get_Normalized_Time();
  IF (:NEW.userChanged > (DOC41WEB_FDT.get_Normalized_Time() + 365750) OR INSERTING) THEN
    :NEW.userChanged := :NEW.changed;
  ELSE
    :NEW.userChanged := :OLD.userChanged;
  END IF;
  IF (INSERTING AND :NEW.object_Id IS NULL) THEN
    :NEW.object_Id := DOC41WEB_FDT.Get_Next_Oid();
  ELSIF (UPDATING) THEN
    :NEW.object_Id := :OLD.object_Id;
  END IF;
END UM_GROUPS_CDT;
/


--
-- UM_OBJ_PERMISSION_CDT  (Trigger) 
--
CREATE OR REPLACE TRIGGER UM_OBJ_PERMISSION_CDT
BEFORE INSERT OR UPDATE ON
  UM_OBJ_PERMISSION
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :NEW.created := DOC41WEB_FDT.get_Normalized_Time();
  END IF;
  IF (UPDATING) THEN
    :NEW.created   := :OLD.created;
    :NEW.createdBy := :OLD.createdBy;
    :NEW.createdOn := :OLD.createdOn;
  END IF;
  :NEW.changed := DOC41WEB_FDT.get_Normalized_Time();
  IF (:NEW.userChanged > (DOC41WEB_FDT.get_Normalized_Time() + 365750) OR INSERTING) THEN
    :NEW.userChanged := :NEW.changed;
  ELSE
    :NEW.userChanged := :OLD.userChanged;
  END IF;
  IF (INSERTING AND :NEW.object_Id IS NULL) THEN
    :NEW.object_Id := DOC41WEB_FDT.Get_Next_Oid();
  ELSIF (UPDATING) THEN
    :NEW.object_Id := :OLD.object_Id;
  END IF;
END UM_OBJ_PERMISSION_CDT;
/


--
-- UM_DPT_PERMISSION_CDT  (Trigger) 
--
CREATE OR REPLACE TRIGGER UM_DPT_PERMISSION_CDT
BEFORE INSERT OR UPDATE ON
  UM_DPT_PERMISSION
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :NEW.created := DOC41WEB_FDT.get_Normalized_Time();
  END IF;
  IF (UPDATING) THEN
    :NEW.created   := :OLD.created;
    :NEW.createdBy := :OLD.createdBy;
    :NEW.createdOn := :OLD.createdOn;
  END IF;
  :NEW.changed := DOC41WEB_FDT.get_Normalized_Time();
  IF (:NEW.userChanged > (DOC41WEB_FDT.get_Normalized_Time() + 365750) OR INSERTING) THEN
    :NEW.userChanged := :NEW.changed;
  ELSE
    :NEW.userChanged := :OLD.userChanged;
  END IF;
  IF (INSERTING AND :NEW.object_Id IS NULL) THEN
    :NEW.object_Id := DOC41WEB_FDT.Get_Next_Oid();
  ELSIF (UPDATING) THEN
    :NEW.object_Id := :OLD.object_Id;
  END IF;
END UM_DPT_PERMISSION_CDT;
/


----
---- UM_GROUPS_SK_BUDR  (Trigger) 
----
--CREATE OR REPLACE TRIGGER UM_GROUPS_SK_BUDR
--BEFORE UPDATE OR DELETE ON
--  UM_GROUPS
--FOR EACH ROW
-----------------------------------------------------------------------------------------------------
---- "WHEN" DISABBLED FOR FDT-Triggers for high extensibility... ENABLE for your OWN appl. tables!!!
-----------------------------------------------------------------------------------------------------
----WHEN (
----  ( NVL( OLD.groupNumber,			' ' ) <> NVL( NEW.groupNumber,			' ' ) ) OR
----  ( NVL( OLD.groupName,			' ' ) <> NVL( NEW.groupName,			' ' ) ) OR
----  ( NVL( OLD.groupDescription,		' ' ) <> NVL( NEW.groupDescription,		' ' ) ) OR
----  ( NVL( OLD.md_Vat_Reg_Number,		' ' ) <> NVL( NEW.md_Vat_Reg_Number,		' ' ) ) OR
----  ( NVL( OLD.ad_Name1,			' ' ) <> NVL( NEW.ad_Name1,			' ' ) ) OR
----  ( NVL( OLD.ad_Name2,			' ' ) <> NVL( NEW.ad_Name2,			' ' ) ) OR
----  ( NVL( OLD.ad_Name3,			' ' ) <> NVL( NEW.ad_Name3,			' ' ) ) OR
----  ( NVL( OLD.ad_Name4,			' ' ) <> NVL( NEW.ad_Name4,			' ' ) ) OR
----  ( NVL( OLD.ad_Street,			' ' ) <> NVL( NEW.ad_Street,			' ' ) ) OR
----  ( NVL( OLD.ad_St_Postal_Code,		' ' ) <> NVL( NEW.ad_St_Postal_Code,		' ' ) ) OR
----  ( NVL( OLD.ad_St_City,			' ' ) <> NVL( NEW.ad_St_City,			' ' ) ) OR
----  ( NVL( OLD.ad_St_City_Area,			' ' ) <> NVL( NEW.ad_St_City_Area,		' ' ) ) OR
----  ( NVL( OLD.ad_Post_Box,			' ' ) <> NVL( NEW.ad_Post_Box,			' ' ) ) OR
----  ( NVL( OLD.ad_Pb_Postal_Code,		' ' ) <> NVL( NEW.ad_Pb_Postal_Code,		' ' ) ) OR
----  ( NVL( OLD.ad_Pb_City,			' ' ) <> NVL( NEW.ad_Pb_City,			' ' ) ) OR
----  ( NVL( OLD.ad_Pb_City_Area,			' ' ) <> NVL( NEW.ad_Pb_City_Area,		' ' ) ) OR
----  ( NVL( OLD.ad_State_Province,		' ' ) <> NVL( NEW.ad_State_Province,		' ' ) ) OR
----  ( NVL( OLD.ad_Formatted_Address_Lines,	' ' ) <> NVL( NEW.ad_Formatted_Address_Lines,	' ' ) ) OR
----  ( NVL( OLD.ad_Phone1,			' ' ) <> NVL( NEW.ad_Phone1,			' ' ) ) OR
----  ( NVL( OLD.ad_Fax,				' ' ) <> NVL( NEW.ad_Fax,			' ' ) ) OR
----  ( NVL( OLD.ad_Email,			' ' ) <> NVL( NEW.ad_Email,			' ' ) ) OR
----  ( NVL( OLD.ad_Language_Code,		' ' ) <> NVL( NEW.ad_Language_Code,		' ' ) ) OR
----  ( NVL( OLD.ad_Country_Code,			' ' ) <> NVL( NEW.ad_Country_Code,		' ' ) ) OR
----  ( NVL( OLD.subType,				' ' ) <> NVL( NEW.subType,			' ' ) ) OR
----  ( NEW.sk_Refresh				      >  0					      )
----      )
--DECLARE
----  declare variables...
--BEGIN
--  IF ( NOT (UPDATING AND (:OLD.SK_REFRESH IS NOT NULL) AND (:NEW.SK_REFRESH IS NULL) ) ) THEN
--    DOC41WEB_FDT.sk_Reset_TableRow( 'GROUP', :OLD.object_Id );
--  END IF;
--END UM_GROUPS_SK_BUDR;
--/
--
--
----
---- UM_GROUPS_QCK  (Trigger) 
----
--CREATE OR REPLACE TRIGGER UM_GROUPS_QCK
--BEFORE INSERT OR UPDATE ON
--  UM_GROUPS
--FOR EACH ROW
--DECLARE
--  val VARCHAR2(1000 CHAR);
--BEGIN
--  val := SUBSTR( FDT_Norm_Quick(
--    NVL( :NEW.groupNumber, '')      || ' ' ||
--    NVL( :NEW.groupName, '' )       || ' ' ||
--    NVL( :NEW.groupDescription, '' )|| ' ' ||
--    NVL( :NEW.topType, '' )         || ' ' ||
--    NVL( :NEW.subType, '' )         || ' ' ||
--    NVL( :NEW.MD_VAT_REG_NUMBER, '' )|| ' ' ||
--    NVL( :NEW.AD_NAME1, '' )         || ' ' ||
--    NVL( :NEW.AD_NAME2, '' )         || ' ' ||
--    NVL( :NEW.AD_NAME3, '' )         || ' ' ||
--    NVL( :NEW.AD_NAME4, '' )         || ' ' ||
--    NVL( :NEW.AD_STREET, '' )        || ' ' ||
--    NVL( :NEW.AD_ST_POSTAL_CODE, '' )|| ' ' ||
--    NVL( :NEW.AD_ST_CITY, '' )       || ' ' ||
--    NVL( :NEW.AD_ST_CITY_AREA, '' )  || ' ' ||
--    NVL( :NEW.AD_POST_BOX, '' )      || ' ' ||
--    NVL( :NEW.AD_PB_POSTAL_CODE, '' )|| ' ' ||
--    NVL( :NEW.AD_PB_CITY, '' )       || ' ' ||
--    NVL( :NEW.AD_PB_CITY_AREA, '' )  || ' ' ||
--    NVL( :NEW.AD_STATE_PROVINCE, '' )|| ' ' ||
--    NVL( :NEW.AD_FORMATTED_ADDRESS_LINES, '' )
--  ), 1, 1000 );
--  IF ( val <> NVL( :NEW.quick, ' ' ) ) THEN
--    :NEW.quick := val;
--  END IF;
--END UM_GROUPS_QCK;
--/
--
--
----
---- UM_GROUPS_SK_AIUR  (Trigger) 
----
--CREATE OR REPLACE TRIGGER UM_GROUPS_SK_AIUR
--AFTER INSERT OR UPDATE ON
--  UM_GROUPS
--FOR EACH ROW
-----------------------------------------------------------------------------------------------------
---- "WHEN" DISABBLED FOR FDT-Triggers for high extensibility... ENABLE for your OWN appl. tables!!!
-----------------------------------------------------------------------------------------------------
----WHEN (
----  ( NVL( OLD.groupNumber,			' ' ) <> NVL( NEW.groupNumber,			' ' ) ) OR
----  ( NVL( OLD.groupName,			' ' ) <> NVL( NEW.groupName,			' ' ) ) OR
----  ( NVL( OLD.groupDescription,		' ' ) <> NVL( NEW.groupDescription,		' ' ) ) OR
----  ( NVL( OLD.md_Vat_Reg_Number,		' ' ) <> NVL( NEW.md_Vat_Reg_Number,		' ' ) ) OR
----  ( NVL( OLD.ad_Name1,			' ' ) <> NVL( NEW.ad_Name1,			' ' ) ) OR
----  ( NVL( OLD.ad_Name2,			' ' ) <> NVL( NEW.ad_Name2,			' ' ) ) OR
----  ( NVL( OLD.ad_Name3,			' ' ) <> NVL( NEW.ad_Name3,			' ' ) ) OR
----  ( NVL( OLD.ad_Name4,			' ' ) <> NVL( NEW.ad_Name4,			' ' ) ) OR
----  ( NVL( OLD.ad_Street,			' ' ) <> NVL( NEW.ad_Street,			' ' ) ) OR
----  ( NVL( OLD.ad_St_Postal_Code,		' ' ) <> NVL( NEW.ad_St_Postal_Code,		' ' ) ) OR
----  ( NVL( OLD.ad_St_City,			' ' ) <> NVL( NEW.ad_St_City,			' ' ) ) OR
----  ( NVL( OLD.ad_St_City_Area,			' ' ) <> NVL( NEW.ad_St_City_Area,		' ' ) ) OR
----  ( NVL( OLD.ad_Post_Box,			' ' ) <> NVL( NEW.ad_Post_Box,			' ' ) ) OR
----  ( NVL( OLD.ad_Pb_Postal_Code,		' ' ) <> NVL( NEW.ad_Pb_Postal_Code,		' ' ) ) OR
----  ( NVL( OLD.ad_Pb_City,			' ' ) <> NVL( NEW.ad_Pb_City,			' ' ) ) OR
----  ( NVL( OLD.ad_Pb_City_Area,			' ' ) <> NVL( NEW.ad_Pb_City_Area,		' ' ) ) OR
----  ( NVL( OLD.ad_State_Province,		' ' ) <> NVL( NEW.ad_State_Province,		' ' ) ) OR
----  ( NVL( OLD.ad_Formatted_Address_Lines,	' ' ) <> NVL( NEW.ad_Formatted_Address_Lines,	' ' ) ) OR
----  ( NVL( OLD.ad_Phone1,			' ' ) <> NVL( NEW.ad_Phone1,			' ' ) ) OR
----  ( NVL( OLD.ad_Fax,				' ' ) <> NVL( NEW.ad_Fax,			' ' ) ) OR
----  ( NVL( OLD.ad_Email,			' ' ) <> NVL( NEW.ad_Email,			' ' ) ) OR
----  ( NVL( OLD.ad_Language_Code,		' ' ) <> NVL( NEW.ad_Language_Code,		' ' ) ) OR
----  ( NVL( OLD.ad_Country_Code,			' ' ) <> NVL( NEW.ad_Country_Code,		' ' ) ) OR
----  ( NVL( OLD.subType,				' ' ) <> NVL( NEW.subType,			' ' ) ) OR
----  ( NEW.sk_Refresh				      >  0					      )
----      )
--DECLARE
----  declare variables...
--BEGIN
--  IF ( NOT (UPDATING AND (:OLD.SK_REFRESH IS NOT NULL) AND (:NEW.SK_REFRESH IS NULL) ) ) THEN
--    DOC41WEB_FDT.sk_Add_Filtered_Value(	'GROUP' , :NEW.object_Id, 'GROUPNUMBER'		, :NEW.groupNumber,			:NEW.object_Id, 'g' );
--    DOC41WEB_FDT.sk_Add_Column(		'GROUP' , :NEW.object_Id, 'GROUPNAME'		, :NEW.groupName,			:NEW.object_Id, 'G' );
--    DOC41WEB_FDT.sk_Add_Column(		'GROUP' , :NEW.object_Id, 'GROUPDESCRIPTION'	, :NEW.groupDescription,		:NEW.object_Id, 'G' );
--    DOC41WEB_FDT.sk_Add_Column(		'GROUP' , :NEW.object_Id, 'MD_VAT_REG_NUMBER'	, :NEW.md_Vat_Reg_Number,		:NEW.object_Id, 'G' );
--    DOC41WEB_FDT.sk_Add_Column(		'GROUP' , :NEW.object_Id, 'AD_NAME1'		, :NEW.ad_Name1,			:NEW.object_Id, 'G' );
--    DOC41WEB_FDT.sk_Add_Column(		'GROUP' , :NEW.object_Id, 'AD_NAME2'		, :NEW.ad_Name2,			:NEW.object_Id, 'G' );
--    DOC41WEB_FDT.sk_Add_Column(		'GROUP' , :NEW.object_Id, 'AD_NAME3'		, :NEW.ad_Name3,			:NEW.object_Id, 'G' );
--    DOC41WEB_FDT.sk_Add_Column(		'GROUP' , :NEW.object_Id, 'AD_NAME4'		, :NEW.ad_Name4,			:NEW.object_Id, 'G' );
--    DOC41WEB_FDT.sk_Add_Column(		'GROUP' , :NEW.object_Id, 'AD_STREET'		, :NEW.ad_Street,			:NEW.object_Id, 'G' );
--    DOC41WEB_FDT.sk_Add_Column(		'GROUP' , :NEW.object_Id, 'AD_ST_POSTAL_CODE'	, :NEW.ad_St_Postal_Code,		:NEW.object_Id, 'G' );
--    DOC41WEB_FDT.sk_Add_Column(		'GROUP' , :NEW.object_Id, 'AD_ST_CITY'		, :NEW.ad_St_City,			:NEW.object_Id, 'G' );
--    DOC41WEB_FDT.sk_Add_Column(		'GROUP' , :NEW.object_Id, 'AD_ST_CITY_AREA'	, :NEW.ad_St_City_Area,			:NEW.object_Id, 'G' );
--    DOC41WEB_FDT.sk_Add_Column(		'GROUP' , :NEW.object_Id, 'AD_POST_BOX'		, :NEW.ad_Post_Box,			:NEW.object_Id, 'G' );
--    DOC41WEB_FDT.sk_Add_Column(		'GROUP' , :NEW.object_Id, 'AD_PB_POSTAL_CODE'	, :NEW.ad_Pb_Postal_Code,		:NEW.object_Id, 'G' );
--    DOC41WEB_FDT.sk_Add_Column(		'GROUP' , :NEW.object_Id, 'AD_PB_CITY'		, :NEW.ad_Pb_City,			:NEW.object_Id, 'G' );
--    DOC41WEB_FDT.sk_Add_Column(		'GROUP' , :NEW.object_Id, 'AD_PB_CITY_AREA'	, :NEW.ad_Pb_City_Area,			:NEW.object_Id, 'G' );
--    DOC41WEB_FDT.sk_Add_Column(		'GROUP' , :NEW.object_Id, 'AD_STATE_PROVINCE'	, :NEW.ad_State_Province,		:NEW.object_Id, 'G' );
--    DOC41WEB_FDT.sk_Add_Column(		'GROUP' , :NEW.object_Id, 'AD_FORMATTED_ADDRES'	, :NEW.ad_Formatted_Address_Lines,	:NEW.object_Id, 'G' );
--    DOC41WEB_FDT.sk_Add_Column(		'GROUP' , :NEW.object_Id, 'AD_PHONE1'		, :NEW.ad_Phone1,			:NEW.object_Id, 'G' );
--    DOC41WEB_FDT.sk_Add_Column(		'GROUP' , :NEW.object_Id, 'AD_FAX'		, :NEW.ad_Fax,				:NEW.object_Id, 'G' );
--    DOC41WEB_FDT.sk_Add_Column(		'GROUP' , :NEW.object_Id, 'AD_EMAIL'		, :NEW.ad_Email,			:NEW.object_Id, 'G' );
--    DOC41WEB_FDT.sk_Add_DPT(		'GROUP' , :NEW.object_Id, 5,  NULL		, :NEW.ad_Language_Code	, '',		:NEW.object_Id, 'G' );
--    DOC41WEB_FDT.sk_Add_DPT(		'GROUP' , :NEW.object_Id, 4,  NULL		, :NEW.ad_Country_Code	, '',		:NEW.object_Id, 'G' );
--    DOC41WEB_FDT.sk_Add_DPT(		'GROUP' , :NEW.object_Id, 54, NULL		, :NEW.subType		, '',		:NEW.object_Id, 'G' );
--  END IF;
--END UM_GROUPS_SK_AIUR;
--/
--
--
--
-- UM_USERS_GROUPS  (Table) 
--
CREATE TABLE UM_USERS_GROUPS
(
  OBJECT_ID    NUMBER(27)                       NOT NULL,
  USER_ID      NUMBER(27),
  GROUP_ID     NUMBER(27),
  CREATED      DATE,
  CHANGED      DATE,
  CREATEDBY    VARCHAR2(15 CHAR),
  CHANGEDBY    VARCHAR2(15 CHAR),
  USERCHANGED  DATE,
  CREATEDON    VARCHAR2(100 CHAR),
  CHANGEDON    VARCHAR2(100 CHAR)
)
TABLESPACE DOC41WEB_DAT
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          1M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_PGU_PERMISSIONS  (Table) 
--
CREATE TABLE UM_PGU_PERMISSIONS
(
  OBJECT_ID      NUMBER(27)                     NOT NULL,
  PERMISSION_ID  NUMBER(27),
  PROFILE_ID     NUMBER(27),
  USER_ID        NUMBER(27),
  GROUP_ID       NUMBER(27),
  CREATED        DATE,
  CHANGED        DATE,
  CREATEDBY      VARCHAR2(15 CHAR),
  CHANGEDBY      VARCHAR2(15 CHAR),
  USERCHANGED    DATE,
  CREATEDON      VARCHAR2(100 CHAR),
  CHANGEDON      VARCHAR2(100 CHAR)
)
TABLESPACE DOC41WEB_DAT
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          2M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- PK_UMUSERS_GROUPS  (Index) 
--
CREATE UNIQUE INDEX PK_UMUSERS_GROUPS ON UM_USERS_GROUPS
(OBJECT_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- PK_UM_PGU_PERMISSIONS  (Index) 
--
CREATE UNIQUE INDEX PK_UM_PGU_PERMISSIONS ON UM_PGU_PERMISSIONS
(OBJECT_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_PGU_PERMISSIONS_GROUP_FK  (Index) 
--
CREATE INDEX UM_PGU_PERMISSIONS_GROUP_FK ON UM_PGU_PERMISSIONS
(GROUP_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_PGU_PERMISSIONS_PERM_FK  (Index) 
--
CREATE INDEX UM_PGU_PERMISSIONS_PERM_FK ON UM_PGU_PERMISSIONS
(PERMISSION_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_PGU_PERMISSIONS_PROF_FK  (Index) 
--
CREATE INDEX UM_PGU_PERMISSIONS_PROF_FK ON UM_PGU_PERMISSIONS
(PROFILE_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_PGU_PERMISSIONS_USER_FK  (Index) 
--
CREATE INDEX UM_PGU_PERMISSIONS_USER_FK ON UM_PGU_PERMISSIONS
(USER_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_USERSGROUPS_GROUP_FK  (Index) 
--
CREATE INDEX UM_USERSGROUPS_GROUP_FK ON UM_USERS_GROUPS
(GROUP_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_USERSGROUPS_USER_FK  (Index) 
--
CREATE INDEX UM_USERSGROUPS_USER_FK ON UM_USERS_GROUPS
(USER_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- UM_PGU_PERMISSIONS_CDT  (Trigger) 
--
CREATE OR REPLACE TRIGGER UM_PGU_PERMISSIONS_CDT
BEFORE INSERT OR UPDATE ON
  UM_PGU_PERMISSIONS
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :NEW.created := DOC41WEB_FDT.get_Normalized_Time();
  END IF;
  IF (UPDATING) THEN
    :NEW.created   := :OLD.created;
    :NEW.createdBy := :OLD.createdBy;
    :NEW.createdOn := :OLD.createdOn;
  END IF;
  :NEW.changed := DOC41WEB_FDT.get_Normalized_Time();
  IF (:NEW.userChanged > (DOC41WEB_FDT.get_Normalized_Time() + 365750) OR INSERTING) THEN
    :NEW.userChanged := :NEW.changed;
  ELSE
    :NEW.userChanged := :OLD.userChanged;
  END IF;
  IF (INSERTING AND :NEW.object_Id IS NULL) THEN
    :NEW.object_Id := DOC41WEB_FDT.Get_Next_Oid();
  ELSIF (UPDATING) THEN
    :NEW.object_Id := :OLD.object_Id;
  END IF;
END UM_PGU_PERMISSIONS_CDT;
/


--
-- UM_USERS_GROUPS_CDT  (Trigger) 
--
CREATE OR REPLACE TRIGGER UM_USERS_GROUPS_CDT
BEFORE INSERT OR UPDATE ON
  UM_USERS_GROUPS
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :NEW.created := DOC41WEB_FDT.get_Normalized_Time();
  END IF;
  IF (UPDATING) THEN
    :NEW.created   := :OLD.created;
    :NEW.createdBy := :OLD.createdBy;
    :NEW.createdOn := :OLD.createdOn;
  END IF;
  :NEW.changed := DOC41WEB_FDT.get_Normalized_Time();
  IF (:NEW.userChanged > (DOC41WEB_FDT.get_Normalized_Time() + 365750) OR INSERTING) THEN
    :NEW.userChanged := :NEW.changed;
  ELSE
    :NEW.userChanged := :OLD.userChanged;
  END IF;
  IF (INSERTING AND :NEW.object_Id IS NULL) THEN
    :NEW.object_Id := DOC41WEB_FDT.Get_Next_Oid();
  ELSIF (UPDATING) THEN
    :NEW.object_Id := :OLD.object_Id;
  END IF;
END UM_USERS_GROUPS_CDT;
/


-- 
-- Non Foreign Key Constraints for Table UM_PROFILES 
-- 
ALTER TABLE UM_PROFILES ADD (
  CONSTRAINT PK_UMPROFILES
  PRIMARY KEY
  (OBJECT_ID)
  USING INDEX PK_UMPROFILES);


-- 
-- Non Foreign Key Constraints for Table UM_PERMISSIONS 
-- 
ALTER TABLE UM_PERMISSIONS ADD (
  CHECK ((ASSIGN_USER = 0) OR (ASSIGN_USER = 1)));

ALTER TABLE UM_PERMISSIONS ADD (
  CHECK ((ASSIGN_GROUP = 0) OR (ASSIGN_GROUP = 1)));

ALTER TABLE UM_PERMISSIONS ADD (
  CHECK ((ASSIGN_PROFILE = 0) OR (ASSIGN_PROFILE = 1)));

ALTER TABLE UM_PERMISSIONS ADD (
  CONSTRAINT PK_UMPERMISSIONS
  PRIMARY KEY
  (OBJECT_ID)
  USING INDEX PK_UMPERMISSIONS);


-- 
-- Non Foreign Key Constraints for Table UM_OBJECTSTATE 
-- 
ALTER TABLE UM_OBJECTSTATE ADD (
  CONSTRAINT PK_UMOBJECTSTATE
  PRIMARY KEY
  (OBJECTSTATE_ID)
  USING INDEX PK_UMOBJECTSTATE);


-- 
-- Non Foreign Key Constraints for Table UM_GROUP_TYPE 
-- 
ALTER TABLE UM_GROUP_TYPE ADD (
  CONSTRAINT GROUP_TYPE_PK
  PRIMARY KEY
  (TOPTYPE)
  USING INDEX GROUP_TYPE_PK);


-- 
-- Non Foreign Key Constraints for Table UM_USERS 
-- 
ALTER TABLE UM_USERS ADD (
  CONSTRAINT UMUSERS_REPSHWLOGS
  CHECK (( reports_Show_Logs = 0 ) OR ( reports_Show_Logs = 1 )));

ALTER TABLE UM_USERS ADD (
  CONSTRAINT PK_UMUSERS
  PRIMARY KEY
  (OBJECT_ID)
  USING INDEX PK_UMUSERS);


-- 
-- Non Foreign Key Constraints for Table UM_GROUP_SUBTYPE 
-- 
ALTER TABLE UM_GROUP_SUBTYPE ADD (
  CONSTRAINT UM_GROUP_SUBTYPE_PK
  PRIMARY KEY
  (SUBTYPE)
  USING INDEX UM_GROUP_SUBTYPE_PK);


-- 
-- Non Foreign Key Constraints for Table UM_GROUPS 
-- 
ALTER TABLE UM_GROUPS ADD (
  CONSTRAINT P_CHECK_MDISDM
  CHECK ( ( md_Is_Delete_Marked = 0 ) OR ( md_Is_Delete_Marked = 1 ) ));

ALTER TABLE UM_GROUPS ADD (
  CONSTRAINT PK_UMGROUPS
  PRIMARY KEY
  (OBJECT_ID)
  USING INDEX PK_UMGROUPS);


-- 
-- Non Foreign Key Constraints for Table UM_USERS_PROFILES 
-- 
ALTER TABLE UM_USERS_PROFILES ADD (
  CONSTRAINT PK_UMUSERS_PROFILES
  PRIMARY KEY
  (OBJECT_ID)
  USING INDEX PK_UMUSERS_PROFILES);


-- 
-- Non Foreign Key Constraints for Table UM_DPT_PERMISSION 
-- 
ALTER TABLE UM_DPT_PERMISSION ADD (
  CONSTRAINT PK_UMDPT_PERMISSION
  PRIMARY KEY
  (OBJECT_ID)
  USING INDEX PK_UM_DPT_PERMISSION);


-- 
-- Non Foreign Key Constraints for Table UM_OBJ_PERMISSION 
-- 
ALTER TABLE UM_OBJ_PERMISSION ADD (
  CONSTRAINT PK_UMOBJ_PERMISSION
  PRIMARY KEY
  (OBJECT_ID)
  USING INDEX PK_UM_OBJ_PERMISSION);


-- 
-- Non Foreign Key Constraints for Table UM_USERS_GROUPS 
-- 
ALTER TABLE UM_USERS_GROUPS ADD (
  CONSTRAINT PK_UMUSERS_GROUPS
  PRIMARY KEY
  (OBJECT_ID)
  USING INDEX PK_UMUSERS_GROUPS);


-- 
-- Non Foreign Key Constraints for Table UM_PGU_PERMISSIONS 
-- 
ALTER TABLE UM_PGU_PERMISSIONS ADD (
  CONSTRAINT PK_UMPGU_PERMISSIONS
  PRIMARY KEY
  (OBJECT_ID)
  USING INDEX PK_UM_PGU_PERMISSIONS);


-- 
-- Foreign Key Constraints for Table UM_USERS 
-- 
ALTER TABLE UM_USERS ADD (
  CONSTRAINT FK_UMUSERS_UMOBJECTSTATE 
  FOREIGN KEY (OBJECTSTATE_ID) 
  REFERENCES UM_OBJECTSTATE (OBJECTSTATE_ID));

ALTER TABLE UM_USERS ADD (
  CONSTRAINT FK_UMUSERS_UMOBJECTSTATE2 
  FOREIGN KEY (OBJECTSTATEINTERNAL_ID) 
  REFERENCES UM_OBJECTSTATE (OBJECTSTATE_ID));


-- 
-- Foreign Key Constraints for Table UM_GROUP_SUBTYPE 
-- 
ALTER TABLE UM_GROUP_SUBTYPE ADD (
  CONSTRAINT GROUP_SUBTYPE_GROUP_TYPE_FK 
  FOREIGN KEY (TOPTYPE) 
  REFERENCES UM_GROUP_TYPE (TOPTYPE));


-- 
-- Foreign Key Constraints for Table UM_GROUPS 
-- 
ALTER TABLE UM_GROUPS ADD (
  CONSTRAINT FK_UMGROUPS_UMOBJECTSTATE 
  FOREIGN KEY (OBJECTSTATE_ID) 
  REFERENCES UM_OBJECTSTATE (OBJECTSTATE_ID));

ALTER TABLE UM_GROUPS ADD (
  CONSTRAINT GROUP_GROUP_SUBTYPE_FK 
  FOREIGN KEY (SUBTYPE) 
  REFERENCES UM_GROUP_SUBTYPE (SUBTYPE));

ALTER TABLE UM_GROUPS ADD (
  CONSTRAINT GROUP_GROUP_TYPE_FK 
  FOREIGN KEY (TOPTYPE) 
  REFERENCES UM_GROUP_TYPE (TOPTYPE));

ALTER TABLE UM_GROUPS ADD (
  CONSTRAINT UMGROUPS_UMUSERS_RESP_FK 
  FOREIGN KEY (USER_ID_RESPONSIBLE) 
  REFERENCES UM_USERS (OBJECT_ID));

ALTER TABLE UM_GROUPS ADD (
  CONSTRAINT UMGROUPS_UMUSERS_RESP2_FK 
  FOREIGN KEY (USER_ID_RESPONSIBLE2) 
  REFERENCES UM_USERS (OBJECT_ID));

ALTER TABLE UM_GROUPS ADD (
  CONSTRAINT UMGROUPS_UMUSERS_RESP3_FK 
  FOREIGN KEY (USER_ID_RESPONSIBLE3) 
  REFERENCES UM_USERS (OBJECT_ID));


-- 
-- Foreign Key Constraints for Table UM_USERS_PROFILES 
-- 
ALTER TABLE UM_USERS_PROFILES ADD (
  CONSTRAINT FK_UMUSERSPROFILES_UMPROFILES 
  FOREIGN KEY (PROFILE_ID) 
  REFERENCES UM_PROFILES (OBJECT_ID)
  ON DELETE CASCADE);

ALTER TABLE UM_USERS_PROFILES ADD (
  CONSTRAINT FK_UMUSERSPROFILES_UMUSERS 
  FOREIGN KEY (USER_ID) 
  REFERENCES UM_USERS (OBJECT_ID)
  ON DELETE CASCADE);


-- 
-- Foreign Key Constraints for Table UM_DPT_PERMISSION 
-- 
ALTER TABLE UM_DPT_PERMISSION ADD (
  CONSTRAINT FK_UMDPTPERMIS_UMGROUP 
  FOREIGN KEY (GROUP_ID) 
  REFERENCES UM_GROUPS (OBJECT_ID)
  ON DELETE CASCADE);

ALTER TABLE UM_DPT_PERMISSION ADD (
  CONSTRAINT FK_UMDPTPERMIS_UMPROFIL 
  FOREIGN KEY (PROFILE_ID) 
  REFERENCES UM_PROFILES (OBJECT_ID)
  ON DELETE CASCADE);

ALTER TABLE UM_DPT_PERMISSION ADD (
  CONSTRAINT FK_UMDPTPERMIS_UMUSER 
  FOREIGN KEY (USER_ID) 
  REFERENCES UM_USERS (OBJECT_ID)
  ON DELETE CASCADE);

ALTER TABLE UM_DPT_PERMISSION ADD (
  CONSTRAINT FK_UMDPTPERMIS_TTYPE 
  FOREIGN KEY (TEXTTYPE_ID) 
  REFERENCES DOC41WEB_FDT.TEXTTYPE (TEXTTYPE_ID));


-- 
-- Foreign Key Constraints for Table UM_OBJ_PERMISSION 
-- 
ALTER TABLE UM_OBJ_PERMISSION ADD (
  CONSTRAINT FK_UMOBJPERMIS_UMGROUP 
  FOREIGN KEY (GROUP_ID) 
  REFERENCES UM_GROUPS (OBJECT_ID)
  ON DELETE CASCADE);

ALTER TABLE UM_OBJ_PERMISSION ADD (
  CONSTRAINT FK_UMOBJPERMIS_UMPROFIL 
  FOREIGN KEY (PROFILE_ID) 
  REFERENCES UM_PROFILES (OBJECT_ID)
  ON DELETE CASCADE);

ALTER TABLE UM_OBJ_PERMISSION ADD (
  CONSTRAINT FK_UMOBJPERMIS_UMUSER 
  FOREIGN KEY (USER_ID) 
  REFERENCES UM_USERS (OBJECT_ID)
  ON DELETE CASCADE);


-- 
-- Foreign Key Constraints for Table UM_USERS_GROUPS 
-- 
ALTER TABLE UM_USERS_GROUPS ADD (
  CONSTRAINT FK_UMUSERSGROUPS_UMGROUPS 
  FOREIGN KEY (GROUP_ID) 
  REFERENCES UM_GROUPS (OBJECT_ID)
  ON DELETE CASCADE);

ALTER TABLE UM_USERS_GROUPS ADD (
  CONSTRAINT FK_UMUSERSGROUPS_UMUSERS 
  FOREIGN KEY (USER_ID) 
  REFERENCES UM_USERS (OBJECT_ID)
  ON DELETE CASCADE);


-- 
-- Foreign Key Constraints for Table UM_PGU_PERMISSIONS 
-- 
ALTER TABLE UM_PGU_PERMISSIONS ADD (
  CONSTRAINT FK_UMPGUPERMIS_UMGROUP 
  FOREIGN KEY (GROUP_ID) 
  REFERENCES UM_GROUPS (OBJECT_ID)
  ON DELETE CASCADE);

ALTER TABLE UM_PGU_PERMISSIONS ADD (
  CONSTRAINT FK_UMPGUPERMIS_UMPERMIS 
  FOREIGN KEY (PERMISSION_ID) 
  REFERENCES UM_PERMISSIONS (OBJECT_ID)
  ON DELETE CASCADE);

ALTER TABLE UM_PGU_PERMISSIONS ADD (
  CONSTRAINT FK_UMPGUPERMIS_UMPROFIL 
  FOREIGN KEY (PROFILE_ID) 
  REFERENCES UM_PROFILES (OBJECT_ID)
  ON DELETE CASCADE);

ALTER TABLE UM_PGU_PERMISSIONS ADD (
  CONSTRAINT FK_UMPGUPERMIS_UMUSER 
  FOREIGN KEY (USER_ID) 
  REFERENCES UM_USERS (OBJECT_ID)
  ON DELETE CASCADE);


GRANT DELETE, INSERT, SELECT, UPDATE ON UM_GROUP_TYPE TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON UM_OBJECTSTATE TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON UM_PERMISSIONS TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON UM_PROFILES TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON UM_GROUP_SUBTYPE TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON UM_USERS TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON UM_DPT_PERMISSION TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON UM_GROUPS TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON UM_OBJ_PERMISSION TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON UM_USERS_PROFILES TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON UM_PGU_PERMISSIONS TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON UM_USERS_GROUPS TO MXDOC41WEB WITH GRANT OPTION;