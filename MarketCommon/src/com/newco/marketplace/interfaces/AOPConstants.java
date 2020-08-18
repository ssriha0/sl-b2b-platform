/**
 * 
 */
package com.newco.marketplace.interfaces;

/**
 * @author schavda
 *
 */
public class AOPConstants {

	public static final String AOP_PARAMS_SEPARATATOR = "|";

	//AOP Advice Constants
    public static final String AOP_ADVICE_ALERT = "Alert";
    public static final String AOP_ADVICE_LOGGING = "Logging";
    public static final String AOP_ADVICE_CACHING = "Caching";
    
    public static final String AOP_METHOD_NAME = "METHOD_NAME";
    public static final String AOP_AUTOACH = "AUTOACH";
    public static final String AOP_TEMPLATE_ID = "TEMPLATE_ID";
    
    public static final String AOP_CACHING_EVENT_ID = "CACHING_EVENT_ID";
    public static final String AOP_CACHING_EVENT_CLASS = "CACHING_EVENT_CLASS";
    
    public static final String AOP_LOGGING_NEWVAL = "AOP_LOGGING_NEWVAL";
    
    public static final String AOP_SO_ID = "SO_ID";
    public static final String AOP_CLOSED_SO_ID = "CLOSED_SO_ID";
    public static final String AOP_BUYER_ID = "BUYER_ID";
    public static final String AOP_PROVIDER_ID = "PROVIDER_ID";
    public static final String AOP_RESOURCE_ID = "RESOURCE_ID";
    
    public static final String AOP_SPEND_LIMIT_FROM = "SPEND_LIMIT_FROM";
    public static final String AOP_SPEND_LIMIT_TO_LABOR = "SPEND_LIMIT_TO_LABOR";
    public static final String AOP_SPEND_LIMIT_TO_PARTS= "SPEND_LIMIT_TO_PARTS";
    public static final String AOP_SPEND_LIMIT_REASON = "SPEND_LIMIT_REASON";
    public static final String AOP_TOTAL_SPEND_LIMIT = "TOTAL_SPEND_LIMIT";
    public static final String AOP_SPEND_LIMIT_LABOR = "SPEND_LIMIT_LABOR";
    public static final String AOP_SPEND_LIMIT = "SPEND_LIMIT";
    public static final String AOP_SPEND_LIMIT_PARTS= "SPEND_LIMIT_PARTS";
    
    
    public static final String AOP_SCHEDULE_FROM = "SCHEDULE_FROM";
    public static final String AOP_SCHEDULE_TO = "SCHEDULE_TO";
    
    public static final String AOP_SERVICE_ORDER = "SERVICE_ORDER";    
    
    public static final String AOP_PROBLEM_REPORT_DATE = "SO_PROBLEM_REPORT_DT";
    public static final String AOP_PROBLEM_DESC = "PROBLEM_DESC";
    public static final String AOP_PROBLEM_DETAILS = "PROBLEM_DETAILS";
    public static final String AOP_COMMENTS = "COMMENT";
    public static final String AOP_NOTE = "NOTE";
    
    public static final String AOP_BUYER_RESOURCE_ID = "BUYER_RESOURCE_ID";
    public static final String AOP_VENDOR_RESOURCE_ID = "VENDOR_RESOURCE_ID";
    public static final String AOP_COMPANY_ID = "COMPANY_ID";
    
    public static final String AOP_ACCEPTED_VENDOR_RESOURCE_FNAME = "ACCEPTED_VENDOR_RESOURCE_FNAME";
    public static final String AOP_ACCEPTED_VENDOR_RESOURCE_LNAME = "ACCEPTED_VENDOR_RESOURCE_LNAME";
    public static final String AOP_PROVIDER_FIRST_NAME = "PROVIDERFIRSTNAME";
    public static final String AOP_PROVIDER_LAST_NAME = "PROVIDERLASTNAME";
    public static final String AOP_VENDOR_RESOURCE_LIST = "VENDOR_RESOURCE_LIST";
    public static final String AOP_CANCEL_AMT = "CANCEL_AMT";
    public static final String AOP_PROVIDER_LIST = "PROVIDER_LIST";
    public static final String AOP_PROVIDER_LIST_COUNT = "PROVIDER_LIST_COUNT";
    
    public static final String AOP_REJECT_REASON_ID = "REJECT_REASON_ID";
    public static final String AOP_REJECT_RESPONSE_ID = "REJECT_RESPONSE_ID";
    public static final String AOP_MODIFIED_BY = "MODIFIED_BY";
    public static final String AOP_REJECT_REASON_DESC = "REJECT_REASON_DESC";
    public static final String AOP_TRANSACTION_VO = "TRANSACTION_VO";
    public static final String AOP_TRANSFERFUNDS_VO = "TRANSFERFUNDS_VO";
    public static final String AOP_FUND_VO = "FUND_VO";
    public static final String AOP_USER_NAME = "USER_NAME";
    public static final String AOP_USERNAME = "USERNAME";
    public static final String AOP_USER_FIRST_NAME = "USER_FIRST_NAME";
    public static final String AOP_USER_LAST_NAME = "USER_LAST_NAME";
    public static final String AOP_USER_ID = "USER_ID";
    public static final String AOP_USERID = "USERID";
	public static final String AOP_SECURITY_CONTEXT = "SECURITY_CONTEXT";
    public static final String AOP_COMPANY_NAME = "COMPANY_NAME";
    public static final String AOP_USER_PHONE_NUMBER = "USER_PHONE_NUMBER";
    public static final String AOP_USER_EMAIL = "USER_EMAIL";
    public static final String AOP_ALT_USER_EMAIL = "ALT_USER_EMAIL";
    public static final String AOP_SO_CANCEL_DATE = "SO_CANCEL_DATE";
    public static final String AOP_SO_ACCEPTED_DATE = "SO_ACCEPTED_DATE";

    public static final String AOP_USER = "USER";
    public static final String AOP_DEEPLINK = "WEBSITEURL";
   
    public static final String AOP_ACTION_ID = "ACTION_ID";
 //   public static final String AOP_AOP_ACTION_ID = "AOP_ACTION_ID";
    public static final String AOP_ACTION_NAME = "ACTION_NAME";
    public static final String AOP_STATUS_ID = "STATUS_ID";
    public static final String AOP_SUBSTATUS_ID = "SUBSTATUS_ID";
    public static final String AOP_SUBSTATUS_DESC = "SUBSTATUS_DESC";
    public static final String AOP_STATUS_DESC = "STATUS_DESC";
    public static final String AOP_BUYER_SUBSTATUS_DESC = "BUYER_SUBSTATUS_DESC";
    public static final String AOP_BUYER_SUBSTATUS_ASSOC = "BUYER_SUBSTATUS_ASSOC";
    public static final String AOP_ASSURANT_COMMENTS = "COMMENTS";
    public static final String AOP_COMMENT = "COMMENT";
    public static final String AOP_ROLE_TYPE = "ROLE_TYPE";
    public static final String AOP_ROLE = "ROLE";
    public static final String AOP_PARTS_FINALPRICE = "PARTS_FINAL_PRICE";
    public static final String AOP_LABOR_FINALPRICE = "LABOR_FINAL_PRICE";
    public static final String AOP_FINALPRICE_TOTAL = "FINAL_PRICE";
    public static final String AOP_SO_CLOSE_DATE = "SO_CLOSED_DATE";
    public static final String AOP_COMPLETION_DATE = "SO_COMPLETION_DATE";
    public static final String AOP_RES_DESCR = "SO_RESOLUTION";
    
    public static final String AOP_UPSOLD_TOTAL_PRICE_DISPLAY = "Total Addons (Sell) is $";
    public static final String AOP_UPSOLD_TOTAL_PRICE = "UPSOLD_TOTAL_PRICE";
    
    public static final String AOP_CONDITIONAL_CHANGE_START_TIME = "SCHEDULE_START_TIME";
    public static final String AOP_CONDITIONAL_CHANGE_END_TIME = "SCHEDULE_END_TIME";
    public static final String AOP_CONDITIONAL_EXPIRATION_DATE = "SCHEDULE_EXPIRATION_DATE";
    
    public static final String AOP_NEW_START_DATE= "NEW_START_DATE";
    public static final String AOP_OLD_START_DATE= "OLD_START_DATE";
    public static final String AOP_NEW_END_DATE= "NEW_END_DATE";
    public static final String AOP_OLD_END_DATE= "OLD_END_DATE";
    public static final String AOP_NEW_START_TIME= "NEW_START_TIME";
    public static final String AOP_OLD_START_TIME= "OLD_START_TIME";
    public static final String AOP_NEW_END_TIME= "NEW_END_TIME";
    public static final String AOP_OLD_END_TIME= "OLD_END_TIME";
    public static final String AOP_CREATED_BY_NAME= "CREATED_BY_NAME";
    public static final String AOP_PROVIDER_RESP_ID= "PROVIDER_RESP_ID";
    public static final String AOP_RESPONSE_REASON_ID= "RESPONSE_REASON_ID";
    public static final String AOP_SERVICE_DATE1= "SERVICE_DATE1";
    public static final String AOP_SERVICE_DATE2= "SERVICE_DATE2";
    public static final String AOP_RESCHEDULE_SERVICE_DATE = "RESCHEDULE_SERVICE_DATE";
    public static final String AOP_RESCHEDULE_SERVICE_TIME = "RESCHEDULE_SERVICE_TIME";
    public static final String AOP_NOTE_TYPE= "NOTE_TYPE";
    public static final String AOP_SUPPORT_NOTE_DATE = "AOP_SUPPORT_NOTE_DATE";
   	public static final String AOP_NOTE_SUBJECT= "NOTE_SUBJECT";
    public static final String AOP_RESPONSE_TO_RESCHEDULE = "RESPONSE_TO_RESCHEDULE";
    public static final String AOP_SO_ROUTED_DATE = "SO_ROUTED_DATE";
    public static final String AOP_SO_TIER_ID = "TIER_ID";
    public static final String AOP_SO_SPN_ID = "SPN_ID";
    public static final String AOP_SO_SPN_NAME = "SPN_NAME";
    public static final String AOP_TIER_ROUTING_REASON = "TIER_ROUTING_REASON";
    public static final String AOP_TIER_OVERFLOW = "TIER_OVERFLOW";
    public static final String AOP_TIER_DASH = "TIER_DASH";
    public static final String AOP_TIER_ROUTING_REASON_DESC = "TIER_ROUTING_REASON_DESC";
    public static final String AOP_TIER_LINE_BREAK = "TIER_LINE_BREAK";
    public static final String AOP_TIER_SPN_TITLE = "TIER_SPN_TITLE";
    public static final String AOP_TIER_TITILE = "TIER_TITLE";
    public static final String AOP_TIER_PRIORITY_TITLE = "TIER_PRIORITY_TITLE";
    public static final String AOP_TIER_REASON_TITLE = "TIER_REASON_TITLE";
    public static final String AOP_TIER_SEP = "TIER_SEP";
    
	public static final String ALERT_TO = "ALERT_TO";
	public static final String ALERT_CC = "ALERT_CC";
	public static final String ALERT_BCC = "ALERT_BCC";
	public static final String SKIP_ALERT = "SKIP_ALERT";
	public static final String SKIP_FTP_ALERT = "SKIP_FTP_ALERT";
	public static final String SKIP_LOGGING = "SKIP_LOGGING";
	
	public static final String AOP_REASON_CODE = "REASON_CODE";
	public static final String AOP_PROVIDER_COMMENT = "PROVIDER_COMMENT";
	public static final String AOP_SO_RELEASE_DATE = "SO_RELEASE_DATE";

	public static final String AOP_SURVEY_RATING_SCORE = "OVERALL_SCORE";
	public static final String AOP_SURVEY_RATING_COMMENT = "SURVEY_RATING_COMMENT";
	
	public static final String AOP_SO_CREATED = "CREATED";
	public static final String AOP_DATE = "DATE";
	public static final String AOP_ORIGINAL_DATE = "ORIGINAL_DATE";
	public static final String AOP_TIME = "TIME";
	public static final String AOP_SO_EDITED = "EDITED";
	public static final String AOP_NOT_APPLICABLE = "N/A";
	public static final String AOP_SO_EXPIRED_DATE = "SO_EXPIRED_DATE";
	public static final String AOP_CONDITIONAL_OFFER_EXPIRED_DATE = "CONDITIONAL_OFFER_EXPIRED_DATE";
	
	// For Route Service Order
	public static final String AOP_SO_MAIN_SERVICE_CATEGORY = "SO_MAIN_SERVICE_CATEGORY";
	public static final String AOP_SERVICE_START_TIME = "SERVICE_START_TIME";
	public static final String AOP_SERVICE_END_TIME = "SERVICE_END_TIME";
	public static final String AOP_SO_TITLE = "SO_TITLE";
	public static final String AOP_SO_DESC = "SO_DESC";
	public static final String AOP_SO_SERVICE_CITY = "SO_SERVICE_CITY";
	public static final String AOP_SO_SERVICE_STATE = "SO_SERVICE_STATE";
	public static final String AOP_SO_SERVICE_ZIP = "SO_SERVICE_ZIP";
	public static final String AOP_SO_SERVICE_LOCATION_TIMEZONE = "SO_SERVICE_LOC_TIMEZONE";
	public static final String AOP_SO_REASON = "SO_REASON";
	
	public static final String AOP_SO_MODIFIED_DATE="SO_MODIFIED_DATE";
	
	// For grouped Order
	public static final String AOP_SO_GROUP_ID = "SO_GROUP_ID";
	// this constant is used to refer aop_action_id to pass to get alert Dispositon query
    public static final String AOP_AOP_ACTION_ID = "AOP_AOP_ACTION_ID";
    public static final String AOP_GROUP_SERVICE_ORDER = "GROUP_SERVICE_ORDER";  
    public static final String AOP_SO_GROUP_CHILDREN = "SO_GROUP_CHILDREN";
    public static final String AOP_SO_GROUP_ROUTED_DATE = "SO_GROUP_ROUTED_DATE";
    
    public static final String AOP_SO_GROUP_MAIN_SERVICE_CATEGORY = "SO_GROUP_MAIN_SERVICE_CATEGORY";
	public static final String AOP_GROUP_SERVICE_START_TIME = "GROUP_SERVICE_START_TIME";
	public static final String AOP_GROUP_SERVICE_END_TIME = "GROUP_SERVICE_END_TIME";
	public static final String AOP_GROUP_SO_TITLE = "GROUP_SO_TITLE";
	public static final String AOP_GROUP_SO_SERVICE_CITY = "GROUP_SO_SERVICE_CITY";
	public static final String AOP_GROUP_SO_SERVICE_STATE = "GROUP_SO_SERVICE_STATE";
	public static final String AOP_GROUP_SO_SERVICE_ZIP = "GROUP_SO_SERVICE_ZIP";
	public static final String AOP_GROUP_SO_SERVICE_LOCATION_TIMEZONE = "GROUP_SO_SERVICE_LOCATION_TIMEZONE";
	public static final String AOP_GROUP_SERVICE_DATE1= "GROUP_SERVICE_DATE1";
    public static final String AOP_GROUP_SERVICE_DATE2= "GROUP_SERVICE_DATE2";
    
    public static final String AOP_GROUP_SPEND_LIMIT_TO_LABOR = "GROUP_SPEND_LIMIT_TO_LABOR";
    public static final String AOP_GROUP_SPEND_LIMIT_TO_PARTS= "GROUP_SPEND_LIMIT_TO_PARTS";
    
    public static final int AOP_ASSURANT_EMAIL_TEMPLATE_ID = 233;
    public static final int AOP_ASSURANT_FTP_TEMPLATE_ID = 227;
    public static final int AOP_ASSURANT_INCIDENT_RESPONSE_FTP_TEMPLATE_ID = 279;
    public static final int AOP_RELEASE_SO_ACTIVE_BUYER_TEMPLATE_ID = 66;
    public static final int AOP_RELEASE_SO_ACTIVE_PROV_TEMPLATE_ID = 251;
    public static final int AOP_RELEASE_SO_ACCEPT_TEMPLATE_ID = 64;
    public static final int AOP_RELEASE_SO_PROB_TEMPLATE_ID = 91;
    public static final int AOP_REJECT_SO_TEMPLATE_ID = 39;
    public static final int AOP_RELEASE_REROUTE_SO_TEMPLATE_ID = 255;
    public static final int AOP_DELETE_DRAFT_SO_TEMPLATE_ID = 256;
    public static final int AOP_CHANGE_OF_SCOPE_SO_TEMPLATE_ID = 210;
    
	public static final String  AOP_AMOUNT = "AMOUNT";
	public static final String  AOP_FIRSTNAME = "FIRSTNAME";
	public static final String  AOP_LASTNAME = "LASTNAME";
	public static final String  AOP_BUYERFIRSTNAME = "BUYERFIRSTNAME";
	public static final String  AOP_BUYERLASTNAME = "BUYERLASTNAME";
	public static final String  AOP_PROVIDERFIRSTNAME = "PROVIDERFIRSTNAME";
	public static final String  AOP_PROVIDERLASTNAME = "PROVIDERLASTNAME";
	
    public static final String AOP_ROUTE_SO_PROVIDER_PROMO_CONTENT = "processRouteSOProvider";
    public static final String AOP_REROUTE_SO_PROVIDER_PROMO_CONTENT = "processReRouteSOProvider";
    
    public static final String AOP_REGISTRATION_DATE = "REGISTRATION_DATE";
    public static final String AOP_REMOVAL_DATE = "REMOVAL_DATE";
    public static final String AOP_REGISTRATION_VO = "REGISTRATION_VO";

    
    public static final String AOP_METHOD_SEND_FORGOT_USERMAIL = "sendForgotUsernameMail";
    public static final String AOP_METHOD_VALIDATE_ANS = "validateAns";
    public static final String AOP_METHOD_RESET_PASSWORD = "resetPassword";
    public static final String AOP_METHOD_SAVE_BUYER_REG = "saveBuyerRegistration";
    public static final String AOP_USER_PASSWORD = "PASSWORD";
    public static final String AOP_RESET_DATE = "RESET_DATE";
    public static final String AOP_BUYERUSERNAME = "BUYERUSERNAME";
    public static final String AOP_LEDGER_TRANID_POST = "LEDGER_TRANID_POST";
    public static final String AOP_TRANS_AMOUNT_POST = "TRANS_AMOUNT_POST";
    public static final String AOP_LEDGER_TRANID_RES = "LEDGER_TRANID_RES";
    public static final String AOP_TRANS_AMOUNT_RES = "TRANS_AMOUNT_RES";
    public static final String AOP_CONTACT_VO = "AOP_CONTACT_VO";
    public static final String AOP_CONTACT = "CONTACT";
    public static final String AOP_ACCOUNT_ID = "ACCOUNT_ID";
    public static final String AOP_DATE_OF_SERVICE = "DATE_OF_SERVICE";
    public static final String AOP_CURRENT_DATE = "CURRENT_DATE";
    public static final String AOP_SO_LIST = "SO_LIST";
	public static final String AOP_SEND_MAIL = "MAIL_TO_SEND";

    /** adding extra parameters for finance related mails **/
    public static final String AOP_LEDGER_TRANID = "LEDGER_TRANID";
    public static final String AOP_TRANS_AMOUNT = "TRANS_AMOUNT";
	
    
    public static final String AOP_COUNTER_OFFER_DETAILS = "COUNTER_OFFER_DETAILS";
    public static final String AOP_COMPANYNAME = "COMPANYNAME";
    public static final String AOP_COMPANYID = "COMPANYID";
    public static final String AOP_BUYERADMINFIRSTNAME = "BUYERADMINFIRSTNAME";
    public static final String AOP_BUYERADMINLASTNAME = "BUYERADMINLASTNAME";
    public static final String AOP_BUYERADMINEMAIL = "BUYERADMINEMAIL";
    public static final String AOP_BUYERADMINUSERID = "BUYERADMINUSERID";
    public static final String AOP_NOTE_PRIV = "NOTE_PRIV_IND";
    public static final String AOP_PROMO_END_DT = "PROMO_END_DT";
    
    public static final Integer AOP_SYSTEM_ROLE_ID = 2;
    public static final String AOP_SYSTEM = "SYSTEM";

    
    public static final String AOP_CONSUMER_FLAG = "CONSUMER";
    public static final String AOP_BID_FLAG = "PRICE_MODEL";
    public static final String AOP_BID_COMMENT = "BID_COMMENTS";
    public static final String AOP_RESOURCE_FN = "PROVIDER_FN";
    public static final String AOP_RESOURCE_LN = "PROVIDER_LN";
    
    //Role 
    public static final String AOP_ROLE_IND = "ROLE_IND";
    
    //Added for SO cancellation from OMS
    public static final double AOP_ZERO_CANCELLATION_AMT = 0.00;
    public static final String AOP_CANCEL_COMMENT_IN_ACCEPTED = "EU requested cancellation";
    
    public static final String AOP_SO_UNIT_NUMBER = "SO_UNIT_NUMBER";
    public static final String AOP_SO_ORDER_NUMBER = "SO_ORDER_NUMBER";
    public static final String AOP_FILE_NAME = "FILENAME";
    public static final String AOP_HSR_TECH_ID = "HSR_TECH_ID";
    public static final String AOP_HSR_FILE_NAME = "SLROUTES";
    public static final int AOP_HSR_FTP_TEMPLATE_ID = 274;
    
    public static final String AOP_SYSTEM_OUTFILE_TYPE = "OUTFILE";
    
    public static final int EMAIL_TEMPLATE_PROVIDER_FIRM_APPROVED = 1;
    public static final String PRIME_CONTACT_STRING = "PRIMECONTACT";
    public static final String REASONDESCRIPTION = "REASONDESCRIPTION";
    public static final String CREDENTIALNUMBER = "CREDENTIALNUMBER";
    public static final String CREDENTIALTYPE= "CREDENTIALTYPE";
    public static final String TARGETSTATENAME = "TARGETSTATENAME";
    public static final String CREDENTIALNAME = "CREDENTIALNAME";
    public static final String RESOURCENAME = "RESOURCENAME";
    //Added for Approve Firm API
    public static final String PRIME_CONTACT = "PrimeContact";
    
    public static final String Buyer = "Buyer";
    public static final String Provider = "Provider";
    
    public static final String SO_UPDATE_SPEND_LIMIT_NO_REASON = "No reason for the increase was included";
    
    public static final String AOP_CUSTOM_REFERENCE_TYPE="CUSTOM_REFERENCE_TYPE";
    public static final String AOP_CUSTOM_REFERENCE_VALUE_NEW="CUSTOM_REFERENCE_VALUE_NEW";
    public static final String AOP_CUSTOM_REFERENCE_VALUE_OLD="CUSTOM_REFERENCE_VALUE_OLD";

	public static String BUSINESS_NAME="BUSINESSNAME";
	

	//code change for SLT-2228
		public static final String AOP_EMAIL_FNAME ="FNAME";
		public static final String AOP_EMAIL_LNAME ="LNAME";
		public static final String AOP_EMAIL_USERNAME ="USERNAME";
		public static final String AOP_EMAIL_LEDGER_TRANID ="LEDGER_TRANID";
		public static final String AOP_EMAIL_TRANS_AMOUNT ="TRANS_AMOUNT";
		public static final String AOP_EMAIL_CURRENT_DATE ="CURRENT_DATE";
		public static final String AOP_EMAIL_SERVICE_URL ="SERVICE_URL";
		public static final String AOP_EMAIL_CREATED_DATE ="CREATED_DATE";
		public static final String AOP_EMAIL_AVAILABLE_BALANCE ="AVAILABLE_BALANCE";
    
}
