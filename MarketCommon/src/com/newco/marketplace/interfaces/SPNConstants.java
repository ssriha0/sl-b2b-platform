package com.newco.marketplace.interfaces;

public interface SPNConstants {

	public final static int INVITATION_EXPIRY_DAYS = 30;
	
	public final static String SPN_BUTTON_TYPE_SELECT = "Select";
	public final static String SPN_BUTTON_TYPE_UPDATE = "Update";

	//Constants for Select Provider Network (SPN)
	public final static String PF_INVITED_TO_SPN= "PF INVITED TO SPN";
	public final static String PF_SPN_NOT_INTERESTED= "PF SPN NOT INTERESTED";
	public final static String SPN_MEMBERSHIP_INCOMPLETE = "Incomplete";
	public static final String[] SPN_MEMBERSHIP_INCOMPLETE_FIRM_STATES = { "PF INVITED TO SPN", "PF SPN INTERESTED", "PF APPLICANT INCOMPLETE", "PF SPN NOT INTERESTED"};
	public final static Integer SUCCESS_IND = 1; 
	public final static String SPN_NAME = "SPN_Name";
	public final static String SPNET_ID = "spnID";
	public final static String VENDOR_ID = "vendorId";
	public final static String VELOCITY_TEMPLATE = "velocity template";
	public final static String SPN_COMM_MONITOR_LIST= "spnCommMonitorList";
	public final static String SPN_MONITOR_AVAILABLE= "spnMonitorAvailable";
	public final static String SPN_MONITOR_LOAD = "spn_monitor_load";
	public final static String SPN_MAIN_MONITOR_LIST= "spnMainMonitorList";
	public final static String SELECTED_SPN_ID = "selectedSpnId"; 
	public final static String SELECTED_SPN = "selectedSpn";
	public final static String DOC_INCOMPLETE= "Incomplete";
	public final static String DOC_INCOMPLETE_STATE_ID= "DOC INCOMPLETE";
		
	public final static Integer SPN_DOCUMENT_CATEGORY_ID = 6;
	public final static Integer SPN_DOCUMENT_ACTIVE_IND = 1;
	public final static String SPN_DOCUMENT_PENDING_APPROVAL_DESC = "Pending Approval";
	public final static String SPN_DOCUMENT_PENDING_APPROVAL_ID = "DOC PENDING APPROVAL";
	public final static String SPN_DOC_TITLE = "dtitle";
	public final static String SPN_BUYER_ID = "buyerId";
	public final static String SPN_BUYER_DOC_ID = "buyerDocId";
	public final static String SPN_SPN_ID = "spnId";
	public final static String SPN_PROV_DOC_ID = "proDocId";
	public final static String SPN_BUTTON_TYPE = "buttonType";
	public final static String SPN_FILE_INPUT = "fileToUpload";
	public final static String SPN_NONE_SELECTED = "none";
	public final static String SPN_SPNBUYERID_ID = "spnBuyerId";
			
	//Constants for Buyer Agreement Modal in SPN
	public final static String DOCUMENT_ID = "docID";
	public final static String SPN_ID = "spnID";
	public final static String DOCUMENT_LIST = "documenList";
	public final static String APPLICANT = "PF SPN APPLICANT";
	public final static int BIT_VAL=256;
	public final static int ZERO=0;
	public final static String INCOMPLETE ="SPN INCOMPLETE";
	public final static String APPLICANT_INCOMPLETE ="PF APPLICANT INCOMPLETE";
	public final static String REAPPLICANT = "PF SPN REAPPLICANT";
	public final static String SPN_SELECTED = "selectSpn";
	public final static String SPN_MEMBER ="PF SPN MEMBER";
	//Constants for SPN Provider Invitation  
	public final static int ONE=1;
	public final static int THREE=3;
	public final static String PROVIDER_INVITATION="providerInvitation";
	public final static String ACCEPT_INVITATION = "acceptInvite";
	public final static String INTERESTED = "PF SPN INTERESTED";
	public final static String NOT_INTERESTED = "PF SPN NOT INTERESTED";
	public final static String DASHBOARD = "dashboard";
	
	//Constants for Criteria Display
	public final static String CRITERIA_NAME_CREDENTIALS = "Credentials";
	public final static String CRITERIA_NAME_INSURANCE = "Insurance";
	public final static String CRITERIA_NAME_SERVICES = "Services & Skills";
	public final static String CRITERIA_NAME_RATING = "Minimum Rating";
	public final static String CRITERIA_NAME_LANGUAGE = "Language";
	public final static String CRITERIA_NAME_COMPLETED_SO = "Minimum Completed Service Orders";
	public final static String CRITERIA_NAME_MEETING = "Meeting";
	public final static String CRITERIA_NAME_MARKET = "Market";
	public final static String CRITERIA_NAME_SALES_VOLUME = "Sales Volume";
	public final static String CRITERIA_NAME_COMPANY_SIZE = "Company Size";
	public final static String AUTO_LIABILITY = "Vehicle Liability|$";
	public final static String COMMERCIAL_LIABILITY = "Commercial General Liability|$";
	public final static String WORKMAN_COMPENSATION = "Worker's Compensation";//"WorkMan Compensation | Verified byServiceLive";
	public final static String CRITERIA_NAME_STATE = "State";
	public final static String MATCH_CRITERIA="1";
	public final static String NOT_MATCH_CRITERIA="0";
	
	//SL-18018
	public final static String OVERRIDED_CRITERIA="2";

	public final static String VERIFIED_BY_SERVICELIVE=""; //"|Verified by ServiceLive*";
	
	//Default values
	/** */public static final String CRITERIA_VALUE_TRUE = "TRUE" ;
	
	//ALL SPN APProval Criteria
	/** */public static final String CRITERIA_MAIN_SERVICES = "Main Services" ;
	/** */public static final String CRITERIA_SKILLS = "Skills" ;
	/** */public static final String CRITERIA_CATEGORY = "Category";
	/** */public static final String CRITERIA_SUB_CATEGORY = "SubCategory";
	/** */public static final String CRITERIA_MINIMUM_RATING = "Minimum Rating";
	/** */public static final String CRITERIA_LANGUAGE = "Language";
	/** */public static final String CRITERIA_MINIMUM_SO_COMPLETED = "SoCompleted";
	/** */public static final String CRITERIA_AUTO_LIABILITY_AMT = "AutoLiabilityAmt";
	/** */public static final String CRITERIA_AUTO_LIABILITY_VERIFIED = "AutoLiabilityVerified";
	/** */public static final String CRITERIA_WC_LIABILITY_VERIFIED = "WCVerified";
	/** */public static final String CRITERIA_WC_LIABILITY_SELECTED = "WCSelected";
	/** */public static final String CRITERIA_COMMERCIAL_LIABILITY_AMT = "CommercialLiabilityAmt";
	/** */public static final String CRITERIA_COMMERCIAL_LIABILITY_VERIFIED = "CommercialLiabilityVerified";
	/** */public static final String CRITERIA_COMPANY_CRED = "ProviderFirmCred";
	/** */public static final String CRITERIA_COMPANY_CRED_CATEGORY = "ProviderFirmCredCat";
	/** */public static final String CRITERIA_COMPANY_CRED_VERIFIED = "ProviderFirmCredVerified";
	
	//SL-18018
	/** */public static final String CRITERIA_COMPANY_CRED_OVERRIDED = "ProviderFirmCredOverrided";
	/** */public static final String CRITERIA_RESOURCE_CRED_OVERRIDED = "ProviderCredOverrided";

	/** */public static final String CRITERIA_SP_CRED = "ProviderCred";
	/** */public static final String CRITERIA_SP_CRED_CATEGORY = "ProviderCredCat";
	/** */public static final String CRITERIA_SP_CRED_VERIFIED = "ProviderCredVerified";
	/** */public static final String CRITERIA_MEETING_REQUIRED = "Meeting Required";
	/** */public static final String CRITERIA_MARKET = "Market";
	/** */public static final String CRITERIA_STATE = "State";
	/** */public static final String CRITERIA_COMPANY_SIZE = "Company Size";
	/** */public static final String CRITERIA_SALES_VOLUME = "Sales Volume";
	
	/** */public static final String CRITERIA_MINIMUM_RATING_NOTRATED = "Not Rated";
	/** */public static final String CRITERIA_MARKET_ALL = "Market_All";
	/** */public static final String CRITERIA_STATE_ALL = "State_All";
	//R11.0 SL-19387
	/** */public static final String CRITERIA_BACKGROUND_CHECK = "Background Check";
	
	// All TIER ROUTE REASON Codes
	public final static Integer TR_REASON_ID_START_TIER_ROUTING = 1;
	public final static Integer TR_REASON_ID_NO_PROVIDERS = 2;
	public final static Integer TR_REASON_ID_ADVANCE_RELEASE_TIME_ELAPSED = 3;
	public final static Integer TR_REASON_ID_ALL_RESPONDED = 4;
	public final static Integer TR_REASON_ID_RESTART_TIER_ROUTING = 5;
	
	//Constants for Tier Routing History
	public static final String SPN_TEXT = "SPN";
	public static final String PRIORITY_TEXT = "Priority";
	public static final String REASON_TEXT = "Reason";
	public static final String COLON_TEXT = ":";
	public static final String LINE_BREAK_TEXT = "<br/>";
	
	//Constants for SPN auditor member compliance
	public static final String ATTENTION_NEEDED = "Attention Needed";
	public static final String ACTION_REQUIRED = "Action Required";
	public static final String IN_COMPLIANCE = "In Compliance";
	public static final String DAYS_NOTICE_SENT = " Days notice sent";
	
	public static String PROVIDER_COMPLIANCE="providerCompliance";
	public static String FIRM_COMPLIANCE="firmCompliance";
	
	//SL 18780 Constant for value from application_properties for spn_edit_split_insert_count
	public final static String SPN_EDIT_SPLIT_INSERT_COUNT = "spn_edit_split_insert_count";
	
	public final static String SERVICES_SKILLS = "Services & Skills";
	public final static String CATEGORY = "Category";
	
	//R11.0
	public static final String SPN_MONITOR_HDR = "Provider,Background Check Status,Last Certification Date,Recertification Due Date,Recertification Status,System Action/Notice Sent On";
	
	//SL20014 Constant for credTypeDesc in InsuancePolicy
	public static final String CREDTYPEDESC_VEHICLELIABILTY = "Auto Liability";
	public static final String CREDTYPEDESC_WORKERSCOMPENSATION = "Workman's Compensation";
	public static final String CREDTYPEDESC_GENERALLIABILTY = "General Liability";
	public static final Integer CRITERIA_PROVIDERFIRMCRED = 13;
	public static final Integer CRITERIA_PROVIDERFIRMCREDCAT = 14;
	//SL20014
	public static final String BUYER_SPNVIEWDOC_PERMISSION = "buyerSPNViewDocPermission";
	//SL20014 end
}
