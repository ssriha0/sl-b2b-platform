package com.newco.marketplace.web.action.details;

import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.ServiceOrderDTO;
import com.opensymphony.xwork2.Preparable;

public class SODetailsRateBuyerAction extends SLDetailsBaseAction implements Preparable {
	
	private static final long serialVersionUID = 10002;// arbitrary number to get rid
												// of warning

	public SODetailsRateBuyerAction(ISODetailsDelegate delegate) {
		this.detailsDelegate = delegate;
	}

	/**
	 * Description of the Method
	 * 
	 * @exception Exception
	 *                Description of the Exception
	 */
	public void prepare() throws Exception {
		
		createCommonServiceOrderCriteria();
	}

	public String execute() throws Exception {
		
		return SUCCESS;
	}

	public ServiceOrderDTO getModel() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
