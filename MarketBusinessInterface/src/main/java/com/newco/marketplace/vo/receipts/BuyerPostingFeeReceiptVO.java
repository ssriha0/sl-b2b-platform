/**
 * 
 */
package com.newco.marketplace.vo.receipts;

import java.io.Serializable;

/**
 * @author Brandon
 *
 */
public class BuyerPostingFeeReceiptVO implements Serializable {

	private static final long serialVersionUID = 4538291193248335615L;
	private Integer ledgerEntityID;
	private Integer entityTypeID;
	private String theSOID;
	private Integer ledgerEntryRuleID;
	
	/**
	 * @return the entityTypeID
	 */
	public Integer getEntityTypeID() {
		return entityTypeID;
	}
	/**
	 * @param entityTypeID the entityTypeID to set
	 */
	public void setEntityTypeID(Integer entityTypeID) {
		this.entityTypeID = entityTypeID;
	}
	/**
	 * @return the theSOID
	 */
	public String getTheSOID() {
		return theSOID;
	}
	/**
	 * @param theSOID the theSOID to set
	 */
	public void setTheSOID(String theSOID) {
		this.theSOID = theSOID;
	}
	/**
	 * @return the ledgerEntityID
	 */
	public Integer getLedgerEntityID() {
		return ledgerEntityID;
	}
	/**
	 * @param ledgerEntityID the ledgerEntityID to set
	 */
	public void setLedgerEntityID(Integer ledgerEntityID) {
		this.ledgerEntityID = ledgerEntityID;
	}
	public Integer getLedgerEntryRuleID() {
		return ledgerEntryRuleID;
	}
	public void setLedgerEntryRuleID(Integer ledgerEntryRuleID) {
		this.ledgerEntryRuleID = ledgerEntryRuleID;
	}
}
