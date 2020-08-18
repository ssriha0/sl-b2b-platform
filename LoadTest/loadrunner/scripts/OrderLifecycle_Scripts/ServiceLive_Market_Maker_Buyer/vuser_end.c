vuser_end()
{
		/*logout*/
/* -------------------------------------------------------------------------------

   Transaction Title        : ServiceLive_MarketMakerBuyer_08_Logout

   Transaction Description  : Click on LogOut button

   Transaction Parameters   : SecureURL 

   Correlated Parameters    : None

 ------------------------------------------------------------------------------------ */
    
	web_reg_find("Text=Login","SaveCount=LogOut_Count",LAST);

	lr_think_time(atoi(lr_eval_string("{think_time}")));

	lr_start_transaction("ServiceLive_MarketMakerBuyer_08_Logout");

	web_url("doLogout.action", 
		"URL={SecureURL}MarketFrontend/doLogout.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t25.inf", 
		"Mode=HTML", 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=2730136077981.113", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=2730136077981.113?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={URL}MarketFrontend/homepage.action", 
		"Snapshot=t26.inf", 
		"Mode=HTML", 
		LAST);*/

	if((!strcmp(lr_eval_string(lr_eval_string("{LogOut_Count}")), "0")))
	{
  		lr_end_transaction("ServiceLive_MarketMakerBuyer_08_Logout", LR_FAIL);
        lr_error_message("Failed to load LogOut");
  		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
  	}
  	else
	{
		lr_end_transaction("ServiceLive_MarketMakerBuyer_08_Logout", LR_PASS);
		
    }

	return 0;
}