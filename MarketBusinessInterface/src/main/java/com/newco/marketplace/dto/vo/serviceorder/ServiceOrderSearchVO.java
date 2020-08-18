package com.newco.marketplace.dto.vo.serviceorder;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.sears.os.vo.request.ABaseServiceRequestVO;

public class ServiceOrderSearchVO extends ABaseServiceRequestVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2219001843491824865L;
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Integer getRoleID() {
		return roleID;
	}
	public void setRoleID(Integer roleID) {
		this.roleID = roleID;
	}
	
	public Integer getSearchType() {
		return searchType;
	}
	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("clientId", getClientId())
		.append("userId", getUserId())
		.append("password", getPassword())
		.toString();
	}
	
	private Integer searchType;
	private String searchValue;
	
	private String role;
	private Integer roleID;	
	
} 
