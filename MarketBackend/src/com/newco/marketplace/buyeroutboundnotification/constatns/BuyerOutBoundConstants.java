package com.newco.marketplace.buyeroutboundnotification.constatns;

public class BuyerOutBoundConstants {
	public static final String RESHEDULE_REASON_CODE 				= "Service Date has been changed.";
	public static final String RESHEDULE_FLAG_YES 					= "Y";
	public static final String SEQ_ZERO			 					= "0";
	public static final String SEQ_HYP			 					= "-";
	public static final String CLIENT_ID 							= "SVLO";
	public static final String NO_OF_ORDERS 						= "1";	
	public static final String NO_OF_RETRIES_FOR_NPS_NOTIFICATION 	= "no_of_retries_for_nps_notification";
	public static final String NPS_SCHEMA_LOCATION  				= "http://www.sears.iss.com/UpdateOrder/Request UpdateOrderRequestSchema.xsd ";
	public static final String NPS_XLMNS_XSI 						= "http://www.w3.org/2001/XMLSchema-instance";
	public static final String NPS_XLMNS 							= "http://www.sears.iss.com/UpdateOrder/Request";
	public static final String XML_VERSION							= "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";	
	public static final Integer NO_OF_RETRIES 						= 4;
	public static final String SEPERATOR			 				= "SOID:";
	public static final Integer MESSAGE_LENGTH 						= 132;
	public static final Integer MAX_NPS_ALLOWED_MESSAGE_COUNT		= 9;
	public static final Integer SEARS_RI_BUYER_ID					= 1000;
	public static final String POST									= "POST";
	public static final String PUT									= "PUT";
	public static final String GET									= "GET";
	public static final String NPS_EXCEPTION						= "NPS Webservice returned an empty response";
	public static final String TRUE									= "true";
	public static final String EMAIL_CONTENT						= "ServiceLive is unable to update NPS.";
	public static final String EMAIL_CONTENT_1						= " SL SO# ";
	public static final String EMAIL_CONTENT_2						= " SEQ# ";
	public static final String EMAIL_CONTENT_3						= ".Please see the attachement for more details.";
	public static final String EMAIL_SPACE							= " ";
	public static final String SUCCESS_CODE							= "000";
	public static final String ERROR_CODE							= "008";
	public static final String TIMEZONE_NPS_MESSGE					= "EST";
	//SLT-4048(Flag to decide whether to send  Reschedule event to NPS through Platform)
    public static final String INHOME_OUTBOUND_STOP_RESCHEDULE_EVENT_FLAG_INPLATFORM = "OFF";
    public static final String ON = "ON";
}
