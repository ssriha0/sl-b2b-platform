package com.servicelive.orderfulfillment.decision;

import org.apache.log4j.Logger;
import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.domain.SOOnSiteVisit;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class IVREntryCheck extends AbstractServiceOrderDecision {

	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(getClass());

	public enum IVRType {
		withIVR, withoutIVR
	}

	public String decide(OpenExecution execution) {
		ServiceOrder serviceOrder = getServiceOrder(execution);
		if (isIVREntryAvailable(serviceOrder.getSoId())) {
			return IVRType.withIVR.name();
		}
		return IVRType.withoutIVR.name();
	}

	private boolean isIVREntryAvailable(String soId) {
		SOOnSiteVisit soOnSiteVisit = serviceOrderDao.getIVRDetails(soId);		
		if(null != soOnSiteVisit) {			
			if (null != soOnSiteVisit.getArrivalDate() || null != soOnSiteVisit.getDepartureDate()) {	
				return true;
			}			
		}
		return false;
	}

}