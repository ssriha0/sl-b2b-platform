/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 04-Jun-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.business.iBusiness.om.api.utils.constants;



/**
 * This class acts as a constant file
 * 
 * @author Infosys
 * @version 1.0
 */
public class PublicAPIConstant {
	
	public static final String CODE_PROPERTY_FILE = "resources/properties/api_responseCodes_enUS.properties";

	//Schema Details
	public static final String SORESPONSE_VERSION= "1.0";
	public static final String SORESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/soResponse";
	public static final String SCHEMA_INSTANCE= "http://www.w3.org/2001/XMLSchema-instance";

	//Filter Types(SL-15462)

	public static final String PROVIDERS_FILTER = "Provider(s):";
	public static final String MARKETS_FILTER = "Market(s):";
	public static final String STATUS_FILTER = "Status:";
	public static final String APPOINTMENT_DATE_FILTER = "Appointment Date:";
	public static final String SUB_STATUS_FILTER = "Sub Status:";
	public static final String SCHEDULE_STATUS_FILTER = "Schedule Status:";
	public static final String PROVIDER_FILTER = "Provider:";
	
	//tab names
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
	public static final String REVISIT_NEEDED = "Revisit Needed";
	

	//SL-15642
	public static final String ADD_NOTE_NAMESPACE = "http://www.servicelive.com/namespaces/addNoteRequest";
	public static final String UPDATE_SCHEDULE_DETAILS_NAMESPACE = "http://www.servicelive.com/namespaces/soRequest";
	public static final String TAB_REFRESH_NAMESPACE = "http://www.servicelive.com/namespaces/getTabListResponse";
	public static final String COUNTER_OFFER_NAMESPACE = "http://www.servicelive.com/namespaces/counterOffer";
	public static final String FETCH_SO_NAMESPACE = "http://www.servicelive.com/namespaces/soRequest";
	public static final String SO_FETCH_RESPONSE_NAMESPACE = "http://www.servicelive.com/namespaces/fetchOrderResponse";
	public static final String SO_FETCH_RESPONSE_SCHEMALOCATION = "http://www.servicelive.com/namespaces/fetchOrderResponse fetchOrderResponse.xsd";
	public static final String EDIT_LOCN_NOTES_NAMESPACE = "http://www.servicelive.com/namespaces/soRequest";
	public static final String SO_RESCHEDULE_RESPONSE_NAMESPACE="http://www.servicelive.com/namespaces/providerSORescheduleRequest";
	public static final String SO_RESCHEDULE_RESPONSE_SCHEMALOCATION = "http://www.servicelive.com/namespaces/fetchOrderResponse fetchOrderResponse.xsd";
	
}
