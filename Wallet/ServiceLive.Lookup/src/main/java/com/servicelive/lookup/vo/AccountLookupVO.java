package com.servicelive.lookup.vo;

import java.io.Serializable;


// TODO: Auto-generated Javadoc
/**
 * Class AccountLookupVO.
 */
public class AccountLookupVO implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 6535472184409314314L;
	
	/** accountId. */
	private Long accountId;

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
	 * getAccountId.
	 * 
	 * @return Long
	 */
	public Long getAccountId() {

		return accountId;
	}
}
