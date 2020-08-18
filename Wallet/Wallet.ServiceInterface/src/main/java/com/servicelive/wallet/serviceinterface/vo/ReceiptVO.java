/**
 * 
 */
package com.servicelive.wallet.serviceinterface.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class ReceiptVO implements Serializable {
	
	private static final long serialVersionUID = -7659764062265994362L;
	
	private String soID;
	private Long ledgerEntityID;
	private Integer entityTypeID;
	private LedgerEntryType ledgerEntryRuleID;
	private Integer entryTypeID;
	
	private Integer transactionID;			
	private Integer ledgerEntryID; 			
	private Double transactionAmount;		
	private String createdDate;
	
	public String getCreatedDate() {
		return createdDate;
	}
	
	public Integer getEntityTypeID() {
		return entityTypeID;
	}
	
	public Integer getEntryTypeID() {
		return entryTypeID;
	}
	
	public Long getLedgerEntityID() {
		return ledgerEntityID;
	}

	public Integer getLedgerEntryID() {
		return ledgerEntryID;
	}
	
	public Integer getLedgerEntryRuleID() {
		return ledgerEntryRuleID.getId();
	}
	
	public String getSoID() {
		return soID;
	}

	public Double getTransactionAmount() {
		return transactionAmount;
	}

	public Integer getTransactionID() {
		return transactionID;
	}
	
	@XmlElement()
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	@XmlElement()
	public void setEntityTypeID(Integer entityTypeID) {
		this.entityTypeID = entityTypeID;
	}
	
	@XmlElement()
	public void setEntryTypeID(Integer entryTypeID) {
		this.entryTypeID = entryTypeID;
	}
	
	@XmlElement()
	public void setLedgerEntityID(Long entityId) {
		this.ledgerEntityID = entityId;
	}

	@XmlElement()
	public void setLedgerEntryID(Integer ledgerEntryID) {
		this.ledgerEntryID = ledgerEntryID;
	}
	
	@XmlElement()
	public void setLedgerEntryRuleID(Integer ledgerEntryRuleID) {
		this.ledgerEntryRuleID = LedgerEntryType.fromId(ledgerEntryRuleID);
	}
	
	@XmlElement()
	public void setSoID(String soID) {
		this.soID = soID;
	}

	@XmlElement()
	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	@XmlElement()
	public void setTransactionID(Integer transactionID) {
		this.transactionID = transactionID;
	}

}
