package com.newco.marketplace.dto.vo.ledger;

import com.sears.os.vo.SerializableBaseVO;

public class TransactionRuleVO extends SerializableBaseVO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6492112416058222752L;
	private Integer busTransId 			= -1;
	private String  busTransType 		= null;
	private String  busTransDescr 		= null;
	private Integer ledgerEntryRuleId 	= -1;
	private Integer ledgerEntityTypeId 	= -1;
	private Integer transactionTypeId 	= -1;
	
	private String  transRuleType 		= null;
	private String  transRuleDescr 		= null;
	
	private String  entityType 				= null;
	private String  entityDescr 			= null;
	private Integer entityDefaultEntityId 	= -1;
	
	private Integer transRuleDebitEntityTypeId 	= -1;
	private Integer transRuleDebitTAcctNo 		= -1;
	private Integer refAcctsDebitTAcctNo 	= -1;
	private String  refAcctsDebitLocn 		= null;
	private String  refAcctsDebitDescr1 	= null;
	private String  refAcctsDebitDescr2 	= null;
	
	private Integer transRuleCreditEntityTypeId = -1;
	private Integer transRuleCreditTAcctNo 		= -1;
	private Integer refAcctsCreditTAcctNo 	= -1;
	private String  refAcctsCreditLocn 		= null;
	private String  refAcctsCreditDescr1 	= null;
	private String  refAcctsCreditDescr2 	= null;
	
	private Integer affectsBalanceInd = 0 ;
	
	private Integer  fundingTypeId 	= null;
	
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
	public Integer getEntityDefaultEntityId() {
		return entityDefaultEntityId;
	}
	public void setEntityDefaultEntityId(Integer entityDefaultEntityId) {
		this.entityDefaultEntityId = entityDefaultEntityId;
	}
	public String getEntityDescr() {
		return entityDescr;
	}
	public void setEntityDescr(String entityDescr) {
		this.entityDescr = entityDescr;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public Integer getLedgerEntityTypeId() {
		return ledgerEntityTypeId;
	}
	public void setLedgerEntityTypeId(Integer ledgerEntityTypeId) {
		this.ledgerEntityTypeId = ledgerEntityTypeId;
	}
	public Integer getLedgerEntryRuleId() {
		return ledgerEntryRuleId;
	}
	public void setLedgerEntryRuleId(Integer ledgerEntryRuleId) {
		this.ledgerEntryRuleId = ledgerEntryRuleId;
	}
	public String getRefAcctsCreditDescr1() {
		return refAcctsCreditDescr1;
	}
	public void setRefAcctsCreditDescr1(String refAcctsCreditDescr1) {
		this.refAcctsCreditDescr1 = refAcctsCreditDescr1;
	}
	public String getRefAcctsCreditDescr2() {
		return refAcctsCreditDescr2;
	}
	public void setRefAcctsCreditDescr2(String refAcctsCreditDescr2) {
		this.refAcctsCreditDescr2 = refAcctsCreditDescr2;
	}
	public String getRefAcctsCreditLocn() {
		return refAcctsCreditLocn;
	}
	public void setRefAcctsCreditLocn(String refAcctsCreditLocn) {
		this.refAcctsCreditLocn = refAcctsCreditLocn;
	}
	public Integer getRefAcctsCreditTAcctNo() {
		return refAcctsCreditTAcctNo;
	}
	public void setRefAcctsCreditTAcctNo(Integer refAcctsCreditTAcctNo) {
		this.refAcctsCreditTAcctNo = refAcctsCreditTAcctNo;
	}
	public String getRefAcctsDebitDescr1() {
		return refAcctsDebitDescr1;
	}
	public void setRefAcctsDebitDescr1(String refAcctsDebitDescr1) {
		this.refAcctsDebitDescr1 = refAcctsDebitDescr1;
	}
	public String getRefAcctsDebitDescr2() {
		return refAcctsDebitDescr2;
	}
	public void setRefAcctsDebitDescr2(String refAcctsDebitDescr2) {
		this.refAcctsDebitDescr2 = refAcctsDebitDescr2;
	}
	public String getRefAcctsDebitLocn() {
		return refAcctsDebitLocn;
	}
	public void setRefAcctsDebitLocn(String refAcctsDebitLocn) {
		this.refAcctsDebitLocn = refAcctsDebitLocn;
	}
	public Integer getRefAcctsDebitTAcctNo() {
		return refAcctsDebitTAcctNo;
	}
	public void setRefAcctsDebitTAcctNo(Integer refAcctsDebitTAcctNo) {
		this.refAcctsDebitTAcctNo = refAcctsDebitTAcctNo;
	}
	public Integer getTransactionTypeId() {
		return transactionTypeId;
	}
	public void setTransactionTypeId(Integer transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}
	public Integer getTransRuleCreditEntityTypeId() {
		return transRuleCreditEntityTypeId;
	}
	public void setTransRuleCreditEntityTypeId(Integer transRuleCreditEntityTypeId) {
		this.transRuleCreditEntityTypeId = transRuleCreditEntityTypeId;
	}
	public Integer getTransRuleCreditTAcctNo() {
		return transRuleCreditTAcctNo;
	}
	public void setTransRuleCreditTAcctNo(Integer transRuleCreditTAcctNo) {
		this.transRuleCreditTAcctNo = transRuleCreditTAcctNo;
	}
	public Integer getTransRuleDebitEntityTypeId() {
		return transRuleDebitEntityTypeId;
	}
	public void setTransRuleDebitEntityTypeId(Integer transRuleDebitEntityTypeId) {
		this.transRuleDebitEntityTypeId = transRuleDebitEntityTypeId;
	}
	public Integer getTransRuleDebitTAcctNo() {
		return transRuleDebitTAcctNo;
	}
	public void setTransRuleDebitTAcctNo(Integer transRuleDebitTAcctNo) {
		this.transRuleDebitTAcctNo = transRuleDebitTAcctNo;
	}
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
	public Integer getFundingTypeId() {
		return fundingTypeId;
	}
	public void setFundingTypeId(Integer fundingTypeId) {
		this.fundingTypeId = fundingTypeId;
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
    
    
}//end class SecretQuectionVO