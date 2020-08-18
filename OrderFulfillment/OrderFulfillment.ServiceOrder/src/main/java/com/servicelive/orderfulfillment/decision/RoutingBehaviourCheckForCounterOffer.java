package com.servicelive.orderfulfillment.decision;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.domain.spn.network.SPNTierMinutes;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

import org.apache.log4j.Logger;
import org.jbpm.api.model.OpenExecution;

public class RoutingBehaviourCheckForCounterOffer extends AbstractServiceOrderDecision {
    /**
	 * 
	 */
	private static final long serialVersionUID = 250204626285590792L;
	protected final Logger logger = Logger.getLogger(getClass());

	public String decide(OpenExecution execution) {
		
		logger.info("Inside RoutingBehaviourCheck");
		ServiceOrder so = getServiceOrder(execution);
		Integer tier = (Integer)execution.getVariable(ProcessVariableUtil.AUTO_ROUTING_TIER);
		Integer currentTier = so.getSOWorkflowControls().getCurrentTier();
		Integer nextTier = so.getSOWorkflowControls().getNextTier();
		logger.info("Inside RoutingBehaviourCheck>>currentTier"+currentTier);
		logger.info("Inside RoutingBehaviourCheck>>nextTier"+nextTier);
		logger.info("Inside RoutingBehaviourCheck>>tierId"+tier);
		
		int routedProviders = 0;
		int routingEligibleProviders = 0;
		if(null!=currentTier){
			routedProviders = so.getRoutedResources().size();
			routingEligibleProviders = so.getTierRoutedResources().size();
		}
		 if(null==currentTier 
				|| (null!= currentTier && null != nextTier && (routedProviders==routingEligibleProviders) && null!= so.getSOWorkflowControls().getMpOverFlow() && !(so.getSOWorkflowControls().getMpOverFlow()))
				|| (null!= currentTier && null == nextTier && null!= so.getSOWorkflowControls().getMpOverFlow() && !(so.getSOWorkflowControls().getMpOverFlow()))){
		return "autoRouted";
		}else{
			Integer provInCurrentTier = (Integer)execution.getVariable(ProcessVariableUtil.PROVIDERS_IN_CURRENT_TIER);
			Integer provInPrevTiers = (Integer)execution.getVariable(ProcessVariableUtil.PROVIDERS_IN_PREVIOUS_TIERS);
			if(provInCurrentTier == null || provInPrevTiers == null){
				SPNHeader hdr = getSPNTierDetails(so.getSpnId());
				List<SPNTierMinutes> tierMinutes = hdr.getTierMinutes();
				int provs = 0;
				int currTierProvs = 0;
				for(SPNTierMinutes tierDetails:tierMinutes){
					Integer id = (Integer) tierDetails.getSpnTierPK().getTierId().getId();
					int i = id.intValue();
					//logger.info("id"+i);
					if(i== nextTier.intValue()){
					provs = tierDetails.getNoOfMembers();
					}
					/*if(i== currentTier.intValue()){
					currTierProvs = tierDetails.getNoOfMembers();
					}*/
				}
				execution.setVariable(ProcessVariableUtil.AUTO_ROUTING_TIER, currentTier);
				execution.setVariable(ProcessVariableUtil.PROVIDERS_IN_CURRENT_TIER, provs);
				//execution.setVariable(ProcessVariableUtil.PROVIDERS_IN_PREVIOUS_TIERS, currTierProvs);
			}
			logger.info("provInCurrentTier"+(Integer)execution.getVariable(ProcessVariableUtil.PROVIDERS_IN_CURRENT_TIER));
			logger.info("provInPrevTiers"+(Integer)execution.getVariable(ProcessVariableUtil.PROVIDERS_IN_PREVIOUS_TIERS));
			Calendar farFutureDate = new GregorianCalendar();
			 String farFutureDateString = convertToJPDLDueDate(farFutureDate);
			 System.out.println("date>>"+farFutureDateString);
		 execution.createVariable(ProcessVariableUtil.AUTO_TIER_ROUTE_DATE, farFutureDateString);
		 execution.setVariable(ProcessVariableUtil.COUNTER_OFFER_BY_ALL_PROV, "true");
		 //return (so.getBuyerId().equals(1000L)?"groupTierRouted":"tierRouted");
		 return "tierRouted";
		}
    }
	public static String convertToJPDLDueDate(Calendar calendar){
    	return String.format("date=%1$tH:%1$tM %1$tm/%1$td/%1$tY", calendar);
    }
}
