#!bin/bash

echo "INFO LOG: Path :: $1"
source $1/JenkinsDeploymentScripts/QA2_Scripts/jenkins_deployment_qa2.config

ALERT=80
svc_server_space=$(ssh -i $path_to_private_key $user@$all_marketplatform_hosts "df -h /appl|grep '/appl'" )
current_svc_space=`echo $svc_server_space | awk '{print $5}' |awk -F\% ' {print $1 }'`

batch_server_space=$(ssh -i $path_to_private_key $user@$all_batch_hosts "df -h /appl|grep '/appl'" )
current_batch_space=`echo $batch_server_space | awk '{print $5}' |awk -F\% ' {print $1 }'`

app_server_space=$(ssh -i $path_to_private_key $user@$all_marketfrontend_hosts "df -h /appl|grep '/appl'" )
current_app_space=`echo $app_server_space | awk '{print $5}' |awk -F\% ' {print $1 }'`

web_server_space=$(ssh -i $path_to_private_key $user@$all_webserver_hosts "df -h /var|grep '/var'" )
current_web_space=`echo $web_server_space | awk '{print $5}' |awk -F\% ' {print $1 }'`


if [ $current_svc_space -gt $ALERT ];then		

ssh -i $path_to_private_key $user@$all_marketplatform_hosts "cd $all_deployables_MarketPlatform_RemoteService; rm -r \`ls | grep $all_marketplatform_war_name | head -n -2\`"
ssh -i $path_to_private_key $user@$all_marketplatform_hosts "cd $all_deployables_Wallet_RemoteService; rm -r \`ls | grep $all_walletremote1_war_name | head -n -2\`"
ssh -i $path_to_private_key $user@$all_marketplatform_hosts "cd $all_deployables_OrderFulfillment_Service; rm -r \`ls | $grep $all_sl_order1_war_name | head -n -2\`"
ssh -i $path_to_private_key $user@$all_marketplatform_hosts "cd $all_deployables_OrderFulfillment_Jobs; rm -r \`ls | grep $all_sl_orderjobs1_war_name | head -n -2\`"
ssh -i $path_to_private_key $user@$all_marketplatform_hosts "cd /appl/sl_api1/deployables; rm -r \`ls | grep $all_sl_api_war_name | head -n -2\`"
ssh -i $path_to_private_key $user@$all_marketplatform_hosts "cd /appl/sl_api1/deployables; rm -r \`ls | grep $all_callback_war_name | head -n -2\`"
ssh -i $path_to_private_key $user@$all_marketplatform_hosts "cd /appl/sl_api1/deployables; rm -r \`ls | grep $all_provider_war_name | head -n -2\`"
ssh -i $path_to_private_key $user@$all_marketplatform_hosts "cd /appl/sl_api2/deployables; rm -r \`ls | grep $all_sl_api_war_name | head -n -2\`"
ssh -i $path_to_private_key $user@$all_marketplatform_hosts "cd /appl/sl_api2/deployables; rm -r \`ls | grep $all_callback_war_name | head -n -2\`"
ssh -i $path_to_private_key $user@$all_marketplatform_hosts "cd /appl/sl_api2/deployables; rm -r \`ls | grep $all_provider_war_name | head -n -2\`"
ssh -i $path_to_private_key $user@$all_marketplatform_hosts "cd $all_deployables_mobile; rm -r \`ls | grep $all_sl_mobileapi_war_name | head -n -2\`"

fi;
		
if [ $current_batch_space -gt $ALERT ];then

ssh -i $path_to_private_key $user@$all_batch_hosts "cd $all_deployables_batch; rm -r \`ls | grep $all_sl_batch_war_name | head -n -2\`"
ssh -i $path_to_private_key $user@$all_batch_hosts "cd $all_deployables_batch; rm -r \`ls | grep $all_sl_spn_batch_war_name | head -n -2\`"
ssh -i $path_to_private_key $user@$all_batch_hosts "cd $all_deployables_batchpymt; rm -r \`ls | $grep $all_sl_batchpymt_war_name | head -n -2\`"
ssh -i $path_to_private_key $user@$all_batch_hosts "cd $all_deployables_mdb; rm -r \`ls | grep $all_sl_mdb_war_name | head -n -2\`"
ssh -i $path_to_private_key $user@$all_batch_hosts "cd $all_deployables_srv; rm -r \`ls | grep $all_sl_srv_war_name | head -n -2\`"

fi;

if [ $current_app_space -gt $ALERT ];then

ssh -i $path_to_private_key $user@$all_marketfrontend_hosts "cd $all_deployables_marketfrontend; rm -r \`ls | grep $all_sl_frontend_war_name | head -n -2\`"
ssh -i $path_to_private_key $user@$all_batch_hosts "cd $all_deployables_marketfrontend; rm -r \`ls | grep $all_sl_ServiceliveWebUtil_war_name | head -n -2\`"
ssh -i $path_to_private_key $user@$all_batch_hosts "cd $all_deployables_marketfrontend; rm -r \`ls | $grep $all_sl_spn_war_name | head -n -2\`"

fi;

if [ $current_web_space -gt $ALERT ];then

ssh -i $path_to_private_key $user@$all_marketfrontend_hosts "cd $all_WebServer_Path; rm -r \`ls -tr| grep ServiceLiveWebUtil | head -n -2\`"
ssh -i $path_to_private_key $user@$all_batch_hosts "cd $all_WebServer_Path; rm -r \`ls -tr| grep StaticWeb | head -n -2\`"

fi;

if [[ ($current_svc_space -lt $ALERT) && ($current_batch_space -lt $ALERT) && ($current_app_space -lt $ALERT) && ($current_web_space -lt $ALERT) ]]; then

	echo "INFO LOG: ************ Starting deploying DB scripts ************* "
	sh $1/JenkinsDeploymentScripts/QA2_Scripts/DDL_DML_script_qa2.sh $1
	if [ $? -eq 0 ];then	
		echo "INFO LOG: ************ Starting deploying artifacts ************* "
		sh $1/JenkinsDeploymentScripts/QA2_Scripts/jenkins_deployment_qa2.sh $1
		if [ $? -eq 0 ]; then
			echo "INFO LOG: Deploying artifacts is completed"
		else
			echo "FATAL ERROR: Deployment is failed. Please check logs above."
		fi;
	else
		echo "FATAL ERROR: Execution of DDL_DML script is failed. Please check logs above."
	fi;
else
      echo "Still memory space is full please clean space manually...."
fi;