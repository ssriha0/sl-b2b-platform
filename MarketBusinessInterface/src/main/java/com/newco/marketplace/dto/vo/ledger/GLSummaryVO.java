package com.newco.marketplace.dto.vo.ledger;

import java.util.Date;

public class GLSummaryVO extends LedgerSummaryVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4600020878467651562L;
	private String soId;
	private String gl_t_account_no;
	private String gl_division; //stored as varchar in db
	private String category;
	private String descr;
	private Integer gl_process_id;
	private String creditInd;
	private Date createdDate;
	private Date modifiedDate;
	// fields from the OMS SHC data
	private String location; 
	private String sku;
	private String coverage;
	private double transactionAmount = 0.0;
	
	
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getGl_division() {
		return gl_division;
	}
	public void setGl_division(String gl_division) {
		this.gl_division = gl_division;
	}
	public String getGl_t_account_no() {
		return gl_t_account_no;
	}
	public void setGl_t_account_no(String gl_t_account_no) {
		this.gl_t_account_no = gl_t_account_no;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public Integer getGl_process_id() {
		return gl_process_id;
	}
	public void setGl_process_id(Integer gl_process_id) {
		this.gl_process_id = gl_process_id;
	}
	public String getCreditInd() {
		return creditInd;
	}
	public void setCreditInd(String creditInd) {
		this.creditInd = creditInd;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getCoverage() {
		return coverage;
	}
	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@Override
	public double getTransactionAmount() {
		return transactionAmount;
	}
	@Override
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getSoId() {
		return soId;
	}

	

}
