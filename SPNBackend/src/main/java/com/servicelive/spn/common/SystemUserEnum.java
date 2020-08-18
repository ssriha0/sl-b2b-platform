/**
 * 
 */
package com.servicelive.spn.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import static com.servicelive.spn.common.SPNBackendConstants.*;
/**
 * @author hoza
 *
 */
public enum SystemUserEnum {
	ENUM_SYSTEM_USER("SYSTEM"),
	ENUM_MEMBERMAINTANENCE_USER(MODIFIED_BY_MEMBERMAINTENANCE),
	ENUM_INVITATION_JOB_USER(MODIFIED_BY_INVITATION_JOB);
	
	
	private String nameOfUser;
	private static final Map<String,SystemUserEnum> lookup     = new HashMap<String,SystemUserEnum>();
	static {
	     for(SystemUserEnum s : EnumSet.allOf(SystemUserEnum.class))
	          lookup.put(s.getNameOfUser(), s);
	}

	private SystemUserEnum(String userName) {
		this.nameOfUser = userName;
	}

	/**
	 * @return the nameOfUser
	 */
	public String getNameOfUser() {
		return nameOfUser;
	}
	
	public static Boolean isSystemUser(String userName) {
		if(lookup.containsKey(userName)){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	
}
