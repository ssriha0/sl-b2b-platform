/*********************************************************************************/
/*  Script Name:    SL_Market_Maker_Buyer_1.lrs                                  */
/*                                                                               */
/*  Description:    1.Launch Application                                         */
/*                 	2.Login using valid credentials								 */
/*					3.Click on WorkFlow monitor tab							     */
/*					4.Click on Claim Next Order link 							 */
/*					5.Select an Action and Click on Submit Notes				 */
/*				    6.Click on Un-Claim Order Button							 */
/*				    7.Click on LogOut											 */				
/*                  								                         	 */
/*                                                                               */
/*  Author:         Infosys              Date: 01-Jun-2009                       */
/*                                                                               */
/*  Comments:       None                                                         */
/*                                                                               */
/*  Change History: Draft Version                                                */
/*                                                                               */
/*********************************************************************************/

vuser_init()
{


	char *URL, *SecureURL, *think_time;
	// Env variables	
	int Counter;

	URL=lr_get_attrib_string("URL");
	lr_save_string(URL, "URL");
	SecureURL=lr_get_attrib_string("SecureURL");
	lr_save_string(SecureURL, "SecureURL");
	think_time=lr_get_attrib_string("think_time");
	lr_save_string(think_time, "think_time");



	// Error Handling	
	web_global_verification("Text/IC=Error Notification", "Fail=Found",  LAST);
	web_global_verification("Text/IC=ServletException", "Fail=Found",  LAST);
	web_global_verification("Text/IC=Generic System Error", "Fail=Found",  LAST);


/* -------------------------------------------------------------------------------

   Transaction Title        : ServiceLive_MarketMakerBuyer_01_Homepage

   Transaction Description  : Access the home page 

   Transaction Parameters   : URL 

   Correlated Parameters    : None

 ------------------------------------------------------------------------------------ */

	//web_reg_find("Text=Find providers","SaveCount=Homepage_Count",LAST);
	web_reg_find("Text=Visit ServiceLive.com","SaveCount=Homepage_Count",LAST);

	lr_think_time(atoi(lr_eval_string("{think_time}")));

    lr_start_transaction("ServiceLive_MarketMakerBuyer_01_Homepage");

	web_url("homepage.action", 
		"URL={URL}MarketFrontend/homepage.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t2.inf", 
		"Mode=HTML", 
		LAST);

	web_add_cookie("id=223e4f4cf20000d2||t=1254421052|et=730|cs=iq_9qofq; DOMAIN=fls.doubleclick.net");

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=7296072266346.515", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=7296072266346.515?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={URL}MarketFrontend/homepage.action", 
		"Snapshot=t3.inf", 
		"Mode=HTML", 
		LAST);*/

	if (atoi(lr_eval_string("{Homepage_Count}")) > 0)
	{ 
		lr_output_message("Homepage successful");
		lr_end_transaction("ServiceLive_MarketMakerBuyer_01_Homepage", LR_PASS);
	}
	else
	{ 
		lr_error_message("Homepage failed");
		lr_end_transaction("ServiceLive_MarketMakerBuyer_01_Homepage", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 


	
	//lr_think_time(20);

	/*web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=4479333428443.434", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=4479333428443.434?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={URL}MarketFrontend/homepage.action", 
		"Snapshot=t28.inf", 
		"Mode=HTML", 
		LAST);*/

	
	/*click on login	*/

	/*fw restictions*/

	//lr_think_time(66);
/* -------------------------------------------------------------------------------

   Transaction Title        : ServiceLive_MarketMakerBuyer_02_Loginpage

   Transaction Description  : Click on Login button 

   Transaction Parameters   : SecureURL 

   Correlated Parameters    : None

 ------------------------------------------------------------------------------------ */

	web_reg_find("Text=>Log In to ServiceLive","SaveCount=Loginpage_Count",LAST);

	/****** Loads the Login page  ******/

	 lr_think_time(atoi(lr_eval_string("{think_time}")));

	 lr_start_transaction("ServiceLive_MarketMakerBuyer_02_Loginpage");

	 web_url("loginAction.action", 
		"URL={SecureURL}MarketFrontend/loginAction.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={URL}MarketFrontend/homepage.action", 
		"Snapshot=t4.inf", 
		"Mode=HTML", 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=8822193733688.843", 
		"URL=https://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=8822193733688.843?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/loginAction.action", 
		"Snapshot=t5.inf", 
		"Mode=HTML", 
		LAST); */

	if (atoi(lr_eval_string("{Loginpage_Count}")) > 0)
	{ 
		lr_output_message("Loginpage successful");
		lr_end_transaction("ServiceLive_MarketMakerBuyer_02_Loginpage", LR_PASS);
	}
	else
	{ 
		lr_error_message("Loginpage failed");
		lr_end_transaction("ServiceLive_MarketMakerBuyer_02_Loginpage", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 

	
	web_reg_find("Text=Logout","SaveCount=Login_Count",LAST);

	/****** Entering the credentials  ******/
/* -------------------------------------------------------------------------------

   Transaction Title        : ServiceLive_MarketMakerBuyer_03_LoginCredentials

   Transaction Description  : Enter login credentials 

   Transaction Parameters   : SecureURL,UserName,password

   Correlated Parameters    : None

 ------------------------------------------------------------------------------------ */
	lr_think_time(atoi(lr_eval_string("{think_time}")));

	lr_start_transaction("ServiceLive_MarketMakerBuyer_03_LoginCredentials");


	web_submit_data("doLogin.action", 
		"Action={SecureURL}MarketFrontend/doLogin.action", 
		"Method=POST", 
		"EncType=multipart/form-data", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={SecureURL}MarketFrontend/loginAction.action", 
		"Snapshot=t6.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=username", "Value={username}", ENDITEM, 
		"Name=password", "Value={password}", ENDITEM, 
		"Name=__checkbox_rememberUserId", "Value=true", ENDITEM, 
		LAST);

	if (atoi(lr_eval_string("{Login_Count}")) > 0)
	{ 
		lr_output_message("Login successful");
		lr_end_transaction("ServiceLive_MarketMakerBuyer_03_LoginCredentials", LR_PASS);
	}
	else
	{ 
		lr_error_message("Login failed");
		lr_end_transaction("ServiceLive_MarketMakerBuyer_03_LoginCredentials", LR_FAIL);
		lr_exit(LR_EXIT_ITERATION_AND_CONTINUE, LR_PASS);
	} 
	
  
	return 0;
}
