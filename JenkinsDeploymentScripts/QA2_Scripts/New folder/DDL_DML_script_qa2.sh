#!bin/bash

echo "Path to config: $1/"
source $1/JenkinsDeployementScripts/QA2_Scripts/DDL_DML_script_qa2.config

echo "Path :: $path"

echo "DDL/DML script has been ran by:"
whoami

#Check for release name
release_count=`tr -dc ',' <<< "$all_releases" | wc -c `

while [ $release_count -ne 0 ]; do

echo $all_releases "are going to be deployed"
echo $release_count "number of releases"
release=`(cut -d "," -f$release_count <<< ${all_releases})`
echo "$release DB scripts getting executed"
#Check if the schema name is present in $release 
count=`ls $path/$release | wc -l`

	if [ $count -ne 0 ]; then
	list_of_schema_names=`ls $path/$release`  #schema_names=accounts_prod servicelive_qrtz supplier_prod 
	i=1	
	while [ $i -le $count ]; do
	
	    #take one schema from sequence
		schema_name=`(cut -d " " -f$i <<< ${list_of_schema_names})`
		echo "inside "$schema_name 
		## Executing the  regular runorder.txt
		ls $path/$release/$schema_name/runorder.txt		
		if [ $? -eq 0 ];then
			
			echo "runorder.txt is present inside "$schema_name"..." 
				#Check if the trace file is present at fixed path on jenkins server if not create path and file first
				ls /appl/jenkins/DDL_DML_trace/$env/$release/$schema_name
				if [ $? -ne 0 ]; then
					mkdir -p /appl/jenkins/DDL_DML_trace/$env/$release/$schema_name #create fixed file path
					mkdir -p /appl/jenkins/DDL_DML_trace/$env/$release/$schema_name/Rollback
					#echo "directory for trace_runorder file got created"
				else
					echo "contents of /appl/jenkins/DDL_DML_trace/$env/$release/$schema_name are as above "
				fi;
				
				ls /appl/jenkins/DDL_DML_trace/$env/$release/$schema_name/trace_runorder.txt		
				if [ $? -ne 0 ]; then
					touch /appl/jenkins/DDL_DML_trace/$env/$release/$schema_name/trace_runorder.txt  #create trace_runorder on jenkins location
					touch /appl/jenkins/DDL_DML_trace/$env/$release/$schema_name/revert_trace.txt #tracing file to rollback complete build
					#echo "trace_runorder.txt file got created on jenkins .."					
				else
					echo "trace_runorder.txt contents are as above ..."
				fi;
		
		
				all_sql_files=`sed -e "s/ //g" <<< $(cat $path/$release/$schema_name/runorder.txt)`
				sql_count=`tr -dc ',' <<< "$all_sql_files" | wc -c `
				j=1
				
				#iterate trace runorder starting from first script
				while [ $j -le $sql_count ]; do
				
					sql_script=`(cut -d "," -f$j <<< ${all_sql_files})`
					
					all_trace_runorder_files=`sed -e "s/ //g" <<< $(cat /appl/jenkins/DDL_DML_trace/$env/$release/$schema_name/trace_runorder.txt)`
					 
					#search $sql_script in trace_runorder.sql file
					temp=`grep -F -c $sql_script /appl/jenkins/DDL_DML_trace/$env/$release/$schema_name/trace_runorder.txt`
					
					if [ $temp -eq 1 ];then						
						j=$((j+1))
						continue
					else
						
						#Running DDL block
						gen_DDL='GEN_DDL'
						env_DDL=$env"_DDL"
						
						if [[ ( "$sql_script" == *"$gen_DDL"* ) || ( "$sql_script" == *"$env_DDL"* ) ]] ; then
							
							echo "Executing "$sql_script
							#need to create directory with jboss ownership on every environment
							#echo "Checking if /tmp/mysql_scripts/$release/$schema_name directory is present, if yes showing contents of directory else creating directory"
							ssh -i $path_to_private_key $user@$application_server_hosts "ls /tmp/mysql_scripts/$release/$schema_name"
							if [ $? -ne 0 ]; then
								ssh -i $path_to_private_key $user@$application_server_hosts "mkdir -p /tmp/mysql_scripts/$release/$schema_name"
							fi;
							scp -i $path_to_private_key $path/$release/$schema_name/$sql_script $user@$application_server_hosts:/tmp/mysql_scripts/$release/$schema_name
							#echo "Copied $sql_script to /tmp/mysql_scripts/$release/$schema_name "


							#ssh -i $path_to_private_key $user@$application_server_hosts "ls /tmp/mysql_scripts/$release/$schema_name"
							
							ssh -i $path_to_private_key $user@$application_server_hosts "mysql -s -u $uname -p$pword -h $hostNm -P$portNo -D $schema_name -e \"SET SQL_SAFE_UPDATES = 0;\""
							ssh -i $path_to_private_key $user@$application_server_hosts "mysql -s -u $uname -p$pword -h $hostNm -P$portNo -D $schema_name -e \"source /tmp/mysql_scripts/$release/$schema_name/$sql_script\""
							
							if [ $? -eq 0 ]; then
								
								echo "$sql_script ran successfully..."
								#update trace_order.sql file with last executed script from runorder.sql file
								echo "$sql_script," >> /appl/jenkins/DDL_DML_trace/$env/$release/$schema_name/trace_runorder.txt


								trace_revert_scripts=$trace_revert_scripts$sql_script,
								echo "$sql_script," >> /appl/jenkins/DDL_DML_trace/$env/$release/$schema_name/revert_trace.txt								
								#if [ $? -eq 0 ];then
								#	echo "added $sql_script to trace_runorder.txt"
								#else
								#	echo "error in adding $sql_script to trace_runorder.txt"
								#fi;
								
							else
								#Revert logic from Rollback directory 	
								echo "Reverting ::: Reverting the failed $sql_script script"
								
								trace_revert_scripts=$trace_revert_scripts$sql_script,
																
								trace_revert_count=`tr -dc ',' <<< "$trace_revert_scripts" | wc -c `
								echo "$trace_revert_count scripts to be reverted"		
								
								while [ $trace_revert_count -gt 0 ];do
																	
									run_revert_script=`(cut -d "," -f$trace_revert_count <<< ${trace_revert_scripts})`
									
									revert_script=`grep -F "Rollback_"$run_revert_script $path/$release/$schema_name/Rollback/revertorder.txt`
									echo "Executing rollback script $revert_script"
									 
									#echo "Checking if /tmp/mysql_scripts/$release/$schema_name/Rollback directory is present, if yes showing contents of directory else creating directory"
									ssh -i $path_to_private_key $user@$application_server_hosts "ls /tmp/mysql_scripts/$release/$schema_name/Rollback"
									if [ $? -ne 0 ]; then
										ssh -i $path_to_private_key $user@$application_server_hosts "mkdir -p /tmp/mysql_scripts/$release/$schema_name/Rollback"
									fi;
									
									scp -i $path_to_private_key $path/$release/$schema_name/Rollback/$revert_script $user@$application_server_hosts:/tmp/mysql_scripts/$release/$schema_name/Rollback
																
									ssh -i $path_to_private_key $user@$application_server_hosts "mysql -s -u $uname -p$pword -h $hostNm -P$portNo -D $schema_name -e \"SET SQL_SAFE_UPDATES = 0;\""
									ssh -i $path_to_private_key $user@$application_server_hosts "mysql -s -u $uname -p$pword -h $hostNm -P$portNo -D $schema_name -e \"source /tmp/mysql_scripts/$release/$schema_name/Rollback/$revert_script\""	
	                            
									if [ $? -eq 0 ]; then									
										echo "$revert_script ran successfully..."
										#update trace_order.sql file with last executed script from runorder.sql file
										#echo "$revert_script" >> /appl/jenkins/DDL_DML_trace/$env/$release/$schema_name/Rollback/revert_trace_runorder.txt																			
									else									
										echo "Error occured while Rollback executing DB script ::: $revert_script. Please correct your revert script and check-in again.."																			
									fi; #end of if rollback success/fails				
									
								trace_revert_count=$(($trace_revert_count-1))								
								done		
							 exit 1;
							fi;	#end of $sql_script success/fails							
						fi; #End of running DDL scripts
						
						#Running DDL block
						gen_DML='GEN_DML'
						env_DML=$env"_DML"
						
						if [[ ( "$sql_script" == *"$gen_DML"* ) || ( "$sql_script" == *"$env_DML"* ) ]] ; then
							
							echo "Executing "$sql_script
							#need to create directory with jboss ownership on every environment
							ssh -i $path_to_private_key $user@$application_server_hosts "ls /tmp/mysql_scripts/$release/$schema_name"
							if [ $? -ne 0 ]; then
								ssh -i $path_to_private_key $user@$application_server_hosts "mkdir -p /tmp/mysql_scripts/$release/$schema_name"
							fi;
							scp -i $path_to_private_key $path/$release/$schema_name/$sql_script $user@$application_server_hosts:/tmp/mysql_scripts/$release/$schema_name
							#echo "Copied $sql_script to /tmp/mysql_scripts/$release/$schema_name "
							ssh -i $path_to_private_key $user@$application_server_hosts "mysql -s -u $uname -p$pword -h $hostNm -P$portNo -D $schema_name -e \"source /tmp/mysql_scripts/$release/$schema_name/$sql_script\""
									
							if [ $? -eq 0 ]; then
								
								echo "$sql_script ran successfully..."
								#update trace_order.sql file with last executed script from runorder.sql file
								echo "$sql_script," >> /appl/jenkins/DDL_DML_trace/$env/$release/$schema_name/trace_runorder.txt
								trace_revert_scripts=$trace_revert_scripts$sql_script,


								echo "$sql_script," >> /appl/jenkins/DDL_DML_trace/$env/$release/$schema_name/revert_trace.txt	
								#if [ $? -eq 0 ];then
								#	echo "added" $sql_script "to trace_runorder.txt "
								#else
								#	echo "error in adding " $sql_script " to trace_runorder.txt"
								#fi;
								
							else
								echo "Reverting ::: reverting failed $sql_script"
								#Revert logic from Rollback directory 								
								
								trace_revert_scripts=$trace_revert_scripts$sql_script,
																
								trace_revert_count=`tr -dc ',' <<< "$trace_revert_scripts" | wc -c `
								echo "$trace_revert_count scripts to be reverted"
								
								while [ $trace_revert_count -gt 0 ];do
																	
									run_revert_script=`(cut -d "," -f$trace_revert_count <<< ${trace_revert_scripts})`
									
									revert_script=`grep -F "Rollback_"$run_revert_script $path/$release/$schema_name/Rollback/revertorder.txt`
									echo "Executing rollback script $revert_script"
									
									#echo "Checking if /tmp/mysql_scripts/$release/$schema_name/Rollback directory is present, if yes showing contents of directory else creating directory"
									ssh -i $path_to_private_key $user@$application_server_hosts "ls /tmp/mysql_scripts/$release/$schema_name/Rollback"
									if [ $? -ne 0 ]; then
										ssh -i $path_to_private_key $user@$application_server_hosts "mkdir -p /tmp/mysql_scripts/$release/$schema_name/Rollback"
									fi;
									
									scp -i $path_to_private_key $path/$release/$schema_name/Rollback/$revert_script $user@$application_server_hosts:/tmp/mysql_scripts/$release/$schema_name/Rollback
									#echo "Copied $revert_script to /tmp/mysql_scripts/$release/$schema_name/Rollback "								
									ssh -i $path_to_private_key $user@$application_server_hosts "mysql -s -u $uname -p$pword -h $hostNm -P$portNo -D $schema_name -e \"SET SQL_SAFE_UPDATES = 0;\""
									ssh -i $path_to_private_key $user@$application_server_hosts "mysql -s -u $uname -p$pword -h $hostNm -P$portNo -D $schema_name -e \"source /tmp/mysql_scripts/$release/$schema_name/Rollback/$revert_script\""	
	                            
									if [ $? -eq 0 ]; then									
										echo "$revert_script ran successfully..."
										#update trace_order.sql file with last executed script from runorder.sql file
										#echo "$revert_script," >> /appl/jenkins/DDL_DML_trace/$env/$release/$schema_name/Rollback/revert_trace_runorder.txt
									else									
										echo "Error occured while Rollback executing DB script ::: $revert_script. Please correct your revert script and check-in again.."																				
									fi; #end of if rollback success/fails									
								trace_revert_count=$(($trace_revert_count-1))								
								done	
							  exit 1; 	
							fi; #End of if DML script ran successfully or not
						fi; #End of running DML script block
					fi; #end of if block - if $sql_script is present 
				j=$((j+1)) # next run sql script
				done #end of while iterate sql scripts in runorder
		fi;#End if runorder.txt is present or not
	i=$((i+1)) # next schema
	done #End while iterate schema 
	fi; #End if of schema more than zero
release_count=$(($release_count-1))
done