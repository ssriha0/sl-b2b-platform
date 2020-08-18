package com.newco.marketplace.dto.vo.ledger;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;


public class TransactionEntryVO extends SerializableBaseVO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6729272647297236103L;
	private Integer transactionId;
    private Integer transactionTypeId;
    private Integer ledgerEntryId;
    private Integer ledgerEntityTypeId;
    private Integer ledgerEntityId;
    private Integer originatingBuyerId;
    private Integer ledgerEntryRuleId;
    private int entryTypeId;
    private Integer taccountNo;
    private Long accountNumber;
    private Date createdDate; 
    private Date modifiedDate;
    private String modifiedBy;
    private boolean isCredit = false;
    private Double transactionAmount;
    private boolean CCInd;
    private Integer reconsiledInd; 
   
    
	public boolean isCCInd() {
		return CCInd;
	}
	public void setCCInd(boolean CCInd) {
		this.CCInd = CCInd;
	}
	public Integer getLedgerEntityId() {
		return ledgerEntityId;
	}
	public void setLedgerEntityId(Integer ledgerEntityId) {
		this.ledgerEntityId = ledgerEntityId;
	}
	public Integer getLedgerEntityTypeId() {
		return ledgerEntityTypeId;
	}
	public void setLedgerEntityTypeId(Integer ledgerEntityTypeId) {
		this.ledgerEntityTypeId = ledgerEntityTypeId;
	}
	
	public Integer getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	public Integer getTransactionTypeId() {
		return transactionTypeId;
	}
	public void setTransactionTypeId(Integer transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}
	public int getEntryTypeId() {
		return entryTypeId;
	}
	public void setEntryTypeId(int entryTypeId) {
		this.entryTypeId = entryTypeId;
	}
	public boolean isCredit() {
		return isCredit;
	}
	public void setIsACreditTransaction(boolean isCredit) {
		this.isCredit = isCredit;
	}
	public Integer getLedgerEntryId() {
		return ledgerEntryId;
	}
	public void setLedgerEntryId(Integer ledgerEntryId) {
		this.ledgerEntryId = ledgerEntryId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getTaccountNo() {
		return taccountNo;
	}
	public void setTaccountNo(Integer taccountNo) {
		this.taccountNo = taccountNo;
	}
	public Double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public Long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Integer getLedgerEntryRuleId() {
		return ledgerEntryRuleId;
	}
	public void setLedgerEntryRuleId(Integer ledgerEntryRuleId) {
		this.ledgerEntryRuleId = ledgerEntryRuleId;
	}
	public void setCredit(boolean isCredit) {
		this.isCredit = isCredit;
	}
	public Integer getReconsiledInd() {
		return reconsiledInd;
	}
	public void setReconsiledInd(Integer reconsiledInd) {
		this.reconsiledInd = reconsiledInd;
	}
	public Integer getOriginatingBuyerId() {
		return originatingBuyerId;
	}
	public void setOriginatingBuyerId(Integer originatingBuyerId) {
		this.originatingBuyerId = originatingBuyerId;
	}

    
}//end class SecretQuectionVO