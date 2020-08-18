package com.newco.marketplace.dto.vo.serviceorder;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

public class PendingCancelDetailsVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -302892359599L;
	
	private Double price;
	private String comments;
	private Date  entryDate;
	private String pendingCancelSubstatus;
	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public String getPendingCancelSubstatus() {
		return pendingCancelSubstatus;
	}
	public void setPendingCancelSubstatus(String pendingCancelSubstatus) {
		this.pendingCancelSubstatus = pendingCancelSubstatus;
	}
	
	
	
	
	
}
