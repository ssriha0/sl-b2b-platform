/*************************************************************************************/
/*  Script: 	        ServiceLive_SPN_CreateNetworkAndCampaign			 	
/*										
/*  Description:        1.Launch Application                                
/*                 		2.Login using valid credentials (Buyer)		
/*						3.Click on create network tab				
/*						4.Enter General Information			
/*						5.Save the network
/*   					6.Click on create campaign tab					
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
