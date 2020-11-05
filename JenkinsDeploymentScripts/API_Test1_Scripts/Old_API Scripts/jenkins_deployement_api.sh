#!bin/bash

echo "Path :: $1"

echo "Jenkins job is been ran by:"
whoami

source $1/JenkinsDeployementScripts/API_Test1_Scripts/jenkins_deployement_api.config

echo "Starting deployement of WAR file"

	## Starting deployment on marketplatform node
	i=1
	count=`tr -dc ',' <<< "$all_marketplatform_hosts" | wc -c `
	count=$(($count+1))

	T_beforeDeployement=`date +%s`

	while [ $i -le $count ]; do
	
		marketplatform_hosts=`(cut -d "," -f$i <<< ${all_marketplatform_hosts})`
		deployables_MarketPlatform_RemoteService=`(cut -d "," -f$i <<< ${all_deployables_MarketPlatform_RemoteService})`
		script_MarketPlatform_RemoteService=`(cut -d "," -f$i <<< ${all_script_MarketPlatform_RemoteService})`
		
		marketplatform_war=`(cut -d "," -f$i <<< ${all_marketplatform_war_name})`
		
		echo "Deploying on $marketplatform_hosts"
		echo "Pasting at $deployables_MarketPlatform_RemoteService"
		echo "App name for deploy $script_MarketPlatform_RemoteService"
		
		echo "Copying MarketPlatform.RemoteService.ear"
		scp -i $path_to_private_key $jenkins_path_MarketPlatform_RemoteService $user@$marketplatform_hosts:$deployables_MarketPlatform_RemoteService
		
		ssh -i $path_to_private_key $user@$marketplatform_hosts "/appl/sm/jboss/manageApp82 deploy $script_MarketPlatform_RemoteService"
			
			if [ $? -eq 0 ]; then
				
				temp="grep -F -c 'Deployed \"$marketplatform_war\"' $log_file_path_MarketPlatform"  
				temp1=`ssh -i $path_to_private_key $user@$marketplatform_hosts "$temp"`
					
				if [ $temp1 -eq 1 ]; then
					echo "Deployment of $marketplatform_war is SUCCESSFUL at location $deployables_MarketPlatform_RemoteService"
				else 
					echo "Deployment of $marketplatform_war is Failed "
					exit 1
				fi;			
				echo "Deployment of $marketplatform_war is SUCCESSFUL at location $deployables_MarketPlatform_RemoteService"
			else
				echo "Deployment of $marketplatform_war FAILED"
			fi;
		
		i=$(($i+1))
	done;

	## Starting deployment on walletremote node
	i=1
	count=`tr -dc ',' <<< "$all_wallet_remoteservices_hosts" | wc -c `
	count=$(($count+1))
	
	while [ $i -le $count ]; do
	
		wallet_remoteservices_hosts=`(cut -d "," -f$i <<< ${all_wallet_remoteservices_hosts})`
		deployables_Wallet_RemoteService=`(cut -d "," -f$i <<< ${all_deployables_Wallet_RemoteService})`
		script_Wallet_RemoteService=`(cut -d "," -f$i <<< ${all_script_Wallet_RemoteService})`
		
		wallet_RemoteServices_War=`(cut -d "," -f$i <<< ${all_walletremote1_war_name})`
		
		echo "Deploying on $wallet_remoteservices_hosts"
		echo "Pasting at $deployables_Wallet_RemoteService"
		echo "App name for deploy $script_Wallet_RemoteService"
		
		echo "Copying Wallet.RemoteService.ear"
		scp -i $path_to_private_key $jenkins_path_Wallet_RemoteService $user@$wallet_remoteservices_hosts:$deployables_Wallet_RemoteService
		
		ssh -i $path_to_private_key $user@$wallet_remoteservices_hosts "/appl/sm/jboss/manageApp82 deploy $script_Wallet_RemoteService"
		if [ $? -eq 0 ]; then
			
			temp="grep -F -c 'Deployed \"$wallet_RemoteServices_War\"' $log_file_path_Wallet_RemoteService"  
			temp1=`ssh -i $path_to_private_key $user@$wallet_remoteservices_hosts "$temp"`
				
			if [ $temp1 -eq 1 ]; then
				echo "Deployment of $wallet_RemoteServices_War is SUCCESSFUL at location $deployables_Wallet_RemoteService"
			else 
				echo "Deployment of $wallet_RemoteServices_War is Failed "
				exit 1
			fi;		
			echo "Deployment of $marketplatform_war is SUCCESSFUL at location $deployables_Wallet_RemoteService"
		else
			echo "Deployment of Wallet.RemoteService.ear FAILED"
		fi;
		
		i=$(($i+1))
	done;
	

	## Starting deployment on order node
	i=1
	count=`tr -dc ',' <<< "$all_OrderFulfillment_Service_hosts" | wc -c `
	count=$(($count+1))

	while [ $i -le $count ]; do

		OrderFulfillment_Service_hosts=`(cut -d "," -f$i <<< ${all_OrderFulfillment_Service_hosts})`
		deployables_OrderFulfillment_Service=`(cut -d "," -f$i <<< ${all_deployables_OrderFulfillment_Service})`
		script_OrderFulfillment_Service=`(cut -d "," -f$i <<< ${all_script_OrderFulfillment_Service})`

		orderfulfillment_Service_War=`(cut -d "," -f$i <<< ${all_sl_order1_war_name})`
		
		echo "Deploying on $OrderFulfillment_Service_hosts"
		echo "Pasting at $deployables_OrderFulfillment_Service"
		echo "App name for deploy $script_OrderFulfillment_Service"

		echo "Copying OrderFulfillment.Service.war"
		scp -i $path_to_private_key $jenkins_path_OrderFulfillment_Service $user@$OrderFulfillment_Service_hosts:$deployables_OrderFulfillment_Service

		ssh -i $path_to_private_key $user@$OrderFulfillment_Service_hosts "/appl/sm/jboss/manageApp82 deploy $script_OrderFulfillment_Service"
		if [ $? -eq 0 ]; then
			temp="grep -F -c 'Deployed \"$orderfulfillment_Service_War\"' $log_file_path_OrderFulfillment_Service"  
			temp1=`ssh -i $path_to_private_key $user@$OrderFulfillment_Service_hosts "$temp"`
				
			if [ $temp1 -eq 1 ]; then
				echo "Deployment of $orderfulfillment_Service_War is SUCCESSFUL at location $deployables_OrderFulfillment_Service"
			else 
				echo "Deployment of $orderfulfillment_Service_War is Failed "
				exit 1
			fi;		
			echo "Deployment of $orderfulfillment_Service_War is SUCCESSFUL at location $deployables_OrderFulfillment_Service"		
		else
			echo "Deployment of OrderFulfillment.Service.war FAILED"
		fi;

		i=$(($i+1))
	done;


	## Starting deployment on orderjobs node
	i=1
	count=`tr -dc ',' <<< "$all_OrderFulfillment_Jobs_hosts" | wc -c `
	count=$(($count+1))
	
	while [ $i -le $count ]; do
	
		OrderFulfillment_Jobs_hosts=`(cut -d "," -f$i <<< ${all_OrderFulfillment_Jobs_hosts})`
		deployables_OrderFulfillment_Jobs=`(cut -d "," -f$i <<< ${all_deployables_OrderFulfillment_Jobs})`
		script_OrderFulfillment_Jobs=`(cut -d "," -f$i <<< ${all_script_OrderFulfillment_Jobs})`
		
		orderfulfillment_Jobs_War=`(cut -d "," -f$i <<< ${all_sl_orderjobs1_war_name})`
		
		echo "Copying OrderFulfillment.Jobs.war"
		scp -i $path_to_private_key $jenkins_path_OrderFulfillment_Jobs $user@$OrderFulfillment_Jobs_hosts:$deployables_OrderFulfillment_Jobs
		
		ssh -i $path_to_private_key $user@$OrderFulfillment_Jobs_hosts "/appl/sm/jboss/manageApp82 deploy $script_OrderFulfillment_Jobs"
		if [ $? -eq 0 ]; then
			temp="grep -F -c 'Deployed \"$orderfulfillment_Jobs_War\"' $log_file_path_OrderFulfillment_Jobs"  
			temp1=`ssh -i $path_to_private_key $user@$OrderFulfillment_Jobs_hosts "$temp"`
				
			if [ $temp1 -eq 1 ]; then
				echo "Deployment of $orderfulfillment_Jobs_War is SUCCESSFUL at location $deployables_OrderFulfillment_Jobs"
			else 
				echo "Deployment of $orderfulfillment_Jobs_War is Failed "
				exit 1
			fi;		
			echo "Deployment of $orderfulfillment_Jobs_War is SUCCESSFUL at location $deployables_OrderFulfillment_Jobs"			
		else
			echo "Deployment of OrderFulfillment.Jobs.war FAILED"
		fi;
		
		i=$(($i+1))
	done;
	

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


	#Starting deployment on api node
	i=1
	count=`tr -dc ',' <<< "$all_api_hosts" | wc -c `
	count=$(($count+1))

	while [ $i -le $count ]; do

		api_hosts=`(cut -d "," -f$i <<< ${all_api_hosts})`
		deployables_api=`(cut -d "," -f$i <<< ${all_deployables_api})`
		script_public=`(cut -d "," -f$i <<< ${all_script_public})`
		script_callback=`(cut -d "," -f$i <<< ${all_script_callback})`
		script_provider=`(cut -d "," -f$i <<< ${all_script_provider})`
		
		
		public_War=`(cut -d "," -f$i <<< ${all_sl_api_war_name})`
		callback_War=`(cut -d "," -f$i <<< ${all_callback_war_name})`
		api_logs=`(cut -d "," -f1 <<< ${log_file_path_api})`
	
		echo "Copying public.war calling #script_public "
		scp -i $path_to_private_key $jenkins_path_public $user@$api_hosts:$deployables_api
	
		ssh -i $path_to_private_key $user@$api_hosts "/appl/sm/jboss/manageApp82 deploy $script_public"
		
		if [ $? -eq 0 ]; then			
			temp="grep -F -c 'Deployed \"$public_War\"' $api_logs"  
			temp1=`ssh -i $path_to_private_key $user@$api_hosts "$temp"`
				
			if [ $temp1 -eq 1 ]; then
				echo "Deployment of $public_War is SUCCESSFUL at location $deployables_api"
			else 
				echo "Deployment of $public_War is Failed "
				exit 1
			fi;		
			echo "Deployment of $public_War is SUCCESSFUL at location $deployables_api"
		else
			echo "Deployment of public.war FAILED"
		fi;
	
		if [ $deployables_api == "/appl/sl_api1/deployables" ]; then
		
			echo "Copying callback.war"
			scp -i $path_to_private_key $jenkins_path_callback $user@$api_hosts:$deployables_api
		
			ssh -i $path_to_private_key $user@$api_hosts "/appl/sm/jboss/manageApp82 deploy $script_callback"
			if [ $? -eq 0 ]; then
				temp="grep -F -c 'Deployed \"$callback_War\"' $api_logs"  
				temp1=`ssh -i $path_to_private_key $user@$api_hosts "$temp"`
				
				if [ $temp1 -eq 1 ]; then
					echo "Deployment of $callback_War is SUCCESSFUL at location $deployables_api"
				else 
					echo "Deployment of $callback_War is Failed "
					exit 1
				fi;		
				echo "Deployment of $callback_War is SUCCESSFUL at location $deployables_api"		
			else
				echo "Deployment of callback.war FAILED"
			fi;
		fi;
		i=$(($i+1))
	done;


	## Starting deployment on mobile node
	i=1
	count=`tr -dc ',' <<< "$all_mobile_hosts" | wc -c `
	count=$(($count+1))

	while [ $i -le $count ]; do

		mobile_hosts=`(cut -d "," -f$i <<< ${all_mobile_hosts})`
		deployables_mobile=`(cut -d "," -f$i <<< ${all_deployables_mobile})`
		script_mobile=`(cut -d "," -f$i <<< ${all_script_mobile})`
		
		mobile_War=`(cut -d "," -f$i <<< ${all_sl_mobileapi_war_name})`		

		echo "Copying mobile.war"
		scp -i $path_to_private_key $jenkins_path_mobile $user@$mobile_hosts:$deployables_mobile

		ssh -i $path_to_private_key $user@$mobile_hosts "/appl/sm/jboss/manageApp82 deploy $script_mobile"
		if [ $? -eq 0 ]; then
			temp="grep -F -c 'Deployed \"$mobile_War\"' $log_file_path_mobileapi"  
			temp1=`ssh -i $path_to_private_key $user@$mobile_hosts "$temp"`
				
			if [ $temp1 -eq 1 ]; then
				echo "Deployment of $mobile_War is SUCCESSFUL at location $all_deployables_mobile"
			else 
				echo "Deployment of $mobile_War is Failed "
				exit 1
			fi;		
			echo "Deployment of $mobile_War is SUCCESSFUL at location $all_deployables_mobile"	
		else
			echo "Deployment of mobile.war FAILED"
		fi;

	i=$(($i+1))
	done;


	## Starting deployment on batch node
	i=1
	count=`tr -dc ',' <<< "$all_batch_hosts" | wc -c `
	count=$(($count+1))

	while [ $i -le $count ]; do

		batch_hosts=`(cut -d "," -f$i <<< ${all_batch_hosts})`
		deployables_batch=`(cut -d "," -f$i <<< ${all_deployables_batch})`
		script_MarketBatch=`(cut -d "," -f$i <<< ${all_script_MarketBatch})`
		script_spn=`(cut -d "," -f$i <<< ${all_script_spn})`
		
		marketBatch_War=`(cut -d "," -f$i <<< ${all_sl_batch_war_name})`
		spn_War=`(cut -d "," -f$i <<< ${all_sl_spn_war_name})`

		echo "Copying MarketBatch.war"
		scp -i $path_to_private_key $jenkins_path_MarketBatch $user@$batch_hosts:$deployables_batch

		ssh -i $path_to_private_key $user@$batch_hosts "/appl/sm/jboss/manageApp82 deploy $script_MarketBatch"
		if [ $? -eq 0 ]; then
			temp="grep -F -c 'Deployed \"$marketBatch_War\"' $log_file_path_batch"  
			temp1=`ssh -i $path_to_private_key $user@$batch_hosts "$temp"`
				
			if [ $temp1 -eq 1 ]; then
				echo "Deployment of $marketBatch_War is SUCCESSFUL at location $deployables_batch"
			else 
				echo "Deployment of $marketBatch_War is Failed "
				exit 1
			fi;		
			echo "Deployment of $marketBatch_War is SUCCESSFUL at location $deployables_batch"	
		else
			echo "Deployment of MarketBatch.war FAILED"
		fi;

		echo "Copying spn.war"
		scp -i $path_to_private_key $jenkins_path_spn $user@$batch_hosts:$deployables_batch
		
		ssh -i $path_to_private_key $user@$batch_hosts "/appl/sm/jboss/manageApp82 deploy $script_spn"
		if [ $? -eq 0 ]; then
			temp="grep -F -c 'Deployed \"$spn_War\"' $log_file_path_batch"  
			temp1=`ssh -i $path_to_private_key $user@$batch_hosts "$temp"`
				
			if [ $temp1 -eq 1 ]; then
				echo "Deployment of $spn_War is SUCCESSFUL at location $deployables_batch"
			else 
				echo "Deployment of $spn_War is Failed "
				exit 1
			fi;		
			echo "Deployment of $spn_War is SUCCESSFUL at location $deployables_batch"
		else
			echo "Deployment of spn.war FAILED"
		fi;

	i=$(($i+1))
	done;

	## Starting deployment on batchpymt node
	i=1
	count=`tr -dc ',' <<< "$all_batchpymt_hosts" | wc -c `
	count=$(($count+1))

	while [ $i -le $count ]; do

		batchpymt_hosts=`(cut -d "," -f$i <<< ${all_batchpymt_hosts})`
		deployables_batchpymt=`(cut -d "," -f$i <<< ${all_deployables_batchpymt})`
		script_batchpymt=`(cut -d "," -f$i <<< ${all_script_batchpymt})`
		
		marketBatch_War=`(cut -d "," -f$i <<< ${all_sl_batchpymt_war_name})`

		echo "Copying Wallet.Batch.war"
		scp -i $path_to_private_key $jenkins_path_batchpymt $user@$batchpymt_hosts:$deployables_batchpymt

		ssh -i $path_to_private_key $user@$batchpymt_hosts "/appl/sm/jboss/manageApp82 deploy $script_batchpymt"
		if [ $? -eq 0 ]; then
			temp="grep -F -c 'Deployed \"$marketBatch_War\"' $log_file_path_batchpymt"  
			temp1=`ssh -i $path_to_private_key $user@$batchpymt_hosts "$temp"`
				
			if [ $temp1 -eq 1 ]; then
				echo "Deployment of $marketBatch_War is SUCCESSFUL at location $deployables_batchpymt"
			else 
				echo "Deployment of $marketBatch_War is Failed "
				exit 1
			fi;		
			echo "Deployment of $marketBatch_War is SUCCESSFUL at location $deployables_batchpymt"
		else
			echo "Deployment of Wallet.Batch.war FAILED"
		fi;

	i=$(($i+1))
	done;



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



	## Starting deployment on mdb node
	i=1
	count=`tr -dc ',' <<< "$all_mdb_hosts" | wc -c `
	count=$(($count+1))

	while [ $i -le $count ]; do

		mdb_hosts=`(cut -d "," -f$i <<< ${all_mdb_hosts})`
		deployables_mdb=`(cut -d "," -f$i <<< ${all_deployables_mdb})`
		script_mdb=`(cut -d "," -f$i <<< ${all_script_mdb})`
		
		mdb_War=`(cut -d "," -f$i <<< ${all_sl_mdb_war_name})`

		echo "Copying Wallet.ValueLink.EJB.ear"
		scp -i $path_to_private_key $jenkins_path_mdb $user@$mdb_hosts:$deployables_mdb

		ssh -i $path_to_private_key $user@$mdb_hosts "/appl/sm/jboss/manageApp82 deploy $script_mdb"
		if [ $? -eq 0 ]; then
			temp="grep -F -c 'Deployed \"$mdb_War\"' $log_file_path_mdb"  
			temp1=`ssh -i $path_to_private_key $user@$mdb_hosts "$temp"`
				
			if [ $temp1 -eq 1 ]; then
				echo "Deployment of $mdb_War is SUCCESSFUL at location $deployables_mdb"
			else 
				echo "Deployment of $mdb_War is Failed "
				exit 1
			fi;		
			echo "Deployment of $mdb_War is SUCCESSFUL at location $deployables_mdb"
		else
			echo "Deployment of Wallet.ValueLink.EJB.ear FAILED"
		fi;

	i=$(($i+1))
	done;


	## Starting deployment on srv node

	i=1
	count=`tr -dc ',' <<< "$all_srv_hosts" | wc -c `
	count=$(($count+1))

	while [ $i -le $count ]; do

		srv_hosts=`(cut -d "," -f$i <<< ${all_srv_hosts})`
		deployables_srv=`(cut -d "," -f$i <<< ${all_deployables_srv})`
		script_srv=`(cut -d "," -f$i <<< ${all_script_srv})`
		
		srv_War=`(cut -d "," -f$i <<< ${all_sl_srv_war_name})`

		echo "Copying marketws.war"
		scp -i $path_to_private_key $jenkins_path_srv $user@$srv_hosts:$deployables_srv

		ssh -i $path_to_private_key $user@$srv_hosts "/appl/sm/jboss/manageApp82 deploy $script_srv"
		if [ $? -eq 0 ]; then
			temp="grep -F -c 'Deployed \"$srv_War\"' $log_file_path_srv"  
			temp1=`ssh -i $path_to_private_key $user@$srv_hosts "$temp"`
				
			if [ $temp1 -eq 1 ]; then
				echo "Deployment of $srv_War is SUCCESSFUL at location $deployables_srv"
			else 
				echo "Deployment of $srv_War is Failed "
				exit 1
			fi;		
			echo "Deployment of $srv_War is SUCCESSFUL at location $deployables_srv"
		else
			echo "Deployment of marketws.war FAILED"
		fi;

	i=$(($i+1))
	done;

	## Starting dceployment on frontend node
	i=1
	count=`tr -dc ',' <<< "$all_marketfrontend_hosts" | wc -c `
	count=$(($count+1))

	while [ $i -le $count ]; do

		marketfrontend_hosts=`(cut -d "," -f$i <<< ${all_marketfrontend_hosts})`
		deployables_marketfrontend=`(cut -d "," -f$i <<< ${all_deployables_marketfrontend})`
		script_MarketFrontend=`(cut -d "," -f$i <<< ${all_script_MarketFrontend})`
		script_ServiceLiveWebUtil=`(cut -d "," -f$i <<< ${all_script_ServiceLiveWebUtil})`
		script_spn=`(cut -d "," -f$i <<< ${all_script_spn})`
		#script_BuyerSurvey=`(cut -d "," -f$i <<< ${all_script_BuyerSurvey})`
		
		frontend_War=`(cut -d "," -f$i <<< ${all_sl_frontend_war_name})`
		serviceLiveWebUtil_War=`(cut -d "," -f$i <<< ${all_sl_ServiceliveWebUtil_war_name})`
		spn_War=`(cut -d "," -f$i <<< ${all_sl_spn_war_name})`
		

		echo "Copying MarketFrontend.war"
		scp -i $path_to_private_key $jenkins_path_MarketFrontend $user@$marketfrontend_hosts:$deployables_marketfrontend

		ssh -i $path_to_private_key $user@$marketfrontend_hosts "/appl/sm/jboss/manageApp82 deploy $script_MarketFrontend"
		if [ $? -eq 0 ]; then
			temp="grep -F -c 'Deployed \"$frontend_War\"' $log_file_path_frontend"  
			temp1=`ssh -i $path_to_private_key $user@$marketfrontend_hosts "$temp"`
				
			if [ $temp1 -eq 1 ]; then
				echo "Deployment of $frontend_War is SUCCESSFUL at location $deployables_marketfrontend"
			else 
				echo "Deployment of $frontend_War is Failed "
				exit 1
			fi;		
			echo "Deployment of $frontend_War is SUCCESSFUL at location $deployables_marketfrontend"
		else
			echo "Deployment on MarketFrontend.war FAILED"
		fi;

		echo "Copying ServiceLiveWebUtil.war"
		scp -i $path_to_private_key $jenkins_path_ServiceLiveWebUtil $user@$marketfrontend_hosts:$deployables_marketfrontend

		ssh -i $path_to_private_key $user@$marketfrontend_hosts "/appl/sm/jboss/manageApp82 deploy $script_ServiceLiveWebUtil"
		if [ $? -eq 0 ]; then
			temp="grep -F -c 'Deployed \"$serviceLiveWebUtil_War\"' $log_file_path_frontend"  
			temp1=`ssh -i $path_to_private_key $user@$marketfrontend_hosts "$temp"`
				
			if [ $temp1 -eq 1 ]; then
				echo "Deployment of $serviceLiveWebUtil_War is SUCCESSFUL at location $deployables_marketfrontend"
			else 
				echo "Deployment of $serviceLiveWebUtil_War is Failed "
				exit 1
			fi;		
			echo "Deployment of $serviceLiveWebUtil_War is SUCCESSFUL at location $deployables_marketfrontend"
		else
			echo "Deployment on ServiceLiveWebUtil.war FAILED"
		fi;

		echo "Copying spn.war"
		scp -i $path_to_private_key $jenkins_path_spn $user@$marketfrontend_hosts:$deployables_marketfrontend

		ssh -i $path_to_private_key $user@$marketfrontend_hosts "/appl/sm/jboss/manageApp82 deploy $script_spn"
		if [ $? -eq 0 ]; then
			temp="grep -F -c 'Deployed \"$spn_War\"' $log_file_path_frontend"  
			temp1=`ssh -i $path_to_private_key $user@$marketfrontend_hosts "$temp"`
				
			if [ $temp1 -eq 1 ]; then
				echo "Deployment of $spn_War is SUCCESSFUL at location $deployables_marketfrontend"
			else 
				echo "Deployment of $spn_War is Failed "
				exit 1
			fi;		
			echo "Deployment of $spn_War is SUCCESSFUL at location $deployables_marketfrontend"
		else
			echo "Deployment on spn.war FAILED"
		fi;


		#echo "Copying BuyerSurvey.war"
		#scp -i $path_to_private_key $jenkins_path_BuyerSurvey $user@$marketfrontend_hosts:$deployables_marketfrontend
		#
		#ssh -i $path_to_private_key $user@$marketfrontend_hosts "/appl/sm/jboss/manageApp82 deploy $script_BuyerSurvey"
		#if [ $rc -eq 0 ]; then
		#echo "Deployment SUCCESSFUL. Deployed BuyerSurvey.war at location $deployables_marketfrontend"
		#else
		#echo "Deployment on BuyerSurvey.war FAILED"
		#fi;

	i=$(($i+1))
	done;
	

	
	## Starting dceployment on webserver - ServiceLiveWebUtil and StaticWeb
	i=1
	count=`tr -dc ',' <<< "$all_webserver_hosts" | wc -c `
	count=$(($count+1))

	while [ $i -le $count ]; do
		webserver_host=`(cut -d "," -f$i <<< ${all_webserver_hosts})`
		serviceliveWebUtil_path=`(cut -d "," -f$i <<< ${all_ServiceLiveWebUtil_WebServer_Path})`
		staticWeb_path=`(cut -d "," -f$i <<< ${all_StaticWeb_WebServer_Path})`
		webServer_path=`(cut -d "," -f$i <<< ${all_WebServer_Path})`
		
			
		ssh -i $path_to_private_key $user@$webserver_host "ls $serviceliveWebUtil_path"
		if [ $? -ne 0 ]; then
			ssh -i $path_to_private_key $user@$webserver_host "mkdir $serviceliveWebUtil_path" #created file path
			echo "directory for ServiceLiveWebUtil got created"
		else			
			echo "/var/www/ServiceLiveWebUtil already exists "
		fi;
		
		if [ $? -eq 0 ]; then
			#rename old ServiceLiveWebUtil directory to backup and create new dir with same name
			now=`date +"%Y_%m_%d__%H_%M_%S"`						
			ssh -i $path_to_private_key $user@$webserver_host "mv $serviceliveWebUtil_path $webServer_path/ServiceLiveWebUtil_bkp_$now;ls -ltr $webServer_path;mkdir $serviceliveWebUtil_path"	
			scp -i $path_to_private_key $jenkins_path_ServiceLiveWebUtil $user@$webserver_host:$serviceliveWebUtil_path/
			echo "ServiceLiveWebUtil.war got copied at webserver location"
			ssh -i $path_to_private_key $user@$webserver_host "ls -ltr $serviceliveWebUtil_path/ServiceLiveWebUtil.war"
			if [ $? -eq 0 ]; then
				ssh -i $path_to_private_key $user@$webserver_host "cd $serviceliveWebUtil_path/;jar -vxf ServiceLiveWebUtil.war"
				echo "Deployment of ServiceLiveWebUtil.war done successfully"
			else
				echo "Failed Deployment of ServiceLiveWebUtil.war"
			fi;	
		else
			echo "Failed Deployment of ServiceLiveWebUtil.war"
		fi;
		
		
		#Deployment of StaticWeb 
		ssh -i $path_to_private_key $user@$webserver_host "ls $staticWeb_path"
		if [ $? -ne 0 ]; then
			ssh -i $path_to_private_key $user@$webserver_host "mkdir $staticWeb_path" #created file path
			echo "directory for StaticWeb got created"
		else			
			echo "/var/www/StaticWeb already exists "
		fi;		
		
		if [ $? -eq 0 ]; then
			#rename old StaicWeb directory to backup and create new dir with same name
			now=`date +"%Y_%m_%d__%H_%M_%S"`
			ssh -i $path_to_private_key $user@$webserver_host "mv $staticWeb_path $webServer_path/StaticWeb_bkp_$now;ls -ltr $webServer_path;mkdir $staticWeb_path"	
			scp -i $path_to_private_key $jenkins_path_StaticWeb $user@$webserver_host:$staticWeb_path
			echo "StaticWeb.war got copied at webserver location"
			ssh -i $path_to_private_key $user@$webserver_host "ls -ltr $staticWeb_path/StaticWeb.war"
			if [ $? -eq 0 ]; then
				ssh -i $path_to_private_key $user@$webserver_host "cd $staticWeb_path/;jar -vxf StaticWeb.war"
				echo "Deployment of StaticWeb.war done successfully"
			else
				echo "Failed Deployment of StaticWeb.war"
			fi;	
		else
			echo "Failed Deployment of StaticWeb.war"
		fi;
		
	i=$(($i+1))
	done;
	
	
		

	T_afterDeployement=`date +%s`

	T_diff=$((	T_afterDeployement - T_beforeDeployement ))

	echo "Time taken(sec) to deploy artifacts is ::::::: $T_diff "