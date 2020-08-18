package com.newco.marketplace.web.action.wizard;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.web.action.base.SLBaseAction;

public class SOWizardSupportAction extends SLBaseAction  {
	
	public String execute() throws Exception
	{
		String displayTab=getParameter("displayTab");
		if(displayTab!=null) {
			getSession().setAttribute(Constants.SESSION.WORKFLOW_DISPLAY_TAB, displayTab);
		}
		//TODO
		if (getSession().getAttribute("displayTab") == null){
			getSession().setAttribute("displayTab", displayTab);
		}
		
		return SUCCESS;
	}
}
