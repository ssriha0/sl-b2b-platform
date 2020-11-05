#!bin/bash

echo "INFO LOG: Path :: $1"

echo "INFO LOG: ************ Starting deploying DB scripts ************* "
sh $1/JenkinsDeployementScripts/PreProd_Scripts/DDL_DML_script_preprod.sh $1
if [ $? -eq 0 ];then	
	echo "INFO LOG: ************ Starting deploying artifacts ************* "
	sh $1/JenkinsDeployementScripts/PreProd_Scripts/jenkins_deployment_preprod.sh $1
	if [ $? -eq 0 ]; then
		echo "INFO LOG: Deploying artifacts is completed"
	else
		echo "FATAL ERROR: Deployment is failed. Please check logs above."
	fi;
else
	echo "FATAL ERROR: Execution of DDL_DML script is failed. Please check logs above."
fi;