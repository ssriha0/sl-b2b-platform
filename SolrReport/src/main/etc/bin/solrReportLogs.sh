#!/bin/sh

##########################################################
# This script is used to create solr report from B2C DB
# Solr server.
# Author  : Shekhar Nirkhe (cnirkhe)
# Date    : 04/29/2009
# Veriosn : 0.1
#########################################################


######### Global Setting ############
#change global setting in config.sh
#####################################


current="$(readlink -f $(dirname "$0"))"
if  test -f $current/config.sh
then
   source $current/config.sh
else
  echo "Error:Config file not found";
  exit;
fi


TS=`getPDate`;
indexB2C() {
  INPUT_SQL=$SOLR_HOME/bin/b2c.sql;
  OUTFILE=$SOLRREPORT_DIR/b2c.$TS.log;
  echo "Going to database $DBHOSTB2C at `date`"
  mysql -h $DBHOSTB2C --port=$DBPORTB2C -u $DBLOGINB2C -p$DBPASSB2C -D $DBB2C < $INPUT_SQL > $OUTFILE;
  echo "Created file $OUTFILE";
}

getPDate(){

# Today's date formatted:
TODAY=`date +'%m-%d-%Y'`

# Get individual elements
MONTH=`echo $TODAY | cut -d'-' -f1`
DAY=`echo $TODAY | cut -d'-' -f2`
YEAR=`echo $TODAY | cut -d'-' -f3`

if [[ `expr $DAY + 0` -eq 1 ]]; then
if [[ $MONTH -eq 1 ]]; then
MONTH=12
YEAR=`expr $YEAR - 1`
else
MONTH=`expr $MONTH - 1`
fi

cal $MONTH $YEAR | grep 31 1>/dev/null 2>&1
if [[ $? -eq 0 ]]; then
DAY=31
else
DAY=30
fi
else
DAY=`expr $DAY + 0`
DAY=`expr $DAY - 1`
fi

if [[ `echo $MONTH | wc -c` -eq 2 ]]; then
MONTH=0$MONTH
fi
if [[ `echo $DAY | wc -c` -eq 2 ]]; then
DAY=0$DAY
fi

# Previous day in the same format, without hyphens
NEW_DATE=$YEAR-$MONTH-$DAY
#return $NEW_DATE 
echo "$NEW_DATE";

} 
indexB2C;
cp $SOLR_HOME/$TOMCAT/logs/catalina..$TS.log  $SOLRREPORT_DIR/.
