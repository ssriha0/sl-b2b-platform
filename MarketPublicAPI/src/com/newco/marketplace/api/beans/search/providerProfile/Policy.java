/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 08-SEP-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.beans.search.providerProfile;

import com.newco.marketplace.search.vo.ProviderSearchResponseVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing all information of 
 * the Policy Details 
 * @author Infosys
 *
 */
@XStreamAlias("policy")
public class Policy {
	
	@XStreamAlias("warrantyLaborMonths")
	private Integer warrantyLaborMonths;
	
	@XStreamAlias("warrantyPartsMonths")
	private Integer warrantyPartsMonths;
	
	@XStreamAlias("freeEstimates")
	private boolean  freeEstimates;

	public Policy() {
		
	}
	
	public Policy(ProviderSearchResponseVO providerSearchResponseVO) {
		this.setWarrantyLaborMonths(providerSearchResponseVO
				.getLaborWarrantyDays() / 30);
		this.setWarrantyPartsMonths(providerSearchResponseVO
				.getLaborPartsDays() / 30);
		this.setFreeEstimates(providerSearchResponseVO
				.isOffersFreeEstimatesInd());
	}
	
	
	public Integer getWarrantyLaborMonths() {
		return warrantyLaborMonths;
	}

	public void setWarrantyLaborMonths(Integer warrantyLaborMonths) {
		this.warrantyLaborMonths = warrantyLaborMonths;
	}

	public Integer getWarrantyPartsMonths() {
		return warrantyPartsMonths;
	}

	public void setWarrantyPartsMonths(Integer warrantyPartsMonths) {
		this.warrantyPartsMonths = warrantyPartsMonths;
	}

	public boolean isFreeEstimates() {
		return freeEstimates;
	}

	public void setFreeEstimates(boolean freeEstimates) {
		this.freeEstimates = freeEstimates;
	}
}
