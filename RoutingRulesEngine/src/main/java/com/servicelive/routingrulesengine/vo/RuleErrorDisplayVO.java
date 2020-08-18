package com.servicelive.routingrulesengine.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * This class is used to display ruleError of
 * car rule.
 * 
 */
public class RuleErrorDisplayVO implements Serializable {
	private static final long serialVersionUID = 4209518599795591722L;
	
	private String errorName;
	private String errorType;
	private List<String> errorCaue;
	private List<String> error;
	public String getErrorName() {
		return errorName;
	}
	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public List<String> getErrorCaue() {
		return errorCaue;
	}
	public void setErrorCaue(List<String> errorCaue) {
		this.errorCaue = errorCaue;
	}
	public List<String> getError() {
		return error;
	}
	public void setError(List<String> error) {
		this.error = error;
	}
	public static void hashMap(Map hashmap)
	{
		hashmap.put(1, "Invalid Zip Code");
		hashmap.put(2, "Invalid job Code");
		hashmap.put(3, "Missing mandatory");
		hashmap.put(4, "Conflicting jobcodes");
		hashmap.put(5, "Conflicting zipcodes");
		hashmap.put(6, "Conflicting pickuplocations");
		hashmap.put(7, "Zipcodes Added");
		hashmap.put(8, "Zipcodes Removed");
		hashmap.put(9, "Jobcodes Added");
		hashmap.put(10, "Jobcodes Removed");
		hashmap.put(11, "Invalid Firms");
		hashmap.put(12, "Invalid Custom Ref");
	}
	

	}
