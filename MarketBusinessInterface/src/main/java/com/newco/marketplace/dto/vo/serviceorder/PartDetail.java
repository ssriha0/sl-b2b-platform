package com.newco.marketplace.dto.vo.serviceorder;


import java.util.Date;

import com.newco.marketplace.webservices.base.CommonVO;
public class PartDetail extends CommonVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6955193595467096878L;
	private String partNo;
	private String partDs;
	private Double price;
	private Double salesTax;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getPartDs() {
		return partDs;
	}
	public void setPartDs(String partDs) {
		this.partDs = partDs;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getSalesTax() {
		return salesTax;
	}
	public void setSalesTax(Double salesTax) {
		this.salesTax = salesTax;
	}

}
