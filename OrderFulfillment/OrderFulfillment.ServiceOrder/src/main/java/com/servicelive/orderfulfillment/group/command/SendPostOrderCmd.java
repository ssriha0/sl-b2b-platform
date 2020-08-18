package com.servicelive.orderfulfillment.group.command;

import java.util.List;
import java.util.Map;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * This command will only be used after calculate pricing command
 * to either change spend limit or post newly added order
 */
public class SendPostOrderCmd extends GroupSignalSOCmd {


	@Override
	protected void handleGroup(SOGroup soGroup, Map<String, Object> processVariables) {
		List<RoutedProvider> providers = null;
        logger.debug("inside send post order command " );
		//get the SOElement for post command
		for(ServiceOrder so : soGroup.getServiceOrders()){
			if (null != so.getRoutedResources() && so.getRoutedResources().size() > 0){
				providers = so.getRoutedResources();
				break;
			}
		}
        if(null != providers){
            SOElement soElement = this.getSOElementForOrderSignal(providers);
            //check which order does not have providers and send post signals to those orders
            for(ServiceOrder so : soGroup.getServiceOrders()){
                if (so.getRoutedResources().size() == 0){
                    logger.debug("Calling Post order command for so " + so.getSoId());
                    this.sendSignalToSOProcess(so, SignalType.POST_ORDER, soElement, getProcessVariableForSendEmail(false));
                }else{
                    logger.debug("Calling spend limit changed for so " + so.getSoId());
                    this.sendSignalToSOProcess(so, SignalType.SPEND_LIMIT_CHANGED, null, getProcessVariableForSendEmail(false));
                }               
            }
        } else {
            throw new ServiceOrderException("Expected that at least one service order in a group would have routed providers");
        }
	}

	@Override
	protected void handleServiceOrder(ServiceOrder so, Map<String, Object> processVariables) {
		throw new ServiceOrderException("This should not have happened");
	}
	
	private SOElementCollection getSOElementForOrderSignal(List<RoutedProvider> routedProviders){
		SOElementCollection soec = new SOElementCollection();
		for (RoutedProvider rp : routedProviders)
			soec.addElement(rp);		
		return soec;
	}
}
