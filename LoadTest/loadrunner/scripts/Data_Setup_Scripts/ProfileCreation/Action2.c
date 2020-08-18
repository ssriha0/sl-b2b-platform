
Action2()
{



	lr_start_transaction("AddServicePro");

	web_reg_find("Text=ServiceLive [Service Pro Profile]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_url("spacer.gif_2", 
		"URL=https://198.179.148.51:1743/MarketFrontend/serviceProAllTab!new.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/addServiceProAction.action", 
		"Snapshot=t25.inf", 
		"Mode=HTML", 
		LAST);

	web_custom_request("GeneralInformation", 
		"URL=https://198.179.148.51:1743/MarketFrontend/generalInfoAction!doLoad.action", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/serviceProAllTab!new.action", 
		"Snapshot=t26.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("AddServicePro",LR_AUTO);

//	if (i == 1) {
	
	lr_start_transaction("GeneralInfo");

	web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DAddPro%2526pidt%253D1%2526oid%253Dfunctionanonymous%252528%252529%25257BsetIndicator%252528%252529%25257D%2526oidt%253D2%2526ot%253DIMAGE%2526oi%253D1857; DOMAIN=198.179.148.51");

	web_reg_find("Text=ServiceLive [Service Pro Profile]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("generalInfoAction.action", 
		"Action=https://198.179.148.51:1743/MarketFrontend/generalInfoAction.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/serviceProAllTab!new.action", 
		"Snapshot=t27.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=generalInfoDto.vendorId", "Value={vendorId}", ENDITEM, 
		"Name=generalInfoDto.resourceId", "Value=", ENDITEM, 
		"Name=generalInfoDto.contactId", "Value=0", ENDITEM, 
		"Name=generalInfoDto.locationId", "Value=0", ENDITEM, 
		"Name=generalInfoDto.ssn", "Value=123124900", ENDITEM, 
		"Name=generalInfoDto.userNameAdmin", "Value=", ENDITEM, 
		"Name=generalInfoDto.ssnValInd", "Value=1", ENDITEM, 
		"Name=generalInfoDto.resourceInd", "Value=1", ENDITEM, 
		"Name=generalInfoDto.firstName", "Value=Stress", ENDITEM, 
		"Name=generalInfoDto.middleName", "Value=", ENDITEM, 
		"Name=generalInfoDto.lastName", "Value={LastName}", ENDITEM, 
		"Name=generalInfoDto.suffix", "Value=", ENDITEM, 
		"Name=generalInfoDto.ssnLeft", "Value=123", ENDITEM, 
		"Name=generalInfoDto.ssnMiddle", "Value=12", ENDITEM, 
		"Name=generalInfoDto.ssnRight", "Value=4900", ENDITEM, 
		"Name=generalInfoDto.ownerInd", "Value=1", ENDITEM, 
		"Name=generalInfoDto.managerInd", "Value=1", ENDITEM, 
		"Name=generalInfoDto.adminInd", "Value=1", ENDITEM, 
		"Name=generalInfoDto.dispatchInd", "Value=1", ENDITEM, 
		"Name=generalInfoDto.sproInd", "Value=1", ENDITEM, 
		"Name=generalInfoDto.otherInd", "Value=1", ENDITEM, 
		"Name=generalInfoDto.otherJobTitle", "Value=", ENDITEM, 
		"Name=generalInfoDto.dispAddStreet1", "Value={businessStreet}", ENDITEM, 
		"Name=generalInfoDto.dispAddApt", "Value=", ENDITEM, 
		"Name=generalInfoDto.dispAddStreet2", "Value=", ENDITEM, 
		"Name=generalInfoDto.dispAddCity", "Value={businessCity}", ENDITEM, 
		"Name=generalInfoDto.dispAddState", "Value={businessState}", ENDITEM, 
		"Name=generalInfoDto.dispAddZip", "Value={businessZip}", ENDITEM, 
		"Name=generalInfoDto.dispAddGeographicRange", "Value=3", ENDITEM, 
		"Name=generalInfoDto.hourlyRate", "Value=50", ENDITEM, 
		"Name=mon", "Value=24", ENDITEM, 
		"Name=generalInfoDto.mon24Ind", "Value=1", ENDITEM, 
		"Name=generalInfoDto.monNaInd", "Value=", ENDITEM, 
		"Name=tue", "Value=24", ENDITEM, 
		"Name=generalInfoDto.tue24Ind", "Value=1", ENDITEM, 
		"Name=generalInfoDto.tueNaInd", "Value=", ENDITEM, 
		"Name=wed", "Value=24", ENDITEM, 
		"Name=generalInfoDto.wed24Ind", "Value=1", ENDITEM, 
		"Name=generalInfoDto.wedNaInd", "Value=", ENDITEM, 
		"Name=thu", "Value=24", ENDITEM, 
		"Name=generalInfoDto.thu24Ind", "Value=1", ENDITEM, 
		"Name=generalInfoDto.thuNaInd", "Value=", ENDITEM, 
		"Name=fri", "Value=24", ENDITEM, 
		"Name=generalInfoDto.fri24Ind", "Value=1", ENDITEM, 
		"Name=generalInfoDto.friNaInd", "Value=", ENDITEM, 
		"Name=sat", "Value=24", ENDITEM, 
		"Name=generalInfoDto.sat24Ind", "Value=1", ENDITEM, 
		"Name=generalInfoDto.satNaInd", "Value=", ENDITEM, 
		"Name=sun", "Value=24", ENDITEM, 
		"Name=generalInfoDto.sun24Ind", "Value=1", ENDITEM, 
		"Name=generalInfoDto.sunNaInd", "Value=", ENDITEM, 
		"Name=radChkUser", "Value=1", ENDITEM, 
		"Name=generalInfoDto.userName", "Value={userName}", ENDITEM, 
		"Name=action:generalInfoActiondoNext.x", "Value=25", ENDITEM, 
		"Name=action:generalInfoActiondoNext.y", "Value=10", ENDITEM, 
		LAST);

	web_custom_request("marketPlaceAction!doLoad.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/marketPlaceAction!doLoad.action?tabView=tab2", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab2", 
		"Snapshot=t28.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=tabView=tab2", 
		LAST);

	//web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DAddPro%2526pidt%253D1%2526oid%253DNext%252520%2526oidt%253D3%2526ot%253DSUBMIT%2526oi%253D300; DOMAIN=198.179.148.51");

	lr_end_transaction("GeneralInfo",LR_AUTO);
//	}

	//i=i+1;

	lr_start_transaction("MarketPlacePerferences");

	web_reg_find("Text=ServiceLive [Service Pro Profile]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("marketPlaceAction.action", 
		"Action=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/marketPlaceAction.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab2", 
		"Snapshot=t29.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=marketPlaceDTO.serviceCall", "Value=1", ENDITEM, 
		"Name=marketPlaceDTO.primaryIndicator", "Value=0", ENDITEM, 
		"Name=isCheckedactivity_0", "Value=true", ENDITEM, 
		"Name=__checkbox_isCheckedactivity_0", "Value=true", ENDITEM, 
		"Name=isCheckedactivity_1", "Value=true", ENDITEM, 
		"Name=__checkbox_isCheckedactivity_1", "Value=true", ENDITEM, 
		"Name=isCheckedactivity_2", "Value=true", ENDITEM, 
		"Name=__checkbox_isCheckedactivity_2", "Value=true", ENDITEM, 
		"Name=isCheckedactivity_3", "Value=true", ENDITEM, 
		"Name=__checkbox_isCheckedactivity_3", "Value=true", ENDITEM, 
		"Name=isCheckedactivity_4", "Value=true", ENDITEM, 
		"Name=__checkbox_isCheckedactivity_4", "Value=true", ENDITEM, 
		"Name=marketPlaceDTO.businessPhone1", "Value=213", ENDITEM, 
		"Name=marketPlaceDTO.businessPhone2", "Value=111", ENDITEM, 
		"Name=marketPlaceDTO.businessPhone3", "Value=4213", ENDITEM, 
		"Name=marketPlaceDTO.businessExtn", "Value=", ENDITEM, 
		"Name=marketPlaceDTO.mobilePhone1", "Value=", ENDITEM, 
		"Name=marketPlaceDTO.mobilePhone2", "Value=", ENDITEM, 
		"Name=marketPlaceDTO.mobilePhone3", "Value=", ENDITEM, 
		"Name=marketPlaceDTO.email", "Value=slprovideradmin@gmail.com", ENDITEM, 
		"Name=marketPlaceDTO.confirmEmail", "Value=slprovideradmin@gmail.com", ENDITEM, 
		"Name=marketPlaceDTO.altEmail", "Value=", ENDITEM, 
		"Name=marketPlaceDTO.confirmAltEmail", "Value=", ENDITEM, 
		"Name=marketPlaceDTO.smsAddress1", "Value=", ENDITEM, 
		"Name=marketPlaceDTO.smsAddress2", "Value=", ENDITEM, 
		"Name=marketPlaceDTO.smsAddress3", "Value=", ENDITEM, 
		"Name=marketPlaceDTO.confirmSmsAddress1", "Value=", ENDITEM, 
		"Name=marketPlaceDTO.confirmSmsAddress2", "Value=", ENDITEM, 
		"Name=marketPlaceDTO.confirmSmsAddress3", "Value=", ENDITEM, 
		"Name=marketPlaceDTO.secondaryContact1", "Value=1", ENDITEM, 
		"Name=action:marketPlaceActiondoNext", "Value=Next ", ENDITEM, 
		LAST);

	web_custom_request("skillAssignGeneralAction!loadSkills.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/jsp/providerRegistration/skillAssignGeneralAction!loadSkills.action?tabView=tab3", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab3", 
		"Snapshot=t30.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=tabView=tab3", 
		LAST);

	lr_end_transaction("MarketPlacePerferences",LR_AUTO);

	lr_start_transaction("Skills&Services");

	web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DAddPro%2526pidt%253D1%2526oid%253Dfunctionanonymous%252528%252529%25257BsubmitSkillsPage%252528%252527skillAssignGeneralActionsaveSkills.action%25253Ftype%25253DbuildSkill%252527%252529%25257D%2526oidt%253D2%2526ot%253DIMAGE%2526oi%253D700; DOMAIN=198.179.148.51");

	web_reg_find("Text=ServiceLive [Service Pro Profile]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("skillAssignGeneralActionsaveSkills.action", 
		"Action=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/skillAssignGeneralActionsaveSkills.action?type=buildSkill", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab3", 
		"Snapshot=t31.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=lan_1", "Value=English", ENDITEM, 
		"Name=chk_800_150", "Value=on", ENDITEM, 
		"Name=x", "Value=101", ENDITEM, 
		"Name=y", "Value=9", ENDITEM, 
		LAST);

	web_custom_request("resourceSkillAssignAction!getSkills.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/jsp/providerRegistration/resourceSkillAssignAction!getSkills.action?tabView=tab3&tabType=level32", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab3&tabType=level32", 
		"Snapshot=t32.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=tabView=tab3&tabType=level32", 
		LAST);

	//web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DAddPro%2526pidt%253D1%2526oid%253Dfunctionanonymous%252528%252529%25257BsubmitServicePage%252528%252527resourceSkillAssignActionassignSkills.action%25253Ftype%25253Dforward%252527%252529%25257D%2526oidt%253D2%2526ot%253DIMAGE%2526oi%253D875; DOMAIN=198.179.148.51");

	web_reg_find("Text=ServiceLive [Service Pro Profile]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("resourceSkillAssignActionassignSkills.action", 
		"Action=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/resourceSkillAssignActionassignSkills.action?type=forward", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab3&tabType=level32", 
		"Snapshot=t33.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=ckeckAll_16", "Value=on", ENDITEM, 
		"Name=hiddHeaders", "Value=16", ENDITEM, 
		"Name=ckeckAll_30", "Value=on", ENDITEM, 
		"Name=hiddHeaders", "Value=30", ENDITEM, 
		"Name=ckeckAll_44", "Value=on", ENDITEM, 
		"Name=hiddHeaders", "Value=44", ENDITEM, 
		"Name=ckeckAll_56", "Value=on", ENDITEM, 
		"Name=hiddHeaders", "Value=56", ENDITEM, 
		"Name=ckeckAll_75", "Value=on", ENDITEM, 
		"Name=hiddHeaders", "Value=75", ENDITEM, 
		"Name=chk_800_16_0_1", "Value=on", ENDITEM, 
		"Name=chk_800_30_0_1", "Value=on", ENDITEM, 
		"Name=chk_800_44_0_1", "Value=on", ENDITEM, 
		"Name=chk_800_56_0_1", "Value=on", ENDITEM, 
		"Name=chk_800_75_0_1", "Value=on", ENDITEM, 
		"Name=chk_801_16_800_2", "Value=on", ENDITEM, 
		"Name=chk_801_30_800_2", "Value=on", ENDITEM, 
		"Name=chk_801_44_800_2", "Value=on", ENDITEM, 
		"Name=chk_801_56_800_2", "Value=on", ENDITEM, 
		"Name=chk_801_75_800_2", "Value=on", ENDITEM, 
		"Name=chk_802_16_801_3", "Value=on", ENDITEM, 
		"Name=chk_802_30_801_3", "Value=on", ENDITEM, 
		"Name=chk_802_44_801_3", "Value=on", ENDITEM, 
		"Name=chk_802_56_801_3", "Value=on", ENDITEM, 
		"Name=chk_802_75_801_3", "Value=on", ENDITEM, 
		"Name=chk_803_16_801_3", "Value=on", ENDITEM, 
		"Name=chk_803_30_801_3", "Value=on", ENDITEM, 
		"Name=chk_803_44_801_3", "Value=on", ENDITEM, 
		"Name=chk_803_56_801_3", "Value=on", ENDITEM, 
		"Name=chk_803_75_801_3", "Value=on", ENDITEM, 
		"Name=chk_804_16_801_3", "Value=on", ENDITEM, 
		"Name=chk_804_30_801_3", "Value=on", ENDITEM, 
		"Name=chk_804_44_801_3", "Value=on", ENDITEM, 
		"Name=chk_804_56_801_3", "Value=on", ENDITEM, 
		"Name=chk_804_75_801_3", "Value=on", ENDITEM, 
		"Name=chk_805_16_801_3", "Value=on", ENDITEM, 
		"Name=chk_805_30_801_3", "Value=on", ENDITEM, 
		"Name=chk_805_44_801_3", "Value=on", ENDITEM, 
		"Name=chk_805_56_801_3", "Value=on", ENDITEM, 
		"Name=chk_805_75_801_3", "Value=on", ENDITEM, 
		"Name=chk_806_16_801_3", "Value=on", ENDITEM, 
		"Name=chk_806_30_801_3", "Value=on", ENDITEM, 
		"Name=chk_806_44_801_3", "Value=on", ENDITEM, 
		"Name=chk_806_56_801_3", "Value=on", ENDITEM, 
		"Name=chk_806_75_801_3", "Value=on", ENDITEM, 
		"Name=chk_842_16_801_3", "Value=on", ENDITEM, 
		"Name=chk_842_30_801_3", "Value=on", ENDITEM, 
		"Name=chk_842_44_801_3", "Value=on", ENDITEM, 
		"Name=chk_842_56_801_3", "Value=on", ENDITEM, 
		"Name=chk_842_75_801_3", "Value=on", ENDITEM, 
		"Name=chk_843_16_801_3", "Value=on", ENDITEM, 
		"Name=chk_843_30_801_3", "Value=on", ENDITEM, 
		"Name=chk_843_44_801_3", "Value=on", ENDITEM, 
		"Name=chk_843_56_801_3", "Value=on", ENDITEM, 
		"Name=chk_843_75_801_3", "Value=on", ENDITEM, 
		"Name=chk_807_16_800_2", "Value=on", ENDITEM, 
		"Name=chk_807_30_800_2", "Value=on", ENDITEM, 
		"Name=chk_807_44_800_2", "Value=on", ENDITEM, 
		"Name=chk_807_56_800_2", "Value=on", ENDITEM, 
		"Name=chk_807_75_800_2", "Value=on", ENDITEM, 
		"Name=chk_808_16_800_2", "Value=on", ENDITEM, 
		"Name=chk_808_30_800_2", "Value=on", ENDITEM, 
		"Name=chk_808_44_800_2", "Value=on", ENDITEM, 
		"Name=chk_808_56_800_2", "Value=on", ENDITEM, 
		"Name=chk_808_75_800_2", "Value=on", ENDITEM, 
		"Name=chk_809_16_800_2", "Value=on", ENDITEM, 
		"Name=chk_809_30_800_2", "Value=on", ENDITEM, 
		"Name=chk_809_44_800_2", "Value=on", ENDITEM, 
		"Name=chk_809_56_800_2", "Value=on", ENDITEM, 
		"Name=chk_809_75_800_2", "Value=on", ENDITEM, 
		"Name=chk_810_16_809_3", "Value=on", ENDITEM, 
		"Name=chk_810_30_809_3", "Value=on", ENDITEM, 
		"Name=chk_810_44_809_3", "Value=on", ENDITEM, 
		"Name=chk_810_56_809_3", "Value=on", ENDITEM, 
		"Name=chk_810_75_809_3", "Value=on", ENDITEM, 
		"Name=chk_811_16_809_3", "Value=on", ENDITEM, 
		"Name=chk_811_30_809_3", "Value=on", ENDITEM, 
		"Name=chk_811_44_809_3", "Value=on", ENDITEM, 
		"Name=chk_811_56_809_3", "Value=on", ENDITEM, 
		"Name=chk_811_75_809_3", "Value=on", ENDITEM, 
		"Name=chk_812_16_809_3", "Value=on", ENDITEM, 
		"Name=chk_812_30_809_3", "Value=on", ENDITEM, 
		"Name=chk_812_44_809_3", "Value=on", ENDITEM, 
		"Name=chk_812_56_809_3", "Value=on", ENDITEM, 
		"Name=chk_812_75_809_3", "Value=on", ENDITEM, 
		"Name=chk_813_16_809_3", "Value=on", ENDITEM, 
		"Name=chk_813_30_809_3", "Value=on", ENDITEM, 
		"Name=chk_813_44_809_3", "Value=on", ENDITEM, 
		"Name=chk_813_56_809_3", "Value=on", ENDITEM, 
		"Name=chk_813_75_809_3", "Value=on", ENDITEM, 
		"Name=chk_814_16_809_3", "Value=on", ENDITEM, 
		"Name=chk_814_30_809_3", "Value=on", ENDITEM, 
		"Name=chk_814_44_809_3", "Value=on", ENDITEM, 
		"Name=chk_814_56_809_3", "Value=on", ENDITEM, 
		"Name=chk_814_75_809_3", "Value=on", ENDITEM, 
		"Name=chk_815_16_809_3", "Value=on", ENDITEM, 
		"Name=chk_815_30_809_3", "Value=on", ENDITEM, 
		"Name=chk_815_44_809_3", "Value=on", ENDITEM, 
		"Name=chk_815_56_809_3", "Value=on", ENDITEM, 
		"Name=chk_815_75_809_3", "Value=on", ENDITEM, 
		"Name=chk_816_16_800_2", "Value=on", ENDITEM, 
		"Name=chk_816_30_800_2", "Value=on", ENDITEM, 
		"Name=chk_816_44_800_2", "Value=on", ENDITEM, 
		"Name=chk_816_56_800_2", "Value=on", ENDITEM, 
		"Name=chk_816_75_800_2", "Value=on", ENDITEM, 
		"Name=chk_817_16_816_3", "Value=on", ENDITEM, 
		"Name=chk_817_30_816_3", "Value=on", ENDITEM, 
		"Name=chk_817_44_816_3", "Value=on", ENDITEM, 
		"Name=chk_817_56_816_3", "Value=on", ENDITEM, 
		"Name=chk_817_75_816_3", "Value=on", ENDITEM, 
		"Name=chk_818_16_816_3", "Value=on", ENDITEM, 
		"Name=chk_818_30_816_3", "Value=on", ENDITEM, 
		"Name=chk_818_44_816_3", "Value=on", ENDITEM, 
		"Name=chk_818_56_816_3", "Value=on", ENDITEM, 
		"Name=chk_818_75_816_3", "Value=on", ENDITEM, 
		"Name=chk_819_16_816_3", "Value=on", ENDITEM, 
		"Name=chk_819_30_816_3", "Value=on", ENDITEM, 
		"Name=chk_819_44_816_3", "Value=on", ENDITEM, 
		"Name=chk_819_56_816_3", "Value=on", ENDITEM, 
		"Name=chk_819_75_816_3", "Value=on", ENDITEM, 
		"Name=chk_820_16_816_3", "Value=on", ENDITEM, 
		"Name=chk_820_30_816_3", "Value=on", ENDITEM, 
		"Name=chk_820_44_816_3", "Value=on", ENDITEM, 
		"Name=chk_820_56_816_3", "Value=on", ENDITEM, 
		"Name=chk_820_75_816_3", "Value=on", ENDITEM, 
		"Name=chk_821_16_816_3", "Value=on", ENDITEM, 
		"Name=chk_821_30_816_3", "Value=on", ENDITEM, 
		"Name=chk_821_44_816_3", "Value=on", ENDITEM, 
		"Name=chk_821_56_816_3", "Value=on", ENDITEM, 
		"Name=chk_821_75_816_3", "Value=on", ENDITEM, 
		"Name=chk_822_16_800_2", "Value=on", ENDITEM, 
		"Name=chk_822_30_800_2", "Value=on", ENDITEM, 
		"Name=chk_822_44_800_2", "Value=on", ENDITEM, 
		"Name=chk_822_56_800_2", "Value=on", ENDITEM, 
		"Name=chk_822_75_800_2", "Value=on", ENDITEM, 
		"Name=chk_823_16_822_3", "Value=on", ENDITEM, 
		"Name=chk_823_30_822_3", "Value=on", ENDITEM, 
		"Name=chk_823_44_822_3", "Value=on", ENDITEM, 
		"Name=chk_823_56_822_3", "Value=on", ENDITEM, 
		"Name=chk_823_75_822_3", "Value=on", ENDITEM, 
		"Name=chk_824_16_822_3", "Value=on", ENDITEM, 
		"Name=chk_824_30_822_3", "Value=on", ENDITEM, 
		"Name=chk_824_44_822_3", "Value=on", ENDITEM, 
		"Name=chk_824_56_822_3", "Value=on", ENDITEM, 
		"Name=chk_824_75_822_3", "Value=on", ENDITEM, 
		"Name=chk_825_16_822_3", "Value=on", ENDITEM, 
		"Name=chk_825_30_822_3", "Value=on", ENDITEM, 
		"Name=chk_825_44_822_3", "Value=on", ENDITEM, 
		"Name=chk_825_56_822_3", "Value=on", ENDITEM, 
		"Name=chk_825_75_822_3", "Value=on", ENDITEM, 
		"Name=chk_826_16_822_3", "Value=on", ENDITEM, 
		"Name=chk_826_30_822_3", "Value=on", ENDITEM, 
		"Name=chk_826_44_822_3", "Value=on", ENDITEM, 
		"Name=chk_826_56_822_3", "Value=on", ENDITEM, 
		"Name=chk_826_75_822_3", "Value=on", ENDITEM, 
		"Name=chk_827_16_800_2", "Value=on", ENDITEM, 
		"Name=chk_827_30_800_2", "Value=on", ENDITEM, 
		"Name=chk_827_44_800_2", "Value=on", ENDITEM, 
		"Name=chk_827_56_800_2", "Value=on", ENDITEM, 
		"Name=chk_827_75_800_2", "Value=on", ENDITEM, 
		"Name=chk_828_16_800_2", "Value=on", ENDITEM, 
		"Name=chk_828_30_800_2", "Value=on", ENDITEM, 
		"Name=chk_828_44_800_2", "Value=on", ENDITEM, 
		"Name=chk_828_56_800_2", "Value=on", ENDITEM, 
		"Name=chk_828_75_800_2", "Value=on", ENDITEM, 
		"Name=chk_829_16_800_2", "Value=on", ENDITEM, 
		"Name=chk_829_30_800_2", "Value=on", ENDITEM, 
		"Name=chk_829_44_800_2", "Value=on", ENDITEM, 
		"Name=chk_829_56_800_2", "Value=on", ENDITEM, 
		"Name=chk_829_75_800_2", "Value=on", ENDITEM, 
		"Name=chk_830_16_800_2", "Value=on", ENDITEM, 
		"Name=chk_830_30_800_2", "Value=on", ENDITEM, 
		"Name=chk_830_44_800_2", "Value=on", ENDITEM, 
		"Name=chk_830_56_800_2", "Value=on", ENDITEM, 
		"Name=chk_830_75_800_2", "Value=on", ENDITEM, 
		"Name=chk_831_16_800_2", "Value=on", ENDITEM, 
		"Name=chk_831_30_800_2", "Value=on", ENDITEM, 
		"Name=chk_831_44_800_2", "Value=on", ENDITEM, 
		"Name=chk_831_56_800_2", "Value=on", ENDITEM, 
		"Name=chk_831_75_800_2", "Value=on", ENDITEM, 
		"Name=chk_832_16_800_2", "Value=on", ENDITEM, 
		"Name=chk_832_30_800_2", "Value=on", ENDITEM, 
		"Name=chk_832_44_800_2", "Value=on", ENDITEM, 
		"Name=chk_832_56_800_2", "Value=on", ENDITEM, 
		"Name=chk_832_75_800_2", "Value=on", ENDITEM, 
		"Name=chk_833_16_800_2", "Value=on", ENDITEM, 
		"Name=chk_833_30_800_2", "Value=on", ENDITEM, 
		"Name=chk_833_44_800_2", "Value=on", ENDITEM, 
		"Name=chk_833_56_800_2", "Value=on", ENDITEM, 
		"Name=chk_833_75_800_2", "Value=on", ENDITEM, 
		"Name=chk_834_16_833_3", "Value=on", ENDITEM, 
		"Name=chk_834_30_833_3", "Value=on", ENDITEM, 
		"Name=chk_834_44_833_3", "Value=on", ENDITEM, 
		"Name=chk_834_56_833_3", "Value=on", ENDITEM, 
		"Name=chk_834_75_833_3", "Value=on", ENDITEM, 
		"Name=chk_835_16_800_2", "Value=on", ENDITEM, 
		"Name=chk_835_30_800_2", "Value=on", ENDITEM, 
		"Name=chk_835_44_800_2", "Value=on", ENDITEM, 
		"Name=chk_835_56_800_2", "Value=on", ENDITEM, 
		"Name=chk_835_75_800_2", "Value=on", ENDITEM, 
		"Name=chk_836_16_835_3", "Value=on", ENDITEM, 
		"Name=chk_836_30_835_3", "Value=on", ENDITEM, 
		"Name=chk_836_44_835_3", "Value=on", ENDITEM, 
		"Name=chk_836_56_835_3", "Value=on", ENDITEM, 
		"Name=chk_836_75_835_3", "Value=on", ENDITEM, 
		"Name=chk_837_16_835_3", "Value=on", ENDITEM, 
		"Name=chk_837_30_835_3", "Value=on", ENDITEM, 
		"Name=chk_837_44_835_3", "Value=on", ENDITEM, 
		"Name=chk_837_56_835_3", "Value=on", ENDITEM, 
		"Name=chk_837_75_835_3", "Value=on", ENDITEM, 
		"Name=chk_838_16_835_3", "Value=on", ENDITEM, 
		"Name=chk_838_30_835_3", "Value=on", ENDITEM, 
		"Name=chk_838_44_835_3", "Value=on", ENDITEM, 
		"Name=chk_838_56_835_3", "Value=on", ENDITEM, 
		"Name=chk_838_75_835_3", "Value=on", ENDITEM, 
		"Name=chk_839_16_800_2", "Value=on", ENDITEM, 
		"Name=chk_839_30_800_2", "Value=on", ENDITEM, 
		"Name=chk_839_44_800_2", "Value=on", ENDITEM, 
		"Name=chk_839_56_800_2", "Value=on", ENDITEM, 
		"Name=chk_839_75_800_2", "Value=on", ENDITEM, 
		"Name=chk_840_16_800_2", "Value=on", ENDITEM, 
		"Name=chk_840_30_800_2", "Value=on", ENDITEM, 
		"Name=chk_840_44_800_2", "Value=on", ENDITEM, 
		"Name=chk_840_56_800_2", "Value=on", ENDITEM, 
		"Name=chk_840_75_800_2", "Value=on", ENDITEM, 
		"Name=chk_841_16_800_2", "Value=on", ENDITEM, 
		"Name=chk_841_30_800_2", "Value=on", ENDITEM, 
		"Name=chk_841_44_800_2", "Value=on", ENDITEM, 
		"Name=chk_841_56_800_2", "Value=on", ENDITEM, 
		"Name=chk_841_75_800_2", "Value=on", ENDITEM, 
		"Name=chk_844_16_800_2", "Value=on", ENDITEM, 
		"Name=chk_844_30_800_2", "Value=on", ENDITEM, 
		"Name=chk_844_44_800_2", "Value=on", ENDITEM, 
		"Name=chk_844_56_800_2", "Value=on", ENDITEM, 
		"Name=chk_844_75_800_2", "Value=on", ENDITEM, 
		"Name=chk_845_16_800_2", "Value=on", ENDITEM, 
		"Name=chk_845_30_800_2", "Value=on", ENDITEM, 
		"Name=chk_845_44_800_2", "Value=on", ENDITEM, 
		"Name=chk_845_56_800_2", "Value=on", ENDITEM, 
		"Name=chk_845_75_800_2", "Value=on", ENDITEM, 
		"Name=chk_846_16_800_2", "Value=on", ENDITEM, 
		"Name=chk_846_30_800_2", "Value=on", ENDITEM, 
		"Name=chk_846_44_800_2", "Value=on", ENDITEM, 
		"Name=chk_846_56_800_2", "Value=on", ENDITEM, 
		"Name=chk_846_75_800_2", "Value=on", ENDITEM, 
		"Name=x", "Value=29", ENDITEM, 
		"Name=y", "Value=5", ENDITEM, 
		LAST);

	web_reg_find("Text=ServiceLive [Provider Admin]", 
		LAST);

	web_custom_request("teamCredentialAction!loadCredentialList.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/teamCredentialAction!loadCredentialList.action?tabView=tab4", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab4", 
		"Snapshot=t34.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=tabView=tab4", 
		LAST);

	lr_end_transaction("Skills&Services",LR_AUTO);

	lr_start_transaction("L&C");

	//web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DAddPro%2526pidt%253D1%2526oid%253Dfunctionanonymous%252528%252529%25257BaddCredentials%252528%252527teamCredentialAction%252521loadCredentialDetails.action%252527%252529%25257D%2526oidt%253D2%2526ot%253DIMAGE%2526oi%253D218; DOMAIN=198.179.148.51");

	web_reg_find("Text=ServiceLive [Service Pro Profile]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("teamCredentialAction!addCredentilDetails.action", 
		"Action=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/teamCredentialAction!addCredentilDetails.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab4", 
		"Snapshot=t35.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=teamCredentialsDto.size", "Value=0", ENDITEM, 
		"Name=checkboxExists", "Value=true", ENDITEM, 
		"Name=x", "Value=74", ENDITEM, 
		"Name=y", "Value=11", ENDITEM, 
		LAST);

	web_reg_save_param("resourceId",
		  "LB=teamCredentialsDto.resourceId\" value=\"",
		  "RB=\" id=\"teamCredentialActionloadCredentialDetails_teamCredentialsDto_resourceId",
		  "Ord=1",
		  LAST);

	web_custom_request("teamCredentialAction!loadCredentialDetails.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/teamCredentialAction!loadCredentialDetails.action?tabView=tab4&tabType=level2", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab4&tabType=level2", 
		"Snapshot=t36.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=tabView=tab4&tabType=level2", 
		LAST);

	web_reg_find("Text=ServiceLive [Service Pro Profile]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("teamCredentialAction!changeCredentialCatType.action", 
		"Action=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/teamCredentialAction!changeCredentialCatType.action", 
		"Method=POST", 
		"EncType=multipart/form-data", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab4&tabType=level2", 
		"Snapshot=t37.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=teamCredentialsDto.resourceCredId", "Value=0", ENDITEM, 
		"Name=teamCredentialsDto.resourceId", "Value={resourceId}", ENDITEM, 
		"Name=teamCredentialsDto.size", "Value=0", ENDITEM, 
		"Name=teamCredentialsDto.typeId", "Value=6", ENDITEM, 
		"Name=teamCredentialsDto.categoryId", "Value=", ENDITEM, 
		"Name=teamCredentialsDto.licenseName", "Value=", ENDITEM, 
		"Name=teamCredentialsDto.issuerName", "Value=", ENDITEM, 
		"Name=teamCredentialsDto.credentialNumber", "Value=", ENDITEM, 
		"Name=teamCredentialsDto.city", "Value=", ENDITEM, 
		"Name=teamCredentialsDto.state", "Value=", ENDITEM, 
		"Name=teamCredentialsDto.county", "Value=", ENDITEM, 
		"Name=teamCredentialsDto.issueDate", "Value=", ENDITEM, 
		"Name=teamCredentialsDto.expirationDate", "Value=", ENDITEM, 
		"Name=teamCredentialsDto.file", "Value=", "File=Yes", ENDITEM, 
		LAST);

	web_custom_request("teamCredentialAction!changeCredCatType.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/teamCredentialAction!changeCredCatType.action?tabView=tab4&tabType=level3", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab4&tabType=level3", 
		"Snapshot=t38.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=tabView=tab4&tabType=level3", 
		LAST);

	//web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DAddPro%2526pidt%253D1%2526oid%253Dfunctionanonymous%252528%252529%25257BsaveCredentilDetail%252528%252529%25253B%25257D%2526oidt%253D2%2526ot%253DIMAGE%2526oi%253D360; DOMAIN=198.179.148.51");

	web_reg_find("Text=ServiceLive [Service Pro Profile]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("teamCredentialAction!saveCredentilDetail.action", 
		"Action=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/teamCredentialAction!saveCredentilDetail.action", 
		"Method=POST", 
		"EncType=multipart/form-data", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab4&tabType=level3", 
		"Snapshot=t39.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=teamCredentialsDto.resourceCredId", "Value=0", ENDITEM, 
		"Name=teamCredentialsDto.resourceId", "Value={resourceId}", ENDITEM, 
		"Name=teamCredentialsDto.size", "Value=0", ENDITEM, 
		"Name=teamCredentialsDto.typeId", "Value=6", ENDITEM, 
		"Name=teamCredentialsDto.categoryId", "Value=55", ENDITEM, 
		"Name=teamCredentialsDto.licenseName", "Value=perftester", ENDITEM, 
		"Name=teamCredentialsDto.issuerName", "Value=Tester", ENDITEM, 
		"Name=teamCredentialsDto.credentialNumber", "Value=123134", ENDITEM, 
		"Name=teamCredentialsDto.city", "Value={businessCity}", ENDITEM, 
		"Name=teamCredentialsDto.state", "Value={businessState}", ENDITEM, 
		"Name=teamCredentialsDto.county", "Value=COOK", ENDITEM, 
		"Name=teamCredentialsDto.issueDate", "Value=2009-08-12", ENDITEM, 
		"Name=teamCredentialsDto.expirationDate", "Value=2011-11-23", ENDITEM, 
		"Name=teamCredentialsDto.file", "Value=", "File=Yes", ENDITEM, 
		"Name=x", "Value=73", ENDITEM, 
		"Name=y", "Value=11", ENDITEM, 
		LAST);

	web_reg_find("Text=ServiceLive [Provider Admin]", 
		LAST);

	web_custom_request("teamCredentialAction!loadCredentialList.action_2", 
		"URL=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/teamCredentialAction!loadCredentialList.action?tabView=tab4", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab4", 
		"Snapshot=t40.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=tabView=tab4", 
		LAST);

	//web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DAddPro%2526pidt%253D1%2526oid%253Dfunctionanonymous%252528%252529%25257BnextCredentilList%252528%252529%25253B%25257D%2526oidt%253D2%2526ot%253DIMAGE%2526oi%253D241; DOMAIN=198.179.148.51");

	web_reg_find("Text=ServiceLive [Service Pro Profile]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("teamCredentialAction!doNextOnCredentialList.action", 
		"Action=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/teamCredentialAction!doNextOnCredentialList.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab4", 
		"Snapshot=t41.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=teamCredentialsDto.size", "Value=1", ENDITEM, 
		"Name=checkboxExists", "Value=false", ENDITEM, 
		"Name=x", "Value=18", ENDITEM, 
		"Name=y", "Value=8", ENDITEM, 
		LAST);

	

	web_reg_save_param("Param2",
		"LB=SERV601790681&amp;parm2=','{resourceId}','",
		"RB=')\"/",
		"Ord=1",
		LAST);

	web_custom_request("backgroundCheckAction!doLoad.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/backgroundCheckAction!doLoad.action?tabView=tab5", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab5", 
		"Snapshot=t42.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=tabView=tab5", 
		LAST);

	//web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DBackgroundCheck.PerformCheckNow%2526pidt%253D1%2526oid%253Dfunctionanonymous%252528%252529%25257BclickRequestOmniture%252528%252527BackgroundCheck.PerformCheckNow%252527%252529%25253BenableBackgroundCheck%252528%252527h%2526oidt%253D2%2526ot%253DIMAGE%2526oi%253D238; DOMAIN=198.179.148.51");

	lr_end_transaction("L&C",LR_AUTO);

	lr_start_transaction("BackgroundCheck");

	web_reg_find("Text=ServiceLive [Service Pro Profile]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("backgroundCheckAction.action", 
		"Action=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/backgroundCheckAction.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab5", 
		"Snapshot=t43.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=performheckInd", "Value=", ENDITEM, 
		"Name=teamProfileDto.email", "Value=slprovideradmin@gmail.com", ENDITEM, 
		"Name=teamProfileDto.confirmEmail", "Value=slprovideradmin@gmail.com", ENDITEM, 
		"Name=teamProfileDto.emailAlt", "Value=", ENDITEM, 
		"Name=teamProfileDto.confirmEmailAlt", "Value=", ENDITEM, 
		"Name=action:backgroundCheckActiondoCheck.x", "Value=117", ENDITEM, 
		"Name=action:backgroundCheckActiondoCheck.y", "Value=10", ENDITEM, 
		LAST);

	//web_add_cookie("__utma=219208603.1611413192.1254764994.1254774562.1254779528.3; DOMAIN=servicelive.plus1solutions.net");

	//web_add_cookie("__utmz=219208603.1254764994.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); DOMAIN=servicelive.plus1solutions.net");

	web_reg_find("Text=ServiceLive", 
		LAST);

	web_url("servicelive.plus1solutions.net", 
		"URL=https://servicelive.plus1solutions.net/?parm1=SERV601790681&parm2={resourceId}&parm3=ZgWM3XdJOyOEs0XZ1KCvbvB4XNWR3JJgCBRwcLHvocKDx5zHuK6%2FmplijfuwygwT", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t44.inf", 
		"Mode=HTML", 
		LAST);

	web_custom_request("backgroundCheckAction!doLoad.action_2", 
		"URL=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/backgroundCheckAction!doLoad.action?tabView=tab5", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab5", 
		"Snapshot=t45.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=tabView=tab5", 
		LAST);

	//web_add_cookie("__utma=219208603.1611413192.1254764994.1254779528.1254863963.4; DOMAIN=servicelive.plus1solutions.net");

	//web_add_cookie("__utmb=219208603.1.10.1254863963; DOMAIN=servicelive.plus1solutions.net");

	//web_add_cookie("__utmc=219208603; DOMAIN=servicelive.plus1solutions.net");

	web_url("home.html", 
		"URL=https://servicelive.plus1solutions.net/html/home.html", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://servicelive.plus1solutions.net/?parm1=SERV601790681&parm2={resourceId}&parm3=ZgWM3XdJOyOEs0XZ1KCvbvB4XNWR3JJgCBRwcLHvocKDx5zHuK6%2FmplijfuwygwT", 
		"Snapshot=t46.inf", 
		"Mode=HTML", 
		LAST);

	//web_add_cookie("s_sq=searsliveprovider%3D%2526pid%253DAddPro%2526pidt%253D1%2526oid%253Dhttps%25253A//198.179.148.51%25253A1743/ServiceLiveWebUtil/images/images_registration/common/spacer.gif%2526ot%253DIMAGE%2526oi%253D269; DOMAIN=198.179.148.51");

	web_reg_find("Text=ServiceLive [Service Pro Profile]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("backgroundCheckAction.action_2", 
		"Action=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/backgroundCheckAction.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab5", 
		"Snapshot=t47.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=performheckInd", "Value=", ENDITEM, 
		"Name=teamProfileDto.email", "Value=slprovideradmin@gmail.com", ENDITEM, 
		"Name=teamProfileDto.confirmEmail", "Value=slprovideradmin@gmail.com", ENDITEM, 
		"Name=teamProfileDto.emailAlt", "Value=", ENDITEM, 
		"Name=teamProfileDto.confirmEmailAlt", "Value=", ENDITEM, 
		"Name=action:backgroundCheckActiondoNext.x", "Value=24", ENDITEM, 
		"Name=action:backgroundCheckActiondoNext.y", "Value=0", ENDITEM, 
		LAST);

	web_custom_request("termsAction!doLoad.action", 
		"URL=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/termsAction!doLoad.action?tabView=tab6", 
		"Method=GET", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab6", 
		"Snapshot=t48.inf", 
		"Mode=HTML", 
		"EncType=application/x-www-form-urlencoded", 
		"Body=tabView=tab6", 
		LAST);

	////web_add_cookie(T"s_sq=searsliveprovider%3D%2526pid%253DAddPro%2526pidt%253D1%2526oid%253Dhttps%25253A//198.179.148.51%25253A1743/ServiceLiveWebUtil/images/images_registration/common/spacer.gif%2526ot%253DIMAGE%2526oi%253D475; DOMAIN=198.179.148.51");

	lr_end_transaction("BackgroundCheck",LR_AUTO);

/*	sprintf(filename,"C:\\LastUpdatedScripts\\Count.txt");

	if ((file_stream = fopen(filename, "a+")) == NULL )
	{
		  lr_error_message("Cannot open %s", filename);
		  return -1;
	}

	fprintf(file_stream,"%s\n",lr_eval_string("{resourceId}"));
	fclose(file_stream);  */
//}



	lr_start_transaction("Terms&C");

	web_reg_find("Text=ServiceLive [Provider Registration]", 
		LAST);

	web_reg_find("Text=ServiceLive : ", 
		LAST);

	web_submit_data("termsAction.action", 
		"Action=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/termsAction.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=https://198.179.148.51:1743/MarketFrontend/jsp/providerRegistration/serviceProAllTab.action?tabView=tab6", 
		"Snapshot=t49.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=termsDto.acceptTerms", "Value=1", ENDITEM, 
		"Name=action:termsActiondoSave.x", "Value=24", ENDITEM, 
		"Name=action:termsActiondoSave.y", "Value=7", ENDITEM, 
		LAST);

	lr_end_transaction("Terms&C",LR_AUTO);

	
	
	return 0;
}
