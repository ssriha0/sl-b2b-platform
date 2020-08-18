package com.newco.marketplace.web.action.whatis;

import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.security.NonSecurePage;

@NonSecurePage
public class SOWhatIsSLAction extends SLBaseAction
{	
	private static final long serialVersionUID = 1L;//arbitrary number to get rid of warning
	
	public void prepare() throws Exception
	{
	}

	
	public String execute() throws Exception
	{
		return SUCCESS;
	}	


	public String aboutSL() throws Exception
	{
		return "aboutSL";
	}	
	public String companyNews() throws Exception
	{
		return "companyNews";
	}	
	public String services() throws Exception
	{
		return "services";
	}	
	public String whatIsSL() throws Exception
	{
		return "whatIsSL";
	}	
	
		
}
