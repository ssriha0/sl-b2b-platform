package com.newco.marketplace.web.action.admin;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.security.AdminPageAction;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Preparable;
@AdminPageAction
public class AdminDashboardAction extends SLBaseAction implements Preparable
{
	private static final long serialVersionUID = 1L;

	public void prepare() throws Exception
	{
		ActionContext.getContext().getSession().put("forApprovalWidget","true");	
		String isFromPA = (String)getRequest().getSession().getAttribute("isFromPA"); 
		if ("true".equals(isFromPA))
		{
			SecurityContext securityContext = (SecurityContext) getSession().getAttribute(SECURITY_KEY);
			securityContext.setRoleId(OrderConstants.NEWCO_ADMIN_ROLEID);
			securityContext.setUsername(securityContext.getSlAdminUName());
			securityContext.setRole(OrderConstants.NEWCO_ADMIN);
			//Refer to LoginAction where companyId is set to 99 by default
			securityContext.setCompanyId(99);
			//Set by default
			securityContext.setVendBuyerResId(-1);

			ActionContext.getContext().getSession().remove("vendorId");
			ActionContext.getContext().getSession().remove("resourceId");
			ActionContext.getContext().getSession().remove("auditTask");
			ActionContext.getContext().getSession().remove("isFromPA");

		}
		createCommonServiceOrderCriteria();
	}


	// EXECUTE METHODS SHOULD BE CONSIDERED AS AN ENTRY POINT
	public String execute() throws Exception
	{
		return SUCCESS;
	}




}
