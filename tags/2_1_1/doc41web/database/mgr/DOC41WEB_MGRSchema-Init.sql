
CREATE TABLE D41_USER_PARTNER
(
  OBJECT_ID           NUMBER(27)                                 NOT NULL,
  CREATED             DATE,
  CHANGED             DATE,
  CREATEDBY           VARCHAR2(10 CHAR),
  CHANGEDBY           VARCHAR2(10 CHAR),
  CREATEDON           VARCHAR2(100 CHAR),
  CHANGEDON           VARCHAR2(100 CHAR),
  USER_ID             NUMBER(27)                                 NOT NULL,
  PARTNER_NUMBER      VARCHAR2(10 CHAR)                          NOT NULL,
  PARTNER_TYPE        VARCHAR2(2 CHAR)                           NOT NULL,
  PARTNER_NAME1       VARCHAR2(40 CHAR)                          NOT NULL,
  PARTNER_NAME2       VARCHAR2(40 CHAR)
)
TABLESPACE DOC41WEB_DAT
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
NOMONITORING;

CREATE UNIQUE INDEX PK_D41_USER_PARTNER ON D41_USER_PARTNER
(OBJECT_ID)
TABLESPACE DOC41WEB_IDX
LOGGING
NOPARALLEL;

CREATE UNIQUE INDEX UK_D41_USER_PARTNER ON D41_USER_PARTNER
(USER_ID, PARTNER_NUMBER)
TABLESPACE DOC41WEB_IDX
LOGGING
NOPARALLEL;

CREATE OR REPLACE TRIGGER D41_USER_PARTNER_CDT
BEFORE INSERT OR UPDATE ON
  DOC41WEB_MGR.D41_USER_PARTNER
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :new.created := DOC41WEB_FDT.get_Normalized_Time();
    IF (:new.changedBy IS NULL) THEN
      :new.changedBy := :new.createdBy;
    END IF;
  END IF;
  IF (UPDATING) THEN
    :new.created   := :old.created;
    :new.createdBy := :old.createdBy;
    :new.createdOn := :old.createdOn;
  END IF;
  :new.changed := DOC41WEB_FDT.get_Normalized_Time();
  IF (INSERTING AND :new.object_Id IS NULL) THEN
    :new.object_Id := DOC41WEB_FDT.get_Next_Oid();
  ELSIF (UPDATING) THEN
    :new.object_Id := :old.object_Id;
  END IF;
END;
/
SHOW ERRORS;

ALTER TABLE D41_USER_PARTNER ADD (
  CONSTRAINT PK_D41_USER_PARTNER
 PRIMARY KEY
 (OBJECT_ID));

ALTER TABLE D41_USER_PARTNER ADD (
  CONSTRAINT UK_D41_USER_PARTNER
 UNIQUE
 (USER_ID, PARTNER_NUMBER));

ALTER TABLE D41_USER_PARTNER ADD (
  CONSTRAINT FK_D41_USER_PARTNER_USER
 FOREIGN KEY (USER_ID)
 REFERENCES DOC41WEB_FDT.UM_USERS (OBJECT_ID)
);

GRANT DELETE, INSERT, SELECT, UPDATE             ON D41_USER_PARTNER TO MXDOC41WEB WITH GRANT OPTION;

CREATE TABLE D41_USER_COUNTRY
(
  OBJECT_ID           NUMBER(27)                                 NOT NULL,
  CREATED             DATE,
  CHANGED             DATE,
  CREATEDBY           VARCHAR2(10 CHAR),
  CHANGEDBY           VARCHAR2(10 CHAR),
  CREATEDON           VARCHAR2(100 CHAR),
  CHANGEDON           VARCHAR2(100 CHAR),
  USER_ID             NUMBER(27)                                 NOT NULL,
  COUNTRY_ISO_CODE    VARCHAR2(2 CHAR)                           NOT NULL
)
TABLESPACE DOC41WEB_DAT
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
NOMONITORING;

CREATE UNIQUE INDEX PK_D41_USER_COUNTRY ON D41_USER_COUNTRY
(OBJECT_ID)
TABLESPACE DOC41WEB_IDX
LOGGING
NOPARALLEL;

CREATE UNIQUE INDEX UK_D41_USER_COUNTRY ON D41_USER_COUNTRY
(USER_ID, COUNTRY_ISO_CODE)
TABLESPACE DOC41WEB_IDX
LOGGING
NOPARALLEL;

CREATE OR REPLACE TRIGGER D41_USER_COUNTRY_CDT
BEFORE INSERT OR UPDATE ON
  DOC41WEB_MGR.D41_USER_COUNTRY
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :new.created := DOC41WEB_FDT.get_Normalized_Time();
    IF (:new.changedBy IS NULL) THEN
      :new.changedBy := :new.createdBy;
    END IF;
  END IF;
  IF (UPDATING) THEN
    :new.created   := :old.created;
    :new.createdBy := :old.createdBy;
    :new.createdOn := :old.createdOn;
  END IF;
  :new.changed := DOC41WEB_FDT.get_Normalized_Time();
  IF (INSERTING AND :new.object_Id IS NULL) THEN
    :new.object_Id := DOC41WEB_FDT.get_Next_Oid();
  ELSIF (UPDATING) THEN
    :new.object_Id := :old.object_Id;
  END IF;
END;
/
SHOW ERRORS;

ALTER TABLE D41_USER_COUNTRY ADD (
  CONSTRAINT PK_D41_USER_COUNTRY
 PRIMARY KEY
 (OBJECT_ID));

ALTER TABLE D41_USER_COUNTRY ADD (
  CONSTRAINT UK_D41_USER_COUNTRY
 UNIQUE
 (USER_ID,  COUNTRY_ISO_CODE));

ALTER TABLE D41_USER_COUNTRY ADD (
  CONSTRAINT FK_D41_USER_COUNTRY_USER
 FOREIGN KEY (USER_ID)
 REFERENCES DOC41WEB_FDT.UM_USERS (OBJECT_ID),
  CONSTRAINT FK_D41_USER_COUNTRY_CNTRY
 FOREIGN KEY (COUNTRY_ISO_CODE)
 REFERENCES DOC41WEB_FDT.COUNTRY (COUNTRY_ISO_CODE)
);

GRANT DELETE, INSERT, SELECT, UPDATE             ON D41_USER_COUNTRY TO MXDOC41WEB WITH GRANT OPTION;

CREATE TABLE D41_USER_PLANT
(
  OBJECT_ID           NUMBER(27)                                 NOT NULL,
  CREATED             DATE,
  CHANGED             DATE,
  CREATEDBY           VARCHAR2(10 CHAR),
  CHANGEDBY           VARCHAR2(10 CHAR),
  CREATEDON           VARCHAR2(100 CHAR),
  CHANGEDON           VARCHAR2(100 CHAR),
  USER_ID             NUMBER(27)                                 NOT NULL,
  PLANT               VARCHAR2(4 CHAR)                           NOT NULL
)
TABLESPACE DOC41WEB_DAT
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
NOMONITORING;

CREATE UNIQUE INDEX PK_D41_USER_PLANT ON D41_USER_PLANT
(OBJECT_ID)
TABLESPACE DOC41WEB_IDX
LOGGING
NOPARALLEL;

CREATE UNIQUE INDEX UK_D41_USER_PLANT ON D41_USER_PLANT
(USER_ID, PLANT)
TABLESPACE DOC41WEB_IDX
LOGGING
NOPARALLEL;

CREATE OR REPLACE TRIGGER D41_USER_PLANT_CDT
BEFORE INSERT OR UPDATE ON
  DOC41WEB_MGR.D41_USER_PLANT
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :new.created := DOC41WEB_FDT.get_Normalized_Time();
    IF (:new.changedBy IS NULL) THEN
      :new.changedBy := :new.createdBy;
    END IF;
  END IF;
  IF (UPDATING) THEN
    :new.created   := :old.created;
    :new.createdBy := :old.createdBy;
    :new.createdOn := :old.createdOn;
  END IF;
  :new.changed := DOC41WEB_FDT.get_Normalized_Time();
  IF (INSERTING AND :new.object_Id IS NULL) THEN
    :new.object_Id := DOC41WEB_FDT.get_Next_Oid();
  ELSIF (UPDATING) THEN
    :new.object_Id := :old.object_Id;
  END IF;
END;
/
SHOW ERRORS;

ALTER TABLE D41_USER_PLANT ADD (
  CONSTRAINT PK_D41_USER_PLANT
 PRIMARY KEY
 (OBJECT_ID));

ALTER TABLE D41_USER_PLANT ADD (
  CONSTRAINT UK_D41_USER_PLANT
 UNIQUE
 (USER_ID,  PLANT));

ALTER TABLE D41_USER_PLANT ADD (
  CONSTRAINT FK_D41_USER_PLANT_USER
 FOREIGN KEY (USER_ID)
 REFERENCES DOC41WEB_FDT.UM_USERS (OBJECT_ID)
);

GRANT DELETE, INSERT, SELECT, UPDATE             ON D41_USER_PLANT TO MXDOC41WEB WITH GRANT OPTION;

CREATE TABLE MD_SYSPARAM
(
  OBJECT_ID              NUMBER(27)                                 NOT NULL,
  CREATED                DATE,
  CHANGED                DATE,
  CREATEDBY              VARCHAR2(10 CHAR),
  CHANGEDBY              VARCHAR2(10 CHAR),
  CREATEDON              VARCHAR2(100 CHAR),
  CHANGEDON              VARCHAR2(100 CHAR),
  PARAM_NAME             VARCHAR2(50 CHAR)                          NOT NULL,
  PARAM_TYPE             VARCHAR2(20 CHAR)                          NOT NULL,
  PARAM_STRINGVALUE      VARCHAR2(500 CHAR),
  PARAM_NUMBERVALUE      NUMBER(27),
  PARAM_DECIMALVALUE     NUMBER(15,3),
  PARAM_BOOLEANVALUE     NUMBER(1)                                           CONSTRAINT CK_MD_SYSPARAM_BOOL  CHECK (PARAM_BOOLEANVALUE IN (0, 1)),
  IS_DELETABLE           NUMBER(1)            DEFAULT 1             NOT NULL CONSTRAINT CK_MD_SYSPARAM_ISDEL CHECK (IS_DELETABLE IN (0, 1))
)
TABLESPACE DOC41WEB_DAT
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
NOMONITORING;

CREATE UNIQUE INDEX PK_MD_SYSPARAM ON MD_SYSPARAM
(OBJECT_ID)
TABLESPACE DOC41WEB_IDX
LOGGING
NOPARALLEL;

CREATE UNIQUE INDEX UK_MD_SYSPARAM ON MD_SYSPARAM
(PARAM_NAME)
TABLESPACE DOC41WEB_IDX
LOGGING
NOPARALLEL;

CREATE OR REPLACE TRIGGER MD_SYSPARAM_CDT
BEFORE INSERT OR UPDATE ON
  DOC41WEB_MGR.MD_SYSPARAM
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :new.created := DOC41WEB_FDT.get_Normalized_Time();
    IF (:new.changedBy IS NULL) THEN
      :new.changedBy := :new.createdBy;
    END IF;
  END IF;
  IF (UPDATING) THEN
    :new.created   := :old.created;
    :new.createdBy := :old.createdBy;
    :new.createdOn := :old.createdOn;
  END IF;
  :new.changed := DOC41WEB_FDT.get_Normalized_Time();
  IF (INSERTING AND :new.object_Id IS NULL) THEN
    :new.object_Id := DOC41WEB_FDT.get_Next_Oid();
  ELSIF (UPDATING) THEN
    :new.object_Id := :old.object_Id;
  END IF;
END;
/
SHOW ERRORS;

ALTER TABLE MD_SYSPARAM ADD (
  CONSTRAINT PK_MD_SYSPARAM
 PRIMARY KEY
 (OBJECT_ID));

ALTER TABLE MD_SYSPARAM ADD (
  CONSTRAINT UK_MD_SYSPARAM
 UNIQUE
 (PARAM_NAME));

GRANT DELETE, INSERT, SELECT, UPDATE ON MD_SYSPARAM TO MXDOC41WEB WITH GRANT OPTION;


CREATE TABLE IM_INTERFACE_DETAIL
(
  OBJECT_ID              NUMBER(27)                                 NOT NULL,
  CREATED                DATE,
  CHANGED                DATE,
  CREATEDBY              VARCHAR2(10 CHAR),
  CHANGEDBY              VARCHAR2(10 CHAR),
  CREATEDON              VARCHAR2(100 CHAR),
  CHANGEDON              VARCHAR2(100 CHAR),
  INTERFACE_NAME         VARCHAR2(50 CHAR)                          NOT NULL,
  INTERFACE_DESCRIPTION  VARCHAR2(200 CHAR)
)
TABLESPACE DOC41WEB_DAT
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
NOMONITORING;

CREATE UNIQUE INDEX PK_IM_INTERFACE_DETAIL ON IM_INTERFACE_DETAIL
(OBJECT_ID)
TABLESPACE DOC41WEB_IDX
LOGGING
NOPARALLEL;

CREATE UNIQUE INDEX UK_IM_INTERFACE_DETAIL ON IM_INTERFACE_DETAIL
(INTERFACE_NAME)
TABLESPACE DOC41WEB_IDX
LOGGING
NOPARALLEL;

CREATE OR REPLACE TRIGGER IM_INTERFACE_DETAIL_CDT
BEFORE INSERT OR UPDATE ON
  DOC41WEB_MGR.IM_INTERFACE_DETAIL
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :new.created := DOC41WEB_FDT.get_Normalized_Time();
    IF (:new.changedBy IS NULL) THEN
      :new.changedBy := :new.createdBy;
    END IF;
  END IF;
  IF (UPDATING) THEN
    :new.created   := :old.created;
    :new.createdBy := :old.createdBy;
    :new.createdOn := :old.createdOn;
  END IF;
  :new.changed := DOC41WEB_FDT.get_Normalized_Time();
  IF (INSERTING AND :new.object_Id IS NULL) THEN
    :new.object_Id := DOC41WEB_FDT.get_Next_Oid();
  ELSIF (UPDATING) THEN
    :new.object_Id := :old.object_Id;
  END IF;
END;
/
SHOW ERRORS;

ALTER TABLE IM_INTERFACE_DETAIL ADD (
  CONSTRAINT PK_IM_INTERFACE_DETAIL
 PRIMARY KEY
 (OBJECT_ID));

ALTER TABLE IM_INTERFACE_DETAIL ADD (
  CONSTRAINT UK_IM_INTERFACE_DETAIL
 UNIQUE
 (INTERFACE_NAME));

GRANT DELETE, INSERT, SELECT, UPDATE ON IM_INTERFACE_DETAIL TO MXDOC41WEB WITH GRANT OPTION;


CREATE TABLE IM_MONITORING_CONTACT
(
  OBJECT_ID              NUMBER(27)                                 NOT NULL,
  CREATED                DATE,
  CHANGED                DATE,
  CREATEDBY              VARCHAR2(10 CHAR),
  CHANGEDBY              VARCHAR2(10 CHAR),
  CREATEDON              VARCHAR2(100 CHAR),
  CHANGEDON              VARCHAR2(100 CHAR),
  INTERFACE_NAME         VARCHAR2(50 CHAR)                          NOT NULL,
  CONTACT_TYPE           VARCHAR2(20 CHAR)                          NOT NULL,
  CWID                   VARCHAR2(10 CHAR)                          NOT NULL,
  FIRST_NAME             VARCHAR2(30 CHAR)                          NOT NULL,
  LAST_NAME              VARCHAR2(70 CHAR)                          NOT NULL,
  EMAIL                  VARCHAR2(70 CHAR)                          NOT NULL,
  PHONE1                 VARCHAR2(35 CHAR)                          NOT NULL,
  PHONE2                 VARCHAR2(35 CHAR)
)
TABLESPACE DOC41WEB_DAT
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
NOMONITORING;

CREATE UNIQUE INDEX PK_IM_MONITORING_CONTACT ON IM_MONITORING_CONTACT
(OBJECT_ID)
TABLESPACE DOC41WEB_IDX
LOGGING
NOPARALLEL;

CREATE UNIQUE INDEX UK_IM_MONITORING_CONTACT ON IM_MONITORING_CONTACT
(INTERFACE_NAME, CWID)
TABLESPACE DOC41WEB_IDX
LOGGING
NOPARALLEL;

CREATE OR REPLACE TRIGGER IM_MONITORING_CONTACT_CDT
BEFORE INSERT OR UPDATE ON
  DOC41WEB_MGR.IM_MONITORING_CONTACT
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :new.created := DOC41WEB_FDT.get_Normalized_Time();
    IF (:new.changedBy IS NULL) THEN
      :new.changedBy := :new.createdBy;
    END IF;
  END IF;
  IF (UPDATING) THEN
    :new.created   := :old.created;
    :new.createdBy := :old.createdBy;
    :new.createdOn := :old.createdOn;
  END IF;
  :new.changed := DOC41WEB_FDT.get_Normalized_Time();
  IF (INSERTING AND :new.object_Id IS NULL) THEN
    :new.object_Id := DOC41WEB_FDT.get_Next_Oid();
  ELSIF (UPDATING) THEN
    :new.object_Id := :old.object_Id;
  END IF;
END;
/
SHOW ERRORS;

ALTER TABLE IM_MONITORING_CONTACT ADD (
  CONSTRAINT PK_IM_MONITORING_CONTACT
 PRIMARY KEY
 (OBJECT_ID));

ALTER TABLE IM_MONITORING_CONTACT ADD (
  CONSTRAINT UK_IM_MONITORING_CONTACT
 UNIQUE
 (INTERFACE_NAME, CWID));

GRANT DELETE, INSERT, SELECT, UPDATE ON IM_MONITORING_CONTACT TO MXDOC41WEB WITH GRANT OPTION;

CREATE TABLE IM_MONITORING_HISTORY
(
  OBJECT_ID              NUMBER(27)                                 NOT NULL,
  CREATED                DATE,
  CHANGED                DATE,
  CREATEDBY              VARCHAR2(10 CHAR),
  CHANGEDBY              VARCHAR2(10 CHAR),
  CREATEDON              VARCHAR2(100 CHAR),
  CHANGEDON              VARCHAR2(100 CHAR),
  INTERFACE_NAME         VARCHAR2(50 CHAR)                          NOT NULL,
  LATEST_REQUEST         TIMESTAMP                                  NOT NULL,
  ACTION_TYPE            VARCHAR2(25 CHAR)                          NOT NULL,
  ACTION_STATUS          VARCHAR2(10 CHAR),
  ACTION_REMARKS         VARCHAR2(100 CHAR),
  ACTION_DETAILS         VARCHAR2(250 CHAR),
  RESPONSE_TIME          NUMBER
)
TABLESPACE DOC41WEB_DAT
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
NOMONITORING;

CREATE UNIQUE INDEX PK_IM_MONITORING_HISTORY ON IM_MONITORING_HISTORY
(OBJECT_ID)
TABLESPACE DOC41WEB_IDX
LOGGING
NOPARALLEL;

CREATE OR REPLACE TRIGGER IM_MONITORING_HISTORY_CDT
BEFORE INSERT OR UPDATE ON
  DOC41WEB_MGR.IM_MONITORING_HISTORY
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :new.created := DOC41WEB_FDT.get_Normalized_Time();
    IF (:new.changedBy IS NULL) THEN
      :new.changedBy := :new.createdBy;
    END IF;
  END IF;
  IF (UPDATING) THEN
    :new.created   := :old.created;
    :new.createdBy := :old.createdBy;
    :new.createdOn := :old.createdOn;
  END IF;
  :new.changed := DOC41WEB_FDT.get_Normalized_Time();
  IF (INSERTING AND :new.object_Id IS NULL) THEN
    :new.object_Id := DOC41WEB_FDT.get_Next_Oid();
  ELSIF (UPDATING) THEN
    :new.object_Id := :old.object_Id;
  END IF;
END;
/
SHOW ERRORS;

ALTER TABLE IM_MONITORING_HISTORY ADD (
  CONSTRAINT PK_IM_MONITORING_HISTORY
 PRIMARY KEY
 (OBJECT_ID));

GRANT DELETE, INSERT, SELECT, UPDATE ON IM_MONITORING_HISTORY TO MXDOC41WEB WITH GRANT OPTION;
