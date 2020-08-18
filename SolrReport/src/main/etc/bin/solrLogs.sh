#!/bin/sh

vDaysAgo=1
vYesterday=`(date --date="$vDaysAgo days ago" '+%Y-%m-%d')`
if [[ $vDaysAgo < 1 ]] 
then
  vFileName="catalina.out"
else
  vFileName="catalina.$vYesterday.log"
fi
#vFileName="catalina.2009-12-*"
vFileName="catalina.out"
vCounter=1;
vCounter2=0; # variable starting with counter 0 for reading values from array, user_logins
#vServer_array=(151.149.116.100 151.149.159.201)
vServer_array=(151.149.116.100)
#vServer_login=(xby2adm jboss)
vServer_login=(xby2adm)
#vServer_logs_path=(/usr/local/solr-tomcat/apache-tomcat-5.5.25/logs /usr/local/solr-tomcat/apache-tomcat-5.5.25/logs)
vServer_logs_path=(/usr/local/solr-tomcat/apache-tomcat-5.5.25/logs)
vDestination_path=/tmp
vMasterLogFile=solrReport.log
vtemp=""

echo "********** Solr copy script start, today's date: $(date +"%Y-%m-%d")  **********"
echo "********** This script copies solr log files from ${vDaysAgo} day ago. **********"
echo ""

for i in ${vServer_array[@]}
do

        echo $vCounter ") Connecting to solr server: " ${i}
        #scp sldev@${i}:~/solr-tomcat/apache-tomcat-5.5.25/log/$vFileName ~/downloads/tmp/ # can use this if the user_logins are same accross all Environments.

        #scp ${vServer_login[$vCounter2]}@${i}:~/solr-tomcat/apache-tomcat-5.5.25/logs/$vFileName $vDestination_path/$vFileName.$vCounter
        scp ${vServer_login[$vCounter2]}@${i}:$vServer_logs_path/$vFileName $vDestination_path/$vFileName.$vCounter

        vResult=$?

        if [[ $vResult = 0 ]]
        then 
                echo "copy success."
                vCounter=$((vCounter+1))
        else 
                echo "!!! ERROR copy FAILED for server: ${i} !!!"
        fi
        vCounter2=$((vCounter2+1))
done

vCounter=$((vCounter-1))
echo ""

if [[ $vCounter > 0 ]]
then 
        echo "Copy from remote servers complete, attempting to create 1 big log file named: ${vMasterLogFile}"

        while [ $vCounter != 0 ]
        do
                vtemp=${vtemp}" "$vFileName.$vCounter
                #echo "vtemp: ${vtemp}"
                vCounter=$((vCounter-1))
        done

        echo "** Now running: cat $vtemp >> $vMasterLogFile"
        echo ""

        cd $vDestination_path

        cat $vtemp >> ${vMasterLogFile}

        #deleting temporary log files
        rm -f $vFileName.*

        ls -ltr $vMasterLogFile*  
fi

echo ""
echo "***********         Solr copy script end                    ***********"
