package com.newco.marketplace.web.dto;

import java.util.Collection;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.account.AccountProfile;
import com.newco.marketplace.webservices.base.CommonVO;

public class ServiceOrdersCriteria extends CommonVO{
	
	private static final long serialVersionUID = -8756462200341377854L;
	private Integer companyId;
	private String fName;
	private String lName;
	private Integer roleId;
	private String roleType;
	private Integer statusId;	
	private String theUserName;
	private Boolean today;
	private Integer vendBuyerResId;
	private Collection<?> permissions;

	@Deprecated private SecurityContext securityContext;
	
	public ServiceOrdersCriteria(){}

	public ServiceOrdersCriteria(AccountProfile ap){
		this.companyId=ap.getCompanyId();
		this.fName=ap.getFirstName();
		this.lName=ap.getLastName();
		this.permissions=null;
		this.roleId=ap.getRoleId();
		this.roleType=ap.getRoleName();
		this.securityContext=null;
		this.statusId=null;
		this.theUserName=ap.getUserName();
		this.today=true;
		this.vendBuyerResId=ap.getResourceId();
		this.permissions=ap.getUserProfilePermissions();
		if(ap.getResourceId() != null && ap.getResourceId().intValue() == -1)
		{
			System.out.println(ap.getResourceId()+"//TODO:: Populate vendor rersource id upon login when the schema is updated.");
			//TODO:: Populate vendor rersource id upon login when the schema is updated.
			this.setVendBuyerResId( securityContext.getVendBuyerResId());
		}
		else
		{
			System.out.println("//TODO:: Populate vendor rersource id upon login when the schema is updated.");
			//this.vendBuyerResId( lvo.getVendBuyerResId() );
		}
	}
	
	public ServiceOrdersCriteria( Integer companyId, 
								  Integer vendBuyerResId,
								  String roleType,
								  Integer roleId,
								  Boolean today)
	{
		this.companyId 			= companyId;
		this.vendBuyerResId 	= vendBuyerResId;
		this.roleType			= roleType;
		this.today 				= today;
		this.roleId				= roleId;
	}

	public Integer getCompanyId() {
		return companyId;
	}
	
	public String getFName() {
		return fName;
	}
	
	
	public String getLName() {
		return lName;
	}
	public Integer getRoleId() {
		return roleId;
	}

	public String getRoleType() {
		return roleType;
	}

	public SecurityContext getSecurityContext() {
		return securityContext;
	}

	public Integer getStatusId() {
		return statusId;
	}

	public String getTheUserName() {
		return theUserName;
	}

	public Boolean getToday() {
		return today;
	}

	public Integer getVendBuyerResId() {
		return vendBuyerResId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setFName(String name) {
		fName = name;
	}

	public void setLName(String name) {
		lName = name;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public void setSecurityContext(SecurityContext securityContext) {
		this.securityContext = securityContext;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public void setTheUserName(String theUserName) {
		this.theUserName = theUserName;
	}
	
	public void setToday(Boolean today) {
		this.today = today;
	}

	public void setVendBuyerResId(Integer vendBuyerResId) {
		this.vendBuyerResId = vendBuyerResId;
	}

	public Collection<?> getPermissions() {
		return permissions;
	}

	public void setPermissions(Collection<?> permissions) {
		this.permissions = permissions;
	}

}
