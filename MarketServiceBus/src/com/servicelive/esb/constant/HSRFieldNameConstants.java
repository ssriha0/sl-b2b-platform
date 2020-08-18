package com.servicelive.esb.constant;

public class HSRFieldNameConstants {
	
	public static final String NEW_FILE_PREFIX = "NEWSLSO";
	public static final String UPDATE_FILE_PREFIX = "SLSOSTSCHG";
	
	/* New file Field indexes START*/
	public static final int SERVICE_UNIT_NUMBER = 0;
	public static final int SERVICE_ORDER_NUMBER = 1;
	public static final int REPAIR_TAG_BAR_CODE = 2;
	public static final int ORDER_TAKEN_TIME = 3;
	public static final int ORDER_TAKEN_DATE = 4;
	
	public static final int PROMISED_DATE = 5;
	public static final int PROMISED_TIME_FROM = 6;
	public static final int PROMISED_TIME_TO = 7;
	public static final int ORIGINAL_SCHEDULED_DATE = 8;
	public static final int ORIGINAL_TIME_FROM = 9;
	public static final int ORIGINAL_TIME_TO = 10;
	
	public static final int CUSTOMER_NUMBER = 11;
	public static final int CUSTOMER_TYPE = 12;
	public static final int CUSTOMER_FIRST_NAME = 13;
	public static final int CUSTOMER_LAST_NAME = 14;
	public static final int CUSTOMER_PHONE = 15;
	public static final int CUSTOMER_ALT_PHONE = 16;
	public static final int CUSTOMER_PREFFERED_LANGUAGE = 17;
	public static final int BUSINESS_ADDRESS_INDICATOR = 18;
	public static final int REPAIR_ADDRESS1 = 19;
	public static final int REPAIR_ADDRESS2 = 20;
	public static final int CITY = 21;
	public static final int STATE = 22;
	public static final int POSTAL_CODE = 23;
	
	
	public static final int CONTACT_NAME = 24;
	public static final int PAYMENT_METHOD_INDICATOR = 25;
	public static final int CUSTOMER_CHARGE_ACCOUNT_NUMBER = 26;
	public static final int CUSTOMER_CHARGE_ACCOUNT_EXPIRATION = 27;
	public static final int SERVICE_REQUESTED = 28;
	public static final int SERVICE_ORDER_STATUS_CODE = 29;
	public static final int SERVICE_LOCATION_CODE = 30;
	
	
	public static final int SPECIAL_INSTRUCTIONS1 = 31;
	public static final int SPECIAL_INSTRUCTIONS2 = 32;
	public static final int PERMANENT_INSTRUCTIONS = 33;
	public static final int PRIORITY_INDICATOR = 34;
	public static final int LAST_MODIFIED_BY_EMPLOYEE_ID = 35;
	
	public static final int COVERAGE_TYPE_LABOR = 36;
	public static final int COVERAGE_TYPE_PARTS = 37;
	public static final int EXCEPTION_PART_WARRANTY_EXPIRATION_DATE = 38;
	public static final int PA_AGGREMENT_NUMBER = 39;
	public static final int DIVISION= 40;
	public static final int PROTECTION_AGREEMENT_TYPE = 41;
	public static final int PA_LATEST_EXPIRATION_DATE = 42;
	public static final int PROTECTION_AGREEMENT_PLAN_TYPE = 43;
	
	public static final int MERCHANDISE_CODE = 44;
	public static final int MERCHANDISE_DESCRIPTION = 45;
	public static final int PURCHASE_DATE = 46;
	public static final int BRAND = 47;
	public static final int MODEL = 48;
	public static final int SERIAL_NUMBER = 49;
	public static final int SEARS_SOLD_CODE = 50;
	public static final int SYSTEM_ITEM_SUFFIX = 51;
	public static final int ITEM_NUMBER = 52;
	public static final int SEARS_STORE_NUMBER= 53;
	
	public static final int ORIGINAL_DELIVERY_DATE = 54;
	public static final int RESIDENTIAL_OR_COMMERCIAL_USAGE = 55;
	public static final int PROMOTION_INDICATOR = 56;
	public static final int PART_WARRANTY_EXPIRATION_DATE = 57;
	public static final int LABOR_WARRANTY_EXPIRAION_DATE = 58;
	public static final int EXCEPTION_PART_WARRANTY_EXPIRATION_DATE2 = 59;
	
	public static final int LABOR_WARRANTY = 60;
	public static final int REPAIR_INSTRUCTIONS = 61;
	public static final int EMPLOYEE_NO_CREATED_SO = 62;
	public static final int SOLICIT_PA_INDICATOR = 63;
	public static final int SO_CREATION_UNIT_NUMBER = 64;
	
	public static final int PROC_ID = 65;
	public static final int CONTRACT_NUMBER = 66;
	public static final int CONTRACT_EXP_DATE = 67;
	public static final int AUTHORIZATION_NUMBER = 68;
	
	/* New file Field indexes END*/
	
	/* Update file Field indexes START*/
	
	public static final int SERVICE_UNIT_NUMBER_UPDATE = 0;
	public static final int SERVICE_ORDER_NUMBER_UPDATE = 1;
	
	public static final int MODIFIED_DATE_UPDATE = 2;
	public static final int MODIFIED_TIME_UPDATE = 3;
	public static final int SERVICE_ORDER_STATUS_CODE_UPDATE = 4;
	public static final int EMPLOYEE_ID_NUMBER_UPDATE = 5;
	public static final int FILLER_UPDATE = 6;
	public static final int MODIFYING_UNIT_ID_UPDATE = 7;
	/* Parts related info, can have upto 6 parts*/
	/* PART1 */
	public static final int PART1_DIV_NO_UPDATE = 8;
	public static final int PART1_PLS_UPDATE = 9;
	public static final int PART1_PART_NUMBER_UPDATE = 10;
	public static final int PART1_QTY_UPDATE = 11;
	public static final int PART1_UNIT_PRICE_UPDATE = 12;
	public static final int PART1_COVERAGE_TYPE_UPDATE = 13;
	/* PART2 */
	public static final int PART2_DIV_NO_UPDATE = 14;
	public static final int PART2_PLS_UPDATE = 15;
	public static final int PART2_PART_NUMBER_UPDATE = 16;
	public static final int PART2_QTY_UPDATE = 17;
	public static final int PART2_UNIT_PRICE_UPDATE = 18;
	public static final int PART2_COVERAGE_TYPE_UPDATE = 19;
	/* PART3 */
	public static final int PART3_DIV_NO_UPDATE = 20;
	public static final int PART3_PLS_UPDATE = 21;
	public static final int PART3_PART_NUMBER_UPDATE = 22;
	public static final int PART3_QTY_UPDATE = 23;
	public static final int PART3_UNIT_PRICE_UPDATE = 24;
	public static final int PART3_COVERAGE_TYPE_UPDATE = 25;
	/* PART4 */
	public static final int PART4_DIV_NO_UPDATE = 26;
	public static final int PART4_PLS_UPDATE = 27;
	public static final int PART4_PART_NUMBER_UPDATE = 28;
	public static final int PART4_QTY_UPDATE = 29;
	public static final int PART4_UNIT_PRICE_UPDATE = 30;
	public static final int PART4_COVERAGE_TYPE_UPDATE = 31;
	/* PART5 */
	public static final int PART5_DIV_NO_UPDATE = 32;
	public static final int PART5_PLS_UPDATE = 33;
	public static final int PART5_PART_NUMBER_UPDATE = 34;
	public static final int PART5_QTY_UPDATE = 35;
	public static final int PART5_UNIT_PRICE_UPDATE = 36;
	public static final int PART5_COVERAGE_TYPE_UPDATE = 37;
	/* PART6 */
	public static final int PART6_DIV_NO_UPDATE = 38;
	public static final int PART6_PLS_UPDATE = 39;
	public static final int PART6_PART_NUMBER_UPDATE = 40;
	public static final int PART6_QTY_UPDATE = 41;
	public static final int PART6_UNIT_PRICE_UPDATE = 42;
	public static final int PART6_COVERAGE_TYPE_UPDATE = 43;
	
	
	/* Update file Field indexes END*/

}
