package com.newco.marketplace.api.mobile.beans.addsoproviderpart;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ns2:parts")
public class LpnPart {
	
	@XStreamAlias("ns2:div")
	private String div;

	@XStreamAlias("ns2:pls")
	private String pls;
	
	@XStreamAlias("ns2:partNumber")
	private String partNumber;
	
	@XStreamAlias("ns2:description")
	private String description;
	
	@XStreamAlias("ns2:onHandQuantity")
	private Integer onHandQuantity;
	
	@XStreamAlias("ns2:brand")
	private String brand;

	public String getDiv() {
		return div;
	}

	public void setDiv(String div) {
		this.div = div;
	}

	public String getPls() {
		return pls;
	}

	public void setPls(String pls) {
		this.pls = pls;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getOnHandQuantity() {
		return onHandQuantity;
	}

	public void setOnHandQuantity(Integer onHandQuantity) {
		this.onHandQuantity = onHandQuantity;
	}

}
