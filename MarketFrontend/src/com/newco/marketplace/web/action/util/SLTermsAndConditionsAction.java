package com.newco.marketplace.web.action.util;

import com.newco.marketplace.dto.vo.TermsAndConditionsVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.ILookupDelegate;

public class SLTermsAndConditionsAction extends SLBaseAction
{

	private static final long serialVersionUID = 1L;
	private ILookupDelegate lookupDelegate = null;
	
	
	public SLTermsAndConditionsAction()
	{
	}
	
	public String displayBuyerAgreement() throws Exception
	{
		TermsAndConditionsVO vo = lookupDelegate.getTermsConditionsContent(OrderConstants.TERMS_CONDITIONS_BUYER_AGREEMENT);
		setAttribute("text", vo.getTermsCondContent());
		setAttribute("pageHeader", "Buyer Agreement");	
		String paramTermsandCond = getParameter("paramTermsandCond");
		if (null!=paramTermsandCond && paramTermsandCond.equals("true")) {
			return "newWindow";
		}
		else{
		return SUCCESS;
		}
	}

	public String displayProviderAgreement() throws Exception
	{
		TermsAndConditionsVO vo = lookupDelegate.getTermsConditionsContent("Provider Agreement");
		setAttribute("text", vo.getTermsCondContent());
		setAttribute("pageHeader", "Provider Agreement");
		return SUCCESS;
	}
	
	//SL - 19293 Showing SL Leads Addendum in footer
	public String displaySLLeadsAddendum() throws Exception
	{
		TermsAndConditionsVO vo = lookupDelegate.getTermsConditionsContent(OrderConstants.SL_LEADS_ADDENDUM_NEW_TANDC);
		setAttribute("text", vo.getTermsCondContent());
		setAttribute("pageHeader", "SL Leads Addendum");
		return SUCCESS;
	}
	
	public String displayTermsOfUse() throws Exception
	{		
		TermsAndConditionsVO vo = lookupDelegate.getTermsConditionsContent(OrderConstants.TERMS_CONDITIONS_BUYER_AGREEMENT);
		setAttribute("text", vo.getTermsCondContent());
		setAttribute("pageHeader", "Terms of Use");		
		return SUCCESS;
	}
	
	public String displayBucksAgreement() throws Exception
	{		
		TermsAndConditionsVO vo = lookupDelegate.getTermsConditionsContent(OrderConstants.SERVICE_BUCKS);
		setAttribute("text", vo.getTermsCondContent());
		setAttribute("pageHeader", "ServiceLive Bucks");		
		return SUCCESS;
	}
	
	public String displayPrivacyPolicy() throws Exception
	{		
		TermsAndConditionsVO vo = lookupDelegate.getTermsConditionsContent(OrderConstants.TERMS_CONDITIONS_BUYER_AGREEMENT);
		setAttribute("text", vo.getTermsCondContent());
		setAttribute("pageHeader", "Privacy Policy");		
		return SUCCESS;
	}

	public ILookupDelegate getLookupDelegate() {
		return lookupDelegate;
	}

	public void setLookupDelegate(ILookupDelegate lookupDelegate) {
		this.lookupDelegate = lookupDelegate;
	}
	
}
