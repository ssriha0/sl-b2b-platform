package com.newco.marketplace.api.provider.constants;

import java.util.Arrays;
import java.util.List;

/**
 * This class acts as a constant file for mobile APIs
 * 
 * @author Infosys
 * @version 1.0
 */
public class ProviderAPIConstant {

	public static final String SCHEMA_INSTANCE = "http://www.w3.org/2001/XMLSchema-instance";
	public static final String XML_VERSION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	public static final String SORESPONSE_VERSION = "1.0";

	// for login provider-Mobile app
	public static final String PRO_LOGIN_REQ_XSD = "proLoginRequest.xsd";
	public static final String PRO_LOGIN_REP_XSD = "proLoginResponse.xsd";
	public static final String LOGIN_PROVIDER_REP_SCHEMALOCATION = "http://www.servicelive.com/namespaces/mobile proLoginResponse.xsd";
	public static final String LOGIN_PROVIDER_REQ_SCHEMALOCATION = "http://www.servicelive.com/namespaces/mobile proLoginResquest.xsd";

	public static final String API_RESULT_FAILURE = "Failure";
	public static final String API_RESULT_SUCCESS = "Success";
	public static final int API_PROVIDER_ROLE_ID = 1;

	public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

	// for time on site
	public static final String EVENT_TYPE_ARRIVAL = "1";
	public static final String EVENT_TYPE_DEPARTURE = "2";
	public static final String TIME_ON_SITE_DEFAULT_TIMEZONE = "CST6CDT";
	public static final int ONSITE_DELETE_IND = 0;
	public static final int ONSITE_VISIT_INPUT_METHOD = 2;

	// Advanced Provider SO Management.
	public static final String ADVANCED_PROVIDER_SO_MANAGEMENT_SCHEMA = "/resources/schemas/mobile/";
	public static final String ADVANCED_PROVIDER_SO_MANAGEMENT_NAMESPACE = "http://www.servicelive.com/namespaces/mobile";
	public static final String SEARCH_PROVIDER_SO_RESPONSE_XSD = "getProviderSOListResponse.xsd";
	public static final String SEARCH_PROVIDER_SO_RESPONSE_SCHEMALOCATION = "http://www.servicelive.com/namespaces/mobile getProviderSOListResponse.xsd";

	// Provider SO list
	public static final String SO_STATUS_ACTIVE = "Active";
	public static final String SO_STATUS_RECEIVED = "Received";
	public static final String SO_STATUS_ROUTED = "Routed";

	// For So Details Retrieve
	public static final String SO_GET_MOBILE_RESPONSE_XSD = "getSoDetailsResponse.xsd";
	public static final String MOBILE_SERVICES_NAMESPACE = "http://www.servicelive.com/namespaces/mobile";
	public static final String MOBILE_SERVICES_SCHEMA = "/resources/schemas/mobile/";
	public static final String SO_GET_MOBILE_RESPONSE_SCHEMALOCATION = "http://www.servicelive.com/namespaces/mobile getSoDetailsResponse.xsd";
	public static final String SO_ID = "soId";
	public static final String SO_SEPERATOR = "_";
	public static final String SEPARATOR = "-";
	public static final String FILTER = "filter";

	// Filter Types
	public static final String SO_DETAILS = "SODetails";
	public static final String COMPLETION_DETAILS = "CompletionDetails";

	// Upload Documents
	public static final String PRO_UPLOAD_DOC_REQ_XSD = "proUploadDocumentRequest.xsd";
	public static final String PRO_UPLOAD_DOC_REP_XSD = "proUploadDocumentResponse.xsd";
	public static final String PRO_UPLOAD_DOC_REP_SCHEMALOCATION = "http://www.servicelive.com/namespaces/mobile proUploadDocumentResponse.xsd";
	public static final String PRO_UPLOAD_DOC_REQ_SCHEMALOCATION = "http://www.servicelive.com/namespaces/mobile proUploadDocumentRequest.xsd";

	public static class AdvancedProviderSOManagement {

		public static final String PAGE_SIZE = "pagesize";
		public static final int DEFAULT_PAGE_SIZE = 10;
		public static final String PAGE_NUMBER = "pageno";
		public static final String RESOURCE_ID = "resourceId";
		public static final String SO_STATUS = "sostatus";
		public static final String PATH_URL = "pathUrl";
		public static final String PATH_URL_TESTING = "/appl/sl/doc/";
		public static final String BASE_URL = "baseUrl";
	}

	// for Update Time Window API
	public static final String EDIT_APPOINTMENT_REQUEST_XSD = "soEditAppointmentRequest.xsd";
	public static final String EDIT_APPOINTMENT_RESPONSE_XSD = "soEditAppointmentResponse.xsd";
	public static final String SORESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/soResponse";
	public static final String OM_RESOURCES_SCHEMAS_V1_0 = "/resources/schemas/mobile/";
	public static final String SORESPONSE_SCHEMALOCATION = "http://www.servicelive.com/namespaces/soResponse soResponse.xsd";

	// AddNote
	public static final String ADD_NOTE_RESQUEST_XSD = "addNoteRequest.xsd";
	public static final String ADD_NOTE_RESPONSE_XSD = "addNoteResponse.xsd";
	public static final String ADD_NOTE_RESPONSE_SCHEMALOCATION = "http://www.servicelive.com/namespaces/mobile addNoteResponse.xsd";
	public final static String NEWCO_ADMIN = "NewCo";
	public final static int ENTITY_ID_SERVICELIVE_OPERATION = 9;
	public static final String PRIVATE = "1";
	public static final String PUBLIC = "0";
	public static final int NOTE_TYPE_SUPPORT_ONE = 1;
	public static final int NOTE_TYPE_GENERAL_TWO = 2;
    
	public static final String COMPLETION_STATUS_CANCEL = "Cancel";
	public static final String COMPLETION_STATUS_COMPLETED = "Complete";
	public static final String HSR_BUYER = "3000";
	public static final String HSR_MAX_VALUE_INVOICE_PARTS = "HSR_MAX_VALUE_INVOICE_PARTS";
	public static final String SERVICE_PARTIAL = "Partial";
	public static final String TASK_ACTIVE = "ACTIVE";
	public static final String TASK_DELETED = "DELETED";
	public static final Integer PERMIT_SKU_TASK_TYPE = 1;
	
	//Action Ids for mobile so logging v1.0
	public static final Integer AUTHENTICATE_USER =1;
	public static final Integer GET_PROVIDER_SO_LIST=2;
	public static final Integer GET_SO_DETAILS_PRO=3;
	public static final Integer UPDATE_SO_COMPLETION=4;
	public static final Integer TIME_ON_SITE=5;
	public static final Integer UPDATE_APPOINTMENT_TIME=6;
	public static final Integer ADD_NOTES=7;
	public static final Integer UPLOAD_DOCUMENT=8;
	public static final Integer DOWNLOAD_DOCUMENT=9;
	public static final Integer CREATE_NEW_PASSWORD_FOR_USER=10;
	
	// For JIRA SL-15
	public static final Integer GET_PROVIDER_PROFILE=11;
	public static final Integer UPDATE_PROVIDER_PROFILE=12;
	public static final Integer GET_PROVIDER_PROFILE_IMAGE=13;
	public static final Integer UPLOAD_PROVIDER_PROFILE_IMAGE=14;
	
	/**Action Ids for mobile so logging v2.0,
	 * The look up table is used for storing these data is lu_mobile_action
	 * Start with 2** to identify it is of version 2.0*/
	public static final Integer TIME_ON_SITE_V2=201;
	public static final Integer SO_REVISIT_NEEDED_V2=202;
	public static final Integer UPDATE_SO_COMPLETION_V2=203;
	public static final Integer UPLOAD_DOCUMENT_V2=204;
	public static final Integer GET_SO_COMP_DETAILS_PRO_V2=205;
	public static final Integer ADD_PROVIDER_SO_PART = 206;
	public static final Integer UPDATE_PROVIDER_PART = 207;
	public static final Integer GET_SO_DETAILS_PRO_V2=208;	
	public static final Integer ADD_PART_INVOICE_INFO=209;
	public static final Integer DELETE_PART_INVOICE_INFO=210;
    public static final Integer FIND_PART_SUPPLIER = 211;
    public static final Integer AUTHENTICATE_USER_V2 =212;
    public static final Integer APP_FEEDBACK = 213;
    public static final Integer AUTHENTICATE_MOBILE_VERSION_CHECK =214;
    public static final Integer UPDATE_MOBILE_VERSION_CHECK =215;
    public static final Integer PART_SEARCH_SERVICE = 250;
    
	
    
    public static final Integer REPORT_A_PROBLEM = 219;
    
    public static final Integer SUBMIT_RESCHEDULE = 234;

    //R14.0 for CounterOffer/WithdrawCounterOffer APi's
    public static final Integer COUNTER_OFFER = 232;
    public static final Integer WITHDRAW_COUNTER_OFFER = 233;
    public static final Integer PROVIDER_RESP_ID_COUNTER_OFFER_PLACED = 2;
    
    //R14.0 for Place Bid/Change Bid APi's
    public static final Integer PLACE_BID = 248;
    public static final Integer CHANGE_BID = 249;
    public static final Integer RELEASE_SO = 225;

    
    public static final Integer UPDATE_SCHEDULE_DETAILS = 229;
    public static final Integer RESPOND_TO_RESCHEDULE = 235;
    
    
   
    
    //R14.0 Advance Search API
    public static final Integer ADVANCE_SEARCH_SO=237;

	//API methods for mobile so logging
	public static final String HTTP_POST="POST";
	public static final String HTTP_GET="GET"; 
	public static final String HTTP_DELETE="DELETE";
	public static final String HTTP_PUT="PUT";
	//Added for AddInvoiceSOProviderPart
	public static final Integer ORDER_ACTIVE =155;
	public static final Integer HSR_BUYER_ID =3000;
    public static final String PART_ID_LIST = "partIdList";
    
    public static final String PROVIDER="Provider";
    public static final String DISCONTINUE_OLD_VERSION_OF_MOBILE_APP="discontinue_old_version_of_mobile_app";
    public static final String DO_VERSION_VALIDATION_OF_MOBILE_APP="do_version_validation_of_mobile_app";
    public static final String FALSE="false";
    public static final String TRUE="true";
    
    //R12.0 Sprint 4: Parts
    public static final String INDICATOR_PARTS_ADDED = "PARTS_ADDED";
	
	//R14.0 for Search SO API
	public static final int PAGE_SIZE_SET_10 = 10;
	public static final int PAGE_SIZE_SET_20 = 20;
	public static final int PAGE_SIZE_SET_50 = 50;
	public static final int PAGE_SIZE_SET_100 = 100;
	public static final int PAGE_SIZE_SET_200 = 200;
	public static final Integer PAGE_SIZE_SET_250 = 250;
	public static final List<Integer> PAGE_SIZE_SET_VALUES = Arrays.asList(
			ProviderAPIConstant.PAGE_SIZE_SET_10,
			ProviderAPIConstant.PAGE_SIZE_SET_20,
			ProviderAPIConstant.PAGE_SIZE_SET_50,
			ProviderAPIConstant.PAGE_SIZE_SET_100,
			ProviderAPIConstant.PAGE_SIZE_SET_200,
			ProviderAPIConstant.PAGE_SIZE_SET_250);
	//R14.0 Reject SO API
	public static final Integer PROVIDER_RESP_REJECTED=3;
	public static final String ONE= "1";
	public static final String REJECT_INVALID_REASON_CODE = "1604.serviceorder.reason.code.error";
	public static final String REJECT_RESOURCE_ID_REQUIRED = "1605.serviceorder.resource.id.warning";
	public static final String REJECT_RESOURCE_ID_INVALID =  "1603.serviceorder.provider.error";
	public static final String GROUP_IND_PARAM = "groupind";
	public static final String GROUPED_SO_IND = "1";
	public static final String NON_GROUPED_SO_IND = "0";
	public static final String REQUEST_MAP = "REQMAP";
	//R14.0 Get Reason Codes API
    public static final String SO_REASONTYPE_LIST ="reasontype";
	public static final String OF_ERROR_CODE = "08";
	
	// R16.0 Forget Username Password Services
	public static final String REQUEST_FOR_USERNAME = "UserName";
	public static final String REQUEST_FOR_PASSWORD = "Password";
	public static final String PROVIDER_ROLE_ID = "1";
	public static final int LOCKED_IND = 1;
	public static final int NEGATIVE_ONE = -1;
	public static final int ZERO = 0;
	public static final String REQUEST_FOR_USERNAME_WITH_EMAIL = "UserNamewithEmail";
	public static final String REQUEST_FOR_USERNAME_WITH_USERID = "UserNamewithUserId";
	
	
	//R14.0 Submit Reschedule API
	public static final String RANGE="Range" ;
	public static final String FIXED="Fixed" ;
	
	//V3/0 APIs
	public static final Integer ROLE_LEVEL_ONE = 1;
	public static final Integer ROLE_LEVEL_TWO = 2;
	public static final Integer ROLE_LEVEL_THREE = 3;
	public static final String SEARCH_CRITERIA_INPUT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String SEARCH_CRITERIA_SEARCH_FORMAT = "yyyy-MM-dd";
	
	public static final Integer DEFAULT_PAGE_SIZE = 10;
	public static final Integer DEFAULT_PAGE_NUMBER = 1;

	
	//R14.0 Release 1 scope v3.0 API's START
	public static final Integer GET_ROUTE_LIST=216;
	public static final Integer SEARCH_SO=217;
	public static final Integer REJECT_SO = 218;
	public static final Integer GET_REASON_CODES = 219;
	public static final Integer MOBILE_SO_ACCEPT_V2 = 220;
	public static final Integer MOBILE_SO_ADVANCE_SEARCH = 221;
	public static final Integer ASSIGN_SERVICE_ORDER=222;
	public static final Integer GET_SO_DETAILS_PRO_V3 =223;
	public static final Integer GET_RECEIVED_ORDERS=224;
	public static final Integer DELETE_FILTER=225;
	public static final Integer UPDATE_FLAG=226;
	public static final Integer GET_FILTER =227;
	public static final Integer SAVE_FILTER =228;
	public static final Integer GET_SO_PROVIDER_LIST =229;
	public static final Integer VIEW_DASHBOARD =230;
	public static final Integer GET_SEARCH_CRITERIA =231;
	public static final Integer VALIDATE_SECURITY=232;

	//R14.0 Release 1 scope v3.0 API's END
	
    //R16.0 for Forget Username-Password  APi's
    public static final Integer FORGET_UNAME_PWD_SERVICE1 = 236;
    public static final Integer FORGET_UNAME_PWD_SERVICE2 = 237;
    
	//v3.1 Mobile API's
	public static final Integer REPORT_PROBLEM_v3_1 = 238;
	public static final Integer RESOLVE_PROBLEM_v3_1 = 239;
	public static final Integer RELEASE_SO_v3_1 = 240;
	public static final Integer UPDATE_TIME_WINDOW_v3_1 = 241;
	public static final Integer UPDATE_SCHEDULE_DETAILS_v3_1 = 242;
	public static final Integer VIEW_COMPANY_PROFILE_v3_1=243;
	public static final Integer VIEW_TEAM_MEMBERS_v3_1 = 244;
	public static final Integer VIEW_DASHBOARD_v3_1 =245;

	public static final String BLANK="";
	 //Add Estimate Mobile APi
    //public static final Integer ADD_ESTIMATE_SO = 239;
    public static final Integer ADD_ESTIMATE_SO = 246;
    //B2C : GetEstimate Details API
    public static final Integer GET_ESTIMATE=247;
	public static final String LABOR_TASK = "LABOR";
	public static final String PARTS_TASK = "PART";
	public static final String AMOUNT = "AMOUNT";
	public static final String PERCENTAGE = "PERCENTAGE";
	public static final String NEW = "NEW";
	public static final String DRAFT_STATUS_YES= "YES";
	public static final String DRAFT_STATUS_NO = "NO";
	public static final String DRAFT = "DRAFT";
	public static final String ZERO_PRICE="0.00";
	
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm aa";
	public static final String DEFAULT_TIME = "00:00 am";
	public static final double ZERO_PRICE_DOUBLE = 0.00 ;
	public static final String SPECIFIC = "Specific";
	public static final String BID_REQUEST = "Bid Request";
	
	public static final String PARTNO = "partno";
	public static final String MODELNO = "modelno";
	public static final String MODELID = "modelid";
	
	/**Action Ids for mobile so logging v3.0,
	 * The look up table is used for storing these data is lu_mobile_action
	 * Start with 3** to identify it is of version 3.0*/
	public static final Integer TIME_ON_SITE_V3=301;
	public static final String CC_VALIDATION_MSG="Please don't enter the credit card number in resolutionComments tag";
	public static final Integer UPDATE_SO_COMPLETION_V3=303;
}