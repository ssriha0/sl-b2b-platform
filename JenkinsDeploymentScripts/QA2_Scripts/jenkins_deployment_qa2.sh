#!bin/bash

echo "INFO LOG: Path :: $1"
echo "INFO LOG: Jenkins job is is getting executed by:" 
whoami

source $1/JenkinsDeployementScripts/QA2_Scripts/jenkins_deployment_qa2.config
source $1/JenkinsDeployementScripts/QA2_Scripts/revert_failed_build_dbscripts_qa2.sh

echo "INFO LOG: Starting deployement of WAR file"

all_jvm_deployed=""
all_revert_hosts=""
all_revert_deployables=""
all_revert_deploy_scripts=""

deploy_war(){

	## Starting deployment on jvms
	# $2 = all_marketplatform_hosts
	# $3 = all_deployables_MarketPlatform_RemoteService
	# $4 = all_script_MarketPlatform_RemoteService
	# $5 = all_marketplatform_war_name
	# $6 = jenkins_path_MarketPlatform_RemoteService
	# $7 = log_file_path_MarketPlatform
	
	i=1
	count=`tr -dc ',' <<< "$2" | wc -c `   
	count=$(($count+1))
	while [ $i -le $count ]; do
	
	jvm_hosts=`(cut -d "," -f$i <<< $2)`
	deployables_jvm=`(cut -d "," -f$i <<< $3)`
	deploy_script_jvm=`(cut -d "," -f$i <<< $4)`
	jvm_war=`(cut -d "," -f$i <<< $5)`
	jvm_log=`(cut -d "," -f$i <<< $7)`
	
	echo "INFO LOG: Deploying on $2"
	echo "INFO LOG: Pasting at $deployables_jvm"
	echo "INFO LOG: Script name : $deploy_script_jvm used to deploy $5"	
	echo "INFO LOG: Copying $5"
	echo "INFO LOG: Started deploying $5"
	
	scp -i $path_to_private_key $6 $user@$jvm_hosts:$deployables_jvm		
	ssh -i $path_to_private_key $user@$jvm_hosts "/appl/sm/jboss/manageApp82 deploy $deploy_script_jvm"
	
		if [ $? -eq 0 ]; then
		
			temp="grep -F -c 'Deployed \"$jvm_war\"' $jvm_log"  
			temp1=`ssh -i $path_to_private_key $user@$jvm_hosts "$temp"`
	
			if [ $temp1 -eq 1 ]; then
					echo "SUCCESS LOG: Deployment of $jvm_war is SUCCESSFUL at location $deployables_jvm"					
					all_jvm_deployed=$all_jvm_deployed$jvm_war,	
					all_revert_hosts=$all_revert_hosts$jvm_hosts,
					all_revert_deployables=$all_revert_deployables$deployables_jvm,
					all_revert_deploy_scripts=$all_revert_deploy_scripts$deploy_script_jvm,
					
			else 
				echo "ERROR LOG: Deployment of $jvm_war FAILED with ERRORS below. If not below look above if local.property file is missing??" 	
				print_error="grep -B5 -A20 'ERROR' $jvm_log"
				ssh -i $path_to_private_key $user@$jvm_hosts "$print_error"
				
				echo "ERROR LOG: Deployment of $jvm_war is Failed. Reverting the current build."					
				undeploy_war 
				revert_build_db_scripts		
				echo "SUCCESS LOG: Build is reverted successfully"
				
				release_count=`tr -dc ',' <<< "$all_releases" | wc -c `
				
				while [ $release_count -ne 0 ]; do
					release=`(cut -d "," -f$release_count <<< ${all_releases})`
					
					rm -r /appl/jenkins/DDL_DML_trace/$env/$release/$schema_name/revert_trace.txt
					if [ $? -eq 0 ]; then 
					echo "INFO LOG: Deleted /appl/jenkins/DDL_DML_trace/$env/$release/$schema_name/revert_trace.txt file from Jenkins server"
					fi;
				  release_count=$(($release_count-1))
				done
				
				exit 1;
			fi;	
		fi;		
		i=$(($i+1))
	done
}


undeploy_war() {
			#revert current build artifacts
			revert_jvm_count=`tr -dc ',' <<< "$all_jvm_deployed" | wc -c `
			
			while [ $revert_jvm_count -gt 0 ]; do				
				
				revert_jvm=`(cut -d "," -f$revert_jvm_count <<< $all_jvm_deployed)`
				revert_hosts=`(cut -d "," -f$revert_jvm_count <<< $all_revert_hosts)`
				revert_deployables=`(cut -d "," -f$revert_jvm_count <<< $all_revert_deployables)`
				revert_deploy_scripts=`(cut -d "," -f$revert_jvm_count <<< $all_revert_deploy_scripts)`
								
				old_war=$(ssh -i $path_to_private_key $user@$revert_hosts "cd $revert_deployables; cut -d \" \" -f1 <<< \`ls | grep $revert_jvm | tail -2\` ")
				echo "INFO LOG: Deploying last old_war :: $old_war"
				ssh -i $path_to_private_key $user@$jvm_hosts "cd $revert_deployables; mv $old_war $revert_jvm;/appl/sm/jboss/manageApp82 deploy $revert_deploy_scripts"				
				echo "INFO LOG: Completed deploying $old_war"
			revert_jvm_count=$(($revert_jvm_count-1))
			done		
}
	
	##deploy_war $2 $3 $4 $5 $6 $7 
    ## Starting deployment on marketplatform node
	deploy_war "" $all_marketplatform_hosts $all_deployables_MarketPlatform_RemoteService $all_script_MarketPlatform_RemoteService $all_marketplatform_war_name $jenkins_path_MarketPlatform_RemoteService $log_file_path_MarketPlatform

	## Starting deployment on walletremote node
	deploy_war "" $all_wallet_remoteservices_hosts $all_deployables_Wallet_RemoteService $all_script_Wallet_RemoteService $all_walletremote1_war_name $jenkins_path_Wallet_RemoteService $log_file_path_Wallet_RemoteService
	
	## Starting deployment on order node
	deploy_war "" $all_OrderFulfillment_Service_hosts $all_deployables_OrderFulfillment_Service $all_script_OrderFulfillment_Service $all_sl_order1_war_name $jenkins_path_OrderFulfillment_Service $log_file_path_OrderFulfillment_Service
	
	## Starting deployment on orderjobs node
	deploy_war "" $all_OrderFulfillment_Jobs_hosts $all_deployables_OrderFulfillment_Jobs $all_script_OrderFulfillment_Jobs $all_sl_orderjobs1_war_name $jenkins_path_OrderFulfillment_Jobs $log_file_path_OrderFulfillment_Jobs
	
	#Starting deployment on api node
	deploy_war "" $all_api_hosts $all_deployables_api $all_script_public $all_sl_api_war_name $jenkins_path_public $log_file_path_api
	#Starting deployment of callback war
	deploy_war "" $all_callback_hosts $all_deployables_api $all_script_callback $all_callback_war_name $jenkins_path_callback $log_file_path_api
	#Starting deployment of provider war
	deploy_war "" $all_api_hosts $all_deployables_api $all_script_provider $all_provider_war_name $jenkins_path_provider $log_file_path_api
	
	
	## Starting deployment on mobile node
	deploy_war "" $all_mobile_hosts $all_deployables_mobile $all_script_mobile $all_sl_mobileapi_war_name $jenkins_path_mobile $log_file_path_mobileapi
	
	## Starting deployment on batch node
	deploy_war "" $all_batch_hosts $all_deployables_batch $all_script_MarketBatch $all_sl_batch_war_name $jenkins_path_MarketBatch $log_file_path_batch
	## Starting deployment of spn war
	deploy_war "" $all_batch_hosts $all_deployables_batch $all_script_spn_batch $all_sl_spn_batch_war_name $jenkins_path_spn_batch $log_file_path_batch

	## Starting deployment on batchpymt node
	deploy_war "" $all_batchpymt_hosts $all_deployables_batchpymt $all_script_batchpymt $all_sl_batchpymt_war_name $jenkins_path_batchpymt $log_file_path_batchpymt
	
	## Starting deployment on mdb node
	deploy_war "" $all_mdb_hosts $all_deployables_mdb $all_script_mdb $all_sl_mdb_war_name $jenkins_path_mdb $log_file_path_mdb
	
	## Starting deployment on srv node
    deploy_war "" $all_srv_hosts $all_deployables_srv $all_script_srv $all_sl_srv_war_name $jenkins_path_srv $log_file_path_srv
	
	## Starting deployment on frontend node
	deploy_war "" $all_marketfrontend_hosts $all_deployables_marketfrontend $all_script_MarketFrontend $all_sl_frontend_war_name $jenkins_path_MarketFrontend $log_file_path_frontend
	## Starting deployment of ServiceLiveWebUtil war
	deploy_war "" $all_marketfrontend_hosts $all_deployables_marketfrontend $all_script_ServiceLiveWebUtil $all_sl_ServiceliveWebUtil_war_name $jenkins_path_ServiceLiveWebUtil $log_file_path_frontend
	## Starting deployment of spn war
	deploy_war "" $all_marketfrontend_hosts $all_deployables_marketfrontend $all_script_spn $all_sl_spn_war_name $jenkins_path_spn $log_file_path_frontend
	
	
	## Starting dceployment on webserver - ServiceLiveWebUtil and StaticWeb
	echo "INFO LOG: Starting deployment on webserver"
	echo "INFO LOG: Started deploying ServiceLiveWebUtil.war"
	i=1
	count=`tr -dc ',' <<< "$all_webserver_hosts" | wc -c `
	count=$(($count+1))

	while [ $i -le $count ]; do
		webserver_host=`(cut -d "," -f$i <<< ${all_webserver_hosts})`
		serviceliveWebUtil_path=`(cut -d "," -f$i <<< ${all_ServiceLiveWebUtil_WebServer_Path})`
		staticWeb_path=`(cut -d "," -f$i <<< ${all_StaticWeb_WebServer_Path})`
		webServer_path=`(cut -d "," -f$i <<< ${all_WebServer_Path})`
		
		#at initial stage if /var/www/ServiceLiveWebUtil directory is not present such new environment
		ssh -i $path_to_private_key $user@$webserver_host "ls $serviceliveWebUtil_path"
		if [ $? -ne 0 ]; then
			ssh -i $path_to_private_key $user@$webserver_host "mkdir $serviceliveWebUtil_path" #created file path
			echo "INFO LOG: Directory for ServiceLiveWebUtil got created"
		else			
			echo "INFO LOG: /var/www/ServiceLiveWebUtil already exists "
		fi;
		
		if [ $? -eq 0 ]; then
			#rename old ServiceLiveWebUtil directory to backup and create new dir with same name
			now=`date +"%Y_%m_%d__%H_%M_%S"`						
			ssh -i $path_to_private_key $user@$webserver_host "mv $serviceliveWebUtil_path $webServer_path/ServiceLiveWebUtil_bkp_$now;ls -ltr $webServer_path;mkdir $serviceliveWebUtil_path"	
			scp -i $path_to_private_key $jenkins_path_ServiceLiveWebUtil $user@$webserver_host:$serviceliveWebUtil_path/
			echo "INFO LOG : ServiceLiveWebUtil.war got copied at webserver location"
			ssh -i $path_to_private_key $user@$webserver_host "ls -ltr $serviceliveWebUtil_path/ServiceLiveWebUtil.war"
			if [ $? -eq 0 ]; then
				ssh -i $path_to_private_key $user@$webserver_host "cd $serviceliveWebUtil_path/;jar -vxf ServiceLiveWebUtil.war"
				echo "SUCCESS LOG: Deployment of ServiceLiveWebUtil.war done successfully"
			else
				echo "ERROR LOG: Failed Deployment of ServiceLiveWebUtil.war"
			fi;	
		else
			echo "ERROR LOG: Failed Deployment of ServiceLiveWebUtil.war"
		fi;
		
		
		#Deployment of StaticWeb 
		echo "INFO LOG: Started deploying StaticWeb.war"
		ssh -i $path_to_private_key $user@$webserver_host "ls $staticWeb_path"
		if [ $? -ne 0 ]; then
			ssh -i $path_to_private_key $user@$webserver_host "mkdir $staticWeb_path" #created file path
			echo "INFO LOG: directory for StaticWeb got created"
		else			
			echo "INFO LOG: /var/www/StaticWeb already exists "
		fi;		
		
		if [ $? -eq 0 ]; then
			#rename old StaicWeb directory to backup and create new dir with same name
			now=`date +"%Y_%m_%d__%H_%M_%S"`
			ssh -i $path_to_private_key $user@$webserver_host "mv $staticWeb_path $webServer_path/StaticWeb_bkp_$now;ls -ltr $webServer_path;mkdir $staticWeb_path"	
			scp -i $path_to_private_key $jenkins_path_StaticWeb $user@$webserver_host:$staticWeb_path
			echo "INFO LOG: StaticWeb.war got copied at webserver location"
			ssh -i $path_to_private_key $user@$webserver_host "ls -ltr $staticWeb_path/StaticWeb.war"
			if [ $? -eq 0 ]; then
				ssh -i $path_to_private_key $user@$webserver_host "cd $staticWeb_path/;jar -vxf StaticWeb.war"
				echo "SUCCESS LOG: Deployment of StaticWeb.war done successfully"
			else
				echo "ERROR LOG: Failed Deployment of StaticWeb.war"
			fi;	
		else
			echo "ERROR LOG: Failed Deployment of StaticWeb.war"
		fi;
		
	i=$(($i+1))
	done;

	T_afterDeployement=`date +%s`

	T_diff=$((	T_afterDeployement - T_beforeDeployement ))

	echo "Time taken(sec) to deploy artifacts is ::::::: $T_diff "
	
	## Starting deployment on solr node
	#i=1
	#count=`tr -dc ',' <<< "$all_solr_hosts" | wc -c `
	#count=$(($count+1))
	#
	#
	#while [ $i -le $count ]; do
	#
	#solr_hosts=`(cut -d "," -f$i <<< ${all_solr_hosts})`
	#deployables_solr=`(cut -d "," -f$i <<< ${all_deployables_solr})`
	#script_solr=`(cut -d "," -f$i <<< ${all_script_solr})`
	#
	#echo "Copying solr.war"
	#scp $jenkins_path_solr $user@$solr_hosts:$deployables_solr
	#
	#ssh $user@$solr_hosts "/appl/sm/jboss/manageApp82 deploy $script_solr"
	#if [ $? -eq 0 ]; then
	#echo "Deployment SUCCESSFUL. Deployed at location $deployables_solr"
	#else
	#echo "Deployment of solr.war FAILED"
	#fi;
	#
	#i=$(($i+1))
	#done;

	## Starting deployement on b2cActivityLog node
	#i=1
	#count=`tr -dc ',' <<< "$all_b2cActivityLog_hosts" | wc -c `
	#count=$(($count+1))
	#
	#while [ $i -le $count ]; do
	#
	#b2cActivityLog_hosts=`(cut -d "," -f$i <<< ${all_b2cActivityLog_hosts})`
	#deployables_b2cActivityLog=`(cut -d "," -f$i <<< ${all_deployables_b2cActivityLog})`
	#script_b2cActivityLog=`(cut -d "," -f$i <<< ${all_script_b2cActivityLog})`
	#
	#echo "Copying ActivityLog.Service.war"
	#scp $jenkins_path_b2cActivityLog $user@$b2cActivityLog_hosts:$deployables_b2cActivityLog
	#
	#ssh $user@$b2cActivityLog_hosts "/appl/sm/jboss/manageApp82 deploy $script_b2cActivityLog"
	#if [ $? -eq 0 ]; then
	#echo "Deployment SUCCESSFUL. Deployed at location $deployables_b2cActivityLog"
	#else
	#echo "Deployment of ActivityLog.Service.war FAILED"
	#fi;
	#
	#i=$(($i+1))
	#done;
