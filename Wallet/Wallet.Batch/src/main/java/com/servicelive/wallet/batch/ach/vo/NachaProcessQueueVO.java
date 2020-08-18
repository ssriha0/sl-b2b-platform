package com.servicelive.wallet.batch.ach.vo;

import java.io.Serializable;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class NachaProcessQueueVO.
 */
public class NachaProcessQueueVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6608011980160871443L;

	/** The account id. */
	private int accountId;

	/** The account no. */
	private String accountNo;

	/** The account status id. */
	private int accountStatusId;

	/** The account type id. */
	private int accountTypeId;

	/** The acc type descr. */
	private String accTypeDescr;

	/** The ach process id. */
	private int achProcessId;

	/** The bank name. */
	private String bankName;

	/** The country id. */
	private int countryId;

	/** The discretionary data. */
	private long discretionaryData;

	/** The entity id. */
	private int entityId;

	/** The entity type descr. */
	private String entityTypeDescr;

	/** The entity type id. */
	private int entityTypeId;

	/** The entry date. */
	private Date entryDate;

	/** The ledger entry id. */
	private long ledgerEntryId;

	/** The ledger transaction entry id. */
	private long ledgerTransactionEntryId;

	/** The ledger trans amount. */
	private double ledgerTransAmount;

	/** The process status id. */
	private int processStatusId;

	/** The reject reason id. */
	private int rejectReasonId;

	/** The routing no. */
	private String routingNo;

	/** The transaction type id. */
	private int transactionTypeId;

	/** The trans amount. */
	private double transAmount;

	/** The trans type descr. */
	private String transTypeDescr;

	/** The user name. */
	private String userName;
	
	private int achTranscodeAccId;

	public int getAchTranscodeAccId() {
		return achTranscodeAccId;
	}

	public void setAchTranscodeAccId(int achTranscodeAccId) {
		this.achTranscodeAccId = achTranscodeAccId;
	}

	/**
	 * Gets the account id.
	 * 
	 * @return the account id
	 */
	public int getAccountId() {

		return accountId;
	}

	/**
	 * Gets the account no.
	 * 
	 * @return the account no
	 */
	public String getAccountNo() {

		return accountNo;
	}

	/**
	 * Gets the account status id.
	 * 
	 * @return the account status id
	 */
	public int getAccountStatusId() {

		return accountStatusId;
	}

	/**
	 * Gets the account type id.
	 * 
	 * @return the account type id
	 */
	public int getAccountTypeId() {

		return accountTypeId;
	}

	/**
	 * Gets the acc type descr.
	 * 
	 * @return the acc type descr
	 */
	public String getAccTypeDescr() {

		return accTypeDescr;
	}

	/**
	 * Gets the ach process id.
	 * 
	 * @return the ach process id
	 */
	public int getAchProcessId() {

		return achProcessId;
	}

	/**
	 * Gets the bank name.
	 * 
	 * @return the bank name
	 */
	public String getBankName() {

		return bankName;
	}

	/**
	 * Gets the country id.
	 * 
	 * @return the country id
	 */
	public int getCountryId() {

		return countryId;
	}

	/**
	 * Gets the delimited string.
	 * 
	 * @param nachaProcessQueueVO 
	 * @param delimiter 
	 * 
	 * @return the delimited string
	 */
	public String getDelimitedString(NachaProcessQueueVO nachaProcessQueueVO, String delimiter) {

		StringBuffer sb = new StringBuffer("");
		sb.append(nachaProcessQueueVO.getAccountId());
		sb.append(delimiter);
		sb.append(nachaProcessQueueVO.getAccountId());
		sb.append(delimiter);
		sb.append(nachaProcessQueueVO.getAccountNo());
		sb.append(delimiter);
		sb.append(nachaProcessQueueVO.getAccountStatusId());
		sb.append(nachaProcessQueueVO.getAccountTypeId());
		sb.append(delimiter);
		sb.append(nachaProcessQueueVO.getAccTypeDescr());
		sb.append(delimiter);
		sb.append(nachaProcessQueueVO.getAchProcessId());
		sb.append(delimiter);
		sb.append(nachaProcessQueueVO.getBankName());
		sb.append(delimiter);
		sb.append(nachaProcessQueueVO.getCountryId());
		sb.append(delimiter);
		sb.append(nachaProcessQueueVO.getEntityId());
		sb.append(delimiter);
		sb.append(nachaProcessQueueVO.getEntityTypeDescr());
		sb.append(nachaProcessQueueVO.getEntityTypeId());
		sb.append(delimiter);
		sb.append(nachaProcessQueueVO.getEntryDate());
		sb.append(delimiter);
		sb.append(nachaProcessQueueVO.getLedgerEntryId());
		sb.append(delimiter);
		sb.append(nachaProcessQueueVO.getProcessStatusId());
		sb.append(delimiter);
		sb.append(nachaProcessQueueVO.getRoutingNo());
		sb.append(delimiter);
		sb.append(nachaProcessQueueVO.getTransactionTypeId());
		sb.append(delimiter);
		sb.append(nachaProcessQueueVO.getTransAmount());
		sb.append(delimiter);
		sb.append(nachaProcessQueueVO.getTransTypeDescr());
		return (sb.toString());
	} // getDelimitedString

	/**
	 * Gets the discretionary data.
	 * 
	 * @return the discretionary data
	 */
	public long getDiscretionaryData() {

		return discretionaryData;
	}

	/**
	 * Gets the entity id.
	 * 
	 * @return the entity id
	 */
	public int getEntityId() {

		return entityId;
	}

	/**
	 * Gets the entity type descr.
	 * 
	 * @return the entity type descr
	 */
	public String getEntityTypeDescr() {

		return entityTypeDescr;
	}

	/**
	 * Gets the entity type id.
	 * 
	 * @return the entity type id
	 */
	public int getEntityTypeId() {

		return entityTypeId;
	}

	/**
	 * Gets the entry date.
	 * 
	 * @return the entry date
	 */
	public Date getEntryDate() {

		return entryDate;
	}

	/**
	 * Gets the ledger entry id.
	 * 
	 * @return the ledger entry id
	 */
	public long getLedgerEntryId() {

		return ledgerEntryId;
	}

	/**
	 * Gets the ledger transaction entry id.
	 * 
	 * @return the ledger transaction entry id
	 */
	public long getLedgerTransactionEntryId() {

		return ledgerTransactionEntryId;
	}

	/**
	 * Gets the ledger trans amount.
	 * 
	 * @return the ledger trans amount
	 */
	public double getLedgerTransAmount() {

		return ledgerTransAmount;
	}

	/**
	 * Gets the process status id.
	 * 
	 * @return the process status id
	 */
	public int getProcessStatusId() {

		return processStatusId;
	}

	/**
	 * Gets the reject reason id.
	 * 
	 * @return the reject reason id
	 */
	public int getRejectReasonId() {

		return rejectReasonId;
	}

	/**
	 * Gets the routing no.
	 * 
	 * @return the routing no
	 */
	public String getRoutingNo() {

		return routingNo;
	}

	/**
	 * Gets the transaction type id.
	 * 
	 * @return the transaction type id
	 */
	public int getTransactionTypeId() {

		return transactionTypeId;
	}

	/**
	 * Gets the trans amount.
	 * 
	 * @return the trans amount
	 */
	public double getTransAmount() {

		return transAmount;
	}

	// private Date processStatusChgDate;
	/**
	 * Gets the trans type descr.
	 * 
	 * @return the trans type descr
	 */
	public String getTransTypeDescr() {

		return transTypeDescr;
	}

	/**
	 * Gets the user name.
	 * 
	 * @return the user name
	 */
	public String getUserName() {

		return userName;
	}

	/**
	 * Sets the account id.
	 * 
	 * @param accountId the new account id
	 */
	public void setAccountId(int accountId) {

		this.accountId = accountId;
	}

	/**
	 * Sets the account no.
	 * 
	 * @param accountNo the new account no
	 */
	public void setAccountNo(String accountNo) {

		this.accountNo = accountNo;
	}

	/**
	 * Sets the account status id.
	 * 
	 * @param accountStatusId the new account status id
	 */
	public void setAccountStatusId(int accountStatusId) {

		this.accountStatusId = accountStatusId;
	}

	/**
	 * Sets the account type id.
	 * 
	 * @param accountTypeId the new account type id
	 */
	public void setAccountTypeId(int accountTypeId) {

		this.accountTypeId = accountTypeId;
	}

	/**
	 * Sets the acc type descr.
	 * 
	 * @param accTypeDescr the new acc type descr
	 */
	public void setAccTypeDescr(String accTypeDescr) {

		this.accTypeDescr = accTypeDescr;
	}

	/**
	 * Sets the ach process id.
	 * 
	 * @param achProcessId the new ach process id
	 */
	public void setAchProcessId(int achProcessId) {

		this.achProcessId = achProcessId;
	}

	/**
	 * Sets the bank name.
	 * 
	 * @param bankName the new bank name
	 */
	public void setBankName(String bankName) {

		this.bankName = bankName;
	}

	/**
	 * Sets the country id.
	 * 
	 * @param countryId the new country id
	 */
	public void setCountryId(int countryId) {

		this.countryId = countryId;
	}

	/**
	 * Sets the discretionary data.
	 * 
	 * @param discretionaryData the new discretionary data
	 */
	public void setDiscretionaryData(long discretionaryData) {

		this.discretionaryData = discretionaryData;
	}

	/**
	 * Sets the entity id.
	 * 
	 * @param entityId the new entity id
	 */
	public void setEntityId(int entityId) {

		this.entityId = entityId;
	}

	/**
	 * Sets the entity type descr.
	 * 
	 * @param entityTypeDescr the new entity type descr
	 */
	public void setEntityTypeDescr(String entityTypeDescr) {

		this.entityTypeDescr = entityTypeDescr;
	}

	/**
	 * Sets the entity type id.
	 * 
	 * @param entityTypeId the new entity type id
	 */
	public void setEntityTypeId(int entityTypeId) {

		this.entityTypeId = entityTypeId;
	}

	/**
	 * Sets the entry date.
	 * 
	 * @param entryDate the new entry date
	 */
	public void setEntryDate(Date entryDate) {

		this.entryDate = entryDate;
	}

	/**
	 * Sets the ledger entry id.
	 * 
	 * @param ledgerEntryId the new ledger entry id
	 */
	public void setLedgerEntryId(long ledgerEntryId) {

		this.ledgerEntryId = ledgerEntryId;
	}

	/**
	 * Sets the ledger transaction entry id.
	 * 
	 * @param ledgerTransactionEntryId the new ledger transaction entry id
	 */
	public void setLedgerTransactionEntryId(long ledgerTransactionEntryId) {

		this.ledgerTransactionEntryId = ledgerTransactionEntryId;
	}

	/**
	 * Sets the ledger trans amount.
	 * 
	 * @param ledgerTransAmount the new ledger trans amount
	 */
	public void setLedgerTransAmount(double ledgerTransAmount) {

		this.ledgerTransAmount = ledgerTransAmount;
	}

	/**
	 * Sets the process status id.
	 * 
	 * @param processStatusId the new process status id
	 */
	public void setProcessStatusId(int processStatusId) {

		this.processStatusId = processStatusId;
	}

	/**
	 * Sets the reject reason id.
	 * 
	 * @param rejectReasonId the new reject reason id
	 */
	public void setRejectReasonId(int rejectReasonId) {

		this.rejectReasonId = rejectReasonId;
	}

	/**
	 * Sets the routing no.
	 * 
	 * @param routingNo the new routing no
	 */
	public void setRoutingNo(String routingNo) {

		this.routingNo = routingNo;
	}

	/**
	 * Sets the transaction type id.
	 * 
	 * @param transactionTypeId the new transaction type id
	 */
	public void setTransactionTypeId(int transactionTypeId) {

		this.transactionTypeId = transactionTypeId;
	}

	/**
	 * Sets the trans amount.
	 * 
	 * @param transAmount the new trans amount
	 */
	public void setTransAmount(double transAmount) {

		this.transAmount = transAmount;
	}

	/**
	 * Sets the trans type descr.
	 * 
	 * @param transTypeDescr the new trans type descr
	 */
	public void setTransTypeDescr(String transTypeDescr) {

		this.transTypeDescr = transTypeDescr;
	}

	/**
	 * Sets the user name.
	 * 
	 * @param userName the new user name
	 */
	public void setUserName(String userName) {

		this.userName = userName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		return ("<NachaProcessQueueVo>" + getDelimitedString(this, " | ") + "</NachaProcessQueueVo>");
	}

}
