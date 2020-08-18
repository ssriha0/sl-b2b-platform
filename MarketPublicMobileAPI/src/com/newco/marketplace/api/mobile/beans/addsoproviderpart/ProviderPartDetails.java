package com.newco.marketplace.api.mobile.beans.addsoproviderpart;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("providerPart")
public class ProviderPartDetails {
	
	@XStreamAlias("category")
	private String category;

	@XStreamAlias("partNumber")
	private String partNumber;

	@XStreamAlias("partDescription")
	private String partDescription;

	@XStreamAlias("qty")
	private Integer qty;

	@XStreamAlias("divisionNumber")
	private String divisionNumber;

	@XStreamAlias("sourceNumber")
	private String sourceNumber;
	
	@XStreamAlias("retailPrice")
	private String retailPrice;
	
	@XStreamAlias("isManual")
	private String isManual;
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getPartDescription() {
		return partDescription;
	}

	public void setPartDescription(String partDescription) {
		this.partDescription = partDescription;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getDivisionNumber() {
		return divisionNumber;
	}

	public void setDivisionNumber(String divisionNumber) {
		this.divisionNumber = divisionNumber;
	}

	public String getSourceNumber() {
		return sourceNumber;
	}

	public void setSourceNumber(String sourceNumber) {
		this.sourceNumber = sourceNumber;
	}

	public String getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}

	public String getIsManual() {
		return isManual;
	}

	public void setIsManual(String isManual) {
		this.isManual = isManual;
	}
	
}
