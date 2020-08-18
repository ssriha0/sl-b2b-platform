package com.servicelive.orderfulfillment.command;

import java.util.Calendar;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.lookup.BuyerFeatureLookup;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.domain.common.BuyerFeatureSetEnum;
import com.servicelive.orderfulfillment.common.ServiceOrderException;

/**
 * User: Mustafa Motiwala Date: Apr 29, 2010 Time: 2:47:32 PM
 */
public class SetRouteDateProcessVariableCmd extends SOCommand {
	private static Log log = LogFactory
			.getLog(UpdateServiceDateProcessVariableCmd.class);
	QuickLookupCollection quickLookupCollection;

	/**
	 * Set ROUTE_DATE process variable for each so for buyers whose
	 * AUTO_POST_API_SO feature set is on. This ROUTE_DATE will act as the
	 * dueDate attribute for auto post timer.
	 */
	@Override
	public void execute(Map<String, Object> processVariables) {
		log.debug("Creating Routedate variable.");
		ServiceOrder so = getServiceOrder(processVariables);
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
					Object obj = processVariables
							.get(ProcessVariableUtil.ROUTE_DATE);
					if (null != obj) {
						log.info("disable ROUTE_DATE for at&t");
						ProcessVariableUtil.setFarFutureDateInProcessVariables(
								processVariables,
								ProcessVariableUtil.ROUTE_DATE);
						log.info("after disable ROUTE_DATE for at&t:"
								+ ProcessVariableUtil.ROUTE_DATE);
					} else {
						Calendar routeDate = addMinutesOrHoursToDate(
								Calendar.MINUTE, 1);
						String routeDateAsString = ProcessVariableUtil
								.convertToJPDLDueDate(routeDate);
						ProcessVariableUtil.addRouteDate(processVariables,
								routeDateAsString);
						log.info("after setting RouteDate");
					}
				}
			}
		}
	}

	/**
	 * To add minutes or hours to current time
	 * 
	 * @param mode
	 * @param interval
	 * @return
	 */

	public static Calendar addMinutesOrHoursToDate(Integer mode,
			Integer interval) {
		Calendar cal = Calendar.getInstance();
		log.info("inside addMinutesOrHoursToDate::" + cal);
		cal.add(mode.intValue(), interval.intValue());
		log.info("after addMinutesOrHoursToDate::" + cal);
		return cal;
	}

	public QuickLookupCollection getQuickLookupCollection() {
		return quickLookupCollection;
	}

	public void setQuickLookupCollection(
			QuickLookupCollection quickLookupCollection) {
		this.quickLookupCollection = quickLookupCollection;
	}
}
