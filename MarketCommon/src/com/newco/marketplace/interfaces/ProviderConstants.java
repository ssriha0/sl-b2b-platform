/**
 *
 */
package com.newco.marketplace.interfaces;

/**
 * @author sahmad7
 *
 */
public interface ProviderConstants {


	public static final String VENDOR_HDR_DAO = "vendorHdrDao";
	public static final String USER_PROFILE_DAO = "userProfileDao";
	public static final String VENDOR_PRINCIPAL_DAO = "vendorPrincipalDao";
	public static final String BUSINESS_INFO_BUSINESS_BEAN = "businessInfoBusinessBean";
	public static final String OWNERSHIP_BUSINESS_BEAN = "ownershipBusinessBean";
	public static final String FINANCE_PROFILE_DAO = "FinanceProfileDAO";
	public static final String FINANCE_BUSINESS_BEAN = "financeBusinessBean";
	public static final String BUSINESS_WARRANTY_INFO_BEAN = "warrantyInfoBusinessBean";
	public static final String LICENSE_CREDENTIAL_ID_BEAN = "licenseCredentialIdBusiness";
	public static final String W9_BUSINESS_BEAN = "w9BusinessBean";
	public static final String QUALIFICATION_TnC_BUSINESS_BEAN = "qualificationTnCBusinessBean";
	public static final String ALERT_MANAGER = "alertManagerBean";
	public static final String LICENSE_CREDENTIAL_CATEGORY_BEAN = "licenseCredentialCategoryBusiness";
	public static final String VENDOR_CREDENTIALS_BUSINESS_BEAN = "vendorCredentialsBusinessBean";
	public static final String SKILL_ASSIGN_BUSINESS_BEAN = "SkillAssignBusinessBean";
	public static final String AUDIT_BUSINESS_BEAN = "auditBusinessBean";

	public static final String DAO_USER_PROFILE = "userProfileDao";
	public static final String DAO_VENDOR_CONTACT = "vendorContactDao";
	public static final String DAO_VENDOR_HEADER = "vendorHeaderDao";

	public static final String FORWARD_RETRIEVE_PASSWORD_FAILURE = "failure";
	public static final String FORWARD_RETRIEVE_PASSWORD_SUCCESS = "success";
	public static final String FORWARD_DISPLAY_SECRET_QUESTION = "displaySecretQuestion";
	public static final String FORWARD_DISPLAY_SECRET_QUESTION_FAILURE = "failure";

	public static final String REGISTRATION_BUSINESS_BEAN = "registrationBusinessBean";
	public static final String MAPPER_ENC_BUSINESS_BEAN = "mapperEncBusinessBean";
	public static final String DATE_KG_BUSINESS_BEAN = "dateKgBusinessBean";
	public static final String INSURANCE_POLICY_BUSINESS_BEAN="insurancePolicyBusinessBean";
	public static final String COMPANY_CREDENTIALS_BEAN="companyCredentialsBusiness";
	public static final String ACTIVITY_BUSINESS_BEAN="activityBusinessBean";
	public static final String INSURANCE_BUSINESS_BEAN="insuranceBusinessBean";
	public static final String TEAM_CREDENTIALS_BUSINESS_BEAN="teamCredentialsBusinessBean";
	public static final String DOCUMENT_BUSINESS_BEAN = "documentBusinessBean";
	public static final String ADMIN_AUDIT_BUSINESS_BEAN = "adminAuditBusinessBean";
	public static final String LOOKUP_BUSINESS_BEAN = "lookupBusinessBean";

	public static final String COMPANY_PROFILE_INCOMPLETE_STATUS = "Incomplete";
	public static final String COMPANY_PROFILE_PENDING_COMPLIANCE_APPROVAL_STATUS = "Pending Compliance Approval";
	public static final String COMPANY_PROFILE_PENDING_FIELD_MGR_APPROVAL_STATUS = "Pending Field Manager Approval";
	public static final String COMPANY_PROFILE_OUT_OF_COMPLIANCE_STATUS = "Out of Compliance";
	public static final String COMPANY_PROFILE_REJECTED_STATUS = "Rejected";
	public static final String COMPANY_PROFILE_APPROVED_STATUS = "Approved";
	public static final String COMPANY_PROFILE = "Company Profile";
	public static final String COMPANY_SSN_ID_MASK = "XXX-XX-";
	public static final String COMPANY_EIN_ID_MASK = "XX-XXX";
	public static final int COMPANY_EID_IND = 1;
	public static final int COMPANY_SSN_IND = 2;
	
	
	public static final String FORWARD_BGCHECK_SUBMIT_SUCCESS = "submitsuccess";
	public static final String FORWARD_BGCHECK_SUBMIT_FAILTURE = "submitfailure";
	public static final String FORWARD_DISPLAY_SUCCESS = "display";

	public static final String SESSION_CONSTANT_RESOURCE_ID =  "resourceId";

    //Workflow state constants
    public static final Integer TEAM_MEMBER_APPLICATION_STATE_INCOMPLETE = new Integer(4);
    public static final Integer TEAM_MEMBER_APPLICATION_STATE_OUT_OF_COMPLIANCE = new Integer(22);

    public static final Integer TEAM_MEMBER_BACKGROUND_CHECK_STATE_NOT_STARTED = new Integer(7);
    public static final Integer TEAM_MEMBER_BACKGROUND_CHECK_STATE_INPROCESS = new Integer(8);
    public static final Integer TEAM_MEMBER_BACKGROUND_CHECK_STATE_CLEAR = new Integer(9);
    public static final Integer TEAM_MEMBER_BACKGROUND_CHECK_STATE_ADVERSE_FINDINGS = new Integer(10);
    public static final Integer TEAM_MEMBER_BACKGROUND_CHECK_STATE_PENDING_SUBMISSION = new Integer(28);
    public static final Integer  TEAM_MEMBER_CREDENTIAL_REVIEWED = new Integer(200);
    public static final Integer  TEAM_MEMBER_CREDENTIAL_APPROVED = new Integer(12);
    public static final Integer  TEAM_MEMBER_CREDENTIAL_PENDING_APPROVAL = new Integer(11);

    public static final Integer COMPANY_CREDENTIAL_PENDING_APPROVAL = new Integer(13);
    public static final Integer COMPANY_CREDENTIAL_APPROVAL = new Integer(14);
    public static final Integer COMPANY_CREDENTIAL_OUT_OF_COMPLIANCE = new Integer(25);
    public static final Integer COMPANY_CREDENTIAL_REVIEWD = new Integer(210);
    
    public static final String  SERVICE_PROVIDER_SPNET_APPROVED = "SP SPN APPROVED";


    public static final String SERVICE_ORDER_BUSINESS_BEAN =  "serviceOrderBusinessBean";
    public static final String STATE_TRANSITION_DAO =  "wfStateTransitionDao";
    public static final String SECURITY_BUSINESS_BEAN =  "securityBusinessBean";

    public static final Integer INSURANCE_GENERAL_LIABILITY = 41;
    public static final Integer INSURANCE_AUTOMOTIVE = 42;
    public static final Integer INSURANCE_WORKERS_COMPENSATION = 43;
    
    //SL-10809 Additional insurance
    public static final Integer EMPLOYEE_DISHONESTY_INS = 142;
    public static final Integer CARGO_LEGAL_LIABILITY = 143;
    public static final Integer WAREHOUSEMEN_LEGAL_LIABILITY = 144;
    public static final Integer UMBRELLA_COVERAGE_INSURANCE = 145;
    public static final Integer PROFESSIONAL_LIABILITY_INS = 146;
    public static final Integer PROFESSIONAL_INDEMNITY_INS=147;
    public static final Integer ADULT_CARE_INS = 148;
    public static final Integer NANNY_INS = 149;
    public static final Integer OTHER_INS =150;
    
    
    
    public static final String EMPLOYEE_DISHONESTY_INS_DESCR = "Employee Dishonesty/Fidelity Insurance";
    public static final String CARGO_LEGAL_LIABILITY_DESCR = "Cargo Legal Liability";
    public static final String WAREHOUSEMEN_LEGAL_LIABILITY_DESCR = "Warehousemen's Legal Liability";
    public static final String UMBRELLA_COVERAGE_INSURANCE_DESCR = "Umbrella/Excess Coverage";
    public static final String PROFESSIONAL_LIABILITY_INS_DESCR = "Professional Liability";
    public static final String ADULT_CARE_INS_DESCR = "Adult Care";
    public static final String NANNY_INS_DESCR = "Nanny Insurance";
    public static final String OTHER_INS_DESCR ="Other";
    public static final String PROFESSIONAL_INDEMNITY_INS_DESCR="Professional Indemnity Insurance";
    
    public static final String ALL_PROVIDERS = "allproviders";
    public static final String SO_MASTER_PROVIDERS_LIST = "somasterproviderslist";
    public static final String PROVIDERS_FILTER_CRITERIA = "providerSearchDto";
    public static final String FILTERED_PROVIDERS = "filteredproviders";
    public static final String SELECTED_PROVIDERS = "selectedProviders";
    public static final String SHOW_SELECTED_PROVIDERS_FLAG = "showSelectedProviders";
    public static final String SELECT_NUBER_OF_TOP_PROVIDERS ="selectTopProviders";
    public static final String SELECT_TOP_PROVIDERS_LIST ="selectedTopProvidersList";
    public static final String CHECKED_PROVIDERS = "checkedProviders";
    public static final String CREDENTAILS_LIST = "credentails";
    public static final String LANGUAGES_LIST = "languages";
    public static final String RATING_LIST = "ratingList";
    public static final String DISTANCE_LIST = "distanceList";
    public static final String TOP_PROVIDER_SELECT_LIST = "topProviderSelectList";
    public static final String SELECTED_MAIN_CATEGORY_ID = "mainCategoryId";
    public static final String SELECTED_CREDENTIAL_ID = "credentialSelected";
    public static final String CREDENTIAL_CATEGORY_LIST = "credentailCategoryList";
    public static final String SERVICE_LOCATION = "serviceLocation";
    public static final String SPN_LIST = "spnetworkList";
    public static final String PERFORMANCE_LEVEL_DROPDOWN_LIST = "performanceLevelDropdownList";
    //SL-18226
    public static final String ROUTING_PRIORITY_APPLIED_FOR_FILTERING = "routingPriorityApplied";
    public static final String PERFORMANCE_SCORE = "performanceScore";
    public static final String PERF_CRITERIA_LEVEL = "perfCriteriaLevel";
    public static final String OLD_SPN_ID ="oldSpnId";
    
    public static final String PROVIDER_FIRM_REGISTRATION = "Provider Firm Registration";
    public static final String SERVICE_PROVIDER_WORKFLOW_ENTITY = "Team Member";
    public static final String PROVIDER_FIRM_AGREEMENT = "Provider Agreement";
    public static final String PROVIDER_SERVICE_BUCKS = "ServiceLive Bucks";
    public static final String SERVICE_PROVIDER_PROFILE = "Service Provider Profile";
    public static final String SERVICE_PROVIDER_AGREEMENT = "Provider Agreement";

    public static final String ERROR_DUPLICATE_USER_ID = "1";
    public static final String ERROR_DUPLICATE_USER_EMAIL = "2";

    public final String AUTO_LIABILITY = "Auto Liability";
    public final String WORKER_COMPENSATION = "Workman's Compensation";
    public final String GENERAL_LIABILITY = "General Liability";
    
    public final String BUSINESS_START_DATE = "Business Start Date";
    public static final Double INSURANCE_AMOUNT_ZERO = new Double(0.00);
    public static final Double INSURANCE_AMOUNT_ZERO_1 = new Double(0.0);
    
    public static final String GENERAL_LIABILITY_RATING_LIST = "generalLiabilityRatingList";
    public static final String VEHICLE_LIABILITY_RATING_LIST = "vehicleLiabilityRatingList";
    public static final String WORKERS_COMPENSATION_RATING_LIST = "workersCompensationRatingList";
    public static final String ADDITIONAL_INSURANCE_LIST = "additionalInsuranceList";
    public static final String SELECTED_ADDITIONAL_INSURANCE_LIST = "selectedAdditionalInsuranceList";
    
    public static final String OTHER_PRIMARY_SERVICES =  "1400";
    public static final String OTHER_PRIMARY_SERVICES_VALUE =  "Other Services";
    public static final String HEAR_FROM_THIRD_PARTY_VALUE =  "ServiceLive 3rd Party";
	public static final String BUSINESS_NO = "BUSINESS";
	public static final String FAX_NO = "FAX";
	public static final String BUSINESS_ZIP = "BusinessZip";
	public static final String MAILING_ZIP = "MailingZip";
	public static final String MAINBUSINESS_EXT = "MainBusinessExt";
	public static final String VENDOR_TYPE_HI ="Home Improvement Firm";
	public static final String RESOURCE_TYPE_HI ="Home Improvement Provider";
	public static final String EMAIL_ID = "email";
	public static final String ALTER_EMAIL_ID = "altEmail";
	public static final String SERVICELIVE_APPROVED="ServiceLive Approved";
	public static final Integer PENDING_APPROVAL = 2;
	public static final Integer REGISTRATION_COMPLETE = 33;
	public static final String VALID_FIRM = "valid";
	public static final String IN_VALID_FIRM = "invalid";
	public static final String POST = "POST";
	public static final String PUT = "PUT";
	public static final String CREATE_FIRM = "Create Firm Account v1.0";
	public static final String UPDATE_FIRM = "Update Firm Account v1.0";
	public static final String APPROVE_FIRM = "Approve Firm v1.0";
	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "ERROR";
	
	public static final String CREATE_PROVIDER = "Create Provider Registration v1.0";
	public static final String UPDATE_PROVIDER = "Update Provider Registration v1.0";
	public static final String APPROVE_PROVIDER = "Approve Provider v1.0";
	public static final String ADD_PROVIDER_SKILL="Add Provider Skill v1.0";
	public static final String REMOVE_PROVIDER_SKILL="Remove Provider Skill v1.0";
	public static final String BACKGROUNDCHECKSTATUS_CLEAR = "Clear";
	public static final Integer BACKGROUNDCHECKSTATUS_CLEAR_ID = 9;
	public static final String APPROVED_STATUS = "Approved (market ready)";
	public static final String  YES= "Yes"; 
	public static final String  BG_REQUEST_TYPE= "N"; 
	
	public static final String ALL = "All";
	public static final String ERROR_STRING = "Error";
	public static final String NONE = "None";
	
	public static final String SUNDAY = "Sunday";
	public static final String MONDAY = "Monday";
	public static final String TUESDAY = "Tuesday";
	public static final String WEDNESDAY = "Wednesday";
	public static final String THURSDAY = "Thursday";
	public static final String FRIDAY = "Friday";
	public static final String SATURDAY = "Saturday";
	
	public static final String TIME_STRING_FORMAT = "yyyyMMdd:hh:mm a";
	
	public static final String DEFAULT_DATE_RESOURCE_SCHEDULE="20070101:";
	
	public static final String OWNER_ROLE="Owner/Principal"; 
	public static final String DISPATCHER_ROLE="Dispatcher/Scheduler"; 
	public static final String MANAGER="Manager";
	public static final String OTHER_ROLE="Other";
	public static final String SP_ROLE="Service Provider";
	public static final String ADMIN_ROLE="Administrator";
	
	public static final String CREATE="Create";
	
	
	public static final String INSIDE_SALES_SWITCH="inside_sales_api_invoke_switch";
	public static final String ADD_LEAD_OPERATION = "addLead";
	public static final String IS_LOGIN_FAILURE = "Inside Sales Login Failure";
	public static final String INSIDE_SALES_LOGIN_OPERATION = "login";
	public static final String INVALID_LOGIN_RESPONSE = "false";
	public static final String IS_LOGIN_FAILED = "InsideSales Login Failed";
	
	public static final String INSIDE_SALES_EMP_OPERATION = "getEmployees";
	public static final String INSIDE_SALES_DATE_OPERATION = "date_created";
	public static final String INSIDE_SALES_CAMP_OPERATION = "getCampaigns";
	public static final String INSIDE_SALES_CALLDATE_OPERATION = "call_date_time";
	public static final String INSIDE_SALES_GTRTHN_OPERATION = ">";
	public static final String IS_DATALOAD_START_DATE = "is_dataload_startdate";
	public static final String IS_DATALOAD_LIMIT = "is_dataload_limit";
	public static final String IS_FULLLOAD = "is_full_dataload";
	public static final String IS_TRUE = "true";
	public static final String IS_FALSE = "false";
	public static final String INSIDE_SALES_ID_OPERATION = "id";
	public static final String INSIDE_SALES_CDR_OPERATION = "getLeadCDRs";
	public static final String INSIDE_SALES_IMP_OPERATION = "getImpressions";
	public static final String INSIDE_SALES_CAMPID_OPERATION = "campaign_id";
	public static final String INSIDE_SALES_BETWEEN_OPERATION = "BETWEEN";

	public static final String CURRENT_DISTANCE = "current_distance";
	public static final String SELECTED_DISTANCE = "selected_distance";
	public static final String SEARCH_CRITERIA = "search_criteria";

}