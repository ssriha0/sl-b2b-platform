/**
 * 
 */
package com.servicelive.lookup.vo;

import java.io.Serializable;
import java.sql.Timestamp;

// TODO: Auto-generated Javadoc
/**
 * Class ValueLinkAccountsVO.
 */
public class ValueLinkAccountsVO implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 250244074584458431L;

	/** accountCode. */
	private String accountCode;

	/** accountDesc. */
	private String accountDesc;

	/** accountId. */
	private Long accountId; // primary key of lu_fullfillment_vlaccounts table

	/** createdDate. */
	private Timestamp createdDate;

	/** entityTypeId. */
	private Long entityTypeId;

	/** ledgerEntityId. */
	private Long ledgerEntityId;

	/** modifiedBy. */
	private String modifiedBy;

	/** modifiedDate. */
	private Timestamp modifiedDate;

	/** v1AccountNo. */
	private Long v1AccountNo;

	/** v2AccountNo. */
	private Long v2AccountNo;

	/**
	 * getAccountCode.
	 * 
	 * @return String
	 */
	public String getAccountCode() {

		return accountCode;
	}

	/**
	 * getAccountDesc.
	 * 
	 * @return String
	 */
	public String getAccountDesc() {

		return accountDesc;
	}

	/**
	 * getAccountId.
	 * 
	 * @return Long
	 */
	public Long getAccountId() {

		return accountId;
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
	 * getEntityTypeId.
	 * 
	 * @return Long
	 */
	public Long getEntityTypeId() {

		return entityTypeId;
	}

	/**
	 * getLedgerEntityId.
	 * 
	 * @return Long
	 */
	public Long getLedgerEntityId() {

		return ledgerEntityId;
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
	 * getV1AccountNo.
	 * 
	 * @return Long
	 */
	public Long getV1AccountNo() {

		return v1AccountNo;
	}

	/**
	 * getV2AccountNo.
	 * 
	 * @return Long
	 */
	public Long getV2AccountNo() {

		return v2AccountNo;
	}

	/**
	 * setAccountCode.
	 * 
	 * @param accountCode 
	 * 
	 * @return void
	 */
	public void setAccountCode(String accountCode) {

		this.accountCode = accountCode;
	}

	/**
	 * setAccountDesc.
	 * 
	 * @param accountDesc 
	 * 
	 * @return void
	 */
	public void setAccountDesc(String accountDesc) {

		this.accountDesc = accountDesc;
	}

	/**
	 * setAccountId.
	 * 
	 * @param accountId 
	 * 
	 * @return void
	 */
	public void setAccountId(Long accountId) {

		this.accountId = accountId;
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
	 * setEntityTypeId.
	 * 
	 * @param entityTypeId 
	 * 
	 * @return void
	 */
	public void setEntityTypeId(Long entityTypeId) {

		this.entityTypeId = entityTypeId;
	}

	/**
	 * setLedgerEntityId.
	 * 
	 * @param ledgerEntityId 
	 * 
	 * @return void
	 */
	public void setLedgerEntityId(Long ledgerEntityId) {

		this.ledgerEntityId = ledgerEntityId;
	}

	/**
	 * setModifiedBy.
	 * 
	 * @param modifiedBy 
	 * 
	 * @return void
	 */
	public void setModifiedBy(String modifiedBy) {

		this.modifiedBy = modifiedBy;
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
	 * setV1AccountNo.
	 * 
	 * @param accountNo 
	 * 
	 * @return void
	 */
	public void setV1AccountNo(Long accountNo) {

		v1AccountNo = accountNo;
	}

	/**
	 * setV2AccountNo.
	 * 
	 * @param accountNo 
	 * 
	 * @return void
	 */
	public void setV2AccountNo(Long accountNo) {

		v2AccountNo = accountNo;
	}

}
