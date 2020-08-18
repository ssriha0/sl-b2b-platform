/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Jun-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.utils.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.interfaces.OrderConstants;
/**
 * This class acts as a constant file
 * 
 * @author Infosys
 * @version 1.0
 */
public class PublicAPIConstant {
	
	public static final String MODIFIED_BY_PUBLIC_API = "PUBLIC_API";
	
	public static final String CODE_PROPERTY_FILE = "resources/properties/api_responseCodes_enUS.properties";
	public static final String SUCCESS = "SUCCESS";
	public static final String CONFIRM_CUSTOMER_TRUE = "true";
	public static final String CONFIRM_CUSTOMER_FALSE = "false";
	public static final String SLOT_SELETED_TRUE = "true";
	public static final String SLOT_SELETED_FALSE = "false";
	public static final String SOID_NOT_VALID="SoId not valid";
	public static final String LOCATION_TYPE_COMMERCIAL = "Commercial";	
	public static final String LOCATION_TYPE_RESIDENTIAL = "Residential";	

	//SL-19206
	public static final String PROVIDER_ASSIGNED = "assigned provider";
	public static final String PROVIDER_NOT_ASSIGNED = "provider not yet assigned";
    
	public static final String STANDARD_ENGLISH = "English";
	public static final String STANDARD_METRIC = "Metric";
	public static final String DATETYPE_RANGE = "range";
	public static final String DATETYPE_FIXED = "fixed";
	public static final String DATETYPE_PREFERENCES = "preferences";
	
	public static final String INVALID_SOID = "000-0000000000-00";	
	public static final String DRAFT= "Draft";
	public static final String COMPLETED = "Completed";
	public static final String RESCHEDULE_REQUEST = "Reschedule Request Submitted";
	public static final String POST_REQUEST = "Service Order posted to 8 providers";
	public static final String ADD_NOTE_RESULT = "Note Added";
	public static final String POST_RESULT1 = "Service Order routed to ";
	public static final String POST_RESULT2 = " providers";
	public static final String INFO_LEVEL =  "infolevel";

	public static final String ACCEPTED= "Accepted";
	public static final String POSTED= "Posted";
	public static final String ONE= "1";
	public static final String ZERO = "0";	
	public static final int INTEGER_ONE = 1;
	public static final int INTEGER_ZERO = 0;
	public static final int TWO= 2;
	public static final int THREE=3;
	public static final int FIVE =5;	
	public static final String REQUEST_XSD= "soRequest.xsd";
	public static final String REQUEST_XSD_v1_1= "soRequest_v1_1.xsd";
	public static final String REQUEST_XSD_v1_2= "soRequest_v1_2.xsd";
	public static final String REQUEST_XSD_v1_3= "soRequest_v1_3.xsd";
	public static final String SMS_SUB_REQUEST_XSD= "unSubscriptionRequest.xsd";
	public static final String SMS_SUB_RESPONSE_XSD= "unSubscriptionResponse.xsd";
	public static final String SEARCH_XSD= "searchRequest.xsd";	
	public static final String DOCUMENT_RESPONSE_XSD= "deleteResponse.xsd";
	public static final String SORESPONSE_XSD= "soResponse.xsd";
	public static final String SOUPDATERESPONSE_XSD= "soUpdateResponse.xsd";
	public static final String SOGETRESPONSE_XSD= "soRetrieveResponse.xsd";
	public static final String SOGETRESPONSE_XSD_V1_3= "soRetrieveResponseV_1_3.xsd";
	public static final String SOGETRESPONSE_XSD_V1_4= "soRetrieveResponseV_1_4.xsd";
	public static final String SOGETRESPONSE_XSD_V1_5= "soRetrieveResponseV_1_5.xsd";
	public static final String SOGETRESPONSE_XSD_V1_7= "soRetrieveResponseV_1_7.xsd";
	public static final String SOUPDATESTIMATESTATUS_XSD= "soUpdateEstimateStatus.xsd";
	public static final String SOUPDATESTIMATESTATUS_RESPONSE_XSD= "soUpdateEstimateStatusResponse.xsd";
	public static final String SOUPDATESTIMATE_RESPONSE_XSD= "addSOEstimateResponse.xsd";
	public static final String SOUPDATEESTIMATE_XSD= "addEstimateRequest.xsd";
	public static final String SEARCHRESPONSE_XSD= "searchResponse.xsd";
	public static final String SEARCHRESPONSE_PRO_XSD= "searchProResponse.xsd";
	public static final String CANCEL_XSD = "cancelRequest.xsd";
	public static final String RESCHEDULE_XSD="rescheduleRequest.xsd";
	public static final String POST_XSD="postRequest.xsd";
	public static final String ADDNOTE_XSD="addNoteRequest.xsd";
	public static final String DOC_UPLOAD_REQUEST_XSD= "uploadRequest.xsd";
	public static final String DOC_UPLOAD_RESPONSE_XSD= "uploadResponse.xsd";
	public static final String MULTI_DOC_UPLOAD_REQUEST_XSD= "multiUploadRequest.xsd";
	public static final String MULTI_DOC_UPLOAD_RESPONSE_XSD= "multiUploadResponse.xsd";
	public static final String EDIT_XSD="soEditRequest.xsd";
	public static final String UPDATE_XSD="soUpdateRequest.xsd";
	public static final String ACCEPT_RESCHEDULE_XSD="acceptRescheduleRequest.xsd";
	public static final String PROVIDER_RESCHEDULE_RESPONSE_XSD="providerRescheduleResponseRequest.xsd";
	public static final String SEARCH_SUCCESSFUL=" results returned";
	public static final String SEARCH_NOT_SUCCESSFUL="Search Not Successful";
	public static final String RESOLVE_PROBLEM_PRO_REQUEST_XSD="ResolveProblemOnSOProRequest.xsd";
	public static final String REPORT_PROBLEM_PRO_RESPONSE_XSD="ReportProblemProResponse.xsd";
	public static final String REPORT_PROBLEM_PRO_REQUEST_XSD="ReportProblemProRequest.xsd";
	public static final String SOGETSPENDLIMITRESPONSE_XSD="soRetriveResponseForSpendLimit.xsd";
	public static final String REQUESTFORSPENDLIMIT_XSD="soGetRequestForSpendLimit.xsd";
	public static final String SO_CANCEL_XSD = "soCancelRequest.xsd";
	public static final String SO_CANCEL_RESPONSE_XSD = "soCancelResponse.xsd";
	public static final String SO_PRICE_HISTORY_XSD = "soPriceHistoryResponse.xsd";
	//Schema Details
	public static final String XML_VERSION= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	public static final String SORESPONSE_VERSION= "1.0";
	public static final String SEARCH_VERSION = "0.0";
	public static final String SORESPONSE_SCHEMALOCATION= "http://www.servicelive.com/namespaces/soResponse soResponse.xsd";
	public static final String SOACCEPTRESPONSE_SCHEMALOCATION= "http://www.servicelive.com/namespaces/soAcceptResponse soAcceptResponse.xsd";
	public static final String SOACCEPTREJECTRELEASERESPONSE_SCHEMALOCATION= "http://www.servicelive.com/namespaces/soAcceptRejectRelease acceptRejectReleaseSOResponse.xsd";
	public static final String SEARCH_SCHEMALOCATION = "http://www.servicelive.com/namespaces/searchResponse searchResponse.xsd";	
	public static final String PROBLEM_PRO_SCHEMALOCATION="http://www.servicelive.com/namespaces/pro/so ReportProblemProResponse.xsd";
	public static final String SORESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/soResponse";
	public static final String SORESPONSE_SPENDLIMIT_NAMESPACE="http://www.servicelive.com/namespaces/spendLimitHistoryResponse";
	public static final String SORESPONSE_PRO_NAMESPACE ="http://www.servicelive.com/namespaces/pro/soResponse";
	public static final String SOACCEPTRESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/soAccept";
	public static final String SOACCEPTREJECTRELEASERESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/soAcceptRejectRelease";
	public static final String SEARCHRESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/searchResponse";
	public static final String SEARCHRESPONSE_PROVIDER_NAMESPACE = "http://www.servicelive.com/namespaces/pro/searchResponse";
	public static final String PROBLEM_PRO_NAMESPACE="http://www.servicelive.com/namespaces/pro/so";
	public static final String SCHEMA_INSTANCE= "http://www.w3.org/2001/XMLSchema-instance";
	public static final String PRICEHISTORY_NAMESPACE = "http://www.servicelive.com/namespaces/soPriceHistory";
	public static final String PRICEHISTORY_NAMESPACE_SCHEMALOCATION = "http://www.servicelive.com/namespaces/soPriceHistory soPriceHistoryResponse.xsd";
	public static final String GET_SUBSTATUS_NAMESPACE = "http://www.servicelive.com/namespaces/subStatusResponse";
	public static final String GET_SUBSTATUS_NAMESPACE_SCHEMALOCATION = "http://www.servicelive.com/namespaces/subStatusResponse getSubstatusResponse.xsd";
	public static final String SO_FETCH_RESPONSE_SCHEMALOCATION = "http://www.servicelive.com/namespaces/fetchOrderResponse fetchOrderResponse.xsd";
	public static final String SO_FETCH_RESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/fetchOrderResponse";                                                  
	
	public static final String PRO_ADDNOTE_XSD="addNoteProRequest.xsd";
	public static final String SOPROVIDERRESPONSE_XSD= "soProResponse.xsd";
	public static final String SORESPONSE_PROVIDER_NAMESPACE = "http://www.servicelive.com/namespaces/pro/soResponse";
	public static final String SO_RESOURCES_SCHEMAS_PRO = "/resources/schemas/pro/so/";	
	public static final String SOPRORESPONSE_SCHEMALOCATION= "http://www.servicelive.com/namespaces/pro/soResponse soProResponse.xsd";
	
	
	public static final String CREATE_PRO_CONDITIONAL_OFFER_XSD = "createCounterOfferProRequest.xsd";
	public static final String COUNTER_OFFER_PRO_RESPONSE_XSD= "counterOfferProResponse.xsd";
	public static final String COUNTER_OFFER_PRO_NAMESPACE = "http://www.servicelive.com/namespaces/pro/counterOffer";
	
	public static final String PRO_COMPLETE_XSD="completeSOProRequest.xsd";
	public static final String PROVIDER_RESCHEDULE_PRO_RESPONSE_XSD="providerRescheduleResponseProRequest.xsd";
	

	
	//search obsolet - remove it later
	public static final String PROVIDER_SEARCH_RESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/byzip";
	public static final String PROVIDER_SKILLTREE_RESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/skillTree";
	
	//B2C Schema Details
	
	public static final String SEARCH_PROVIDER_NAMESPACE = "http://www.servicelive.com/namespaces/byzip";
	public static final String SEARCH_PROFILEID_SCHEMALOCATION = "http://www.servicelive.com/namespaces/byzip providerResults.xsd";
	public static final String SEARCH_ZIP_SCHEMALOCATION = "http://www.servicelive.com/namespaces/byzip searchProviderByZipCodeResponse.xsd";
	public static final String PROVIDER_RESPONSE_XSD= "providerResults.xsd";
	public static final String DOCRESPONSE_SCHEMALOCATION = "http://www.servicelive.com/namespaces/uploadResponse uploadResponse.xsd";
	public static final String DOCRESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/uploadResponse";
	public static final String DOC_DELETE_RESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/documentResponse";
	
	// For authentication and authorization purposes 
	public static final String LOGIN_USERNAME = "username";	
	public static final String LOGIN_PASSWORD= "password";
	public static final String API_KEY = "apikey";	
	public static final String IDENTIFICATION_USERNAME = "identification/username";	
	public static final String IDENTIFICATION_APPLICATIONKEY = "identification/applicationkey";	
	public static final String IDENTIFICATION_PASSWORD = "identification/password";	
	public static final String SO_RESOURCES_SCHEMAS = "/resources/schemas/so/";	
	public static final String BuyerCallback_RESOURCES_SCHEMAS = "/resources/schemas/buyerEventCallback/";	
	public static final String OM_RESOURCES_SCHEMAS_V1_0 = "/resources/schemas/ordermanagement/v1_0/";
	public static final String SO_RESOURCES_SCHEMAS_V1_1 = "/resources/schemas/so/v1_1/";
	public static final String SO_RESOURCES_SCHEMAS_V1_2 = "/resources/schemas/so/v1_2/";
	public static final String SO_RESOURCES_SCHEMAS_V1_3 = "/resources/schemas/so/v1_3/";
	public static final String SO_RESOURCES_SCHEMAS_V1_4 = "/resources/schemas/so/v1_4/";
	public static final String SO_RESOURCES_SCHEMAS_V1_5 = "/resources/schemas/so/v1_5/";
	public static final String SO_RESOURCES_SCHEMAS_V1_7 = "/resources/schemas/so/v1_7/";
	public static final String UTF = "UTF-8";	
	public static final String SERVICE_ORDER = "serviceorder";
	public static final String FILE_TITLE = "filetitle";
	public static final String DOC_RESOURCES_SCHEMAS = "/resources/schemas/document/";	
	public static final String PROVODER_CAPACITY_V1_1 = "/resources/schemas/provider/capacity/v1_1/";
	public static final String BuyerAUTHENTICATION_RESOURCES_SCHEMAS = "/resources/schemas/buyerauthenticationdetails/";
	
	public static final String SERVICE_ORDER_RESPONSE_XSD= "getServiceOrdersResponse.xsd";
	public static final String PROVIDER_NAMESPACE = "http://www.servicelive.com/namespaces/provider/soResponse";
	
	public static final String  PROVIDER_RESPONSE_SCHEMALOCATION = "http://www.servicelive.com/namespaces/provider/soResponse getServiceOrdersResponse.xsd" ;
	//Phone Type 
	public static final String WORK = "Work";	
	public static final String MOBILE= "Mobile";
	public static final String HOME = "Home";	
	public static final String PAGER = "Pager";
	public static final String OTHER = "Other";	
	
	//Contact Type
	public static final String SERVICE = "Service";	
	public static final String END_USER= "End User";
	public static final String BUYER_SUPPORT = "Buyer Support";	
	public static final String PROVIDER = "Provider";
	public static final String PICKUP = "Pickup";	
	public static final String BUYER_ID="buyerid";
	public static final String RESOURCE_ID="resourceid";
	//Filter Types
	public static final String GENERAL = "General";	
	public static final String SCOPEOFWORK= "Scope Of Work";
	public static final String SERVICELOCATION = "Service Location";	
	public static final String SCHEDULE = "Schedule";
	public static final String PRICING = "Pricing";	
	public static final String CONTACTS = "Contacts";	
	public static final String ATTACHMENTS= "Attachments";
	public static final String PARTS = "Parts";	
	public static final String CUSTOMREFERENCES = "Custom References";
	public static final String NOTES = "Notes";	
	public static final String ADDONS = "Addons";	
	public static final String HISTORY = "History";
	public static final String ROUTEDPROVIDERS = "Routed Providers";
	public static final String JOBCODES = "JobCodes";
	public static final String PAYMENTDETAILS = "Payment Details";
	public static final String INVOICEPARTS = "Invoice Parts";
	public static final String SEPARATOR = "-";
	public static final String FETCHREASONCODES = "Fetch Reason Codes";	
	//pre call
	public static final String FETCH_PRECALL_REASONCODES = "Fetch PreCall Reason Codes";	
	// Exceptions
	public static final String ERROR_KEY = "1";
	public static final String SERVICEORDER_KEY = "2";	
	
	//Provider SO list API
		public static final String SO_STATUS_ACTIVE = "Active";
		public static final String SO_STATUS_RECEIVED = "Received";
		public static final String SO_STATUS_ROUTED = "Routed";
		
	//Search b2c	
	public static final String SEARCH_RESOURCES_SCHEMAS = "/resources/schemas/search/";	
	public static final String KEYWORD = "keyword";
	public static final String SPELLCHECK = "spellcheck";
	public static final String IDLIST = "idlist";
	
	//SL-21467

	
	public static final String ZIP = "zip";
	public static final String CATEGORY_ID = "categoryid";
	public static final String CATEGORY_NAME = "categoryName";
	public static final String SERVICE_TYPE = "servicetype";
	public static final String LANGUAGE = "language";
	public static final String MAX_RESULT_SET = "max_result_set";
	public static final String PAGE_SIZE_SET = "pagesize";
	public static final String SO_STATUS_SET = "so_status_set";
	public static final String CUSTOMER_NAME = "cust_name";
	public static final String SEARCH_FILTER = "searchfilter";
	public static final String SERVICE_LOC_PHONE = "service_loc_phone";
	public static final String CUSTOM_REFERENCES = "cust_references";
	public static final String SO_STATUS = "status";
	public static final String PAGE_NUMBER = "pagenumber";
	public static final String PAGE_SIZE = "pagesize";
	public static final String CUSTOM_REFERENCES_PAIR_SEPERATOR = "#";
	public static final String CUSTOM_REFERENCES_KEY_VALUE_SEPERATOR = "-";
	public static final int PAGE_SIZE_SET_10 = 10;
	public static final int PAGE_SIZE_SET_20 = 20;
	public static final int PAGE_SIZE_SET_50 = 50;
	public static final int PAGE_SIZE_SET_100 = 100;
	public static final int PAGE_SIZE_SET_200 = 200;
	public static final int PAGE_SIZE_SET_500 = 500;
	public static final String SOSTATUS_SET_ROUTED = "Routed";
	public static final String SOSTATUS_SET_POSTED = "Posted";
	public static final String SOSTATUS_SET_ACCEPTED = "Accepted";
	public static final String SOSTATUS_SET_DRAFTED = "Draft";
	public static final String SOSTATUS_SET_ACTIVE = "Active";
	public static final String SOSTATUS_SET_CLOSED = "Closed";
	public static final String SOSTATUS_SET_COMPLETED = "Completed";
	public static final String SOSTATUS_SET_RECEIVED = "Received";
	public static final String SOSTATUS_SET_PROBLEM = "Problem";
	public static final String SOSTATUS_SET_EXPIRED = "Expired";
	public static final String SOSTATUS_SET_PENDINGCANCEL = "Pending Cancel";
	public static final String CREATED_STARTDATE = "createddatestart";
	public static final String CREATED_ENDDATE = "createddateend";
	public static final String ERROR_FIELD = "ErrorField";
	public static final String INVALID_FILTER = "invalidFilter";
	
	public static final String NO_MATCH = "No Match Found";
	public static final String MATCH_FOUND = "Match Found";
	public static final String MULTIPLE_MATCH = "Multiple Matches Found";
	
	public static final String SKILLTREE_XSD= "skillTreeResponse.xsd";
	public static final String PROVIDERRESULT_XSD= "providerResults.xsd";
	public static final String PRIORITY1="1";
	public static final String API="API";
	public static final String COMPLETE_XSD="completeSORequest.xsd";
	
	//ERROR CODES
	//General
	public static final String EXCEPTION_MESSAGE = "0701.serviceorder.general.error";	
	public static final String SECURITY_ERROR_CODE = "0702.serviceorder.general.error";
	public static final String RESPONSE_ERROR_CODE = "0703.serviceorder.general.error";
	public static final String COMPANY_ERROR_CODE = "0704.serviceorder.general.error";
	public static final String CONTACT_ERROR_CODE = "0705.serviceorder.general.error";
	public static final String VENDOR_BUYERRESOURCE_ERROR_CODE = "0706.serviceorder.general.error";
	public static final String SOID_MISSING_ERROR_CODE = "0707.serviceorder.general.error";

	//Validation
	public static final String INVALIDXML_ERROR_CODE = "0001.validation.error";
	public static final String AUTHENTICATION_ERROR_CODE = "0002.validation.error";
	public static final String AUTHORIZATION_ERROR_CODE= "0003.validation.error";

	//Create
	public static final String DRAFT_CREATED_RESULT_CODE = "0101.serviceorder.create.result";	
	public static final String DRAFT_NOTCREATED_ERROR_CODE = "0102.serviceorder.create.error";
	public static final String DOCUMENT_ERROR_CODE = "0103.serviceorder.create.error";
	public static final String TASK_SKU_ERROR_CODE = "0104.serviceorder.create.error";
	public static final String ZIPCODEINVALID_SERVICE = "0105.serviceorder.create.error";
	public static final String STATEINVALID_SERVICE = "0106.serviceorder.create.error";
	public static final String ZIPCODEINVALID_PICKUP= "0107.serviceorder.create.error";
	public static final String STATEINVALID_PICKUP = "0108.serviceorder.create.error";
	public static final String ENDDATE_REQUIRED = "0109.serviceorder.create.error";
	public static final String SKU_INVALID_OR_NO_TASK_MAPPING = "0110.serviceorder.create.error";
	public static final String STARTDATE_IMPROPER = "0111.serviceorder.create.error";
	public static final String STARTDATE_ENDDATE = "0112.serviceorder.create.error";
	public static final String BUYER_RESOURCEID_NOT_VALID = "0113.serviceorder.create.error";
	public static final String BUYER_NOT_AUTHORIZED = "0114.serviceorder.create.error";
	public static final String SERVICEORDER_NOT_UNIQUE = "0115.serviceorder.create.error";
	public static final String INVALID_UNIQUE_ID = "0116.serviceorder.create.error";
	public static final String INVALID_SCHEDULE = "0117.serviceorder.create.error";
	public static final String INVALID_SCHEDULE_SLOTS = "0118.serviceorder.create.error";

	
	//Post
	public static final String NO_PROVIDERS_ERROR_CODE="0501.serviceorder.post.error";
	public static final String FROM_DATETIME_ERROR_CODE="0503.serviceorder.post.error";
	public static final String FROM_DATE_ERROR_CODE="0504.serviceorder.post.error";
	public static final String FROM_TODATE_ERROR_CODE="0505.serviceorder.post.error";
	public static final String MIN_PRICE_ERROR_CODE="0506.serviceorder.post.error";
	public static final String TIME_ERROR_CODE="0507.serviceorder.post.error";
	
	//Get
	public static final String RETRIEVE_RESULT_CODE = "0201.serviceorder.get.result";
	public static final String RETRIEVE_ERROR_CODE = "0202.serviceorder.get.error";
	public static final String RETRIEVE_ACCES_ERROR_CODE = "0203.serviceorder.get.error";
	public static final String NOT_AUTHORISED_BUYER_RESOURCEID = "0709.notauthorised.error";
	public static final String ACCEPT_OFFER_XSD = "acceptCounterOfferRequest.xsd";
	public static final String CREATE_CONDITIONAL_OFFER_XSD = "createCounterOfferRequest.xsd";
	public static final String TRUE="true";
	public static final String FALSE="false";
	public static final String DELIMITER="$";
	//Documents
	public static final String DOCUMENT_SCHEMAS = "/resources/schemas/so/";
	public static final String DOCUMENT_SCHEMAS_PATH= "/resources/schemas/document/";
	public static final String DOCUMENT_XSD = "addSODoc.xsd";
	public static final String DOCUMENT_ADD_RESULT_CODE= "1101.serviceorder.addsodoc.result";
	public static final String DOCUMENT_ADD_ERROR_CODE= "1102.serviceorder.addsodoc.error";
	public static final String DOCUMENT_DELETE_RESULT_CODE= "1103.serviceorder.deletesodoc.result";
	public static final String DOCUMENT_DELETE_ERROR_CODE= "1104.serviceorder.deletesodoc.error";
	public static final String DOCUMENT_DELETE_INPUT_ERROR_CODE= "1105.serviceorder.deletedoc.error";
	public static final String DOCUMENT_DELETE_REQUEST_ERROR_CODE= "1106.serviceorder.deletesodoc.error";
	public static final String SO_ID = "soid";
	public static final String FILE_NAME = "filename";
	public static final String USER_NAME = "username";
	public static final String PASSWORD = "password";
	public static final String BUYER_RES_ID = "buyerresid";
	public static final String BUYERID = "buyerid";
	public static final int VIDEO_TYPE = 8;
	//Cancel
	public static final String INVALID_STATE_ERROR_CODE="0401.serviceorder.cancel.error";
	//Login
	public static final String LOGIN_REQUEST_XSD = "loginRequest.xsd";
	public static final String LOGIN_RESPONSE_XSD = "loginResponse.xsd";
	public static final String LOGIN_SCHEMALOCATION= "http://www.servicelive.com/namespaces/ums loginRequest.xsd";
	//ReSchedule
	public static final String RESCHEDULE_RESULT_CODE = "0601.serviceorder.reschedule.result";
	public static final String ENDDATE_ERROR_CODE = "0603.serviceorder.reschedule.error";
	public static final String STARTDATE_ERROR_CODE = "0604.serviceorder.reschedule.error";
	public static final String STATUS_ERROR_CODE = "0605.serviceorder.reschedule.error";
	
	//Search
	public static final String SEARCH_UNSUCCESSFUL_ERROR_CODE="0302.serviceorder.search.error";
	public static final String SEARCH_VALIDATION_ZIP_ERROR_CODE="0303.serviceorder.search.zip.validation.error";
	public static final String SEARCH_VALIDATION_MAXRESULTSET_ERROR_CODE="0304.serviceorder.search.result.set.validation.error";
	
	//Add Note
	public static final String ADD_NOTE_ERROR_CODE="0801.serviceorder.addnote.error";
	public static final String ADD_NOTE_RESULT_CODE="0802.serviceorder.addnote.result";
	public static final int NOTE_TYPE_GENERAL_TWO = 2;
	public static final int NOTE_TYPE_SUPPORT_ONE = 1;
	
	//Funding Source
	public static final String GET_FUNDING_SOURCE_RESPONSE_XSD= "getFundingSourcesResponse.xsd";
	public static final String GET_FUNDING_SOURCE_RESOURCES_SCHEMAS = "/resources/schemas/wallet/";
	public static final String GET_FUNDING_SOURCE_NAMESPACE= "http://www.servicelive.com/namespaces/wallet";
	public static final String GET_FUNDING_SOURCE_RESOURCES_SCHEMASLOCATION = "http://www.servicelive.com/namespaces/wallet getFundingSourcesResponse.xsd";

	
	public static final String CREATE_FUNDING_SOURCE_RESPONSE_XSD= "getFundingSourcesResponse.xsd";
	public static final String CREATE_FUNDING_SOURCE_REQUEST_XSD= "createFundingSourceRequest.xsd";
	public static final String CREATE_FUNDING_SOURCE_RESOURCES_SCHEMAS = "/resources/schemas/wallet/";
	public static final String CREATE_FUNDING_SOURCE_NAMESPACE= "http://www.servicelive.com/namespaces/wallet";
	public static final String CREATE_FUNDING_SOURCE_RESOURCES_SCHEMASLOCATION = "http://www.servicelive.com/namespaces/wallet/ getFundingSourcesResponse.xsd";
	
	public static final String CREATE_FUNDING_SOURCE_ERROR_CODE = "0013.createfunding.failed";
	public static final String FUNDING_SOURCE_RESULT_SIZE_CODE = "0903.serviceorder.get.result";
	public static final String GET_FUNDING_SOURCE_FAILED_DURING_RETRIVAL = "0014.fundingsource.get.result";
	
	//Counter Offer
	public static final String COUNTER_OFFER_RESPONSE_XSD= "counterOfferResponse.xsd";
	public static final String COUNTER_OFFER_RESULT_CODE= "1001.serviceorder.offer.result";
	public static final String COUNTER_OFFER_ERROR_CODE= "1002.serviceorder.offer.error";
	public static final String COUNTER_OFFER_NOT_PRESENT= "1003.serviceorder.offer.error";
	public static final String COUNTER_OFFER_RESOURCE_ID_NOTVALID= "1004.serviceorder.offer.error";
	public static final String COUNTER_OFFER_NAMESPACE = "http://www.servicelive.com/namespaces/counterOffer";

	//Document upload
	public static final String DOCUMENT_CREATED_RESULT_CODE = "01001.document.upload.result";	
	public static final String DOCUMENT_NOTCREATED_ERROR_CODE = "01002.document.upload.error";
	public static final String DOCUMENT_NOTMAPPED_ERROR_CODE = "01003.document.mapping.error";
	public static final String DOCUMENT_NOTEXISTS_ERROR_CODE = "01004.document.notexists.error";
	public static final String DOCUMENT_KEY = "2";
	public static final String UNDERSCORE = "_";
	public static final String DOT = ".";	
	public static final String CONTENT_DISPOSITION= "Content-Disposition";
	public static final String ATTACHMENT_FILENAME= "attachment;filename=\"";
	public static final String BACK_SLASH = "\"";
	//Buyer Account
	public static final String BUYER_ACCOUNT_RESPONSE_XSD= "getBuyerAccountResponse.xsd";
	
	//Edit Service Order
	
	public static final String SUBSTATUS_EDIT_ERROR="1304.serviceorder.edit.error";
	public static final String PRICING_EDIT_ERROR="1305.serviceorder.edit.error";
	public static final String PARTS_EDIT_ERROR="1303.serviceorder.edit.error";
	public static final String PARTID_WRONG_ERROR="1307.serviceorder.edit.error";
	public static final String PARTID_REQUIRED_ERROR="1308.serviceorder.edit.error";
	public static final String PART_DETAILS_INCOMPLETE="1309.serviceorder.edit.error";
	public static final String TASK_DETAILS_INCOMPLETE="1312.serviceorder.edit.error";
	public static final String TASKID_WRONG_ERROR="1311.serviceorder.edit.error";
	
	//Update Service Order
	public static final String INVALID_STATE_FOR_UPDATE="4112.serviceorder.update.error";
	public static final String INVALID_REASON_CODE="4102.serviceorder.update.error";
	public static final String INVALID_REASON_CODE_RELAY="4102.serviceorder.update.relay.error";	
	public static final String NO_REASON_CODE="4103.serviceorder.update.error";
	public static final String NO_REASON_CODE_RELAY="4103.serviceorder.update.relay.error";
	public static final String INVALID_PRICE="4104.serviceorder.update.error";
	public static final String INVALID_PRICE_RELAY="4104.serviceorder.update.relay.error";
	public static final String NO_NOTE="4105.serviceorder.update.error";
	public static final String NO_NOTE_RELAY="4105.serviceorder.update.relay.error";
	public static final String NO_FIELDS="4108.serviceorder.update.error";
	public static final String INVALID_STATE_SPEND_LIMIT="4109.serviceorder.update.error";
	public static final String INVALID_STATE_SPEND_LIMIT_RELAY="4109.serviceorder.update.relay.error";
	public static final String INVALID_STATE_CUST_REF="4110.serviceorder.update.error";
	public static final String INVALID_CUST_REF_VALUE="4111.serviceorder.update.error";
	public static final String NOTEDITABLE_CUST_REF_VALUE="4113.serviceorder.update.error";
	public static final String INVALID_BUYER_RESOURCE="4114.serviceorder.update.error";
	public static final String SPEND_LIMIT_EXCEEDED="4115.serviceorder.update.error";
	public static final String INVALID_STATE_SO_CON_LOC_UPDATE="4116.serviceorder.update.error";
	public static final String INVALID_SUBSTATUS_FOR_CURRENT_STATE="4117.serviceorder.update.error";
	public static final String INVALID_SO_STATE="4118.serviceorder.update.error";
	public static final String SUBSTATUS_UPDATE_NOT_ALLOWED="4119.serviceorder.update.error";
	public static final String INVALID_SPEND_LIMIT_INCREASE="4120.serviceorder.update.error";
	public static final String INVALID_SPEND_LIMIT_DECREASE="4121.serviceorder.update.error";
	public static final String INVALID_UPDATE__RESULT_CODE="4122.serviceorder.update.error";
	//Report problem
	public final static int PROBLEM_STATUS = 170;
	public final static int ACTIVE_STATUS = 155;
	public final static int ACCEPTED_STATUS = 150;
	public final static int COMPLETED_STATUS = 160;
	
	public static final String RESCHEDULE_RESPONSE_ACCEPT = "Accept";
	public static final String RESCHEDULE_RESPONSE_REJECT = "Reject";
	public static final String ACCEPT = "Accept";
	public static final String REJECT = "Reject";
	public static final String RELEASE = "Release";
	
	public static Map problemDescMap = populateProblemDescMap();
	//Application properties
	public static final String IDENTIFICATION_KEY = "IndentificationObject";
	
	public static final String CREDIT="Credit";
	public static final String DEBIT="Debit";
	
	//Retrieve Version 2
	
	public static final String SO_ID_LIST = "soIdList";
	public static final String SO_SEPERATOR = "_";
	public static final String SO_STAUS_LIST = "status";
	public static final String SO_ROUTED = "Routed";
	public static final String SO_POSTED = "Posted";
	//Reject Service Order API (Provider)
	public static final String REJECT_INVALID_REASON_CODE = "1604.serviceorder.reason.code.error";
	public static final String REJECT_RESOURCE_ID_REQUIRED = "1605.serviceorder.resource.id.warning";
	public static final String REJECT_RESOURCE_ID_INVALID =  "1603.serviceorder.provider.error";

	//Accept Service Order API (Provider)
	public static final String ACCEPT_INVALID_RESOURCE = "1502.serviceorder.accept.invalid.resource";
	public static final String ACCEPT_ERROR_RESPONSE = "1503.serviceorder.accept.error.common";
	//SLT-3838
	public static final String PROTECTION_AGREEMENT = "Protection Agreement";
	public static final String IN_WARRANTY = "In-Warranty";
	public static final String Pay_Roll_Transfer = "Pay Roll Transfer";
	public static final String Customer_Collect = "Customer Collect";
	
	public static final String IW = "IW";
	public static final String SP = "SP";
	public static final String CC = "CC";
	public static final String PT = "PT";
	public static final String PA = "PA";
	
	public static class Wallet {
		public static final String WALLET_RESOURCES_SCHEMAS = "/resources/schemas/wallet/";		
		public static final String BUYER    = "Buyer";
		
		public static class History {
			// use this class for history
			//Wallet
			public static final String WALLET_HISTORY_RESPONSE_SCHEMALOCATION= "http://www.servicelive.com/namespaces/wallethistory walletHistoryResponse.xsd";
			public static final String WALLET_HISTORY_RESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/wallethistory";
			public static final String WALLET_HISTORY_RESPONSE_XSD= "walletHistoryResponse.xsd";
			public static final String START_DATE = "startdate";
			public static final String END_DATE   = "enddate";
			public static final int HISTORY_PERIOD_YEAR = 1;
			public static final String HISTORY_PERIOD_YEAR_STRING = "YEAR";
			public static final int HISTORY_PERIOD_MONTH = 1;
			public static final String HISTORY_PERIOD_MONTH_STRING = "MONTH";
			public static final int HISTORY_PERIOD_DAY = 1;
			public static final String HISTORY_PERIOD_DAY_STRING = "DAY";
			public static final int MAX_HISTORY_RESULT_SET = 1000;
			//Get
			public static final String WALLET_HISTORY_RESULT_CODE 	   = "0901.wallet.get.result";
			public static final String WALLET_HISTORY_ACCES_ERROR_CODE = "0902.wallet.get.error";
			public static final String WALLET_HISTORY_DATES_ERROR_CODE = "0904.wallet.get.error";			
			public static final String WALLET_HISTORY_RESULT_SIZE_CODE = "0903.wallet.get.result";
		}
		
		
		public static class WalletBalance {
			public static final String SCHEMALOCATION= "http://www.servicelive.com/namespaces/walletbalance walletBalanceResponse.xsd";
			public static final String NAMESPACE = "http://www.servicelive.com/namespaces/walletbalance";
			public static final String RESPONSE_XSD= "walletBalanceResponse.xsd";
			public static final String WALLET_DEBIT = "Debit";
			public static final String WALLET_CREDIT = "Credit";
		}
		
		public static class BuyerWalletThreshold {
			public static final String SCHEMALOCATION= "http://www.servicelive.com/namespaces/buyerwalletthreshold buyerWalletThresholdResponse.xsd";
			public static final String NAMESPACE = "http://www.servicelive.com/namespaces/buyerwalletthreshold";
			public static final String RESPONSE_XSD= "buyerWalletThresholdResponse.xsd";
			public static final String THRESHOLD_ACTIVE = "Active";
			public static final String THRESHOLD_AMOUNT = "Amount";
		}	
	}
	
	public static class ProviderReviews {
		public static final String SCHEMALOCATION= "http://www.servicelive.com/namespaces/search providerReviewsResponse.xsd";
		public static final String NAMESPACE = "http://www.servicelive.com/namespaces/";		
		public static final String RESOURCES_SCHEMAS = "/resources/schemas/search/";
		public static final int DEFAULT_PAGE_SIZE = 100;
		public static final String providerId = "PROVIDER_ID";	
		public static final String BEGIN_DATE = "beginDate";
		public static final String END_DATE = "endDate";
		public static final String MAXRATING = "maxRating";
		public static final String MINRATING = "minRating";
		public static final String SORT = "sort";
		public static final String ORDER = "order";
		public static final String PAGE_SIZE = "pageSize";
		public static final String PAGE_NUM = "pageNum";
		public static final int HISTORY_PERIOD_YEAR = 5;
		public static final String HISTORY_PERIOD_YEAR_STRING = "YEAR";
		
		public static final String ADD_REVIEW_SCHEMALOCATION= "http://www.servicelive.com/namespaces/addProviderReview addProviderReviewResponse.xsd";
		public static final String ADD_REVIEW_NAMESPACE = "http://www.servicelive.com/namespaces/addProviderReview";
		public static final String ADD_REVIEW_PRO_NAMESPACE="http://www.servicelive.com/namespaces/pro/addReview";
		public static final String ADD_REVIEW_RESPONSE_XSD= "addProviderReviewResponse.xsd";
		public static final String ADD_REVIEW_REQUEST_XSD= "addProviderReviewRequest.xsd";
		public static final String CLEANLINESS = "Cleanliness";
		public static final String TIMELINESS = "Timeliness";
		public static final String COMMUNICATION = "Communication";
		public static final String PROFESSIONALISM = "Professionalism";
		public static final String QUALITY = "Quality";
		public static final String VALUE = "Value";
		public static final String CREDENTIAL_TOTAL = "CredentialTotal";
		public static final String ADD_REVIEW_PRO_REQUEST_XSD="addReviewProRequest.xsd";
		public static final String ADD_REVIEW_PRO_RESPONSE_XSD="addReviewProResponse.xsd";
		public static final String INACTIVE_REVIEW_ID="inactiveReviewId";
		
	}
	/*Code added by 643272 date 03-09-2013 
	 * Provider creation using API Start
	 * */
	
	public static class ProviderAccount {
		
		public static final String RESOURCES_SCHEMAS = "/resources/schemas/ums/";
		public static final String NAMESPACE = "http://www.servicelive.com/namespaces/ums";
		
		//create operation
				public static class Create {
					public static final String SCHEMALOCATION= "/resources/schemas/ums/";
					public static final String REQUEST_XSD="createProviderAccountRequest.xsd";
					public static final String RESPONSE_XSD="providerAccountResponse.xsd";
				}
		
	}
	/*Code added by 643272 date 03-09-2013 
	 * Provider creation using API End
	 * */
	
	
	public static class BuyerAccount {
		//common across create, modify, close, delete buyer account operations
		public static final String RESOURCES_SCHEMAS = "/resources/schemas/ums/";
		public static final String NAMESPACE = "http://www.servicelive.com/namespaces/ums";
		
		//create operation
		public static class Create {
			public static final String SCHEMALOCATION= "http://www.servicelive.com/namespaces/ums createBuyerAccountRequest.xsd";
			public static final String REQUEST_XSD="createBuyerAccountRequest.xsd";
			public static final String RESPONSE_XSD="buyerAccountResponse.xsd";
		}
		
		public static class Modify {
			public static final String SCHEMALOCATION= "http://www.servicelive.com/namespaces/ums modifyBuyerAccountRequest.xsd";
			public static final String REQUEST_XSD="modifyBuyerAccountRequest.xsd";
			public static final String RESPONSE_XSD="modifyBuyerAccountResponse.xsd";
		}
		
		public static class Get {
			public static final String SCHEMALOCATION= "http://www.servicelive.com/namespaces/ums getBuyerAccountRequest.xsd";
			public static final String RESPONSE_XSD="getBuyerAccountResponse.xsd";
			public static final String BUYER_ACCOUNT_RESPONSE_XSD= "getBuyerAccountResponse.xsd";
			public static final String GET_BUYER_USERNAME_DOESNOT_EXIST="Buyer does not exist";
			public static final String GET_BUYER_ACCOUNT_SUCCESSFUL="Buyer Account fetched successfully";
			
		}
	}
	

	public static class ProviderSearch {
		public static final String RESOURCES_SCHEMAS = "/resources/schemas/search/";
		public static final String RESULT_MODE = "resultmode";
		//SL-21378
		public static final Object UNKNOWN = "Unknown";
		
		
		public static class ProviderByZipCode {
			public static final String PAGE_SIZE = "pagesize";
			public static final int DEFAULT_PAGE_SIZE = 200;
			public static final int DEFAULT_MAX_DISTANCE = 40;
			public static final String PAGE_NUMBER = "pagenumber";
			public static final String FAVOARITE_ID_LIST = "favoriteList";
			public static final String SORT_BY = "sortby";
			public static final String SORT_ORDER = "sortorder";
			public static final String MAX_DISTANCE = "maxdistance";
			public static final String MIN_RATING = "minrating";
			public static final String MAX_RATING = "maxrating";
			public static final String MINIMUM = "minimum";
			public static final String MEDIUM = "medium";
			public static final String COMPANY_ID = "companyId";
			public static final String CITY = "city";
			public static final String STATE = "state";
			
		}
		
	}
	public static class sendAlert {
		
		public static final String RESOURCES_SCHEMAS = "/resources/schemas/alerts/";

		public static final String SCHEMALOCATION= "http://www.servicelive.com/namespaces/alerts sendAlerts.xsd";
		public static final String NAMESPACE = "http://www.servicelive.com/namespaces/alerts";
		public static final String REQUEST_XSD="sendAlerts.xsd";
		public static final String RESPONSE_XSD="sendAlertResponse.xsd";

	}
	public static class cancelSO {
		
		public static final String SO_ID = "soId";
		public static final String RESOURCE_ID = "resourceId";
		public static final String CANCEL_COMMENT = "cancelcomment";
		public static final String CANCEL_AMOUNT = "cancelamount";
		public static final String CANCELLATION_REQUEST="Cancellation Request";
		public static final String RESOURCE_ID_NOT_VALID = "0405.serviceorder.cancel.error";
		public static final String CANCEL_COMMENT_MISSING = "0406.serviceorder.cancel.error";
		public static final String CANCEL_AMOUNT_MISSING = "0407.serviceorder.cancel.error";
		public static final String RESOURCEID_OR_SOID_MISSING = "0408.serviceorder.cancel.error";
		public static final String SO_CANCELLED = "0409.serviceorder.cancel.error";
		public static final String SO_CANCELLATION_AMOUNT_GREATER = "0410.serviceorder.cancel.error";
		public static final String INVALID_CANCEL_REASONCODE_ERROR_CODE="0411.serviceorder.cancel.error";
		public static final String INVALID_WORK_ORDER_SKU_ERROR_CODE="0412.serviceorder.cancel.error";
		public static final String SO_CANCELLATION_AMOUNT_EXCEEDS_MAX_LIMIT="0413.serviceorder.cancel.error";
		public static final String SO_CANCELLATION_AMOUNT_INSUFFICIENT_BALANCE="0417.serviceorder.cancel.insufficient_balance";
		public static final String SO_CANCELLATION_AMOUNT_NON_FUNDED_BUYER_AMOUNT="0420.serviceorder.cancel.non.funded.amount.error";
		public static final String SO_CANCELLATION_SUCCESS = "The Service Order is successfully cancelled.";
		public static final String SO_PENDING_CANCELLATION_SUCCESS = "The Cancellation request has been successfully submitted and notification is sent to the provider. Please note that Service order will be cancelled when provider agrees to your request.";
		public static final String NO = "NO";
		public static final String YES = "YES";
		public static final String PENDING_CANCEL_STATE = "Request";
		public static final String PENDING_CANCEL_STATUS = "Cancel";
		public static final String DEFAULT_WORK_ORDER_SKU = "Cancel";
		public static final String DEFAULT_SYS_ERROR_MESSAGE="0418.serviceorder.cancel.default.error";
		public static final String CANCEL_REASON_CODE_MISSING="0419.serviceorder.cancel.reasoncode.error";
	}
	
	
	public static class retrieveSO {
		
		public static final String SO_ID = "soId";
		public static final String RESOURCE_ID = "resourceId";
		public static final String BUYER_ID = "buyerId";
		public static final String RESPONSE_FILTER = "responsefilter";
		public static final String SUPPORT_NOTE = "Support Note";
		public static final String GENERAL_NOTE = "General Note";
		public static final String CLAIM_NOTE = "Claim Note";
		public static final String PROVIDER = "Provider";
		public static final String BUYER = "Buyer";
	}
	public static class retrieveSOForSpendLimit{
		
		public static final String RESPONSE_FILTER = "responsefilter";
		public static final String THIN_RESPONSE = "Thin";
		public static final String VERBOSE_RESPONSE = "Verbose";
	}
	
public static class acceptSO {	
	public static final String SO_ID = "soId";
	public static final String VENDOR_RESOURCE_ID = "vendorResourceId";
	public static final String PROVIDER_ID = "providerId";
	public static final String RESPONSE_FILTER = "responsefilter";
	public static final String ACCEPTRESPONSE_XSD= "soAcceptResponse.xsd";
	public static final String ACCEPTREQUEST_XSD= "soAcceptRequest.xsd";
	public static final String SOACCEPTRESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/acceptResponse";
	public static final int    TERMS_AND_COND_IND_PROVIDER_ACCEPT = 10;
}
	public static class acceptRejectReleaseSO {
		
		public static final String SO_ID = "soId";
		public static final String VENDOR_RESOURCE_ID = "vendorResourceId";
		public static final String PROVIDER_ID = "providerId";
		public static final String RESPONSE_FILTER = "responsefilter";
		public static final String ACCEPTREJECTRELEASEREQUEST_XSD= "acceptRejectReleaseSORequest.xsd";
		public static final String ACCEPTREJECTRELEASERESPONSE_XSD= "acceptRejectReleaseSOResponse.xsd";
		public static final String SOACCEPTREJECT_NAMESPACE = "http://www.servicelive.com/namespaces/soAcceptReject";
		public static final String SOACCEPTREJECT_SCHEMALOCATION= "http://www.servicelive.com/namespaces/soAcceptReject soAcceptRejectResponse.xsd";
		public static final String ACCEPT = "Accept";
		public static final String REASONID = "ReasonId";
		public static final String REJECT = "Reject";
	}

public static class providerRetrieveSO {
		
	public static final String SO_ID = "soId";
	public static final String RESOURCE_ID = "providerResourceId";
	public static final String SORESPONSE_XSD= "soProviderResponse.xsd";
	public static final String SORESPONSE_PRO_XSD= "soProvResponse.xsd";
	public static final String SORESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/soProviderResponse";
	public static final String SORESPONSE_PRO_NAMESPACE = "http://www.servicelive.com/namespaces/pro/soProviderResponse";
	public static final String SO_RESOURCES_SCHEMAS = "/resources/schemas/so/v1_1/";
	public static final String SORESPONSE_SCHEMALOCATION= "http://www.servicelive.com/namespaces/soResponse soProviderResponse.xsd";
	public static final int POSTED = 110;
	public static final int DRAFT = 100;
	public static final String PROVIDER = "Provider";
	public static final String BUYER = "Buyer";
	public static final String SUPPORT_NOTE = "Support Note";
	public static final String GENERAL_NOTE = "General Note";
	public static final String CLAIM_NOTE = "Claim Note";
}
public static class SOSearchTemplate {
	public static final String RESOURCES_SCHEMAS = "/resources/schemas/search/";
	public static final String SEARCHTEMPLATERESPONSE_XSD= "searchTemplateResponse.xsd";
	public static final String SEARCHTEMPLATERESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/searchTemplateResponse";
}
//login service
public static final String ERRORCODE_USER_NOT_VERIFIED = "1000";
public static final String ERRORCODE_USER_ACCOUNT_LOCKED = "1001";
public static final String ERRORCODE_INCORRECT_USER = "1002";
public static final String ERRORCODE_BUYER_NOT_ASSOCIATED = "1004";
//Search Filter map constants
public static final String SEARCH_FILTER_STATE_LIST = "STATE_LIST";
public static final String SEARCH_FILTER_SKILL_LIST = "SKILL_LIST";
public static final String SEARCH_FILTER_MARKET_LIST = "MARKET_LIST";
public static final String SEARCH_FILTER_CUST_REF_LIST = "CUST_REF_LIST";
public static final String SEARCH_FILTER_CHECKNUMBER_LIST = "CHECKNUMBER_LIST";
public static final String SEARCH_FILTER_CUSTOMER_NAME_LIST = "CUSTOMERNAME_LIST";
public static final String SEARCH_FILTER_PHONE_LIST = "PHONE_LIST";
public static final String SEARCH_FILTER_PROVIDER_FIRM_ID_LIST = "PROV_FIRM_ID_LIST";
public static final String SEARCH_FILTER_SO_ID_LIST = "SOID_LIST";
public static final String SEARCH_FILTER_SERVICEPRO_ID_LIST = "SERVICEPRO_ID_LIST";
public static final String SEARCH_FILTER_SERVICEPRO_NAME_LIST = "SERVICEPRO_NAME_LIST";
public static final String SEARCH_FILTER_ZIP_CODE_LIST = "ZIP_CODE_LIST";
public static final String SEARCH_FILTER_START_DATE_LIST = "START_DATE_LIST";
public static final String SEARCH_FILTER_END_DATE_LIST = "END_DATE_LIST";
public static final String SEARCH_FILTER_MAIN_CATEGORY_LIST = "MAIN_CATEGORY_LIST";
public static final String SEARCH_FILTER_CATEGORY_LIST = "CATEGORY_LIST";
public static final String SEARCH_FILTER_STATUS_LIST = "STATUS_LIST";
public static final String SEARCH_CRITERIA_KEY = "SEARCH_CRITERIA_KEY";
public static final String ORDER_CRITERIA_KEY = "ORDER_CRITERIA_KEY";
public static final String SORT_CRITERIA_KEY = "SORT_CRITERIA_KEY";
public static final String PAGING_CRITERIA_KEY = "PAGING_CRITERIA_KEY";
public static final String FILTER_CRITERIA_KEY = "FILTER_CRITERIA_KEY";	
public static class ServiceOrder{
	//The following constants were used for Cancel_Request_Reschedule api
	public static final String RESOURCE_TYPE = "resourcetype";
	public static final String RESOURCE_TYPE_BUYER    = "2";
	public static final String RESOURCE_TYPE_PROVIDER = "1";
}
public static final String BUYER_RESOURCE_ID = "BuyerResourceId";	 

//SL-15642 xsds:

public static final String EDIT_APPOINTMENT_REQUEST_XSD = "soEditAppointmentRequest.xsd";
public static final String EDIT_APPOINTMENT_RESPONSE_XSD = "soEditAppointmentResponse.xsd";

public static final String UPDATE_SCHEDULE_DETAILS_REQUEST_XSD = "updateScheduleDetlsRequest.xsd";
public static final String UPDATE_SCHEDULE_DETAILS_RESPONSE_XSD = "updateScheduleDetlsResponse.xsd";

public static final String SO_GET_ELIGIBLE_PRO_RESPONSE_XSD = "soEligibleProviderResponse.xsd";
public static final String SO_EDIT_SL_NOTES_REQUEST_XSD = "soLocationNotesRequest.xsd";
public static final String SO_EDIT_SL_NOTES_RESPONSE_XSD = "soLocationNotesResponse.xsd";
public static final String SO_ASSIGN_PROVIDER_REQUEST_XSD = "assignProviderRequest.xsd";
public static final String SO_ASSIGN_PROVIDER_RESPONSE_XSD = "soAssignProviderResponse.xsd";
public static final String SET_SO_PRIORITY_RESPONSE_XSD = "soPriorityResponse.xsd";
public static final String GET_RELEASE_INFO_RESPONSE_XSD = "getReleaseInfoResponse.xsd";
public static final String SO_RELEASE_REQUEST_XSD = "soReleaseRequest.xsd";
public static final String SO_RELEASE_RESPONSE_XSD = "soReleaseResponse.xsd";
public static final String SO_PROVIDER_CALL_REQUEST_XSD = "soProviderCallRequest.xsd";
public static final String SO_PROVIDER_CALL_RESPONSE_XSD = "soProviderCallResponse.xsd";
public static final String GET_OM_TABS_REQUEST_XSD = "getTabListResponse.xsd";
public static final String GET_CALLINFO_RESPONSE_XSD = "getCallInfoResponse.xsd";
public static final String GET_PRECALL_HISTORY_RESPONCE_XSD = "getPrecallHistoryDetailsResponse.xsd";
public static final String SO_FETCH_REQUEST_XSD = "fetchOrderRequest.xsd";
public static final String SO_FETCH_RESPONSE_XSD = "fetchOrderResponse.xsd";
public static final String GET_REASONCODES_RESPONSE_XSD = "getReasonCodesResponse.xsd";
public static final String WITHDRAW_OFFER_PRO_RESPONSE_XSD = "withdrawOfferResponse.xsd";

public static final String TAB_REFRESH_NAMESPACE = "http://www.servicelive.com/namespaces/getTabListResponse";
public static final String REFRESH_RESPONSE_SCHEMALOCATION= "http://www.servicelive.com/namespaces/getTabListResponse getTabListResponse.xsd";

public static final String GROUP_IND_PARAM = "groupind";
public static final String GROUPED_SO_IND = "1";
public static final String REQUEST_MAP = "REQMAP";

//Constants added for SL-18864
public static final String RESPONSE_FILTER_BOTH = "both";
public static final String RESPONSE_FILTER_PROVIDER = "provider";
public static final String RESPONSE_FILTER_FIRM = "firm";
public static final int MAX_COUNT_OF_TAGS_WITH_NULL_VALUE=6;
public static final String INVALID_ID_MSG="id tag in the request is empty.";
public static final String DUPLICATE_ID_MSG="id tag is duplicate in the request.";
public static final String INVALID_STATE_CD_MSG="state code should be a 2 letter abbreviation.";
public static final String INVALID_ZIP_CODE_MSG="zipCode should be a 5 digit numerical value.";
public static final String INVALID_ZIP_CODE_NUMBER_MSG="zipCode can only be a numerical value.";
public static final String INVALID_PHONE_NUMBER_MSG="phone number should be a 10 digit numerical value.";
public static final String INVALID_PHONE_NO_MSG="phone tag contains an invalid value.";
public static final String MAX_COUNT_OF_TAGS_WITH_NULL_VALUE_MSG="All tags are empty.";
public static final String API_RESULT_FAILURE="Failure";
public static final String API_RESULT_SUCCESS="Success";
public static final String API_REQUEST_EMPTY_MSG="Request is empty.";
public static final String DEFAULT_PROV_SEARCH_API_LIMIT = "max_allowed_provider_search_api_count";
public static final String API_REQUEST_COUNT_EXCEEDED_MSG = "Total number of search requests provided in the web service request has exceeded the maximum number allowed.";

/*IPR SL-16934*/
public static final String API_USERNAME_PASSWORD_EMPTY_MESSAGE = "UserName or Password cannot be empty.";
public static final String API_VALID_USER_MESSAGE = "Valid User.";
public static final String API_VALID_USERNAME_PASSWORD_MESSAGE = "Please provide a Valid UserName and Password";
public static final int API_PROVIDER_ROLE_ID = 1;
public static final int API_BUYER_ROLE_ID = 1;
public static final String URGENCY_NEXT_DAY_IND = "1";
public static final String URGENCY_SAME_DAY_IND = "7";
public static final String LAUNCH_MARKET_TRUE = "TRUE";
public static final String LAUNCH_MARKET_FALSE = "FALSE";
public static final int LAUNCH_MARKET_TRUE_IND = 1;
public static final int LAUNCH_MARKET_FALSE_IND = 0;
public static final String BUSINESS_ADDRESS = "Business Address";
/*IPR SL-16934 : for LMS API*/
public static final String PARTNER_CREATED = "Partner successfully created";
public static final String PARTNER_CREATE_SUCCESS = "Success";

public static final String STATUS_INACTIVE = "0";
public static final String STATUS_INACTIVE_DESC = "Not Active";
public static final String CREATE_PARTNER_STATUS = "22";
public static final String CREATE_PARTNER_STATUS_DESC = "Skills,Ts/Cs Credit Card Pending";
public static final String INSERT_FILTERSET_STATUS = "2";
public static final String INSERT_FILTERSET_STATUS_DESC = "Credit Card Pending";
public static final String CIM_PROFILE_SUCCESS = "12";
public static final String CIM_PROFILE_SUCCESS_DESC = "Final Approval Pending";
public static final String CIM_PROFILE_FAILURE = "4";
public static final String CIM_PROFILE_FAILURE_DESC="Credit Card Declined";
public static final String CREATE_NEW_PARTNER_API = "createNewPartner";
public static final String SET_PARTNER_STATUS_API = "setPartnerStatus";
public static final String INSERT_COMP_FILTER_SET_API = "competitiveInsertFilterSet";
public static final String INSERT_EXCL_FILTER_SET_API = "exclusiveInsertFilterSet";
public static final String CIM_PROFILE_CHARGE_API = "createCIMProfileAndCharge";
public static final String INSERT_COMP_FILTER_SET = "Competitive Filter Set";
public static final String INSERT_EXCL_FILTER_SET = "Exclusive Filter Set";
public static final String INSERT_COMP_FILTER_SET_PRICE = "8.00";
public static final String INSERT_EXCL_FILTER_SET_PRICE = "25.00";

/*IPR SL-16934 : for LMS API Custom API*/
public static final String CREATE_PARTNER_FILTERSET_CUSTOM_API = "customSignup";
public static final String CUSTOM_API_EMAIL = "Email";
public static final String CUSTOM_COMMENTS = "Comments";
public static final String CUSTOM_PARTNER_ID = "Partner_ID";
public static final String LICENSING_IND = "Licensing";
public static final String CUSTOM_FILTER_STATE = "Filter_State";
public static final String CUSTOM_IND_VALUE_TRUE = "1";
public static final int CUSTOM_IND_VALUE_TRUE_INT = 1;
public static final String CUSTOM_IND_VALUE_FALSE = "0";
public static final String MULTIPLE_AREAS_IND = "Multiple_Areas";
public static final String CUSTOM_TEXT_MESSAGE_PREF = "Text_Message_Preference";
public static final String CUSTOM_CELL_PHONE = "Cell_Phone";
public static final String CUSTOM_DISTANCE = "Distance";
public static final String CUSTOM_LOCATION_TYPE = "Orders";
public static final String CUSTOM_PACKAGE_DESC = "Package";
public static final String PROJECT_DESC = "Project[]";
public static final String PROJECT_DESC_STR = "Project[";
public static final String DAY_DESC = "Day[0]"; 
public static final String DAY_DESC_STR = "Day[";
public static final String CUSTOM_URGENCY_OF_SERVICE = "Urgency_Of_Service[0]";
public static final String CUSTOM_URGENCY_OF_SERVICE_STR = "Urgency_Of_Service[";
public static final String CUSTOM_SERVICES = "Services[0]";
public static final String CUSTOM_SERVICES_STR = "Services[";
public static final int    LEAD_CUSTOM_PACKAGE_ID_INT = 4;
public static final String MAXIMUM_MONTHLY_SPEND = "Maximum_Monthly_Spend";
public static final String CUSTOM_DAY = "Maximum_Monthly_Spend";

public static final String MONDAY_STR    = "Monday";
public static final String TUESDAY_STR   = "Tuesday";
public static final String WEDNESDAY_STR = "Wednesday";
public static final String THURSDAY_STR  = "Thursday";
public static final String FRIDAY_STR    = "Friday";
public static final String SATURDAY_STR  = "Saturday";
public static final String SUNDAY_STR    ="Sunday";


/*IPR SL-16934 : CreateBilling API*/

public static final int LEADS_TERMS_AND_COND_IND_SET = 1;
public static final String LMS_BILLING_ACCOUNT_IND_SET = "YES";
public static final int LEADS_PROFILE_ACCOUNT_CREATED = 303;


public static final String KEY = "Key";
public static final String API_ACTION = "API_Action";
public static final String API_LOGIN = "Login";
public static final String API_PASSWORD = "Password";
public static final String COMPANY_NAME = "Company_Name";
public static final String FIRST_NAME = "First_Name";
public static final String LAST_NAME = "Last_Name";
public static final String ADDRESS = "Address";
public static final String CITY = "City";
public static final String STATE = "State";
public static final String COUNTRY = "Country";
public static final String LEAD_ZIP = "Zip";
public static final String PHONE = "Phone";
public static final String CONTACT_EMAIL = "Contact_Email";
public static final String LEAD_EMAIL = "Lead_Email";
public static final String DELIVERY_OPTION = "Delivery_Option";
public static final String DELIVERY_OPTION_SMS = "1";
public static final String DELIVERY_OPTION_EMAIL = "4";
public static final String SECONDARY_PARTNER_LABEL = "Secondary_Partner_Label";
public static final String FAX = "Fax";
public static final String WEBSITE = "Website";							
public static final String STATUS = "Status";
public static final String STATUS_REASON = "Status_Reason";
public static final String NEWSLETTER = "Newsletter";
public static final String COMMENTS = "Comments";
public static final String SALES_REP_ID = "Sales_Rep_ID";
public static final String PROMO_CODE = "Promo_Code";
public static final String PARTNER_LABEL = "Partner_Label";			
public static final String PARTNER_GROUP = "Partner_Group";
public static final String PARTNER_ID = "Partner_ID";
public static final String FILTER_SET_NAME = "Filter_Set_Name";
public static final String FILTER_SET_PRICE = "Filter_Set_Price";
public static final String ACCEPTED_SOURCES = "Accepted_Sources";
public static final String MATCH_PRIORITY = "Match_Priority";
public static final String DAILY_LIMIT = "Daily_Limit";
public static final String MONTHLY_LIMIT = "Monthly_Limit";
public static final String DAY_OF_WEEK = "Day_Of_Week_Accept_Leads";
public static final String TIME_OF_DAY = "Time_Of_Day_Accept_Leads";
public static final String ACCEPT_MANUALLY_REVIEWED_LEADS = "Accept_Manually_Reviewed_Leads";
public static final String ZIP_MODE = "ZIP_Mode";
public static final String FILTER_ZIP = "ZIP";
public static final String ZIP_RADIUS = "ZIP_Radius";
public static final String LICENSING_STATE = "State";
public static final String PROJECT = "Project";
public static final String LOCATION_TYPE = "Residential_Or_Commercial";
public static final String SKILL = "Skill";
public static final String URGENCY_OF_SERVICE = "Urgency_Of_Service";
public static final String AMOUNT = "Amount";
public static final String ORGANIZATION_TYPE = "Organization_Type";
public static final String DESCRIPTION = "Description";
public static final String CARD_NO = "Card_Number";
public static final String EXPIRATION_YEAR = "Expiration_Year";
public static final String EXPIRATION_MONTH = "Expiration_Month";
public static final String CCV = "CCV";
public static final Object VENDOR_ID = "vendorid";
public static final String COUNTRY_CODE = "United States";
public static final String YES = "Yes";
public static final String NO = "No";

//Advanced Provider SO Management.
public static final String ADVANCED_PROVIDER_SO_MANAGEMENT_SCHEMA="/resources/schemas/advancedprovidersomanagement/";
public static final String ADVANCED_PROVIDER_SO_MANAGEMENT_NAMESPACE = "http://www.servicelive.com/namespaces/advancedprovmanagement";
public static final String SEARCH_PROVIDER_SO_RESPONSE_XSD = "getProviderSOListResponse.xsd";
public static final String SEARCH_PROVIDER_SO_RESPONSE_SCHEMALOCATION="http://www.servicelive.com/namespaces/advancedprovmanagement getProviderSOListResponse.xsd";

//New Services XSD and Schemas
public static final String NEW_SERVICES_NAMESPACE = "http://www.servicelive.com/namespaces/newservices";
public static final String FETCH_PROVIDER_FIRM_REQUEST_XSD="matchProviderFirmsRequest.xsd";
public static final String LEAD_REQUEST_XSD="leadRequest.xsd";
public static final String FETCH_PROVIDER_FIRM_RESPONSE_XSD="providerFirmsResponse.xsd";
public static final String GET_LEAD_DETAILS_REQUEST_XSD="getLeadDetailsRequest.xsd";
public static final String GET_LEAD_DETAILS_RESPONSE_XSD="getleadDeatilsResponse.xsd";

public static final String NEW_SERVICES_SCHEMA="/resources/schemas/newservices/";
public static final String FETCH_PROVIDER_FIRM_RESPONSE_SCHEMALOCATION="http://www.servicelive.com/namespaces/newservices providerFirmsResponse.xsd";
public static final String GET_LEAD_DETAILS_RESPONSE_SCHEMALOCATION="http://www.servicelive.com/namespaces/newservices getleadDeatilsResponse.xsd";
public static final String GET_PROVIDER_FIRM_DETAIL_REQUEST_XSD="getProviderFirmDetailRequest.xsd";
public static final String GET_PROVIDER_FIRM_DETAIL_RESPONSE_XSD="getProviderFirmDetailResponse.xsd";
public static final String GET_PROVIDER_FIRM_DETAIL_RESPONSE_SCHEMALOCATION="http://www.servicelive.com/namespaces/newservices getProviderFirmDetailResponse.xsd";

public static final String UPDATE_MEMBERSHIP_INFO_REQUEST_XSD="updateMembershipInfoRequest.xsd";
public static final String UPDATE_MEMBERSHIP_INFO_RESPONSE_XSD="updateMembershipInfoResponse.xsd";
public static final String UPDATE_MEMBERSHIP_INFO_RESPONSE_SCHEMALOCATION="http://www.servicelive.com/namespaces/newservices updateMembershipInfoResponse.xsd";

 
public static final String ADD_RATING_AND_REVIEW_REQUEST_XSD="addRatingandReviewRequest.xsd";
public static final String ADD_RATING_AND_REVIEW_RESPONSE_XSD="addRatingsandReviewResponse.xsd";
public static final String ADD_RATING_AND_REVIEW_RESPONSE_SCHEMALOCATION="http://www.servicelive.com/namespaces/newservices addRatingsandReviewResponse.xsd";

public static final String SCHEDULE_INFO_REQUEST_XSD="scheduleAppointmentRequest.xsd";
public static final String SCHEDULE_INFO_RESPONSE_XSD="scheduleAppointmentResponse.xsd";
public static final String SCHEDULE_INFO__RESPONSE_SCHEMALOCATION="http://www.servicelive.com/namespaces/newservices scheduleAppointmentResponse.xsd";
public static final String SCHEDULE_INFO__REQUEST_SCHEMALOCATION="http://www.servicelive.com/namespaces/newservices scheduleAppointmentRequest.xsd";

public static final String GET_ALL_LEADS_BY_FIRM_REQUEST_XSD  = "LeadInfoRequest.xsd";
public static final String GET_ALL_LEADS_BY_FIRM_RESPONSE_XSD = "LeadInfoResponse.xsd";
public static final String GET_ALL_LEADS_BY_FIRM_RESPONSE_SCHEMALOCATION="http://www.servicelive.com/namespaces/newservices LeadInfoResponse.xsd";

public static final String ASSIGN_OR_REASSIGN_PROVIDER_REQUEST_XSD = "assignOrReassignProviderRequest.xsd";
public static final String ASSIGN_OR_REASSIGN_PROVIDER_RESPONSE_XSD = "assignOrReassignProviderResponse.xsd";
public static final String ASSIGN_OR_REASSIGN_PROVIDER_RESPONSE_SCHEMALOCATION = "http://www.servicelive.com/namespaces/newservices assignOrReassignProviderResponse.xsd";

public static final String GET_ELIGIBLE_PROVIDERS_RESPONSE_XSD = "getEligibleProvidersResponse.xsd";
public static final String GET_ELIGIBLE_PROVIDERS_RESPONSE_SCHEMALOCATION = "http://www.servicelive.com/namespaces/newservices getEligibleProvidersResponse.xsd";

public static final String COMPLETES_LEADS_REQUEST_XSD = "completeLeadsRequest.xsd";
public static final String COMPLETES_LEADS_RESPONSE_XSD = "completeLeadsResponse.xsd";
public static final String COMPLETES_LEADS_RESPONSE_SCHEMALOCATION = "http://www.servicelive.com/namespaces/newservices completeLeadsResponse.xsd";

public static final String BUYER_SKU_RESPONSE_XSD = "serviceCategoryResponse.xsd";
public static final String BUYER_SKU_RESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/serviceCategoryResponse";
public static final String BUYER_SKU_RESPONSE_SCHEMALOCATION= "http://www.servicelive.com/namespaces/serviceCategoryResponse serviceCategoryResponse.xsd";

public static final String PROVIDER_CAPACITY_AVAILABLE_TIMESLOTS_XSD = "availableTimeSlotsResponse.xsd";
public static final String PROVIDER_CAPACITY_AVAILABLE_TIMESLOTS_NAMESPACE = "http://www.servicelive.com/namespaces/availableTimeSlotsResponse";
public static final String PROVIDER_CAPACITY_AVAILABLE_TIMESLOTS_SCHEMALOCATION = "http://www.servicelive.com/namespaces/availableTimeSlotsResponse availableTimeSlotsResponse.xsd";

public static final String FIRM_ID_LIST = "firmIdList";
public static final String FIRM_SEPERATOR = "_";
//New Services Constants
public static class DataFlowDirection{
	public static final String DATA_FLOW_CLIENT_TO_SL = "CLIENT_TO_SL";
	public static final String DATA_FLOW_SL_TO_LMS = "SL_TO_LMS";
	public static final String DATA_FLOW_LMS_TO_SL = "LMS_TO_SL";
	public static final String DATA_FLOW_SL_TO_CLIENT = "SL_TO_CLIENT";
}

public static class TypeOfInteraction{
	public static final String GET_FIRM_INTERACTION ="GET_FIRMS";
	public static final String POST_LEAD_INTERACTION = "POST_LEAD";
	public static final String GET_FIRM_DETAILS_INTERACTION= "GET_FIRM_DETAILS";
}

public static class LMSLeadStatus{
	public static final String MATCHED ="Matched";
	public static final String UNMATCHED = "Unmatched";
	public static final String ERROR= "Error";
}

public static final String COMPETITIVE_THREE_LEAD ="COMPETITIVE_THREE";
public static final String COMPETITIVE ="COMPETITIVE";
public static final String EXCLUSIVE_LEAD ="EXCLUSIVE";
public static final String FIRM_ID="firmid";
public static final String COUNT="count";
public static final String LEAD_STATUS="status";
public static final String SL_LEAD_ID="slleadid";

//R14.0 Submit Reschedule api
	public static final String REQUEST_PROCESSED = "001";
	public static final String REQUEST_ACCEPTED = "002";
	public static final String REQUEST_REJECTED = "003";

/**
 * FIXME
 * Pushkar - The below method should be removed as the following values should be read from DB. This is more of a quick fix. This has to be cleaned up post R3_8.
 * 
 */
private static Map<String, String> populateProblemDescMap(){
	Map<String, String> problemDesc = new HashMap<String, String>();
	problemDesc.put("1", "Abandoned Work");
	problemDesc.put("2", "Additional Part Required");
	problemDesc.put("3", "Additional Work Required");
	problemDesc.put("7", "End User No Show");
	problemDesc.put("10", "Out of Scope/Scope Mismatch");
	problemDesc.put("17", "Property Damage");
	problemDesc.put("18", "Provider No Show");
	problemDesc.put("19", "Provider Not Qualified to Complete Work");
	problemDesc.put("27", "Site Not Ready");
	problemDesc.put("29", "Unprofessional Action / Behavior");
	problemDesc.put("30", "Work Not Completed");
	problemDesc.put("52", "No Substatus");
	problemDesc.put("54", "Customer Delayed Service");
	problemDesc.put("55", "Close and Pay / Completion Record - Issue");
	problemDesc.put("62", "Scope Change");
	return problemDesc;
}

	public static  Map<String, String> filters(){
		Map<String, String> responseFilters = new HashMap<String, String>();
	//Filter Types
		responseFilters.put(GENERAL,GENERAL);
		responseFilters.put(SCOPEOFWORK,SCOPEOFWORK);
		responseFilters.put(SERVICELOCATION,SERVICELOCATION);
		responseFilters.put(SCHEDULE,SCHEDULE);
		responseFilters.put(PRICING,PRICING);
		responseFilters.put(CONTACTS,CONTACTS);
		responseFilters.put(ATTACHMENTS,ATTACHMENTS);
		responseFilters.put(PARTS,PARTS);
		responseFilters.put(CUSTOMREFERENCES,CUSTOMREFERENCES);
		responseFilters.put(NOTES,NOTES);
		responseFilters.put(HISTORY,HISTORY);
		responseFilters.put(ROUTEDPROVIDERS,ROUTEDPROVIDERS);
		responseFilters.put(FETCHREASONCODES,FETCHREASONCODES);
		responseFilters.put(FETCH_PRECALL_REASONCODES,FETCH_PRECALL_REASONCODES);		
		responseFilters.put(ESTIMATE,ESTIMATE);
		responseFilters.put(REVIEW,REVIEW);
		responseFilters.put(ADDONS,ADDONS);
		responseFilters.put(JOBCODES, JOBCODES);
		responseFilters.put(PAYMENTDETAILS, PAYMENTDETAILS);
		responseFilters.put(INVOICEPARTS, INVOICEPARTS);
		
		return responseFilters;
}
public static String problemDesc(String key){
	return problemDescMap.get(key).toString();
}

public static final int API_TIME_OUT = 10000;

//Error for API time out:
public static final String API_TIME_OUT_ERROR = "Unable to Perform User Action";

// CRM Request parameters
public static final String EQUALS= "=";
public static final String AND = "&";
public static final String COMMA = ",";
public static final String REQ_PHONE= "phone";
public static final String REQ_EMAIL= "email";
public static final String REQ_ZIP= "zip";
public static final String REQ_LEGALNAME= "legalName";
public static final String REQ_DOINGBUSINESSAS= "doingBusinessAsName";
public static final String REQ_NAME= "name";
public static final String REQ_SERVICE_INDUSTRY= "serviceIndustry";
public static final String REQ_REF_CODE= "refCode";
public static final String REQ_BUSSINESS_ADDRESS_STREET1 = "streetAddress";
public static final String OPTIONAL_DECISION_MAKER = "decisionMaker";
public static final String OPTIONAL_PROVIDER_ID= "providerid";
public static final String OPTIONAL_FIRM_ID= "firmid";


public static final String CRM_SUCCESS_CODE= "1";
public static final String CRM_FAILURE_CODE= "0";
public static final String LMS_PARTNER_STATUS_CREAETED = "Created";
public static final String LMS_PARTNER_STATUS_NOT_CREAETED = "Not Created";
public static final String LMS_PARTNER_STATUS_FILTERSET = "Filter Set Created";
public static final String LMS_PARTNER_STATUS_ACCOUNT = "Account Created";
public static final String CREDIT_CARD_AUTH_AMOUNT = "0.99";
public static final String CREDIT_CARD_AUTH_DESCRIPTION = "Lead profile Creation for partner ";

public static final String COMP_FILTER_LIST = "COMP";
public static final String EXCL_FILTER_LIST = "EXCL";

public static final String LMS_ACCEPTED_ALL_SOURCES = "ALLSOURCES";
public static final String LMS_ACCEPTED_SOURCE_AHS = "AHS";
public static final String LMS_ACCEPTED_SOURCE_ATT_PPC_YP = "ATT_PP_YC";
public static final String LMS_ACCEPTED_SOURCE_B2C = "B2C";
public static final String LMS_ACCEPTED_SOURCE_CALL_TRN_COM = "Call_Transfers_com";
public static final String LMS_ACCEPTED_SOURCE_CALL_TRN_SMS = "Call_Transfers_SMS";
public static final String LMS_ACCEPTED_SOURCE_CLIENT_SERIVCE = "Client_Service";
public static final String LMS_ACCEPTED_SOURCE_COMMSN_JUN = "CommissionJunction";
public static final String LMS_ACCEPTED_SOURCE_CRFTSMN = "Craftsman";
public static final String LMS_ACCEPTED_SOURCE_LP_GLOBAL = "LP_Global";
public static final String LMS_ACCEPTED_SOURCE_NEWCO = "NewCo";
public static final String LMS_ACCEPTED_SOURCE_NEW_SRC8 = "New_Src8";
public static final String LMS_ACCEPTED_SOURCE_SHS = "SHS";
public static final String LMS_ACCEPTED_SOURCE_SIGNUP = "signup";
public static final String LMS_ACCEPTED_SOURCE_SYWR = "SYWR";
public static final String LMS_MATCHING_PRIORITY = "5";
public static final String LMS_TIME_OF_DAY = "00:00-23:59";
public static final String LMS_ACCEPT_MANUAL_LEADS = "Yes";
public static final String LMS_ACCEPT_LISTED_ZIPS = "1";
public static final String LEAD_CUSTOM_PACKAGE_ID = "4";
public static final String LOCATION_TYPE_RES = "Residential";
public static final String LOCATION_TYPE_COM = "Commercial";
public static final String LOCATION_TYPE_BOTH = "Both";
public static final String LMS_LOCATION_TYPE_RES = "1";
public static final String LMS_LOCATION_TYPE_COM = "2";
public static final String LMS_LOCATION_TYPE_BOTH = "1,2";
public static final String SEPERATOR_PIPE = "|";
public static final String FILTER_TYPE_EXCLUSIVE = "Exclusive";
public static final String FILTER_TYPE_COMPETITIVE = "Competitive";


public static final int QUESTION_QUALITY =4;
public static final int QUESTION_COMMUNICATION =2;
public static final int QUESTION_TIMELINESS =1;
public static final int QUESTION_PROFESSIONALISM =3;
public static final int QUESTION_VALUE =5;
public static final int QUESTION_CLEANLINESS =6;

//Lead Add Note API
public static final String LEAD_ADDNOTE_REQUEST_XSD="leadAddNoteRequest.xsd";
public static final String LEAD_UPDATE_FIRM_STATUS_REQUEST_XSD="updateLeadStatusRequest.xsd";
public static final String LEAD_ADDNOTE_RESPONSE_XSD= "leadAddNoteResponse.xsd";
public static final String LEAD_UPDATE_FIRM_STATUS_RESPONSE_XSD= "updateLeadStatusResponse.xsd";
public static final String LEAD_ADDNOTE_REQUEST_NAMESPACE = "http://www.servicelive.com/namespaces/leadAddNoteRequest";
public static final String LEAD_ADDNOTE_RESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/leadAddNoteResponse";
public static final String LEAD_UPDATE_FIRM_STATUS_RESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/updateLeadStatusResponse";
public static final String LEAD_RESOURCES_SCHEMAS_V1_0 = "/resources/schemas/leaddetailmanagement/v1_0/";
public static final String LEAD_ADDNOTE_RESPONSE_SCHEMALOCATION = "http://www.servicelive.com/namespaces/leadAddNoteResponse leadAddNoteResponse.xsd";
public static final String LEAD_UPDATE_FIRM_STATUS_REQUEST_SCHEMALOCATION = "http://www.servicelive.com/namespaces updateLeadStatusRequest.xsd";
public static final String LEAD_UPDATE_FIRM_STATUS_RESPONSE_SCHEMALOCATION = "http://www.servicelive.com/namespaces updateLeadStatusResponse.xsd";
public static final String LEAD_ADDNOTE_REQUEST_SCHEMALOCATION = "http://www.servicelive.com/namespaces/leadAddNoteRequest leadAddNoteRequest.xsd";
public static final String CANCEL_LEAD_REQUEST_SCHEMALOCATION = "http://www.servicelive.com/namespaces/newservices cancelLeadRequest.xsd";
public static final String COMPLETE_LEAD_REQUEST_SCHEMALOCATION = "http://www.servicelive.com/namespaces/newservices completeLeadsRequest.xsd";
public static final String CANCEL_LEAD_REQUEST_XSD = "cancelLeadRequest.xsd";
public static final String CANCEL_LEAD_RESPONSE_XSD = "cancelLeadResponse.xsd";
public static final String CANCEL_LEAD_RESPONSE_SCHEMALOCATION = "http://www.servicelive.com/namespaces/newservices cancelLeadResponse.xsd";
public static final String LEAD_ASSIGN_REQUEST_SCHEMALOCATION = "http://www.servicelive.com/namespaces/newservices assignOrReassignProviderRequest.xsd";
public static final String LEAD_ASSIGN_RESPONSE_SCHEMALOCATION = "http://www.servicelive.com/namespaces/newservices assignOrReassignProviderResponse.xsd";

//SL-21086
public static final String GET_SUBSTATUS_RESPONSE_XSD="getSubstatusResponse.xsd";
public static final String GET_SUBSTATUS_RESPONSE_SCHEMALOCATION="http://www.servicelive.com/namespaces/newservices getSubstatusResponse.xsd";

//Changes for GetProviderFirmDetailService

//Constants needed for the warranty period static map
public static final String NINETY_DAYS = "90 days";
public static final String ONE_EIGHTY_DAYS = "180 days";
public static final String ONE_YEAR = "1 year";
public static final String TWO_YEARS = "2 years";
public static final String FIVE_YEARS = "5 years";
public static final String TEN_YEARS = "10 years";
public static final String LIFETIME = "Lifetime";

public static  Map<String, Integer> WARRANTY_PERIOD(){
	Map<String, Integer> warrantyPeriod = new HashMap<String, Integer>();
	//Warranty Periods
	warrantyPeriod.put(NINETY_DAYS,3);
	warrantyPeriod.put(ONE_EIGHTY_DAYS,6);
	warrantyPeriod.put(ONE_YEAR,12);
	warrantyPeriod.put(TWO_YEARS,24);
	warrantyPeriod.put(FIVE_YEARS,60);
	warrantyPeriod.put(TEN_YEARS,120);
	warrantyPeriod.put(LIFETIME,1200);		
	return warrantyPeriod;
}

//Constants needed for the company size static map
public static final String SOLE_PROPRIETOR = "Sole Proprietor or Individual";
public static final String TWO_TO_TEN_EMPLOYEES = "2 - 10 Employees";
public static final String ELEVEN_TO_FIFTY_EMPLOYEES = "11 - 50 Employees";
public static final String FIFTY_TO_TWOFIFTY_EMPLOYEES = "51 - 250 Employees";
public static final String TWOFIFTY_TO_FIVEHUNDRED_EMPLOYEES = "251 - 500 Employees";
public static final String FIVEHUNDRED_TO_THOUSAND = "501 - 1000 Employees";
public static final String THOUSAND_TO_THOUSANDFIVEHUNDRED = "1000+ Employees";
public static final String NOT_DISCLOSE = "Prefer to not disclose.";
public static final String NEW_LEAD_STATUS = "new";
public static final String WORKING_LEAD_STATUS = "working";
public static final String SCHEDULE_LEAD_STATUS = "scheduled";
public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
public static  Map<String, Integer> COMPANY_SIZE(){
	Map<String, Integer> companySize = new HashMap<String, Integer>();
	//Warranty Periods
	companySize.put(SOLE_PROPRIETOR,1);
	companySize.put(TWO_TO_TEN_EMPLOYEES,10);
	companySize.put(ELEVEN_TO_FIFTY_EMPLOYEES,50);
	companySize.put(FIFTY_TO_TWOFIFTY_EMPLOYEES,250);
	companySize.put(TWOFIFTY_TO_FIVEHUNDRED_EMPLOYEES,500);
	companySize.put(FIVEHUNDRED_TO_THOUSAND,1000);
	companySize.put(THOUSAND_TO_THOUSANDFIVEHUNDRED,1500);
	//warrantyPeriod.put(NOT_DISCLOSE,0);
	return companySize;
}
public static Map<String,Integer> leadFirmStatusMap(){
	Map<String, Integer> leadFirmStatus = new HashMap<String, Integer>();
	leadFirmStatus.put(NEW_LEAD_STATUS,1);
	leadFirmStatus.put(WORKING_LEAD_STATUS,2);
	leadFirmStatus.put(SCHEDULE_LEAD_STATUS,3);
	return leadFirmStatus;  
}
public static class AdvancedProviderSOManagement {

		public static final String PAGE_SIZE = "pageSize";
		public static final int DEFAULT_PAGE_SIZE = 10;
		public static final String PAGE_NUMBER = "pageNo";
		public static final String RESOURCE_ID = "resourceId";
		public static final String SO_STATUS = "status";

}

public static final Integer UPDATE_TIME_ACTION_ID = 274;
public static final String UPDATE_TIME_ACTION_DESC = "Appointment time updated to";
public static final String UPDATE_TIME_ACTION_DESC_STATUS = "The schedule status is ";

public static final String TIME_WINDOW_CALL_ATTEMPTED_DESC = "Time Window-Call Attempted (Not Confirmed)";
public static final String TIME_WINDOW_CALL_COMPLETED_DESC = "Time Window-Call Completed (Confirmed)";	

public static final String WHITE_SPACE = " ";
public static final String HYPHEN = "-";
// SL-19448 : Download Signed Copy WebService Constants
public static final String SIGNED_COPY_DOCUMENT_TITLE = "signed_copy_document_title";
public static final String DOCUMENT_ZIP_LOCATION ="zip_location";
public static final String SERVICE_ORDER_COUNT ="service_order_count";
public static final String FILE_SIZE_LIMIT ="file_size_limit";
public static final int RESULT_INT =1;
public static final String FILE_EXTENSION =".zip";
public static final Integer BUYER_ID_IS_EMPTY = -1;
public static final String  BUYER_ID_EMPTY ="buyerId";
public static final String  SO_ID_EMPTY ="soId";
public static final Integer COMPLETED_WF_STATUS = 160;
public static final Integer CLOSED_WF_STATUS = 180;
public static final Integer SEARS_BUYER = 1000;
public static final String DOCUMET_RETRIEVE_SUCCESS = "Success";
public static final Integer PUBLIC_NOTE = 0;

public static final Integer INHOME_BUYER_ID = 3000;
public static final Integer AT_AND_T_BUYER_ID = 512353;

public static final String REQUIRED = "Required";
public static final String NOT_STARTED = "Not Started";
public static final String Y = "Y";
public static final String N = "N";
public static final String PROVIDER_SIGNATURE = "Provider Signature";
public static final String CUSTOMER_SIGNATURE = "Customer Signature";
public static final String PROOF_OF_PERMIT = "Proof Of Permit";
public static final String INSTALLED = "Installed";
public static final String RETURNED = "Returned";
public static final String NOT_INSTALLED = "Not Installed";
//R12.0 Sprint 5: Invoice Parts
public static final String INDICATOR_PARTS_ADDED = "PARTS_ADDED";
public static final String CHECK="Check";

public static final Integer APPROVED_MARKET_READY = 6;

//SL-20420
public static final String DOC_TYPE = "doctype";
//SL-20678
public static final String POST_SO_TO_FIRM_XSD_REQ="soPostFirmRequest.xsd";
public static final String POST_SO_TO_FIRM_XSD_RESP="soPostFirmResponse.xsd";
public static final String SO_POSTFIRM_RESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/soPostFirmResponse";
public static final String SO_POSTFIRM_RESPONSE_SCHEMALOCATION= "http://www.servicelive.com/namespaces/soPostFirmResponse soPostFirmResponse.xsd";

//SLT-324
public static final String GET_SO_RESCHEDULE_REASON_XSD_RESP="soRescheduleReasonResponse.xsd";
public static final String SO_RESCHEDULE_REASON_RESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/soRescheduleReasonResponse";
public static final String SO_RESCHEDULE_REASON_RESPONSE_SCHEMALOCATION= "http://www.servicelive.com/namespaces/soRescheduleReasonResponse soRescheduleReasonResponse.xsd";
//SLT-324
public static final String GET_SO_CANCEL_REASON_XSD_RESP="soCancelReasonResponse.xsd";
public static final String SO_CANCEL_REASON_RESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/soCancelReasonResponse";
public static final String SO_CANCEL_REASON_RESPONSE_SCHEMALOCATION= "http://www.servicelive.com/namespaces/soCancelReasonResponse soCancelReasonResponse.xsd";

public static final String BUYER_CALLBACK_API_DETAILS_XSD_RESP="buyerDetailsEventCallbackResponse.xsd";
public static final String BUYER_CALLBACK_API_DETAILS_RESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/buyerDetailsEventCallbackResponse";
public static final String BUYER_CALLBACK_API_DETAILS_RESPONSE_SCHEMALOCATION= "http://www.servicelive.com/namespaces/soCancelReasonResponse buyerDetailsEventCallbackResponse.xsd";

public static final String BUYER_EVENT_ACK_API_DETAIL_REQUEST_XSD ="buyerEventCallbackAckRequest.xsd";
public static final String BUYER_EVENT_ACK_API_DETAILS_XSD_RESP="buyerEventCallbackAckResponse.xsd";
public static final String BUYER_EVENT_ACK_API_DETAILS_RESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/buyerEventCallbackAckResponse";
public static final String BUYER_EVENT_ACK_API_DETAILS_RESPONSE_SCHEMALOCATION= "http://www.servicelive.com/namespaces/buyerEventCallbackAckResponse buyerEventCallbackAckResponse.xsd";

public static final String BUYER_AUTHENTICATION_API_DETAILS_XSD_RESP="buyerAuthenticationDetailsResponse.xsd";
public static final String BUYER_AUTHENTICATION_API_DETAILS_RESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/buyerAuthenticationDetailsResponse";
public static final String BUYER_AUTHENTICATION_API_DETAILS_RESPONSE_SCHEMALOCATION= "http://www.servicelive.com/namespaces/buyerAuthenticationDetailsResponse buyerAuthenticationDetailsResponse.xsd";

//R12.4 Constants
public static final Integer BUYER_9000 = 9000;
public static final String SHOPIFY_ORDER_SKU_PRICE1 = "Shopify Order SKU Price1";
public static final String SHOPIFY_ORDER_SKU_PRICE2 = "Shopify Order SKU Price2";
public static final String SHOPIFY_ORDER_SKU_PRICE3 = "Shopify Order SKU Price3";
public static final String SHOPIFY_ORDER_SKU_PRICE4 = "Shopify Order SKU Price4";
public static final String TARGET_STRING = " ";
public static final String REPLACEMENT_STRING = "_";

//Create HI Provider Account
public static final String PRIMARY_CONTACT="Primary Email Address";
public static final String ALTERNATE_CONTACT="Alternate Email Address";
public static final String SMS_CONTACT="SMS Address";
public static Map<String,Integer> secondaryContactMap(){
	Map<String, Integer> secondaryContact = new HashMap<String, Integer>();
	secondaryContact.put(PRIMARY_CONTACT,1);
	secondaryContact.put(ALTERNATE_CONTACT,2);
	secondaryContact.put(SMS_CONTACT,3);
	return secondaryContact;  
}
//Substatus List available from mobile Only
public static final List<Integer> MOBILE_SUB_STATUS(){
	List<Integer> mobileSubStatus = new ArrayList<Integer>();
	mobileSubStatus.add(134);
	mobileSubStatus.add(135);
	mobileSubStatus.add(136);
	mobileSubStatus.add(137);
	return mobileSubStatus;
}
public static final List<String> ADD_PARTICIPANT_DETAILS() {
    List<String> apiDetailsNeeded = new ArrayList<String>();
    apiDetailsNeeded.add(OrderConstants.COMPANY_ID);
	    apiDetailsNeeded.add(OrderConstants.HEADER);
	    apiDetailsNeeded.add(OrderConstants.ADD_PARTICIPANT_API_URL);
	    apiDetailsNeeded.add(OrderConstants.ACQUISITION_CAMPAIGN_ID);
	    return apiDetailsNeeded;
}
public static final String BG_TYPE ="Team Member Background Check";
public static final int ADDRESS_TYPE_WORK = 4;
public final static String RESOURCE = "Team Member"; 
public final static String RESOURCE_BACKGROUND_CHECK = "Team Member Background Check";
public final static String RESOURCE_INCOMPLETE = "Incomplete"; 
public final static String RESOURCE_BACKGROUND_CHECK_INCOMPLETE = "Not Started";
public final static String RESOURCE_CREDENTIAL = "Team Member Credential"; 
public final static String BG_STATUS_NOT_STARTED = "Not Started";


public static final String UNIT_NUBMER = "UnitNumber";
public static final String ORDER_NUBMER = "OrderNumber";
public static final String CREATE_SO_API_LOGGING = "Create SO API";


public static final Integer IS_CRM_SUCCESS_CODE= 1;
public static final Integer IS_CRM_FAILURE_CODE= 0;
public static final String INSIDE_SALES_REFERRAL_PREFIX = "IS__";
//Priority 1
public static final String UPDATE_SO_CON_LOC_COMMENTS="An update to the order's overview, location, or customer contact information has been made.";

//SL-21086
public static final String UPDATE_SO_SUBSTATUS="Service Order Sub Status Changed to ";

public static final String CLIENT="CLIENT";

//B2C Changes
public static final String ESTIMATE = "Estimate";
public static final String COMPLETED_IN = "completedin";
public static final String NO_OF_ORDERS = "nooforders";
public static final String CLOSEDSORESPONSE_PRO_XSD = "closedSOProvResponse.xsd";
public static final String CLOSEDSORESPONSE_PRO_NAMESPACE = "http://www.servicelive.com/namespaces/pro/closedSOProviderResponse";
public static final String CLOSEDSORESPONSE_SCHEMALOCATION= "http://www.servicelive.com/namespaces/pro/so closedSOProviderResponse.xsd";
public static final int INTEGER_TWO = 2;
public static final int INTEGER_FOUR = 4;
public static final int INTEGER_SIX = 6;
public static final String GET_FIRM_DETAIL_REQUEST_XSD ="getFirmDetailsRequest.xsd";
public static final String GET_FIRM_DETAIL_RESPONSE_XSD ="getFirmDetailsResponse.xsd";
public static final String GET_FIRM_DETAIL_RESPONSE_SCHEMALOCATION ="";
public static final String GET_FIRM_DETAIL_SCHEMA="/resources/schemas/search/";
public static final String GET_FIRM_DETAIL_NAMESPACE="";
public static final String REVIEW = "Review";


//Firm Filter Types
	public static final String FULL = "full";	
	public static final String BASIC= "basic";
	public static final String CONTACT = "contact";	
	public static final String STATISTICS = "statistics";
	public static final String SERVICES = "services";	
	public static final String LASTORDER = "lastorder";	
	public static final String CREDENTIALS= "credentials";
	public static final String INSURANCES = "insurances";	
	public static final String WARRANTY = "warranty";
	public static final String REVIEWS = "reviews";	
	public static final String POLICY = "policy";
	
public static  List<String> getFirmFilters(){
	List<String> firmFilters = new ArrayList<String>();
	firmFilters.add(BASIC);
	firmFilters.add(CONTACT);
	firmFilters.add(STATISTICS);
	firmFilters.add(SERVICES);
	firmFilters.add(LASTORDER);
	firmFilters.add(CREDENTIALS);
	firmFilters.add(INSURANCES);
	firmFilters.add(WARRANTY);
	firmFilters.add(REVIEWS);
	firmFilters.add(POLICY);
	return firmFilters;
}

public static  List<String> getClosedOrderFilter(){
	List<String> closedOrderFilter = new ArrayList<String>();
	closedOrderFilter.add(ZIP);
	closedOrderFilter.add(COMPLETED_IN);
	closedOrderFilter.add(NO_OF_ORDERS);
	closedOrderFilter.add(OAUTH_CONSUMER_KEY);
	closedOrderFilter.add(OAUTH_NONCE);
	closedOrderFilter.add(OAUTH_SIGNATURE);
	closedOrderFilter.add(OAUTH_SIGNATURE_METHOD);
	closedOrderFilter.add(OAUTH_TIMESTAMP);
	closedOrderFilter.add(OAUTH_TOKEN);
	closedOrderFilter.add(OAUTH_VERSION);
	return closedOrderFilter;
}
public static final String RESPONSE_FILTER = "responsefilter";

public static final String FREE_ESTIMATE = "Do you charge for project estimates?";
public static final String WARRANTY_LABOR ="Do you offer a warranty on labor?";
public static final String WARRANTY_PARTS ="Do you offer a warranty on parts?";
public static final String DRUG_TEST= "Have you implemented a drug testing policy?";
public static final String CONSIDER_DRUG_TEST ="Would you consider implementing a drug testing policy?";
public static final String WORK_ENV ="Do you have written policies promoting a lawful and ethical work environment?";
public static final String CONSIDER_WORK_ENV ="Would you consider implementing these policies?";
public static final String CITIZEN_PROOF ="Do you require your employees to supply proof of citizenship?";
public static final String CONSIDER_CITIZEN_PROOF="Would you consider implementing this policy?";
public static final String REQUIRE_BDGE ="Do you require your crews to wear badges?";
public static final String CONSIDER_BAGDE  ="Would you consider implementing this policy?";

public static final String NOT_APPLICABLE="N/A";
public static final String APPROVED = "Approved";
public static final String COMPANY_CREDENTIAL_TYPE = "COMPANY";


public static final String OAUTH_CONSUMER_KEY = "oauth_consumer_key";
public static final String OAUTH_NONCE = "oauth_nonce";
public static final String OAUTH_TIMESTAMP = "oauth_timestamp";
public static final String OAUTH_SIGNATURE = "oauth_signature";
public static final String OAUTH_SIGNATURE_METHOD = "oauth_signature_method";
public static final String OAUTH_TOKEN = "oauth_token";
public static final String OAUTH_VERSION = "oauth_version";

//SL-21308: Standard Service Offerings: Search API constants

public static final String SEARCHFIRMSRESPONSE_XSD = "searchFirmsResponse.xsd";
public static final String SEARCHFIRMSRESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/pro/searchFirmsResponse";
public static final String RESOURCES_SEARCHFIRMS_SCHEMAS = "/resources/schemas/search/";
public static final String SERVICETIME_ALL = "All";
public static final String SERVICETIME_EARLY_MORNING = "Early Morning (before 8 AM)";
public static final String SERVICETIME_MORNING = "Morning 8-12";
public static final String SERVICETIME_AFTERNOON = "Afternoon 12-4";
public static final String SERVICETIME_EVENING = "Evening 4-8";

public static final Integer RELAY_SERVICES_BUYER_ID = 3333;
public static final Integer TECHTALK_SERVICES_BUYER_ID = 7777;

//R 16_2_0_1: SL-21376: Constants added for Search provider firms by zip and category and skill, SKU API
public static final String SKU = "sku";
public static final String SUBCATEGORY_ID = "subcategoryid";
public static final String MAIN_SERVICE_CATEGORY_ID = "mainservicecategoryid";
public static final String DESC = "Desc";
public static final int DEFAULT_MAX_DISTANCE = 40;
public static final String SEARCH_PROVIDER_FIRMS_RESPONSE = " Provider Firm(s) Returned";
public static final String SEARCH_PROVIDER_FIRMS_RESULT = " Provider Firm(s) Found. ";
public static final String PROVIDER_FIRM_SEARCH_RESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/searchbyzip";
public static final String SEPERATOR = "\\|";
public static final int DEFAULT_PAGE_SIZE = 400;
public static final String RATING = "rating";

//SL-21446-Relay Services: firm logo details 
public static final String DEFAULT_FIRM_LOGO = "default_firm_logo_path";
public static final String FIRM_LOGO_PATH = "firm_logo_retreival_path";

//SL-21378
public static final List<String> MAX_DISTANCE() {
    List<String> maxDistList = new ArrayList<String>();
    maxDistList.add("5");
    maxDistList.add("10");
    maxDistList.add("15");
    maxDistList.add("20");
    maxDistList.add("25");
    maxDistList.add("30");
    maxDistList.add("40");
    maxDistList.add("50");
    maxDistList.add("75");
    maxDistList.add("100");
    maxDistList.add("125");
    maxDistList.add("150");
    maxDistList.add("175");
    maxDistList.add("200");
    return maxDistList;
}

public static final List<String> PAGE_SIZE() {
    List<String> pageSizeList = new ArrayList<String>();
    pageSizeList.add("10");
    pageSizeList.add("20");
    pageSizeList.add("30");
    pageSizeList.add("50");
    pageSizeList.add("75");
    pageSizeList.add("100");
    return pageSizeList;
}

public static final Integer GET_ESTIMATE=247;
public static final String LABOR_TASK = "LABOR";
public static final String PARTS_TASK = "PART";
//public static final String AMOUNT_CAPS = "AMOUNT";
public static final String PERCENTAGE = "PERCENTAGE";
public static final String NEW = "NEW";
public static final String DRAFT_STATUS_YES= "YES";
public static final String DRAFT_STATUS_NO = "NO";
public static final String DRAFT_CAPS = "DRAFT";
public static final String ZERO_PRICE="0.00"; 

//buyerEventCallback
public static final String BUYER_EVENT_CALLBACK_RESPONSE_XSD="buyerEventCallbackResponse.xsd";
public static final String BUYER_EVENT_CALLBACK_RESPONSE_NAMESPACE="http://www.servicelive.com/namespaces/buyereventcallback";
public static final String BUYER_EVENT_CALLBACK_RESOURCES_SCHEMAS="/resources/schemas/buyerEventCallback/";
public static final String BUYER_EVENT_CALLBACK_RESPONSE_SCHEMALOCATION="http://www.servicelive.com/namespaces/buyereventcallback buyerEventCallbackResponse.xsd" ;
public static final String BUYER_EVENT_CALLBACK_ACK_RESPONSE_XSD="buyerEventCallbackAResponse.xsd";
//public static final String ACTIONID = "actionid";

//SLT-1649 Extended survey constants
public static final String SURVEY_REQUEST_SCHEMALOCATION = "http://www.servicelive.com/namespaces/surveyRequest surveyRequest.xsd";
public static final String SURVEY_REQUEST_NAMESPACE  = "http://www.servicelive.com/namespaces/surveyRequest";
public static final String SURVEY_REQUEST_XSD = "surveyRequest.xsd";
public static final String SURVEY_RESPONSE_SCHEMALOCATION= "http://www.servicelive.com/namespaces/surveyResponse surveyResponse.xsd";
public static final String SURVEY_RESPONSE_XSD = "surveyResponse.xsd";
public static final String SURVEY_RESOURCES_SCHEMAS="/resources/schemas/survey/";
public static final String SURVEY_RESPONSE_NAMESPACE= "http://www.servicelive.com/namespaces/surveyResponse";
public static final Integer CONSUMER_ENTITY_TYPE_ID = 40;
public static final Integer ANSWER_BIT_ONE = 1;
public static final Integer ANSWER_BIT_ZERO = 0;
public static final String AGREED= "agreed";
public static final String SUBMITTED="submitted";
public static final String SURVEY_OPTION_ID="survey_option_id";
public static final String CSAT="CSAT";
public static final String NPS="NPS";
//SLT-3974
public static final String COVERAGE_CODE="PT";
public static final String CHARGE_AMOUNT="85.00";
public static final String RELATED_FLAG="R";
public static final String SEQUENCE="1";


public static HashMap<String, String> coverageCodes(){
	HashMap<String, String> coverageCodes = new HashMap<String, String>();
	coverageCodes.put(PROTECTION_AGREEMENT, SP);
	coverageCodes.put(IN_WARRANTY, IW);
	coverageCodes.put(Pay_Roll_Transfer, PT);
	coverageCodes.put(Customer_Collect, CC);
	coverageCodes.put(IW, IW);
	return coverageCodes;
}

public static List<Integer> SKILL_CATEGORY_IDS_REPAIR_PRICE =  Arrays.asList(1000,600,1,1400);
public static String REPAIR_CHARGE_AMOUNT = "165.00";

}