package com.newco.marketplace.utils;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import com.newco.marketplace.interfaces.MPConstants;

/**
 * <p>Title:RFSUtils </p>
 * <p>Description:Utilitry methods used across the application /p>
 * @author RHarini
 * @date Jun 23, 2006
 */
public class MPUtils {

	private static ResourceBundle resouceBundle = null;

	/**
	 * Returns the message for Given errorCode
	 * @param String errorCode
	 * @return String message
	 */
	public static String getErrorMessage(String errorCode) {
		String message = null;
		try {
			if (resouceBundle == null) {
				resouceBundle = ResourceBundle
						.getBundle("com.sears.rfs.utils.ErrorMessages");
			}
			message = resouceBundle.getString(errorCode);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * Returns true if the val is numeric else returns false
	 * @param String val
	 * @return boolean
	 */
	public static boolean hasNumericOnly(String val) {
		String source = new String(val);
		source = source.trim();

		for (int i = 0; i < source.length(); i++) {
			if (!Character.isDigit(source.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * Returns true if the val is numeric and dot else returns false
	 * @param String val
	 * @return boolean
	 */
	public static boolean hasNumericAndDotOnly(String val) {
		String source = new String(val);
		int ctr = 0;
		source = source.trim();
		for (int i = 0; i < source.length(); i++) {
			if (Character.isDigit(source.charAt(i)) || source.charAt(i) == '.') {
				if (source.charAt(i) == '.')
					ctr = ctr + 1;
			} else {
				return false;
			}
		}
		if (ctr > 1)
			return false;

		return true;
	}

	/**
	 * Returns true if the val is alphabetic else returns false
	 * @param String val
	 * @return boolean
	 */
	public static boolean hasAlphabetsOnly(String val) {
		String source = new String(val);
		source = source.trim();
		for (int i = 0; i < source.length(); i++) {
			if (!Character.isLetter(source.charAt(i))
					&& !Character.isSpaceChar(source.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * Returns true if the val is alphanumeric or space else returns false
	 * @param String val
	 * @return boolean
	 */
	public static boolean hasAlphaNumericOnly(String val) {
		String source = new String(val);
		source = source.trim();

		for (int i = 0; i < source.length(); i++) {
			if (!Character.isLetterOrDigit(source.charAt(i))
					&& !Character.isWhitespace(source.charAt(i))
					&& !hasSpecialCharacter(source.charAt(i) + ""))
				return false;

		}
		return true;
	}

	/**
	 * Returns if val length >0 else returns false
	 * @param String val
	 * @return boolean
	 */
	public static boolean isRequired(String val) {
		if (val != null && val.trim().length() > 0)
			return true;

		return false;

	}

	/**
	 * Returns true if sourceChar is a special character
	 * @param String sourceChar
	 * @return boolean
	 */
	public static boolean hasSpecialCharacter(String sourceChar) {
		char specialChars[] = { '-', '%', '&', '*', '$', '#', '>', '<', '/',
				'?', '!', '(', ')', '^', '@', '\'', '\"', ',', '[', ']', '{',
				'}', '|', '+', '=', '_', '~', '`', ':', ';', '\\', '.' };
		if (checkTrim(sourceChar).length() > 0) {
			for (int i = 0; i < specialChars.length; i++) {
				if (sourceChar.indexOf(specialChars[i]) != -1) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns true if sourceChar is a special character except '-'
	 * @param String sourceChar
	 * @return boolean
	 */
	//Harini: 06-Apr-07 removed '.' from the specialchar check for part acceptance with decimal
	public static boolean hasSpecialCharacterDash(String sourceChar) {
		char specialChars[] = { '%', '&', '*', '$', '#', '>', '<', '/', '?',
				'!', '(', ')', '^', '@', '\'', '\"', ',', '[', ']', '{', '}',
				'|', '+', '=', '_', '~', '`', ':', ';', '\\', ' ' };
		if (checkTrim(sourceChar).length() > 0) {
			for (int i = 0; i < specialChars.length; i++) {
				if (sourceChar.indexOf(specialChars[i]) != -1) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns true if sourceChar is a special character except '.'
	 * @param String sourceChar
	 * @return boolean
	 */
	public static boolean hasSpecialCharacterAmount(String sourceChar) {
		char specialChars[] = { '-', '%', '&', '*', '$', '#', '>', '<', '/',
				'?', '!', '(', ')', '^', '@', '\'', '\"', ',', '[', ']', '{',
				'}', '|', '+', '=', '_', '~', '`', ':', ';', '\\', ' ' };
		if (checkTrim(sourceChar).length() > 0) {
			for (int i = 0; i < specialChars.length; i++) {
				if (sourceChar.indexOf(specialChars[i]) != -1) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * checkTrim : Removes the leading trailing spaces from the String
	 * @return java.lang.String
	 * @param strResult java.lang.String
	 */
	public static String checkTrim(String strResult) {
		String strTemp = "";
		if (strResult != null) {
			strTemp = strResult.trim();
		}
		return strTemp;
	}

	/**
	 * checkNum : Returns -1 if the number is null 
	 * @return java.lang.String
	 * @param strResult java.lang.String
	 */
	public static String checkNum(String strResult) {
		String strTemp = "-1";
		if (checkTrim(strResult).length() > 0)
			if (strResult != null) {
				strTemp = strResult.trim();
			}
		return strTemp;
	}

	/**
	 * checkNull : Removes the leading trailing spaces from the String
	 * @return java.lang.String
	 * @param strResult java.lang.String
	 */
	public static String checkNull(String strResult) {
		String strTemp = " ";
		if (strResult != null) {
			strTemp = strResult.trim();
		}
		if (strTemp.trim().length() == 0) {
			strTemp = " ";
		}
		return strTemp;
	}

	/**
	 * isStringNull : Checks if the string is null or is "" and returns true if null, else false
	 * @return boolean
	 * @param strResult java.lang.String
	 */
	public static boolean isStringNull(String strResult) {
		if ((strResult == null) || (strResult.equals("")))
			return true;
		else
			return false;
	}

	/**
	 * formatNull : Returns -1 if input string is null
	 * @return java.lang.String
	 * @param strResult java.lang.String
	 */
	public static String formatNull(String strResult) {
		String strTemp = "-1";
		if (strResult != null) {
			strTemp = strResult.trim();
		}
		if (strTemp.trim().length() == 0) {
			strTemp = "-1";
		}
		return strTemp;
	}

	public static boolean isDollarFormat(String str) {
		//	if (str.indexOf(".")){

		//	}
		StringTokenizer st = new StringTokenizer(str, ".");
		if (st.countTokens() != 2) {
			return false;
		} else {
			while (st.hasMoreTokens()) {
				String hld = "";
				hld = st.nextToken();
				if (hld.length() != 2)
					return false;
			}
		}
		return true;
	}

	/**
	 * isTransactionSuccessful : Returns true if input string is null (transaction is successful)
	 * @return java.lang.String
	 * @param boolean
	 */
	public static boolean isTransactionSuccessful(String strErrorCode) {
		if ((strErrorCode == null) || (strErrorCode.equals("")))
			return true;
		else
			return false;
	}

	/**
	 * hasSystemError : Returns true if input string is null (if system error is present)
	 * @return java.lang.String
	 * @param boolean
	 */
	public static boolean hasSystemError(String strErrorCode) {
		if ((strErrorCode != null)
				&& (strErrorCode.equals(MPConstants.SYSTEMERROR)))
			return true;
		else
			return false;
	}

	/**
	 * hasBusinessError : Returns true if input string is null (if business error is present)
	 * @return java.lang.String
	 * @param boolean
	 */

	public static boolean hasBusinessError(String strErrorCode) {
		if ((strErrorCode != null)
				&& (strErrorCode.equals(MPConstants.BUSINESSERROR)))
			return true;
		else
			return false;
	}



	/**
	 * Returns User Role description 
	 * @param String Role Code
	 * @return String
	 */

	public static String getRoleDesc(String strRoleCode)

	{
		HashMap hm = new HashMap();
		hm.put("ADM", "Administrator");
		hm.put("SPV", "Supervisor");
		hm.put("ASC", "Associate");
		if (hm.containsKey(strRoleCode)) {
			return ((String) hm.get(strRoleCode));
		} else {
			return "";
		}
	}

	/**
	 * Returns Receive code description 
	 * @param String Role Code
	 * @return String
	 */

	public static String getRcvCodeDesc(String strRoleCode) {
		HashMap hm = new HashMap();
		hm.put("NML", "Normal");
		hm.put("MSS", "Missing");
		hm.put("MDR", "Misdirected");
		if (hm.containsKey(strRoleCode)) {
			return ((String) hm.get(strRoleCode));
		} else {
			return "";
		}
	}

	/**
	 * Returns Part Retrun code description 
	 * @param String Role Code
	 * @return String
	 */

	public static String getPartRtnCodeDesc(String strRoleCode) {
		HashMap hm = new HashMap();
		hm.put("CLO", "Closed Normally");
		hm.put("CLF", "Forced Closed");
		hm.put("CWS", "Core/In-Warranty Shipment Intransit");
		hm.put("DOF", "Dropped Off at Unit");
		hm.put("ICB", "Invalid Carrier Bar Code");
		hm.put("LNA", "Label Not Available");
		hm.put("MOR", "Manual Override");
		hm.put("NAD", "Newly Added Assortment Deletion");
		hm.put("PNA", "Part Not Available");
		hm.put("RCV", "Received at Unit and Dispositioned");
		hm.put("SAN", "Initial Barcode Scanned/Key Entered");
		hm.put("UVS", "Unit/Vendor Shipment Intransit");
		if (hm.containsKey(strRoleCode)) {
			return ((String) hm.get(strRoleCode));
		} else {
			return "";
		}
	}

	/**
	 * Returns Unit description 
	 * @param String Role Code
	 * @return String
	 */

	public static String getUnitCodeDesc(String strRoleCode) {
		HashMap hm = new HashMap();
		hm.put("BS", "Branch");
		hm.put("DS", "District");
		hm.put("GRC", "Carry-In");
		hm.put("EMC", "Carry-In");
		hm.put("IRC", "IRC");
		hm.put("PDC", "PDC");
		if (hm.containsKey(strRoleCode)) {
			return ((String) hm.get(strRoleCode));
		} else {
			return "";
		}
	}

	/**
	 * Returns Part Return Code description 
	 * @param String Role Code
	 * @return String
	 */

	public static String getPartReturnCodeDesc(String strRoleCode) {
		HashMap hm = new HashMap();
		hm.put("ASD", "Assortment Deletion");
		hm.put("OTH", "Other");
		hm.put("RCO", "Replaced Part to be Returned - Core");
		hm.put("RIW", "Replaced Part to be Returned - In-Warranty");
		hm.put("UNI", "Uninstalled Part");
		if (hm.containsKey(strRoleCode)) {
			return ((String) hm.get(strRoleCode));
		} else {
			return "";
		}
	}

	/**
	 * Checks if the length of the numeric value entered matches with the database length  
	 * Returns true or false 
	 * @param String DecimalValue, Length of the Integer part , DecimalLength
	 * @return boolean
	 */

	public static boolean checkNumericLength(String strValue,
			int intIntegerLength, int intDecimalLength) {
		String strIntegerPart = "";
		String strDecimalPart = "";
		int intIndex = 0;
		intIndex = strValue.indexOf(".");
		if (intIndex >= 0) {
			strIntegerPart = strValue.substring(0, intIndex);
			strDecimalPart = strValue
					.substring(intIndex + 1, strValue.length());
			if (strDecimalPart.length() == 0)
				return false;
			if ((strIntegerPart.length() > intIntegerLength)
					|| (strDecimalPart.length() > intDecimalLength))
				return false;
			else
				return true;
		} else {
			if (strValue.length() > intIntegerLength)
				return false;
			else
				return true;
		}

	}

	/**
	 * Compares two dates and return a boolean value
	 * @param fromDate
	 * @return boolean
	 */
	public static boolean compareToSysdate(String fromDate, int partAge) {
		String DATE_FORMAT = "yyyy-MM-dd";
		int year = 0;
		int month = 0;
		int day = 0;
		boolean dateFlag = false;
		Date d1;
		Date d2;
		StringTokenizer tokenizer = new StringTokenizer(fromDate, "-");
		while (tokenizer.hasMoreTokens()) {
			year = Integer.parseInt(tokenizer.nextToken());
			month = Integer.parseInt(tokenizer.nextToken());
			day = Integer.parseInt(tokenizer.nextToken());

		}
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar c1 = Calendar.getInstance();
		c1.set(year, month, day);
		c1.add(Calendar.MONTH, partAge);
		Date today = new Date();
		String datenewformat = sdf.format(today);
		try {
			d1 = sdf.parse(datenewformat);
			d2 = sdf.parse(sdf.format(c1.getTime()));
			if (d1.before(d2)) {
				dateFlag = true;
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dateFlag;
	}

	/**
	 * Verify date format and return a boolean value
	 * @param Date
	 * @return boolean
	 */
	public static boolean checkFutureDate(String strDate) {
		String DATE_FORMAT = "yyyy-MM-dd";
		Date d1 = null;
		Date d2 = null;
		String strHldTokens[] = getDateTokens(strDate);
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar c1 = Calendar.getInstance();
		c1.set(Integer.parseInt(strHldTokens[2]), Integer
				.parseInt(strHldTokens[0]) - 1, Integer
				.parseInt(strHldTokens[1]));
		Date today = new Date();
		String datenewformat = sdf.format(today);
		try {
			d1 = sdf.parse(datenewformat);
			d2 = sdf.parse(sdf.format(c1.getTime()));
			if (d1.before(d2)) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * Verify date format and return a boolean value
	 * @param Date
	 * @return boolean
	 */
	public static boolean checkDateFormat(String strDate) {
		String strHldTokens[] = getDateTokens(strDate);
		if (strHldTokens[0].length() != 2 || strHldTokens[1].length() != 2
				|| strHldTokens[2].length() != 4) {
			return false;
		}
		return true;
	}

	/**
	 * Verify date & month and return a boolean value
	 * @param strDate
	 * @return boolean
	 */
	public static boolean invalidDate(String strDate) {
		String strHldTokens[] = getDateTokens(strDate);
		try {
			if (Integer.parseInt(strHldTokens[0]) > 12
					|| Integer.parseInt(strHldTokens[1]) > 31) {
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * Change date format in to yyyy-mm-dd
	 * @param String
	 * @return String
	 */
	public static String changeDateFormat(String strDate) throws ParseException {
		String strHldTokens[] = getDateTokens(strDate);
		return (strHldTokens[2] + "-" + strHldTokens[0] + "-" + strHldTokens[1]);
	}

	/**
	 * Compare date format and return a boolean value
	 * @param Date
	 * @return boolean
	 */
	public static int getDateDifference(String strFromDate, String strToDate) {
		/** The date at the end of the last century */
		String strHldTokens[] = getDateTokens(strFromDate);
		Date fromDate = new GregorianCalendar(
				Integer.parseInt(strHldTokens[2]), Integer
						.parseInt(strHldTokens[0]), Integer
						.parseInt(strHldTokens[1])).getTime();
		/** Today's date */
		//yyy/mm/dd
		Date toDate = new GregorianCalendar(Integer.parseInt(strHldTokens[2]),
				Integer.parseInt(strHldTokens[0]), Integer
						.parseInt(strHldTokens[1])).getTime();
		// Get msec from each, and subtract.
		long diff = toDate.getTime() - fromDate.getTime();
		return (int) (diff / (1000 * 60 * 60 * 24));

	}

	private static String[] getDateTokens(String strDate) {
		StringTokenizer st = new StringTokenizer(strDate, "/");
		String strHldTokens[] = new String[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			strHldTokens[i] = new String();
			strHldTokens[i] = st.nextToken();
			i++;
		}
		return strHldTokens;
	}

	/**
	 * Returns Disposition description 
	 * @param String dispCode
	 * @return String
	 */

	public static String getDPODescription(String dispCode) {
		HashMap hm = new HashMap();
		hm.put("CIW", "Core/IW");
		hm.put("HZD", "Hazardous Material");
		hm.put("KPU", "Keep at Unit");
		hm.put("KPP", "Keep at PDC");
		hm.put("KPI", "Keep at IRC");
		hm.put("SCP", "Scrap");
		hm.put("SNP", "Send to PDC");
		hm.put("SNI", "Send to IRC");
		hm.put("SNV", "Send to Vendor");

		if (hm.containsKey(dispCode)) {
			return ((String) hm.get(dispCode));
		} else {
			return "";
		}

	}

	/**
	 * Returns Current date in yyyy-mm-dd 
	 *  * @return String
	 */
	public static String getCurrentDate() {
		String DATE_FORMAT = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar c = Calendar.getInstance();
		return sdf.format(c.getTime());
	}

	/**
	 * Returns From date = (ToDate - 60 days) 
	 *  * @return String
	 */
	public static String assignFromDate(String strToDate) {
		String DATE_FORMAT = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String strHldTokens[] = getDateTokens(strToDate);
		Calendar c1 = Calendar.getInstance();
		c1.set(Integer.parseInt(strHldTokens[2]), Integer
				.parseInt(strHldTokens[0]), Integer.parseInt(strHldTokens[1]));
		c1.roll(Calendar.MONTH, 5);
		return sdf.format(c1.getTime());
	}

	public static String formatUnitNumber(String strUnit) {
		int intUnitLength = 0;
		int intNumberOfZeroes = 0;
		String strZero = "";
		if ((strUnit != null) && (!strUnit.equals(""))) {
			intUnitLength = strUnit.length();
			if (intUnitLength < 7) {
				intNumberOfZeroes = (7 - intUnitLength);
				for (int i = 0; i < intNumberOfZeroes; i++)
					strZero = strZero + "0";
				strUnit = strZero + strUnit;
			}
		}
		return strUnit;
	}

	public static String formatDivNO(String divNo) {
		int intDivLength = 0;
		int intNumberOfZeroes = 0;
		String strZero = "";
		if ((divNo != null) && (!divNo.equals(""))) {
			intDivLength = divNo.length();
			if (intDivLength < 3) {
				intNumberOfZeroes = (3 - intDivLength);
				for (int i = 0; i < intNumberOfZeroes; i++)
					strZero = strZero + "0";
				divNo = strZero + divNo;
			}
		}
		return divNo;
	}

	public static String prependZeroes(String strParam, int intNumberOfZeroes) {
		String strZero = "";
		if ((strParam != null) && (!strParam.equals(""))) {
			for (int i = 0; i < intNumberOfZeroes; i++)
				strZero = strZero + "0";
			strParam = strZero + strParam;
		}
		return strParam;
	}

	/**
	 * Returns True for Valid Date 
	 *  * @return boolean
	 */
	public static boolean isDateValid(String date) {
		try {
			String format = "MMddyyyy";
			String strTokens[] = getDateTokens(date);
			date = (strTokens[0] + strTokens[1] + strTokens[2]);
			Date dateSimple = new SimpleDateFormat(format).parse(date);
			Format formatter = new SimpleDateFormat(format);
			if (!date.equals(formatter.format(dateSimple)))
				return false;
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * Converts a single quote into double quotes in a given string
	 * @param String
	 * @return String 
	 * @author RHarini
	 *
	 */
	public static String processDesc(String strDesc) {
		//			the  max number of characters for part description is 12
		/*** build sql in clause start ***/
		strDesc = strDesc.trim().toUpperCase();
		char[] cDesc = strDesc.toCharArray();
		HashSet hCollection = new HashSet();
		StringBuffer sbInClause = new StringBuffer();
		for (int i = 0; i < cDesc.length; i++) {
			String sCharacters = "";
			for (int j = i; j < cDesc.length; j++) {
				// if the character is the single quote, make it 2 single quotes as
				// required by the sql statement.
				String sCharacter = "";
				if (cDesc[j] == '\'') {
					sCharacter = "" + "''";
				} else {
					sCharacter = "" + cDesc[j];
				}
				sCharacters = sCharacters + sCharacter;

				// add only uique string values to the sql In clause
				if (hCollection.add(sCharacters) == true
						&& sCharacters.length() > 1) {
					sbInClause.append(",'" + sCharacters + "'");
				}
			}
		}
		String sInClause = sbInClause.substring(1).toString();
		return sInClause;
	}

	/**
	 * Converts a single quote into double quotes in a given string
	 * @param String
	 * @return String []
	 * @author RHarini
	 *
	 */
	public static String[] processDescr(String strDesc) {
		//				the  max number of characters for part description is 12
		/*** build sql in clause start ***/
		strDesc = strDesc.trim().toUpperCase();
		char[] cDesc = strDesc.toCharArray();
		int charLength = cDesc.length;
		//int arrLength = factorial(charLength)/(2 * factorial(charLength-2));
		int arrLength = (charLength * (charLength - 1)) / 2;
		int intRepetitionCtr = 0;
		String sCharacterd[] = new String[arrLength];
		HashSet hCollection = new HashSet();
		int counter = 0;
		for (int i = 0; i < cDesc.length; i++) {
			String sCharacters = "";
			for (int j = i; j < cDesc.length; j++) {
				// if the character is the single quote, make it 2 single quotes as
				// required by the sql statement.
				String sCharacter = "";
				if (cDesc[j] == '\'') {
					sCharacter = "" + "''";
				} else {
					sCharacter = "" + cDesc[j];
				}
				sCharacters = sCharacters + sCharacter;

				// add only uique string values to the sql In clause
				if (hCollection.add(sCharacters) == true
						&& sCharacters.length() > 1) {
					if (!isStringNull(sCharacters)) {
						sCharacterd[counter] = new String();
						sCharacterd[counter] = sCharacters;
						counter++;
					}

				} else if (hCollection.add(sCharacters) == false
						&& sCharacters.length() > 1) {
					intRepetitionCtr = intRepetitionCtr + 1;

				}

			}
		}
		String sCharacterFinal[] = new String[counter];
		if (intRepetitionCtr > 0) {
			System.arraycopy(sCharacterd, 0, sCharacterFinal, 0, counter);
			return sCharacterFinal;
		} else
			return sCharacterd;
	}

	/**
	 * To find a factorial of a given number
	 * @param n
	 * @return int
	 */
	public static int factorial(int n) {
		int ans = 1;
		for (int i = 1; i <= n; i++)
			ans = ans * i;
		return ans;
	}

}
