package com.servicelive.routingrulesweb.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import com.servicelive.domain.routingrules.RoutingRuleAlert;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.AutoAcceptHistory;
import com.servicelive.domain.routingrules.detached.RoutingRuleQuickViewVO;
import com.servicelive.domain.routingrules.detached.RoutingRuleAutoAcceptHistoryVO;
import com.servicelive.domain.spn.detached.LabelValueBean;
import com.servicelive.routingrulesengine.RoutingRulesConstants;
import com.servicelive.routingrulesengine.vo.RoutingRuleErrorVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesPaginationVO;

// @NonSecurePage
public class RoutingRulesManageTabAction 
	extends RoutingRulesBaseAction	 
	implements ModelDriven<Map<String,Object>>
{
	Logger logger = Logger.getLogger(RoutingRulesManageTabAction.class);
	
	private static final long serialVersionUID = 0L;

	private static final String HAS_ALERTS = "hasAlerts";

	//private static final String HAS_EDIT_PERMS = "hasEditPerms";
	private static final String LIST_ROUTING_RULE_HDR = "listRoutingRuleHdr";
	private Map<String,Object> model= new HashMap<String,Object>();

	
	public String displayError ()
	throws Exception
	{
		String ruleHdrId = getRequest().getParameter("id");
		List<RoutingRuleErrorVO> ruleErrorlist = getRoutingRulesService()
				.getRoutingRuleErrors(Integer.parseInt(ruleHdrId));
		getRequest().setAttribute("ruleErrorlist", ruleErrorlist);
		getRequest().setAttribute("routingRuleId", ruleHdrId);
		for (RoutingRuleErrorVO ruleError : ruleErrorlist)
		{
			if(ruleError.getErrorType().equalsIgnoreCase(RoutingRulesConstants.NON_PERSISTENT_ERROR))
			{
				getRequest().setAttribute("removeAlert",Boolean.TRUE);
				break;
			}
		}
		return "success";
		
	}
	
	public String displayQuickView ()
	throws Exception
	{
		String ruleHdrId = getRequest().getParameter("id");
		logger.info("Before QuickView"+System.currentTimeMillis());
		List<LabelValueBean> markets= getRoutingRulesService().getMarkets();
		RoutingRuleHdr rule=getRoutingRulesService().getRoutingRulesHeader(Integer.parseInt(ruleHdrId));
		RoutingRuleQuickViewVO ruleQuickView=getRoutingRulesService().setQuickViewforRule(rule,markets);
		//rule.setRuleQuickView(ruleQuickView);
		logger.info("After QuickView"+System.currentTimeMillis());
		getRequest().setAttribute("ruleQuickView", ruleQuickView);
		return "success";
	}
	
	//SL 15642 method to get history of particular rule for buyer  
	public String displayAutoAcceptHistoryForBuyer()
	{
		String ruleHdrId = getRequest().getParameter("id");
		List<RoutingRuleAutoAcceptHistoryVO> autoAcceptHistoryList=new ArrayList<RoutingRuleAutoAcceptHistoryVO>();
		autoAcceptHistoryList=getRoutingRulesService().getAutoAcceptHistoryBuyer(Integer.parseInt(ruleHdrId));
		if(autoAcceptHistoryList!=null)
		{
				getSession().setAttribute("autoAcceptHistoryListForBuyer", autoAcceptHistoryList);
		}
		return SUCCESS;
		
	}
	public String display ()
		throws Exception
	{	RoutingRulesPaginationVO pagingCriteria=null;
		// Handle Pagination Parameters
		String pageNumber[]=(String[])getRequest().getAttribute("pageNo");
		pagingCriteria=(RoutingRulesPaginationVO)getSession().getAttribute("pagingCriteria");
		if(pagingCriteria!=null){
			if (pageNumber != null) {
				  Integer pageNo=Integer.parseInt(pageNumber[0]);
				  pagingCriteria.setPageRequested(pageNo);
			  } 
		}

		// Handle Sorting Parameters
		String[] sortCol = (String[])getRequest().getAttribute("sortCol");
		if (sortCol != null) {
			pagingCriteria.setSortCol(Integer.parseInt(sortCol[0]));
		}
		String[] sortOrd = (String[])getRequest().getAttribute("sortOrd");
		if (sortOrd != null) {
			pagingCriteria.setSortOrd(Integer.parseInt(sortOrd[0]));
		}
		//logger.info("SortCol = " + pagingCriteria.getSortCol() + " SortOrd = " + pagingCriteria.getSortOrd());
		
		Integer buyerId = getContextBuyerId();	
		
		pagingCriteria=getRoutingRulesPaginationService().setManageRulePagination(buyerId, pagingCriteria);
		

		
		List<RoutingRuleHdr> rrHdrs = getRoutingRulesService().getRoutingRulesHeaderList(buyerId,pagingCriteria);

		getSession().setAttribute("pagingCriteria", pagingCriteria);

		//SL-20363 Need to Add UI for Forceful CAR Activation for buyer# 3000
		if ((((Boolean) ActionContext.getContext().getSession().get("System_Property_ForceActiveInd")) 
				&& ((Boolean) ActionContext.getContext().getSession().get("carFeatureOn")) 
				&& getSecurityContext().getRoles().isPermissionForForcefulCarActivation()) 
				&& (null != rrHdrs && rrHdrs.size() > 0)) {
			     for (RoutingRuleHdr routingRuleHdr : rrHdrs) {
				   if ((null != routingRuleHdr.getRuleStatus())
						&& (routingRuleHdr.getRuleStatus().equals("INACTIVE"))
						&& (null != routingRuleHdr.getRoutingRuleError())
						&& (routingRuleHdr.getRoutingRuleError().size() > 0)) {
					routingRuleHdr.setForceActiveInd(Boolean.TRUE);
					logger
							.info("Force Active indicator(forceActiveInd) is true for rule id: "
									+ routingRuleHdr.getRoutingRuleHdrId());
				}
			}
		}
		
		if(getSecurityContext().getRoles().isPermissionForForcefulCarActivation())
			ActionContext.getContext().getSession().put("UserForcefulCARActPermission",Boolean.TRUE);
		else
			ActionContext.getContext().getSession().put("UserForcefulCARActPermission",Boolean.FALSE);
		
		// End of Force Active CAR		
		
		
		
		model.put(LIST_ROUTING_RULE_HDR, rrHdrs);
		
		model.put("msgString", "Empty");

		if (rrHdrs != null) {
			for (RoutingRuleHdr rule : rrHdrs) {
				
				if (rule.getRoutingRuleAlerts() != null) {
					for (RoutingRuleAlert alert : rule.getRoutingRuleAlerts()) {
						if (RoutingRulesConstants.ROUTING_ALERT_STATUS_ACTIVE.equals(alert.getAlertStatus())) {
							model.put(HAS_ALERTS, Boolean.TRUE);
							return SUCCESS;
						}
					}
				}
				
			}
		}
		
		//Start SL 15642 To get email id from db if buyer has already saved the email id
				String autoAccpetStatus=(String)getSession().getAttribute("autoAcceptBuyerAdmin");
				if(null!=autoAccpetStatus && autoAccpetStatus.equalsIgnoreCase("true"))
				{
						String savedEmailId=getRoutingRulesService().fetchSavedEmailId(buyerId);
						if(null!=savedEmailId)
						{
							model.put("emailRequiredInd","1");
							model.put("buyerSavedEmailId", savedEmailId);
						}
						else
						{
							model.put("emailRequiredInd","0");
						}
				}
				//End 

		model.put(HAS_ALERTS, Boolean.FALSE);
		return SUCCESS;
	}
	
	//SL-15642 Method to save the email id for buyer to get mail on change in auto acceptance status
	public String configureBuyerEmailId()
	{ 
		//Email notification required status 
		String indEmailNotifyRequired=getRequest().getParameter("indEmailNotifyRequired");
    	Integer emailNotifyRequiredInd=Integer.parseInt(indEmailNotifyRequired); 
    	//Entered email id
    	String manageRuleBuyerEmailId=getRequest().getParameter("manageRuleBuyerEmailId");
    	Integer buyerId = getContextBuyerId();
    
    	try{
    		getRoutingRulesService().saveManageRuleBuyerEmailId(emailNotifyRequiredInd,manageRuleBuyerEmailId,buyerId);
    	}
    	catch(Exception e) {
			 logger.info("Exception in getRoutingRulesBuyer:"+e.getMessage());
			 e.printStackTrace();
			 
		 }
    	return SUCCESS;
	}
	public void setModel(Map<String,Object> model) {
		this.model = model;
	}
	
	public Map<String,Object> getModel() {		
		return model;
	}
		
}
