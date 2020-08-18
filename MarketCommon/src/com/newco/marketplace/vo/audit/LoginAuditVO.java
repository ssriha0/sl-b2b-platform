package com.newco.marketplace.vo.audit;

import com.sears.os.vo.SerializableBaseVO;

public class LoginAuditVO  extends SerializableBaseVO{
	/**
	 *
	 */
	private static final long serialVersionUID = 1841530717142902895L;
	private int loginAuditId;
	private int sessionAuditId;
	private int resourceId;
	private int companyId;
	private int roleId;
	private int sessionTimeoutInd;

	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getSessionTimeoutInd() {
		return sessionTimeoutInd;
	}
	public void setSessionTimeoutInd(int sessionTimeoutInd) {
		this.sessionTimeoutInd = sessionTimeoutInd;
	}
	public int getLoginAuditId() {
		return loginAuditId;
	}
	public void setLoginAuditId(int loginAuditId) {
		this.loginAuditId = loginAuditId;
	}
	public int getSessionAuditId() {
		return sessionAuditId;
	}
	public void setSessionAuditId(int sessionAuditId) {
		this.sessionAuditId = sessionAuditId;
	}
		
}
