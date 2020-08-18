/**
 * 
 */
package com.servicelive.spn.common;

/**
 * @author hoza
 *
 */
public enum RoleEnum {
	/** This enum reresent enterprise buyer */ENTERPRISE_BUYER_ROLE(new Integer(3)),
	/** provider role */PROVIDER_ROLE ( new Integer(1)),
	/** Sl Admin */SL_ADMIN_ROLE(new Integer(2)),
	/** Home and Ofice Buyer*/SIMPLE_BUYER_ROLE(new Integer(5));
	
	private Integer roleId;

	/**
	 * @param roleId
	 */
	private RoleEnum(Integer roleId) {
		this.roleId = roleId;
	}
	
	public Integer getRoleId(){
		return this.roleId;
	}
	
}
