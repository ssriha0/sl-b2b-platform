package com.newco.marketplace.mobile.constants;

import java.util.Arrays;
import java.util.List;


public interface MPConstants {
	
//Mobile Constants
	
	//For login provider
    public static final String TOKEN_EXPIRY_DATE_LIMIT="token_expiry_date_limit";    
    public static final String ENCRYPTION_KEY = "enKey";
    public static final String ACTIVE = "ACTIVE";
    //For So Details Retrieve
    public static final String SO_DETAILS="SODetails";
    public static final String COMPLETION_DETAILS="CompletionDetails";
    public static final String YES="Y";
    public static final String NO="N";
    public static final String ONE="1";
    public static final String TWO="2";
	public static final String REQUIRED_DATE_FORMAT = "yyyy-MM-dd";
	public static final String HSR_BUYER_ID = "3000";
	public static final String DEFAULT_START_TIME="00:00 AM";
	public static final int VISIBLE_NO_OF_DIGITS_CREDIT_CARD=4;
	public static final int QUANTITY_ONE=1;
	public static final String PERMIT_PROVIDER_PAID="100.0%";
	public static final String NOT_AVAILABLE = "Product At Job Site";
	public static final String AVAILABLE = "Available";
	public static final String EMPTY_STRING = "";
	//For provider get so list
	public static final String BASE_URL="mobile_logo_base_url";
	public static final String PATH_URL="mobile_logo_path_url";
	public static final String REQUESTED="Requested";
	public static final String REQUIRED="Required";
	// for update so completion
	public static final Integer Building_TYPE=1;
	public static final Integer Electrical_TYPE=2;
	public static final Integer Plumbing_TYPE=3;
	public static final String BUILDING="Building";  
	public static final String ELECTICAL="Electrical";
	public static final String PLUMBING="Plumbing";			
	public static final String CANCEL="Cancel";
	public static final Integer PARTIAL_SUBSTATUS_ID=134;
	public static final Integer CNH_REVISIT_NEEDED_SUBSTATUS_ID=137;
	public static final String CUSTOMER_NOT_HOME = "Customer Not Home";
	public static final Integer CANCEL_SUBSTATUS_ID=37;
	public static final Integer PENDING_CLAIM_SUBSTATUS_ID=135;
	public static final Integer WORK_STARTED_SUBSTATUS_ID=136;
	public static final Integer PROVIDER_ON_SITE_SUBSTATUS_ID=20;
	
	public static final String SERVICE_PARTIAL = "Partial";
	
	public static final String PROVIDER = "PROVIDER";
	public static final String CUSTOMER = "CUSTOMER";
	
	public static final int OTHER_REASON = 3;	
	public static final String SERVICE_COMPLETE = "Complete";
	public static final String WORK_STARTED = "Work Started";
	public static final String WORK_NOT_STARTED = "Work not started";

	public static final String JOB_ON_SITE = "Provider on site";

	public static final String SERVICE_COMPLETED = "Completed";
	public static final String PENDING_CLAIM  ="Pending Claim";
	public static final String PERMIT = "Permit";
	public static final String ADDON = "Addon";
	public static final Integer AUTO_GENERATED_IND_TRUE=1;
	public static final Integer AUTO_GENERATED_IND_FALSE=0;
	public static final String CREDIT_CARD="Credit Card";
	public static final String CHECK="Check";
	public static final String PAYMENT_TYPE_CHECK="CA";
	public static final double HSR_COST_TO_INVENTORY = 55.0;
	public static final String PERMIT_SCOPE = "Complete Permit Application(s) Obtain Permit(s) from all applicable municipalities Complete / attend inspection as required by the municipality";
	public static final String PERMIT_SKU = "99888";
	public static final Integer DEFAULT_QTY = 1;
	public static final String COVERAGE_TYPE_CC = "CC";
	public static final String COVERAGE_TYPE_PT = "PT";
	public static final Integer PERMIT_TASK_MISC = 1;
	
	public static final Integer SUBSTATUS_ACTION_ID = 35;
	public static final String SUBSTATUS_DESCRIPTION = "Service Order Sub Status Changed to";
	public static final String REVISIT_NEEDED = "Revisit needed";
	public static final String CNH_REVISIT_NEEDED = "Not Home - Revisit Needed";
	public static final String CANCELLATION_REQUESTED ="Cancellation Request";
	public static final String REVISIT_NEEDED_SCHEDULE_SOURCE = "REVISIT_NEEDED";
	public static final Integer SCHEDULE_NEEDED_SCHEDULE_STATUS = 1;
	public static final Integer REVISIT_SCHEDULED_SCHEDULE_STATUS = 6;
	
	// For SO Revisit Needed Mobile API
	public static final Integer REVISIT_NEEDED_ACTION_ID = 275;
	public static final String REVISIT_NEEDED_DESCRIPTION = "Service Order Appointment Date Changed";
	public static final String LOGGING_DATE_FORMAT = "MM/dd/yy";
	public static final String LOGGING_DATE_TIME_STAMP_FORMAT1 = "MM/dd/yy hh:mm a";
	public static final String DATE_FORMAT_VALIDATION = "yyyy-MM-dd hh:mm a";
	public static final Integer RI_BUYER_ID = 1000;
	public static final Integer HSR_BUYER_ID_INTEGER = 3000;
	
	public static final String[] CARRIER_TYPES = { "UPS","FedEx","DHL","USPS","Other" };
	
	// So Task Types
	public static final Integer PRIMARY_TASK = 0;
	public static final Integer PERMIT_TASK = 1;
	public static final Integer DELIVERY_TASK = 2;
	public static final Integer NON_PRIMARY_TASK = 3;
	
	public static final int SERVICE_INCOMPLETE = 1;

	//for update appointment window
	public static final String DATE_FORMAT_DISPLAYED_FOR_ACCEPT = "yyyy-MM-dd";
	
	//SL-21580: Code change starts
	public static final String DATE_FORMAT_APPENDED_WITH_TIME = "yyyy-MM-dd HH:mm:ss";
	//Code change ends
	
	public static final String TIME_PARAMETER = "time";
	public static final String DATE_PARAMETER = "date";
	
	//For AddNote
	public static final Integer NOTE_ACTION_ID = 12;
	public static final String NOTE_DESCRIPTION = "A Note was created for this service order by Provider";
	public static final String PARTS = "Parts";
	public static final String LABOR = "Labor";
	public static final String NON_SEARS = "Non Sears";
	public static final String NON_SEARS_INPUT = "Non-sears";
	public static final String SEARS_SOURCE = "Sears";
	
	public static final Integer PROVIDER_ROLE = 1;
	
	public static final Integer BUYER_ROLE = 3;
	
	public static final String PDF_GEN_STATUS = "WAITING";
	//added for so Pdf Generation  Batch
	public static final Integer COMPLETED=160;
	public static final String PDF_STATUS_INPROGRESS = "IN PROGRESS";
	public static final String PDF_STATUS_WAITING = "WAITING ";
	public static final String PDF_STATUS_FAILURE = "FAILURE";
	public static final String PDF_STATUS_ERROR = "ERROR";
	public static final String PDF_STATUS_COMPLETED = "COMPLETED";
	public static final String MAIL_HOST="smtp.sears.com";
	public static final Integer MAX_RETRY_COUNT= 5;
	public static final String PDF_TITLE="Signed Customer Copy Including Waiver of Lien";
	public static final String DOC_CATEGORY_ID="Reference";
	public static final String FILE_FORMAT="application/pdf";
	public static final String PDF_FORMAT=".pdf";
	
	public static final String SEARS_REPAIR="SearsRepair";

	public static final Integer ROLE_ID=3;
	public static final String PROOF_OF_PERMIT = "proof of permit";
	public static final String SEARS_BUYER = "1000";
	public static final String CUSTOMER_SIGNATURE = "customer signature";
	public static final String PROVIDER_SIGNATURE = "provider signature";
	public static final String CUSTOMER_SIGNATURE_IND = "CUSTOMER";
	public static final String PROVIDER_SIGNATURE_IND = "PROVIDER";
	public static final Integer SIGNATURE_DOC_FORMAT=4;
	public static final String ORDER_NOT_COMPLETED = "Service order is not completed";
	public static final String SIGNATURE_INVALID_FORMAT=": Signature format Invalid";
	public static final String MOBILE_PDF_EMAIL_FROM="servicelive@contact.servicelive.com";
	public static final String MOBILE_PDF_EMAIL_CONTENT="This is a system generated PDF of completed Order";
	public static final String MOBILE_PDF_EMAIL_SUBJECT="Signed Customer Copy Including Waiver of Lien";
	public static final String MOBILE_PDF_DUPLICATE_ENTRY="There is already an entry for the service order in documents";	
	public static final String MOBILE_PDF_INVALID_SO_STATUS="Document upload is not allowed in the current SO status";
	public static final String MOBILE_PDF_INVALID_FIRM_ASSOC="invalid service order and provider firm association";
	public static final String MOBILE_PDF_EMAIL_CONTENT_TEXT1="Dear ";
	public static final String MOBILE_PDF_EMAIL_CONTENT_TEXT2="Customer";
	public static final String MOBILE_PDF_EMAIL_CONTENT_TEXT3=",\n\n";
	public static final String MOBILE_PDF_EMAIL_CONTENT_TEXT4="Please find attached the Signed customer copy including waiver of lien for the service order";
	public static final String MOBILE_PDF_EMAIL_CONTENT_TEXT5=". If you have any questions, please contact us at support@servicelive.com\n\n";
	public static final String MOBILE_PDF_EMAIL_CONTENT_TEXT6="Sincerely,\nYour ServiceLive Support Team\n";
	//time on site
	public static final String EVENT_TYPE_ARRIVAL = "1";
	public static final String EVENT_TYPE_DEPARTURE = "2";
	public static final String PROOF_OF_PERMIT_FOR_UPLOAD = "Proof of Permit";
	public static final String CUSTOMER_SIGNATURE_FOR_UPLOAD = "Customer Signature";
	public static final String PROVIDER_SIGNATURE_FOR_UPLOAD = "Provider Signature";
	public static final String COMPLETED_TASK = "COMPLETED";
	public static final Object TASK_LEVEL = "TASK_LEVEL"; 
	
	
	public static final String SIGNATURE_NO_OF_ORDERS = "signature_no_of_orders";
	public static final String SIGNATURE_MAX_NO_OF_RETRY = "signature_max_no_of_retry";
	public static final String DIGITAL_SIGNATURE_EMAIL_SWITCH = "digital_signature_email_switch";
	public static final String SIGNATURE_NO_OF_ORDERS_DEFAULT = "10";
	public static final String SIGNATURE_MAX_NO_OF_RETRY_DEFAULT = "5";
	
	public static final Integer TIME_WINDOW_CALL_ATTEMPTED = 4;
	public static final Integer TIME_WINDOW_CALL_COMPLETED = 5;
	//Added for Credit Card & Accno
	public static final String CC_ENCRYPTION_KEY = "CCENKEY";
	
	public static final Integer CUST_PARTIAL_CONTACT = 4;
	public static final Integer CUST_INVALID_NUMBER = 5;
	public static final Integer CUST_LEFT_MESSAGE = 6;
	
	public static final Integer UPDATE_TIME_ACTION_ID = 274;
	public static final String UPDATE_TIME_ACTION_DESC = "Appointment time updated to";
	public static final String UPDATE_TIME_ACTION_DESC_STATUS = "The schedule status is ";
	
	public static final String TIME_WINDOW_CALL_ATTEMPTED_DESC = "Time Window-Call Attempted (Not Confirmed)";
	public static final String TIME_WINDOW_CALL_COMPLETED_DESC = "Time Window-Call Completed (Confirmed)";	
	
	public static final String WHITE_SPACE = " ";
	public static final String DOT = ".";
	public static final String HYPHEN = "-";
	
	public static final String SO_COMPLETION_ERROR = "Service Order Completion Error.";
	public static final Integer COMPLETION_ACTION_ID = 15;
	
	public static final String OPEN = "OPEN";
	public static final String UPDATE = "UPDATE";
	public static final String END = "ENDED";
	public static final String MOBILE = "MOBILE";
	
	public static final String REVISIT_NEEDED_CHECKOUT = "Revisit Needed";
	
	public static final String PHOTOS_DOCUMENTS = "PHOTOS_DOCUMENTS";
	public static final String TRIP_ADDON = "ADDON";
	public static final String TRIP_TASK = "TASK";
	public static final String TRIP_PERMIT_TASK = "PERMIT_TASK";
	public static final String TRIP_PERMIT_ADDON = "PERMIT_ADDON";
	public static final String TRIP_PARTS = "PARTS";
	public static final String TRIP_HSR_PARTS = "INVOICE_PARTS";
	public static final String TRIP_PAYMENT = "PAYMENT";
	public static final String TRIP_ADDON_PAYMENT = "ADDON_PAYMENT";
	public static final String TRIP_REFERENCE = "REFERENCE";
	public static final String TRIP_RESOLUTIONCOMMENT = "RESOLUTION_COMMENT";
	public static final String TRIP_SIGNATURE = "SIGNATURE";
	public static final String UPLOADED = "Uploaded";
	public static final String ADDED = "Added";
	public static final String REMOVED = "Removed";
	public static final String UPDATED = "Updated";
	public static final Integer ACTIVE_ORDER = 155;
	public static final Integer INHOME_BUYER = 3000;
	public static final Integer INVOICE_PART_ID = 190833;
	public static final String  PART_CHANGE_TYPE ="INVOICE_PARTS";
	public static final String  PART_ADDED ="Provider added parts";
	public static final String  PART_UPDATED ="Provider Updated parts";
	public static final String  PART_DELETED ="Provider Deleted parts";
	public static final String  PART_INVOICE_ADDED ="Provider added invoice to the parts";
	public static final String CORRELATION_ID="1168457570";
	public static final String RESPONSE_CODE ="00";
	public static final String RESPONSE_MESSAGE ="Success";
	public static final String SUCCESS = "Success";
	public static final String MESSAGE1="No Error Message";
	public static final String MESSAGE2="No More Error Messages";
	public static final String PART_NO ="ASK165S-VV-HH";
	public static final String DESCRIPTION="FILTER-DRIER,SUCTION,5/8ODF";
	public static final String BRAND="EMERSON FLOW CONTROLS";
	public static final String LPN_SERVICE_ID = "4";
	public static final String LPN_URL="LPNUrl";
	public static final String LPN_HEADER ="LPNClientHeader";
	public static final String COVERAGE_TYPE_LABOR = "CoverageTypeLabor";
	public static final String COVERAGE_TYPE_LABOR_PA = "PA";
	public static final String COVERAGE_TYPE_LABOR_IW = "IW";
	public static final String COVERAGE_TYPE_LABOR_SP = "SP";
	public static final String PROTECTION_AGREEMENT = "Protection Agreement";
	public static final String IN_WARRANTY = "In-Warranty";
	
	//R12_0: Sprint 4
	public static final String[] INVOICE_PARTS_TYPES = {"NO_PARTS_ADDED","PARTS_ADDED","NO_PARTS_REQUIRED"};
	public static final String INDICATOR_PARTS_ADDED = "PARTS_ADDED";
	public static final String INDICATOR_NO_PARTS_ADDED = "NO_PARTS_ADDED";
	public static final String INDICATOR_NO_PARTS_REQUIRED= "NO_PARTS_REQUIRED";
	
	//r12_0 sPRINT 5
	public static final String WEB = "WEB";
	public static final String SOURCE = "Source";
	public static final String COVERAGE = "Coverage";
	public static final String MISMATCH_ERROR = "Duplicate records found. Edit for each part";
	public static final String PAY_ROLL_TRANSFER = "Pay Roll Transfer";
	public static final String CUSTOMER_COLLECT = "Customer Collect";
	public static final String COVERAGE_TYPE_LABOR_PT = "PT";
	public static final String COVERAGE_TYPE_LABOR_CC = "CC";
	//SL-21811-public static final Double RETAIL_MAX = 2500.00;

	//R12.0 Sprint7
	public static final String TRIP_NUMBER_DEFAULT = "999";
	public static final String PARTS_MESSAGE1 = "Part status for";
	public static final String PARTS_MESSAGE2 = " has been updated from ";
	public static final String TO = " to ";
	public static final String PARTS_ADD_MESSAGE = " has been added to the order.";
	public static final String PARTS_DELETE_MESSAGE = " has been deleted.";
	public static final String PARTS_INVOICE = "parts invoice";
	public static final String TRUCK_STOCK = "Truck Stock";
	public static final String PARTS_STATUS_INSTALLED = "Installed";
	public static final String VELOCITY_TEMPLATE = "Velocity Template";
	//R 12_0_2 Constants
	public static final String PART_SOURCE_MANUAL = "MANUAL";
	public static final String PART_SOURCE_LIS = "LIS";
	public static final String MANUAL_NO = "NO";

	public static final String ADJUDICATION_PRICE_PERC_KEY="adjudication_commercial_discount_percentage";
	public static final String INVOICE_PARTS_REIMBURSEMENT_PRICING_MODEL="REIMBURSEMENT";
	public static final String INVOICE_PARTS_COSTPLUS_PRICING_MODEL="COST_PLUS";
	public static final String DIGITAL_SIGNATURE = "_digital_reciept";
	public static final String STATUS = "Status";
	public static final String SUB_STATUS = "SubStatus";
	public static final String SO_ID = "SoId";
	public static final String SERVICE_DATE = "ServiceDate";
	public static final String SPEND_LIMIT = "SpendLimit";
	public static final String TIME_TO_APPOINTMENT = "TimeToAppointment";
	public static final String AGE_OF_ORDER = "AgeOfOrder";
	public static final String PROVIDER_LAST_NAME = "ProviderLastName";
	public static final String CITY = "City";
	public static final String SPEND_LIMIT_TOTAL = "SpendLimitTotal";
	public final static int PROBLEM_STATUS = 170;
	public final static String REPORT_A_PROBLEM="ReportProblem";
	public final static String RESOLVE_PROBLRM ="ResolveProblem";
	public static final String [] RESPOND_TYPES = {"Accept","Reject","Cancel"};
	public static final String RESCHEDULE_ACCEPT = "Accept";
	public static final String RESCHEDULE_CANCEL = "Cancel";
	public static final String RESCHEDULE_REJECT = "Reject";
	public static final Integer RESCHEDUL_LOG_ID = 36;
	
	// PRE CALL API
		public static final String PRE_CALL = "PRE_CALL";
		public static final String CONFIRM_APPOINTMENT = "CONFIRM_APPOINTMENT";
		public static final int PRE_CALL_COMPLETED = 3;
		public static final int PRE_CALL_ATTEMPTED = 2;
		public static final int CONFIRM_APPOINTMENT_COMPLETED = 5;
		public static final int CONFIRM_APPOINTMENT_ATTEMPTED = 4;
		public static final String PRE_CALL_COMPLETED_REASON = "7";
		public static final String TIME_WINDOW_CALL_COMPLETED_REASON = "8";
		public static final Integer RESCHEDULE_REASON = 3;
		public static final Integer UPDATE_SERVICE_WINDOW = 2;
		public static final String DATE_FORMAT_IN_DB = "yyyy-MM-dd";
		public static final String TIME_FORMAT_IN_DB = "HH:mm:ss";
		public static final String TIME_STAMP_FORMAT_IN_DB_TWENTY_FOUR = "yyyy-MM-dd HH:mm:ss";
		public static final String TIME_STAMP_FORMAT_IN_DB_TWELVE_HOUR = "yyyy-MM-dd hh:mm:ss";
		public static final String DATE_RANGE = "Enter Range";
		public static final String PRE_CALL_ACTION = "Pre Call";
		public static final String CONFIRM_APPOINTMENT_ACTION = "Confirm Appointment";
		public static final int SEARS_BUYER_INT = 1000;
		
		//R14.0 AssignReassign API
		public static final String ASSIGN_SO = "Assign";
		public static final String REASSIGN_SO = "Reassign";
		
	//V3/0 APIs
	public static final Integer ROLE_LEVEL_ONE = 1;
	public static final Integer ROLE_LEVEL_TWO = 2;
	public static final Integer ROLE_LEVEL_THREE = 3;

	// R14.0 Role level validations
	public static final int SOM_PERMISSION = 4;
	public static final int VIEW_ORDER_PRICING_PERMISSION = 59;
	//GetSoList API v3.0 
	public static final int PAGE_NO_LIMIT = 25;
	public static final int PAGE_SIZE_LIMIT = 250;
	public static final String SORT_FLAG = "set";
	public static final String SORT_BY  = "AppointmentDate";
	public static final Integer DEFAULT_PAGE_NO = 1;
	public static final Integer DEFAULT_PAGE_SIZE = 10;
	public static final String ACTIVE_STATUS ="Active";
	public static final String UNDERSCORE = "_";
	public static final String [] VALID_STATUS_ARRAY = {"Active","Accepted","Problem"};
	public static final String BUYER_LOGO_BASE_URL = "baseUrl";
	public static final String BUYER_LOGO_PATH_URL = "pathUrl";
	public static final String PATH_URL_TESTING = "/appl/sl/doc/";
	public static final String DMY_DATE_FORMAT = "yyyy-MM-dd";
	public static final String RANGE = "RANGE";
	//R14.0 GetFilter API
	public static final String [] CRITERIA_NAME_ARRAY = {"MARKET","STATUS","SUBSTATUS","PROVIDER_RESOURCE","APPOINTMENT_TYPE","APPOINTMENT_START_DATE","APPOINTMENT_END_DATE","ORDER_FLAGGED","ORDER_UNASSIGNED"};
	public static final String MARKET="MARKET";
	public static final String ORDERSTATUS="STATUS";
	public static final String ORDERSUBSTATUS="SUBSTATUS";
	public static final String PROVIDER_RESOURCE="PROVIDER_RESOURCE";
	public static final String APPOINTMENT_TYPE="APPOINTMENT_TYPE";
	public static final String APPOINTMENT_START_DATE="APPOINTMENT_START_DATE";
	public static final String APPOINTMENT_END_DATE="APPOINTMENT_END_DATE";
	public static final String ORDER_FLAGGED="ORDER_FLAGGED";
	public static final String ORDER_UNASSIGNED="ORDER_UNASSIGNED";
	public static final String FLAG_VALUE="1";
	public static final String UNFLAG_VALUE="0";
	public static final String UNASSIGNED_VALUE="1";
	public static final String ASSIGNED_VALUE="0";
	public static final String APPOINTMENT_VALUE_RANGE="Range";
	public static final String MARKET_CRITERIA_LIST="criteriaValueMarketList";
	public static final String STATUS_CRITERIA_LIST="criteriaValueStatusList";
	public static final String SUBSTATUS_CRITERIA_LIST="criteriaValuesubStatusList";
	public static final String PROVIDER_RESOURCE_CRITERIA_LIST="criteriaValueserviceProNameList";
	
	
	
	public static final String QUERY_PARAM_PAGE_NO = "pageno";
	public static final String QUERY_PARAM_PAGE_SIZE = "pagesize";
	public static final String GET_RECIEVED_ORDERS_SUCCESS_MESSAGE = "Received Order details fetched successfully";
	public static final String PRODUCT_AVAILABILITY_APPLICABLE = "Applicable";
	
	public static final String DELETE_FILTER_FILTER_ID = "filterid";
	public static final Integer [] STATUS_ARRAY_WITH_RECIEVED = {110,150,155,170};
	public static final Integer [] STATUS_ARRAY_WITH_OUT_RECIEVED = {150,155,170};	
	
	//R12_3 Constants
	public static final String TRIP_ENDED_STATUS = "ENDED";
	public static final String TRIP_REOPEN_STATUS = "REOPEN";
	public static final String SIGNED_CUSTOMER_COPY_INCLUDING_WAIVER_OF_LIEN = "Signed Customer Copy Including Waiver of Lien";
	public static final Integer NO_SUBSTATUS = 52;
	public static final int LEVEL_NOT_ONE_MAX_TAB_LIST = 5;
	public static final int LEVEL_ONE_MAX_TAB_LIST = 3;
	public static final String RECIEVED_TAB = "Received";
	public static final String ACCEPTED_TAB = "Accepted";
	public static final String ACTIVE_TAB = "Active";
	public static final String PROBLEM_TAB = "Problem";
	//R16_2_0_1: SL-21266: Including bidcount in view dashboard service v3.1
	public static final String BID_REQUESTS = "Bid Requests";
	public static final List<String> LEVEL_ONE_TAB_LIST = Arrays.asList(
			ACCEPTED_TAB,
			ACTIVE_TAB,
			PROBLEM_TAB);
	public static final List<String> LEVEL_NOT_ONE_TAB_LIST = Arrays.asList(
			RECIEVED_TAB,
			ACCEPTED_TAB,
			ACTIVE_TAB,
			PROBLEM_TAB,
			BID_REQUESTS);
	public static final Integer ZERO_COUNT = 0;
	//R12_4 Constants
	public static final String DOCUMENT_TYPE_LOGO = "Logo";
	public static final String ERROR_LOGO_PRESENT_IN_TEMPLATE = "Cannot delete logo since it is associated with a template";
	public static final String NO_BUYER_LOGO = "Don't display a logo";
	public static final Integer NO_BUYER_LOGO_ID = 0;
	//SL-20838
	public static final String FIRM= "FIRM";
	public static final String RETURN_ROLE_THREE_FOR_MOBILE="return_role_three_for_mobile";  
	public static final String TRUE="true";   
	public static final String FALSE="false";   
	//R15_2 Constants
	public static final String LIS_APPKEY = "LISUrl";
	public static final String LIS_PARTNUMBER= "partNo";
	public static final String LIS_APIKEY ="apikey";
	public static final String LIS_API_KEY ="API-Key";
	public static final String LIS_CONSUMER_KEY ="IzeKXKkWmMySLLt9Vg0EkPYSEl1sq9HN";
	public static final String LIS_ERROR_RETURN="Some error occured.Try again later";
	public static final String LIS_TIMEOUT_ERROR="TimeOut happen.Please try again later";
	public static final String LIS_MODEL_APPKEY = "LISModelUrl";
	public static final String LIS_MODEL_DETAILS_APPKEY = "LISModelDetailsUrl";
	
	//PCI Phase II Changes --START
	
	public static final String RESPONSE_NAMESPACE = "http://base.hs.searshc.com/Response/";
	public static final String RESPONSE_NAMESPACE_END = "http://service.credit.som.hs.searshc.com/Response/CreditAuth/";
	
	 public static final String INETBASED = "I";
	    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	    public static final String HSS_APPROVED ="APPROVED";
	    public static final String HSS_RESPONSE_CODE ="00";
	    public static final String HSS_RESPONSE_MESSAGE ="SUCCESS";
	    public static final String HSS_AUTH_CD ="000";
	    public static final String HSS_STORE_NUM ="000";
	    //public static final String HSS_HEADER = "clientid:SLIVE,currentdatetime:2014,servicename:HSSOMCreditAuth,userid:athakkar,serviceversion:1.0";
	    public static final String NAMESPACE = "http://service.credit.som.hs.searshc.com/Request/CreditAuth/";
	    public static final String CREDIT_CARD_HS_TOKENIZE_URL = "webservices.hs.tokenize.url";
	    public static final String CREDIT_CARD_HS_AUTH_HEADER = "webservices.hs.header";
	    public static final String CREDIT_CARD_HDR_END = ",userid:";
	    public static final String  SUCCESS_CODE = "00";
	    public static final String  ERROR_MESSAGE = "This transaction was not approved; please verify the card information you provided and try again or contact your credit card company";
	    public static final String AUTHORIZATION = "Full Authorization";
	    public static final String VALIDATION = "Validation";
	    public static final Double TRANS_AMOUNT= 0.0;
	    public static final String TRANS_AMOUNT_STRING= "0.0";
	    public static final String TRANS_AMOUNT_CARD_ADD= "00";
	    public static final char DECIMAL_POINT= '.';
	    public static final String DECIMAL_POINTS= ".";
	    public static final int API_TIME_OUT = 15000;
	    
	    //R15_3 Mobile API
	    
	    public static final Integer [] WF_VALID_STATES_SUBMIT_RESCHEDULE = {150,155,170};
	    public  static final long HOUR_IN_MILLISECONDS = 1000*60*60;
	    public  static final String REASON_SPLIT = "Reason:";
	    public  static final String COMMENTS_SPLIT = "Comments:";
	    
	    public  static final String PART_NO_PARAM= "?partNo=";
	    public  static final String API_KEY_PARAM= "&apikey=";
	    public  static final String GET= "GET";
	    public  static final String ACCEPT= "Accept";
	    public  static final String APP_XML= "application/xml";
	    public  static final int SUCESS_CODE= 200;
	    public  static final String ROUND_OFF_TWO_DECIMAL= "#.##";

	    
		// R14.0 Forget Username Password Services
		public static final String REQUEST_FOR_USERNAME = "UserName";
		public static final String REQUEST_FOR_PASSWORD = "Password";
    
	    //R16_0_2 Get Bid Orders in Received Orders API
		public static final String QUERY_PARAM_BID_ONLY_IND = "bidonly";
		
		public static final String SPECIFIC = "specific";
		public static final String BID_RANGE = "range";
		public  static final String ROUND_OFF_THREE_DECIMAL= "#0.000";
		public  static final String ROUND_OFF_TWO_DECIMAL_WITH_ZEROS= "#0.00";
		public static final int INT_ZERO = 0;
		public static final double MINUS_ONE = -1.0;
		
	    //Mobile API v3.1
	    //Report Problem/ Resolve Problem API
		public final static String REPORT_PROBLEM_v3_1="ReportProblem";
		public final static String RESOLVE_PROBLEM_v3_1 ="ResolveProblem";
		public final static String UPDATE_TIME_WINDOW_v3_1 ="UpdtTimeWindow";
		public final static String RESOLVE_PBM_NOTE_COMMENT = "Issue Resolution";
		
		//Release API
		public static final String RELEASE_FIRM= "FIRM";
	    public static final String RELEASE_REASON_CODE_PROVIDER= "13";
	    public static final String RELEASE_REASON_CODE_FIRM= "14";
	    public static final String RELEASE_SO_ACTION="ReleaseSO";
	    public static final String RELEASE_ACTION_ID_ACCEPTED="24";
	    public static final String RELEASE_ACTION_ID_ACTIVE="26";
	    public static final String RELEASE_ACTION_ID_PROBLEM="52";
	    public static final String RELEASE_ACTION_PERFORMED_ID="1";
	    public static final Integer RELEASE_ACCEPTED_STATUS=150;
	    public static final Integer RELEASE_ACTIVE_STATUS=155;
	    public static final Integer RELEASE_PROBLEM_STATUS=170;
	    
	    //Company Profile API
	    
	    public static final Integer PENDING_APPROVAL_STATUS_ID=13;
	    public static final Integer APPROVED_STATUS_ID=14;
	    public static final Integer OUT_OF_COMPLIANCE_STATUS_ID=25;
	    public static final Integer REVIEWED_STATUS_ID=210;
	    public static final String PENDING_APPROVAL="Pending Approval";
	    public static final String APPROVED="Approved";
	    public static final String OUT_OF_COMPLIANCE="Out of Compliance";
	    public static final String REVIEWED="Reviewed";
	    public static final Integer FOREIGN_IND_TRUE=1;
	    
	    //Update Time Window API
	    public static final String SOURCE_UPDATE_TIME = "UPDATE_TIME";
	    public static final Integer CUSTOMER_CONFIRM_TRUE=1;
	    public static final Integer CUSTOMER_CONFIRM_FALSE=0;
	    public static final Integer SERVICE_TYPE_DATE_RANGE=2;
	    
	    //Get Team Members API
	    public static final String ZERO="0";
	    public static final String MEMBER_ACTIVE="Active";
	    public static final String MEMBER_INACTIVE="InActive";
	    public static final String BG_CHECK_STATUS_NOT_STARTED="Not Started";
	    public static final String NOT_APPLICABLE="N/A";
	    //Pre Call API
	    public static final Integer CUST_NOT_AVAILABLE_REASON_CODE_BLANK=0;
	    public static final Integer CUSTOMER_NOT_AVAILABLE=0;
	    public static final Integer CUSTOMER_AVAILABLE=1;
	    
	    //View Dashboard API 3.1
	    public static final String LEAD_STATUS_NEW="New";
	    public static final String LEAD_STATUS_WORKING="Working";
	    public static final String LEAD_STATUS_SCHEDULED="Scheduled";
	    public static final String LEAD_STATUS_COMPLETED="Completed";
	    public static final String LEAD_STATUS_CANCELLED="Cancelled";
	    public static final String LEAD_STATUS_STALE="Stale";
	    public static final String SL_STATUS_NOT_STARTED="Not Started";
	    public static final String SL_STATUS_PENDING_SUBMISSION="Pending Submission";
	    public static final String SL_STATUS_IN_PROGRESS="In Progress";
	    public static final String SL_STATUS_NOT_CLEARED="Not Cleared";
	    public static final String SL_STATUS_CLEAR="Clear";
	    public static final String SL_STATUS_RE_CERTIFICATION_DUE="Re-Certification Due";
	    public static final String PROVIDER_REGISTRATION_STATUS_APPROVED="Approved";
	    public static final String PROVIDER_REGISTRATION_STATUS_UNAPPROVED="Unapproved";
	    public static final String PIPE="|";
	    public static final String SPLIT_PIPE="\\|";
	    public static final Double DOUBLE_ZERO=0.0;
	    public static final int SPN_INVITE_PERMISSION = 66;
	    //Get SO Details Pro
	    public static final String SO_STATUS_PROBLEM= "Problem";
	    //R16_1 Release Service Order API
	    public static final Integer PROVIDER_RESP_RELEASE_BY_PROVIDER = 5;
		public static final Integer PROVIDER_RESP_RELEASE_BY_FIRM = 7;
		
	    public static final String SO_REVISIT_NEEDED_API_EVENT= "order_revisit_needed_by_provider";//SL-21458
	    public static final String ACTIVE_PENDING_CLAIM_API_EVENT="order_completed_pending_claim_by_provider";//SL-21458
		public static final Integer RELAY_SERVICES_BUYER_ID = 3333;
		public static final Integer TECHTALK_SERVICES_BUYER_ID = 7777;
		public final static String RELAY_SERVICES_NOTIFY_FLAG = "relay_services_notify_flag";
		public static final String ACCEPTED_STATUS="ACCEPTED";
		public static final String DECLINED_STATUS="DECLINED";
		public static final String SERVER_TIMEZONE="CST6CDT";
		public static final String UPDATED_STATUS="UPDATED";
		
		//SL-21458 - Change Webhook event data
		public static final String ADD_ESTIMATE_API_EVENT= "order_estimate_added_by_provider";
		public static final String UPDATE_ESTIMATE_API_EVENT= "order_estimate_details_updated_by_provider";
		public static final String UPDATE_ESTIMATE_PRICE_API_EVENT= "order_estimate_price_updated_by_provider";
		
	
		public static final String ADD_ESTIMATE_API_EVENT_BUYER= "order_estimate_added_by_customer";
		public static final String UPDATE_ESTIMATE_API_EVENT_BUYER= "order_estimate_updated_by_customer";
		public static final String UPDATE_ESTIMATE_PRICE_API_EVENT_BUYER= "order_estimate_price_updated_by_customer";

		public static final String UPDATE_ESTIMATE_STATUS_API_EVENT= "order_estimate_status_updated_by_customer";
		
		public static final String CANCELLATION_REQUESTED_API_EVENT="order_cancellation_requested_by_provider";

		public static final String TIME_ONSITE_API_EVENT= "order_checked_out_by_provider";
		
		public  static final String ROUND_OFF_TWO_DECIMAL_WORKING= "0.00##";
		
		public static final String  SENSITIVE_INFORMATION="This request contains sensitive customer information";
		
		public static final String  GRACE_PERIOD_MESSAGE="URGENT: You must update this app version soon!";
		
		public static final String TRIP_SOURCE_PROVIDER_API = "PROVIDER_API";
		
}
	

