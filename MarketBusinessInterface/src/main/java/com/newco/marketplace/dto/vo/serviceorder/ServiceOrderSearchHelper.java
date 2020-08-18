package com.newco.marketplace.dto.vo.serviceorder;

import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

public class ServiceOrderSearchHelper extends SerializableBaseVO{	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8522216386300297374L;
	private String soID;
	private String roleType;
	private String firstName;
	private String lastName;
	private String soStatus;
	private String soSubStatus;
	private Integer contactLocationTypeID;
	
	private List<ServiceOrderSearchResultsVO> searchList;
	
	
	public Integer getContactLocationTypeID() {
		return contactLocationTypeID;
	}

	public void setContactLocationTypeID(Integer contactLocationTypeID) {
		this.contactLocationTypeID = contactLocationTypeID;
	}

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

	public String getSoID() {
		return soID;
	}

	public void setSoID(String soID) {
		this.soID = soID;
	}

	public String getSoStatus() {
		return soStatus;
	}

	public void setSoStatus(String soStatus) {
		this.soStatus = soStatus;
	}

	public String getSoSubStatus() {
		return soSubStatus;
	}

	public void setSoSubStatus(String soSubStatus) {
		this.soSubStatus = soSubStatus;
	}

	public List<ServiceOrderSearchResultsVO> getSearchList() {
		return searchList;
	}
	
	public void setSearchList(List<ServiceOrderSearchResultsVO> searchList) {
		this.searchList = searchList;
	}	
	
	public String getRoleType() {
		return roleType;
	}
	
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
}
