/*************************************************************************************/
/*  Script: 	        ServiceLive_SPN_ApplyingForMembership			 	
/*										
/*  Description:        1.Launch Application 
/*                      2.Launch HomePage                               
/*                 		3.Login using valid credentials (provider)		
/*						4.Select the Invitation				
/*						5.Click on View Company Requirements linkn			
/*						6.Click on View Provider Requirements Link
/*   					7.Upload the Signature Document					
/*						7.Select the provider network
/*						8.Click on Update Results
/*				        9.Click on Save & Done button
/*					   10.Click on LogOut		
/*										
/*					                                    
/*                                                                          
/*  Author:         Infosys              Date: 08-Apr-2010                  
/*                                                                          
/*  Comments:       None                                                    
/*                                                                           
/*  Change History: Draft Version                                            
/*                                                                           
/*************************************************************************************/

vuser_init()
{

	char *loginURL, *think_time;

	// Error Handling	
	web_global_verification("Text/IC=Error Notification", "Fail=Found",  LAST);
	web_global_verification("Text/IC=ServletException", "Fail=Found",  LAST);
	web_global_verification("Text/IC=Generic System Error", "Fail=Found",  LAST);

	// Env variables	

	loginURL=lr_get_attrib_string("loginURL");
	lr_save_string(loginURL, "loginURL");
	think_time=lr_get_attrib_string("think_time");
	lr_save_string(think_time, "think_time");

	return 0;
}
