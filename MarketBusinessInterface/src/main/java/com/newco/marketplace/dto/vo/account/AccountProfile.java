package com.newco.marketplace.dto.vo.account;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.ActivityVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.webservices.base.CommonVO;

public class AccountProfile extends CommonVO {

	private static final long serialVersionUID = -3331714973882982087L;
	private Contact contact=new Contact();
	private UserProfile userProfile=new UserProfile();
	private List<ActivityVO> userProfilePermissions=new ArrayList<ActivityVO>(20); 
	private UserType userType=new UserType();
	
	public String getFirstName() {
		return contact.getFirstName();
	}
	public String getLastName() {
		return contact.getLastName();
	}
	public Integer getRoleId() {
		return userProfile.getRoleId();
	}
	public String getRoleName() {
		return userProfile.getRoleName();
	}
	public String getUserName() {
		return userProfile.getUserName();
	}
	public void setFirstName(String firstName) {
		contact.setFirstName(firstName);
	}
	public void setLastName(String lastName) {
		contact.setLastName(lastName);
	}
	public void setRoleId(Integer roleId) {
		userProfile.setRoleId(roleId);
	}
	public void setRoleName(String roleName) {
		userProfile.setRoleName(roleName);
	}
	public void setUserName(String userName) {
		userProfile.setUserName(userName);
	}
	public Integer getCompanyId() {
		return userType.getCompanyId();
	}
	public Integer getResourceId() {
		return userType.getResourceId();
	}
	public void setCompanyId(Integer companyId) {
		userType.setCompanyId(companyId);
	}
	public void setResourceId(Integer resourceId) {
		userType.setResourceId(resourceId);
	}
	public List<ActivityVO> getUserProfilePermissions() {
		return userProfilePermissions;
	}
	public void setUserProfilePermissions(List<ActivityVO> userProfilePermissions) {
		this.userProfilePermissions = userProfilePermissions;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
}
