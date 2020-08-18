Action()
{

	//web_add_cookie("__utma=182934374.1749896678.1270670060.1270756137.1270756229.13; DOMAIN=151.149.118.30");

	//web_add_cookie("__utmb=182934374.2.10.1270756229; DOMAIN=151.149.118.30");

	//web_add_cookie("__utmz=182934374.1270670060.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); DOMAIN=151.149.118.30");

	web_reg_find("Text=ServiceLive : Home Improvement & Repair", 
		LAST);

	web_url("homepage.action", 
		"URL=http://151.149.118.30:1980/MarketFrontend/homepage.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t1.inf", 
		"Mode=HTML", 
		LAST);

	//web_add_cookie("id=cf78d94250000ba||t=1270590842|et=730|cs=s00anrks; DOMAIN=fls.doubleclick.net");

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=58569705749.509926", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=58569705749.509926?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/MarketFrontend/homepage.action", 
		"Snapshot=t2.inf", 
		"Mode=HTML", 
		LAST);*/

	lr_start_transaction("LoginPage");

	////web_add_cookie("__utma=182934374.1749896678.1270670060.1270756229.1270756513.14; DOMAIN=151.149.118.30");

	//web_add_cookie("__utmb=182934374.1.10.1270756513; DOMAIN=151.149.118.30");

	//web_add_cookie("s_cc=true; DOMAIN=151.149.118.30");

	//web_add_cookie("s_sq=%5B%5BB%5D%5D; DOMAIN=151.149.118.30");

	//web_add_cookie("__utmc=182934374; DOMAIN=151.149.118.30");

	web_reg_find("Text=ServiceLive : Log in to ServiceLive", 
		LAST);

	lr_think_time(4);

	web_url("loginAction.action", 
		"URL=https://151.149.118.30:1943/MarketFrontend/loginAction.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/MarketFrontend/homepage.action", 
		"Snapshot=t3.inf", 
		"Mode=HTML", 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=2815485858765.0117", 
		"URL=https://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=2815485858765.0117?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://151.149.118.30:1943/MarketFrontend/loginAction.action", 
		"Snapshot=t4.inf", 
		"Mode=HTML", 
		LAST);*/

	lr_end_transaction("LoginPage",LR_AUTO);

	lr_think_time(5);

	lr_start_transaction("Login");

	//web_add_cookie("__utmb=182934374.2.10.1270756513; DOMAIN=151.149.118.30");

	web_reg_find("Text=ServiceLive Dashboard", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("doLogin.action", 
		"Action=https://151.149.118.30:1943/MarketFrontend/doLogin.action", 
		"Method=POST", 
		"EncType=multipart/form-data", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://151.149.118.30:1943/MarketFrontend/loginAction.action", 
		"Snapshot=t5.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=username", "Value=scarpe5@searshc.com", ENDITEM, 
		"Name=password", "Value=Test123!", ENDITEM, 
		"Name=__checkbox_rememberUserId", "Value=true", ENDITEM, 
		LAST);

	lr_end_transaction("Login",LR_AUTO);

	lr_think_time(5);

	lr_start_transaction("SPNAuditor");

	web_reg_find("Text=ServiceLive : Log in to Select Provider Network", 
		LAST);

	web_url("SPN Auditor", 
		"URL=https://151.149.118.30:1943/MarketFrontend/spnMonitorAction_callExternalSPN.action?targetExternalAction=spnAuditorController_route", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t6.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("SPNAuditor",LR_AUTO);

	web_reg_save_param("providerFirmId",
		"LB=href=\"spnAuditorDocumentsTab_viewTabAjax.action?providerFirmId=",
		"RB=&networkId",
		"Ord=1",
		"Search=Body",
		LAST);

	web_reg_save_param("networkId",
		"LB=&networkId=",
		"RB=&expandCriteriaVO.fromSearch=\">",
		"Ord=1",
		"Search=Body",
		LAST);


	lr_think_time(5);

	lr_start_transaction("Login");

	web_reg_find("Text=ServiceLive : SPN Auditor : New Applicants", 
		LAST);

	web_submit_data("spnLoginAction_loginUser.action", 
		"Action=http://151.149.118.30:1980/spn/spnLoginAction_loginUser.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/spn/spnLoginAction_display.action?targetAction=spnAuditorController_route", 
		"Snapshot=t7.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=userName", "Value=scarpe5@searshc.com", ENDITEM, 
		"Name=credential", "Value=Test123!", ENDITEM, 
		"Name=targetAction", "Value=spnAuditorController_route", ENDITEM, 
		"Name=buyerId", "Value=", ENDITEM, 
		LAST);

	web_url("Documents", 
		"URL=http://151.149.118.30:1980/spn/spnAuditorDocumentsTab_viewTabAjax.action?providerFirmId={providerFirmId}&networkId={networkId}&expandCriteriaVO.fromSearch=", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/spn/spnAuditorApplicantsTab_displayNewApplicant.action", 
		"Snapshot=t8.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("Login",LR_AUTO);

	web_reg_save_param("spnId",
		"LB=<option value=\"",
		"RB=\">{spnName}</option>",
		"Ord=1",
		"Search=Body",
		LAST);

	lr_think_time(5);

	lr_start_transaction("Search");

	web_reg_find("Text=ServiceLive : SPN Auditor : Search", 
		LAST);

	lr_think_time(11);

	web_url("spnAuditorSearchTab_display.action", 
		"URL=http://151.149.118.30:1980/spn/spnAuditorSearchTab_display.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/spn/spnAuditorApplicantsTab_displayNewApplicant.action", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		LAST);

	web_submit_data("spnAuditorSearchTab_searchSPNAjax.action", 
		"Action=http://151.149.118.30:1980/spn/spnAuditorSearchTab_searchSPNAjax.action?searchCriteriaVO.providerFirmName=&searchCriteriaVO.providerFirmNumber=&searchCriteriaVO.marketId=-1&searchCriteriaVO.zipCode=&searchCriteriaVO.districtId=-1&searchCriteriaVO.spnId=-1&searchCriteriaVO.stateCd=-1&searchCriteriaVO.viewAll=false", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/spn/spnAuditorSearchTab_display.action", 
		"Snapshot=t10.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=searchCriteriaVO.providerFirmStatus", "Value=-1", ENDITEM, 
		LAST);

	lr_end_transaction("Search",LR_AUTO);

	lr_think_time(5);

	lr_start_transaction("FilterbySPN");

	lr_think_time(41);

	web_submit_data("spnAuditorSearchTab_searchSPNAjax.action_2", 
		"Action=http://151.149.118.30:1980/spn/spnAuditorSearchTab_searchSPNAjax.action?searchCriteriaVO.providerFirmName=&searchCriteriaVO.providerFirmNumber=&searchCriteriaVO.marketId=-1&searchCriteriaVO.zipCode=&searchCriteriaVO.districtId=-1&searchCriteriaVO.spnId={spnId}&searchCriteriaVO.stateCd=-1&searchCriteriaVO.viewAll=false", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/spn/spnAuditorSearchTab_display.action", 
		"Snapshot=t11.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=searchCriteriaVO.providerFirmStatus", "Value=-1", ENDITEM, 
		LAST);

	lr_end_transaction("FilterbySPN",LR_AUTO);

	lr_think_time(5);

	web_reg_save_param("providerFirmId1",
		"LB=<input type=\"hidden\" name=\"pfId\" value=\"",
		"RB=\" />",
		"Ord=1",
		"Search=Body",
		LAST);

	web_reg_save_param("originalModifiedDate",
		"LB=name=\"origModDate\" value=\"",
		"RB=\" />",
		"Ord=1",
		"Search=Body",
		LAST);


	lr_start_transaction("FilterByStatus");

	lr_think_time(42);

	web_submit_data("spnAuditorSearchTab_searchSPNAjax.action_3", 
		"Action=http://151.149.118.30:1980/spn/spnAuditorSearchTab_searchSPNAjax.action?searchCriteriaVO.providerFirmName=&searchCriteriaVO.providerFirmNumber=&searchCriteriaVO.marketId=-1&searchCriteriaVO.zipCode=&searchCriteriaVO.districtId=-1&searchCriteriaVO.spnId={spnId}&searchCriteriaVO.stateCd=-1&searchCriteriaVO.viewAll=false", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/spn/spnAuditorSearchTab_display.action", 
		"Snapshot=t12.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=searchCriteriaVO.providerFirmStatus", "Value=PF SPN APPLICANT", ENDITEM, 
		LAST);

	lr_end_transaction("FilterByStatus",LR_AUTO);

	lr_think_time(5);

	lr_start_transaction("SelectSPN");

	lr_think_time(20);

	web_submit_data("spnAuditorApplicantsTab_displayApplicantPanelAjax.action", 
		"Action=http://151.149.118.30:1980/spn/spnAuditorApplicantsTab_displayApplicantPanelAjax.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/spn/spnAuditorSearchTab_display.action", 
		"Snapshot=t13.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=expandCriteriaVO.spnId", "Value={spnId}", ENDITEM, 
		"Name=expandCriteriaVO.providerFirmId", "Value={providerFirmId1}", ENDITEM, 
		"Name=expandCriteriaVO.originalModifiedDate", "Value={originalModifiedDate}", ENDITEM, 
		"Name=expandCriteriaVO.fromSearch", "Value=1", ENDITEM, 
		"Name=expandCriteriaVO.lockedRecord", "Value=", ENDITEM, 
		"Name=expandCriteriaVO.lockedByMe", "Value=false", ENDITEM, 
		LAST);

	web_reg_save_param("docId",
		"LB=<a href=\"spnAuditorDocumentsTab_loadDocumentAjax.action?docId=",
		"RB=\" target=\"_newDocWindow\">",
		"Ord=1",
		"Search=Body",
		LAST);

	web_url("Documents_2", 
		"URL=http://151.149.118.30:1980/spn/spnAuditorDocumentsTab_viewTabAjax.action?providerFirmId={providerFirmId1}&networkId={spnId}&expandCriteriaVO.fromSearch=1", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/spn/spnAuditorSearchTab_display.action#anchor_{providerFirmId1}_{spnId}", 
		"Snapshot=t14.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("SelectSPN",LR_AUTO);

	lr_think_time(5);

	lr_start_transaction("ClickEdit");

	lr_think_time(15);

	web_submit_data("spnAuditorApplicantsTab_getProviderFirmSPNLockAjax.action", 
		"Action=http://151.149.118.30:1980/spn/spnAuditorApplicantsTab_getProviderFirmSPNLockAjax.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/spn/spnAuditorSearchTab_display.action#anchor_{providerFirmId1}_{spnId}", 
		"Snapshot=t15.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=expandCriteriaVO.spnId", "Value={spnId}", ENDITEM, 
		"Name=expandCriteriaVO.providerFirmId", "Value={providerFirmId1}", ENDITEM, 
		"Name=expandCriteriaVO.originalModifiedDate", "Value={originalModifiedDate}", ENDITEM, 
		LAST);

	lr_end_transaction("ClickEdit",LR_AUTO);

	lr_think_time(5);

	lr_start_transaction("ClickSelect");

	web_submit_data("spnAuditorDocumentsTab_buttonSubmitAddAction.action", 
		"Action=http://151.149.118.30:1980/spn/spnAuditorDocumentsTab_buttonSubmitAddAction.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/spn/spnAuditorSearchTab_display.action#anchor_{providerFirmId1}_{spnId}", 
		"Snapshot=t16.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=providerFirmId", "Value={providerFirmId1}", ENDITEM, 
		"Name=networkId", "Value={spnId}", ENDITEM, 
		"Name=comments", "Value=Approved", ENDITEM, 
		"Name=numPages", "Value=2", ENDITEM, 
		"Name=action", "Value=DOC APPROVED", ENDITEM, 
		"Name=docId", "Value={docId}", ENDITEM, 
		LAST);

	lr_end_transaction("ClickSelect",LR_AUTO);

	lr_think_time(5);

	lr_start_transaction("ClickMeet");

	lr_think_time(16);

	web_url("spnAuditorMeetAndGreetTab_viewTabAjax.action", 
		"URL=http://151.149.118.30:1980/spn/spnAuditorMeetAndGreetTab_viewTabAjax.action?providerFirmId={providerFirmId1}&networkId={spnId}&expandCriteriaVO.fromSearch=1", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/spn/spnAuditorSearchTab_display.action#anchor_{providerFirmId1}_{spnId}", 
		"Snapshot=t17.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("ClickMeet",LR_AUTO);

	lr_think_time(5);

	lr_start_transaction("ClickSelect&Enterdetails");

	lr_end_transaction("ClickSelect&Enterdetails",LR_AUTO);

	lr_think_time(5);

	lr_start_transaction("Submit");

	web_submit_data("spnAuditorMeetAndGreetTab_buttonSubmitAddAction.action", 
		"Action=http://151.149.118.30:1980/spn/spnAuditorMeetAndGreetTab_buttonSubmitAddAction.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/spn/spnAuditorSearchTab_display.action#anchor_{providerFirmId1}_{spnId}", 
		"Snapshot=t18.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=providerFirmId", "Value={providerFirmId1}", ENDITEM, 
		"Name=networkId", "Value={spnId}", ENDITEM, 
		"Name=comments", "Value=Scheduled the meeting", ENDITEM, 
		"Name=name", "Value=Network Meeting", ENDITEM, 
		"Name=date", "Value={meetingDate}", ENDITEM, 
		"Name=action", "Value=MEET APPROVED", ENDITEM, 
		LAST);

	lr_end_transaction("Submit",LR_AUTO);

	lr_think_time(5);

	lr_start_transaction("APPROVE");

	lr_think_time(20);

	web_submit_data("spnAuditorApplicantsTab_buttonAcceptAjax.action", 
		"Action=http://151.149.118.30:1980/spn/spnAuditorApplicantsTab_buttonAcceptAjax.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/spn/spnAuditorSearchTab_display.action#anchor_{providerFirmId1}_{spnId}", 
		"Snapshot=t19.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=networkId", "Value={spnId}", ENDITEM, 
		"Name=providerFirmId", "Value={providerFirmId1}", ENDITEM, 
		"Name=expandCriteriaVO.fromSearch", "Value=1", ENDITEM, 
		LAST);

	web_submit_data("spnAuditorSearchTab_searchSPNAjax.action_4", 
		"Action=http://151.149.118.30:1980/spn/spnAuditorSearchTab_searchSPNAjax.action?searchCriteriaVO.providerFirmName=&searchCriteriaVO.providerFirmNumber=&searchCriteriaVO.marketId=-1&searchCriteriaVO.zipCode=&searchCriteriaVO.districtId=-1&searchCriteriaVO.spnId={spnId}&searchCriteriaVO.stateCd=-1&searchCriteriaVO.viewAll=false", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/spn/spnAuditorSearchTab_display.action#anchor_{providerFirmId1}_{spnId}", 
		"Snapshot=t20.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=searchCriteriaVO.providerFirmStatus", "Value=PF SPN APPLICANT", ENDITEM, 
		LAST);

	lr_end_transaction("APPROVE",LR_AUTO);

	lr_think_time(5);

	lr_start_transaction("LogOut");

	web_reg_find("Text=ServiceLive : Log in to Select Provider Network", 
		LAST);

	web_url("spnLoginAction_logoutUser.action", 
		"URL=http://151.149.118.30:1980/spn/spnLoginAction_logoutUser.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/spn/spnAuditorSearchTab_display.action", 
		"Snapshot=t21.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("LogOut",LR_AUTO);

	lr_think_time(5);

	return 0;
}