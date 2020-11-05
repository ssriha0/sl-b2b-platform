#!bin/bash

echo "Path to config: $1/"
source $1/JenkinsDeployementScripts/QA2_Scripts/DDL_DML_script_qa2.config
echo "Path :: $path"

echo "DDL/DML script has been ran by:"
whoami

#Check if the runorder file is present in $release 
count=`ls $path/$release | wc -l`

	if [ $count -ne 0 ]; then
	list_of_schema_names=`ls $path/$release`  #schema_names=accounts_prod supplier_prod  
	i=1
	while [ $i -le $count ]; do
			
			schema_name=`(cut -d " " -f$i <<< ${list_of_schema_names})`
			echo "inside"$schema_name 
			## Executing the  regular runorder.txt
			ls $path/$release/$schema_name/runorder.txt
			if [ $? -eq 0 ];then
				echo "runorder.txt is present inside "$schema_name"..." 
				#Check if the trace file is present at fixed path on jenkins server if not create path and file first
				ls /appl/jenkins/DDL_DML_trace/$release/$schema_name/$env
				if [ $? -ne 0 ]; then
					mkdir -p /appl/jenkins/DDL_DML_trace/$release/$schema_name/$env/#create fixed file path
					echo "directory for trace_runorder file got created"
				else
					echo "/appl/jenkins/DDL_DML_trace/"$release"/"$schema_name"/"$env" already exists "
				fi;
				
				ls /appl/jenkins/DDL_DML_trace/$release/$schema_name/$env/trace_runorder.txt		
				if [ $? -ne 0 ]; then
					touch /appl/jenkins/DDL_DML_trace/$release/$schema_name/$env/trace_runorder.txt  #create trace_runorder on jenkins location
					echo "trace_runorder.txt file got created on jenkins .."
				else
					echo "trace_runorder.txt already exists"
				fi;
				
				#all_sql_files=`cat $path/$release/$schema_name/runorder.txt` 
				all_sql_files=`sed -e "s/ //g" <<< $(cat $path/$release/$schema_name/runorder.txt)`
				sql_count=`tr -dc ',' <<< "$all_sql_files" | wc -c `
				
				
				#Enter the block if sql_count is greater than zero
				if [ $sql_count -gt 0 ];then 
				
				echo $sql_count"  DB scripts going to run one by one"
				
				#Compare the content of trace_runorder with runorder
				trace_runorder_count=`tr -dc ',' <<< $(cat /appl/jenkins/DDL_DML_trace/$release/$schema_name/$env/trace_runorder.txt) | wc -c` 
									
				j=$(($trace_runorder_count+1))  #we are increasing count by one - to start execution in runorder file from trace_order +1 
				while [ $j -le $sql_count ]; do
					sql_script=`(cut -d "," -f$j <<< ${all_sql_files})`
					echo "Executing "$sql_script
					#need to create directory with jboss ownership on every environment
					ssh -i $path_to_private_key $user@$application_server_hosts "ls /tmp/mysql_scripts/$release/$schema_name/$env/"
					if [ $? -ne 0 ]; then
						ssh -i $path_to_private_key $user@$application_server_hosts "mkdir -p /tmp/mysql_scripts/$release/$schema_name/$env/"
					fi;
					scp -i $path_to_private_key $path/$release/$schema_name/$sql_script $user@$application_server_hosts:/tmp/mysql_scripts/$release/$schema_name/$env/
					echo "Copied $sql_script to /tmp/mysql_scripts/$release/$schema_name/$env/ "
					ssh -i $path_to_private_key $user@$application_server_hosts "mysql -s -u $uname -p$pword -h $hostNm -P$portNo -D $schema_name -e \"source /tmp/mysql_scripts/$release/$schema_name/$env/$sql_script\""
							
					if [ $? -ne 0 ]; then
						echo "Error occured while executing DB script :::" $sql_script
						exit 1;
					else
						echo "$sql_script ran successfully..."
						#update trace_order.sql file with last executed script from runorder.sql file
						echo "$sql_script," >> /appl/jenkins/DDL_DML_trace/$release/$schema_name/$env/trace_runorder.txt
						if [ $? -eq 0 ];then
						echo "added" $sql_script "to trace_runorder.txt"
					else
						echo "error in adding " $sql_script " to trace_runorder.txt"
						fi;
					fi;					
					j=$((j+1))
				done;					
				fi;
			fi;
			echo "Finished executing runorder.txt...and starting runorder environment specific"
		## Executing env specific runorder.txt
			ls $path/$release/$schema_name/runorder_$env.txt
			if [ $? -eq 0 ]; then
				#Compare the content of trace_runorder for specific env with runorder
				echo "runorder_"$env".txt is present inside "$schema_name"..." 
				
				#Check if the trace file is present at fixed path on jenkins server if not create path and file first
				ls /appl/jenkins/DDL_DML_trace/$release/$schema_name/$env/
				if [ $? -ne 0 ]; then
					mkdir -p /appl/jenkins/DDL_DML_trace/$release/$schema_name/$env/ #create fixed file path
					echo "directory for trace_runorder file got created"
				else
					echo "/appl/jenkins/DDL_DML_trace/"$release"/"$schema_name"/"$env" already exists "
				fi;					
						
				ls /appl/jenkins/DDL_DML_trace/$release/$schema_name/$env/trace_runorder_$env.txt
				if [ $? -ne 0 ]; then
					touch /appl/jenkins/DDL_DML_trace/$release/$schema_name/$env/trace_runorder_$env.txt  #create trace_runorder on jenkins location
					echo "trace_runorder.txt file got created on jenkins .."
				else
					echo "trace_runorder.txt already exists"
				fi;			
				
				#all_sql_files=`sed -e "s/ //g" <<< $(cat runorder_Training.txt)`
				all_sql_files=`sed -e "s/ //g" <<< $(cat $path/$release/$schema_name/runorder_$env.txt)`
				sql_count=`tr -dc ',' <<< "$all_sql_files" | wc -c`
				
				#Enter the block if sql_count is greater than zero
				if [ $sql_count -gt 0 ];then						
					echo $sql_count"  DB scripts going to run one by one"
				
					#Compare the content of trace_runorder with runorder
					trace_runorder_count=`tr -dc ',' <<< $(cat /appl/jenkins/DDL_DML_trace/$release/$schema_name/$env/trace_runorder_$env.txt) | wc -c` 
								
					##Compare the content of trace_runorder with runorder
											
					j=$(($trace_runorder_count+1))  #we are increasing count by one - to start execution in runorder file from trace_order +1 
					while [ $j -le $sql_count ]; do
						sql_script=`(cut -d "," -f$j <<< ${all_sql_files})`
						echo "Executing "$sql_script
						#need to create directory with jboss ownership on every environment
						ssh -i $path_to_private_key $user@$application_server_hosts "ls /tmp/mysql_scripts/$release/$schema_name/$env"
						if [ $? -ne 0 ]; then
							ssh -i $path_to_private_key $user@$application_server_hosts "mkdir -p /tmp/mysql_scripts/$release/$schema_name/$env"
						fi;
						scp -i $path_to_private_key $path/$release/$schema_name/$sql_script $user@$application_server_hosts:/tmp/mysql_scripts/$release/$schema_name/$env/
						echo "Copied $sql_script to /tmp/mysql_scripts/$release/$schema_name/$env/"
						ssh -i $path_to_private_key $user@$application_server_hosts "mysql -s -u $uname -p$pword -h $hostNm -P$portNo -D $schema_name -e \"source /tmp/mysql_scripts/$release/$schema_name/$env/$sql_script\""
								
						if [ $? -ne 0 ]; then
							echo "Error occured while executing DB script :::" $sql_script							
							exit 1;
						else
							echo "$sql_script ran successfully..."
							#update trace_runorder_$env.txt file with last executed script from runorder.sql file					
							echo "$sql_script," >> /appl/jenkins/DDL_DML_trace/$release/$schema_name/$env/trace_runorder_$env.txt 
							if [ $? -eq 0 ];then
								echo "added" $sql_script "to trace_runorder_"$env".txt"
							else
								echo "error in adding " $sql_script " to trace_runorder_"$env".txt"
							fi;
						fi;						
						j=$((j+1))
					done;
				fi;						
			fi;			
			i=$((i+1))
		done;		
	fi;
        	
		
			
			

			
			
		
		