-- WATCH FOR TS: XXX_DAT, XXX_IDX, MXXXX, XXX_MGR, INITIAL-Sizes of IDX/TABS and propper choosing of OID-Sequ.: One Range per Stage, best: lowest Range for PROD
-- 
-- Create Schema Script 
--   Database Version   : 10.2.0.1.0 
--   Toad Version       : 10.5.1.3 
--   DB Connect String  : byz2f8:1521/XE 
--   Schema             : CMN 
--   Script Created by  : GBO_ADMIN 
--   Script Created at  : 19.01.2012 12:27:08 
--   Physical Location  :  
--   Notes              :  
--

-- Object Counts: 
--   Directories: 0 
--   Functions: 17      Lines of Code: 527 
--   Indexes: 51        Columns: 112        
--   Object Privileges: 157 
--   Procedures: 14     Lines of Code: 914 
--   Sequences: 1 
--   Tables: 19         Columns: 216        Lob Segments: 2     Constraints: 44     
--   Triggers: 15 
--   Types: 1 
--   Type Bodies: 1 
--   Views: 5           Columns: 63         


-- "Set define off" turns off substitution variables. 
Set define off; 
--
-- T_LISTAGGFDT  (Type) 
--
CREATE OR REPLACE TYPE
  t_ListAggFdt
AS
  OBJECT
(

  val  VARCHAR2(32767),


  STATIC FUNCTION
    ODCIAggregateInitialize(	sctx		IN OUT	t_ListAggFdt	)
  RETURN
    NUMBER,


  MEMBER FUNCTION
    ODCIAggregateIterate(	self		IN OUT	t_ListAggFdt,
				value		IN	VARCHAR2	)
  RETURN
    NUMBER,


  MEMBER FUNCTION
    ODCIAggregateTerminate(	self		IN	t_ListAggFdt,
				returnValue	OUT	VARCHAR2,
				flags		IN	NUMBER		)
  RETURN
    NUMBER,


  MEMBER FUNCTION
    ODCIAggregateMerge(		self		IN OUT	t_ListAggFdt,
				ctx2		IN	t_ListAggFdt	)
  RETURN
    NUMBER


);
/


--
-- T_LISTAGGFDT  (Type Body) 
--
CREATE OR REPLACE TYPE BODY
  t_ListAggFdt
IS


  STATIC FUNCTION
    ODCIAggregateInitialize(	sctx		IN OUT	t_ListAggFdt	)
  RETURN
    NUMBER
  IS
  BEGIN
    sctx := t_ListAggFdt(NULL);
    RETURN ODCICONST.SUCCESS;
  END;


  MEMBER FUNCTION
    ODCIAggregateIterate(	self		IN OUT	t_ListAggFdt,
				value		IN	VARCHAR2	)
  RETURN
    NUMBER
  IS
  BEGIN
    self.val := self.val || '|' || value;
    RETURN ODCICONST.SUCCESS;
  END;


  MEMBER FUNCTION
    ODCIAggregateTerminate(	self		IN	t_ListAggFdt,
				returnValue	OUT	VARCHAR2,
				flags		IN	NUMBER		)
  RETURN
    NUMBER
  IS
  BEGIN
    returnValue := RTRIM( LTRIM( self.val, '|' ), '|' );
    RETURN ODCICONST.SUCCESS;
  END;


  MEMBER FUNCTION
    ODCIAggregateMerge(		self		IN OUT	t_ListAggFdt,
				ctx2		IN	t_ListAggFdt	)
  RETURN
    NUMBER
  IS
  BEGIN
    self.val := self.val || '|' || ctx2.val;
    RETURN ODCICONST.SUCCESS;
  END;


END;
/

--
-- OBJECT_ID  (Sequence) 
--
CREATE SEQUENCE OBJECT_ID
  START WITH 100000
  MAXVALUE 99999999999999
  MINVALUE 100000
  NOCYCLE
  CACHE 1000
  NOORDER;


--
-- PERFORMANCE  (Table) 
--
CREATE TABLE PERFORMANCE
(
  OBJECT_ID      NUMBER(27)                     NOT NULL,
  LOGCHANNELNO   NUMBER(2),
  LOGCHANNEL     VARCHAR2(20 CHAR),
  PHYSCHANNELNO  NUMBER(2),
  CLASSNAME      VARCHAR2(100 CHAR),
  THREAD         VARCHAR2(50 CHAR),
  USERS          VARCHAR2(10 CHAR),
  EXECUSER       VARCHAR2(10 CHAR),
  COMPONENT      VARCHAR2(5 CHAR),
  ACTIONTYPE     VARCHAR2(20 CHAR),
  KEYNAME        VARCHAR2(50 CHAR),
  KEYID          NUMBER(27),
  SUBKEYNAME     VARCHAR2(50 CHAR),
  REFNAME        VARCHAR2(50 CHAR),
  REFID          NUMBER(27),
  MESSAGE        VARCHAR2(250 CHAR),
  ADDINFO        VARCHAR2(250 CHAR),
  MILLISSUM      NUMBER(12),
  MILLISSTEP     NUMBER(12),
  DATA_DATE      DATE,
  MACHINE        VARCHAR2(15 CHAR),
  VMTS           DATE,
  CREATED        DATE                           NOT NULL
)
TABLESPACE DOC41WEB_DAT
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          20M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );



--
-- WEBMETRIX  (Table) 
--
CREATE TABLE WEBMETRIX
(
  OBJECT_ID      NUMBER(27)                     NOT NULL,
  LOGCHANNELNO   NUMBER(2),
  LOGCHANNEL     VARCHAR2(20 CHAR),
  PHYSCHANNELNO  NUMBER(2),
  CLASSNAME      VARCHAR2(100 CHAR),
  THREAD         VARCHAR2(50 CHAR),
  VMTS           DATE,
  USERS          VARCHAR2(10 CHAR),
  EXECUSER       VARCHAR2(10 CHAR),
  COMPONENT      VARCHAR2(5 CHAR),
  ACTIONTYPE     VARCHAR2(20 CHAR),
  DATA1          VARCHAR2(750 CHAR),
  DATA2          VARCHAR2(250 CHAR),
  DATA3          VARCHAR2(250 CHAR),
  DATA4          VARCHAR2(250 CHAR),
  DATA5          VARCHAR2(250 CHAR),
  CREATED        DATE                           NOT NULL,
  MACHINE        VARCHAR2(15 CHAR),
  DATA_DATE      DATE,
  DATA_NUMBER1   NUMBER(20,5),
  DATA_NUMBER2   NUMBER(20,5),
  DATA_NUMBER3   NUMBER(20,5),
  CREATED_M      VARCHAR2(7 BYTE),
  VMTS_M         VARCHAR2(7 BYTE)
)
TABLESPACE DOC41WEB_DAT
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          20M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- WEBINFO_COMPONENT_TYPE  (Table) 
--
CREATE TABLE WEBINFO_COMPONENT_TYPE
(
  COMP_TYPE      VARCHAR2(30 CHAR)              NOT NULL,
  COMP_DESC      VARCHAR2(500 CHAR),
  COMP_DESC_TAG  VARCHAR2(50 CHAR)              NOT NULL,
  DISPLAY_GROUP  NUMBER                         NOT NULL
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
-- VERSIONS  (Table) 
--
CREATE TABLE VERSIONS
(
  MODULE      VARCHAR2(20 CHAR)                 NOT NULL,
  VERSION     NUMBER(2)                         NOT NULL,
  SUBVERSION  NUMBER(3)                         NOT NULL
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
-- TRANSLATIONS  (Table) 
--
CREATE TABLE TRANSLATIONS
(
  OBJECT_ID      NUMBER(27)                     NOT NULL,
  MANDANT        VARCHAR2(20 CHAR)              NOT NULL,
  COMPONENT      VARCHAR2(10 CHAR)              NOT NULL,
  JSP_NAME       VARCHAR2(20 CHAR)              NOT NULL,
  TAG_NAME       VARCHAR2(50 CHAR)              NOT NULL,
  LANGUAGE_CODE  VARCHAR2(2 CHAR)               NOT NULL,
  COUNTRY_CODE   VARCHAR2(2 CHAR)               NOT NULL,
  TAG_VALUE      VARCHAR2(4000 CHAR)            NOT NULL,
  CREATED        DATE,
  CHANGED        DATE,
  CREATEDBY      VARCHAR2(10 CHAR),
  CHANGEDBY      VARCHAR2(10 CHAR),
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
            INITIAL          10M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- TEXTTYPE  (Table) 
--
CREATE TABLE TEXTTYPE
(
  TEXTTYPE            VARCHAR2(20 CHAR),
  TEXTTYPE_ID         NUMBER(4)                 NOT NULL,
  PARENT_TEXTTYPE_ID  NUMBER(4)
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
-- SESSION_DATA  (Table) 
--
CREATE TABLE SESSION_DATA
(
  OBJECT_ID   NUMBER(27)                        NOT NULL,
  ID          VARCHAR2(80 CHAR)                 NOT NULL,
  COMPONENT   VARCHAR2(20 CHAR)                 NOT NULL,
  CREATED     DATE                              NOT NULL,
  CHANGED     DATE                              NOT NULL,
  TIMETOLIVE  NUMBER(10)                        NOT NULL,
  ENDOFLIFE   DATE                              NOT NULL,
  DATA        CLOB
)
LOB (DATA) STORE AS (
  TABLESPACE DOC41WEB_DAT
  ENABLE       STORAGE IN ROW
  CHUNK       8192
  RETENTION
  NOCACHE
  LOGGING
  INDEX       (
        TABLESPACE DOC41WEB_DAT
        STORAGE    (
                    INITIAL          256K
                    MINEXTENTS       1
                    MAXEXTENTS       UNLIMITED
                    PCTINCREASE      0
                    BUFFER_POOL      DEFAULT
                   ))
      STORAGE    (
                  INITIAL          5M
                  MINEXTENTS       1
                  MAXEXTENTS       UNLIMITED
                  PCTINCREASE      0
                  BUFFER_POOL      DEFAULT
                 ))
TABLESPACE DOC41WEB_DAT
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          5M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- SCHEDULED_TASKS  (Table) 
--
CREATE TABLE SCHEDULED_TASKS
(
  OBJECT_ID        NUMBER(27)                   NOT NULL,
  STATUS           NUMBER(2)                    NOT NULL,
  START_TIME       DATE                         NOT NULL,
  INTERVAL         NUMBER(13)                   NOT NULL,
  LAST_EXECUTION   DATE,
  PARAMETERS       CLOB                         NOT NULL,
  TASK_NAME        VARCHAR2(80 CHAR)            NOT NULL,
  FULL_CLASSNAME   VARCHAR2(100 CHAR)           NOT NULL,
  LIMITED_MACHINE  VARCHAR2(200 CHAR)
)
LOB (PARAMETERS) STORE AS (
  TABLESPACE DOC41WEB_DAT
  ENABLE       STORAGE IN ROW
  CHUNK       8192
  RETENTION
  NOCACHE
  LOGGING
  INDEX       (
        TABLESPACE DOC41WEB_DAT
        STORAGE    (
                    INITIAL          64K
                    MINEXTENTS       1
                    MAXEXTENTS       UNLIMITED
                    PCTINCREASE      0
                    BUFFER_POOL      DEFAULT
                   ))
      STORAGE    (
                  INITIAL          256K
                  MINEXTENTS       1
                  MAXEXTENTS       UNLIMITED
                  PCTINCREASE      0
                  BUFFER_POOL      DEFAULT
                 ))
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
-- OBJECTSTATE  (Table) 
--
CREATE TABLE OBJECTSTATE
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
-- NAVIGATION  (Table) 
--
CREATE TABLE NAVIGATION
(
  OBJECT_ID           NUMBER(27)                NOT NULL,
  NAV_COMPONENT       VARCHAR2(30 CHAR)         NOT NULL,
  NAV_CONTEXT         VARCHAR2(50 CHAR)         NOT NULL,
  NAV_TAG             VARCHAR2(40 CHAR),
  LINK                VARCHAR2(500 CHAR),
  POST_PARAMETERS     VARCHAR2(1000 CHAR),
  TARGET              VARCHAR2(30 CHAR),
  POSITION            NUMBER(3),
  GROUPS              VARCHAR2(200 CHAR),
  CWID                VARCHAR2(10 CHAR),
  INFORMATION         VARCHAR2(250 CHAR),
  PARENT_ID           NUMBER(27),
  CREATED             DATE                      NOT NULL,
  CHANGED             DATE                      NOT NULL,
  CREATEDBY           VARCHAR2(10 CHAR),
  CHANGEDBY           VARCHAR2(10 CHAR),
  ACCESS_CHECKER      VARCHAR2(20 CHAR),
  LINK_BUILDER        VARCHAR2(20 CHAR),
  NAVIGATION_BUILDER  VARCHAR2(20 CHAR),
  TO_BE_TRANSLATED    NUMBER(1)                 DEFAULT 1                     NOT NULL,
  USERCHANGED         DATE,
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
-- LANGUAGE  (Table) 
--
CREATE TABLE LANGUAGE
(
  LANGUAGE           VARCHAR2(80 CHAR),
  LANGUAGE_ISO_CODE  VARCHAR2(2 CHAR)           NOT NULL
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
-- COUNTRY  (Table) 
--
CREATE TABLE COUNTRY
(
  COUNTRY           VARCHAR2(80 CHAR),
  COUNTRY_ISO_CODE  VARCHAR2(2 CHAR)            NOT NULL
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
-- FK_NAVIGATION_NAVIGATION_IDX  (Index) 
--
CREATE INDEX FK_NAVIGATION_NAVIGATION_IDX ON NAVIGATION
(PARENT_ID)
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
-- NAVIGATION_SK  (Index) 
--
CREATE UNIQUE INDEX NAVIGATION_SK ON NAVIGATION
(NAV_COMPONENT, NAV_CONTEXT)
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
-- PK_COUNTRY  (Index) 
--
CREATE UNIQUE INDEX PK_COUNTRY ON COUNTRY
(COUNTRY_ISO_CODE)
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
-- PK_LANGUAGE  (Index) 
--
CREATE UNIQUE INDEX PK_LANGUAGE ON LANGUAGE
(LANGUAGE_ISO_CODE)
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
-- PK_NAVIGATION  (Index) 
--
CREATE UNIQUE INDEX PK_NAVIGATION ON NAVIGATION
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
-- PK_OBJECTSTATE  (Index) 
--
CREATE UNIQUE INDEX PK_OBJECTSTATE ON OBJECTSTATE
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
-- PK_PERFORMANCE  (Index) 
--
CREATE UNIQUE INDEX PK_PERFORMANCE ON PERFORMANCE
(OBJECT_ID)
TABLESPACE DOC41WEB_IDX
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
-- PK_SCHEDULED_TASKS  (Index) 
--
CREATE UNIQUE INDEX PK_SCHEDULED_TASKS ON SCHEDULED_TASKS
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
-- PK_TEXTTYPE  (Index) 
--
CREATE UNIQUE INDEX PK_TEXTTYPE ON TEXTTYPE
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
-- PK_VERSIONS  (Index) 
--
CREATE UNIQUE INDEX PK_VERSIONS ON VERSIONS
(MODULE)
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
-- PK_WEBINFO_COMPONENT_TYPE  (Index) 
--
CREATE UNIQUE INDEX PK_WEBINFO_COMPONENT_TYPE ON WEBINFO_COMPONENT_TYPE
(COMP_TYPE)
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
-- PK_WEBMETRIX  (Index) 
--
CREATE UNIQUE INDEX PK_WEBMETRIX ON WEBMETRIX
(OBJECT_ID)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          3M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- SESSION_DATAPK  (Index) 
--
CREATE UNIQUE INDEX SESSION_DATAPK ON SESSION_DATA
(OBJECT_ID)
TABLESPACE DOC41WEB_IDX
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
-- SESSION_DATA_SK  (Index) 
--
CREATE UNIQUE INDEX SESSION_DATA_SK ON SESSION_DATA
(ID, COMPONENT)
TABLESPACE DOC41WEB_IDX
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
-- TRANSLATIONS_KEY_IDX_U  (Index) 
--
CREATE UNIQUE INDEX TRANSLATIONS_KEY_IDX_U ON TRANSLATIONS
(MANDANT, COMPONENT, JSP_NAME, LANGUAGE_CODE, COUNTRY_CODE, 
TAG_NAME)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          5M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- TRANSLATIONS_MEREGEORD_IDX  (Index) 
--
CREATE INDEX TRANSLATIONS_MEREGEORD_IDX ON TRANSLATIONS
(COMPONENT, JSP_NAME, COUNTRY_CODE)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          4M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- TRANSLATIONS_NAME_LOC_IDX  (Index) 
--
CREATE INDEX TRANSLATIONS_NAME_LOC_IDX ON TRANSLATIONS
(JSP_NAME, LANGUAGE_CODE, COUNTRY_CODE, TAG_NAME)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          4M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- TRANSLATIONS_PK  (Index) 
--
CREATE UNIQUE INDEX TRANSLATIONS_PK ON TRANSLATIONS
(OBJECT_ID)
TABLESPACE DOC41WEB_IDX
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
-- WM_ACT_CRE  (Index) 
--
CREATE INDEX WM_ACT_CRE ON WEBMETRIX
(ACTIONTYPE, CREATED)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          3M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- WM_COM_ACT_DAT1_CRE  (Index) 
--
CREATE INDEX WM_COM_ACT_DAT1_CRE ON WEBMETRIX
(COMPONENT, ACTIONTYPE, DATA1, CREATED)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          3M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- WM_COM_CREM_USR_EXEC  (Index) 
--
CREATE INDEX WM_COM_CREM_USR_EXEC ON WEBMETRIX
(COMPONENT, CREATED_M, USERS, EXECUSER)
TABLESPACE DOC41WEB_IDX
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
-- WM_CRE  (Index) 
--
CREATE INDEX WM_CRE ON WEBMETRIX
(CREATED)
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
-- WM_EXEC_CREM  (Index) 
--
CREATE INDEX WM_EXEC_CREM ON WEBMETRIX
(EXECUSER, CREATED_M)
TABLESPACE DOC41WEB_IDX
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
-- WM_EXEC_VMTSM  (Index) 
--
CREATE INDEX WM_EXEC_VMTSM ON WEBMETRIX
(EXECUSER, VMTS_M)
TABLESPACE DOC41WEB_IDX
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
-- WM_EXU_ACT_COM_CRE  (Index) 
--
CREATE INDEX WM_EXU_ACT_COM_CRE ON WEBMETRIX
(EXECUSER, ACTIONTYPE, COMPONENT, CREATED)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          5M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- WM_EXU_COM_CRE  (Index) 
--
CREATE INDEX WM_EXU_COM_CRE ON WEBMETRIX
(EXECUSER, COMPONENT, CREATED)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          3M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- WM_USR_ACT_COM_CRE  (Index) 
--
CREATE INDEX WM_USR_ACT_COM_CRE ON WEBMETRIX
(USERS, ACTIONTYPE, COMPONENT, CREATED)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          5M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- WM_USR_COM_CRE  (Index) 
--
CREATE INDEX WM_USR_COM_CRE ON WEBMETRIX
(USERS, COMPONENT, CREATED)
TABLESPACE DOC41WEB_IDX
PCTFREE    10
INITRANS   2
MAXTRANS   255
STORAGE    (
            INITIAL          3M
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- WM_VMTS  (Index) 
--
CREATE INDEX WM_VMTS ON WEBMETRIX
(VMTS)
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
-- SYNC_CHANGED_SERIAL_AND_EXTKEY  (Procedure) 
--
CREATE OR REPLACE PROCEDURE
  Sync_Changed_Serial_And_ExtKey (
    old_ExtKey        IN VARCHAR2,
    old_Serial        IN NUMBER,
    old_Sys           IN VARCHAR2,
    new_ExtKey        IN OUT VARCHAR2,
    new_Serial        IN OUT NUMBER,
    new_Sys           IN OUT VARCHAR2,
    source_Table_Name IN VARCHAR2,
    source_Serial_Col IN VARCHAR2 := 'object_Id',
    source_ExtKey_Col IN VARCHAR2 := 'object_No',
    source_Sys_Col    IN VARCHAR2
  )
IS
  xtmp VARCHAR2(200 CHAR) := '';
PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN
  IF ( ( ( ( NVL( old_ExtKey, ' ' ) <> NVL( new_ExtKey, ' ' ) ) OR
           ( NVL( old_Sys, ' '    ) <> NVL( new_Sys, ' '    ) )    ) AND        -- ExtKey/Sys changed
         (   NVL( new_ExtKey, ' ' ) <> '_'                         ) AND        -- AND NOT due to reinit request itself
         (   NVL( old_Serial, -1  ) =  NVL( new_Serial, -1  )      )     ) OR   -- but Serial not updated
       (     NVL( new_Serial, -1  ) =  0                                 )    ) -- or Serial request reinit itself
  THEN -- UPDATE new_Serial, follows new_ExtKey !!!
    IF ( new_ExtKey IS NULL ) THEN
      new_Serial := NULL;
    ELSE
      BEGIN
        new_Serial := NULL;
        IF ( source_Sys_Col IS NOT NULL ) THEN
          IF ( new_Sys IS NOT NULL ) THEN
            xtmp := ' AND ( ' || source_Sys_Col || ' = ''' || REPLACE( new_Sys, '''', '''''' ) || ''' )';
          ELSE
            xtmp := ' AND ( ' || source_Sys_Col || ' IS NULL )';
          END IF;
        END IF;
        --DBMS_OUTPUT.PUT_LINE('SELECT ' || source_Serial_Col || ' FROM ' || source_Table_Name ||
        --' WHERE ( ' || source_ExtKey_Col || ' = ''' || REPLACE( new_ExtKey, '''', '''''' ) || ''' )' || xtmp);
        EXECUTE IMMEDIATE 'SELECT ' || source_Serial_Col || ' FROM ' || source_Table_Name ||
        ' WHERE ( ' || source_ExtKey_Col || ' = ''' || REPLACE( new_ExtKey, '''', '''''' ) || ''' )' || xtmp INTO new_Serial;
        --DBMS_OUTPUT.PUT_LINE( '=>' || new_Serial );
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
        new_Serial := NULL; -- may cause a constraint violation, but can't be resolved...
      END;
    END IF;
  ELSIF ( ( ( NVL( old_Serial, -1  ) <> NVL( new_Serial, -1  ) ) AND        -- Serial changed
            ( NVL( new_Serial, -1  ) <> 0                      ) AND        -- AND NOT due to reinit request itself
            ( NVL( old_ExtKey, ' ' ) =  NVL( new_ExtKey, ' ' ) ) AND
            ( NVL( old_Sys, ' '    ) =  NVL( new_Sys, ' '    ) )     ) OR   -- but ExtKey/Sys not updated
            ( NVL( new_ExtKey, ' ' ) =  '_'                          )    ) -- or ExtKey request reinit itself
  THEN -- UPDATE new_ExtKey, follows new_Serial !!!
    IF ( new_Serial IS NULL ) THEN
      new_ExtKey := NULL;
    ELSE
      BEGIN
        new_ExtKey := NULL;
        new_Sys := NULL;
        --IF ( source_Sys_Col IS NOT NULL ) THEN
        xtmp := ', ' || NVL( source_Sys_Col, 'NULL' ); -- || ' INTO :new_Sys';
        --END IF;
        --DBMS_OUTPUT.PUT_LINE('SELECT ' || source_ExtKey_Col || xtmp || ' FROM ' || source_Table_Name ||
        --' WHERE ( ' || source_Serial_Col || ' = ''' || REPLACE( new_Serial, '''', '''''' ) || ''' )');
        EXECUTE IMMEDIATE 'SELECT ' || source_ExtKey_Col || xtmp || ' FROM ' || source_Table_Name ||
        ' WHERE ( ' || source_Serial_Col || ' = ''' || REPLACE( new_Serial, '''', '''''' ) || ''' )' INTO new_ExtKey, new_Sys;
        --DBMS_OUTPUT.PUT_LINE( '=>' || new_ExtKey || '/' || new_Sys );
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
        new_ExtKey := NULL; -- unknown for the ne SerialId!!!
        new_Sys := NULL;
      END;
    END IF;
  END IF;
END Sync_Changed_Serial_And_ExtKey;
/


--
-- FROM_HTML_CLOB  (Procedure) 
--
CREATE OR REPLACE PROCEDURE FROM_HTML_CLOB (
    ival IN CLOB,
    res IN OUT NOCOPY CLOB
  )
IS
  idx   BINARY_INTEGER := 0;
  idxs  BINARY_INTEGER;
  sidx  BINARY_INTEGER := 1;
  pos   BINARY_INTEGER := 1;
  rl    BINARY_INTEGER;
  maxL  BINARY_INTEGER := 3000;
  maxT  BINARY_INTEGER := 5;
  crlf  CHAR(2 CHAR) := CHR(13) || CHR(10);
  crdel CHAR(2 CHAR) := '  '; 
  sOpn  CHAR(1 CHAR) := '<';
  sCls  CHAR(1 CHAR) := '>';
  delim VARCHAR2(1 CHAR) := '';
  tag   VARCHAR2(4000 CHAR) := NULL;
  val   CLOB := ival;
  res1  CLOB;
  resA  VARCHAR2(4000 CHAR);
  partTag BOOLEAN;
  partVal BOOLEAN;
BEGIN
  IF ( val IS NOT NULL ) THEN
    DBMS_LOB.CREATETEMPORARY( res1, false, DBMS_LOB.CALL );
    LOOP
      idx := DBMS_LOB.INSTR( val, sOpn, sidx );
      partVal := false;
      IF ( idx > 0 ) THEN
        IF ( idx > sidx + maxL ) THEN
          idx := sidx + maxL;
          partVal := true;
        END IF;
        resA := delim || TRANSLATE( DBMS_LOB.SUBSTR( val, idx - sidx, sidx ), crlf, crdel );
        IF ( LENGTH(resA) > 0 ) THEN
          DBMS_LOB.APPEND( res1, TO_CLOB( resA ) );
        END IF;
        IF ( partVal ) THEN
          sidx := idx;
        ELSE
          sidx := idx + 1;
          idx := DBMS_LOB.INSTR( val, sCls, sidx );
          delim := '';
          IF ( idx > 0 ) THEN
            idxs := idx;
            IF ( idxs > sidx + maxT ) THEN
              idxs := sidx + maxT;
            END IF;
            tag := REPLACE( REPLACE( UPPER( DBMS_LOB.SUBSTR( val, idxs - sidx, sidx ) ), ' /', '/' ), '/ ', '/' );
            sidx := idx + 1;
            IF ( tag IN ('BR','BR/', 'P','/P', 'P/') ) THEN
              delim:=CHR(10);
	    ELSIF ( tag = 'PRE' ) THEN
              crdel:=' ' || CHR(10);
            ELSIF ( tag = '/PRE' ) THEN
              crdel:='  ';
            END IF;
          END IF;
        END IF;
      ELSE
        resA := delim || TRANSLATE( SUBSTR( val, sidx ), crlf, crdel );
        IF ( LENGTH(resA) > 0 ) THEN
          DBMS_LOB.APPEND(res1, TO_CLOB( resA ) );
        END IF;
      END IF;
      IF ( idx <= 0 ) THEN
      	EXIT;
      END IF;
    END LOOP;
    val := res1;
    sidx := 1;
    sOpn := '&';
    sCls := ';';
    delim := '';
    LOOP
      idx := DBMS_LOB.INSTR( val, sOpn, sidx );
      partVal := false;
      IF ( idx > 0 ) THEN
        IF ( idx > sidx + maxL ) THEN
          idx := sidx + maxL;
          partVal := true;
        END IF;
        resA := delim || DBMS_LOB.SUBSTR( val, idx - sidx, sidx );
        IF ( LENGTH(resA) > 0 ) THEN
          DBMS_LOB.APPEND( res, TO_CLOB( resA ) );
        END IF;
        delim := '';
        IF ( partVal ) THEN
          sidx := idx;
        ELSE
          sidx := idx + 1;
          idx := DBMS_LOB.INSTR( val, sCls, sidx );
          IF ( idx > 0 ) THEN
            tag := UPPER( SUBSTR( val, sidx, idx - sidx ) );
            sidx := idx + 1;
            IF ( tag = 'AMP' ) THEN
              delim:='&';
	    ELSIF ( tag = 'LT' ) THEN
              delim:='<';
            ELSIF ( tag = 'GT' ) THEN
              delim:='>';
            END IF;
          END IF;
        END IF;
      ELSE
        rl := DBMS_LOB.GETLENGTH( val ) - sidx + 1;
        IF ( rl > maxL ) THEN
          idx := sidx + maxL - 1;
          rl := maxL;
        END IF;
        resA := delim || DBMS_LOB.SUBSTR( val, rl, sidx );
        IF ( LENGTH(resA) > 0 ) THEN
          DBMS_LOB.APPEND( res, TO_CLOB( resA ) );
        ENd IF;
        delim := '';
        sidx := idx + 1;
      END IF;
      IF ( idx <= 0 ) THEN
      	EXIT;
      END IF;
    END LOOP;
    IF ( LENGTH( delim ) > 0 ) THEN
      DBMS_LOB.APPEND( res, TO_CLOB( delim ) );
    END IF;
    DBMS_LOB.FREETEMPORARY( res1 );
  END IF;
END FROM_HTML_CLOB;
/



--
-- GET_NEXT_OID  (Function) 
--
CREATE OR REPLACE FUNCTION 
  Get_Next_OID(
    dest_Table_Schema VARCHAR2 := NULL, -- optional, for future extensions, allows adaption of foreign serial systems
    dest_Table_Name VARCHAR2   := NULL  -- optional, for future extensions, allows adaption of foreign serial systems
  )
RETURN
  NUMBER
IS
  oid NUMBER;
-- PRAGMA AUTONOMOUS_TRANSACTION; -- NOT REQUIRED anymore
BEGIN
  SELECT
    Object_Id.nextVal
  INTO
    oid
  FROM
    DUAL;
--  COMMIT;
  RETURN oid;
END;
/


--
-- SDT_GET_OID  (Function) 
--
CREATE OR REPLACE FUNCTION
  SDT_Get_OID (
    new_Id        Session_Data.id%TYPE,
    new_Component Session_Data.component%TYPE
  )
RETURN
  Session_Data.Object_Id%TYPE
IS
-- Check, if a matching session exists, return the object_id
  exist_Object_Id    Session_Data.Object_Id%TYPE := NULL;
PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN
  SELECT
    object_Id
  INTO
    exist_Object_Id
  FROM
    Session_Data
  WHERE
    ( id        = new_Id        ) AND
    ( component = new_Component )
  ;
  COMMIT WORK;
  RETURN exist_Object_Id;
EXCEPTION
WHEN NO_DATA_FOUND THEN
  COMMIT WORK;
  RETURN NULL;
END;
/


--
-- LISTAGGFDT  (Function) 
--
CREATE OR REPLACE FUNCTION
  listAggFdt ( val VARCHAR2 )
RETURN
  VARCHAR2
PARALLEL_ENABLE AGGREGATE USING t_ListAggFdt;
/


--
-- LOOKUP_SERIAL_BY_EXTKEY  (Function) 
--
CREATE OR REPLACE FUNCTION
  LookUp_Serial_By_ExtKey (
    source_Table_Name IN VARCHAR2,
    new_ExtKey        IN VARCHAR2,
    source_ExtKey_Col IN VARCHAR2 := 'object_No',
    source_Serial_Col IN VARCHAR2 := 'object_Id',
    ext_Cond          IN VARCHAR2 := ''
  )
RETURN
  NUMBER
IS
PRAGMA AUTONOMOUS_TRANSACTION;
  new_Serial NUMBER := NULL;
BEGIN
  EXECUTE IMMEDIATE 'SELECT MIN(' || source_Serial_Col || ') FROM ' || source_Table_Name ||
    ' WHERE ' || NVL(ext_Cond, ' ') || ' ( ' || source_ExtKey_Col || ' = ''' || REPLACE( new_ExtKey, '''', '''''' ) ||
    ''' ) HAVING ( MIN(' || source_ExtKey_Col || ') = MAX(' || source_ExtKey_Col || ') )' INTO new_Serial;
  RETURN new_Serial;
EXCEPTION
WHEN NO_DATA_FOUND THEN
  RETURN NULL; -- may cause a constraint violation, but can't be resolved...
END LookUp_Serial_By_ExtKey;
/


--
-- LOOKUP_SERIAL_BY_EXTKEY_IT  (Function) 
--
CREATE OR REPLACE FUNCTION
  LookUp_Serial_By_ExtKey_IT (
    source_Table_Name IN VARCHAR2,
    new_ExtKey        IN VARCHAR2,
    source_ExtKey_Col IN VARCHAR2 := 'object_No',
    source_Serial_Col IN VARCHAR2 := 'object_Id',
    ext_Cond          IN VARCHAR2 := ''
  )
RETURN
  NUMBER
IS
  new_Serial NUMBER := NULL;
BEGIN
  EXECUTE IMMEDIATE 'SELECT MIN(' || source_Serial_Col || ') FROM ' || source_Table_Name ||
    ' WHERE ' || NVL(ext_Cond, ' ') || ' ( ' || source_ExtKey_Col || ' = ''' || REPLACE( new_ExtKey, '''', '''''' ) ||
    ''' ) HAVING ( MIN(' || source_ExtKey_Col || ') = MAX(' || source_ExtKey_Col || ') )' INTO new_Serial;
  RETURN new_Serial;
EXCEPTION
WHEN NO_DATA_FOUND THEN
  RETURN NULL; -- may cause a constraint violation, but can't be resolved...
END LookUp_Serial_By_ExtKey_IT;
/


--
-- LOOKUP_FIELDS_BY_ID_IT  (Function) 
--
CREATE OR REPLACE FUNCTION
  LookUp_Fields_By_Id_IT (
    source_Table_Name IN VARCHAR2,
    new_Object_Id     IN NUMBER,
    query_Expr        IN VARCHAR2 := 'object_No',
    source_Serial_Col IN VARCHAR2 := 'object_Id'
  )
RETURN
  VARCHAR2
IS
  res VARCHAR2(500 CHAR) := NULL;
BEGIN
  IF ( new_Object_Id IS NOT NULL ) THEN
    EXECUTE IMMEDIATE 'SELECT ' || query_Expr || ' FROM ' || source_Table_Name ||
      ' WHERE ( ' || source_Serial_Col || ' = ' || TO_CHAR( new_Object_Id ) || ' )' INTO res;
  END IF;
  RETURN res;
EXCEPTION
WHEN NO_DATA_FOUND THEN
  RETURN NULL;
END LookUp_Fields_By_Id_IT;
/


--
-- LOOKUP_FIELDS_BY_ID_AT  (Function) 
--
CREATE OR REPLACE FUNCTION
  LookUp_Fields_By_Id_AT (
    source_Table_Name IN VARCHAR2,
    new_Object_Id     IN NUMBER,
    query_Expr        IN VARCHAR2 := 'object_No',
    source_Serial_Col IN VARCHAR2 := 'object_Id'
  )
RETURN
  VARCHAR2
IS
  res VARCHAR2(500 CHAR) := NULL;
PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN
  res := LookUp_Fields_By_Id_IT(source_Table_Name, new_Object_Id, query_Expr, source_Serial_Col);
  COMMIT;
  RETURN res;
END LookUp_Fields_By_Id_AT;
/


--
-- CHARCODES  (Function) 
--
CREATE OR REPLACE FUNCTION
  charCodes(
    src VARCHAR,
    fstr VARCHAR := '000X'
  )
RETURN
  VARCHAR
IS
  flen	BINARY_INTEGER		:= LENGTH(fstr);
  len	BINARY_INTEGER		:= LEAST(NVL( LENGTH(src), 0 ), 1000);
  res	VARCHAR2(32767 CHAR)	:= '';
BEGIN
  FOR i IN 1..len LOOP
    res := res || TO_CHAR( ASCII( SUBSTR(src, i, 1) ), fstr );
  END LOOP;
  RETURN res;
END charCodes;
/


--
-- GET_NORMALIZED_TIME  (Function) 
--
CREATE OR REPLACE FUNCTION
  Get_Normalized_Time
RETURN
  DATE
IS
BEGIN
--no normalize:
--RETURN SYSDATE;

--normalize to UTC
  RETURN TO_DATE( TO_CHAR(systimestamp AT TIME ZONE 'UTC','YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS');

--normalize to UTC (00:00)
--RETURN TO_DATE( TO_CHAR(systimestamp AT TIME ZONE '+00:00','YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS');

--normalize to CET w/o DST (01:00 fix)
--RETURN TO_DATE( TO_CHAR(systimestamp AT TIME ZONE '+01:00','YYYY-MM-DD HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS');
END;
/


--
-- FDT_BUILD_DATE  (Function) 
--
CREATE OR REPLACE FUNCTION
  FDT_Build_Date(
    v1 VARCHAR2,
    v2 VARCHAR2,
    v3 VARCHAR2,
    var NUMBER
  )
RETURN
  VARCHAR2
DETERMINISTIC
IS
  result VARCHAR2(10 CHAR) := NULL;
  d VARCHAR2(2 CHAR) := NULL;
  m VARCHAR2(2 CHAR) := NULL;
  y4 VARCHAR2(4 CHAR) := NULL;
BEGIN
  IF ( LENGTH( v1 || v2 || v3 ) = 6 ) THEN
    IF ( var = 1 ) THEN
      result:= v1||v2||v3;
    ELSIF ( var = 2 ) THEN
      result:= v1||v3||v2;
    ELSIF ( var = 3 ) THEN
      result:= v2||v1||v3;
    ELSIF ( var = 4 ) THEN
      result:= v2||v3||v1;
    ELSIF ( var = 5 ) THEN
      result:= v3||v1||v2;
    ELSIF ( var = 6 ) THEN
      result:= v3||v2||v1;
    END IF;
    d := SUBSTR(result, 5, 2);
    m := SUBSTR(result, 3, 2);
  ELSIF ( LENGTH( v1 || v2 || v3 ) = 8 ) THEN
    IF ( LENGTH(v1) = 4 ) THEN
      IF ( var = 1 ) THEN
        result:= v1||v2||v3;
      ELSIF ( var = 2 ) THEN
        result:= v1||v3||v2;
      END IF;
    ELSIF ( LENGTH(v2) = 4 ) THEN
      IF ( var = 1 ) THEN
        result:= v2||v1||v3;
      ELSIF ( var = 2 ) THEN
        result:= v2||v3||v1;
      END IF;
    ELSIF ( LENGTH(v3) = 4 ) THEN
      IF ( var = 1 ) THEN
        result:= v3||v1||v2;
      ELSIF ( var = 2 ) THEN
        result:= v3||v2||v1;
      END IF;
    END IF;
    d := SUBSTR(result, 7, 2);
    m := SUBSTR(result, 5, 2);
    y4 := SUBSTR(result, 0, 4);
  END IF;
  IF ( (d = '00') OR (d > '31') OR (m = '00') OR (m > 12) OR (y4 < '1000' ) OR (y4 > '2100' ) ) THEN
    result := NULL;
  END IF; 
  RETURN result;
END FDT_Build_Date;
/


--
-- FROM_HTML  (Function) 
--
CREATE OR REPLACE FUNCTION FROM_HTML (
    ival VARCHAR2
  )
RETURN
VARCHAR2
IS
  idx   BINARY_INTEGER := 0;
  sidx  BINARY_INTEGER := 1;
  pos   BINARY_INTEGER := 1;
  crlf  CHAR(2 CHAR) := CHR(13) || CHR(10);
  crdel CHAR(2 CHAR) := '  '; 
  sOpn  CHAR(1 CHAR) := '<';
  sCls  CHAR(1 CHAR) := '>';
  delim VARCHAR2(1 CHAR) := '';
  tag   VARCHAR2(4000 CHAR) := NULL;
  res   VARCHAR2(4000 CHAR) := NULL;
  val   VARCHAR2(4000 CHAR) := ival;
BEGIN
  IF ( val IS NOT NULL ) THEN
    LOOP
      idx := INSTR( val, sOpn, sidx );
      IF ( idx > 0 ) THEN
        res := NVL( res, '' ) || delim || TRANSLATE( SUBSTR( val, sidx, idx - sidx ), crlf, crdel );
        sidx := idx + 1;
        idx := INSTR( val, sCls, sidx );
        delim := '';
        IF ( idx > 0 ) THEN
          tag := REPLACE( REPLACE( UPPER( SUBSTR( val, sidx, idx - sidx ) ), ' /', '/' ), '/ ', '/' );
          sidx := idx + 1;
          IF ( tag IN ('BR','BR/', 'P','/P', 'P/') ) THEN
            delim:=CHR(10);
	  ELSIF ( tag = 'PRE' ) THEN
            crdel:=' ' || CHR(10);
          ELSIF ( tag = '/PRE' ) THEN
            crdel:='  ';
          END IF;
        END IF;
      ELSE
        res := NVL( res, '' ) || delim || TRANSLATE( SUBSTR( val, sidx ), crlf, crdel );
      END IF;
      IF ( idx <= 0 ) THEN
      	EXIT;
      END IF;
    END LOOP;
    val := res;
    res := NULL;
    sidx := 1;
    sOpn := '&';
    sCls := ';';
    delim := '';
    LOOP
      idx := INSTR( val, sOpn, sidx );
      IF ( idx > 0 ) THEN
        res := NVL( res, '' ) || delim || SUBSTR( val, sidx, idx - sidx );
        delim := '';
        sidx := idx + 1;
        idx := INSTR( val, sCls, sidx );
        IF ( idx > 0 ) THEN
          tag := UPPER( SUBSTR( val, sidx, idx - sidx ) );
          sidx := idx + 1;
          IF ( tag = 'AMP' ) THEN
            delim:='&';
	  ELSIF ( tag = 'LT' ) THEN
            delim:='<';
          ELSIF ( tag = 'GT' ) THEN
            delim:='>';
          END IF;
        END IF;
      ELSE
        res := NVL( res, '' ) || delim || SUBSTR( val, sidx );
        delim := '';
      END IF;
      IF ( idx <= 0 ) THEN
      	EXIT;
      END IF;
    END LOOP;
  END IF;
  RETURN res || delim;
END FROM_HTML;
/


--
-- TO_HTML  (Function) 
--
CREATE OR REPLACE FUNCTION TO_HTML (
    ival VARCHAR2,
    br VARCHAR2 := '<br>'
  )
RETURN
VARCHAR2
IS
BEGIN
  RETURN REPLACE( REPLACE( REPLACE( REPLACE( REPLACE( ival, '&', '&'||'amp;' ), '<', '&'||'lt;' ), '>', '&'||'gt;' ), CHR(10), br ), CHR(13), '' );
END TO_HTML;
/


--
-- WEBM_CHAINS_MONTH_EACH  (View) 
--
CREATE OR REPLACE FORCE VIEW WEBM_CHAINS_MONTH_EACH
(CHAIN, EX_MONTH, EX_FIRST, EX_LAST, EX_NUM, 
 EX_NUM_DAY, EX_MANUAL, EX_AVG_S, EX_MAX_S, EX_MIN_S, 
 EX_AVG_INT_M, HOST1, HOST2, HOST_NUM)
AS 
SELECT
    data1						AS chain,
    vmts_M						AS ex_Month,
    MIN( vmts )						AS ex_First,
    MAX( vmts )						AS ex_Last,
    COUNT(*)						AS ex_Num,
    ROUND(
      ( COUNT(*) - COUNT( DISTINCT data1 ) ) /
      NULLIF( MAX( vmts ) - MIN( vmts ), 0 ),
      1
    )							AS ex_Num_Day,
    SUM( DECODE( SUBSTR( data2, 1, 3 ), '(m)', 1, 0) )	AS ex_Manual,
    ROUND( SUM( data_Number3 ) / COUNT(*) / 1000, 1)	AS ex_Avg_S,
    ROUND( MAX( data_Number3 ) / 1000, 1)		AS ex_Max_S,
    ROUND( MIN( data_Number3 ) / 1000, 1)		AS ex_Min_S,
    ROUND(
      ( MAX( vmts ) - MIN( vmts ) ) * 24 * 60 /
      NULLIF( COUNT(*) - 1, 0 ),
      1
    )							AS ex_Avg_Int_M,
    MIN( machine )					AS host1,
    MAX( machine )					AS host2,
    COUNT( DISTINCT machine )				AS host_Num
  FROM
    Webmetrix
  WHERE
    ( component		= 'CHAIN'	) AND
    ( actiontype	= 'RUN'		)
  GROUP BY
    data1, vmts_M
  ORDER BY
    1, 2 DESC;


--
-- WEBM_CHAINS_MONTH_ALL  (View) 
--
CREATE OR REPLACE FORCE VIEW WEBM_CHAINS_MONTH_ALL
(EX_MONTH, PROC1, PROC2, PROC_NUM, EX_FIRST, 
 EX_LAST, EX_NUM, EX_NUM_DAY, EX_MANUAL, EX_AVG_S, 
 EX_MAX_S, EX_MIN_S, EX_AVG_INT_M, HOST1, HOST2, 
 HOST_NUM)
AS 
SELECT
    vmts_M						AS ex_Month,
    MIN( data1 )					AS proc1,
    MAX( data1 )					AS proc2,
    COUNT( DISTINCT data1 )				AS proc_num,
    MIN( vmts )						AS ex_First,
    MAX( vmts )						AS ex_Last,
    COUNT(*)						AS ex_Num,
    ROUND(
      ( COUNT(*) - COUNT( DISTINCT data1 ) ) /
      NULLIF( MAX( vmts ) - MIN( vmts ), 0 ),
      1
    )							AS ex_Num_Day,
    SUM( DECODE( SUBSTR( data2, 1, 3 ), '(m)', 1, 0) )	AS ex_Manual,
    ROUND( SUM( data_Number3 ) / COUNT(*) / 1000, 1)	AS ex_Avg_S,
    ROUND( MAX( data_Number3 ) / 1000, 1)		AS ex_Max_S,
    ROUND( MIN( data_Number3 ) / 1000, 1)		AS ex_Min_S,
    ROUND(
      ( MAX( vmts ) - MIN( vmts ) ) * 24 * 60 /
      NULLIF( COUNT(*) - 1, 0 ),
      1
    )							AS ex_Avg_Int_M,
    MIN( machine )					AS host1,
    MAX( machine )					AS host2,
    COUNT( DISTINCT machine )				AS host_Num
  FROM
    Webmetrix
  WHERE
    ( component		= 'CHAIN'	) AND
    ( actiontype	= 'RUN'		)
  GROUP BY
    vmts_M
  ORDER BY
    1 desc;


--
-- WEBM_CHAINS_DAY_EACH  (View) 
--
CREATE OR REPLACE FORCE VIEW WEBM_CHAINS_DAY_EACH
(CHAIN, EX_DAY, EX_FIRST, EX_LAST, EX_NUM, 
 EX_NUM_DAY, EX_MANUAL, EX_AVG_S, EX_MAX_S, EX_MIN_S, 
 EX_AVG_INT_M, HOST1, HOST2, HOST_NUM)
AS 
SELECT
    data1						AS chain,
    TRUNC( vmts )					AS ex_Day,
    MIN( vmts )						AS ex_First,
    MAX( vmts )						AS ex_Last,
    COUNT(*)						AS ex_Num,
    ROUND(
      ( COUNT(*) - COUNT( DISTINCT data1 ) ) /
      NULLIF( MAX( vmts ) - MIN( vmts ), 0 ),
      1
    )							AS ex_Num_Day,
    SUM( DECODE( SUBSTR( data2, 1, 3 ), '(m)', 1, 0) )	AS ex_Manual,
    ROUND( SUM( data_Number3 ) / COUNT(*) / 1000, 1)	AS ex_Avg_S,
    ROUND( MAX( data_Number3 ) / 1000, 1)		AS ex_Max_S,
    ROUND( MIN( data_Number3 ) / 1000, 1)		AS ex_Min_S,
    ROUND(
      ( MAX( vmts ) - MIN( vmts ) ) * 24 * 60 /
      NULLIF( COUNT(*) - 1, 0 ),
      1
    )							AS ex_Avg_Int_M,
    MIN( machine )					AS host1,
    MAX( machine )					AS host2,
    COUNT( DISTINCT machine )				AS host_Num
  FROM
    Webmetrix
  WHERE
    ( component		= 'CHAIN'	) AND
    ( actiontype	= 'RUN'		) AND
    ( vmts		>= TRUNC( ADD_MONTHS( SYSDATE, -1 ), 'MONTH' ) )
  GROUP BY
    data1, TRUNC( vmts )
  ORDER BY
    1, 2 DESC;


--
-- WEBM_CHAINS_DAY_ALL  (View) 
--
CREATE OR REPLACE FORCE VIEW WEBM_CHAINS_DAY_ALL
(EX_DAY, PROC1, PROC2, PROC_NUM, EX_FIRST, 
 EX_LAST, EX_NUM, EX_NUM_DAY, EX_MANUAL, EX_AVG_S, 
 EX_MAX_S, EX_MIN_S, EX_AVG_INT_M, HOST1, HOST2, 
 HOST_NUM)
AS 
SELECT
    TRUNC( vmts )					AS ex_Day,
    MIN( data1 )					AS proc1,
    MAX( data1 )					AS proc2,
    COUNT( DISTINCT data1 )				AS proc_num,
    MIN( vmts )						AS ex_First,
    MAX( vmts )						AS ex_Last,
    COUNT(*)						AS ex_Num,
    ROUND(
      ( COUNT(*) - COUNT( DISTINCT data1 ) ) /
      NULLIF( MAX( vmts ) - MIN( vmts ), 0 ),
      1
    )							AS ex_Num_Day,
    SUM( DECODE( SUBSTR( data2, 1, 3 ), '(m)', 1, 0) )	AS ex_Manual,
    ROUND( SUM( data_Number3 ) / COUNT(*) / 1000, 1)	AS ex_Avg_S,
    ROUND( MAX( data_Number3 ) / 1000, 1)		AS ex_Max_S,
    ROUND( MIN( data_Number3 ) / 1000, 1)		AS ex_Min_S,
    ROUND(
      ( MAX( vmts ) - MIN( vmts ) ) * 24 * 60 /
      NULLIF( COUNT(*) - 1, 0 ),
      1
    )							AS ex_Avg_Int_M,
    MIN( machine )					AS host1,
    MAX( machine )					AS host2,
    COUNT( DISTINCT machine )				AS host_Num
  FROM
    Webmetrix
  WHERE
    ( component		= 'CHAIN'	) AND
    ( actiontype	= 'RUN'		) AND
    ( vmts		>= TRUNC( ADD_MONTHS( SYSDATE, -1 ), 'MONTH' ) )
  GROUP BY
    TRUNC( vmts )
  ORDER BY
    1 desc;


--
-- NAVIGATION_CDT  (Trigger) 
--
CREATE OR REPLACE TRIGGER NAVIGATION_CDT
BEFORE INSERT OR UPDATE ON
  NAVIGATION
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :NEW.created := get_Normalized_Time();
  END IF;
  IF (UPDATING) THEN
    :NEW.created   := :OLD.created;
    :NEW.createdBy := :OLD.createdBy;
    :NEW.createdOn := :OLD.createdOn;
  END IF;
  :NEW.changed := get_Normalized_Time();
  IF (:NEW.userChanged > (get_Normalized_Time() + 365750) OR INSERTING) THEN
    :NEW.userChanged := :NEW.changed;
  ELSE
    :NEW.userChanged := :OLD.userChanged;
  END IF;
  IF (INSERTING AND :NEW.object_Id IS NULL) THEN
    :NEW.object_Id := Get_Next_Oid();
  ELSIF (UPDATING) THEN
    :NEW.object_Id := :OLD.object_Id;
  END IF;
END NAVIGATION_CDT;
/


--
-- TRANSLATIONS_CDT  (Trigger) 
--
CREATE OR REPLACE TRIGGER TRANSLATIONS_CDT
BEFORE INSERT OR UPDATE ON
  TRANSLATIONS
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :NEW.created := get_Normalized_Time();
  END IF;
  IF (UPDATING) THEN
    :NEW.created   := :OLD.created;
    :NEW.createdBy := :OLD.createdBy;
    :NEW.createdOn := :OLD.createdOn;
  END IF;
  :NEW.changed := get_Normalized_Time();
  IF (:NEW.userChanged > (get_Normalized_Time() + 365750) OR INSERTING) THEN
    :NEW.userChanged := :NEW.changed;
  ELSE
    :NEW.userChanged := :OLD.userChanged;
  END IF;
  IF (INSERTING AND :NEW.object_Id IS NULL) THEN
    :NEW.object_Id := Get_Next_Oid();
  ELSIF (UPDATING) THEN
    :NEW.object_Id := :OLD.object_Id;
  END IF;
END TRANSLATIONS_CDT;
/


--
-- SESSION_DATA_CHK_CDT  (Trigger) 
--
CREATE OR REPLACE TRIGGER SESSION_DATA_CHK_CDT
BEFORE INSERT OR UPDATE ON
  SESSION_DATA
FOR EACH ROW
DECLARE
--  declare variables...
  object_Id Session_Data.object_Id%TYPE := NULL;
BEGIN
--  check for integrity
  IF (INSERTING) THEN
    object_Id := SDT_Get_OID( :new.Id, :new.Component );
    IF ( object_Id <> :new.object_Id ) THEN
      RAISE_APPLICATION_ERROR( -20888,
        'Duplicate entry exception <Session_Data:' || NVL(TO_CHAR( object_Id ),'<NULL>') || '>:' ||
        'Session_Data with object_Id ' || NVL(TO_CHAR(:new.object_Id),'<NULL>') ||
        ' rejected, entry for same Id ' || NVL(:new.id,'<NULL>') ||
        ' and Component ' || NVL(:new.component,'<NULL>') || ' already exists!'
      );
    END IF;
  END IF;

--  protect the create columns
  IF (INSERTING) THEN
    :NEW.created := get_Normalized_Time();
  END IF;
  IF (UPDATING) THEN
    :NEW.created   := :OLD.created;
--  :NEW.createdBy := :OLD.createdBy;
--  :NEW.createdOn := :OLD.createdOn;
  END IF;
  :NEW.changed := get_Normalized_Time();
--IF (:NEW.userChanged > (get_Normalized_Time() + 365750) OR INSERTING) THEN
--  :NEW.userChanged := :NEW.changed;
--ELSE
--  :NEW.userChanged := :OLD.userChanged;
--END IF;
  IF (INSERTING AND :NEW.object_Id IS NULL) THEN
--  :NEW.object_Id := Get_Next_Oid();
NULL;
  ELSIF (UPDATING) THEN
    :NEW.object_Id := :OLD.object_Id;
  END IF;
  
  -- FIXME: requires additional update of FDT-SQL-TEMPLATE to use get_Normalized_Time() instead of SYSDATE!!!
  :new.endOfLife := :new.changed + (:new.timeToLive/86400);
END SESSION_DATA_CHK_CDT;
/


--
-- PERFORMANCE_CDT  (Trigger) 
--
CREATE OR REPLACE TRIGGER PERFORMANCE_CDT
BEFORE INSERT OR UPDATE ON
  PERFORMANCE
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :NEW.created := get_Normalized_Time();
  END IF;
  IF (UPDATING) THEN
    :NEW.created   := :OLD.created;
--  :NEW.createdBy := :OLD.createdBy;
--  :NEW.createdOn := :OLD.createdOn;
  END IF;
--:NEW.changed := get_Normalized_Time();
--IF (:NEW.userChanged > (get_Normalized_Time() + 365750) OR INSERTING) THEN
--  :NEW.userChanged := :NEW.changed;
--ELSE
--  :NEW.userChanged := :OLD.userChanged;
--END IF;
  IF (INSERTING AND :NEW.object_Id IS NULL) THEN
    :NEW.object_Id := Get_Next_Oid();
  ELSIF (UPDATING) THEN
    :NEW.object_Id := :OLD.object_Id;
  END IF;
END PERFORMANCE_CDT;
/


--
-- WEBMETRIX_CDT  (Trigger) 
--
CREATE OR REPLACE TRIGGER WEBMETRIX_CDT
BEFORE INSERT OR UPDATE ON
  WEBMETRIX
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :NEW.created := Get_Normalized_Time();
    :NEW.created_m := TO_CHAR( :NEW.created, 'YYYY-MM' );
  END IF;
  IF (UPDATING) THEN
    :NEW.created   := :OLD.created;
    :NEW.created_m := :OLD.created_m;
--  :NEW.createdBy := :OLD.createdBy;
--  :NEW.createdOn := :OLD.createdOn;
  END IF;
--:NEW.changed := get_Normalized_Time();
--IF (:NEW.userChanged > (get_Normalized_Time() + 365750) OR INSERTING) THEN
--  :NEW.userChanged := :NEW.changed;
--ELSE
--  :NEW.userChanged := :OLD.userChanged;
--END IF;
  IF (INSERTING AND :NEW.object_Id IS NULL) THEN
    :NEW.object_Id := Get_Next_Oid();
  ELSIF (UPDATING) THEN
    :NEW.object_Id := :OLD.object_Id;
  END IF;
END WEBMETRIX_CDT;
/


--
-- WEBMETRIX_MON_BI  (Trigger) 
--
CREATE OR REPLACE TRIGGER WEBMETRIX_MON_BI
BEFORE INSERT ON
  WEBMETRIX
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
  :NEW.created_m := TO_CHAR( :NEW.created, 'YYYY-MM' );
  :NEW.vmts_m    := TO_CHAR( :NEW.vmts,    'YYYY-MM' );
END WEBMETRIX_MON_BI;
/


--
-- TEXTTYPE_REG  (Table) 
--
CREATE TABLE TEXTTYPE_REG
(
  TEXTTYPE_ID  NUMBER(4)                        NOT NULL,
  SCHEMANAME   VARCHAR2(20 CHAR)                NOT NULL,
  TABLENAME    VARCHAR2(30 CHAR)                NOT NULL,
  COLUMNNAME   VARCHAR2(30 CHAR)                NOT NULL,
  COLUMNLEN    NUMBER                           NOT NULL,
  IS_CODE      NUMBER(1)                        NOT NULL,
  CREATED      DATE                             DEFAULT SYSDATE               NOT NULL
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
-- WEBINFO  (Table) 
--
CREATE TABLE WEBINFO
(
  OBJECT_ID              NUMBER(27)             NOT NULL,
  COMPONENT              VARCHAR2(30 CHAR)      NOT NULL,
  COMP_TYPE              VARCHAR2(30 CHAR)      NOT NULL,
  DESCRIPTION            VARCHAR2(500 CHAR),
  DISPLAY_POS            NUMBER(2)              NOT NULL,
  EMAIL_TO               VARCHAR2(1000 CHAR),
  EMAIL_NOT_AVAIL_BODY   VARCHAR2(1000 CHAR),
  EMAIL_AVAIL_BODY       VARCHAR2(1000 CHAR),
  ISACTIVE_FLG           NUMBER(1)              DEFAULT 0                     NOT NULL,
  MONITOR_FLG            NUMBER(1)              DEFAULT 0                     NOT NULL,
  NEWS_FLG               NUMBER(1)              DEFAULT 0                     NOT NULL,
  NEWS_ID                NUMBER(27),
  WEBMETRIX_FLG          NUMBER(1)              DEFAULT 0                     NOT NULL,
  ISAVAILABLE_FLG        NUMBER(1)              DEFAULT 1                     NOT NULL,
  LAST_AVAIL_CHECK       DATE,
  LAST_AVAIL_CHANGE      DATE,
  TASK_TYPE              VARCHAR2(30 CHAR)      DEFAULT 'JAVA'                NOT NULL,
  TASK_EXISTS            VARCHAR2(200 CHAR)     DEFAULT NULL,
  TASK_OUTPUT            VARCHAR2(200 CHAR)     DEFAULT NULL,
  CREATEDBY              VARCHAR2(10 CHAR),
  CREATED                DATE                   NOT NULL,
  CHANGEDBY              VARCHAR2(10 CHAR),
  CHANGED                DATE                   NOT NULL,
  EMAIL_STATIC_FLG       NUMBER(1)              DEFAULT 0                     NOT NULL,
  EMAIL_UM_FLG           NUMBER(1)              DEFAULT 0                     NOT NULL,
  SEND_NEWS_FLG          NUMBER(1)              DEFAULT 0                     NOT NULL,
  SEND_EMAIL_UM_FLG      NUMBER(1)              DEFAULT 0                     NOT NULL,
  SEND_EMAIL_STATIC_FLG  NUMBER(1)              DEFAULT 0                     NOT NULL,
  USERCHANGED            DATE,
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

COMMENT ON COLUMN WEBINFO.COMP_TYPE IS 'FK to COMPONENT_TYPE';

COMMENT ON COLUMN WEBINFO.SEND_NEWS_FLG IS 'set by trigger, if true msg task send news';

COMMENT ON COLUMN WEBINFO.SEND_EMAIL_UM_FLG IS 'set by trigger, if true msg task send um emails';

COMMENT ON COLUMN WEBINFO.SEND_EMAIL_STATIC_FLG IS 'set by trigger, if true msg task send static emails';


--
-- NEWS  (Table) 
--
CREATE TABLE NEWS
(
  OBJECT_ID               NUMBER(27)            NOT NULL,
  VALID_FROM              DATE                  NOT NULL,
  VALID_TO                DATE                  NOT NULL,
  CREATED                 DATE,
  CHANGED                 DATE,
  CREATEDBY               VARCHAR2(10 CHAR),
  CHANGEDBY               VARCHAR2(10 CHAR),
  ISMANDATORY             NUMBER(1)             DEFAULT 0                     NOT NULL,
  OBJECTSTATEINTERNAL_ID  NUMBER(2)             DEFAULT 1                     NOT NULL,
  OBJECTSTATE_ID          NUMBER(2)             DEFAULT 1                     NOT NULL,
  TOPIC                   VARCHAR2(160 CHAR),
  SEGMENT_ID              NUMBER(4)             NOT NULL,
  USERCHANGED             DATE,
  CREATEDON               VARCHAR2(100 CHAR),
  CHANGEDON               VARCHAR2(100 CHAR),
  LIMITED_TO              VARCHAR2(30 CHAR)
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
-- DISPLAYTEXT  (Table) 
--
CREATE TABLE DISPLAYTEXT
(
  TEXT_ID            NUMBER(4)                  NOT NULL,
  TEXTTYPE_ID        NUMBER(4)                  NOT NULL,
  LANGUAGE_ISO_CODE  VARCHAR2(2 CHAR)           NOT NULL,
  TEXT               VARCHAR2(80 CHAR),
  DESCRIPTION        VARCHAR2(200 CHAR),
  CODE               VARCHAR2(30 CHAR),
  SEGMENT_ID         NUMBER(4),
  IS_ACTIVE          NUMBER(1)                  DEFAULT 1                     NOT NULL,
  ORDERBY            NUMBER(4),
  TYPE_NAME          VARCHAR2(30 CHAR),
  PARENT_TEXT_ID     NUMBER(4)
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
-- FK_NEWS_OBJSTATE_INT_IDX  (Index) 
--
CREATE INDEX FK_NEWS_OBJSTATE_INT_IDX ON NEWS
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
-- PK_DISPLAYTEXT  (Index) 
--
CREATE UNIQUE INDEX PK_DISPLAYTEXT ON DISPLAYTEXT
(TEXT_ID, TEXTTYPE_ID, LANGUAGE_ISO_CODE)
TABLESPACE DOC41WEB_IDX
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
-- PK_NEWS  (Index) 
--
CREATE UNIQUE INDEX PK_NEWS ON NEWS
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
-- PK_WEBINFO  (Index) 
--
CREATE UNIQUE INDEX PK_WEBINFO ON WEBINFO
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
-- TEXTTYPEREG_TEXTTYPE_FK_IDX  (Index) 
--
CREATE INDEX TEXTTYPEREG_TEXTTYPE_FK_IDX ON TEXTTYPE_REG
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
-- TEXTTYPE_REG_PK_IDX  (Index) 
--
CREATE UNIQUE INDEX TEXTTYPE_REG_PK_IDX ON TEXTTYPE_REG
(TEXTTYPE_ID, SCHEMANAME, TABLENAME, COLUMNNAME)
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
-- CDT  (Procedure) 
--
CREATE OR REPLACE PROCEDURE
  CDT (
    -- OBLIGATORY
    is_Insert			IN boolean,		-- INSERTING?  (or UPDATING = false)
    dest_OID_Schema_Name	IN VARCHAR2,		-- SCHEMA of Dest-Table for requesting OIDs, obligatory for later use
    dest_OID_Table_Name		IN VARCHAR2,		-- NAME of Dest-Table for requesting OIDs, obligatory for later use
    old_Object_Id		IN NUMBER,		-- OBJECT_ID (or the internal serial) used to identify this dataset
    new_Object_Id		IN OUT NUMBER,		-- ...autodefined if not null, change protected
    -- optional set 1: created/changed
    old_Created			IN DATE := NULL,	-- DATE, this row was created at the DB
    new_Created			IN OUT DATE,		-- ... auto defined if not predef, change protected 
    old_Changed			IN DATE := NULL,	-- DATE, this row was last touched at the DB, NOT correlated to Changed BY
    new_Changed			IN OUT DATE,		-- ... auto defined / changed on any touch of row
    -- optional set 1.1: userchanged
    old_UserChanged		IN DATE := NULL,	-- DATE, this row was last touched at the application, IS correlated to Changed BY
    new_UserChanged		IN OUT DATE,		-- ... auto defined / changed on application request (inc by > 1100 YEARS)
    -- optional set 1.2: createdBy/changedBy
    old_CreatedBy		IN VARCHAR2 := NULL,	-- CWID of User (or Pseudo-CWID of process) this row is created
    new_CreatedBy		IN OUT VARCHAR2,	-- ... change protected
    old_ChangedBy		IN VARCHAR2 := NULL,	-- CWID of User (or Pseudo-CWID of process) this row is touched by the APPLICATION
    new_ChangedBy		IN OUT VARCHAR2,	-- ... if changed, enforces change of UserChanged, too (additionaly, fallback)
    -- optional set 1.2.1: createdOn/changedOn
    old_CreatedOn		IN VARCHAR2 := NULL,	-- SystemIdent of Application Instance, the row was created on, autofilled by UserChangeableDCs
    new_CreatedOn		IN OUT VARCHAR2,	-- ... change protected
    old_ChangedOn		IN VARCHAR2 := NULL,	-- SystemIdent of Application Instance, the row was last touched at, autofilled by UserChangeableDCs
    new_ChangedOn		IN OUT VARCHAR2,	-- ... curently no action, for future use
    -- optional set 2: objectNo 
    old_Object_No		IN VARCHAR2 := NULL,	-- external Key (SK) of this row to other applications 
    new_Object_No		IN OUT VARCHAR2,	-- ... defined if enabled, otherwise curently no action, for future use
    object_No_Allow_Chg		IN boolean := false,	-- are changes of a once defined object_No allowed? false: no way, true: via NULL only (keep in mind FK references, violation causes exception)
    new_Object_No_Auto_Prefix	IN VARCHAR2 := NULL,	-- ENABLES auto Object_No generation on INSERTs, if No is NULL, prefix must be 3 CHARS EXACTLY
    new_Object_No_Auto_NumLen	IN NUMBER   := 1,	-- defines the MIN len to lpad the numeric part to.
    new_Object_No_Auto_Sys	IN VARCHAR2 := NULL,	-- spec. the Sys to set, if auto Object_No generation is used
    -- optional set 2.1 / 3: objectSys
    old_Object_Sys		IN VARCHAR2 := NULL,	-- source System, this rows SK was defined by (if n/a, the row was defined by)
    new_Object_Sys		IN OUT VARCHAR2		-- ... curently no action, for future use
  )
IS
BEGIN

  new_Changed := get_Normalized_Time();

  IF (is_Insert) THEN
    IF (new_Object_Id IS NULL) THEN
      new_Object_Id := get_Next_Oid( dest_OID_Schema_Name, dest_OID_Table_Name );
    END IF;
    IF (new_Created IS NULL) THEN -- allow take over of 
      new_Created := new_Changed;
    END IF;
  ELSE
    new_Object_Id := old_Object_Id;
    new_Created   := old_Created;
    new_CreatedBy := old_CreatedBy;
    new_ChangedOn := old_ChangedOn;
  END IF;
  
  IF (new_UserChanged > (old_UserChanged + 365750) OR is_Insert OR (NVL(old_ChangedBy, '%') <> NVL(new_ChangedBy, '%'))) THEN
    new_UserChanged := new_Changed;
  ELSE
    new_UserChanged := old_UserChanged;
  END IF;
  
  IF ( is_Insert AND (new_Object_No IS NULL) AND (new_Object_No_Auto_Prefix IS NOT NULL) ) THEN
    new_Object_No := new_Object_No_Auto_Prefix || TRIM( TO_CHAR( new_Object_Id, SUBSTR( '99999999999999999999999999999999999999999999999999', 1, 50 - new_Object_No_Auto_NumLen ) || SUBSTR( '00000000000000000000000000000000000000000000000000', 1, new_Object_No_Auto_NumLen ) ) );
    IF ( (new_Object_No_Auto_Sys IS NOT NULL) AND (new_Object_Sys IS NULL) ) THEN
      new_Object_Sys := new_Object_No_Auto_Sys;
    END IF;
  ELSIF ( (old_Object_No IS NOT NULL) AND ((old_Object_No <> new_Object_No) OR ((new_Object_No IS NULL) AND NOT object_No_Allow_Chg)) ) THEN
    RAISE_APPLICATION_ERROR( -20112,
      'Object #' || NVL( TO_CHAR(new_Object_Id), '<null>' ) || ': Illegal (forbidden) change of secondary: ' ||
      NVL( old_Object_No, '<NULL>' ) || ' => ' || NVL( new_Object_No, '<NULL>' ) || ', rejected!'
    );
  END IF;

END CDT;
/


--
-- DT_CHECK_CODE_FK  (Procedure) 
--
CREATE OR REPLACE PROCEDURE
  DT_Check_Code_FK (
    new_Code         DisplayText.Code%TYPE,
    new_TextType_Id  DisplayText.TextType_Id%TYPE,
    trigger_Name     VARCHAR2,
    table_Column     VARCHAR2
  )
IS
-- Update Allowed_BillTo_ShipTo, add a BillTo/ShipTo combination of an Order_Catalog entry
  first_Code DisplayText.Code%TYPE;
BEGIN
  IF (new_Code IS NOT NULL) THEN
    SELECT
      code
    INTO
      first_Code
    FROM
      DisplayText
    WHERE
      ( code        =  new_Code        ) AND
      ( textType_Id =  new_TextType_Id ) AND
      ( ROWNUM      <= 1               )
    ;
  END IF;
EXCEPTION
WHEN NO_DATA_FOUND THEN
  RAISE_APPLICATION_ERROR( -20800,
    'Integry constraint trigger ('||NVL(trigger_Name,'???')||
    ') violated - parent key not found on table DisplayText for '||
    NVL(table_Column,'???')||', Text_Id='||NVL(new_Code,'<NULL>')||
    ', textType_Id='||NVL(TO_CHAR(new_TextType_Id),'<NULL>')||'!'
  );
  --
END;
/


--
-- DT_CHECK_FK  (Procedure) 
--
CREATE OR REPLACE PROCEDURE
  DT_Check_FK (
    new_Text_Id      DisplayText.Text_Id%TYPE,
    new_TextType_Id  DisplayText.TextType_Id%TYPE,
    trigger_Name     VARCHAR2,
    table_Column     VARCHAR2
  )
IS
-- Update Allowed_BillTo_ShipTo, add a BillTo/ShipTo combination of an Order_Catalog entry
  first_Text_Id DisplayText.Text_Id%TYPE;
BEGIN
  IF (new_Text_Id IS NOT NULL) THEN
    SELECT
      text_Id
    INTO
      first_Text_Id
    FROM
      DisplayText
    WHERE
      ( text_Id     =  new_Text_Id     ) AND
      ( textType_Id =  new_TextType_Id ) AND
      ( ROWNUM      <= 1               )
    ;
  END IF;
EXCEPTION
WHEN NO_DATA_FOUND THEN
  RAISE_APPLICATION_ERROR( -20800,
    'Integry constraint trigger ('||NVL(trigger_Name,'???')||
    ') violated - parent key not found on table DisplayText for '||
    NVL(table_Column,'???')||', Text_Id='||NVL(TO_CHAR(new_Text_Id),'<NULL>')||
    ', textType_Id='||NVL(TO_CHAR(new_TextType_Id),'<NULL>')||'!'
  );
  --
END;
/


--
-- DT_TEXTTYPE_REG  (Procedure) 
--
CREATE OR REPLACE PROCEDURE
  DT_TextType_Reg(
    new_TextType_Id	TextType_Reg.TextType_Id%TYPE,
    new_Schemaname	TextType_Reg.SchemaName%TYPE,
    new_Tablename	TextType_Reg.TableName%TYPE,
    new_Columnname	TextType_Reg.ColumnName%TYPE,
    new_Is_Code		TextType_Reg.is_Code%TYPE
  )
IS
  c TextType_Reg.is_Code%TYPE;
  len NUMBER;
PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN
  SELECT
    MIN(is_Code)
  INTO
    c
  FROM
    TextType_Reg
  WHERE
    ( textType_Id	= new_TextType_Id	) AND
    ( schemaName	= new_SchemaName	) AND
    ( tableName		= new_TableName		) AND
    ( columnName	= new_ColumnName	)
  ;
  IF ( c IS NULL ) THEN
    SELECT
      MIN( DECODE( new_Is_Code, 1, char_Length, 0, data_Precision ) )
    INTO
      len
    FROM
      all_Tab_Cols
    WHERE
      ( owner			= UPPER( new_SchemaName	)	) AND
      ( table_Name		= UPPER( new_TableName	)	) AND
      ( column_Name		= UPPER( new_ColumnName	)	) AND
      ( ( ( new_Is_Code		= 1		) AND
          ( data_Type		= 'VARCHAR2'	) AND
          ( char_Length		<= 30		)	) OR
        ( ( new_Is_Code		= 0		) AND
          ( data_Type		= 'NUMBER'	) AND
          ( data_Precision	<= 4		) AND
          ( data_Scale		= 0		)	)	)
    ;
    IF ( len IS NOT NULL ) THEN
      INSERT INTO
        TextType_Reg
        (textType_Id, schemaName, tableName, columnName, columnLen, is_Code)
      VALUES
        (new_TextType_Id, new_SchemaName, new_TableName, new_ColumnName, len, new_Is_Code)
      ;
    ELSE
      RAISE_APPLICATION_ERROR( -20488, 'Bad DPT-Table-Column-Reference, Schema, Table or Column does not exist,' ||
        'Table is not SELECT granted to FDT Schema OR Column Type does not match required Type (code VARCHAR2(30 CHAR) OR id NUMBER(4)): ' ||
        new_Schemaname || '.' || new_TableName || '.' || new_ColumnName ||
        ' - TextType: ' || TO_CHAR( new_TextType_Id ) || ', is_Code: ' || TO_CHAR(new_Is_Code) || ' !!!' );
      END IF;
  ELSIF (c <> new_Is_Code) THEN
    RAISE_APPLICATION_ERROR( -20489, 'Illegal change of Column-DPT-Type in Registry: Code/ID: ' ||
      new_Schemaname || '.' || new_TableName || '.' || new_ColumnName ||
      ' - TextType: ' || TO_CHAR( new_TextType_Id ) || ' (' || TO_CHAR(c) || '->' || TO_CHAR(new_Is_Code) || ') !!!' );
  END IF;
  COMMIT;
--EXCEPTION
--WHEN NO_DATA_FOUND THEN
--  NULL;
END DT_TextType_Reg;
/


--
-- DT_LOOKUP_TEXT_BY_CODE  (Function) 
--
CREATE OR REPLACE FUNCTION
  DT_LookUp_Text_By_Code (
    new_Code          DISPLAYTEXT.code%TYPE,
    new_TextType_Id   DISPLAYTEXT.textType_Id%TYPE,
    new_Lang_ISO_Code DISPLAYTEXT.language_ISO_Code%TYPE,
    cur_Idx           NUMBER := 0,  -- should be ROWNUM on paging, -1 to disable, 0 to enable if no paging
    from_Idx          NUMBER := -1, -- should be page start idx on paging,        -1 if no paging
    to_Idx            NUMBER := -1  -- should be page end idx on paging,          -1 if no paging 
    -- examples: row, min, max always >= 0, -1 is equivalent to any number <0, * means ANY number at all
    -- -1, -1, -1      disabled
    -- row, -1, -1     enabled                        (no paging)
    -- -1, min, max    disabled                       (paging, but column disabled)
    -- row, min, max   conditioned min <= row <= max  (pageging window)
    -- row, -1, max    conditioned row <= max
    -- row, min, -1    conditioned min <= row 
  )
RETURN
  DISPLAYTEXT.text%TYPE
IS
  res DISPLAYTEXT.text%TYPE := NULL;
BEGIN
  IF ( (cur_Idx < 0) OR (cur_Idx < from_Idx) OR ((cur_Idx > to_Idx) AND (to_Idx > -1)) ) THEN
    RETURN NULL;
  END IF;
  SELECT
    text
  INTO
    res
  FROM
    DISPLAYTEXT
  WHERE
    ( code              = new_Code          ) AND
    ( textType_Id       = new_TextType_Id   ) AND
    ( language_ISO_Code = new_Lang_ISO_Code )
  ;
  RETURN res;
EXCEPTION
WHEN NO_DATA_FOUND THEN
  RETURN NULL;
END DT_LookUp_Text_By_Code;
/


--
-- DT_LOOKUP_TEXT_BY_ID  (Function) 
--
CREATE OR REPLACE FUNCTION
  DT_LookUp_Text_By_Id (
    new_Text_Id       DISPLAYTEXT.text_Id%TYPE,
    new_TextType_Id   DISPLAYTEXT.textType_Id%TYPE,
    new_Lang_ISO_Code DISPLAYTEXT.language_ISO_Code%TYPE,
    cur_Idx           NUMBER := 0,  -- should be ROWNUM on paging, -1 to disable, 0 to enable if no paging
    from_Idx          NUMBER := -1, -- should be page start idx on paging,        -1 if no paging
    to_Idx            NUMBER := -1  -- should be page end idx on paging,          -1 if no paging 
    -- examples: row, min, max always >= 0, -1 is equivalent to any number <0, * means ANY number at all
    -- -1, -1, -1      disabled
    -- row, -1, -1     enabled                        (no paging)
    -- -1, min, max    disabled                       (paging, but column disabled)
    -- row, min, max   conditioned min <= row <= max  (pageging window)
    -- row, -1, max    conditioned row <= max
    -- row, min, -1    conditioned min <= row 
  )
RETURN
  DISPLAYTEXT.text%TYPE
IS
  res DISPLAYTEXT.text%TYPE := NULL;
BEGIN
  IF ( (cur_Idx < 0) OR (cur_Idx < from_Idx) OR ((cur_Idx > to_Idx) AND (to_Idx > -1)) ) THEN
    RETURN NULL;
  END IF;
  SELECT
    text
  INTO
    res
  FROM
    DISPLAYTEXT
  WHERE
    ( text_Id           = new_Text_Id       ) AND
    ( textType_Id       = new_TextType_Id   ) AND
    ( language_ISO_Code = new_Lang_ISO_Code )
  ;
  RETURN res;
EXCEPTION
WHEN NO_DATA_FOUND THEN
  RETURN NULL;
END DT_LookUp_Text_By_Id;
/


--
-- DT_CHECK_CODE_COUNT  (Function) 
--
CREATE OR REPLACE FUNCTION
  DT_Check_Code_Count(
    new_TextType_Id	TextType_Reg.TextType_Id%TYPE,
    new_Schemaname	TextType_Reg.SchemaName%TYPE,
    new_Tablename	TextType_Reg.TableName%TYPE,
    new_Columnname	TextType_Reg.ColumnName%TYPE,
    new_Code		DisplayText.code%TYPE,
    new_Parent_Code	DisplayText.code%TYPE := NULL
  )
RETURN
  NUMBER
IS
  cnt NUMBER := 0;
BEGIN
  DT_TextType_Reg( new_TextType_Id, new_Schemaname, new_Tablename, new_Columnname, 1 );
  IF ( new_Parent_Code IS NULL ) THEN
    SELECT
      COUNT(1)
    INTO
      cnt
    FROM
      DisplayText
    WHERE
      ( textType_Id  = new_TextType_Id   ) AND
      ( code       = new_Code        )
    ;
  ELSE
    SELECT
      COUNT(1)
    INTO
      cnt
    FROM
      DisplayText dt
      INNER JOIN TextType t ON
        ( dt.textType_Id    = t.textType_Id      ) AND
        ( dt.textType_Id    = new_TextType_Id   ) AND
        ( dt.code         = new_Code        )
      INNER JOIN DisplayText pdt ON
        ( t.parent_TextType_Id     = pdt.textType_Id     ) AND
        ( dt.parent_Text_Id  = pdt.text_Id        ) AND
        ( dt.language_ISO_Code    = pdt.language_ISO_Code     ) AND
        ( pdt.code        = new_Parent_Code   )
    ;
  END IF;
  RETURN cnt;
--EXCEPTION
--WHEN NO_DATA_FOUND THEN
--  RETURN NULL;
END DT_Check_Code_Count;
/


--
-- DT_CHECK_ID_COUNT  (Function) 
--
CREATE OR REPLACE FUNCTION
  DT_Check_Id_Count(
    new_TextType_Id    TextType_Reg.TextType_Id%TYPE,
    new_Schemaname     TextType_Reg.SchemaName%TYPE,
    new_Tablename      TextType_Reg.TableName%TYPE,
    new_Columnname     TextType_Reg.ColumnName%TYPE,
    new_Text_Id        DisplayText.text_Id%TYPE,
    new_Parent_Text_Id DisplayText.text_Id%TYPE := NULL
  )
RETURN
  NUMBER
IS
  cnt NUMBER := 0;
BEGIN
  DT_TextType_Reg( new_TextType_Id, new_Schemaname, new_Tablename, new_Columnname, 0 );
  IF ( new_Parent_Text_Id IS NULL ) THEN
    SELECT
      COUNT(1)
    INTO
      cnt
    FROM
      DisplayText
    WHERE
      ( textType_Id  = new_TextType_Id   ) AND
      ( text_Id           = new_Text_Id       )
    ;
  ELSE
    SELECT
      COUNT(1)
    INTO
      cnt
    FROM
      DisplayText dt
      INNER JOIN TextType t ON
        ( dt.textType_Id    = t.textType_Id      ) AND
        ( dt.textType_Id    = new_TextType_Id   ) AND
        ( dt.text_Id        = new_Text_Id       )
      INNER JOIN DisplayText pdt ON
        ( t.parent_TextType_Id     = pdt.textType_Id     ) AND
        ( dt.parent_Text_Id  = pdt.text_Id        ) AND
        ( dt.language_ISO_Code    = pdt.language_ISO_Code     ) AND
        ( pdt.text_Id       = new_Parent_Text_Id )
    ;
  END IF;
  RETURN cnt;
--EXCEPTION
--WHEN NO_DATA_FOUND THEN
--  RETURN NULL;
END DT_Check_Id_Count;
/


--
-- FK_NEWS_DISPLAYTEXT  (Trigger) 
--
CREATE OR REPLACE TRIGGER FK_NEWS_DISPLAYTEXT
BEFORE INSERT OR UPDATE ON
  NEWS
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
  IF ( ( :old.segment_Id    <> :new.segment_Id    ) OR
       ( (:old.segment_Id    IS     NULL) AND (:new.segment_Id    IS NOT NULL) ) OR
       ( (:old.segment_Id    IS NOT NULL) AND (:new.segment_Id    IS     NULL) )    ) THEN
    DT_Check_FK( :new.segment_Id,    2, 'FK_NEWS_DISPLAYTEXT', 'News.segment_Id'    );
  END IF;
END;
/


--
-- WEBINFO_SENDMSG_U  (Trigger) 
--
CREATE OR REPLACE TRIGGER WEBINFO_SENDMSG_U
BEFORE UPDATE ON
  WEBINFO
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
  --  set the Send_... flags, if the isAvailable_Flg changes
  IF (:old.isAvailable_Flg <> :new.isAvailable_Flg) THEN
    -- reset flags, if status has been changed 
    :new.send_News_Flg         := :new.news_Flg;
    :new.send_Email_Static_Flg := :new.email_Static_Flg;
    :new.send_Email_Um_Flg     := :new.email_Um_Flg;
    -- deactivate news always if component is available
    IF ( :new.isAvailable_Flg = 1 ) THEN
      :new.send_News_Flg       := 1;
    END IF;
/*
    -- send  messages also if status has been changed    
    IF ( :new.news_Flg         = 1 ) THEN
      :new.send_News_Flg         := 1;
    END IF;
    IF ( :new.email_Static_Flg = 1 ) THEN
      :new.send_Email_Static_Flg := 1;
    END IF;
    IF ( :new.email_Um_Flg     = 1 ) THEN
      :new.send_Email_Um_Flg     := 1;
    END IF;
*/
  END IF;
-- Reset unhandled send-requests, when sending is generally disabled
  IF ( (:old.news_Flg         = 1) AND (:new.news_Flg         = 0) ) THEN
    :new.send_News_Flg         := 0;
  END IF;
  IF ( (:old.email_Static_Flg = 1) AND (:new.email_Static_Flg = 0) ) THEN
    :new.send_Email_Static_Flg := 0;
  END IF;
  IF ( (:old.email_Um_Flg     = 1) AND (:new.email_Um_Flg     = 0) ) THEN
    :new.send_Email_Um_Flg     := 0;
  END IF;
-- Reset unhandled send-requests, when component is reactivated
  IF ( (:old.isactive_Flg     = 0) AND (:new.isactive_Flg     = 1) ) THEN
    :new.send_News_Flg         := 0;
    :new.send_Email_Static_Flg := 0;
    :new.send_Email_Um_Flg     := 0;
  END IF;
END WebInfo_SendMsg_U;
/


--
-- WEBINFO_CDT  (Trigger) 
--
CREATE OR REPLACE TRIGGER WEBINFO_CDT
BEFORE INSERT OR UPDATE ON
  WEBINFO
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (:old.created IS NOT NULL) THEN
    :new.created := :old.created;
  ELSE
    :new.created := get_Normalized_Time();
  END IF;
  IF (:old.createdBy IS NOT NULL) THEN
    :new.createdBy := :old.createdBy;
  END IF;
  :new.changed := get_Normalized_Time();
  IF ( :new.object_Id IS NULL ) THEN
    IF ( :old.object_Id IS NULL ) THEN
      :new.object_Id := get_Next_OID();
    ELSE
      :new.object_Id := :old.object_Id;
    END IF;
  END IF;
END WEBINFO_CDT;
/


--
-- NEWS_CDT  (Trigger) 
--
CREATE OR REPLACE TRIGGER NEWS_CDT
BEFORE INSERT OR UPDATE ON
  NEWS
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :NEW.created := get_Normalized_Time();
  END IF;
  IF (UPDATING) THEN
    :NEW.created   := :OLD.created;
    :NEW.createdBy := :OLD.createdBy;
    :NEW.createdOn := :OLD.createdOn;
  END IF;
  :NEW.changed := get_Normalized_Time();
  IF (:NEW.userChanged > (get_Normalized_Time() + 365750) OR INSERTING) THEN
    :NEW.userChanged := :NEW.changed;
  ELSE
    :NEW.userChanged := :OLD.userChanged;
  END IF;
  IF (INSERTING AND :NEW.object_Id IS NULL) THEN
    :NEW.object_Id := Get_Next_Oid();
  ELSIF (UPDATING) THEN
    :NEW.object_Id := :OLD.object_Id;
  END IF;
END NEWS_CDT;
/



--
-- NEWS_READ_BY  (Table) 
--
CREATE TABLE NEWS_READ_BY
(
  OBJECT_ID    NUMBER(27)                       NOT NULL,
  USER_ID      NUMBER(27)                       NOT NULL,
  NEWS_ID      NUMBER(27)                       NOT NULL,
  CREATED      DATE,
  CHANGED      DATE,
  CREATEDBY    VARCHAR2(10 CHAR),
  CHANGEDBY    VARCHAR2(10 CHAR),
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
-- NEWS_DATA  (Table) 
--
CREATE TABLE NEWS_DATA
(
  OBJECT_ID      NUMBER(27)                     NOT NULL,
  NEWS_ID        NUMBER(27)                     NOT NULL,
  TITLE          VARCHAR2(160 CHAR)             NOT NULL,
  TEXT           VARCHAR2(4000 CHAR),
  LANGUAGE_CODE  VARCHAR2(2 CHAR)               NOT NULL,
  CREATED        DATE,
  CHANGED        DATE,
  CREATEDBY      VARCHAR2(10 CHAR),
  CHANGEDBY      VARCHAR2(10 CHAR),
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
            INITIAL          256K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
           );


--
-- FK_NEWSDATA_LANGUAGE_IDX  (Index) 
--
CREATE INDEX FK_NEWSDATA_LANGUAGE_IDX ON NEWS_DATA
(LANGUAGE_CODE)
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
-- FK_NEWSDATA_NEWSID  (Index) 
--
CREATE INDEX FK_NEWSDATA_NEWSID ON NEWS_DATA
(NEWS_ID)
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
-- NEWSDATA_NEWSID_LANGCODE_U  (Index) 
--
CREATE UNIQUE INDEX NEWSDATA_NEWSID_LANGCODE_U ON NEWS_DATA
(NEWS_ID, LANGUAGE_CODE)
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
-- NEWSREADBY_USERID_NEWSID_U  (Index) 
--
CREATE UNIQUE INDEX NEWSREADBY_USERID_NEWSID_U ON NEWS_READ_BY
(USER_ID, NEWS_ID)
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
-- PK_NEWS_DATA  (Index) 
--
CREATE UNIQUE INDEX PK_NEWS_DATA ON NEWS_DATA
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
-- PK_NEWS_READ_BY  (Index) 
--
CREATE UNIQUE INDEX PK_NEWS_READ_BY ON NEWS_READ_BY
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
-- DT_CHECK_ID_CONSTR  (Procedure) 
--
CREATE OR REPLACE PROCEDURE
  DT_Check_Id_Constr(
    new_TextType_Id	TextType_Reg.TextType_Id%TYPE,
    new_Schemaname	TextType_Reg.SchemaName%TYPE,
    new_Tablename	TextType_Reg.TableName%TYPE,
    new_Columnname	TextType_Reg.ColumnName%TYPE,
    old_Text_Id         DisplayText.text_Id%TYPE,
    new_Text_Id         DisplayText.text_Id%TYPE,
    old_Parent_Text_Id  DisplayText.text_Id%TYPE := NULL,
    new_Parent_Text_Id  DisplayText.text_Id%TYPE := NULL
  )
IS
BEGIN
  IF ( (NVL( old_Text_Id, -1 ) <> NVL( new_Text_Id, -1 )) OR (NVL( old_Parent_Text_Id, -1 ) <> NVL( new_Parent_Text_Id, -1 )) ) THEN
    IF ( DT_Check_Id_Count(new_TextType_Id, new_Schemaname, new_Tablename, new_Columnname, new_Text_Id, new_Parent_Text_Id ) = 0 ) THEN
      RAISE_APPLICATION_ERROR( -20485,
        'Illegal textId: ' || TO_CHAR(new_Text_Id) || ' / parentTextId: ' || NVL(TO_CHAR(new_Parent_Text_Id), '<NULL>') || ' in: ' ||
        new_Schemaname || '.' || new_TableName || '.' || new_ColumnName ||
        ' - TextTypeId: ' || TO_CHAR( new_TextType_Id ) || ' !!!' );
    END IF;
  END IF;
END DT_Check_Id_Constr;
/


--
-- DT_CHECK_CODE_CONSTR  (Procedure) 
--
CREATE OR REPLACE PROCEDURE
  DT_Check_Code_Constr(
    new_TextType_Id	TextType_Reg.TextType_Id%TYPE,
    new_Schemaname	TextType_Reg.SchemaName%TYPE,
    new_Tablename	TextType_Reg.TableName%TYPE,
    new_Columnname	TextType_Reg.ColumnName%TYPE,
    old_Code            DisplayText.code%TYPE,
    new_Code            DisplayText.code%TYPE,
    old_Parent_Code     DisplayText.code%TYPE := NULL,
    new_Parent_Code     DisplayText.code%TYPE := NULL
  )
IS
BEGIN
  IF ( (NVL( old_Code, ' ' ) <> NVL( new_Code, ' ' )) OR (NVL( old_Parent_Code, ' ' ) <> NVL( new_Parent_Code, ' ' )) ) THEN
    IF ( DT_Check_Code_Count(new_TextType_Id, new_Schemaname, new_Tablename, new_Columnname, new_Code, new_Parent_Code ) = 0 ) THEN
      RAISE_APPLICATION_ERROR( -20486,
        'Illegal code: ' || NVL(new_Code, '<NULL>') || ' / parentCode: ' || NVL(new_Parent_Code, '<NULL>') || ' in: ' ||
        new_Schemaname || '.' || new_TableName || '.' || new_ColumnName ||
        ' - TextTypeId: ' || TO_CHAR( new_TextType_Id ) || ' !!!' );
    END IF;
  END IF;
END DT_Check_Code_Constr;
/


--
-- NEWSREADBY_GET_OID  (Function) 
--
CREATE OR REPLACE FUNCTION
  NewsReadBy_Get_OID (
    new_User_Id News_Read_By.user_Id%TYPE,
    new_News_Id News_Read_By.news_Id%TYPE
  )
RETURN
  News_Read_By.Object_Id%TYPE
IS
-- Check, if a matching session exists, return the object_id
  exist_Object_Id    News_Read_By.Object_Id%TYPE := NULL;
PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN
  SELECT
    object_Id
  INTO
    exist_Object_Id
  FROM
    News_Read_By
  WHERE
    ( user_Id = new_User_Id ) AND
    ( news_Id = new_News_Id )
  ;
  COMMIT WORK;
  RETURN exist_Object_Id;
EXCEPTION
WHEN NO_DATA_FOUND THEN
  COMMIT WORK;
  RETURN NULL;
END NewsReadBy_Get_OID;
/


--
-- FK_DISPLAYTEXT  (Trigger) 
--
CREATE OR REPLACE TRIGGER FK_DISPLAYTEXT
BEFORE UPDATE OR DELETE ON
  DISPLAYTEXT
FOR EACH ROW
WHEN (
( old.text_Id         <> NVL( new.text_Id,     0 ) ) OR
       ( NVL( old.code, '' ) <> NVL( new.code,       '' ) ) OR
       ( old.textType_Id     <> NVL( new.textType_Id, 0 ) )
      )
DECLARE
  exist  NUMBER        := 0;
  ctype  VARCHAR2(20 CHAR)  := NULL;
  tables VARCHAR2(100 CHAR) := NULL;
BEGIN
  IF    ( :old.textType_Id = 1 ) THEN
    ctype  := 'salutation';
    tables := 'Users, Registration';
--    SELECT SUM(cnt) INTO exist FROM (
--      SELECT COUNT(*) AS cnt FROM ADC.Users        WHERE ( salutation_Id         = :old.text_Id ) UNION ALL
--      SELECT COUNT(*) AS cnt FROM ADC.Registration WHERE ( salutation_Id         = :old.text_Id )
--    );
  ELSIF ( :old.textType_Id = 2 ) THEN
    ctype  := 'segment';
    tables := 'Company, Users, Groups, News';
    SELECT SUM(cnt) INTO exist FROM (
--      SELECT COUNT(*) AS cnt FROM ADC.Company      WHERE ( segment_Id            = :old.text_Id ) UNION ALL
--      SELECT COUNT(*) AS cnt FROM ADC.Users        WHERE ( internal_Segment_Id   = :old.text_Id ) UNION ALL
--      SELECT COUNT(*) AS cnt FROM ADC.Groups       WHERE ( segment_Id            = :old.text_Id ) UNION ALL
--      SELECT COUNT(*) AS cnt FROM ADC.News         WHERE ( segment_Id            = :old.text_Id ) UNION ALL
      SELECT COUNT(*) AS cnt FROM News             WHERE ( segment_Id            = :old.text_Id )
    );
  ELSIF ( :old.textType_Id = 3 ) THEN
    ctype  := 'membership';
    tables := 'Company, Role, Users, Groups';
--    SELECT SUM(cnt) INTO exist FROM (
--      SELECT COUNT(*) AS cnt FROM ADC.Company      WHERE ( membership_Id         = :old.text_Id ) UNION ALL
--      SELECT COUNT(*) AS cnt FROM ADC.Role         WHERE ( membership_Id         = :old.text_Id ) UNION ALL
--      SELECT COUNT(*) AS cnt FROM ADC.Users        WHERE ( membership_Id         = :old.text_Id ) UNION ALL
--      SELECT COUNT(*) AS cnt FROM ADC.Groups       WHERE ( membership_Id         = :old.text_Id )
--    );
  ELSIF ( :old.textType_Id = 4 ) THEN
    ctype  := 'country';
    tables := 'Users, Registration, UM_Users';
--    SELECT SUM(cnt) INTO exist FROM (
--      SELECT COUNT(*) AS cnt FROM ADC.Users        WHERE ( countryId             = :old.text_Id ) UNION ALL
--      SELECT COUNT(*) AS cnt FROM ADC.Registration WHERE ( country_Id            = :old.text_Id ) UNION ALL
--      SELECT COUNT(*) AS cnt FROM UM_Users         WHERE ( country_Code          = :old.code    ) UNION ALL
--      SELECT COUNT(*) AS cnt FROM UM_Users         WHERE ( display_Country_Code  = :old.code    )
--    );
  ELSIF ( :old.textType_Id = 5 ) THEN
    ctype  := 'language';
    tables := 'Users, Registration, UM_Users';
    SELECT SUM(cnt) INTO exist FROM (
--      SELECT COUNT(*) AS cnt FROM ADC.Users        WHERE ( languageId            = :old.text_Id ) UNION ALL
--      SELECT COUNT(*) AS cnt FROM ADC.Registration WHERE ( language_Id           = :old.text_Id ) UNION ALL
--      SELECT COUNT(*) AS cnt FROM ADC.News_Data    WHERE ( language_Code         = :old.code    ) UNION ALL
      SELECT COUNT(*) AS cnt FROM News_Data        WHERE ( language_Code         = :old.code    ) --UNION ALL
--      SELECT COUNT(*) AS cnt FROM UM_Users         WHERE ( language_Code         = :old.code    ) UNION ALL
--      SELECT COUNT(*) AS cnt FROM UM_Users         WHERE ( display_Language_Code = :old.code    )
    );
  ELSIF ( :old.textType_Id = 6 ) THEN
    ctype  := 'position';
    tables := 'Users';
--    SELECT SUM(cnt) INTO exist FROM (
--      SELECT COUNT(*) AS cnt FROM ADC.Users        WHERE ( position_Id           = :old.text_Id )
--    );
  ELSIF ( :old.textType_Id = 7 ) THEN
    ctype  := 'timezone';
    tables := 'Users';
--    SELECT SUM(cnt) INTO exist FROM (
--      SELECT COUNT(*) AS cnt FROM ADC.Users        WHERE ( timezone_Id           = :old.text_Id )
--    );
  ELSIF ( :old.textType_Id = 8 ) THEN
    ctype  := 'regional-operation';
    tables := 'Registration';
--    SELECT SUM(cnt) INTO exist FROM (
--      SELECT COUNT(*) AS cnt FROM ADC.Registration WHERE ( regional_Operations_Id1 = :old.text_Id ) OR
--                                                         ( regional_Operations_Id2 = :old.text_Id ) OR
--                                                         ( regional_Operations_Id3 = :old.text_Id ) OR
--                                                         ( regional_Operations_Id4 = :old.text_Id ) OR
--                                                         ( regional_Operations_Id5 = :old.text_Id ) OR
--                                                         ( regional_Operations_Id6 = :old.text_Id )
--    );
  END IF;
  IF ( exist > 0 ) THEN
  RAISE_APPLICATION_ERROR( -20805,
    'Integry constraint trigger (FK_DISPLAYTEXT) violated - still referenced by ' ||
    TO_CHAR(exist) || ' ' || ctype || '-Ids/Codes, tables: ' || tables ||
    ', textType_Id = ' || NVL ( TO_CHAR( :old.textType_Id ), '<NULL>' ) ||
    ', text_Id = '     || NVL ( TO_CHAR( :old.text_Id     ), '<NULL>' ) ||
    ', code = '        || NVL (          :old.code         , '<NULL>' ) || '!'
  );
  END IF;
END FK_DISPLAYTEXT;
/


--
-- FK_NEWSDATA_DISPLAYTEXT  (Trigger) 
--
CREATE OR REPLACE TRIGGER FK_NEWSDATA_DISPLAYTEXT
BEFORE INSERT OR UPDATE ON
  NEWS_DATA
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
  IF ( ( :old.language_Code <> :new.language_Code ) OR
       ( (:old.language_Code IS     NULL) AND (:new.language_Code IS NOT NULL) ) OR
       ( (:old.language_Code IS NOT NULL) AND (:new.language_Code IS     NULL) )    ) THEN
    DT_Check_Code_FK( :new.language_Code, 5, 'FK_NEWSDATA_DISPLAYTEXT', 'News_Data.language_Code' );
  END IF;
END;
/


--
-- NEWS_READ_BY_CDT  (Trigger) 
--
CREATE OR REPLACE TRIGGER NEWS_READ_BY_CDT
BEFORE INSERT OR UPDATE ON
  NEWS_READ_BY
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :NEW.created := get_Normalized_Time();
  END IF;
  IF (UPDATING) THEN
    :NEW.created   := :OLD.created;
    :NEW.createdBy := :OLD.createdBy;
    :NEW.createdOn := :OLD.createdOn;
  END IF;
  :NEW.changed := get_Normalized_Time();
  IF (:NEW.userChanged > (get_Normalized_Time() + 365750) OR INSERTING) THEN
    :NEW.userChanged := :NEW.changed;
  ELSE
    :NEW.userChanged := :OLD.userChanged;
  END IF;
  IF (INSERTING AND :NEW.object_Id IS NULL) THEN
    :NEW.object_Id := Get_Next_Oid();
  ELSIF (UPDATING) THEN
    :NEW.object_Id := :OLD.object_Id;
  END IF;
END NEWS_READ_BY_CDT;
/


--
-- NEWS_DATA_CDT  (Trigger) 
--
CREATE OR REPLACE TRIGGER NEWS_DATA_CDT
BEFORE INSERT OR UPDATE ON
  NEWS_DATA
FOR EACH ROW
DECLARE
--  declare variables...
BEGIN
--  protect the create columns
  IF (INSERTING) THEN
    :NEW.created := get_Normalized_Time();
  END IF;
  IF (UPDATING) THEN
    :NEW.created   := :OLD.created;
    :NEW.createdBy := :OLD.createdBy;
    :NEW.createdOn := :OLD.createdOn;
  END IF;
  :NEW.changed := get_Normalized_Time();
  IF (:NEW.userChanged > (get_Normalized_Time() + 365750) OR INSERTING) THEN
    :NEW.userChanged := :NEW.changed;
  ELSE
    :NEW.userChanged := :OLD.userChanged;
  END IF;
  IF (INSERTING AND :NEW.object_Id IS NULL) THEN
    :NEW.object_Id := Get_Next_Oid();
  ELSIF (UPDATING) THEN
    :NEW.object_Id := :OLD.object_Id;
  END IF;
END NEWS_DATA_CDT;
/


-- 
-- Non Foreign Key Constraints for Table PERFORMANCE 
-- 
ALTER TABLE PERFORMANCE ADD (
  CONSTRAINT PK_PERFORMANCE
  PRIMARY KEY
  (OBJECT_ID)
  USING INDEX PK_PERFORMANCE);


-- 
-- Non Foreign Key Constraints for Table WEBMETRIX 
-- 
ALTER TABLE WEBMETRIX ADD (
  CONSTRAINT PK_WEBMETRIX
  PRIMARY KEY
  (OBJECT_ID)
  USING INDEX PK_WEBMETRIX);


-- 
-- Non Foreign Key Constraints for Table WEBINFO_COMPONENT_TYPE 
-- 
ALTER TABLE WEBINFO_COMPONENT_TYPE ADD (
  CONSTRAINT PK_WEBINFO_COMPONENT_TYPE
  PRIMARY KEY
  (COMP_TYPE)
  USING INDEX PK_WEBINFO_COMPONENT_TYPE);


-- 
-- Non Foreign Key Constraints for Table VERSIONS 
-- 
ALTER TABLE VERSIONS ADD (
  CONSTRAINT PK_VERSIONS
  PRIMARY KEY
  (MODULE)
  USING INDEX PK_VERSIONS);


-- 
-- Non Foreign Key Constraints for Table TRANSLATIONS 
-- 
ALTER TABLE TRANSLATIONS ADD (
  CONSTRAINT TRANSLATIONS_PK
  PRIMARY KEY
  (OBJECT_ID)
  USING INDEX TRANSLATIONS_PK);


-- 
-- Non Foreign Key Constraints for Table TEXTTYPE 
-- 
ALTER TABLE TEXTTYPE ADD (
  CONSTRAINT PK_TEXTTYPE
  PRIMARY KEY
  (TEXTTYPE_ID)
  USING INDEX PK_TEXTTYPE);


-- 
-- Non Foreign Key Constraints for Table SESSION_DATA 
-- 
ALTER TABLE SESSION_DATA ADD (
  CONSTRAINT SESSION_DATAPK
  PRIMARY KEY
  (OBJECT_ID)
  USING INDEX SESSION_DATAPK);


-- 
-- Non Foreign Key Constraints for Table SCHEDULED_TASKS 
-- 
ALTER TABLE SCHEDULED_TASKS ADD (
  CONSTRAINT PK_SCHEDULED_TASKS
  PRIMARY KEY
  (OBJECT_ID)
  USING INDEX PK_SCHEDULED_TASKS);


-- 
-- Non Foreign Key Constraints for Table OBJECTSTATE 
-- 
ALTER TABLE OBJECTSTATE ADD (
  CONSTRAINT PK_OBJECTSTATE
  PRIMARY KEY
  (OBJECTSTATE_ID)
  USING INDEX PK_OBJECTSTATE);


-- 
-- Non Foreign Key Constraints for Table NAVIGATION 
-- 
ALTER TABLE NAVIGATION ADD (
  CONSTRAINT CT_REGISTRATION_TBTRL
  CHECK ( ( TO_BE_TRANSLATED = 0 ) OR ( TO_BE_TRANSLATED = 1 ) ));

ALTER TABLE NAVIGATION ADD (
  CONSTRAINT PK_NAVIGATION
  PRIMARY KEY
  (OBJECT_ID)
  USING INDEX PK_NAVIGATION);


-- 
-- Non Foreign Key Constraints for Table LANGUAGE 
-- 
ALTER TABLE LANGUAGE ADD (
  CONSTRAINT PK_LANGUAGE
  PRIMARY KEY
  (LANGUAGE_ISO_CODE)
  USING INDEX PK_LANGUAGE);


-- 
-- Non Foreign Key Constraints for Table COUNTRY 
-- 
ALTER TABLE COUNTRY ADD (
  CONSTRAINT PK_COUNTRY
  PRIMARY KEY
  (COUNTRY_ISO_CODE)
  USING INDEX PK_COUNTRY);


-- 
-- Non Foreign Key Constraints for Table TEXTTYPE_REG 
-- 
ALTER TABLE TEXTTYPE_REG ADD (
  CONSTRAINT TTREG_ISCODE_CHK
  CHECK ( is_Code IN (0, 1) ));

ALTER TABLE TEXTTYPE_REG ADD (
  CONSTRAINT TEXTTYPE_REG_PK
  PRIMARY KEY
  (TEXTTYPE_ID, SCHEMANAME, TABLENAME, COLUMNNAME)
  USING INDEX TEXTTYPE_REG_PK_IDX);


-- 
-- Non Foreign Key Constraints for Table WEBINFO 
-- 
ALTER TABLE WEBINFO ADD (
  CHECK ((ISACTIVE_FLG=0) or (ISACTIVE_FLG=1)));

ALTER TABLE WEBINFO ADD (
  CHECK ((MONITOR_FLG=0) or (MONITOR_FLG=1)));

ALTER TABLE WEBINFO ADD (
  CHECK ((NEWS_FLG=0) or (NEWS_FLG=1)));

ALTER TABLE WEBINFO ADD (
  CHECK ((WEBMETRIX_FLG=0) or (WEBMETRIX_FLG=1)));

ALTER TABLE WEBINFO ADD (
  CHECK ((ISAVAILABLE_FLG=0) or (ISAVAILABLE_FLG=1)));

ALTER TABLE WEBINFO ADD (
  CHECK ((EMAIL_STATIC_FLG=0) or (EMAIL_STATIC_FLG=1)));

ALTER TABLE WEBINFO ADD (
  CHECK ((EMAIL_UM_FLG=0) or (EMAIL_UM_FLG=1)));

ALTER TABLE WEBINFO ADD (
  CHECK ((SEND_NEWS_FLG=0) or (SEND_NEWS_FLG=1) ));

ALTER TABLE WEBINFO ADD (
  CHECK ((SEND_EMAIL_UM_FLG=0) or (SEND_EMAIL_UM_FLG=1) ));

ALTER TABLE WEBINFO ADD (
  CHECK ((SEND_EMAIL_STATIC_FLG=0) or (SEND_EMAIL_STATIC_FLG=1)  ));

ALTER TABLE WEBINFO ADD (
  CONSTRAINT PK_WEBINFO
  PRIMARY KEY
  (OBJECT_ID)
  USING INDEX PK_WEBINFO);

ALTER TABLE WEBINFO ADD (
  UNIQUE (COMPONENT)
  USING INDEX
    TABLESPACE DOC41WEB_IDX
    PCTFREE    10
    INITRANS   2
    MAXTRANS   255
    STORAGE    (
                INITIAL          256K
                MINEXTENTS       1
                MAXEXTENTS       UNLIMITED
                PCTINCREASE      0
               ));


-- 
-- Non Foreign Key Constraints for Table NEWS 
-- 
ALTER TABLE NEWS ADD (
  CHECK ((ISMANDATORY = 0) OR (ISMANDATORY = 1)));

ALTER TABLE NEWS ADD (
  CONSTRAINT PK_NEWS
  PRIMARY KEY
  (OBJECT_ID)
  USING INDEX PK_NEWS);


-- 
-- Non Foreign Key Constraints for Table DISPLAYTEXT 
-- 
ALTER TABLE DISPLAYTEXT ADD (
  CHECK (( is_Active = 0 ) OR ( is_Active = 1 )));

ALTER TABLE DISPLAYTEXT ADD (
  CONSTRAINT PK_DISPLAYTEXT
  PRIMARY KEY
  (TEXT_ID, TEXTTYPE_ID, LANGUAGE_ISO_CODE)
  USING INDEX PK_DISPLAYTEXT);


-- 
-- Non Foreign Key Constraints for Table NEWS_READ_BY 
-- 
ALTER TABLE NEWS_READ_BY ADD (
  CONSTRAINT PK_NEWS_READ_BY
  PRIMARY KEY
  (OBJECT_ID)
  USING INDEX PK_NEWS_READ_BY);


-- 
-- Non Foreign Key Constraints for Table NEWS_DATA 
-- 
ALTER TABLE NEWS_DATA ADD (
  CONSTRAINT PK_NEWS_DATA
  PRIMARY KEY
  (OBJECT_ID)
  USING INDEX PK_NEWS_DATA);


-- 
-- Foreign Key Constraints for Table NAVIGATION 
-- 
ALTER TABLE NAVIGATION ADD (
  CONSTRAINT FK_NAVIGATION_NAVIGATION 
  FOREIGN KEY (PARENT_ID) 
  REFERENCES NAVIGATION (OBJECT_ID)
  ON DELETE CASCADE);


-- 
-- Foreign Key Constraints for Table TEXTTYPE_REG 
-- 
ALTER TABLE TEXTTYPE_REG ADD (
  CONSTRAINT TEXTTYPEREG_TEXTTYPE_FK 
  FOREIGN KEY (TEXTTYPE_ID) 
  REFERENCES TEXTTYPE (TEXTTYPE_ID));


-- 
-- Foreign Key Constraints for Table WEBINFO 
-- 
ALTER TABLE WEBINFO ADD (
  CONSTRAINT FK_WEBINFO_COMPONENT_TYPE 
  FOREIGN KEY (COMP_TYPE) 
  REFERENCES WEBINFO_COMPONENT_TYPE (COMP_TYPE));


-- 
-- Foreign Key Constraints for Table NEWS 
-- 
ALTER TABLE NEWS ADD (
  CONSTRAINT FK_NEWS_OBJSTATE 
  FOREIGN KEY (OBJECTSTATE_ID) 
  REFERENCES OBJECTSTATE (OBJECTSTATE_ID));

ALTER TABLE NEWS ADD (
  CONSTRAINT FK_NEWS_OBJSTATE_INT 
  FOREIGN KEY (OBJECTSTATEINTERNAL_ID) 
  REFERENCES OBJECTSTATE (OBJECTSTATE_ID));


-- 
-- Foreign Key Constraints for Table DISPLAYTEXT 
-- 
ALTER TABLE DISPLAYTEXT ADD (
  CONSTRAINT FK_DISPLAYTEXT_LANGUAGE 
  FOREIGN KEY (LANGUAGE_ISO_CODE) 
  REFERENCES LANGUAGE (LANGUAGE_ISO_CODE));

ALTER TABLE DISPLAYTEXT ADD (
  CONSTRAINT FK_DISPLAYTEXT_TEXTTYPE 
  FOREIGN KEY (TEXTTYPE_ID) 
  REFERENCES TEXTTYPE (TEXTTYPE_ID));


-- 
-- Foreign Key Constraints for Table NEWS_READ_BY 
-- 
ALTER TABLE NEWS_READ_BY ADD (
  CONSTRAINT FK_NEWSREADBY_NEWS 
  FOREIGN KEY (NEWS_ID) 
  REFERENCES NEWS (OBJECT_ID)
  ON DELETE CASCADE);


-- 
-- Foreign Key Constraints for Table NEWS_DATA 
-- 
ALTER TABLE NEWS_DATA ADD (
  CONSTRAINT FK_NEWSDATA_LANGUAGE 
  FOREIGN KEY (LANGUAGE_CODE) 
  REFERENCES LANGUAGE (LANGUAGE_ISO_CODE));

ALTER TABLE NEWS_DATA ADD (
  CONSTRAINT FK_NEWSDATA_NEWS 
  FOREIGN KEY (NEWS_ID) 
  REFERENCES NEWS (OBJECT_ID)
  ON DELETE CASCADE);


GRANT EXECUTE ON CHARCODES TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON COUNTRY TO MXDOC41WEB;

GRANT EXECUTE ON FROM_HTML TO MXDOC41WEB;

GRANT EXECUTE ON FROM_HTML_CLOB TO MXDOC41WEB WITH GRANT OPTION;

GRANT EXECUTE ON GET_NEXT_OID TO MXDOC41WEB WITH GRANT OPTION;

GRANT EXECUTE ON GET_NORMALIZED_TIME TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON LANGUAGE TO MXDOC41WEB;

GRANT EXECUTE ON LISTAGGFDT TO MXDOC41WEB;

GRANT EXECUTE ON LOOKUP_FIELDS_BY_ID_AT TO MXDOC41WEB;

GRANT EXECUTE ON LOOKUP_FIELDS_BY_ID_IT TO MXDOC41WEB;

GRANT EXECUTE ON LOOKUP_SERIAL_BY_EXTKEY TO MXDOC41WEB WITH GRANT OPTION;

GRANT EXECUTE ON LOOKUP_SERIAL_BY_EXTKEY_IT TO MXDOC41WEB;

GRANT DELETE, INSERT, SELECT, UPDATE ON NAVIGATION TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON OBJECTSTATE TO MXDOC41WEB WITH GRANT OPTION;

GRANT REFERENCES                     ON OBJECTSTATE TO DOC41WEB_MGR WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON PERFORMANCE TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON SCHEDULED_TASKS TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON SESSION_DATA TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON TEXTTYPE TO MXDOC41WEB WITH GRANT OPTION;

GRANT EXECUTE ON TO_HTML TO MXDOC41WEB;

GRANT DELETE, INSERT, SELECT, UPDATE ON TRANSLATIONS TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON VERSIONS TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON WEBINFO_COMPONENT_TYPE TO MXDOC41WEB WITH GRANT OPTION;

GRANT SELECT ON WEBM_CHAINS_DAY_ALL TO MXDOC41WEB WITH GRANT OPTION;

GRANT SELECT ON WEBM_CHAINS_DAY_EACH TO MXDOC41WEB WITH GRANT OPTION;

GRANT SELECT ON WEBM_CHAINS_MONTH_ALL TO MXDOC41WEB WITH GRANT OPTION;

GRANT SELECT ON WEBM_CHAINS_MONTH_EACH TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON WEBMETRIX TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, REFERENCES, SELECT, UPDATE ON COUNTRY TO DOC41WEB_MGR;

GRANT EXECUTE ON FROM_HTML TO DOC41WEB_MGR;

GRANT EXECUTE ON FROM_HTML_CLOB TO DOC41WEB_MGR;

GRANT EXECUTE ON GET_NEXT_OID TO DOC41WEB_MGR;

GRANT EXECUTE ON GET_NORMALIZED_TIME TO DOC41WEB_MGR;

GRANT DELETE, INSERT, REFERENCES, SELECT, UPDATE ON LANGUAGE TO DOC41WEB_MGR;

GRANT EXECUTE ON LISTAGGFDT TO DOC41WEB_MGR;

GRANT EXECUTE ON LOOKUP_FIELDS_BY_ID_AT TO DOC41WEB_MGR;

GRANT EXECUTE ON LOOKUP_FIELDS_BY_ID_IT TO DOC41WEB_MGR;

GRANT EXECUTE ON LOOKUP_SERIAL_BY_EXTKEY TO DOC41WEB_MGR;

GRANT EXECUTE ON LOOKUP_SERIAL_BY_EXTKEY_IT TO DOC41WEB_MGR;

GRANT EXECUTE ON SYNC_CHANGED_SERIAL_AND_EXTKEY TO DOC41WEB_MGR;

GRANT EXECUTE ON TO_HTML TO DOC41WEB_MGR;

GRANT DELETE, INSERT, SELECT, UPDATE ON VERSIONS TO DOC41WEB_MGR;

GRANT EXECUTE ON CDT TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON DISPLAYTEXT TO MXDOC41WEB WITH GRANT OPTION;

GRANT EXECUTE ON DT_CHECK_CODE_COUNT TO MXDOC41WEB WITH GRANT OPTION;

GRANT EXECUTE ON DT_CHECK_ID_COUNT TO MXDOC41WEB WITH GRANT OPTION;

GRANT EXECUTE ON DT_LOOKUP_TEXT_BY_CODE TO MXDOC41WEB;

GRANT EXECUTE ON DT_LOOKUP_TEXT_BY_ID TO MXDOC41WEB;

GRANT DELETE, INSERT, SELECT, UPDATE ON NEWS TO MXDOC41WEB WITH GRANT OPTION;

GRANT SELECT ON TEXTTYPE_REG TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON WEBINFO TO MXDOC41WEB WITH GRANT OPTION;

GRANT EXECUTE ON CDT TO DOC41WEB_MGR;

GRANT EXECUTE ON DT_CHECK_CODE_COUNT TO DOC41WEB_MGR;

GRANT EXECUTE ON DT_CHECK_CODE_FK TO DOC41WEB_MGR;

GRANT EXECUTE ON DT_CHECK_FK TO DOC41WEB_MGR;

GRANT EXECUTE ON DT_CHECK_ID_COUNT TO DOC41WEB_MGR;

GRANT EXECUTE ON DT_CHECK_CODE_CONSTR TO MXDOC41WEB WITH GRANT OPTION;

GRANT EXECUTE ON DT_CHECK_ID_CONSTR TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON NEWS_DATA TO MXDOC41WEB WITH GRANT OPTION;

GRANT DELETE, INSERT, SELECT, UPDATE ON NEWS_READ_BY TO MXDOC41WEB WITH GRANT OPTION;

GRANT EXECUTE ON DT_CHECK_CODE_CONSTR TO DOC41WEB_MGR;

GRANT EXECUTE ON DT_CHECK_ID_CONSTR TO DOC41WEB_MGR;
