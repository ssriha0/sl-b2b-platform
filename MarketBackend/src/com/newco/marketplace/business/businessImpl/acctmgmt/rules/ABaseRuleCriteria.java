package com.newco.marketplace.business.businessImpl.acctmgmt.rules;

public abstract class ABaseRuleCriteria {
	
	private Integer busTransId 			= -1;
	private String  busTransType 		= null;
	private String  busTransDescr 		= null;
	private Integer ledgerEntryRuleId 	= -1;
	private Integer transactionTypeId 	=  -1;
	private Integer virtualLedgerId		=  -1;
	
	 
	private Integer transRuleEntityTypeId 	= -1; // this field is used to determine which entity
												  // is debited or credited
	private Integer transRuleTAcctNo 		= -1;

	private Integer affectsBalanceInd = 0 ;
	
	private Integer autoAchInd = 0;
	
	public String getBusTransDescr() {
		return busTransDescr;
	}
	public void setBusTransDescr(String busTransDescr) {
		this.busTransDescr = busTransDescr;
	}
	public Integer getBusTransId() {
		return busTransId;
	}
	public void setBusTransId(Integer busTransId) {
		this.busTransId = busTransId;
	}
	public String getBusTransType() {
		return busTransType;
	}
	public void setBusTransType(String busTransType) {
		this.busTransType = busTransType;
	}
	
	public Integer getLedgerEntryRuleId() {
		return ledgerEntryRuleId;
	}
	public void setLedgerEntryRuleId(Integer ledgerEntryRuleId) {
		this.ledgerEntryRuleId = ledgerEntryRuleId;
	}
	
	public Integer getTransactionTypeId() {
		return transactionTypeId;
	}
	public void setTransactionTypeId(Integer transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}
	public Integer getTransRuleEntityTypeId() {
		return transRuleEntityTypeId;
	}
	public void setTransRuleEntityTypeId(Integer transRuleEntityTypeId) {
		this.transRuleEntityTypeId = transRuleEntityTypeId;
	}
	public Integer getTransRuleTAcctNo() {
		return transRuleTAcctNo;
	}
	public void setTransRuleTAcctNo(Integer transRuleTAcctNo) {
		this.transRuleTAcctNo = transRuleTAcctNo;
	}
	
	public Integer getVirtualLedgerId() {
		return virtualLedgerId;
	}
	public void setVirtualLedgerId(Integer virtualLedgerId) {
		this.virtualLedgerId = virtualLedgerId;
	}
	public Integer getAffectsBalanceInd() {
		return affectsBalanceInd;
	}
	public void setAffectsBalanceInd(Integer affectsBalanceInd) {
		this.affectsBalanceInd = affectsBalanceInd;
	}
	public Integer getAutoAchInd() {
		return autoAchInd;
	}
	public void setAutoAchInd(Integer autoAchInd) {
		this.autoAchInd = autoAchInd;
	}

	
	
	//public 
}
