/**
 * 
 */
package com.newco.marketplace.web.dto.provider;

public class VendorResourceDto extends BaseDto
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4666922732885647105L;

	private Integer vendorId = -1;
    
    private Integer resourceId = -1;
    
    private Integer wfStateId = -1;
    
    private String wfState;

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getWfStateId() {
		return wfStateId;
	}

	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}

	/**
	 * @return the wfState
	 */
	public String getWfState() {
		return wfState;
	}

	/**
	 * @param wfState the wfState to set
	 */
	public void setWfState(String wfState) {
		this.wfState = wfState;
	}
    
  

}
