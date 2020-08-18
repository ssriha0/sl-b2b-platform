package com.newco.marketplace.web.action.homepage;

import com.newco.marketplace.web.action.base.SLSimpleBaseAction;
import com.newco.marketplace.web.security.NonSecurePage;
import com.opensymphony.xwork2.Preparable;

@NonSecurePage
public class ExamplesAction extends SLSimpleBaseAction implements Preparable
{
	private static final long serialVersionUID = 1L;

	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();
	}	
	
	public ExamplesAction()
	{		
	}
	
	public String displayPage() throws Exception
	{
		
		return SUCCESS;
	}
}
