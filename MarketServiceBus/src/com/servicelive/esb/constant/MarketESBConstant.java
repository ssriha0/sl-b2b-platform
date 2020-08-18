package com.servicelive.esb.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * The Contants class that defines the constant values used by the ObjectMapper and ESB modules
 * @author pbhinga
 *
 */
public class MarketESBConstant {

	public static final String ORIGINAL_FILE_FEED_NAME = "org.jboss.soa.esb.gateway.original.file.name";
	public static final String ORIGINAL_FILE_ENTRY_TIME = "org.jboss.soa.esb.message.time.dob";
	public static final String UNMARSHALLED_OBJ_GRAPH = "ServiceOrdersUnmarshalled";
	public static final String UNMARSHALLED_OBJ_GRAPH_NOTE = "ServiceOrderUnmarshalledNotes";
	public static final String UNMARSHALLED_OBJ_GRAPH_ACK = "ServiceOrderUnmarshalledAck";
	public static final String IS_LEGACY_ORDER = "IsLegacyOrder";
	public static final String LEGACY_ORDERS = "LegacyOrders";
	
	public static final String INTEGRATION_TYPE = "integrationType";
	public static final String INTEGRATION_BATCH_ID = "integrationBatchId";
	public static final String OMS_ERROR_FILE_PATH = "OMS_ERROR_FILE_PATH";
	public static final String OMS_NEW_ERROR_FILE_PATH = "OMS_NEW_ERROR_FILE_PATH";
	public static final String ASSURANT_ERROR_FILE_PATH = "Assurant_ERROR_FILE_PATH";
	public static final String ASSURANT_NEW_ERROR_FILE_PATH = "Assurant_NEW_ERROR_FILE_PATH";
	public static final String ASSURANT_NEW_UPDATE_FILE_PATH = "Assurant_NEW_UPDATE_FILE_PATH";
	public static final String HSR_NEW_UPDATE_FILE_PATH = "HSR_NEW_UPDATE_FILE_PATH";
	public static final String ERROR_FILE_SUFFIX = "ERROR_FILE_SUFFIX";
	public static final String ARCHIVE_DIR = "archiveDir";
	public static final String GATEWAY_DIR = "gatewayDir";
	public static final String BUYER_RESOURCE_ID = "buyerResourceId";
	
	public static final String UNMARSHALLED_NPS_AUDIT_OBJ = "NPSAuditOrderUnmarshalled";
	public static final String TRANSLATED_NPS_AUDIT_OBJ = "NPSAuditOrderTranslated";
	public static final String AUDIT_EMAIL_DELIMITER = "|";
		
	public static final String MAPPED_OBJ_GRAPH_NEW = "ServiceOrdersMappedNew";
	public static final String MAPPED_OBJ_GRAPH_UPDATE = "ServiceOrdersMappedUpdate";
	public static final String MAPPED_NOTE_OBJ_GRAPH = "NotesMapped";
	public static final String MAPPED_ACK_OBJ_GRAPH = "AckMapped";
	public static final String MAPPED_ROUTEOBJ_GRAPH = "MappedServiceOrdersForRouting";
	
	public static final String TRANSLATED_OBJ_GRAPH_NEW = "ServiceOrdersTranslatedNew";
	public static final String TRANSLATED_OBJ_GRAPH_UPDATE = "ServiceOrdersTranslatedUpdate";
	public static final String TRANSLATED_ROUTEOBJ_GRAPH = "TranslatedServiceOrdersForRouting";
	public static final String TRANSLATED_NOTE_OBJ_GRAPH = "NotesTranslated";
	public static final String TRANSLATED_ACK_OBJ_GRAPH = "AckTranslated";
	
	public static final String ROUTEOBJ_GRAPH = "ServiceOrdersForRouting";
	public static final String SL_ORDER_SERVICE = "slOrderService";
	public static final String SL_STAGING_SERVICE = "StagingService";
	public static final String HSR_STAGING_SERVICE = "hsrStagingService";
	public static final String FILE_FEED_NAME = "fileFeedName";
	public static final String FILE_FEED_TIME = "fileFeedTime";
	public static final String ROUTE_REQUEST_OBJECT_GRAPH = "slRouteRequests";
	public static final String CLIENT_KEY = "clientKey";
	public static final String INCIDENT_STRING = "incidentString";
	public static final String INTERNAL="internal";
	public static final String NO_UPDATE_NOTE_MESSAGE="No update has been made to the Service Order";
	public static final String NO_UPDATE_NOTE_SUBJECT="Service Order Update Received";
	public static final String NO_UPDATE_NOTE_MESSAGE_HSR="The Service Order is not in a valid state to process the update. The update was not processed";
	public static final String NO_UPDATE_NOTE_SUBJECT_HSR="Update Received from NPS";
	
	// Staging WebService Related Constants
	public static final String STAGING_WEB_SERVICE = "StagingWebService";
	
	//OMS Staging story
	public static final String UNMARSHALLED_SKU_LIST_GRAPH="skuList";
	public static final String UNMARSHALLED_STAGE_OBJ_GRAPH = "ServiceOrderUnmarshalledStagingDetails";
	public static final String PERMIT_SKU= "99888";
	public static final short PERMIT_SKU_IND_ONE = 1;
	public static final short PERMIT_SKU_IND_ZERO = 0;
	public static final String INSTALLATION_ORDER= "ServiceOrder";
	public static final String JOBCODE= "JobCode";
	public static final String SALES_CHECK_ITEM= "SalesCheckItem";
	public static final int DEFAULT_SKU_QTY = 1;
	
	public static final String FAILED_PROCESS_STATUS = "Failed";
	
	public static final Double ZERO = 0.00;
	public static final String EMPTY_STR = "";
	
	public static final int PARTS_SUPPLIED_BY_BUYER = 1;
	public static final int PARTS_SUPPLIED_BY_PROVIDER = 2;
	public static final int PRIMARY_PHONE = 1;
	public static final int ALTERNATE_PHONE = 2;

	public static final int PRIMARY_JOB_CODE = 1;
	public static final int ALTERNAME_JOB_CODE = 0;
	public static final String JOB_CODE_PRIMARY = "R";
	public static final String QUANTITY = "1";
	public static final String COUNTRY = "US";
	public static final String NEXT_DAY_TASK = "01000";

	//Sales Check node labels that associate with values from OMS feed
	public static final String SALES_CHECK_NUM = "Sales Check Information Number: ";
	public static final String DATE_LABEL = " Date: ";
	public static final String DIVISION_LABEL = " Division: ";
	public static final String ITEM_NUMBER_LABEL = " Item Number: ";
	public static final String DESC_LABEL = " Description: ";
	public static final String QUANTITY_LABEL = " Quantity: ";
	public static final String GIFT_FLAG_LABEL = " Gift Flag: ";
	public static final String GIFT_DATE_LABEL = " Gift Date: ";
	public static final String NEWLINE_CHAR = "\r\n";

	//Custom Ref fields
	public static final String CUSTOM_REF_PREF_LANG = "PREFERRED LANGUAGE";
	public static final String CUSTOM_REF_UNIT_NUM = "UNIT NUMBER";
	public static final String CUSTOM_REF_ORDER_NUM = "ORDER NUMBER";
	public static final String CUSTOM_REF_UNIT_NUM_HSR = "UnitNumber";
	public static final String CUSTOM_REF_ORDER_NUM_HSR = "OrderNumber";	
	public static final String CUSTOM_REF_SALES_CHECK_NUM = "SALES CHECK NUM 1";
	public static final String CUSTOM_REF_SALES_CHECK_DATE = "SALES CHECK DATE 1";
	public static final String CUSTOM_REF_SALES_CHECK_TIME = "SALES CHECK TIME 1";
	public static final String CUSTOM_REF_STORE_NUMBER = "STORE NUMBER";
	public static final String CUSTOM_REF_SERVICE_REQUESTED = "SERVICE REQUESTED";
	public static final String CUSTOM_REF_SELLING_ASSOC = "SELLING ASSOC";
	public static final String CUSTOM_REF_ORDERID_STRING = "ORDER_ID_STRING";
	public static final String CUSTOM_REF_DATE_CALCULATION_METHOD = "DATE CALCULATION METHOD";
	public static final String CUSTOM_REF_ASSOCIATED_INCIDENT = "ASSOCIATED INCIDENT";
	public static final String CUSTOM_REF_INCIDENT_ID = "INCIDENTID";
	public static final String CUSTOM_REF_SL_INCIDENT_ID = "SERVICELIVE INCIDENT ID";
	public static final String CUSTOM_REF_CLASSCODE = "CLASS CODE";
	public static final String CUSTOM_REF_RETAILER = "RETAILER";
	public static final String CUSTOM_REF_CONTRACTDATE = "CONTRACT DATE";
	public static final String CUSTOM_REF_CONTRACTNUMBER = "CONTRACT NUMBER";
	public static final String CUSTOM_REF_TEMPLATE_NAME = "TEMPLATE NAME";
	public static final String CUSTOM_REF_PRIMARY_PARTNUMBER = "PRIMARY PART NUMBER";
	public static final String CUSTOM_REF_PARTSLABORFLAG = "PARTS LABOR FLAG";
	public static final String CUSTOM_REF_MERCHANDISE_AVAILABILITY_DATE = "Merchandise Availability Date";
	public static final String CUSTOM_REF_CONTRACTTYPE ="INCIDENT CONTRACT TYPE";
	public static final String CUSTOM_REF_PICKUP_LOCATION_CODE = "PICKUP LOCATION CODE";
	public static final String CUSTOM_REF_SPECIALTY_CODE = "SPECIALTY CODE";	
	public static final String CUSTOM_REF_MERCHANDISE_CODE = "MERCHANDISE CODE";	
	public static final String CUSTOM_REF_BRAND = "BRAND";	
	public static final String CUSTOM_REF_MAIN_SKU = "Main SKU";
	public static final String CUSTOM_REF_CUSTOMER_NUMBER = "CUSTOMER NUMBER";
	public static final String CUSTOM_REF_PAYMENT_METHOD_IND = "PAYMENT METHOD IND";
	public static final String CUSTOM_REF_CHARGE_ACCT_NO = "CHARGE ACCT NO";
	public static final String CUSTOM_REF_CHARGE_ACCT_EXP = "CHARGE ACCT EXP";
	public static final String CUSTOM_REF_PRIORITY_IND = "PRIORITY IND";
	public static final String CUSTOM_REF_COVERAGE_TYPE_LABOR = "COVERAGE TYPE LABOR";
	public static final String CUSTOM_REF_COVERAGE_TYPE_PARTS = "COVERAGE TYPE PARTS";
	public static final String CUSTOM_REF_COVERAGE_TYPE_LABOR_HSR = "CoverageTypeLabor";
	public static final String CUSTOM_REF_COVERAGE_TYPE_PARTS_HSR = "CoverageTypeParts";
	public static final String CUSTOM_REF_DIVISION = "DIVISION";
	public static final String CUSTOM_REF_MODEL = "MODEL";
    public static final String CUSTOM_REF_Model_Number = "Model Number";
    public static final String CUSTOM_REF_Serial_Number = "Serial Number";
	public static final String CUSTOM_REF_SERIAL_NUMBER = "SERIAL NUMBER";
	public static final String CUSTOM_REF_PURCHASE_DATE = "PURCHASE DATE";
	public static final String CUSTOM_REF_PROMOTION_IND = "PROMOTION IND";
	public static final String CUSTOM_REF_PROC_ID = "PROC ID";
	public static final String CUSTOM_REF_CONTRACT_NUMBER = "CONTRACT NUMBER";
	public static final String CUSTOM_REF_CONTRACT_EXP = "CONTRACT EXP";
	public static final String CUSTOM_REF_AUTH_NUMBER = "AUTH NUMBER";
	public static final String CUSTOM_REF_PROCESS_ID = "ProcessID";
    public static final String CUSTOM_REF_MODIFIED_BY_EMPLOYEE_ID = "MODIFIED_BY_EMPLOYEE_ID";
    public static final String CUSTOM_REF_EXTERNAL_STATUS = "EXTERNAL_STATUS";
    public static final String CUSTOM_REF_FINAL_PERMIT_PRICE = "FINAL PERMIT PRICE";

		
	//TODO - Put a better mechanism for showing values in the email sent in the event of an error
	public static final String SALES_CHECK_DATE_EMAIL_TITLE = "Sales Check Date";
	public static final String SERVICE_UNIT_NUMBER_EMAIL_TITLE = "Service Unit Number";
	public static final String SERVICE_ORDER_NUMBER_EMAIL_TITLE = "Service Order Number";
	public static final String SALES_CHECK_NUMBER_EMAIL_TITLE = "Sales Check Number";
	
	public static final String BODY_TEXT = "http://wiki.intra.sears.com/confluence/display/SOUQ/ServiceLive+Environments";

	public static final int ZIP_LONG = 9;
	public static final int ZIP_SHORT = 5;
	
	public static final int DEFAULT_CONTACT_TYPE_ID = 10;
	
	public static final int HOME_PHONE_TYPE = 0;
	public static final int WORK_PHONE_TYPE = 1;
	public static final int CELL_PHONE_TYPE = 2;
	public static final int OTHER_PHONE_TYPE = 5;
	
	public static final int COMMERCIAL_LOCATION = 1;
	public static final int RESIDENTIAL_LOCATION = 2;
	
	//Commercial type (non-residential) customer location
	public static final String KEY_WORK_PHONE_TYPE = "S";
	
		
	public static final String DEFAULT_DATE = "1111-11-11";
	public static final String KEY_PROMISED_DATE = "P";
	public static final String KEY_DELIVERY_DATE = "D";
	public static final String KEY_NEXT_DATE = "N";
	public static final String KEY_CURRENT_DATE = "C";
	
	public static final String TX_MODE_UPDATE = "UPDATE_FROM_SEARS";
	public static final String TX_MODE_NEW = "NEW";
	
	public static final String EMAIL_NEWLINE_CHAR = "<BR>";
	
	public static final String NEXT_DAY_TASK_COMMENTS="Next Day 01000 ";
	
	//Shipping Carrier
	public static final int FEDEX = 2;
	
	//Assurant
	public static final String CLASSCODE = "ClassCode";
	public static final String ASSURANT_ACK_KEY = "ACKNOWLEDGEMENT";
	public static final String ASSURANT_INCIDENT_NOTE_KEY = "INFO";
	public static final String ASSURANT_INCIDENT_CANCEL_KEY = "CANCEL";
	public static final String[] ASSURANT_ACCIDENTAL_DAMAGE_CODES = {"200712","200713","200721","200816","200817","200818"};
	public static final String ASSURANT_ACCIDENTAL_DAMAGE= "Accidental Damage"; 
	
	//For Error Logging
	public static final String EX_PARSE_ERROR = "UMPE01";
	public static final String EX_PARSE_ERROR_MESSAGE = "Error while parsing the given file feed"; 
	public static final String EX_TRANSLATOR_ERROR = "TRE01";
	public static final String EX_TRANSLATOR_ERROR_MESSAGE = "Exception during Translation service"; 
	public static final String EX_TRANSLATOR_SERVICEORDER_ERROR = "TRE02";
	public static final String EX_TRANSLATOR_SERVICEORDER_ERROR_MESSAGE = "ServiceOrder list null";
	public static final String EX_TRANSLATOR_REQUEST_NULL_ERROR = "TRE03";
	public static final String EX_TRANSLATOR_REQUEST_NULL_ERROR_MESSAGE = "CreateDraftRequest New & Update lists null";
	public static final String EX_TRANSLATOR_SO_LIST_SIZE_ERROR = "TRE04";
	public static final String EX_TRANSLATOR_SO_LIST_SIZE_ERROR_MESSAGE = "ServiceOrder List size mismatch";
	
	//Status
	public final static String CANCELLED_STATUS = "Cancelled";
	public final static String VOIDED_STATUS = "Voided";
	public final static String CLOSED_STATUS = "Closed";
	public final static String DELETED_STATUS="Deleted";
	public final static String COMPLETED_STATUS="Completed";
	
	
	public class ClientStatus {
		public static final String OMS_CANCELLED = "CA";
		public static final String OMS_VOIDED = "CV";
		public static final String OMS_CLOSED = "CO";
		public static final String OMS_EDITED = "ED";
		public static final String OMS_WAITING_SERVICE = "WS";
		public static final String OMS_ASSIGNED_TECH = "AT";
		/* ASSURANT client status*/
		public static final String ASSURANT_CANCELLED = "CANCEL";
		public static final String ASSURANT_NEW = "NEW";
		public static final String ASSURANT_UPDATE = "UPDATE";
		public static final String ASSURANT_ACKNOWLEDGEMENT = "ACKNOWLEDGEMENT";
		public static final String ASSURANT_INFO = "INFO";
		/* HSR client status*/
		public static final String HSR_WAITING_SERVICE = "WS";
		public static final String HSR_AWAITING_ESTIMATE_APPROVAL = "AA";
		public static final String HSR_ESTIMATE_DECLINED = "ED";
		public static final String HSR_ASSIGNED_TO_TECHNICIAN = "AT";
		public static final String HSR_PICK_UP_FOR_SHOP_REPAIR = "PU";
		public static final String HSR_BACK_SHIPPED = "BS";
		public static final String HSR_WAITING_DELIVERY = "WD";
		public static final String HSR_WAITING_PARTS = "WP";
		public static final String HSR_PARTS_ARRIVED = "PA";
		public static final String HSR_COMPLETE = "CO";
		public static final String HSR_CUSTOMER_PICK_UP_NEEDED = "CP";
		public static final String HSR_CALL_AVOIDED = "CV";
		public static final String HSR_MERCHANDISE_TRANSIT_TO_REPAIR_SITE  = "IT";
		public static final String HSR_OTHER = "O";
		public static final String HSR_CANCELLED = "CA";
		public static final String HSR_RESCHEDULING_NEEDED = "RN";
		public static final String HSR_NOT_HOME = "NH";
		
		public static final String SL_CANCELLED = "CANCELLED"; //OMS
		public static final String SL_VOIDED = "VOIDED"; // OMS
		public static final String SL_CLOSED = "CLOSED"; // OMS
		public static final String SL_EDITED = "EDITED"; // OMS
		public static final String SL_WAITING_SERVICE = "WAIT_SERVICE"; // OMS, HSR
		public static final String SL_ASSIGNED_TECH = "ASSIGNED_TECH"; // OMS, HSR
		public static final String SL_NEW = "NEW";
		public static final String SL_UPDATE = "UPDATED";
		public static final String SL_ACKNOWLEDGEMENT = "ACKNOWLEDGEMENT";
		public static final String SL_INFO = "INFO";
		
		public static final String SL_AWAITING_ESTIMATE_APPROVAL = "AWAITING_ESTIMATE_APPROVAL";
		public static final String SL_ESTIMATE_DECLINED = "ESTIMATE_DECLINED";
		public static final String SL_PICK_UP_FOR_SHOP_REPAIR = "PICK_UP_FOR_SHOP_REPAIR";
		public static final String SL_BACK_SHIPPED = "BACK_SHIPPED";
		public static final String SL_WAITING_DELIVERY = "WAITING_DELIVERY";
		public static final String SL_WAITING_PARTS = "WAITING_PARTS";
		public static final String SL_PARTS_ARRIVED = "PARTS_ARRIVED";
		public static final String SL_COMPLETE = "COMPLETE";
		public static final String SL_CUSTOMER_PICK_UP_NEEDED = "CUSTOMER_PICK_UP_NEEDED";
		public static final String SL_CALL_AVOIDED = "CALL_AVOIDED";
		public static final String SL_MERCHANDISE_TRANSIT_TO_REPAIR_SITE  = "IT";
		public static final String SL_OTHER = "OTHER";
		public static final String SL_RESCHEDULING_NEEDED = "RESCHEDULING_NEEDED";
		public static final String SL_NOT_HOME = "NOT_HOME";
		
		
	}
	
	public class Client {
		public static final String OMS = "OMS";
		public static final String ASSURANT = "Assurant";
		public static final String HSR = "HSR";
		public static final String NPS_RI_AUDIT= "NpsRIAudit";
	}
	
	public static Map<String,String>  clientToSLStatus = new HashMap<String, String>();
	static  {
			  clientToSLStatus.put(ClientStatus.HSR_WAITING_SERVICE , ClientStatus.SL_WAITING_SERVICE);
			  clientToSLStatus.put(ClientStatus.HSR_AWAITING_ESTIMATE_APPROVAL , ClientStatus.SL_AWAITING_ESTIMATE_APPROVAL);
			  clientToSLStatus.put(ClientStatus.HSR_ESTIMATE_DECLINED , ClientStatus.SL_ESTIMATE_DECLINED);
			  clientToSLStatus.put(ClientStatus.HSR_ASSIGNED_TO_TECHNICIAN , ClientStatus.SL_ASSIGNED_TECH);
			  clientToSLStatus.put(ClientStatus.HSR_PICK_UP_FOR_SHOP_REPAIR , ClientStatus.SL_PICK_UP_FOR_SHOP_REPAIR);
			  clientToSLStatus.put(ClientStatus.HSR_BACK_SHIPPED , ClientStatus.SL_BACK_SHIPPED);
			  clientToSLStatus.put(ClientStatus.HSR_WAITING_DELIVERY , ClientStatus.SL_WAITING_DELIVERY);
			  clientToSLStatus.put(ClientStatus.HSR_WAITING_PARTS , ClientStatus.SL_WAITING_PARTS);
			  clientToSLStatus.put(ClientStatus.HSR_PARTS_ARRIVED , ClientStatus.SL_PARTS_ARRIVED);
			  clientToSLStatus.put(ClientStatus.HSR_COMPLETE , ClientStatus.SL_COMPLETE);
			  clientToSLStatus.put(ClientStatus.HSR_CUSTOMER_PICK_UP_NEEDED , ClientStatus.SL_CUSTOMER_PICK_UP_NEEDED);
			  clientToSLStatus.put(ClientStatus.HSR_CALL_AVOIDED , ClientStatus.SL_CALL_AVOIDED);
			  clientToSLStatus.put(ClientStatus.HSR_MERCHANDISE_TRANSIT_TO_REPAIR_SITE , ClientStatus.SL_MERCHANDISE_TRANSIT_TO_REPAIR_SITE);
			  clientToSLStatus.put(ClientStatus.HSR_CANCELLED , ClientStatus.SL_CANCELLED);
			  clientToSLStatus.put(ClientStatus.HSR_RESCHEDULING_NEEDED , ClientStatus.SL_RESCHEDULING_NEEDED);
			  clientToSLStatus.put(ClientStatus.HSR_NOT_HOME, ClientStatus.SL_NOT_HOME);
			  clientToSLStatus.put(ClientStatus.HSR_OTHER, ClientStatus.SL_OTHER);
	}
		
	
	public static final String HSR_TX_MODE_UPDATE = "UPDATE_FROM_HSR";
	public static final String HSR_TX_MODE_UPDATE_PRIOR_NEW = "UPDATE_PRIOR_NEW_HSR";
	
	public static final String HSR_CLIENT_STATUS_CODE = "HSR_CLIENT_STATUS_CODE";
	public static final String HSR_MODIFYING_UNIT_ID = "HSR_MODIFYING_UNIT_ID";
	public static final String HSR_ORDER_TAKEN_TIME = "Order Taken Time : ";
	public static final String HSR_ORDER_TAKEN_DATE = "Order Taken Date : ";
	public static final String HSR_SERVICE_REQUESTED = "Service Requested : ";
	public static final String HSR_PROT_AGR_TYPE = "Protection Agreement Type : ";
	public static final String HSR_PROT_AGR_PLAN_TYPE= "Protection  Agreement Plan Type : ";
	public static final String HSR_PROT_AGR_EXP_DATE = "Protection Agreement Expiration Date : ";
	public static final String HSR_ORG_DELIVERY_DATE = "Original Delivery Date : ";	
	public static final String HSR_NO_NEW_DATE = "NO NEW DATA";
}
