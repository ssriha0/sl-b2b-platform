package com.servicelive.routingrulesweb.action;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ModelDriven;
import com.servicelive.domain.routingrules.RoutingRuleAlert;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.detached.RoutingRuleQuickViewVO;
import com.servicelive.domain.spn.detached.LabelValueBean;
import com.servicelive.routingrulesengine.RoutingRulesConstants;
import com.servicelive.routingrulesengine.vo.RoutingRulesPaginationVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesSearchVO;

public class RoutingRulesSearchTabAction extends RoutingRulesBaseAction implements ModelDriven<Map<String,Object>> 
{
	
	private static final long serialVersionUID = -3043330491299299741L;
	
	private static final String HAS_ALERTS = "hasAlertsforSearch"; 
	private static final String LIST_ROUTING_RULE_HDR_SEARCH = "searchResultList";
	private Map<String,Object> model = new HashMap<String,Object>();
	protected final Logger log = Logger.getLogger(this.getClass());

	public String display() throws Exception 
	{
		// Handle Pagination
		RoutingRulesPaginationVO pagingCriteria = (RoutingRulesPaginationVO) getSession()
				.getAttribute("searchRulePagination");
		String pageNumber[] = (String[]) getRequest().getAttribute("pageNo");
		
		if (pagingCriteria != null) 
		{
			if (pageNumber != null) 
			{
				Integer pageNo = Integer.parseInt(pageNumber[0]);
				pagingCriteria.setPageRequested(pageNo);
			} else 
			{
				pagingCriteria = null;
			}
		}
		
		boolean archiveIndicator = false;
		// Handle Sorting Parameters
		String[] sortCol = (String[]) getRequest().getAttribute("sortCol");
		if (sortCol != null) 
		{
			pagingCriteria.setSortCol(Integer.parseInt(sortCol[0]));
		}
		String[] sortOrd = (String[]) getRequest().getAttribute("sortOrd");
		if (sortOrd != null) 
		{
			pagingCriteria.setSortOrd(Integer.parseInt(sortOrd[0]));
		}

		String[] archiveInd = (String[]) getRequest().getAttribute("archive");
		if (archiveInd != null && Integer.parseInt(archiveInd[0]) == 1) 
		{
			archiveIndicator = true;
			getRequest().setAttribute("archiveInd", 1);
			getRequest().removeAttribute("searchRulePagination");
		} 
		else 
		{
			archiveIndicator = false;
			getRequest().setAttribute("archiveInd", 0);
			getRequest().removeAttribute("searchRulePagination");
		}
		//logger.info("SortCol = " + pagingCriteria.getSortCol() + " SortOrd = " + pagingCriteria.getSortOrd());

		RoutingRulesSearchVO routingRulesSearchVo = new RoutingRulesSearchVO();
		HttpServletRequest request = getRequest();
		String value = request.getParameter("value");
		String column = request.getParameter("column");
		String archive = request.getParameter("archive");
		String exactSearch = request.getParameter("exactSearch");
		
		if(exactSearch != null){
			routingRulesSearchVo.setExactSearch(Boolean.valueOf(exactSearch));
		}

		if (archive != null) 
		{
			Integer aIndicator = Integer.parseInt(archive);
			if (aIndicator != null && aIndicator == 1) 
			{
				archiveIndicator = true;
			}
			else 
			{
				archiveIndicator = false;
			}
		}

		if (value != null && column != null) 
		{
			log.debug("RRSTA no search value and column");
			Integer searchColumn = Integer.parseInt(column);
			routingRulesSearchVo.setSearchColumn(searchColumn);
			Integer buyerId = getContextBuyerId();
			routingRulesSearchVo.setBuyerId(buyerId);
			switch (searchColumn) 
			{
			case RoutingRulesConstants.SEARCH_RULE_BY_FIRM_ID:
				BigInteger searchValue = BigInteger.valueOf(Long
						.parseLong(value));
				routingRulesSearchVo.setProviderFirmId(searchValue);
				routingRulesSearchVo.setNullSearchCriteria(RoutingRulesConstants.NULL_CRITERIA_PROVIDER_ID+RoutingRulesConstants.NULL_CRITERIA_DELIMITER+searchValue);
				break;
			case RoutingRulesConstants.SEARCH_RULE_BY_FIRM_NAME:
				routingRulesSearchVo.setProviderFirmName(value);
				routingRulesSearchVo.setNullSearchCriteria(RoutingRulesConstants.NULL_CRITERIA_PROVIDER_NAME+RoutingRulesConstants.NULL_CRITERIA_DELIMITER+value);
				break;
			case RoutingRulesConstants.SEARCH_RULE_BY_RULE_NAME:
				routingRulesSearchVo.setRuleName(value);
				routingRulesSearchVo.setNullSearchCriteria(RoutingRulesConstants.NULL_CRITERIA_RULE_NAME+RoutingRulesConstants.NULL_CRITERIA_DELIMITER+value);
				break;
			case RoutingRulesConstants.SEARCH_RULE_BY_UPLOADED_FILE_NAME:
				routingRulesSearchVo.setUploadedFileName(value);
				routingRulesSearchVo.setNullSearchCriteria(RoutingRulesConstants.NULL_CRITERIA_UPLOADED_FILE_NAME+RoutingRulesConstants.NULL_CRITERIA_DELIMITER+value);
				break;
			case RoutingRulesConstants.SEARCH_RULE_BY_RULE_ID:
				BigInteger ruleId = BigInteger.valueOf(Long
						.parseLong(value));
				routingRulesSearchVo.setRuleId(ruleId);
				routingRulesSearchVo.setNullSearchCriteria(RoutingRulesConstants.NULL_CRITERIA_RULE_ID+RoutingRulesConstants.NULL_CRITERIA_DELIMITER+ruleId);
				break;
			case RoutingRulesConstants.SEARCH_RULE_BY_PROCESS_ID:
				routingRulesSearchVo.setProcessId(value);
				routingRulesSearchVo.setNullSearchCriteria(RoutingRulesConstants.NULL_CRITERIA_PROCESS_ID+RoutingRulesConstants.NULL_CRITERIA_DELIMITER+value);
				break;
			case RoutingRulesConstants.SEARCH_RULE_BY_AUTO_ACCEPTANCE_STATUS:
				routingRulesSearchVo.setAutoAcceptSearchlabel(value);
				routingRulesSearchVo.setNullSearchCriteria(RoutingRulesConstants.NULL_CRITERIA_AUTOACCEPT_LABEL+RoutingRulesConstants.NULL_CRITERIA_DELIMITER+value);
				break;
			default:
				break;
			}
		} 
		else 
		{	
			log.debug("RRSTA search value and column");
			routingRulesSearchVo = (RoutingRulesSearchVO) getSession()
					.getAttribute("routingRuleSearchCriteria");
		}

		pagingCriteria = getRoutingRulesPaginationService()
				.setSearchResultPagination(routingRulesSearchVo,
						pagingCriteria, archiveIndicator);
		List<RoutingRuleHdr> rrHdrs = getRoutingRulesService()
				.getRoutingRulesHeaderAfterSearch(routingRulesSearchVo,
						pagingCriteria, archiveIndicator);
		
		model.put(LIST_ROUTING_RULE_HDR_SEARCH, rrHdrs);
		model.put("msgString", "Empty");
		getSession().setAttribute("searchRulePagination", pagingCriteria);
		getSession().setAttribute("routingRuleSearchCriteria",
				routingRulesSearchVo);
		getSession().setAttribute("archiveIndicator",archiveIndicator);

		if (rrHdrs != null) 
		{
			for (RoutingRuleHdr rule : rrHdrs) 
			{
				if (rule.getRoutingRuleAlerts() != null) 
				{
					for (RoutingRuleAlert alert : rule.getRoutingRuleAlerts()) 
					{
						if (RoutingRulesConstants.ROUTING_ALERT_STATUS_ACTIVE
								.equals(alert.getAlertStatus())) {
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
	
	public void setModel(Map<String,Object> model) 
	{
		this.model = model;
	}
	
	public Map<String,Object> getModel() 
	{		
		return model;
	}

}  
