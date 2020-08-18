package com.newco.marketplace.dto.vo.addons;


import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("addOn")
public class AddOn {

	@XStreamAlias("soAddonId")
	private String soAddonId;
	
	@XStreamAlias("addonSKU")
	private String addonSKU;
	
	@XStreamAlias("description")
	private String description;
	
	@XStreamAlias("customerCharge")
	private Double customerCharge;
	
	@XStreamAlias("miscInd")
	private String miscInd;
	
	@XStreamAlias("qty")
	private Integer qty;
	
	@XStreamAlias("editable")
	private String editable;
	
	@XStreamAlias("taxPercentage")
	private Double  taxPercentage;
	
	@XStreamAlias("margin")
	private String margin;
	
	@XStreamOmitField
	private String coverageType;
	
	/**
	 * @return the soAddonId
	 */
	public String getSoAddonId() {
		return soAddonId;
	}

	/**
	 * @param soAddonId the soAddonId to set
	 */
	public void setSoAddonId(String soAddonId) {
		this.soAddonId = soAddonId;
	}

	/**
	 * @return the addonSKU
	 */
	public String getAddonSKU() {
		return addonSKU;
	}

	/**
	 * @param addonSKU the addonSKU to set
	 */
	public void setAddonSKU(String addonSKU) {
		this.addonSKU = addonSKU;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getCustomerCharge() {
		return customerCharge;
	}

	public void setCustomerCharge(Double customerCharge) {
		this.customerCharge = customerCharge;
	}

	public String getMiscInd() {
		return miscInd;
	}

	public void setMiscInd(String miscInd) {
		this.miscInd = miscInd;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public String getEditable() {
		return editable;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public Double getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(Double taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	public String getMargin() {
		return margin;
	}

	public void setMargin(String margin) {
		this.margin = margin;
	}

	public String getCoverageType() {
		return coverageType;
	}

	public void setCoverageType(String coverageType) {
		this.coverageType = coverageType;
	}



		
	

}
