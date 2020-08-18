package com.servicelive.orderfulfillment.domain.util;

import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.util.ConditionalOfferComparator;

public class ConditionalOfferComparatorTest {
    private static Logger logger = Logger.getLogger(ConditionalOfferComparatorTest.class);
    private ConditionalOfferComparator comparator ;
   
    @Test
    public void compareRoutedproviderdate(){
    	try{
    		Date today = Calendar.getInstance().getTime();
    		Date tomorrow = DateUtils.addDays(today, 1);
    		RoutedProvider provider1=new RoutedProvider();
    		RoutedProvider provider2=new RoutedProvider();
    		provider1.setCreatedDate(today);
    		provider2.setCreatedDate(tomorrow);
    		comparator = new ConditionalOfferComparator();
    		int resultNew = comparator.compare(provider1, provider2);
    		Assert.assertSame("Dates are not same",0,resultNew);
    		}catch (Exception e) {
    		logger.error("Exception in comparing routed providers:"+ e.getMessage());
		}
    }
	
}
