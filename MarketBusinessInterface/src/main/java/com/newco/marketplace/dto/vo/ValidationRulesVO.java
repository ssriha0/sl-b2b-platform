package com.newco.marketplace.dto.vo;



/**
 * Priority 5B changes
 * Class to hold the column values
 * of lu_validation_rules table
 *
 */
public class ValidationRulesVO{
	
	private String field;
	private String rule;
	private String regex;
	private String errorMsg;
	
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getRule() {
		return rule;
	}
	public void setRule(String rule) {
		this.rule = rule;
	}
	public String getRegex() {
		return regex;
	}
	public void setRegex(String regex) {
		this.regex = regex;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	
}
