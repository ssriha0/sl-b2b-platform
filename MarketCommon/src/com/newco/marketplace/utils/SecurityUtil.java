package com.newco.marketplace.utils;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.login.LoginCredentialVO;

/**
 * 
 * $Revision: 1.5 $ $Author: glacy $ $Date: 2008/04/26 00:51:53 $
 *
 */
public class SecurityUtil {

	public static SecurityContext getSystemSecurityContext(){
		SecurityContext securityContext = new SecurityContext(OrderConstants.SYSTEM_ROLE);

		securityContext.setVendBuyerResId(0);
		securityContext.setCompanyId(0);
		securityContext.setRole(OrderConstants.SYSTEM_ROLE);
		securityContext.setRoleId(OrderConstants.NEWCO_ADMIN_ROLEID);

		LoginCredentialVO role = new LoginCredentialVO();
		role.setFirstName(OrderConstants.SYSTEM_ROLE);
		role.setLastName("");
		role.setUsername(OrderConstants.SYSTEM_ROLE.toLowerCase());

		securityContext.setRoles(role);
		securityContext.setUsername(OrderConstants.SYSTEM_ROLE.toLowerCase());

		return securityContext;
	}
}
/*
 * Maintenance History:
 * $Log: SecurityUtil.java,v $
 * Revision 1.5  2008/04/26 00:51:53  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.2.14.1  2008/04/01 22:01:30  mhaye05
 * merged changed from Head into I18_Fin branch
 *
 * Revision 1.3  2008/03/27 18:57:33  mhaye05
 * Merged I18_ADM to Head
 *
 * Revision 1.2.18.1  2008/03/12 16:12:17  mhaye05
 * added javadoc
 *
 */