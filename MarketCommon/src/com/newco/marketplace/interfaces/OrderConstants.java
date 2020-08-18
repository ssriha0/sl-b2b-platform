package com.newco.marketplace.interfaces;

import java.util.HashMap;

public interface OrderConstants {

	public final static String SSO_FIND_PROVIDERS_DTO = "sso_find_providers";
	public final static String SSO_DESCRIBE_AND_SCHEDULE_DTO = "sso_describe_and_schedule";
	public final static String SSO_HOMEPAGE_DTO = "sso_homepage_dto";
	public final static String SSO_CREATE_ACCOUNT_DTO = "sso_create_account";
	public final static String SSO_EDIT_ACCOUNT_DTO = "sso_edit_account";
	public final static String SSO_ADD_FUNDS_DTO = "sso_add_funds";
	public final static String SSO_ORDER_REVIEW_DTO = "sso_order_review";
	public final static String SSO_SELECT_LOCATION_DTO = "sso_select_location";
	public final static String EDIT_MODE = "edit_mode";
	public final static String CREATE_MODE = "create_mode";
	public final static String COPY_MODE = "copy_mode";
	public final static String APP_MODE = "appMode";
	public final static String SIMPLE_SERVICE_ORDER_WIZARD_INDICTATOR = "Simple_Service_Order_Wizard_is_active.";
	public final static String COUNTRY_USA_CODE = "US";
	public final static int DEFAULT_PAGE_SIZE = 25;
	public final static String PAGINATION_RESULTS_SET = "paginationResults";
	public final static String ORDER_BEING_EDITED = "Unable to complete action.  Service Order is currently being edited.";
	public final static String ORDER_HAS_GROUPED_NOW = "Unable to accept the service order. The order is no longer an individual order and has been grouped with other orders; you may refresh service order monitor and accept the order as grouped order.";
	public final static String SSO_WIZARD_IN_PROGRESS = "wizardInProgress";
	public final static String SSO_WIZARD_IN_PROGRESS_VALUE = "inProgress";
	public final static String FRONTEND_ACTION_SOURCE = "ServiceLiveButton";
	public final static String ORDER_IN_CANCELLED_STATUS = "Order has been cancelled by the buyer";
	public final static String ORDER_ACCEPTED_BY_ANOTHER_PROVIDER = "Order has been accepted by another provider";
	public final static String ORDER_ACCEPTED_BY_ANOTHER_PROVIDER_OF = "Order workflow is in a state where ACCEPT_ORDER signal is prohibited";
	public final static String BUYER_ADMIN_CONTACT_ID = "buyerAdminContactId";
	public final static String ORDER_IS_ROUTED = "Service Order is ROUTED";
	public final static String RESCHEDULE_DTO = "RescheduleDTO";
	public final static String EDIT_COMPLETION = "editCompletion";
	public final static String AUTOCLOSE_SUCCESS = "Service order is auto closed.";
	public final static String AUTOCLOSE_FAILURE = "Service order is not auto closed and requires a manual review.";
	// Levels for different sku Name
	public final static int MAIN_SERVICE_CAT_SKILL_LEVEL = 1;
	public final static int CATEGORY_SKILL_LEVEL = 2;
	public final static int SUB_CAT_SKILL_LEVEL = 3;

	// Default values for buyer sku table: SL- 17504
	public final static Double DEFAULT_BILLING_PRICE = 0.00;
	public final static Double DEFAULT_BILLING_MARGIN = 0.0000;
	public final static Boolean DEFAULT_MANAGE_SCOPE_IND = false;
	public final static String DEFAULT_BID_PRICE_SCHEMA = "DEFAULT";
	public final static String DEFAULT_PRICE_TYPE = "FIXED";

	// Title for sku category modal pop up: SL-17504
	public final static String ADD_SKU_CATEGORY = "Add SKU Category";
	public final static String ADD_SKU = "Add SKU";
	public final static String UPDATE_SKU = "Update SKU";

	// Constants for History Table:SL-17504
	public final static String SKU_CATEGORY_CREATED = "SKU Category Created";
	public final static String NAME_UPDATED = "Name Updated";
	public final static String DESC_UPDATED = "Description Updated";
	public final static String SKU_CREATED = "SKU Created";
	public final static String SKU_MODIFIED = "Modified";

	// Info messages for add sku category modal SL-17504
	public final static String RETAILPRICE_INFO = "This is the price you as the buyer might charge your customer for the service.   ServiceLive platform does not use Retail price unless you specify a margin.  (e.g. you sell to your customer for $100 and expect to make a 20% margin, thus the system calculates your maximum price to be $80.";
	public final static String BID_INFO = "Maximum price is the amount you are paying the provider. Buyer can either provide a specific Maximum price or ServiceLive platform will calculate Maximum price with the use of Retail price and Margin.Margin can be (+) or (-) of the retail price.";
	public final static String BILLING_INFO = "This is only applicable for ServiceLive managed accounts.Billing price/Billing margin is the price/margin ServiceLive charges to manage Buyer account.";
	public final static String PRICETYPE_INFO = "This is the price type for which you can select values FIXED or VARIABLE";

	// STATUSES
	public final static int ACTIVE_IND = 1;
	public final static int DRAFT_STATUS = 100;
	public final static int ROUTED_STATUS = 110;
	public final static int CONDITIONAL_OFFER_STATUS = 140;
	public final static int ACCEPTED_STATUS = 150;
	public final static int ACTIVE_STATUS = 155;
	public final static int COMPLETED_STATUS = 160;
	public final static int PENDING_CANCEL_STATUS = 165;
	public final static int CANCELLED_STATUS = 120;
	public final static int VOIDED_STATUS = 125;
	public final static int CLOSED_STATUS = 180;
	public final static int PROBLEM_STATUS = 170;
	public final static int EXPIRED_STATUS = 130;
	public final static int DELETED_STATUS = 105;
	public final static int TODAY_STATUS = 9000;
	public final static int INACTIVE_STATUS = 9500;
	public final static int SEARCH_STATUS = 9600;
	public final static int INACTIVE_GROUP_STATUS = 190;
	public final static int COMPLETION_SOURCE = 1;

	// SUBSTATUSES
	public final static int MISSING_INFORMATION_SUBSTATUS = 36;
	public final static int CANCELLATION_REQUEST_SUBSTATUS = 37;
	public final static int RESCHEDULED_CONFIRMED_SUBSTATUS = 41;
	public final static int NEEDS_ATTENTION_SUBSTATUS = 42;
	public final static int SCHEDULING_NEEDED_SUBSTATUS = 50;
	public final static int QUEUED_FOR_GROUPING_SUBSTATUS = 51;
	public final static int NO_PROVIDER_SUBSTATUS = 56;
	public final static int AWAITING_PAYMENT_SUBSTATUS = 60;
	public final static int CONFIRM_ADDON_FUNDS_SUBSTATUS = 34;
	public final static int NO_SUBSTATUS = 52;
	public final static int SCOPE_CHANGE = 62;
	public final static int READY_FOR_POSTING_SUBSTATUS = 63;
	public final static int MANUAL_REVIEW = 100;
	public final static int REPEAT_REPAIR = 101;

	public final static int SERVICE_LOCATION_CONTACT_TYPE_ID = 10;
	public static final Integer JOB_DONE = Integer.valueOf(8);
	public static final Integer PROVIDER_ONSITE = Integer.valueOf(20);
	public static final Integer NOTAPPLICABLE = Integer.valueOf(0);
	public static final Integer BUYERIDOFHSR = 3000;
	public static final Integer BUYERIDOFSEARS = 1000;

	public final static String SERVICE_ORDER = "serviceOrder";
	public final static String SERVICE_ORDER_DELIMITER = "-";
	public final static String PROVIDERS = "providers";
	public final static String BUYER = "buyer";
	public final static String SIMPLE_BUYER = "SimpleBuyer";
	public final static String TASKS = "tasks";
	public final static String FAILED_SERVICE_ORDER_NO = "000-0000000000-00";
	// ROLE_ID
	public final static int UNKNOWN_ROLEID = -1;
	public final static int NEWCO_ADMIN_ROLEID = 2;
	public final static int NEWCO_ADMIN_COMPANY_ROLE = 9;
	public final static int PROVIDER_ROLEID = 1;
	public final static int BUYER_ROLEID = 3;
	public final static int SIMPLE_BUYER_ROLEID = 5;
	public final static int SYSTEM_ROLEID = 6;
	public final static int SIMPLE_BUYER_COMPANY_ROLE_ID = 50;
	public final static int OTHER_WEBSITE_OR_FORUM = 5;

	public final static String ADMIN_MODIFIED_BY = "admin";
	public final static String PROVIDER = "provider";
	public final static String NEWCO_ADMIN = "NewCo";
	public final static String PAGINATION_VO = "paging_vo";

	public final static String NEWCO_DISPLAY_SYSTEM = "System";

	public final static String NO_EMAIL_ALERT = "2";
	public final static String ADD_NOTES_MESSAGE = "Notes are shared (public) unless marked private. Email alerts are not sent for private notes.";

	// 1099 Reports
	public final static String PROVIDER_RPT = "Provider";
	public final static String BUYER_RPT = "Buyer";

	// FUNDING TYPE ID
	public final static int PREFUNDED = 90;
	public final static int DIRECT_FUNDED = 20;
	public final static int SHC_FUNDED = 40;
	public final static int CONSUMER_FUNDED = 70;

	// ESCHEATMENT ACCOUNT TYPE
	public final static Long ESCHEATMENT_ACCOUNT_TYPE = 60L;

	// PRICING
	public final int FIXED_PRICE = 1;
	public final int HOURLY_PRICE = 2;
	// Maximum amount for withdrawal
	public final int MAX_WITHDRAWAL = 900;
	// Constants for Service Order Wizard Edit mode
	public static final int SO_NOT_EDIT_MODE_FLAG = 1;

	// SLRating
	public final static Double SL_NOTRATED = 0.00;
	
	// Newco Permission Set
	public final static int Admin_Only_Actions_Newco = 32;

	// Legal Hold : Disable Wallet Funds Withdrawals
	public final static String Legal_Hold_Disable_Wallet_Funds_Withdrawals = "Legal Hold : Disable Wallet Funds Withdrawals";

	// VALIDATION MESSAGES
	public final static String NO_CONDITIONAL_OFFER_ASSOCIATED_WITH_WFSTATE = "Service Order in this state cannot have Counter Offer";
	public final static String SERVICE_ORDER_NOT_IN_DRAFT_STATE = "Service Order in this state cannot be deleted";
	public final static String SUCESSFULLY_WITH_DRAWN_OFFER = "Counter Offer withdrawn";
	public final static String NO_CONDITIONAL_OFFER_ASSOCIATED = "No Counter Offer associated with Service Order";
	public final static String NOTE_TYPE_REQUIRED = "Note type id is required";
	public final static String NOTE_TEXT_REQUIRED = "Note text is required";
	public final static String PRICING_TYPE_REQUIRED = "Pricing type is required";
	public final static String MODIFIED_BY_REQUIRED = "Modified by is required";
	public final static String SERVICE_ORDER_NUMBER_REQUIRED = "Service order number is required";
	public final static String BUYER_ID_REQUIRED = "Buyer id is required";
	public final static String INCREASE_IN_SPEND_LIMIT_REQUIRED = "Enter increase in labor/Maximum Price for Materials amount";
	public final static String INCREASE_IN_SPEND_LIMIT_MUST_INCREASE = "New Maximum Price must be greater than the current Maximum Price";
	public final static String NUMBER_INCREASE_LABOR_LIMIT = "Enter increase in Maximum Price for Labor amount in decimal form";
	public final static String INCREASE_LABOR_LIMIT_MUST_INCREASE = "New Maximum Price for Labor amount cannot be less than the current Maximum Price for Labor";
	public final static String NUMBER_INCREASE_PARTS_LIMIT = "Enter increase in Maximum Price for Materials amount in decimal form";
	public final static String INCREASE_PARTS_LIMIT_MUST_INCREASE = "New Maximum Price for Parts amount cannot be less than the current Maximum Price for Parts";
	public final static String INCREASE_LABOR_PARTS_LIMIT_SAME = "Enter an increase in Maximum Price for either the Labor amount or the Parts amount";
	public final static String REASON_REQUIRED = "Enter/Select a reason for spend limit increase";
	public final static String CREATOR_USERNAME_REQUIRED = "Creator user name is required";
	public final static String SCHEDULE_DATE_REQUIRED = "Schedule date is required";
	public final static String APPOINTMENT_START_DATE_REQUIRED = "Appointment start date is required";
	public final static String APPOINTMENT_END_DATE_REQUIRED = "Appointment end date is required";
	public final static String SERVICE_ADDRESS_REQUIRED = "Service address is required";
	public final static String TASKS_REQUIRED = "Tasks are required";
	public final static String NOTE_OBJ_REQUIRED = "Service order note object is null";
	public final static String SERVICE_OBJ_REQUIRED = "Service order object is null";
	public final static String STATEMENT_OF_WORK_TITLE_REQUIRED = "Statement of work title is required";
	public final static String STATEMENT_OF_WORK_DESCRIPTION_REQUIRED = "Statement of work description is required";
	public final static String PRIMARY_SKILL_CATEGORY_REQUIRED = "Primary Skill Category is required";
	public final static String TASKS_NOTES_REQUIRED = "Tasks notes is required";
	public final static String PROVIDER_SPECIAL_INSTRUCTIONS_REQUIRED = "Provider Special Instructions is required";
	public final static String BUYER_CUSTOM_FIELD_REQUIRED = "Buyer Custom Field is required";
	public final static String SERVICE_LOCATION_TYPE_REQUIRED = "Service Location Type is required";
	public final static String SERVICE_LOCATION_ADDRESS_REQUIRED = "Service Location Address is required";
	public final static String SERVICE_LOCATION_CITY_REQUIRED = "Service Location City is required";
	public final static String SERVICE_LOCATION_STATE_REQUIRED = "Service Location State is required";
	public final static String SERVICE_LOCATION_ZIP_REQUIRED = "Service Location Zip is required";
	public final static String CONTACT_NAME_REQUIRED = "Contact Name is required";
	public final static String CONTACT_HOME_PHONE_REQUIRED = "Contact Home Phone is required";
	public final static String CONTACT_WORK_PHONE_REQUIRED = "Contact Work Phone is required";
	public final static String CONTACT_MOBILE_PHONE_REQUIRED = "Contact Mobile Phone is required";
	public final static String CONTACT_FAX_REQUIRED = "Contact Fax is required";
	public final static String CONTACT_EMAIL_REQUIRED = "Contact Email is required";
	public final static String DATE_CREATED_REQUIRED = "Date Created is required";
	public final static String DATE_ROUTED_REQUIRED = "Date Routed is required";
	public final static String PRICE_TYPE_REQUIRED = "Price Types is required";
	public final static String INITIAL_PRICE_REQUIRED = "Initial Price is required";
	public final static String BUYER_CONTACT_ID_REQUIRED = "Buyer contact id is required";
	public final static String SERVICE_ORDER_MUST_BE_IN_DRAFT_STATE = "The Service order is not in a draft state, so it cannot be routed.";
	public final static String ROUTED_RESOUCES_NOT_SELECTED = "Please select atleast one provider to post the service order. ";
	public final static String PROVIDER_LIST_EMPTY = "The provider list is empty. Please provide at least one provider.";
	public final static String INVALID_SERVICE_ORDER = "The Service Order Id provided is not valid.";
	public final static String SERVICE_ORDER_SUCCESSFULLY_ROUTED = "The Service Order was successfully routed.";
	public final static String SERVICE_ORDER_SUCCESSFULLY_REROUTED = "The Service Order was successfully re-routed.";
	public final static String SERVICE_ORDER_VALIDATION_ERRORS_WARNINGS = "Errors Or warnings still exist. Please rectify them before posting Service Order";
	public final static String SERVICE_ORDER_RE_ROUTE_FAILURE = "Service Order Re-route failure.";
	public final static String GROUP_ORDER_SUCCESSFULLY_ROUTED = "The Gropued Order was successfully routed.";
	public final static String GROUP_ORDER_ALREADY_ACCEPTED = "The grouped Order has already been accepted.";
	public final static String RESCHEDULE_START_DATE_REQUIRED = "A start date is required. Please try again.";
	public final static String RESCHEDULE_END_DATE_REQUIRED = "An end date is required. Please try again.";
	public final static String RESCHEDULE_COMMENTS_REQUIRED = "Comments are required. Please try again.";
	public final static String RESCHEDULE_REASON_CODE_REQUIRED = "You must choose a reason for rescheduling this service visit";

	public static final String SERVICE_ORDER_REQUIRED = "A service order is required.";
	public static final String USER_NAME_REQUIRED = "User name is required.";
	public static final String SERVICEORDERDAO_BEAN = "serviceOrderDao";
	public static final String SPEND_LIMIT_LESSTHAN_INTIALPRICE = "Maximum Price should be greater than initial limit.";
	public static final String UNAPPROPRIATE_WFSTATE = "Can not increase the Maximum Price for this SO state.";

	public static final String LABOR_SPEND_LIMIT_LESSTHAN_INITIALPRICE = "Final Labor Price should not be greater than Maximum Price for Labor.";
	public static final String PARTS_SPEND_LIMIT_LESSTHAN_INITIALPRICE = "Final Materials Price should not be greater than Maximum Price for Materials.";

	// R12.0 Sprint5 Retail Price validation
	//SL-21811
	public static final String RETAIL_PRICE_MAX_VALIDATION = "Total retail price has exceeded the maximum limit of $ for this service order.";
	public static final String RETAIL_PRICE_ERROR = "RETAIL_PRICE_ERROR";

	// Cancel Service Order
	public final static String ACCEPTED_CANCELSO_FAILURE = "Failure in cancelling the service order. Please try again.";
	public final static String CANCEL_AMT_REQUIRED = "Enter Cancellation amount.";
	public final static String UNAPPROPRIATE_WFSTATE_CANCEL = "Service Order can not be cancelled in this state.";
	public final static String ACCEPTED_CANCELSO_SUCCESS = "Service Order cancelled successfully.";
	public final static String REQUEST_CANCELSO_SUCCESS = "Successfully sent Cancellation request to provider.";
	public final static String CANCEL_COMMENT_REQUIRED = "Enter Cancellation comment.";
	public final static String CANCELLATION_REQUEST_SUBSTATUS_FAILURE = "The substatus could not be updated.";
	public final static String CANCELSO_DELETE_SUCCESS = "Service order was successfully Deleted.";
	public final static String CANCELSO_VOID_SUCCESS = "Service order was successfully Voided.";
	public final static String CANCELSO_SUCCESS = "Service order was successfully Cancelled.";
	public final static String PENDING_CANCELSO_SUCCESS1 = "Your request to cancel the service order for $";
	public final static String PENDING_CANCELSO_SUCCESS2 = " was successful. This order is pending cancellation "
			+ "until the provider confirms the amount you submitted";
	public final static String CANCELSO_AMOUNT_SUCCESS = "The provider was paid the cancel amount of $";

	// Void Service Order
	public final static String VOIDSO_FAILURE = "Failure in voiding the service order. Please try again.";
	public final static String VOIDSO_SUCCESS = "Service Order voided successfully.";
	public final static String UNAPPROPRIATE_WFSTATE_VOID = "Service Order can not be voided in this state.";

	public final static String CANCELLED = "Cancelled";
	public final static String ROUTED = "routed";
	public final static String POSTED = "Posted";
	public final static String VOIDED = "Voided";
	public final static String DELETED = "Deleted";
	public final static String PROBLEM = "Problem";
	public final static String CLOSED = "Closed";

	public final static String SERVICE_ORDER_ID_REQUIRED = "Service Order ID is required";
	public final static String SERVICE_ORDER_ID_FORMAT_ERROR = "Service Order ID is not in correct format";
	public final static String SERVICE_ORDER_OBJ_NOT_FOUND = "Service Order can not retrive details of the service order id";
	public final static String SERVICE_ORDER_PROVIDER_DETAILS_NOT_FOUND = "The provider details on the service order are missing. Please contact ServiceLive support. ";
	public final static String SERVICE_ORDER_BUYER_DETAILS_NOT_FOUND = "The buyer details on the service order are missing. Please contact ServiceLive support. ";
	public final static String SERVICE_ORDER_WFSTATE_NOT_FOUND = "Cancel Service Order can not retrive work flow state";
	public final static String FULLFILLMENT_ENTRY_NOT_FOUND = "You cannot close this service order, please contact support for more details";
	public final static String SERVICE_ORDER_IN_PROBLEM_STATE = "Service order is in problem state. Can not cancel the service order. Please resolve";
	public final static String SERVICE_ORDER_IN_OTHER_STATE = "Can not cancel the service order";
	public final static String SERVICE_ORDER_ID_DELIMITER_ERROR = "The delimiter should be a single character";
	public final static String SERVICE_ORDER_NOT_SUFFICIENT_FUNDS_AVAILABLE = "Not enough funds available";
	public final static String SERVICE_ORDER_INSUFFICIENT_FUNDS_PROJECT = "Not enough project funding allocated to this service order";
	public final static String SERVICE_ORDER_NOT_IN_ACTIVE_STATE = "Service order is not in Active State";
	public final static String BUYER_IS_NOT_AUTHORIZED = "Buyer is not authorized to access the service order";
	public final static String BUYER_DOES_NOT_HAVE_ENOUGH_FUNDS = "Buyer does not have enough funds";
	public final static String SIMPLE_BUYER_DOES_NOT_HAVE_ENOUGH_FUNDS = "Not enough funds available to complete your Service Order transaction. You must go to ServiceLive Wallet and add funds.";
	public final static String BUYER_OVER_MAX_SPEND_LIMIT = "You have requested a $ amount that exceeds your Maximum Price";
	public final static String BUYER_SO_STATE_NOT_DRAFT = "Buyer service order status is not draft";
	public final static String BUYER_SO_AGREEMENT = "Buyer Agreement";
	public final static String PROVIDER_COMPLETE_NOT_AUTHORIZED = "Provider is not authorized to complete the service order";
	public final static String PROVIDER_IS_NOT_AUTHORIZED = "Not authorized to reject the order";
	public final static String UNAPPROPRIATE_WFSTATE_REJECT = "Service Order can not be rejected in this state.";
	public final static String WFSTATE_NOT_FOUND = "Service Order can not retrive work flow state";
	public final static String MISSING_INPUT = "Missing Service Order Details.";
	public final static String PROVIDER_ID_REQUIRED = "Provider Id is required";
	public final static String REJECT_SUCCESS = "Successfully rejected the service order.";
	public final static String REJECT_FAILURE = "Error in rejecting the service order.";
	public static final String ENTER_PBTYPE = "Enter Problem Type";
	public static final String ENTER_PBDESC = "Enter Problem Description";
	public final static String USER_IS_NOT_AUTHORIZED = "User is not authorized to access the service order";
	public final static String NEED_A_VALID_SERVICE_ORDER_ID = "Please enter a valid service order id";
	public final static String BUYER_UPFUND_LIMIT_PER_TRANSACTION_ERROR = "You have requested a transaction amount that exceeds your limit. Please contact your administrator for further action.";
	// to do email address set for webservice CancelSO in active status
	public final static String BODY_MESSAGE = "Your Service Order is being  canceled in ACTIVE STATE.Please VERIFY the Service Order and  Confirm";
	public final static String SUBJECT = "Confirm to Cancel Service Order";

	// Reschedule Service Order
	public final static String SERVICE_ORDER_WFSTATE_NOT_VALID_RESCHEDULING = "WFState is not valid to Reschedule the Service Order";
	public final static String SERVICE_ORDER_INVALID_RESCHEDULING_DATE = "Service Order rescheduled date is earlier than orginal scheduled date or in the past";
	public final static String SERVICE_ORDER_MISSING_RESCHEDULING_DATE = "Service Order rescheduled date is missing";

	// complete service order
	public final static String ALREADY_COMPLETED_REPORTED = "Service order is already in completed state";
	public final static String RESPONSE_CODE = "00";
	public final static String FILE_UPLOAD_STATUS = "fileUploadStatus";

	// Service Order Search
	public final static Integer SEARCH_BY_PHONE_NUMBER = 1;
	public final static Integer SEARCH_BY_ZIP_CODE = 2;
	public final static Integer SEARCH_BY_SO_ID = 3;
	public final static Integer SEARCH_BY_USER_NAME = 4;
	public final static Integer SEARCH_BY_TECHNICIAN_NAME = 6;
	public final static Integer SEARCH_BY_TECHNICIAN_ID = 5;
	public final static Integer SEARCH_BY_PB_FILTER_ID = 7;
	public final static Integer SEARCH_BY_ADDRESS = 9;
	public final static Integer SEARCH_ACTIVESO_BY_PROVIDER = 12;

	// Order Group Manager Search
	public final static String SEARCH_BY_PHONE_NO = "1";
	public final static String SEARCH_BY_ZIP_CD = "2";
	public final static String SEARCH_BY_ORDER_ID = "3";
	public final static String SEARCH_BY_USR_NAME = "4";
	public final static String SEARCH_BY_ADDR = "9";
	public static final String CUSTOM_REF_SEARCH_IDENTIFIER = "CR-";

	// Add Note
	public final static String MISSING_SOID = "Service Order Id is missing. Please close the browser and start again.";
	public final static String MISSING_SUBJECT = "Enter Note Subject.";
	public final static String MISSING_MESSAGE = "Enter Note Message.";
	//SLT-1166
	public final static String VALIDATION_SUBJECT = "Please don't enter the credit card number in Subject field.";
	public final static String VALIDATION_MESSAGE = "Please don't enter the credit card number in Message field.";
	
	public final static String SEARCH_BY_PHONE_NUMBER_VALUE_NOT_VALID = "Search by phone number value is not a number";
	public final static String SEARCH_BY_PHONE_NUMBER_LENGTH_NOT_VALID = "Search by phone number length should be 10";
	public final static String SEARCH_BY_ZIP_CODE_VALUE_NOT_VALID = "Search by zip code value is not a number";
	public final static String SEARCH_BY_ZIP_CODE_LENGTH_NOT_VALID = "Search by zip code length should be 5";
	public final static String SEARCH_BY_END_USER_NAME_VALUE_NOT_VALID = "Search by name value can only be alphabets and space";
	public final static String SEARCH_BY_TECHNICIAN_VALUE_NOT_VALID = "Search by Technician value is not a number";
	public final static String SEARCH_BY_PROVIDER_FIRM_ID_NOT_VALID = "Search by Provider firm ID is not a number";
	public final static String SEARCH_BY_TECHNICIAN_NAME_VALUE_NOT_VALID = "Search by name value can only be alphabets and space";
	public final static String SERVICE_ORDER_NOT_CLOSED = "Service Order is not closed";
	public final static String CLOSESO_SUCCESS = "Successfully closed the service order.";
	public final static String CLOSESO_FAILURE = "Error in closing the service order.";

	//SL-21117: Revenue Pull Code change starts
	public final static String REVENUE_PULL_ENTER_AMOUNT = "Please enter a valid amount.";
	public final static String REVENUE_PULL_ENTER_POSITIVE_AMOUNT = "Please enter a valid positive amount.";
	public final static String REVENUE_PULL_ENTER_NUMERIC_AMOUNT = "Please enter a valid numeric amount.";
	public final static String REVENUE_PULL_MAXIMUM_AMOUNT = "For a single request, at most $499999.00 amount can be processed.";
	public final static String REVENUE_PULL_MANDATORY_DATE = "Date must be selected.";

	public final static String REVENUE_PULL_VALID_DATE = "Please select a valid date greater than today.";
	public final static String REVENUE_PULL_REASON_COMMENT = "Please mention reason comment.";
	public final static String REVENUE_PULL_MAXIMUM_REASON_COMMENT = "Only 255 characters are allowed for reason comment.";

	public static final String REVENUE_PULL_ALERT_CC = "revenue_pull_alert_cc";
	//Code Change ends

	// R12_1
	// SL-20362
	public final static String PENDINGRESCHEDULE = "PENDINGRESCHEDULE";

	// Service Order Tabs for Buyer and Provider
	public final static String SEARCH_BY_VALUE_REQUIRED = "Search By value is required";
	public final static String FILTER_ID_IS_NOT_SET = "Filter id is not set";

	// FM history Tab
	public static final String FM_HISTORY_PAGING_CRITERIA_KEY = "FM_HISTORY_PAGING_CRITERIA_KEY";
	public static final String MAX_WALLET_HISTORY_EXPORT_LIMIT = "max_wallet_history_export_limit";
	public static final String MAX_SEARCH_DAYS_WALLET_HISTORTY = "Maximum_search_days_wallet_history";
	public static final String WALLET_PAGE_LOAD_DATE_RESTRICTION = "wallet_page_load_date_restriction";
	public static final int ACCOUNT_HISTORY_START_INDEX = 0;
	public static final String INVALID_SEARCH_TYPE = "InvalidDateRange";
	public static final String DATERANGE_SEARCH_TYPE = "DateRange";
	public static final String INTERVAL_SEARCH_TYPE = "Interval";

	// Service Order Tabs for Buyer
	public final static String TAB_ACTIVE = "ACTIVE";
	public final static String TAB_EXPIRED = "EXPIRED";
	public final static String TAB_COMPLETED = "COMPLETED";
	public final static String TAB_DRAFT = "DRAFT";
	public final static String TAB_ROUTED = "ROUTED";
	public final static String TAB_ACCEPTED = "ACCEPTED";
	public final static String TAB_PROBLEM = "PROBLEM";
	public final static String TAB_INACTIVE = "INACTIVE";
	public final static String TAB_CANCELLED = "CANCELLED";
	public final static String TAB_BID_REQUESTS = "BID REQUESTS";
	public final static String TAB_BULLETIN_BOARD = "BULLETIN BOARD";
	public final static String TAB_CLOSED = "CLOSED";
	public final static String TAB_VOIDED = "VOIDED";
	public final static String TAB_DELETED = "DELETED";
	public final static String TAB_RECEIVED = "RECEIVED";
	public final static String TAB_PENDING_CANCEL = "PENDING CANCEL";
	public static final String PROVIDER_ROLE = "PROVIDER";
	public static final String BUYER_ROLE = "BUYER";
	public static final String SIMPLE_BUYER_ROLE = "BUYER_SIMPLE";
	public static final String SYSTEM_ROLE = "SYSTEM";
	public static final String NEWCO_ADMIN_ROLE = "NEWCO_ADMIN";
	public static final String SERVICELIVE_ROLE = "SERVICELIVE";

	public static final String FILTER_CRITERIA_KEY = "FILTER_CRITERIA_KEY";
	public static final String SORT_CRITERIA_KEY = "SORT_CRITERIA_KEY";
	public static final String PAGING_CRITERIA_KEY = "PAGING_CRITERIA_KEY";
	public static final String ORDER_CRITERIA_KEY = "ORDER_CRITERIA_KEY";
	public static final String SEARCH_CRITERIA_KEY = "SEARCH_CRITERIA_KEY";
	public static final String SEARCH_WORDS_CRITERIA_KEY = "SEARCH_WORDS_CRITERIA_KEY";
	public static final String DISPLAY_TAB_CRITERIA_KEY = "DISPLAY_TAB_CRITERIA_KEY";

	public static final String ETM_FILTER_CRITERIA_KEY = "ETM_FILTER_CRITERIA_KEY";
	public static final String ETM_SORT_CRITERIA_KEY = "ETM_SORT_CRITERIA_KEY";
	public static final String ETM_PAGING_CRITERIA_KEY = "ETM_PAGING_CRITERIA_KEY";
	public static final String ETM_ORDER_CRITERIA_KEY = "ETM_ORDER_CRITERIA_KEY";
	public static final String ETM_SEARCH_CRITERIA_KEY = "ETM_SEARCH_CRITERIA_KEY";
	public static final String ETM_SEARCH_KEY = "ETM_SEARCH_KEY";
	public static final String ETM_FILTER_ZIP = "ETM_FILTER_ZIP_KEY";

	public static final String ETM_FILTERS_DISABLED = "filtersDisabled";

	public static final String SERVICE_ORDER_CRITERIA_KEY = "SERVICE_ORDER_CRITERIA_KEY";

	public static final String INCREASE_SPEND_LIMIT_VAL_REQUIRED = "Calculate increase in Maximum Price";
	public static final String ADDED_SPEND_LIMIT_VAL_REQUIRED = "Enter increase in Maximum Price";
	public static final String INCREASED_SPEND_LIMIT_COMMENT_REQUIRED = "Enter comment for increase in Maximum Price";
	public static final String SUCCESSFUL_SPEND_LIMIT_INCREASE = "Successfully increased the Maximum Price";
	public static final String SUCCESSFUL_SPEND_LIMIT_UPDATE = "Successfully updated the Maximum Price";

	public static final String SO_REJECT_REASONS = "SoRejectReasons";
	public static final String BUYER_OPERATION_NOT_PERMITTED = "This operation is not permitted by buyer.";
	public static final String PROVIDER_OPERATION_NOT_PERMITTED = "This operation is not permitted by provider";
	public static final String SELECT_REJECT_REASON_CODE = "Select reason code to reject";
	public static final String SUCCESSFUL_REJECT_SERVICE_ORDER = "Successfully rejected the service order";

	public static final String PROBLEM_ADD_SUCCESS = "Successfully reported the problem";
	public static final String RESOLUTION_ADD_SUCCESS = "Successfully added the resolution";
	public static final String PROBLEM_ADD_FAILURE = "Failed to report the problem";
	public static final String RESOLUTION_ADD_FAILURE = "Failed to add the resolution";
	public static final String UNAPPROPRIATE_WFSTATE_PROBLEM = "Unappropriate state for adding the problem";
	public static final String UNAPPROPRIATE_WFSTATE_RESOLUTION = "Unappropriate state for adding the resolution";
	public static final String ALREADY_PROBLEM_REPORTED = "The other party has already indicated a Problem on the service order";
	public static final String ENTER_RESCOMMENT = "Enter problem resolution";
	public static final String LAST_STATUS_NULL = "The service order was not in valid state";
	public static final String PROBLEM_ADDITIONAL_COMMENT = "Scope Change Initiated by Buyer. Please follow the steps sent through the Scope Change Email.";
	public static final String SPEND_LIMIT_CHANGE_SUBJECT = "Service Order Spend Limit Change";
	public static final String OMS_UPDATE = "OMS Update";
	public static final String DELIVERY_TASK_NAME = "Deliver Merchandise";
	// Provider Counter Offer
	public static final Integer ACCEPTED = 1;
	public static final String ACCEPTED_DESC = "Accepted";
	public static final Integer CONDITIONAL_OFFER = 2;
	public static final String CONDITIONAL_OFFER_DESC = "Counter Offer";
	public static final Integer REJECTED = 3;
	public static final String REJECTED_DESC = "Rejected";
	public static final Integer WITHDRAW_CONDITIONAL_OFFER = 4;
	public static final String WITHDRAW_CONDITIONAL_OFFER_DESC = "Withdraw Counter Offer";
	public static final Integer RELEASED = 5;
	public static final String RELEASED_DESC = "Released";
	public static final Integer EXPIRED = 6;

	public static final Integer REJECTED_REASON1 = 10;
	public static final Integer REJECTED_REASON2 = 1;
	// public static final Integer REJECTED_REASON3 = 2;
	public static final Integer REJECTED_REASON4 = 3;
	public static final Integer REJECTED_REASON5 = 4;
	public static final Integer REJECTED_REASON6 = 5;
	public static final Integer REJECTED_REASON7 = 15;
	public static final Integer REJECTED_REASON8 = 16;
	public static final Integer REJECTED_REASON9 = 6;

	public static final String REJECTED_REASON_DESC1 = "Capacity Conflict";
	public static final String REJECTED_REASON_DESC2 = "Spend limit too low";
	// public static final String REJECTED_REASON_DESC3 =
	// "Spend limit does not match scope";
	public static final String REJECTED_REASON_DESC4 = "Service scope is unclear";
	public static final String REJECTED_REASON_DESC5 = "Service location too far";
	public static final String REJECTED_REASON_DESC6 = "Schedule conflict ";
	public static final String REJECTED_REASON_DESC7 = "I do not offer this service";
	public static final String REJECTED_REASON_DESC8 = "I am not interested in this job";
	public static final String REJECTED_REASON_DESC9 = "Other";

	public static final Integer RESCHEDULE_SERVICE_DATE = 7;
	public static final String RESCHEDULE_SERVICE_DATE_DESC = "Reschedule Service Date";
	public static final Integer RESCHEDULE_SERVICE_DATE_AND_SPEND_LIMIT = 8;
	public static final String RESCHEDULE_SERVICE_DATE_AND_SPEND_LIMIT_DESC = "Service Date & Maximum Price";
	public static final Integer SPEND_LIMIT = 9;
	public static final String SPEND_LIMIT_DESC = "Maximum Price";
	public static final String PROPORTIONATE_LABOR_SPEND_LIMIT = "Proportionate Maximum Price for Labor";
	public static final String PROPORTIONATE_PARTS_SPEND_LIMIT = "Proportionate Maximum Price for Materials";
	public final static String RESOURCE_ID_REQUIRED = "Resource Id is required";
	public final static String VENDOR_ID_REQUIRED = "Vendor Id is required";
	public static final String SO_CONDITIONAL_OFFER_WFSTATE_ERROR = "Counter Offer can not be completed because it is already accepted";
	public static final String SO_CONDITIONAL_OFFER_CAN_NOT_COMPLETE = "Counter Offer can not be completed";
	public static final String SO_CONDITIONAL_START_DATE_OR_SPEND_LIMIT_REQUIRED = "Counter Offer Start Date or Maximum Price is required";
	public static final String SO_CONDITIONAL_START_TIME_REQUIRED = "Counter Offer Start Time is required";
	public static final String SO_CONDITIONAL_END_TIME_REQUIRED = "Counter Offer End Time is required";
	public static final String SO_CONDITIONAL_EXPIRATION_DATE_REQUIRED = "Counter Offer Expiration Date is required";
	public static final String SO_CONDITIONAL_START_DATE1_ERROR = "Counter Offer Start Date1 cannot be before the current date";
	public static final String SO_CONDITIONAL_EXPIRATION_DATE_ERROR = "Counter Offer Expiration Date cannot be before the current date";
	public static final String SO_CONDITIONAL_EXPIRATION_DATE_CAN_NOT_BE_AFTER_START_DATE = "Counter Offer Expiration Date can not be after the Counter Offer start date";
	public static final String SO_CONDITIONAL_OFFER_SUCCESS = "Counter Offer request has been sent";
	public static final Integer TWENTY_FOUR_HRS_IN_MILLI_SECONDS = 86400000;
	public static final String SO_CONDITIONAL_OFFER_UPDATE_NOT_POSSIBLE = "Counter Offer was not successful";
	public static final String SO_CONDITIONAL_OFFER_ALREADY_EXISTS = "Counter Offer already exists";
	public static final String SO_CONDITIONAL_OFFER_END_DATE_CAN_NOT_BE_AFTER_START_DATE = "Counter Offer end date can not be before the Counter Offer start date";
	public static final String SO_CONDITIONAL_OFFER_NEGATIVE_SPEND_LIMIT = "Counter Offer Maximum Price can not be negative";
	public static final String SO_BID_OFFER_CAN_NOT_COMPLETE = "Bid request cannot be completed";

	public static final String SO_CONDITIONAL_OFFER_PROVIDER_RESPONSE_ID = "providerRespId";
	public static final String UNDERSCORE_DELIMITER = "_";
	public static final String COMMA_DELIMITER = ",";
	public static final String DOLLAR_DELIMITER = "$";
	public static final String SO_COUNTER_OFFER_REASONS_STR = "checkedCounterOfferReasons";
	public static final String SO_COUNTER_OFFER_REASONS_NOT_AVAILABLE = "No matching Reasons List for the selected counter offer";

	public static final String SO_CONDITIONAL_OFFER_WITHDRAWN = "Counter Offer was successfully withdrawn";
	public static final String SO_CONDITIONAL_OFFER_NOT_WITHDRAWN = "Counter Offer could not be withdrawn";
	public static final String SO_CONDITIONAL_OFFER_ACCEPTED = "Counter Offer successfully accepted.";
	public static final String SO_CONDITIONAL_OFFER_ACCEPTED_TASK_LEVEL = "Counter Offer successfully accepted.";
	public static final String SO_CONDITIONAL_OFFER_NOT_ACCEPTED = "Counter Offer could not be accepted.";
	public static final String SO_MARKET_MAKER_COMMENTS_UPDATED = "Comments updated successfully";
	public static final String SO_MARKET_MAKER_CALL_STATUS_REQUIRED = "Please select a call status";
	public static final String SO_MARKET_MAKER_COMMENT_REQUIRED = "Please enter a comment";

	public static final String SOM_COUNT_FOR_BUYER = "SOM_COUNT_FOR_BUYER";
	public static final String SOM_COUNT_FOR_BUYER_QUERY = "som.queryCountsByStatusForBuyer";
	public static final String SOM_COUNT_FOR_PROVIDER = "SOM_COUNT_FOR_BUYER";
	public static final String SOM_COUNT_FOR_PROVIDER_QUERY = "som.queryCountsByStatusForProvider";

	public static final String LABORFINALPRICE_CANNOT_BE_MORE_THAN_LABORSPENDLIMIT = " Final Labor price cannot be more than Maximum Price for Labor";
	public static final String PARTSFINALPRICE_CANNOT_BE_MORE_THAN_PARTSSPENDLIMIT = " Final Parts price cannot be more than Maximum Price for Materials";
	public static final String LABORFINALPRICE_CANNOT_BE_LESS_THAN_ZERO = " Final Labor price cannot be less than zero";
	public static final String PARTSFINALPRICE_CANNOT_BE_LESS_THAN_ZERO = " Final Parts price cannot be less than zero";
	public static final String PARTSFINALPRICE_ERROR_IN_FORMAT = " Final Parts price  cannot be empty and should be of the following format [xx.xx]";
	public static final String LABORFINALPRICE_ERROR_IN_FORMAT = " Final Labor price cannot be empty and should be of the following format [xx.xx]";
	public static final String PARTSFINALPRICE_CANNOT_ENTER = "Can not enter final parts price as service order do not have any parts";
	public static final String RETURN_TRACKING_NO_MISSING = "Enter return tracking number for part ";
	public static final String RETURN_CARRIER_ID_MISSING = "Enter return carrier id for part ";
	public static final String FM_TAB_ERROR = "error";
	public static final String SOW_TAB_ERROR = "error";
	public static final String SOW_TAB_WARNING = "warning";
	public static final String SOW_TAB_COMPLETE = "complete";

	public static final String ORDER_GROUP_WIZARD_KEY = "ORDER_GROUP_WIZARD_KEY";
	public static final String SOW_ACTION_TYPE = "actionType";
	public static final String SERVICE_ORDER_WIZARD_KEY = "SERVICE_ORDER_WIZARD_KEY";
	public static final String SIMPLE_SERVICE_ORDER_WIZARD_KEY = "simple_service_order_key";
	public static final String TAB_SEQUENCE_INFO_KEY = "TAB_SEQUENCE_INFO_KEY";
	public static final String SOW_SOW_TAB = "sow";
	public static final String SOW_ADDITIONAL_INFO_TAB = "addnlInfo";
	public static final String SOW_PARTS_TAB = "parts";
	public static final String SOW_PROVIDERS_TAB = "providers";
	public static final String SOW_PRICING_TAB = "pricing";
	public static final String SOW_REVIEW_TAB = "review";
	public static final String SOW_TAB_INACTIVE = "sow_tab_inactive";
	public static final String SOW_TAB_ACTIVE = "sow_tab_active";
	public static final String SOW_TAB_ENABLED = "sow_tab_enabled";
	public static final String SOW_NAVIGATION_OBJ_KEY = "sow_navigation_obj_key";
	public static final String SERVICE_ORDER_WIZARD_INDICTATOR = "Service Order Wizard is active.";
	public static final String SOW_SO_ID_LIST = "SOW_SO_ID_LIST";

	public static final String SOW_SAVE_AS_DRAFT_ACTION = "SOW_SAVE_DRAFT";
	public static final String SOW_PREVIOUS_ACTION = "SOW_PREVIOUS_ACTION";
	public static final String SOW_NEXT_ACTION = "SOW_NEXT_ACTION";
	public static final String SOW_TAB_CLICK_ACTION = "SOW_TAB_CLICK_ACTION";
	public static final String SOW_POST_SO_ACTION = "SOW_POST_SO_ACTION";
	public static final String SOW_CANCEL_ACTION = "SOW_CANCEL_ACTION";

	public static final String SOW_SOW_BUYER_PROVIDES_PART = "1";
	public static final String SOW_SOW_PROVIDER_PROVIDES_PART = "2";
	public static final String SOW_SOW_PARTS_NOT_REQUIRED = "3";

	public static final String ENGLISH = "1";
	public static final String METRIC = "2";
	public static final String STANDARD_ENGLISH = "U.S. (in./lbs)";
	public static final String STANDARD_METRIC = "Metric (m/kg)";

	public static final String SERVICELIVESESSIONTIMEOUT = "sessiontimeout";
	public static final long SERVICELIVESESSIONTIMEINTERVAL = 1800000;

	public static final String DB_PERSIST = "DB";
	public static final String SESSION_PERSIST = "SESSION";

	public static final String SOW_EDIT_MODE = "sow_edit_mode";
	public static final String SOW_CREATE_MODE = "sow_create_mode";

	public static final String SO_ID = "SERVICE_ORDER_ID";
	public static final String EDITED_SO_ID = "EDITED_SO_ID";

	// Constants for Service Order Wizard Edit mode
	public static final int SO_VIEW_MODE_FLAG = 0;
	public static final int SO_EDIT_MODE_FLAG = 1;
	public static final String SOW_REVIEW_TAB_SUCCESS = "success";
	public static final String SOW_REVIEW_TAB_FAILED = "failed";
	// Constants for Agree and Disagree Terms and Conditions
	public static final String SOW_REVIEW_DONT_ACCEPT_TERMS_AND_CONDITIONS = "0";
	public static final String SOW_REVIEW_ACCEPT_TERMS_AND_CONDITIONS = "1";

	public static final String SOW_REVIEW_SAVE_SYSTEM_ERROR = "Your information couldn't be saved due to system error. ";
	public static final String SOW_REVIEW_REROUTE_SYSTEM_ERROR = "We couldn't route the service order due to system error. ";
	public static final String SOW_REVIEW_REROUTE_BUSINESS_ERROR = "We couldn't route the service order due to the following reasons: ";
	public static final String PROVIDER_CONFIRM_SERVICE_TIME_YES = "Provider will confirm the appointment with Service location Contact(s)";
	public static final String PROVIDER_CONFIRM_SERVICE_TIME_NO = "Do NOT confirm appointment with the  Service location Contact";
	public static final String SOW_STARTPOINT_SOM = "serviceOrderMonitor";
	public static final String SOW_STARTPOINT_DASHBOARD = "dashboardAction";
	public static final String SOW_EXIT_SAVE_AS_DRAFT = "SOW_EXIT_SAVE_AS_DRAFT";
	public static final String WORKFLOW_STARTINGPOINT = "gl_power_buyer_success";
	public static final String SOW_GO_TO_REVIEW_ACTION = "SOW_GO_TO_REVIEW_ACTION";
	public static final String SO_ACCEPT_ERROR = "soAcceptError";
	public static final int PROVIDER_CONFIRM_SERVICE_TIME = 1;

	// release so
	public static final Integer RELEASE_SO_REASON_CODE = 5;

	// Complete SO
	public final static String RESOLUTION_DESCR_REQUIRED = "Enter Resolution Description";
	public final static String RESOLUTION_DESCR_REQUIRED_OfficeMaxBuyer = "Enter Resolution Description more than 25 characters";
	public final static String RESOLUTION_COMMENTS_CREDITCARD_VALIDATION_MSG="Please don't enter the credit card number in Resolution Comments";
	public final static String RESOLUTION_DESCR_CREDITCARD_VALIDATION_MSG="Please do not provide the credit card number in resolutionDesc tag";
	public final static String SELECT_REASON_REQUIRED_LABOR = "Please Select a reason for labor price change";
	public final static String SELECT_REASON_REQUIRED_PARTS = "Please Select a reason for parts price change";
	public final static String SELECT_REASON_REQUIRED_LABOR_TEXT = "Please Enter other reason for labor price change";
	public final static String SELECT_REASON_REQUIRED_PARTS_TEXT = "Please Enter other reason for parts price change";

	public final static String PARTS_FINAL_PRICE_REQUIRED = "Enter parts final price";
	public final static String LABOR_FINAL_PRICE_REQUIRED = "Enter labor final price";
	public static final String COMPLETESO_SUCCESS = "Successfully completed the service order for payment";
	public static final String AUTOCLOSE_SO_SUCCESS = "This order was successfully closed and payment has been made";
	public static final String CANCELLED_SO_SUCCESS = "This order was successfully completed. The order has been cancelled.";
	public static final String AUTOCLOSE_COMPLETESO_SUCCESS = "You have successfully completed the order. The buyer will review this order for payment processing.";
	public static final String COMPLETESO_FAILURE = "Failed to complete the service order for payment";

	public static final String PENDINGCANCEL_FAILURE = "There was a problem with your request. Please try again or contact ServiceLive Support.";
	public static final String PENDINGRESPONSE_SUCESS = "Your request was successfully saved. Your service order is now pending response and needs to be confirmed by the provider.";
	public static final String PENDINGREVIEW_SUCESS = "Your request was successfully saved. Your service order is now pending review and needs to be confirmed by the buyer.";
	public static final String PENDINGCANCEL_SUCESS = "Your request was successfully saved.";

	public static final String INAPPROPRIATE_WFSTATE_COMPLETE = "Can not complete the SO in this state.";
	public static final String SIGNED_CUSTOMER_COPY_REQUIRED = "Please attach all required documents to complete the order. Required <br>Documents: Signed Customer Copy Including Waiver of Lien.";
	public static final String DOCUMENTS_MANDATORY = "Please attach all required documents to complete the order. Required <br>documents:";
	public static final String PROOF_OF_PERMIT_REQUIRED = "Please attach all required documents to complete the order. Required <br>documents: Proof of Permit.";
	public static final String BOTH_PROOF_OF_PERMIT_AND_SIGNED_CUSTOMER_COPY_REQUIRED_REQUIRED = "Please attach all required documents to complete the order. Required <br>Documents: Signed Customer Copy Including Waiver of Lien and Proof of Permit.";
	public static final String TIMEONSITE_ARRIVAL_DATE_REQUIRED = "Time Onsite arrival date is required to complete the order";
	public static final String TIMEONSITE_DEPARTURE_DATE_REQUIRED = "Time Onsite departure date is required to complete the order";
	public static final String TIMEONSITE_ARRIVAL_AND_DEPARTURE_DATE_REQUIRED = "Time Onsite arrival and departure dates are required to complete the order";
	public static final String ASSIGN_PROVIDER = "Please select provider";

	// so create createEntry scopeofworkaction
	public static final String STATES_MAP = "statesMap";
	public static final String PHONE_TYPES = "phoneTypes";
	public static final String MAIN_SERVC_CAT_NAMES_MAP = "mainServiceCategoryNamesMap";
	public static final String MAIN_SERVC_CAT = "mainServiceCategory";
	public static final String SKILL_SELCTN_MAP = "skillSelectionMap";
	public static final String CATEGORY_SELCTN = "categorySelection";
	public static final String SKILL_SELCTN = "skillSelection";

	// Constants for validate Schedule
	public final static String VALID_DATE = "valid_date";
	public final static String INVALID_DATE = "invalid_date";
	public final static String INVALID_FORMAT = "invalid_format";
	public static final String SUCESSFULLY_DELETED_DRAFT = "Draft is sucessfully deleted";
	public static final String DELETE_DRAFT_FAILURE = "Draft could not be deleted";

	// Start Finance Manager
	public final static String FM_OVERVIEW_TAB = "FM_OVERVIEW_TAB";
	public final static String FM_FINANCIAL_PROFILE_TAB = "FM_FINANCIAL_PROFILE_TAB";
	public final static String FM_MANAGE_ACCOUNTS_TAB = "FM_MANAGE_ACCOUNTS_TAB";
	public final static String FM_MANAGE_FUNDS_TAB = "FM_MANAGE_FUNDS_TAB";

	//SL-21117: Revenue Pull code change starts
	public final static String FM_REVENUE_PULL_TAB = "FM_REVENUE_PULL_TAB";
	//Code change ends

	public final static String FM_HISTORY_TAB = "FM_HISTORY_TAB";
	public final static String FM_REPORTS_TAB = "FM_REPORTS_TAB";
	public final static String FM_ERROR = "error";

	public final static String FM_OVERVIEW = "Overview";
	public final static String FM_HISTORY = "History";
	public static final String FM_FINANCIAL_PROFILE = "Financial Profile";
	public static final String FM_MANAGE_ACCOUNTS = "Manage Accounts";
	public static final String FM_MANAGE_FUNDS = "Manage Funds";

	//SL-21117: Revenue Pull code change starts
	public static final String FM_REVENUE_PULL = "Revenue Pull";
	//Code change ends

	public final static String FM_SEARCH = "Search";
	public final static String FM_REPORTS = "Reports";
	// End Finance Manager

	// GMT conversion
	public final static String GMT_TIME = "time";
	public final static String GMT_DATE = "date";
	public final static String GMT_ZONE = "GMT";
	public final static String EST_ZONE = "EST";
	public final static String CST_ZONE = "CST";
	public final static String DEFAULT_ZONE = "EST";
	public final static String SERVICELIVE_ZONE = "CST";
	public final static String UTC_ZONE = "UTC";
	public final static String TIME_ZONE = "TIME_ZONE";

	public static final String DOC_PROCESSING_ERROR_RC = "100";
	public static final String DOC_UPLOAD_ERROR_RC = "110";
	public static final String DOC_DELETE_ERROR_RC = "120";
	public static final String DOC_RETRIEVAL_ERROR_RC = "130";
	public static final String DOC_USER_AUTH_ERROR_RC = "140";
	public static final String SO_DOC_NOT_IN_ALLOWED_STATE_ERROR_RC = "150";
	public static final String SO_DOC_SIZE_EXCEEDED_RC = "160";
	public static final String SO_DOC_UPLOAD_EXSITS = "170";
	public static final String SO_DOC_INVALID_FORMAT = "180";
	public static final String SO_DOC_WFSTATE_CLOSED_DELETE = "190";
	public static final String SO_DOC_WFSTATE_CLOSED_INSERT = "200";
	public static final String LOGO_DOC_INVALID_FORMAT = "230";
	public static final String SO_DOC_INVALID_FORMAT_SEARS_BUYER = "260";
	public static final String DOC_CATEGORY_REQUIRED_SEARS_BUYER = "270";
	public static final int ORDER_CLOSED = 180;
	public static final int ORDER_CANCELELD = 120;
	public static final int ORDER_PENDINGCANCEL = 165;
	public static final int ORDER_PENDINGREVIEW = 68;
	public static final int ORDER_PENDINGESPONSE = 69;

	public static final String NOT_APPLICABLE = "N/A";

	// SOM Sorting
	public static final String SORT_COLUMN_KEY = "sort_column_key";
	public static final String SORT_ORDER_KEY = "sort_order_key";

	public final static String SORT_ORDER_ASC = "ASC";
	public final static String SORT_ORDER_DESC = "DESC";

	public final static String SORT_COLUMN_SOM_SERVICEDATE_TZ = "CONVERT_TZ(DATE_ADD(s.service_date1, INTERVAL STR_TO_DATE(s.service_time_start,'%l:%i %p') HOUR_SECOND), 'GMT', s.service_locn_time_zone),s.so_id";
	public final static String SORT_COLUMN_SOM_SERVICEDATE = "s.service_date1";
	public final static String SORT_COLUMN_SOM_SERVICETIME = "lut.full_time";
	public final static String SORT_COLUMN_SOM_STATUS = "wf_alias.sort_order";
	public final static String SORT_COLUMN_SUB_STATUS = "lu_wf_substatus.sort_order";
	public final static String SORT_COLUMN_SOM_SOID = "s.so_id";
	public final static String SORT_COLUMN_SOM_SO_GRP_ID = "sortSOandGroupID";
	public final static String SORT_COLUMN_SOM_SPENDLIMIT = "s.spend_limit_labor";
	public final static String SORT_COLUMN_SOM_SO_GRP_SPENDLIMIT = "sortSOandGroupSpendLimit";
	public final static String SORT_COLUMN_SOM_CREATEDDATE = "s.created_date";
	public final static String SORT_COLUMN_SOM_ROUTED_RESOURCE_LAST_NAME = "vcontact.last_name";
	public final static String SORT_COLUMN_SOM_CITY = "loc.city";
	public final static String SORT_COLUMN_SOM_SPEND_LIMIT_TOTAL = "s.spend_limit_labor";

	public final static String SORT_COLUMN_MS_MEMBERID = "vw.vendor_id";
	public final static String SORT_COLUMN_MS_COMPANYTYPE = "vw.business_name";
	public final static String SORT_COLUMN_MS_STATUS = "vw.vendor_status";
	public final static String SORT_COLUMN_MS_STATE = "vw.vendor_state";
	public final static String SORT_COLUMN_MS_ZIP = "vw.vendor_zip";
	public final static String SORT_COLUMN_BUSINESS_NAME = "business_name";
	public final static String SORT_COLUMN_BUSINESS_STATE = "state";
	public final static String SORT_COLUMN_BUSINESS_ZIP = "zip";
	public final static String SORT_COLUMN_AS_PRIMARYINDUSTRY = "primaryIndustry";
	public final static String SORT_COLUMN_AS_MARKET = "marketName";
	public final static String SORT_COLUMN_AS_LASTACTIVITY = "lastActivityDate";
	public final static String NULL_RESOURCE_ID = "0";
	// ADmin Buyer Search Sort : rest of the common name used from above
	public final static String SORT_COLUMN_FUNDING_TYPE = "fundingType";
	public final static String SORT_COLUMN_BUYER_ID = "buyer_id";
	public final static String SORT_COLUMN_BUYER_COMPANYNAME = "businessName";
	// End buyer
	public final static String SORT_COLUMN_AS_ID = "vpp.buyer_id";
	public final static String SORT_COLUMN_AS_BUSNAME = "vpp.business_name";
	public final static String SORT_COLUMN_AS_USERNAME = "vpp.user_name";
	public final static String SORT_COLUMN_AS_NAME = "name";
	public final static String SORT_COLUMN_AS_PHONE = "phone";

	public final static String SORT_ETM_COLUMN_AS_PROVIDERNAME = "firstName";
	public final static String SORT_ETM_COLUMN_AS_TOTAL_ORDERS = "totalOrdersCompleted";
	public final static String SORT_ETM_COLUMN_AS_DISTANCE = "distance";
	public final static String SORT_ETM_COLUMN_AS_SL_RATINGS = "ratingScore";
	public final static Integer ETM_MARKET_READY_INDICATOR = 1;

	public final static String SORT_COLUMN_PB_SERVICEDATE = "appointStartDate";
	public final static String SORT_COLUMN_PB_STATUS = "";
	public final static String SORT_COLUMN_PB_SOID = "soId";

	public final static String NULL_STRING = "null";
	public final static String DASH_WITH_SPACES = " - ";

	public static final String TERMS_CONDITIONS_BUYER = "Buyer Agreement";
	public static final String TERMS_CONDITIONS_BUYER_AGREEMENT = "Buyer Agreement";
	public static final String SERVICE_BUCKS = "ServiceLive Bucks";

	public final static String METHOD_REROUTE_SO = "processReRouteSO";
	public final static String METHOD_ROUTE_SO = "processRouteSO";
	public final static String METHOD_UPDATE_SPEND_LIMIT = "updateSOSpendLimit";
	public final static String METHOD_RELEASE_SO = "releaseSOProviderAlert";
	public final static String METHOD_RELEASE_SO_IN_ACTIVE = "releaseServiceOrderInActive";
	public final static String METHOD_ADD_NOTE = "processAddNote";
	public final static String METHOD_PROCESS_SUPPORT_ADD_NOTE = "processSupportAddNote";
	public final static String METHOD_ACCEPT_SERVICE_ORDER = "processAcceptServiceOrder";
	public final static String METHOD_REASSIGN_SERVICE_ORDER = "saveReassignSO";
	public final static String METHOD_ACCEPT_CONDITIONAL_SERVICE_ORDER = "acceptConditionalOffer";
	public final static String METHOD_COMPLETE_SERVICE_ORDER = "processCompleteSO";
	// public final static String METHOD_EXPIRED_SO = "expirePostedSO";
	public final static String METHOD_TRANSFER_SLBUCKS = "transferSLBucks";
	public final static String METHOD_TRANSFER_SLBUCKS_CREDIT = "transferSLBucksCredit";
	public final static String METHOD_TRANSFER_SLBUCKS_DEBIT = "transferSLBucksDebit";

	public final static String METHOD_DEPOSIT_FUNDS = "depositfunds";
	public final static String METHOD_REPORT_A_PROBLEM = "reportProblem";
	public final static String METHOD_ISSUE_RESOLUTION = "reportResolution";
	public final static String METHOD_ACCEPT_SERVICEORDER = "processAcceptServiceOrder";
	public final static String METHOD_REJECT_SERVICEORDER = "rejectServiceOrder";
	public final static String METHOD_SEND_ALL_PROVIDER_REJECT_ALERT = "sendAllProviderRejectAlert";
	public final static String METHOD_RESCHEDULE_REQUEST = "rescheduleSOComments";
	public final static String METHOD_CANCEL_RESCHEDULE_REQUEST = "cancelRescheduleRequest";
	public static final String METHOD_SAVE_SIMPLE_BUYER_REG = "saveSimpleBuyerRegistration";
	public static final String METHOD_SAVE_PROF_BUYER_REG = "saveProfBuyerRegistration";
	public static final String METHOD_SAVE_REGISTRATION = "saveRegistration";

	public static final String METHOD_PROCESS_CREATE_CONDITIONALOFFER = "processCreateConditionalOffer";

	public static final String METHOD_PROCESS_CREATE_BID = "processCreateBid";
	public final static String METHOD_PROCESS_CLOSE_SO = "processCloseSO";
	public final static String METHOD_REQUEST_RESCHEDULE_SO = "requestRescheduleSO";

	// NEW OMS METHODS
	public final static String METHOD_PROCESS_UPDATE_SPEND_LIMIT_FOR_WS = "processUpdateSpendLimitforWS";
	public final static String METHOD_PROCESS_REROUTE_SO_FOR_WS = "processReRouteSOForWS";
	public final static String METHOD_PROCESS_UPDATE_TASK = "processUpdateTask";
	public final static String METHOD_PROCESS_CHANGE_OF_SCOPE = "processChangeOfScope";
	public final static String METHOD_PROCESS_CANCEL_SO_IN_ACCEPTED_FOR_WS = "processCancelSOInAcceptedForWS";
	public final static String METHOD_PROCESS_CANCEL_SO_IN_ACTIVE_FOR_WS = "processCancelSOInActiveForWS";
	public final static String METHOD_DELETE_DRAFT_SO = "deleteDraftSO";

	public final static String RANGE_DATE = "2";
	public final static String FIXED_DATE = "1";
	public final static String PREFERENCES = "3";

	// Constants for Phone Class types
	public static final String PHONE_CLASS_HOME = "0";
	public static final String PHONE_CLASS_WORK = "1";
	public static final String PHONE_CLASS_MOBILE = "2";
	public static final String PHONE_CLASS_PAGER = "4";
	public static final String PHONE_CLASS_OTHER = "5";
	public static final String PHONE_CLASS_FAX = "6";

	// Constants for Phone types
	public static final String PHONE_TYPE_PRIMARY = "1";
	public static final String PHONE_TYPE_ALTERNATE = "2";
	public static final String PHONE_TYPE_FAX = "3";

	public static final String FLAG_YES = "Y";
	public static final String FLAG_NO = "N";

	// Constants for field length limit values for fields in Summary tab on
	// SOD/SOW
	public static final Integer SUMMARY_TAB_GENERAL_INFO_OVERVIEW_LENGTH = 5000;
	public static final Integer SUMMARY_TAB_GENERAL_INFO_BUYER_TERM_COND_LENGTH = 5000;
	public static final Integer SUMMARY_TAB_GENERAL_INFO_SPECIAL_INSTRUCTION_LENGTH = 5000;
	public static final Integer SUMMARY_TAB_SCOPE_OF_WORK_TASK_COMMENTS_LENGTH = 5000;

	// SL-20728
	public static final Integer SUMMARY_TAB_GENERAL_INFO_OVERVIEW_LENGTH_RTF = 10000;
	public static final Integer SUMMARY_TAB_GENERAL_INFO_BUYER_TERM_COND_LENGTH_RTF = 10000;
	public static final Integer SUMMARY_TAB_GENERAL_INFO_SPECIAL_INSTRUCTION_LENGTH_RTF = 10000;
	public static final Integer SUMMARY_TAB_SCOPE_OF_WORK_TASK_COMMENTS_LENGTH_RTF = 10000;

	public static final String RADIO_ON = "on";

	// On SOD(Summary) and SOW(Review) use this constant to determine whether or
	// not the data
	// entered for Overview/Special Instructions/Buyer's Terms and
	// Conditions/Task Comments
	// should be shown in a DIV with scrollbar. This is to show large data in
	// limited area with scrollbar.
	public static final Integer MAX_CHARACTERS_WITHOUT_SCROLLBAR = 1575;

	// Constant to set the Lifetime Rating number in session in the LoginAction
	// for the common header file
	public static final String LIFETIME_RATINGS_STAR_NUMBER = "lifetimeRatingStarsNumber";
	public static final String LIFETIME_RATINGS_VO = "lifetimeRatingVO";

	public static final Double MIN_LABOR_SPEND_LIMIT = 25.00;

	// Notes and Support Notes can have maximum text of 750 char in Message and
	// 30 char in Subject
	public static final Integer SO_NOTE_MESSAGE_MAX_LENGTH = 750;
	public static final Integer SO_NOTE_SUBJECT_MAX_LENGTH = 30;
	public static final Integer SO_NOTE_SUPPORT_TYPE = 1;
	public static final Integer SO_NOTE_GENERAL_TYPE = 2;
	public static final String SO_NOTE_PUBLIC_ACCESS = "0";
	public static final String SO_NOTE_PRIVATE_ACCESS = "1";

	// Tab Icons
	public static String ICON_NON = "NOIcon";
	public static String ICON_ERROR = "error";
	public static String ICON_ERROR_ON = "errorOn";
	public static String ICON_INCOMPLETE = "incomplete";
	public static String ICON_INCOMPLETE_ON = "incompleteOn";
	public static String ICON_COMPLETE = "complete";
	public static String ICON_COMPLETE_ON = "completeOn";

	// Constants for Manage Accounts in Financial Manager

	public static Integer COUNTRY_ID_US = 1;
	public static Integer ACCOUNT_STATUS_ACTIVE = 1;

	public static final String FIRST_NAME_REQUIRED = "Enter First Name";
	public static final String LAST_NAME_REQUIRED = "Enter Last Name";
	public static final String EMAIL_REQUIRED = "Enter Email Address";
	public static final String CATEGORY_REQUIRED = "Enter Category";
	public static final String SUBJECT_REQUIRED = "Enter Subject";
	public static final String COMMENT_REQUIRED = "Enter Comment";
	public static final String VALID_COMMENT = "Enter Valid Comments";
	public static final String VALID_EMAIL_ADDRESS = "Enter Valid Email Address";

	public static final String EDIT_COMPLETIONRECORD_SUCCESS = "Completion Record can be updated now.";
	public static final String EDIT_COMPLETIONRECORD_FAILURE = "Failed to set the Service Order in active state, so can not update the completion record.";
	public static final String EDIT_COMPLETIONRECORD_FAILURE_ALREADY_COMPLETED = "SericeOrder Closed, Completion Record cannot be edited.";

	public static final String TRUE = "true";
	public static final String FALSE = "false";
	public final static int ENABLED_IND = 1;
	public final static int DISABLED_IND = 0;
	public final static int FUNDING_TYPE_ID_TRUE = 40;
	public final static int FUNDING_TYPE_ID_FALSE = 90;

	public static final int ACCOUNT_HISTORY_LIMIT_COUNT = 100;

	/**
	 * Added for BackGround Check Email status. bnatara
	 */
	public static final int TEAM_BACKGROUND_NOT_STARTED = 7;
	public static final int TEAM_BACKGROUND_IN_PROCESS = 8;
	public static final int TEAM_BACKGROUND_CLEAR = 9;
	public static final int TEAM_BACKGROUND_ADVERSE_FINDINGS = 10;
	public static final int TEAM_BACKGROUND_PENDING_SUBMISSION = 28;
	public static final int TEAM_CREDENTIAL_APPROVED = 12;
	public static final int TEAM_CREDENTIAL_REVIEWED = 200;

	public static final String TEAM_BCKGRND_NOT_STARTED = "Not Started";
	public static final String TEAM_BCKGRND_IN_PROCESS = "In Process";
	public static final String TEAM_BCKGRND_CLEAR = "Clear";
	public static final String TEAM_BCKGRND_ADVERSE_FINDINGS = "Not Cleared";
	public static final String TEAM_BCKGRND_PENDING_SUBMISSION = "Pending Submission";

	public static final String ENTER_SERVICE_CATEGORY = "Enter Service category";
	public static final String ENTER_ZIP = "Enter zip code";
	public static final String ENTER_FIVE_DIGITS_ZIP = "Please enter a valid 5-digit Zip code";
	public static final String ENTER_ETM_FILTER = "Select at least one filter";
	public static final String ENTER_PROVIDER_SEARCH_FILTER = "Select at least one filter";

	public static final String FM_BUTTON_MODE = "buttonMode";
	public static final String FM_CARD_BUTTON_MODE = "cardbuttonMode";
	public static final String FM_EDIT_BUTTON_MODE = "Edit Mode";
	public static final String FM_ESCHEATMENT_BUTTON_MODE = "escheatmentButtonMode";
	public static final String FM_CANCEL_BUTTON_MODE = "Cancel Mode";
	public static final String FM_ACCOUNT_EDIT_BUTTON_CLICKED = "Edit bank account";

	public static final String FM_CREDIT_CARD_EDIT_BUTTON_CLICKED = "Edit Credit Card";

	public static final String AM = "AM";
	public static final String PM = "PM";

	public static final String SOD_TAB_ERROR = "error";
	public static final int TIME_ON_SITE_COMPLETED_STATUS = 1;

	public static final double NO_SPEND_LIMIT = 0.00;
	public static final String SUPER_ADMIN_ROLE_NAME = "Super Admin";

	// Constants for OMS
	public static final Integer FOLLOWUP_SUBSTATUS = Integer.valueOf(38);
	public static final Integer RESEARCH_SUBSTATUS = Integer.valueOf(39);
	public static final Integer VALIDATE_POS_CANCELLATION = Integer.valueOf(59);
	public static final Integer TIME_WINDOW_SUBSTATUS = Integer.valueOf(40);
	public static final String CUSTOM_REF_DATE_CALCULATION_METHOD = "DATE CALCULATION METHOD";
	public static final String CUSTOM_REF_UNIT_NUM = "UNIT NUMBER";
	public static final String CUSTOM_REF_ORDER_NUM = "ORDER NUMBER";
	public static final String KEY_PROMISED_DATE = "P";
	public static final String KEY_DELIVERY_DATE = "D";
	public static final String KEY_NEXT_DATE = "N";
	public static final String KEY_CURRENT_DATE = "C";
	public static final String SIMPLE_BUYER_FIRST_SO_LOGGED_IN = "SIMPLE_BUYER_FIRST_SO_LOGGED_IN";
	public static final Object TAB_CURRENT = "Current";
	public static final String ORDER_GROUP_NO_PREFIX = "GSO-";
	public static final String BUYER_FEATURE_ORDER_GROUP = "ORDER_GROUPING";
	public static final String BUYER_FEATURE_CONDITIONAL_ROUTE = "CONDITIONAL_ROUTE";
	public static final String GROUP_NOTES_PREFIX = "ORDERSET - ";
	public static final String NEXT_DAY_TASK_COMMENTS = "Next Day 01000 ";
	public static final String INCREASED = "increased";
	public static final String DECREASED = "decreased";
	public static final String PRICE_DECREASE = "PRICE_DECREASE";
	public static final String PRICE_INCREASE = "PRICE_INCREASE";
	public static final String DELIVERY_TASK_ADDED = "DELIVERY_TASK_ADDED";
	public static final String JOBCODE_ADDED = "JOBCODE_ADDED";
	public static final String JOBCODE_DELETED = "JOBCODE_DELETED";

	// OMS SO Route
	public static final Integer SO_ROUTE_CRITERIA_DIST = Integer.valueOf(40);
	// background check
	public static final Integer SO_ROUTE_CRITERIA_BC = Integer.valueOf(1);
	// custom ref Template Name
	public final static String CUSTOM_REF_TEMPLATE_NAME = "TEMPLATE NAME";
	// custom ref Incident Id
	public final static String INCIDENT_REFERNECE_KEY = "INCIDENTID";
	public final static String SL_INCIDENT_REFERNECE_KEY = "SERVICELIVE INCIDENT ID";
	public final static String CUSTOM_REF_CLASS_CODE = "CLASS CODE";
	public final static String CUSTOM_REF_PARTS_LABOR_FLAG = "PARTS LABOR FLAG";

	public final static String PERMIT_PRICE_REFERENCE_KEY = "FINAL PERMIT PRICE";

	// Incident Tracker actions
	public final static Integer INCIDENT_TRACKER_REQUEST_ADDITIONAL_INFO = Integer
			.valueOf(1);
	public final static Integer INCIDENT_TRACKER_CANCEL_BY_PROVIDER = Integer
			.valueOf(2);
	public final static Integer INCIDENT_TRACKER_CANCEL_BY_ASSURANT = Integer
			.valueOf(3);
	public final static Integer INCIDENT_TRACKER_SERVICE_DENIED = Integer
			.valueOf(4);
	public final static Integer INCIDENT_TRACKER_CUST_DELAYED_SERVICE = Integer
			.valueOf(5);

	// For footer
	public static final String BLACKOUT_STATES = "blackoutStates";
	public static final String LOCKED_PROVIDER_OBJECT = "locked_provider";
	public static final String SSOW_RESOURCE_ID = "ssow_resource_id";
	public static final String SSOW_ZIP = "csoZipCode";

	public class ClientStatus {
		public static final String CANCELLED = "CANCELLED"; // OMS
		public static final String VOIDED = "VOIDED"; // OMS
		public static final String CLOSED = "CLOSED"; // OMS
		public static final String EDITED = "EDITED"; // OMS
		public static final String WAITING_SERVICE = "WAIT_SERVICE"; // OMS
		public static final String ASSIGNED_TECH = "ASSIGNED_TECH"; // OMS
		public static final String NEW = "NEW";
		public static final String UPDATED = "UPDATED"; // Assurant
		public static final String ACKNOWLEDGEMENT = "ACKNOWLEDGEMENT";
		public static final String INFO = "INFO";
		public static final String RESCHEDULING_NEEDED = "RESCHEDULING_NEEDED";
		public static final String NOT_HOME = "NOT_HOME";

	}

	public class CommonProBuyerParameters {
		public static final String CLIENT_STATUS = "ClientStatus";
		public static final String TEMPLATE_NAME = "TemplateName";
	}

	// FM - Transaction Statuses
	public final static String ACH_TRANSACTION = "ACH";
	public final static String TRANSACTION_STATUS_PENDING = "Pending";
	public final static String TRANSACTION_STATUS_FAILED = "Failed";
	public final static String TRANSACTION_STATUS_COMPLETED = "Completed";

	// SO Receipts constants - LEDGER ENTRY RULE IDs
	public final static Integer LEDGER_ENTRY_RULE_ID_SO_POSTING_FEE = Integer
			.valueOf(1100);
	public final static Integer LEDGER_ENTRY_RULE_ID_SO_REROUTE_FEE = Integer
			.valueOf(1200);
	public final static Integer LEDGER_ENTRY_RULE_ID_SO_SPEND_LIMIT = Integer
			.valueOf(1120);
	public final static Integer LEDGER_ENTRY_RULE_ID_CANCELLATION_PENALTY = Integer
			.valueOf(1300);
	public final static Integer LEDGER_ENTRY_RULE_ID_SO_PROVIDER_FINAL_PRICE = Integer
			.valueOf(1400);
	public final static Integer LEDGER_ENTRY_RULE_ID_SO_PROVIDER_TOTAL_PRICE_DECLARATION = Integer
			.valueOf(1405);
	public final static Integer LEDGER_ENTRY_RULE_ID_SO_PROVIDER_SERVICE_FEE = Integer
			.valueOf(1410);
	public final static Integer LEDGER_ENTRY_RULE_ID_SLA_CREDIT_TO_BUYER = Integer
			.valueOf(5300);
	public final static Integer LEDGER_ENTRY_RULE_ID_SLA_CREDIT_TO_PROVIDER = Integer
			.valueOf(5400);
	public final static Integer LEDGER_ENTRY_RULE_ID_SLA_DEBIT_TO_BUYER = Integer
			.valueOf(5500);
	public final static Integer LEDGER_ENTRY_RULE_ID_SLA_DEBIT_TO_PROVIDER = Integer
			.valueOf(5600);
	// Ledger Rules for escheatment
	public final static Integer LEDGER_ENTRY_RULE_ID_SLA_ESCHEATMENT_TO_BUYER = Integer
			.valueOf(5710);
	public final static Integer LEDGER_ENTRY_RULE_ID_SLA_ESCHEATMENT_TO_PROVIDER = Integer
			.valueOf(5810);

	// SO Receipts constants - LEDGER ENTITY TYPE IDs
	public final static Integer LEDGER_ENTITY_TYPE_ID_BUYER = Integer
			.valueOf(10);
	public final static Integer LEDGER_ENTITY_TYPE_ID_PROVIDER = Integer
			.valueOf(20);
	public final static Integer LEDGER_ENTITY_TYPE_ID_SERVICELIVE = Integer
			.valueOf(30);

	// SO Receipts constants - LEDGER ENTRY TYPE IDs
	public final static Integer ENTRY_TYPE_ID_DEBIT = Integer.valueOf(1);
	public final static Integer ENTRY_TYPE_ID_CREDIT = Integer.valueOf(2);

	// SO Receipts constants - SL Bucks Credit and Debit
	public final static String TIME_DIFF_LOWER_THRESHOLD = "00:00:00";
	public final static String TIME_DIFF_UPPER_THRESHOLD = "00:01:00";

	// Constants for Order Grouping
	public final static String INDIVIDUAL_ORDER = "Individual Order";
	public final static String GROUPED_ORDERS = "Grouped Orders";
	public final static String INDIVIDUAL_CONDITIONAL_ORDERS = "Conditional Orders";
	public final static String READY_FOR_POSTING_ORDERS = "Ready for Posting";

	public final static String GROUP_ID = "groupOrderId";
	public final static String SO_CHILD_ID = "currentChildOrderId";
	public final static int ORDER_TYPE_INDIVIDUAL = 1;
	public final static int ORDER_TYPE_CHILD = 2;
	public final static int ORDER_TYPE_GROUP = 3;
	// Group Service Date & Time
	public final static String GROUP_SERVICE_DATE1 = "GROUP_SERVICE_DATE1";
	public final static String GROUP_SERVICE_DATE2 = "GROUP_SERVICE_DATE2";
	public final static String GROUP_SERVICE_START_TIME = "GROUP_SERVICE_START_TIME";
	public final static String GROUP_SERVICE_END_TIME = "GROUP_SERVICE_END_TIME";

	// method names
	public final static String METHOD_ROUTE_ORDER_GROUP = "processRouteOrderGroup";
	public final static String METHOD_ROUTE_GROUP_WITH_PROVIDERS = "routeGroupToSelectedProviders";
	public final static String METHOD_SEND_ALL_PROVIDERS_EXCEPT_ACCEPTED_GRP = "sendallProviderResponseExceptAcceptedForGroup";
	public final static String METHOD_RELEASE_SO_IN_ACCEPTED = "releaseServiceOrderInAccepted";
	public final static String METHOD_SAVE_NEW_USER = "saveUser";

	// Buyer File Upload
	public static final String FILE_UPLAOD_PROCESSING = "Processing";
	public static final String FILE_UPLOAD_COMPLETED = "0";
	public static final String FILE_PROCESSING_COMPLETED = "COMPLETED";

	// Assurant
	public static final String DISPATCH_RECEIVED = "ServiceLive has received this as a new dispatch into it system";
	public static final String CLASSCODE = "ClassCode";
	public static final String BUYER_STATUS = "buyer_status";
	public static final String REWARD_POINT_COMMENTS = "comments";
	public static final String BUYER_SUBSTATUS_ASSOC_ID = "BUYER_SUBSTATUS_ASSOC_ID";
	public static final String BUYER_SUBSTATUS_DESC = "BUYER_SUBSTATUS_DESC";
	public static final String FILE_EXTENSION_INPROG = ".INPROG";
	public static final long ONE_SECOND = 1000l; // 1000 Milliseconds = 1 second
	public static final String CLASSCOMMENTS = "ClassComments";
	public static final String PARTNUMBER = "PartNumber";
	public static final String MANUFACTURER = "Manufacturer";
	public static final String MODELNUMBER = "Model";
	public static final String SERIALNUMBER = "Serial#";
	public static final String OEM = "OEM";

	public static final String OFFICEMAX_BUYER = "1953";

	// Insurance Tab Enhancements
	public static final String BUTTON_TYPE_EDIT = "EDIT";
	public static final String BUTTON_TYPE_ADD = "ADD";
	public static final String POLICY_AMOUNT = "policyAmount";
	public static final String BUTTON_TYPE = "buttonType";
	public static final String CREDENTIAL_ID = "credId";
	public static final String USER_NAME = "username";
	public static final String CATEGORY_NAME = "category";
	public static final String DOCUMENT_ID = "docId";
	public static final String AMOUNT = "amount";
	public static final String POLICY_NUMBER = "policyNumber";
	public static final String CARRIER_NAME = "carrierName";
	public static final String AGENCY_NAME = "agencyName";
	public static final String AGENCY_STATE = "agencyState";
	public static final String AGENCY_COUNTRY = "agencyState";
	public static final String POLICY_ISSUE_DATE = "policyIssueDate";
	public static final String POLICY_EXPIRATION_DATE = "policyExpirationDate";
	public static final String EMPTY_STR = "";
	public static final int CREDENTIAL_TYPE_ID = 6;
	public static final int GL_CREDENTIAL_CATEGORY_ID = 41;
	public static final int AL_CREDENTIAL_CATEGORY_ID = 42;
	public static final int WC_CREDENTIAL_CATEGORY_ID = 43;
	public static final String GENERAL_LIABILTY = "General Liability";
	public static final String AUTO_LIABILTY = "Auto Liability";
	public static final String WORKMANS_COMPENSATION = "Workmans Compensation";
	public static final String LAST_DOCUMENT_EXISTS = "lastDocumentInd";
	public static final String SAME_DOCUMENT_ID = "sameDocId";
	public static final String INSURANCE_STATUS = "insuranceStatus";
	public static final String INSURANCE_STATUS_INCOMPLETE = "0";
	public static final String CURRENT_TAB = "currentTab";
	public static final String FROM_MODAL_IND = "fromModal";
	public static final String FROM_MODAL_IND_VAL = "modal";
	public static final String CBGLI_BTN_IND = "cbgliBtn";
	public static final String VLI_BTN_IND = "vliBtn";
	public static final String WCI_BTN_IND = "wciBtn";
	public static final String CREDENTIAL_FILE_NAME = "credentialDocumentFileName";
	public static final String CREDENTIAL_DOC_ID = "credentialDocumentId";
	public static final String FROM_POPUP_IND = "frompopup";
	public static final String FROM_POPUP_IND_TRUE = "1";
	public static final String FIELD_ERRORS = "fieldErrors";
	public static final String INSURANCE_INFO = "insuranceInfoDto";
	public static final String INSURANCE_INFORMATION = "insuranceInformationDto";
	public static final String INSURANCE_POLICY_INFORMATION = "insurancePolicyDto";
	public static final String INSURANCE_TYPELIST = "insuranceTypeList";
	// SL-20227
	public static final String ADDITIONALINSURANCE_TYPELIST = "additionalinsuranceTypeList";
	public static final String ADDITIONALINSURANCE_TYPELISTSIZE = "additionalinsuranceTypeListSize";

	public static final String CURRENT_DOC_ID = "currentDocId";
	public static final String CURRENT_DOC_NAME = "currentDocName";
	public static final String DOC_ID = "docId";
	public static final String SAME_DOC_NAME = "sameDocName";
	// SL-10809 Additional Insurance Types
	public static final int OTHER_CREDENTIAL_CATEGORY_ID = 150;

	public static final Integer EMPLOYEE_DISHONESTY_INS = 142;
	public static final Integer CARGO_LEGAL_LIABILITY = 143;
	public static final Integer WAREHOUSEMEN_LEGAL_LIABILITY = 144;
	public static final Integer UMBRELLA_COVERAGE_INSURANCE = 145;
	public static final Integer PROFESSIONAL_LIABILITY_INS = 146;
	public static final Integer PROFESSIONAL_INDEMNITY_INS = 147;
	public static final Integer ADULT_CARE_INS = 148;
	public static final Integer NANNY_INS = 149;
	public static final Integer OTHER_INS = 150;

	public static final String OTHER_CREDENTIAL_CATEGORY_DESCR = "Other";

	// Upsell
	public static final String UPSELL_PAYMENT_TYPE_CHECK = "CA";// For check or
	// cash both
	public static final String UPSELL_PAYMENT_TYPE_CREDIT = "CC";

	public static final String AVAILABLE_BALANCE_NULL = "Pending";
	public static final int BUYER_POSTING_TEMPLATE_ID = 213;

	public final static String PRIVATE_IND_VAL = "1";
	public final static String SEND_EMAIL_ALERT = "true";

	public final int POSTED_STATUS_ID = 110;

	// Response Status
	public static final String SPACE = " ";
	public static final String REASON = "Reason:";
	public static final String COMMENTS = "Comments:";
	public final static String OPENING_BRACE = "(";
	public final static String CLOSING_BRACE = ")";
	public final static String BID_SUBMITTED = "Bid Submitted";

	// Constants in Dashboard
	public final static String IS_FROM_PA = "isFromPA";
	public final static String VENDOR_ID = "vendorId";
	public final static String RESOURCE_ID = "resourceId";
	public final static String FILE_SIZE = "fileSize";
	public final static String FILE_NAME = "fileName";
	public final static String AUDIT_TASK = "auditTask";
	public final static String SECURITY_CONTEXT = "SecurityContext";
	public final static String ROLE_PROVIDER = "Provider";
	public final static String ROLE_BUYER = "Buyer";
	public final static String ROLE_SIMPLE_BUYER = "SimpleBuyer";
	public final static String ROLE_ASSOC_BUYER = "assocBuyer";
	public final static String ROLE_DISPATCHER_PROVIDER = "dispatcherProvider";
	public final static String ROLE_TECHNICIAN_PROVIDER = "technicianProvider";
	public final static String DISPLAY_COMMUNICATION_MONITOR = "displayCommMonitor";
	public final static String SP_EDIT_PROFILE_2 = "2";
	public final static String SP_EDIT_PROFILE_1 = "1";
	public final static String COMPANY_ID_2 = "2";
	public final static String TOTAL_AMOUNT_IN_DECIMAL = "totalAmtInDecimal";
	public final static String DASHBOARD_DTO = "dashboardDTO";
	public final static String DRAFTED_STRING = "drafted";
	public final static String ACCEPTED_STRING = "accepted";
	public final static String POSTED_STRING = "posted";
	public final static String PROBLEM_STRING = "problem";
	public final static String TODAYS_STRING = "todays";
	public final static String RECEIVED_STRING = "received";
	public final static String BID_REQUESTS_STRING = "Bid Requests";
	public final static String VITAL_STATS_DTO = "vitalStatsDTO";
	public final static String PROVIDER_FIRM_DETAILS = "providerFirmDetails";
	public final static String COMMUNICATION_MONITOR_LIST = "commMonitorList";
	public final static String MODULE_LIST = "moduleList";
	public final static String DATE_STRING = "dateString";
	public final static String DASHBOARD_LOG = "DashboardLog";
	public final static String TODAY_STRING = "Today";
	public final static String BULLETIN_BOARD_STRING = "Bulletin Board";
	public final static String PENDING_CANCEL_STRING = "Pending Cancel";
	// SL-21645
	public final static String ESTIMATION_REQUEST_STRING = "Estimation Request";

	// SO STATES
	public final static String ENDSTATE = "endstate";
	public final static String REQUEUE = "requeue";

	public final static Integer COMPELETE_INDICATOR_ZERO = 0;
	public final static Integer COMPELETE_INDICATOR_ONE = 1;

	public final static String RESOLUTION_REQUIRED_QUEUE_ID = "40";

	// RESET PASSWORD - Move it to other file, This is not the right place
	public final static String DEEPLINK_PASSCODE = "deeplink_passcode";
	public final static String DEEPLINK_USERNAME = "deeplink_uname";
	// public final static int ACTIVITY_ID_PASSWORD_RESET_FOR_SL_ADMIN = 38;
	public final static int ACTIVITY_ID_PASSWORD_RESET_FOR_ALL_EXTERNAL_USERS = 40;
	public final static int ACTIVITY_ID_VIEW_CONDITIONAL_AUTO_ROUTING_RULES = 43;
	public final static int ACTIVITY_ID_MANAGE_CONDITIONAL_AUTO_ROUTING_RULES = 44;
	public final static int PERMISSION_MANAGE_PASSWORD_RESET = 31;
	public final static int ACTIVITY_ID_TIER_ROUTING = 47;

	public final static int ACTIVITY_ROLE_ID_BUYER_ROUTING_TIERS = 94;
	public final static int ACTIVITY_ROLE_ID_BUYER_ROUTING_RULES_VIEW = 89;
	public final static int ACTIVITY_ROLE_ID_BUYER_ROUTING_RULES_EDIT = 90;
	public final static Integer ACTIVITY_ID_PROVIDER_ORDER_PRICING_VIEW = 59;
	public final static String STRICT_DELIMITER = "|~|";
	// SO Queue Constants.
	public static final Integer CallBack_QueueId = 2;
	public static final Integer CallBack_QueueId_facilities = 72;
	public static final Integer MAX_FOLLOWUP_ALLOWED = 10;
	public static final String DISPLAY_TAB = "displayTab";
	public static final String DEFAULT_TAB = "defaultTab";
	public static final Integer DISPLAY_MSG_MAX_LENGTH = 250;

	// Tier Routing Constants
	public static final Integer OVERFLOW = 9999;
	public static final Integer NON_TIER = null;

	// Buyer refernce id
	public static String BUYER_REFERENCE_ID_ONE = "1";
	public static String BUYER_REFERENCE_UNIT_NUMBER_FORMAT = "0000000";
	public static int BUYER_REFERENCE_UNIT_NUMBER_LENGTH = 7;

	// Close & Pay and Complete for Payment
	public static String CLOSE_AND_PAY = "CloseAndPay";
	public static String COMPLETE_FOR_PAYMENT = "CompleteForPayment";

	// B2C Provider Screens
	public static Boolean SHOW_B2C_PROVIDER_SCREENS = Boolean.TRUE;

	/*
	 * Buyer specific attributes need for special handling , these are fields
	 * got in the original file
	 */
	public static final String HSR_CLIENT_STATUS_CODE = "HSR_CLIENT_STATUS_CODE";
	public static final String HSR_MODIFYING_UNIT_ID = "HSR_MODIFYING_UNIT_ID";

	/* HSR UPDATE notes */
	public static final String HSR_UPDATE_NEED_ATTENTION_NOTES = "The Service Order is not in a valid state to process the update."
			+ " The SO Sub status has been changed to NEED ATTENTION. Please take appropriate action";
	public static final String HSR_UPDATE_NOTES_SUBJECT = "Update Received from NPS";
	public static final String HSR_UPDATE_NOT_PROCESSED_NOTES = "The Service Order is not in a valid state to process the update. The update was not processed";
	public static final String HSR_UPDATE_CANCELLED_NOTES = "The SO status has been changed to CANCELLED";
	public static final String HSR_UPDATE_VOIDED_NOTES = "The SO status has been changed to VOIDED";
	public static final String HSR_UPDATE_DELETED_NOTES = "The SO status has been changed to DELETED";
	public static final double HSR_COST_TO_INVENTORY = 55.0;

	// Service Pro and Company Credentials out of compliance statuses
	public static final int SERVICE_PRO_CREDENTIAL_OOC = 24;
	public static final int FIRM_CREDENTIAL_OOC = 25;

	// Feature Set
	public static final String ALLOW_COMMUNICATION = "ALLOW_COMMUNICATION";

	// Search Filter map constants
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
	public static final String SEARCH_FILTER_AUTOCLOSE_RULES_LIST = "AUTOCLOSE_RULES_LIST";
	public static final String SEARCH_FILTER_CITY_LIST = "CITY_LIST";

	// Reason Codes for Reschedule Request.
	// 22 - Rescheduled by End User
	// 23 - Rescheduled by Provider
	// 24 - Rescheduled due to End Customer No Show
	// 25 - Rescheduled due to Provider No Show
	// Rescheduled Reason Id has been added under SL-18381
	public static final Integer[] REASON_LIST = { 110, 111, 112, 113, 114, 115,
			116, 117, 118, 119, 120, 121, 122, 123, 124, 126, 127, 128, 129,
			130, 131, 132, 133 };
	// Constants for IVR
	public static final Integer[] ACTIVE_ACCEPTED_PROBLEM = { 150, 155, 170 };
	public static final String VALID = "Valid";
	public static final String FAILURE = "Failed";
	public static final String PIPE_DELIMITER = "|";
	public static final int UNIT_NUMBER_LENGTH = 7;
	public static final int ORDER_NUMBER_LENGTH = 8;
	public static final int BUYER_ENTITY_ID = 10;
	public static final String INHOME_BUYER = "3000";
	// Buyer Document Permission
	public static final String VIEW_PROVIDER_DOC_PERMISSION = "52";

	// Adopted buyer document permission
	public static final String VIEW_PROVIDER_DOC_PERMISSION_ADOPTED_BUYER = "53";

	/** SORT COLUMNS. */
	public static final String COLUMN_LASTNAME = "lastName";
	public static final String COLUMN_USERID = "userId";
	public static final String COLUMN_LAST_ACTIVITY_DATE = "lastActivityDate";
	public static final String COLUMN_MARKETNAME = "marketName";
	public static final String COLUMN_STATE = "state";
	public static final String COLUMN_MARKETID = "marketId";
	public static final String COLUMN_ROLETYPE = "roleType";
	public static final String COLUMN_STATENAME = "stateName";
	public static final String COLUMN_SERVICE_PRO_STATUS = "serviceProStatus";
	public static final String COLUMN_FIRSTNAME = "firstName";
	public static final String COLUMN_CITY = "city";
	public static final String COLUMN_COMPANYID = "companyId";
	public static final String COLUMN_PRO_FIRM_STATUS = "proFirmStatus";
	public static final String COLUMN_BUSINESSNAME = "businessName";
	public static final String COLUMN_ADJUSTMENT = "adjustment";

	public static final long LONG_ZERO = 0;

	// Order History tab enhancement
	public static final String SYSTEM_IVR = "IVR/SYSTEM";
	public static final String SYSTEM_IVR_TEXT = "IVR/System";
	public static final String DEPARTURE_INPUT_IVR = "Departure Time Entered - via IVR";
	public static final String ARRIVAL_INPUT_IVR = "Arrival Time Entered - via IVR";
	public static final String ARRIVAL_INPUT_WEB = "Arrival Time Entered - via Web";
	public static final String ARRIVAL_TEXT = "The Arrival time for this order was marked as ";
	public static final String DEPARTURE_INPUT_WEB = "Departure Time Entered - via Web";
	public static final String DEPARTURE_TEXT = "The Departure time for this order was marked as ";
	// Added ofr SPN -1110
	public static final String ARRIVAL_INPUT_MOBILE = "Arrival Time Entered - via Mobile";
	public static final String DEPARTURE_INPUT_MOBILE = "Departure Time Entered - via Mobile";

	public static final int SERVICE_INCOMPLETE = 1;

	public static final Integer SIZE_KB = Integer.valueOf(1024);
	public static final Integer FIVE_KB = Integer.valueOf(5 * 1024);
	public static final Integer TWO_KB = Integer.valueOf(2 * 1024);
	//SL-21811-public static final Double RETAIL_MAX = 2500.00;

	// Buyer Id
	public static final Integer FACILITIES_BUYER = 4000;
	public static final Integer ATnT = 512353;

	// Reason code Manager
	public static final String TYPE_CANCEL = "Cancellation";
	public static final String TYPE_SCOPE = "Manage Scope";
	public static final String TYPE_SPEND_LIMIT = "Spend Limit";
	public static final String ACTIVE_CODE = "Active";
	public static final String DELETED_CODE = "Deleted";
	public static final String ARCHIVED_CODE = "Archived";
	public static final String ADDED_CODE = "Added";
	public static final String DELETE_STATUS = "delete";
	public static final String ARCHIVE_STATUS = "archive";
	public static final String SAVE = "save";
	public static final String EXISTS = "exists";

	public static final String SAVE_ERROR = "There was a problem while saving your reason code. Please try again or contact ServiceLive support";
	public static final String DELETE_ERROR = "There was a problem while deleting your reason code. Please try again or contact ServiceLive support";
	public static final String ADD_ERROR = "The reason code already exists";
	public static final String SAVE_SUCCESS = "The reason code was successfully saved";
	public static final String DELETE_SUCCESS = "The reason code was successfully deleted";
	public static final String ARCHIVE_SUCCESS = "The reason code was successfully archived";
	public static final String REASON_CODE_ERROR = "Please enter a reason code.";
	public static final String REASON_TYPE_ERROR = "Please select a type.";

	// SO Price Types
	public static final String TASK_LEVEL_PRICING = "TASK_LEVEL";
	public static final String SO_LEVEL_PRICING = "SO_LEVEL";

	// So Task Types
	public static final Integer PRIMARY_TASK = 0;
	public static final Integer PERMIT_TASK = 1;
	public static final Integer DELIVERY_TASK = 2;
	public static final Integer NON_PRIMARY_TASK = 3;

	// SO Task Status
	public static final String ACTIVE_TASK = "ACTIVE";
	public static final String DELETED_TASK = "DELETED";
	public static final String CANCELED_TASK = "CANCELED";
	// For handling newly added task status through SL mobile application
	public static final String COMPLETED_TASK = "COMPLETED";

	// SKU Maintenance
	public static final String NO_SKU_SELECTED = "No SKU is selected";

	// Filter Types
	public static final String GENERAL = "General";
	public static final String SCOPEOFWORK = "Scope Of Work";
	public static final String SERVICELOCATION = "Service Location";
	public static final String SCHEDULE = "Schedule";
	public static final String PRICING = "Pricing";
	public static final String CONTACTS = "Contacts";
	public static final String ATTACHMENTS = "Attachments";
	public static final String PARTS = "Parts";
	public static final String CUSTOMREFERENCES = "Custom References";
	public static final String NOTES = "Notes";
	public static final String HISTORY = "History";
	public static final String ROUTEDPROVIDERS = "Routed Providers";
	// Time onsite valiadtion constant
	public static final Integer ARRIVAL_DATE_PRESENT = 1;
	public static final Integer DEPARTURE_DATE_PRESENT = 1;
	// Reason Code
	public static final String FETCHREASONCODES = "Fetch Reason Codes";
	// pre call
	public static final String FETCH_PRECALL_REASONCODES = "Fetch PreCall Reason Codes";

	public static final String OTHER = "-2";
	public static final String SELECT = "-1";

	/* Constants for MISC Reports */
	public static final Integer UPPER_LIMIT_FOR_DISPLAY_REPORTS = 10000;
	public static final Integer REPORT_PAGE_SIZE = 100;
	public static final Integer RADIX_DECIMAL = 10;
	public static final String REPORT_START_DATE = "2008-01-01";

	public static final String GENERATE_REPORT = "GenerateReport";;

	public static final String PROVIDER_SO_REPORT = "ProviderPaymentReportBySO";
	public static final String PROVIDER_REV_REPORT = "Provider_Rev_Report";
	public static final String BUYER_SO_REPORT = "Buyer_So_Report";
	public static final String BUYER_TAXID_REPORT = "Buyer_TaxId_Report";
	public static final String ADMIN_PAYMENT_REPORT = "Admin_Payment_Report";
	public static final String MISC_REPORT = "Misc_Report";
	public static final String PROVIDER_SO_DTO = "prov_so_DTO";
	public static final String PROVIDER_REV_DTO = "prov_rev_DTO";
	public static final String BUYER_SO_DTO = "buyer_so_DTO";
	public static final String BUYER_TAXID_DTO = "buyer_taxId_DTO";
	public static final String ADMIN_PAYMENT_DTO = "admin_payment_DTO";
	public static final String MISC_REPORT_DTO = "misc_report_DTO";

	public static final String PROVIDER_SO_PAGINATION = "PROVIDER_SO_PAGINATION";
	public static final String PROVIDER_REV_PAGINATION = "PROVIDER_REV_PAGINATION";
	public static final String BUYER_SO_PAGINATION = "BUYER_SO_PAGINATION";
	public static final String BUYER_TAXID_PAGINATION = "BUYER_TAXID_PAGINATION";
	public static final String ADMIN_PAYMENT_PAGINATION = "ADMIN_PAYMENT_PAGINATION";
	public static final String MISC_REPORT_PAGINATION = "MISC_REPORT_PAGINATION";

	public static final String PROVIDER_SO_TOTAL_REC = "PROVIDER_SO_TOTAL_REC";
	public static final String PROVIDER_REV_TOTAL_REC = "PROVIDER_REV_TOTAL_REC";
	public static final String BUYER_SO_TOTAL_REC = "BUYER_SO_TOTAL_REC";
	public static final String BUYER_TAXID_TOTAL_REC = "BUYER_TAXID_TOTAL_REC";
	public static final String ADMIN_PAYMENT_TOTAL_REC = "ADMIN_PAYMENT_TOTAL_REC";
	public static final String MISC_REPORT_TOTAL_REC = "MISC_REPORT_TOTAL_REC";
	public static final String REPORT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String CSV_REPORT_DATE_FORMAT = "MM/dd/yyyy";
	public static final String REPORT_DATE_TIME_STAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String FM_REPORTS_PAGING_CRITERIA_KEY = "FM_REPORTS_PAGING_CRITERIA_KEY";
	public static final String REPORT_EXPORT_PROV_SO = "exportStatus_prov_so";
	public static final String REPORT_EXPORT_PROV_REV = "exportStatus_prov_rev";
	public static final String REPORT_EXPORT_BUYER_SO = "exportStatus_buyer_so";
	public static final String REPORT_EXPORT_BUYER_ID = "exportStatus_buyer_tax_id";
	public static final String REPORT_EXPORT_ADMIN_PAYMENT = "exportStatus_admin_payment";

	public static final String REPORT_QUEUED = "Pending";
	public static final String REPORT_PROCESSING = "In Process";
	public static final String REPORT_COMPLETED = "Completed";
	public static final String REPORT_FAILED = "Failed";
	public static final String REPORT_DELETED = "Deleted";
	public static final String NOT_APPLICABLE_SIGN = "-";

	public static final String PROVIDER_SO_REPORT_HDR = "BuyerID,Buyer Name,Date Complete,Service Order #,Date Paid,Gross- Labor,Gross- Other,Total Gross Service Order,Service Fee,Net";
	public static final String PROVIDER_REV_REPORT_HDR = "BuyerID,Buyer Name,Gross Revenue,Service Fee,Net";
	public static final String BUYER_SO_REPORT_HDR = "Provider FirmID,Provider Firm Name,Taxpayer Type,Exempt?,TIN Type,Taxpayer ID,Date Paid,Service Order #,Total Gross Payment,Buyer Posting Fee,Provider Fee";
	public static final String BUYER_TAXID_REPORT_HDR = "Provider FirmID,Provider Firm Name,DBA,Taxpayer Type,Exempt?,TIN Type,Taxpayer ID,Street Address1,Street Address2,City,State,Zip,Total Gross Payments";
	public static final String PROVIDER_SO_REPORT_HDR_ADMN = "Provider FirmID,Provider Firm Name,BuyerID,Buyer Name,Date Complete,Service Order #,Date Paid,Gross- Labor,Gross- Other,Total Gross Service Order,Service Fee,Net";
	public static final String PROVIDER_REV_REPORT_HDR_ADMN = "Provider FirmID,Provider Firm Name,BuyerID,Buyer Name,Gross Revenue,Service Fee,Net";
	public static final String BUYER_SO_REPORT_HDR_ADMN = "BuyerID,Buyer Name,Provider FirmID,Provider Firm Name,Taxpayer Type,Exempt?,TIN Type,Taxpayer ID,Date Paid,Service Order #,Total Gross Payment,Buyer Posting Fee,Provider Fee";
	public static final String BUYER_TAXID_REPORT_HDR_ADMN = "BuyerID,Buyer Name,Provider FirmID,Provider Firm Name,DBA,Taxpayer Type,Exempt?,TIN Type,Taxpayer ID,Street Address1,Street Address2,City,State,Zip,Total Gross Payments";
	// public static final String
	// BUYER_TAXID_REPORT_HDR_ADMN="BuyerID,Buyer Name,Provider FirmID,Provider Firm Name,Taxpayer Type,Exempt?,TIN Type,Taxpayer ID,Date Paid,Service Order #,Total Gross Payment,Buyer Posting Fee,Provider Fee";
	public static final String ADMIN_PAYMENT_HDR = "Vendor ID,ServiceLive Goodwill Credits,ServiceLIve Goodwill Debits,Net Goodwill Amount";

	public static final String REPORT_FOOTER_INFO = "Please check with your local tax professional to determine how to treat this business expense.";

	public static final String PRICE_FORMAT = "#,###,##0.00";
	public static final String PRICE_FORMAT_DB = "#0.00";
	public static final String PRICE_FORMAT_CSV = "$0.00";

	public static final String PROVIDER_SO_REPORTNAME = "Provider_Payments_By_Service_Order";
	public static final String PROVIDER_REV_REPORTNAME = "Provider_Revenue_Summary_Report";
	public static final String BUYER_SO_REPORTNAME = "Buyer_Payments_By_Service_Order";
	public static final String BUYER_TAXID_REPORTNAME = "Buyer_Payments_By_TaxpayerID";
	public static final String ADMIN_PAYMENT_REPORTNAME = "ServiceLive_Admin_Payments_Report";

	public static final String ZERO_DOLLAR = "$0.00";
	public static final Integer MAX_ATTEMPT_BEFORE_FAIL = 2;

	public static final String REPORT_MSG_EXPORT_PROV_SO = "prov_so_stat_msg";
	public static final String REPORT_MSG_EXPORT_PROV_REV = "prov_rev_stat_msg";
	public static final String REPORT_MSG_EXPORT_BUYER_SO = "buyer_so_stat_msg";
	public static final String REPORT_MSG_EXPORT_BUYER_ID = "buyer_id_stat_msg";
	public static final String REPORT_MSG_EXPORT_ADMIN_PAYMENT = "admin_payment_stat_msg";
	public static final String CSV_QOUTE = "Quote";
	public static final int REPORT_BUYER_SO_COUNT = 50000;

	public static final int MAX_MONTHS_RANGE = 13;

	public static final String MANAGESCOPE_REASON_CODE = "Cancelled through API";
	public static final String MANAGESCOPE_REASON_COMMENT = "Cancelled through API";
	public static final String SORT_PROPERTY_NAME = "name";
	public static final String SORT_PROPERTY_DISTANCE = "distance";
	public static final String SORT_ORDER_ASCENDING = "ascending";
	public static final String SORT_ORDER_DESCENDING = "descending";

	public static final String SO_ASSIGNMENT_TYPE_FIRM = "FIRM";
	public static final String SO_ASSIGNMENT_TYPE_PROVIDER = "PROVIDER";
	public static final String ASSIGNMENT_TYPE_FIRM = "typeFirm";
	public static final String ASSIGNMENT_TYPE_PROVIDER = "typeProvider";
	public static final String TYPE_GROUP = "group";

	// SL-18007 Constants added for the info Level while fetching the so price
	// details
	public static final Integer CURRENT_PRICE_INFO = 0;
	public static final Integer SO_LEVEL_PRICE_HISTORY = 1;
	public static final Integer TASK_LEVEL_PRICE_HISTORY = 2;
	public static final String DEFAULT_SERVICE_LOCATION_TIMEZONE = "CST";
	public static final String CREDIT_INDICATOR = "(+)";
	public static final String DEBIT_INDICATOR = "(-)";
	public static final char CREDIT_CHAR = '+';
	public static final char DEBIT_CHAR = '-';

	public static final int SEARS_BUYER_ID = 1000;
	public static final String SERIAL_NO = "Serial Number";
	public static final String MODEL_NO = "Model Number";

	public static final String RESPOND_TAB = "Respond";
	public static final String INBOX_TAB = "Inbox";
	public static final String SCHEDULE_TAB = "Schedule";
	public static final String CONFIRM_APPT_WDW_TAB = "Confirm Appt window";
	public static final String PRINT_PAPERWORK_TAB = "Print Paperwork";
	public static final String CURRENT_ORDERS_TAB = "Current Orders";
	public static final String AWAITING_PAYMENT_TAB = "Awaiting Payment";
	public static final String ASSIGN_PROVIDER_TAB = "Assign Provider";
	public static final String MANAGE_ROUTE_TAB = "Manage Route";
	public static final String JOB_DONE_TAB = "Job Done";
	public static final String CANCELLATIONS_TAB = "Cancellations";
	public static final String RESOLVE_PROBLEM_TAB = "Resolve Problem";

	// For R12_0
	public static final String REVISIT_NEEDED_TAB = "Revisit Needed";
	public static final String NO_PARTS_ADDED_IND = "NO_PARTS_ADDED";

	// for print paper work
	public static final String CHECKED_SO_IDS = "checkedSoIds";
	public static final String CHECKED_SO_ID_COUNT = "checkedSoIdsCount";
	public static final String MAX_PERMISSIBLE_COUNT = "maxPermissibleCount";

	public static final String CHECKED_OPTIONS = "checkedOptions";
	public static final Object VIEW_CUSTOMER_COPY = "viewCustomerCopy";
	public static final Object VIEW_PROVIDER_COPY = "viewProviderCopy";
	public static final String SO_DROP_DOWN_OPTIONS = "soOptions";
	public static final String SELECT_STRING = "-Select-";
	public static final int SELECT_VALUE = -1;
	public static final String TO = " to ";
	public static final String ORDERS = " orders ";

	public final static String SORT_COLUMN_OM_STATUS = "wf_alias.sort_order";
	public final static String SORT_COLUMN_OM_APPOINTMENT_DT_TZ = "CONVERT_TZ(DATE_ADD(s.service_date1, INTERVAL STR_TO_DATE(s.service_time_start,'%h:%i %p') HOUR_SECOND), 'GMT', s.service_locn_time_zone)";
	public final static String SORT_COLUMN_OM_SO_TITLE = "s.sow_title";
	public final static String SORT_COLUMN_OM_SCHEDULE_STATUS = "sch.schedule_status";
	public final static String SORT_COLUMN_OM_SERVICE_LOCATION = "loc.state_cd, loc.city ";
	public final static String SORT_COLUMN_OM_PROVIDER = "COALESCE(vcontact.first_name, 'ZZZZ'), COALESCE(vcontact.last_name, 'ZZZZ')";
	public final static String SORT_COLUMN_OM_FOLLOW_UP = "followUpFlag";

	public final static String REQUEST_CONTENT_TYPE = "application/xml";

	public static final String DATETYPE_RANGE = "range";
	public static final String DATETYPE_FIXED = "fixed";
	public static final int DEAFULT_NUMBER_OF_ORDERS_OM = 50;

	public static final int API_CONNECTION_TIMEOUT = 1000 * 5; // In
	// Milliseconds

	// constant for viewOderPricing
	public static final String VIEW_ORDER_PRICING = "59";

	public static final int PROVIDER_FEEDBACK_DOC_CATEGORY = 4;

	// Buyer Lead Management

	public static final String BUYER_LEAD_MANAGEMENT_DEFAULT_LEAD_COUNT = "buyer_lead_management_lead_count";
	public static final String APPEND_STR_PLUS = "+ ";
	public static final String APPEND_STR_MINUS = "- ";
	public static final int SHOP_YOUR_WAY_REWARD_POINDS_ID = 9;
	public static final String SHOP_YOUR_WAY_REWARD_POINDS_ID_STR = "9";
	public static final String SHOP_YOUR_WAY_REWARD_POINDS_ACTION_REASON = "UPDATE_SYWP";
	public static final int SHOP_YOUR_WAY_REWARD_POINDS_ADD = 1;
	public static final int SHOP_YOUR_WAY_REWARD_POINDS_REVOKE = 2;
	public static final String LEAD_REASON_CODE_TYPE_CANCELLATION = "CANCELLATION";
	public static final int BUYER_LEAD_ADD_NOTE = 11;
	public static final String BUYER_NOTE_TEXT = "A Note was created for this Lead by Buyer";
	public static final String PROVIDER_NOTE_TEXT = "A Note was created for this Lead by Provider";
	public static final String PROVIDER_NOTE_EDIT_TEXT = "A Note was edited for this Lead by Provider";
	public static final int PROVIDER_ATTACH_DOC = 12;
	public static final String PROVIDER_ATTACH_DOC_TEXT = "Document attached by Provider.";
	public static final int PROVIDER_CANCEL_LEAD = 10;
	public static final String PROVIDER_CANCEL_LEAD_PROVIDER_TEXT = "The Lead was cancelled by the provider";
	public static final String PROVIDER_CANCEL_LEAD_CUSTOMER_TEXT = "The Lead was cancelled by the customer";
	public static final int PROVIDER_COMPLETE_LEAD = 8;
	public static final String PROVIDER_DELETE_ATTACH_DOC_TEXT = "The attached document was deleted by Provider";

	// Lead Details
	public static final String SAME_DAY = "Same Day";
	public static final String NEXT_DAY = "Next Day";
	public static final String AFTER_TOMORROW = "After Tomorrow";
	public static final String INSTALL = "Install";
	public static final String REPAIR = "Repair";
	public static final String DELIVERY = "Delivery";
	public static final String EXCLUSIVE = "Exclusive";
	public static final String COMPETITIVE = "Competitive";
	public static final String LEAD_ROLE_TYPE_BUYER = "buyer";
	public static final String LEAD_ROLE_TYPE_PROVIDER = "provider";
	public static final String LEAD_ROLE_TYPE_CUSTOMER = "customer";
	public static final String CUSTOMER_INITIATED = "Customer";
	public static final String BUYER_INITIATED = "Buyer";
	public static final String FIRM_INITIATED = "Firm";
	public static final String PREFERRED_DATE_NOT_AVAIABLE = "Not specified";
	public static final String USER_ID = "User ID #";

	public static final int LEAD_FIRM_UPDATED = 7;
	public static final String STATUS_CHANGED_WORKING = "Status changed to working. Reason : ";
	// SL-19293
	public static final String SL_LEADS_ADDENDUM_LINK = "SL_Leads_Addendum_Link";
	public static final String SL_LEADS_ADDENDUM_NEW_TANDC = "SL Leads Addendum";
	public static final int CONSTANT_1 = 1;
	public static final int LEADS_TC_VERSION_0 = 0;
	public static final String LEAD_GEN_FEES_DEFAULT_PDF = "OTHER";
	public static final String LEAD_TC_ACTION_NAME = "Leads T&C Accepted";

	public static final String RESCHEDULE_DATE_TIME_STAMP_FORMAT1 = "MM/dd/yy hh:mm a";
	public static final String RESCHEDULE_DATE_TIME_STAMP_FORMAT2 = "yyyy-MM-dd hh:mm a";
	public static final String RESCHEDULE_DATE_FORMAT = "MM/dd/yy";

	// Update SO Completion
	public static final String SERVICE_PARTIAL = "Partial";
	// SL-19823
	public static final String SOW_SERVICE_ORDER_STATUS_SOID = "SOW_SERVICE_ORDER_STATUS_SOID";

	public static final int CATEGORY_TYPE_INSURANCE = 6;

	// R12.0 IVR
	public static final String ARRIVAL_DEPARTURE_REASONCODE_IVR = "IVR";
	public static final String TRIP_STATUS_OPEN = "OPEN";
	public static final String TRIP_STATUS_CLOSED = "ENDED";
	public static final String ARRIVAL_DEPARTURE_SOURCE_IVR = "IVR";
	public static final String IVR_ARRIVAL = "arrival_insert";
	public static final String IVR_DEPARTURE_UPDATE = "departure_update";
	public static final String IVR_DEPARTURE_INSERT = "departure_insert";

	// R12.0 Sprint 3 Time ON Site front-end changes
	public static final String DATE_FORMAT_1 = "MM/dd/yyyy";
	public static final String DATE_FORMAT_2 = "MM/dd/yy hh:mm a";
	public static final String DATE_FORMAT_3 = "EEE, MMM d, yyyy";
	public static final String DATE_FORMAT_4 = "EEE, MMM d, yyyy hh:mm a";
	public static final String MOBILE = "MOBILE";
	public static final String MOBILE_1 = "Mobile";
	public static final String WEB = "WEB";
	public static final String WEB_1 = "Manual";
	public static final String IVR = "IVR";
	public static final String TRIP_SO_ID = "soId";
	public static final String SO_TRIP_NO = "soTripNo";
	public static final String WEB_ARRIVAL = "Arrival";
	public static final String WEB_DEPARTURE = "Departure";
	public static final String CANCELLATION_REQUESTED = "Cancellation Requested";
	public static final String REVISIT_NEEDED = "Revisit Needed";
	public static final String SERVICE_COMPLETED = "Service Completed";
	public static final HashMap<String, String> TRIP_CHANGE_TYPE = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;

		{
			put("REFERENCE", "Custom Reference");
			put("ADDON", "Add-On");
			put("RESOLUTION_COMMENT", "Resolution Comment");
			put("PARTS", "Parts");
			put("PHOTOS_DOCUMENTS", "Photos/Documents");
			put("SIGNATURE", "Signature");
			put("PAYMENT", "Payment");
			put("INVOICE_PARTS", "Invoice Parts");
			put("ADDON_PAYMENT", "Add-On Payment");
			put("PERMIT_TASK", "Permit Task");
			put("PERMIT_ADDON", "Permit Add-On");
			put("TASK", "Task");
		}
	};

	// For R12_0 Sprint 3 Pre Call Changes
	public static final int PRE_CALL_COMPLETED = 3;

	// R12_0 Sprint 5 Changes
	public static final String CHECKED_INVOICE_IDS = "checkedInvoiceIds";

	public static final String PART_STATUS_INSTALLED = "Installed";
	// R12_0 Sprint 7 Changes
	public static final String TRUCK_STOCK = "Truck Stock";
	public static final String INSTALLED = "Installed";
	public static final String NOT_INSTALLED = "Not Installed";
	public static final double DISCOUNT_PERCENTAGE = 0.80;
	public static final Integer WORK_STARTED_SUBSTATUS_ID = 136;
	public static final Integer PROVIDER_ON_SITE_SUBSTATUS_ID = 20;

	public static final Integer REVISIT_NEEDED_SUBSTATUS_ID = 134;
	public static final Integer PENDING_CLAIM_SUBSTATUS_ID = 135;
	public static final Integer NOT_HOME_REVISIT_NEEDED_SUBSTATUS_ID = 137;
	// SL-20363 Need to Add UI for Forceful CAR Activation for buyer# 3000
	public static final String FORCE_ACTIVE_PROPERTY_INDICATOR = "Force_Active_Ind";

	// R14.0 Reject SO API
	public static final Integer PROVIDER_RESP_REJECTED = 3;
	public static final Integer REJECT_SO = 214;
	public static final String ONE = "1";
	public static final String REJECT_INVALID_REASON_CODE = "1604.serviceorder.reason.code.error";
	public static final String REJECT_RESOURCE_ID_REQUIRED = "1605.serviceorder.resource.id.warning";
	public static final String REJECT_RESOURCE_ID_INVALID = "1603.serviceorder.provider.error";
	public static final String GROUP_IND_PARAM = "groupind";
	public static final String GROUPED_SO_IND = "1";
	public static final Integer OTHER_REASON_CODE = 6;
	// R14.0 GetFilter API
	public static final String SEARCH_FILTER_SUBSTATUS_LIST = "SUBSTATUS_LIST";
	public static final String SEARCH_FILTER_APPOINTMENT_DATE = "APPOINTMENT_DATE_LIST";
	public static final String SEARCH_FILTER_FLAGGED_ORDERS = "FLAGGED_ORDERS";
	public static final String SEARCH_FILTER_ASSIGNED_ORDERS = "UNASSIGNED_ORDERS";
	public static final String FILTER_FLAG_VALUE = "Y";
	public static final String FILTER_ORDER_VALUE = "Y";
	// R15.2 SL-20555
	public static final String LIS_ERROR = "LIS_ERROR";
	// R15_2_1 CC-897
	public final static String ORDER_STATUS_RECIEVED = "Received";
	// SL-20997 Adding constant to prefix aptNo
	public static final String APT_NUMBER = "Apt ";
	public static final String COMMA_DELIMITER_SPACE = ", ";

	public final static String INVALID_REQUEST_CONTACT_ADMIN = "Invalid request. Please contact your admin.";
	// R16_0 SL-20548: Show Communications Monitor for all providers having
	// permission 66- Manage SPN Invitations
	public static final String SPN_INVITE_PERMISSION = "66";

	// SL-20926
	public static final String ADDON_CLOSE_ERROR = " We are unable to close the order, please contact ServiceLive support.";
	public static final String ADDON_COMPLETE_ERROR = " We are unable to complete the order, please contact ServiceLive support.";
	public static final String ADDON_RESPONSE = "spendLimitValidation";

	// SL-21070
	public final static int LOCK_EDIT_TRUE = 1;
	public static final String LOCK_EDIT_CURRENT_SO_REQ_VAR = "currentSoId";
	public static final String LOCK_EDIT_SESSION_VAR = "isBeingEdited_";

	// R16_1: SL-18979 Add Participant/Delete Subscription API vibes Changes
	public static final String COMPANY_ID = "VIBESCompanyId";
	public static final String ACQUISITION_CAMPAIGN_ID = "VIBESAcquisitionCampaignId";
	public static final String SUBSCRIPTION_ID = "VIBESSubscriptionId";
	public static final String ADD_PARTICIPANT_API_URL = "VIBESAddParticipantAPIURL";
	public static final String DELETE_SUBSCRIBER_API_URL = "VIBESDeleteSubscriberAPIURL";
	public static final String URL_COMPANY_ID = "{companyId}";
	public static final String URL_ACQUISITION_CAMPAIGN_ID = "{acquisitionCampaignId}";
	public static final String URL_SUBSCRIPTION_ID = "{subscriptionId}";
	public static final String URL_PERSON_ID = "{personId}";
	public static final String URL = "url";
	public static final String REQUEST = "request";
	public static final String HEADER = "VIBESHeader";
	public static final String HYPHEN = "-";
	public static final int STATUS_CODE_SUCCESS1 = 200;
	public static final int STATUS_CODE_SUCCESS2 = 201;
	public static final int STATUS_CODE_EXCEPTION = 500;
	public static final String STATUS = "status";
	public static final String PENDING_STATUS = "PENDING";
	public static final String PARTICIPATION_DATE = "participation_date";
	public static final String EXPIRE_DATE = "expire_date";
	public static final String PERSON = "person";
	public static final String PERSON_ID = "person_id";
	public static final String INVALID_MOBILE2 = "not a mobile phone number";
	public static final String INVALID_MOBILE3 = "InvalidMDNFormat";
	public static final String INVALID_MOBILE4 = "MdnMissingOrInvalid";
	public static final String ERRORS = "errors";
	public static final String MESSAGE = "message";
	public static final int STATUS_CODE_ERROR422 = 422;
	public static final int STATUS_CODE_ERROR409 = 409;// Provider already in
	// subscription list.
	public static final String MOBILE_NO_INVALID = "invalid.mobile.error";
	public static final String MOBILE_NO_GENERIC_ERROR = "generic.error";
	public static final String MOBILE_NO_EXISTS_ERROR = "mobile.exists.error";
	public static final String MOBILE_NO_GENERIC_ERROR2 = "generic.error2";
	public static final String ACTIVE_RECORD = "ACTIVE";
	public static final String ADD = "ADD";
	public static final String DELETE = "DELETE";
	public static final int STATUS_CODE_DELETED = 204;
	public static final int STATUS_CODE_NOT_FOUND = 404;
	public final static String DELETED_CAPS = "DELETED";
	public static final String INACTIVE = "INACTIVE";
	public static final String MIGRATION_BATCH = "SMS Migration Batch";
	public static final String BATCH_KEY = "KEY";
	public static final Integer TERMINATED = 32;
	public static final String VIBES_SWITCH = "VIBES_SMS_notification_switch";
	public static final String INVALID_REASON_CODE_ERROR = "Please enter a valid  reasoncode for reschedule";

	// SL-21215
	public static final String OAUTHERRORSTRING = "Oauth authetication error";
	public static final String BASIC = "Basic";
	public static final String AUTHENTICATION_HEADER = "Authorization";
	public static final String BASICAUTHERROR = "Authentication failed";
	public static final String URL_NOT_FOUND = "URL not found";
	public static final String AUTHERRORSTRING = "Error while authenticating";
	public static final String COLON = ":";

	// B2C Changes
	public static final String ESTIMATE = "Estimate";
	public static final String RANGE = "Range";

	public static final String SPECIFIC = "Specific";
	public static final String REVIEW = "Review";
	public static final String ADDONS = "Addons";

	public static final String EVENT = "event";
	public static final String EQUALS = "=";
	public static final String AND = "&";
	public static final String SERVICEPROVIDER = "serviceprovider";
	public static final String NUMERIC_PROVIDER_ID = "Please enter number only.";
	public static final String SERVICELIVE = "SL";
	public static final String FIRM_LOGO = "firmLogo";
	
	// SL-21662
	public static final String ORDER_CHECKED_IN_BY_PROVIDER = "order_checked_in_by_provider";
	public static final String ORDER_CHECKED_OUT_BY_PROVIDER = "order_checked_out_by_provider";
	
	//E-Wallet Enhancement
	public static final Integer State_Regulations_Reason_Code = 70;
	public static final Integer IRS_Levy_Reason_Code = 90;
	public static final Integer Legal_Hold_Reason_Code = 100;
	public static final Integer Wallet_Control_Id_IRS_Levy = 3;
	public static final Integer Wallet_Control_Id_Legal_hold = 4;
	
	//SLT-2138
	public static final String ORDER_CANCEL_PUSH_NOTIFICATION_TEMPLATE = "Cancelled Service Order - PUSH";
	public static final String BUYER_CANCELLATION_PUSH_NOTIFICATION_TEMPLATE = "Request to Cancel Service Order - PUSH";
	public static final String PROVIDER_CANCELLATION_PUSH_NOTIFICATION_TEMPLATE="Pro-Request to Cancel Service Order - PUSH";
	//SLT-3838
	public static final String JOBCODES = "JobCodes";
	public static final String PAYMENTDETAILS = "Payment Details";
	public static final String INVOICEPARTS = "Invoice Parts";
	}
