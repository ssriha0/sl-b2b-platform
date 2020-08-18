/*************************************************************************************/
/*  Script: 	        SL_Provider_Acceptance.usr			 	
/*										
/*  Description:        1.Launch Application                                
/*                 		2.Login using valid credentials (Provider User)		
/*						3.Click on WorkFlow monitor tab				
/*						4.Click on Recived Tab 			
/*						5.Click on SO(Loads the order page) 					
/*   					6.Click on View Details to Accept link					
/*						7.Click on Accept SO quick link				
/*						8.Click on terms Accept button			
/*						9.Click on LogOut		
/*										
/*					                                    
/*                                                                          
/*  Author:         Infosys              Date: 01-Jun-2009                  
/*                                                                          
/*  Comments:       None                                                    
/*                                                                           
/*  Change History: Draft Version                                            
/*                                                                           
/*********************************************************************************/

vuser_init()
{

	char *URL, *SecureURL, *think_time;

	// Error Handling	
	web_global_verification("Text/IC=Error Notification", "Fail=Found",  LAST);
	web_global_verification("Text/IC=ServletException", "Fail=Found",  LAST);
	web_global_verification("Text/IC=Generic System Error", "Fail=Found",  LAST);

	// Env variables	
    
	URL=lr_get_attrib_string("URL");
	lr_save_string(URL, "URL");
	SecureURL=lr_get_attrib_string("SecureURL");
	lr_save_string(SecureURL, "SecureURL");
	think_time=lr_get_attrib_string("think_time");
	lr_save_string(think_time, "think_time");

	return 0;
}

