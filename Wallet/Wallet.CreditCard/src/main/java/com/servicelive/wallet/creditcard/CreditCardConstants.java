/**
 * 
 */
package com.servicelive.wallet.creditcard;

// TODO: Auto-generated Javadoc
/**
 * Interface CreditCardConstants.
 */
public interface CreditCardConstants {

	/** ACTIVE_ACCOUNT_STATUS. */
	public static final int ACTIVE_ACCOUNT_STATUS = 1;

	/** BILLING_LOCATION_ID. */
	public static final int BILLING_LOCATION_ID = 5;

	/** BUSINESS_TRANSACTION_CC_AMEX. */
	public static final int BUSINESS_TRANSACTION_CC_AMEX = 270;

	/** BUSINESS_TRANSACTION_CC_SEARS_CARDS. */
	public static final int BUSINESS_TRANSACTION_CC_SEARS_CARDS = 300;

	/** BUSINESS_TRANSACTION_CC_VMC. */
	public static final int BUSINESS_TRANSACTION_CC_VMC = 260;

	/** CARD_ID_AMEX. *//*
	public static final int CARD_ID_AMEX = 8;

	*//** CARD_ID_AMEX_STR. *//*
	// SLT-2591 and SLT-2592: Disable Amex
	public static final String CARD_ID_AMEX_STR = "AX";*/

	/** CARD_ID_COMMERCIAL_ONE_STR. */
	public static final String CARD_ID_COMMERCIAL_ONE_STR = "CM";

	/** CARD_ID_DISCOVER. */
	public static final int CARD_ID_DISCOVER = 5;

	/** CARD_ID_DISCOVER_STR. */
	public static final String CARD_ID_DISCOVER_STR = "DD";

	/** CARD_ID_GIFT_STR. */
	public static final String CARD_ID_GIFT_STR = "??"; // No definition given by business

	/** CARD_ID_MASTERCARD. */
	public static final int CARD_ID_MASTERCARD = 7;

	/** CARD_ID_MASTERCARD_STR. */
	public static final String CARD_ID_MASTERCARD_STR = "MC";

	/** CARD_ID_SEARS. */
	public static final int CARD_ID_SEARS = 0;

	/** CARD_ID_SEARS_CHARGE_PLUS_STR. */
	public static final String CARD_ID_SEARS_CHARGE_PLUS_STR = "SP";

	/** CARD_ID_SEARS_CHARGE_STR. */
	public static final String CARD_ID_SEARS_CHARGE_STR = "SS";

	/** CARD_ID_SEARS_COMMERCIAL. */
	public static final int CARD_ID_SEARS_COMMERCIAL = 3;

	/** CARD_ID_SEARS_MASTERCARD. */
	public static final int CARD_ID_SEARS_MASTERCARD = 4;

	/** CARD_ID_SEARS_PLUS. */
	public static final int CARD_ID_SEARS_PLUS = 2;

	/** CARD_ID_SEARS_PREMIER. */
	public static final int CARD_ID_SEARS_PREMIER = 1;

	/** CARD_ID_VISA. */
	public static final int CARD_ID_VISA = 6;

	/** CARD_ID_VISA_STR. */
	public static final String CARD_ID_VISA_STR = "VI";

	/** CC_ADDRESS_MAX_LENGTH. */
	public static final int CC_ADDRESS_MAX_LENGTH = 20;

	/** CREDIT_CARD_AUTH_URL. */
	public static final String CREDIT_CARD_AUTH_URL = "webservices.rtca.url";

	/** DIVISION. */
	public static final String DIVISION = "000";

	/** SEARS_CARD_16_BIN_RANGE. */
	public static final String[] SEARS_CARD_16_BIN_RANGE = { "504994", "380000", "381000", "382000", "383000" };

	/** SEARS_COMMERCIAL_CARD_FIRST_SIX_DIGITS. */
	public static final String SEARS_COMMERCIAL_CARD_FIRST_SIX_DIGITS = "540553";
	//Commenting out some BIN ranges as the HSBC card has been removed from the Sears family
	//Added 530226 bin for Barclays in Sears Master Card family 
	/** SEARS_MASTER_CARD_BIN_RANGE. */
	public static final String SEARS_MASTER_CARD_BIN_RANGE[] = { "512106", "512107", "518537", "512108","530226"/*, "520094", "520118", "520611", "520612", "521331", "549506"*/ };

	/** SEARS_WHITE_CARD_BIN_RANGE. */
	public static final String[] SEARS_WHITE_CARD_BIN_RANGE =
		{ "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "11", "20", "21", "34", "36", "40", "44", "48", "50", "54", "57", "60", "64", "70", "75", "80", "81", "82",
			"95" };

	/** ZIPCODE_MAX_LENGTH. */
	public static final int ZIPCODE_MAX_LENGTH = 9;
}
