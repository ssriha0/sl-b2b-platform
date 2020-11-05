#!bin/bash

echo "Path :: $1"

sh $1/JenkinsDeployementScripts/QA2_Scripts/DDL_DML_script_qa2.sh $1
if [ $? -eq 0 ];then
	echo "DDL_DML_scripts ran successfully. Starting with deploying artifacts"
	sh $1/JenkinsDeployementScripts/QA2_Scripts/jenkins_deployment_qa2.sh $1
	if [ $? -eq 0 ]; then
		echo "Deploying artifacts is successfully completed"
	else
		echo "Deployment is failed. Please check logs above."
	fi;
else
	echo "Execution of DDL_DML script is failed. Please check logs above."
fi;