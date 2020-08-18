package com.newco.marketplace.web.action.wizard.pricing;

import com.newco.marketplace.web.action.base.SLWizardBaseAction;
import com.newco.marketplace.web.delegates.ISOWizardFetchDelegate;
import com.opensymphony.xwork2.Preparable;

public class SOWPricingPaymentAction extends SLWizardBaseAction implements Preparable
{
	private static final long serialVersionUID = 1L;

	
	public SOWPricingPaymentAction(ISOWizardFetchDelegate fetchDelegate ) {
		this.fetchDelegate = fetchDelegate;
	}
	public void prepare() throws Exception
	{
	}
	
	// AN ENTRY POINT, no execute()
	public String createEntryPoint() throws Exception {
	
		return SUCCESS;
	}
	


	public String createAndRoute() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	public String editEntryPoint() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String next() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String previous() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String saveAsDraft() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
