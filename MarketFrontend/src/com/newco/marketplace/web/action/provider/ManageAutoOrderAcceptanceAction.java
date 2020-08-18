/**
 *
 */
package com.newco.marketplace.web.action.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.autoAcceptance.ManageAutoOrderAcceptanceDTO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.service.provider.manageautoacceptance.ManageAutoOrderAcceptanceService;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.detached.RoutingRuleAutoAcceptHistoryVO;
import com.servicelive.domain.routingrules.detached.RoutingRuleQuickViewVO;
import com.servicelive.domain.spn.detached.LabelValueBean;
import com.servicelive.routingrulesengine.services.RoutingRulesService;

/**
 * @author hoza
 *
 */

// @Validation
public class ManageAutoOrderAcceptanceAction extends SLBaseAction {

	private static final long serialVersionUID = 123509443231332602L;	
	Logger logger = Logger.getLogger(ManageAutoOrderAcceptanceAction.class);
	
	private ManageAutoOrderAcceptanceService manageAutoOrderAcceptanceService;
	private RoutingRulesService routingRulesService;
	private static final String PENDING_RULES = "pendingRules";	
	private static final String RULES_QUICK_VIEW = "ruleQuickView";	
	private static final String PROVIDER_HISTORY = "autoAcceptHistForProv";
	private static final String ROUTING_RULE_ID = "routingRuleId";
	private static final String CAR_BUYERS = "carBuyers";
	private static final String RULE_LIST = "ruleList";
	private static final String CAR_BUYER = "carBuyer";
	private static final String RULE_COUNT = "ruleCount";
	private ManageAutoOrderAcceptanceDTO model = new ManageAutoOrderAcceptanceDTO();
	
	//display manage auto acceptance provider dash-board
	public String execute() throws Exception {	
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute("SecurityContext");
		Integer vendorId = soContxt.getCompanyId();
		Map<String,String> carBuyer = new HashMap<String, String>();
		
		try{
			//fetch the buyers having CAR enabled
			Map<String,String> carBuyers = manageAutoOrderAcceptanceService.fetchCARBuyers(vendorId);
			getRequest().setAttribute(CAR_BUYERS,carBuyers);	
			
			//setting buyer name & id
			carBuyer.put(carBuyers.entrySet().iterator().next().getKey(), carBuyers.entrySet().iterator().next().getValue());
			Integer buyerId = Integer.parseInt(carBuyers.entrySet().iterator().next().getKey());
			getRequest().setAttribute(CAR_BUYER, carBuyer);
			
			//fetch the CAR rules for the buyer
			List<ManageAutoOrderAcceptanceDTO> ruleList = manageAutoOrderAcceptanceService.fetchRulesForBuyer(buyerId, vendorId);
			if(null != ruleList){
				getRequest().setAttribute(RULE_LIST,ruleList);
				getRequest().setAttribute(RULE_COUNT,ruleList.size());
			}			
		}
		catch(BusinessServiceException e){
			logger.error("Exception in ManageAutoOrderAcceptanceAction execute() due to "+ e.getMessage());
		}
		return SUCCESS;
	}
	
	//fetches pending car rules for provider notification
	public String fetchCARRulesForProvider() throws Exception {
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute("SecurityContext");
		Integer vendorId = soContxt.getCompanyId();
		try{
			//fetch new & updated active pending CAR rules
			List<ManageAutoOrderAcceptanceDTO> pendingRules = manageAutoOrderAcceptanceService.fetchPendingCARRuleList(vendorId);
			getRequest().setAttribute(PENDING_RULES,pendingRules);			
		}
		catch(BusinessServiceException e){
			logger.error("Exception in ManageAutoOrderAcceptanceAction fetchCARRulesForProvider() due to "+ e.getMessage());
		}
		return SUCCESS;
	}
	
	//updates the auto acceptance status of CAR rules
	public String saveRules()throws Exception {
		ManageAutoOrderAcceptanceDTO acceptanceDTO = getModel();
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute("SecurityContext");
		acceptanceDTO.setVendorId(soContxt.getCompanyId());
		acceptanceDTO.setUserName(soContxt.getUsername());	
		acceptanceDTO.setResourceId(soContxt.getVendBuyerResId());
		
		//if adopted, set adoptedBy as SLAdmin name
		if(soContxt.isAdopted()){
			acceptanceDTO.setAdoptedBy(soContxt.getSlAdminFName()+" "+soContxt.getSlAdminLName());
		}
		try{
			//save the rule status			
			manageAutoOrderAcceptanceService.saveRules(acceptanceDTO);
		}
		catch(BusinessServiceException e){
			logger.error("Exception in ManageAutoOrderAcceptanceAction saveRules() due to "+ e.getMessage());
		}	
		return SUCCESS;
	}
	
	//display CAR rule details & history
	public String displayQuickView()throws Exception {
		Integer ruleHdrId = Integer.parseInt(getRequest().getParameter("id"));	
		try{
			//display the rule details for quick view
			List<LabelValueBean> markets = routingRulesService.getMarkets();
			RoutingRuleHdr rule = routingRulesService.getRoutingRulesHeader(ruleHdrId);
			RoutingRuleQuickViewVO ruleQuickView = routingRulesService.setQuickViewforRule(rule,markets);
			getRequest().setAttribute(RULES_QUICK_VIEW, ruleQuickView);
			
			//display auto acceptance history for provider
			SecurityContext soContxt = (SecurityContext) getSession().getAttribute("SecurityContext");
			Integer vendorId = soContxt.getCompanyId();
			List<RoutingRuleAutoAcceptHistoryVO> autoAcceptHistoryList = routingRulesService.getAutoAcceptHistoryProvider(ruleHdrId, vendorId);
			getRequest().setAttribute(ROUTING_RULE_ID, ruleHdrId);
			getRequest().setAttribute(PROVIDER_HISTORY, autoAcceptHistoryList);
			
		}
		catch(Exception e){
			logger.error("Exception in ManageAutoOrderAcceptanceAction displayQuickView() due to "+ e.getMessage());
		}	
		return SUCCESS;
	}
	
	//updates the auto acceptance status of CAR rules from auto acceptance dash-board
	public String saveAndFetchRules()throws Exception {
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute("SecurityContext");
		Integer vendorId = soContxt.getCompanyId();
		
		ManageAutoOrderAcceptanceDTO acceptanceDTO = getModel();
		acceptanceDTO.setVendorId(vendorId);
		acceptanceDTO.setUserName(soContxt.getUsername());
		acceptanceDTO.setResourceId(soContxt.getVendBuyerResId());
		Integer buyerId = acceptanceDTO.getBuyerId();
		//if adopted, set adoptedBy as SLAdmin name
		if(soContxt.isAdopted()){
			acceptanceDTO.setAdoptedBy(soContxt.getSlAdminFName()+" "+soContxt.getSlAdminLName());
		}
		try{
			//saving the rule status			
			manageAutoOrderAcceptanceService.saveRules(acceptanceDTO);
			
			//fetching the buyers
			Map<String,String> carBuyers = manageAutoOrderAcceptanceService.fetchCARBuyers(vendorId);
			getRequest().setAttribute(CAR_BUYERS,carBuyers);			
			
			//setting buyer id & name
			Map<Integer,String> carBuyer = new HashMap<Integer, String>();
			carBuyer.put(acceptanceDTO.getBuyerId(), acceptanceDTO.getBuyer());
			getRequest().setAttribute(CAR_BUYER,carBuyer);
			
			//fetching the saved car rules
			List<ManageAutoOrderAcceptanceDTO> ruleList = manageAutoOrderAcceptanceService.fetchRulesForBuyer(buyerId, vendorId);
			if(null != ruleList){
				getRequest().setAttribute(RULE_LIST,ruleList);
				getRequest().setAttribute(RULE_COUNT,ruleList.size());
			}	
			
		}
		catch(BusinessServiceException e){
			logger.error("Exception in ManageAutoOrderAcceptanceAction saveAndFetchRules() due to "+ e.getMessage());
		}	
		return SUCCESS;
	}
	
	//display car rules for buyer
	public String loadDetails() throws Exception {		
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute("SecurityContext");
		Integer vendorId = soContxt.getCompanyId();
		Map<String,String> carBuyer = new HashMap<String, String>();
		
		//get buyerId and buyerName
		String id = getRequest().getParameter("id");
		String name = getRequest().getParameter("name");
		try{
			//setting buyer name & id
			if(null != id && StringUtils.isNotBlank(id) && null != name && StringUtils.isNotBlank(name)){
				carBuyer.put(id, name);
				Integer buyerId = Integer.parseInt(id);
				getRequest().setAttribute(CAR_BUYER,carBuyer);
				//fetch the CAR rules for the buyer
				List<ManageAutoOrderAcceptanceDTO> ruleList = manageAutoOrderAcceptanceService.fetchRulesForBuyer(buyerId, vendorId);
				if(null != ruleList){
					getRequest().setAttribute(RULE_LIST,ruleList);
					getRequest().setAttribute(RULE_COUNT,ruleList.size());
				}
			}
		}
		catch(BusinessServiceException e){
			logger.error("Exception in ManageAutoOrderAcceptanceAction execute() due to "+ e.getMessage());
		}
		return SUCCESS;
	}
	

	public ManageAutoOrderAcceptanceService getManageAutoOrderAcceptanceService() {
		return manageAutoOrderAcceptanceService;
	}

	public void setManageAutoOrderAcceptanceService(
			ManageAutoOrderAcceptanceService manageAutoOrderAcceptanceService) {
		this.manageAutoOrderAcceptanceService = manageAutoOrderAcceptanceService;
	}

	public ManageAutoOrderAcceptanceDTO getModel() {
		return model;
	}

	public void setModel(ManageAutoOrderAcceptanceDTO model) {
		this.model = model;
	}

	public RoutingRulesService getRoutingRulesService() {
		return routingRulesService;
	}

	public void setRoutingRulesService(RoutingRulesService routingRulesService) {
		this.routingRulesService = routingRulesService;
	}


}
