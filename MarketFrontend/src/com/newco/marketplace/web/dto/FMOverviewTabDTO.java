package com.newco.marketplace.web.dto;

import java.sql.Date;

public class FMOverviewTabDTO extends SerializedBaseDTO
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2529934673191369237L;
	private String selectedDropdown;
	private Integer transactionNumber;
	private Date dateTime;
	private String type;
	private String soId;
	private String status;
	private Double amount;
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public String getSelectedDropdown() {
		return selectedDropdown;
	}
	public void setSelectedDropdown(String selectedDropdown) {
		this.selectedDropdown = selectedDropdown;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTransactionNumber() {
		return transactionNumber;
	}
	public void setTransactionNumber(Integer transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
