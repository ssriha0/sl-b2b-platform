package com.servicelive.orderfulfillment.group.command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import com.servicelive.marketplatform.common.vo.ProviderIdVO;
import com.servicelive.orderfulfillment.command.util.AutoRouteHelper;
import com.servicelive.orderfulfillment.command.util.SOCommandArgHelper;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.GroupRoutedProvider;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

public class AutoRouteGroupCmd extends GroupSignalSOCmd {

	AutoRouteHelper autoRouteHelper;
	
	@Override
	public void handleGroup(SOGroup soGroup, Map<String, Object> processVariables) {
		
        String autoPost = (String) processVariables.get(ProcessVariableUtil.AUTO_POST_IND);
        String isRepost = (String) processVariables.get(ProcessVariableUtil.REPOST);
        String perfCriteriaLevel = (String)processVariables.get(ProcessVariableUtil.PERF_CRITERIA_LEVEL);
        logger.info("Inside AutoRouteGroupCmd..1>>perfCriteriaLevel>>"+perfCriteriaLevel);
		List<ProviderIdVO> providers = getProviderList(soGroup.getFirstServiceOrder(), processVariables);
		Integer tier = (Integer) processVariables.get(ProcessVariableUtil.AUTO_ROUTING_TIER);
		String isOfEligible = (String)processVariables.get(ProcessVariableUtil.OVERFLOW_ELIGIBLE);
		Integer nextTier = (Integer) processVariables.get(ProcessVariableUtil.NEXT_TIER);
		Integer provInPrevTiers = (Integer)processVariables.get(ProcessVariableUtil.PROVIDERS_IN_PREVIOUS_TIERS);
		logger.info("Inside AutoRouteGroupCmd..1>>nextTier>>"+nextTier);
		logger.info("Inside AutoRouteGroupCmd..1>>isOfEligible>>"+isOfEligible);
		logger.info("Providers in previous tiers>>"+provInPrevTiers);
		saveRoutedProvidersOnGroup(soGroup, providers);
		if (sendPostSignal(processVariables)){
			this.sendSignalToSOProcess(soGroup, SignalType.POST_ORDER, getSOElementForOrderSignal(providers, tier, nextTier), getProcessVariables());
		}else {
			for(ServiceOrder so : soGroup.getServiceOrders())
				autoRouteHelper.saveRoutedProviders(providers, so, tier, nextTier, isRepost, autoPost, perfCriteriaLevel, provInPrevTiers);
			if(null!=tier && tier.equals(4) && null!=isOfEligible && isOfEligible.equals("false")){
	    		logger.info("AutoRouteGroupCmd>>disabling tier");
	    		processVariables.put(ProcessVariableUtil.PROVIDERS_IN_CURRENT_TIER, 0);
	     		processVariables.put(ProcessVariableUtil.PROVIDERS_IN_PREVIOUS_TIERS, 0);
	     		Calendar farFutureDate = new GregorianCalendar(2100, Calendar.JANUARY, 1);
	            String farFutureDateString = convertToJPDLDueDate(farFutureDate);
	            processVariables.put(ProcessVariableUtil.AUTO_TIER_ROUTE_DATE, farFutureDateString);
	     		ProcessVariableUtil.setFarFutureDateInProcessVariables(processVariables, ProcessVariableUtil.AUTO_TIER_ROUTE_DATE);
	    		processVariables.put(ProcessVariableUtil.AUTO_ROUTING_TIER, null);
	    		processVariables.put(OrderfulfillmentConstants.POST_FROM_FRONTEND_ACTION, null);
	    	}
		}
	}

    private Map<String, Serializable> getProcessVariables() {
        Map<String, Serializable> variables = getProcessVariableForSendEmail(false);
        variables.put(OrderfulfillmentConstants.PVKEY_LOG_ROUTING, false);
        return variables;
    }

	private void saveRoutedProvidersOnGroup(SOGroup soGroup, List<ProviderIdVO> providers) {
		List<ProviderIdVO> nProviders = new ArrayList<ProviderIdVO>();
		for(ProviderIdVO proId : providers){
			boolean found = false;
			for(GroupRoutedProvider grp : soGroup.getGroupRoutedProviders()){
				if(grp.getRoutedProviderId().intValue() == proId.getResourceId().intValue()){
					found = true;
					break;
				}				
			}
			if(!found){
				nProviders.add(proId);
			}
		}
		
		for (ProviderIdVO rp : nProviders){
			GroupRoutedProvider grp = new GroupRoutedProvider();
			grp.setProviderResourceId(rp.getResourceId().longValue());
			grp.setVendorId(rp.getVendorId());
			grp.setRoutedDate(new Date());
			soGroup.addGroupRoutedProvider(grp);
		}
	}

	@Override
	public void handleServiceOrder(ServiceOrder so, Map<String, Object> processVariables) {
		
        String autoPost = (String) processVariables.get(ProcessVariableUtil.AUTO_POST_IND);
        String isRepost = (String) processVariables.get(ProcessVariableUtil.REPOST);
        String perfCriteriaLevel = (String)processVariables.get(ProcessVariableUtil.PERF_CRITERIA_LEVEL);
        logger.info("Inside AutoRouteGroupCmd..2>>perfCriteriaLevel>>"+perfCriteriaLevel);
		List<ProviderIdVO> providers = getProviderList(so, processVariables);
		Integer tier = (Integer) processVariables.get(ProcessVariableUtil.AUTO_ROUTING_TIER);
		String isOfEligible = (String)processVariables.get(ProcessVariableUtil.OVERFLOW_ELIGIBLE);
		Integer nextTier = (Integer) processVariables.get(ProcessVariableUtil.NEXT_TIER);
		Integer provInPrevTiers = (Integer)processVariables.get(ProcessVariableUtil.PROVIDERS_IN_PREVIOUS_TIERS);
		logger.info("Inside AutoRouteGroupCmd..2>>nextTier>>"+nextTier);
		logger.info("Providers in previous tiers>>"+provInPrevTiers);
		if(sendPostSignal(processVariables)){
			this.sendSignalToSOProcess(so, SignalType.POST_ORDER, getSOElementForOrderSignal(providers, tier, nextTier), getProcessVariables());
		}else{
			autoRouteHelper.saveRoutedProviders(providers, so, tier, nextTier,isRepost, autoPost, perfCriteriaLevel, provInPrevTiers);
			if(null!=tier && tier.equals(4) && null!=isOfEligible && isOfEligible.equals("false")){
	    		logger.info("AutoRouteGroupCmd>>disabling tier");
	    		processVariables.put(ProcessVariableUtil.PROVIDERS_IN_CURRENT_TIER, 0);
	     		processVariables.put(ProcessVariableUtil.PROVIDERS_IN_PREVIOUS_TIERS, 0);
	     		Calendar farFutureDate = new GregorianCalendar(2100, Calendar.JANUARY, 1);
	            String farFutureDateString = convertToJPDLDueDate(farFutureDate);
	            processVariables.put(ProcessVariableUtil.AUTO_TIER_ROUTE_DATE, farFutureDateString);
	     		ProcessVariableUtil.setFarFutureDateInProcessVariables(processVariables, ProcessVariableUtil.AUTO_TIER_ROUTE_DATE);
	    		processVariables.put(ProcessVariableUtil.AUTO_ROUTING_TIER, null);
	    		processVariables.put(OrderfulfillmentConstants.POST_FROM_FRONTEND_ACTION, null);
	    	}
		}
	}
	
	private List<ProviderIdVO> getProviderList(ServiceOrder so, Map<String, Object> processVariables ) {
        if (sendPostSignal(processVariables)){
        	@SuppressWarnings("unchecked")
            List<ProviderIdVO> list = (List<ProviderIdVO>) processVariables.get(ProcessVariableUtil.PROVIDER_LIST);
			return list;
        }else{//this is for tier routing
		    return autoRouteHelper.getProviders(so, processVariables);
        }
	}
		
	private SOElementCollection getSOElementForOrderSignal(List<ProviderIdVO> routedProviders, Integer tier, Integer nextTier){
		SOElementCollection soec = new SOElementCollection();
		for (ProviderIdVO pi : routedProviders){
			RoutedProvider rp = new RoutedProvider();
			rp.setVendorId(pi.getVendorId());
			rp.setProviderResourceId(pi.getResourceId().longValue());
			rp.setPerfScore(pi.getRoutingTimePerfScore());
			rp.setFirmPerfScore(pi.getRoutingTimeFirmPerfScore());
            rp.setTierId(tier);
            rp.setNextTier(nextTier);
			soec.addElement(rp);
		}
		return soec;
	}
	
	public static String convertToJPDLDueDate(Calendar calendar){
    	return String.format("date=%1$tH:%1$tM %1$tm/%1$td/%1$tY", calendar);
    }

	public void setAutoRouteHelper(AutoRouteHelper autoRouteHelper) {
		this.autoRouteHelper = autoRouteHelper;
	}
}
