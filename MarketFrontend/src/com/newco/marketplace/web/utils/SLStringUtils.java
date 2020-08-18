package com.newco.marketplace.web.utils;

import org.apache.log4j.Logger;

import com.newco.marketplace.web.action.wizard.pricing.SOWPricingAction;


public class SLStringUtils {
	
	private static final Logger logger = Logger.getLogger(SLStringUtils.class);

	
	static public boolean isNullOrEmpty(String str)
	{
		if(str == null)
			return true;
		
		if(str.equals(""))
			return true;
		
		return false;
	}
	
	// recognizes integers, floats, and doubles as a number
	static public boolean IsParsableNumber(String str)
	{
		try
		{
			Double.parseDouble(str);
			return true;
		}
		catch(Exception nfe)
		{
			logger.warn("Not parsable number: "+ str +" : ",nfe);
			return false;
		}
	}


	/**
	 * Description:  Checks whether a String is valid number by stripping commans and 
	 * checking for a precision of two decimal places.
	 * @param str
	 * @return Double or null if invalid
	 */
	static public Double getMonetaryNumber(String str) {
		Double validMonetary = null;
		// Strip commas
		if (str != null){
			String noCommas = org.apache.commons.lang.StringUtils.replace(str, ",", "");
			if (org.apache.commons.lang.StringUtils.contains(noCommas, ".")) {
				String decimals = noCommas.substring(str.indexOf(".")+1);
				// Check if precision greater than 2 and just return two
				if (decimals.length() > 2) {
					str = noCommas.substring(0, ((noCommas.length() - decimals.length()) + 2));
				}
				else 
				{
					str = noCommas;
				}
			}
			else 
			{
				str = noCommas;
			}
			try {
				// Check if all values are numeric
				validMonetary = Double.parseDouble(str);
			} catch (Exception nfe) {
				logger.error("Not a parsable string : " + str + " : ", nfe);
			}
		}
		return validMonetary;
	}
	
	// Sets an upper limit on the spend limit
	static public boolean IsSpendLimit(String str)
	{
		try
		{
			double x = Double.parseDouble(str);
			if (x > 10000000)
				return false;
		}
		catch(NumberFormatException nfe)
		{
			return false;
		}
		return true;
	}
	
	//set the lower limit to not to be negative
	static public boolean IsNotNegative(String str)
	{
		try
		{
			double x = Double.parseDouble(str);
			if (x <0d)
				return false;
		}
		catch(NumberFormatException nfe)
		{
			return false;
		}
		return true;
	}
	
	static public boolean isEmailValid(String email)
	{
		if(email == null)
			return false;
		
		int atIndex = email.indexOf('@');
		int dotIndex = email.indexOf('.');
		int atLastIndex = email.lastIndexOf('@');
		int dotLastIndex = email.lastIndexOf('.');
		char dotNext = 0 ;
		if(dotLastIndex+1 != email.length()){
			dotNext = email.charAt(dotIndex+1);
		}
		char dotNexttoAT = email.charAt(atIndex+1);
		if(atIndex > 0 && dotIndex > 0 && dotLastIndex >atIndex && atLastIndex == atIndex 
				&& !email.endsWith(".") && dotNext != '.' && dotNexttoAT != '.'){
			return true;
		}
		
		return false;
	}
	
	static public String getAreaCodeFromPhoneNumber(String phone)
	{
		if(isNullOrEmpty(phone))
			return "";
		
		if(phone.length() < 10)
			return "";
		
		return phone.substring(0,3);//First 3 digits
	}
	
	static public String getPart1FromPhoneNumber(String phone)
	{
		if(isNullOrEmpty(phone))
			return "";

		if(phone.length() < 10)
			return "";
		
		return phone.substring(3,6);//Middle 3 digits
	}
	
	static public String getPart2FromPhoneNumber(String phone)
	{
		if(isNullOrEmpty(phone))
			return "";

		if(phone.length() < 10)
			return "";
		
		return phone.substring(6,10);// Last 4 digits
	}
	
	/**
	 * Capitalize first letter of a string and converts remaining letters to lowercase
	 * @param str
	 * @return
	 */
	static public String capitalizeFirstLetter(String str){
		str = str.substring(0,1).toUpperCase()+str.substring(1).toLowerCase();
		return str;
	}
}
