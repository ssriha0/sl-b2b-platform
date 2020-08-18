/*************************************************************************************/
/*  Script: 	        SL_Provider_Completion.usr			 	
/*										
/*  Description:        1.Launch Application                            
/*						2.Click on Login to  open Login Page
/*						3.Login using valid credentials (Provider User)
/*						4.Click on Today Link
/*						5. Filter by Active
/*						6.Click on SO order with active
/*						7.Enter details of SO order
/*						8.Submit SO Order
/*						9.Click on LogOut	
/*										
/*					                                    
/*                                                                          
/*  Author:         Infosys              Date: 11-Aug-2009                  
/*                                                                          
/*  Comments:       None                                                    
/*                                                                           
/*  Change History: Draft Version                                            
/*                                                                           
/*********************************************************************************/

vuser_init()
{
	char *URL,*URL1,*think_time;
	

	URL1=lr_get_attrib_string("URL1");
	URL=lr_get_attrib_string("URL");
	think_time=lr_get_attrib_string("think_time");


	lr_save_string(URL1,"URL1");
	lr_save_string(URL,"URL");    
	lr_save_string(think_time, "think_time");

	return 0;
}
