package com.servicelive.orderfulfillment.serviceinterface;

public class OrderfulfillmentConstants {
	public static final String PVKEY_ACCEPTED_PROVIDER_STATE = "PVKey_AcceptedProviderState";
	public static final String PVKEY_ASSIGNEE = "PVKey_Assignee";
    public static final String PVKEY_RQSTD_ACTIVE_CANCELLATION_AMT = "PVKey_RequestedActiveCancellationAmount";
    public static final String PVKEY_PREVIOUS_CANCELLATION_AMT = "PVKey_PreviousCancellationAmount";
    public static final String PVKEY_MAX_SPENDLIMIT_PER_SO= "PVKey_MaxSpendLimitPerSo";
    public static final String PVKEY_RQSTD_CANCELLATION_AMT = "PVKey_RequestedActiveCancellationAmount";
    public static final String PVKEY_RQSTD_BUYER_AMT = "PVKey_BuyerAmount";
    public static final String PVKEY_RQSTD_PROVIDER_AMT = "PVKey_ProviderAmount";
    public static final String PVKEY_PROBLEM_DESC = "PVKey_problemDesc";
    public static final String PVKEY_PROBLEM_COMMENT = "PVKey_Comment";
    public static final String PVKEY_PROBLEM_SUBSTATUS_ID = "PVKey_WfSubStatusId";
    public static final String PVKEY_RESCHEDULE_REQUEST_COMMENT = "PVKey_Comment";
    public static final String PVKEY_RESCHEDULE_REQUEST_REASON_CODE = "reschedule_ReasonCode";
    public static final String PVKEY_EDIT_RESCHEDULE_REQUEST = "edit_Reschedule";
    public static final String PVKEY_EDIT_RESCHEDULE_DATE_INFO = "edit_reschedule_date_info";

    public static final String PVKEY_AUTOCLOSE = "PVKey_AutoClose";
    public static final String PVKEY_ADDONPRICE = "PVKey_AddOnPrice";
    public static final String PVKEY_PARTPRICE = "PVKey_PartPrice";
    public static final String PVKEY_RETAILPRICE = "PVKey_retailPrice";
    public static final String PVKEY_REIMBURSEMENTRETAILPRICE = "PVKey_reimbursementRetailPrice";
    public static final String PVKEY_PARTSSLGROSSUP = "PVKey_partsSLGrossup";
    public static final String PVKEY_RETAILPRICESLGROSSUP = "PVKey_retailPriceSLGrossup";
    public static final String PVKEY_SEND_PROVIDER_EMAIL = "sendProviderEmailInd";
    //SL-15642-Release service order
    public static final String PVKEY_RELEASE_COMMENT = "PVKey_ReleaseComment";
    public static final String PVKEY_RELEASE_ACTION_ID = "PVKey_ReleaseActionId";
    public static final String PVKEY_RELEASE_REASON = "PVKey_ReleaseReason";
    public static final String PVKEY_RELEASE_NAME = "PVKey_ReleaseName";
    public static final String PVKEY_RELEASE_ID = "PVKey_ReleaseId";
    
    public static final String RELEASE_ACTION_PERFORMED_IND = "ReleaseActionPerformedInd";
    public static final String RELEASE_CHANGED_SERVICE_DATE_INFO = "ChangedServiceDateInfo";

    //SL-15642-Accept SO firm
    public static final String PVKEY_ACCEPTED_NAME = "PVKey_AcceptedName";
    
    public static final String PVKEY_AUTOCLOSE_SO = "PVKey_AutoClose_So";
    public static final String PVKEY_CANCELLATION_COMMENT = "PVKey_Comment";
    public static final String PVKEY_CANCEL_COMMENT = "Cancellation_Comment";
    public static final String PVKEY_SEND_EMAIL= "PVKey_sendEmail";
    public static final String PVKEY_PRIVATE_NOTE="PVKey_privateNote";
	public static final String PVKEY_GROUP_ORDER_LABOR_SPEND_LIMIT = "GroupOrderLaborSpendLimit";
	public static final String PVKEY_GROUP_ORDER_PARTS_SPEND_LIMIT = "GroupOrderPartsSpendLimit";
	public static final String PVKEY_DOCUMENT_TITLES = "document_titles";
    public static final String PVKEY_LOG_ROUTING = "logRoutingToSOLog";
    public static final String AUTO_CLOSE_FAILURE_RULES="AUTO_CLOSE_FAILURE_RULES";
    public static final String AUTO_CLOSE_SUCESS_RULES="AUTO_CLOSE_SUCESS_RULES";
    public static final String CANCELLED_FIXED_PRICE="CANCELLED_FIXED_PRICE";
    public static final String FINAL_PRICE_LABOR="FinalPriceLabor";
    public static final String FINAL_PRICE_PARTS="FinalPriceParts";
    public static final String FINAL_PRICE="FinalPrice";
    public static final String AUTO_CLOSE_PENDING_TRANSACTION="autoclosePendingTransaction";
    public static final String PERMIT_SKU="99888";
    public static final String DEFAULT_STORE_NO = "09300";
    public static final String PVKEY_ADMIN_USER_NAME = "adminUserName";
	public static final String PVKEY_ADMIN_USER_ID = "adminUserId";
	public static final String PVKEY_MODIFIED_USER_NAME = "modifiedUserName";
	public static final String PVKEY_SPEND_LIMIT_REASON = "spendLimitReasonId";
	public static final String PVKEY_SPEND_LIMIT_OLD_PRICE = "oldSpendLimit";
	public static final String PVKEY_SPEND_LIMIT_OLD_LABOR_PRICE = "oldLaborPrice";
	public static final String PVKEY_SPEND_LIMIT_OLD_PARTS_PRICE = "oldPartsPrice";
	public static final String PVKEY_SPEND_LIMIT_NEW_LABOR_PRICE = "newLaborPrice";
	public static final String PVKEY_SPEND_LIMIT_NEW_PARTS_PRICE = "newPartsPrice";
	public static final String PVKEY_USER_NAME = "userName";
	public static final String PVKEY_MODIFIED_USER_ID = "modifiedUserId";
	public static final String PVKEY_AUTO_POST = "autoPost";
	public static final String PVKEY_TRIP_CHARGE_OVERRIDE_FEATURE = "TRIP_CHARGE_OVERRIDE";
	public static final String PVKEY_PENDING_CANCEL_STATUS ="PENDING_CANCEL_STATUS";
	public static final String PENDING_CANCEL_REQUEST_DATE ="PENDING_CANCEL_REQUEST_DATE";
	public static final String PENDING_CANCEL_REVIEW ="Review";
	public static final String PENDING_CANCEL_RESPONSE ="Response";
	public static final String PVKEY_PENDING_CANCEL_FIRMNAME ="FIRM_NAME";
	public static final String PVKEY_CANCEL_DATE ="SO_CANCEL_DATE";
	public static final String PVKEY_CANCELLATION_REASON_CODE ="CANCEL_REASON_CODE";
	public static final String PVKEY_CANCELLATION_REASON ="CANCEL_REASON";
	public static final String PVKEY_PAY_PROVIDER_IND = "PAY_PROVIDER_IND";
	public static final String PVKEY_SO_PRICING_METHOD = "SO_PRICING_METHOD";
	public static final String PVKEY_CANCELLATION_PROVIDER_ACKNOWLEDGEMENT_IND = "PROVIDER_ACKNOWLEDGEMENT_IND";
	public static final String BUYER_WITHDRAW_REQUEST="buyerWithdraw";
	public static final String BUYER_AGREE_AMOUNT="buyerAgree";
	public static final String BUYER_DISAGREE_AMOUNT="buyerDisagree";
	public static final String PROVIDER_WITHDRAW_REQUEST="providerWithdraw";
	public static final String PROVIDER_AGREE_AMOUNT="providerAgree";
	public static final String PROVIDER_DISAGREE_AMOUNT="providerDisagree";
	public static final Integer BUYER_ROLE_ID=new Integer (3);
	public static final Integer PROVER_ROLE_ID=new Integer (1);
	public static final Integer PENDING_REVIEW=new Integer(68);
	public static final Integer PENDING_RESPONSE=new Integer(69);
	public static final String PREVIOUS_SUBSTATUS="previousSubStaus";
	public static final String FEATURE_ON = "on";
	public static final String FEATURE_OFF = "off";
	public static final String NON_FUNDED_ORDER = "nonfunded";
	public static final String PVKEY_PENDING_CANCEL_STATE = "PENDING_CANCEL_STATE";
	public static final String REASON_COUNTER_OFFER = "Counter Offer";
	public static final String ROUTING_PRIORITY_IND = "routingPriorityInd";
	public static final String PERF_CRITERIA_LEVEL = "perfCriteriaLevel";
	public static final String OLD_SPN_ID = "oldSpnId";
	public static final String REPOST_FOR_TIER_FRONTEND = "repostForTierFrontend";
	public static final String FE_POST = "fePost";
	public static final String PVKEY_AUTO_ROUTING_BEHAVIOR = "autoRoutingBehavior";
	public static final String PROVIDERS_IN_CURRENT_TIER = "provInCurrentTier";
	public static final String PROVIDERS_IN_PREVIOUS_TIERS = "provInPreviousTiers";
	public static final String AUTO_ROUTING_TIER = "autoRoutingTier";
	public static final String ISUPDATE = "isUpdate";
	//pos cancellation
	public static final String SO_ID = "SO_ID";
	public static final String NEW_STATUS= "NEW_STATUS";
	public static final String OLD_STATUS = "OLD_STATUS";
	//SO Price Types
	public static final String TASK_LEVEL_PRICING = "TASK_LEVEL";
	public static final String SO_LEVEL_PRICING = "SO_LEVEL";
	
	//So Task Types
	public static final  Integer PRIMARY_TASK = 0;
	public static final  Integer PERMIT_TASK = 1;
	public static final  Integer DELIVERY_TASK = 2;
	public static final  Integer NON_PRIMARY_TASK = 3;
	
	//SO Task Status
	public static final String ACTIVE_TASK = "ACTIVE";
	public static final String DELETED_TASK = "DELETED";
	public static final String CANCELED_TASK= "CANCELED";
	
	//Deposition ID
	public static final String PVKEY_DEPOSITION_CODE_IND = "CLIENT_CHARGED_DEPOSITION_CODE_ID";

	public static final String FE_POST_ORDER = "FE_POST_ORDER";
	public static final String SAVE_AS_DRAFT = "saveAsDraft";
	public static final String OTHER = "Other";
	//added for modification in overview for officeMax
	public static final String OVERVIEW_TITLE="This service order is for a:";
	public static final String OVERVIEW_QUESTION_HEADING="Below are assessment question answers pertaining to the services requested that the end user has provided:";
	public static final String PVKEY_CANCELLATION_API_CODE= "CANCELLATION_THROUGH_API_KEY";
	public static final String CANCELLATION_API="CANCELLATION_THROUGH_API";
	public static final String PVKEY_MANAGESCOPE_REASON_CODE = "REASON_CODE";
	public static final String PVKEY_MANAGESCOPE_REASON_CODE_ID = "REASON_CODE_ID";
	public static final String PVKEY_MANAGESCOPE_REASON_CODE_COMMENTS = "REASON_COMMENTS";
	
	public static final String DATE_FORMAT = "MMM d, yyyy";
	public static final String ZERO_PRICE_BID = "ZERO_PRICE_BID";
	//added for choice project
	public static final Integer BUYER_ID=1000;
	
	public static final String ASSIGNMENT_TYPE_FIRM = "FIRM";
	public static final String ASSIGNMENT_TYPE_PROVIDER = "PROVIDER";
	public static final String POST_FROM_FRONTEND_ACTION ="postFromFrontEndAction";
	
	public final static int PROVIDER_ROLEID = 1;
	public final static int BUYER_ROLEID = 3;
	
	public final static String INHOME_OUTBOUND_NOTIFICATION_RESCHEDULE_MESSAGE = "INHOME_OUTBOUND_NOTIFICATION_RESCHEDULE_MESSAGE";
	public final static String INHOME_OUTBOUND_NOTIFICATION_AUTOACCEPT_MESSAGE = "INHOME_OUTBOUND_NOTIFICATION_AUTOACCEPT_MESSAGE";
	public final static String INHOME_NOTIFICATION_TYPE_RESCHEDULE = "RESCHEDULE";
	public final static String INHOME_NOTIFICATION_TYPE_AUTO_ACCEPT = "AUTO_ACCEPT";
	public final static String INHOME_NOTIFICATION_TYPE_SUB_STATUS_CHANGE = "SUB_STATUS_CHANGE";
	public final static String INHOME_NOTIFICATION_TYPE_STATUS_POSTED = "POSTED";
	
	public final static String BUYER_ID_NONFUNDED="buyerId";
	public final static String TEMPLATE_ID="templateId";
	
	
	public final static int PROBLEM_STATUS=170; 
	public final static int ACTIVE_STATUS=155; 
	public final static int PENDING_CLAIM_SUBSTATUS=135; 
	public final static int COMPLETED_STATUS=160; 
	public final static int CLOSED_STATUS=180; 
	
	//R12_0
	public final static String  INHOME_OUTBOUND_NOTIFICATION_RESCHEDULE_CALL_CODE="INHOME_OUTBOUND_NOTIFICATION_RESCHEDULE_CALL_CODE";
	
	public static final String PART_STATUS_INSTALLED = "Installed";
	//R12_1
	public static final String CLAIMSTATUS_APPROVED = "Approved";
	public static final String CLAIMSTATUS_PENDING = "Pending";
	public static final String STATUS_WAITING = "WAITING";
	public static final String PART_STATUS_NOT_INSTALLED = "Not Installed";
	public static final String TIME_INTERVAL = "inhome_autoclose_time_interval";
	public static final String PENDING_AUTO_CLOSE = "Pending Auto Close";
	public static final String MANUAL_REVIEW = "Manual Review";
	public static final String STATUS_MANUAL = "MANUAL";
	//R12_1 SL-20647
	public static final String SYSTEM = "System";
	public static final boolean INDICATOR_PRIVATE = true;
	public static final long INHOME_BUYER = 3000;
	public static final long AT_AND_T_BUYER = 512353;
	public static final int NO_SUBSTATUS = 52;
	
	public static final long SYSTEM_ENTITY_ID=0;
	public static final int NOTE_TYPE_GENERAL_TWO = 2;
	public static final String PA_NOTE1 = "All ";
	public static final String PA_NOTE2 = " parts have claim status as Approved.";
	public static final String PA_NOTE3 = " part has claim status as Approved.";
	public static final String PA_NOTE4 = "There are no installed parts and the service order is in pending auto close.";
	public static final String MR_NOTE_SUBJECT = "Audit and Approve for Payment";
	public static final String MR_NOTE1 = " parts have claim status as Pending because the estimated provider payment exceeds the max limit.";
	public static final String MR_NOTE2 = "parts are manually added.";
	public static final String MR_NOTE3 = " parts have claim status as Pending because the parts are manually added. ";
	public static final String MR_NOTE4 = " part has claim status as Pending because the part is manually added. ";
	public static final String MR_NOTE5 = " part has claim status as Pending because the estimated provider payment exceeds the max limit.";
	public static final String MR_NOTE6 = "part is manually added.";
	
	//SL-21126
	public static final String PRICE_REDUCTION_FACTOR = "price_reduction_factor";
	public static final String PRICE_REDUCTION_LOGGING = "Multiple same date/location orders. Order price has been adjusted";
	public static final Integer POSTED_STATE = 110;
	
	public static final Integer ACCEPTED_STATE = 150;
	public static final Integer ACTIVE_STATE = 155;
	public static final Integer PROBLEM_STATE = 170;
	public static final String POSSIBLE_WF_STATES_OF_PRIMARY_ORDER = "possible_wf_states_of_primary_order";
	
	//Priority 1 changes
	public final static int ACCEPTED_STATUS=150; 
	
	public final static int DRAFT_STATUS=100;
	public final static int POSTED_STATUS=110;
	public static final int EXPIRED_STATUS = 130;
	
	//Priority 5B changes
	public static final String PVKEY_MODEL_SERIAL_ERROR = "PVKey_ModelSerialError";
	
    public static final String PVKEY_RESOURCE_ID = "PVKey_ResourceId";
    public static final String PVKEY_CREATED_BY = "PVKey_CreatedBy";
    public static final String PVKEY_MODIFIED_BY = "PVKey_ModifiedBy";
    public static final String PVKEY_RES_COMMENTS = "Resolution comments";
    public static final String PVKEY_ROLE_ID = "PVKey_RoleId";
  	public static final Integer READ_IND_ZER0 = 0;

    public static final String EVENT = "event";
    public static final String EQUALS = "=";
    public static final String CALL_BACK_URL = "CallBackURL";
    public static final long RELAY_SERVICES_BUYER_ID = 3333;
    public static final long TECHTALK_SERVICES_BUYER_ID = 7777;
    public static final String TAX_SERVICE_KEY = "tax_service_url";
    public static final String AND = "&";
	public static final String  SERVICEPROVIDER= "serviceprovider";
	public static final String  SERVICELIVE= "SL";
	// SL-21466
    public static final String AUTO_ACCEPT_BUYER_RESCHEDULE = "System: Automatic Reschedule";

    public static final String ORDER_REPOSTED_TO_NEW_FIRM = "ORDER_REPOSTED_TO_NEW_FIRM";
    public static final String ORDER_REPOSTED_TO_FIRM = "ORDER_REPOSTED_TO_FIRM";
    public static final String ORDER_POSTED_TO_FIRM = "ORDER_POSTED_TO_FIRM";
    public static final String ORDER_STATE_CHANGED_TO_PROBLEM = "order_state_changed_to_problem";
    public static final String ORDER_ACCEPTED_BY_PROVIDER = "order_accepted_by_provider";
    
    public static final Integer NO_OF_RETRIES = -1;
    public static final String STATUS_WAITING_FOR_REQUEST_DATA = "WAITING_FOR_REQUEST_DATA";
}
