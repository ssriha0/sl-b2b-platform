package com.servicelive.routingrulesengine.services.impl;

import static com.servicelive.routingrulesengine.RoutingRulesConstants.MODIFIED_BY;
import static com.servicelive.routingrulesengine.RoutingRulesConstants.PROVIDER_FIRM_SEARS_APPROVED;
import static com.servicelive.routingrulesengine.RoutingRulesConstants.PROVIDER_FIRM_SERVICE_LIVE_APPROVED;
import static com.servicelive.routingrulesengine.RoutingRulesConstants.PROVIDER_MARKET_READY;
import static com.servicelive.routingrulesengine.RoutingRulesConstants.ROUTING_ALERT_PRICING_ID;
import static com.servicelive.routingrulesengine.RoutingRulesConstants.ROUTING_ALERT_PROVIDER_STATUS_ID;
import static com.servicelive.routingrulesengine.RoutingRulesConstants.ROUTING_ALERT_STATUS_ACTIVE;
import static com.servicelive.routingrulesengine.RoutingRulesConstants.ROUTING_ALERT_STATUS_INACTIVE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.lookup.LookupRoutingAlertType;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.domain.routingrules.RoutingRuleAlert;
import com.servicelive.domain.routingrules.RoutingRuleBuyerAssoc;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.RoutingRulePrice;
import com.servicelive.domain.routingrules.RoutingRuleVendor;
import com.servicelive.domain.routingrules.detached.RoutingRuleEmailVO;
import com.servicelive.routingrulesengine.RoutingRulesConstants;
import com.servicelive.routingrulesengine.dao.RoutingRuleAlertDao;
import com.servicelive.routingrulesengine.dao.RoutingRuleHdrDao;
import com.servicelive.routingrulesengine.dao.ServiceProviderDao;
import com.servicelive.routingrulesengine.services.RoutingRuleAlertService;

public class RoutingRuleAlertServiceImpl implements RoutingRuleAlertService {

	private RoutingRuleHdrDao routingRuleHdrDao;
	private RoutingRuleAlertDao routingRuleAlertDao;
	private ServiceProviderDao serviceProviderDao;
	private static final Logger logger = Logger.getLogger(RoutingRuleAlertServiceImpl.class);

	/**
	 * 
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void processAlertsForActiveRules() throws Exception {
		// get rules
		List<RoutingRuleHdr> routingRules = routingRuleHdrDao.findAll();

		if (routingRules == null || routingRules.isEmpty()) {
			logger.info("RoutingRuleAlertServiceImpl.processAlertForActiveRules: no rules to process!");
			return;
		}

		for (RoutingRuleHdr routingRuleHdr :routingRules) { 
			if (routingRuleHdr.getRuleStatus().equals(RoutingRulesConstants.ROUTING_RULE_STATUS_INACTIVE)) {
				handleInactiveRuleAlerts(routingRuleHdr);
			}else{
				processAlerts(routingRuleHdr);
			}
		}
	}
	

	public void processAlertsForOneRule(RoutingRuleHdr routingRuleHdr) throws Exception {
		processAlerts(routingRuleHdr);
	}

	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<RoutingRuleEmailVO> processActiveRulesForEmail()
		throws Exception {
		List<RoutingRuleEmailVO> emailVOs = new ArrayList<RoutingRuleEmailVO>();
		List<RoutingRuleHdr> routingRules = routingRuleHdrDao.findAll();
		
		for (RoutingRuleHdr rule : routingRules) {
			if (rule.getRuleStatus().equals(RoutingRulesConstants.ROUTING_RULE_STATUS_ACTIVE)) {
				
				Map<String, Integer> vendors = findVendorsWithAlerts(rule);
				List<String> jobCodes = findJobCodesWithNullOrZeroPrice(rule);
				
				if (vendors != null || jobCodes != null) {
					RoutingRuleEmailVO emailVO = new RoutingRuleEmailVO();
					emailVOs.add(emailVO);
					
					RoutingRuleBuyerAssoc assoc = rule.getRoutingRuleBuyerAssoc();
					emailVO.setBuyerCompanyID(assoc.getBuyer().getBuyerId());
					emailVO.setBuyerCompanyName(assoc.getBuyer().getBusinessName());
					emailVO.setRuleContactFN(rule.getContact().getFirstName());
					emailVO.setRuleContactLN(rule.getContact().getLastName());
					emailVO.setRuleContactEmail(rule.getContact().getEmail());
					emailVO.setCarRuleName(rule.getRuleName());
					emailVO.setVendors(vendors);
					emailVO.setJobCodes(jobCodes);
					if(vendors != null)
					{
						emailVO.setIsStatusChange(Boolean.TRUE);
					}
					if(jobCodes != null)
					{
						emailVO.setIsMissingPrice(Boolean.TRUE);
					}
				}
			}
			continue;
		}
		return emailVOs;
	}

	private List<String> findJobCodesWithNullOrZeroPrice(RoutingRuleHdr rule) {

		List<String> jobCodes = new ArrayList<String>();
		List<RoutingRulePrice> routingRulePrices = rule.getRoutingRulePrice();
		for (RoutingRulePrice routingRulePrice : routingRulePrices) {
			BigDecimal price = routingRulePrice.getPrice();
			if (price == null ) {
				jobCodes.add(routingRulePrice.getJobcode());
			}
		}
		if (jobCodes.isEmpty()) {
			return null;
		}
		return jobCodes;
	}
	
	
	public Map<String, Integer> findVendorsWithAlerts(RoutingRuleHdr rule) {
		
		List<RoutingRuleVendor> vendors = new ArrayList<RoutingRuleVendor>();
		List<RoutingRuleVendor> ruleVendors = rule.getRoutingRuleVendor();
		if (checkProviderStatus(ruleVendors)) {
			// get firm information
			vendors = getProviderAlertFirm(ruleVendors);
		}
				
		if(vendors.isEmpty()) {
			return null;
		}
		
		Map<String, Integer> vendorMap = new HashMap<String, Integer>(vendors.size());
		for (RoutingRuleVendor vendor : vendors) {
			vendorMap.put(vendor.getVendor().getBusinessName(), vendor.getVendor().getId());
		}
		return vendorMap;
	}
	
	/**
	 * 
	 * @param rrVendors
	 * @return boolean
	 */
	private List<RoutingRuleVendor> getProviderAlertFirm(List<RoutingRuleVendor> rrVendors) {
		List<RoutingRuleVendor> alertVendors = new ArrayList<RoutingRuleVendor>();
		for (RoutingRuleVendor rrVendor : rrVendors) {
			ProviderFirm providerFirm = rrVendor.getVendor();
			Integer serviceLiveWfStateId = providerFirm.getServiceLiveWorkFlowState().getId();

			// check ProviderFirmStatus
			if(serviceLiveWfStateId.intValue() != PROVIDER_FIRM_SEARS_APPROVED.intValue() && 
					serviceLiveWfStateId.intValue() != PROVIDER_FIRM_SERVICE_LIVE_APPROVED.intValue()) {
				alertVendors.add(rrVendor);
			}
			else {
				Integer providerFirmId = providerFirm.getId();

				// check Service Provider Status
				List<ServiceProvider> serviceProviders = serviceProviderDao.findByProviderFirmId(providerFirmId);
				boolean providerFound = false;
				for(ServiceProvider serviceProvider: serviceProviders) {
					if(serviceProvider.getServiceLiveWorkFlowId().getId().intValue() == PROVIDER_MARKET_READY.intValue()) {
						providerFound = true;
						break;
					}
				}
				if (!providerFound) {
					alertVendors.add(rrVendor);
				}
				
			}
		}
		
		return alertVendors;
	}

	/**
	 * check for 0 / NULL price alerts
	 * check for vendor status alerts 
	 * @param routingRuleHdr
	 * @throws Exception 
	 */
	private void processAlerts(RoutingRuleHdr routingRuleHdr) throws Exception {
		boolean alertFound1 = checkProviderStatus(routingRuleHdr.getRoutingRuleVendor());
		handleAlert(routingRuleHdr, alertFound1, ROUTING_ALERT_PROVIDER_STATUS_ID);

		boolean alertFound2 = checkJobCodePricing(routingRuleHdr.getRoutingRulePrice());
		handleAlert(routingRuleHdr, alertFound2, ROUTING_ALERT_PRICING_ID);		
	}


	private void handleInactiveRuleAlerts(RoutingRuleHdr routingRuleHdr) throws Exception {
		List<RoutingRuleAlert> alerts = routingRuleHdr.getRoutingRuleAlerts();
			for (RoutingRuleAlert alert : alerts) {
				if (alert.getAlertStatus().equals(RoutingRulesConstants.ROUTING_ALERT_STATUS_ACTIVE)) {
					alert.setAlertStatus(RoutingRulesConstants.ROUTING_ALERT_STATUS_INACTIVE);
					routingRuleAlertDao.saveAndUpdate(alert);
				}
			}
	}


	/**
	 * 
	 * @param rrVendors
	 * @return boolean
	 */
	private boolean checkProviderStatus(List<RoutingRuleVendor> rrVendors) {
		for (RoutingRuleVendor rrVendor : rrVendors) {
			ProviderFirm providerFirm = rrVendor.getVendor();
			Integer serviceLiveWfStateId = providerFirm.getServiceLiveWorkFlowState().getId();

			// check ProviderFirmStatus
			if(serviceLiveWfStateId.intValue() == PROVIDER_FIRM_SEARS_APPROVED.intValue() || serviceLiveWfStateId.intValue() == PROVIDER_FIRM_SERVICE_LIVE_APPROVED.intValue()) {
				Integer providerFirmId = providerFirm.getId();

				// check Service Provider Status
				List<ServiceProvider> serviceProviders = serviceProviderDao.findByProviderFirmId(providerFirmId);
				for(ServiceProvider serviceProvider: serviceProviders) {
					if(serviceProvider.getServiceLiveWorkFlowId().getId().intValue() == PROVIDER_MARKET_READY.intValue()) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param routingRuleSpecialties
	 * @return boolean
	 */
	private boolean checkJobCodePricing(List<RoutingRulePrice> routingRulePrices) {
		boolean result = false;
		for (RoutingRulePrice routingRulePrice : routingRulePrices) {
				BigDecimal price = routingRulePrice.getPrice();
				if (price == null ) {
					return true;
				}
			}
		return result;
	}


	/**
	 * 
	 * @param routingRuleHdr
	 * @param alertFound
	 * @param alertType
	 * @throws Exception
	 */
	private void handleAlert(RoutingRuleHdr routingRuleHdr, boolean alertFound, Integer alertType) throws Exception {
		List<RoutingRuleAlert> ruleAlerts = routingRuleHdr.getRoutingRuleAlerts();

		RoutingRuleAlert existingAlert = checkForAlert(ruleAlerts, alertType);
		
		// Existing Alert | AlertFound | status    => result
		// --------------------------------------------------------------------------
		//  null            true          N/A      => *create alert with inactive status
		//  null            false         N/A      => don't do anything
		//  not null        true          active   => don't do anything
		//  not null        true          inactive => *update status to active
		//  not null        false         active   => *update status to inactive
		//  not null        false         inactive => don't do anything

		if (existingAlert == null) {
			if(alertFound) {
				RoutingRuleAlert alert = new RoutingRuleAlert();
				alert.setAlertType(new LookupRoutingAlertType(alertType));
				alert.setRoutingRuleHdr(routingRuleHdr);
				alert.setAlertStatus(ROUTING_ALERT_STATUS_INACTIVE);
				alert.setModifiedBy(MODIFIED_BY);
				routingRuleAlertDao.saveAndUpdate(alert);
			}
		} else {
			String alertStatus = existingAlert.getAlertStatus();
			if (alertFound) {
				if(alertStatus.equals(ROUTING_ALERT_STATUS_INACTIVE)) {
					RoutingRuleAlert alert = existingAlert;
					alert.setAlertStatus(ROUTING_ALERT_STATUS_ACTIVE);
					routingRuleAlertDao.saveAndUpdate(alert);
				}
			} else {
				if(alertStatus.equals(ROUTING_ALERT_STATUS_ACTIVE)) {
					RoutingRuleAlert alert = existingAlert;
					alert.setAlertStatus(ROUTING_ALERT_STATUS_INACTIVE);
					routingRuleAlertDao.saveAndUpdate(alert);
				}

			}
		}
	}

	
	/**
	 * 
	 * @param ruleAlerts
	 * @param alertType
	 * @return RoutingRuleAlert
	 */
	private RoutingRuleAlert checkForAlert(List<RoutingRuleAlert> ruleAlerts, Integer alertType) {
		if(ruleAlerts == null || ruleAlerts.isEmpty()) {
			return null;
		}

		for (RoutingRuleAlert alert : ruleAlerts) {
			if (alert.getAlertType().getId().equals(alertType)) {
				return alert;
			}
		}

		return null;
	}


	/**
	 * 
	 * @param routingRuleHdrDao
	 */
	public void setRoutingRuleHdrDao(RoutingRuleHdrDao routingRuleHdrDao) {
		this.routingRuleHdrDao = routingRuleHdrDao;
	}
	
	/**
	 * 
	 * @param serviceProviderDao
	 */
	public void setServiceProviderDao(ServiceProviderDao serviceProviderDao) {
		this.serviceProviderDao = serviceProviderDao;
	}

	/**
	 * @param routingRuleAlertDao the routingRuleAlertDao to set
	 */
	public void setRoutingRuleAlertDao(RoutingRuleAlertDao routingRuleAlertDao) {
		this.routingRuleAlertDao = routingRuleAlertDao;
	}
	
}
