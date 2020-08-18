ServiceLive_SPN_CampaignApproval()
{

	web_reg_find("Text=ServiceLive : Home Improvement & Repair","SaveCount=Homepage_Count", 
		LAST);

	/* ------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_SPN_CampaignApproval_01_HomePage
	   Transaction Description  : Access the Home Page
	   Transaction Parameters   : loginURL
	   Correlated Parameters    : None
	 --------------------------------------------------------------------------*/

	lr_start_transaction("ServiceLive_SPN_CampaignApproval_01_HomePage");

	web_url("homepage.action", 
		"URL={loginURL}MarketFrontend/homepage.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t1.inf", 
		"Mode=HTML", 
		LAST);

		if (atoi(lr_eval_string("{Homepage_Count}")) > 0)
	{ 
		lr_output_message("Homepage successful");
		lr_end_transaction("ServiceLive_SPN_CampaignApproval_01_HomePage", LR_PASS);
	}
	else
	{ 
		lr_error_message("Homepage failed");
		lr_end_transaction("ServiceLive_SPN_CampaignApproval_01_HomePage", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 

	lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=ServiceLive : Log in to ServiceLive","SaveCount=Loginpage_Count",  
		LAST);

	/* -------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_SPN_CampaignApproval_01_HomePage
	   Transaction Description  : Access the Login Page
	   Transaction Parameters   : loginURL
	   Correlated Parameters    : None
	 ---------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_SPN_CampaignApproval_02_LoginPage");


	web_url("Login", 
		"URL={secureURL}MarketFrontend/loginAction.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}MarketFrontend/homepage.action", 
		"Snapshot=t2.inf", 
		"Mode=HTML", 
		LAST);

	//web_add_cookie("VISITOR_INFO1_LIVE=ssBqfC5RaMw; DOMAIN=www.youtube.com");

/*	web_url("q3T_-gaV_j8&hl=en&fs=1&rel=0", 
		"URL=http://www.youtube.com/v/q3T_-gaV_j8&hl=en&fs=1&rel=0", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}MarketFrontend/homepage.action", 
		"Snapshot=t3.inf", 
		"Mode=HTML", 
		LAST);*/

		if (atoi(lr_eval_string("{Loginpage_Count}")) > 0)
	{ 
		lr_output_message("Login Page displayed successfully");
		lr_end_transaction("ServiceLive_SPN_CampaignApproval_02_LoginPage", LR_PASS);
	}
	else
	{ 
		lr_error_message("Login Page failed");
		lr_end_transaction("ServiceLive_SPN_CampaignApproval_02_LoginPage", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 
	
	lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=Your ServiceLive Dashboard","SaveCount=Login_Count", 
		LAST);

	/* ------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_SPN_CampaignApproval_03_EnterLoginCredentials
	   Transaction Description  : Enter the login credentials
	   Transaction Parameters   : secureURL,userName,Password
	   Correlated Parameters    : None
	 -------------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_SPN_CampaignApproval_03_EnterLoginCredentials");

	web_submit_data("doLogin.action", 
		"Action={secureURL}MarketFrontend/doLogin.action", 
		"Method=POST", 
		"EncType=multipart/form-data", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/loginAction.action", 
		"Snapshot=t4.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=username", "Value={userName}", ENDITEM, 
		"Name=password", "Value={Password}", ENDITEM, 
		"Name=__checkbox_rememberUserId", "Value=true", ENDITEM, 
		LAST);

		if (atoi(lr_eval_string("{Login_Count}")) > 0)
	{ 
		lr_output_message("{userName} logged in successfully");
		lr_end_transaction("ServiceLive_SPN_CampaignApproval_03_EnterLoginCredentials", LR_PASS);
	}
	else
	{ 
		lr_error_message("{userName} failed to log-in");
		lr_end_transaction("ServiceLive_SPN_CampaignApproval_03_EnterLoginCredentials", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 
	
	lr_think_time(atoi(lr_eval_string("{think_time}")));
	

	web_reg_find("Text=ServiceLive - Search Portal","SaveCount=Search_Count", 
		LAST);

	/* ------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_SPN_CampaignApproval_04_ClickSearch
	   Transaction Description  : Click on Search Tab
	   Transaction Parameters   : secureURL
	   Correlated Parameters    : None
	 -------------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_SPN_CampaignApproval_04_ClickSearch");

	web_url("Search Portal", 
		"URL={secureURL}MarketFrontend/adminSearch_execute.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/adminDashboard.action", 
		"Snapshot=t5.inf", 
		"Mode=HTML", 
		LAST);
	
		if (atoi(lr_eval_string("{Search_Count}")) > 0)
	{ 
		lr_output_message("Search Tab page displayed successfully");
		lr_end_transaction("ServiceLive_SPN_CampaignApproval_04_ClickSearch", LR_PASS);
	}
	else
	{ 
		lr_error_message("Search Tab page display failed");
		lr_end_transaction("ServiceLive_SPN_CampaignApproval_04_ClickSearch", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 
	
	lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=ServiceLive - Search Portal","SaveCount=Search_Count", 
		LAST);

	/* ---------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_SPN_CampaignApproval_05_EnterBuyerId&ClickSearch
	   Transaction Description  : Enter the Buyer Id and Click on Search button
	   Transaction Parameters   : secureURL
	   Correlated Parameters    : None
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_SPN_CampaignApproval_05_EnterBuyerId&ClickSearch");

	web_submit_data("adminSearch_searchBuyer.action", 
		"Action={secureURL}MarketFrontend/adminSearch_searchBuyer.action?sortKey=&sortOrder=desc", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/adminSearch_execute.action", 
		"Snapshot=t6.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=searchPortalBuyerVO.user.userId", "Value=", ENDITEM, 
		"Name=searchPortalBuyerVO.user.companyId", "Value=1000", ENDITEM, 
		"Name=searchPortalBuyerVO.user.fnameOrLname", "Value=", ENDITEM, 
		"Name=searchPortalBuyerVO.user.userName", "Value=", ENDITEM, 
		"Name=searchPortalBuyerVO.user.businessName", "Value=", ENDITEM, 
		"Name=searchPortalBuyerVO.user.fromSignUpDate", "Value=", ENDITEM, 
		"Name=searchPortalBuyerVO.user.toSignUpDate", "Value=", ENDITEM, 
		"Name=searchPortalBuyerVO.soId", "Value=", ENDITEM, 
		"Name=searchPortalBuyerVO.location.phoneNumber", "Value=", ENDITEM, 
		"Name=searchPortalBuyerVO.location.emailAddress", "Value=", ENDITEM, 
		"Name=searchPortalBuyerVO.location.state", "Value=-1", ENDITEM, 
		"Name=searchPortalBuyerVO.location.city", "Value=", ENDITEM, 
		"Name=searchPortalBuyerVO.location.zip", "Value=", ENDITEM, 
		LAST);

		if (atoi(lr_eval_string("{Search_Count}")) > 0)
	{ 
		lr_output_message("Buyer Result page displayed successfully");
		lr_end_transaction("ServiceLive_SPN_CampaignApproval_05_EnterBuyerId&ClickSearch", LR_PASS);
	}
	else
	{ 
		lr_error_message("Buyer Result page display failed");
		lr_end_transaction("ServiceLive_SPN_CampaignApproval_05_EnterBuyerId&ClickSearch", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 
	
	lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=ServiceLive : ","SaveCount = Action_Count", 
		LAST);

	/* ---------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_SPN_CampaignApproval_06_ClickTakeAction
	   Transaction Description  : Click on Take Action --> Adopt User
	   Transaction Parameters   : secureURL
	   Correlated Parameters    : None
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_SPN_CampaignApproval_06_ClickTakeAction");

	web_url("adminSearch_navigateToBuyerPage.action", 
		"URL={secureURL}MarketFrontend/adminSearch_navigateToBuyerPage.action?searchPortalBuyerVO.user.companyId=1000&searchPortalBuyerVO.user.userId=1&searchPortalBuyerVO.user.roleTypeId=3&searchPortalBuyerVO.user.adminName=scarpe5@searshc.com&searchPortalBuyerVO.user.userName=scarpe5@searshc.com&searchPortalBuyerVO.user.businessName=Sears+Holdings&searchPortalBuyerVO.location.city=Hoffman+Estates&searchPortalBuyerVO.location.state=IL&resourceId=", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/adminSearch_execute.action", 
		"Snapshot=t7.inf", 
		"Mode=HTML", 
		LAST);

		if (atoi(lr_eval_string("{Action_Count}")) > 0)
	{ 
		lr_output_message("Take Action page displayed successfully");
		lr_end_transaction("ServiceLive_SPN_CampaignApproval_06_ClickTakeAction", LR_PASS);
	}
	else
	{ 
		lr_error_message("Take Action page  display failed");
		lr_end_transaction("ServiceLive_SPN_CampaignApproval_06_ClickTakeAction", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 
	
	lr_think_time(atoi(lr_eval_string("{think_time}")));
	
	web_reg_find("Text=ServiceLive : Log in to Select Provider Network","SaveCount=Approval_Count",
		LAST);

	/* ---------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_SPN_CampaignApproval_07_CampaignApproval
	   Transaction Description  : Go to Administrator --> SPN Campaign Approve
	   Transaction Parameters   : secureURL
	   Correlated Parameters    : None
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_SPN_CampaignApproval_07_CampaignApproval");

	web_url("SPN Approve Campaign", 
		"URL={secureURL}MarketFrontend/spnMonitorAction_callExternalSPN.action?targetExternalAction=spnMonitorNetwork_display&buyerId=1000", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t8.inf", 
		"Mode=HTML", 
		LAST);

		if (atoi(lr_eval_string("{Approval_Count}")) > 0)
	{ 
		lr_output_message("Campaign Approval Page displayed successfully");
	}
	else
	{ 
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 

		lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=ServiceLive - Select Provider Network - Manage Networks","Save_Count=Login_Count", 
		LAST);

	/* ---------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_SPN_CampaignApproval_08_EnterLoginCredentials
	   Transaction Description  : Enter the login credentials
	   Transaction Parameters   : loginURL
	   Correlated Parameters    : None
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_SPN_CampaignApproval_08_EnterLoginCredentials");

	web_submit_data("spnLoginAction_loginUser.action", 
		"Action={loginURL}spn/spnLoginAction_loginUser.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnLoginAction_display.action?targetAction=spnMonitorNetwork_display&buyerId=1000", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=userName", "Value=rbutl4799", ENDITEM, 
		"Name=credential", "Value=Test123!", ENDITEM, 
		"Name=targetAction", "Value=spnMonitorNetwork_display", ENDITEM, 
		"Name=buyerId", "Value=1000", ENDITEM, 
		LAST);

		if (atoi(lr_eval_string("{Login_Count}")) > 0)
	{ 
		lr_output_message("Login credentials page displayed successfully");
		lr_end_transaction("ServiceLive_SPN_CampaignApproval_08_EnterLoginCredentials", LR_PASS);
	}
	else
	{ 
		lr_error_message("Login credentials page display failed");
		lr_end_transaction("ServiceLive_SPN_CampaignApproval_08_EnterLoginCredentials", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 

		lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_save_param("campaignId",
		"LB=campaignId\" id=\"campaignId\" value=\"",
		"RB=\" />",
		"Ord=1",
		"Search=Body",
		LAST);

	web_reg_find("Text=ServiceLive - Select Provider Network - Campaign\r\n\t\t\tMonitor", "Save_Count=Monitor_Count",
		LAST);

	/* ---------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_SPN_CampaignApproval_09_ClickCampaignMonitor
	   Transaction Description  : Click on Campaign Monitor Tab
	   Transaction Parameters   : loginURL
	   Correlated Parameters    : campaignId
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_SPN_CampaignApproval_09_ClickCampaignMonitor");

	web_url("Campaign Monitor", 
		"URL={loginURL}spn/spnMonitorCampaign_display.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorNetwork_display.action", 
		"Snapshot=t10.inf", 
		"Mode=HTML", 
		LAST);

	web_url("Campaign Details", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId={campaignId}", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t11.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=220", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t12.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_2", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=221", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t13.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_3", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=219", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t14.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_4", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=218", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t15.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_5", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=217", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t16.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_6", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=211", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t17.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_7", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=209", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t18.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_8", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=208", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t19.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_9", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=207", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t20.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_10", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=210", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t21.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_11", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=206", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t22.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_12", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=205", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t23.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_13", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=204", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t24.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_14", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=203", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t25.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_15", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=202", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t26.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_16", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=201", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t27.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_17", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=200", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t28.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_18", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=199", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t29.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_19", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=198", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t30.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_20", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=197", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t31.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_21", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=196", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t32.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_22", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=195", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t33.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_23", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=194", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t34.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_24", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=193", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t35.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_25", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=192", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t36.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_26", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=191", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t37.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_27", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=188", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t38.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_28", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=187", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t39.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_29", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=186", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t40.inf", 
		"Mode=HTML", 
		LAST);
	
		if (atoi(lr_eval_string("{Monitor_Count}")) > 0)
	{ 
		lr_output_message("Campaign Monitor page displayed successfully");
		lr_end_transaction("ServiceLive_SPN_CampaignApproval_09_ClickCampaignMonitor", LR_PASS);
	}
	else
	{ 
		lr_error_message("Campaign Monitor page display failed");
		lr_end_transaction("ServiceLive_SPN_CampaignApproval_09_ClickCampaignMonitor", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 
	
		lr_think_time(atoi(lr_eval_string("{think_time}")));
	
	web_reg_find("Text=ServiceLive - Select Provider Network - Campaign\r\n\t\t\tMonitor","Save_Count=Campaign_Count",
		LAST);

	/* ---------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_SPN_CampaignApproval_10_SelectCampaign
	   Transaction Description  : Select Campaign for Approval
	   Transaction Parameters   : loginURL
	   Correlated Parameters    : campaignId
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_SPN_CampaignApproval_10_SelectCampaign");

	web_submit_data("spnMonitorCampaign_approveCampaign.action", 
		"Action={loginURL}spn/spnMonitorCampaign_approveCampaign.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t41.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=campaignId", "Value={campaignId}", ENDITEM, 
		"Name=approveComments", "Value=Approved", ENDITEM, 
		LAST);

	web_url("Campaign Details_2", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId={campaignId}", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t42.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_30", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=220", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t43.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_31", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=221", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t44.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_32", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=219", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t45.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_33", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=218", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t46.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_34", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=217", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t47.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_35", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=211", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t48.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_36", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=210", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t49.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_37", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=209", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t50.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_38", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=208", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t51.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_39", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=207", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t52.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_40", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=206", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t53.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_41", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=205", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t54.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_42", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=204", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t55.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_43", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=203", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t56.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_44", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=202", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t57.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_45", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=201", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t58.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_46", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=200", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t59.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_47", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=199", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t60.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_48", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=198", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t61.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_49", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=197", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t62.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_50", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=196", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t63.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_51", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=195", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t64.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_52", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=194", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t65.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_53", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=193", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t66.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_54", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=192", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t67.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_55", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=191", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t68.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_56", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=188", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t69.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_57", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=187", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t70.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_58", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=186", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t71.inf", 
		"Mode=HTML", 
		LAST);

		if (atoi(lr_eval_string("{Campaign_Count}")) > 0)
	{ 
		lr_output_message("Selected Campaign displayed successfully");
		lr_end_transaction("ServiceLive_SPN_CampaignApproval_10_SelectCampaign", LR_PASS);
	}
	else
	{ 
		lr_error_message("Selected Campaign display failed");
		lr_end_transaction("ServiceLive_SPN_CampaignApproval_10_SelectCampaign", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 
	
		lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=ServiceLive : Log in to Select Provider Network", "SaveCount=LogOut_Count",
		LAST);

	/* --------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_SPN_CampaignApproval_11_ClickLogOut
	   Transaction Description  : Click Logout
	   Transaction Parameters   : loginURL
	   Correlated Parameters    : None
	 ----------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_SPN_CampaignApproval_11_ClickLogOut");

	web_url("spnLoginAction_logoutUser.action", 
		"URL={loginURL}spn/spnLoginAction_logoutUser.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t72.inf", 
		"Mode=HTML", 
		LAST);

		if (atoi(lr_eval_string("{LogOut_Count}")) > 0)
	{ 
		lr_output_message("LogOut page displayed successfully");
		lr_end_transaction("ServiceLive_SPN_CampaignApproval_11_ClickLogOut", LR_PASS);
	}
	else
	{ 
		lr_error_message("LogOut page display failed");
		lr_end_transaction("ServiceLive_SPN_CampaignApproval_11_ClickLogOut", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 
	
	return 0;
}