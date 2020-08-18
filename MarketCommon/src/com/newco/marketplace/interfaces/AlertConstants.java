/**
 * 
 */
package com.newco.marketplace.interfaces;

/**
 * @author schavda
 *
 */
public interface AlertConstants {

	public static final String EMAIL_DELIMITER = ";";
	public static final String SMS_NO = "SMS_NO";
	public static final String EQUALS = "=";
	public static final String SMS_DELIMITOR = "|";
	public static final String FIRST_NAME = "FIRST_NAME";
	public static final String LAST_NAME = "LAST_NAME";
	public static final Integer ALT_CONTACT_SMS = 3;
	public static final int ALERT_TYPE_SMS = 6;
	public static final int ALERT_TYPE_EMAIL = 1;
	public static final int TEMPLATE_SMS_SUBSCRIBE = 290;
	public static final int TEMPLATE_SMS_EMAIL = 291;
	public static final String SMS_PRIORITY = "1";
	public static final String SMSALERT_PROVIDER_LOOKUP = "/app/carriers/lookup/";
	public static final String XML_EXTN = ".xml";

	public static final int CALLBACK_SUCCESS_CODE = 200;
	public static final String UPDATE_NOTIFICATION_STATUS_SUCCESS = "WAITING";
	public static final String UPDATE_NOTIFICATION_STATUS_WAITING_FOR_REQUEST_DATA = "WAITING_FOR_REQUEST_DATA";
	public static final String UPDATE_NOTIFICATION_STATUS_SUCCESS_PAYLOAD = "WAITING_FOR_PAYLOAD";
	public static final String UPDATE_NOTIFICATION_STATUS_FAILURE = "FAILED_REQUEST_DATA";
	public static final String CALLBACK_NOTIFICATION_STATUS_SUCCESS = "AWS_SUCCESS";
	public static final String CALLBACK_NOTIFICATION_STATUS_FAILURE = "AWS_FAILURE";
	public static final String CALLACK_SERVICE_URL = "callback_service_url";
	public static final String CALLBACK_NITIFICATION_QUERY_LIMIT = "callback_notification_query_limit";
	public static final String SL_WRAPPER_API_TOKEN = "sl_wrapper_api_token";
	public static final String SMS_NOTIFICATION_SERVICE_URL = "SMSServiceUrl";
	public static final String PUSH_NOTIFICATION_SERVICE_URL = "PushServiceUrl";
	public static final String NOTIFICATION_QUERY_LIMIT = "notification_query_limit";
	public static final String DATE = "DATE";
	public static final String EVENT_TYPE = "EVENT_TYPE";
	public static final String VENDOR_ID = "VENDOR_ID";
	public static final String VENDOR_RESOURCE_ID = "VENDOR_RESOURCE_ID";
	public static final String EVENT_ID = "EVENT_ID";
	public static final String NOTIFICATION_SERVICE = "NotificationService";

	public static final String USA_CODE = "+1";
	public static final String INDIA_CODE = "+91";

	public static final String SERVICE_LIVE_MAILID = "noreply@servicelive.com";
	public static final String SERVICE_LIVE_MAILID_SUPPORT = "support@servicelive.com";
	public static final String SERVICE_LIVE_MAILID_SO_SUPPORT = "serviceordersupport@servicelive.com";
	public static final String SL_SUPPORT_MAILID = "SHI_HS_SL_OPS@searshc.com";

	public static final String ALERT_DISP_FROM = "ALERT_DISP_FROM";
	public static final String ALERT_DISP_TO = "ALERT_DISP_TO";
	public static final String ALERT_DISP_CC = "ALERT_DISP_CC";
	public static final String ALERT_DISP_BCC = "ALERT_DISP_BCC";

	public static final String ALERT_ADVICE = "ALERT ADVICE";
	public static final String COMPLETE_INDICATOR = "2";
	public static final String INCOMPLETE_INDICATOR = "1";

	public static final int TEMPLATE_TYPE_EMAIL = 1;
	public static final int TEMPLATE_TYPE_SMS = 2;
	public static final int TEMPLATE_TYPE_FTP = 4;
	public static final int TEMPLATE_TYPE_WS = 5;
	public static final int TEMPLATE_TYPE_SMS_SUBSCRIBE = 6;
	

	public static final String ALERT_ROLE_ADMIN = "A";
	public static final String ALERT_ROLE_ALL_BUYERS = "AB";
	public static final String ALERT_ROLE_ALL_ROUTED_RESOURCES = "ALL_RVR";
	public static final String ALERT_ROLE_ALL_ROUTED_RESOURCES_RELEASE = "ALL_RVR_REL";
	public static final String ALERT_ROLE_ALL_PROVIDERS = "AP";
	public static final String ALERT_ROLE_ACCEPTED_PROVIDERS = "AV";
	public static final String ALERT_ROLE_ACCEPTED_VENDOR_RESOURCE = "AVR";
	public static final String ALERT_ROLE_BUYER = "B";
	public static final String ALERT_ROLE_BUYER_RESOURCE = "BC";
	public static final String ALERT_ROLE_BUYER_AND_PROVIDER = "BP";
	public static final String ALERT_ROLE_SERVICELIVE = "SL";
	public static final String ALERT_ROLE_SERVICELIVE_SUPPORT = "SL_SUPPORT";
	public static final String ALERT_ROLE_X = "X";
	public static final String ALERT_ROLE_PROVIDER_ADMIN = "PA";
	public static final String ALERT_ROLE_BUYER_ADMIN_AND_SO_CREATOR = "BA";
	public static final String ALERT_ROLE_BUYER_CONTACTS_AND_ACCEPTED_VENDOR_RESOURCE = "BC_AVR";
	public static final String ALERT_ROLE_ALL_ROUTED_PROVIDERS_EXCEPT_ACCEPTED = "ARP";
	public static final String LOGGED_IN_PROVIDER = "LP";

	public static final String ALERT_ROLE_CLIENT_ASSURANT = "C_ASSURANT";
	public static final String ALERT_ROLE_CLIENT_ASSURANT_EMAIL = "C_ASSURANT_INCIDENT_CANCEL_REQUEST";
	public static final String ALERT_ROLE_CLIENT_HSR = "C_HSR";

	public static final String ALERT_ROLE_B_OR_P = "B_OR_P";

	public static final String ROLE_PROVIDER = "P";
	public static final String ROLE_CONSUMER_BUYER = "CB";
	public static final String ROLE_PROFESSIONAL_BUYER = "PB";

	/// for consumer buyer
	public static final String CONSUMER_BUYER = "CONSUMER";
	public static final String YES = "Y";
	public static final String NO = "N";

	// Triggered Emails
	public static final String BUYER_RESOURCE_ID = "BUYER_RESOURCE_ID";
	public static final String SCHEDULER_NAME = "TRIGGEREMAIL";

	public static final String VELOCITY_TEMPLATE = "Velocity Template";
	public static final String ALERT_TYPE_SMS_SUBSCRIBE = "6";
	public static final int TEMPLATE_TYPE_ALERT_RESPONSYS = 336;
    public static final int TEMPLATE_TYPE_FAILURE_ALERT_RESPONSYS = 335;
	public static final int TEMPLATE_PROVIDER_CHECK_EMAIL = 296;
	// SL 15642 Constant template id for provider auto accept change mail
	public static final int TEMPLATE_AUTO_ACCPET_CHANGE_PROVIDER_CHECK_EMAIL = 304;
	public static final int TEMPLATE_AUTO_ACCPET_CHANGE_BUYER_EMAIL = 305;
	public static final int ALERT_TYPE_ID = 1;
	public static final String PRIORITY = "1";

	public static final String SERVICE_LIVE_COMPLAINCE_MAILID = "SLCompliance@servicelive.com";

	public static final int TEMPLATE_NPS_FAILURE_EMAIL = 302;
	// R11.0
	public static final int TEMPLATE_PROVIDER_BACKGROUNDCHECK_EMAIL = 321;

	// R16_1 SL-18979 vibe constants for SO Post batch
	public static final String VIBES_COMPANYID = "VIBESCompanyId";
	public static final String VIBES_CREATE_EVENT_API_URL = "VIBESCreateEventAPIURL";
	public static final String VIBES_EVENT_TYPE = "VIBESEventType";
	public static final String VIBES_HEADER = "VIBESHeader";
	public static final String VIBES_DEEP_LINK_URL = "VIBESDeepLinkURL";

	public static final String VIBES_SEMI_COLON = ";";
	public static final String VIBES_COLON = ":";
	public static final String VIBES_COMMA = ",";
	public static final String VIBES_HYPHEN = "-";
	public static final String VIBES_SPACE = " ";

	public static final String VIBES_SO_ID = "SO_ID";
	public static final String VIBES_SO_SERVICE_CITY = "SO_SERVICE_CITY";
	public static final String VIBES_SO_SPEND_LIMIT = "SPEND_LIMIT";
	public static final String VIBES_SO_SERVICE_ZIP = "SO_SERVICE_ZIP";
	public static final String VIBES_SO_SERVICE_STATE = "SO_SERVICE_STATE";

	public static final String VIBES_SO_ROUTED_DATE = "SO_ROUTED_DATE";
	public static final String VIBES_SO_SERVICE_DATE1 = "SERVICE_DATE1";
	public static final String VIBES_SO_SERVICE_DATE2 = "SERVICE_DATE2";
	public static final String VIBES_SO_SERVICE_START_TIME = "SERVICE_START_TIME";
	public static final String VIBES_SO_SERVICE_END_TIME = "SERVICE_END_TIME";
	public static final String VIBES_SO_SERVICE_LOCN_TIMEZONE = "SO_SERVICE_LOC_TIMEZONE";
	public static final String VIBES_SO_CREATED_DATE = "CREATED_DATE";
	public static final String VIBES_SO_MODIFIED_DATE = "MODIFIED_DATE";

	public static final String VIBES_COMPANY_ID_TO_REPLACE = "{companyId}";

	public static final int SMS_API_SUCCESS_CODE = 200;
	public static final int PUSH_API_SUCCESS_CODE = 200;

	public static final int VIBES_CREATE_EVENT_API_SUCCESS_CODE = 202;
	public static final int VIBES_CREATE_EVENT_API_FAILURE_CODE = 413;
	public static final String VIBES_ALERT_STATUS_SUCCESS = "Success";
	public static final String VIBES_ALERT_STATUS_FAILURE = "Failure";
	public static final String HYPHEN = "-";
	public static final String VIBES_DISPLAY_DATE_FORMAT_WITH_TIMEZONE = "yyyy-MM-dd'T'hh:mm:ssz";
	public static final String VIBES_QUERY_LIMIT = "vibes_alert_task_query_limit";
	public static final String VIBES_DATE_FORMAT_REQUIRED = "MMM dd, yyyy";
	public static final String VIBES_DATE_FORMAT_REFORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String VIBES_SID = "VIBES_SID";

	public static final String STATUS = "STATUS";
	public static final String SO_ID = "SO_ID";
	public static final String NO_OF_RETRIES = "NO_OF_RETRIES";
	public static final String EXCEPTION ="EXCEPTION";
	public static final String FAILURE = "FAILURE";
	
	public static final String PRIMARY_RESOURCE_ID = "ACCEPTED_VENDOR_ID";
	
	//SLT-3836
	public static final String CALLBACK_BASE_GATEWAY = "GATEWAY";
	public static final String CALLBACK_BASE_PLATFORM = "PLATFORM";
	public static final String CALLBACK_NOTIFICATION_PLATFORM_SUCCESS = "SUCCESS";
	public static final String CALLBACK_NOTIFICATION_PLATFORM_FAILURE = "FAILURE";
	
	//SLT-4020
	public static final int CALLBACK_GATEWAY_TIMEOUT_CODE=504;
	public static final String CALLBACK_GATEWAY_TIMEOUT_ERROR="Unable to connect to Callback Api";
	public static final String CALLBACK_BASE_PLATFORM_RETRY="WAITING";
	public static final String CALLBACK_BASE_GATEWAY_RETRY="AWS_FAILURE";
	public static final String CALLBACK_FAILURE="FAILURE";
	public static final int CALLBACK_SERVICE_TEMP_UNAWAILABLE_CODE=503;
	
}
