Action()
{

	////web_add_cookie("__utma=178720740.1396126752.1257709992.1257799951.1257801219.17; DOMAIN=198.179.148.51");

	////web_add_cookie("__utmz=178720740.1257709992.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); DOMAIN=198.179.148.51");

	////web_add_cookie("__utmb=178720740.7.10.1257801219; DOMAIN=198.179.148.51");

	web_reg_find("Text=ServiceLive : Home Improvement & Repair", 
		LAST);

	lr_start_transaction("HomePage");

	web_url("homepage.action", 
		"URL=http://198.179.148.51:1780/MarketFrontend/homepage.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t1.inf", 
		"Mode=HTML", 
		LAST);



/*	////web_add_cookie("id=2278d1b9f0000020|644190/170290/14557|t=1257533987|et=730|cs=tp2bvdz4; DOMAIN=fls.doubleclick.net");

	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=5773478298340.379", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=5773478298340.379?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://198.179.148.51:1780/MarketFrontend/homepage.action", 
		"Snapshot=t2.inf", 
		"Mode=HTML", 
		LAST);*/

	lr_end_transaction("HomePage",LR_AUTO);

	lr_start_transaction("LoginPage");

	//////web_add_cookie("__utma=178720740.1396126752.1257709992.1257801219.1257801497.18; DOMAIN=198.179.148.51");

	////web_add_cookie("__utmb=178720740.1.10.1257801497; DOMAIN=198.179.148.51");

	////web_add_cookie("s_cc=true; DOMAIN=198.179.148.51");

	////web_add_cookie("s_sq=%5B%5BB%5D%5D; DOMAIN=198.179.148.51");

	////web_add_cookie("__utmc=178720740; DOMAIN=198.179.148.51");

	web_reg_find("Text=ServiceLive : Log in to ServiceLive", 
		LAST);

	web_url("loginAction.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/loginAction.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://198.179.148.51:1780/MarketFrontend/homepage.action", 
		"Snapshot=t3.inf", 
		"Mode=HTML", 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=2386077074570.7065", 
		"URL=https://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=2386077074570.7065?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/loginAction.action", 
		"Snapshot=t4.inf", 
		"Mode=HTML", 
		LAST); */

	lr_end_transaction("LoginPage",LR_AUTO);

	lr_start_transaction("Login");

	////web_add_cookie("__utmb=178720740.2.10.1257801497; DOMAIN=198.179.148.51");

	web_reg_find("Text=Your ServiceLive Dashboard", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("doLogin.action", 
		"Action=https://198.179.148.51:1743/MarketFrontend/doLogin.action", 
		"Method=POST", 
		"EncType=multipart/form-data", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/loginAction.action", 
		"Snapshot=t5.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=username", "Value=jbendew99", ENDITEM, 
		"Name=password", "Value=Test123!", ENDITEM, 
		"Name=__checkbox_rememberUserId", "Value=true", ENDITEM, 
		LAST);

	lr_end_transaction("Login",LR_AUTO);

	lr_start_transaction("SearchPortal");

	web_reg_find("Text=ServiceLive - Search Portal", 
		LAST);

	web_url("Search Portal", 
		"URL=https://198.179.148.51:1743/MarketFrontend/adminSearch_execute.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/adminDashboard.action", 
		"Snapshot=t6.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("SearchPortal",LR_AUTO);


	lr_start_transaction("Search");

	// [WCSPARAM WCSParam_Text1 5 32349] Parameter {WCSParam_Text1} created by Correlation Studio
	web_reg_save_param( "companyId", "LB=Company Id: ", "RB=<", "Ord=1", "IgnoreRedirections=Yes", "Search=Body", "RelFrameId=1", LAST );

	web_submit_data("adminSearch_searchServiceProvider.action", 
		"Action=https://198.179.148.51:1743/MarketFrontend/adminSearch_searchServiceProvider.action?sortKey=&sortOrder=desc", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/adminSearch_execute.action", 
		"Snapshot=t7.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=searchPortalServiceProviderVO.user.userId", "Value={resourceId}", ENDITEM, 
		"Name=searchPortalServiceProviderVO.user.fnameOrLname", "Value=", ENDITEM, 
		"Name=searchPortalServiceProviderVO.user.userName", "Value=", ENDITEM, 
		"Name=searchPortalServiceProviderVO.user.businessName", "Value=", ENDITEM, 
		"Name=searchPortalServiceProviderVO.user.fromSignUpDate", "Value=", ENDITEM, 
		"Name=searchPortalServiceProviderVO.user.toSignUpDate", "Value=", ENDITEM, 
		"Name=searchPortalServiceProviderVO.soId", "Value=", ENDITEM, 
		"Name=searchPortalServiceProviderVO.primaryVerticleId", "Value=-1", ENDITEM, 
		"Name=searchPortalServiceProviderVO.workflowStateId", "Value=-1", ENDITEM, 
		"Name=searchPortalServiceProviderVO.bgCheckStateId", "Value=-1", ENDITEM, 
		"Name=searchPortalServiceProviderVO.spnId", "Value=-1", ENDITEM, 
		"Name=searchPortalServiceProviderVO.spnetId", "Value=-1", ENDITEM, 
		"Name=searchPortalServiceProviderVO.location.phoneNumber", "Value=", ENDITEM, 
		"Name=searchPortalServiceProviderVO.location.emailAddress", "Value=", ENDITEM, 
		"Name=searchPortalServiceProviderVO.location.state", "Value=-1", ENDITEM, 
		"Name=searchPortalServiceProviderVO.location.city", "Value=", ENDITEM, 
		"Name=searchPortalServiceProviderVO.location.zip", "Value=", ENDITEM, 
		"Name=searchPortalServiceProviderVO.location.marketId", "Value=-1", ENDITEM, 
		"Name=searchPortalServiceProviderVO.location.districtId", "Value=-1", ENDITEM, 
		"Name=searchPortalServiceProviderVO.location.regionId", "Value=-1", ENDITEM, 
		LAST);

	lr_end_transaction("Search",LR_AUTO);

	lr_start_transaction("TakeAction");

	////web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253Dsearch.portal%2526pidt%253D1%2526oid%253Djavascript%25253AshowEditProv%252528%25252732349%252527%25252C%25252520%25252747513%252527%252529%2526ot%253DA%2526oi%253D1412; DOMAIN=198.179.148.51");

	web_reg_find("Text=ServiceLive [Service Pro Profile]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_url("adminSearch_navigateToProviderPage.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/adminSearch_navigateToProviderPage.action?searchPortalProviderFirmVO.user.companyId={companyId}&searchPortalProviderFirmVO.user.userId={resourceId}&resourceId={resourceId}", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/adminSearch_execute.action", 
		"Snapshot=t8.inf", 
		"Mode=HTML", 
		LAST);

	web_custom_request("generalInfoAction!doLoad.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/generalInfoAction!doLoad.action?resourceId={resourceId}", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/serviceProAllTab.action?resourceId={resourceId}", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=resourceId={resourceId}", 
		LAST);

	lr_end_transaction("TakeAction",LR_AUTO);

	lr_start_transaction("ServiceProApproval");

	web_custom_request("commonAuditorAjax.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/commonAuditorAjax.action", 
		"Method=POST", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/xml", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/serviceProAllTab.action?resourceId={resourceId}", 
		"Snapshot=t10.inf", 
		"Mode=HTML", 
		"Body=companyOrServicePro.commonSEL1=6&companyOrServicePro.sendEmailAppr=1&Button=Save%20Provider%20Status&companyOrServicePro.commonMessageNote=%5BMessage%5D&companyOrServicePro.currentKey=Approved%20(market%20ready)&companyOrServicePro.currentVal=6&companyOrServicePro.selectType=Team%20Member&companyOrServicePro.sendEmail=1&companyOrServicePro.subSelectName=commonSEL1SUB&noteReq.commonNoteSubject=&noteReq.commonMessageNote=&actionSubmitType=ServiceProCompany&companyOrServicePro.theResourceId={resourceId}", 
		LAST);

	////web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DAddPro%2526pidt%253D1%2526oid%253Dfunctionanonymous%252528%252529%25257Bnewco.jsutils.handleServiceProAppv%252528%252527commonSEL1%252527%25252C%252527TeamMember%252527%252529%25257D%2526oidt%253D2%2526ot%253DBUTTON%2526oi%253D195; DOMAIN=198.179.148.51");

	web_custom_request("auditorApproval.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/auditorApproval.action", 
		"Method=POST", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/xml", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/serviceProAllTab.action?resourceId={resourceId}", 
		"Snapshot=t11.inf", 
		"Mode=HTML", 
		"Body=companyOrServicePro.commonSEL1=6&companyOrServicePro.commonSEL1SUB=54&companyOrServicePro.sendEmailAppr=0&Button=Save%20Provider%20Status&companyOrServicePro.commonMessageNote=%5BMessage%5D&companyOrServicePro.currentKey=Approved%20(market%20ready)&companyOrServicePro.currentVal=6&companyOrServicePro.selectType=Team%20Member&companyOrServicePro.sendEmail=0&companyOrServicePro.subSelectName=commonSEL1SUB&noteReq.commonNoteSubject=&noteReq.commonMessageNote=&actionSubmitType=ServiceProCompany&"
		"companyOrServicePro.theResourceId={resourceId}", 
		LAST);

	lr_end_transaction("ServiceProApproval",LR_AUTO);

	lr_start_transaction("CertificateApproval");

	////web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DAddPro%2526pidt%253D1%2526oid%253Dfunction%252528%252529%25257Bvarap%25253DArray.prototype%25252Ch%25253Ddojo._ie_listener.handlers%25252Cc%25253Darguments.callee%25252Cls%25253Dc._listeners%25252Ct%25253Dh%2526oidt%253D2%2526ot%253DSPAN%2526oi%253D2074; DOMAIN=198.179.148.51");

	web_reg_find("Text=ServiceLive [Provider Admin]", 
		LAST);

	// [WCSPARAM WCSParam_Text2 5 {resourceCredId}] Parameter {WCSParam_Text2} created by Correlation Studio
	web_reg_save_param( "resourceCredId", "LB=resourceCredId=", "RB='", "Ord=1", "IgnoreRedirections=Yes", "Search=Body", "RelFrameId=1", LAST );
	web_custom_request("teamCredentialAction!loadCredentialList.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/teamCredentialAction!loadCredentialList.action?resourceId={resourceId}", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/serviceProAllTab.action?resourceId={resourceId}", 
		"Snapshot=t12.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=resourceId={resourceId}", 
		LAST);

	////web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DEditServicePro.dijit_layout_LinkPane_3%2526pidt%253D1%2526oid%253Dfunctionanonymous%252528%252529%25257BeditCredentials%252528%252527/MarketFrontend/teamCredentialAction%252521loadCredentialDetails.acti%2526oidt%253D2%2526ot%253DIMAGE%2526oi%253D2028; DOMAIN=198.179.148.51");

	web_reg_find("Text=ServiceLive [Service Pro Profile]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("teamCredentialAction!editCredentilDetails.action", 
		"Action=https://198.179.148.51:1743/MarketFrontend/teamCredentialAction!editCredentilDetails.action?resourceCredId={resourceCredId}", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/serviceProAllTab.action?resourceId={resourceId}", 
		"Snapshot=t13.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=teamCredentialsDto.size", "Value=1", ENDITEM, 
		"Name=checkboxExists", "Value=false", ENDITEM, 
		"Name=x", "Value=30", ENDITEM, 
		"Name=y", "Value=6", ENDITEM, 
		LAST);

	web_custom_request("teamCredentialAction!loadCredentialDetails.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/teamCredentialAction!loadCredentialDetails.action?tabView=tab4&tabType=level2", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab4&tabType=level2", 
		"Snapshot=t14.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=tabView=tab4&tabType=level2", 
		LAST);

	////web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DAddPro%2526pidt%253D1%2526oid%253Dfunction%252528%252529%25257Bvarap%25253DArray.prototype%25252Ch%25253Ddojo._ie_listener.handlers%25252Cc%25253Darguments.callee%25252Cls%25253Dc._listeners%25252Ct%25253Dh%2526oidt%253D2%2526ot%253DDIV%2526oi%253D323; DOMAIN=198.179.148.51");

	web_custom_request("commonAuditorAjax.action_2", 
		"URL=https://198.179.148.51:1743/MarketFrontend/commonAuditorAjax.action", 
		"Method=POST", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/xml", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab4&tabType=level2", 
		"Snapshot=t15.inf", 
		"Mode=HTML", 
		"Body=credentialRequest.currentKey=Approved&credentialRequest.currentVal=12&credentialRequest.selectType=Team%20Member%20Credential&credentialRequest.sendEmail=1&credentialRequest.subSelectName=commonSEL2SUB&actionSubmitType=ServiceProCredential&credentialRequest.credentialKey={resourceCredId}&credentialRequest.commonReviewNote=&credentialRequest.resourceCredentialKey={resourceCredId}&credentialRequest.theResourceId={resourceId}&vendorCredId=0&credentialRequest.commonSEL2=12&holder1=1&commonReviewComment=&Button="
		"Save%20Credential%20Status", 
		LAST);

	////web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DAddPro%2526pidt%253D1%2526oid%253Dfunctionanonymous%252528%252529%25257Bnewco.jsutils.handleEmailOption%252528this%25252C%252527CredForm%252527%252529%25257D%2526oidt%253D2%2526ot%253DRADIO%2526oi%253D641; DOMAIN=198.179.148.51");

	web_custom_request("auditorApproval.action_2", 
		"URL=https://198.179.148.51:1743/MarketFrontend/auditorApproval.action", 
		"Method=POST", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/xml", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab4&tabType=level2", 
		"Snapshot=t16.inf", 
		"Mode=HTML", 
		"Body=credentialRequest.currentKey=Approved&credentialRequest.currentVal=12&credentialRequest.selectType=Team%20Member%20Credential&credentialRequest.sendEmail=0&credentialRequest.subSelectName=70&actionSubmitType=ServiceProCredential&credentialRequest.credentialKey={resourceCredId}&credentialRequest.commonReviewNote=&credentialRequest.resourceCredentialKey={resourceCredId}&credentialRequest.theResourceId={resourceId}&vendorCredId=0&credentialRequest.commonSEL2=12&credentialRequest.commonSEL2SUB=70&holder1=0&"
		"commonReviewComment=&Button=Save%20Credential%20Status", 
		LAST);

	lr_end_transaction("CertificateApproval",LR_AUTO);

	lr_start_transaction("CompanyProfileApproval");

	web_reg_find("Text=ServiceLive [Provider Registration]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_url("allTabView.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/allTabView.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab4&tabType=level2", 
		"Snapshot=t17.inf", 
		"Mode=HTML", 
		LAST);

	web_custom_request("Business Information", 
		"URL=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/businessinfoActiondoLoad.action", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/allTabView.action", 
		"Snapshot=t18.inf", 
		"Mode=HTML", 
		LAST);

	web_custom_request("commonAuditorAjax.action_3", 
		"URL=https://198.179.148.51:1743/MarketFrontend/commonAuditorAjax.action", 
		"Method=POST", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/xml", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/allTabView.action", 
		"Snapshot=t19.inf", 
		"Mode=HTML", 
		"Body=companyOrServicePro.commonSEL1=3&companyOrServicePro.sendEmailAppr=1&Button=Save%20Provider%20Status&companyOrServicePro.commonMessageNote=%5BMessage%5D&companyOrServicePro.currentKey=Sears%20Provider%20Approved&companyOrServicePro.currentVal=3&companyOrServicePro.selectType=Company%20Profile&companyOrServicePro.sendEmail=1&companyOrServicePro.subSelectName=commonSEL1SUB&noteReq.commonNoteSubject=&noteReq.commonMessageNote=&actionSubmitType=ProviderCompany", 
		LAST);

	////web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DProFirmProfile%2526pidt%253D1%2526oid%253Dfunctionanonymous%252528%252529%25257Bnewco.jsutils.handleProviderFirmAppv%252528%252527commonSEL1%252527%25252C%252527CompanyProfile%252527%252529%25257D%2526oidt%253D2%2526ot%253DBUTTON%2526oi%253D209; DOMAIN=198.179.148.51");

	web_custom_request("auditorApproval.action_3", 
		"URL=https://198.179.148.51:1743/MarketFrontend/auditorApproval.action", 
		"Method=POST", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/xml", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/allTabView.action", 
		"Snapshot=t20.inf", 
		"Mode=HTML", 
		"Body=companyOrServicePro.commonSEL1=3&companyOrServicePro.commonSEL1SUB=24&companyOrServicePro.sendEmailAppr=0&Button=Save%20Provider%20Status&companyOrServicePro.commonMessageNote=%5BMessage%5D&companyOrServicePro.currentKey=Sears%20Provider%20Approved&companyOrServicePro.currentVal=3&companyOrServicePro.selectType=Company%20Profile&companyOrServicePro.sendEmail=0&companyOrServicePro.subSelectName=commonSEL1SUB&noteReq.commonNoteSubject=&noteReq.commonMessageNote=&actionSubmitType=ProviderCompany", 
		LAST);

	lr_end_transaction("CompanyProfileApproval",LR_AUTO);

	lr_start_transaction("CompanyCertificateApproval");

	////web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DProFirmProfile%2526pidt%253D1%2526oid%253Dfunction%252528%252529%25257Bvarap%25253DArray.prototype%25252Ch%25253Ddojo._ie_listener.handlers%25252Cc%25253Darguments.callee%25252Cls%25253Dc._listeners%25252Ct%25253Dh%2526oidt%253D2%2526ot%253DSPAN%2526oi%253D1093; DOMAIN=198.179.148.51");

	web_custom_request("listLicenceAndCertdoViewList1.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/listLicenceAndCertdoViewList1.action", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/allTabView.action", 
		"Snapshot=t21.inf", 
		"Mode=HTML", 
		LAST);

	web_reg_find("Text=ServiceLive [Provider Registration]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_url("spacer.gif", 
		"URL=https://198.179.148.51:1743/MarketFrontend/allTabView.action?tabView=tab3&nexturl=licensesAndCertActiondoLoad.action?id=14061", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/allTabView.action", 
		"Snapshot=t22.inf", 
		"Mode=HTML", 
		LAST);

	web_custom_request("licensesAndCertActiondoLoad.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/licensesAndCertActiondoLoad.action?id=14061", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/allTabView.action?tabView=tab3&nexturl=licensesAndCertActiondoLoad.action?id=14061", 
		"Snapshot=t23.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=id=14061", 
		LAST);

	////web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DProFirmProfile%2526pidt%253D1%2526oid%253Dfunction%252528%252529%25257Bvarap%25253DArray.prototype%25252Ch%25253Ddojo._ie_listener.handlers%25252Cc%25253Darguments.callee%25252Cls%25253Dc._listeners%25252Ct%25253Dh%2526oidt%253D2%2526ot%253DDIV%2526oi%253D565; DOMAIN=198.179.148.51");

	web_custom_request("commonAuditorAjax.action_4", 
		"URL=https://198.179.148.51:1743/MarketFrontend/commonAuditorAjax.action", 
		"Method=POST", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/xml", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/allTabView.action?tabView=tab3&nexturl=licensesAndCertActiondoLoad.action?id=14061", 
		"Snapshot=t24.inf", 
		"Mode=HTML", 
		"Body=credentialRequest.currentKey=Approved&credentialRequest.currentVal=14&credentialRequest.selectType=Company%20Credential&credentialRequest.sendEmail=1&credentialRequest.subSelectName=commonSEL2SUB&actionSubmitType=VendorCredential&credentialRequest.commonReviewNote=&credentialRequest.credentialKey={resourceCredId}&vendorCredId=0&credentialRequest.commonSEL2=-1&holder1=1&commonReviewComment=&Button=Save%20Credential%20Status", 
		LAST);

	////web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DProFirmProfile%2526pidt%253D1%2526oid%253Dfunctionanonymous%252528%252529%25257Bnewco.jsutils.handleCredentialAppv%252528%252527commonSEL2%252527%25252C%252527CompanyCredential%252527%25252C14061%252529%25257D%2526oidt%253D2%2526ot%253DBUTTON%2526oi%253D945; DOMAIN=198.179.148.51");

	web_custom_request("auditorApproval.action_4", 
		"URL=https://198.179.148.51:1743/MarketFrontend/auditorApproval.action", 
		"Method=POST", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/xml", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/allTabView.action?tabView=tab3&nexturl=licensesAndCertActiondoLoad.action?id=14061", 
		"Snapshot=t25.inf", 
		"Mode=HTML", 
		"Body=credentialRequest.currentKey=Approved&credentialRequest.currentVal=14&credentialRequest.selectType=Company%20Credential&credentialRequest.sendEmail=0&credentialRequest.subSelectName=68&actionSubmitType=VendorCredential&credentialRequest.commonReviewNote=&credentialRequest.credentialKey=14061&vendorCredId=0&credentialRequest.commonSEL2=-1&credentialRequest.commonSEL2SUB=68&holder1=1&commonReviewComment=&Button=Save%20Credential%20Status", 
		LAST);

	lr_end_transaction("CompanyCertificateApproval",LR_AUTO);

	lr_start_transaction("Logout");

	web_reg_find("Text=Join ServiceLive",LAST);

	web_url("doLogout.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/doLogout.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t26.inf", 
		"Mode=HTML", 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=9284973060388.931", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=9284973060388.931?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://198.179.148.51:1780/MarketFrontend/homepage.action", 
		"Snapshot=t27.inf", 
		"Mode=HTML", 
		LAST);*/

	lr_end_transaction("Logout",LR_AUTO);

	return 0;
}