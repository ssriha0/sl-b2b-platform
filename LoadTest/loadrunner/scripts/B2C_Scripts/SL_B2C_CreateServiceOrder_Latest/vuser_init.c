/*********************************************************************************/
/*  Script: 	        SL_B2C_CreateServiceOrder.usr			 	
/*										
/*  Description:        1.Launch Application                                
/*                 		2.Login using valid credentials (Provider User)		
/*						3.Search for a service				
/*						4.Describe the project
/*						5.Enter Project Details
/*   					6.Add providers for the service
/*						7.Review the order and post it
/*						8.Click on LogOut		
/*					                                    
/*                                                                          
/*  Author:         Infosys              Date: 22-Feb-2010                  
/*                                                                          
/*  Comments:       None                                                    
/*                                                                           
/*  Change History: Draft Version                                            
/*                                                                           
/*********************************************************************************/

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

