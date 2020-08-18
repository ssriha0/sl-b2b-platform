package com.servicelive.routingrulesweb.action;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import com.servicelive.domain.routingrules.RoutingRuleAlert;
import com.servicelive.domain.routingrules.RoutingRuleBuyerAssoc;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.detached.RoutingRuleQuickViewVO;
import com.servicelive.domain.spn.detached.LabelValueBean;
import com.servicelive.routingrulesengine.RoutingRulesConstants;
import com.servicelive.routingrulesengine.vo.RoutingRulesPaginationVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesSearchVO;


// @NonSecurePage
public class RoutingRulesDashboardAction extends RoutingRulesBaseAction	 
	implements ModelDriven<Map<String,Object>>
{
	Logger logger = Logger.getLogger(RoutingRulesDashboardAction.class);

	private static final String HAS_ALERTS = "hasAlerts";

	//private static final String HAS_EDIT_PERMS = "hasEditPerms";
	private static final String LIST_ROUTING_RULE_HDR = "listRoutingRuleHdr";
	private static final String LIST_ROUTING_RULE_HDR_SEARCH = "searchResultList";
	private static final long serialVersionUID = -3043330491299299741L;
	private Map<String,Object> model= new HashMap<String,Object>();

	public String decision () throws Exception {
		HttpServletRequest request = getRequest();
		String tabType= request.getParameter("tabType");
		if(tabType.equals("searchTab")){
			String result = displaySearch();
			return result;
		}else{
 			String result = display();
			return result;
		}
	}
	
	public String display ()
		throws Exception
	{	RoutingRulesPaginationVO pagingCriteria=null;
		pagingCriteria=(RoutingRulesPaginationVO)getSession().getAttribute("pagingCriteria");
		Integer buyerId = getContextBuyerId();
		pagingCriteria=getRoutingRulesPaginationService().setManageRulePagination(buyerId, pagingCriteria);

		pagingCriteria.setSortCol(0);
		pagingCriteria.setSortOrd(0);

		List<RoutingRuleHdr> rrHdrs = getRoutingRulesService().getRoutingRulesHeaderList(buyerId,pagingCriteria);

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
		if(getSecurityContext().getRoles().isPermissionForForcefulCarActivation())// Only to show seperate col...
			ActionContext.getContext().getSession().put("UserForcefulCARActPermission",Boolean.TRUE);
		else
			ActionContext.getContext().getSession().put("UserForcefulCARActPermission",Boolean.FALSE);
		// End of Force Active CAR
		
		
		
		//Start SL 15642 To get email id from db if buyer has already saved the email id
		String autoAccpetStatus=(String)getSession().getAttribute("autoAcceptBuyerAdmin");
		if(autoAccpetStatus!=null && autoAccpetStatus.equalsIgnoreCase("true"))
		{
				String savedEmailId=getRoutingRulesService().fetchSavedEmailId(buyerId);
				if(savedEmailId!=null)
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
		
		getSession().setAttribute("pagingCriteria", pagingCriteria);
		String msgString =(String)getSession().getAttribute("msgString");
		if(msgString!=null){
		model.put("msgString", msgString);
		getSession().removeAttribute("msgString");
		}else{
	    model.put("msgString", "Empty");
		}
		

		model.put(LIST_ROUTING_RULE_HDR, rrHdrs);
		model.put("tabToDisplay", "manageRules");

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

		model.put(HAS_ALERTS, Boolean.FALSE);
		return SUCCESS;
	}
	
	public String displaySearch ()
	throws Exception
    {	
        //getSession().setAttribute("tabType", "searchTab");
		
		// Handle Pagination
		RoutingRulesPaginationVO pagingCriteria = (RoutingRulesPaginationVO)getSession().getAttribute("searchRulePagination");
		String pageNumber[]=(String[])getRequest().getAttribute("pageNo");
		if(!((getSession().getAttribute("searchSession")!=null)&&(getSession().getAttribute("searchSession").equals("true"))))
		{
			if (pagingCriteria != null) {
				if (pageNumber != null) {
					Integer pageNo = Integer.parseInt(pageNumber[0]);
					pagingCriteria.setPageRequested(pageNo);
				} else {
					pagingCriteria=null;
				}
			}
		}
		else if(pagingCriteria != null)
		{
			pagingCriteria.setSortCol(0);
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
		
		RoutingRulesSearchVO routingRulesSearchVo = new RoutingRulesSearchVO();
		HttpServletRequest request = getRequest();
        String value = request.getParameter("value");
        String column = (String)request.getParameter("column");
        Boolean archInd = false;
        if(getSession().getAttribute("archiveIndicator")!=null)
        {
        	archInd = (Boolean)getSession().getAttribute("archiveIndicator");
        }
        if(archInd==true){
        	getRequest().setAttribute("archiveInd", 1);
        }else if (archInd==false){
        	getRequest().setAttribute("archiveInd", 0);
        }
        
        if (value != null && column != null) {
        	Integer searchColumn = Integer.parseInt(column);
        	routingRulesSearchVo.setSearchColumn(searchColumn);
			Integer buyerId = getContextBuyerId();
			routingRulesSearchVo.setBuyerId(buyerId);
			switch(searchColumn) {
			  case 1:
				BigInteger searchValue  = BigInteger.valueOf(Long.parseLong(value));
				routingRulesSearchVo.setProviderFirmId(searchValue);
				break;
			  case 2:
			     routingRulesSearchVo.setProviderFirmName(value);
				 break;
			  case 3:
				 routingRulesSearchVo.setRuleName(value);
				 break;
			  default:
			     break;
			}
		} else {
			routingRulesSearchVo = (RoutingRulesSearchVO)getSession().getAttribute("routingRuleSearchCriteria");
		}

        pagingCriteria=getRoutingRulesPaginationService().setSearchResultPagination(routingRulesSearchVo, pagingCriteria,archInd);
        List<RoutingRuleHdr> rrHdrs = getRoutingRulesService().getRoutingRulesHeaderAfterSearch(routingRulesSearchVo,pagingCriteria,archInd);
        String msgString1 =(String)getSession().getAttribute("msgString1");
        if(msgString1==null||msgString1.equals("")){
        	model.put("msgString1", "Empty");
        }else{
        	model.put("msgString1", msgString1);
        	getSession().removeAttribute("msgString1");
        }	
		getSession().setAttribute("searchRulePagination", pagingCriteria);
		getSession().setAttribute("routingRuleSearchCriteria", routingRulesSearchVo);
		if (rrHdrs != null) {
			List<LabelValueBean> markets= getRoutingRulesService().getMarkets();
			for (RoutingRuleHdr rule : rrHdrs) {
				
				RoutingRuleQuickViewVO ruleQuickView=getRoutingRulesService().setQuickViewforRule(rule,markets);
				
				rule.setRuleQuickView(ruleQuickView);
			}
		}
		model.put(LIST_ROUTING_RULE_HDR_SEARCH, rrHdrs);
    	model.put("tabToDisplay", "searchRules");
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
		
		model.put(HAS_ALERTS, Boolean.FALSE);
		return SUCCESS;
    } 




	public void setModel(Map<String,Object> model) {
		this.model = model;
	}
	
	public Map<String,Object> getModel() {		
		return model;
	}
	
}
