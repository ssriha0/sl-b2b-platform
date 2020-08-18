/**
 * 
 */
package com.newco.marketplace.utils;

/**
 * @author KSudhanshu
 *
 */
public class ActivityRegistryConstants {

	public static final String NOT_STARTED = "notstarted";
	public static final String COMPLETE = "complete";
	public static final String INCOMPLETE = "incomplete";

	public static final String BUSINESSINFO = "Business Information";
	public static final String WARRANTY = "Warranty & Policies";
	public static final String LICENSE = "Credentials";
	public static final String INSURANCE = "Insurance";
	public static final String TERMSANDCOND = "Terms & Conditions";
	
	public static final String RESOURCE_GENERALINFO = "General Information";
	public static final String RESOURCE_MARKETPLACE = "Marketplace Preferences";
	public static final String RESOURCE_SKILLS = "Skills";
	public static final String RESOURCE_CREDENTIALS = "Credentials";
	public static final String RESOURCE_BACKGROUNDCHECK = "Background Check";
	public static final String RESOURCE_TERMSANDCOND = "Terms & Conditions";
	
	public static final String USER_STATUS_NEW = "addUser";
	public static final String USER_STATUS_EDIT = "editUser";
	
	/**
	 * Added to Update Incomplete status.
	 * Fix for Sears00045965.
	 */
	public static final int ACTIVITY_STATUS_INCOMPLETE = 0;
	
	/* Activity Ids for permissions  */
	public final static int ACTIVITY_ID_PASSWORD_RESET_FOR_ALL_EXTERNAL_USERS = 40;
	//public final static int ACTIVITY_ID_PASSWORD_RESET_FOR_SL_ADMIN = 38;
	public final static int PERMISSION_MANAGE_PASSWORD_RESET = 31;
	public final static int ACTIVITY_ID_VIEW_CONDITIONAL_AUTO_ROUTING_RULES = 43;
	public final static int ACTIVITY_ID_MANAGE_CONDITIONAL_AUTO_ROUTING_RULES = 44;
	public final static int ACTIVITY_ID_MANAGE_AUTO_CLOSE_AND_PAY_RULES = 55;	
	public final static int ACTIVITY_ROLE_ID_ADMIN_VIEW_MEMBER_MANAGER = 96;
	public final static int ACTIVITY_ROLE_ID_ADMIN_EDIT_MEMBER_MANAGER = 98;
	public final static int ACTIVITY_ROLE_ID_BUYER_VIEW_MEMBER_MANAGER = 97;
	public final static int ACTIVITY_ROLE_ID_BUYER_EDIT_MEMBER_MANAGER = 99;
	public final static int ACTIVITY_ID_TIER_ROUTING = 49;
	public final static int ACTIVITY_ROLE_ID_BUYER_ROUTING_TIERS = 94;
	public final static int ACTIVITY_ROLE_ID_BUYER_ROUTING_RULES_VIEW = 89;
	public final static int ACTIVITY_ROLE_ID_BUYER_ROUTING_RULES_EDIT= 90; 
	public final static int ACTIVITY_ROLE_ID_INCREASE_SPEND_LIMIT = 106;
	public final static int ACTIVITY_ROLE_ID_MANAGE_AUTO_CLOSE_AND_PAY_RULES = 105;
	//R12_1
	public final static int ACTIVITY_ROLE_ID_HSR_MANAGE_AUTO_CLOSE_AND_PAY_RULES = 118;
	public final static int ACTIVITY_ID_HSR_MANAGE_AUTO_CLOSE_AND_PAY_RULES = 64;
	//SL-15642
	public final static int ACTIVITY_ROLE_ID_VIEW_ORDER_MANAGEMENT = 4;  
	//SL-18330
	public final static int ACTIVITY_ROLE_ID_PROVIDER_NAME_CORRECTION = 60;  // Provider Name change
	public final static int ACTIVITY_ROLE_ID_PROVIDER_ADMIN_NAME_CHANGE = 61;  // Provider Admin Name change
	
	//SL-20363 Need to Add UI for Forceful CAR Activation for buyer# 3000
	public final static int ACTIVITY_ROLE_ID_FORCEFUL_CAR_RULE_ACTIVATION = 62; // CAR Force Activation
	//changes for 20461 starts
	public final static int ACTIVITY_ROLE_ID_BUYER_ADMIN_NAME_CHANGE = 63;  // Buyer Admin Name change
	//changes for 20461 ends
	//SL20014 To provide buyer permission to view provider uploaded compliance documents
	public final static int ACTIVITY_ROLE_ID_BUYER_SPN_VIEWDOC_PERMISSION = 65;
	public final static String ACTIVITY_ROLE_ID_BUYER_SPN_VIEWDOC_PERMISSION_STRING = "65";
	//SL20014 end
}
