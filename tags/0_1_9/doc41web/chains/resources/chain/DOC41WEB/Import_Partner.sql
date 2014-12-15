//#############################################################################
//# author:  Ingeborg Menge (KEI)
//# history: v1.1    20.12.2013  Initial version
//#          v1.2    06.01.2014  KEI: Partner -> Customer & Vendor
//#          v1.3    ...         ...
//#############################################################################

#ECHO_ON

// --- Fixed Identifiers ---
// (none)

// --- Property Identifiers ---
#DEFINE_PARM CSV-FILE .csv-file
#DEFINE_PARM TABNAM .table-name
#DEFINE_PARM SCHEMA .table-schema
#DEFINE_PARM PROCESS .process
#DEFINE_PARM ENCODING .encoding

// --- Args Identifiers
// (none)


#ON_ERRORS SHOW

#IMPORT_CSV_FILE (ECHO) (ENCODING=[ENCODING]) (+ERRORLOG) (KEYS=PARTNER_NUMBER) (EXCLUDE=PARTNER_TYPE) [CSV-FILE].csv *[SCHEMA].[TABNAM]
#INFO [PROCESS] -- [REJECTED_ROWS.clear("0").cassign("WARNING","CHANNEL")][CHANNEL.embed("<",">")][PROCESS]: [INSERTED_ROWS.clear("-1").default("?")] new rows inserted, [UPDATED_ROWS.clear("-1").default("?")] rows updated, [REJECTED_ROWS.clear("-1").default("?")] rows rejected for [TABNAM].
