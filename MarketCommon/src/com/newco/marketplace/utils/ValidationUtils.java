package com.newco.marketplace.utils;

public class ValidationUtils {
	
	/**
	 * Method checks if a string value is a valid integer
	 * @param val
	 * @return boolean
	 */
	public static boolean isInteger(String val){
		boolean isInteger = true;
		try{
			Integer.parseInt(val);
		}catch(NumberFormatException numEx){
			isInteger = false;
		}
		return isInteger;
	}


}
