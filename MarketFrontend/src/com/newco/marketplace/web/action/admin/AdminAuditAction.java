package com.newco.marketplace.web.action.admin;

import com.newco.marketplace.web.action.base.SLBaseAction;
import com.opensymphony.xwork2.Preparable;

public class AdminAuditAction extends SLBaseAction implements Preparable
{
	private static final long serialVersionUID = 1L;
	
	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();	
	}	

	
	// EXECUTE METHODS SHOULD BE CONSIDERED AS AN ENTRY POINT 
	public String execute() throws Exception
	{
		return SUCCESS;
	}
	
	
	
	
}
