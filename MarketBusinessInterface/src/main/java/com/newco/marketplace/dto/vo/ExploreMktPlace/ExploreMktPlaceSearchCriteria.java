package com.newco.marketplace.dto.vo.ExploreMktPlace;

import com.newco.marketplace.criteria.ICriteria;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSearchCriteriaVO;

public class ExploreMktPlaceSearchCriteria extends ProviderSearchCriteriaVO implements ICriteria {

	private static final long serialVersionUID = -8871986675951786027L;
	private Integer mktReady;
	private Integer serviceCategoryId;
	private String buyerZipCodeStr;
	
	public Integer getMktReady() {
		return mktReady;
	}
	
	public void setMktReady(Integer mktReady) {
		this.mktReady = mktReady;
	}
	
	public Integer getServiceCategoryId() {
		return serviceCategoryId;
	}

	public void setServiceCategoryId(Integer serviceCategoryId) {
		this.serviceCategoryId = serviceCategoryId;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public boolean isSet() {
		// TODO Auto-generated method stub
		return false;
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @return the buyerZipCodeStr
	 */
	public String getBuyerZipCodeStr() {
		return buyerZipCodeStr;
	}

	/**
	 * @param buyerZipCodeStr the buyerZipCodeStr to set
	 */
	public void setBuyerZipCodeStr(String buyerZipCodeStr) {
		this.buyerZipCodeStr = buyerZipCodeStr;
	}


	
}
