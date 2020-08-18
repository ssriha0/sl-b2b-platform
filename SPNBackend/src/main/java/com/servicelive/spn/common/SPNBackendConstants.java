/**
 *
 */
package com.servicelive.spn.common;

/**
 * @author hoza
 *
 */
public final class SPNBackendConstants {
	//Globle
	/** */public static final Integer MAX_PARAMETER = Integer.valueOf(1000);
	
	//ALL Default values
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
	/** */public static final String CRITERIA_WC_LIABILITY_SELECTED = "WCSelected";
	/** */public static final String CRITERIA_WC_LIABILITY_VERIFIED = "WCVerified";
	/** */public static final String CRITERIA_COMMERCIAL_LIABILITY_AMT = "CommercialLiabilityAmt";
	/** */public static final String CRITERIA_COMMERCIAL_LIABILITY_VERIFIED = "CommercialLiabilityVerified";
	/** */public static final String CRITERIA_COMPANY_CRED = "ProviderFirmCred";
	/** */public static final String CRITERIA_COMPANY_CRED_CATEGORY = "ProviderFirmCredCat";
	/** */public static final String CRITERIA_COMPANY_CRED_VERIFIED = "ProviderFirmCredVerified";
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
	//R10.3 SL-19812 Introduce Primary Industry criteria for Campaign
	/** */public static final String CRITERIA_PRIMARY_INDUSTRY = "Primary Industry" ;
	//R11.0 SL-19387
	/** */public static final String CRITERIA_BACKGROUND_CHECK = "Background Check";
	
	
	
	//REMAINING CAPAING Approval CRiteria
	
	
	
	//Regular Document ID 
	/** */public static final Integer DOC_CATEGORY_ID_FOR_SPN = Integer.valueOf(6); 
	/** */public static final Integer DOC_TYPE_ELECTRONIC_AGREEMENT = Integer.valueOf(2);
	
	/**  *****************  WORKFLOW CONSTANTS **/
	/** */public static final String WF_ENTITY_CAMPAIGN = "CAMPAIGN" ;
	/** */public static final String WF_ENTITY_NETWORK = "NETWORK" ;
	/** */public static final String WF_ENTITY_PROVIDER_FIRM = "PROVIDER FIRM" ;
	/** */public static final String WF_ENTITY_SERVICE_PROVIDER = "SERVICE PROVIDER" ;
	
	//SPN & Campaign
	/** */public static final String WF_STATUS_SPN_INCOMPLETE = "SPN INCOMPLETE" ;
	/** */public static final String WF_STATUS_SPN_COMPLETE = "SPN COMPLETE" ;
	/** */public static final String WF_STATUS_SPN_DOC_EDITED = "SPN DOC EDITED" ;
	/** */public static final String WF_STATUS_SPN_EDITED = "SPN EDITED" ;
	/** */public static final String WF_STATUS_SPN_EDITED_WITHOUT_STATUS_CHANGE = "SPN EDITED STATUS UNCHANGED" ;
	/** */public static final String WF_STATUS_CAMPAIGN_PENDING = "CAMPAIGN PENDING" ;
	/** */public static final String WF_STATUS_CAMPAIGN_INACTIVE = "CAMPAIGN INACTIVE" ;
	/** */public static final String WF_STATUS_CAMPAIGN_APPROVED = "CAMPAIGN APPROVED" ;
	/** */public static final String WF_STATUS_CAMPAIGN_ACTIVE = "CAMPAIGN ACTIVE" ;

	//Provider Firm(PF)
	/** */public static final String WF_STATUS_PF_INVITED_TO_SPN = "PF INVITED TO SPN";
	/** */public static final String WF_STATUS_PF_SPN_NOT_INTERESTED = "PF SPN NOT INTERESTED";
	/** */public static final String WF_STATUS_PF_SPN_INTERESTED = "PF SPN INTERESTED";
	/** */public static final String WF_STATUS_PF_SPN_APPLICANT = "PF SPN APPLICANT";
	/** */public static final String WF_STATUS_PF_SPN_REAPPLICANT = "PF SPN REAPPLICANT";
	/** */public static final String WF_STATUS_PF_SPN_MEMBERSHIP_UNDER_REVIEW = "PF SPN MEMBERSHIP UNDER REVIEW";
	/** */public static final String WF_STATUS_PF_AWAITING_MEET_AND_GREET = "PF AWAITING MEET AND GREET";
	/** */public static final String WF_STATUS_PF_SPN_DECLINED = "PF SPN DECLINED";
	/** */public static final String WF_STATUS_PF_SPN_MEMBER = "PF SPN MEMBER";
	/** */public static final String WF_STATUS_PF_APPLICANT_INCOMPLETE = "PF APPLICANT INCOMPLETE";
	/** */public static final String WF_STATUS_PF_SPN_REMOVED_FIRM = "PF SPN REMOVED FIRM";
	/** */public static final String WF_STATUS_PF_FIRM_OUT_OF_COMPLIANCE = "PF FIRM OUT OF COMPLIANCE";
	
	// Service Provider(SP)
	/** */public static final String WF_STATUS_SP_SPN_APPROVED = "SP SPN APPROVED";
	/** */public static final String WF_STATUS_SP_SPN_REMOVED = "SP SPN REMOVED";
	/** */public static final String WF_STATUS_SP_SPN_OUT_OF_COMPLIANCE = "SP SPN OUT OF COMPLIANCE";
	/** */public static final String WF_STATUS_SP_SPN_PERF_LEVEL_CHANGE = "SP PERF LEVEL CHANGE";

	/**  *******************  EMAIL CONSTANTS ************************ **/
	/** */public static final String EMAIL_TO_ADDRESS_SEPERATOR = ";" ;
	/** */public static final String EMAIL_ACTION_CAMPAIGN_CREATED = "campaignCreated";
	/** */public static final String EMAIL_ACTION_SPN_JOIN = "spnJoin";
	/** */public static final String EMAIL_ACTION_SPN_EDITED = "spnEdited";
	/** */public static final String EMAIL_ACTION_SPN_AUDITOR_MODIFIED = "spnAuditorModified";

	/**  *******************  ROLE CONSTANTS ************************ **/
	/** */public static final String BUYER_ROLE = "Buyer";
	/** */public static final Integer BUYER_ROLE_ID = Integer.valueOf(3);

	//SPN WORKFLOW STATE GROUP TYPE 
	/** */public static final String PF_WF_STATE_GROUP_TYPE = "PROVIDER FIRM";
	
	
	/** */public static final String DOC_STATE_APPROVED = "DOC APPROVED";
	/** */public static final String DOC_STATE_INCOMPLETE = "DOC INCOMPLETE";
	/** */public static final String DOC_STATE_PENDING_APPROVAL = "DOC PENDING APPROVAL";
	/** */public static final String DOC_STATE_PENDING_NEED_MORE_INFO = "DOC NEED MORE INFO";
	
	//SPN STATE FLOW RESPONSES
	/** */public static final String STATE_FLOW_RESP_EMAIL_NOT_SENT = "emailNotSent";
	/** */public static final String STATE_FLOW_RESP_SUCCESS = "success";
	//Provider 
	/**  */public static final Integer PROVIDER_FIRM_INSURANCE_CRED_ID = Integer.valueOf(6);
	
	//SPN MAILS
	//For mapping spnet_email_template_id and template_id
	public static final int SPN_TEMP_ID1=1;
	public static final int SPN_TEMP_ID3=3;
	public static final int SPN_TEMP_ID4=4;
	public static final int SPN_TEMP_ID5=5;
	public static final int SPN_TEMP_ID6=6;
	public static final int SPN_TEMP_ID7=7;
	public static final int SPN_TEMP_ID8=8;
	public static final int SPN_TEMP_ID9=9;
	public static final int SPN_TEMP_ID10=10;
	public static final int SPN_TEMP_ID11=11;
	public static final int SPN_TEMP_ID12=12;
	
	public static final int TEMP_ID1=257;
	public static final int TEMP_ID3=258;
	public static final int TEMP_ID4=259;
	public static final int TEMP_ID6=260;
	public static final int TEMP_ID10=261;
	public static final int TEMP_ID11=262;
	public static final int TEMP_ID12=263;
	
	public static final String PRIORITY1="1";
	public static final String INCOMPL_IND="1";
	public static final int EMAIL_TYPE=1;

	// SPN STATUS UPDATE ACTION MAPPER
	/*
	public static final Integer STATUS_UPDATE_ACTION_ID_JOB_PF_SPN_INVITATION = Integer.valueOf(1);
	public static final Integer STATUS_UPDATE_ACTION_ID_USER_PF_SPN_INTERESTED = Integer.valueOf(2);
	public static final Integer STATUS_UPDATE_ACTION_ID_USER_PF_SPN_NOT_INTERESTED = Integer.valueOf(3);
	public static final Integer STATUS_UPDATE_ACTION_ID_USER_PF_SPN_APPLICANT = Integer.valueOf(4);
	public static final Integer STATUS_UPDATE_ACTION_ID_USER_PF_SPN_DECLINED = Integer.valueOf(5);
	public static final Integer STATUS_UPDATE_ACTION_ID_USER_PF_SPN_MEMBER = Integer.valueOf(6);
	public static final Integer STATUS_UPDATE_ACTION_ID_JOB_PF_SPN_MEMBER  = Integer.valueOf(7);
	public static final Integer STATUS_UPDATE_ACTION_ID_USER_PF_SPN_REAPPLICANT = Integer.valueOf(8);
	public static final Integer STATUS_UPDATE_ACTION_ID_JOB_MM_PF_SPN_FIRM_OOC = Integer.valueOf(9);
	public static final Integer STATUS_UPDATE_ACTION_ID_USER_PF_SPN_OOC = Integer.valueOf(10);
	public static final Integer STATUS_UPDATE_ACTION_ID_USER_PF_APP_INCOMPLETE = Integer.valueOf(11);
	public static final Integer STATUS_UPDATE_ACTION_ID_USER_PF_SPN_REMOVED  = Integer.valueOf(12);
	
	public static final Integer STATUS_UPDATE_ACTION_ID_JOB_MEMBER_MAINTANCE_SP_OUT_OF_COMPLIANCE = Integer.valueOf(14);
	public static final Integer STATUS_UPDATE_ACTION_ID_JOB_MEMBER_MAINTANCE_SP_APPROVED = Integer.valueOf(13);
	*/
	/*public static final Integer STATUS_UPDATE_ACTION_ID_USER_SP_APPROVED = Integer.valueOf(13);
	public static final Integer STATUS_UPDATE_ACTION_ID_USER_SP_OUT_OF_COMPLIANCE = Integer.valueOf(14);
	*/
	/*
	
	public static final Integer STATUS_UPDATE_ACTION_ID_USER_SP_SPN_OOC = Integer.valueOf(15);
	public static final Integer STATUS_UPDATE_ACTION_ID_USER_SP_SPN_REMOVED = Integer.valueOf(16);
	public static final Integer STATUS_UPDATE_ACTION_ID_USER_SP_SPN_APPROVED = Integer.valueOf(17);
	*/
/*	public static final Integer STATUS_UPDATE_ACTION_ID_USER_SP_SPN_OOC = Integer.valueOf(18);
	public static final Integer STATUS_UPDATE_ACTION_ID_USER_SP_SPN_REMOVED = Integer.valueOf(19);
	*/
	
	// SPN Status Update Action mapper.Action Type
	public static final String ACTION_TYPE_PROVIDER_FIRM = "PROVIDER_FIRM";
	public static final String ACTION_TYPE_SERVICE_PROVIDER = "SERVICE_PROVIDER";
	public static final String ACTION_TYPE_PROVIDER_FIRM_SPN_AUDITOR = "PROVIDER_FIRM_SPN_AUDITOR";
	

	// SPN status update action mapper.modified
	public static final String MODIFIED_BY_USER = "USER";
	public static final String MODIFIED_BY_INVITATION_JOB = "INVITATIONJOB";
	public static final String MODIFIED_BY_MEMBERMAINTENANCE = "MemberMaintenance";
	
	public static final int ZERO=0;
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public static final String INSERT = "insert";

	
	public static final int FIRM_CREDENTIAL_OUT_OF_COMPLAINT_STATE=25;
	public static final int PROVIDER_CREDENTIAL_OUT_OF_COMPLAINT_STATE=24;
	public static final String SP_SPN_APPROVED="SP SPN APPROVED";
	public static final String SP_SPN_OUT_OF_COMPLIANCE="SP SPN OUT OF COMPLIANCE";
	public static final String SP_SPN_COMPLIANT_DUE_TO_BUYER_OVERRIDE="SP SPN COMPLIANT DUE TO BUYER OVERRIDE";
	public static final int GRACE_PERIOD_EXCEPTION_ID=1;
	public static final int STATE_EXCEPTION_ID=2;
	
	
	public static String PROVIDER_COMPLIANCE="providerCompliance";
	public static String FIRM_COMPLIANCE="firmCompliance";

	//SL-18226
	public static final String NA = "N/A";
	public static final String EDIT = "edit";
	public static final String VIEW = "view";
	public static final String MINUTES = "Minutes";
	public static final String HOURS = "Hours";
	public static final String DAYS = "Days";
	public static final String PRIORITY_CREATED = "PRIORITY CREATED";
	public static final String PRIORITY_EDITED = "PRIORITY EDITED";
	public static final String PRIORITY_ACTIVE = "PRIORITY ACTIVE";
	public static final String PRIORITY_INACTIVE = "PRIORITY INACTIVE";
	public static final String ACTIVE = "ACTIVE";
	public static final String INACTIVE = "INACTIVE";
	
	public static final String PROVIDER = "PROVIDER";
	public static final String FIRM = "FIRM";
	public static final String ALL = "ALL";
	public static final String SINGLE = "SINGLE";
	public static final Integer NO_OF_TIERS = 4;
	
	public static final int MINIMUM_RATING_CRITERIA_ID=5;
	public static final int SO_COMPLETED_CRITERIA_ID=7;
	public static final int LANGUAGE_COMPLIANCE_CRITERIA_ID=6;
	public static final int MAIN_SERVICES_COMPLIANCE_CRITERIA_ID=1;
	public static final int SKILLS_COMPLIANCE_CRITERIA_ID=2;
	public static final int CATEGORY_CRITERIA_ID=3;
	public static final int SUB_CATEGORY_CRITERIA_ID=4;
	public static final int VEHICLE_LIABILITY_AMT_CRITERIA_ID=8;
	public static final int COMMERCIAL_LIABILITY_AMT_CRITERIA_ID=11;
	
	
	
	public static String SP_SPN_CRED_INCOMPLIANCE ="SP SPN CRED INCOMPLIANCE";
	public static String SP_SPN_CRED_OUTOFCOMPLIANCE  ="SP SPN CRED OUTOFCOMPLIANCE";
	public static String SP_SPN_CRED_OVERRIDE  ="SP SPN CRED OVERRIDE";

	public static String PF_SPN_CRED_INCOMPLIANCE ="PF SPN CRED INCOMPLIANCE";
	public static String PF_SPN_CRED_OUTOFCOMPLIANCE ="PF SPN CRED OUTOFCOMPLIANCE";
	public static String PF_SPN_CRED_OVERRIDE ="PF SPN CRED OVERRIDE";


	// sl-18018 exceptions:
	
	public static final String CREDENTIALS="CREDENTIALS";
	public static final String CRED_GRACE_UPDATE="SPN CREDENTIALS - GRACE PERIOD UPDATED";
	public static final String CRED_TYPE_CATEGORY_SEPARATOR=" > ";
	public static final String CRED_EXCEPTION_SEPARATOR=";";
	public static final String CRED_GRACE_ALLOWED="Allowed Grace Period: ";
	
	public static final String CRED_STATE_UPDATE="SPN CREDENTIALS - STATE EXEMPTION UPDATED";
	public static final String CRED_STATE_ALLOWED="State(s) Exempted: ";
	
	public static final String CRED_STATE_ADD="SPN CREDENTIALS - STATE EXEMPTION CREATED";
	public static final String CRED_GRACE_ADD="SPN CREDENTIALS - GRACE PERIOD CREATED";
	public static final String CRED_GRACE_DAYS = " days";
	public static final String CRED_TYPE_VENDOR = "vendor";
	public static final String CRED_TYPE_RESOURCE = "resource";

	public static final Integer MEET_AND_GREET_CRITERIA_ID = 19;
	
	// SL-12300
	public static final String DATE_FORMAT = "MM/dd/yyyy";
	public static final String NO_EXPIRATION_DATE = "No expiration date";
	
	//R11.0 SL-19387
	public static final int BACKGROUND_CHECK_CRITERIA_ID=29;
	public static final String BACKGROUND_STATE_ADVERSE_FINDINGS="Not Cleared";
	public static final String BACKGROUND_STATE_CLEAR="Clear";
	public static final String BACKGROUND_STATE_IN_PROCESS="In Process";
	public static final String BACKGROUND_CHECK_OVERALL="E";
	public static final String BACKGROUND_CHECK_EXPIRED="E";
	public static final String BACKGROUND_CHECK_RECERTIFICATION_APPLIED="R";
	public static final String OVERALL_INPROCESS="P";
	
	//R11.0 CR SL-20289
	public static final String SL_MARKET_READY_WF_STATE_ID="6";


	public static final String SPN_AUDITOR_HDR = "Provider Firm,Provider,Background Check Status,Last Certification Date,Recertification Due Date,Recertification Status,System Action/Notice Sent On";

	public static final  int INSURANCECREDTYPE=6;
	public static final  int CRED_ID=0;
	public static final  int COMMERCIAL_LAIBILITY_TYPE=41;
	public static final  int VEHICLE_LAIBILITY_TYPE=42;

	
	public static final int FIRM_CREDENTIAL_APPROVED=14;
	public static final String SPLIT_STRING="\\|";
	public static final String FIRM_CRED_APPROVED="14";
	public static final int  CONST_ZERO=0;
	
	public static final int WORKMAN_COMPENSATION_CRITERIA_ID=10;



}
