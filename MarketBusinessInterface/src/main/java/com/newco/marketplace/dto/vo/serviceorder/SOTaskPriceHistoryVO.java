package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.newco.marketplace.webservices.base.CommonVO;

public class SOTaskPriceHistoryVO extends CommonVO implements Comparable<SOTaskPriceHistoryVO>{

	private String soId;
	private String sequenceNumber;
	private Double price;
	private Timestamp createdDate;
	private Date modifiedDate;
	private String modifiedBy;
	private String modifiedByName;
	private String modifiedDateFmt;
	private String sku;
	private Integer taskSeqNum;
	
	public Integer getTaskSeqNum() {
		return taskSeqNum;
	}

	public void setTaskSeqNum(Integer taskSeqNum) {
		this.taskSeqNum = taskSeqNum;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getModifiedDateFmt() {
		return getFormatdDate();
	}

	public void setModifiedDateFmt(String modifiedDateFmt) {
		getFormatdDate();
	}

	public String getFormatdDate(){
		Date date =  getModifiedDate();
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String displayDate = df.format(date) ;
		String newDate = displayDate.substring(0, 10);
		this.modifiedDateFmt = newDate;
		return newDate;
	}
	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

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

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedByName() {
		return modifiedByName;
	}

	public void setModifiedByName(String modifiedByName) {
		this.modifiedByName = modifiedByName;
	}

	public int compareTo(SOTaskPriceHistoryVO arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
