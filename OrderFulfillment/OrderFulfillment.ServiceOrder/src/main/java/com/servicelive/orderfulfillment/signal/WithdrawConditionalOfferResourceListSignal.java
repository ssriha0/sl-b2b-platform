package com.servicelive.orderfulfillment.signal;

import java.util.ArrayList;
import java.util.List;

import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOCounterOfferReason;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/05/22
 * New signal for WidthdrawGroupConditionalOffer, accepting a resource list
 */
public class WithdrawConditionalOfferResourceListSignal extends RoutedProviderSignal {

    /* (non-Javadoc)
     * @see com.servicelive.orderfulfillment.signal.Signal#process(com.servicelive.orderfulfillment.domain.SOElement, com.servicelive.orderfulfillment.domain.ServiceOrder)
     */
    @Override
    protected void processRoutedProvider(final RoutedProvider source, final RoutedProvider target) {

        /*Remove the conditional offer reasons.*/
        for(SOCounterOfferReason reason: target.getCounterOffers()){
            reason.setRoutedProvider(null);
            getServiceOrderDao().delete(reason);
        }
        target.widthdrawConditionalOffer();
    }

    
    @Override
    protected List<String> validate(final SOElement soe, final ServiceOrder soTarget) {
    	List<String> returnVal=new ArrayList<String>(); 
        
        final SOElementCollection soeCollection=(SOElementCollection)soe;
    	final List<SOElement> soeList=soeCollection.getElements();
        
    	for(SOElement soElement: soeList){
    		returnVal = super.validate(soElement, soTarget);
	        if(!returnVal.isEmpty()){
	            //Parent failed validation - Should not continue with specialized validation.
	            return returnVal;
	        }
	        
	        final RoutedProvider input = (RoutedProvider) soElement;
	        final RoutedProvider target = soTarget.getRoutedResource(input.getProviderResourceId());
	        if(target.getCounterOffers().size() < 0){
	            returnVal.add("No conditional offers to withdraw.");
	        }
    	}
        
    	return returnVal;
    }
}
