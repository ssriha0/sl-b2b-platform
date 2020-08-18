package com.servicelive.orderfulfillment.decision;

import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class ServiceDateTimePastCheck extends AbstractServiceOrderDecision {
    /*** not needed but takes away our warnings ***/
    private static final long serialVersionUID = 1L;

    public String decide(OpenExecution execution) {
        ServiceOrder serviceOrder = getServiceOrder(execution);
        return Boolean.toString(isServiceDateTimePast(serviceOrder));
    }

    private boolean isServiceDateTimePast(ServiceOrder serviceOrder) {
        if (serviceOrder.getSchedule().isRequestedServiceTimeTypeDateRange()) {
            return serviceOrder.isServiceEndDateTimePast();
        } else {
            return serviceOrder.isServiceStartDateTimePast();
        }
    }
}
