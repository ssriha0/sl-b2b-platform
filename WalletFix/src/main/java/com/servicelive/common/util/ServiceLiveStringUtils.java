package com.servicelive.common.util;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

// TODO: Auto-generated Javadoc
/**
 * $Revision: 1.6 $ $Author: glacy $ $Date: 2008/04/26 00:51:53 $
 */

/*
 * Maintenance History
 * $Log: StringUtils.java,v $
 * Revision 1.6 2008/04/26 00:51:53 glacy
 * Shyam: Merged I18_Fin to HEAD.
 * 
 * Revision 1.4.22.1 2008/04/23 11:42:01 glacy
 * Shyam: Merged HEAD to finance.
 * 
 * Revision 1.5 2008/04/23 05:17:45 hravi
 * Shyam: Reverting to build 247.
 * 
 * Revision 1.4 2008/01/08 21:57:23 schavda
 * added maskString()
 * 
 * Revision 1.3 2007/12/07 15:34:10 mhaye05
 * made sure to check for a -1 in state code
 * 
 * Revision 1.2 2007/12/01 16:12:49 mhaye05
 * added method to concatenate city, state, and zip together
 */
public class ServiceLiveStringUtils extends StringUtils {

	/** SPACE. */
	private static final String SPACE = " ";

	/**
	 * concatenateCityStateZip will concatenate the four input parameters into one string. City and State
	 * will be separated by a comma if both are not empty and zip and zip4 will be separated by an '-'
	 * if both are not empty. If zip4 is not empty but zip is then zip4 will not be included in the
	 * final result.
	 * 
	 * @param city 
	 * @param state 
	 * @param zip 
	 * @param zip4 
	 * 
	 * @return 
	 */
	public static String concatenateCityStateZip(String city, String state, String zip, String zip4) {

		StringBuffer sb = new StringBuffer();
		boolean cityPresent = false;
		boolean statePresent = false;
		boolean zipPresent = false;

		if (StringUtils.isNotEmpty(city)) {
			sb.append(city);
			cityPresent = true;
		}

		if (StringUtils.isNotEmpty(state) && !StringUtils.equals(state, "-1")) {
			if (cityPresent) {
				sb.append(", ");
			}
			sb.append(state);
			statePresent = true;
		}

		if (StringUtils.isNotEmpty(zip)) {
			if (cityPresent || statePresent) {
				sb.append(" ");
			}
			sb.append(zip);
			zipPresent = true;
		}

		if (StringUtils.isNotEmpty(zip4)) {
			if (zipPresent) {
				sb.append("-");
				sb.append(zip4);
			}
		}

		return sb.toString();
	}

	/**
	 * Concatenates all three input parameters together and includes appropriate spacing.
	 * 
	 * @param street1 
	 * @param street2 
	 * @param citystatezip 
	 * 
	 * @return 
	 */
	public static String concatentAllAddressFields(String street1, String street2, String citystatezip) {

		StringBuffer returnValue = new StringBuffer();
		boolean street1Set = false;
		boolean street2Set = false;

		if (StringUtils.isNotEmpty(street1)) {
			returnValue.append(street1);
			street1Set = true;
		}
		if (StringUtils.isNotEmpty(street2)) {
			if (street1Set) {
				returnValue.append(SPACE);
			}
			returnValue.append(street2);
			street2Set = true;
		}
		if (StringUtils.isNotEmpty(citystatezip)) {
			if (street1Set || street2Set) {
				returnValue.append(SPACE);
			}
			returnValue.append(citystatezip);
		}

		return returnValue.toString();
	}

	/**
	 * This methods gets the value of a key from a string containing delimiter1 and delimiter2.
	 * For Examples: sourceString = "Comments:hello|Quantity:2|Size:3"
	 * If we call this method as getKeyVal(sourceString, "Quantity", "|", ":"), it will return 2.
	 * The method will return null if the string does not contain the key.
	 * 
	 * @param sourceString 
	 * @param key 
	 * @param delimiter1 
	 * @param delimiter2 
	 * 
	 * @return the value of the key
	 */
	public static String getKeyVal(String sourceString, String key, String delimiter1, String delimiter2) {

		String val = null;
		StringTokenizer st = new StringTokenizer(sourceString, delimiter1);
		while (st.hasMoreTokens()) {
			String myKeyValue = st.nextToken();
			StringTokenizer myKeyValueToken = new StringTokenizer(myKeyValue, delimiter2);
			String keyVal = myKeyValueToken.nextToken().trim();
			if (myKeyValueToken.hasMoreTokens() && keyVal.equals(key)) {
				return myKeyValueToken.nextToken();
			}
		}
		return val;
	}

	/**
	 * leftPad.
	 * 
	 * @param str 
	 * @param size 
	 * 
	 * @return String
	 */
	public static String leftPad(String str, int size) {

		String arg = str;
		if (arg == null) str = "";
		return StringUtils.leftPad(arg, size, ' ');
	}

	/**
	 * leftPad.
	 * 
	 * @param str 
	 * @param size 
	 * @param padChar 
	 * 
	 * @return String
	 */
	public static String leftPad(String str, int size, char padChar) {

		if (str == null) str = "";
		return StringUtils.leftPad(str, size, padChar);
	}

	/**
	 * leftPad.
	 * 
	 * @param str 
	 * @param size 
	 * @param padStr 
	 * 
	 * @return String
	 */
	public static String leftPad(String str, int size, String padStr) {

		if (str == null) str = "";
		return StringUtils.leftPad(str, size, padStr);
	}

	/**
	 * function will mask the String with the given character.
	 * 
	 * @param input 
	 * @param clearTextLen 
	 * @param maskChar 
	 * 
	 * @return 
	 */
	public static String maskString(String input, int clearTextLen, String maskChar) {

		String finalString = null;
		String clearText = null;
		if (input != null && (!("").equals(input))) {
			int inputLen = input.length();
			if (inputLen <= clearTextLen) {
				return input;
			}
			clearText = input.substring(inputLen - clearTextLen);
			finalString = input.substring(0, inputLen - clearTextLen).replaceAll("[0-9]", maskChar) + clearText;

		}

		return finalString;
	}

	/**
	 * prefixString.
	 * 
	 * @param inputString 
	 * @param finalLen 
	 * 
	 * @return String
	 */
	public static String prefixString(String inputString, int finalLen) {

		String resultString = "";
		StringBuffer concatString = new StringBuffer("");
		int appendLen = 0;

		appendLen = finalLen - inputString.length();

		if (appendLen > 0) {
			for (int i = 0; i < appendLen; i++) {
				concatString = concatString.append(" ");
			}
			resultString = concatString.toString() + inputString;
		} else if (appendLen < 0) {
			resultString = inputString.substring(0, finalLen);
		} else {
			resultString = inputString;
		}
		return resultString;
	}

	/**
	 * <p>
	 * Replaces all occurrences of new line characters within a String.
	 * </p>
	 * 
	 * @param text text to search and replace in
	 * @param with the String to replace with
	 * 
	 * @return the text with any replacements processed, <code>null</code> if null String input
	 */
	public static String replaceNewLineChars(String text, String with) {

		String sourceString = text;
		// Replace the new line character \n
		sourceString = org.apache.commons.lang.StringUtils.replace(sourceString, "\n", with);
		// Replace the new line character \r
		sourceString = org.apache.commons.lang.StringUtils.replace(sourceString, "\r", with);
		// Replace the new line character "\r\n"
		sourceString = org.apache.commons.lang.StringUtils.replace(sourceString, "\r\n", with);

		return sourceString;

	}

	/**
	 * rightPad.
	 * 
	 * @param str 
	 * @param size 
	 * 
	 * @return String
	 */
	public static String rightPad(String str, int size) {

		if (str == null) str = "";
		return StringUtils.rightPad(str, size, ' ');
	}

	/**
	 * rightPad.
	 * 
	 * @param str 
	 * @param size 
	 * @param padChar 
	 * 
	 * @return String
	 */
	public static String rightPad(String str, int size, char padChar) {

		if (str == null) str = "";
		return StringUtils.rightPad(str, size, padChar);
	}

	/**
	 * rightPad.
	 * 
	 * @param str 
	 * @param size 
	 * @param padStr 
	 * 
	 * @return String
	 */
	public static String rightPad(String str, int size, String padStr) {

		if (str == null) str = "";
		return StringUtils.rightPad(str, size, padStr);
	}

	/**
	 * scrubImageMicroReferences.
	 * 
	 * @param outputComments 
	 * 
	 * @return String
	 */
	public static String scrubImageMicroReferences(String outputComments) {

		Matcher matcher = null;
		Pattern pattern = Pattern.compile("IMAGEMICRO", Pattern.CASE_INSENSITIVE | Pattern.COMMENTS | Pattern.MULTILINE);
		Pattern pattern2 = Pattern.compile("IMAGE MICRO", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
		matcher = pattern.matcher(outputComments);
		outputComments = matcher.replaceAll("buyer");

		matcher = pattern2.matcher(outputComments);
		outputComments = matcher.replaceAll("buyer");

		return outputComments;
	}
}
