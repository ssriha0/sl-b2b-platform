Action()
{

	lr_start_transaction("Home");

	//web_add_cookie("__utma=178720740.2958222.1254433160.1254769284.1254770683.8; DOMAIN=198.179.148.51");

	//web_add_cookie("__utmz=178720740.1254433160.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); DOMAIN=198.179.148.51");

	//web_add_cookie("__utmb=178720740.1.10.1254770683; DOMAIN=198.179.148.51");

	web_reg_find("Text=ServiceLive : Home Improvement & Repair", 
		LAST);

	web_url("homepage.action", 
		"URL=http://198.179.148.51:1780/MarketFrontend/homepage.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t2.inf", 
		"Mode=HTML", 
		LAST);

/*	//web_add_cookie("id=223e4f4cf20000d2||t=1254421052|et=730|cs=iq_9qofq; DOMAIN=fls.doubleclick.net");

	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=1634888043996.392", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=1634888043996.392?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://198.179.148.51:1780/MarketFrontend/homepage.action", 
		"Snapshot=t3.inf", 
		"Mode=HTML", 
		LAST);
*/
	lr_end_transaction("Home",LR_AUTO);

	lr_start_transaction("SPs Tab");

	//web_add_cookie("__utma=178720740.2958222.1254433160.1254770683.1254770741.9; DOMAIN=198.179.148.51");

	//web_add_cookie("__utmb=178720740.1.10.1254770741; DOMAIN=198.179.148.51");

	//web_add_cookie("s_cc=true; DOMAIN=198.179.148.51");

	//web_add_cookie("s_sq=%5B%5BB%5D%5D; DOMAIN=198.179.148.51");

	//web_add_cookie("__utmc=178720740; DOMAIN=198.179.148.51");

	web_reg_find("Text=Join ServiceLive", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_url("joinNowAction.action", 
		"URL=http://198.179.148.51:1780/MarketFrontend/joinNowAction.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://198.179.148.51:1780/MarketFrontend/homepage.action", 
		"Snapshot=t4.inf", 
		"Mode=HTML", 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=9550399695039.43", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=9550399695039.43?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://198.179.148.51:1780/MarketFrontend/joinNowAction.action", 
		"Snapshot=t5.inf", 
		"Mode=HTML", 
		LAST);*/

/*	web_url("activityi;src=2077836;type=landi583;cat=servi767;ord=1;num=8965240571920.398", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi767;ord=1;num=8965240571920.398?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://198.179.148.51:1780/MarketFrontend/joinNowAction.action", 
		"Snapshot=t6.inf", 
		"Mode=HTML", 
		LAST);*/

	lr_end_transaction("SPs Tab",LR_AUTO);

	lr_start_transaction("JoinNow");

	//web_add_cookie("__utmb=178720740.2.10.1254770741; DOMAIN=198.179.148.51");

	web_reg_find("Text=ServiceLive [Provider Registration]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_url("providerRegistrationAction.action", 
		"URL=http://198.179.148.51:1780/MarketFrontend/providerRegistrationAction.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://198.179.148.51:1780/MarketFrontend/joinNowAction.action", 
		"Snapshot=t7.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("JoinNow",LR_AUTO);

	lr_start_transaction("BusinessFirmInfo");

	web_reg_find("Text=ServiceLive [Provider Registration]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("providerRegSaveAction.action", 
		"Action=https://198.179.148.51:1743/MarketFrontend/providerRegSaveAction.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/providerRegistrationAction.action", 
		"Snapshot=t8.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=registrationDto.legalBusinessName", "Value={businessName}", ENDITEM, 
		"Name=registrationDto.DBAName", "Value=", ENDITEM, 
		"Name=registrationDto.mainBusiPhoneNo1", "Value=321", ENDITEM, 
		"Name=registrationDto.mainBusiPhoneNo2", "Value=414", ENDITEM, 
		"Name=registrationDto.mainBusiPhoneNo3", "Value=4325", ENDITEM, 
		"Name=registrationDto.mainBusinessExtn", "Value=", ENDITEM, 
		"Name=registrationDto.businessFax1", "Value=", ENDITEM, 
		"Name=registrationDto.businessFax2", "Value=", ENDITEM, 
		"Name=registrationDto.businessFax3", "Value=", ENDITEM, 
		"Name=registrationDto.primaryIndustry", "Value=800", ENDITEM, 
		"Name=registrationDto.websiteAddress", "Value=", ENDITEM, 
		"Name=registrationDto.businessStreet1", "Value={streetNo} Arlington", ENDITEM, 
		"Name=registrationDto.businessStreet2", "Value=", ENDITEM, 
		"Name=registrationDto.businessAprt", "Value=", ENDITEM, 
		"Name=registrationDto.businessCity", "Value={cityName}", ENDITEM, 
		"Name=registrationDto.businessState", "Value={stateName}", ENDITEM, 
		"Name=registrationDto.businessZip", "Value={zipCode}", ENDITEM, 
		"Name=registrationDto.mailAddressChk", "Value=true", ENDITEM, 
		"Name=__checkbox_registrationDto.mailAddressChk", "Value=true", ENDITEM, 
		"Name=registrationDto.mailingStreet1", "Value={streetNo} Arlington", ENDITEM, 
		"Name=registrationDto.mailingStreet2", "Value=", ENDITEM, 
		"Name=registrationDto.mailingAprt", "Value=", ENDITEM, 
		"Name=registrationDto.mailingCity", "Value={cityName}", ENDITEM, 
		"Name=registrationDto.mailingState", "Value={stateName}", ENDITEM, 
		"Name=registrationDto.mailingZip", "Value={zipCode}", ENDITEM, 
		"Name=registrationDto.roleWithinCom", "Value=2", ENDITEM, 
		"Name=registrationDto.jobTitle", "Value=", ENDITEM, 
		"Name=registrationDto.firstName", "Value=Stress", ENDITEM, 
		"Name=registrationDto.middleName", "Value=", ENDITEM, 
		"Name=registrationDto.lastName", "Value=User", ENDITEM, 
		"Name=registrationDto.nameSuffix", "Value=", ENDITEM, 
		"Name=registrationDto.email", "Value=slprovideradmin@gmail.com", ENDITEM, 
		"Name=registrationDto.confirmEmail", "Value=slprovideradmin@gmail.com", ENDITEM, 
		"Name=registrationDto.altEmail", "Value=", ENDITEM, 
		"Name=registrationDto.confAltEmail", "Value=", ENDITEM, 
		"Name=registrationDto.userName", "Value={businessName}", ENDITEM, 
		"Name=registrationDto.serviceCall", "Value=Yes", ENDITEM, 
		"Name=registrationDto.howDidYouHear", "Value=2", ENDITEM, 
		"Name=registrationDto.promotionCode", "Value=", ENDITEM, 
		"Name=x", "Value=64", ENDITEM, 
		"Name=y", "Value=5", ENDITEM, 
		LAST);

	/*web_url("activityi;src=2077836;type=conve041;cat=servi093;ord=1;num=1151497059036.034", 
		"URL=https://fls.doubleclick.net/activityi;src=2077836;type=conve041;cat=servi093;ord=1;num=1151497059036.034?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/providerRegSaveAction.action", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		LAST);*/

	//web_add_cookie("id=223e4f4cf20000d2||t=1254421052|et=730|cs=iq_9qofq; DOMAIN=googleads.g.doubleclick.net");

	web_url("1049460976", 
		"URL=https://www.googleadservices.com/pagead/conversion/1049460976/?random=1254770954562&cv=6&fst=1254770954562&num=1&fmt=1&label=pTrNCJaKexDwgbb0Aw&bg=ffffff&hl=en&gl=US&guid=ON&u_h=900&u_w=1440&u_ah=870&u_aw=1440&u_cd=32&u_his=3&u_tz=-300&u_nplug=0&u_nmime=0&url=https%3A//198.179.148.51%3A1743/MarketFrontend/providerRegSaveAction.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://fls.doubleclick.net/activityi;src=2077836;type=conve041;cat=servi093;ord=1;num=1151497059036.034?", 
		"Snapshot=t10.inf", 
		"Mode=HTML", 
		LAST);

	web_reg_find("Text=ServiceLive : Home Improvement & Repair", 
		LAST);

	web_submit_data("login.action", 
		"Action=https://198.179.148.51:1743/MarketFrontend/login.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/providerRegSaveAction.action", 
		"Snapshot=t11.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=zipCode", "Value=Enter Zip Code", ENDITEM, 
		"Name=popularSimpleServices[0].name", "Value=", ENDITEM, 
		"Name=popularSimpleServices[0].mainCategoryId", "Value=", ENDITEM, 
		"Name=popularSimpleServices[0].categoryId", "Value=", ENDITEM, 
		"Name=popularSimpleServices[0].subCategoryId", "Value=", ENDITEM, 
		"Name=popularSimpleServices[0].serviceTypeTemplateId", "Value=", ENDITEM, 
		"Name=popularSimpleServices[0].buyerTypeId", "Value=", ENDITEM, 
		LAST);

	lr_end_transaction("BusinessFirmInfo",LR_AUTO);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=6929703912677.036", 
		"URL=https://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=6929703912677.036?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/login.action", 
		"Snapshot=t12.inf", 
		"Mode=HTML", 
		LAST);*/

	return 0;
}