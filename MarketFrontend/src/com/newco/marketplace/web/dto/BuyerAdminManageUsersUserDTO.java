package com.newco.marketplace.web.dto;

import java.util.List;

import com.newco.marketplace.dto.vo.ActivityVO;
import com.newco.marketplace.dto.vo.permission.UserRoleVO;

/**
 * $Revision: 1.4 $ $Author: glacy $ $Date: 2008/04/26 01:13:44 $
 */
public class BuyerAdminManageUsersUserDTO extends SerializedBaseDTO
{

	private static final long serialVersionUID = 1794230494585866274L;
	private String name;
	private String title;
	private Double maxSpendLimit;
	private String username;
	private Integer resourceId;
	private List<UserRoleVO> companyRoles;
	private List<ActivityVO> userActivities;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getMaxSpendLimit() {
		return maxSpendLimit;
	}
	public void setMaxSpendLimit(Double maxSpendLimit) {
		this.maxSpendLimit = maxSpendLimit;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
	public List<UserRoleVO> getCompanyRoles() {
		return companyRoles;
	}
	public void setCompanyRoles(List<UserRoleVO> companyRoles) {
		this.companyRoles = companyRoles;
	}
	public List<ActivityVO> getUserActivities() {
		return userActivities;
	}
	public void setUserActivities(List<ActivityVO> userActivities) {
		this.userActivities = userActivities;
	}

	
}
