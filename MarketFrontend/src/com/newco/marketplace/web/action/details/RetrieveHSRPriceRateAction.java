package com.newco.marketplace.web.action.details;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.action.buyer.BuyerEditPIIAction;
import com.servicelive.hsr.parts.services.IFetchHSRPriceRateService;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.hsr.parts.BuyerPartsPriceCalcValues;

public class RetrieveHSRPriceRateAction extends SLBaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(RetrieveHSRPriceRateAction.class.getName());
	IFetchHSRPriceRateService fetchHSRPriceRateService;
    private BuyerPartsPriceCalcValues buyerPartsPriceCalcValues ;
     
	public IFetchHSRPriceRateService getFetchHSRPriceRateService() {
		return fetchHSRPriceRateService;
	}

	public void setFetchHSRPriceRateService(
			IFetchHSRPriceRateService fetchHSRPriceRateService) {
		this.fetchHSRPriceRateService = fetchHSRPriceRateService;
	}

	public String getReimbureRate() throws Exception{
		
		String partCoverage = getRequest().getParameter("partCoverage");
		String partSource = getRequest().getParameter("partSource");
		System.out.println("The part coverage is"+partCoverage+ "And the part Source is "+partSource);
		buyerPartsPriceCalcValues=fetchHSRPriceRateService.fetchReimbursementRate(partCoverage, partSource);
		//SL-19820
        //getSession().setAttribute("reimbursementRate", buyerPartsPriceCalcValues.getReimbursementRate());
		setAttribute("reimbursementRate", buyerPartsPriceCalcValues.getReimbursementRate());
		return SUCCESS;
		
	}

	public BuyerPartsPriceCalcValues getBuyerPartsPriceCalcValues() {
		return buyerPartsPriceCalcValues;
	}

	public void setBuyerPartsPriceCalcValues(
			BuyerPartsPriceCalcValues buyerPartsPriceCalcValues) {
		this.buyerPartsPriceCalcValues = buyerPartsPriceCalcValues;
	}


}
