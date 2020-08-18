package com.newco.marketplace.webservices.util;

public abstract class StagingConstants {
	
	public static final String TRANSACTION_TYPE_NEW = "NEW";
	public static final String TRANSACTION_TYPE_UPDATE = "UPDATE_FROM_SEARS";
	public static final String SL_STAGING_SERVICE = "StagingService";
	
	public static final short PERMIT_SKU_IND_ONE = 1;
	public static final short PERMIT_SKU_IND_ZERO = 0;
	public static final int DEFAULT_SKU_QTY = 1;
	public static final String DEFAULT_SKU_STATUS = "I";
	public static final String PRIMARY_SKU_TYPE = "R";
	
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
	
	//For NPS
	public static final Integer CLOSED_STATE = Integer.valueOf(180);
	public static final String NPS_ORDER_TO_PROCESS= "NPS_order_to_process";
	public static final String ASSIGNED_TECH = "AT";
	
	// Staging Upsell sku 
	
	public static final String UPSELL_SKU_CHARGE_CODE = "Z";
	public static final String UPSELL_SKU_COVERAGE = "PT";
	public static final String UPSELL_SKU_TYPE = "N";
	public static final String UPSELL_SKU_STATUS = "I";
	public static final Short  UPSELL_PERMIT_SKU_IND = new Short("0");
	public static final Double UPSELL_SKU_PRICE_RATIO = new Double(0.0000);
	
	public final static String FAILED_SERVICE_ORDER_NO = "000-0000000000-00";

}
