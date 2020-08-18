	int		rand_selection;
	char	rand_param[30];

SL_ProviderAcceptance()
{


	web_cleanup_cookies();

	//web_reg_find("Text=Find providers",LAST);

	web_reg_find("Text=Visit ServiceLive.com","SaveCount=Homepage_Count",LAST);

	/****** Loads the home page of the ServiceLive site  ******/

	 lr_think_time(atoi(lr_eval_string("{think_time}")));

 /* -------------------------------------------------------------------------------
	Transaction Title        : ServiceLive_ProviderAcceptance_01_Homepage
	Transaction Description  : Access the home page
	Transaction Parameters   : URL
	Correlated Parameters    : None
  ---------------------------------------------------------------------------------- */


	 lr_start_transaction("ServiceLive_ProviderAcceptance_01_Homepage");

	 web_url("homepage.action", 
		"URL={URL}MarketFrontend/homepage.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t1.inf", 
		"Mode=HTML", 
		LAST);



/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=9193824356860.191", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=9193824356860.191?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={URL}MarketFrontend/homepage.action", 
		"Snapshot=t2.inf", 
		"Mode=HTML", 
		LAST);*/

		 if (atoi(lr_eval_string("{Homepage_Count}")) > 0)
	 { 
		 lr_output_message("Homepage successful");
		 lr_end_transaction("ServiceLive_ProviderAcceptance_01_Homepage", LR_PASS);
	 }
	 else
	 { 
		 lr_error_message("Homepage failed");
		 lr_end_transaction("ServiceLive_ProviderAcceptance_01_Homepage", LR_FAIL);
		 lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	 } 

	 web_reg_find("Text=>Log In to ServiceLive","SaveCount=Loginpage_Count",LAST);

	 /****** Loads the Login page  ******/

	  lr_think_time(atoi(lr_eval_string("{think_time}")));

 /* -------------------------------------------------------------------------------
	Transaction Title        : ServiceLive_ProviderAcceptance_02_Loginpage
	Transaction Description  : Click on Login button to access Loginpage page
	Transaction Parameters   : SecureURL
	Correlated Parameters    : None
  ---------------------------------------------------------------------------------- */

	 lr_start_transaction("ServiceLive_ProviderAcceptance_02_Loginpage");

	web_url("loginAction.action", 
		"URL={SecureURL}MarketFrontend/loginAction.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={URL}MarketFrontend/homepage.action", 
		"Snapshot=t3.inf", 
		"Mode=HTML", 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=60127313389.30433", 
		"URL=https://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=60127313389.30433?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/loginAction.action", 
		"Snapshot=t4.inf", 
		"Mode=HTML", 
		LAST);
*/
	if (atoi(lr_eval_string("{Loginpage_Count}")) > 0)
	{ 
		lr_output_message("Loginpage successful");
		lr_end_transaction("ServiceLive_ProviderAcceptance_02_Loginpage", LR_PASS);
	}
	else
	{ 
		lr_error_message("Loginpage failed");
		lr_end_transaction("ServiceLive_ProviderAcceptance_02_Loginpage", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 


	web_reg_save_param("ReceivedOrders",
	   "LB=span id=\"received\">",
	   "RB=</span>",
	   "Ord=1",
	   "NotFound=warning",
	   "IgnoreRedirections=Yes",
	   LAST);

	web_reg_find("Text=Logout<","SaveCount=Login_Count",LAST);

	/****** Entering the Provider credentials  ******/

	 lr_think_time(atoi(lr_eval_string("{think_time}")));

/* -------------------------------------------------------------------------------
   Transaction Title        : ServiceLive_ProviderAcceptance_03_Login
   Transaction Description  : Enter credentials Access the home page
   Transaction Parameters   : SecureURL,UserName,Password
   Correlated Parameters    : ReceivedOrders
 ---------------------------------------------------------------------------------- */

	 lr_start_transaction("ServiceLive_ProviderAcceptance_03_Login");
	
	web_submit_data("doLogin.action", 
		"Action={SecureURL}MarketFrontend/doLogin.action", 
		"Method=POST", 
		"EncType=multipart/form-data", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/loginAction.action", 
		"Snapshot=t5.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=username", "Value={userName}", ENDITEM, 
		"Name=password", "Value={Password}", ENDITEM, 
		"Name=__checkbox_rememberUserId", "Value=true", ENDITEM, 
		LAST);

	if (atoi(lr_eval_string("{Login_Count}")) > 0)
	{ 
		lr_output_message("Login successful");
		lr_end_transaction("ServiceLive_ProviderAcceptance_03_Login", LR_PASS);
	}
	else
	{ 
		lr_error_message("Login failed");
		lr_end_transaction("ServiceLive_ProviderAcceptance_03_Login", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 


	if (atoi(lr_eval_string("{ReceivedOrders}")) <= 0)
	{
		lr_log_message("No Orders in Received for this provider");
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	}


	web_reg_save_param("receivedCount",
	   "LB=title=\"Received (",
	   "RB=)\"",
	   "Ord=1",
	   "NotFound=warning",
	   "IgnoreRedirections=Yes",
	   LAST);

	lr_think_time(atoi(lr_eval_string("{think_time}")));

	/* -------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_ProviderAcceptance_04_ReceivedTab
	   Transaction Description  : Click on Recived Tab
	   Transaction Parameters   : SecureURL
	   Correlated Parameters    : ReceivedOrders
	 ---------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_ProviderAcceptance_04_ReceivedTab");

	web_url("{ReceivedOrders} - Received", 
		"URL={SecureURL}MarketFrontend/serviceOrderMonitor.action?displayTab=Received", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/dashboardAction.action", 
		"Snapshot=t6.inf", 
		"Mode=HTML", 
		LAST);

	web_reg_save_param("currentSpendLimit",
	   "LB=spendLimitTotal : '",
	   "RB='",
	   "Ord=ALL",
	   "NotFound=warning",
	   "IgnoreRedirections=Yes",
	   LAST);

	web_reg_save_param("currentLimitLabor",
	   "LB=spendLimitLabor : '",
	   "RB='",
	   "Ord=ALL",
	   "NotFound=warning",
	   "IgnoreRedirections=Yes",
	   LAST);

	web_reg_save_param("currentLimitParts",
	   "LB=spendLimitParts : '",
	   "RB='",
	   "Ord=ALL",
	   "NotFound=warning",
	   "IgnoreRedirections=Yes",
	   LAST);

	web_reg_save_param("resId",
	   "LB=&resId=",
	   "RB=&displayTab=Summary", //&displayTab=Received",
	   "Ord=1",
	   "NotFound=warning",
	   "IgnoreRedirections=Yes",
	   LAST);


	web_reg_save_param( "SoId", 
						"LB=href=\"soDetailsController.action?soId=",               //parent.newco.jsutils.detailView('", 
						"RB=&resId=", //','", 
						"Ord=ALL",
						"NotFound=warning",
						"IgnoreRedirections=Yes", LAST );


	//web_reg_find("Text=View Details to Accept","SaveCount=Received_Count",LAST);

	web_custom_request("gridHolder.action", 
		"URL={SecureURL}MarketFrontend/monitor/gridHolder.action?tab=Received", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/serviceOrderMonitor.action?displayTab=Received", 
		"Snapshot=t7.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=tab=Received", 
		LAST);

/*	if (atoi(lr_eval_string("{Received_Count}")) > 0)
	{ 
		lr_output_message("Received tab successful");
		lr_end_transaction("ServiceLive_ProviderAcceptance_04_ReceivedTab", LR_PASS);
	}
	else
	{ 
		lr_error_message("Received tab failed");
		lr_end_transaction("ServiceLive_ProviderAcceptance_04_ReceivedTab", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 
*/

lr_end_transaction("ServiceLive_ProviderAcceptance_04_ReceivedTab", LR_AUTO);
	/****** Loads the order page  ******/

	 lr_think_time(atoi(lr_eval_string("{think_time}")));

/* -------------------------------------------------------------------------------
   Transaction Title        : ServiceLive_ProviderAcceptance_05_SelectOrder
   Transaction Description  : Select SO
   Transaction Parameters   : SecureURL
   Correlated Parameters    : currentSpendLimit,currentLimitLabor,currentLimitParts,resId,SoId_1
 ---------------------------------------------------------------------------------- */

	 lr_start_transaction("ServiceLive_ProviderAcceptance_05_SelectOrder");

	web_submit_data("refreshTabs.action", 
		"Action={SecureURL}MarketFrontend/monitor/refreshTabs.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/xml", 
		"Referer={SecureURL}MarketFrontend/serviceOrderMonitor.action?displayTab=Received", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=soId0", "Value={SoId_1}", ENDITEM, 
		"Name={SoId_1}_slCount", "Value={currentSpendLimit_1}", ENDITEM, 
		"Name={SoId_1}_slCount", "Value={currentLimitLabor_1}", ENDITEM, 
		"Name=soId1", "Value={SoId_2}", ENDITEM, 
		"Name={SoId_2}_slCount", "Value={currentSpendLimit_2}", ENDITEM, 
		"Name={SoId_2}_slCount", "Value={currentLimitLabor_2}", ENDITEM, 
		"Name=soId2", "Value={SoId_3}", ENDITEM, 
		"Name=soId3", "Value={SoId_4}", ENDITEM, 
		"Name=soId4", "Value={SoId_5}", ENDITEM, 
		"Name={SoId_5}_slCount", "Value={currentSpendLimit_3}", ENDITEM, 
		"Name={SoId_5}_slCount", "Value={currentLimitLabor_3}", ENDITEM, 
		"Name=soId5", "Value={SoId_6}", ENDITEM, 
		"Name={SoId_6}_slCount", "Value={currentSpendLimit_4}", ENDITEM, 
		"Name={SoId_6}_slCount", "Value={currentLimitLabor_4}", ENDITEM, 
		"Name=soId6", "Value={SoId_7}", ENDITEM, 
		"Name={SoId_7}_slCount", "Value={currentSpendLimit_5}", ENDITEM, 
		"Name={SoId_7}_slCount", "Value={currentLimitLabor_5}", ENDITEM, 
		"Name=soId7", "Value={SoId_8}", ENDITEM, 
		"Name={SoId_8}_slCount", "Value={currentSpendLimit_6}", ENDITEM, 
		"Name={SoId_8}_slCount", "Value={currentLimitLabor_6}", ENDITEM, 
		"Name=soId8", "Value={SoId_9}", ENDITEM, 
		"Name={SoId_9}_slCount", "Value={currentSpendLimit_7}", ENDITEM, 
		"Name={SoId_9}_slCount", "Value={currentLimitLabor_7}", ENDITEM, 
		"Name=soId9", "Value={SoId_10}", ENDITEM, 
		"Name={SoId_10}_slCount", "Value={currentSpendLimit_8}", ENDITEM, 
		"Name=soId10", "Value={SoId_11}", ENDITEM, 
		"Name={SoId_11}_slCount", "Value={currentSpendLimit_9}", ENDITEM, 
		"Name={SoId_11}_slCount", "Value={currentLimitLabor_8}", ENDITEM, 
		"Name=soId11", "Value={SoId_12}", ENDITEM, 
		"Name=soId12", "Value={SoId_13}", ENDITEM, 
		"Name=soId13", "Value={SoId_14}", ENDITEM, 
		"Name={SoId_14}_slCount", "Value={currentSpendLimit_10}", ENDITEM, 
		"Name=soId14", "Value={SoId_15}", ENDITEM, 
		"Name={SoId_15}_slCount", "Value={currentSpendLimit_11}", ENDITEM, 
		"Name=soId15", "Value={SoId_16}", ENDITEM, 
		"Name={SoId_16}_slCount", "Value={currentSpendLimit_12}", ENDITEM, 
		"Name=soId16", "Value={SoId_17}", ENDITEM, 
		"Name={SoId_17}_slCount", "Value={currentSpendLimit_13}", ENDITEM, 
		"Name={SoId_17}_slCount", "Value={currentLimitLabor_9}", ENDITEM, 
		"Name=soId17", "Value={SoId_18}", ENDITEM, 
		"Name={SoId_18}_slCount", "Value={currentSpendLimit_14}", ENDITEM, 
		"Name={SoId_18}_slCount", "Value={currentLimitLabor_10}", ENDITEM, 
		"Name=soId18", "Value={SoId_19}", ENDITEM, 
		"Name=soId19", "Value={SoId_20}", ENDITEM, 
		"Name=soId20", "Value={SoId_21}", ENDITEM, 
		"Name={SoId_21}_slCount", "Value={currentSpendLimit_15}", ENDITEM, 
		"Name=soId21", "Value={SoId_22}", ENDITEM, 
		"Name=soId22", "Value={SoId_23}", ENDITEM, 
		"Name=soId23", "Value={SoId_24}", ENDITEM, 
		"Name=soId24", "Value={SoId_25}", ENDITEM, 
		"Name=Received", "Value={ReceivedOrders}", ENDITEM, 
		LAST);


	rand_selection = (rand () %atoi(lr_eval_string("{SoId_count}")));

	if (rand_selection == 0) {
		rand_selection = rand_selection+1;
	
	}
	
	
	sprintf (rand_param, "{SoId_%d}", rand_selection);
	
	lr_save_string (lr_eval_string(rand_param), "Random_SoId");

	web_reg_find("Text={Random_SoId}","SaveCount=OrderSelect_Count",LAST);


	web_submit_data("soDocumentsAndPhotos_getDocumentsWidget.action", 
		"Action={SecureURL}MarketFrontend/soDocumentsAndPhotos_getDocumentsWidget.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/xml", 
		"Referer={SecureURL}MarketFrontend/serviceOrderMonitor.action?displayTab=Received", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=selectedSO", "Value={Random_SoId}", ENDITEM, 
		"Name=groupInd", "Value=false", ENDITEM, 
		"Name=groupId", "Value=", ENDITEM, 
		"Name=resId", "Value={resId}", ENDITEM, 
		"Name=selectedRowIndex", "Value=5", ENDITEM, 
		"Name=currentSpendLimit", "Value={currentSpendLimit_1}", ENDITEM, 
		"Name=currentLimitLabor", "Value=", ENDITEM, 
		"Name=currentLimitParts", "Value=", ENDITEM, 
		"Name=totalSpendLimit", "Value=", ENDITEM, 
		"Name=totalSpendLimitParts", "Value=", ENDITEM, 
		"Name=incSpendLimitComment", "Value=", ENDITEM, 
		"Name=reasonId", "Value=", ENDITEM, 
		"Name=requestFrom", "Value=SOM", ENDITEM, 
		"Name=cancelComment", "Value=", ENDITEM, 
		"Name=subject", "Value=", ENDITEM, 
		"Name=message", "Value=", ENDITEM, 
		"Name=theRole", "Value=1", ENDITEM, 
		"Name=tab", "Value=Received", ENDITEM, 
		LAST);

	if (atoi(lr_eval_string("{OrderSelect_Count}")) > 0)
	{ 
		lr_output_message("SelectOrder successful");
		lr_end_transaction("ServiceLive_ProviderAcceptance_05_SelectOrder", LR_PASS);
	}
	else
	{ 
		lr_error_message("SelectOrder failed");
		lr_end_transaction("ServiceLive_ProviderAcceptance_05_SelectOrder", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 



	/*Clicking on View Details to Accept link*/

	 lr_think_time(atoi(lr_eval_string("{think_time}")));
	
	/* -------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_ProviderAcceptance_06_ViewDetails
	   Transaction Description  : Veiw SO details
	   Transaction Parameters   : SecureURL
	   Correlated Parameters    : SoId,resId
	 ---------------------------------------------------------------------------------- */

	 lr_start_transaction("ServiceLive_ProviderAcceptance_06_ViewDetails");


	web_url("soDetailsController.action", 
		"URL={SecureURL}MarketFrontend/monitor/soDetailsController.action?soId={Random_SoId}&resId={resId}&defaultTab=Summary&displayTab=Received", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/monitor/gridLoader.action?tab=Received", 
		"Snapshot=t10.inf", 
		"Mode=HTML", 
		LAST);

	web_url("promoAction_displayPromotion.action", 
		"URL={SecureURL}MarketFrontend/monitor/promoAction_displayPromotion.action?soId={Random_SoId}&contentLocation=SODProvider", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/monitor/soDetailsController.action?soId={Random_SoId}&resId={resId}&defaultTab=Summary&displayTab=Received", 
		"Snapshot=t11.inf", 
		"Mode=HTML", 
		LAST);

	web_reg_find("Text=General Information","SaveCount=Details_Count",LAST);


	web_url("soDetailsSummary.action", 
		"URL={SecureURL}MarketFrontend/monitor/soDetailsSummary.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/monitor/soDetailsController.action?soId={Random_SoId}&resId={resId}&defaultTab=Summary&displayTab=Received", 
		"Snapshot=t12.inf", 
		"Mode=HTML", 
		LAST);

	web_url("soDetailsQuickLinks.action", 
		"URL={SecureURL}MarketFrontend/monitor/soDetailsQuickLinks.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/monitor/soDetailsController.action?soId={Random_SoId}&resId={resId}&defaultTab=Summary&displayTab=Received", 
		"Snapshot=t13.inf", 
		"Mode=HTML", 
		LAST);
	if (atoi(lr_eval_string("{Details_Count}")) > 0)
	{ 
		lr_output_message("View Details successful");
		lr_end_transaction("ServiceLive_ProviderAcceptance_06_ViewDetails", LR_PASS);
	}
	else
	{ 
		lr_error_message("View Details failed");
		lr_end_transaction("ServiceLive_ProviderAcceptance_06_ViewDetails", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 


	lr_think_time(atoi(lr_eval_string("{think_time}")));

/* -------------------------------------------------------------------------------
   Transaction Title        : ServiceLive_ProviderAcceptance_07_Accept
   Transaction Description  : Access the home page
   Transaction Parameters   : SecureURL
   Correlated Parameters    : None
 ---------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_ProviderAcceptance_07_Accept");

	web_url("soDetailsAcceptSO.action", 
		"URL={SecureURL}MarketFrontend/soDetailsAcceptSO.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t14.inf", 
		"Mode=HTML", 
		LAST);


	web_url("promoAction_displayPromotion.action_2", 
		"URL={SecureURL}MarketFrontend/promoAction_displayPromotion.action?soId={Random_SoId}&contentLocation=SODProvider", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/soDetailsController.action?id=&role=provider&status=accepted", 
		"Snapshot=t15.inf", 
		"Mode=HTML", 
		LAST);

	web_reg_find("Text=Accepted","SaveCount=Accept_Count",LAST);

	web_url("soDetailsSummary.action_2", 
		"URL={SecureURL}MarketFrontend/soDetailsSummary.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/soDetailsController.action?id=&role=provider&status=accepted", 
		"Snapshot=t16.inf", 
		"Mode=HTML", 
		LAST);

	web_url("soDetailsQuickLinks.action_2", 
		"URL={SecureURL}MarketFrontend/soDetailsQuickLinks.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/soDetailsController.action?id=&role=provider&status=accepted", 
		"Snapshot=t17.inf", 
		"Mode=HTML", 
		LAST);

	if (atoi(lr_eval_string("{Accept_Count}")) > 0)
	{ 
		lr_output_message("Order Accepted successful");
		lr_end_transaction("ServiceLive_ProviderAcceptance_07_Accept", LR_PASS);
	}
	else
	{ 
		lr_error_message("Order acceptance failed");
		lr_end_transaction("ServiceLive_ProviderAcceptance_07_Accept", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	}

		/*Logout*/

	lr_think_time(atoi(lr_eval_string("{think_time}")));

/* -------------------------------------------------------------------------------
   Transaction Title        : ServiceLive_ProviderAcceptance_08_Logout
   Transaction Description  : click logout
   Transaction Parameters   : SecureURL
   Correlated Parameters    : None
 ---------------------------------------------------------------------------------- */

	web_reg_find("Text=Login","SaveCount=LogOut_Count",LAST);

	lr_start_transaction("ServiceLive_ProviderAcceptance_08_Logout");

	web_url("doLogout.action", 
		"URL={SecureURL}MarketFrontend/doLogout.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t18.inf", 
		"Mode=HTML", 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=5839903287993.724", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=5839903287993.724?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={URL}MarketFrontend/homepage.action", 
		"Snapshot=t19.inf", 
		"Mode=HTML", 
		LAST);*/

	if (atoi(lr_eval_string("{LogOut_Count}")) > 0)
	{ 
		lr_output_message("LogOut successful");
		lr_end_transaction("ServiceLive_ProviderAcceptance_08_Logout", LR_PASS);
	}
	else
	{ 
		lr_error_message("LogOut failed");
		lr_end_transaction("ServiceLive_ProviderAcceptance_08_Logout", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	}

	return 0;
}