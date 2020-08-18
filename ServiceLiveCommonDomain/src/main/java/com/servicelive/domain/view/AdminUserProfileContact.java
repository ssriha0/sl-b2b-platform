package com.servicelive.domain.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vw_ar_up_contact")
public class AdminUserProfileContact {
    
	@Id
	@Column(name = "resource_id")
	private Integer resourceId;
	
	@Column(name = "first_name")
	private Integer firstName;
	
	@Column(name = "last_name")
	private Integer lastName;

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	public Integer getFirstName() {
		return firstName;
	}

	public void setFirstName(Integer firstName) {
		this.firstName = firstName;
	}

	public Integer getLastName() {
		return lastName;
	}

	public void setLastName(Integer lastName) {
		this.lastName = lastName;
	}
	
	
	
}
