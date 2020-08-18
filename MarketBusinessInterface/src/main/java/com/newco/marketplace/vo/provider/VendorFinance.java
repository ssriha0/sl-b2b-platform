package com.newco.marketplace.vo.provider;

import java.util.Date;

public class VendorFinance extends BaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8431610005472493503L;
	private int vendor_id = -1;
	private int audit = -1;
	private int bankruptcy = -1;
	private Date bankruptcy_date;
	private int sale_volume = -1;
	private int budgeting = -1;
	private Date created_date;
	private Date modified_date;
	/**
	 * @return the audit
	 */
	public int getAudit() {
		return audit;
	}
	/**
	 * @param audit the audit to set
	 */
	public void setAudit(int audit) {
		this.audit = audit;
	}
	/**
	 * @return the bankruptcy
	 */
	public int getBankruptcy() {
		return bankruptcy;
	}
	/**
	 * @param bankruptcy the bankruptcy to set
	 */
	public void setBankruptcy(int bankruptcy) {
		this.bankruptcy = bankruptcy;
	}
	/**
	 * @return the bankruptcy_date
	 */
	public Date getBankruptcy_date() {
		return bankruptcy_date;
	}
	/**
	 * @param bankruptcy_date the bankruptcy_date to set
	 */
	public void setBankruptcy_date(Date bankruptcy_date) {
		this.bankruptcy_date = bankruptcy_date;
	}
	/**
	 * @return the budgeting
	 */
	public int getBudgeting() {
		return budgeting;
	}
	/**
	 * @param budgeting the budgeting to set
	 */
	public void setBudgeting(int budgeting) {
		this.budgeting = budgeting;
	}
	/**
	 * @return the created_date
	 */
	public Date getCreated_date() {
		return created_date;
	}
	/**
	 * @param created_date the created_date to set
	 */
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	/**
	 * @return the modified_date
	 */
	public Date getModified_date() {
		return modified_date;
	}
	/**
	 * @param modified_date the modified_date to set
	 */
	public void setModified_date(Date modified_date) {
		this.modified_date = modified_date;
	}
	/**
	 * @return the sale_volume
	 */
	public int getSale_volume() {
		return sale_volume;
	}
	/**
	 * @param sale_volume the sale_volume to set
	 */
	public void setSale_volume(int sale_volume) {
		this.sale_volume = sale_volume;
	}
	/**
	 * @return the vendor_id
	 */
	public int getVendor_id() {
		return vendor_id;
	}
	/**
	 * @param vendor_id the vendor_id to set
	 */
	public void setVendor_id(int vendor_id) {
		this.vendor_id = vendor_id;
	}
	



}
