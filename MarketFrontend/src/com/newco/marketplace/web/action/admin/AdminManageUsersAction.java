package com.newco.marketplace.web.action.admin;

import java.util.List;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.IManageUsersDelegate;
import com.newco.marketplace.web.dto.AdminManageUsersUserDTO;
import com.newco.marketplace.web.security.AdminPageAction;
import com.opensymphony.xwork2.Preparable;

/**
 * $Revision: 1.6 $ $Author: glacy $ $Date: 2008/04/26 01:13:52 $
 */
@AdminPageAction
public class AdminManageUsersAction extends SLBaseAction implements Preparable
{

	private static final long serialVersionUID = -8816707461562441751L;
	private IManageUsersDelegate manageUsersDelegate;
	
	
	public AdminManageUsersAction(IManageUsersDelegate manageUsersDelegate)
	{
		this.manageUsersDelegate = manageUsersDelegate;
	}
	

	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();	
	}	

	
	// EXECUTE METHODS SHOULD BE CONSIDERED AS AN ENTRY POINT 
	public String execute() throws Exception
	{		
		
		List<AdminManageUsersUserDTO> usersList;
	
		
		usersList = manageUsersDelegate.getAllAdminUsers();
		setAttribute("usersList", usersList);
		//load resetPassword permission here
		SecurityContext securityContext = (SecurityContext) getSession().getAttribute("SecurityContext");
		if (securityContext != null) {
			boolean passwordResetForSLAdmin = securityContext.getRoles().isPasswordResetForSLAdmin();
			boolean passwordResetForAllExternalUsers = securityContext.getRoles().isPasswordResetForAllExternalUsers();

			if (passwordResetForSLAdmin == true)
				setAttribute("passwordResetForSLAdmin", "true");
			if (passwordResetForAllExternalUsers == true)		
				setAttribute("passwordResetForAllExternalUsers", "true");
		}
		
//		Boolean passwordResetForSLAdmin = (Boolean)getSession().getAttribute("passwordResetForSLAdmin");
//		Boolean passwordResetForAllExternalUsers = (Boolean) getSession().getAttribute("passwordResetForAllExternalUsers");
//		if (passwordResetForSLAdmin != null)
//			setAttribute("passwordResetForSLAdmin", passwordResetForSLAdmin.toString());
//		if (passwordResetForAllExternalUsers != null)		
//			setAttribute("passwordResetForAllExternalUsers", passwordResetForAllExternalUsers.toString());
		retrieveActionMessage();
		return SUCCESS;
	}

	public IManageUsersDelegate getManageUsersDelegate() {
		return manageUsersDelegate;
	}


	public void setManageUsersDelegate(IManageUsersDelegate manageUsersDelegate) {
		this.manageUsersDelegate = manageUsersDelegate;
	}
	
}
