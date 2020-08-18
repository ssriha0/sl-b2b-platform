package com.newco.marketplace.vo.provider;

public class FirmAndResource extends BaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3678922563226365980L;
	private String resourceIdList = null;
	private Integer vendorId = -1;
	
	public String getResourceIdList() {
		return resourceIdList;
	}

	public void setResourceIdList(String resourceIdList) {
		this.resourceIdList = resourceIdList;
	}

	public Integer getVendorId() {
		return vendorId;
	}
	
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	@Override
	public String toString()
	{
		return "vendorResourceID: "+getResourceIdList().toString()
		+"\nvendorID: "+getVendorId();
	}
}
