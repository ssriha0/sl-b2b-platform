#!/bin/bash
echo "About to run the pgp command..."
PGPFILE=$1
INPUTFILE=$2
GPGERR=2
gpg --yes -eq --trust-model always -r "asandler@kmart.com" -o ${PGPFILE} ${INPUTFILE}
status=$?
echo "return status is $status"

if [ ${status} -ne 0 ] ; then
   
   echo "************************************************************************************************"
   echo "                  error : gpg encryption failed                  "
   echo "************************************************************************************************"	
   exit ${status}

else
   echo "info : gpg action successful"
fi
