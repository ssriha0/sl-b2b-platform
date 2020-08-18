package com.newco.marketplace.web.action.details;

import org.apache.log4j.Logger;

import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.opensymphony.xwork2.Preparable;

public class SODetailsBidNotesAction extends SLDetailsBaseAction implements Preparable{
	
	private static final long serialVersionUID = 9065809023750187227L;
	private static final Logger logger = Logger.getLogger(SODetailsBidNotesAction.class.getName());
	private ISODetailsDelegate detailsDelegate;
	
	
	
	
	public SODetailsBidNotesAction()
	{
	}
	

	public void prepare() throws Exception {
		createCommonServiceOrderCriteria();
	}
	
	
	public String execute() throws Exception {
		return SUCCESS;
	}


	public ISODetailsDelegate getDetailsDelegate()
	{
		return detailsDelegate;
	}


	public void setDetailsDelegate(ISODetailsDelegate detailsDelegate)
	{
		this.detailsDelegate = detailsDelegate;
	}
	
	
	
}
