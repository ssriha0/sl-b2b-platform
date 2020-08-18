package com.newco.marketplace.dto.vo;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author sahmad7
 *
 */
public class SoRoutedProviders extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6090462422171018469L;
	private String soId;
	private Integer resourceId;
	private Integer resourceRspId;
	
	public Integer getResourceId() {
		return resourceId;
	}
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public Integer getResourceRspId() {
		return resourceRspId;
	}
	public void setResourceRspId(Integer resourceRspId) {
		this.resourceRspId = resourceRspId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}

}
