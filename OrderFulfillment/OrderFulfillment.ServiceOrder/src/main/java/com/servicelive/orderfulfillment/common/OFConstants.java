package com.servicelive.orderfulfillment.common;

public class OFConstants {

	public final static String SL_ADMIN_USERNAME = "sladmin";
	public final static Integer SL_ADMIN_ROLE_ID = 2;

    public final static String SVC_TMPLT_TYP_DELIVERY = "Delivery";

    public static final String DELIVERY_TASK_NAME = "Deliver Merchandise";
    public static final String DELIVERY_TASK_COMMENTS = "Pick up merchandise at designated facility (reference the parts section of the service order for pick up location and description of the merchandise). Deliver merchandise to customer.";

    public static final int ALTERNAME_JOB_CODE = 0;
    public static final String PERMIT_SKU= "99888";
    
    public static final Long HSR_BUYER_ID= 3000L;
    public static final String TRUE = "true";
    // SO Receipts constants - LEDGER ENTITY TYPE IDs
    public final static Integer LEDGER_ENTITY_TYPE_ID_PROVIDER = Integer.valueOf(20);
    public final static Integer LEDGER_ENTITY_TYPE_ID_SERVICELIVE = Integer.valueOf(30);
    
	//FUNDING TYPE ID
	public final static int PREFUNDED=30;
	public final static int DIRECT_FUNDED=20;
	public final static int SHC_FUNDED=40;
	public final static int CONSUMER_FUNDED=70;

    
    public static final  Integer PRIMARY_TASK = 0;
	public static final  Integer PERMIT_TASK = 1;
	public static final  Integer DELIVERY_TASK = 2;
	public static final  Integer NON_PRIMARY_TASK = 3;
	
	public static final  Integer ATT_BUYER = 512353;
	//R12_3:SL-20664
	public static final  Integer SL_DIRECT_BUYER = 9000;
	public static final  Long SEARS_BUYER = 1000L;
	public static final  Long ASSURANT_BUYER = 1085L;
	public static final  Long NEST_BUYER = 5555L;
	public static final  Long FLUSHMATE_BUYER = 5566L;
	public static final String  DEFAULT_TEMPLATE_FOR_SEARS ="default_template_for_sears";
	public static final String  DEFAULT_TEMPLATE_FOR_ASSURANT ="default_template_for_assurant";
	public static final String  BUYER_RESCHEDULE ="BUYER_REQUEST_RESCHEDULE";
	public static final String  PROVIDER_RESCHEDULE ="PROVIDER_REQUEST_RESCHEDULE";
	public static final String  BUYER_CANCEL_RESCHEDULE ="BUYER_CANCEL_RESCHEDULE";
	public static final String  PROVIDER_ACCEPT_RESCHEDULE ="PROVIDER_ACCEPT_RESCHEDULE";
	
	
	//SL-18007 Added action values for persisting the so price change history.
	 public final static String ORDER_CREATION = "Order Created";
	 public final static String ORDER_EDITION = "Order Edited";
	 public final static String ORDER_POSTING = "Order Posted";
	 public final static String ORDER_GROUPING = "Order Grouped";
	 public final static String SCOPE_UPDATED_PENDING_CANCEL = "Scope Updated - Pending Cancel";
	 public final static String SCOPE_UPDATED = "Scope Updated";
	 public final static String INCREASE_SPEND_LIMIT = "Increased Spend Limit";
	 public final static String ORDER_CLOSURE = "Service Order Closed";
	 public final static String ORDER_AUTO_CLOSED = "Service Order Auto Closed";
	 public final static String ORDER_CANCELLED = "Order Cancelled";
	 public final static String ORDER_CANCELLATION = "Service Order Cancellation";
	 public final static String ORDER_PENDING_CANCELLATION = "Service Order Pending Cancellation";
	 public final static String ORDER_DELETED = "Order Deleted";
	 public final static String ORDER_VOIDED = "Order Voided";
	 public final static String ACCEPT_COUNTER_OFFER = "Counter offer accepted";
	 public final static String ORDER_REMOVED_FROM_GROUP = "Order Removed From Group";
	 public final static Integer BUYER_ROLE = 3;
	 public final static Integer PROVIDER_ROLE = 1;
	 	//SL-15642
	 public static final  Integer SCHEDULE_NEEDED = 1;
	 public static final  String MODIFIED_BY = "SYSTEM";
	 public static final  Long FACILITIES_BUYER = 4000L;
	//SL-19050
	//Constant for substatus when provider rejects a reschedule request
	 public static final  Integer BUYER_RESCHEDULE_REQUIRED = 109;
	 
	 //Constant for provider reject reschedule request
	 public static final String PROVIDER_REJECT_RESCHEDULE ="PROVIDER_REJECT_RESCHEDULE";
	 public static final  Integer POSTED_STATUS = 110;
	 //SL-21285 : Added to check the expired status for spend limit reverse.
	 public static final  Integer EXPIRED_STATUS = 130;
	 //Non W2 Priority 2 Constants
	 public static final Long INHOME_BUYER_LONG = 3000L;
	 public static final String REPEAT_REPAIR_ORDER_NUMBER="RepeatRepairServiceOrderNumber";
	 public static final String REPEAT_REPAIR_UNIT_NUMBER="RepeatRepairUnitNumber";
	 public static final Integer SO_COMPLETED=160;
	 public static final Integer SO_CLOSED=180;

	 //R15-4:SL-20975
	 public static final  Integer EPM_BUYER = 513561;
	 //R16_0_1:SL-21238
	 public static final  Integer AMERICAN_BUYER = 513590;
	 //SL-21284
	public static final Integer RELAY_SERVICES_BUYER = 3333;
	//SLT-735
	public static final Integer TECHTALK_BUYER = 7777;
	public static final String ORDER_TYPE = "Order_Type";
	public static final String STANDARD = "STANDARD";
	public static final String SELECTED_FIRM_ID = "SELECTED_FIRM_ID";
	
	
	public static final String LABOR_TAX_PERCENTAGE = "D2C Labor Tax Rate";
	public static final String MATERIALS_TAX_PERCENTAGE = "D2C Parts Tax Rate";
	public static final String LABOR_TAX_AMOUNT = "D2C Labor Tax Amount";
	public static final String MATERIALS_TAX_AMOUNT = "D2C Parts Tax Amount";
	public static final String PRICE_REGEX = "\\d{1,5}\\.\\d{1,2}";
	
	public static final int PREFERENCEIND=1;
	
	public final static String SERVICE_URL = "servicelive_url";
	
	
}
