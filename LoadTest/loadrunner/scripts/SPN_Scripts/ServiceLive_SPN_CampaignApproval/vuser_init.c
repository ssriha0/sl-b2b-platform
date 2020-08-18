/*************************************************************************************/
/*  Script: 	        ServiceLive_SPN_CampaignApproval			 	
/*										
/*  Description:        1.Launch Application
/*                      2.Click on Login button          
/*                 		3.Login using valid credentials (Buyer)		
/*						4.Click on buyer tab				
/*						5.Enter BuyerId and click on search button			
/*						6.Click on Take Action link and adopt user
/*   					7.Go to Administrator --> SPN Approve Campaign					
/*						8.Login using valid credentials
/*						9.Click on Campaign Monitor
/*				       10.Select Campaign and Approve the campaign
/*					   11.Click on LogOut		
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

	char *loginURL, *secureURL,*think_time;

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
