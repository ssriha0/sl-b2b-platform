/**
 * 
 */
package com.servicelive.spn.constants;

/**
 * @author hoza
 *
 */
public final class SPNActionConstants {

	//Global COnstants 
	/** */public static final String NOT_LOGGED_IN = "NOT_LOGGED_IN";
	/** */public static final String NOT_LOGGED_IN_AS_BUYER = "NOT_LOGGED_IN_AS_BUYER";
	/** */public static final String USER_OBJECT_IN_SESSION = "USER_OBJECT_IN_SESSION";
	/** */public static final String USER_ID_IN_SESSION = "USERID_IN_SESSION";
	/** */public static final String LOGGED_IN_USER_NAME_IN_SESSION = "USERNAME_IN_SESSION";
	/** */public static final String ROLE_ID_IN_SESSION = "ROLE_IN SESSION";
	/** */public static final  Integer ROLE_ID_ENTERPRISE_BUYER = Integer.valueOf(3);
	/** */public static final Integer ROLE_ID_SIMPLE_BUYER_ID = Integer.valueOf(5);
	/** */public static final Integer ROLE_ID_PROVIDER= Integer.valueOf(1);
	/** */public static final Integer ROLE_ID_ADMIN = Integer.valueOf(2);
	//Param names 
	/** */public  final static String SPN_ID_PARAM = "spnid";
	/** */public  final static String BUYER_ID_PARAM = "buyerid";
	/** */public  final static String NEW_APPLICANTS_COUNT_PARAM = "newApplicantsCount";
	/** */public  final static String RE_APPLICANTS_COUNT_PARAM = "reApplicantsCount";
	
	//Action results
	/** */public  final static String RESULT_SPN_ID_NOTFOUND = "nospnfound";
	/** */public static final String RESULT_SERVICES_WITH_SKILLS = "servicesWithSkills";
	/** */public static final String RESULT_RESOURCE_CRED_TYPES_WITH_CATEGORIES = "resCredTypesWithCategories";
	/** */public static final String RESULT_VENDOR_CRED_TYPES_WITH_CATEGORIES = "vendorCredTypesWithCategories";
	/** */public static final String RESULT_VIEW_NETWORK_DETAILS_TAB = "viewnewtworkdetailstab";
	
	/** */public static final String RESULT_UPLOAD_DOCUMENT = "uploadDocument";

	//GLOBAL EXCEPTIOn RESULT
	/** */public static final String RESULT_GLOBAL_RECOVERABLE_EXCEPTION = "RecoverableException";
	/** */public static final String RESULT_GLOBAL_FATAL_EXCEPTION = "RecoverableException";
	
	//SessionVariable Names
	/** */public static final String SPN_NETWORKMODEL_IN_REQUEST = "SPN_NETWORKMODEL_IN_REQUEST";
	/** */public static final String SPN_CAMPAIGNMODEL_IN_REQUEST = "SPN_CAMPAIGNMODEL_IN_REQUEST";
	/** */public static final Integer DOC_MAXIMUM_UPLOAD_SIZE_SPN = Integer.valueOf(100 * 1024 * 1024);
	
	/** */public static final String SPN_AUTHORITY_ROUTING_TIERS = "Routing Tiers";
	
	/** */public static final  Integer MM_SEARCH_TYPE_PROVIDER_FIRM_ID = Integer.valueOf(4);
	/** */public static final Integer MM_SEARCH_TYPE_PROVIDER_FIRM_NAME = Integer.valueOf(3);
	/** */public static final Integer MM_SEARCH_TYPE_SERVICE_PROVIDER_ID = Integer.valueOf(2);
	/** */public static final Integer MM_SEARCH_TYPE_SERVICE_PROVIDER_NAME = Integer.valueOf(1);
	
	//R11.0 Param names 
	
		/** */public static final String EXPORT_SELECTED_FORMAT = "selectedFormat";
		/** */public static final String SEARCH_VO = "searchVO";
		/** */public static final String EXPORT_DATE_FORMAT = "MMddyyyyhhmmss";
		/** */public static final String EXPORT_FILE_NAME = "BackgroundCheckStatus_";
		/** */public static final String XLS_FILE_FORMAT = ".xls";
		/** */public static final String CSV_FILE_FORMAT = ".csv";
		/** */public static final String VENDOR_ID = "vendorId";
		/** */public static final String RESOURCE_ID = "resourceId";
		/** */public static final String BACKGROUND_STATE = "backgroundState";
		/** */public static final String NOTCLEARED = "NotCleared";
		/** */public static final String NOT_CLEARED = "Not Cleared";
		/** */public static final String BACKGROUND_CHECK_STATUS_DATE_FORMAT = "MM/dd/yyyy";
	
	
	public  final static String BG_SPN_ID_PARAM = "spnId";
	public  final static String BG_STATE_CD_PARAM ="stateCd";
	public  final static String BG_STATUS_PARAM ="status";
	public  final static String BG_PROVIDER_FIRM_NAME_PARAM ="providerFirmName";
	public  final static String BG_PROVIDER_FIRM_NO_PARAM ="providerFirmNumber";
	public  final static String BG_MARKET_ID_PARAM ="marketId";
	public  final static String BG_ZIP_CODE_PARAM ="zipCode";
	public  final static String BG_DISTRICT_ID_PARAM ="districtId";
	
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
	
	
	
	
	
}
