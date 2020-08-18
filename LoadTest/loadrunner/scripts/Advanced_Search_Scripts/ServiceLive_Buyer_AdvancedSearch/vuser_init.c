/*************************************************************************************/
/*  Script: 	        ServiceLive_Buyer_AdvanceSearch
/*										
/*  Description:        1.Launch Application                                
/*                 		2.Login using valid credentials (Buyer)		
/*						3.Click on Service Order Monitor tab				
/*						4.Click on Search Tab			
/*						5.Enter Search Criteria and Click on Search
/*                      6.Click on LogOut		
/*										
/*					                                    
/*                                                                          
/*  Author:         Infosys              Date: 22-Apr-2010                  
/*                                                                          
/*  Comments:       None                                                    
/*                                                                           
/*  Change History: Draft Version                                            
/*                                                                           
/*************************************************************************************/

vuser_init()
{

	char *loginURL, *secureURL, *think_time;

	// Error Handling	
	web_global_verification("Text/IC=Error Notification", "Fail=Found",  LAST);
	web_global_verification("Text/IC=ServletException", "Fail=Found",  LAST);
	web_global_verification("Text/IC=Generic System Error", "Fail=Found",  LAST);

	// Env variables	

	loginURL=lr_get_attrib_string("loginURL");
	lr_save_string(loginURL, "loginURL");
	secureURL=lr_get_attrib_string("secureURL");
	lr_save_string(secureURL, "secureURL");
	think_time=lr_get_attrib_string("think_time");
	lr_save_string(think_time, "think_time");

	return 0;
}
