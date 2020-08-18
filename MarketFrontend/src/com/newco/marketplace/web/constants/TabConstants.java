package com.newco.marketplace.web.constants;

public final class TabConstants {
	
	public static final String BUYER_TODAY = "Today";
	public static final String BUYER_ACTIVE = "Active";
	public static final String BUYER_ACCEPTED = "Accepted";
	public static final String BUYER_DRAFT = "Draft";
	public static final String BUYER_POSTED = "Posted";
	public static final String BUYER_INACTIVE = "Inactive";
	public static final String BUYER_PROBLEM = "Problem";
	public static final String BUYER_SEARCH = "Search";
	
	public static final String PROVIDER_TODAY = "Today";
	public static final String PROVIDER_RECEIVED = "Received";
	public static final String PROVIDER_ACTIVE = "Active";
	public static final String PROVIDER_ACCEPTED = "Accepted";
	public static final String PROVIDER_BID_REQUESTS = "Bid Requests";
	public static final String PROVIDER_BULLETIN_BOARD = "Bulletin Board";
	public static final String PROVIDER_DRAFT = "Draft";
	public static final String PROVIDER_INACTIVE = "Inactive";
	public static final String PROVIDER_PROBLEM = "Problem";
	public static final String PROVIDER_SEARCH = "Search";
	
	public static final String SIMPLE_BUYER_TODAY = "Current";
	public static final String SIMPLE_BUYER_ACTIVE = "Active";
	public static final String SIMPLE_BUYER_ACCEPTED = "Accepted";
	public static final String SIMPLE_BUYER_DRAFT = "Saved";
	public static final String SIMPLE_BUYER_POSTED = "Posted";
	public static final String SIMPLE_BUYER_INACTIVE = "History";
	public static final String SIMPLE_BUYER_PROBLEM = "Problem";
	public static final String SIMPLE_BUYER_SEARCH = "Search";
	
	public static final String TODAY = "Today";

	/* from wf_states in db
	100	Draft
	110	Routed
	120	Cancelled
	125	Voided
	130	Expired
	140	Conditional Offer
	150	Accepted
	155	Active
	160	Completed
	170	Problem
	180	Closed	
	*/	
	public static final Integer DRAFT_STATE = 100;
	public static final Integer ROUTED_STATE = 110;
	public static final Integer CANCELLED_STATE = 120;
	public static final Integer VOIDED_STATE = 125;
	public static final Integer EXPIRED_STATE = 130;
	public static final Integer CONDITIONAL_OFFER = 140;
	public static final Integer ACCEPTED_STATE = 150;
	public static final Integer ACTIVE_STATE = 155;
	public static final Integer COMPLETED_STATE = 160;
	public static final Integer PENDING_CANCEL_STATE = 165;
	public static final Integer PROBLEM_STATE = 170;
	public static final Integer CLOSED_STATE = 180;
	public static final Integer DELETED_STATE = 105;
	
	//	For SL-15462:
	
	public static final String RESPOND = "Respond";
	public static final String INBOX = "Inbox";
	public static final String SCHEDULE = "Schedule";
	public static final String CONFIRM_APPT_WDW = "Confirm Appt window";
	public static final String PRINT_PAPERWORK = "Print Paperwork";
	public static final String CURRENT_ORDERS = "Current Orders";
	public static final String AWAITING_PAYMENT = "Awaiting Payment";
	public static final String ASSIGN_PROVIDER = "Assign Provider";
	public static final String MANAGE_ROUTE = "Manage Route";
	public static final String JOB_DONE = "Job Done";
	public static final String CANCELLATIONS = "Cancellations";
	public static final String RESOLVE_PROBLEM = "Resolve Problem";

	// For R12_0
	public static final String REVISIT_NEEDED = "Revisit Needed";


	public static final String PROVIDERS = "Provider(s)";
	public static final String MARKETS = "Market(s)";
	public static final String STATUS = "Status";
	public static final String APPOINTMENT_DATE = "Appointment Date";
	public static final String SUB_STATUS = "SubStatus";
	public static final String SCHEDULE_STATUS = "ScheduleStatus";
	public static final String PROVIDER = "Provider:";
	public static final String ROUTED_TO = "Routed To";
	public static final String JOB_DONE_SUBSTATUS = "Job Done Status";
	//For Current Order SubStatus
	public static final String CURRENT_ORDERS_SUBSTATUS = "Current Orders Status";
	//R12.0 Sprint3 Cancellations substatus
	public static final String CANCELLATIONS_SUBSTATUS = "Cancellations Status";
	//R12.0 sprint4 revisit substatus
	public static final String REVISIT_SUBSTATUS = "Revisit Status";

}