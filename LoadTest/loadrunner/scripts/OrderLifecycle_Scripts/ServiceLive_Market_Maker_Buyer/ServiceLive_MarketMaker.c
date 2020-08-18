char *currentTime;

extern char * strtok(char * string, const char * delimiters ); // Explicit declaration 
char *path;
char separators[] = " "; 
char * token; 

ServiceLive_MarketMaker()
{

/* -------------------------------------------------------------------------------

   Transaction Title        : ServiceLive_MarketMakerBuyer_04_WorkflowMonitor

   Transaction Description  : Click on WorkFlow Monitor Tab  

   Transaction Parameters   : SecureURL 

   Correlated Parameters    : None

 ------------------------------------------------------------------------------------ */
	web_reg_find("Text=Monitor","SaveCount=Monitor_Count",LAST);

	lr_think_time(atoi(lr_eval_string("{think_time}")));

	lr_start_transaction("ServiceLive_MarketMakerBuyer_04_WorkflowMonitor");

	web_url("pbController_execute.action", 
		"URL={SecureURL}MarketFrontend/pbController_execute.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/adminDashboard.action", 
		"Snapshot=t7.inf", 
		"Mode=HTML", 
		LAST);


	web_custom_request("pbClaimedTab_execute.action", 
		"URL={SecureURL}MarketFrontend/pbClaimedTab_execute.action", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/pbController_execute.action", 
		"Snapshot=t8.inf", 
		"Mode=HTML", 
		LAST);

	if (atoi(lr_eval_string("{Monitor_Count}")) > 0)
	{ 
		lr_output_message("Workflow Monitor selected");
		lr_end_transaction("ServiceLive_MarketMakerBuyer_04_WorkflowMonitor", LR_PASS);
	}
	else
	{ 
		lr_error_message("Workflow Monitor failed");
		lr_end_transaction("ServiceLive_MarketMakerBuyer_04_WorkflowMonitor", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 

    web_reg_save_param("FilterId",
		"LB=pbFilterId=",
		"RB=&pbFilterName=Marketplace Posted Orders",
		"Ord=1",
		"RelFrameId=1",
		"IgnoreRedirections=Yes",
		LAST);	



	/* -------------------------------------------------------------------------------
	
	   Transaction Title        : ServiceLive_MarketMakerBuyer_05_ClaimNextOrder
	
	   Transaction Description  : Click on Claim Next Order 
	
	   Transaction Parameters   : URL 
	
	   Correlated Parameters    : FilterId
	
	 ------------------------------------------------------------------------------------ */
	lr_start_transaction("ServiceLive_MarketMakerBuyer_05_ClaimNextOrder");

		web_custom_request("pbWorkflowTab_execute.action", 
		"URL={SecureURL}MarketFrontend/pbWorkflowTab_execute.action", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/pbController_execute.action", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		LAST);


	web_reg_save_param("SoId",
		"LB=soId=",
		"RB=&action",
		"Ord=1",
		"RelFrameId=1",
		"IgnoreRedirections=Yes",
		"NotFound=Warning",
		LAST);


	web_url("Claim Next Order", 
		"URL={SecureURL}MarketFrontend/pbWorkflowTab_claimNext.action?filterId={FilterId}", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/pbController_execute.action", 
		"Snapshot=t10.inf", 
		"Mode=HTML", 
		LAST);

		if(atoi(lr_eval_string ("{SoId}"))==0)
	{
  		lr_end_transaction("ServiceLive_MarketMakerBuyer_05_ClaimNextOrder", LR_FAIL);
        lr_error_message("Failed to load order claim page");
  		lr_exit(LR_EXIT_VUSER, LR_PASS);
  	}

	//web_add_cookie("Selectedsubtab=Summary; DOMAIN=198.179.148.51");

	web_url("promoAction_displayPromotion.action", 
		"URL={SecureURL}MarketFrontend/promoAction_displayPromotion.action?soId={SoId}&contentLocation=SODProvider", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/soDetailsController.action?soId={SoId}", 
		"Snapshot=t11.inf", 
		"Mode=HTML", 
		LAST);

	web_url("soDetailsController.action", 
		"URL={SecureURL}MarketFrontend/soDetailsController.action?soId={SoId}", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/pbController_execute.action", 
		"Snapshot=t12.inf", 
		"Mode=HTML", 
		LAST);

	web_url("promoAction_displayPromotion.action_2", 
		"URL={SecureURL}MarketFrontend/promoAction_displayPromotion.action?soId={SoId}&contentLocation=SODProvider", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/soDetailsController.action?soId={SoId}", 
		"Snapshot=t13.inf", 
		"Mode=HTML", 
		LAST);

	web_url("soDetailsResponseStatus.action", 
		"URL={SecureURL}MarketFrontend/soDetailsResponseStatus.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/soDetailsController.action?soId={SoId}", 
		"Snapshot=t14.inf", 
		"Mode=HTML", 
		LAST);


		web_reg_save_param("randomNumUnclaim",
	   "LB=randomNumUnclaim\" value=\"",
	   "RB=\"",
	   "Ord=1",
	   "NotFound=warning",
	   "IgnoreRedirections=Yes",
	   LAST);

	web_reg_save_param("queueID",
	   "LB=queueID\" value=\"",
	   "RB=\"",
	   "Ord=1",
	   "NotFound=warning",
	   "IgnoreRedirections=Yes",
	   LAST);

	web_reg_save_param("buyerId",
	   "LB=buyerId\" value=\"",
	   "RB=\"",
	   "Ord=1",
	   "NotFound=warning",
	   "IgnoreRedirections=Yes",
	   LAST);

	web_reg_save_param("uniqueNumber",
	   "LB=uniqueNumber\" value=\"",
	   "RB=\"",
	   "Ord=1",
	   "NotFound=warning",
	   "IgnoreRedirections=Yes",
	   LAST);

	web_reg_save_param("soQueueId",
	   "LB=soQueueId\" value=\"",
	   "RB=\"",
	   "Ord=1",
	   "NotFound=warning",
	   "IgnoreRedirections=Yes",
	   LAST);

	web_reg_save_param("queueDate",
		"LB=margin-bottom: 10px; width: 105px;  font-size: 10px;\" readonly=\"readonly\" value=\"",
		"RB=\"",
		"Ord=1",
		LAST);


	web_url("soDetailsQuickLinks.action", 
		"URL={SecureURL}MarketFrontend/soDetailsQuickLinks.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/soDetailsController.action?soId={SoId}", 
		"Snapshot=t15.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("ServiceLive_MarketMakerBuyer_05_ClaimNextOrder",LR_AUTO);



	currentTime= lr_eval_string("{requeueDate}");

	token = (char *)strtok(currentTime, separators); 

    if (!token) { 

        lr_output_message ("No tokens found in string!"); 
        return( -1 ); 

    } 

    while (token != NULL ) { 

      //  lr_output_message ("%s", token ); 

		lr_save_string(token,"currentTime");

    	token= NULL;
             
    } 

	//lr_output_message ("time is: %s", lr_eval_string("{currentTime}"));




/* -------------------------------------------------------------------------------

   Transaction Title        : ServiceLive_MarketMakerBuyer_06_ResponseStatus

   Transaction Description  : Select an Action and Click on Submit Notes 

   Transaction Parameters   : SecureURL,requeueDate,QueueNote,Queue_Action

   Correlated Parameters    : SoId,randomNumUnclaim,queueID,buyerId,uniqueNumber,soQueueId

 ------------------------------------------------------------------------------------ */

	//
	// web_reg_find("Text=Market Maker Response","SaveCount=Response_Count",LAST);
	
	lr_think_time(atoi(lr_eval_string("{think_time}")));

	lr_start_transaction("ServiceLive_MarketMakerBuyer_06_ResponseStatus");
	

	web_custom_request("soDetailsQueueNote.action", 
		"URL={SecureURL}MarketFrontend/soDetailsQueueNote.action?wfmBuyerQueueDTO11=Could%20not%20complete%20action.%7C~%7C1190%7C~%7Crequeue%7C~%7COther%20-%20Requeue%7C~%7C4%7C~%7C0%7C~%7C11&pri=on&noteText=Could%20not%20complete%20action.&message_leftChars=750&requeueDate={currentTime}&requeueTime={requeueTime}&primaryQueue=true&randomNumUnclaim={randomNumUnclaim}&tabToShow=null&soTaskId=1190&taskState=requeue&taskCode=Other%20-%20Requeue&queueSeq=0&queueID={queueID}&buyerId={buyerId}&soQueueId={soQueueId}&uniqueNumber="
		"{uniqueNumber}&queuedDate11{uniqueNumber}={queueDate}&", 
		"Method=POST", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/soDetailsController.action?soId={SoId}", 
		"Snapshot=t16.inf", 
		"Mode=HTML", 
		"EncType=", 
		LAST);

	web_url("soDetailsController.action_2", 
		"URL={SecureURL}MarketFrontend/soDetailsController.action?soId={SoId}", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/pbController_execute.action", 
		"Snapshot=t17.inf", 
		"Mode=HTML", 
		LAST);



	web_url("promoAction_displayPromotion.action_3", 
		"URL={SecureURL}MarketFrontend/promoAction_displayPromotion.action?soId={SoId}&contentLocation=SODProvider", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/soDetailsController.action?soId={SoId}", 
		"Snapshot=t18.inf", 
		"Mode=HTML", 
		LAST);

		web_reg_find("Text=What kind of response is your service order","SaveCount=Response_Count",LAST);

	web_url("soDetailsResponseStatus.action_2", 
		"URL={SecureURL}MarketFrontend/soDetailsResponseStatus.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/soDetailsController.action?soId={SoId}", 
		"Snapshot=t19.inf", 
		"Mode=HTML", 
		LAST);

		web_reg_save_param("UniqueNum",
	   "LB=uniqueNumber\" value=\"",
	   "RB=\"",
	   "Ord=1",
	   "NotFound=warning",
	   "IgnoreRedirections=Yes",
	   LAST);

	web_url("soDetailsQuickLinks.action_2", 
		"URL={SecureURL}MarketFrontend/soDetailsQuickLinks.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/soDetailsController.action?soId={SoId}", 
		"Snapshot=t20.inf", 
		"Mode=HTML", 
		LAST);

	if (atoi(lr_eval_string("{Response_Count}")) > 0)
	{ 
		lr_output_message("Response Status found");
		lr_end_transaction("ServiceLive_MarketMakerBuyer_06_ResponseStatus", LR_PASS);
	}
	else
	{ 
		/*while (Counter > 0){
		lr_think_time(atoi(lr_eval_string("{think_time}")));
		Counter = Counter -1;
		goto response;
		}*/

		lr_error_message("Response Status not Matching");
		lr_end_transaction("ServiceLive_MarketMakerBuyer_06_ResponseStatus", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 


/* -------------------------------------------------------------------------------

   Transaction Title        : ServiceLive_MarketMakerBuyer_07_SubmitUnClaim

   Transaction Description  : Click on Un-Claim Order button 

   Transaction Parameters   : SecureURL 

   Correlated Parameters    : SoId,uniqueNumber

 ------------------------------------------------------------------------------------ */
	lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=Successfully un-claimed","SaveCount=Unclaim_Count",LAST);

	lr_start_transaction("ServiceLive_MarketMakerBuyer_07_SubmitUnClaim");

	web_custom_request("submitUnClaim.action", 
		"URL={SecureURL}MarketFrontend/submitUnClaim.action?unclaimVerification=true&uniqueNumber={UniqueNum}", 
		"Method=POST", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/soDetailsController.action?soId={SoId}", 
		"Snapshot=t21.inf", 
		"Mode=HTML", 
		"EncType=", 
		LAST);

		if (atoi(lr_eval_string("{Unclaim_Count}")) == 0)
	{
		web_reg_find("Text=Successfully un-claimed","SaveCount=Unclaim_Count",LAST);
	}

	web_custom_request("submitUnClaim.action_2", 
		"URL={SecureURL}MarketFrontend/submitUnClaim.action?unclaimVerification=false&uniqueNumber={UniqueNum}", 
		"Method=POST", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/soDetailsController.action?soId={SoId}", 
		"Snapshot=t22.inf", 
		"Mode=HTML", 
		"EncType=", 
		LAST);

	web_submit_data("pbController_execute.action_2", 
		"Action={SecureURL}MarketFrontend/pbController_execute.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/soDetailsController.action?soId={SoId}", 
		"Snapshot=t23.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=hidden", "Value=", ENDITEM, 
		LAST);

	web_custom_request("pbClaimedTab_execute.action_2", 
		"URL={SecureURL}MarketFrontend/pbClaimedTab_execute.action", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/pbController_execute.action", 
		"Snapshot=t24.inf", 
		"Mode=HTML", 
		LAST);

	if (atoi(lr_eval_string("{Unclaim_Count}")) > 0)
	{ 
		lr_output_message("Unclaimed Successfully");
		lr_end_transaction("ServiceLive_MarketMakerBuyer_07_SubmitUnClaim", LR_PASS);
	}
	else
	{ 
		/*while (Counter > 0){
		lr_think_time(atoi(lr_eval_string("{think_time}")));
		Counter = Counter -1;
		goto Unclaim;
		}*/

		lr_error_message("Unclaimed UnSuccessfully");
		lr_end_transaction("ServiceLive_MarketMakerBuyer_07_SubmitUnClaim", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
		}
	

	return 0;
}
