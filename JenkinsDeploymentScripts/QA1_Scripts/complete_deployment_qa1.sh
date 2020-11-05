#!bin/bash

echo "INFO LOG: Path :: $1"

echo "INFO LOG: ************ Starting deploying DB scripts ************* "
sh $1/JenkinsDeployementScripts/QA1_Scripts/DDL_DML_script_qa1.sh $1
if [ $? -eq 0 ];then	
	echo "INFO LOG: ************ Starting deploying artifacts ************* "
	sh $1/JenkinsDeployementScripts/QA1_Scripts/jenkins_deployment_qa1.sh $1
	if [ $? -eq 0 ]; then
		echo "INFO LOG: Deploying artifacts is completed"
	else
		echo "FATAL ERROR: Deployment is failed. Please check logs above."
	fi;
else
	echo "FATAL ERROR: Execution of DDL_DML script is failed. Please check logs above."
fi;