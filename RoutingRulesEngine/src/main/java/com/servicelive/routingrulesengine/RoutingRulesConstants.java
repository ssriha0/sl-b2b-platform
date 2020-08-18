package com.servicelive.routingrulesengine;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines various constants used by CAR Frontend 
 */
public class RoutingRulesConstants {

	public static final String MODIFIED_BY = "System User";
	public static final String SYSTEM= "System";

	public static final String ROUTING_RULE_STATUS_ACTIVE = "ACTIVE";
	public static final String ROUTING_RULE_STATUS_INACTIVE = "INACTIVE";
	public static final String ROUTING_RULE_STATUS_ARCHIVED = "ARCHIVED";
	
	public static final String ROUTING_RULE_SUBSTATUS_ERROR = "Error";
	public static final String ROUTING_RULE_SUBSTATUS_VALID = "Valid";
	public static final String ROUTING_RULE_SUBSTATUS_CONFLICT = "Conflict";
	
	public static final String ROUTING_RULE_UPLOAD_SUCCESS = "Success";
	public static final String ROUTING_RULE_UPLOAD_ERROR = "Error";
	
	public static final String ROUTING_ALERT_STATUS_ACTIVE = "ACTIVE";
	public static final String ROUTING_ALERT_STATUS_INACTIVE = "INACTIVE";
	public static final String ROUTING_ALERT_SUBSTATUS_CONFLICT = "conflict";

	public static final String SPN_APPROVED = "SP SPN APPROVED"; //FIXME: This should be moved to an enum in SLCommonDomain
	public static final String PF_SPN_MEMBER= "PF SPN MEMBER";

	public static final String CUSTOM_REF_SPECIALTY_CODE = "SPECIALTY CODE";

	public static final String CRITERIA_NAME_MARKET = "MARKET";
	public static final String CRITERIA_NAME_ZIP = "ZIP";
	public static final String CRITERIA_NAME_PICKUP_LOCATION = "PICKUP LOCATION CODE";
	public static final String DELIMITER = "@@";
	public static final String PREFIX_CHOSEN_JOB_PRICE = "chosenJobPrice";
	public static final String PREFIX_AUTO_PULL = "isAutoPull_";
	public static final String ON = "on";
	
	public static final Integer ROUTING_ALERT_PRICING_ID = Integer.valueOf(1);
	public static final Integer ROUTING_ALERT_PROVIDER_STATUS_ID = Integer.valueOf(2);
	
	public static final Integer PROVIDER_FIRM_SERVICE_LIVE_APPROVED = Integer.valueOf(34);
	public static final Integer PROVIDER_FIRM_SEARS_APPROVED = Integer.valueOf(3);
	public static final Integer PROVIDER_MARKET_READY = Integer.valueOf(6);
	
	public static final int ROUTING_RULE_HDR_COMMENT_LEN = 512;
	public static final int ROUTING_RULE_HDR_RULE_NAME_LEN = 90;
	public static final int CONTACT_NAME_LEN = 50;
	
	public static final int SEARCH_RULE_BY_FIRM_ID = 1;
	public static final int SEARCH_RULE_BY_FIRM_NAME = 2;
	public static final int SEARCH_RULE_BY_RULE_NAME = 3;
	
	public static final int ACCEPTED_STATUS = 150;

	public static final int SEARCH_RULE_BY_UPLOADED_FILE_NAME  = 4;
	public static final int SEARCH_RULE_BY_RULE_ID = 5;
	public static final int SEARCH_RULE_BY_PROCESS_ID = 6;
	
	// SL 15642 constant to set value for auto accept search
	public static final int SEARCH_RULE_BY_AUTO_ACCEPTANCE_STATUS = 7;
	
	public static final String PERSISTENT_ERROR = "Persistent";
	public static final String NON_PERSISTENT_ERROR = "Non-Persistent";
	
	public static final Integer INVALID_ZIP = Integer.valueOf(1);
	public static final Integer INVALID_JOBCODE = Integer.valueOf(2);
	public static final Integer MISSING_MANDATORY = Integer.valueOf(3);
	public static final Integer CONFLICTING_JOBCODES =Integer.valueOf(4);
	public static final Integer CONFLICTING_ZIPCODES = Integer.valueOf(5);
	public static final Integer CONFLICTING_PICKUPLOCATIONS = Integer.valueOf(6);
	public static final Integer ZIPCODES_ADDED = Integer.valueOf(7);
	public static final Integer ZIPCODES_REMOVED = Integer.valueOf(8);
	public static final Integer JOBCODES_ADDED = Integer.valueOf(9);
	public static final Integer JOBCODES_REMOVED = Integer.valueOf(10);
	public static final Integer INVALID_FIRMS = Integer.valueOf(11);
	public static final Integer INVALID_CUST_REFS = Integer.valueOf(12);
	public static final Integer INVALID_STATUS = Integer.valueOf(13);
	public static final Integer INVALID_PRICE = Integer.valueOf(14);
	public static final Integer RULE_ERROR = Integer.valueOf(15);
	public static final Integer RULE_CONFLICT = Integer.valueOf(16);
	public static final Integer CUST_REFS_ADDED = Integer.valueOf(17);
	public static final Integer CUST_REFS_REMOVED = Integer.valueOf(18);
	public static final Integer FIRMS_ADDED = Integer.valueOf(19);
	public static final Integer FIRMS_REMOVED = Integer.valueOf(20);
	public static final Integer MANDATORY_DELETED = Integer.valueOf(25);
	public static final Integer INVALID_INFO = Integer.valueOf(26);
	public static final Integer CONFLICTING_MARKETS = Integer.valueOf(27);
	public static final Integer INVALID_CUST_REF_VALUE = Integer.valueOf(28);
	public static final Integer INVALID_FIRM_STATE = Integer.valueOf(29);
	public static final Integer EXCEPTION = Integer.valueOf(30);
	public static final Integer INVALID_STATUS_ARCHIVE = Integer.valueOf(31);
	public static final Integer CUST_REF_MISSING = Integer.valueOf(32);
	public static final Integer JOB_CODE_MISSING = Integer.valueOf(33);
	
	public static final String VALID_FIRMIDS = "validFirmIds";
	public static final String INVALID_FIRMIDS = "invalidFirmIds";
	public static final String VALID_ZIPS = "validZips";
	public static final String INVALID_ZIPS = "invalidZips";
	public static final String DUPLICATE_ZIPS = "duplicateZips";
	public static final String VALID_JOBCODES = "validJobCodes";
	public static final String INVALID_JOBCODES = "invalidJobCodes";
	public static final String INVALID_JOBCODE_PRICE = "invalidJobCodesPrice";
	public static final String VALID_CUSTREF = "validBuyerReference";
	public static final String INVALID_CUSTREF = "invalidBuyerReference";
	public static final String INVALID_CUSTREF_VAL = "invalidBuyerReferenceVal";
	public static final String MISSING_CUSTREF = "missingBuyerReference";
	public static final String MISSING_JOBCODES = "missingJobCodes";
	public static final String PERMIT_JOBCODES = "permitJobCodes";
	
	public static final String UPLOAD_ACTION_NEW="New";
	public static final String UPLOAD_ACTION_ACTIVE="Active";
	public static final String UPLOAD_ACTION_ADD="Add";
	public static final String UPLOAD_ACTION_DELETE="Remove";
	public static final String UPLOAD_ACTION_ARCHIVE="Archive";
	public static final String UPLOAD_ACTION_INACTIVE="Inactive";
	public static final String UPLOAD_ACTION_ERROR="Error";
	
	public static final String UPLOAD_NEW_NONPERSISTENT_MESSAGE="Following Updates were not made for the file ";
	public static final String UPLOAD_INACTIVE_NONPERSISTENT_MESSAGE="Archived rule cannot be inactivated";
	public static final String UPLOAD_ARCHIVE_NONPERSISTENT_MESSAGE="Active rule cannot be archived";
	public static final String UPLOAD_ADD_NONPERSISTENT_MESSAGE="Following Updates were not made for the file ";
	public static final String UPLOAD_DELETE_NONPERSISTENT_MESSAGE="Following Updates were not made for the file ";
	public static final String UPLOAD_ACTIVE_NONPERSISTENT_MESSAGE="Archived rule cannot be activated";
	
	public static final String UPLOAD_ADD_NONPERSISTENT_CONFLICT="Updates in upload file ";
	public static final String UPLOAD_ADD_NONPERSISTENT_CONFLICT_END=" created the following conflict(s) and were not applied.";
	
	public static final String UPLOAD_NEW_PERSISTENT_MESSAGE="Following Updates wew not made (New)";
	public static final String UPLOAD_ADD_PERSISTENT_MESSAGE="Following Updates wew not made (Add)";
	public static final String UPLOAD_DELETE_PERSISTENT_MESSAGE="Following Updates wew not made (Delete)";
	
	public static final String UPLOAD_ACTIVE_PERSISTENT_CONFLICT="An attempt to activate this rule was made in the upload file ";
	public static final String UPLOAD_ACTIVE_PERSISTENT_END=" and could not be applied due to the following conflict(s).";
	
	//Routing Rule File
	public static final String ROUTING_RULE_FILE_PATH = "/appl/sl/iss/routingrules/indata/";
	public static final String ROUTING_RULE_ERROR_FILE_PATH = "/appl/sl/iss/routingrules/error/";
	public static final String ROUTING_RULE_SUCCESS_FILE_PATH = "/appl/sl/iss/routingrules/success/";
	public static final String MODIFIED_BY_CAR = "CAR Upload Tool";
	
	public static final Integer FILE_ERROR_INVALID_RULE = 21;
	public static final Integer FILE_ERROR_INVALID_FILE = 22;
	public static final Integer FILE_ERROR_RULE_EXISTS = 23;
	public static final Integer FILE_ERROR_NO_RULE = 24;
	
	//Routing rule hist
	public static final String HISTORY_ARCHIVED = "Archived";
	public static final String HISTORY_ACTIVATED = "Activated";
	public static final String HISTORY_CREATED = "Created";
	public static final String HISTORY_DEACTIVATED = "Deactivated";
	public static final String HISTORY_UPDATED = "Updated";
	
	//Sl 15642 Auto Accept different status 
	public static final String AUTO_ACCEPT_PENDING_STATUS="PENDING";
	public static final String AUTO_ACCEPT_ON_STATUS="ON";
	public static final String AUTO_ACCEPT_NA_STATUS="NA";
	public static final String AUTO_ACCEPT_HISTORY_CREATED = "RULE Created";
	public static final String AUTO_ACCEPT_HISTORY_UPDATED = "RULE Updated";
	
	public static final String FILE_STATUS_SCHEDULED = "Scheduled";
	public static final String FILE_STATUS_PROCESSING = "Processing";
	public static final String FILE_STATUS_SUCCESS = "Success";
	public static final String FILE_STATUS_FAILURE = "Failure";
	
	public static final String FILE_ERROR_INVALID_RULE_DESC = "Following invalid rules";
	public static final String FILE_ERROR_INVALID_RULE_DESC_END = "are found in the file";
	public static final String FILE_ERROR_INVALID_FILE_DESC = "Invalid file";
	public static final String FILE_ERROR_RULE_EXISTS_DESC = "Following rule";
	public static final String FILE_ERROR_RULE_EXISTS_END = "is already existing";
	public static final String FILE_ERROR_NO_RULE_DESC = "No Rules are defined in the file";
	
	public static final String NO_FILE = "Please select a file to upload.";
	public static final String WRONG_FILE_EXT = "Please upload .xls or .xlsx template only.";
	public static final String FILE_EXISTS = "A file with this name exists, please upload the template with a unique file name.";
	public static final String TOO_MANY_SHEETS = "Maximum number of sheets allowed per file is ";
	public static final String TOO_MANY_LINES = "The number of entries in at least one of the fields exceeds the maximum allowed at one time.Please check the information and try again. ";
	public static final String INVALID_SHEET = "Uploaded file has empty sheet";
	public static final String XLSX_FILE_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	public static final String XLS_FILE_TYPE = "application/vnd.ms-excel";
	
	public static final String RULE_ACTION_NEW = "NEW";
	public static final String RULE_ACTION_UPDATE = "UPDATE";
	public static final String RULE_ACTION_INACTIVATE ="INACTIVATE";
	public static final String RULE_ACTION_ACTIVATE = "ACTIVATE";
	public static final String RULE_ACTION_ARCHIVED = "ARCHIVED";
	
	public static final String FILE_STATUS_ERROR = "Error";
	public static final String FILE_STATUS_CONFLICT = "Conflict";

	public static final String INVALID_TEMPLATE = "Please upload a valid template.";
	
	public static final String CREATE_RULE_TEMPLATE = "download_create_rule_template";
	public static final String UPDATE_RULE_TEMPLATE = "download_update_rule_template";
	public static final String ARCHIVE_RULE_TEMPLATE = "download_archive_rule_template";
	public static final String ROUTING_RULE_FILE_DIR = "rule_file_upload_dir";
	public static final String UPLOAD_PAGE_REFRESH = "upload_rule_page_refresh";
	
	public static final String RULE_FILE_SHEETS = "rule_file_number_of_sheets";
	public static final String RULE_FILE_HEADERS = "rule_file_number_of_header_rows";
	public static final String RULE_FILE_DATA = "rule_file_rows_per_sheet";
	public static final String RULE_FILE_ARCHIVE_DATA = "rule_file_rows_per_archive_inactive_sheet";
	
	public static final String RULE_FILE_SHEETS_DEFAULT = "1";
	public static final String RULE_FILE_HEADERS_DEFAULT = "12";
	public static final String RULE_FILE_DATA_DEFAULT = "2000";
	public static final String RULE_FILE_ARCHIVE_DATA_DEFAULT = "200";
	
	
	public static final Integer UPDATE_FROM_BATCH = Integer.valueOf(0);
	public static final Integer UPDATE_FROM_FRONT = Integer.valueOf(1);
		
	public static final String INVALID_ZIP_ERROR = "Invalid Zip Code(s): ${##}.";
	public static final String INVALID_JOBCODE_ERROR = "Invalid Job Code(s): ${##}.";
	public static final String MISSING_MANDATORY_ERROR = "Missing ${##}."; 
	public static final String DELETING_MANDATORY_ERROR = "An attempt to delete the mandatory field(s): ${##} was made and the updates were not applied."; 
	public static final String INVALID_MANDATORY_ERROR = "Following field(s) are invalid: ${##}."; 
	public static final String INVALID_FIRMS_ERROR = "Invalid provider firm(s): ${##}.";
	public static final String INVALID_CUST_REFS_ERROR= "Invalid Custom Reference Type(s): ${##}.";
	public static final String INVALID_FIRM_STATE_ERROR= "Provider Firm(s) ${##} status is inactive.";
	public static final String INVALID_PRICE_ERROR= "Invalid Job Code Price : ${##}.";
	public static final String FILE_ERROR_NO_RULE_ERROR= "No rules were found in the file.";
	public static final String FILE_ERROR_NO_VALUE_TO_UPDATE_ERROR= "No values were found in the file to update the rule.";	
	public static final String FILE_ERROR_INVALID_RULE_ERROR= "No existing rule(s) ${##} were found and updates were not applied.";
	public static final String FILE_ERROR_RULE_EXISTS_ERROR= "A rule with name ${##} exists, please upload the template with a unique rule name.";
	public static final String INVALID_CUST_REF_VALUE_ERROR= "Invalid Custom Reference Value(s): ${##}.";
	public static final String EDIT_ARCHIVED_RULE_ERROR= "Rule(s) ${##} are currently archived and cannot be updated.";
	public static final String INVALID_STATUS_DO_ARCHIVE_ERROR= "Rule(s) ${##} ";
	public static final String ARCHIVED = "are currently active and cannot be archived.";
	public static final String DE_ACTIVATED = "are currently archived and cannot be deactivated.";
	
	public static final String NULL_CRITERIA_PROVIDER_ID= "Provider Firm ID";
	public static final String NULL_CRITERIA_PROVIDER_NAME= "Provider Firm Name";
	public static final String NULL_CRITERIA_RULE_NAME= "Rule Name";
	public static final String NULL_CRITERIA_UPLOADED_FILE_NAME= "Uploaded File Name";
	public static final String NULL_CRITERIA_RULE_ID= "Rule Id";
	public static final String NULL_CRITERIA_PROCESS_ID= "Process Id";
	public static final String NULL_CRITERIA_AUTOACCEPT_LABEL= "Auto Accept Label";
	
	public static final String NULL_CRITERIA_DELIMITER= ": ";
	
	public static final Map<Integer, String> errorDescMap = new HashMap<Integer, String>(){
		 {
			 put(INVALID_ZIP, INVALID_ZIP_ERROR);
			 put(INVALID_JOBCODE, INVALID_JOBCODE_ERROR);
			 put(MISSING_MANDATORY, MISSING_MANDATORY_ERROR);
			 put(INVALID_FIRMS, INVALID_FIRMS_ERROR);
			 put(INVALID_CUST_REFS, INVALID_CUST_REFS_ERROR);
			 put(INVALID_STATUS, INVALID_STATUS_DO_ARCHIVE_ERROR);
			 put(INVALID_PRICE, INVALID_PRICE_ERROR);
			 put(FILE_ERROR_INVALID_RULE, FILE_ERROR_INVALID_RULE_ERROR);
			 put(FILE_ERROR_INVALID_FILE, FILE_ERROR_NO_VALUE_TO_UPDATE_ERROR);
			 put(FILE_ERROR_RULE_EXISTS, FILE_ERROR_RULE_EXISTS_ERROR);
			 put(FILE_ERROR_NO_RULE, FILE_ERROR_NO_RULE_ERROR);
			 put(MANDATORY_DELETED, DELETING_MANDATORY_ERROR);
			 put(INVALID_INFO, INVALID_MANDATORY_ERROR);
			 put(INVALID_CUST_REF_VALUE, INVALID_CUST_REF_VALUE_ERROR);			 
			 put(INVALID_FIRM_STATE, INVALID_FIRM_STATE_ERROR);
			 put(INVALID_STATUS_ARCHIVE,EDIT_ARCHIVED_RULE_ERROR);
			 put(CUST_REF_MISSING,INVALID_CUST_REF_VALUE_ERROR);
			 put(JOB_CODE_MISSING,INVALID_PRICE_ERROR);
		 }
	 };
	 
	 public static final Map<Integer, String> commonFileHeaderMap = new HashMap<Integer, String>(){
		 {
			 put(0, RULE_HEADER_ACTION);
			 put(1, RULE_HEADER_COMMENTS);
			 put(2, RULE_HEADER_PROVIDER_FIRM);
			 put(3, RULE_HEADER_RULE_NAME);
			 put(4, RULE_FILE_RULE_ID);
			 put(5, RULE_HEADER_FIRST_NAME);
			 put(6, RULE_HEADER_LAST_NAME);
			 put(7, RULE_HEADER_EMAIL);
			 put(8, RULE_FILE_VERTICAL);
		 }
	 };
	 
	 public static final Map<Integer, String> createFileHeaderMap = new HashMap<Integer, String>(){
		 {
			 put(0, RULE_FILE_PROVIDER_FIRM);
			 put(1, RULE_HEADER_ZIP);
			 put(2, RULE_HEADER_CUST_REF_NAME);
			 put(3, RULE_HEADER_CUST_REF_VALUE);
			 put(4, RULE_HEADER_JOBCODE);
			 put(5, RULE_HEADER_PRICE);
			 put(6, RULE_FILE_SUMMARY);
			 put(7, RULE_FILE_MAINADDON);
		 }
	 };
	 
	 public static final String RULE_HEADER_ACTION = "Action";
	 public static final String RULE_HEADER_COMMENTS = "Comments";
	 public static final String RULE_HEADER_RULE_NAME = "Rule Name";
	 public static final String RULE_HEADER_FIRST_NAME = "Contact First Name";
	 public static final String RULE_HEADER_LAST_NAME= "Contact Last Name";
	 public static final String RULE_HEADER_EMAIL = "Contact Email";
	 public static final String RULE_HEADER_ZIP = "Zip Codes";
	 public static final String RULE_HEADER_CUST_REF_NAME= "Custom Ref Name";
	 public static final String RULE_HEADER_CUST_REF_VALUE = "Custom Ref Value";
	 public static final String RULE_HEADER_JOBCODE = "Job Code";
	 public static final String RULE_HEADER_PRICE= "Price";
	 public static final String RULE_FILE_PROVIDER_FIRM= "Provider Firm Ids";
	 public static final String RULE_HEADER_PROVIDER_FIRM = "Provider Firm";
	 public static final String RULE_FILE_RULE_ID = "Rule Id";
	 public static final String RULE_FILE_VERTICAL = "Vertical";
	 public static final String RULE_FILE_SUMMARY = "Summary Description";
	 public static final String RULE_FILE_MAINADDON = "Main/Add On";
	 
	 //SL-20643
	 public static final String PERMIT_SKU = "99888";
	 public static final String PERMIT_ERROR = "99888 is a permit SKU and cannot be added to a CAR rule";
	 
	 public static final String INITIAL_CACHE_FILENAME = "initial_rule_cache_file";
	 public static final String UPDATE_CACHE_FILENAME = "update_rule_cache_file";
	 public static final String INITIAL_CACHE_NO_FILENAME = "/appl/sl/iss/routingrules/cache/initial_rule_cache";
	 public static final String UPDATE_CACHE_NO_FILENAME = "/appl/sl/iss/routingrules/cache/updated_rule_cache";
	 public static final String CACHE_WRITE_SWITCH_ON = "cache_write_required";
	 public static final String CACHE_WRITE_SWITCH_ON_DEFAULT = "0";
}
