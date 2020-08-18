package com.servicelive.routingrulesengine.vo;

import java.io.Serializable;

/**
 * 
 * This class is used to display rulename,zipcodes,jobcodes and pickup locations of conflict of
 * car rule.
 * 
 */
public class RuleErrorVO implements Serializable {
	private static final long serialVersionUID = 4209518599795591722L;
	
	private String errorCause;
	private String errorDescription;
	
	public String getErrorCause() {
		return errorCause;
	}
	public void setErrorCause(String errorCause) {
		this.errorCause = errorCause;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	
}
