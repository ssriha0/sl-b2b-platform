ServiceLive_Buyer_AdvanceSearch()
{

	web_reg_find("Text=ServiceLive : Home Improvement & Repair", 
		LAST);

	/* -------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_01_HomePage
	   Transaction Description  : Access the home page
	   Transaction Parameters   : loginURL
	   Correlated Parameters    : None
	 ---------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_01_HomePage");

	web_url("homepage.action", 
		"URL={loginURL}MarketFrontend/homepage.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t1.inf", 
		"Mode=HTML", 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=4564140244454.214", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=4564140244454.214?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}MarketFrontend/homepage.action", 
		"Snapshot=t2.inf", 
		"Mode=HTML", 
		LAST);*/

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_01_HomePage",LR_AUTO);

		lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=ServiceLive : Log in to ServiceLive", 
		LAST);

	/* -------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_02_LoginPage
	   Transaction Description  : Access the login page
	   Transaction Parameters   : secureURL,loginURL
	   Correlated Parameters    : None
	 ---------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_02_LoginPage");

	web_url("loginAction.action", 
		"URL={secureURL}MarketFrontend/loginAction.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}MarketFrontend/homepage.action", 
		"Snapshot=t3.inf", 
		"Mode=HTML", 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=6034536007460.914", 
		"URL=https://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=6034536007460.914?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/loginAction.action", 
		"Snapshot=t4.inf", 
		"Mode=HTML", 
		LAST);*/

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_02_LoginPage",LR_AUTO);

		lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=ServiceLive Dashboard", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	/* -----------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_03_EnterLoginCredentials
	   Transaction Description  : Enter login credentials
	   Transaction Parameters   : secureURL,userName,Password
	   Correlated Parameters    : None
	 ------------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_03_EnterLoginCredentials");

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

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_03_EnterLoginCredentials",LR_AUTO);

		lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=ServiceLive: Service Order Monitor", 
		LAST);

	web_reg_find("Text=ServiceLive : Service Order Monitor", 
		LAST);

	/* --------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_04_ClickServiceOrderMonitor
	   Transaction Description  : Click on Service Order Monitor Page
	   Transaction Parameters   : secureURL
	   Correlated Parameters    : None
	 ---------------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_04_ClickServiceOrderMonitor");

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

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_04_ClickServiceOrderMonitor",LR_AUTO);

		lr_think_time(atoi(lr_eval_string("{think_time}")));

    web_reg_save_param("optionValue",
        "LB=<option value='",
        "RB='>",
        "Ord=ALL",
        "Search=Body",
        LAST);

	web_reg_save_param("referenceValue",
		"LB=<option value='CR-",
		"RB='>Ref:",
		"Ord=ALL",
		"Search=Body",
		LAST);

	/* ------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_05_ClickSearchTab
	   Transaction Description  : Click on Search Tab
	   Transaction Parameters   : secureURL
	   Correlated Parameters    : None
	 -------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_05_ClickSearchTab");

	web_custom_request("gridHolder.action_2", 
		"URL={secureURL}MarketFrontend/monitor/gridHolder.action?tab=Search&dojo.preventCache=1271987005053", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/serviceOrderMonitor.action", 
		"Snapshot=t8.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=tab=Search&dojo.preventCache=1271987005053", 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_05_ClickSearchTab",LR_AUTO);

		lr_think_time(atoi(lr_eval_string("{think_time}")));

	count=atoi(lr_eval_string("{optionValue_count}") );

	value=(rand() % count)+1;   

	sprintf( random_value,
		"{optionValue_%d}",
		value );

    sprintf( out_value,
	"%s",
	lr_eval_string(random_value) );

	switch(value)
	{
	case 1: 

	/* ---------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchBycheckNumber
	   Transaction Description  : Enter Check Number and Click on Search button
	   Transaction Parameters   : secureURL,checkNumber
	   Correlated Parameters    : None
	 ----------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBycheckNumber");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value=", ENDITEM, 
		"Name=checkNumberVals", "Value={checkNumber}", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBycheckNumber",LR_AUTO);
	break;

	case 2: 

	/* ----------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchBycustomerName
	   Transaction Description  : Enter Customer Name and Click on Search button
	   Transaction Parameters   : secureURL,customerName
	   Correlated Parameters    : None
	 ------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBycustomerName");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value=", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value={customerName}", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBycustomerName",LR_AUTO);

	break;

	case 3: 

	/* ----------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchByphoneNumber
	   Transaction Description  : Enter PhoneNumber and Click on Search button
	   Transaction Parameters   : secureURL,phoneNumber
	   Correlated Parameters    : None
	 ------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByphoneNumber");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value=", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value={phoneNumber}", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByphoneNumber",LR_AUTO);

	break;

	case 4: 

	/* ------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchByproviderFirmId
	   Transaction Description  : Enter providerFirmId and Click on Search button
	   Transaction Parameters   : secureURL,providerFirmId
	   Correlated Parameters    : None
	 -------------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByproviderFirmId");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value=", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value={providerFirmId}", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByproviderFirmId",LR_AUTO);

	break;

	case 5: 

	/* ------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchByunitNumber
	   Transaction Description  : Enter unitNumber and Click on Search button
	   Transaction Parameters   : secureURL,unitNumber
	   Correlated Parameters    : referenceValue
	 -------------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByunitNumber");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value={referenceValue_1}#{unitNumber}", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByunitNumber",LR_AUTO);

	break;
	
	case 6: 

	/* ------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchByorderNumber
	   Transaction Description  : Enter orderNumber and Click on Search button
	   Transaction Parameters   : secureURL,orderNumber
	   Correlated Parameters    : referenceValue
	 -------------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByorderNumber");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value={referenceValue_2}#{orderNumber}", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByorderNumber",LR_AUTO);

	break;

	case 7: 

	/* ------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchBysalesCheckNum1
	   Transaction Description  : Enter salesCheckNum1 and Click on Search button
	   Transaction Parameters   : secureURL,salesCheckNum1
	   Correlated Parameters    : referenceValue
	 -------------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBysalesCheckNum1");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value={referenceValue_3}#{salesCheckNum1}", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBysalesCheckNum1",LR_AUTO);

	break;

	case 8: 

	/* -------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchBysalesCheckDate1
	   Transaction Description  : Enter salesCheckDate1 and Click on Search button
	   Transaction Parameters   : secureURL,salesCheckDate1
	   Correlated Parameters    : None
	 --------------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBysalesCheckDate1");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value={referenceValue_4}#{salesCheckDate1}", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBysalesCheckDate1",LR_AUTO);

	break;

	case 9: 

	/* ---------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchBysalesCheckNumber2
	   Transaction Description  : Enter salesCheckNumber2 and Click on Search button
	   Transaction Parameters   : secureURL,salesCheckNumber2
	   Correlated Parameters    : referenceValue_5
	 ----------------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBysalesCheckNumber2");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value={referenceValue_5}#{salesCheckNumber2}", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBysalesCheckNumber2",LR_AUTO);

	break;

	case 10: 

	/* ---------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchBysalesCheckNumber3
	   Transaction Description  : Enter salesCheckNumber3 and Click on Search button
	   Transaction Parameters   : secureURL,salesCheckNumber3
	   Correlated Parameters    : referenceValue_6
	 ----------------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBysalesCheckNumber3");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value={referenceValue_6}#{salesCheckNumber3}", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBysalesCheckNumber3",LR_AUTO);

	break;

	case 11: 

	/* ---------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchBymodelNumber
	   Transaction Description  : Enter modelNumber and Click on Search button
	   Transaction Parameters   : secureURL,modelNumber
	   Correlated Parameters    : referenceValue_7
	 ----------------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBymodelNumber");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value={referenceValue_7}#{modelNumber}", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBymodelNumber",LR_AUTO);

	break;

	case 12: 

	/* ---------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchBymerchandiseCode
	   Transaction Description  : Enter merchandiseCode and Click on Search button
	   Transaction Parameters   : secureURL,merchandiseCode
	   Correlated Parameters    : referenceValue_8
	 ----------------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBymerchandiseCode");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value={referenceValue_8}#{merchandiseCode}", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBymerchandiseCode",LR_AUTO);

	break;

	case 13: 

	/* ---------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchBybrand
	   Transaction Description  : Enter brand and Click on Search button
	   Transaction Parameters   : secureURL,brand
	   Correlated Parameters    : referenceValue_9
	 ----------------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBybrand");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value={referenceValue_9}#{brand}", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBybrand",LR_AUTO);

	break;

	case 14: 

	/* ----------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchBypickupLocationCode
	   Transaction Description  : Enter pickupLocationCode and Click on Search button
	   Transaction Parameters   : secureURL,pickupLocationCode
	   Correlated Parameters    : referenceValue_10
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBypickupLocationCode");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value={referenceValue_10}#{pickupLocationCode}", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBypickupLocationCode",LR_AUTO);

	break;

	case 15: 

	/* ----------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchByspecialityCode
	   Transaction Description  : Enter specialityCode and Click on Search button
	   Transaction Parameters   : secureURL,specialityCode
	   Correlated Parameters    : referenceValue_11
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByspecialityCode");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value={referenceValue_11}#{specialityCode}", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByspecialityCode",LR_AUTO);

	break;

	case 16: 

	/* ----------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchBymainSKU
	   Transaction Description  : Enter mainSKU and Click on Search button
	   Transaction Parameters   : secureURL,mainSKU
	   Correlated Parameters    : referenceValue_12
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBymainSKU");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value={referenceValue_12}#{mainSKU}", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBymainSKU",LR_AUTO);

	break;

	case 17: 

	/* ----------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchBystoreNumber
	   Transaction Description  : Enter storeNumber and Click on Search button
	   Transaction Parameters   : secureURL,storeNumber
	   Correlated Parameters    : referenceValue_13
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBystoreNumber");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value={referenceValue_13}#{storeNumber}", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBystoreNumber",LR_AUTO);

	break;

	case 18: 

	/* ----------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchBymainCategoryId
	   Transaction Description  : Enter mainCategoryId and Click on Search button
	   Transaction Parameters   : secureURL,mainCategoryId
	   Correlated Parameters    : referenceValue_14
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBymainCategoryId");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value={referenceValue_14}#{mainCategoryId}", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBymainCategoryId",LR_AUTO);

	break;

	case 19: 

	/* ----------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchBycategoryId
	   Transaction Description  : Enter categoryId and Click on Search button
	   Transaction Parameters   : secureURL,categoryId
	   Correlated Parameters    : referenceValue_15
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBycategoryId");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value={referenceValue_15}#{categoryId}", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBycategoryId",LR_AUTO);

	break;

	case 20: 

	/* ----------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchBysubCategoryId
	   Transaction Description  : Enter subCategoryId and Click on Search button
	   Transaction Parameters   : secureURL,subCategoryId
	   Correlated Parameters    : referenceValue_16
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBysubCategoryId");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value={referenceValue_16}#{subCategoryId}", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBysubCategoryId",LR_AUTO);

	break;

	case 21: 

	/* ----------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchByskill
	   Transaction Description  : Enter skill and Click on Search button
	   Transaction Parameters   : secureURL,skill
	   Correlated Parameters    : referenceValue_17
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByskill");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value={referenceValue_17}#{skill}", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByskill",LR_AUTO);

	break;

	case 22: 

	/* ----------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchByprocessId
	   Transaction Description  : Enter processId and Click on Search button
	   Transaction Parameters   : secureURL,processId
	   Correlated Parameters    : referenceValue_18
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByprocessId");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value={referenceValue_18}#{processId}", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByprocessId",LR_AUTO);

	break;

	case 23: 

	/* ----------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchByserviceOrderId
	   Transaction Description  : Enter serviceOrderId and Click on Search button
	   Transaction Parameters   : secureURL,serviceOrderId
	   Correlated Parameters    : None
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByserviceOrderId");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value=", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value={serviceOrderId}", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByserviceOrderId",LR_AUTO);

	break;

	case 24: 

	/* ----------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchByserviceProId
	   Transaction Description  : Enter serviceProId and Click on Search button
	   Transaction Parameters   : secureURL,serviceProId
	   Correlated Parameters    : None
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByserviceProId");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value=", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value={serviceProId}", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByserviceProId",LR_AUTO);

	break;

	case 25: 

	/* ----------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchByserviceProName
	   Transaction Description  : Enter serviceProName and Click on Search button
	   Transaction Parameters   : secureURL,serviceProName
	   Correlated Parameters    : None
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByserviceProName");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value=", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value={serviceProName}", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByserviceProName",LR_AUTO);

	break;

	case 26: 

	/* ----------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchByzipCode
	   Transaction Description  : Enter zipCode and Click on Search button
	   Transaction Parameters   : secureURL,zipCode
	   Correlated Parameters    : None
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByzipCode");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value=", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value={zipCode}", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByzipCode",LR_AUTO);

	break;

	case 27: 

	/* ----------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchBystartDate
	   Transaction Description  : Enter startDate and Click on Search button
	   Transaction Parameters   : secureURL,startDate
	   Correlated Parameters    : None
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBystartDate");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value=", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value={startDate}", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBystartDate",LR_AUTO);

	break;

	case 28: 

	/* ----------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchByendDate
	   Transaction Description  : Enter endDate and Click on Search button
	   Transaction Parameters   : secureURL,endDate
	   Correlated Parameters    : None
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByendDate");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value=", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value={endDate}", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByendDate",LR_AUTO);

	break;

	case 29: 

	/* ----------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchBystartDate
	   Transaction Description  : Enter startDate and Click on Search button
	   Transaction Parameters   : secureURL,startDate
	   Correlated Parameters    : None
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBystartDate");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value=", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value={startDate}", ENDITEM, 
		"Name=endDate", "Value=", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchBystartDate",LR_AUTO);

	break;

	case 30: 

	/* ----------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_Buyer_AdvanceSearch_06_SearchByendDate
	   Transaction Description  : Enter endDate and Click on Search button
	   Transaction Parameters   : secureURL,endDate
	   Correlated Parameters    : None
	 ------------------------------------------------------------------------------------------ */

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByendDate");

	web_submit_data("soSearch.action", 
		"Action={secureURL}MarketFrontend/monitor/soSearch.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}MarketFrontend/monitor/PBWorkflowSearch.action?tab=Search&wfFlag=&pbFilterId=&pbFilterName=&pbFilterOpt=&refType=&refVal=", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=pageIndicator", "Value=", ENDITEM, 
		"Name=searchType", "Value=", ENDITEM, 
		"Name=searchValue", "Value=", ENDITEM, 
		"Name=tab", "Value=Search", ENDITEM, 
		"Name=status", "Value=", ENDITEM, 
		"Name=subStatus", "Value=", ENDITEM, 
		"Name=sortColumnName", "Value=null", ENDITEM, 
		"Name=sortOrder", "Value=null", ENDITEM, 
		"Name=stateVals", "Value=", ENDITEM, 
		"Name=skillVals", "Value=", ENDITEM, 
		"Name=marketVals", "Value=", ENDITEM, 
		"Name=statusVals", "Value=", ENDITEM, 
		"Name=custRefVals", "Value=", ENDITEM, 
		"Name=checkNumberVals", "Value=", ENDITEM, 
		"Name=customerNameVals", "Value=", ENDITEM, 
		"Name=phoneVals", "Value=", ENDITEM, 
		"Name=providerFirmIdVals", "Value=", ENDITEM, 
		"Name=serviceOrderIdVals", "Value=", ENDITEM, 
		"Name=serviceProIdVals", "Value=", ENDITEM, 
		"Name=serviceProNameVals", "Value=", ENDITEM, 
		"Name=zipCodeVals", "Value=", ENDITEM, 
		"Name=startDate", "Value=", ENDITEM, 
		"Name=endDate", "Value={endDate}", ENDITEM, 
		"Name=mainCatId", "Value=", ENDITEM, 
		"Name=catAndSubCatId", "Value=", ENDITEM, 
		"Name=count", "Value=null", ENDITEM, 
		"Name=wfFlag", "Value=", ENDITEM, 
		"Name=isSearchClicked", "Value=1", ENDITEM, 
		LAST);

	
	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_06_SearchByendDate",LR_AUTO);

}
	lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=Join ServiceLive","SaveCount=Logout_Count",LAST);

	lr_start_transaction("ServiceLive_Buyer_AdvanceSearch_07_ClickLogOut");

	web_url("doLogout.action", 
		"URL={secureURL}MarketFrontend/doLogout.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t10.inf", 
		"Mode=HTML", 
		LAST);


	lr_end_transaction("ServiceLive_Buyer_AdvanceSearch_07_ClickLogOut",LR_AUTO);

	return 0;
}