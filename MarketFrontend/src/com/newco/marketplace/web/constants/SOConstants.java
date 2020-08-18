package com.newco.marketplace.web.constants;


public interface SOConstants {
	
	public static final String SECURITY_KEY = "SecurityContext";
	public static final String IS_LOGGED_IN = "IS_LOGGED_IN";
	public static final String IS_SIMPLE_BUYER = "IS_SIMPLE_BUYER";
	public static final String IS_ADMIN = "IS_ADMIN";
	public static final String USERNAME_WHILE_CREATING_SERVICE_ORDER = "joinWhileCreatingOrder.username";
	// Statuses
	public static final Integer DRAFT_INDEX = 1;
	public static final Integer ROUTED_INDEX = 2;
	public static final Integer EXPIRED_INDEX = 3;
	public static final Integer VOIDED_INDEX = 4;
	public static final Integer ACCEPTED_INDEX = 5;	
	public static final Integer ACTIVE_INDEX = 6;
	public static final Integer PROBLEM_INDEX = 7;
	public static final Integer COMPLETED_INDEX = 8;
	public static final Integer CLOSED_INDEX = 9;
	public static final Integer CANCELLED_INDEX = 10;
	public static final Integer COMPLETION_SOURCE = 1;
	public static final String  THE_SERVICE_ORDER = "THE_SERVICE_ORDER";
	public static final String  THE_GROUP_ORDER = "THE_GROUP_ORDER";
	public static final String  THE_SERVICE_ORDER_STATUS_CODE = "THE_SERVICE_ORDER_STATUS_CODE";
	public static final String 	THE_CURRENT_ROLE = "THE_CURRENT_ROLE";
	public static final String 	REFETCH_SERVICE_ORDER = "REFETCH_SERVICE_ORDER";
	public static final String COMMON_CONFIG = "resources/properties/servicelive.properties";

	
	public static final String TO_COMMON_GRID 	= "toCommonGrid";
	public static final String TO_REALTIME_GRID = "toRealTimeGrid";
	public static final String TO_SEARCH_GRID 	= "toSearchGrid";
	public static final String TO_DETAILS_VIEW = "soDetailsView";
	public static final String VIEW = "view";
	public static final String TO_SSO_HOMEPAGE_VIEW = "homepage_displayPage";
	public static final String TO_SSO_FIND_PROVIDERS_VIEW = "csoFindProviders";
	public static final String TO_SSO_DESCRIBE_AND_SCHEDULE_VIEW = "csoDescribeAndSchedule";
	public static final String TO_SSO_SERVICE_ORDER_REVIEW_VIEW = "csoReview";
	public static final String TO_SSO_CREATE_ACCOUNT_VIEW = "csoCreateAccount";
	public static final String TO_SSO_EDIT_ACCOUNT_VIEW = "csoEditAccount";	
	public static final String TO_SSO_ADD_FUNDS_VIEW = "csoAddFunds";
	public static final String TO_SSO_SERVICE_ORDER_CONFIRMATION_VIEW = "csoConfirmation";
	
	public static final Integer SENT_INDEX = 11;
	public static final String SENT = "Sent";
	
	public static final Integer PLEASE_SELECT_INDEX = 0;
	public static final String PLEASE_SELECT = "Please Select";
	
	public static final String DRAFT = "Draft";
	public static final String PART_BACK_ORDERED = "Part Back Ordered";
	public static final String PART_ON_ORDER = "Part on Order";
	
	public static final String ROUTED = "Routed";
	public static final String RELEASED_BY_PROVIDER = "Released by Provider";
	public static final String LOCKED_FOR_BUYER = "Locked For Buyer Edit";
		
	public static final String EXPIRED = "Expired";
	
	public static final String REVIEW ="Review";
	
	/***********NOTE TYPES*************/
	public static final Integer SUPPORT_NOTE = 1;
	public static final Integer GENERAL_NOTE = 2;
	public static final Integer INTERNAL_NOTE = 3;
	public static final Integer PRIVATE_NOTE = 1;
	public static final Integer PUBLIC_NOTE = 0;
	public static final String GENERAL_NOTE_SUCCESS = "general";
	public static final String SUPPORT_NOTE_SUCCESS = "support";
	public static final String NOTE_ADD_SUCCESS = "Successfully added note.";
	public static final Integer SEARS_BUYER=1000;
	/***********NOTE TYPES*************/
	
	public static final String SEND_EMAIL_ALERT = "0";
	public static final String NO_EMAIL_ALERT = "2";
	
	public static final String GOTO_COMMON_DETAILS_CONTROLLER = "gl_details_success";
	public static final String GOTO_COMMON_WIZARD_CONTROLLER = "gl_wizard_success";
	public static final String GOTO_COMMON_POWER_BUYER_CONTROLLER = "gl_power_buyer_success";
	public static final String GOTO_COMMON_FINANCE_MGR_CONTROLLER = "gl_finance_success";
	public static final String GOTO_COMMON_FINANCE_MGR_CONTROLLER_ERROR = "gl_finance_error";
	public static final String GOTO_COMMON_SIMPLE_SERVICE_ORDER_CONTROLLER = "gl_simple_wizard_success";
	
	public static final String TAB_ATTRIBUTE = "tabList";
	
	public static final String VOIDED = "Voided";
	
	// COMMON DATA GRID INFORMATION
	public static final String DATA_GRID_LIST = "D_GRID_INFO";
	
	public static final String ACCEPTED = "Accepted";
	public static final String RESCHEDULED_CUSTOMER = "Rescheduled by Customer";
	public static final String RESCHEDULED_PROVIDER = "Rescheduled by Provider";
	public static final String RESCHEDULED_CUSTOMER_NO_SHOW = "Rescheduled, Customer No Show";
	public static final String RESCHEDULED_PROVIDER_NO_SHOW = "Rescheduled, Provider No Show";
	public static final String PART_RECEIVED_HOLD = "Part Received, Hold for Pickup";
	public static final String PART_RECEIVED_CUSTOMER = "Part Received by Customer";
	public static final String PART_RECEIVED_PROVIDER = "Part Received by Provider";
	public static final String NEED_ADDITIONAL_PARTS = "Need Additional Parts";
	public static final String PART_SHIPPED = "Part Shipped";
				
	public static final String ACTIVE = "Active";
	public static final String PROVIDER_ON_SITE = "Provider On-Site";
	public static final String JOB_DONE = "Job Done";
	
	public static final String PROBLEM = "Problem";
	public static final String ABANDONED_WORK = "Abandoned Work";
	public static final String ADDITIONAL_WORK_REQUIRED= "Additional Work Required";
	public static final String ADDITIONAL_PART_REQUIRED = "Additional Part Required";
	public static final String OUT_OF_SCOPE = "Out of Scope";
	public static final String PROPERTY_DAMAGE = "Property Damage";
	public static final String PROVIDER_NO_SHOW = " Provider No Show";
	public static final String END_CUSTOMER_NO_SHOW = "End Customer No Show";
	public static final String PROVIDER_NOT_QUALIFIED = "Team Member Not Qualified to Complete Work";
	public static final String SITE_NOT_READY = "Site not Ready";
	public static final String UNPROFESSIONAL_ACTION = "Unprofessional Action/Behavior";
	public static final String WORK_NOT_COMPLETE = "Work Not Complete";
	
	
	public static final String COMPLETED = "Completed";
	public static final String AWAITING_PAYMENT = "Awaiting Payment";
	public static final String DOCUMENT_REQUIRED = "Document Required";
	
	
	public static final String CLOSED = "Closed";
	
	public static final String CANCELLED = "Cancelled";
	public static final String CANCELLED_BY_END_CUSTOMER_NO_SHOW = "Cancelled By End Customer No Show";
	public static final String CANCELLED_BY_BUYER_SIDE= "Cancelled By Buyer Side";
	public static final String CANCELLED_BY_END_CUSTOMER= "Cancelled By End Customer";
	
	
	// Extra
	public static final String CANCELLED_BY_PROVIDER_SIDE= "Cancelled By Provider Side";
	public static final String RATING_AVAILABLE = "Ratings Available";
	public static final String FUNDS_TRANSFERRED= "Funds Transferred";
	public static final String TEAM_MEMBER_NO_SHOW = "Team Member No Show";
	public static final String RESCHEDULED = "Rescheduled";
	public static final String NO_COMMUNICATION_OR_NOTES = "No Communication or Notes";
	public static final String PART_RECEIVED_TEAMMEMBER = "Part Received by Team Member";
	public static final String TEAM_MEMBER_ON_SITE = "Team Member On-site";
	public static final String SPEND_LIMIT_INCREASE_NEEDED = "Maximum Price Increase Needed";
	public static final String REWORKED_NEEDED = "Rework Needed";
	public static final String RESCHEDULE_NEEDED = "Reschedule Needed";
	public static final String INCOMPLETE_SOW = "Incomplete Statement of Work";
	// Extra
	
	//Details
	public static final String DETAILS_ERROR_MSG = "detailsErrorMsg";
	//Details
	
	public static final Integer PART_BACK_ORDERED_INDEX = 1;
	public static final Integer PART_ON_ORDER_INDEX = 2;
	public static final Integer RELEASED_BY_PROVIDER_INDEX = 3;
	public static final Integer LOCKED_FOR_BUYER_INDEX = 4;
	public static final Integer RESCHEDULED_CUSTOMER_INDEX = 5;
	public static final Integer RESCHEDULED_PROVIDER_INDEX = 6;
	public static final Integer RESCHEDULED_CUSTOMER_NO_SHOW_INDEX = 7;
	public static final Integer RESCHEDULED_PROVIDER_NO_SHOW_INDEX = 8;
	public static final Integer PART_RECEIVED_HOLD_INDEX = 9;
	public static final Integer PART_RECEIVED_CUSTOMER_INDEX = 10;
	public static final Integer PART_RECEIVED_PROVIDER_INDEX = 11;
	public static final Integer NEED_ADDITIONAL_PARTS_INDEX = 12;
	public static final Integer PART_SHIPPED_INDEX= 13;
	public static final Integer PROVIDER_ON_SITE_INDEX = 14;
	public static final Integer JOB_DONE_INDEX = 15;
	public static final Integer ABANDONED_WORK_INDEX = 16;
	public static final Integer ADDITIONAL_WORK_REQUIRED_INDEX = 17;
	public static final Integer ADDITIONAL_PART_REQUIRED_INDEX = 18;
	public static final Integer OUT_OF_SCOPE_INDEX= 19;
	public static final Integer PROPERTY_DAMAGE_INDEX = 20;
	public static final Integer PROVIDER_NO_SHOW_INDEX = 21;
	public static final Integer END_CUSTOMER_NO_SHOW_INDEX = 22;
	public static final Integer PROVIDER_NOT_QUALIFIED_INDEX = 23;
	public static final Integer SITE_NOT_READY_INDEX = 24;
	public static final Integer UNPROFESSIONAL_ACTION_INDEX = 25;
	public static final Integer WORK_NOT_COMPLETE_INDEX = 26;
	public static final Integer AWAITING_PAYMENT_INDEX = 27;
	public static final Integer DOCUMENT_REQUIRED_INDEX = 28;
	public static final Integer CANCELLED_BY_END_CUSTOMER_NO_SHOW_INDEX = 29;
	public static final Integer CANCELLED_BY_BUYER_SIDE_INDEX = 30;
	public static final Integer CANCELLED_BY_END_CUSTOMER_INDEX = 31;
	
	
	// EXTRA
	public static final Integer CANCELLED_BY_PROVIDER_SIDE_INDEX = 6;
	public static final Integer FUNDS_TRANSFERRED_INDEX = 8;
	public static final Integer INCOMPLETE_STATEMENT_OF_WORK= 9;
	public static final Integer JOB_DONE_TO_BE_COMPLETED_FOR_PAYMENT= 10;
	public static final Integer NO_COMMUNICATION_OR_NOTES_INDEX= 11;	
	public static final Integer PART_RECEIVED_HOLD_FOR_PICKUP= 15;
	public static final Integer PART_RECEIVED_BY_END_CUSTOMER= 16;
	public static final Integer PART_RECEIVED_BY_TEAM_MEMBER= 17;	
	public static final Integer MAX_COUNT= 20;
//	Service Order Conditions
	
	public static final String ACCEPT_TERMS_AND_COND_FOR_PROVIDER ="Service Order Accept";
	public static final String ACCEPT_TERMS_AND_COND_FOR_AGREEMENT ="Provider Agreement";
	public static final String ACCEPT_TERMS_AND_COND_BUX_FOR_PROVIDER ="ServiceLive Bucks";
	public static final String ACCEPT_TERMS_AND_COND_STATE_LICENSES ="State Licenses";
	public static final String  ACCEPT_TERMS_AND_COND_FOR_BUYER="Buyer Agreement";
	public final static String BUYER_SO_AGREEMENT = "Buyer Agreement";
	// provider resp codes
	
	public static final Integer PROVIDER_RESP_REJECTED = 3;
	public static final Integer PROVIDER_RESP_RELEASE_BY_PROVIDER = 5;
	public static final Integer PROVIDER_RESP_RELEASE_BY_FIRM = 7;
	
	// Dashboard modules
	public static final String BUYER_ADMIN_OFFICE = "buyer_admin_office.jsp";
	public static final String BUYER_FINANCE_MANAGER = "buyer_finance_manager.jsp";
	public static final String BUYER_FINANCE_MANAGER2 = "buyer_finance_manager2.jsp";
	public static final String BUYER_MY_FAVORITES = "buyer_my_favorites.jsp";
	public static final String BUYER_OVERALL_STATUS_MONITOR = "buyer_overall_status_monitor.jsp";	
	public static final String BUYER_PROFILE_PREFS = "buyer_profile_prefs.jsp";
	public static final String BUYER_REPORTS = "buyer_reports.jsp";
	public static final String BUYER_SERVICE_ORDERS = "buyer_service_orders.jsp";
	
	public static final String PROVIDER_FINANCE_MANAGER = "provider_finance_manager.jsp";
	public static final String PROVIDER_MY_FAVORITES = "provider_my_favorites.jsp";
	public static final String PROVIDER_OVERALL_STATUS_MONITOR = "provider_overall_status_monitor.jsp";
	public static final String PROVIDER_PROFILE_PREFS = "provider_profile_prefs.jsp";
	public static final String PROVIDER_RECEIVED_SERVICE_ORDERS = "provider_received_service_orders.jsp";
	public static final String PROVIDER_REPORTS = "provider_reports.jsp";
	public static final String PROVIDER_TECH_OVERALL_STATUS_MONITOR = "provider_tech_overall_status_monitor.jsp";
	public static final String PROVIDER_WEATHER = "provider_weather.jsp";
	public static final String PROVIDER_ADMIN_OFFICE = "provider_admin_office.jsp";
	public static final String PROVIDER_MINI_SPN_MONITOR = "provider_mini_spn_monitor.jsp";
	public static final String PROVIDER_D2C_PORTEL = "provider_consumer_service.jsp";
	public static final String PROVIDER_MEMBER_OFFERS = "provider_memberOffer_widget.jsp";
	public static final String PROVIDER_LEADS_SIGNUP = "provider_leads_signup_widget.jsp";
	//Phone Types
	public static final Integer PHONE_HOME = 3;
	public static final Integer PHONE_WORK = 1;
	public static final Integer PHONE_MOBILE = 2;
	public static final Integer PHONE_PAGER = 4;
	public static final Integer PHONE_OTHER = 5;
	
	public static final String SESSION_SORT_COLUMN = "sortColumnName";
	public static final String SESSION_SORT_ORDER = "sortOrder";
	public static final String SESSION_PRICE_MODEL ="priceModel";
	public static final String NOTE = "note_key";
	public static final String MESSAGE = "message_key";
	
	public static final String SO_STATUS="soStatus";
	public static final String SO_SUBSTATUS="soSubStatus";
	public static final String PRICE_MODEL = "priceModel";
	public static final String SO_SERVICEPRONAME="serviceProName";
	public static final String SO_ROLEID="roleId";
	public static final String SO_MARKETNAME="marketName";
	public static final String PUBLIC_CUSTOM_REF="false";
	public static final String SERVICE_ORDER_STATUS="serviceOrderStatus"; 
	public static final String DRAFT_STATUS = "100"; 
	public static final String EXPIRED_STATUS = "130"; 
	public static final String POSTED_STATUS = "110"; 
	
	public final class FieldLength
	{
		public static final Integer STREET1 = 100;
		public static final Integer STREET2 = 100;
		public static final Integer CITY = 30;
	}
	
	public static final Integer CallBack_QueueId = 2;
	public static final String TODAY = "Today";
	
	
	public static String SECURITY_TOKEN_SESSION_KEY = "securityToken";
	public static String SECURITY_TOKEN_DIGESTER = "MD5";
}
