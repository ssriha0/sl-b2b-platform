package com.servicelive.wallet.ledger.vo;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * Class TransactionVO.
 */
public class TransactionVO implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 5169985142281155774L;

	/** affectsBalanceInd. */
	private Integer affectsBalanceInd;

	/** authorizationNo. */
	private String authorizationNo;

	/** authorizedDate. */
	private Date authorizedDate;

	/** authorizedInd. */
	private boolean authorizedInd;

	/** autoAchInd. */
	private Integer autoAchInd;

	/** businessTransId. */
	private Long businessTransId;

	/** createdDate. */
	private Timestamp createdDate;

	/** entryRemark. */
	private String entryRemark;

	/** glProcessed. */
	private Integer glProcessed;

	/** glProcessedDate. */
	private Timestamp glProcessedDate;

	/** ledgerEntryDate. */
	private Timestamp ledgerEntryDate;

	/** ledgerEntryId. */
	private Long ledgerEntryId;

	/** ledgerEntryRuleId. */
	private Long ledgerEntryRuleId;

	/** modifiedBy. */
	private String modifiedBy;

	/** modifiedDate. */
	private Timestamp modifiedDate;

	/** reconsidedDate. */
	private Timestamp reconsidedDate;

	/** reconsiledInd. */
	private boolean reconsiledInd;

	/** referenceNo. */
	private String referenceNo;

	/** soId. */
	private String soId;

	/** transactionAmount. */
	private Double transactionAmount;

	/** transactions. */
	private ArrayList<TransactionEntryVO> transactions = new ArrayList<TransactionEntryVO>();

	/** transferReasonCode. */
	private Integer transferReasonCode;

	/** user. */
	private String user;
	
	/** This is retrieved only for including in the ACH Settlement email sent out */
	private String emailTransactionId;
	
	private String nonce;

	public String getEmailTransactionId() {
		return emailTransactionId;
	}

	public void setEmailTransactionId(String emailTransactionId) {
		this.emailTransactionId = emailTransactionId;
	}

	/**
	 * addTransactionEntry.
	 * 
	 * @param creditEntry 
	 * 
	 * @return void
	 */
	public void addTransactionEntry(TransactionEntryVO creditEntry) {

		// TODO Auto-generated method stub
		this.transactions.add(creditEntry);
	}

	/**
	 * getAffectsBalanceInd.
	 * 
	 * @return Integer
	 */
	public Integer getAffectsBalanceInd() {

		return affectsBalanceInd;
	}

	/**
	 * getAuthorizationNo.
	 * 
	 * @return String
	 */
	public String getAuthorizationNo() {

		return authorizationNo;
	}

	/**
	 * getAuthorizedDate.
	 * 
	 * @return Date
	 */
	public Date getAuthorizedDate() {

		return authorizedDate;
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
	 * getBusinessTransId.
	 * 
	 * @return Long
	 */
	public Long getBusinessTransId() {

		return businessTransId;
	}

	/**
	 * getCreatedDate.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getCreatedDate() {

		return createdDate;
	}

	/**
	 * getEntryRemark.
	 * 
	 * @return String
	 */
	public String getEntryRemark() {

		return entryRemark;
	}

	/**
	 * getGlProcessed.
	 * 
	 * @return Integer
	 */
	public Integer getGlProcessed() {

		return glProcessed;
	}

	/**
	 * getGlProcessedDate.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getGlProcessedDate() {

		return glProcessedDate;
	}

	/**
	 * getLedgerEntryDate.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getLedgerEntryDate() {

		return ledgerEntryDate;
	}

	/**
	 * getLedgerEntryId.
	 * 
	 * @return Long
	 */
	public Long getLedgerEntryId() {

		return ledgerEntryId;
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
	 * getModifiedBy.
	 * 
	 * @return String
	 */
	public String getModifiedBy() {

		return modifiedBy;
	}

	/**
	 * getModifiedDate.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getModifiedDate() {

		return modifiedDate;
	}

	/**
	 * getReconsidedDate.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getReconsidedDate() {

		return reconsidedDate;
	}

	/**
	 * getReferenceNo.
	 * 
	 * @return String
	 */
	public String getReferenceNo() {

		return referenceNo;
	}

	/**
	 * getSoId.
	 * 
	 * @return String
	 */
	public String getSoId() {

		return soId;
	}

	/**
	 * getTransactionAmount.
	 * 
	 * @return Double
	 */
	public Double getTransactionAmount() {

		return transactionAmount;
	}

	/**
	 * getTransactions.
	 * 
	 * @return ArrayList<TransactionEntryVO>
	 */
	public ArrayList<TransactionEntryVO> getTransactions() {

		return transactions;
	}

	/**
	 * getTransferReasonCode.
	 * 
	 * @return Integer
	 */
	public Integer getTransferReasonCode() {

		return transferReasonCode;
	}

	/**
	 * getUser.
	 * 
	 * @return String
	 */
	public String getUser() {

		return user;
	}

	/**
	 * isAuthorizedInd.
	 * 
	 * @return boolean
	 */
	public boolean isAuthorizedInd() {

		return authorizedInd;
	}

	/**
	 * isReconsiledInd.
	 * 
	 * @return boolean
	 */
	public boolean isReconsiledInd() {

		return reconsiledInd;
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
	 * setAuthorizationNo.
	 * 
	 * @param authorizationNo 
	 * 
	 * @return void
	 */
	public void setAuthorizationNo(String authorizationNo) {

		this.authorizationNo = authorizationNo;
	}

	/**
	 * setAuthorizedDate.
	 * 
	 * @param authorizedDate 
	 * 
	 * @return void
	 */
	public void setAuthorizedDate(Date authorizedDate) {

		this.authorizedDate = authorizedDate;
	}

	/**
	 * setAuthorizedInd.
	 * 
	 * @param authorizedInd 
	 * 
	 * @return void
	 */
	public void setAuthorizedInd(boolean authorizedInd) {

		this.authorizedInd = authorizedInd;
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
	 * setBusinessTransId.
	 * 
	 * @param businessTransId 
	 * 
	 * @return void
	 */
	public void setBusinessTransId(Long businessTransId) {

		this.businessTransId = businessTransId;
	}

	/**
	 * setCreatedDate.
	 * 
	 * @param createdDate 
	 * 
	 * @return void
	 */
	public void setCreatedDate(Timestamp createdDate) {

		this.createdDate = createdDate;
	}

	/**
	 * setEntryRemark.
	 * 
	 * @param entryRemark 
	 * 
	 * @return void
	 */
	public void setEntryRemark(String entryRemark) {

		this.entryRemark = entryRemark;
	}

	/**
	 * setGlProcessed.
	 * 
	 * @param glProcessed 
	 * 
	 * @return void
	 */
	public void setGlProcessed(Integer glProcessed) {

		this.glProcessed = glProcessed;
	}

	/**
	 * setGlProcessedDate.
	 * 
	 * @param glProcessedDate 
	 * 
	 * @return void
	 */
	public void setGlProcessedDate(Timestamp glProcessedDate) {

		this.glProcessedDate = glProcessedDate;
	}

	/**
	 * setLedgerEntryDate.
	 * 
	 * @param ledgerEntryDate 
	 * 
	 * @return void
	 */
	public void setLedgerEntryDate(Timestamp ledgerEntryDate) {

		this.ledgerEntryDate = ledgerEntryDate;
	}

	/**
	 * setLedgerEntryId.
	 * 
	 * @param ledgerEntryId 
	 * 
	 * @return void
	 */
	public void setLedgerEntryId(Long ledgerEntryId) {

		this.ledgerEntryId = ledgerEntryId;
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
	 * setModifiedDate.
	 * 
	 * @param modifiedDate 
	 * 
	 * @return void
	 */
	public void setModifiedDate(Timestamp modifiedDate) {

		this.modifiedDate = modifiedDate;
	}

	/**
	 * setMoydifiedBy.
	 * 
	 * @param modifiedBy 
	 * 
	 * @return void
	 */
	public void setMoydifiedBy(String modifiedBy) {

		this.modifiedBy = modifiedBy;
	}

	/**
	 * setReconsidedDate.
	 * 
	 * @param reconsidedDate 
	 * 
	 * @return void
	 */
	public void setReconsidedDate(Timestamp reconsidedDate) {

		this.reconsidedDate = reconsidedDate;
	}

	/**
	 * setReconsiledInd.
	 * 
	 * @param reconsiledInd 
	 * 
	 * @return void
	 */
	public void setReconsiledInd(boolean reconsiledInd) {

		this.reconsiledInd = reconsiledInd;
	}

	/**
	 * setReferenceNo.
	 * 
	 * @param referenceNo 
	 * 
	 * @return void
	 */
	public void setReferenceNo(String referenceNo) {

		this.referenceNo = referenceNo;
	}

	/**
	 * setSoId.
	 * 
	 * @param soId 
	 * 
	 * @return void
	 */
	public void setSoId(String soId) {

		this.soId = soId;
	}

	/**
	 * setTransactionAmount.
	 * 
	 * @param transactionAmount 
	 * 
	 * @return void
	 */
	public void setTransactionAmount(Double transactionAmount) {

		this.transactionAmount = transactionAmount;
	}

	/**
	 * setTransactions.
	 * 
	 * @param transactions 
	 * 
	 * @return void
	 */
	public void setTransactions(List<TransactionEntryVO> transactions) {

		this.transactions.addAll(transactions);
	}
	
	public void replaceTransactions(List<TransactionEntryVO> transactions) {

		this.transactions = (ArrayList<TransactionEntryVO>) transactions;
	}

	/**
	 * setTransferReasonCode.
	 * 
	 * @param transferReasonCode 
	 * 
	 * @return void
	 */
	public void setTransferReasonCode(Integer transferReasonCode) {

		this.transferReasonCode = transferReasonCode;
	}

	/**
	 * setUser.
	 * 
	 * @param user 
	 * 
	 * @return void
	 */
	public void setUser(String user) {

		this.user = user;
	}
	
	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

}// end class SecretQuectionVO