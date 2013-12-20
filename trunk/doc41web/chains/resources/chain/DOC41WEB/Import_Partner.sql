//#############################################################################
//# author:  Ingeborg Menge (KEI)
//# history: v1.1    20.12.2013  Initial version
//#          v1.2    ...         ...
//#          v1.3    ...         ...
//#############################################################################

#ECHO_ON

// --- Fixed Identifiers ---
// (none)

// --- Property Identifiers ---
#DEFINE_PARM CSV-FILE .csv-file
#DEFINE_PARM SCHEMA .table-schema
#DEFINE_PARM PROCESS .process
#DEFINE_PARM ENCODING .encoding

// --- Args Identifiers
// (none)


#ON_ERRORS SHOW

#IMPORT_CSV_FILE (ECHO) (ENCODING=[ENCODING]) (+ERRORLOG) (KEYS=PARTNER_NUMBER) [CSV-FILE].csv *[SCHEMA].SAP_PARTNER
#INFO [PROCESS] -- [REJECTED_ROWS.clear("0").cassign("WARNING","CHANNEL")][CHANNEL.embed("<",">")][PROCESS]: [INSERTED_ROWS.clear("-1").default("?")] new rows inserted, [UPDATED_ROWS.clear("-1").default("?")] rows updated, [REJECTED_ROWS.clear("-1").default("?")] rows rejected.
