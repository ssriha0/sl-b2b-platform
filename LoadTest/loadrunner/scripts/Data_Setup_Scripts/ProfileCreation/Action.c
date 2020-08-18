Action()
{

	lr_start_transaction("Home");

	//web_add_cookie("__utma=178720740.2958222.1254433160.1254861187.1254862928.21; DOMAIN=198.179.148.51");

	//web_add_cookie("__utmz=178720740.1254433160.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); DOMAIN=198.179.148.51");

	//web_add_cookie("__utmb=178720740.1.10.1254862928; DOMAIN=198.179.148.51");

	web_reg_find("Text=ServiceLive : Home Improvement & Repair", 
		LAST);

	web_url("homepage.action", 
		"URL=http://198.179.148.51:1780/MarketFrontend/homepage.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t1.inf", 
		"Mode=HTML", 
		LAST);

	web_reg_find("Text=ServiceLive : Log in to ServiceLive", 
		LAST);

	web_url("Login", 
		"URL=https://198.179.148.51:1743/MarketFrontend/loginAction.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://198.179.148.51:1780/MarketFrontend/homepage.action", 
		"Snapshot=t2.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("Home",LR_AUTO);

	//web_add_cookie("id=223e4f4cf20000d2||t=1254421052|et=730|cs=iq_9qofq; DOMAIN=fls.doubleclick.net");

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=8430733954075.8", 
		"URL=https://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=8430733954075.8?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/loginAction.action", 
		"Snapshot=t3.inf", 
		"Mode=HTML", 
		LAST);
*/
	lr_start_transaction("Login");

	//web_add_cookie("__utma=178720740.2958222.1254433160.1254862928.1254863012.22; DOMAIN=198.179.148.51");

	//web_add_cookie("__utmb=178720740.1.10.1254863012; DOMAIN=198.179.148.51");

	//web_add_cookie("s_cc=true; DOMAIN=198.179.148.51");

	//web_add_cookie("s_sq=%5B%5BB%5D%5D; DOMAIN=198.179.148.51");

	//web_add_cookie("__utmc=178720740; DOMAIN=198.179.148.51");

	web_reg_find("Text=ServiceLive [Service Provider Registration]", 
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
		"Snapshot=t4.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=username", "Value={userId}", ENDITEM, 
		"Name=password", "Value={password}", ENDITEM, 
		"Name=__checkbox_rememberUserId", "Value=true", ENDITEM, 
		LAST);

	lr_end_transaction("Login",LR_AUTO);

	lr_start_transaction("ProviderFirmProfile");

	web_reg_find("Text=ServiceLive [Provider Registration]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_url("Edit Company Profile", 
		"URL=https://198.179.148.51:1743/MarketFrontend/allTabView.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/regDashboard.action", 
		"Snapshot=t5.inf", 
		"Mode=HTML", 
		LAST);

	web_reg_save_param("locationIdB",
		"LB=businessinfoDto.locnIdB\" value=\"",
		"RB=\" id=\"businessinfoAction_businessinfoDto_locnIdB",
		"Ord=1",
		LAST);

	web_reg_save_param("locationId",
		"LB=businessinfoDto.locnId\" value=\"",
		"RB=\" id=\"businessinfoAction_businessinfoDto_locnId",
		"Ord=1",
		LAST);

	web_reg_save_param("businessName",
		"LB=businessinfoDto.businessName\" value=\"",
		"RB=\" id=\"businessinfoAction_businessinfoDto_businessName",
		"Ord=1",
		LAST);

	web_reg_save_param("dbaName",
		"LB=businessinfoDto.dbaName\" value=\"",
		"RB=\" id=\"businessinfoAction_businessinfoDto_dbaName",
		"Ord=1",
		LAST);

	web_reg_save_param("businessStreet",
		"LB=businessinfoDto.businessStreet1\" maxlength=\"40\" value=\"",
		"RB=\" id=\"businessStreet1",
		"Ord=1",
		LAST);

	web_reg_save_param("businessCity",
		  "LB=businessinfoDto.businessCity\" maxlength=\"30\" value=\"",
		  "RB=\" id=\"businessCity",
		  "Ord=1",
		  LAST);


	web_reg_save_param("businessZip",
		  "LB=businessinfoDto.businessZip\" maxlength=\"10\" value=\"",
		  "RB=\" id=\"businessZip",
		  "Ord=1",
		  LAST);



	web_custom_request("Business Information", 
		"URL=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/businessinfoActiondoLoad.action", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/allTabView.action", 
		"Snapshot=t6.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("ProviderFirmProfile",LR_AUTO);

	lr_start_transaction("BusinessInfo");

	//web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DProFirmProfile%2526pidt%253D1%2526oid%253Dhttps%25253A//198.179.148.51%25253A1743/ServiceLiveWebUtil/images/images_registration/common/spacer.gif%2526ot%253DIMAGE%2526oi%253D870; DOMAIN=198.179.148.51");

	web_reg_find("Text=ServiceLive [Provider Registration]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("businessinfoAction.action", 
		"Action=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/businessinfoAction.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/allTabView.action", 
		"Snapshot=t7.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=businessinfoDto.locnIdB", "Value={locationIdB}", ENDITEM, 
		"Name=businessinfoDto.locnId", "Value={locationId}", ENDITEM, 
		"Name=businessinfoDto.businessName", "Value={businessName}", ENDITEM, 
		"Name=businessinfoDto.dbaName", "Value={dbaName}", ENDITEM, 
		"Name=businessinfoDto.einNo", "Value=", ENDITEM, 
		"Name=businessinfoDto.firstName", "Value=Stress", ENDITEM, 
		"Name=businessinfoDto.middleName", "Value=", ENDITEM, 
		"Name=businessinfoDto.lastName", "Value={LastName}", ENDITEM, 
		"Name=businessinfoDto.nameSuffix", "Value=", ENDITEM, 
		"Name=businessinfoDto.mainBusiPhoneNo1", "Value=321", ENDITEM, 
		"Name=businessinfoDto.mainBusiPhoneNo2", "Value=414", ENDITEM, 
		"Name=businessinfoDto.mainBusiPhoneNo3", "Value=4325", ENDITEM, 
		"Name=businessinfoDto.busPhoneExtn", "Value=", ENDITEM, 
		"Name=businessinfoDto.businessFax1", "Value=", ENDITEM, 
		"Name=businessinfoDto.businessFax2", "Value=", ENDITEM, 
		"Name=businessinfoDto.businessFax3", "Value=", ENDITEM, 
		"Name=businessinfoDto.dunsNo", "Value=", ENDITEM, 
		"Name=businessinfoDto.isForeignOwned", "Value=1", ENDITEM, 
		"Name=businessinfoDto.companySize", "Value=6", ENDITEM, 
		"Name=businessinfoDto.primaryIndustry", "Value=800", ENDITEM, 
		"Name=businessinfoDto.busStartDt", "Value=2009-08-19", ENDITEM, 
		"Name=businessinfoDto.foreignOwnedPct", "Value=3", ENDITEM, 
		"Name=businessinfoDto.annualSalesRevenue", "Value=7", ENDITEM, 
		"Name=businessinfoDto.webAddress", "Value=", ENDITEM, 
		"Name=businessinfoDto.description", "Value= Test", ENDITEM, 
		"Name=businessinfoDto.businessStreet1", "Value={businessStreet}", ENDITEM, 
		"Name=businessinfoDto.businessStreet2", "Value=", ENDITEM, 
		"Name=businessinfoDto.businessAprt", "Value=", ENDITEM, 
		"Name=businessinfoDto.businessCity", "Value={businessCity}", ENDITEM, 
		"Name=businessinfoDto.businessState", "Value={businessState}", ENDITEM, 
		"Name=businessinfoDto.businessZip", "Value={businessZip}", ENDITEM, 
		"Name=__checkbox_businessinfoDto.mailAddressChk", "Value=true", ENDITEM, 
		"Name=businessinfoDto.mailingStreet1", "Value={businessStreet}", ENDITEM, 
		"Name=businessinfoDto.mailingStreet2", "Value=", ENDITEM, 
		"Name=businessinfoDto.mailingAprt", "Value=", ENDITEM, 
		"Name=businessinfoDto.mailingCity", "Value={businessCity}", ENDITEM, 
		"Name=businessinfoDto.mailingState", "Value={businessState}", ENDITEM, 
		"Name=businessinfoDto.mailingZip", "Value={businessZip}", ENDITEM, 
		"Name=businessinfoDto.ownerInd", "Value=1", ENDITEM, 
		"Name=businessinfoDto.managerInd", "Value=1", ENDITEM, 
		"Name=businessinfoDto.adminInd", "Value=1", ENDITEM, 
		"Name=businessinfoDto.dispatchInd", "Value=1", ENDITEM, 
		"Name=businessinfoDto.sproInd", "Value=1", ENDITEM, 
		"Name=businessinfoDto.otherInd", "Value=1", ENDITEM, 
		"Name=businessinfoDto.jobTitle", "Value=", ENDITEM, 
		"Name=businessinfoDto.email", "Value=slprovideradmin@gmail.com", ENDITEM, 
		"Name=businessinfoDto.confirmEmail", "Value=slprovideradmin@gmail.com", ENDITEM, 
		"Name=businessinfoDto.altEmail", "Value=", ENDITEM, 
		"Name=businessinfoDto.confAltEmail", "Value=", ENDITEM, 
		"Name=action:businessinfoActiondoNext.x", "Value=27", ENDITEM, 
		"Name=action:businessinfoActiondoNext.y", "Value=3", ENDITEM, 
		LAST);

	web_custom_request("doWarrantydoLoad.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/jsp/providerRegistration/doWarrantydoLoad.action?tabView=tab2", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/allTabView.action?tabView=tab2", 
		"Snapshot=t8.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=tabView=tab2", 
		LAST);

	lr_end_transaction("BusinessInfo",LR_AUTO);

	lr_start_transaction("Warranty&Policies");

	//web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DProFirmProfile%2526pidt%253D1%2526oid%253Dhttps%25253A//198.179.148.51%25253A1743/ServiceLiveWebUtil/images/images_registration/common/spacer.gif%2526ot%253DIMAGE%2526oi%253D581; DOMAIN=198.179.148.51");

	web_reg_find("Text=ServiceLive [Provider Registration]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("doWarrantydoSave.action", 
		"Action=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/jsp/providerRegistration/doWarrantydoSave.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/allTabView.action?tabView=tab2", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=wdto.freeEstimate", "Value=1", ENDITEM, 
		"Name=wdto.warrOfferedLabor", "Value=1", ENDITEM, 
		"Name=wdto.warrOfferedParts", "Value=1", ENDITEM, 
		"Name=wdto.warrPeriodLabor", "Value=2", ENDITEM, 
		"Name=wdto.warrPeriodParts", "Value=2", ENDITEM, 
		"Name=wdto.conductDrugTest", "Value=1", ENDITEM, 
		"Name=wdto.hasEthicsPolicy", "Value=1", ENDITEM, 
		"Name=wdto.requireUsDoc", "Value=1", ENDITEM, 
		"Name=wdto.requireBadge", "Value=1", ENDITEM, 
		"Name=action:doWarrantydoNext.x", "Value=14", ENDITEM, 
		"Name=action:doWarrantydoNext.y", "Value=9", ENDITEM, 
		LAST);

	web_custom_request("listLicenceAndCertdoViewList1.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/listLicenceAndCertdoViewList1.action?tabView=tab3", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/allTabView.action?tabView=tab3", 
		"Snapshot=t10.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=tabView=tab3", 
		LAST);

	lr_end_transaction("Warranty&Policies",LR_AUTO);

	lr_start_transaction("L&C");

	web_reg_find("Text=ServiceLive [Provider Registration]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_url("spacer.gif", 
		"URL=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/allTabView.action?tabView=tab3&nexturl=licensesAndCertActiondoLoad.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/allTabView.action?tabView=tab3", 
		"Snapshot=t11.inf", 
		"Mode=HTML", 
		LAST);

    web_reg_save_param("vendorId",
        "LB=licensesAndCertDto.vendorId\" value=\"",
        "RB=\" id=\"licensesAndCertActiondoLoad",
        "Ord=1",
        LAST);


	web_custom_request("Licenses & Certifications", 
		"URL=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/licensesAndCertActiondoLoad.action", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/allTabView.action?tabView=tab3&nexturl=licensesAndCertActiondoLoad.action", 
		"Snapshot=t12.inf", 
		"Mode=HTML", 
		LAST);

	//web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DProFirmProfile%2526pidt%253D1%2526oid%253Dfunctionanonymous%252528%252529%25257BchangeDropdown%252528this%252529%25257D%2526oidt%253D2%2526ot%253DSELECT%2526oi%253D440; DOMAIN=198.179.148.51");

	web_reg_find("Text=ServiceLive [Provider Registration]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("licensesAndCertActiondoLoad.action", 
		"Action=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/licensesAndCertActiondoLoad.action?type=cat", 
		"Method=POST", 
		"EncType=multipart/form-data", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/allTabView.action?tabView=tab3&nexturl=licensesAndCertActiondoLoad.action", 
		"Snapshot=t13.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=licensesAndCertDto.vendorId", "Value={vendorId}", ENDITEM, 
		"Name=licensesAndCertDto.vendorCredId", "Value=0", ENDITEM, 
		"Name=licensesAndCertDto.credentialTypeId", "Value=4", ENDITEM, 
		"Name=licensesAndCertDto.categoryId", "Value=-1", ENDITEM, 
		"Name=licensesAndCertDto.licenseName", "Value=", ENDITEM, 
		"Name=licensesAndCertDto.issuerOfCredential", "Value=", ENDITEM, 
		"Name=licensesAndCertDto.credentialNum", "Value=", ENDITEM, 
		"Name=licensesAndCertDto.city", "Value=", ENDITEM, 
		"Name=licensesAndCertDto.stateId", "Value=AL", ENDITEM, 
		"Name=licensesAndCertDto.county", "Value=", ENDITEM, 
		"Name=licensesAndCertDto.issueDate", "Value=", ENDITEM, 
		"Name=licensesAndCertDto.expirationDate", "Value=", ENDITEM, 
		"Name=licensesAndCertDto.file", "Value=", "File=Yes", ENDITEM, 
		LAST);

	web_custom_request("Licenses & Certifications_2", 
		"URL=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/licensesAndCertdoViewList.action", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/allTabView.action?tabView=tab3&nexturl=licensesAndCertdoViewList.action", 
		"Snapshot=t14.inf", 
		"Mode=HTML", 
		LAST);

	//web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DProFirmProfile%2526pidt%253D1%2526oid%253Dfunctionanonymous%252528%252529%25257BsaveLCInformation%252528%252527licensesAndCertActiondoSave.action%252527%252529%25253B%25257D%2526oidt%253D2%2526ot%253DIMAGE%2526oi%253D586; DOMAIN=198.179.148.51");

	web_reg_find("Text=ServiceLive [Provider Registration]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("licensesAndCertActiondoSave.action", 
		"Action=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/licensesAndCertActiondoSave.action", 
		"Method=POST", 
		"EncType=multipart/form-data", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/allTabView.action?tabView=tab3&nexturl=licensesAndCertdoViewList.action", 
		"Snapshot=t15.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=licensesAndCertDto.vendorId", "Value={vendorId}", ENDITEM, 
		"Name=licensesAndCertDto.vendorCredId", "Value=0", ENDITEM, 
		"Name=licensesAndCertDto.credentialTypeId", "Value=4", ENDITEM, 
		"Name=licensesAndCertDto.categoryId", "Value=28", ENDITEM, 
		"Name=licensesAndCertDto.licenseName", "Value=Tester", ENDITEM, 
		"Name=licensesAndCertDto.issuerOfCredential", "Value=Testing", ENDITEM, 
		"Name=licensesAndCertDto.credentialNum", "Value=123137", ENDITEM, 
		"Name=licensesAndCertDto.city", "Value={businessCity}", ENDITEM, 
		"Name=licensesAndCertDto.stateId", "Value={businessState}", ENDITEM, 
		"Name=licensesAndCertDto.county", "Value=COOK", ENDITEM, 
		"Name=licensesAndCertDto.issueDate", "Value=2009-09-15", ENDITEM, 
		"Name=licensesAndCertDto.expirationDate", "Value=2012-01-18", ENDITEM, 
		"Name=licensesAndCertDto.file", "Value=", "File=Yes", ENDITEM, 
		"Name=x", "Value=59", ENDITEM, 
		"Name=y", "Value=4", ENDITEM, 
		LAST);

	web_custom_request("Licenses & Certifications_3", 
		"URL=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/listLicenceAndCertdoViewList1.action", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/allTabView.action?tabView=tab3&nexturl=listLicenceAndCertdoViewList1.action", 
		"Snapshot=t16.inf", 
		"Mode=HTML", 
		LAST);

	//web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DProFirmProfile%2526pidt%253D1%2526oid%253Dhttps%25253A//198.179.148.51%25253A1743/ServiceLiveWebUtil/images/images_registration/common/spacer.gif%2526ot%253DIMAGE%2526oi%253D458; DOMAIN=198.179.148.51");

	web_reg_find("Text=ServiceLive [Provider Registration]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("licensesAndCertAction.action", 
		"Action=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/licensesAndCertAction.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/allTabView.action?tabView=tab3&nexturl=listLicenceAndCertdoViewList1.action", 
		"Snapshot=t17.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=action:licensesAndCertActiondoNextLicenses.x", "Value=31", ENDITEM, 
		"Name=action:licensesAndCertActiondoNextLicenses.y", "Value=8", ENDITEM, 
		LAST);

	web_custom_request("addInsuranceActionloadInsuranceList.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/addInsuranceActionloadInsuranceList.action?tabView=tab4", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/allTabView.action?tabView=tab4", 
		"Snapshot=t18.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=tabView=tab4", 
		LAST);

	lr_end_transaction("L&C",LR_AUTO);

	lr_start_transaction("Insurance");

	web_reg_find("Text=ServiceLive [Provider Registration]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("addInsuranceActionloadInsuranceTypeList.action", 
		"Action=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/addInsuranceActionloadInsuranceTypeList.action", 
		"Method=POST", 
		"EncType=multipart/form-data", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/allTabView.action?tabView=tab4", 
		"Snapshot=t19.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=cbgliBtn_type", "Value=", ENDITEM, 
		"Name=vliBtn_type", "Value=", ENDITEM, 
		"Name=wciBtn_type", "Value=", ENDITEM, 
		"Name=insuranceInfoDto.WCI", "Value=1", ENDITEM, 
		"Name=insuranceInfoDto.CBGLI", "Value=true", ENDITEM, 
		"Name=__checkbox_insuranceInfoDto.CBGLI", "Value=true", ENDITEM, 
		"Name=insuranceInfoDto.CBGLIAmount", "Value=123134", ENDITEM, 
		"Name=insuranceInfoDto.VLI", "Value=true", ENDITEM, 
		"Name=__checkbox_insuranceInfoDto.VLI", "Value=true", ENDITEM, 
		"Name=insuranceInfoDto.VLIAmount", "Value=521521", ENDITEM, 
		"Name=insuranceInfoDtoWCI", "Value=1", ENDITEM, 
		"Name=insuranceInfoDto.WCIAmount", "Value=526421", ENDITEM, 
		LAST);

	web_custom_request("termsAndCondActiondoLoad.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/jsp/providerRegistration/termsAndCondActiondoLoad.action?tabView=tab5", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/allTabView.action?tabView=tab5", 
		"Snapshot=t20.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=tabView=tab5", 
		LAST);

	lr_end_transaction("Insurance",LR_AUTO);

	lr_start_transaction("T&C");

	//web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DProFirmProfile%2526pidt%253D1%2526oid%253Dfunctionanonymous%252528%252529%25257BsetCheckBox%252528%252527termsAndCondActiondoSave.action%252527%252529%25257D%2526oidt%253D2%2526ot%253DIMAGE%2526oi%253D712; DOMAIN=198.179.148.51");

	web_reg_find("Text=ServiceLive [Provider Registration]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("termsAndCondActiondoSave.action", 
		"Action=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/termsAndCondActiondoSave.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/allTabView.action?tabView=tab5", 
		"Snapshot=t21.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=termsFlag", "Value=false", ENDITEM, 
		"Name=termsAndCondDto.acceptTerms", "Value=true", ENDITEM, 
		"Name=__checkbox_termsAndCondDto.acceptTerms", "Value=true", ENDITEM, 
		"Name=termsAndCondDto.acceptBucksTerms", "Value=true", ENDITEM, 
		"Name=__checkbox_termsAndCondDto.acceptBucksTerms", "Value=true", ENDITEM, 
		"Name=termsAndCondDto.acceptTermsInd", "Value=1", ENDITEM, 
		"Name=termsAndCondDto.acceptBucksTermsInd", "Value=1", ENDITEM, 
		"Name=x", "Value=27", ENDITEM, 
		"Name=y", "Value=10", ENDITEM, 
		LAST);

	web_custom_request("termsAndCondActiondoLoad.action_2", 
		"URL=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/termsAndCondActiondoLoad.action?type=save", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/allTabView.action?tabView=tab5&nexturl=termsAndCondActiondoLoad.action%3Ftype%3Dsave", 
		"Snapshot=t22.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=type=save", 
		LAST);

	lr_end_transaction("T&C",LR_AUTO);

	lr_start_transaction("ServiceProProfile");

	web_reg_find("Text=ServiceLive [Service Provider Registration]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_url("dashboardAction.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/dashboardAction.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/allTabView.action?tabView=tab5&nexturl=termsAndCondActiondoLoad.action%3Ftype%3Dsave", 
		"Snapshot=t23.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("ServiceProProfile",LR_AUTO);

	lr_start_transaction("ManageTeam");

	web_reg_find("Text=ServiceLive [SP Profile]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_url("Manage Team", 
		"URL=https://198.179.148.51:1743/MarketFrontend/manageUserActiondoLoadUsers.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/regDashboard.action", 
		"Snapshot=t24.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("ManageTeam",LR_AUTO);




	return 0;
}