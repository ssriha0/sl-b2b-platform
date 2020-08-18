#!/bin/sh

###################################################
# This script is used to start, stop and index
# Solr server.
# Author  : Shekhar Nirkhe (cnirkhe)
# Date    : 04/29/2009
# Veriosn : 0.7
###################################################


######### Global Setting ############
#change global setting in config.sh
#####################################


######### Do not modify beyond this line #######
port=0
current="$(readlink -f $(dirname "$0"))"
if  test -f $current/config.sh
then
   source $current/config.sh
else
  echo "Error:Config file not found";
  exit;
fi



cmd () {
  unset http_proxy;
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

indexOld() {
  echo "Indexing started at `date`";
  rm -rf $SOLR_HOME/solr/data/*;
  restart;
  sleep 30;
  echo "Indexing skilltree"
  doIndex $SOLR_HOME/bin/skillTree.sql /tmp/skill.tmp;
  echo "Indexing zip"
  indexZip; 
  echo "Indexing provider"
  doIndex $SOLR_HOME/bin/provider.sql /tmp/provider.tmp;   
  echo "Indexing done at `date`"
}

index() {
  echo "Indexing started at `date`";
   
  echo "Indexing skilltree"
  doIndex $SOLR_HOME/bin/skillTree.sql /tmp/skill.tmp 'S';
  
  echo "Indexing zip"  
  indexZip;
  echo "Indexing provider"
  
  doIndex $SOLR_HOME/bin/provider.sql /tmp/provider.tmp 'P';  
  buildSpell;  
  echo "Indexing done at `date`"
}

doIndex() {
  unset http_proxy;
 
  INPUT_SQL=$1
  OUTFILE=$2

  if  test -f $OUTFILE
  then
    rm $OUTFILE;
  fi
  echo "Going to database $DBHOST at `date`"
  mysql -h $DBHOST --port=$DBPORT -u $DBLOGIN -p$DBPASS -D $DB < $INPUT_SQL > $OUTFILE;
  count=`wc -l $OUTFILE`;
  deleteIndex $3;
  echo "Feeding $count records to solr server ${SOLR_SERVER} at `date`"
  curl "http://${SOLR_SERVER}/solr/update/csv?commit=true&separator=%09&escape=\&stream.file="$OUTFILE  
}

indexZip() {
  OUTFILE=$SOLR_HOME/bin/zipcode.index;
  count=`wc -l $OUTFILE`;
  deleteIndex 'Z';
  echo "Feeding $count zipcodes to solr server ${SOLR_SERVER} at `date`"
  curl "http://${SOLR_SERVER}/solr/update/csv?commit=true&separator=%09&escape=\&stream.file="$OUTFILE
}

buildSpell() {
curl "http://${SOLR_SERVER}/solr/select/?q=paintin&qt=dismax&spellcheck=true&spellcheck.collate=true&spellcheck.build=true"
}

deleteIndex() { 
  curl http://${SOLR_SERVER}/solr/update --data-binary '<delete><query>id:$1*</query></delete>'  -H 'Content-type:text/plain; charset=utf-8'
  curl http://${SOLR_SERVER}/solr/update --data-binary '<commit/>'  -H 'Content-type:text/plain; charset=utf-8'
}

start() {
#len=`ps -aef|grep org.apache.catalina.startup.Bootstrap|grep -v solr-tomcat-report |grep solr|wc -l`
getPort;
len=`netstat -na|grep $port|grep LISTEN|wc -l`;

if [ $len = 0 ]
then
  cd $SOLR_HOME;
  sh ./${TOMCAT}/bin/startup.sh
  echo "Solr server started";
else
    pid=`netstat -nap|grep $port|grep java|tr -s " "|cut -d" " -f7|cut -d\/ -f1|head -1`;
    echo "Solr or some other process is already running on port:$port with pid:$pid";
fi
}

stop() {
cd $SOLR_HOME;
#sh ./${TOMCAT}/bin/shutdown.sh    
getPort;
len=`netstat -nap 2>&1 |grep $port|grep java|tr -s " "|cut -d" " -f7|cut -d\/ -f1|wc -l`;
#len=`ps -aef|grep org.apache.catalina.startup.Bootstrap|grep solr|wc -l`
if [ $len = 0 ]
then
  echo "Solr server not running";
else
   #pid=`ps -aef|grep org.apache.catalina.startup.Bootstrap|grep solr|tr -s " "|cut -d " " -f2`
   pid=`netstat -nap 2>&1 |grep $port|grep java|tr -s " "|cut -d" " -f7|cut -d\/ -f1|head -1`;
   kill $pid;
   echo "Please wait ..."
   sleep 10;
   len=`netstat -nap 2>&1 |grep $port|grep java|tr -s " "|cut -d" " -f7|cut -d\/ -f1|wc -l`;
   if [ $len = 0 ]
   then
     echo "Solr server stopped";
   else
     kill -9 $pid;
     echo "Solr server killed with $pid";
   fi
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
    #mv $SOLR_HOME/bin/cmd.sh $SOLR_HOME/bin/cmd.sh.backup;
    #mv $SOLR_HOME/solr/conf/cmd.sh $SOLR_HOME/bin;
    mv $SOLR_HOME/solr/conf/*.sh $SOLR_HOME/bin;
    chmod +x $SOLR_HOME/bin/cmd.sh;
    #index;   
    echo "Deployment successful"
  else
    echo "Provide a valid zip file path"  
fi
}

getPort() {
  line=`grep -n 'Define a non-SSL HTTP' ${SOLR_HOME}/${TOMCAT}/conf/server.xml|cut -f1 -d:`;
  nline=`expr $line + 1`;
  port=`head -$nline ${SOLR_HOME}/${TOMCAT}/conf/server.xml |tail -1|cut -d\" -f2`
  port=$port;
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
