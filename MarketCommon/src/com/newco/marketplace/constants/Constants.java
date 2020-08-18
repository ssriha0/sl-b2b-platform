package com.newco.marketplace.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * $Revision: 1.74 $ $Author: schavda $ $Date: 2008/06/05 19:52:04 $
 */


public final class Constants {

    public final class LocationConstants {
		public static final int ZIP_VALID = 1;
		public static final int ZIP_NOT_VALID = 2;
		public static final int ZIP_STATE_NO_MATCH = 3;
		public static final int LOCATION_TYPE_BILLING = 5;
		public static final int COUNTRY_US = 1;
	}

	public final class DocumentTypes {
		public static final int BUYER = 1;
		public static final int VENDOR = 2;
		public static final int RESOURCE = 3;
		public static final int SERVICEORDER = 4;
		public static final int BUYERDOCUMENT = 5;
		public static final int VENDORCREDENTIAL = 6;
		public static final int SPN = 7;

		public final class CATEGORY {
			public final static int LOGO = 1;
			public final static int CONTRACT = 2;
			public final static int REFERENCE = 3;
			public final static int IMAGE = 4;
			public final static int PROVIDER_PHOTO = 5;
			public final static int VIDEO = 8;
			public final static int SIGNED_CUSTOMER_COPY= 9;
			public final static int PROOF_OF_PERMIT = 10;
			public final static int BOTH_PROOF_OF_PERMIT_AND_SIGNED_CUSTOMER_COPY = 11;
			public final static int OTHER= 12;
			}
	}

	public final class ApplicationContextBeans {
		public static final String SERVICE_ORDER_DOC_DAO = "serviceOrderDocumentDAO";
		public static final String BUYER_DOC_DAO = "ibuyerDocumentDao";
		public static final String SERVICE_ORDER_DAO = "serviceOrderDao";
		public static final String VENDOR_RESOURCE_DAO = "vendorResourceDao";
		public static final String VENDOR_DOCUMENT_DAO = "vendorDocumentDao";
		public static final String RESOURCE_DOCUMENT_DAO = "resourceDocumentDao";
		public static final String ACTION_VALIDATOR_BEAN = "actionValidatorBean";
		public static final String DOCUMENT_BO_BEAN = "documentBO";
		public static final String SERVICE_ORDER_BO_BEAN = "serviceOrderBOTarget";
		public static final String APPLICATION_PROP_DAO = "applicationPropertiesDao";
		public static final String POWER_BUYER_BO_BEAN = "powerBuyerBO";
		public static final String WS_QUEUE_BEAN = "wsPayloadDispatcher";
		public static final String ETM_PROVIDER_SEARCH_BO_BEAN = "etmProviderSearchBO";
		public static final String AUDIT_PROFILE_BO_BEAN = "auditProfileBo";
		public static final String SELECT_PROVIDER_NETWORK_BO_BEAN= "selectProviderNetworkBO";
		public static final String SPN_DOC_DAO = "spnDocumentDao";

	}

	public final class SESSION {
		public static final String SO_SEARCH_RESULT_IDS = "so_search_result_ids";
		public static final String SECURITY_CONTEXT = "SecurityContext";

		public static final String SOD_MSG = "msg";
		public static final String SOD_MSG_CD = "msgcode";
		public static final String SOD_SO_CLOSED_DTO = "soCloseDto";
		public static final String SOD_SO_AUTOCLOSED_INFO_DTO = "soAutoCloseInfoDto";
		public static final String SOD_SO_COMPLETE = "soComplete";
		public static final String SOD_SO_COMPLETE_DTO = "soCompleteDto";
		public static final String SOD_SHIP_CAR = "shippingCarrier";
		public static final String SOD_INC_SO_DTO = "soIncSLDto";
		public static final String SOD_ERR_LIST = "errList";
		public static final String SOD_PRB_RES_SO = "prbResolutionSoVO";
		public static final String SOD_NOTE_DTO = "soNoteDTO";
		public static final String SOD_RSLT_BUY_TO_PROV = "surveyResultsFromBuyerToProvider";
		public static final String SOD_RSLT_PROV_TO_BUY = "surveyResultsFromProviderToBuyer";
		public static final String SOD_SO_STATUS_LIST = "serviceOrderStatusVOList";
		public static final String SOD_ROUTED_RES_ID = "routedResourceId";
		public static final String SOD_MKT_MAKER_RESPONSE_NOTES_SUBJECT = "Market Maker Response";
		public static final String SOD_ISSUE_RESOLUTION_NOTES_SUBJECT = "Issue Resolution";
		public static final String SOD_RESCHEDULE_MSG = "rescheduleMsg";
		public static final String SOD_SPENT_LIMIT_CHECK = "spendLimitMsg";
		public static final String SO_ASSIGNMENT_TYPE = "assignmentType";
		public static final String SO_GROUP_PROVIDER_LIST ="soGroupProviderList";

		public static final String CAME_FROM_WORKFLOW_MONITOR = "cameFromWorkflowMonitor";
		public static final String GOTO_WORKFLOW_TAB = "GOTO_WORKFLOW_TAB";
		public static final String PB_WF_MESSAGE = "PB_WF_MESSAGE";
		public static final String WORKFLOW_DISPLAY_TAB = "WorkFlowDisplayTab";

		public static final String SOD_SO_STATUS = "soStatus";
		public static final String SOD_SO_COMPLETION_RECORD = "fromPage";
		public static final String AUDIT_APPROVAL_SELECTS = "audit_selects";
		public static final String AUDIT_APPROVAL_CREDENTIALS = "audit_cred_selects";
		public static final String AUDIT_TEAM_APPROVAL_SELECTS = "audit_team_selects";
		public static final String AUDIT_TEAM_APPROVAL_CREDENTIALS = "audit_team_cred_selects";
		

		public static final String SIMPLE_BUYER_GUID="simple_buyer_guid";
		public static final String DOCS_IN_CURRENTVISIT_LIST="docsInCurrentVisit";
		
		//Captcha Related Constants
		public static final String VENDOR_BUCK_INDICATOR = "vendorBuckInd";
		public static final String PRIMARY_VENDOR_INDICATOR = "primaryVendorInd";
		public static final String CAPTCHA_ERROR = "captchaError";
		public static final String CAPTCHA_ERROR_POSITION = "captchaErrorPosition";
		public static final String NUMBER_OF_ORDERS_IN_GROUP = "numberOfOrders";
		
		public static final String CANCELLATION_REQUEST_FAILURE = "cancellation_request_failure";
		
		//SL-15642
		public static final String CAME_FROM_ORDER_MANAGEMENT = "cameFromOrderManagement";
	}

	public final class AppPropConstants {

		public final static String APPLICATION_ENVIRONMENT = "dev_qa_application_environment";
		public final static String MARKET_FRONTEND_VERSION = "dev_qa_market_frontend_version";
		public final static String DOC_SAVE_LOC = "document_save_location";
		public final static String FIRM_LOGO_SAVE_LOC = "firm_logo_save_location";		
		public final static String SO_DOC_UPLOAD_LIMIT = "so_total_doc_size_limit";
		public final static String SIMPLE_DOC_PATH = "simple_buyer_save_doc_path";
		public final static String SERVICE_URL = "servicelive_url";
		public final static String VIDEO_BASE_URL = "video_base_url";
		public final static String MAX_PHOTO_SIZE = "max_photo_size_limit";

		public final static String SPN_SAVE_DOC_PATH = "so_save_doc_path";		
		public final static String PERSIST_SPN_DOC_IN_DB = "persist_so_doc_db";
		
		public final static String ENCRYPTION_KEY = "enKey";
		public final static String PROVIDER_SEARCH_DISTANCE_FILTER = "provider_search_distance_filter";
		public final static String EXPLORE_MARKETPLACE_SEARCH_DISTANCE_FILTER = "explore_marketplace_distance_filter";
		public final static String SERVICELIVE_URL = "servicelive_url";

		public final static String STATIC_CONTEXT_ROOT = "static_context_root";
		public final static String RESOURCES_CONTEXT_ROOT = "resources.context";

		public final static String TIME_INTERVALS = "time_intervals";

		public final static String EVICTION_INTERVAL = "eviction_interval";

		public final static String UNFUNDED_SO = "unfunded_so";

		public final static String COMMUNITY_URL = "Community.URL";

		public final static String OMNITURE_URL = "omnitureURL";

		public final static String WAIVE_POSTING_FEE = "waive_posting_fee_promotion";

		public final static String HP_OUTBOUND_WS_URL_PORT = "hp_outbound_ws_url_port";

		public final static String MAX_LOGIN_ATTEMPTS_LIMIT = "max_login_attempts_limit";

		public final static String INTERIM_PWD_EXPIRATION_TIME = "interim_pwd_expiration_time";

		public final static String MAX_SECRET_QUESTION_ATTEMPTS_LIMIT = "max_secret_question_attempts_limit";

		public static final String PB_CLAIM_EXPIRY_TIME_INTERVAL = "expired_claim_time_interval";

		public static final String SERVICE_LIVE_MAILID = "email_sl_no_reply";

		public static final String SERVICE_LIVE_SUPPORT_MAILID = "email_sl_support";

		public static final String PROVIDER_MAX_WITHDRAWAL = "provider_max_withdrawal";

		public static final String PROVIDER_MAX_WITHDRAWAL_NO = "provider_max_withdrawal_no";

		public static final String ASSURANT_INCIDENT_CANCELLATION_REQUEST_MAILID = "email_sl_assurant_cancel_request";

		public static final String NACHA_FILE_DIRECTORY = "nacha_file_directory";
		public static final String NACHA_FILE_ARCHIVE_DIRECTORY = "nacha_file_archive_directory";

		public static final String CLOSED_LOOP_FILE_DIRECTORY = "closed_loop_file_directory";

		//21513
		public static final String CSAT_MAPPING = "csat_mapping_path";
		public static final String CSAT_MAPPING_ARCHIVE = "csat_mapping_archive_path";
		public static final String CSAT_MAPPING_ERROR = "csat_mapping_error_path";		
		public static final String SMTP_SERVER = "smtp_server";
		public static final String CSAT_MAIL_TO = "csat_mail_to";
		public static final String CSAT_MAIL_FROM = "csat_mail_from";
		
		public static final String NACHA_UNBALANCED_FILE_DIRECTORY = "nacha_unbalanced_file_directory";
		public static final String NACHA_UNBALANCED_FILE_ARCHIVE_DIRECTORY = "nacha_unbalanced_file_archive_directory";

		public static final String ORIGINATION_FILE_DIRECTORY = "origination_file_directory";
		public static final String ORIGINATION_FILE_ARCHIVE_DIRECTORY = "origination_file_archive_directory";

		public static final String RETURNS_FILE_DIRECTORY  = "returns_file_directory";
		public static final String RETURNS_FILE_ARCHIVE_DIRECTORY  = "returns_file_archive_directory";

		public static final String ACKNOWLEDGEMENT_FILE_DIRECTORY = "acknowledgement_file_directory";
		public static final String ACKNOWLEDGEMENT_FILE_ARCHIVE_DIRECTORY = "acknowledgement_file_archive_directory";

		public static final String BACKGROUND_CHECK_FILE_DIRECTORY = "background_check_file_directory";
		public static final String BACKGROUND_CHECK_PLUSONE_FILE_DIRECTORY = "background_check_plusone_file_directory";
		public static final String BACKGROUND_CHECK_FILE_ARCHIVE_DIRECTORY = "background_check_file_archive_directory";

		public static final String PTD_FILE_DIRECTORY = "ptd_file_directory";
		public static final String PTD_FILE_ARCHIVE_DIRECTORY = "ptd_file_archive_directory";
		public static final String PTD_ALERT_TO_ADDRESS = "ptd_alert_to_address";
		public static final String PTD_TIME_OF_RUN = "ptd_time_of_run";

		public static final String SIMPLE_BUYER_DEPOSIT_LIMIT = "simple_buyer_deposit_limit";

		public static final String MMH_REGISTRATION_URL = "mmh_registration_url";

		public static final String CREDIT_CARD_AUTH_URL = "webservices.rtca.url";
		
		public static final String NEW_CREDIT_CARD_AUTH_TOKENIZE_URL = "webservices.pci.creditcardauthtokenize.url";
		public static final String NEW_CREDIT_CARD_AUTH_TOKENIZE_XAPIKEY = "webservices.pci.creditcardauthtokenize.xapikey";
		public static final String NEW_CREDIT_CARD_TOKEN_URL = "webservices.pci.creditcardtoken.url";
		public static final String NEW_CREDIT_CARD_TOKEN_CRENDL = "webservices.pci.creditcardtoken.crendl";
		

		public static final String SL_STORE_NO = "sl_store_no";
		public static final String SL_STORE_NO_WO_ZERO = "sl_store_no_wo_zero";
		public static final String GOOGLE_MAP_API_KEY = "google_map_api_key";
		public static final String SO_IMPORT_TOOL_HELP_URL = "so_import_tool_help_url";
		public static final String SO_IMPORT_TOOL_SAMPLE_FILE_URL = "so_import_tool_sample_file_url";

		public static final String DAILY_RECONCILIATION_FILE_DIRECTORY = "daily_reconciliation_file_directory";

		public static final String STAGE_ORDER_WEBSERVICE_END_POINT_URL = "staging_ws_endpoint";

		public static final String ACCOUNT_LOCK_TIME = "account_lock_time";
		public static final String SEARS_BUYER_ID = "sears_buyer_id";
		public static final String HSR_BUYER_ID = "HSR_buyer_id";
		public final static String DOC_WRITE_READ_IND = "doc_write_read_ind";
		
		public final static String ASSURANT_BUYER_ID="assurant_buyer_id";
		public final static String SL_PARTS_ORDER_FILE_DIRECTORY ="sl_parts_order_file_directory";
		public final static String SL_PARTS_ORDER_FILE_ARCHIVE_DIRECTORY="sl_parts_order_file_archive_directory";
		public final static String ASSURANT_PARTS_INFO_EMAIL_ID = "assurant_parts_info_email_id";
		
		public final static String WAIT_TIME_TO_ACCEPT_SERVICE_ORDER = "timer_to_accept_so";
		public final static String SL_BUCKS_SIMPLE = "sl_bucks_simple";
		public final static String SL_BUCKS_PROFESSIONAL = "sl_bucks_professional";
		public final static String PII_EINSSN_RESTRICTED_PATTERN = "payment.pii.validation.invalidPattern";
		
		//SL-15642:Order Mangement Flag
		public final static String ORDER_MANAGEMENT_FLAG = "order_management_flag";
		//NS-104 :Lead Management Flag
		public final static String PROVIDER_LEAD_MANAGEMENT_FLAG = "provider_lead_management_flag";
		public final static String BUYER_LEAD_MANAGEMENT_FLAG = "buyer_lead_management_flag";

		//SL-18979 : SMS Data synch between Vibes and SL
		public final static String VIBES_INPUT_FILE_DIRECTORY = "vibes_input_directory";
		public final static String VIBES_ARCHIVE_FILE_DIRECTORY = "vibes_archive_directory";
		public final static String VIBES_ERROR_FILE_DIRECTORY = "vibes_error_directory";
		
		//SLT-394 : Remove hard coded scripts path for FTP location
		public final static String SL_ENCRYPT_PARTS_FILE_SCRIPT_PATH = "sl_encrypt_parts_file_script_path";
		
		//SLT-1475 : zipcoverage
		public static final String MAPBOX_URL = "map_box_url";
	}
	public final class REDIRECT {
		public static final String DASHBOARD = "dashboardAction.action";
		public static final String LOGIN = "doLogin.action";
		public static final String HOMEPAGE = "homepage.action";
		public static final String LOGINPAGE ="loginAction.action";
	}

	/*
	 * Matches lu_action_master table
	 */
	public final class SO_ACTION {
		public static final String SERVICE_ORDER_CLOSED = "1";
		public static final String SERVICE_ORDER_CANCELLED = "2";
		public static final String SERVICE_ORDER_ROUTED = "3";
		public static final String SERVICE_ORDER_RESCHEDULED = "5";
		public static final String SERVICE_ORDER_SPEND_LIMIT_INCREASED = "6";
		public static final String SERVICE_ORDER_REJECTED = "7";
		public static final String SERVICE_ORDER_ACCEPTED = "8";
		public static final String SERVICE_ORDER_REJECTED_SEND_ALERTS = "9";
		public static final String REPORT_PRB_OR_RESOL = "10";
		public static final String SERVICE_ORDER_ADD_NOTE = "12";
		public static final String RESCHEDULE_SERVICE_ORDER = "13";
		public static final String REPORT_RESOLUTION = "14";
		public static final String SERVICE_ORDER_COMPLETES = "15";
		public static final String PROVIDER_CONDITIONAL_ACCEPTANCE = "16";
		public static final String PROVIDER_WITHDRAWL_CONDITIONAL_OFFER = "17";
		public static final String BUYER_ACCEPTS_CONDITIONAL_OFFER = "18";
		public static final String SERVICE_ORDER_RESPOND_TO_RESCHEDULE_REQUEST = "19";
		public static final String SERVICE_ORDER_CANCEL_RESCHEDULE_REQUEST = "20";
		public static final String SERVICE_ORDER_CREATE_DRAFT = "21";
		public static final String CANCELLATION_REQUEST_TO_PROVIDER_IN_ACT_ST = "22";
		public static final String SERVICE_ORDER_VOID = "23";
		public static final String SERVICE_ORDER_RELEASED_FROM_ACCEPTED = "24";
		public static final String SERVICE_ORDER_DELETE_DRAFT = "25";
		public static final String SERVICE_ORDER_RELEASED_FROM_ACTIVE = "26";
		public static final String SERVICE_ORDER_SAVE_AS_DRAFT = "27";
		public static final String SERVICE_ORDER_ALERT_PROVIDERS = "29";
		public static final String RATED = "30";
		public static final String SERVICE_ORDER_ACTIVATED = "31";
		public static final String SERVICE_ORDER_EXPIRED = "32";
		public static final String CONDITIONAL_OFFER_EXPIRED = "33";
		public static final String SERVICE_ORDER_REASSIGN="53";
		public static final String SERVICE_ORDER_ASSIGN="273";
	}

	public final class CLAIM_HISTORY_CODE {
		public static final int SELF_UNCLAIM_INACTIVE = 1;
		public static final int OVERRIDE_UNCLAIM = 2;
		public static final int EXPIRED_CLAIM = 3;
		public static final int SELF_UNCLAIM_COMPLETE = 4;
		public static final int SELF_UNCLAIM_INCOMPLETE = 5;
		public static final int SELF_UNCLAIM_REQUED = 6;


	}
	public final class PHONE_NUMBERS {
		public static final String BUSINESS_OWNER_PHONE_NUMBER = "business_owner_phone_number";



	}
	public final class EMAIL_ADDRESSES {
		public static final String BUSINESS_OWNER = "business_owner";
		public static final String SERVICELIVE_ADMIN = "email_admin_servicelive";
		public static final String NO_REPLY = "email_sl_no_reply";
		public static final String EMAIL_PROCESS_FAILURE_SUBJECT = "Batch Processing Failed and Threw a Exception";
		public static final String ORIGINATION_PROCESS_FAILURE_SUBJECT = "Origination file not found";
		public static final String ACKNOWLEDGMENT_PROCESS_FAILURE_SUBJECT = "Acknowledgment file not found";
		public static final String ACKNOWLEDGEMENT_PROCESS_FAILURE_BODY = "Acknowledgment file not found in source directory. ACHAcknowledgmentProcessor failed.";
		public static final String ORIGINATION_PROCESS_FAILURE_BODY = "Origination file not found in source directory. ACHOriginationProcessor failed.";
		public static final String DAILY_RECONCILIATION_EMAIL_TO = "daily_reconciliation_email_to";
		public static final String DAILY_RECONCILIATION_EMAIL_BODY = "daily_reconciliation_email_body";
		public static final String CLOSED_LOOP_FILE_WRITE_FAILURE_SUBJECT = "Closed Loop File Writing Process Failed and Threw a Exception";
		public static final String AUDIT_ERROR_EMAIL_TO_ADDRESS = "audit_error_email_to_address";
		public static final String AUDIT_ERROR_EMAIL_FROM_ADDRESS = "audit_error_email_from_address";
		public static final String ACTIVATION_ERROR_EMAIL_TO_ADDRESS = "activation_error_email_to_address";
		public static final String ACTIVATION_ERROR_EMAIL_FROM_ADDRESS = "activation_error_email_from_address";
	}

	public final class SERVLETCONTEXT {
		public static final String STATES_LIST = "stateCodes";
		public static final String HOURS_LIST = "hoursList";
		public static final String MINUTES_LIST = "minutesList";
		public static final String AMPM_LIST = "ampmList";
	}

	public static final Map<Integer, String> CODEMAP=new HashMap<Integer, String>();
	static{
		CODEMAP.put(CLAIM_HISTORY_CODE.SELF_UNCLAIM_INACTIVE, "Claim Overridden/Expired");
		CODEMAP.put(CLAIM_HISTORY_CODE.OVERRIDE_UNCLAIM, "Claim Overridden");
		CODEMAP.put(CLAIM_HISTORY_CODE.EXPIRED_CLAIM, "Claim Expired");
		CODEMAP.put(CLAIM_HISTORY_CODE.SELF_UNCLAIM_COMPLETE, "Claim Action Complete");
		CODEMAP.put(CLAIM_HISTORY_CODE.SELF_UNCLAIM_INCOMPLETE, "Claim Action Incomplete");
		CODEMAP.put(CLAIM_HISTORY_CODE.SELF_UNCLAIM_REQUED, "Claim Re-queued");
	}

	public static final List<String> AJAXACTIONS=new ArrayList<String>();
	static{
		AJAXACTIONS.add("/serviceOrderListSort.action");
		AJAXACTIONS.add("/loadTabData.action");
		AJAXACTIONS.add("/updateSOLocationNotes.action");
		AJAXACTIONS.add("/addNotes.action");
		AJAXACTIONS.add("/acceptServiceOrder.action");
		AJAXACTIONS.add("/assignProvider.action");
		AJAXACTIONS.add("/updateTime.action");
		AJAXACTIONS.add("/cancelReschedule.action");
		AJAXACTIONS.add("/savePrecallDetails.action");
		AJAXACTIONS.add("/updateDataForRequestReschedule.action");
		AJAXACTIONS.add("");
		AJAXACTIONS.add("");
		AJAXACTIONS.add("");
		AJAXACTIONS.add("");
	}

	public final class UserProfileLoggingActions {
		public static final String CREATED = "created";
		public static final String MODIFIED = "modified";
		public static final String REMOVED = "removed";
	}

	public final class UserManagement{
		public static final String NEW_BUYER_USER_TEMPLATE_ID = "Buyer User Registration Email";
		public static final String NEW_SL_ADMIN_USER_TEMPLATE_ID = "SL Admin Registration Email";
		public static final String NEW_PROVIDER_USER_TEMPLATE_ID="Company Registration Email";
		public static final String REMOVE_BUYER_USER_TEMPLATE_ID = "Buyer User Removed";
		public static final String REMOVE_SL_ADMIN_USER_TEMPLATE_ID = "SL Admin User Removed";
		public static final String BUYER_TERMS_CONDITIONS_TYPE = "Buyer Agreement";
		public static final String BUYER_TERMS_CONDITIONS_AGREEMENT = "Buyer Agreement";
		public static final int BUYER_TERMS_CONDITIONS_ID = 2;
		public static final int SUPER_ADMIN_ROLE_ID = 20;

	}

	public final class BuyerAdmin {
		public static final String DOC_WITH_TITLE_EXISTS="210";
		public static final String DOC_WITH_FILENAME_EXISTS="220";
		public static final int DOC_CATEGORY_ID = 3; //Reference Document Type
		public static final int LOGO_DOC_CATEGORY_ID = 1;
		public static final String CATEGORY_TYPE_BUYER_DOC = "Document";
		public static final String CATEGORY_TYPE_BUYER_LOGO = "Logo";
	}
	//Mapping to corresponding sub-status from lu so substatus for Problem
	/*
	 * In IVR if option gets selected 
	 * 
	 */
	public static final Map<Long, Integer> EVENT_SUBSTATUS_MAP =new HashMap<Long, Integer>();
	static{
		EVENT_SUBSTATUS_MAP.put(0L, 52);
		EVENT_SUBSTATUS_MAP.put(1L, 2);
		EVENT_SUBSTATUS_MAP.put(2L, 7);
		EVENT_SUBSTATUS_MAP.put(3L, 10);
		EVENT_SUBSTATUS_MAP.put(4L, 27);
	}
	 //SL-18992 Constants to set the reason code and its description to fix production issue related with IVR
	public static final String CUSTOMERNOTPRESENT ="Customer not present";
	public static final String PROJECTOUTOFSCOPE ="Project out of scope";
	public static final String SITENOTREADY = "Site not ready";
	public static final String WRONGPART = "Wrong part";
	//SL-18992 Mapping the reason code to its proper description
	public static final Map<Long, String> EVENT_REASON_CODE =new HashMap<Long, String>();
	static{
		EVENT_REASON_CODE.put(1L, CUSTOMERNOTPRESENT);
		EVENT_REASON_CODE.put(2L, PROJECTOUTOFSCOPE);
		EVENT_REASON_CODE.put(3L, SITENOTREADY);
		EVENT_REASON_CODE.put(4L, WRONGPART);
	}
	public static final String POWERBUYER = "POWERBUYER";
	
	//Buyer Id
	public static final Integer FACILITIES_BUYER = 4000;
	public static final Integer ATnT_BUYER = 512353;
	
	public static final String CALENDAR_LIST = "calendarList";

	public static final String FM_RELEASE_DATE = "finance_mgr_release_date";

	public static final String RECONCILATION_WORKING_DAYS = "reconcilation_working_days";

	public static final String REASSIGNMENT_NOTE_SUBJECT = "Service order Reassigned" ;

	public static final String WCI_NOT_REQUIRED_ALERT ="sendWCINotRequiredAlert";
	public static final String VERIFIED = "Verified";
	
	public static final String APPROVED = "Approved";
	public static final String PENDING_APPROVAL = "Pending Approval";
	public static final String REVIEWED = "Reviewed";
	public static final int PENDING_APPROVAL_CD = 13;
	public static final int VERIFIED_CD = 14;
	public static final int REVIEWED_CD = 210;
	public static final int PROVIDER_ACTIVATION = -1;
	public static final String SPACE = " ";
	public static final String DOT = ".";
	public static final String PROVIDER_PHOTO_FOLDER = "public";
	public static final String DOCUMENT_TEMPORARY_FOLDER = "/appl/sl/doc/";
	public final class ThumbNail{
		public static final String IMAGE_TYPE_STRING = "image/";
		public static final String TIFF_IMAGE_TYPE_STRING = "image/tiff";
		//If you are changing the value _Thumb, please change it 
		//in query with id 'documents.query' in serviceOrderMap.xml
		public static final String THUMBNAIL_SUFFIX = "_Thumb";
		public static final int THUMB_IMAGE_WIDTH = 64;
		public static final int THUMB_IMAGE_HEIGHT = 64;
	}
	public final class SPN {
		public final class STATUS {
			public final static int INVITED = 10;
			public final static int APPLICANT = 20;
			public final static int NOT_INTERESTED = 30;
			public final static int MEMBER = 40;
			public final static int INACTIVE = 50;
			public final static int REMOVED = 60;
		}

		public final class CRITERIA_TYPE {
			public final static int RESOURCE_CRED = 10;
			public final static int VENDOR_CRED = 20;
			public final static int VENDOR_GENERAL_INS = 30;
			public final static int VENDOR_AUTO_INS = 40;
			public final static int VENDOR_WORKMAN_INS = 50;
			public final static int STAR_RATING = 60;
			public final static int SKILL = 70;
			public final static int LANGUAGE = 80;
			public final static int MIN_SO_CLOSED = 90;
		}

		public final static String INTERESTED_STR = "INTERESTED";
		public final static String NOT_INTERESTED_STR = "NOT_INTERESTED";
	}

	public final class BUYER_TYPE {
		public final static int SIMPLE = 1;
		public final static int PRO = 2;
	}

	public final class COMPANY_ROLE {
		public final static String SUPER_ADMIN = "20";
		public final static int SUPER_ADMIN_ID = 20;
	}

	public final class TIME_CONSTANTS {
		public static final int SECONDS_IN_MINUTE = 60;
		public static final int MILISECONDS_IN_SECOND = 1000;
	}

	public final class IMAGE_COMPRESSION_CONSTANTS {
		public static final int IMAGE_WIDTH = 800;
		public static final int IMAGE_HEIGHT = 600;
	}

	public static final String CAPTCHA_ENABLE = "captchaEnable";
	public static final String IS_CAR_ORDER = "isCarOrder";
	public static final String HSR_MAX_VALUE_INVOICE_PARTS = "maxValueForInvoicePartsHSR";

	public final class SO_QUEUE {
		public static final int SO_QUEUE_CLAIMED_FROM_SEARCH_TAB = 1;
		public static final int SO_QUEUE_CLAIMED_FROM_SEARCH_TAB_FOR_FACILITIES = 73;
		public static final int SO_QUEUE_FOLLOWUP = 2;
		public static final int SO_QUEUE_FOLLOWUP_FACILITIES = 72;
		public static final int SO_QUEUE_STANDARD_CLAIMED = 130;
	}

    public final class OrderFulfillment{
        public final static String USE_NEW_ORDER_FULFILLMENT_PROCESS = "use_new_orderfulfillment_process";
    }

    public final class PriceModel{
        public static final String NAME_PRICE       = "NAME_PRICE";
	    public static final String ZERO_PRICE_BID   = "ZERO_PRICE_BID";
        public static final String BULLETIN         = "BULLETIN";
        public static final String ZERO_PRICE       = "BID";
        public static final String NAME_YOUR_PRICE  = "NYP";
        public static final String ZERO_PRICE_SEALED_BID   = "ZERO_PRICE_SEALED_BID";
    }
    
    public static final Integer  AUTO_ACCEPT=1;
    public static final Integer  AUTO_ACCEPT_DAYS = 14;
    public static final Integer  AUTO_ACCEPT_TIMES = 1;
    public static final Integer SOURCE = 1;
    
    public static final String TRIP_CHARGE_OVERRIDE = "TRIP_CHARGE_OVERRIDE";
    public static final String IS_WITHIN_24HRS = "isWithin24Hours";
    public static final String CANCEL_REASON_CODES = "cancelReasonCodes";
    public static final String SCOPE_CHANGE_TASKS = "scopeChangeTasks";
    public static final String SEARS_BUYER="1000";
    public static final String TECH_TALK_BUYER="7777";
    public static final String DEPOSITION_CODE_LIST = "depositionCodes";
    public static final String ESTIMATION_CUST_REF = "ESTIMATION";
    

	public static final String SPEND_LIMIT_INCREASE_REASON_CODES = "spendLimitReasonCodes";
	
	public static final String SYSTEM = "System";
	public static final String BACKGROUND_CHECK_DESCRIPTION = "30 days - Background check recertification notice sent to PRO ID #";
	public static final String SPN_PRO_ID = "PRO ID # ";
	public static final String SPN_REQUIREMENT_TYPE = " - Background Check";
	public static final String RESOURCE_NAME = "RESOURCE_NAME";
	public static final String FIRM_NAME = "FIRM_NAME";
	public static final String RESOURCE_ID = "RESOURCE_ID";
	public static final String EXPIRATION_DATE = "EXPIRATION_DATE";
	public static final String NOTIFICATION_DATA = "NOTIFICATION_DATA";
 
	//SL 15642 Constants for mail to provider on change of auto accept status
	public static final String FIRM_ID = "FIRM_ID";
	public static final String PROVIDER_FIRST_NAME="PROVIDER_FIRST_NAME";
	public static final String PROVIDER_LAST_NAME="PROVIDER_LAST_NAME";
	public static final String PROVIDER_ADMIN_EMAIL_ID="PROVIDER_ADMIN_EMAIL_ID";
	public static final String BUYER_CONTACT_NAME="BUYER_CONTACT_NAME";
	public static final String RULE_NAME="RULE_NAME";
	public static final String RULES = "RULES";
	public static final String BUYER_EMAIL = "BUYER_EMAIL";
	public static final String BUYER_NAME = "BUYER_NAME";
	public static final String PROVIDER_NAME = "PROVIDER_NAME";
	public static final String PRIMARY_PH = "PRIMARY_PH";
	public static final String ALT_PH = "ALT_PH";
	public static final String SERVICE_LIVE_STATUS = "SERVICE_LIVE_STATUS";
	public static final String RULE_TABLE_HEADER = "<html>" + "<body>"
		+ "<table border='1' style='border-style: solid;'>"
		+ "<tr><th >Rule Name</th> <th>Reason for not Auto Accepting </th>"
		+ "<th>Updated Date & Time</th></tr>";
	
	public static final Integer BACKGROUND_NOTIFICATION_TYPE = 30;
	public static final Integer BACKGROUND_CREDENTIAL_IND = 4;
	public static final int NEXT_MONTH_ADDITION=1;

	public static final String TABLE_HEADER = 
			"<table border='1' style='border:1px solid black;border-color:black;'cellspacing='0'>"
			+ "<tr><th style='font: bold;padding-left: 10px;padding-right: 40px;border-color:black;font-size: 10pt;'>No</th> " 
			+"<th style='font: bold;padding-left: 10px;padding-right: 40px;border-color:black;font-size: 10pt;'>Provider Name</th>"
			+ "<th style='font: bold;padding-left: 10px;padding-right: 40px;border-color:black;font-size: 10pt;'>Resource Id</th>" 
			+ "<th style='font: bold;padding-left: 10px;padding-right: 40px;border-color:black;font-size: 10pt;'>Background Expiration Date</th>"
			+ "</tr>";
	public static final String TABLE_BODY_TR="<tr>";
	public static final String TABLE_BODY_TR_CLOSE="</tr>";
	public static final String TABLE_BODY_TD="<td style='font: bold;padding-left:10px;border-color:black;font-size: 10pt;'>";
	public static final String TABLE_BODY_TD_CLOSE="</td>";
	public static final String TABLE_BODY_TD_NAME="<td style='font: bold;padding-left:10px;border-color:black;font-size: 10pt;'>";
	public static final String TABLE_BODY_TD_DATE="<td style='color: red;font: bold;padding-left:10px;border-color:black;font-size: 10pt;'>";
	public static final String TABLE_FOOTER ="</table>";
	
	
	public static final String EMPTY_STRING ="";
	public static final String COMMA_STRING =",";
	public static final String SPACE_STRING =" ";
    public static final Integer  INTEGER_ONE=1;
    public static final Integer  INTEGER_ZERO=0;
    public static final int  INT_ZERO=0;
    
    //sl-15642:
	public static final String ASSIGNMENT_NOTE_SUBJECT = "Service order Assigned";
	public static final String FROM_ORDER_MANAGEMENT = "fromOrderManagement";
    public static final Integer  SEARS_BUYER_ID=1000;

    public static final String NO_OF_RECORDS_FOR_PERF_SCORE_BATCH = "no_of_records_for_perf_score_batch";
    public static final int DEFAULT_NO_OF_RECORDS_FOR_PERF_SCORE_BATCH = 200;
    public static final String CSAT = "CSAT";
    public static final String ACCEPTED = "ACCEPTED";
    public static final String ACCEPTED_IF_ROUTED = "ACCEPTED_IF_ROUTED";
    public static final String ACCEPTED_BY_FIRM = "ACCEPTED_BY_FIRM";
    public static final String ACCEPTED_BY_FIRM_IF_ROUTED = "ACCEPTED_BY_FIRM_IF_ROUTED";
    public static final String ROUTED = "ROUTED";
    public static final String COMPLETED = "COMPLETED";
    public static final String RELEASED = "RELEASED";
    public static final String REJECTED = "REJECTED";
    public static final String RESCHEDULED = "RESCHEDULED";
    public static final String RESPONSE = "RESPONSE";
    public static final String IVR = "IVR";
    
    public static final String BUYER_ID = "buyerId";
    public static final String TEMPLATE_ID = "templateId";
    
    //R11.0
    public static final Integer NOTIFICATION_SEVEN =7;
    public static final Integer NOTIFICATION_THIRTY = 30;
    public static final Integer NOTIFICATION_ZERO = 0;
    public static final Integer PAST_DUE = -1;
	public static final String BACKGROUND_CHECK_DESCRIPTION_THIRTY = "30 days - Background check recertification notice sent to PRO ID #";
	public static final String BACKGROUND_CHECK_DESCRIPTION_SEVEN = "7 days - Background check recertification notice sent to PRO ID #";
	public static final String BACKGROUND_CHECK_DESCRIPTION_ZERO = "0 days - Background check recertification notice sent to PRO ID #";
	public static final String NEXTLINE_STRING ="<br />";
	public static final String DOT_STRING =".";
	public static final String OPEN_STRING ="(";
	public static final String CLOSE_STRING =")";
	public static final String USERID_STRING = "User Id#";
	public static final String BACKGROUND_STRING = "Background Check Status";
	public static final String SEMICOLON_STRING =":";
	public static final String CLEAR_STRING = "Clear";
	public static final String RECERTIFICATIONEDATE_STRING = "Recertification Due Date";
	public static final String DUE_STRING = "Due in";
	public static final String DAYS_STRING = "days";
	public static final String TABSPACE_STRING ="&nbsp;&nbsp;&nbsp;&nbsp;";
	public static final String BOLDSTART_STRING = "<b>";
	public static final String BOLDEND_STRING = "</b>";
	public static final String COLORSTART_STRING = "<font color=blue>";
	public static final String COLOREND_STRING = "</font>";
	public static final String USTART_STRING = "<u>";
	public static final String UEND_STRING = "</u>";
	public static final String REDSTART_STRING = "<font color=red>";
	public static final String PASTDUE_STRING = "Past Due";
	public static final String MORE_STRING ="more..";
	public static final String DUE_TODAY = "Due Today";
	public static final String PLUSONE_URL = "https://servicelive.plus1solutions.net/?parm1=SERV601790681&parm2=";
	public static final String PARAM3 = "&parm3=";
	public static final String PARAM4 = "&parm4=";
	public static final String RECERTIFICATION_IND ="Y";
	public static final String ANCHORSTART_STRING = "<a href='";
	public static final String QUOTEEND_STRING = "'>";
	public static final String ANCHOREND_STRING = "</a>";
	public static final String RECT_STRING = "Perform Recertification Now";
		
	public static final String IGNORE ="I";
	
	public static final String RATING_N ="N";
	public static final String RATING_Y ="Y";
	public static final String RATING_H ="H";
	public static final String RATING_P ="P";
	public static final String RECERTIFY ="R";

	public static final String  SCREENING_IN_PROCESS= "Screening is in progress";
	
	public static final String  BACKGROUND_CHECK_STATUS_UPDATE="Background Check Status updated from ";
	public static final String  BACKGROUND_CHECK_STATUS_SET="Background Check Status set to  ";
	public static final String  VERIFICATION_DATE_UPDATE="Verification Date updated from ";
	public static final String  REVERIFICATION_DATE_UPDATE="Recertification Date updated from ";
	public static final String  BACKGROUND_CHECK_EXPIRED="Background Check certification expired on ";
	public static final String  BACKGROUND_CHECK_RESULTS_UPDATED="Background Check results updated";
	public static final String  IN_PROCESS="In Process";
	public static final String  PAST_DUE_STRING="Past Due";
	public static final String  STRING_Y="Y";
	public static final String  STRING_N="N";

	
	//R11.0 Param names 
	
			/** */public static final String EXPORT_SELECTED_FORMAT = "selectedFormat";
			/** */public static final String SEARCH_VO = "searchVO";
			/** */public static final String EXPORT_DATE_FORMAT = "MMddyyyyhhmmss";
			/** */public static final String EXPORT_FILE_NAME = "BackgroundCheckStatus_";
			/** */public static final String XLS_FILE_FORMAT = ".xls";
			/** */public static final String CSV_FILE_FORMAT = ".csv";
			/** */public static final String VENDOR_ID = "vendorId";
			/** */public static final String BACKGROUND_STATE = "backgroundState";
			/** */public static final String NOTCLEARED = "NotCleared";
			/** */public static final String NOT_CLEARED = "Not Cleared";
			/** */public static final String BACKGROUND_CHECK_STATUS_DATE_FORMAT = "MM/dd/yyyy";
			/** */public static final String BG_RESOURCE_ID = "resourceId";
		
	
		
		public  final static String BG_SPN_ID_PARAM = "spnId";
		public  final static String BG_STATE_CD_PARAM ="stateCd";
		public  final static String BG_STATUS_PARAM ="status";
		
		public  final static String BG_SPN_MEMBER_NOSPACE ="PFSPNMEMBER";
		public  final static String BG_SPN_MEMBER_SPACE ="PF SPN MEMBER";
		public  final static String BG_SPN_OUTOFCOMPLIANCE_NOSPACE ="PFFIRMOUTOFCOMPLIANCE";
		public  final static String BG_SPN_OUTOFCOMPLIANCE_SPACE ="PF FIRM OUT OF COMPLIANCE";
		
		public  final static String BG_DATATABLE_SEARCHING_PARAM ="searching";
		public  final static String BG_DATATABLE_IDISPLAYSTART_PARAM ="iDisplayStart";
		public  final static String BG_DATATABLE_IDISPLAYLENGTH_PARAM ="iDisplayLength";
		public  final static String BG_DATATABLE_ISORTCOL_0_PARAM ="iSortCol_0";
		public  final static String BG_DATATABLE_SSORTDIR_0_PARAM ="sSortDir_0";
		public  final static String BG_DATATABLE_SSEARCH_PARAM ="sSearch";
		public  final static String BG_DATATABLE_SEECHO_PARAM ="sEcho";
		public  final static String BG_DATATABLE_HUNDRED_PARAM ="100";
		public  final static String BG_DATATABLE_TEN_PARAM ="10";
		
		public  final static String BG_DATATABLE_ADVANCE_FILTER_PARAM ="Select All";
		
		public  final static String BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM1 ="Clear";
		public  final static String BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM2="Not Cleared";
		public  final static String BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM3="In Process";
		public  final static String BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM4="Not Started";
		public  final static String BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM5="Pending Submission";
		public  final static String BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM="sLBackgroundStatusList";

		public  final static String BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM1 ="Past Due";
		public  final static String BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM2 ="Today";
		public  final static String BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM5 ="Within 7 days";
		public  final static String BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM6 ="7 to 30 days";
		public  final static String BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM7 ="In Process";
		public  final static String BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM8 ="InProcess";
		public  final static String BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM ="reCertificationList";
		
		public  final static String BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM1 ="30 Days Notice Sent";
		public  final static String BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM2 ="7 Days Notice Sent";
		public  final static String BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM3 ="Due Today";
		public  final static String BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM ="systemActionList";
		
		public  final static String BG_DATATABLE_ADVANCE_FILTER_BG_STATUS_PARAM_ALL="SLBackgroundStatusAll";
		public  final static String BG_DATATABLE_ADVANCE_FILTER_RECERTIFICATION_PARAM_ALL ="ReCertificationAll";
		public  final static String BG_DATATABLE_ADVANCE_FILTER_SYSTEM_ACTION_PARAM_ALL ="SystemActionAll";
		
		public  final static String BG_DATATABLE_COUNT_RESULTS_PARAM ="count";
		public  final static String BG_DATATABLE_COUNT_RESULTS_EXPORT_PARAM ="totalRecordCount";
		
		public  final static String BG_DATATABLE_SELECTED_BG_STATUS_PARAM="selectedSLBackgroundStatus";
		public  final static String BG_DATATABLE_SELECTED_RECERTIFICATION_PARAM ="selectedReCertification";
		public  final static String BG_DATATABLE_SELECTED_SYSTEM_ACTION_PARAM ="selectedSystemAction";
		public  final static String BG_DATATABLE_SELECTED_FIRMID_PARAM ="selectedProviderFirmId";
		
		public  final static String BG_DEFAULT_SELECTION ="-1";
		public  final static String BG_SEVEN ="7";
		public  final static String BG_THIRTY ="30";
		public  final static String BG_ZERO ="0";
		public  final static String BG_PAST ="Past";
		
		public  final static String BG_DATATABLE_COLUMN_SORT_FIRM="firm";
		public  final static String BG_DATATABLE_COLUMN_SORT_PROVIDER="provider";
		public  final static String BG_DATATABLE_COLUMN_SORT_SLSTATUS="slStatus";
		public  final static String BG_DATATABLE_COLUMN_SORT_CERTDATE="certDate";
		public  final static String BG_DATATABLE_COLUMN_SORT_RECERTDATE="reCertDate";
		public  final static String BG_DATATABLE_COLUMN_SORT_RECERTSTATUS="reCertStatus";
		
		public  final static String BG_LIST="backgroundInfoList";
		public  final static String BG_PROVIDERNAME="providerName";
		public  final static String BG_FORMATTEDHISTLIST="formattedHistList";
		
		
		
		public static final String  IN_PROCESS_STATE= "In Process";
		public static final String  CLEAR_STATE= "Clear";
		public static final String  REQUEST_TYPE_R= "R";
		public  final static String PART_STATUS_INSTALLED="Installed";

		//Business Types
		public static final int S_CORPORATION	= 8;
		public static final int INDIVIDUAL		= 7;

		//Taxpyer type or TIN used
		public static final int EIN 			= 1;
		public static final int SSN 			= 2;
		
		
		public static final Integer ACTIVITY_ID_BUSINESS_INFO = new Integer(1);
		public static final Integer ACTIVITY_INURANCE_INFO = new Integer(4);
		public static final Integer ACTIVITY_LICENSE_INFO = new Integer(3);

		public static final Integer ONE = new Integer(1);
		public static final Integer ZERO = new Integer(0);

		public static final String PENDING_SUBMISSION = "Pending Submission";
		public static final String NOT_STARTED = "Not Started";
		
		public static Map<Integer,String> backgroundCheckMap(){
			Map<Integer,String> backgroundCheck = new HashMap<Integer,String>();
			backgroundCheck.put(28,PENDING_SUBMISSION);
			backgroundCheck.put(10,NOT_CLEARED);
			backgroundCheck.put(9,CLEAR_STATE);
			backgroundCheck.put(8,IN_PROCESS);
			backgroundCheck.put(7,NOT_STARTED);
	        return backgroundCheck;  
		}
		
		public static Map<String, Integer> backgroundCheckStatusMap(){
			Map<String,Integer> backgroundCheck = new HashMap<String,Integer>();
			backgroundCheck.put(PENDING_SUBMISSION,28);
			backgroundCheck.put(NOT_CLEARED,10);
			backgroundCheck.put(CLEAR_STATE,9);
			backgroundCheck.put(IN_PROCESS,8);
			backgroundCheck.put(NOT_STARTED,7);
	        return backgroundCheck;  
		}
		
		// list for providers from 3333, 7777 spn to display provider 
		// added 10254 for api test env
		public static final List<Integer> calendarProviders = Arrays.asList(10013, 31848, 33493, 33970, 37216, 41614, 59182, 63531, 83300,
			96724, 106977, 107885, 110547, 112275, 112361, 10254);

		public static final int SYSTEM_ENTITY_ID = 0;
		public static final String FALSE = "false";
		public static final String BOTH = "Both";
		
		public static final int BUYER_EDIT_ID = 278;
		public static final int INVALID_FIELD_ID = 281;
		
		public static final String BUYER_MODEL_EDIT = "Service Order Model Number has been updated";
		public static final String BUYER_SERIAL_EDIT = "Service Order Serial Number has been updated";
		
		public static final String MODEL_NUM_IMAGE_ERROR = "Page is submitted with the checkbox box checked but no 'Model Number Image' document attached to the order";
		public static final String SERIAL_NUM_IMAGE_ERROR = "Page is submitted with the checkbox box checked but no 'Serial Number Image' document attached to the order";
		
		public static final String INVALID_FIELDS_COMMENT = "Provider has completed the order with invalid Model and Serial Number";
		public static final String INVALID_MODEL_COMMENT = "Provider has completed the order with invalid Model Number";
		public static final String INVALID_SERIAL_COMMENT = "Provider has completed the order with invalid Serial Number";
		
		public static final String MODEL_RULE = "modelRule";
		public static final String SERIAL_RULE = "serialRule";
		public static final String MODEL_SERIAL_IND = "modelSerialInd";
		public static final String MODEL_SERIAL_ERROR = "modelSerialErrorMsgs";
		public static final String MODEL_SERIAL_VALUES = "modelSerialValues";
		
		public static final String MODEL_IMAGE_ERROR = "The Provider has attached an image of the model number to this order. See attachments below";
		public static final String SERIAL_IMAGE_ERROR = "The Provider has attached an image of the serial number to this order. See attachments below";
		public static final String MODEL_INVALID_ERROR = "The Provider has entered a non-standard model number which may not be accepted by the Manufacturer";
		public static final String SERIAL_INVALID_ERROR = "The Provider has entered a non-standard serial number which may not be accepted by the Manufacturer";
		
		public static final String MODEL_SERIAL_EQUAL_ERROR = "Model Number and Serial Number should not be same";
		
		public static final String REF_TYPE = "refType";
		public static final String REF_VAL = "refVal";
		public static final String REF_OLD_VAL = "refValOld";
		public static final String INVALID_IND = "invalidInd";
		//SL-21045
		public static final String ERROR_MSG_FOR_INCR_PRICE = "Error in submitting the new price. Reload the order and try again!";
		
		//SL-18979 : Constants for SMSDataSynch batch
		public static final String TRUE = "TRUE";
		public static final String FALSE_CAPS = "FALSE";
		public static final String OPT_IN = "optIn";
		public static final String OPT_OUT = "optOut";
		public static final String EXTN_CSV = ".csv";
		public static final String PERSON_ID = "person_id";
		public static final String OPT_IN_STATUS = "success/failure";
		public static final String SUBSCRIPTION_EVENT = "subscription_event";
		public static final String MDN = "mdn";
		public static final String OPT_IN_DATE = "opt_in_date";
		public static final String OPT_OUT_DATE = "opt_out_date";
		public static final int VIBES_RECORDS_PROCESSING_LIMIT = 1000;
		public static final String EXTN_TXT = ".txt";
		public static final String EXTN_TXT_CAPS = ".TXT";
		public static final String TAB_DELIMITER ="\\t";
		public static final String PENDING = "PENDING";
		public static final String ACTIVE = "ACTIVE";
		public static final int PERSON_ID_COL = 0;
		public static final int SUB_EVENT_COL = 8;
		public static final int MDN_COL = 2;
		public static final int SUBS_LIST_ID_COL = 5;
		public static final int OPTINDATE_COL = 6;
		public static final int OPTOUTDATE_COL = 7;
		public static final String VIBESSUBSCRIPTIONID = "VIBESSubscriptionId";
		public static final String DELETED = "DELETED";

		//Constants for SMS Data Migration batch
		public static final String STATUS_SUCCESS = "SUCCESS";
		public static final String STATUS_DUPLICATE = "DUPLICATE";
		public static final String STATUS_ERROR = "ERROR";
		public static final String DUPLICATE_MESSAGE = "The sms number is already being used by a provider under another firm";
		public static final String SMS_MIGRATION_BATCH = "SMSMIGRATIONBATCH";
		
		public static final String DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm aa";
		public static final String DATE_FORMAT = "yyyy-MM-dd";
		public static final String TIME_FORMAT = "hh:mm aa";
		public static final String PROVIDER_BID = "Provider Bid";

		//SLT-976
		public static final Integer BG_STATE_NOT_STARTED = 7;
		public static final Integer BG_STATE_PENDING_SUBMISSION = 28;
		public static final Integer BG_STATE_IN_PROCESS = 8;
		public static final Integer BG_STATE_CLEARED = 9;
		public static final Integer BG_STATE_NOT_CLEARED = 10;
		
		//SLT-1894
		public static final String STANDARD_QUEUES = "STANDARD_QUEUES";
		public static final String BUYER_QUEUES = "BUYER_QUEUES";
		
}	
