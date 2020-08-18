package com.newco.marketplace.interfaces;

/**
 * $Revision: 1.16 $ $Author: akashya $ $Date: 2008/05/21 23:48:29 $
 */
public interface MPConstants {
	//	logger info
	public static final String LOGGER_DEBUG = "DEBUG";
	public static final String LOGGER_INFO = "INFO";
	public static final String LOGGER_ERROR = "ERROR";
	public static final String LOGGER_FATAL = "FATAL";

	// Numeric Constants
	public static final int ZERO = 0;
	//public static final int ONE = 1;

	public static final String ERROR = "Error";
	public static final String SUCCESS = "Success";

	public static final String PART_BEAN = "partBean";
	// Create Return Constants
	public static final String GET_PART = "getPart";
	public static final String SAVE_NEXT = "saveNext";
	public static final String SAVE_RECEIVE = "saveReceive";
	public static final String PARTRETURN_BEAN = "partReturnBean";
	public static final String PARTRETURN = "partReturn";
	public static final String SHOWRETURN = "showReturn";
	public static final String CLEARRETURN = "clearReturn";
	public static final String PROCID = "ALL_PRC";
	public static final String DISP_CORE = "CIW";
	public static final String CORE_PART = "RCO";
	public static final String WARR_PART = "RIW";
	public static final String RETURNTYPECODE = "PRT";
	public static final String RETURNREASONCODE = "PRR";
	public static final String NRETURNTYPECODE = "NRT";
	public static final String INSTALLED = "INS";
	public static final String UNINSTALLED = "UNI";
	public static final String OTHERS = "OTH";
	public static final String REDIRECT_PAGE = "redirect";

	//User info
	public static final String USERS = "users";
	public static final String USER_RESULTS = "userresults";
	public static final String USERADDEDIT = "useraddedit";
	public static final String USERFORM = "UserForm";
	public static final String SECURITYTYPECODE = "UAR";
	public static final String UPDATE = "update";
	public static final String LOGIN = "login";
	public static final String INSERT = "insert";
	public static final String SAVEUSER = "saveUser";
	public static final String GETUSERS = "getUsers";
	public static final String GETLOGIN = "getlogin";
	public static final String ROLE_ADMIN = "ADM";
	public static final String ROLE_SUPERVISOR = "SPV";
	public static final String ROLE_ASSOCIATE = "ASC";
	public static final String ADMIN_UNIT = "0000490";
	public static final String USERPROFILE_USERID = "USERID";
	public static final String USERPROFILE_SECURITYLEVEL = "SECURITYLEVEL";
	public static final String USERPROFILE_UNITNUMBER = "UNITNUMBER";
	public static final String USERPROFILE_STATUS = "STATUS";
	public static final String USERPROFILE_STATUS_YES = "Yes";
	public static final String USERPROFILE_STATUS_NO = "No";
	public static final String USER_BEAN = "userBean";
	public static final String LOGIN_BEAN = "LoginBean";
	public static final String UNIT_BEAN = "UnitBean";
	public static final String UNITSIZE = "UnitSize";

    public static final String VENDOR_SUBMISSION_STATUS = "vendorSubmissionStatus";

	//System Control
	public static final String SYSTEM_MESSAGE = "systemmessages";
	public static final String SYSTEM_CONTROL_ADMINMESSAGE = "SYSADMSG";
	public static final String SAVE_SYSTEM_MESSAGE = "saveSystemMessage";

	//Global Error
	public static final String GLOBALERROR = "rfsglobalerror";
	public static final String POPUPERROR = "rfspopuperror";
	public static final String SYSTEMERROR = "rfssystemerror";
	public static final String BUSINESSERROR = "rfsbusinesserror";
	public static final String SESSIONERROR = "rfssessionerror";

	//Field Info Queries
	public static final String FIELDINFOQUERY = "FieldInfo.getFieldCodeDesc";
	public static final String FIELDINFO_DESC_QUERY =
		"FieldInfo.getFieldDescForCode";
	public static final String FIELDINFO_BEAN = "fieldInfoBean";

	//Unit Profile Constants
	public static final String UNIT_LIST = "UNITS";
	public static final String UNIT_NO = "UNITNO";
	public static final String UNIT = "validunit";
	public static final String UNITS = "validunits";
	public static final String INVALID = "invalid";
	public static final String NAVIGATE = "navigate";
	public static final String WELCOME = "welcome";
	public static final String REDIRECT = "redirectlogin";
	public static final String SCRAP = "SCP";
	public static final String HAZMAT = "HZD";
	public static final String SENDTOPDC = "SNP";
	public static final String SENDTOIRC = "SNI";
	public static final String SENDTOVENDOR = "SNV";
	public static final String KEEPATUNIT = "KPU";
	public static final String OT = "OT";
	public static final String OPEN = "OPEN";
	public static final String IRC = "IRC";
	public static final String INACTIVE = "N";
	public static final String UNITEDIT = "edit";
	public static final String INVALIDUNITCODE = "invalidunitcode";
	public static final String AVLPDCNULL = "availablepdcnull";
	public static final String ASGPDCNULL = "assigneddcnull";
	public static final String AVLRULESNULL = "availablerulesnull";
	public static final String GETUNITPAGE = "getunitpage";
	public static final String GETSHIPMENTPAGE = "getPage";

	//	Unit Profile Constants
	public static final String PARTSTATUSCODE = "PRS";
	public static final String SHIPMENTSTATUSCODE = "SBS";
	public static final String SHIPMENT_BEAN ="ShipmentBean";

	//Inquiry Constants
	public static final String PARTINQRESULT = "partInquiryResult";
	public static final String PARTINQ = "partInquiry";
	public static final String PARTRTNHISTORY = "partReturnHistory";
	public static final String SHIPINQRES = "shipmentInquiryResults";
	public static final String SEARCHINQUIRY = "searchInquiry";
	public static final String LOADINQUIRY = "loadInquiry";
	public static final String SEARCHDETAILS = "searchDetails";
	public static final String VIEWPRTRTNHSTRY = "viewPartReturnHistory";
	public static final String VIEWSHIPDETAILS = "viewShipmentDetails";
	public static final String RGINO = "hidRGINo";
	public static final String BARCODE ="txtBarCode";
	public static final String SANRECORD = "SAN";
	public static final int DATA_LIMIT = 200;
	public static final String SNVDESC = "Send to Vendor";
	public static final String MOVEPAGE="moveInquiry";
	public static final String RTR_NUMBER="hdnRTRNumber";

	//  Shipment Constants
	public static final String SHIPPERCODE = "CAR";
	public static final String GETSHIPMENT = "getShipmentDetails";
	public static final String ITS = "Intransit";
	public static final String CWS = "CWS";
	public static final String MISSED = "MSS";
	public static final String NORMAL = "Normal";

	//	Session Variables
	public static final String USERID = "userid";
	public static final String SUNIT = "sunit";
	public static final String SECURITY = "security";
	public static final String SECURITYDESC = "securityDescription";
	public static final String COMMONNAME = "commonname";
	public static final String RESOURCEID = "resourceId";
    public static final String RESOURCENAME = "resourceName";
	
	public static final String DISPATCH = "dispatch";

	// LDAP
	public static final String LDAP_UID = "uid=";
	public static final String LDAP_INITCTX =
		"com.sun.jndi.ldap.LdapCtxFactory";
	//TEST env
	//public static final String LDAP_HOST = "ldap://166.76.8.21:389";
	//PROD Env
	public static final String LDAP_HOST = "ldap://10.72.247.76:389";
	public static final String LDAP_SEARCH_START = "o=intra,dc=sears,dc=com";
	public static final String LDAP_PART_FLAG="part";

	// Scrap_Threshold
	public static final String THRESHOLD_SUCCESS = "thresholdSuccess";
	public static final String SCRAP_SUCCESS = "scrapSuccess";
	public static final String THRESHOLD_BEAN = "thresholdBean";
	public static final String SAVE_PART_AMOUNT_DETAILS =
		"savePartAmountDetails";
	public static final String GET_PART_RETRIEVE = "getPartRetrieve";

	//Receive Part variables
	public static final String RECEIVEPART = "receivePart";
	public static final String RECEIVEPARTCONFIRM = "receivePartConfirm";
	public static final String SHOWRECEIVEPART = "showReceivePart";
	public static final String DPREASONCODE = "DPR";
	public static final String DISPCODE = "DPO";
	public static final String RECEIVEPART_BEAN = "receivePartBean";
	public static final String ONE_WAY = "1 way sub";
	public static final String TWO_WAY = "2 way sub";
	public static final String ONE = "1";
	public static final String TWO = "2";
	public static final String SUBPART = "subPart";
	public static final String FLAG_YES = "Y";
	public static final String FLAG_NO = "N";
	public static final String STATUS_CLOSED = "CLO";
	public static final String STATUS_CFO = "CFO";
	public static final String STATUS_OPEN = "OPN";
	public static final String PART_SHIP_CD = "UVS";
	public static final String RCD_NML = "NML";
	public static final String RCD_MDR = "MDR";
	public static final String VIEWHISTORY = "viewhistory";
	public static final String OVERRIDE_DISPOSITION = "overrideDisposition";
	public static final String SAVE_OVERRIDE = "saveOverrideDisposition";
	public static final String PART_RETURN_OVERRIDE_REASON = "PROR";
	public static final String ROLE_ASC = "ASC";
	public static final String KEEPATPDC = "KPP";
	public static final String KEEPATIRC = "KPI";
	public static final String EDITPARTINFO = "editPartInfo";
	public static final String UPDATEPARTINFO = "updatePartInfo";
	public static final String PRT_AVL_CD = "U";
	public static final String HZT_CD_F = "F";
	public static final String HZT_CD_O = "O";
	public static final String HZT_CD_AK = "AK";
	public static final String HZT_CD_HI = "HI";
	public static final String HZT_CD_PR = "PR";
	public static final String CORE_INW = "Core/In Warranty";
	public static final String MOR = "MOR";
	public static final String CLEAR_RECEIVE = "clearReceivedPart";
	public static final String CANCEL_RECEIVE = "cancelReceive";
	public static final String VIEWRETURNHISTORY = "viewReturnHistory";
	public static final String UNKNOWN = "UNKNOWN";


	//vendorthreshold
	public static final String VENDORSUCCESS = "vendorSucess";
	public static final String CARRY_IN_GRC = "GRC";
	public static final String CARRY_IN_EMC = "EMC";
	public static final String BRANCH = "BS";
	public static final String DISTRICT = "DS";
	public static final String PDC = "PDC";
	public static final String CARRY_IN = "CARRY_IN";
	public static final String RESTOCK_AMOUNT_TYPE = "RSK_CLC_CD";
	public static final String RESTOCK_AMOUNT_VALUE = "RSK_CLC_NO";
	public static final String UNIT_TYPE_CODE = "UN_TYP_CD";
	public static final String ACTIVE_FLAG = "ATV_FL";
	public static final String GET_VENDOR_INFO = "getVendorInfo";
	public static final String SAVE_VENDOR_INFO = "saveVendorInfo";

	//Disposition
	public static final String SHIPPED_STATUS = "UVS";
	public static final String RECEIVED_STATUS = "RCV";
	public static final String LTY_UNIT = "U";
	public static final String LTY_SOURCE = "S";
	public static final String LTY_A = "A";
	public static final String DISP_CD = "DISPCD";
	public static final String DISP_LOC_NO = "DISPLOCNO";
	public static final String DISP_TYP_CD = "DISPTYPCD";
	public static final String DISPOSITIONBEAN = "dispositionEngine";

	//Pdc/Irc threshold
	public static final String PDC_SUCCESS = "pdcSuccess";
	public static final String PART_RETURN_TYPE = "PRT";
	public static final String GET_FORECAST_PARAM = "getForecastParam";
	public static final String SAVE_FORECAST_USAGE_PARAM =
		"saveForecastUsageParam";
	public static final String GENERAL_EXCEPTION = "generalException";


	//State contactants
	public static final String STATEDISPLAY = "stateDisplay";
	public static final String STATENOTFOUND = "stateNotFound";
	public static final String STATEFORM = "stateForm";

	public static final String STATEBUSINESSBEAN = "StateBusinessBean";

	public static final String AUTHENTICATION_TOKEN_NAME = "security.authentication.token";
	public static final String FORWARD_LOGIN_SUCCESSFUL = "success"; //"security.actionforwards.loginSuccessful";
	public static final String FORWARD_LOGIN_FAILTURE = "failure"; // "security.actionforwards.loginFailture";
	public static final String SPRING_BEAN_SECURITY_BUSINESS_BEAN = "SecurityBusinessBean";
	public static final String SESSION_SECURITY_CONTEXT = "ProviderSecurityContext";
	public static final String AUDITOR_SESSION_SECURITY_CONTEXT = "AuditorSecurityContext";	
        
    //Struts constants
    public static final String STRUTS_SUCCESS = "success";
    public static final String STRUTS_FAILURE = "failure";
    
    //Struts message constants
    public static final String MSG_DATA_ACCESS_FAILURE = "data.access.failure";
    
    public static final String MSG_LOST_PASSWORD_USERNAME_INVALID ="lostPassworForm.usernname.invalid";
    public static final String MSG_LOST_PASSWORD_SECRETANSWER_INVALID ="lostPassworForm.secretAnswer.invalid";
        
    
    //Field message constants
    public static final String MSG_INVALID_INTEGER_FORMAT = "validation.integer.format";
    
    //Activity constants
    public static final String USER_LOGIN = "Login";
    public static final String BUSINESS_INFORMATION = "Business Information";
    public static final String OWNERSHIP = "Ownership";
    public static final String CREDENTIALS = "Credentials";
    public static final String WARRANTY_AND_POLICIES = "Warranty & Policies";
    public static final String FINANCIAL = "Financial";
    public static final String W9 = "W9";
    public static final String INSURANCE = "Insurance";
    public static final String TERMS_AND_CONDITIONS = "Terms & Conditions";
    public static final String TEAM_MEMBER = "Team Member";
    public static final String TEAM_MEMBER_CREDENTIALS = "Team Member Credentials";
    public static final String CONTACT_INFORMATION = "Contact Information";
    public static final String SKILLS = "Skills";
    public static final String COVERAGE_AREA = "Coverage Area";
    public static final String SCHEDULE = "Schedule";
    public static final String QUALIFICATIONS = "Qualifications";
    public static final String VENDOR = "Vendor";
    public static final String VENDORID = "VendorId";
    public static final String RESOURCE = "Resource";
    public static final String PROVIDER_SEARCH = "Provider Search";
    public static final String AUDIT_PROFILE = "Audit Profile";
    
    public static final String MSGTYPE = "msgtype";
    public static final String MSGTYPE_ERROR = "error";
    public static final String MSGTYPE_SUCCESS = "success";    
    
    //Page level action constants
    public static final int ACTION_TYPE_INSERT = 1;
    public static final int ACTION_TYPE_UPDATE = 2;
    
    public static final String BUSINESSBEAN_AUDIT = "auditBoImpl"; 
    
    public static final String ROLE_PROVIDER_ADMIN = "Provider Administrator";
    public static final String ROLE_PROVIDER= "Provider";
    public static final String ROLE_BUYER= "Buyer";
    public static final String ROLE_SIMPLE_BUYER= "SimpleBuyer";
    
    public static final String ROLE_AUDITOR = "Compliance Auditor";

    public static final String FORWARD_PROVIDERSEARCH_SUCCESSFUL = "providerSearchSuccess"; 
    
    public static final String BEAN_NAME_AUDIT_EMAIL_BEAN = "auditEmailBusinessBean";
    public static final String BEAN_NAME_EMAILTEMPLATEBEAN = "emailTemplateBean";
    
    public static final String BEAN_NAME_PROVIDER_EMAILBEAN = "providerEmailBean";
    
    public static final String TEMPLATE_NAME_REGISTRATION_EMAIL = "Company Registration Email";
    public static final String TEMPLATE_NAME_BUYER_COMPANY_REGISTRATION = "Professional Buyer Registration";
    public static final String TEMPLATE_NAME_BACKGROUND_CHECK_EMAIL = "Team Member Background Check Email";
    public static final String TEMPLATE_NAME_FORGOT_USERNAME_EMAIL = "User Profile Forgot Username";
    ////Added by Mayank for sending EMAIL when TEAM MEMBER registration is done - this should be changed to correct template later
    public static final String TEMPLATE_NAME_REGISTRATION_EMAIL_TEAMMEMBER="Company Registration Email";
    //Added for Provider User Registration Confirmation template name
    public static final String TEMPLATE_NAME_PROVIDER_MEMBER_CONF_MAIL = "Provider User Registration Email";
    
    public static final String TEMPLATE_NAME_RESET_PASSWORD = "User Profile Reset Password";
    public static final String TEMPLATE_INVALID_STATE_CONFIRM_EMAIL="Company Registration Please Comeback";
    public static final String TEMPLATE_NAME_SIMPLE_BUYER_REGISTRATION="Simple Buyer Registration Email";
    
    public static final Integer EMAIL_TEMPLATE_ROUTING_RULES_ALERT_BUYER_NOTIFICATION_COMBINED = 269;
    
    public static final String SERVICE_BUSINESSBEAN = "serviceOrderDao";
    
    public static final int EMAIL = 1;
    public static final int SMS = 2;
    public static final int PAGER = 3;
    public static final String NEW = "new";
    public static final String ACCEPTED = "accepted";
    public static final String REJECTED = "rejected";
    public static final Integer ROUTED = 1;
    public static final String ORDER = "order";
    public static final String CREDENTIAL = "credential";

    public static final String CHECKBOX = "error.vendor.credential.checkbox";
    
    public static final String AUDIT_SUCCESS = "auditSuccess";
    
    public static final String ENCRYPTION_KEY = "enKey";
    
    //SL-18789
    public static final String CC_ENCRYPTION_KEY = "CCENKEY";
    public static final String SECRET_KEY = "AES";
    public static final String SECRET_KEY_BYTES = "8859_1";
    public static final String SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA1";
    
    public static final Integer SLADMIN_RESOURCE_ID = 0;
    
    //Constants for the Payment reports batch
    public static final String PAYMENT_REPORTS_DIRECTORY = "payment_reports_directory";
    public static final String FILE_REMOVAL_THRESHOLD = "file_removal_threshold";
    public static final int FILE_REMOVAL_THRESHOLD_VALUE = 7;    
	public static final String CSV_REPORT_DATE_FORMAT = "MM/dd/yyyy";
	public static final String MAX_TIME_INTERVAL_FOR_BATCH_SEC = "max_time_interval_for_batch_sec";
	public static final int TIME_INTERVAL_FOR_BATCH_SEC	=7200;
	public static final String NUMBER_REPORTS_TO_BE_PROCESSED = "no_of_report_input_criteria_to_be_picked_up";
	
	//Constants for sending email for slAdmin
    public static final String ADMIN_NOTIFICATION_DIRECTORY = "admin_notification_directory";
	public static final String FEEDBACK_FILE_DIRECTORY = "feedback_file_directory";
	public static final String MOBILEFEEDBACK_FILE_DIRECTORY = "mobile_feedback_file_directory";
    public static final String THIRTY_DAYS_INSURANCE = "30 Days - Insurance expiration notice (";
    public static final String NO_THIRTY_DAYS_INSURANCE = "No 30 Days - Insurance expiration notice sent on (";
    public static final String THIRTY_DAYS_COMPANY = "30 Days - Company Credential expiration notice (";
    public static final String NO_THIRTY_DAYS_COMPANY = "No 30 Days - Company Credential expiration notice sent on (";
    public static final String DELIVERED_SUCCESSFULLY = "Delivered Successfully";
    public static final String SERIAL_NO = "Sr. No";
    public static final String FIRM_ID ="Firm ID #";
    public static final String FIRM_NAME ="Firm Name";
    public static final String EXPIRATION_DATE ="Expiration Date";
    public static final String NOTICE_SENT_ON ="Notice sent on Date";
    
    public static final String THIRTY_DAYS_PROVIDER ="30 Days - Provider Credential expiration notice (";
    public static final String NO_THIRTY_DAYS_PROVIDER ="No 30 Days - Provider Credential expiration notice sent on (";

    public static final String PROVIDER_ID ="Provider ID #";
    public static final String PROVIDER_NAME ="Provider Name";
    public static final String CREDENTIAL_TYPE="Type of Credential";
    public static final String SEVEN_DAYS_INSURANCE="7 Days - Insurance expiration notice  (";
    public static final String NO_SEVEN_DAYS_INSURANCE="No 7 Days - Insurance expiration notice sent on (";

    public static final String INSURANCE_TYPE="Type of Insurance";
    public static final String SEVEN_DAYS_COMPANY="7 Days - Company Credential expiration notice (";
    public static final String NO_SEVEN_DAYS_COMPANY="No 7 Days - Company Credential expiration notice sent on (";

    public static final String SEVEN_DAYS_PROVIDER="7 Days - Provider Credential expiration notice (";
    public static final String NO_SEVEN_DAYS_PROVIDER="No 7 Days - Provider Credential expiration notice sent on (";

    public static final String OUT_OF_COMPLIANCE_INSURANCE="Out of Compliance - Insurance (";
    public static final String NO_OUT_OF_COMPLIANCE_INSURANCE="No Out of Compliance - Insurance sent on (";

    public static final String OUT_OF_COMPLIANCE_STATUS_CHANGE="Status changed to \"Out of compliance\"";
    
    public static final String OUT_OF_COMPLIANCE_COMPANY="Out of Compliance - Company Credential  (";
    public static final String NO_OUT_OF_COMPLIANCE_COMPANY="No Out of Compliance - Company Credential sent on (";

    public static final String OUT_OF_COMPLIANCE_PROVIDER="Out of Compliance - Provider Credential  (";
    public static final String NO_OUT_OF_COMPLIANCE_PROVIDER="No Out of Compliance - Provider Credential sent on (";

    public static final String FIRST_SHEET= "First Sheet";

    public static final String THIRTY_DAYS_NOTIFICATION_XLS= "30daysNotification_";
    public static final String SEVEN_DAYS_NOTIFICATION_XLS= "7daysNotification_";
    public static final String OUT_OF_COMPLAINCE_XLS= "OutofComplainceNotification_";
    public static final String XLS_FORMAT= ".xls";

    
    public static final String EMAIL_BODY="";
    
    public static final String THIRTY_DAYS_EMAIL_SUBJECT="30 Days Expiry Notification Reports";
    public static final String SEVEN_DAYS_EMAIL_SUBJECT="7 Days Expiry Notification Reports";
    public static final String OUT_COMPLAINCE_EMAIL_SUBJECT="Out of compliance Reports";
    public static final String EMAIL_FROM="SLCompliance@servicelive.com";
    
    public static final String NPS_NOTIFICATION_RETRY="NPSNotificationRetry";
    public static final String NPS_NOTIFICATION_REQUEST="NPSNotificationRequest";
    public static final String NPS_NOTIFICATION_RESPONSE="NPSNotificationResponse";
    public static final String NPS_NOTIFICATION_ERROR_CODE_REQUEST="NPSNotificationErrorCodeRequest";
    public static final String NPS_NOTIFICATION_ERROR_CODE_RESPONSE="NPSNotificationErrorCodeResponse";

    public static final String NPS_NOTIFICATION_EXCEPTION="NPSNotificationException";

    public static final String NPS_NOTIFICATION_SUBJECT="SL-NPS Integration Failure Notification";

    public static final String NPS_NOTIFICATION_DIRECTORY="NPS_notification_directory";

    public static final String NPS_NOTIFICATION_EMAIL="NPS_notification_email";
    public static final String NPS_NOTIFICATION_SWITCH="NPS_outbound_notification_switch";
    public static final String NPS_NOTIFICATION_ADDRESS="NPS_notification_address";
    public static final String NPS_NOTIFICATION_SL_ADDRESS="NPS_notification_SL_address";
    public static final String NPS_EMAIL_FROM="servicelive@contact.servicelive.com";
    
    public static final String NPS_NOTIFICATION_SUBJECT_LEADS = "SL-NPS Integration Failure Notification for Leads";
    public static final String NPS_NOTIFICATION_LEADS_SL_ADDRESS="NPS_notification_leads_SL_address";
    public static final String NPS_NOTIFICATION_LEADS_EMAIL="NPS_notification_leads_email";
    public static final String NPS_NOTIFICATION_LEADS_NO_OF_RETRIES="NPS_notification_leads_no_of_retries";
    public static final String NPS_NOTIFICATION_LEADS_SERVICE_UNIT_NUMBER="NPS_notification_leads_service_unit_number";
    public static final String NPS_NOTIFICATION_LEADS_RECORDS_PROCESSING_LIMIT="NPS_notification_leads_records_processing_limit";
    
    public static final String MAIL_HOST="smtp.sears.com";
    
    public static final int EXCEL_COLUMN_SIZE = 20;
    public static final int EXCEL_COLUMN_SIZE_THIRTY = 30;
    public static final int EXCEL_POSITION_ZERO = 0;
    public static final int EXCEL_POSITION_ONE = 1;
    public static final int EXCEL_POSITION_TWO = 2;
    public static final int EXCEL_POSITION_THREE = 3;
    public static final int EXCEL_POSITION_FOUR = 4;
    public static final int EXCEL_POSITION_FIVE = 5;
    public static final int EXCEL_POSITION_SIX  =6;
    public static final int EXCEL_POSITION_SEVEN =7;
    public static final int EXCEL_POSITION_EIGHT =8;
    public static final int EXCEL_POSITION_NINE =9;
    public static final int EXCEL_POSITION_TEN =10;
    public static final int EXCEL_POSITION_ELEVEN =11;
    public static final int EXCEL_POSITION_TWELVE =12;

    public static final String OUT_OF_COMPLAINCE_FIRM_INSURANCE="outOfComplainceFirmInsuranceList";
    public static final String OUT_OF_COMPLAINCE_FIRM_NONINSURANCE= "outOfComplainceFirmNonInsuranceList";
    public static final String SEVEN_DAYS_FIRM_INSURANCE="sevenDaysFirmInsuranceList";
    public static final String SEVEN_DAYS_FIRM_NONINSURANCE="sevenDaysFirmNonInsuranceList";
    public static final String THIRTY_DAYS_FIRM_INSURANCE="thirtDaysFirmInsuranceList";
    public static final String THIRTY_DAYS_FIRM_NONINSURANCE="thirtDaysFirmNonInsuranceList";
    public static final String SEVEN_DAYS_FIRM_NONINSURANCE_FOR_UPDATE ="sevenDaysFirmNonInsuranceListForUpdate";
    public static final String SEVEN_DAYS_FIRM_INSURANCE_FOR_UPDATE ="sevenDaysFirmInsuranceListForUpdate";
    
    public static final String OUT_OF_COMPLAINCE_PROVIDER="outOfComplainceProviderList";
    public static final String SEVEN_DAYS_PROVIDER_LIST ="sevenDaysProviderList";
    public static final String THIRTY_DAYS_PROVIDER_LIST ="thirtDaysProviderList";
    public static final String SEVEN_DAYS_PROVIDER_LIST_FOR_UPDATE = "sevenDaysProviderListForUpdate";
    public static final String EMPTY_STRING ="";
    public static final String CONNECTION_STRING =" > ";
    public static final String SPACE_STRING =" ";
    public static final String SENT_ON =" sent on";


   

    //Cancellation API default work order SKU
    public static final String DEFAULT_WORK_ORDER_SKU = "default_work_order_sku";
    public static final String DEFAULT_WORK_ORDER_SKU_COMMENT = "default_work_order_sku_comment";
    public static final String DEFAULT_WORK_ORDER_TASK = "default_work_order_task";
    
    //admin Email
    public static final String ADMIN_EMAIL_GROUP = "admin_email_group";
    
    //SL-18007:
    public static final String DEFAULT_API_SO_LIMIT = "max_allowed_so_count_pricehistory_api";
   
    //SL-15642:for performance testing
    public static final String OM_API_INDICATOR = "order_management_api_use_indicator";

    //SL-18748 Feedback batch
    public static final String FEEDBACK_EMAIL_GROUP = "feedback_email_group";
    public static final String MOBILE_FEEDBACK_EMAIL_GROUP = "mobile_feedback_email_group";
    public static final String FEED_BACK  			= "Feedback_";
    public static final String MOBILE_FEEDBACK  	= "MobileApp_Feedback_";
    public static final String CSV_FORMAT 			= ".csv";
    public static final String FEEDBACK_FIRST_NAME  = "First Name";
    public static final String FEEDBACK_LAST_NAME   = "Last Name";
    public static final String FEEDBACK_USER_ID     = "User ID";
    public static final String FEEDBACK_FIRM_NAME   = "Firm Name";
    public static final String FEEDBACK_FIRM_ID     = "Firm ID";
    public static final String FEEDBACK_CREATED_DATE= "Submitted Date & time";
    public static final String FEEDBACK_OM_PAGENAME = "Page Name";
    public static final String FEEDBACK_OM_TAB      = "Tab";
    public static final String FEEDBACK_URL      	= "Url";
    public static final String FEEDBACK_CATEGORY    = "Category";
    public static final String FEEDBACK_SCREENSHOT  = "Screenshot";
    public static final String FEEDBACK_COMMENTS    = "Comments";
    public static final String FEEDBACK_CONTACT_ME  = "Contact Me";
    public static final String FEEDBACK_COMMA 		= ",";
    public static final String FEEDBACK_ID   		= "FeedBack Id";
    public static final String FEEDBACK_USER_NAME   = "User Name";
    public static final String FEEDBACK_EMAIL       = "E-Mail";
    public static final String FEEDBACK_PHONE_NO    = "Phone#";
    public static final String FEEDBACK_APP_VERSION	= "App Version";
    public static final String FEEDBACK_OS_VERSION	= "Device OS";
    public static final String FEEDBACK_RESOURCE_ID = "Resource ID";
    //public static final String FEEDBACK_CONTACT_ME_YES = "Y";
    //public static final String FEEDBACK_CONTACT_ME_NO  = "N";
    public static final String BLANK		   		   = "";
    public static final String SPACE		   		   = " ";
    public static final String REPLACE_SPACE		   = "%20";
    public static final String ATTACH_DATE_IN_FEEDBACK_CSV_FILE = "yyyyMMddHHmmss";
    public static final String ATTACH_DATE_IN_FEEDBACK_EXCEL_FILE = "yyyyMMddHHmmss";
    public static final String SPLIT		   = ";";
    public static final String FEEDBACK_MAIL_SUBJECT = "Order Management Feedback";
    public static final String MOBILE_FEEDBACK_MAIL_SUBJECT = "Mobile app Feedback";
    public static final String FEEDBACK_SHEET = "Feedback";
    public static final String MOBILE_FEEDBACK_SHEET = "MobileAppFeedback";
    public static final String FEEDBACK_MOUNTED_LOCATION = "feedback_mounted_location";
    //public static final String FEEDBACK_MOUNTED_LOCATION_TILL = "feedback_mounted_location_till";
    public static final String FEEDBACK_URL_LOCATION_TYPE = "feedback_url_location_type";
    public static final String FEEDBACK_URL_OR_FILESYSTEM_TYPE = "1";  //If URL then 1 , 0 if file system
    public static final String FEEDBACK_SAVE_LOCATION= "feedback_save_location";
    public static final String FEEDBACK_EMAIL_MESSAGE_BODY = "Please find attached order management feedback details for the period ";
    public static final String MOBILE_FEEDBACK_EMAIL_MESSAGE_BODY = "Please find attached mobile app feedback details for the period ";
    public static final String EMAIL_MESSAGE_BODY_WHEN_NO_FEEDBACK = "There is no feedback on the order management for the period ";
    public static final String MOBILE_EMAIL_MESSAGE_BODY_WHEN_NO_FEEDBACK = "There is no feedback on the mobile app for the period ";
    public static final String FEEDBACK_EMAIL_MESSAGE_BODY_TO = " to ";
    public static final String FEEDBACK_EMAIL_MESSAGE_BODY_DOT = ".";
    public static final String FEEDBACK_EMAIL_MESSAGE_DATE = "yyyy/MM/dd";
    public static final String FEEDBACK_DAYS_INTERVAL = "feedback_days_interval";
    public static final int DEFAULT_FEEDBACK_DAYS_INTERVAL = 7;
    public static final String PROVIDER_FEEDBACK_INDICATOR="feedback";
    public static final String OM_REPORT="OM";
    public static final String MOBILE_REPORT="MOBILE";
    public static final String XLSX_FORMAT= ".xlsx";
    public static final String PROVIDER_LEGAL_NOTICE_INDICATOR="legal_notice";
    public static final String LEGAL_NOTICE_SAVE_LOCATION= "legal_notice_save_location";
    // For KYC batch (OFACFeedScheduler)
    public static final String KYC_FILE_NAME_FORMAT="MMddyyyy_HHmmss";
    public static final String KYC_DATE_FORMAT="yyyyMMddHHmmss";
    public static final String KYC_TIME_ZONE="EST5EDT";
    public static final String KYC_FILE_DIR="ofac_file_dir";
    public static final String KYC_TRANSACTION_AMOUNT="000000000000";
    
    
    
	public static final String  SHARE_BKND_CHK="Shared existing background check results";
	public static final String  RECERT_SUB="&ldquo;Perform Recertification&rdquo; clicked.";
	public static final String  NEW_SCREENING="&ldquo;Perform Background Check Now&rdquo; clicked.";

	public static final String  SENSITIVE_INFORMATION="This request contains sensitive customer information";
	
	
	//Sl-21645
	public static final String ADD_ESTIMATE_API_EVENT= "order_estimate_added_by_provider";
	public static final String UPDATE_ESTIMATE_API_EVENT= "order_estimate_details_updated_by_provider";
	public static final String UPDATE_ESTIMATE_PRICE_API_EVENT= "order_estimate_price_updated_by_provider";
	public static final String TIME_ONSITE_API_EVENT = "order_checked_out_by_provider";

	// WH when SKU price is updated by buyer
	public static final String ORDER_SPENDLIMIT_UPDATE_FROM_FRONTEND = "order_spendlimit_update_from_frontend";
	
	//SLT-1790
	public static final String ENCRYPTION_KEY_FOR_SOID = "soIdEnKey";
	
	
	
}//MPConstants
