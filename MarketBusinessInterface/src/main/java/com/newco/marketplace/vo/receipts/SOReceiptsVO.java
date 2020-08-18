/**
 * 
 */
package com.newco.marketplace.vo.receipts;

import java.io.Serializable;

/**
 * @author bindia
 *
 */
public class SOReceiptsVO implements Serializable {
	
	private static final long serialVersionUID = -7659764062265994362L;
	
	private String soID;
	private Integer ledgerEntityID;
	private Integer entityTypeID;
	private Integer ledgerEntryRuleID;
	private Integer entryTypeID;
	
	private Integer transactionID;			
	private Integer ledgerEntryID; 			
	private Double transactionAmount;		
	private String createdDate;
	private String timeDiffLowerThreshold;
	private String timeDiffUpperThreshold;

	/**
	 * @return the transactionID
	 */
	public Integer getTransactionID() {
		return transactionID;
	}
	/**
	 * @param transactionID the transactionID to set
	 */
	public void setTransactionID(Integer transactionID) {
		this.transactionID = transactionID;
	}
	/**
	 * @return the transactionAmount
	 */
	public Double getTransactionAmount() {
		return transactionAmount;
	}
	/**
	 * @param transactionAmount the transactionAmount to set
	 */
	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	/**
	 * @return the ledgerEntryID
	 */
	public Integer getLedgerEntryID() {
		return ledgerEntryID;
	}
	/**
	 * @param ledgerEntryID the ledgerEntryID to set
	 */
	public void setLedgerEntryID(Integer ledgerEntryID) {
		this.ledgerEntryID = ledgerEntryID;
	}
	public String getSoID() {
		return soID;
	}
	public void setSoID(String soID) {
		this.soID = soID;
	}
	public Integer getLedgerEntityID() {
		return ledgerEntityID;
	}
	public void setLedgerEntityID(Integer ledgerEntityID) {
		this.ledgerEntityID = ledgerEntityID;
	}
	public Integer getEntityTypeID() {
		return entityTypeID;
	}
	public void setEntityTypeID(Integer entityTypeID) {
		this.entityTypeID = entityTypeID;
	}
	public Integer getLedgerEntryRuleID() {
		return ledgerEntryRuleID;
	}
	public void setLedgerEntryRuleID(Integer ledgerEntryRuleID) {
		this.ledgerEntryRuleID = ledgerEntryRuleID;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getTimeDiffLowerThreshold() {
		return timeDiffLowerThreshold;
	}
	public void setTimeDiffLowerThreshold(String timeDiffLowerThreshold) {
		this.timeDiffLowerThreshold = timeDiffLowerThreshold;
	}
	public String getTimeDiffUpperThreshold() {
		return timeDiffUpperThreshold;
	}
	public void setTimeDiffUpperThreshold(String timeDiffUpperThreshold) {
		this.timeDiffUpperThreshold = timeDiffUpperThreshold;
	}
	public Integer getEntryTypeID() {
		return entryTypeID;
	}
	public void setEntryTypeID(Integer entryTypeID) {
		this.entryTypeID = entryTypeID;
	}

}
