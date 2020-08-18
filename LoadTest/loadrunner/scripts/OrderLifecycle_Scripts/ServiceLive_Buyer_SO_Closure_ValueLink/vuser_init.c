/*********************************************************************************/
/*  Script Name:    SL_Buyer_SO_Closure.lrs                                      */
/*                                                                               */
/*  Description:    1.Launch Application                                         */
/*                 	2.Login using valid credentials (Buyer User)				 */
/*					3.Click on Service Order Monitor tab					     */
/*					4.Select from Drop Down List as completed					 */
/*                  5.Sort orders based on Time									 */
/*					6.Click on Close Order and Pay Link     					 */
/*					7.Click on Close Order and Pay button        				 */
/*				    8.Click on LogOut											 */				
/*                  								                         	 */
/*                                                                               */
/*  Author:         Infosys              Date: 21-Dec-2009                       */
/*                                                                               */
/*  Comments:       None                                                         */
/*                                                                               */
/*  Change History: Code Changed                                                 */
/*                                                                               */
/*********************************************************************************/

vuser_init()
{

	char *loginURL, *secureURL, *think_time;

// Env variables	

	loginURL=lr_get_attrib_string("loginURL");
	lr_save_string(loginURL, "loginURL");
	secureURL=lr_get_attrib_string("secureURL");
	lr_save_string(secureURL, "secureURL"); 
	think_time=lr_get_attrib_string("think_time");
	lr_save_string(think_time, "thinkTime");

	return 0;
}
