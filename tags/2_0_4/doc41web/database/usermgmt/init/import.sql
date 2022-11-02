#ECHO_ON
#ON_ERRORS SHOW

#DEFINE_PARM SCHEMA .defaultschema
#DEFINE_ARGS SCHEMA,FDTSCHEMA

#DEFINE SERIALSQL [DATABASE.copy(0,5).upper().trim().switch("{call [FDTSCHEMA.defaultT(\"[SCHEMA]\")].get_next_oid(?,?)}","MICRO","SELECT [FDTSCHEMA.defaultT(\"[SCHEMA]\")].get_next_OID() FROM DUAL")]

#IMPORT_CSV_DIR (ECHO) (ERRORLOG) *csvlist [SCHEMA]
#IMPORT_CSV_DIR (ECHO) (ERRORLOG) (AUTOID=OBJECT_ID) *csvlistOID [SCHEMA]
#IMPORT_CSV_DIR (ECHO) (ERRORLOG) *csvlistFDT [FDTSCHEMA.defaultT("[SCHEMA]")]

#QUERY ADM_ID
SELECT object_Id FROM [SCHEMA].UM_Users WHERE CWID = '_ADM_';

#QUERY ADM_PROF_ID
SELECT object_Id FROM [SCHEMA].UM_Profiles WHERE ProfileName = 'adm';

#QUERY CNT
SELECT COUNT(*) FROM [SCHEMA].UM_Users_Profiles WHERE (user_Id = [ADM_ID]) AND (profile_Id = [ADM_PROF_ID]);

#ON_VALUE CNT (0) SWITCH ,end
#OBJECT_IDS 1
INSERT INTO [SCHEMA].UM_Users_Profiles ( object_Id, user_Id, profile_Id ) VALUES ([1], [ADM_ID], [ADM_PROF_ID]);
#INFO Assigned adm profile to the superuser: [1], [ADM_ID], [ADM_PROF_ID].

:end
