package com.newco.marketplace.dto.vo.audit;

import com.sears.os.vo.SerializableBaseVO;

public class AuditUserProfileVO extends SerializableBaseVO{

	private static final long serialVersionUID = 9095938517192652937L;
	
	private int auditUserProfileId;
	private int loginResourceId;
	private int loginCompanyId;
	private int roleId;
	private int isSLAdminInd;
	private String actionPerformed;
	private String userProfileData;
	private String modifiedBy;
	public int getAuditUserProfileId() {
		return auditUserProfileId;
	}
	public void setAuditUserProfileId(int auditUserProfileId) {
		this.auditUserProfileId = auditUserProfileId;
	}
	public int getLoginResourceId() {
		return loginResourceId;
	}
	public void setLoginResourceId(int loginResourceId) {
		this.loginResourceId = loginResourceId;
	}
	public int getLoginCompanyId() {
		return loginCompanyId;
	}
	public void setLoginCompanyId(int loginCompanyId) {
		this.loginCompanyId = loginCompanyId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getActionPerformed() {
		return actionPerformed;
	}
	public void setActionPerformed(String actionPerformed) {
		this.actionPerformed = actionPerformed;
	}
	public String getUserProfileData() {
		return userProfileData;
	}
	public void setUserProfileData(String userProfileData) {
		this.userProfileData = userProfileData;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public int getIsSLAdminInd() {
		return isSLAdminInd;
	}
	public void setIsSLAdminInd(int isSLAdminInd) {
		this.isSLAdminInd = isSLAdminInd;
	}
	
}



