#!bin/bash

source $1/JenkinsDeployementScripts/QA2_Scripts/DDL_DML_script_qa2.config

revert_build_db_scripts(){

echo "Reverting DB scripts.."
#Check for release name
release_count=`tr -dc ',' <<< "$all_releases" | wc -c `

while [ $release_count -ne 0 ]; do
release=`(cut -d "," -f$release_count <<< ${all_releases})`
echo "$release DB scripts getting reverted"
count=`ls $path/$release | wc -l`
if [ $count -ne 0 ]; then
	list_of_schema_names=`ls $path/$release`  #schema_names=accounts_prod servicelive_qrtz supplier_prod 
	i=1	
	while [ $i -le $count ]; do
	#take one schema in sequence
		schema_name=`(cut -d " " -f$i <<< ${list_of_schema_names})`
		echo "inside "$schema_name 
		ls /appl/jenkins/DDL_DML_trace/$env/$release/$schema_name/revert_trace.txt
		if [ $? -eq 0 ]; then
		all_revert_sql_files=`sed -e "s/ //g" <<< $(cat /appl/jenkins/DDL_DML_trace/$env/$release/$schema_name/revert_trace.txt)`
		revert_sql_count=`tr -dc ',' <<< "$all_revert_sql_files" | wc -c `
				
		#iterate revert_trace starting from first script
		while [ $revert_sql_count -gt 0 ]; do
			
			run_revert_script=`(cut -d "," -f$revert_sql_count <<< ${all_revert_sql_files})`
				
			revert_script=`grep -F "Rollback_"$run_revert_script $path/$release/$schema_name/Rollback/revertorder.txt`
			echo "Executing rollback script $revert_script"
				 
			ssh -i $path_to_private_key $user@$application_server_hosts "ls /tmp/mysql_scripts/$release/$schema_name/Rollback"
			if [ $? -ne 0 ]; then
				ssh -i $path_to_private_key $user@$application_server_hosts "mkdir -p /tmp/mysql_scripts/$release/$schema_name/Rollback"
			fi;
			
			scp -i $path_to_private_key $path/$release/$schema_name/Rollback/$revert_script $user@$application_server_hosts:/tmp/mysql_scripts/$release/$schema_name/Rollback
											
			ssh -i $path_to_private_key $user@$application_server_hosts "mysql -s -u $uname -p$pword -h $hostNm -P$portNo -D $schema_name -e \"SET SQL_SAFE_UPDATES = 0;\""
			ssh -i $path_to_private_key $user@$application_server_hosts "mysql -s -u $uname -p$pword -h $hostNm -P$portNo -D $schema_name -e \"source /tmp/mysql_scripts/$release/$schema_name/Rollback/$revert_script\""
			if [ $? -eq 0 ]; then
				echo "$revert_script ran successfully..."
				rm -r /appl/jenkins/DDL_DML_trace/$env/$release/$schema_name/revert_trace.txt
			else
				echo "FATAL ERROR occured while Rollback executing DB script ::: $revert_script. Please correct your revert script and check-in again.."
			fi; 
		revert_sql_count=$(($revert_sql_count-1))
		done
		fi;
	i=$((i+1))
	done
fi;
release_count=$(($release_count-1))
done
}