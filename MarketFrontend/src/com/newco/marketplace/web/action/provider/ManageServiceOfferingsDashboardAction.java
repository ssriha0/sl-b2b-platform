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
import com.newco.marketplace.dto.vo.serviceOfferings.AvailabilityVO;
import com.newco.marketplace.dto.vo.serviceOfferings.ManageServiceOfferingsDTO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.service.provider.manageautoacceptance.ManageAutoOrderAcceptanceService;
import com.newco.marketplace.service.provider.serviceofferings.ManageServiceOfferingsService;
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
public class ManageServiceOfferingsDashboardAction extends SLBaseAction {

	private static final long serialVersionUID = 123509443231332602L;	
	Logger logger = Logger.getLogger(ManageServiceOfferingsDashboardAction.class);
	
	private ManageServiceOfferingsService manageServiceOfferingsService;
	
	private static final String PENDING_RULES = "pendingRules";	
	private static final String RULES_QUICK_VIEW = "ruleQuickView";	
	private static final String PROVIDER_HISTORY = "autoAcceptHistForProv";
	private static final String ROUTING_RULE_ID = "routingRuleId";
	private static final String CAR_BUYERS = "carBuyers";
	private static final String SERVICE_OFFERING_LIST = "serviceOfferingList";
	private static final String CAR_BUYER = "carBuyer";
	private static final String RULE_COUNT = "ruleCount";
	private ManageServiceOfferingsDTO model = new ManageServiceOfferingsDTO();
	
	//display manage service offering dash-board
	public String execute() throws Exception {	
		SecurityContext soContxt = (SecurityContext) getSession().getAttribute("SecurityContext");
		Integer vendorId = soContxt.getCompanyId();
		Map<String,String> carBuyer = new HashMap<String, String>();
		
		try{
			List<ManageServiceOfferingsDTO> serviceOfferingList = manageServiceOfferingsService.fetchServiceOfferingList(vendorId);
			List<AvailabilityVO> availabilityList=manageServiceOfferingsService.fetchServiceOfferingAvailability(vendorId);

			if(null != serviceOfferingList){
				getRequest().setAttribute(SERVICE_OFFERING_LIST,serviceOfferingList);
				//getRequest().setAttribute(RULE_COUNT,ruleList.size());
			}			
		}
		catch(Exception e){
			logger.error("Exception in ManageAutoOrderAcceptanceAction execute() due to "+ e.getMessage());
		}
		return SUCCESS;
	}
	

	public ManageServiceOfferingsService getManageServiceOfferingsService() {
		return manageServiceOfferingsService;
	}

	public void setManageServiceOfferingsService(
			ManageServiceOfferingsService manageServiceOfferingsService) {
		this.manageServiceOfferingsService = manageServiceOfferingsService;
	}

	public ManageServiceOfferingsDTO getModel() {
		return model;
	}

	public void setModel(ManageServiceOfferingsDTO model) {
		this.model = model;
	}
	
	


}
