package com.newco.marketplace.dto.vo.serviceorder;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;


public class ServiceOrderAltBuyerContactVO extends SerializableBaseVO{

	private static final long serialVersionUID = -2692880888452955404L;
	private String firstName;
	private String lastName;
	private Integer contactId;
	private Date createdDate;
	private String entityId;
	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

//	public Integer getResourceId() {
//		return resourceId;
//	}
//
//	public void setResourceId(Integer resourceId) {
//		this.resourceId = resourceId;
//	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	
	

}
