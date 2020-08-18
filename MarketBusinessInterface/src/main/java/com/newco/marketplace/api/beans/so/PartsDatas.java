package com.newco.marketplace.api.beans.so;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("invoicePart")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartsDatas {
	
	@XStreamAlias("partDivNo")
	private String partDivNo;

	@XStreamAlias("partPlsNo")
	private String partPlsNo;

	@XStreamAlias("partPartNo")
	private String partPartNo;
	
	@XStreamAlias("partQty")
	private Integer partQty;
    
	@XStreamAlias("partCoverageCode")
	private String partCoverageCode;
	
	@XStreamAlias("partPrice")
	private String partPrice;
	
	@XStreamAlias("partStatus")
	private String partStatus;


	public String getPartDivNo() {
		return partDivNo;
	}


	public void setPartDivNo(String partDivNo) {
		this.partDivNo = partDivNo;
	}


	public String getPartPlsNo() {
		return partPlsNo;
	}


	public void setPartPlsNo(String partPlsNo) {
		this.partPlsNo = partPlsNo;
	}


	public String getPartPartNo() {
		return partPartNo;
	}


	public void setPartPartNo(String partPartNo) {
		this.partPartNo = partPartNo;
	}


	public Integer getPartQty() {
		return partQty;
	}


	public void setPartQty(Integer partQty) {
		this.partQty = partQty;
	}


	public String getPartCoverageCode() {
		return partCoverageCode;
	}


	public void setPartCoverageCode(String partCoverageCode) {
		this.partCoverageCode = partCoverageCode;
	}


	public String getPartPrice() {
		return partPrice;
	}


	public void setPartPrice(String partPrice) {
		this.partPrice = partPrice;
	}


	public String getPartStatus() {
		return partStatus;
	}


	public void setPartStatus(String partStatus) {
		this.partStatus = partStatus;
	}
	
	
	
	

 
}
