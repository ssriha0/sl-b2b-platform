package com.servicelive.lookup.vo;

import java.io.Serializable;


/**
 * Class EntityLookupVO.
 */
public class EntityLookupVO implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 63944399882507995L;

	/** entityId. */
	private Long entityId;
	
	/** fundingTypeId. */
	private Long fundingTypeId;
	
	/** firstName. */
	private String firstName;
	
	/** lastName. */
	private String lastName;
	
	/** state. */
	private String state;
	
	/**
	 * getEntityId.
	 * 
	 * @return Long
	 */
	public Long getEntityId() {
	
		return entityId;
	}
	
	/**
	 * setEntityId.
	 * 
	 * @param entityId 
	 * 
	 * @return void
	 */
	public void setEntityId(Long entityId) {
	
		this.entityId = entityId;
	}
	
	/**
	 * getFundingTypeId.
	 * 
	 * @return Long
	 */
	public Long getFundingTypeId() {
	
		return fundingTypeId;
	}
	
	/**
	 * setFundingTypeId.
	 * 
	 * @param fundingTypeId 
	 * 
	 * @return void
	 */
	public void setFundingTypeId(Long fundingTypeId) {
	
		this.fundingTypeId = fundingTypeId;
	}
	
	/**
	 * getFirstName.
	 * 
	 * @return String
	 */
	public String getFirstName() {
	
		return firstName;
	}
	
	/**
	 * setFirstName.
	 * 
	 * @param firstName 
	 * 
	 * @return void
	 */
	public void setFirstName(String firstName) {
	
		this.firstName = firstName;
	}
	
	/**
	 * getLastName.
	 * 
	 * @return String
	 */
	public String getLastName() {
	
		return lastName;
	}
	
	/**
	 * setLastName.
	 * 
	 * @param lastName 
	 * 
	 * @return void
	 */
	public void setLastName(String lastName) {
	
		this.lastName = lastName;
	}
	
	/**
	 * getState.
	 * 
	 * @return String
	 */
	public String getState() {
	
		return state;
	}
	
	/**
	 * setState.
	 * 
	 * @param state 
	 * 
	 * @return void
	 */
	public void setState(String state) {
	
		this.state = state;
	}
}
