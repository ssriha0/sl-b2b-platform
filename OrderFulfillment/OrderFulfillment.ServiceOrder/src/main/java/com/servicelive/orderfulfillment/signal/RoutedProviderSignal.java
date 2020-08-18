package com.servicelive.orderfulfillment.signal;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * @author Mustafa Motiwala
 *
 */
public abstract class RoutedProviderSignal extends Signal {
    
	private static final Logger LOGGER = Logger.getLogger(RoutedProviderSignal.class);

    @Override
    protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
    	LOGGER.info("inside RoutedProviderSignal.validate()");
        List<String> returnVal = new ArrayList<String>();
        if(!(soe instanceof RoutedProvider || soe instanceof SOElementCollection)){
            returnVal.add("Invalid type passed to signal. [Expected RoutedProvider but found " + soe.getClass().getName() + "]");
        }        
        LOGGER.info("leaving RoutedProviderSignal.validate()");
        return returnVal;
    }

    @Override
    protected void update(SOElement soe, ServiceOrder so) {
    	LOGGER.info("inside RoutedProviderSignal.update()");
    	List<SOElement> soeList=new ArrayList<SOElement>();
    	if(soe instanceof RoutedProvider){
    		soeList.add(soe);
    	}
    	else{
    		SOElementCollection soeCollection=(SOElementCollection)soe;
    		soeList=soeCollection.getElements();
    	}
    	for(SOElement soElement: soeList){
    		RoutedProvider source = (RoutedProvider)soElement;
    		RoutedProvider target = so.getRoutedResource(source.getProviderResourceId());
    		processRoutedProvider(source, target);
    		serviceOrderDao.update(target);
    		serviceOrderDao.update(so);
    	}
    	LOGGER.info("leaving RoutedProviderSignal.update()");
    }
    
    abstract protected void processRoutedProvider(RoutedProvider provider, RoutedProvider target);
}
