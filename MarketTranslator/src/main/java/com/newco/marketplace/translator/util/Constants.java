package com.newco.marketplace.translator.util;

public abstract class Constants {
	
	public final static int RANGE_DATE = 2;
	public final static int FIXED_DATE = 1;
	
	public abstract class ApplicationPropertiesConstants {
		public static final String SL_ENDPOINT_KEY = "service_live_ws_endpoint";
		public static final String STAGING_ENDPOINT_KEY = "staging_ws_endpoint";
		public static final String HSR_BUYER_ID = "HSR_buyer_id";
		public static final String NPS_NUM_ORDERS_TO_CLOSE="NPS_num_orders_to_close";
		public static final String OF_ROLLOUT_UNITS = "orderfulfillment_rollout_stores";
		public static final String USE_NEW_OF_PROCESS = "use_new_orderfulfillment_process";
		public static final String OF_TEST_MODE = "orderfulfillment_test_mode";
		public static final String MAINTAIN_LEGACY_STAGING_DATA = "maintain_legacy_staging_data";

	}

	public static final String DELIVERY = "Delivery";
	public static final String DELIVERY_TASK_NAME = "Deliver Merchandise";
	public static final String DELIVERY_TASK_COMMENTS = "Pick up merchandise at designated Sears Holdings facility (reference the parts section of the service order for pick up location and description of the merchandise).  Deliver merchandise to customer.";
	public static final int PRIMARY_JOB_CODE = 1;
	public static final int ALTERNAME_JOB_CODE = 0;
	public static final short PERMIT_SKU_IND_ONE = 1;
	public static final short PERMIT_SKU_IND_ZERO = 0;
	public static final String TRANSACTION_TYPE_NEW = "NEW";
	public static final String TRANSACTION_TYPE_UPDATE = "UPDATE_FROM_SEARS";
	public static final String SL_STAGING_SERVICE = "StagingService";
	public static final int DEFAULT_SKU_QTY = 1;
	public static final String DEFAULT_SKU_STATUS = "I";
	public static final String DEFAULT_STORE_NO = "09300";
	public static final double DEFAULT_PRICE_RATIO_VALUE = 0.0001;
	public static final String UNMAPPED_TASK = "(Unmapped Task)";
	public static final int DEFAULT_PRIMARY_SKILL_ID = 800;
	
	//For Error Logging
	public static final String EX_SCHEDULING_ERROR = "TRSH01";
	public static final String EX_SCHEDULING_ERROR_MESSAGE = "Service Order could not be scheduled"; 
	public static final String EX_PRIMARY_SKILL_ERROR = "TRPS01";
	public static final String EX_PRIMARY_SKILL_ERROR_MESSAGE = "Error setting primary skill node id";
	public static final String EX_SKU_ERROR = "TRSK01";
	public static final String EX_SKU_ERROR_MESSAGE = "SKU could not be translated from request.";
	public static final String EX_PRICING_ERROR = "TRPR01";
	public static final String EX_PRICING_ERROR_MESSAGE = "Error pricing Service Order";
	public static final String EX_LOCATION_ERROR = "TRLC01";
	public static final String EX_LOCATION_ERROR_MESSAGE = "The service location could not be found";
	public static final String EX_STORE_CODE_ERROR = "TRSC01";
	public static final String EX_STORE_CODE_ERROR_MESSAGE = "The store code could not be found";
	public static final String EX_SKU_SKILL_MAPPING_ERROR = "TRSC01";
	public static final String EX_SKU_SKILL_MAPPING_ERROR_MESSAGE = "Service Order has SKUs that could not be mapped to Skill Nodes";
	
	//Custom Ref fields
	public static final String CUSTOM_REF_PREF_LANG = "PREFERRED LANGUAGE";
	public static final String CUSTOM_REF_UNIT_NUM = "UNIT NUMBER";
	public static final String CUSTOM_REF_ORDER_NUM = "ORDER NUMBER";
	public static final String INCIDENT_ID = "INCIDENTID";
	
	//For NPS
	public static final Integer CLOSED_STATE = Integer.valueOf(180);
	
	// Staging Upsell sku 
	public static final String UPSELL_SKU_CHARGE_CODE = "Z";
	public static final String UPSELL_SKU_COVERAGE = "PT";
	public static final String UPSELL_SKU_TYPE = "N";
	public static final String UPSELL_SKU_STATUS = "I";
	public static final Short  UPSELL_PERMIT_SKU_IND = new Short("0");
	public static final Double UPSELL_SKU_PRICE_RATIO = new Double(0.0000);
	public static final Integer MISCELLANEOUS_SKU_INDICATOR = new Integer(1);
	public static final String PERMIT_SKU = "99888";
	public static final Integer NOT_MISCELLANEOUS_SKU_INDICATOR = new Integer(0);
	public static final String CALL_COLLECT_COVERAGE_TYPE = "CC";
	
	//buyer sku
	public static final String SKU_PRICE_TYPE_VARIABLE = "VARIABLE";
	public static String[] WORK_ORDER_PERMIT_SKUS = {"03187", "06187", "09187", "22187", "26187", "42187", "46187", "57187", "71187", "83187", "99888"};
	
	// addon 
	public static final String ADD_ON_ATTRIBUTE_TYPE = "ADDON";
	public static final String ADD_ON_ATTRIBUTE_TYPE_REQ = "ADDONREQ";
	public static final Integer DEFAULT_REQ_ADDON_QTY = new Integer(1);
	
	//For email
	public static final String NEWLINE_CHAR = "\r\n";

	//BUYER UPSELL AND SERVICE
	//ENTERPRISE USER ID FOR PRICING
	public static final Double MARGIN_RATE_UNTIL_SPECIALTY_ADD_ON_TABLE_HAS_CORRECT_COLUMNS = 
		new Double( 0.4 );

	
	public static final String PRIMARY_SKU_TYPE = "R";
	
	public static String MARKET_WS_URL;
	public static String STAGING_WS_URL;
	//For Notes request
	public static final Integer BUYER_ROLE_ID=Integer.valueOf(3);
	public final static String WEBSERVICE="WebService";
	public static final String SO_NOTE_PUBLIC_ACCESS = "0";
	
	//For mapping skills as custom refs
	public static final int MAIN_SERVICE_CAT_SKILL_LEVEL = 1;
	public static final int CATEGORY_SKILL_LEVEL = 2;
	public static final int SUB_CAT_SKILL_LEVEL = 3;
	public static final String CUSTOM_REF_MAIN_SERVICE_CAT= "MAIN SERVICE CATEGORY";
	public static final String CUSTOM_REF_CATEGORY = "CATEGORY";
	public static final String CUSTOM_REF_SUB_CATEGORY= "SUB-CATEGORY";
	public static final String CUSTOM_REF_SKILL = "SKILL";
	public static final String CUSTOM_REF_TEMPLATE_NAME = "TEMPLATE_NAME";
	
	public static final String HSR_ORDER_TAKEN_TIME = "Order Taken Time : ";
	public static final String HSR_ORDER_TAKEN_DATE = "Order Taken Date : ";
	public static final String HSR_SERVICE_REQUESTED = "Service Requested : ";
	public static final String HSR_PROT_AGR_TYPE = "Protection Agreement Type : ";
	public static final String HSR_PROT_AGR_PLAN_TYPE= "Protection  Agreement Plan Type : ";
	public static final String HSR_PROT_AGR_EXP_DATE = "Protection Agreement Expiration Date : ";
	public static final String HSR_ORG_DELIVERY_DATE = "Original Delivery Date : ";
	
	//Pricing Schemas
	public static final String MARGIN_ADJUST_SCHEMA = "MARGIN_ADJUST";
	
	public class Client {
		public static final String OMS = "OMS";
		public static final String ASSURANT = "Assurant";
		public static final String HSR = "HSR";
	}
}
