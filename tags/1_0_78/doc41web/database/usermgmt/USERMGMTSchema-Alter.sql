-- $Id: USERMGMTSchema-Alter.sql,v 1.29 2013/06/03 13:23:49 imwif Exp $

-- >> HOWTO: <<
--
-- BEFORE EXECUTE for <APL> REPLACE:	MXXXX -> MX<APL>, XXX -> <APL> (XXX_*), CMN. -> , WATCH FOR INITIAL-Sizes of IDX/TABS
-- CREATING NEW STEP:	USE umgmt@byz2f8:1521/XE, UPDATE ALSO *Schema.sql, init/VERSIONS.csv, SB/OTUserManagement(N).java of foundation-web Project (version-no)!!!


--------------------------------------
-- Alter-Script: CVS v1.32 -> v1.33 --
--------------------------------------
-- Indexing, 2 more OI indexes...

CREATE INDEX UM_USERGROUPS_UGID		ON UM_USERS_GROUPS(	USER_ID, GROUP_ID							) TABLESPACE DOC41WEB_IDX STORAGE(INITIAL 1M) ONLINE;
CREATE INDEX UM_GROUPS_PK_IDX_OI	ON UM_GROUPS(		OBJECT_ID, GROUPNAME, REGIONAL_SCOPE, AD_COUNTRY_CODE			) TABLESPACE DOC41WEB_IDX STORAGE(INITIAL 1M) ONLINE;
CREATE INDEX UM_PGU_PERMISSIONS_UP_IDX	ON UM_PGU_PERMISSIONS(	USER_ID, PERMISSION_ID							) TABLESPACE DOC41WEB_IDX STORAGE(INITIAL 256K) ONLINE;
CREATE INDEX UM_PGU_PERMISSIONS_GP_IDX	ON UM_PGU_PERMISSIONS(	GROUP_ID, PERMISSION_ID							) TABLESPACE DOC41WEB_IDX STORAGE(INITIAL 256K) ONLINE;
CREATE INDEX UM_PGU_PERMISSIONS_PP_IDX	ON UM_PGU_PERMISSIONS(	PROFILE_ID, PERMISSION_ID						) TABLESPACE DOC41WEB_IDX STORAGE(INITIAL 256K) ONLINE;
CREATE INDEX UM_PGU_PERMISSIONS_PPGU_IDX ON UM_PGU_PERMISSIONS(	PERMISSION_ID, USER_ID, GROUP_ID, PROFILE_ID				) TABLESPACE DOC41WEB_IDX STORAGE(INITIAL 256K) ONLINE;

UPDATE DOC41WEB_FDT.Versions SET subVersion=33 WHERE ( module = 'Foundation-UserMgmt' ) AND subVersion=32;
COMMIT WORK;

--------------------------------------
-- Alter-Script: CVS v1.33 -> v1.34 --
--------------------------------------
-- Indexing, 2 more OI indexes...

CREATE INDEX UM_PERMISSIONS_PK_OI_IDX	ON UM_PERMISSIONS( object_Id, code, type	) TABLESPACE DOC41WEB_IDX STORAGE(INITIAL 256K) ONLINE;
CREATE INDEX UM_USERS_PROFILES_UP_IDX	ON UM_USERS_PROFILES( user_Id, profile_Id	) TABLESPACE DOC41WEB_IDX STORAGE(INITIAL 256K) ONLINE;
CREATE INDEX UM_USERS_PROFILES_PU_IDX	ON UM_USERS_PROFILES( profile_Id, user_Id	) TABLESPACE DOC41WEB_IDX STORAGE(INITIAL 256K) ONLINE;

UPDATE DOC41WEB_FDT.Versions SET subVersion=34 WHERE ( module = 'Foundation-UserMgmt' ) AND subVersion=33;
COMMIT WORK;

--------------------------------------
-- Alter-Script: CVS v1.34 -> v1.35 --
--------------------------------------
-- empty alterstep - sqls where added to wrong alter script, corected - therefore this step is now empty...

UPDATE DOC41WEB_FDT.Versions SET subVersion=35 WHERE ( module = 'Foundation-UserMgmt' ) AND subVersion=34;
COMMIT WORK;

--------------------------------------
-- Alter-Script: CVS v1.35 -> v1.36 --
--------------------------------------
-- add new out-of-office message fields for users to set an out-of-office message inside the application (if needed)

-- TREES of UM_Groups, with automanageg TH table (new concept)

ALTER TABLE UM_GROUPS ADD parent_Id NUMBER(27);
CREATE INDEX UMGroups_UMGroups_FK_IDX ON UM_Groups(parent_Id) TABLESPACE DOC41WEB_IDX STORAGE(INITIAL 256K);
ALTER TABLE UM_GROUPS ADD (CONSTRAINT UMGroups_UMGroups_FK FOREIGN KEY (parent_Id) REFERENCES UM_Groups(object_Id) ENABLE VALIDATE); 


CREATE TABLE UM_GROUPS_TH (
  group_Id_Parent   NUMBER(27),
  group_Id_Child    NUMBER(27),
  path_Len          NUMBER(3)  DEFAULT 0 NOT NULL,
  is_Deleted        NUMBER(1)  DEFAULT 0 NOT NULL CONSTRAINT UMGROUPSTH_ISDEL_CHK CHECK (is_Deleted IN (0, 1)),
  is_Parent_Deleted NUMBER(1)  DEFAULT 0 NOT NULL CONSTRAINT UMGROUPSTH_ISPDEL_CHK CHECK (is_Parent_Deleted IN (0, 1))
) TABLESPACE DOC41WEB_IDX STORAGE(INITIAL 2M);

CREATE UNIQUE	INDEX UMGroupsTH_PK_IDX			ON UM_Groups_TH(group_Id_Parent, group_Id_Child)						TABLESPACE DOC41WEB_IDX STORAGE(INITIAL 2M);
CREATE		INDEX UMGroupsTH_UMGroups_P_FK_IDX	ON UM_Groups_TH(group_Id_Parent)								TABLESPACE DOC41WEB_IDX STORAGE(INITIAL 1M);
CREATE		INDEX UMGroupsTH_UMGroups_C_FK_IDX	ON UM_Groups_TH(group_Id_Child)									TABLESPACE DOC41WEB_IDX STORAGE(INITIAL 1M);
CREATE		INDEX UMGroupsTH_UMGroupsP_FK_IDX_OI	ON UM_Groups_TH(group_Id_Parent, group_Id_Child, is_Deleted, is_Parent_Deleted, path_Len)	TABLESPACE DOC41WEB_IDX STORAGE(INITIAL 2M);
CREATE		INDEX UMGroupsTH_UMGroupsC_FK_IDX_OI	ON UM_Groups_TH(group_Id_Child, group_Id_Parent, is_Deleted, is_Parent_Deleted, path_Len)	TABLESPACE DOC41WEB_IDX STORAGE(INITIAL 2M);

ALTER TABLE UM_GROUPS_TH ADD (CONSTRAINT UMGroupsTH_PK			PRIMARY KEY (group_Id_Parent, group_Id_Child) USING INDEX UMGroupsTH_PK_IDX ENABLE VALIDATE);
ALTER TABLE UM_GROUPS_TH ADD (CONSTRAINT UMGroupsTH_UMGroups_P_FK	FOREIGN KEY (group_Id_Parent)	REFERENCES UM_Groups(object_Id) ON DELETE CASCADE ENABLE VALIDATE);
ALTER TABLE UM_GROUPS_TH ADD (CONSTRAINT UMGroupsTH_UMGroups_C_FK	FOREIGN KEY (group_Id_Child)	REFERENCES UM_Groups(object_Id) ON DELETE CASCADE ENABLE VALIDATE);

CREATE OR REPLACE TRIGGER
  UM_GROUPS_GTH_AIU
AFTER INSERT OR UPDATE ON
  UM_GROUPS
FOR EACH ROW
WHEN (
(OLD.object_Id IS NULL) OR (NVL(OLD.parent_Id, -1) <> NVL(NEW.parent_Id, -1)) OR (OLD.objectState_Id <> NEW.objectState_Id)
      )
DECLARE
--  declare variables...
  cnt NUMBER(1) := 0;
--PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN
  IF (:OLD.parent_Id <> NVL(:NEW.parent_Id, -1)) THEN
    UPDATE
      UM_Groups_TH
    SET
      is_Deleted = 1
    WHERE
      (group_Id_Parent <> group_Id_Child) AND 
      (group_Id_Parent IN (SELECT group_Id_Parent FROM UM_Groups_TH WHERE (group_Id_Child  = :OLD.parent_Id) AND (is_Deleted = 0))) AND
      (group_Id_Child  IN (SELECT group_Id_Child  FROM UM_Groups_TH WHERE (group_Id_Parent = :OLD.object_Id) AND (is_Deleted = 0))) AND
      (is_Deleted = 0)
    ;
  END IF;
  IF (INSERTING) THEN
    INSERT INTO UM_Groups_TH( group_Id_Parent, group_Id_Child, path_Len, is_Deleted, is_Parent_Deleted )
      VALUES( :NEW.object_Id, :NEW.object_Id, 0, 0, DECODE(:NEW.objectState_Id, 0, 1, 0) );
  END IF;
  IF (NVL(:OLD.parent_Id, -1) <> :NEW.parent_Id) THEN
    SELECT
      COUNT(group_Id_Parent)
    INTO
      cnt
    FROM
      UM_Groups_TH
    WHERE
      ( group_Id_Parent = :NEW.object_Id ) AND
      ( group_Id_Child  = :NEW.parent_Id ) AND
      ( is_Deleted      = 0              )
    ;
    IF ( cnt > 0 ) THEN
      RAISE_APPLICATION_ERROR( -20555,
        'Illegal path building active cycle, rejected: ' ||
        TO_CHAR( :new.object_Id ) || ' -> ' || TO_CHAR( :new.parent_Id ) || '!'
      );
    END IF;
    UPDATE
      UM_Groups_TH u
    SET
      is_Deleted = 0,
      path_Len = (
        SELECT
          l1.path_Len + l2.path_Len + 1
        FROM
          UM_Groups_TH l1,
          UM_Groups_TH l2
        WHERE
          ( l1.group_Id_Parent = u.group_Id_Parent ) AND
          ( l1.group_Id_Child  = :NEW.parent_Id    ) AND
          ( l2.group_Id_Parent = :NEW.object_Id    ) AND
          ( l2.group_Id_Child  = u.group_Id_Child  ) AND
          ( l1.is_Deleted      = 0                 ) AND
          ( l2.is_Deleted      = 0                 )
      )
    WHERE
      ( group_Id_Parent <> group_Id_Child ) AND
      ( group_Id_Parent IN (
        SELECT
          group_Id_Parent
        FROM
          UM_Groups_TH
        WHERE
          (group_Id_Child = :NEW.parent_Id) AND
          (is_Deleted = 0)
      ) ) AND
      ( group_Id_Child IN (
        SELECT
          group_Id_Child
        FROM
          UM_Groups_TH
        WHERE
          (group_Id_Parent = :NEW.object_Id) AND
          (is_Deleted = 0)
      ) )
    ;
    INSERT INTO UM_Groups_TH( group_Id_Parent, group_Id_Child, path_Len, is_Deleted, is_Parent_Deleted )
      SELECT
        i1.group_Id_Parent, i2.group_Id_Child, i1.path_Len + i2.path_Len + 1, 0, i1.is_Parent_Deleted
      FROM
        (
          SELECT
            group_Id_Parent,
            path_Len,
            is_Parent_Deleted
          FROM
            UM_Groups_TH
          WHERE
            (group_Id_Child = :NEW.parent_Id) AND
            (is_Deleted = 0)
        ) i1
        INNER JOIN (
          SELECT
            group_Id_Child,
            path_Len
          FROM
            UM_Groups_TH
          WHERE
            (group_Id_Parent = :NEW.object_Id) AND
            (is_Deleted = 0)
        ) i2 ON
          (1 = 1)
        LEFT OUTER JOIN UM_Groups_TH e ON
          (i1.group_Id_Parent = e.group_Id_Parent) AND
          (i2.group_Id_Child  = e.group_Id_Child )
      WHERE
        (e.group_Id_Parent IS NULL)
    ;
  END IF;
  IF (:OLD.objectState_Id <> :NEW.objectState_Id) THEN
    UPDATE
      UM_Groups_TH
    SET
      is_Parent_Deleted = DECODE(:NEW.objectState_Id, 0, 1, 0)
    WHERE
      (group_Id_Parent = :NEW.object_Id) AND
      (is_Parent_Deleted <> DECODE(:NEW.objectState_Id, 0, 1, 0))
    ;
  END IF;
--  COMMIT;
EXCEPTION
WHEN NO_DATA_FOUND THEN
  ROLLBACK WORK;
  RAISE_APPLICATION_ERROR( -20555,
    'Parent Node invisible/invalid, update of UMGroups_TH failed: ' ||
    TO_CHAR( :new.object_Id ) || ' -> ' || TO_CHAR( :new.parent_Id ) || '!'
  );
WHEN OTHERS THEN
--  ROLLBACK WORK;
  RAISE;
END UM_GROUPS_GTH_AIU;
/
SHOW ERRORS;

GRANT SELECT ON UM_GROUPS_TH TO MXDOC41WEB WITH GRANT OPTION;

INSERT INTO UM_Groups_TH( group_Id_Parent, group_Id_Child, path_Len, is_Deleted )
  SELECT object_Id, object_Id, 0, 0 FROM UM_Groups
;


ALTER TABLE UM_USERS ADD OUT_OF_OFFICE_START_DATE DATE;
ALTER TABLE UM_USERS ADD OUT_OF_OFFICE_END_DATE   DATE;
ALTER TABLE UM_USERS ADD OUT_OF_OFFICE_MSG VARCHAR2(1000 CHAR);
ALTER TABLE UM_USERS ADD OUT_OF_OFFICE_APPL_ACT_DATE DATE;
ALTER TABLE UM_USERS ADD OUT_OF_OFFICE_APPL_DEACT_DATE DATE;
ALTER TABLE UM_USERS ADD OUT_OF_OFFICE_DEPUTY_USER_CWID VARCHAR2(10 CHAR);

UPDATE DOC41WEB_FDT.Versions SET subVersion=36 WHERE ( module = 'Foundation-UserMgmt' ) AND subVersion=35;
COMMIT WORK;


--------------------------------------
-- Alter-Script: CVS v1.36 -> v1.37 --
--------------------------------------
-- make optionally distributable - masterdata style

-- OPEN MWB_FDT @ PMWB
-- DEV: CPD_FDT @ PMWB

--OBJECTSTATE_ID:
--ALTER TABLE UM_Users		ADD is_Deleted		NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT UMUsers_IsDel_Chk CHECK (is_Deleted		IN (0, 1));
ALTER TABLE UM_Users		ADD do_Ext_Revalidate	NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT UMUsers_DoRev_Chk CHECK (do_Ext_Revalidate	IN (0, 1));
ALTER TABLE UM_Profiles		ADD is_Deleted		NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT UMProfs_IsDel_Chk CHECK (is_Deleted		IN (0, 1));
ALTER TABLE UM_Profiles		ADD do_Ext_Revalidate	NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT UMProfs_DoRev_Chk CHECK (do_Ext_Revalidate	IN (0, 1));
--OBJECTSTATE_ID:
--ALTER TABLE UM_Groups		ADD is_Deleted		NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT UMGroups_IsDel_Chk CHECK (is_Deleted		IN (0, 1));
ALTER TABLE UM_Groups		ADD do_Ext_Revalidate	NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT UMGroups_DoRev_Chk CHECK (do_Ext_Revalidate	IN (0, 1));
--makes sense, BUT should be done only by application ALTER STEPS, NOT by UserMgmt-OTSingleton:
ALTER TABLE UM_Permissions	ADD is_Deleted		NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT UMPerms_IsDel_Chk CHECK (is_Deleted		IN (0, 1));
ALTER TABLE UM_Permissions	ADD do_Ext_Revalidate	NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT UMPerms_DoRev_Chk CHECK (do_Ext_Revalidate	IN (0, 1));
ALTER TABLE UM_Users_Groups	ADD is_Deleted		NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT UMUsGps_IsDel_Chk CHECK (is_Deleted		IN (0, 1));
ALTER TABLE UM_Users_Groups	ADD do_Ext_Revalidate	NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT UMUsGps_DoRev_Chk CHECK (do_Ext_Revalidate	IN (0, 1));
ALTER TABLE UM_Users_Profiles	ADD is_Deleted		NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT UMUsPfs_IsDel_Chk CHECK (is_Deleted		IN (0, 1));
ALTER TABLE UM_Users_Profiles	ADD do_Ext_Revalidate	NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT UMUsPfs_DoRev_Chk CHECK (do_Ext_Revalidate	IN (0, 1));
ALTER TABLE UM_PGU_Permissions	ADD is_Deleted		NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT UMPGUPs_IsDel_Chk CHECK (is_Deleted		IN (0, 1));
ALTER TABLE UM_PGU_Permissions	ADD do_Ext_Revalidate	NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT UMPGUPs_DoRev_Chk CHECK (do_Ext_Revalidate	IN (0, 1));
ALTER TABLE UM_OBJ_Permission	ADD is_Deleted		NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT UMOBJPs_IsDel_Chk CHECK (is_Deleted		IN (0, 1));
ALTER TABLE UM_OBJ_Permission	ADD do_Ext_Revalidate	NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT UMOBJPs_DoRev_Chk CHECK (do_Ext_Revalidate	IN (0, 1));
ALTER TABLE UM_DPT_Permission	ADD is_Deleted		NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT UMDPTPs_IsDel_Chk CHECK (is_Deleted		IN (0, 1));
ALTER TABLE UM_DPT_Permission	ADD do_Ext_Revalidate	NUMBER(1) DEFAULT 0 NOT NULL CONSTRAINT UMDPTPs_DoRev_Chk CHECK (do_Ext_Revalidate	IN (0, 1));

REVOKE DELETE ON UM_PERMISSIONS FROM MXDOC41WEB;

UPDATE DOC41WEB_FDT.Versions SET subVersion=37 WHERE ( module = 'Foundation-UserMgmt' ) AND subVersion=36;
COMMIT WORK;


-- DEV: MWB_FDT @ PMWB/QMWB

-- external Keys:
-- UM_Users		: CWID
-- UM_Profiles		: PROFILENAME
-- UM_Groups		: GROUPNUMBER, MD_SYSTEM_ID_GROUP_NUMBER
-- UM_Permissions	: CODE
-- UM_Users_Groups	: USER_ID, GROUP_ID	= UM_USERS.CWID, UM_GROUPS.GROUPNUMBER, UM_GROUPS.MD_SYSTEM_ID_GROUP_NUMBER
-- UM_Users_Profiles	: USER_ID, PROFILE_ID	= UM_USERS.CWID, UM_PROFILES.CODE
-- UM_PGU_Permissions	: USER_ID, PERMISSION_ID / USER_ID, GROUP_ID / USER_ID, PROFILE_ID = CWID/GROUPNUMBER+MD_SYSTEM_ID_GROUP_NUMBER/PROFILENAME/CODE
-- UM_OBJ_Permission	: USER_ID/GROUP_ID/PROFILE_ID/OBJECT_TABLE_ID/OBJ_TYPE/Permission

-- UPDATE CDTs: key-Prot?! do_Revalidate?!





-- VERSION-UPDATE:
------------------
--UPDATE DOC41WEB_FDT.Versions SET subVersion=## WHERE ( module = 'Foundation-UserMgmt' ) AND subVersion=##;
--COMMIT WORK;

------------------------------------------------------------------
-- $Log: USERMGMTSchema-Alter.sql,v $
-- Revision 1.29  2013/06/03 13:23:49  imwif
-- new alter step for foundation umgmt, prepare soft deletes and marking
-- req. of external resync (LDAP, ADS, etc), corrected one grant
--
-- Revision 1.28  2013/03/19 17:16:30  imwif
-- new alter step defining extra User Columns and an automatic
-- transitive hull for hierarchical Groups
--
-- Revision 1.27  2012/11/23 15:06:38  imwif
-- empty step - sql moved to other alter script (added to wrong location)
--
-- Revision 1.26  2012/09/03 15:08:53  imwif
-- new alterstep , perf opt
--
-- Revision 1.25  2012/08/21 09:04:56  imwif
-- some very usefull perf. indexes
--
-- Revision 1.24  2012/01/19 11:43:51  imwif
-- new version, see alter step corresponding to cvs version of schema script
--
-- Revision 1.23  2011/07/04 13:13:22  imwif
-- added new step, missing indexes
--
-- Revision 1.22  2011/02/23 18:01:13  imwif
-- renamed new column, typo..
--
-- Revision 1.21  2011/02/16 11:58:30  imwif
-- new columns for users + groups
--
-- Revision 1.20  2011/02/15 16:55:30  imwif
-- added alter step to resize descriptions
--
-- Revision 1.19  2011/02/04 13:51:27  imwif
-- 2 new altersteps added, schema script not yet updated
--
-- Revision 1.18  2010/09/27 15:42:05  imwif
-- new alterstep
--
-- Revision 1.17  2010/07/29 08:47:55  imwif
-- added the GRANTs to MXDOC41WEB now to the schema script (less manual investigation req)
--
-- Revision 1.16  2009/12/15 14:47:25  imwif
-- now supports SEARCHKEYS search on Users & Groups
--
-- Revision 1.15  2009/08/25 13:23:26  imwif
-- added missing indexes (some prj have already 4 of the idxs inofficially!!!)
--
-- Revision 1.14  2009/05/29 11:40:32  imwif
-- added new column sponsoredBy
--
-- Revision 1.13  2009/04/03 14:50:33  imwif
-- added how-to
--
-- Revision 1.12  2009/03/27 17:08:05  imwif
-- standard TS/CMN-SCHEMA
--
-- Revision 1.11  2009/01/02 14:10:31  imwif
-- added new function DOC41WEB_FDT.get_Normalized_Time() now used instead
-- of SYSDATE to allow application global control on the applications
-- DB TIMEZONE
--
-- Revision 1.10  2008/08/06 12:27:32  imwif
-- next alter step to support new Umgmt
--
-- Revision 1.9  2006/11/24 12:13:17  imwif
-- added column to um_users for controlling flr3 showlogs
--
-- Revision 1.8  2006/05/02 12:52:33  immsf
-- added new column to UM_PERMISSIONS: orderBy, now UM_PERMISSIONS gets ordered by orderBy
--
-- Revision 1.7  2004/03/11 16:04:37  imwif
-- added check for prev-version when incrementing subversion counter
-- on the new versions table
--
-- Revision 1.6  2004/03/11 12:00:59  imwif
-- not required anymore
--
-- Revision 1.5  2004/03/04 12:50:00  imokl
-- fixed typo
--
-- Revision 1.4  2004/03/04 10:52:28  imwif
-- extended DisplayTextFK-Trigger
--
-- Revision 1.3  2004/01/15 09:34:09  imdst
-- changed update stmt due to inconsistent states on existing DBs
--
-- Revision 1.2  2004/01/14 18:04:23  imwif
-- added ALL missing conversion statements since v1.9 !!!
--
-- Revision 1.1  2004/01/14 16:59:54  imwif
-- ALTER-STATEMENTS for last changes still MISSING!!!
--
