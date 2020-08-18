package com.newco.marketplace.dto.vo.providerSearch;

import com.sears.os.vo.SerializableBaseVO;

public class ProviderBackgroundCheckResultsVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8173298833978526266L;
	private Integer vendorResourceID;
	private Integer backgroundCheckStatus;	
	
	public Integer getBackgroundCheckStatus() {
		return backgroundCheckStatus;
	}
	public void setBackgroundCheckStatus(Integer backgroundCheckStatus) {
		this.backgroundCheckStatus = backgroundCheckStatus;
	}
	public Integer getVendorResourceID() {
		return vendorResourceID;
	}
	public void setVendorResourceID(Integer vendorResourceID) {
		this.vendorResourceID = vendorResourceID;
	}	

}
