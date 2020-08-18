ServiceLive_SPN_CreateNetworkAndCampaign()
{


	web_reg_save_param("jSessionId",
		"LB=action=\"/spn/spnLoginAction_loginUser.action;jsessionid=",
		"RB=\" method=\"post\">",
		"Ord=1",
		"Search=Body",
		LAST);
		
	web_reg_find("Text=ServiceLive : Log in to Select Provider Network","SaveCount=Homepage_Count",
		LAST);

	/* -------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_SPN_CreateNetworkAndCampaign_01_HomePage
	   Transaction Description  : Access the Home Page
	   Transaction Parameters   : loginURL
	   Correlated Parameters    : None
	 ---------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_01_HomePage");

	web_url("spnLoginAction_display.action", 
		"URL={loginURL}spn/spnLoginAction_display.action?targetAction=spnMonitorNetwork_display", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t10.inf", 
		"Mode=HTML", 
		LAST);

		if (atoi(lr_eval_string("{Homepage_Count}")) > 0)
	{ 
		lr_output_message("Homepage successful");
		lr_end_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_01_HomePage", LR_PASS);
	}
	else
	{ 
		lr_error_message("Homepage failed");
		lr_end_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_01_HomePage", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 


	lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=ServiceLive - Select Provider Network - Manage Networks","SaveCount=Login_Count", 
				 LAST);

	/* -------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_SPN_CreateNetworkAndCampaign_02_Login
	   Transaction Description  : Enter the login credentials
	   Transaction Parameters   : loginURL,userName,password
	   Correlated Parameters    : jSessionId
	 ---------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_02_Login");

	web_submit_data("spnLoginAction_loginUser.action;jsessionid={jSessionId}", 
		"Action={loginURL}spn/spnLoginAction_loginUser.action;jsessionid={jSessionId}", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnLoginAction_display.action?targetAction=spnMonitorNetwork_display", 
		"Snapshot=t11.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=userName", "Value={userName}", ENDITEM, 
		"Name=credential", "Value={password}", ENDITEM, 
		"Name=targetAction", "Value=spnMonitorNetwork_display", ENDITEM, 
		"Name=buyerId", "Value=", ENDITEM, 
		LAST);

		if (atoi(lr_eval_string("{Login_Count}")) > 0)
	{ 
		lr_output_message("Login for {userName} successful");
		lr_end_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_02_Login", LR_PASS);
	}
	else
	{ 
		lr_error_message("Login for {userName} failed");
		lr_end_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_02_Login", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 

	lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=ServiceLive - Select Provider Network - Create Networks","SaveCount=CreateNetwork_Count",  
		LAST);

	/* ------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_SPN_CreateNetworkAndCampaign_03_CreateNetwork
	   Transaction Description  : Click on Create Network Tab
	   Transaction Parameters   : loginURL
	   Correlated Parameters    : None
	 -------------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_03_CreateNetwork");

    web_url("Create Network", 
		"URL={loginURL}spn/spnCreateNetwork_display.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorNetwork_display.action", 
		"Snapshot=t12.inf", 
		"Mode=HTML", 
		LAST);

	/* ------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_SPN_CreateNetworkAndCampaign_04_GeneralInfo
	   Transaction Description  : Enter General Information
	   Transaction Parameters   : loginURL
	   Correlated Parameters    : None
	 -------------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_04_GeneralInfo");

    web_url("spnCreateNetwork_getMainServicesWithSkillsAjax.action", 
		"URL={loginURL}spn/spnCreateNetwork_getMainServicesWithSkillsAjax.action?&approvalItems.selectedMainServices=200", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnCreateNetwork_display.action", 
		"Snapshot=t13.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnCreateNetwork_getSubCategoriesAjax.action", 
		"URL={loginURL}spn/spnCreateNetwork_getSubCategoriesAjax.action?&approvalItems.selectedMainServices=200", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnCreateNetwork_display.action", 
		"Snapshot=t14.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_04_GeneralInfo",LR_AUTO);

	lr_think_time(atoi(lr_eval_string("{think_time}")));

    web_reg_save_param("documentId",
		"LB=\"msg\": \"",
		"RB=\"",
		"Ord=1",
		"Search=Body",
		LAST);

	/* --------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_SPN_CreateNetworkAndCampaign_05_SelectDocuments
	   Transaction Description  : Select Documents and Upload Them
	   Transaction Parameters   : loginURL
	   Correlated Parameters    : None
	 ---------------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_05_SelectDocuments");

	web_submit_data("spnCreateNetworkUploadDocument_uploadDocumentAjax.action", 
		"Action={loginURL}spn/spnCreateNetworkUploadDocument_uploadDocumentAjax.action", 
		"Method=POST", 
		"EncType=multipart/form-data", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnCreateNetwork_display.action", 
		"Snapshot=t15.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=uploadDocData.uploadDocTitle", "Value=Information Document for PT", ENDITEM, 
		"Name=uploadDocData.uploadDocType", "Value=1", ENDITEM, 
		"Name=uploadDocData.uploadDocDesc", "Value=Information document for PT", ENDITEM, 
		"Name=photoDoc", "Value=D:\\\\Sears\\\\ESB file path.doc", "File=Yes", ENDITEM, 
		LAST);

	web_reg_save_param("documentId1",
		"LB=\"msg\": \"",
		"RB=\"",
		"Ord=1",
		"Search=Body",
		LAST);

	web_submit_data("spnCreateNetworkUploadDocument_uploadDocumentAjax.action_2", 
		"Action={loginURL}spn/spnCreateNetworkUploadDocument_uploadDocumentAjax.action", 
		"Method=POST", 
		"EncType=multipart/form-data", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnCreateNetwork_display.action", 
		"Snapshot=t16.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=uploadDocData.uploadDocTitle", "Value=Electronic Signature", ENDITEM, 
		"Name=uploadDocData.uploadDocType", "Value=2", ENDITEM, 
		"Name=uploadDocData.uploadDocDesc", "Value=Electronic Signature Document for PT", ENDITEM,
		"Name=photoDoc", "Value=D:\\\\Sears\\\\Imp Steps Needs to be followed.doc", "File=Yes", ENDITEM, 
		LAST);


	web_reg_save_param("documentId2",
		"LB=\"msg\": \"",
		"RB=\"",
		"Ord=1",
		"Search=Body",
		LAST);

	web_submit_data("spnCreateNetworkUploadDocument_uploadDocumentAjax.action_3", 
		"Action={loginURL}spn/spnCreateNetworkUploadDocument_uploadDocumentAjax.action", 
		"Method=POST", 
		"EncType=multipart/form-data", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnCreateNetwork_display.action", 
		"Snapshot=t17.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=uploadDocData.uploadDocTitle", "Value=Sign Return Document", ENDITEM, 
		"Name=uploadDocData.uploadDocType", "Value=3", ENDITEM, 
		"Name=uploadDocData.uploadDocDesc", "Value=Sign and Return document for PT", ENDITEM,
		"Name=photoDoc", "Value=D:\\\\Sears\\\\Injection File drop.doc", "File=Yes", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_05_SelectDocuments",LR_AUTO);

	lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=ServiceLive - Select Provider Network - Create Networks", 
		LAST);

	/* --------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_SPN_CreateNetworkAndCampaign_06_ClickSave
	   Transaction Description  : Save the Network
	   Transaction Parameters   : loginURL,spnName
	   Correlated Parameters    : None
	 ---------------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_06_ClickSave");

	web_submit_data("spnCreateNetwork_saveAndDone.action", 
		"Action={loginURL}spn/spnCreateNetwork_saveAndDone.action", 
		"Method=POST", 
		"EncType=multipart/form-data", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnCreateNetwork_display.action", 
		"Snapshot=t18.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=spnHeader.effectiveDate", "Value=", ENDITEM, 
		"Name=spnHeader.comments", "Value=", ENDITEM, 
		"Name=spnHeader.spnName", "Value={spnName}", ENDITEM, 
		"Name=spnHeader.spnId", "Value=", ENDITEM, 
		"Name=buyerId", "Value=1000", ENDITEM, 
		"Name=spnHeader.contactName", "Value=Performance Tester", ENDITEM, 
		"Name=spnHeader.contactEmail", "Value=performancetester@servicelive.com", ENDITEM, 
		"Name=spnHeader.contactPhone", "Value=224-211-2121", ENDITEM, 
		"Name=spnHeader.spnDescription", "Value=SPN For Performance Testing", ENDITEM, 
		"Name=spnHeader.spnInstruction", "Value=", ENDITEM, 
		"Name=approvalItems.selectedMainServices", "Value=200", ENDITEM, 
		"Name=approvalItems.selectedSkills", "Value=3", ENDITEM, 
		"Name=approvalItems.selectedSkills", "Value=10", ENDITEM, 
		"Name=approvalItems.selectedMinimumRating", "Value=0", ENDITEM, 
		"Name=__checkbox_approvalItems.isNotRated", "Value=true", ENDITEM, 
		"Name=approvalItems.selectedLanguages", "Value=1", ENDITEM, 
		"Name=approvalItems.minimumCompletedServiceOrders", "Value=", ENDITEM, 
		"Name=approvalItems.vehicleLiabilitySelected", "Value=true", ENDITEM, 
		"Name=__checkbox_approvalItems.vehicleLiabilitySelected", "Value=true", ENDITEM, 
		"Name=approvalItems.vehicleLiabilityAmt", "Value=10000", ENDITEM, 
		"Name=approvalItems.vehicleLiabilityVerified", "Value=true", ENDITEM, 
		"Name=__checkbox_approvalItems.vehicleLiabilityVerified", "Value=true", ENDITEM, 
		"Name=approvalItems.workersCompensationSelected", "Value=true", ENDITEM, 
		"Name=__checkbox_approvalItems.workersCompensationSelected", "Value=true", ENDITEM, 
		"Name=approvalItems.workersCompensationVerified", "Value=true", ENDITEM, 
		"Name=__checkbox_approvalItems.workersCompensationVerified", "Value=true", ENDITEM, 
		"Name=approvalItems.commercialGeneralLiabilitySelected", "Value=true", ENDITEM, 
		"Name=__checkbox_approvalItems.commercialGeneralLiabilitySelected", "Value=true", ENDITEM, 
		"Name=approvalItems.commercialGeneralLiabilityAmt", "Value=10000", ENDITEM, 
		"Name=approvalItems.commercialGeneralLiabilityVerified", "Value=true", ENDITEM, 
		"Name=__checkbox_approvalItems.commercialGeneralLiabilityVerified", "Value=true", ENDITEM, 
		"Name=approvalItems.meetingRequired", "Value=true", ENDITEM, 
		"Name=__checkbox_approvalItems.meetingRequired", "Value=true", ENDITEM, 
		"Name=selectedDocument", "Value=-1", ENDITEM, 
		"Name=uploadDocData.uploadDocTitle", "Value=", ENDITEM, 
		"Name=uploadDocData.uploadDocType", "Value=1", ENDITEM, 
		"Name=uploadDocData.uploadDocDesc", "Value=", ENDITEM, 
		"Name=photoDoc", "Value=", "File=Yes", ENDITEM, 
		"Name=docId", "Value={documentId}", ENDITEM, 
		"Name=uploadDocData.uploadDocIdList", "Value={documentId}", ENDITEM, 
		"Name=uploadDocData.uploadDocTitleList", "Value=Information Document for PT", ENDITEM, 
		"Name=uploadDocData.uploadDocTypeList", "Value=1", ENDITEM, 
		"Name=uploadDocData.uploadDocDescList", "Value=Information document for PT", ENDITEM, 
		"Name=docId", "Value={documentId1}", ENDITEM, 
		"Name=uploadDocData.uploadDocIdList", "Value={documentId1}", ENDITEM, 
		"Name=uploadDocData.uploadDocTitleList", "Value=Electronic Signature", ENDITEM, 
		"Name=uploadDocData.uploadDocTypeList", "Value=2", ENDITEM, 
		"Name=uploadDocData.uploadDocDescList", "Value=Electronic Signature Document for PT", ENDITEM, 
		"Name=docId", "Value={documentId2}", ENDITEM, 
		"Name=uploadDocData.uploadDocIdList", "Value={documentId2}", ENDITEM, 
		"Name=uploadDocData.uploadDocTitleList", "Value=Sign Return Document", ENDITEM, 
		"Name=uploadDocData.uploadDocTypeList", "Value=3", ENDITEM, 
		"Name=uploadDocData.uploadDocDescList", "Value=Sign and Return document for PT", ENDITEM, 
		"Name=uploadDocData.uploadDocFlag", "Value=1", ENDITEM, 
		"Name=uploadDocData.deleteDocFlag", "Value=0", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_06_ClickSave",LR_AUTO);

		if (atoi(lr_eval_string("{CreateNetwork_Count}")) > 0)
	{ 
		lr_output_message("Create Network successful");
		lr_end_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_03_CreateNetwork", LR_PASS);
	}
	else
	{ 
		lr_error_message("Create Network failed");
		lr_end_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_03_CreateNetwork", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 

	lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=ServiceLive - Select Provider Network - Create\r\n\t\t\tCampaigns","SaveCount=CreateCampaign_Count", 
		LAST);

	web_reg_save_param("spnId",
		"LB=<option value=\"",
		"RB=\">{spnName}</option>",
		"Ord=1",
		"Search=Body",
		LAST);

	/* --------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_SPN_CreateNetworkAndCampaign_07_CreateCampaign
	   Transaction Description  : Click on create campaign tab
	   Transaction Parameters   : loginURL
	   Correlated Parameters    : None
	 ---------------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_07_CreateCampaign");

	web_url("Create Campaigns", 
		"URL={loginURL}spn/spnCreateCampaign_display.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnReleaseTiers_display.action", 
		"Snapshot=t19.inf", 
		"Mode=HTML", 
		LAST);

	/* --------------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_SPN_CreateNetworkAndCampaign_08_SelectProviderNetwork
	   Transaction Description  : Select the provider network
	   Transaction Parameters   : loginURL
	   Correlated Parameters    : spnId
	 ---------------------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_08_SelectProviderNetwork");

    web_url("spnCreateCampaign_loadSelectedSpnDataAjax.action", 
		"URL={loginURL}spn/spnCreateCampaign_loadSelectedSpnDataAjax.action?spnHeader.spnId={spnId}", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnCreateCampaign_display.action", 
		"Snapshot=t20.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnCreateCampaign_updateRightSideAjax.action", 
		"URL={loginURL}spn/spnCreateCampaign_updateRightSideAjax.action?spnHeader.spnId={spnId}", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnCreateCampaign_display.action", 
		"Snapshot=t21.inf", 
		"Mode=HTML", 
		LAST);

    web_url("spnCreateCampaign_getSPNCountsAjax.action", 
		"URL={loginURL}spn/spnCreateCampaign_getSPNCountsAjax.action?spnHeader.spnId={spnId}", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnCreateCampaign_display.action", 
		"Snapshot=t22.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_08_SelectProviderNetwork",LR_AUTO);

	lr_think_time(atoi(lr_eval_string("{think_time}")));

	/* --------------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_SPN_CreateNetworkAndCampaign_09_UpdateResults
	   Transaction Description  : Click on Update Results button
	   Transaction Parameters   : loginURL
	   Correlated Parameters    : None
	 ---------------------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_09_UpdateResults");

    web_url("spnCreateCampaign_getCampaignCountsAjax.action", 
		"URL={loginURL}spn/spnCreateCampaign_getCampaignCountsAjax.action?&approvalItems.selectedStates=IL&approvalItems.selectedLanguages=1&approvalItems.selectedMinimumRating=0&approvalItems.minimumCompletedServiceOrders=&approvalItems.vehicleLiabilitySelected=true&approvalItems.vehicleLiabilityAmt=10000&approvalItems.vehicleLiabilityVerified=true&approvalItems.workersCompensationSelected=true&approvalItems.workersCompensationVerified=true&approvalItems.commercialGeneralLiabilitySelected"
		"=true&approvalItems.commercialGeneralLiabilityAmt=10000&approvalItems.commercialGeneralLiabilityVerified=true&approvalItems.selectedCompanySize=-1&approvalItems.selectedSalesVolume=-1&approvalItems.isNotRated=false&approvalItems.isAllMarketsSelected=false&approvalItems.isAllStatesSelected=false&approvalItems.selectedMainServices=200&approvalItems.selectedSkills=3&approvalItems.selectedSkills=10", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnCreateCampaign_display.action", 
		"Snapshot=t23.inf", 
		"Mode=HTML", 
		LAST);


	lr_end_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_09_UpdateResults",LR_AUTO);

    lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=ServiceLive - Select Provider Network - Campaign\r\n\t\t\tMonitor","SaveCount=CampaignMonitor_Count", 
		LAST);

	/* --------------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_SPN_CreateNetworkAndCampaign_10_ClickSave&Done
	   Transaction Description  : Click on Save&Done button
	   Transaction Parameters   : loginURL,startDate,endDate,campaignNumber
	   Correlated Parameters    : spnId
	 ---------------------------------------------------------------------------------------------- */
	
	lr_start_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_10_ClickSave&Done");

	web_submit_data("spnCreateCampaign_saveAndDone.action", 
		"Action={loginURL}spn/spnCreateCampaign_saveAndDone.action", 
		"Method=POST", 
		"EncType=multipart/form-data", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnCreateCampaign_display.action", 
		"Snapshot=t24.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=spnList", "Value={spnId}", ENDITEM, 
		"Name=spnHeader.spnId", "Value={spnId}", ENDITEM, 
		"Name=campaignHeader.campaignId", "Value=", ENDITEM, 
		"Name=startDate", "Value={startDate}", ENDITEM, 
		"Name=endDate", "Value={endDate}", ENDITEM, 
		"Name=__checkbox_approvalItems.isAllMarketsSelected", "Value=true", ENDITEM, 
		"Name=approvalItems.selectedStates", "Value=IL", ENDITEM, 
		"Name=__checkbox_approvalItems.isAllStatesSelected", "Value=true", ENDITEM, 
		"Name=providersMatchingSPN.providerFirmCounts", "Value=914", ENDITEM, 
		"Name=providersMatchingSPN.providerCounts", "Value=3422", ENDITEM, 
		"Name=providersMatchingCampaign.providerFirmCounts", "Value=45", ENDITEM, 
		"Name=providersMatchingCampaign.providerCounts", "Value=123", ENDITEM, 
		"Name=approvalItems.selectedMinimumRating", "Value=0", ENDITEM, 
		"Name=__checkbox_approvalItems.isNotRated", "Value=true", ENDITEM, 
		"Name=approvalItems.selectedLanguages", "Value=1", ENDITEM, 
		"Name=approvalItems.minimumCompletedServiceOrders", "Value=", ENDITEM, 
		"Name=approvalItems.vehicleLiabilitySelected", "Value=true", ENDITEM, 
		"Name=__checkbox_approvalItems.vehicleLiabilitySelected", "Value=true", ENDITEM, 
		"Name=approvalItems.vehicleLiabilityAmt", "Value=10000", ENDITEM, 
		"Name=approvalItems.vehicleLiabilityVerified", "Value=true", ENDITEM, 
		"Name=__checkbox_approvalItems.vehicleLiabilityVerified", "Value=true", ENDITEM, 
		"Name=approvalItems.workersCompensationSelected", "Value=true", ENDITEM, 
		"Name=__checkbox_approvalItems.workersCompensationSelected", "Value=true", ENDITEM, 
		"Name=approvalItems.workersCompensationVerified", "Value=true", ENDITEM, 
		"Name=__checkbox_approvalItems.workersCompensationVerified", "Value=true", ENDITEM, 
		"Name=approvalItems.commercialGeneralLiabilitySelected", "Value=true", ENDITEM, 
		"Name=__checkbox_approvalItems.commercialGeneralLiabilitySelected", "Value=true", ENDITEM, 
		"Name=approvalItems.commercialGeneralLiabilityAmt", "Value=10000", ENDITEM, 
		"Name=approvalItems.commercialGeneralLiabilityVerified", "Value=true", ENDITEM, 
		"Name=__checkbox_approvalItems.commercialGeneralLiabilityVerified", "Value=true", ENDITEM, 
		"Name=approvalItems.selectedCompanySize", "Value=-1", ENDITEM, 
		"Name=approvalItems.selectedSalesVolume", "Value=-1", ENDITEM, 
		"Name=approvalItems.selectedMainServices", "Value=200", ENDITEM, 
		"Name=approvalItems.selectedSkills", "Value=3", ENDITEM, 
		"Name=approvalItems.selectedSkills", "Value=10", ENDITEM, 
		"Name=campaignHeader.campaignName", "Value=PerformanceTestingCampaign{campaignNumber}", ENDITEM, 
		"Name=method:saveAndDone", "Value=Save & Done", ENDITEM, 
		LAST);

	web_url("Campaign Details", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=219", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t25.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=218", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t26.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_2", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=217", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t27.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_3", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=211", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t28.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_4", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=210", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t29.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_5", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=209", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t30.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_6", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=208", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t31.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_7", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=207", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t32.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_8", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=206", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t33.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_9", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=205", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t34.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_10", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=204", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t35.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_11", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=203", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t36.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_12", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=202", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t37.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_13", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=201", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t38.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_14", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=200", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t39.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_15", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=199", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t40.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_16", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=198", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t41.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_17", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=197", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t42.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_18", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=196", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t43.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_19", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=195", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t44.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_20", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=194", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t45.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_21", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=193", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t46.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_22", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=192", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t47.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_23", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=191", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t48.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_24", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=188", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t49.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_25", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=187", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t50.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_26", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=186", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t51.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_27", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=185", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t52.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_28", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=184", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t53.inf", 
		"Mode=HTML", 
		LAST);

	web_url("spnMonitorCampaign_displayTabCampaignDetailsAjax.action_29", 
		"URL={loginURL}spn/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=183", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t54.inf", 
		"Mode=HTML", 
		LAST);

		if (atoi(lr_eval_string("{CampaignMonitor_Count}")) > 0)
	{ 
		lr_output_message("Campaign Saved successfully");
		lr_end_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_10_ClickSave&Done", LR_PASS);
	}
	else
	{ 
		lr_error_message("Campaign Save failed");
		lr_end_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_10_ClickSave&Done", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 


		if (atoi(lr_eval_string("{CreateCampaign_Count}")) > 0)
	{ 
		lr_output_message("Create Campaign successful");
		lr_end_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_07_CreateCampaign", LR_PASS);
	}
	else
	{ 
		lr_error_message("Create Campaign failed");
		lr_end_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_07_CreateCampaign", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 


	lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=ServiceLive : Log in to Select Provider Network","SaveCount=LogOut_Count", 
		LAST);

	/* --------------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_SPN_CreateNetworkAndCampaign_11_ClickLogout
	   Transaction Description  : Click on LogOut button
	   Transaction Parameters   : loginURL
	   Correlated Parameters    : None
	 ---------------------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_11_ClickLogout");

	web_url("spnLoginAction_logoutUser.action", 
		"URL={loginURL}spn/spnLoginAction_logoutUser.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}spn/spnMonitorCampaign_display.action", 
		"Snapshot=t55.inf", 
		"Mode=HTML", 
		LAST);

		if (atoi(lr_eval_string("{LogOut_Count}")) > 0)
	{ 
		lr_output_message("Logged Out successfully");
		lr_end_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_11_ClickLogout", LR_PASS);
	}
	else
	{ 
		lr_error_message("Log Out failed");
		lr_end_transaction("ServiceLive_SPN_CreateNetworkAndCampaign_11_ClickLogout", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 
	
    return 0;
}