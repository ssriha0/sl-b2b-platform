package com.newco.marketplace.api.mobile.beans.sodetails;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * This is a generic bean class for storing PermitAddon information.
 * @author Infosys
 *
 */

@XStreamAlias("permitAddon")
@XmlAccessorType(XmlAccessType.FIELD)
public class PermitAddon {
	
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
	
	@XStreamAlias("autogenAddon")
	private Boolean autogenAddon;
	
	@XStreamAlias("permitType")
	private String permitType;
	
	@XStreamOmitField
	private String soId;
	
	@XStreamOmitField
	private Integer misc = 0;
	
	@XStreamOmitField
	private String scopeOfWork;
	
	@XStreamOmitField
	private String coverageType;

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

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

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the customerCharge
	 */
	public Double getCustomerCharge() {
		return customerCharge;
	}

	/**
	 * @param customerCharge the customerCharge to set
	 */
	public void setCustomerCharge(Double customerCharge) {
		this.customerCharge = customerCharge;
	}

	/**
	 * @return the miscInd
	 */
	public String getMiscInd() {
		return miscInd;
	}

	/**
	 * @param miscInd the miscInd to set
	 */
	public void setMiscInd(String miscInd) {
		this.miscInd = miscInd;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public Integer getMisc() {
		return misc;
	}

	public void setMisc(Integer misc) {
		this.misc = misc;
	}

	public String getScopeOfWork() {
		return scopeOfWork;
	}

	public void setScopeOfWork(String scopeOfWork) {
		this.scopeOfWork = scopeOfWork;
	}

	public Boolean getAutogenAddon() {
		return autogenAddon;
	}

	public void setAutogenAddon(Boolean autogenAddon) {
		this.autogenAddon = autogenAddon;
	}

	public String getCoverageType() {
		return coverageType;
	}

	public void setCoverageType(String coverageType) {
		this.coverageType = coverageType;
	}

	public String getPermitType() {
		return permitType;
	}

	public void setPermitType(String permitType) {
		this.permitType = permitType;
	}
}
