#!bin/bash

echo "INFO LOG: Path :: $1"

echo "INFO LOG: ************ Starting deploying DB scripts ************* "
sh $1/JenkinsDeployementScripts/Dev_Scripts/DDL_DML_script_dev.sh $1
if [ $? -eq 0 ];then	
	echo "INFO LOG: ************ Starting deploying artifacts ************* "
	sh $1/JenkinsDeployementScripts/Dev_Scripts/jenkins_deployement_dev.sh $1
	if [ $? -eq 0 ]; then
		echo "INFO LOG: Deploying artifacts is completed"
	else
		echo "FATAL ERROR: Deployment is failed. Please check logs above."
	fi;
else
	echo "FATAL ERROR: Execution of DDL_DML script is failed. Please check logs above."
fi;