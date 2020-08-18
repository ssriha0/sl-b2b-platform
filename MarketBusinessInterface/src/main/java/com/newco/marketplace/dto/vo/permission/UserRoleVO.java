package com.newco.marketplace.dto.vo.permission;

import com.sears.os.vo.SerializableBaseVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * $Revision$ $Author$ $Date$
 */
@XStreamAlias("UserRole")
public class UserRoleVO extends SerializableBaseVO{
	
	private static final long serialVersionUID = 4773349518657315835L;
	@XStreamAlias("RoleDesc")
	private String roleDescription;
	@XStreamAlias("CompanyRoleId")
	private int companyRoleId;
	
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	public int getCompanyRoleId() {
		return companyRoleId;
	}
	public void setCompanyRoleId(int companyRoleId) {
		this.companyRoleId = companyRoleId;
	}
}
