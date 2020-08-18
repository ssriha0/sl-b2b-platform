package com.servicelive.orderfulfillment.domain.type;

public enum LegacySOSubStatus {
	ABANDONED_WORK (1, "Abandoned Work"),
	ADDITIONAL_PART_REQUIRED (2, "Additional Part Required"),
	ADDITIONAL_WORK_REQUIRED (3, "Additional Work Required"),
	CANCELLED_BY_BUYER (4, "Cancelled by Buyer"),
	CANCELLED_BY_END_USER (5, "Cancelled by End User"),
	END_USER_NO_SHOW (7, "End User No Show"),
	JOB_DONE (8, "Job Done"),
	NO_COMMUNICATION_OR_NOTES (9, "No Communication or Notes"),
	OUT_OF_SCOPE_OR_SCOPE_MISMATCH (10, "Out of Scope/Scope Mismatch"),
	PART_BACK_ORDERED (11, "Part Back Ordered"),
	PART_ON_ORDER (12, "Part on Order"),
	PART_RECEIVED__HOLD_FOR_PICKUP (13, "Part Received - Hold for Pickup"),
	PART_RECEIVED_BY_END_USER (14, "Part Received by End User"),
	PART_RECEIVED_BY_PROVIDER (15, "Part Received by Provider"),
	PART_SHIPPED (16, "Part Shipped"),
	PROPERTY_DAMAGE (17, "Property Damage"),
	PROVIDER_NO_SHOW (18, "Provider No Show"),
	PROVIDER_NOT_QUALIFIED (19, "Provider Not Qualified to Complete Work"),
	PROVIDER_ON_SITE (20, "Provider On-site"),
	RESCHEDULED (21, "Rescheduled"),
	RESCHEDULED_BY_END_USER (22, "Rescheduled by End User"),
	RESCHEDULED_BY_PROVIDER (23, "Rescheduled by Provider"),
	RESCHEDULED_DUE_TO_END_USER_NO_SHOW (24, "Rescheduled due to End User No Show"),
	RESCHEDULED_DUE_TO_PROVIDER_NO_SHOW (25, "Rescheduled due to Provider No Show"),
	REWORK_NEEDED (26, "Rework Needed"),
	SITE_NOT_READY (27, "Site Not Ready"),
	SPEND_LIMIT_INCREASE_NEEDED (28, "Spend Limit Increase Needed"),
	UNPROFESSIONAL_ACTION_OR_BEHAVIOR (29, "Unprofessional Action / Behavior"),
	WORK_NOT_COMPLETED (30, "Work Not Completed"),
	RELEASED_BY_PROVIDER (31, "Released by Provider"),
	LOCKED_FOR_BUYER_EDIT (32, "Locked for Buyer Edit"),
	NEED_ADDITIONAL_PARTS (33, "Need additional parts"),
	CONFIRM_ADD_ON_FUNDS (34, "Confirm Add-on Funds"),
	DOCUMENTATION_REQUIRED (35, "Documentation Required"),
	MISSING_INFORMATION (36, "Missing Information"),
    CANCELLATION_REQUEST (37, "Cancellation Request"),
	FOLLOW_UP_NEEDED (38, "Follow-up Needed"),
	RESEARCH (39, "Research Needed"),
	TIME_WINDOW (40, "Time Window"),
	SCHEDULE_CONFIRMED (41, "Schedule Confirmed"),
	NEEDS_ATTENTION (42, "Needs Attention"),
	SCHEDULING_NEEDED (50, "Scheduling Needed"),
	QUEUED_FOR_GROUPING (51, "Queued For Grouping"),
	NO_SUBSTATUS (52, "No Substatus"),
	NO_HARDWARE_FAILURE (53, "No Hardware Failure"),
	CUSTOMER_DELAYED_SERVICE (54, "Customer Delayed Service"),
	CLOSE_AND_PAY_OR_COMPLETION_ISSUE (55, "Close and Pay / Completion Record - Issue"),
	NO_PROVIDERS (56, "No Providers"),
	TIME_WINDOW_EXPIRED__ARRIVAL (57, "Time Window Expired - Arrival"),
	TIME_WINDOW_EXPIRED__DEPARTURE (58, "Time Window Expired - Departure"),
	VALIDATE_POS_CANCELLATION (59, "Validate POS Cancellation"),
	AWAITING_PAYMENT (60, "Awaiting Payment"),
	PRE_CALL_COMPLETED (61, "Pre-Call Completed"),
	SCOPE_CHANGE (62, "Scope Change"),
	READY_FOR_POSTING (63, "Ready for Posting"),
	PENDING_WALLET_CONFIRMATION (64, "Pending Wallet Confirmation"),
	NOT_AUTO_CLOSED(66,"Not Auto Closed"),
	AUTO_CLOSED(65,"Auto Closed"),
	SERVICE_INCOMPLETE(67,"Service Incomplete"),
	PENDING_REVIEW(68,"Pending Review"),
	PENDING_RESPONSE(69,"Pending Response"),
	POS_CANCELLATIONS(70,"POS Cancellations"),
	MANUAL_REVIEW(100,"Manual Review"),
	REPEAT_REPAIR(101,"Repeat Repair"),
	PERMIT_REQUIRED(105,"Permit Required"),
	INSPECTION_REQUIRED(106,"Inspection Required"),
	FAILED_INSPECTION(107,"Failed Inspection"),
	JOBCODE_MISMATCH(108,"Jobcode Mismatch"),
	BUYER_RESCHEDULE_REQUIRED(109,"Buyer:  Reschedule Required"),
	CUSTOMER_ADULT_NOT_PRESENT(110,"Customer:  Adult Not Present"),
	CUSTOMER_CANCELLATION_REQUESTED(111,"Customer:  Cancellation Requested"),
	CUSTOMER_HOME_NOTE_TO_CODE(112,"Customer:  Home Not to Code"),
	CUSTOMER_MISSED_SERVICE_APPOINTMENT(113,"Customer:  Missed Service Appointment (No Show)"),
	CUSTOMER_RESCHEDULE_REQUESTED(114,"Customer: Reschedule Requested"),
	CUSTOMER_SITE_NOT_ACCESSIBLE(115,"Customer:  Site Not Accessible"),
	MERCHANDISE_DAMAGED(116,"Merchandise:  Damaged"),
	MERCHANDISE_NO_INFORMATION_AVAILABLE(117,"Merchandise:  No Information Available"),
	MERCHANDISE_NOT_AT_JOB_SITE(118,"Merchandise:  Not at Job Site (as expected)"),
	MERCHANDISE_NOT_AT_PICKUP_LOCATION(119,"Merchandise:  Not at Pick-Up Location"),
	MERCHANDISE_NOT_AVAILABLE_UNTIL_AFTER_DAY_OF_SERVICE(120,"Merchandise: NOT Available UNTIL AFTER DAY of Service"),
	MERCHANDISE_NOT_AVAILABLE_UNTIL_SAME_DAY_AS_SERVICE(121,"Merchandise: NOT Available UNTIL Same DAY AS Service"),
	MERCHANDISE_WRONG_PRODUCT(122,"Merchandise:  Wrong Product"),
	NEXT_DAY_SERVICE(123,"Next Day Service: Order Received After 3PM Cut-Of (Day Prior to Service)"),
	PROVIDER_FIRM_BUYER_AGREED_UPON_SERVICE_AREA(124,"Provider Firm:  Beyond Agreed Upon Service Area"),
	PROVIDER_FIRM_OTHER(125,"Provider Firm:  Other"),
	PROVIDER_FIRM_PERMITS_NOT_OBTAINED(126,"Provider Firm:  Permit(s) Not Obtained"),
	PROVIDER_FIRM_PROVIDER_TECHNICIAN_MISSED_APPOINTMENT(127,"Provider Firm: Provider Technician Ran Late and Missed Appointment"),
	PROVIDER_FIRM_REQUIRED_PARTS_ON_ORDER(128,"Provider Firm: Required Parts On Order (Not Yet Available)"),
	PROVIDER_FIRM_SAME_DAY_SERVICE_NOT_SUPPORTED(129,"Provider Firm:  Same Day Service Not Supported"),
	PROVIDER_FIRM_SERVICE_LOCATION_NOT_SUPPORTED_THAT_DAY_OF_WEEK(130,"Provider Firm: Service Location Not Supported That Day-of-Week"),
	PROVIDER_FIRM_SHORTAGE_OF_AVAILABLE_RESOURCE(131,"Provider Firm: Shortage of Available/Qualified Resource"),
	PROVIDER_FIRM_VEHICLE_ISSUE(132,"Provider Firm:  Vehicle Issue"),
	PROVIDER_FIRM_WEATHER_DELAY(133,"Provider Firm:  Weather Delay"),
	REVISIT_NEEDED(134,"Revisit Needed"),
	PENDING_CLAIM(135,"Pending Claim"),
	WORK_STARTED(136,"Work Started"),
	CNH_REVISIT_NEEDED(137,"Not Home - Revisit Needed"),
	NO_MATCHING_RULE(138,"No CAR Rule Match Found"),
	REPEAT_REPAIR_NO_PROVIDER(139,"Recall Provider Not Available");

    private final int subStatusId;
    private String description;

    private LegacySOSubStatus(int aStatusId, String description) {
        subStatusId = aStatusId;
        this.description = description;
    }

    public final int getId() {
        return subStatusId;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static LegacySOSubStatus fromId(int id) {
        switch (id) {
	        case 1: return ABANDONED_WORK;
	        case 2: return ADDITIONAL_PART_REQUIRED;
	        case 3: return ADDITIONAL_WORK_REQUIRED;
	        case 4: return CANCELLED_BY_BUYER;
	        case 5: return CANCELLED_BY_END_USER;
	        case 7: return END_USER_NO_SHOW;
	        case 8: return JOB_DONE;
	        case 9: return NO_COMMUNICATION_OR_NOTES;
	        case 10: return OUT_OF_SCOPE_OR_SCOPE_MISMATCH;
	        case 11: return PART_BACK_ORDERED;
            case 12: return PART_ON_ORDER;
	        case 13: return PART_RECEIVED__HOLD_FOR_PICKUP;
	        case 14: return PART_RECEIVED_BY_END_USER;
	        case 15: return PART_RECEIVED_BY_PROVIDER;
            case 16: return PART_SHIPPED;
	        case 17: return PROPERTY_DAMAGE;
	        case 18: return PROVIDER_NO_SHOW;
	        case 19: return PROVIDER_NOT_QUALIFIED;
	        case 20: return PROVIDER_ON_SITE;
	        case 21: return RESCHEDULED;
	        case 22: return RESCHEDULED_BY_END_USER;
	        case 23: return RESCHEDULED_BY_PROVIDER;
	        case 24: return RESCHEDULED_DUE_TO_END_USER_NO_SHOW;
	        case 25: return RESCHEDULED_DUE_TO_PROVIDER_NO_SHOW;
	        case 26: return REWORK_NEEDED;
	        case 27: return SITE_NOT_READY;
	        case 28: return SPEND_LIMIT_INCREASE_NEEDED;
	        case 29: return UNPROFESSIONAL_ACTION_OR_BEHAVIOR;
	        case 30: return WORK_NOT_COMPLETED;
	        case 31: return RELEASED_BY_PROVIDER;
	        case 32: return LOCKED_FOR_BUYER_EDIT;
	        case 33: return NEED_ADDITIONAL_PARTS;
	        case 34: return CONFIRM_ADD_ON_FUNDS;
	        case 35: return DOCUMENTATION_REQUIRED;
            case 36: return MISSING_INFORMATION;
            case 37: return CANCELLATION_REQUEST;
            case 38: return FOLLOW_UP_NEEDED;
            case 39: return RESEARCH;
            case 40: return TIME_WINDOW;
	        case 41: return SCHEDULE_CONFIRMED;
            case 42: return NEEDS_ATTENTION;
	        case 50: return SCHEDULING_NEEDED;
	        case 51: return QUEUED_FOR_GROUPING;
	        case 52: return NO_SUBSTATUS;
	        case 53: return NO_HARDWARE_FAILURE;
	        case 54: return CUSTOMER_DELAYED_SERVICE;
	        case 55: return CLOSE_AND_PAY_OR_COMPLETION_ISSUE;
            case 56: return NO_PROVIDERS;
	        case 57: return TIME_WINDOW_EXPIRED__ARRIVAL;
	        case 58: return TIME_WINDOW_EXPIRED__DEPARTURE;
	        case 59: return VALIDATE_POS_CANCELLATION;
            case 60: return AWAITING_PAYMENT;
	        case 61: return PRE_CALL_COMPLETED;
            case 62: return SCOPE_CHANGE;
	        case 63: return READY_FOR_POSTING;
            case 64: return PENDING_WALLET_CONFIRMATION;
            case 100:return MANUAL_REVIEW;
            case 105:return PERMIT_REQUIRED;
            case 106:return INSPECTION_REQUIRED;
            case 107:return FAILED_INSPECTION;
            case 108:return JOBCODE_MISMATCH;
            case 109:return BUYER_RESCHEDULE_REQUIRED;
            case 110:return CUSTOMER_ADULT_NOT_PRESENT;
            case 111:return CUSTOMER_CANCELLATION_REQUESTED;
            case 112:return CUSTOMER_HOME_NOTE_TO_CODE;
            case 113:return CUSTOMER_MISSED_SERVICE_APPOINTMENT;
            case 114:return CUSTOMER_RESCHEDULE_REQUESTED;
            case 115:return CUSTOMER_SITE_NOT_ACCESSIBLE;
            case 116:return MERCHANDISE_DAMAGED;
            case 117:return MERCHANDISE_NO_INFORMATION_AVAILABLE;
            case 118:return MERCHANDISE_NOT_AT_JOB_SITE;
            case 119:return MERCHANDISE_NOT_AT_PICKUP_LOCATION;
            case 120:return MERCHANDISE_NOT_AVAILABLE_UNTIL_AFTER_DAY_OF_SERVICE;
            case 121:return MERCHANDISE_NOT_AVAILABLE_UNTIL_SAME_DAY_AS_SERVICE;
            case 122:return MERCHANDISE_WRONG_PRODUCT;
            case 123:return NEXT_DAY_SERVICE;
            case 124:return PROVIDER_FIRM_BUYER_AGREED_UPON_SERVICE_AREA;
            case 125:return PROVIDER_FIRM_OTHER;
            case 126:return PROVIDER_FIRM_PERMITS_NOT_OBTAINED;
            case 127:return PROVIDER_FIRM_PROVIDER_TECHNICIAN_MISSED_APPOINTMENT;
            case 128:return PROVIDER_FIRM_REQUIRED_PARTS_ON_ORDER;
            case 129:return PROVIDER_FIRM_SAME_DAY_SERVICE_NOT_SUPPORTED;
            case 130:return PROVIDER_FIRM_SERVICE_LOCATION_NOT_SUPPORTED_THAT_DAY_OF_WEEK;
            case 131:return PROVIDER_FIRM_SHORTAGE_OF_AVAILABLE_RESOURCE;
            case 132:return PROVIDER_FIRM_VEHICLE_ISSUE;
            case 133:return PROVIDER_FIRM_WEATHER_DELAY;
            case 134:return REVISIT_NEEDED;
            case 135:return PENDING_CLAIM;
            case 136:return WORK_STARTED;
            case 137:return CNH_REVISIT_NEEDED;
            case 138:return NO_MATCHING_RULE;
            case 139:return REPEAT_REPAIR_NO_PROVIDER;
            default: return null;
        }
    }
}
