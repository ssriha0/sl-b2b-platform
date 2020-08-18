package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Policy")
public class Policy {
	
	@XStreamAlias("WarrantyLaborMonths")
	private Integer WarrantyLaborMonths;
	
	@XStreamAlias("WarrantyPartsMonths")
	private Integer WarrantyPartsMonths;
	
	@XStreamAlias("FreeEstimates")
	private Boolean FreeEstimates;

	

	public Boolean getFreeEstimates() {
		return FreeEstimates;
	}

	public void setFreeEstimates(Boolean freeEstimates) {
		FreeEstimates = freeEstimates;
	}

	public Integer getWarrantyLaborMonths() {
		return WarrantyLaborMonths;
	}

	public void setWarrantyLaborMonths(Integer warrantyLaborMonths) {
		WarrantyLaborMonths = warrantyLaborMonths;
	}

	public Integer getWarrantyPartsMonths() {
		return WarrantyPartsMonths;
	}

	public void setWarrantyPartsMonths(Integer warrantyPartsMonths) {
		WarrantyPartsMonths = warrantyPartsMonths;
	}



}
