#!/bin/sh

###################################################
# This script is used to start, stop and index
# Solr Report server.
# Author  : Shekhar Nirkhe (cnirkhe)
# Date    : 04/29/2009
# Veriosn : 0.3
###################################################


######### Global Setting ############
#change global setting in config.sh
#####################################


######### Do not modify beyond this line #######
current="$(readlink -f $(dirname "$0"))"
if  test -f $current/config.sh 
then
   source $current/config.sh 
else
  echo "Error:Config file not found";
  exit;
fi



cmd () {
  if [ $# -eq 0 ]
  then
   echo "solr -start|-stop|-index|-log|-deploy <Zip File>";   
   return;
  fi

  if [ $1 = "-start" ]
  then    
    start;
    return;
  fi
  
   if [ $1 = "-restart" ]
  then    
    restart;
    return;
  fi
  
  if [ $1 = "-stop" ]
  then
    stop;
    return;
  fi
  
  if [ $1 = "-index" ]
  then  
   index;
    return;
  fi
  
  if [ $1 = "-delete" ]
  then   
    deleteIndex;    
    return;
  fi
  
  if [ $1 = "-deploy" ]
  then   
    deploy $2;    
    return;
  fi
  
  if [ $1 = "-log" ]
  then 
    filename=`ls -ltr $SOLR_HOME/${TOMCAT}/logs |tail -1|tr -s " "|  cut -d " " -f9`;
    tail -f $SOLR_HOME/${TOMCAT}/logs/$filename; 
    return;
  fi
  
  if [ $1 = "-report" ]
  then
    report;
    return;
  fi

  
  echo "No such command"
}

index() {
  echo "Indexing started at `date`";
  #rm -rf $SOLR_HOME/solr/data/*;
  #restart;
  #sleep 30;
  #$current/solrLogs.sh
  echo "Indexing report"
  indexB2C;
  perl  $current/solrReport.pl /tmp/solrReport.log
  doIndex /tmp/solr_out.csv;
}

doIndex() {
  unset http_proxy;
  
  OUTFILE=$1
  count=`wc -l $OUTFILE`;
  echo "Output: $OUTPUT"
  echo "Feeding $count records to solr server ${SOLR_SERVER} at `date`"
  curl "http://${SOLR_SERVER}/solr/update/csv?commit=true&stream.contentType=text/plain;charset=utf-8&stream.file="$OUTFILE
}

indexB2C() {
  INPUT_SQL=$SOLR_HOME/bin/b2c.sql;
  OUTFILE=/tmp/b2c.tmp
  echo "Going to database $DBHOSTB2C at `date`"
  mysql -h $DBHOSTB2C --port=$DBPORTB2C -u $DBLOGINB2C -p$DBPASSB2C -D $DBB2C < $INPUT_SQL > $OUTFILE;
}

deleteIndex() {
  unset http_proxy
  curl http://${SOLR_SERVER}/solr/update --data-binary '<delete><query>*:*</query></delete>'  -H 'Content-type:text/plain; charset=utf-8'
  curl http://${SOLR_SERVER}/solr/update --data-binary '<commit/>'  -H 'Content-type:text/plain; charset=utf-8'
}

start() {
len=`ps -aef|grep org.apache.catalina.startup.Bootstrap|grep solr-tomcat-report|wc -l`
if [ $len = 0 ]
then
  cd $SOLR_HOME;
  sh ./${TOMCAT}/bin/startup.sh
  echo "Solr server started";
else
    echo "Solr server already running";
fi 
}

stop() {
cd $SOLR_HOME;
sh ./${TOMCAT}/bin/shutdown.sh     
sleep 2;
len=`ps -aef|grep org.apache.catalina.startup.Bootstrap|grep solr|wc -l`
if [ $len = 0 ]
then
  echo "Solr server stopped";
else
   pid=`ps -aef|grep org.apache.catalina.startup.Bootstrap|grep solr|tr -s " "|cut -d " " -f2`
   kill -9 $pid;
   echo "Solr server killed";
fi
}

restart() {
  stop;
  echo "Waiting for 60 Sec";
  sleep 60;
  start;
}

deploy() {
  if test -f $1
  then
    /usr/bin/unzip -o $1 -d  $SOLR_HOME/solr/conf/
    mv $SOLR_HOME/bin/cmd.sh $SOLR_HOME/bin/cmd.sh.backup; 
    mv $SOLR_HOME/solr/conf/cmd.sh $SOLR_HOME/bin;
    mv $SOLR_HOME/solr/conf/*.sh $SOLR_HOME/bin;
    chmod +x $SOLR_HOME/bin/cmd.sh;
#    index;    
    echo "Deployment successful"
  else
    echo "Provide a valid zip file path"   
 fi
}

report () {
  TS=`date '+%Y-%m-%d'`;
  logfile=$SOLR_HOME/${TOMCAT}/logs/catalina.${TS}.log;
  report=$SOLR_HOME/$TOMCAT/webapps/solr/admin/nomatch.txt
  grep 'qt=slDisMax' $logfile|grep 'hits=0' | awk '{split($0,a,"&q="); print a[2]}'|cut -d "&" -f1 > /tmp/tmp1.tmp
  echo "======== $TS ==========" >> $report;
  cat /tmp/tmp1.tmp | sort| uniq >> $report;
  echo "==============================" >> $report;
  rm /tmp/tmp1.tmp;
}


main() {
 DIR=`pwd`;
 cmd $1 $2 $3;
 cd $DIR;
}

main $1 $2 $3;
