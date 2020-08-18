package com.newco.marketplace.web.dto.provider;


public class ResourceDto extends BaseDto
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4194260326416005647L;
	private long resourceId;
	private String resourceName;
	
	public long getResourceId() {
		return resourceId;
	}
	public void setResourceId(long resourceId) {
		this.resourceId = resourceId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
}
