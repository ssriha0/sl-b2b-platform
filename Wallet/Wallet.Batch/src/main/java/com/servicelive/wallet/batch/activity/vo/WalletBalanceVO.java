package com.servicelive.wallet.batch.activity.vo;

import java.io.Serializable;
import java.sql.Date;

// TODO: Auto-generated Javadoc
/**
 * The Class WalletBalanceVO.
 */
public class WalletBalanceVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5849939924378366407L;

	/** The end date. */
	private Date endDate;

	/** The entity id. */
	private Integer entityId;

	/** The entity type. */
	private Integer entityType;

	// out
	/** The ledger entry id. */
	private Integer ledgerEntryId;

	// in
	/** The start date. */
	private Date startDate;

	/** The trans amount. */
	private Double transAmount;

	/** The validated amount. */
	private Double validatedAmount;

	/**
	 * Gets the end date.
	 * 
	 * @return the end date
	 */
	public Date getEndDate() {

		return endDate;
	}

	/**
	 * Gets the entity id.
	 * 
	 * @return the entity id
	 */
	public Integer getEntityId() {

		return entityId;
	}

	/**
	 * Gets the entity type.
	 * 
	 * @return the entity type
	 */
	public Integer getEntityType() {

		return entityType;
	}

	/**
	 * Gets the ledger entry id.
	 * 
	 * @return the ledger entry id
	 */
	public Integer getLedgerEntryId() {

		return ledgerEntryId;
	}

	/**
	 * Gets the start date.
	 * 
	 * @return the start date
	 */
	public Date getStartDate() {

		return startDate;
	}

	/**
	 * Gets the trans amount.
	 * 
	 * @return the trans amount
	 */
	public Double getTransAmount() {

		return transAmount;
	}

	/**
	 * Gets the validated amount.
	 * 
	 * @return the validated amount
	 */
	public Double getValidatedAmount() {

		return validatedAmount;
	}

	/**
	 * Sets the end date.
	 * 
	 * @param endDate the new end date
	 */
	public void setEndDate(Date endDate) {

		this.endDate = endDate;
	}

	/**
	 * Sets the entity id.
	 * 
	 * @param entityId the new entity id
	 */
	public void setEntityId(Integer entityId) {

		this.entityId = entityId;
	}

	/**
	 * Sets the entity type.
	 * 
	 * @param entityType the new entity type
	 */
	public void setEntityType(Integer entityType) {

		this.entityType = entityType;
	}

	/**
	 * Sets the ledger entry id.
	 * 
	 * @param ledgerEntryId the new ledger entry id
	 */
	public void setLedgerEntryId(Integer ledgerEntryId) {

		this.ledgerEntryId = ledgerEntryId;
	}

	/**
	 * Sets the start date.
	 * 
	 * @param startDate the new start date
	 */
	public void setStartDate(Date startDate) {

		this.startDate = startDate;
	}

	/**
	 * Sets the trans amount.
	 * 
	 * @param transAmount the new trans amount
	 */
	public void setTransAmount(Double transAmount) {

		this.transAmount = transAmount;
	}

	/**
	 * Sets the validated amount.
	 * 
	 * @param validatedAmount the new validated amount
	 */
	public void setValidatedAmount(Double validatedAmount) {

		this.validatedAmount = validatedAmount;
	}
}
