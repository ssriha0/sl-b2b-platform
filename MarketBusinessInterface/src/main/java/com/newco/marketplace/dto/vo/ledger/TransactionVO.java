package com.newco.marketplace.dto.vo.ledger;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

public class TransactionVO extends SerializableBaseVO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5169985142281155774L;
	private Integer ledgerEntryId;
    private Integer businessTransId;
    private Integer ledgerEntryRuleId;
    private Timestamp ledgerEntryDate;
    private String referenceNo;
    private String soId;
    private String entryRemark;
    private boolean reconsiledInd;
    private Timestamp reconsidedDate;
    private boolean authorizedInd;
    private Date authorizedDate;
    private String authorizationNo;
    private Timestamp createdDate;
    private Timestamp modified_date;
    private String modified_by;
    private Double transactionAmount;
    private Integer glProcessed;
    private Timestamp glProcessedDate;
    private Integer transferReasonCode;
    private String user;
    private Integer affectsBalanceInd;
    private Integer autoAchInd;
    private ArrayList<TransactionEntryVO> _transactions = new ArrayList<TransactionEntryVO>();
    
    
	public Date getAuthorizedDate() {
		return authorizedDate;
	}
	public void setAuthorizedDate(Date authorizedDate) {
		this.authorizedDate = authorizedDate;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getAuthorizationNo() {
		return authorizationNo;
	}
	public void setAuthorizationNo(String authorizationNo) {
		this.authorizationNo = authorizationNo;
	}
	public boolean isAuthorizedInd() {
		return authorizedInd;
	}
	public void setAuthorizedInd(boolean authorizedInd) {
		this.authorizedInd = authorizedInd;
	}
	public String getEntryRemark() {
		return entryRemark;
	}
	public void setEntryRemark(String entryRemark) {
		this.entryRemark = entryRemark;
	}
	public Integer getLedgerEntryId() {
		return ledgerEntryId;
	}
	public void setLedgerEntryId(Integer ledgerEntryId) {
		this.ledgerEntryId = ledgerEntryId;
	}
	public Integer getLedgerEntryRuleId() {
		return ledgerEntryRuleId;
	}
	public void setLedgerEntryRuleId(Integer ledgerEntryRuleId) {
		this.ledgerEntryRuleId = ledgerEntryRuleId;
	}
	public Timestamp getReconsidedDate() {
		return reconsidedDate;
	}
	public boolean isReconsiledInd() {
		return reconsiledInd;
	}
	public void setReconsiledInd(boolean reconsiledInd) {
		this.reconsiledInd = reconsiledInd;
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public Timestamp getLedgerEntryDate() {
		return ledgerEntryDate;
	}
	public void setLedgerEntryDate(Timestamp ledgerEntryDate) {
		this.ledgerEntryDate = ledgerEntryDate;
	}
	public Integer getBusinessTransId() {
		return businessTransId;
	}
	public void setBusinessTransId(Integer businessTransId) {
		this.businessTransId = businessTransId;
	}
	public ArrayList<TransactionEntryVO> getTransactions() {
		return _transactions;
	}
	
	
	public void setTransactions(List<TransactionEntryVO> transactions) {
		this._transactions.addAll(transactions);
	}
	
	public void addTransactionEntry(TransactionEntryVO creditEntry) {
		// TODO Auto-generated method stub
		this._transactions.add(creditEntry);
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public Timestamp getModified_date() {
		return modified_date;
	}
	public void setModified_date(Timestamp modified_date) {
		this.modified_date = modified_date;
	}
	public String getModified_by() {
		return modified_by;
	}
	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}
	public Double getTransactionAmount() {
		return transactionAmount;
	}
	
	public Integer getGlProcessed() {
		return glProcessed;
	}
	public void setGlProcessed(Integer glProcessed) {
		this.glProcessed = glProcessed;
	}
	public Timestamp getGlProcessedDate() {
		return glProcessedDate;
	}
	public void setGlProcessedDate(Timestamp glProcessedDate) {
		this.glProcessedDate = glProcessedDate;
	}
	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public void setReconsidedDate(Timestamp reconsidedDate) {
		this.reconsidedDate = reconsidedDate;
	}
	public Integer getTransferReasonCode() {
		return transferReasonCode;
	}
	public void setTransferReasonCode(Integer transferReasonCode) {
		this.transferReasonCode = transferReasonCode;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
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