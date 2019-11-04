#!/bin/bash
##################################################################
# project:   DOC41WEB
# filename:  Chain-DOC41WEB-2.sh
# task:      Cleanup DOC41WEB database (once a month)
# author:    Ingeborg Menge (KEI)
# changed:   17.12.2013
# arguments: none
##################################################################


cd ${CHAIN_BIN}

set -e
./StartProc.sh $0
set +e

### Cleanup history tables
	./Chain-#.sh "DOC41WEB-2" ${CHAIN}/Cleanup_Histories.chn

### Save Logs ###
./saveLogs.sh DOC41WEB-2

./StopProc.sh $0
