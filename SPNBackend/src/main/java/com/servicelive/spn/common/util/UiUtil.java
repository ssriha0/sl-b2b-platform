package com.servicelive.spn.common.util;

/**
 * 
 * @author svanloon
 *
 */
public class UiUtil {
	/**
	 * 
	 * @param unformattedPhone
	 * @return String
	 */
	public static String formatPhone(String unformattedPhone) {
		if(unformattedPhone == null || unformattedPhone.length() != 10) {
			return unformattedPhone;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(unformattedPhone.substring(0, 3));
		sb.append("-");
		sb.append(unformattedPhone.substring(3, 6));
		sb.append("-");
		sb.append(unformattedPhone.substring(6));
		return sb.toString();
	}

}
