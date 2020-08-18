/**
 * 
 */
package com.newco.marketplace.vo.provider;

import java.sql.Date;

/**
 * @author kapil sharma
 *
 */
public class FinanceProfile extends BaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4246006975709174680L;
	private int haveAuditedBooks;
	private int haveBeenBankrupt;
	private Date bankruptcyDate;
	private Integer annualSalesVolume;
	private int haveAnnualBudget;
	private int vendorId = -1;

	
	
	/**
	 * @return the annualSalesVolume
	 */
	public Integer getAnnualSalesVolume() {
		return annualSalesVolume;
	}
	/**
	 * @param annualSalesVolume the annualSalesVolume to set
	 */
	public void setAnnualSalesVolume(Integer annualSalesVolume) {
		this.annualSalesVolume = annualSalesVolume;
	}
		/**
	 * @return the bankruptcyDate
	 */
		/**
	 * @return the haveAnnualBudget
	 */
	public int getHaveAnnualBudget() {
		return haveAnnualBudget;
	}
	/**
	 * @param haveAnnualBudget the haveAnnualBudget to set
	 */
	public void setHaveAnnualBudget(int haveAnnualBudget) {
		this.haveAnnualBudget = haveAnnualBudget;
	}
	/**
	 * @return the haveAuditedBooks
	 */
	public int getHaveAuditedBooks() {
		return haveAuditedBooks;
	}
	/**
	 * @param haveAuditedBooks the haveAuditedBooks to set
	 */
	public void setHaveAuditedBooks(int haveAuditedBooks) {
		this.haveAuditedBooks = haveAuditedBooks;
	}
	/**
	 * @return the haveBeenBankrupt
	 */
	public int getHaveBeenBankrupt() {
		return haveBeenBankrupt;
	}
	/**
	 * @param haveBeenBankrupt the haveBeenBankrupt to set
	 */
	public void setHaveBeenBankrupt(int haveBeenBankrupt) {
		this.haveBeenBankrupt = haveBeenBankrupt;
	}
	/**
	 * @return the vendorCredId
	 */
	public int getVendorId() {
		return vendorId;
	}
	/**
	 * @param vendorCredId the vendorCredId to set
	 */
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	/**
	 * @return the bankruptcyDate
	 */
	public Date getBankruptcyDate() {
		return bankruptcyDate;
	}
	/**
	 * @param bankruptcyDate the bankruptcyDate to set
	 */
	public void setBankruptcyDate(Date bankruptcyDate) {
		this.bankruptcyDate = bankruptcyDate;
	}
	
}
