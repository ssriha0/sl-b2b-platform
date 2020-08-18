package com.servicelive.wallet.ledger.vo;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * Class TransactionEntryTypeVO.
 */
public class TransactionEntryTypeVO implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 297056649930630183L;

	/** descr. */
	private String descr;

	/** entryTypeId. */
	private Integer entryTypeId;

	/** type. */
	private String type;

	/**
	 * getDescr.
	 * 
	 * @return String
	 */
	public String getDescr() {

		return descr;
	}

	/**
	 * getEntryTypeId.
	 * 
	 * @return Integer
	 */
	public Integer getEntryTypeId() {

		return entryTypeId;
	}

	/**
	 * getType.
	 * 
	 * @return String
	 */
	public String getType() {

		return type;
	}

	/**
	 * setDescr.
	 * 
	 * @param descr 
	 * 
	 * @return void
	 */
	public void setDescr(String descr) {

		this.descr = descr;
	}

	/**
	 * setEntryTypeId.
	 * 
	 * @param entryTypeId 
	 * 
	 * @return void
	 */
	public void setEntryTypeId(Integer entryTypeId) {

		this.entryTypeId = entryTypeId;
	}

	/**
	 * setType.
	 * 
	 * @param type 
	 * 
	 * @return void
	 */
	public void setType(String type) {

		this.type = type;
	}

}// end class SecretQuectionVO