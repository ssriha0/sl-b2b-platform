package com.newco.marketplace.dto.vo.logging;

import java.util.Date;

import com.sears.os.vo.ABaseVO;

public class SoAutoCloseDetailVo extends ABaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6809168278630367195L;
	
	
	private String ruleStatus=null;
	private String ruleName=null;
	private String ruleDescription=null;
	private int criteriaValue;
	public String getRuleStatus() {
		return ruleStatus;
	}
	public void setRuleStatus(String ruleStatus) {
		this.ruleStatus = ruleStatus;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleDescription() {
		return ruleDescription;
	}
	public void setRuleDescription(String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}
	public int getCriteriaValue() {
		return criteriaValue;
	}
	public void setCriteriaValue(int criteriaValue) {
		this.criteriaValue = criteriaValue;
	}
	
	@Override
	public String toString(){
		//TODO
		return("<SoAutoCloseDetailVo" );
	}
	
	
	
}
