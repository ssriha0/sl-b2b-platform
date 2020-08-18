package com.servicelive.orderfulfillment.decision;

import java.util.List;

import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;

public class SendAllCounterOfferAlertDecision extends AbstractServiceOrderDecision {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6912320866132453867L;

	public String decide(OpenExecution execution) {
        ServiceOrder serviceOrder = getServiceOrder(execution);

        return allRoutedProvidersCounterOffer(serviceOrder.getRoutedResources()) ? "sendAllRejectAlert" : "noAlert";
    }

    private boolean allRoutedProvidersCounterOffer(List<RoutedProvider> routedProviderList) {
        if (routedProviderList.size()==0) return false;

        for (RoutedProvider routedProvider : routedProviderList) {
            if (routedProvider.getProviderResponse() != ProviderResponseType.CONDITIONAL_OFFER) {
                return false;
            }
        }

        return true;
    }
}
