ServiceLive_Provider_Acceptance()
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
		"URL={loginURL}MarketFrontend/homepage.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t1.inf", 
		"Mode=HTML", 
		LAST);

/*	//web_add_cookie("id=cb2e8352400006d||t=1266521168|et=730|cs=7j3zrqmt; DOMAIN=fls.doubleclick.net");

	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=3781813570436.4595", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=3781813570436.4595?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}MarketFrontend/homepage.action", 
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
	
	 lr_think_time(atoi(lr_eval_string("{think_time}")));

	 web_reg_find("Text=>Log In to ServiceLive","SaveCount=Loginpage_Count",LAST);

	 lr_start_transaction("ServiceLive_ProviderAcceptance_02_Loginpage");

 	web_url("loginAction.action", 
		"URL={secureURL}MarketFrontend/loginAction.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}MarketFrontend/homepage.action", 
		"Snapshot=t3.inf", 
		"Mode=HTML", 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=9144691204368.492", 
		"URL=https://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=9144691204368.492?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/loginAction.action", 
		"Snapshot=t4.inf", 
		"Mode=HTML", 
		LAST);*/

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
	   "LB=span id=\"accepted\">",
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
   Transaction Parameters   : secureURL,userName,Password
   Correlated Parameters    : None
 ---------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_ProviderAcceptance_03_Login");

	web_submit_data("doLogin.action", 
		"Action={secureURL}MarketFrontend/doLogin.action", 
		"Method=POST", 
		"EncType=multipart/form-data", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/loginAction.action", 
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
	   Transaction Parameters   : secureURL
	   Correlated Parameters    : ReceivedOrders
	 ---------------------------------------------------------------------------------- */

    lr_start_transaction("ServiceLive_ProviderAcceptance_04_ReceivedTab");

	web_url("{ReceivedOrders} - Received", 
		"URL={secureURL}MarketFrontend/serviceOrderMonitor.action?displayTab=Received", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/dashboardAction.action", 
		"Snapshot=t6.inf", 
		"Mode=HTML", 
		LAST);

	web_reg_save_param( "SoId", 
						"LB=soDetailsController.action?soId=", 
						"RB=&", 
						"Ord=1",
						"NotFound=warning",
						"IgnoreRedirections=Yes", 
						LAST );

	web_reg_save_param("resId",
					   "LB=&resId=",
					   "RB=&displayTab=Summary",
					   "Ord=1",
					   "NotFound=warning",
					   "IgnoreRedirections=Yes",
						LAST);

	web_reg_find("Text=Received","SaveCount=Received_Count",LAST);
    
	web_custom_request("gridHolder.action", 
		"URL={secureURL}MarketFrontend/monitor/gridHolder.action?tab=Received", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/serviceOrderMonitor.action?displayTab=Received", 
		"Snapshot=t7.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=tab=Received", 
		LAST);

	if (atoi(lr_eval_string("{Received_Count}")) > 0)
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

	/****** Loads the order page  ******/


	 lr_think_time(atoi(lr_eval_string("{think_time}")));


	/* -------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_ProviderAcceptance_05_SelectOrder
	   Transaction Description  : Select SO
	   Transaction Parameters   : secureURL
	   Correlated Parameters    : resId,SoId
	 ---------------------------------------------------------------------------------- */

	 lr_start_transaction("ServiceLive_ProviderAcceptance_05_SelectOrder");

	web_submit_data("soDetailsController.action", 
		"Action={secureURL}MarketFrontend/monitor/soDetailsController.action?soId={SoId}&resId={resId}&displayTab=Summary", 
		"Method=POST", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/gridLoader.action?tab=Received", 
		"Snapshot=t8.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		LAST);

	web_submit_data("soDocumentsAndPhotos_getDocumentsWidget.action", 
		"Action={secureURL}MarketFrontend/soDocumentsAndPhotos_getDocumentsWidget.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/xml", 
		"Referer={secureURL}MarketFrontend/serviceOrderMonitor.action?displayTab=Received", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=selectedSO", "Value={SoId}", ENDITEM, 
		"Name=groupInd", "Value=false", ENDITEM, 
		"Name=groupId", "Value=", ENDITEM, 
		"Name=resId", "Value={resId}", ENDITEM, 
		"Name=selectedRowIndex", "Value=1", ENDITEM, 
		"Name=currentSpendLimit", "Value=95.99", ENDITEM, 
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

	//web_add_cookie("Selectedsubtab=Summary; DOMAIN=151.149.116.100");

	web_url("promoAction_displayPromotion.action", 
		"URL={secureURL}MarketFrontend/monitor/promoAction_displayPromotion.action?soId={SoId}&contentLocation=SODProvider", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/soDetailsController.action?soId={SoId}&resId={resId}&displayTab=Summary", 
		"Snapshot=t10.inf", 
		"Mode=HTML", 
		LAST);

	web_reg_find("Text=General Information","SaveCount=Details_Count",LAST);

	web_url("soDetailsSummary.action", 
		"URL={secureURL}MarketFrontend/monitor/soDetailsSummary.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/soDetailsController.action?soId={SoId}&resId={resId}&displayTab=Summary", 
		"Snapshot=t11.inf", 
		"Mode=HTML", 
		LAST);

	web_url("soDetailsQuickLinks.action", 
		"URL={secureURL}MarketFrontend/monitor/soDetailsQuickLinks.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/soDetailsController.action?soId={SoId}&resId={resId}&displayTab=Summary", 
		"Snapshot=t12.inf", 
		"Mode=HTML", 
		LAST);

	if (atoi(lr_eval_string("{Details_Count}")) > 0)
	{ 
		lr_output_message("Select Order successful");
		lr_end_transaction("ServiceLive_ProviderAcceptance_05_SelectOrder", LR_PASS);
	}
	else
	{ 
		lr_error_message("Select Order failed");
		lr_end_transaction("ServiceLive_ProviderAcceptance_05_SelectOrder", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 

	lr_think_time(atoi(lr_eval_string("{think_time}")));


   /* -------------------------------------------------------------------------------
	  Transaction Title        : ServiceLive_ProviderAcceptance_06_AcceptSO
	  Transaction Description  : Accepts SO
	  Transaction Parameters   : secureURL
	  Correlated Parameters    : SoId
	---------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_ProviderAcceptance_06_AcceptSO");

	web_url("soDetailsAcceptSO.action", 
		"URL={secureURL}MarketFrontend/soDetailsAcceptSO.action?securityCode=qjq2q&position=2305", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t13.inf", 
		"Mode=HTML", 
		LAST);

	web_url("promoAction_displayPromotion.action_2", 
		"URL={secureURL}MarketFrontend/promoAction_displayPromotion.action?soId={SoId}&contentLocation=SODProvider", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/soDetailsController.action?id=&role=provider&status=accepted", 
		"Snapshot=t14.inf", 
		"Mode=HTML", 
		LAST);

	web_reg_find("Text=Accepted","SaveCount=Accept_Count",LAST);

	web_url("soDetailsSummary.action_2", 
		"URL={secureURL}MarketFrontend/soDetailsSummary.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/soDetailsController.action?id=&role=provider&status=accepted", 
		"Snapshot=t15.inf", 
		"Mode=HTML", 
		LAST);

	web_url("soDetailsQuickLinks.action_2", 
		"URL={secureURL}MarketFrontend/soDetailsQuickLinks.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/soDetailsController.action?id=&role=provider&status=accepted", 
		"Snapshot=t16.inf", 
		"Mode=HTML", 
		LAST);

	if (atoi(lr_eval_string("{Accept_Count}")) > 0)
	{ 
		lr_output_message("Accept SO successful");
		lr_end_transaction("ServiceLive_ProviderAcceptance_06_AcceptSO", LR_PASS);
	}
	else
	{ 
		lr_error_message("Accept SO failed");
		lr_end_transaction("ServiceLive_ProviderAcceptance_06_AcceptSO", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 


	lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=Join ServiceLive","SaveCount=Logout_Count",LAST);

	/* -------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_ProviderAcceptance_07_Logout
	   Transaction Description  : Access the home page
	   Transaction Parameters   : secureURL
	   Correlated Parameters    : None
	 ---------------------------------------------------------------------------------- */
	

	lr_start_transaction("ServiceLive_ProviderAcceptance_07_Logout");


	web_url("doLogout.action", 
		"URL={secureURL}MarketFrontend/doLogout.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t17.inf", 
		"Mode=HTML", 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi767;ord=1;num=1133355980955.5305", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi767;ord=1;num=1133355980955.5305?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}MarketFrontend/joinNowAction.action", 
		"Snapshot=t18.inf", 
		"Mode=HTML", 
		LAST);

	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=2035583015331.327", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=2035583015331.327?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}MarketFrontend/joinNowAction.action", 
		"Snapshot=t19.inf", 
		"Mode=HTML", 
		LAST);
*/
	if (atoi(lr_eval_string("{Logout_Count}")) > 0)
	{ 
		lr_output_message("LogOut successful");
		lr_end_transaction("ServiceLive_ProviderAcceptance_07_Logout", LR_PASS);
	}
	else
	{ 
		lr_error_message("LogOut failed");
		lr_end_transaction("ServiceLive_ProviderAcceptance_07_Logout", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 

	return 0;
}