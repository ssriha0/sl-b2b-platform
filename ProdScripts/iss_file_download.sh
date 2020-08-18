
##################################################################
# This script downloads the OMS file from remote box and copies to
# /appl/sl/iss/ directories
##################################################################

HOST=ftp://storm.shs.sears.com
USER=risuser
PASSWORD=ris1user
INDATA_DIR=/appl/sl/iss/indata/
INDATA_ARCHIVE_DIR=/appl/sl/iss/archive/indata/
OUTDATA_DIR=/appl/sl/iss/outdata
OUTDATA_ARCHIVE_DIR=/appl/sl/iss/archive/outdata
FILE_REG_EXP=SLSSO.*.*.xml

cd /appl/sl/iss/intmp/
#download file first
lftp -u $USER,$PASSWORD $HOST <<EOF
mget -E $OUTDATA_DIR/$FILE_REG_EXP
bye
EOF

#Now, create a copy of the same file in remote archive folder
#For some reason, the mv command did not work in lftp session
#lftp -u $USER,$PASSWORD $HOST <<EOF
#mput -O $OUTDATA_ARCHIVE_DIR $FILE_REG_EXP
#bye
#EOF

cp $FILE_REG_EXP $INDATA_DIR

# Rename files to "startProcessGW" extension because JBOSS ESB uses this
# to trigger the process.
for i in $INDATA_DIR/*.xml;
do mv $i ${i%%.xml}.xml.startProcessGW;
done

cp $FILE_REG_EXP $INDATA_ARCHIVE_DIR

rm $FILE_REG_EXP

