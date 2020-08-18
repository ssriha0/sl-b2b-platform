/**
 * 
 */
package com.newco.marketplace.interfaces;

/**
 * @author schavda
 *
 */
public interface CreditCardConstants {

	public static final int BILLING_LOCATION_ID = 5;
	
	//move to application_properties table
	//public static final String SEARS_STORE_ID = "9300";
	//public static final String SEARS_STORE_ID_0 = "09300";
	
	public static final String DIVISION = "000";
	
	public static final int CARD_ID_SEARS = 0;
	public static final int CARD_ID_SEARS_PREMIER = 1;
	public static final int CARD_ID_SEARS_PLUS = 2;
	public static final int CARD_ID_SEARS_COMMERCIAL = 3;
	public static final int CARD_ID_SEARS_MASTERCARD = 4;

	public static final String SEARS_COMMERCIAL_CARD_FIRST_SIX_DIGITS= "540553";
	public static final String[] SEARS_WHITE_CARD_BIN_RANGE = {"00","01","02","03","04","05","06","07","08","09","11","20","21","34","36","40","44","48","50","54","57","60","64","70","75","80","81","82","95"};
	//Commenting out some BIN ranges as the HSBC card has been removed from the Sears family
	//Added 530226 bin for Barclays in Sears Master Card family
	public static final String SEARS_MASTER_CARD_BIN_RANGE[] = {"512106","512107","518537","512108","530226"/*,"520094","520118","520611","520612","521331","549506"*/} ;
	public static final String[] SEARS_CARD_16_BIN_RANGE = {"504994","380000","381000","382000","383000"};
	public static final int CARD_ID_DISCOVER = 5;
	public static final String CARD_ID_DISCOVER_DESC = "Discover";
	public static final int CARD_ID_VISA = 6;
	public static final String CARD_ID_VISA_DESC = "Visa";
	public static final int CARD_ID_MASTERCARD = 7;
	public static final String CARD_ID_MASTERCARD_DESC = "Mastercard";
	public static final String CARD_ID_SEARS_COMMERCIAL_DESC = "Sears Commercial";
	public static final String CARD_ID_SEARS_PLUS_DESC = "Sears Plus";
	/* SLT-2591 and SLT-2592: Disable Amex
	 public static final int CARD_ID_AMEX = 8;
	public static final String CARD_ID_AMEX_STR = "AX";
	public static final String CARD_ID_AMEX_DESC = "American Express";*/
	public static final String CARD_ID_SEARS_MASTERCARD_DESC = "Sears Master Card";
	public static final String CARD_ID_SEARS_DESC = "Sears";
	public static final String CARD_ID_COMMERCIAL_ONE_STR = "CM";
	public static final String CARD_ID_DISCOVER_STR = "DD";
	public static final String CARD_ID_GIFT_STR = "??"; // No definition given by business
	public static final String CARD_ID_MASTERCARD_STR = "MC";
	public static final String CARD_ID_SEARS_CHARGE_PLUS_STR = "SP";
	public static final String CARD_ID_SEARS_CHARGE_STR = "SS";
	public static final String CARD_ID_VISA_STR = "VI";
	
	public static final int BUSINESS_TRANSACTION_CC_VMC = 260;
	public static final int BUSINESS_TRANSACTION_CC_AMEX = 270;
	public static final int BUSINESS_TRANSACTION_CC_SEARS_CARDS = 300;
	
	public static final int ACTIVE_ACCOUNT_STATUS = 1;
	
	public static final int CC_ADDRESS_MAX_LENGTH = 20;
	public static final int ZIPCODE_MAX_LENGTH = 9;

}
