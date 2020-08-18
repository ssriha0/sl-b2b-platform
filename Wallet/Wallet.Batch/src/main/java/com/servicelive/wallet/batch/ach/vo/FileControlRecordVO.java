package com.servicelive.wallet.batch.ach.vo;

// TODO: Auto-generated Javadoc
/**
 * FileControlRecordVO.java - This class represents the file control record as specified by NACHA
 * 
 * @author Siva
 * @version 1.0
 */
public class FileControlRecordVO extends NachaGenericRecordVO {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5601409914139601130L;

	/** The total credit amount. */
	private String totalCreditAmount;

	/** The total debit amount. */
	private String totalDebitAmount;

	/**
	 * Gets the total credit amount.
	 * 
	 * @return the total credit amount
	 */
	public String getTotalCreditAmount() {

		return totalCreditAmount;
	}

	/**
	 * Gets the total debit amount.
	 * 
	 * @return the total debit amount
	 */
	public String getTotalDebitAmount() {

		return totalDebitAmount;
	}

	/**
	 * Sets the total credit amount.
	 * 
	 * @param totalCreditAmount the new total credit amount
	 */
	public void setTotalCreditAmount(String totalCreditAmount) {

		this.totalCreditAmount = totalCreditAmount;
	}

	/**
	 * Sets the total debit amount.
	 * 
	 * @param totalDebitAmount the new total debit amount
	 */
	public void setTotalDebitAmount(String totalDebitAmount) {

		this.totalDebitAmount = totalDebitAmount;
	}

}
