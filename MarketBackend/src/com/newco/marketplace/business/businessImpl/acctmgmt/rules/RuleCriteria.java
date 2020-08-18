package com.newco.marketplace.business.businessImpl.acctmgmt.rules;

/**
 *  Description of the Class
 *
 *@author     dmill03
 *@created    August 15, 2007
 */
public class RuleCriteria extends ABaseRuleCriteria {
	
	private String  transRuleType 		= null;
	private String  transRuleDescr 		= null;
	private boolean isCredit			= true;
	
	
	public String getTransRuleDescr() {
		return transRuleDescr;
	}
	public void setTransRuleDescr(String transRuleDescr) {
		this.transRuleDescr = transRuleDescr;
	}
	public String getTransRuleType() {
		return transRuleType;
	}
	public void setTransRuleType(String transRuleType) {
		this.transRuleType = transRuleType;
	}
	public boolean isCredit() {
		return isCredit;
	}
	public void setIsACreditAcct(boolean isCredit) {
		this.isCredit = isCredit;
	}
	

}

