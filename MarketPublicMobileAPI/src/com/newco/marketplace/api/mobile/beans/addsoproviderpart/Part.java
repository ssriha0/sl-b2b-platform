package com.newco.marketplace.api.mobile.beans.addsoproviderpart;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("part")
@XmlAccessorType(XmlAccessType.FIELD)
public class Part {
	
	@XStreamAlias("div")
	private String div;

	@XStreamAlias("pls")
	private String pls;
	
	@XStreamAlias("partNumber")
	private String partNumber;
	
	@XStreamAlias("description")
	private String description;
	
	@XStreamAlias("onHandQuantity")
	private Integer onHandQuantity;
	
	@XStreamAlias("brand")
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
