//#############################################################################
//# author:  Ingeborg Menge (KEI)
//# history: v1.1    17.12.2013  Initial version
//#          v1.2    ...         ...
//#          v1.3    ...         ...
//#############################################################################

#ECHO_OFF

// --- Fixed Identifiers ---
#DEFINE FORMAT_DDATE 'YYYYMMDD'
#DEFINE FORMAT_MDATE 'YYYYMM'
#DEFINE FORMAT_YDATE 'YYYY'
#DEFINE FORMAT_DQUOT ''''
#DEFINE FORMAT_MQUOT '01'''
#DEFINE FORMAT_YQUOT '0101'''

// --- Property Identifiers ---
#DEFINE_PARM SCHEMA .table-schema
#DEFINE_PARM PROCESS .process
#DEFINE_PARM BASE_DIR :dir.csv

// --- Args Identifiers
// (none)


#ON_ERRORS SHOW

// Delete Monitoring History older then six month

#QUERY DEL_LIMIT
SELECT
  [FORMAT_DQUOT] ||
  TO_CHAR(ADD_MONTHS(systimestamp AT TIME ZONE 'UTC', -6), [FORMAT_MDATE]) ||
  [FORMAT_MQUOT]
FROM
  DUAL
;

#QUERY_CSV [BASE_DIR]Cleanup_Mon_History.csv
SELECT OBJECT_ID FROM [SCHEMA].IM_MONITORING_HISTORY
  WHERE
    CHANGED < TO_DATE([DEL_LIMIT], [FORMAT_DDATE])
;

#IMPORT_CSV_FILE (AUTOCOMMIT=1) (KEYS=OBJECT_ID) @[BASE_DIR]Cleanup_Mon_History.csv [SCHEMA].IM_MONITORING_HISTORY
#INFO [PROCESS]: [DELETED_ROWS] rows deleted until [DEL_LIMIT] (Monitoring History).
