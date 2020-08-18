package com.newco.marketplace.dto.vo.skillTree;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author zizrale
 *
 */
public class ServiceTypesVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5837255889506073352L;
	private String description;
	private Integer serviceTypeId;
	private boolean active;

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
	public Integer getServiceTypeId() {
		return serviceTypeId;
	}
	/** @param serviceTypeId */
	public void setServiceTypeId(Integer serviceTypeId) {
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
}
