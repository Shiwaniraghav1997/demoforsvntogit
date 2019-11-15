#!/bin/bash
##################################################################
# project:      DOC41WEB
# filename:     cronDOC41WEBSAP-DB.sh
# task:         Import data from SAP
#               Host CHAIN_DOC41WEBHOST (same as for BOE2)
# author:       Ingeborg Menge (KEI)
# changed:      18.12.2013
#               03.01.2014 KEI: Change log name
# arguments:    $1: extension of the logfiles -> Chain-$1[-Run].log
#               $2: SAP data file name 	i.e. BUS_CUST.txt
#               $3: data name     			i.e. BUS_Customer
#               $4: Chain script name		i.e. BUS_Customer-SAP-DB
##################################################################
#
#
##set -x

###### used shell variables ######
AKTDATETIME=`date +'%d.%m.%Y-%H%M%S'`   
CHAINLOGID=${1}
REPORTNAME=${2}
REPORTLOCAL=${3}
CHAIN1=${CHAIN}/${4}.chn
LOG=${CHAIN_LOG}/${CHAINENV}-Transfer.log
REMOTEDIR=${CHAIN_DOC41WEBDIR:+cd ${CHAIN_DOC41WEBDIR}}

cd ${CHAIN_BIN}/${CHAIN_REPORT}
rm -f ${REPORTLOCAL}
sftp ${CHAIN_DOC41WEBUSER}@${CHAIN_DOC41WEBHOST} >> ${CHAIN_BIN}/${LOG} 2>&1 <<end
   ${REMOTEDIR}
   get ${REPORTNAME} ${REPORTLOCAL}
   exit
end

if [ -a "${REPORTLOCAL}" ]
then
	cd ${CHAIN_BIN}
	echo "Report ${REPORTNAME} at ${AKTDATETIME} received  " >>${LOG}
########## DELTA-SECTION-1 ###########
	./delta.sh       ${CHAIN_REPORT}/${REPORTLOCAL} 1
######################################
	./Chain-#.sh "${CHAINLOGID}" ${CHAIN1}
########## DELTA-SECTION-2 ###########
	./delta-check.sh ${CHAIN_REPORT}/${REPORTLOCAL} $?
	./delta-done.sh  ${CHAIN_REPORT}/${REPORTLOCAL}
	cd ${CHAIN_REPORT}
	compress -f ${REPORTLOCAL}.UPD
	compress -f ${REPORTLOCAL}.DEL
	OTTDATE=`date +'%Y-%m-%d-%H%M%S'`
	mv -f ${REPORTLOCAL}.UPD.Z ${3}-${OTTDATE}.csv.UPD.Z
	mv -f ${REPORTLOCAL}.DEL.Z ${3}-${OTTDATE}.csv.DEL.Z
	mv -f ${3}-*.???.Z received
######################################
	cd ${CHAIN_BIN}
else
	cd ${CHAIN_BIN}
	echo "Error: Report ${REPORTNAME} at ${AKTDATETIME} not received" >>${LOG}
	echo "Error: Report ${REPORTNAME} at ${AKTDATETIME} not received"
fi
