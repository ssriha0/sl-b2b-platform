package com.newco.marketplace.vo.provider;
import com.sears.os.vo.SerializableBaseVO;

public class ResourceVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7163851384378300041L;
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
