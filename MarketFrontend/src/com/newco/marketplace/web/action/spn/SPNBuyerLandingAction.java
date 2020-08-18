package com.newco.marketplace.web.action.spn;

import java.util.List;

import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.action.details.SODetailsSessionCleanup;
import com.newco.marketplace.web.delegates.ISPNBuyerDelegate;
import com.newco.marketplace.web.dto.SPNLandingTableRowDTO;
import com.newco.marketplace.web.dto.ServiceOrdersCriteria;
import com.opensymphony.xwork2.Preparable;


public class SPNBuyerLandingAction extends SLBaseAction implements Preparable 
{

	private static final long serialVersionUID = -2389925564397223643L;
	private ISPNBuyerDelegate buyerSPNDelegate;
	
	public SPNBuyerLandingAction(ISPNBuyerDelegate buyerSPNDelegate){
		this.buyerSPNDelegate = buyerSPNDelegate;
		
	}
	
	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();
		
		getSession().removeAttribute("spnBuilderFormInfo");
	}	

	public String displayPage() throws Exception
	{
		
	    ServiceOrdersCriteria criteria = this.get_commonCriteria();
	    SODetailsSessionCleanup.getInstance().cleanUpSession();
		List<SPNLandingTableRowDTO> buyerSPNList = buyerSPNDelegate.getNetworkList(criteria.getCompanyId());
		setAttribute("buyerSPNList", buyerSPNList);

		//setAttribute("networks", networks);
		
		return SUCCESS;
	}
	
	public String createNewNetwork() throws Exception
	{
		return "create_new_network";
	}
		
}
