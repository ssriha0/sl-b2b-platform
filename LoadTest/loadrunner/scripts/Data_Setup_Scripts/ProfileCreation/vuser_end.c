vuser_end()
{
	lr_start_transaction("LogOut");

	web_reg_find("Text=ServiceLive : Home Improvement & Repair", 
		LAST);

	web_url("Logout", 
		"URL=https://198.179.148.51:1743/MarketFrontend/doLogout.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t51.inf", 
		"Mode=HTML", 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=3717505721981.112", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=3717505721981.112?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=http://198.179.148.51:1780/MarketFrontend/homepage.action", 
		"Snapshot=t52.inf", 
		"Mode=HTML", 
		LAST);*/

	lr_end_transaction("LogOut",LR_AUTO);


	return 0;
}
