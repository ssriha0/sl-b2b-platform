package com.newco.marketplace.dto.vo.price;

import java.sql.Timestamp;
import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;

public class PendingCancelPriceVO extends SerializableBaseVO{
	
	private static final long serialVersionUID = 0L;
	
	private String soId;
	private Double price;
	private String comments;
	private Date entryDate;
	
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
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
	
	
	
	
 
		
}
