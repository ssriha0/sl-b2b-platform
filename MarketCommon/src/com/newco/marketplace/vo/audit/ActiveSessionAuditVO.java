package com.newco.marketplace.vo.audit;

import com.sears.os.vo.SerializableBaseVO;

public class ActiveSessionAuditVO extends SerializableBaseVO{
	/**
	 *
	 */
	private static final long serialVersionUID = 1841530717142902895L;
	private Long activeSessionAuditId;
	private int resourceId;
	private int companyId;
	private int roleId;
	public Long getActiveSessionAuditId() {
		return activeSessionAuditId;
	}
	public void setActiveSessionAuditId(Long activeSessionAuditId) {
		this.activeSessionAuditId = activeSessionAuditId;
	}
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
	
}
