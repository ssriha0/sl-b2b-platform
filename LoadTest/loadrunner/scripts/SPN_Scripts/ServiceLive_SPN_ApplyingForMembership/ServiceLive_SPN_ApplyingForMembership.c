ServiceLive_SPN_ApplyingForMembership()
{

	lr_start_transaction("ServiceLive_SPN_ApplyingForMembership_01_HomePage");

	//web_add_cookie("__utma=182934374.1749896678.1270670060.1270764152.1270778237.18; DOMAIN=151.149.118.30");

	//web_add_cookie("__utmb=182934374.2.10.1270778237; DOMAIN=151.149.118.30");

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

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=680402058456.1915", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=680402058456.1915?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/MarketFrontend/homepage.action", 
		"Snapshot=t2.inf", 
		"Mode=HTML", 
		LAST);*/

	lr_end_transaction("ServiceLive_SPN_ApplyingForMembership_01_HomePage",LR_AUTO);

	lr_think_time(5);

	lr_start_transaction("ServiceLive_SPN_ApplyingForMembership_02_LoginPage");

	//web_add_cookie("__utma=182934374.1749896678.1270670060.1270778237.1270778939.19; DOMAIN=151.149.118.30");

	//web_add_cookie("__utmb=182934374.1.10.1270778939; DOMAIN=151.149.118.30");

	//web_add_cookie("s_cc=true; DOMAIN=151.149.118.30");

	//web_add_cookie("s_sq=%5B%5BB%5D%5D; DOMAIN=151.149.118.30");

	//web_add_cookie("__utmc=182934374; DOMAIN=151.149.118.30");

	web_reg_find("Text=ServiceLive : Log in to ServiceLive", 
		LAST);

	web_url("loginAction.action", 
		"URL=https://151.149.118.30:1943/MarketFrontend/loginAction.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/MarketFrontend/homepage.action", 
		"Snapshot=t3.inf", 
		"Mode=HTML", 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=858017519948.7705", 
		"URL=https://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=858017519948.7705?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://151.149.118.30:1943/MarketFrontend/loginAction.action", 
		"Snapshot=t4.inf", 
		"Mode=HTML", 
		LAST);*/

	lr_end_transaction("ServiceLive_SPN_ApplyingForMembership_02_LoginPage",LR_AUTO);

	lr_think_time(5);

	web_reg_save_param("spnId",
		"LB=<a href=\"/MarketFrontend/spnProviderInvitation_loadInvitation.action?&spnID=",
		"RB=\">You are invited",
		"Ord=1",
		"Search=Body",
		LAST);

	
	lr_start_transaction("ServiceLive_SPN_ApplyingForMembership_03_EnterLoginCredentials");

	//web_add_cookie("__utmb=182934374.2.10.1270778939; DOMAIN=151.149.118.30");

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
		"Name=username", "Value=futurevision", ENDITEM, 
		"Name=password", "Value=Test123!", ENDITEM, 
		"Name=__checkbox_rememberUserId", "Value=true", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_SPN_ApplyingForMembership_03_EnterLoginCredentials",LR_AUTO);

	lr_think_time(5);

	lr_start_transaction("ServiceLive_SPN_ApplyingForMembership_04_AcceptInvitation");

	web_reg_find("Text=ServiceLive : SPN Invitation", 
		LAST);

	web_url("You are invited to PerformanceTestingNetwork15", 
		"URL=https://151.149.118.30:1943/MarketFrontend/spnProviderInvitation_loadInvitation.action?&spnID={spnId}", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t6.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("ServiceLive_SPN_ApplyingForMembership_04_AcceptInvitation",LR_AUTO);

	lr_think_time(5);

	lr_start_transaction("ServiceLive_SPN_ApplyingForMembership_05_ViewCompanyRequirements");

	web_url("spnMonitorAction_getCompanyRequirementsList.action", 
		"URL=http://151.149.118.30:1980/MarketFrontend/spnMonitorAction_getCompanyRequirementsList.action?&spnID={spnId}&vendorId=10226", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/MarketFrontend/spnProviderInvitation_loadInvitation.action?&spnID={spnId}", 
		"Snapshot=t7.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("ServiceLive_SPN_ApplyingForMembership_05_ViewCompanyRequirements",LR_AUTO);

	lr_think_time(5);

	lr_start_transaction("ServiceLive_SPN_ApplyingForMembership_06_ViewProviderRequirements");

	lr_think_time(16);

	web_url("spnMonitorAction_getProviderRequirementsList.action", 
		"URL=http://151.149.118.30:1980/MarketFrontend/spnMonitorAction_getProviderRequirementsList.action?&spnID={spnId}&vendorId=10226", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/MarketFrontend/spnProviderInvitation_loadInvitation.action?&spnID={spnId}#companyReqs", 
		"Snapshot=t8.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("ServiceLive_SPN_ApplyingForMembership_06_ViewProviderRequirements",LR_AUTO);

	lr_think_time(5);

	lr_start_transaction("ServiceLive_SPN_ApplyingForMembership_07_ApplyforMembership");

	web_reg_find("Text=ServiceLive - SPN Monitor", 
		LAST);

	lr_think_time(20);

	web_submit_data("spnMonitorAction_loadSPNMonitor.action", 
		"Action=http://151.149.118.30:1980/MarketFrontend/spnMonitorAction_loadSPNMonitor.action?acceptInvite=1&selectedSpnId={spnId}", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/MarketFrontend/spnProviderInvitation_loadInvitation.action?&spnID={spnId}", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=rejectId", "Value=", ENDITEM, 
		"Name=rejectReason", "Value=", ENDITEM, 
		"Name=spnId", "Value={spnId}", ENDITEM, 
		"Name=vendorId", "Value=10226", ENDITEM, 
		"Name=spnId", "Value={spnId}", ENDITEM, 
		"Name=vendorId", "Value=10226", ENDITEM, 
		"Name=nothankuReason", "Value=0", ENDITEM, 
		"Name=otherReason", "Value=", ENDITEM, 
		LAST);

	web_submit_data("spnMonitorAction_loadSPNAjax.action", 
		"Action=http://151.149.118.30:1980/MarketFrontend/spnMonitorAction_loadSPNAjax.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/MarketFrontend/spnMonitorAction_loadSPNMonitor.action?acceptInvite=1&selectedSpnId={spnId}", 
		"Snapshot=t10.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=spnId", "Value={spnId}", ENDITEM, 
		LAST);

	
	web_reg_save_param("docId",
		"LB=<a href=\"/MarketFrontend/spnMonitorAction_loadDocument.action?&docID=",
		"RB=\" target=\"_docWindow",
		"Ord=1",
		"Search=Body",
		LAST);

	web_reg_save_param("documentId",
		"LB=<a href=\"/MarketFrontend/spnMonitorAction_loadDocument.action?&docID=",
		"RB=\" class=\"clickViewDoc",
		"Ord=1",
		"Search=Body",
		LAST);


	web_url("Requirements", 
		"URL=http://151.149.118.30:1980/MarketFrontend/spnMonitorAction_loadSPNRequirementsTabAjax.action?spnMonitorVO.spnId={spnId}", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/MarketFrontend/spnMonitorAction_loadSPNMonitor.action?acceptInvite=1&selectedSpnId={spnId}", 
		"Snapshot=t11.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("ServiceLive_SPN_ApplyingForMembership_07_ApplyforMembership",LR_AUTO);

	lr_think_time(5);

	lr_start_transaction("ServiceLive_SPN_ApplyingForMembership_08_UploadDoc");

	lr_think_time(24);

	web_submit_data("spnMonitorAction_uploadDocument.action", 
		"Action=http://151.149.118.30:1980/MarketFrontend/spnMonitorAction_uploadDocument.action", 
		"Method=POST", 
		"EncType=multipart/form-data", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/MarketFrontend/spnMonitorAction_loadSPNMonitor.action?acceptInvite=1&selectedSpnId={spnId}", 
		"Snapshot=t12.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=buyerId", "Value=1000", ENDITEM, 
		"Name=dtitle", "Value=Sign Return Document", ENDITEM, 
		"Name=buyerDocId", "Value={docId}", ENDITEM, 
		"Name=proDocId", "Value=0", ENDITEM, 
		"Name=buttonType", "Value=Select", ENDITEM, 
		"Name=spnId", "Value={spnId}", ENDITEM, 
		"Name=spnBuyerId", "Value=1000", ENDITEM,
        "Name=fileToUpload", "Value=D:\\\\Sears\\\\Imp Steps Needs to be followed.doc", "File=Yes", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_SPN_ApplyingForMembership_08_UploadDoc",LR_AUTO);

	lr_think_time(5);

	lr_start_transaction("ServiceLive_SPN_ApplyingForMembership_09_ClickAgreebutton");

	lr_think_time(4);

	web_custom_request("spnBuyerAgreeModal_acceptBuyerAgreementAjax.action", 
		"URL=http://151.149.118.30:1980/MarketFrontend/spnBuyerAgreeModal_acceptBuyerAgreementAjax.action?spnDocId={documentId}", 
		"Method=POST", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/MarketFrontend/spnMonitorAction_loadSPNMonitor.action?acceptInvite=1&selectedSpnId={spnId}", 
		"Snapshot=t13.inf", 
		"Mode=HTML", 
		"EncType=", 
		LAST);

	lr_end_transaction("ServiceLive_SPN_ApplyingForMembership_09_ClickAgreebutton",LR_AUTO);

	lr_think_time(5);

	lr_start_transaction("ServiceLive_SPN_ApplyingForMembership_10_ClickonSubmit");

	web_reg_find("Text=ServiceLive - SPN Monitor", 
		LAST);

	web_submit_data("spnSubmitBuyerAgreement.action", 
		"Action=http://151.149.118.30:1980/MarketFrontend/spnSubmitBuyerAgreement.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/MarketFrontend/spnMonitorAction_loadSPNMonitor.action?acceptInvite=1&selectedSpnId={spnId}", 
		"Snapshot=t14.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=spnId", "Value={spnId}", ENDITEM, 
		"Name=firmId", "Value=10226", ENDITEM, 
		LAST);

	web_submit_data("spnMonitorAction_loadSPNAjax.action_2", 
		"Action=http://151.149.118.30:1980/MarketFrontend/spnMonitorAction_loadSPNAjax.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/MarketFrontend/spnMonitorAction_loadSPNMonitor.action?selectedSpnId={spnId}", 
		"Snapshot=t15.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=spnId", "Value={spnId}", ENDITEM, 
		LAST);

	web_url("Requirements_2", 
		"URL=http://151.149.118.30:1980/MarketFrontend/spnMonitorAction_loadSPNRequirementsTabAjax.action?spnMonitorVO.spnId={spnId}", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/MarketFrontend/spnMonitorAction_loadSPNMonitor.action?selectedSpnId={spnId}", 
		"Snapshot=t16.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("ServiceLive_SPN_ApplyingForMembership_10_ClickonSubmit",LR_AUTO);

	lr_think_time(5);

	lr_start_transaction("ServiceLive_SPN_ApplyingForMembership_11_ClickLogOut");

	web_reg_find("Text=Join ServiceLive", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_url("doLogout.action", 
		"URL=http://151.149.118.30:1980/MarketFrontend/doLogout.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t17.inf", 
		"Mode=HTML", 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi767;ord=1;num=1392162589132.6994", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi767;ord=1;num=1392162589132.6994?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/MarketFrontend/joinNowAction.action", 
		"Snapshot=t18.inf", 
		"Mode=HTML", 
		LAST);

	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=4090161431427.863", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=4090161431427.863?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://151.149.118.30:1980/MarketFrontend/joinNowAction.action", 
		"Snapshot=t19.inf", 
		"Mode=HTML", 
		LAST);*/

	lr_end_transaction("ServiceLive_SPN_ApplyingForMembership_11_ClickLogOut",LR_AUTO);



	return 0;
}