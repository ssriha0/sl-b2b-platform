package com.servicelive.wallet.ledger.vo;

import java.io.Serializable;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * Class LedgerBusinessTransactionVO.
 */
public class LedgerBusinessTransactionVO implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 3011009624452263533L;

	/** autoAchInd. */
	private Integer autoAchInd;

	/** businessTransDesc. */
	private String businessTransDesc;

	/** businessTransId. */
	private Long businessTransId;

	/** businessTransType. */
	private String businessTransType;;

	/** entityId. */
	private String entityId;

	/** fundingTypeId. */
	private Long fundingTypeId;
	
	/** transactionEntryId. */
	private Long transactionEntryId;
	
	/** entityTypeId. */
	private Long entityTypeId;

	public Long getEntityTypeId() {
		return entityTypeId;
	}

	public void setEntityTypeId(Long entityTypeId) {
		this.entityTypeId = entityTypeId;
	}

	/** transactions. */
	private ArrayList<TransactionVO> transactions = new ArrayList<TransactionVO>();

	public void setTransactions(ArrayList<TransactionVO> transactions) {
		this.transactions = transactions;
	}

	/** Indicator for successful ledger transaction. */
	private boolean ledgerEntries = true;

	/**
	 * addTransaction.
	 * 
	 * @param theTransaction 
	 * 
	 * @return void
	 */
	public void addTransaction(final TransactionVO theTransaction) {

		this.transactions.add(theTransaction);
	}

	/**
	 * addTransactions.
	 * 
	 * @param transactions 
	 * 
	 * @return void
	 */
	public void addTransactions(final ArrayList<TransactionVO> transactions) {

		this.transactions.addAll(transactions);
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
	 * getBusinessTransDesc.
	 * 
	 * @return String
	 */
	public String getBusinessTransDesc() {

		return businessTransDesc;
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
	 * getBusinessTransType.
	 * 
	 * @return String
	 */
	public String getBusinessTransType() {

		return businessTransType;
	}

	/**
	 * getEntityId.
	 * 
	 * @return String
	 */
	public String getEntityId() {

		return entityId;
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
	 * getTransactions.
	 * 
	 * @return ArrayList<TransactionVO>
	 */
	public ArrayList<TransactionVO> getTransactions() {

		return transactions;
	}

	/**
	 * isContainsTransaction.
	 * 
	 * @return boolean
	 */
	public boolean isContainsTransaction() {

		return !this.transactions.isEmpty();
	}

	/**
	 * isHasManyTransactions.
	 * 
	 * @return boolean
	 */
	public boolean isHasManyTransactions() {

		return (this.transactions.size() > 1);
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
	 * setBusinessTransDesc.
	 * 
	 * @param businessTransDesc 
	 * 
	 * @return void
	 */
	public void setBusinessTransDesc(String businessTransDesc) {

		this.businessTransDesc = businessTransDesc;
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
	 * setBusinessTransType.
	 * 
	 * @param businessTransType 
	 * 
	 * @return void
	 */
	public void setBusinessTransType(String businessTransType) {

		this.businessTransType = businessTransType;
	}

	/**
	 * setEntityId.
	 * 
	 * @param entityId 
	 * 
	 * @return void
	 */
	public void setEntityId(String entityId) {

		this.entityId = entityId;
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
	 * getTransactionEntryId.
	 * 
	 * @return Long
	 */
	public Long getTransactionEntryId() {
		return this.transactionEntryId;
	}

	/**
	 * setTransactionEntryId.
	 * 
	 * @param transactionEntryId 
	 * 
	 * @return void
	 */
	public void setTransactionEntryId(Long transactionEntryId) {
		this.transactionEntryId = transactionEntryId;
	}

	public void setLedgerEntries(boolean ledgerEntries) {
		this.ledgerEntries = ledgerEntries;
	}

	public boolean isLedgerEntries() {
		return ledgerEntries;
	}

}
