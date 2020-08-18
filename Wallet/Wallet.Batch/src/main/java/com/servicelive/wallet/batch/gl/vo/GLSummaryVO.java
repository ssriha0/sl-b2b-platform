package com.servicelive.wallet.batch.gl.vo;

import java.io.Serializable;
import java.util.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class GLSummaryVO.
 */
public class GLSummaryVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4600020878467651562L;

	/** The category. */
	private String category;

	/** The coverage. */
	private String coverage;

	/** The created date. */
	private Date createdDate;

	/** The credit ind. */
	private String creditInd;

	/** The descr. */
	private String descr;

	/** The entry type id. */
	private int entryTypeId;

	/** The gl division. */
	private String glDivision; // stored as varchar in db

	/** The gl process id. */
	private Integer glProcessId;

	/** The gl t account number. */
	private String glTAccountNumber;

	/** The ledger entity id. */
	private Long ledgerEntityId;

	/** The ledger entity type id. */
	private int ledgerEntityTypeId;

	/** The ledger entry id. */
	private Long ledgerEntryId;

	// fields from the OMS SHC data
	/** The location. */
	private String location;

	/** The modified date. */
	private Date modifiedDate;

	/** The sku. */
	private String sku;

	/** The transaction amount. */
	private double transactionAmount = 0.0;

	/** The transaction id. */
	private int transactionId;

	/** The transaction type id. */
	private int transactionTypeId;

	private String soId;
	
	/**
	 * Gets the category.
	 * 
	 * @return the category
	 */
	public String getCategory() {

		return category;
	}

	/**
	 * Gets the coverage.
	 * 
	 * @return the coverage
	 */
	public String getCoverage() {

		return coverage;
	}

	/**
	 * Gets the created date.
	 * 
	 * @return the created date
	 */
	public Date getCreatedDate() {

		return createdDate;
	}

	/**
	 * Gets the credit ind.
	 * 
	 * @return the credit ind
	 */
	public String getCreditInd() {

		return creditInd;
	}

	/**
	 * Gets the descr.
	 * 
	 * @return the descr
	 */
	public String getDescr() {

		return descr;
	}

	/**
	 * Gets the entry type id.
	 * 
	 * @return the entry type id
	 */
	public int getEntryTypeId() {

		return entryTypeId;
	}

	/**
	 * Gets the gl division.
	 * 
	 * @return the gl division
	 */
	public String getGlDivision() {

		return glDivision;
	}

	/**
	 * Gets the gl process id.
	 * 
	 * @return the gl process id
	 */
	public Integer getGlProcessId() {

		return glProcessId;
	}

	/**
	 * Gets the gl t account number.
	 * 
	 * @return the gl t account number
	 */
	public String getGlTAccountNumber() {

		return glTAccountNumber;
	}

	/**
	 * Gets the ledger entity id.
	 * 
	 * @return the ledger entity id
	 */
	public Long getLedgerEntityId() {

		return ledgerEntityId;
	}

	/**
	 * Gets the ledger entity type id.
	 * 
	 * @return the ledger entity type id
	 */
	public int getLedgerEntityTypeId() {

		return ledgerEntityTypeId;
	}

	/**
	 * Gets the ledger entry id.
	 * 
	 * @return the ledger entry id
	 */
	public Long getLedgerEntryId() {

		return ledgerEntryId;
	}

	/**
	 * Gets the location.
	 * 
	 * @return the location
	 */
	public String getLocation() {

		return location;
	}

	/**
	 * Gets the modified date.
	 * 
	 * @return the modified date
	 */
	public Date getModifiedDate() {

		return modifiedDate;
	}

	/**
	 * Gets the sku.
	 * 
	 * @return the sku
	 */
	public String getSku() {

		return sku;
	}

	/**
	 * Gets the transaction amount.
	 * 
	 * @return the transaction amount
	 */
	public double getTransactionAmount() {

		return transactionAmount;
	}

	/**
	 * Gets the transaction id.
	 * 
	 * @return the transaction id
	 */
	public int getTransactionId() {

		return transactionId;
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
	 * Sets the category.
	 * 
	 * @param category the new category
	 */
	public void setCategory(String category) {

		this.category = category;
	}

	/**
	 * Sets the coverage.
	 * 
	 * @param coverage the new coverage
	 */
	public void setCoverage(String coverage) {

		this.coverage = coverage;
	}

	/**
	 * Sets the created date.
	 * 
	 * @param createdDate the new created date
	 */
	public void setCreatedDate(Date createdDate) {

		this.createdDate = createdDate;
	}

	/**
	 * Sets the credit ind.
	 * 
	 * @param creditInd the new credit ind
	 */
	public void setCreditInd(String creditInd) {

		this.creditInd = creditInd;
	}

	/**
	 * Sets the descr.
	 * 
	 * @param descr the new descr
	 */
	public void setDescr(String descr) {

		this.descr = descr;
	}

	/**
	 * Sets the entry type id.
	 * 
	 * @param entryTypeId the new entry type id
	 */
	public void setEntryTypeId(int entryTypeId) {

		this.entryTypeId = entryTypeId;
	}

	/**
	 * Sets the gl division.
	 * 
	 * @param glDivision the new gl division
	 */
	public void setGlDivision(String glDivision) {

		this.glDivision = glDivision;
	}

	/**
	 * Sets the gl process id.
	 * 
	 * @param gl_process_id the new gl process id
	 */
	public void setGlProcessId(Integer gl_process_id) {

		this.glProcessId = gl_process_id;
	}

	/**
	 * Sets the gl t account number.
	 * 
	 * @param glTAccountNumber the new gl t account number
	 */
	public void setGlTAccountNumber(String glTAccountNumber) {

		this.glTAccountNumber = glTAccountNumber;
	}

	/**
	 * Sets the ledger entity id.
	 * 
	 * @param ledgerEntityId the new ledger entity id
	 */
	public void setLedgerEntityId(Long ledgerEntityId) {

		this.ledgerEntityId = ledgerEntityId;
	}

	/**
	 * Sets the ledger entity type id.
	 * 
	 * @param ledgerEntityTypeId the new ledger entity type id
	 */
	public void setLedgerEntityTypeId(int ledgerEntityTypeId) {

		this.ledgerEntityTypeId = ledgerEntityTypeId;
	}

	/**
	 * Sets the ledger entry id.
	 * 
	 * @param ledgerEntryId the new ledger entry id
	 */
	public void setLedgerEntryId(Long ledgerEntryId) {

		this.ledgerEntryId = ledgerEntryId;
	}

	/**
	 * Sets the location.
	 * 
	 * @param location the new location
	 */
	public void setLocation(String location) {

		this.location = location;
	}

	/**
	 * Sets the modified date.
	 * 
	 * @param modifiedDate the new modified date
	 */
	public void setModifiedDate(Date modifiedDate) {

		this.modifiedDate = modifiedDate;
	}

	/**
	 * Sets the sku.
	 * 
	 * @param sku the new sku
	 */
	public void setSku(String sku) {

		this.sku = sku;
	}

	/**
	 * Sets the transaction amount.
	 * 
	 * @param transactionAmount the new transaction amount
	 */
	public void setTransactionAmount(double transactionAmount) {

		this.transactionAmount = transactionAmount;
	}

	/**
	 * Sets the transaction id.
	 * 
	 * @param transactionId the new transaction id
	 */
	public void setTransactionId(int transactionId) {

		this.transactionId = transactionId;
	}

	/**
	 * Sets the transaction type id.
	 * 
	 * @param transactionTypeId the new transaction type id
	 */
	public void setTransactionTypeId(int transactionTypeId) {

		this.transactionTypeId = transactionTypeId;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

}
