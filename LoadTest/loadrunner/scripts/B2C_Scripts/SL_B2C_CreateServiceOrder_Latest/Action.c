Action()
{

	web_reg_find("Text=ServiceLive - From Home Improvement to Repairs, Find a Service Provider Online", 
		LAST);


  /* -------------------------------------------------------------------------------
	Transaction Title        : ServiceLive_B2C_CreateServiceOrder_01_Homepage
	Transaction Description  : Access the home page
	Transaction Parameters   : loginURL
	Correlated Parameters    : None
  ---------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_B2C_CreateServiceOrder_01_Homepage");

	web_url("home", 
		"URL={loginURL}", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t1.inf", 
		"Mode=HTML", 
		LAST);

	////////web_add_cookie("id=cb2e8352400006d||t=1266521168|et=730|cs=7j3zrqmt; DOMAIN=fls.doubleclick.net");

	/*web_url("activityi;src=2077836;type=landi583;cat=homeo760;ord=1;num=3200087582504.14", 
		"URL=https://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=homeo760;ord=1;num=3200087582504.14?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}", 
		"Snapshot=t2.inf", 
		"Mode=HTML", 
		LAST);

	web_url("activityi;src=2077836;type=landi583;cat=servi018;ord=1;num=4740021705857.5205", 
		"URL=https://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi018;ord=1;num=4740021705857.5205?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}", 
		"Snapshot=t3.inf", 
		"Mode=HTML", 
		LAST);*/

	lr_end_transaction("ServiceLive_B2C_CreateServiceOrder_01_Homepage",LR_AUTO);

	lr_think_time(atoi(lr_eval_string("{think_time}")));

	/****** Loads the home page of the ServiceLive site  ******/

	web_reg_find("Text=ServiceLive - From Home Improvement to Repairs, Find a Service Provider Online", 
		LAST);

	/* -------------------------------------------------------------------------------
		Transaction Title        : ServiceLive_B2C_CreateServiceOrder_02_Loginpage
		Transaction Description  : Click on Login button to access Loginpage page
		Transaction Parameters   : loginURL
		Correlated Parameters    : None
	  ---------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_B2C_CreateServiceOrder_02_Loginpage");

	web_url("loginPage.action", 
		"URL={loginURL}loginPage.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}", 
		"Snapshot=t4.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("ServiceLive_B2C_CreateServiceOrder_02_Loginpage",LR_AUTO);

    lr_think_time(atoi(lr_eval_string("{think_time}")));


	web_reg_find("Text=ServiceLive - From Home Improvement to Repairs, Find a Service Provider Online", 
		LAST);

	/* -------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_B2C_CreateServiceOrder_03_Login
	   Transaction Description  : Enter credentials Access the home page
	   Transaction Parameters   : secureURL,userName,Password
	   Correlated Parameters    : None
	 ---------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_B2C_CreateServiceOrder_03_Login");

	web_submit_data("login.action", 
		"Action={secureURL}login.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t5.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=j_username", "Value={userName}", ENDITEM, 
		"Name=j_password", "Value={Password}", ENDITEM, 
		"Name=__checkbox_rememberLogin", "Value=true", ENDITEM, 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi018;ord=1;num=5703694283054.586", 
		"URL=https://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi018;ord=1;num=5703694283054.586?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}index.jsp", 
		"Snapshot=t6.inf", 
		"Mode=HTML", 
		LAST);

	web_url("activityi;src=2077836;type=landi583;cat=homeo760;ord=1;num=10621109759.980096", 
		"URL=https://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=homeo760;ord=1;num=10621109759.980096?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}index.jsp", 
		"Snapshot=t7.inf", 
		"Mode=HTML", 
		LAST);*/

	lr_end_transaction("ServiceLive_B2C_CreateServiceOrder_03_Login",LR_AUTO);

    lr_think_time(atoi(lr_eval_string("{think_time}")));

		web_reg_save_param("categoryId",
		"LB=<a href=\"javascript:selectCategory(",
		"RB=)\">",
		"Ord=ALL",
		LAST);

	/* -------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_B2C_CreateServiceOrder_04_Search
	   Transaction Description  : Enter Search Phrase and click on Search button
	   Transaction Parameters   : loginURL,searchPhrase
	   Correlated Parameters    : categoryId,categoryName,parentCategoryId
	 ---------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_B2C_CreateServiceOrder_04_Search");

	lr_start_sub_transaction("ServiceLive_B2C_CreateServiceOrder_04_Search_SearchPhrase","ServiceLive_B2C_CreateServiceOrder_04_Search");

    web_submit_data("searchAjaxAction.action", 
		"Action={loginURL}searchAjaxAction.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={loginURL}index.jsp", 
		"Snapshot=t8.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=searchPhrase", "Value={searchPhrase}", ENDITEM, 
		"Name=spellCheck", "Value=true", ENDITEM, 
		LAST);

	lr_end_sub_transaction("ServiceLive_B2C_CreateServiceOrder_04_Search_SearchPhrase",LR_AUTO);

	web_reg_find("Text=ServiceLive - From Home Improvement to Repairs, Find a Service Provider Online", 
		LAST);

			// Randomization for categoryId
	
	//Finding the number of values in category field 
	
		count=atoi( lr_eval_string("{categoryId_count}") );
	
		value=(rand() % count)+1;   
	
	//Taking randomized number and keeping that value in buffer 
	
		sprintf( random_value,
			"{categoryId_%d}",
			value );
	
	// Taking the value corresponding to randomized number and putting it in buffer 
		  sprintf( out_value,
			"%s",
			lr_eval_string(random_value) );

		  //Splitting the randomly selecting value using the delimiter and storing it in a variable
		  categoryIdPointer = (char *) strtok(out_value, delimiters); 

		  stringLength = strlen(categoryIdPointer);
          
		  for(i = 0; i < stringLength; i++) 
		 {
		  categoryId[i] = categoryIdPointer[i];
		 }
		  categoryId[stringLength] = '\0';

		  //Saving the result in a variable for later use
		  lr_save_string(categoryId,"categoryId");

		  //Splitting the randomly selected categoryName value using the delimiter and storing it in a variable
		  categoryNamePointer = (char *) strtok(NULL, delimiters); 

		  //Finding the lenght of categoryName value
		  stringLength = strlen(categoryNamePointer);

		  //Eliminating extra characters in categoryName
		  for(i = 0; i < stringLength - 3; i++) 
		 {
		  categoryName[i] = categoryNamePointer[i + 2];
		 }

		  categoryName[stringLength - 3] = '\0';

		  //Saving the result in a variable for later use
		  lr_save_string(categoryName,"categoryName");

		  //Splitting the randomly selected parentCategoryId value using the delimiter and storing it in a variable
           parentCategoryIdPointer = (char *) strtok(NULL, delimiters); 

		   //Finding the lenght of parentCategoryId value
		  stringLength = strlen(parentCategoryIdPointer);

		  //Eliminating extra characters in parentCategoryId
		  for(i = 0; i < stringLength - 1; i++) 
		 {
		  parentCategoryId[i] = parentCategoryIdPointer[i + 1];
	     }

		  parentCategoryId[stringLength - 1] = '\0';

		   //Saving the result in a variable for later use
		  lr_save_string(parentCategoryId,"parentCategoryId");


	web_reg_save_param("projectTypeId",
		"LB=<option value=\"",
		"RB=\">",
		"Ord=ALL",
		LAST);

	lr_start_sub_transaction("ServiceLive_B2C_CreateServiceOrder_04_Search_SearchPhraseResults","ServiceLive_B2C_CreateServiceOrder_04_Search");

	web_submit_data("selectCategoryAction.action", 
		"Action={loginURL}selectCategoryAction.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={loginURL}index.jsp", 
		"Snapshot=t9.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=orderName", "Value={searchPhrase}", ENDITEM, 
		"Name=categoryId", "Value={categoryId}", ENDITEM, 
		"Name=categoryName", "Value={categoryName}", ENDITEM, 
		"Name=parentCategoryId", "Value={parentCategoryId}", ENDITEM, 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi018;ord=1;num=502392689111.22625", 
		"URL=https://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi018;ord=1;num=502392689111.22625?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}orderDescription.action?actionname=orderDescription", 
		"Snapshot=t10.inf", 
		"Mode=HTML", 
		LAST);

	web_url("activityi;src=2077836;type=landi583;cat=homeo760;ord=1;num=7571621090413.619", 
		"URL=http://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=homeo760;ord=1;num=7571621090413.619?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}orderDescription.action?actionname=orderDescription", 
		"Snapshot=t11.inf", 
		"Mode=HTML", 
		LAST);*/

	web_url("getAttachments.action", 
		"URL={loginURL}getAttachments.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/xml", 
		"Referer={loginURL}orderDescription.action?actionname=orderDescription", 
		"Snapshot=t12.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_sub_transaction("ServiceLive_B2C_CreateServiceOrder_04_Search_SearchPhraseResults",LR_AUTO);

	lr_end_transaction("ServiceLive_B2C_CreateServiceOrder_04_Search",LR_AUTO);

		//Finding the number of values in projectTypeId field 

		count=atoi( lr_eval_string("{projectTypeId_count}") );

		count=count-48;

		value=(rand() % count)+1;   

	//Taking randomized number and keeping that value in buffer 

		sprintf( random_value,
			"{projectTypeId_%d}",
			value );

	// Taking the value corresponding to randomized number and putting it in buffer 
		  sprintf( out_value,
			"%s",
			lr_eval_string(random_value) );


		  lr_save_string(out_value,"projectTypeId");

		lr_think_time(atoi(lr_eval_string("{think_time}")));

	/* -------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_B2C_CreateServiceOrder_05_DescribeProject
	   Transaction Description  : Enter decription details of the project
	   Transaction Parameters   : loginURL,searchPhrase,startDate,endDate,zipCode
	   Correlated Parameters    : categoryId,categoryName,parentCategoryId
	 ---------------------------------------------------------------------------------- */

	lr_start_transaction("ServiceLive_B2C_CreateServiceOrder_05_DescribeProject");

	web_submit_data("reloadOrderQuestionsAjaxAction.action", 
		"Action={loginURL}reloadOrderQuestionsAjaxAction.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={loginURL}orderDescription.action?actionname=orderDescription", 
		"Snapshot=t13.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=categoryId", "Value={categoryId}", ENDITEM, 
		"Name=projectTypeId", "Value={projectTypeId}", ENDITEM, 
		LAST);

		web_reg_save_param("providerDetails",
		"LB=Provider(\"",
		"RB=);",
		"Ord=ALL",
		LAST);

	web_reg_find("Text=ServiceLive - From Home Improvement to Repairs, Find a Service Provider Online", 
		LAST);

	web_submit_data("saveOrderDescriptionAction.action", 
		"Action={loginURL}saveOrderDescriptionAction.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={loginURL}orderDescription.action?actionname=orderDescription", 
		"Snapshot=t14.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=categoryId", "Value={categoryId}", ENDITEM, 
		"Name=categoryName", "Value={categoryName}", ENDITEM, 
		"Name=parentCategoryId", "Value={parentCategoryId}", ENDITEM, 
		"Name=readOnlyZip", "Value=", ENDITEM, 
		"Name=warnOnCategoryChange", "Value=false", ENDITEM, 
		"Name=cookiesEnabled", "Value=", ENDITEM, 
		"Name=singleProvider", "Value=false", ENDITEM, 
		"Name=projectSearchPhrase", "Value={searchPhrase}", ENDITEM, 
		"Name=searchTerms", "Value={categoryName}", ENDITEM, 
		"Name=projectTypeId", "Value={projectTypeId}", ENDITEM, 
		"Name=orderName", "Value={searchPhrase}", ENDITEM, 
		"Name=zipCode", "Value={zipCode}", ENDITEM, 
		"Name=dateSpecification", "Value=dateRange", ENDITEM, 
		"Name=thisDate", "Value=", ENDITEM, 
		"Name=startTime", "Value=9:00am", ENDITEM, 
		"Name=endTime", "Value=10:00am", ENDITEM, 
		"Name=datetime", "Value=on", ENDITEM, 
		"Name=startDate", "Value={startDate}", ENDITEM, 
		"Name=endDate", "Value={endDate}", ENDITEM, 
		"Name=description", "Value=Install the garage door opener", ENDITEM, 
		LAST);

	lr_end_transaction("ServiceLive_B2C_CreateServiceOrder_05_DescribeProject",LR_AUTO);

    lr_think_time(atoi(lr_eval_string("{think_time}")));

		
		count=atoi( lr_eval_string("{providerDetails_count}") );
	
		value=(rand() % count)+1;   
	
	//Taking randomized number and keeping that value in buffer 
	
		sprintf( random_value,
			"{providerDetails_%d}",
			value );
	
	// Taking the value corresponding to randomized number and putting it in buffer 
		  sprintf( out_value,
			"%s",
			lr_eval_string(random_value) );

          //Splitting the randomly selected providerId value using the delimiter and storing it in a variable
		  providerIdPointer= (char *) strtok(out_value, delimiter); 

          //Finding the lenght of providerId value
          stringLength = strlen(providerIdPointer);

		  //Eliminating extra characters in providerId
		  for(i = 0; i < stringLength - 1; i++) 
	        {
			  providerId[i] = providerIdPointer[i];
		    }

		  providerId[stringLength - 1] = '\0';

          //Saving the result in a variable for later use
          lr_save_string(providerId,"providerId");

		  //Splitting the randomly selected providerName value using the delimiter and storing it in a variable
		  providerNamePointer = (char *) strtok(NULL, delimiter); 

		  //Finding the lenght of providerName value
		  stringLength = strlen(providerNamePointer);

		  //Eliminating extra characters in providerName
		  for(i = 0; i < stringLength - 1; i++) 
		  {
		   providerName[i] = providerNamePointer[i];
	      }

		  providerName[stringLength - 1] = '\0';

		  //Saving the result in a variable for later use
		  lr_save_string(providerName,"providerName");


	  //For selecting the second provider
		  do{

			  count=atoi( lr_eval_string("{providerDetails_count}") );

			  value=(rand() % count)+1;   


			  //Taking randomized number and keeping that value in buffer 
			  sprintf( random_value,
					   "{providerDetails_%d}",
					   value );

		  // Taking the value corresponding to randomized number and putting it in buffer 
			  sprintf( out_value,
					   "%s",
					   lr_eval_string(random_value) );

			  providerIdPointer= (char *) strtok(out_value, delimiter); // 108

			  stringLength = strlen(providerIdPointer);
		 
			   for(i = 0; i < stringLength - 1; i++) {
					providerId1[i] = providerIdPointer[i];
			   }
			   providerId[stringLength - 1] = '\0';
			
			   lr_save_string(providerId1,"providerId_1");
			
			}while(providerId==providerId1);
			
		   providerNamePointer = (char *) strtok(NULL, delimiter); 
		
			stringLength = strlen(providerNamePointer);
			for(i = 0; i < stringLength - 1; i++) {
				 providerName[i] = providerNamePointer[i];
			}
			providerName[stringLength - 1] = '\0';

	lr_save_string(providerName,"providerName_1");

	/* -----------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_B2C_CreateServiceOrder_06_SearchAndAddProviders
	   Transaction Description  : Search for providers and Add
	   Transaction Parameters   : loginURL,zipCode,searchPhrase
	   Correlated Parameters    : providerId,providerName,providerId_1,providerName_1,categoryId
	   -----------------------------------------------------------------------------------------*/

	lr_start_transaction("ServiceLive_B2C_CreateServiceOrder_06_SearchAndAddProviders");

	web_submit_data("addProviderAjaxAction.action", 
		"Action={loginURL}addProviderAjaxAction.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={loginURL}searchProvider.action?actionname=searchProvider&zipCode={zipCode}&projectName={searchPhrase}&categoryId={categoryId}&projectSearchPhrase={searchPhrase}", 
		"Snapshot=t15.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=providerId", "Value={providerId}", ENDITEM, 
		"Name=providerName", "Value={providerName}", ENDITEM, 
		LAST);

	//////web_add_cookie("s_sq=searssearsliveproviderdev%3D%2526pid%253DB2C.simple.findProviders%2526pidt%253D1%2526oid%253Djavascript%25253AaddProvider%252528%25252735421%252527%25252C%25252520%252527MIKE%25252520C%252527%252529%25253B%2526ot%253DA%2526oi%253D453; DOMAIN=151.149.116.100");

	web_submit_data("addProviderAjaxAction.action_2", 
		"Action={loginURL}addProviderAjaxAction.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={loginURL}searchProvider.action?actionname=searchProvider&zipCode={zipCode}&projectName={searchPhrase}&categoryId={categoryId}&projectSearchPhrase={searchPhrase}", 
		"Snapshot=t16.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=providerId", "Value={providerId_1}", ENDITEM, 
		"Name=providerName", "Value={providerName_1}", ENDITEM, 
		LAST);

	web_reg_find("Text=ServiceLive - From Home Improvement to Repairs, Find a Service Provider Online", 
		LAST);

	web_url("Continue", 
		"URL={loginURL}finishSelectingProviders.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}searchProvider.action?actionname=searchProvider&zipCode={zipCode}&projectName={searchPhrase}&categoryId={categoryId}&projectSearchPhrase={searchPhrase}", 
		"Snapshot=t18.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("ServiceLive_B2C_CreateServiceOrder_06_SearchAndAddProviders",LR_AUTO);

    web_reg_save_param("strutsToken",
        "LB=struts.token\" value=\"",
            "RB=\" />",
		"Ord=1",
		LAST);

	lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=ServiceLive - From Home Improvement to Repairs, Find a Service Provider Online", 
			LAST);

	/* --------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_B2C_CreateServiceOrder_07_ProjectDetails
	   Transaction Description  : Enter project details
	   Transaction Parameters   : secureURL
	   Correlated Parameters    : None
	   --------------------------------------------------------------------------------------*/

	lr_start_transaction("ServiceLive_B2C_CreateServiceOrder_07_ProjectDetails");

	web_submit_data("saveOrderDetailsAction.action", 
		"Action={secureURL}saveOrderDetailsAction.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}orderDetails.action?actionname=orderDetails", 
		"Snapshot=t19.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=laborPricingType", "Value=", ENDITEM, 
		"Name=materialsType", "Value=", ENDITEM, 
		"Name=isNamePrice", "Value=false", ENDITEM, 
		"Name=userType", "Value=returning", ENDITEM, 
		"Name=estimatedHourlyRate", "Value=0.0", ENDITEM, 
		"Name=singleProvider", "Value=false", ENDITEM, 
		"Name=postingDuration", "Value=14", ENDITEM, 
		"Name=ratePerHour", "Value=", ENDITEM, 
		"Name=hourlyRateMax", "Value=", ENDITEM, 
		"Name=fixedPriceAmount", "Value=", ENDITEM, 
		"Name=materialsMax", "Value=", ENDITEM, 
		"Name=serviceAddressId", "Value=8473", ENDITEM, 
		"Name=addNewAddress", "Value=", ENDITEM, 
		"Name=newAddressLine1", "Value=", ENDITEM, 
		"Name=newAddressLine2", "Value=", ENDITEM, 
		"Name=newAddressCity", "Value=", ENDITEM, 
		"Name=newAddressState", "Value=AL", ENDITEM, 
		"Name=newAddressZipCode", "Value=", ENDITEM, 
		"Name=serviceAddressLine1", "Value=", ENDITEM, 
		"Name=serviceAddressLine2", "Value=", ENDITEM, 
		"Name=serviceAddressCity", "Value=", ENDITEM, 
		"Name=serviceAddressState", "Value=AL", ENDITEM, 
		"Name=serviceAddressZipCode", "Value=", ENDITEM, 
		"Name=__checkbox_checkBoxHomeAddress", "Value=true", ENDITEM, 
		"Name=contactFirstName", "Value=", ENDITEM, 
		"Name=contactLastName", "Value=", ENDITEM, 
		"Name=contactPhoneAreaCode", "Value=", ENDITEM, 
		"Name=contactPhoneFirst3", "Value=", ENDITEM, 
		"Name=contactPhoneLast4", "Value=", ENDITEM, 
		"Name=alternatePhoneAreaCode", "Value=", ENDITEM, 
		"Name=alternatePhoneFirst3", "Value=", ENDITEM, 
		"Name=alternatePhoneLast4", "Value=", ENDITEM, 
		"Name=homeAddressLine1", "Value=", ENDITEM, 
		"Name=homeAddressLine2", "Value=", ENDITEM, 
		"Name=homeAddressCity", "Value=", ENDITEM, 
		"Name=homeAddressState", "Value=AL", ENDITEM, 
		"Name=homeAddressZipCode", "Value=", ENDITEM, 
		"Name=email", "Value=", ENDITEM, 
		"Name=confirmEmail", "Value=", ENDITEM, 
		"Name=password", "Value=", ENDITEM, 
		"Name=confirmPassword", "Value=", ENDITEM, 
		"Name=securityQuestionId", "Value=1", ENDITEM, 
		"Name=securityAnswer", "Value=", ENDITEM, 
		"Name=__checkbox_specialOffers", "Value=true", ENDITEM, 
		"Name=__checkbox_agreeToTermsAndConditions", "Value=true", ENDITEM, 
		"Name=__checkbox_agreeToPaymentServices", "Value=true", ENDITEM, 
		"Name=agreeToTermsAndConditionsReturning", "Value=true", ENDITEM, 
		"Name=__checkbox_agreeToTermsAndConditionsReturning", "Value=true", ENDITEM, 
		LAST);

	web_url("getAttachments.action_2", 
		"URL={secureURL}getAttachments.action", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/xml", 
		"Referer={secureURL}reviewOrderAction.action?actionname=reviewOrderAction", 
		"Snapshot=t20.inf", 
		"Mode=HTML", 
		LAST);

	lr_end_transaction("ServiceLive_B2C_CreateServiceOrder_07_ProjectDetails",LR_AUTO);

    lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=ServiceLive - From Home Improvement to Repairs, Find a Service Provider Online", 
		LAST);

	/* --------------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_B2C_CreateServiceOrder_08_ReviewOrder
	   Transaction Description  : Review the Order and Post it
	   Transaction Parameters   : secureURL
	   Correlated Parameters    : None
	   --------------------------------------------------------------------------------------*/

	lr_start_transaction("ServiceLive_B2C_CreateServiceOrder_08_ReviewOrder");

	web_submit_data("postOrderAction.action", 
		"Action={secureURL}postOrderAction.action", 
		"Method=POST", 
		"TargetFrame=", 
		"RecContentType=text/html", 
		"Referer={secureURL}reviewOrderAction.action?actionname=reviewOrderAction", 
		"Snapshot=t21.inf", 
		"Mode=HTML", 
		ITEMDATA, 
		"Name=struts.token.name", "Value=struts.token", ENDITEM, 
		"Name=struts.token", "Value={strutsToken}", ENDITEM, 
		"Name=uninsuredProviderCount", "Value=1", ENDITEM, 
		"Name=uninsuredProvider", "Value=true", ENDITEM, 
		"Name=insuranceCheckbox", "Value=true", ENDITEM, 
		"Name=__checkbox_insuranceCheckbox", "Value=true", ENDITEM, 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi018;ord=1;num=7836774442438.314", 
		"URL=https://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi018;ord=1;num=7836774442438.314?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}manageOrder.action?actionname=manageOrder&isFirstVisit=true", 
		"Snapshot=t22.inf", 
		"Mode=HTML", 
		LAST);

	web_url("activityi;src=2077836;type=conve041;cat=simpl049;ord=1;num=904685898251.1652", 
		"URL=https://fls.doubleclick.net/activityi;src=2077836;type=conve041;cat=simpl049;ord=1;num=904685898251.1652?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={secureURL}manageOrder.action?actionname=manageOrder&isFirstVisit=true", 
		"Snapshot=t23.inf", 
		"Mode=HTML", 
		LAST);*/

	lr_end_transaction("ServiceLive_B2C_CreateServiceOrder_08_ReviewOrder",LR_AUTO);

    lr_think_time(atoi(lr_eval_string("{think_time}")));

	web_reg_find("Text=ServiceLive - From Home Improvement to Repairs, Find a Service Provider Online", 
		LAST);

	/* -------------------------------------------------------------------------------
	   Transaction Title        : ServiceLive_B2C_CreateServiceOrder_09_LogOut
	   Transaction Description  : Enter credentials Access the home page
	   Transaction Parameters   : secureURL
	   Correlated Parameters    : None
	 ---------------------------------------------------------------------------------- */
	lr_start_transaction("ServiceLive_B2C_CreateServiceOrder_09_LogOut");

	web_url("j_spring_security_logout", 
		"URL={secureURL}j_spring_security_logout", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer=", 
		"Snapshot=t24.inf", 
		"Mode=HTML", 
		LAST);

/*	web_url("activityi;src=2077836;type=landi583;cat=servi018;ord=1;num=8005985886135.245", 
		"URL=https://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi018;ord=1;num=8005985886135.245?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}logout.action", 
		"Snapshot=t25.inf", 
		"Mode=HTML", 
		LAST);

	web_url("activityi;src=2077836;type=landi583;cat=homeo760;ord=1;num=6983805315018.383", 
		"URL=https://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=homeo760;ord=1;num=6983805315018.383?", 
		"TargetFrame=", 
		"Resource=0", 
		"RecContentType=text/html", 
		"Referer={loginURL}logout.action", 
		"Snapshot=t26.inf", 
		"Mode=HTML", 
		LAST); */

	lr_end_transaction("ServiceLive_B2C_CreateServiceOrder_09_LogOut",LR_AUTO);

	return 0;
}