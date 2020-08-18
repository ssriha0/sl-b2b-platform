	int		rand_selection;
	char	rand_param[30];

	int startIndex;
	int endIndex;

ServiceLive_Buyer_SO_Closure()
{

	web_reg_find("Text=ServiceLive : Home Improvement & Repair","SaveCount=Home_Count",LAST);

	/* -------------------------------------------------------------------------------

	   Transaction Title        : ServiceLive_SearsBuyerSOClosure_01_HomePage

	   Transaction Description  : Access the home page 

	   Transaction Parameters   : loginURL 

	   Correlated Parameters    : None

	 ------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_SearsBuyerSOClosure_01_HomePage");

	web_url("homepage.action", 
		"URL={loginURL}MarketFrontend/homepage.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t1.inf", 
		"Mode=HTML", 
		LAST);


/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=4687766426878.421", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=4687766426878.421?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}MarketFrontend/homepage.action", 
		"Snapshot=t2.inf", 
		"Mode=HTML", 
		LAST);
*/
	if((!strcmp(lr_eval_string(lr_eval_string("{Home_Count}")), "0")))
	{
		lr_end_transaction("ServiceLive_SearsBuyerSOClosure_01_HomePage", LR_FAIL);
		lr_error_message("Failed to load Homepage ");
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	}
	else
	{
		lr_end_transaction("ServiceLive_SearsBuyerSOClosure_01_HomePage", LR_PASS);

	}


	lr_think_time(atoi(lr_eval_string("{thinkTime}")));

	/* -------------------------------------------------------------------------------

	   Transaction Title        : ServiceLive_SearsBuyerSOClosure_02_LoginPage

	   Transaction Description  : Click on Login button 

	   Transaction Parameters   : secureURL 

	   Correlated Parameters    : None

	 ------------------------------------------------------------------------------------ */


	web_reg_find("Text=Log in to ServiceLive","SaveCount=Login_Count",LAST);

	lr_start_transaction("ServiceLive_SearsBuyerSOClosure_02_LoginPage");

	web_url("loginAction.action", 
		"URL={secureURL}MarketFrontend/loginAction.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}MarketFrontend/homepage.action", 
		"Snapshot=t3.inf", 
		"Mode=HTML", 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=1606798723130.6867", 
		"URL=https://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=1606798723130.6867?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/loginAction.action", 
		"Snapshot=t4.inf", 
		"Mode=HTML", 
		LAST); */

	if((!strcmp(lr_eval_string(lr_eval_string("{Login_Count}")), "0")))
	{
		lr_end_transaction("ServiceLive_SearsBuyerSOClosure_02_LoginPage", LR_FAIL);
		lr_error_message("Failed to load Login page");
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	}
	else
	{
		lr_end_transaction("ServiceLive_SearsBuyerSOClosure_02_LoginPage", LR_PASS);

	}

	lr_think_time(atoi(lr_eval_string("{thinkTime}")));

/* -------------------------------------------------------------------------------

   Transaction Title        : ServiceLive_SearsBuyerSOClosure_03_EnterLoginCredentials 

   Transaction Description  : Enter login credentials 

   Transaction Parameters   : secureURL,UserId, Password 

   Correlated Parameters    : None

 ------------------------------------------------------------------------------------ */


	web_reg_find("Text=Welcome","SaveCount=Welcome_Count",LAST);

	lr_start_transaction("ServiceLive_SearsBuyerSOClosure_03_EnterLoginCredentials");
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
		"Name=username", "Value={UserId}", ENDITEM, 
		"Name=password", "Value={Password}", ENDITEM, 
		"Name=__checkbox_rememberUserId", "Value=true", ENDITEM, 
		LAST);

	if((!strcmp(lr_eval_string(lr_eval_string("{Welcome_Count}")), "0")))
	{
		lr_end_transaction("ServiceLive_SearsBuyerSOClosure_03_EnterLoginCredentials", LR_FAIL);
		lr_error_message("Failed to load Login");
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	}
	else
	{
		lr_end_transaction("ServiceLive_SearsBuyerSOClosure_03_EnterLoginCredentials", LR_PASS);

	}

	lr_think_time(atoi(lr_eval_string("{thinkTime}")));
/* -------------------------------------------------------------------------------

   Transaction Title        : ServiceLive_SearsBuyerSOClosure_04_ServiceOrderMonitor 

   Transaction Description  : Click on Service Order Monitor Tab 

   Transaction Parameters   : secureURL 

   Correlated Parameters    : None

 ------------------------------------------------------------------------------------ */


	web_reg_find("Text=ServiceLive: Service Order Monitor","SaveCount=ServiceOrder_Count", 
		LAST);

	lr_start_transaction("ServiceLive_SearsBuyerSOClosure_04_ServiceOrderMonitor");

	web_url("View »", 
		"URL={secureURL}MarketFrontend/serviceOrderMonitor.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/dashboardAction.action", 
		"Snapshot=t6.inf", 
		"Mode=HTML", 
		LAST);

	web_custom_request("gridHolder.action", 
		"URL={secureURL}MarketFrontend/monitor/gridHolder.action?tab=Today", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/serviceOrderMonitor.action", 
		"Snapshot=t7.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=tab=Today", 
		LAST);

	if((!strcmp(lr_eval_string(lr_eval_string("{ServiceOrder_Count}")), "0")))
	{
	lr_end_transaction("ServiceLive_SearsBuyerSOClosure_04_ServiceOrderMonitor", LR_FAIL);
	lr_error_message("Failed to load ServiceOrder Monitor");
	lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	}
	else
	{
	lr_end_transaction("ServiceLive_SearsBuyerSOClosure_04_ServiceOrderMonitor", LR_PASS);

	}

	lr_think_time(atoi(lr_eval_string("{thinkTime}")));

	/* -------------------------------------------------------------------------------

	Transaction Title        : ServiceLive_SearsBuyerSOClosure_05_SelectfromDropdownlist 

	Transaction Description  : Select Status as Completed from Drop Down list

	Transaction Parameters   : secureURL 

	Correlated Parameters    : None

	------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_SearsBuyerSOClosure_05_SelectfromDropdownlist");

	web_submit_data("gridLoader.action", 
		"Action={secureURL}MarketFrontend/monitor/gridLoader.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/gridLoader.action?tab=Today", 
		"Snapshot=t8.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=tab", "Value=Today", ENDITEM, 
		"Name=status", "Value=160", ENDITEM, 
		"Name=subStatus", "Value=null", ENDITEM, 
		"Name=serviceProName", "Value=null", ENDITEM, 
		"Name=marketName", "Value=null", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=resetSort", "Value=false", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_SearsBuyerSOClosure_05_SelectfromDropdownlist", LR_PASS);

	lr_think_time(atoi(lr_eval_string("{thinkTime}")));	


	/* -------------------------------------------------------------------------------

	   Transaction Title        : ServiceLive_SearsBuyerSOClosure_06_SortOrdersbyDate 

	   Transaction Description  : Sort the Orders by Date

	   Transaction Parameters   : secureURL 

	   Correlated Parameters    : None

	 ------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_SearsBuyerSOClosure_06_SortOrdersbyDate");

	web_submit_data("gridLoader.action_2", 
		"Action={secureURL}MarketFrontend/monitor/gridLoader.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/gridLoader.action", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=tab", "Value=Today", ENDITEM, 
		"Name=status", "Value=160", ENDITEM, 
		"Name=subStatus", "Value=0", ENDITEM, 
		"Name=serviceProName", "Value=", ENDITEM, 
		"Name=marketName", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=ServiceDate", ENDITEM, 
		"Name=sortOrder", "Value=ASC", ENDITEM, 
		"Name=resetSort", "Value=false", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_SearsBuyerSOClosure_06_SortOrdersbyDate",LR_AUTO);

	
    startIndex = ((rand()%15)*25)+1;
	
	endIndex=startIndex+24;
	
	sprintf (rand_param, "%d", startIndex);
	
	lr_save_string (lr_eval_string(rand_param), "random_sIndex");
	
	sprintf (rand_param, "%d", endIndex);
	
	lr_save_string (lr_eval_string(rand_param), "random_eIndex");
	
	
				//Correlation for SoId
		web_reg_save_param("SoId",
		"LB=soDetailsController.action?soId=",
		"RB=&defaultTab=Close and Pay&displayTab=Today\" target=\"_top\"",
		"Ord=ALL",
		"NotFound=Warning",
		LAST); 
	
	/* -------------------------------------------------------------------------------
	
	Transaction Title        : ServiceLive_SearsBuyerSOClosure_07_ClickLink 
	
	Transaction Description  : Click on Close And Pay Link
	
	Transaction Parameters   : secureURL 
	
	Correlated Parameters    : SoId
	
	------------------------------------------------------------------------------------ */
	

lr_start_transaction("ServiceLive_SearsBuyerSOClosure_07_ClickLink");

	web_submit_data("gridLoader.action_3", 
		"Action={secureURL}MarketFrontend/monitor/gridLoader.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/gridLoader.action", 
		"Snapshot=t10.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=startIndex", "Value={random_sIndex}", ENDITEM, 
		"Name=endIndex", "Value={random_eIndex}", ENDITEM, 
		"Name=pageSize", "Value=25", ENDITEM, 
		"Name=resetSort", "Value=false", ENDITEM, 
		"Name=tab", "Value=Today", ENDITEM, 
		"Name=soStatus", "Value=160", ENDITEM, 
		"Name=soSubStatus", "Value=null", ENDITEM, 
		"Name=serviceProName", "Value=null", ENDITEM, 
		"Name=marketName", "Value=null", ENDITEM, 
		"Name=pbFilterId", "Value=", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_SearsBuyerSOClosure_07_ClickLink", LR_PASS);

	lr_think_time(atoi(lr_eval_string("{thinkTime}")));
	
		rand_selection = (rand () %atoi(lr_eval_string("{SoId_count}")));
	
	if (rand_selection%5 != 0) {
		rand_selection = rand_selection+1;
	
	}
	
	
	sprintf (rand_param, "{SoId_%d}", rand_selection);
	
	lr_save_string (lr_eval_string(rand_param), "Random_SoId");
	
	/* -------------------------------------------------------------------------------
	
	Transaction Title        : ServiceLive_SearsBuyerSOClosure_08_SelectSO 
	
	Transaction Description  : Select an SO for closure
	
	Transaction Parameters   : secureURL 
	
	Correlated Parameters    : SoId
	
	------------------------------------------------------------------------------------ */
	
	lr_start_transaction("ServiceLive_SearsBuyerSOClosure_08_SelectSO");

	web_submit_data("soDocumentsAndPhotos_getDocumentsWidget.action", 
		"Action={secureURL}MarketFrontend/soDocumentsAndPhotos_getDocumentsWidget.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/xml", 
		"Referer={secureURL}MarketFrontend/serviceOrderMonitor.action#", 
		"Snapshot=t11.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=selectedSO", "Value={Random_SoId}", ENDITEM, 
		"Name=groupInd", "Value=false", ENDITEM, 
		"Name=groupId", "Value=", ENDITEM, 
		"Name=resId", "Value=null", ENDITEM, 
		"Name=selectedRowIndex", "Value=1", ENDITEM, 
		"Name=currentSpendLimit", "Value=60.00", ENDITEM, 
		"Name=currentLimitLabor", "Value=", ENDITEM, 
		"Name=currentLimitParts", "Value=", ENDITEM, 
		"Name=totalSpendLimit", "Value=", ENDITEM, 
		"Name=totalSpendLimitParts", "Value=", ENDITEM, 
		"Name=incSpendLimitComment", "Value=", ENDITEM, 
		"Name=reasonId", "Value=null", ENDITEM, 
		"Name=requestFrom", "Value=SOM", ENDITEM, 
		"Name=cancelComment", "Value=", ENDITEM, 
		"Name=subject", "Value=", ENDITEM, 
		"Name=message", "Value=", ENDITEM, 
		"Name=theRole", "Value=3", ENDITEM, 
		"Name=tab", "Value=Today", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_SearsBuyerSOClosure_08_SelectSO",LR_AUTO);

	lr_think_time(atoi(lr_eval_string("{thinkTime}")));

	/* -------------------------------------------------------------------------------

	   Transaction Title        : ServiceLive_SearsBuyerSOClosure_09_ClickCloseAndPaySO

	   Transaction Description  : Click on Close And Pay Link 

	   Transaction Parameters   : secureURL 

	   Correlated Parameters    : Random_SoId

	 ------------------------------------------------------------------------------------ */

	web_reg_find("Text=ServiceLive : ","SaveCount=Pay_Count",  
		LAST);

	lr_start_transaction("ServiceLive_SearsBuyerSOClosure_09_ClickCloseAndPaySO");

	web_url("soDetailsController.action", 
		"URL={secureURL}MarketFrontend/monitor/soDetailsController.action?soId={Random_SoId}&defaultTab=Close%20and%20Pay&displayTab=Today", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/gridLoader.action", 
		"Snapshot=t12.inf", 
		"Mode=HTML", 
		LAST);

	web_url("promoAction_displayPromotion.action", 
		"URL={secureURL}MarketFrontend/monitor/promoAction_displayPromotion.action?soId={Random_SoId}&contentLocation=SODProvider", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/soDetailsController.action?soId={Random_SoId}&defaultTab=Close and Pay&displayTab=Today", 
		"Snapshot=t13.inf", 
		"Mode=HTML", 
		LAST);

	web_url("soDetailsDisplayCloseAndPay.action", 
		"URL={secureURL}MarketFrontend/monitor/soDetailsDisplayCloseAndPay.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/soDetailsController.action?soId={Random_SoId}&defaultTab=Close and Pay&displayTab=Today", 
		"Snapshot=t14.inf", 
		"Mode=HTML", 
		LAST);

	web_url("soDetailsQuickLinks.action", 
		"URL={secureURL}MarketFrontend/monitor/soDetailsQuickLinks.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/soDetailsController.action?soId={Random_SoId}&defaultTab=Close and Pay&displayTab=Today", 
		"Snapshot=t15.inf", 
		"Mode=HTML", 
		LAST);

	if((!strcmp(lr_eval_string(lr_eval_string("{Pay_Count}")), "0")))
	   {
		   lr_end_transaction("ServiceLive_SearsBuyerSOClosure_09_ClickCloseAndPaySO", LR_FAIL);
		   lr_error_message("Failed to load Summary Tab");
		   lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	   }
	   else
	   {
		   lr_end_transaction("ServiceLive_SearsBuyerSOClosure_09_ClickCloseAndPaySO", LR_PASS);

	   }

	   lr_think_time(atoi(lr_eval_string("{thinkTime}")));


	   /* ------------------------------------------------------------------------------------

		  Transaction Title        : ServiceLive_SearsBuyerSOClosure_10_ClickCloseAndPayButton

		  Transaction Description  : Click on Close And Pay button 

		  Transaction Parameters   : secureURL 

		  Correlated Parameters    : Random_SoId

		---------------------------------------------------------------------------------------- */


	 lr_start_transaction("ServiceLive_SearsBuyerSOClosure_10_ClickCloseAndPayButton");

	web_submit_data("soDetailsSubmitCloseAndPay.action", 
		"Action={secureURL}MarketFrontend/monitor/soDetailsSubmitCloseAndPay.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/soDetailsController.action?soId={Random_SoId}&defaultTab=Close and Pay&displayTab=Today", 
		"Snapshot=t16.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=x", "Value=80", ENDITEM, 
		"Name=y", "Value=24", ENDITEM, 
		LAST);

	web_url("promoAction_displayPromotion.action_2", 
		"URL={secureURL}MarketFrontend/monitor/promoAction_displayPromotion.action?soId={Random_SoId}&contentLocation=SODProvider", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/soDetailsController.action?defaultTab=Rate%20Provider", 
		"Snapshot=t17.inf", 
		"Mode=HTML", 
		LAST);

	web_url("soDetailsRateProvider.action", 
		"URL={secureURL}MarketFrontend/monitor/soDetailsRateProvider.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/soDetailsController.action?defaultTab=Rate%20Provider", 
		"Snapshot=t18.inf", 
		"Mode=HTML", 
		LAST);

	web_url("soDetailsQuickLinks.action_2", 
		"URL={secureURL}MarketFrontend/monitor/soDetailsQuickLinks.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/soDetailsController.action?defaultTab=Rate%20Provider", 
		"Snapshot=t19.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("ServiceLive_SearsBuyerSOClosure_10_ClickCloseAndPayButton",LR_AUTO);

	lr_think_time(atoi(lr_eval_string("{thinkTime}")));

	/* -------------------------------------------------------------------------------

	   Transaction Title        : ServiceLive_SearsBuyerSOClosure_11_ClickSummaryTab 

	   Transaction Description  : Click on Summary Tab 

	   Transaction Parameters   : secureURL 

	   Correlated Parameters    : None

	 ------------------------------------------------------------------------------------ */

	web_reg_find("Text=Successfully closed the service order","SaveCount=Close_Count",LAST);

	lr_start_transaction("ServiceLive_SearsBuyerSOClosure_11_ClickSummaryTab");

	web_url("soDetailsSummary.action", 
		"URL={secureURL}MarketFrontend/monitor/soDetailsSummary.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/soDetailsController.action?defaultTab=Rate%20Provider", 
		"Snapshot=t20.inf", 
		"Mode=HTML", 
		LAST);

	web_url("soDetailsQuickLinks.action_3", 
		"URL={secureURL}MarketFrontend/monitor/soDetailsQuickLinks.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/soDetailsController.action?defaultTab=Rate%20Provider", 
		"Snapshot=t21.inf", 
		"Mode=HTML", 
		LAST);

	if((!strcmp(lr_eval_string(lr_eval_string("{Close_Count}")), "0")))
	{
		lr_end_transaction("ServiceLive_SearsBuyerSOClosure_11_ClickSummaryTab", LR_FAIL);
		lr_error_message("Failed to load Summary Tab");
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	}
	else
	{
		lr_end_transaction("ServiceLive_SearsBuyerSOClosure_11_ClickSummaryTab", LR_PASS);

	}

	lr_think_time(atoi(lr_eval_string("{thinkTime}")));

	web_reg_find("Text=Login","SaveCount=LogOut_Count",LAST);

	/* -------------------------------------------------------------------------------

	   Transaction Title        : ServiceLive_SearsBuyerSOClosure_12_LogOut 

	   Transaction Description  : Click on Logout 

	   Transaction Parameters   : secureURL 

	   Correlated Parameters    : None

	 ------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_SearsBuyerSOClosure_12_LogOut");

	web_url("doLogout.action", 
		"URL={secureURL}MarketFrontend/doLogout.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t22.inf", 
		"Mode=HTML", 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=1482132130837.2458", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=1482132130837.2458?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}MarketFrontend/homepage.action", 
		"Snapshot=t23.inf", 
		"Mode=HTML", 
		LAST);*/

	if((!strcmp(lr_eval_string(lr_eval_string("{LogOut_Count}")), "0")))
	{
		lr_end_transaction("ServiceLive_SearsBuyerSOClosure_12_LogOut", LR_FAIL);
		lr_error_message("Failed to load LogOut");
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	}
	else
	{
		lr_end_transaction("ServiceLive_SearsBuyerSOClosure_12_LogOut", LR_PASS);

	}

	return 0;
}