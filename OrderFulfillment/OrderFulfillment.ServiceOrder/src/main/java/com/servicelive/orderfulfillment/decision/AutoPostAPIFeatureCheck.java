package com.servicelive.orderfulfillment.decision;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.api.model.OpenExecution;

import com.servicelive.domain.common.BuyerFeatureSetEnum;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.lookup.BuyerFeatureLookup;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;

public class AutoPostAPIFeatureCheck extends AbstractServiceOrderDecision {

	private static final long serialVersionUID = 7258373364727565809L;
	private static Log log = LogFactory.getLog(AutoPostAPIFeatureCheck.class);
	QuickLookupCollection quickLookupCollection ;
	
	 /**
     * Set ROUTE_DATE process variable for each so for buyers whose AUTO_POST_API_SO feature set is on.
     * This ROUTE_DATE will act as the dueDate attribute for auto post timer.
     */
	@SuppressWarnings("unchecked")
	public String decide(OpenExecution execution) {
		log.debug("Creating Routedate variable.");
		ServiceOrder so = getServiceOrder(execution);
		if (null != so) {
			Long buyerId = so.getBuyerId();
			log.info("buyerId::" + buyerId);
			if (null != buyerId) {
				BuyerFeatureLookup buyerFeatureLookup = quickLookupCollection
						.getBuyerFeatureLookup();
				if (!buyerFeatureLookup.isInitialized()) {
					throw new ServiceOrderException(
							"Unable to lookup buyer feature . BuyerFeatureLookup not initialized.");
				}
				if (buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(
						BuyerFeatureSetEnum.AUTO_POST_API_SO, buyerId)) {
					log.info("AUTO_POST_API_SO is on");
					Calendar routeDate = addMinutesOrHoursToDate(
							Calendar.MINUTE, 2);
					String routeDateAsString = ProcessVariableUtil
							.convertToJPDLDueDate(routeDate);
					execution.setVariable(ProcessVariableUtil.ROUTE_DATE,
							routeDateAsString);
					log.info("after setting RouteDate");
					return "autoPostAPIOn";
				}
				else{
					return "autoPostAPIOff";
				}
			}
		}
		return "autoPostAPIOff";
	}
	
	/**
     * To add minutes or hours to current time
     * @param mode
     * @param interval
     * @return
     */
    
    public static Calendar addMinutesOrHoursToDate(Integer mode,Integer interval) {
		Calendar cal = Calendar.getInstance();
		log.info("inside addMinutesOrHoursToDate::"+ cal);
		cal.add(mode.intValue(), interval.intValue());
		log.info("after addMinutesOrHoursToDate::"+ cal);
		return cal;
	}
    
	public QuickLookupCollection getQuickLookupCollection() {
		return quickLookupCollection;
	}
	public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
		this.quickLookupCollection = quickLookupCollection;
	}

}