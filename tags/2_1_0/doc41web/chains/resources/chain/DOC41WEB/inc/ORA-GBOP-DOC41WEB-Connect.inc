# INCLUDE FOR CONNECTING TO THE ORACLE GBOP (DOC41WEB)
#############################################################################
# author:  Ingeborg Menge (KEI)
# history: v1.1    12.06.2013  KEI: Initial version
#          v1.2    ...         ...
#          v1.3    ...         ...
#############################################################################
#
#
# connection:
.db=jdbc:oracle:thin:@ldap://by-oida.bayer-ag.com:389/gbop,cn=OracleContext,dc=lev,dc=de,dc=bayer,dc=cnb ldap://by-oidb.bayer-ag.com:389/gbop,cn=OracleContext,dc=lev,dc=de,dc=bayer,dc=cnb
.user=MXDOC41WEB
.password=*CPFGHTG58
.table-schema=DOC41WEB_MGR
.table-user=DOC41WEB_MGR
.table-password=*CPF4VJQF
#
#
.mgr-user=DOC41WEB_MGR
#
.cmn-user=DOC41WEB_FDT
#.flr-user=DOC41WEB_MGR
.comp-password=*CPF4VJQF
#
#
# DBObjectSQLServer options:
.objects.autorestore=false
#.catalog=null
#.schema=null
#.initialcommit=false
.autorollback=false
#.transactionlevel=??????
#.rollback.reconnect=true
.oracle.defaultprefetch=100
.maxconnects=1
.initialconnects=1
#
# DBObjectSQLServerScript options:
#.debug.maxexport=30
#.sqlecho=INFO
#.csvdelimiter=,
#.csvex.esc.double=true
#.templatehash.initialsize=10000
#.templatehash.resizelimit=75
#
# Debugging:
#.trace.all=false
#.showlog=false
