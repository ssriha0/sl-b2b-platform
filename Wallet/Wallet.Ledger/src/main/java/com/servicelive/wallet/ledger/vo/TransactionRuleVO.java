package com.servicelive.wallet.ledger.vo;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * Class TransactionRuleVO.
 */
public class TransactionRuleVO implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 6492112416058222752L;

	/** affectsBalanceInd. */
	private Integer affectsBalanceInd = 0;

	/** autoAchInd. */
	private Integer autoAchInd = 0;

	/** busTransDescr. */
	private String busTransDescr = null;

	/** busTransId. */
	private Long busTransId = -1L;

	/** busTransType. */
	private String busTransType = null;

	/** entityDefaultEntityId. */
	private Long entityDefaultEntityId = -1L;

	/** entityDescr. */
	private String entityDescr = null;

	/** entityType. */
	private String entityType = null;

	/** fundingTypeId. */
	private Long fundingTypeId = null;

	/** ledgerEntityTypeId. */
	private Integer ledgerEntityTypeId = -1;

	/** ledgerEntryRuleId. */
	private Long ledgerEntryRuleId = -1L;

	/** refAcctsCreditDescr1. */
	private String refAcctsCreditDescr1 = null;

	/** refAcctsCreditDescr2. */
	private String refAcctsCreditDescr2 = null;

	/** refAcctsCreditLocn. */
	private String refAcctsCreditLocn = null;

	/** refAcctsCreditTAcctNo. */
	private Long refAcctsCreditTAcctNo = -1L;

	/** refAcctsDebitDescr1. */
	private String refAcctsDebitDescr1 = null;

	/** refAcctsDebitDescr2. */
	private String refAcctsDebitDescr2 = null;

	/** refAcctsDebitLocn. */
	private String refAcctsDebitLocn = null;

	/** refAcctsDebitTAcctNo. */
	private Long refAcctsDebitTAcctNo = -1L;

	/** transactionTypeId. */
	private Long transactionTypeId = -1L;

	/** transRuleCreditEntityTypeId. */
	private Long transRuleCreditEntityTypeId = -1L;

	/** transRuleCreditTAcctNo. */
	private Long transRuleCreditTAcctNo = -1L;

	/** transRuleDebitEntityTypeId. */
	private Long transRuleDebitEntityTypeId = -1L;

	/** transRuleDebitTAcctNo. */
	private Long transRuleDebitTAcctNo = -1L;

	/** transRuleDescr. */
	private String transRuleDescr = null;

	/** transRuleType. */
	private String transRuleType = null;

	/**
	 * getAffectsBalanceInd.
	 * 
	 * @return Integer
	 */
	public Integer getAffectsBalanceInd() {

		return affectsBalanceInd;
	}

	/**
	 * getAutoAchInd.
	 * 
	 * @return Integer
	 */
	public Integer getAutoAchInd() {

		return autoAchInd;
	}

	/**
	 * getBusTransDescr.
	 * 
	 * @return String
	 */
	public String getBusTransDescr() {

		return busTransDescr;
	}

	/**
	 * getBusTransId.
	 * 
	 * @return Long
	 */
	public Long getBusTransId() {

		return busTransId;
	}

	/**
	 * getBusTransType.
	 * 
	 * @return String
	 */
	public String getBusTransType() {

		return busTransType;
	}

	/**
	 * getEntityDefaultEntityId.
	 * 
	 * @return Long
	 */
	public Long getEntityDefaultEntityId() {

		return entityDefaultEntityId;
	}

	/**
	 * getEntityDescr.
	 * 
	 * @return String
	 */
	public String getEntityDescr() {

		return entityDescr;
	}

	/**
	 * getEntityType.
	 * 
	 * @return String
	 */
	public String getEntityType() {

		return entityType;
	}

	/**
	 * getFundingTypeId.
	 * 
	 * @return Long
	 */
	public Long getFundingTypeId() {

		return fundingTypeId;
	}

	/**
	 * getLedgerEntityTypeId.
	 * 
	 * @return Long
	 */
	public Integer getLedgerEntityTypeId() {

		return ledgerEntityTypeId;
	}

	/**
	 * getLedgerEntryRuleId.
	 * 
	 * @return Long
	 */
	public Long getLedgerEntryRuleId() {

		return ledgerEntryRuleId;
	}

	/**
	 * getRefAcctsCreditDescr1.
	 * 
	 * @return String
	 */
	public String getRefAcctsCreditDescr1() {

		return refAcctsCreditDescr1;
	}

	/**
	 * getRefAcctsCreditDescr2.
	 * 
	 * @return String
	 */
	public String getRefAcctsCreditDescr2() {

		return refAcctsCreditDescr2;
	}

	/**
	 * getRefAcctsCreditLocn.
	 * 
	 * @return String
	 */
	public String getRefAcctsCreditLocn() {

		return refAcctsCreditLocn;
	}

	/**
	 * getRefAcctsCreditTAcctNo.
	 * 
	 * @return Long
	 */
	public Long getRefAcctsCreditTAcctNo() {

		return refAcctsCreditTAcctNo;
	}

	/**
	 * getRefAcctsDebitDescr1.
	 * 
	 * @return String
	 */
	public String getRefAcctsDebitDescr1() {

		return refAcctsDebitDescr1;
	}

	/**
	 * getRefAcctsDebitDescr2.
	 * 
	 * @return String
	 */
	public String getRefAcctsDebitDescr2() {

		return refAcctsDebitDescr2;
	}

	/**
	 * getRefAcctsDebitLocn.
	 * 
	 * @return String
	 */
	public String getRefAcctsDebitLocn() {

		return refAcctsDebitLocn;
	}

	/**
	 * getRefAcctsDebitTAcctNo.
	 * 
	 * @return Long
	 */
	public Long getRefAcctsDebitTAcctNo() {

		return refAcctsDebitTAcctNo;
	}

	/**
	 * getTransactionTypeId.
	 * 
	 * @return Long
	 */
	public Long getTransactionTypeId() {

		return transactionTypeId;
	}

	/**
	 * getTransRuleCreditEntityTypeId.
	 * 
	 * @return Long
	 */
	public Long getTransRuleCreditEntityTypeId() {

		return transRuleCreditEntityTypeId;
	}

	/**
	 * getTransRuleCreditTAcctNo.
	 * 
	 * @return Long
	 */
	public Long getTransRuleCreditTAcctNo() {

		return transRuleCreditTAcctNo;
	}

	/**
	 * getTransRuleDebitEntityTypeId.
	 * 
	 * @return Long
	 */
	public Long getTransRuleDebitEntityTypeId() {

		return transRuleDebitEntityTypeId;
	}

	/**
	 * getTransRuleDebitTAcctNo.
	 * 
	 * @return Long
	 */
	public Long getTransRuleDebitTAcctNo() {

		return transRuleDebitTAcctNo;
	}

	/**
	 * getTransRuleDescr.
	 * 
	 * @return String
	 */
	public String getTransRuleDescr() {

		return transRuleDescr;
	}

	/**
	 * getTransRuleType.
	 * 
	 * @return String
	 */
	public String getTransRuleType() {

		return transRuleType;
	}

	/**
	 * setAffectsBalanceInd.
	 * 
	 * @param affectsBalanceInd 
	 * 
	 * @return void
	 */
	public void setAffectsBalanceInd(Integer affectsBalanceInd) {

		this.affectsBalanceInd = affectsBalanceInd;
	}

	/**
	 * setAutoAchInd.
	 * 
	 * @param autoAchInd 
	 * 
	 * @return void
	 */
	public void setAutoAchInd(Integer autoAchInd) {

		this.autoAchInd = autoAchInd;
	}

	/**
	 * setBusTransDescr.
	 * 
	 * @param busTransDescr 
	 * 
	 * @return void
	 */
	public void setBusTransDescr(String busTransDescr) {

		this.busTransDescr = busTransDescr;
	}

	/**
	 * setBusTransId.
	 * 
	 * @param busTransId 
	 * 
	 * @return void
	 */
	public void setBusTransId(Long busTransId) {

		this.busTransId = busTransId;
	}

	/**
	 * setBusTransType.
	 * 
	 * @param busTransType 
	 * 
	 * @return void
	 */
	public void setBusTransType(String busTransType) {

		this.busTransType = busTransType;
	}

	/**
	 * setEntityDefaultEntityId.
	 * 
	 * @param entityDefaultEntityId 
	 * 
	 * @return void
	 */
	public void setEntityDefaultEntityId(Long entityDefaultEntityId) {

		this.entityDefaultEntityId = entityDefaultEntityId;
	}

	/**
	 * setEntityDescr.
	 * 
	 * @param entityDescr 
	 * 
	 * @return void
	 */
	public void setEntityDescr(String entityDescr) {

		this.entityDescr = entityDescr;
	}

	/**
	 * setEntityType.
	 * 
	 * @param entityType 
	 * 
	 * @return void
	 */
	public void setEntityType(String entityType) {

		this.entityType = entityType;
	}

	/**
	 * setFundingTypeId.
	 * 
	 * @param fundingTypeId 
	 * 
	 * @return void
	 */
	public void setFundingTypeId(Long fundingTypeId) {

		this.fundingTypeId = fundingTypeId;
	}

	/**
	 * setLedgerEntityTypeId.
	 * 
	 * @param ledgerEntityTypeId 
	 * 
	 * @return void
	 */
	public void setLedgerEntityTypeId(Integer ledgerEntityTypeId) {

		this.ledgerEntityTypeId = ledgerEntityTypeId;
	}

	/**
	 * setLedgerEntryRuleId.
	 * 
	 * @param ledgerEntryRuleId 
	 * 
	 * @return void
	 */
	public void setLedgerEntryRuleId(Long ledgerEntryRuleId) {

		this.ledgerEntryRuleId = ledgerEntryRuleId;
	}

	/**
	 * setRefAcctsCreditDescr1.
	 * 
	 * @param refAcctsCreditDescr1 
	 * 
	 * @return void
	 */
	public void setRefAcctsCreditDescr1(String refAcctsCreditDescr1) {

		this.refAcctsCreditDescr1 = refAcctsCreditDescr1;
	}

	/**
	 * setRefAcctsCreditDescr2.
	 * 
	 * @param refAcctsCreditDescr2 
	 * 
	 * @return void
	 */
	public void setRefAcctsCreditDescr2(String refAcctsCreditDescr2) {

		this.refAcctsCreditDescr2 = refAcctsCreditDescr2;
	}

	/**
	 * setRefAcctsCreditLocn.
	 * 
	 * @param refAcctsCreditLocn 
	 * 
	 * @return void
	 */
	public void setRefAcctsCreditLocn(String refAcctsCreditLocn) {

		this.refAcctsCreditLocn = refAcctsCreditLocn;
	}

	/**
	 * setRefAcctsCreditTAcctNo.
	 * 
	 * @param refAcctsCreditTAcctNo 
	 * 
	 * @return void
	 */
	public void setRefAcctsCreditTAcctNo(Long refAcctsCreditTAcctNo) {

		this.refAcctsCreditTAcctNo = refAcctsCreditTAcctNo;
	}

	/**
	 * setRefAcctsDebitDescr1.
	 * 
	 * @param refAcctsDebitDescr1 
	 * 
	 * @return void
	 */
	public void setRefAcctsDebitDescr1(String refAcctsDebitDescr1) {

		this.refAcctsDebitDescr1 = refAcctsDebitDescr1;
	}

	/**
	 * setRefAcctsDebitDescr2.
	 * 
	 * @param refAcctsDebitDescr2 
	 * 
	 * @return void
	 */
	public void setRefAcctsDebitDescr2(String refAcctsDebitDescr2) {

		this.refAcctsDebitDescr2 = refAcctsDebitDescr2;
	}

	/**
	 * setRefAcctsDebitLocn.
	 * 
	 * @param refAcctsDebitLocn 
	 * 
	 * @return void
	 */
	public void setRefAcctsDebitLocn(String refAcctsDebitLocn) {

		this.refAcctsDebitLocn = refAcctsDebitLocn;
	}

	/**
	 * setRefAcctsDebitTAcctNo.
	 * 
	 * @param refAcctsDebitTAcctNo 
	 * 
	 * @return void
	 */
	public void setRefAcctsDebitTAcctNo(Long refAcctsDebitTAcctNo) {

		this.refAcctsDebitTAcctNo = refAcctsDebitTAcctNo;
	}

	/**
	 * setTransactionTypeId.
	 * 
	 * @param transactionTypeId 
	 * 
	 * @return void
	 */
	public void setTransactionTypeId(Long transactionTypeId) {

		this.transactionTypeId = transactionTypeId;
	}

	/**
	 * setTransRuleCreditEntityTypeId.
	 * 
	 * @param transRuleCreditEntityTypeId 
	 * 
	 * @return void
	 */
	public void setTransRuleCreditEntityTypeId(Long transRuleCreditEntityTypeId) {

		this.transRuleCreditEntityTypeId = transRuleCreditEntityTypeId;
	}

	/**
	 * setTransRuleCreditTAcctNo.
	 * 
	 * @param transRuleCreditTAcctNo 
	 * 
	 * @return void
	 */
	public void setTransRuleCreditTAcctNo(Long transRuleCreditTAcctNo) {

		this.transRuleCreditTAcctNo = transRuleCreditTAcctNo;
	}

	/**
	 * setTransRuleDebitEntityTypeId.
	 * 
	 * @param transRuleDebitEntityTypeId 
	 * 
	 * @return void
	 */
	public void setTransRuleDebitEntityTypeId(Long transRuleDebitEntityTypeId) {

		this.transRuleDebitEntityTypeId = transRuleDebitEntityTypeId;
	}

	/**
	 * setTransRuleDebitTAcctNo.
	 * 
	 * @param transRuleDebitTAcctNo 
	 * 
	 * @return void
	 */
	public void setTransRuleDebitTAcctNo(Long transRuleDebitTAcctNo) {

		this.transRuleDebitTAcctNo = transRuleDebitTAcctNo;
	}

	/**
	 * setTransRuleDescr.
	 * 
	 * @param transRuleDescr 
	 * 
	 * @return void
	 */
	public void setTransRuleDescr(String transRuleDescr) {

		this.transRuleDescr = transRuleDescr;
	}

	/**
	 * setTransRuleType.
	 * 
	 * @param transRuleType 
	 * 
	 * @return void
	 */
	public void setTransRuleType(String transRuleType) {

		this.transRuleType = transRuleType;
	}

}// end class SecretQuectionVO