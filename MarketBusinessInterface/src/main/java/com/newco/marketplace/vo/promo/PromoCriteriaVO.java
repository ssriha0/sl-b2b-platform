package com.newco.marketplace.vo.promo;

import com.sears.os.vo.SerializableBaseVO;

public class PromoCriteriaVO extends SerializableBaseVO{

	boolean soDependent;
	Integer roleId;
	
	public boolean isSoDependent() {
		return soDependent;
	}
	public void setSoDependent(boolean soDependent) {
		this.soDependent = soDependent;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	
}
