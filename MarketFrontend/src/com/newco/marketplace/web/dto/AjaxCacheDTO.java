package com.newco.marketplace.web.dto;

public class AjaxCacheDTO extends SerializedBaseDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5668128780991952425L;
	private String userName = "";
	private String roleType = "";
	private Integer companyId = -1; //Provider Admin or Buyer Admin
	private Integer vendBuyerResId = -1; //Provider Resource or Buyer Resource
	private Integer routedTabCount = -1;
	
	
	public Integer getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public Integer getRoutedTabCount() {
		return routedTabCount;
	}
	public void setRoutedTabCount(Integer routedTabCount) {
		this.routedTabCount = routedTabCount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getVendBuyerResId() {
		return vendBuyerResId;
	}
	public void setVendBuyerResId(Integer vendBuyerResId) {
		this.vendBuyerResId = vendBuyerResId;
	}
	
	

}
