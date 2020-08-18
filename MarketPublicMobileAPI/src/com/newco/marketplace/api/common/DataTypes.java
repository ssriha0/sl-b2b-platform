package com.newco.marketplace.api.common;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.utils.DateUtils;

public enum DataTypes {
	STRING,
	INTEGER,
	DOUBLE,
	MONEY,
	DATE;
	

	public static boolean validate(String param, DataTypes type) {
		switch (type) {
		case STRING:
			return validateString(param);
		case INTEGER:
			return validateInteger(param);
		case DOUBLE:
			return validateDouble(param);
		case MONEY:
			return false;
		case DATE:
			return validateDate(param);
		default:
			return false;
		}
	}
	
	public static boolean validate(Object param, DataTypes type) {
		switch (type) {
		case STRING:
			return param instanceof String;
		case INTEGER:
			boolean flag = param instanceof Integer;
			if (flag == true) {
				return flag;
			}
			else {
				flag = param instanceof String;
				String str = (String)param;
				if (flag == true && StringUtils.isNotBlank(str)) {
					flag =  StringUtils.isNumeric(str);
					return flag;
				} else{
					return false;
				}				
			}
		case DOUBLE:
			return  param instanceof Double;
		case MONEY:
			return false;
		case DATE:
			return param instanceof Date;
		default:
			return false;
		}
	}
	
	public static boolean  validateString(String value) {
		if (StringUtils.isBlank(value))
			return false;
		else
			return true;
	}
	
	public static boolean validateInteger(String value) {
		try {  
			Integer.parseInt(value);  
			return true;  
		} catch (Exception e) {  
			return false;  
		} 	
	}
	
	public static boolean validateDouble(String value) {
		try {  
			Double.parseDouble(value);
			return true;  
		} catch (Exception e) {  
			return false;  
		} 
	}
	
	public static boolean validateDate(String value) {
		Date date = DateUtils.getDateFromString(value, "MMddyyyy");
		if (date == null) 
			return false;
		return true;
	}	
	
}
