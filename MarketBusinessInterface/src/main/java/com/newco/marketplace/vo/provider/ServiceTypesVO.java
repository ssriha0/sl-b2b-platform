package com.newco.marketplace.vo.provider;

import com.sears.os.vo.SerializableBaseVO;


public class ServiceTypesVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -617276219992745052L;
	private String description;
	private int serviceTypeId;
	private boolean active;
	private Integer rootNodeId;

	public ServiceTypesVO() {
		
	}
	
	public ServiceTypesVO(ServiceTypesVO vo) {
		
	}
	
	/** @return boolean isActive*/
	public boolean isActive() {
		return active;
	}
	/** @param active */
	public void setActive(boolean active) {
		this.active = active;
	}
	/** @return int serviceTypeId */
	public int getServiceTypeId() {
		return serviceTypeId;
	}
	/** @param serviceTypeId */
	public void setServiceTypeId(int serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	/** @return String description */
	public String getDescription() {
		return description;
	}
	/** @param description */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the rootNodeId
	 */
	public Integer getRootNodeId() {
		return rootNodeId;
	}

	/**
	 * @param rootNodeId the rootNodeId to set
	 */
	public void setRootNodeId(Integer rootNodeId) {
		this.rootNodeId = rootNodeId;
	}

}
