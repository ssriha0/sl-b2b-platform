Action()
{



	web_cleanup_cookies();

    
	lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Search=Body",
		"Text=Visit ServiceLive.com",
		"Fail=NotFound",
		LAST);

/* -------------------------------------------------------------------------------
   Transaction Title        : ServiceLive_ProviderCompletion_01_LaunchHomePage
   Transaction Description  : Launch the home page
   Transaction Parameters   : URL1
   Correlated Parameters    : None
 ---------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_ProviderCompletion_01_LaunchHomePage");

	web_url("homepage.action", 
		"URL=http://{URL1}/MarketFrontend/homepage.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t1.inf", 
		"Mode=HTML", 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=1886314897719.0742", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=1886314897719.0742?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://{URL1}/MarketFrontend/homepage.action", 
		"Snapshot=t2.inf", 
		"Mode=HTML", 
		LAST);*/

	lr_end_transaction("ServiceLive_ProviderCompletion_01_LaunchHomePage", LR_AUTO);

	/*login click*/

	lr_think_time(atoi(lr_eval_string("{think_time}")));


	web_reg_find("Text=ServiceLive : Log in to ServiceLive", "Fail=NotFound",
		LAST);

	web_reg_find("Search=Body",
		"Text=Please enter your information below to continue.", "Fail=NotFound",
		LAST);

	/*cont*/

/* -------------------------------------------------------------------------------
   Transaction Title        : ServiceLive_ProviderCompletion_02_ClickLogin
   Transaction Description  : Click on Login button
   Transaction Parameters   : URL
   Correlated Parameters    : None
 ---------------------------------------------------------------------------------- */
	lr_start_transaction("ServiceLive_ProviderCompletion_02_ClickLogin");

	web_url("loginAction.action", 
		"URL=https://{URL}/MarketFrontend/loginAction.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t3.inf", 
		"Mode=HTML", 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=7815128822745.853", 
		"URL=https://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=7815128822745.853?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://{URL}/MarketFrontend/loginAction.action", 
		"Snapshot=t4.inf", 
		"Mode=HTML", 
		LAST);*/

	lr_end_transaction("ServiceLive_ProviderCompletion_02_ClickLogin", LR_AUTO);

	/*Login*/

	lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=ServiceLive Dashboard","Fail=NotFound",
		LAST);

	web_reg_find("Text=ServiceLive : ",
		LAST);

/*	web_reg_find("Fail=NotFound",
		"Search=Body",
		"Text=Total orders",
		LAST);*/

/* -------------------------------------------------------------------------------
   Transaction Title        : ServiceLive_ProviderCompletion_03_LoginSubmit
   Transaction Description  : Enter Credentials and click on submit
   Transaction Parameters   : URL,UserId,Password
   Correlated Parameters    : None
 ---------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_ProviderCompletion_03_LoginSubmit");

	web_submit_data("doLogin.action", 
		"Action=https://{URL}/MarketFrontend/doLogin.action", 
		"Method=POST", 
		"EncType=multipart/form-data", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://{URL}/MarketFrontend/loginAction.action", 
		"Snapshot=t5.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=username", "Value={UserName}", ENDITEM, 
		"Name=password", "Value={Password}", ENDITEM, 
		"Name=__checkbox_rememberUserId", "Value=true", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_ProviderCompletion_03_LoginSubmit", LR_AUTO);

	/*Click Today*/

	lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=ServiceLive: Service Order Monitor", "Fail=NotFound",
		LAST);

	web_reg_find("Text=ServiceLive : Service Order Monitor","Fail=NotFound",
		LAST);

/* -------------------------------------------------------------------------------
   Transaction Title        : ServiceLive_ProviderCompletion_04_ClickTodayLink
   Transaction Description  : Click on Todays Link
   Transaction Parameters   : URL
   Correlated Parameters    : None
 ---------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_ProviderCompletion_04_ClickTodayLink");

	web_url("231", 
		"URL=https://{URL}/MarketFrontend/serviceOrderMonitor.action?displayTab=Today", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://{URL}/MarketFrontend/dashboardAction.action", 
		"Snapshot=t6.inf", 
		"Mode=HTML", 
		LAST);

	web_custom_request("gridHolder.action", 
		"URL=https://{URL}/MarketFrontend/monitor/gridHolder.action?tab=Today", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://{URL}/MarketFrontend/serviceOrderMonitor.action?displayTab=Today", 
		"Snapshot=t7.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=tab=Today", 
		LAST);

	web_submit_data("refreshTabs.action", 
		"Action=https://{URL}/MarketFrontend/monitor/refreshTabs.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/xml", 
		"Referer=https://{URL}/MarketFrontend/serviceOrderMonitor.action?displayTab=Today", 
		"Snapshot=t8.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		LAST);

	lr_end_transaction("ServiceLive_ProviderCompletion_04_ClickTodayLink", LR_AUTO);


	/*Select Filter -  Active*/

		//Correlation for SoId
		web_reg_save_param("SoId",
		"LB=soDetailsController.action?soId=",
		"RB=&defaultTab=Complete for Payment&displayTab=Today",
		"Ord=ALL",
        "NotFound=Warning",
		LAST); 

	lr_think_time(atoi(lr_eval_string("{think_time}")));

/* -------------------------------------------------------------------------------
   Transaction Title        : ServiceLive_ProviderCompletion_05_SelectActive
   Transaction Description  : Select SO Order with Active status
   Transaction Parameters   : URL
   Correlated Parameters    : SoId
 ---------------------------------------------------------------------------------- */
	lr_start_transaction("ServiceLive_ProviderCompletion_05_SelectActive");


	web_submit_data("gridLoader.action", 
		"Action=https://{URL}/MarketFrontend/monitor/gridLoader.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://{URL}/MarketFrontend/monitor/gridLoader.action?tab=Today", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=tab", "Value=Today", ENDITEM, 
		"Name=status", "Value=155", ENDITEM, 
		"Name=subStatus", "Value=null", ENDITEM, 
		"Name=serviceProName", "Value=", ENDITEM, 
		"Name=marketName", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=resetSort", "Value=false", ENDITEM, 
		LAST);

	web_submit_data("refreshTabs.action_2", 
		"Action=https://{URL}/MarketFrontend/monitor/refreshTabs.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/xml", 
		"Referer=https://{URL}/MarketFrontend/serviceOrderMonitor.action?displayTab=Today", 
		"Snapshot=t10.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		LAST);

	web_submit_data("refreshTabs.action_3", 
		"Action=https://{URL}/MarketFrontend/monitor/refreshTabs.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/xml", 
		"Referer=https://{URL}/MarketFrontend/serviceOrderMonitor.action?displayTab=Today", 
		"Snapshot=t11.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		LAST);

	lr_end_transaction("ServiceLive_ProviderCompletion_05_SelectActive", LR_AUTO);

	lr_think_time(atoi(lr_eval_string("{think_time}")));

/* -------------------------------------------------------------------------------
   Transaction Title        : ServiceLive_ProviderCompletion_06_ClickActiveSO
   Transaction Description  : Edit SO 
   Transaction Parameters   : URL,SoId
   Correlated Parameters    : None
 ---------------------------------------------------------------------------------- */
	lr_start_transaction("ServiceLive_ProviderCompletion_06_ClickActiveSO");

	web_submit_data("soDocumentsAndPhotos_getDocumentsWidget.action", 
		"Action=https://{URL}/MarketFrontend/soDocumentsAndPhotos_getDocumentsWidget.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/xml", 
		"Referer=https://{URL}/MarketFrontend/serviceOrderMonitor.action?displayTab=Today", 
		"Snapshot=t12.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=selectedSO", "Value={SoId_1}", ENDITEM, 
		"Name=groupInd", "Value=false", ENDITEM, 
		"Name=groupId", "Value=", ENDITEM, 
		"Name=resId", "Value=0", ENDITEM, 
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
		"Name=theRole", "Value=1", ENDITEM, 
		"Name=tab", "Value=Today", ENDITEM, 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_url("soDetailsController.action", 
		"URL=https://{URL}/MarketFrontend/monitor/soDetailsController.action?soId={SoId_1}&defaultTab=Complete%20for%20Payment&displayTab=Today", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://{URL}/MarketFrontend/monitor/gridLoader.action", 
		"Snapshot=t13.inf", 
		"Mode=HTML", 
		LAST);

	web_url("Complete for Payment", 
		"URL=https://{URL}/MarketFrontend/monitor/soDetailsCompleteForPayment_displayPage.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://{URL}/MarketFrontend/monitor/soDetailsController.action?soId={SoId_1}&defaultTab=Complete for Payment&displayTab=Today", 
		"Snapshot=t14.inf", 
		"Mode=HTML", 
		LAST);

	web_url("soDetailsQuickLinks.action", 
		"URL=https://{URL}/MarketFrontend/monitor/soDetailsQuickLinks.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://{URL}/MarketFrontend/monitor/soDetailsController.action?soId={SoId_1}&defaultTab=Complete for Payment&displayTab=Today", 
		"Snapshot=t15.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("ServiceLive_ProviderCompletion_06_ClickActiveSO",LR_AUTO);

/* -------------------------------------------------------------------------------
   Transaction Title        : ServiceLive_ProviderCompletion_07_SubmitforPayment
   Transaction Description  : Submit SO for Payment
   Transaction Parameters   : URL
   Correlated Parameters    : SoId
 ---------------------------------------------------------------------------------- */


	lr_start_transaction("ServiceLive_ProviderCompletion_07_SubmitforPayment");

	web_url("w9registrationAction_isW9exist.action", 
		"URL=https://{URL}/MarketFrontend/monitor/w9registrationAction_isW9exist.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://{URL}/MarketFrontend/monitor/soDetailsController.action?soId={SoId_1}&defaultTab=Complete for Payment&displayTab=Today", 
		"Snapshot=t16.inf", 
		"Mode=HTML", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("soDetailsCompleteForPayment_completeSo.action", 
		"Action=https://{URL}/MarketFrontend/soDetailsCompleteForPayment_completeSo.action", 
		"Method=POST", 
		"EncType=multipart/form-data", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://{URL}/MarketFrontend/monitor/soDetailsController.action?soId={SoId_1}&defaultTab=Complete for Payment&displayTab=Today", 
		"Snapshot=t17.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=w9isExist", "Value=true", ENDITEM, 
		"Name=w9isExistWithSSNInd", "Value=true", ENDITEM, 
		"Name=__checkbox_addonServicesDTO.addonCheckbox", "Value=true", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[0].quantity", "Value=0", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[0].endCustomerCharge", "Value=79.99", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[0].margin", "Value=0.4", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[1].quantity", "Value=0", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[1].endCustomerCharge", "Value=45.0", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[1].margin", "Value=0.4", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[2].quantity", "Value=0", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[2].endCustomerCharge", "Value=25.0", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[2].margin", "Value=0.4", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[3].quantity", "Value=0", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[3].endCustomerCharge", "Value=45.0", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[3].margin", "Value=0.4", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[4].quantity", "Value=0", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[4].endCustomerCharge", "Value=40.0", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[4].margin", "Value=0.4", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[5].quantity", "Value=0", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[5].endCustomerCharge", "Value=10.0", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[5].margin", "Value=0.4", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[6].quantity", "Value=0", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[6].endCustomerCharge", "Value=25.0", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[6].margin", "Value=0.4", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[7].quantity", "Value=0", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[7].endCustomerCharge", "Value=49.99", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[7].margin", "Value=0.4", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[8].description", "Value=D/22 - CHANGE OF SPECS", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[8].quantity", "Value=0", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[8].endCustomerCharge", "Value=0.0", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[8].margin", "Value=0.33", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[8].endCustomerSubtotal", "Value=", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[9].description", "Value=D/22 - INSTALLER WARRANTY", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[9].quantity", "Value=0", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[9].endCustomerCharge", "Value=0.0", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[9].margin", "Value=0.4", ENDITEM, 
		"Name=addonServicesDTO.addonServicesList[9].endCustomerSubtotal", "Value=", ENDITEM, 
		"Name=pmttype", "Value=CA", ENDITEM, 
		"Name=addonServicesDTO.checkNumber", "Value=", ENDITEM, 
		"Name=addonServicesDTO.checkAmount", "Value=", ENDITEM, 
		"Name=addonServicesDTO.creditCardNumber", "Value=", ENDITEM, 
		"Name=addonServicesDTO.selectedCreditCardType", "Value=-1", ENDITEM, 
		"Name=addonServicesDTO.selectedMonth", "Value=-1", ENDITEM, 
		"Name=addonServicesDTO.selectedYear", "Value=-1", ENDITEM, 
		"Name=addonServicesDTO.preAuthNumber", "Value=", ENDITEM, 
		"Name=addonServicesDTO.amtAuthorized", "Value=", ENDITEM, 
		"Name=resComments", "Value=Tst", ENDITEM, 
		"Name=resComments_leftChars", "Value=995", ENDITEM, 
		"Name=partsSl", "Value=0.00", ENDITEM, 
		"Name=finalPartPrice", "Value=0", ENDITEM, 
		"Name=laborSl", "Value=60.00", ENDITEM, 
		"Name=finalLaborPrice", "Value=10", ENDITEM, 
		"Name=partList[0].coreReturnCarrierId", "Value=-1", ENDITEM, 
		"Name=partList[0].coreReturnTrackingNumber", "Value=", ENDITEM, 
		"Name=partIdCount", "Value=1", ENDITEM, 
		"Name=buyerRefs[0].referenceValue", "Value=12345", ENDITEM, 
		"Name=buyerRefs[1].referenceValue", "Value=1234", ENDITEM, 
		"Name=buyerRefs[2].referenceValue", "Value=10", ENDITEM, 
		LAST);

	web_url("Summary", 
		"URL=https://{URL}/MarketFrontend/soDetailsSummary.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://{URL}/MarketFrontend/soDetailsController.action?defaultTab=Complete%20for%20Payment", 
		"Snapshot=t18.inf", 
		"Mode=HTML", 
		LAST);

	web_url("soDetailsQuickLinks.action_2", 
		"URL=https://{URL}/MarketFrontend/soDetailsQuickLinks.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://{URL}/MarketFrontend/soDetailsController.action?defaultTab=Complete%20for%20Payment", 
		"Snapshot=t19.inf", 
		"Mode=HTML", 
		LAST);

    
	lr_end_transaction("ServiceLive_ProviderCompletion_07_SubmitforPayment", LR_AUTO);

/* -------------------------------------------------------------------------------
   Transaction Title        : ServiceLive_ProviderCompletion_08_Logout
   Transaction Description  : Click on Logout
   Transaction Parameters   : URL
   Correlated Parameters    : None
 ---------------------------------------------------------------------------------- */

	lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=Join ServiceLive", "Fail=NotFound",
		LAST);

	lr_start_transaction("ServiceLive_ProviderCompletion_08_Logout");

	web_url("doLogout.action", 
		"URL=https://{URL}/MarketFrontend/doLogout.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t20.inf", 
		"Mode=HTML", 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=8902142083222.607", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=8902142083222.607?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://{URL1}/MarketFrontend/homepage.action", 
		"Snapshot=t21.inf", 
		"Mode=HTML", 
		LAST);*/

    
	lr_end_transaction("ServiceLive_ProviderCompletion_08_Logout", LR_AUTO);


	return 0;
}