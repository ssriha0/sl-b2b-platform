package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class RemoveAcceptedProviderFromSOCmd extends SOCommand {
    @Override
    public void execute(Map<String, Object> processVariables) {
        ServiceOrder serviceOrder = getServiceOrder(processVariables);

        //from service order remove accepted provider
        serviceOrder.setAcceptedProviderId(null);
        serviceOrder.setAcceptedProviderResourceId(null);
        serviceOrder.setSoTermsCondId(null);
        serviceOrder.setProviderSOTermsCondInd(null);
        serviceOrder.setProviderTermsCondDate(null);

        serviceOrderDao.update(serviceOrder);
    }
}
