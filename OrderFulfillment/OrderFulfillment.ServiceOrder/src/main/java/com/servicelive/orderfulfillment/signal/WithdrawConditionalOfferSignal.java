package com.servicelive.orderfulfillment.signal;

import java.util.List;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOCounterOfferReason;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * @author Mustafa Motiwala
 *
 */
public class WithdrawConditionalOfferSignal extends RoutedProviderSignal {

	private static final Logger LOGGER = Logger.getLogger(WithdrawConditionalOfferSignal.class);
    /* (non-Javadoc)
     * @see com.servicelive.orderfulfillment.signal.Signal#process(com.servicelive.orderfulfillment.domain.SOElement, com.servicelive.orderfulfillment.domain.ServiceOrder)
     */
    @Override
    protected void processRoutedProvider(RoutedProvider source,  RoutedProvider target) {
    	LOGGER.info("inside WithdrawConditionalOfferSignal.processRoutedProvider()");
        /*Remove the conditional offer reasons.*/
        for(SOCounterOfferReason reason: target.getCounterOffers()){
            reason.setRoutedProvider(null);
            getServiceOrderDao().delete(reason);
        }
        target.widthdrawConditionalOffer();
        LOGGER.info("leaving WithdrawConditionalOfferSignal.processRoutedProvider()");
    }

    
    @Override
    protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
    	LOGGER.info("inside WithdrawConditionalOfferSignal.validate()");
        List<String> returnVal = super.validate(soe,soTarget);
        if(!returnVal.isEmpty()) return returnVal; //Parent validation failed. Do not continue with specialized validation.
        RoutedProvider srcRoutedProvider = (RoutedProvider) soe;
        RoutedProvider target = soTarget.getRoutedResource(srcRoutedProvider.getProviderResourceId());
        if(target.getCounterOffers().size() < 0){ //The isEmpty check does not work. HBM Lazy initialize fails us here.
            returnVal.add("No conditional offers to withdraw.");
        }
        LOGGER.info("leaving WithdrawConditionalOfferSignal.validate()");
        return returnVal;
    }
}
