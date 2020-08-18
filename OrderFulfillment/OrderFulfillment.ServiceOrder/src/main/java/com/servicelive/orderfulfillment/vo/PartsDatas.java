package com.servicelive.orderfulfillment.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for the In Home Out
 * bound Notification Service
 * 
 * @author Infosys
 */
@XStreamAlias("partsDatas")
public class PartsDatas {

	@XStreamAlias("partDivNo")
	private String partDivNo;

	@XStreamAlias("partPlsNo")
	private String partPlsNo;

	@XStreamAlias("partPartNo")
	private String partPartNo;

	/*@XStreamAlias("partOrderQty")
	private String partOrderQty;*/

	@XStreamAlias("partInstallQty")
	private String partInstallQty;

	@XStreamAlias("partLocation")
	private String partLocation;

	@XStreamAlias("partCoverageCode")
	private String partCoverageCode;

	@XStreamAlias("partPrice")
	private String partPrice;

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

	/*public String getPartOrderQty() {
		return partOrderQty;
	}

	public void setPartOrderQty(String partOrderQty) {
		this.partOrderQty = partOrderQty;
	}*/

	public String getPartInstallQty() {
		return partInstallQty;
	}

	public void setPartInstallQty(String partInstallQty) {
		this.partInstallQty = partInstallQty;
	}

	public String getPartLocation() {
		return partLocation;
	}

	public void setPartLocation(String partLocation) {
		this.partLocation = partLocation;
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



}
