package com.newco.marketplace.api.mobile.beans.updateSoCompletion;

import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * This is a generic bean class for storing addOn information.
 * @author Infosys
 *
 */
@XStreamAlias("addOn")
public class AddOn {
	
	@XStreamAlias("soAddonId")
	private Integer soAddonId;
	
	@XStreamAlias("addOnSKU")
	private String addOnSKU;
	
	@XStreamAlias("description")
	private String description;
	
	@XStreamAlias("customerCharge")
	private String customerCharge;
	
	@XStreamAlias("qty")
	private Integer qty;   
	
	@XStreamAlias("miscInd")
	private String miscInd;	
	
	@XStreamOmitField
	private String soId;
	
	@XStreamOmitField
	private Date createdDate;
	
	@XStreamOmitField
	private Date modifiedDate;
	
	@XStreamOmitField
	private String modifiedBy;
	
	@XStreamOmitField
	private Boolean miscellInd;

	
	
	

	public Boolean getMiscellInd() {
		return miscellInd;
	}

	public void setMiscellInd(Boolean miscellInd) {
		this.miscellInd = miscellInd;
	}

	public String getAddOnSKU() {
		return addOnSKU;
	}

	public void setAddOnSKU(String addOnSKU) {
		this.addOnSKU = addOnSKU;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCustomerCharge() {
		return customerCharge;
	}

	public void setCustomerCharge(String customerCharge) {
		this.customerCharge = customerCharge;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public boolean getMisc() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getMiscInd() {
		return miscInd;
	}

	public void setMiscInd(String miscInd) {
		this.miscInd = miscInd;
	}

	

	public Integer getSoAddonId() {
		return soAddonId;
	}

	public void setSoAddonId(Integer soAddonId) {
		this.soAddonId = soAddonId;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
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
	
	

}
