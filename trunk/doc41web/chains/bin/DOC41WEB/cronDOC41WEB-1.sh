#!/bin/bash
##################################################################
# project:   DOC41WEB
# filename:  Chain-DOC41WEB-1.sh
# task:      Import SAP Data
# author:    Ingeborg Menge (KEI)
# changed:   20.12.2013
#            03.01.2014  KEI: SAP_PARTNER -> SAP_Partner
# arguments: none
##################################################################


cd ${CHAIN_BIN}

set -e
./StartProc.sh $0
set +e

### Import SAP_PARTNER
${CHAIN_BIN_PRJ}/cronDOC41WEBSAP-DB.sh "DOC41WEB-1" "D41_PARTNER.txt" "SAP_Partner.csv" "Import_Partner"

### Save Logs ###
./saveLogs.sh DOC41WEB-1

./StopProc.sh $0
