package com.newco.marketplace.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.interfaces.CreditCardConstants;

public class UIUtils implements java.io.Serializable {

	private static final long serialVersionUID = -3290524435101285348L;

	/**
	 * Method to return a whole number for a decimal rating, which can then be mapped on to star shape images to show ratings.
	 * 
	 * This method takes a decimal rating value in the range {0 - 5} and returns a integer value in the range {1-20}
	 * For ex. if a given score value is 1.69, then an image with 1 full star and a star 3/4 filled need to be shown.
	 * The way it is calculated, a full star is shown whenever score value is between 0 through 5. The remaining fractional
	 * value (0.69 in this case), is checked to find which range it fits in among the below ranges - 
	 * {<= 0.12}   = Star image not filled at all
	 * {0.13-0.37} = Star image 1/4 filled
	 * {0.38-0.62} = Star image 1/2 filled
	 * {0.63-0.87} = Star image 3/4 filled
	 * {>= 0.88}   = Star image fully filled
	 * Hence, for the score of 1.69, a star image is shown with a full star and a star 3/4th filled.
	 * 
	 * The given score is converted from a decimal number to a whole number. Logic is given below.
	 * ScoreNumber =  Integer value of (given score / 0.25 ) + 1 [if the fractional value (given score MOD 0.25) > 0.12 ]
	 *
	 * For the given score of 1.69,
	 * Integer value of (given score / 0.25 ) = (int)(1.69 / 0.25) = 6
	 * Given score MOD 0.25 = 1.69 % 0.25 = 0.19. Since 0.19 is greater than 0.12, add 1 to above value.
	 * Hence scoreNumber for 1.69 = 6 + 1 = 7. The image that maps to 7 shows one full star and a 3/4th filled star.
	 * 
	 * ScoreNumber = 1 ==> Shows a star image 25% filled
	 * ScoreNumber = 2 ==> Shows a star image 50% filled
	 * ScoreNumber = 3 ==> Shows a star image 75% filled
	 * ScoreNumber = 4 ==> Shows a star image 100% filled.
	 * ScoreNumber > 4 ==> 
	 * 		Shows (ScoreNumber / 4) times star 100% filled and
	 * 		Shows (ScoreNumber % 4) value to show next star image filled as per above values for 1,2 or 3. 
	 * 
	 * @param score Decimal score value in range {0-5}
	 * @return scoreNumber Int score value in range {1-20}
	 */
	public static int calculateScoreNumber(double score) {
		//Formula for scoreNumber => {intValue / 0.25} + {number associated with fractionalValue based on which range it belongs to}

		int scoreNumber = (int)(score / 0.25);
		double fractionValue = score % 0.25;
		
		if(fractionValue > 0.12)
			scoreNumber++;
		
		return scoreNumber;
	}
	
	/**
	 * Method to handle special characters by replacing them with their HTML equivalent characters
	 * @param inputValue
	 * @return
	 */
	public static String handleSpecialCharacters(String inputValue) {
		String escapedValue = ""; 
		//Handle Single Quote
		escapedValue = StringUtils.replace(inputValue, "'", "&#39;"); 
		//Handle Double Quote
		escapedValue = StringUtils.replace(escapedValue, "\"", "&quot;"); //OR "&#34;"
		return escapedValue;
	}

	/**
	 * Method to format a given phone number of 10 digits (for ex. 1112223333) into
	 * a representation like 111-222-3333.
	 * @param phoneNum
	 * @return Formatted phone number
	 */
	public static String formatPhoneNumber(String phoneNumber) {
		String formattedPhone = StringUtils.EMPTY;
		if (null != phoneNumber) {
			if (phoneNumber.length() > 2) {
				formattedPhone = phoneNumber.substring(0, 3);
				if (phoneNumber.length() > 5) {
					formattedPhone = formattedPhone + "-" + phoneNumber.substring(3, 6);
				} else if (phoneNumber.length() > 3) {
					formattedPhone = formattedPhone + "-" + phoneNumber.substring(3);
				}
				if (phoneNumber.length() > 6) {
					formattedPhone = formattedPhone + "-" + phoneNumber.substring(6);
				}
			} else {
				formattedPhone = phoneNumber;
			}
		}
		return formattedPhone;
	}
	
	public static String formatDollarAmount(double amount) {
		String finalAmount = "";
		NumberFormat myFormatter = new DecimalFormat("###0.00");
		finalAmount = myFormatter.format(amount);
		return finalAmount;
	}
	
	
	/**
	 * Description:
	 * Checks whether a string of digits is a valid credit card number according
	 * to the Luhn algorithm.
	 * Then determines if the valid number matches the selected card-type.
	 *
	 * 1. Starting with the second to last digit and moving left, double the
	 *    value of all the alternating digits. For any digits that thus become
	 *    10 or more, add their digits together. For example, 1111 becomes 2121,
	 *    while 8763 becomes 7733 (from (1+6)7(1+2)3).
	 *
	 * 2. Add all these digits together. For example, 1111 becomes 2121, then
	 *    2+1+2+1 is 6; while 8763 becomes 7733, then 7+7+3+3 is 20.
	 *
	 * 3. If the total ends in 0 (put another way, if the total modulus 10 is
	 *    0), then the number is valid according to the Luhn formula, else it is
	 *    not valid. So, 1111 is not valid (as shown above, it comes out to 6),
	 *    while 8763 is valid (as shown above, it comes out to 20).
	 * 4. Check for the presence of certain digits in certain places to determine if 
	 * 	  the card type matches the number.  
	 *
	 * @param <code>String</code> credit card number to validate.
	 * @param <code>int</code> representing the card type.
	 * @return <code>boolean</code> true if the number is valid, false otherwise.
	 */
	public static boolean isCreditCardValid(String ccNum, int cardTypeId) {
		boolean validCC = false;
		
		//Check to see if the number is valid
		int sum = 0;
		boolean alternate = false;
		for (int i = ccNum.length() - 1; i >= 0; i--) {
			int n = Integer.parseInt(ccNum.substring(i, i + 1));
			if (alternate) {
				n *= 2;
				if (n > 9) {
					n = (n % 10) + 1;
				}
			}
			sum += n;
			alternate = !alternate;
		}

		validCC = (sum % 10 == 0);
		
		//if it's valid, check to see that the selected drop-down matches the number.
		if (validCC)
		{
			// VISA
			if (cardTypeId == CreditCardConstants.CARD_ID_VISA)
			{
				if ((ccNum.length() != 16) || Integer.parseInt(ccNum.substring(0, 1)) != 4) {
					validCC = false;
				}
			}

			// Discover card
			if (cardTypeId == CreditCardConstants.CARD_ID_DISCOVER)
			{ 
				if ((ccNum.length() != 16) 
						|| (Integer.parseInt(ccNum.substring(0, 4)) != 6011
						&& Integer.parseInt(ccNum.substring(0, 2)) != 30)) {
					validCC = false;
				}
			}
			
			
			// MASTER CARD
			else if (cardTypeId == CreditCardConstants.CARD_ID_MASTERCARD)
			{
				if (ccNum.length() != 16
						|| Integer.parseInt(ccNum.substring(0, 2)) < 51
						|| Integer.parseInt(ccNum.substring(0, 2)) > 55
						|| checkForSearsMasterCard(ccNum,cardTypeId)) {
					validCC = false;
				}
			}
			
			// SEARS MasterCard
			else if (cardTypeId == CreditCardConstants.CARD_ID_SEARS_MASTERCARD && (!checkForSearsMasterCard(ccNum,cardTypeId))) {
				validCC = false;
			}

			
			// AMEX
			// SLT-2591 and SLT-2592: Disable Amex
			/*if (cardTypeId == CreditCardConstants.CARD_ID_AMEX)
			{
				if (ccNum.length() != 15
						|| (Integer.parseInt(ccNum.substring(0, 2)) != 34 && Integer
								.parseInt(ccNum.substring(0, 2)) != 37)) {
					validCC = false;
				}
			}*/
			
			//
			else if ((cardTypeId == 0)) {

				if (ccNum.length() != 16
						&& ccNum.length() != 13) {
					validCC = false;
				}
				// Sears 16 digit Card
				else if ((ccNum.length() == 16)
						&& (!checkForSearsCardOf16digits(ccNum,cardTypeId))) {
					validCC = false;
				}
				// Sears White Card
				else if ((ccNum.length() == 13)
						&& (!checkForSearsWhiteCard(ccNum,cardTypeId))) {

					validCC = false;

				}

			}
			
			//Card not selected
			if (cardTypeId == -1)
			{
				validCC = false;
			}
		}

		return validCC;
	}
	// Check if its Sears White card or not
	public static boolean checkForSearsWhiteCard(String cardNumber, int cardTypeId )
	{
		if (cardNumber != null && cardNumber.length()== 13 && cardTypeId ==0)//Sears card has card type id of 0
		{
			String searsWhiteCardInitial2digits[] = CreditCardConstants.SEARS_WHITE_CARD_BIN_RANGE;
			String cardInitialTwoDigits = cardNumber.substring(0, 2);
			for (int i = 0; i < searsWhiteCardInitial2digits.length;i++)
			{
				if (cardInitialTwoDigits.equalsIgnoreCase(searsWhiteCardInitial2digits[i]))
				{
					return true;
					
				}
			}
		}
		return false;
	}
	
	public static boolean checkForSearsCardOf16digits(String cardNumber, int cardTypeId )
	{
		if (cardNumber.length()== 16 && cardTypeId == 0)//Sears card has card type id of 0
		{
			String searsCardInitial6digits[] = CreditCardConstants.SEARS_CARD_16_BIN_RANGE;
			String cardInitialSixDigits = cardNumber.substring(0, 6);
			for (int i = 0; i < searsCardInitial6digits.length;i++)
			{
				if (cardInitialSixDigits.equalsIgnoreCase(searsCardInitial6digits[i]))
				{
					return true;
				}
			}

		}
		return false;
	}
	
	public static boolean checkForSearsMasterCard(String cardNumber, int cardTypeId )
	{
		if (cardNumber.length()== 16 )//Sears Master card has card type id of 4
		{
			String searsMasterCardInitial6digits[] = CreditCardConstants.SEARS_MASTER_CARD_BIN_RANGE;
			String cardInitialSixDigits = cardNumber.substring(0, 6);
			for (int i = 0; i < searsMasterCardInitial6digits.length;i++)
			{
				if (StringUtils.equals(cardInitialSixDigits,searsMasterCardInitial6digits[i]))
				{
					
					return true;
					
				}
			}
		}
		return false;
	}
	
	/**
	 * Description:  Return String of card's name
	 * 
	 * @param int card type
	 * @return String of card name
	 */
	public static String getCardName(Long cardTypeId) {
		String cardName = "";
		int id = (int) cardTypeId.intValue();
		//VISA
		if (id == 6) {
			cardName = "VISA";
		}// MASTER CARD
		else if (id == 7) {
			cardName = "MASTER CARD";
		}/*// AMEX
		SLT-2591 and SLT-2592: Disable Amex
		else if (id == 8) {
			cardName = "AMEX";
		}// AMEX
*/		else if (id == 4) {
			cardName = "SEARS MASTER CARD";
		}
		else//Card not selected
		{
			cardName = "";
		}
		return cardName;
	}
	

	/**
	 * Description:  Returns string representation of Integer passed in.
	 * 1 is true and 0 is false
	 * @param boolInt
	 * @return
	 */
	public static String getBooleanStringFromInt(int boolInt) {
		String boolString = "false";
		if (boolInt == 0){
			boolString = "false";
		}else if (boolInt == 1){
			boolString = "true";
		}else{
			boolString = "false";
		}
		return boolString;
	}

	/**
	 * Description:  Returns Integer representation of string passed in.
	 * True is 1 and False is zero
	 * @param boolInt
	 * @return
	 */
	public static Integer getBooleanIntFromString(String boolString) {
		Integer boolInt = new Integer(0);
		if ("false".equalsIgnoreCase(boolString)){
			boolInt = 0;
		}else if ("true".equalsIgnoreCase(boolString)){
			boolInt = 1;
		}else{
			boolInt = 0;
		}
		return boolInt;
	}

	/**
	 * Description:  Truncates precision to 2 decimal places on strings.
	 * @param priceAsString
	 * @return <code>Double</code> with only two precision points.
	 */
	public static Double preciseDouble(String priceAsString) {
		double divided = 0;
		if (priceAsString.substring(priceAsString.indexOf(".")).length() >= 4){
			priceAsString = priceAsString.substring(0, (priceAsString.indexOf(".") + 3));
			divided = Double.valueOf(priceAsString);
		}else{
			divided = Double.valueOf(priceAsString);
		}
		return new Double(divided);
	}	
	
	/**
	 * Description:  Truncates precision to 2 decimal places on strings converted from doubles.
	 * @param <code>Double</code>priceAsString
	 * @return <code>Double</code> with only two precision points.
	 */
	public static Double preciseDouble(Double priceAsString) {
		return new Double(preciseDouble(priceAsString.toString()));
	}
	/**
	 * Method to handle special characters('<' and '>') by replacing them with their HTML equivalent characters
	 * @param inputValue
	 * @return
	 */
	public static String encodeSpecialChars(String inputValue) {
		String escapedValue = ""; 
		//Handle <
		escapedValue = StringUtils.replace(inputValue, "<", "&lt;"); 
		//Handle >
		escapedValue = StringUtils.replace(escapedValue, ">", "&gt;"); 
		return escapedValue;
	}
}
