package com.servicelive.orderfulfillment.domain.util;

import com.servicelive.orderfulfillment.domain.RoutedProvider;

import java.util.Comparator;

/**
 * User: Yunus Burhani
 * Date: Jul 12, 2010
 * Time: 2:49:34 PM
 */
public class ConditionalOfferComparator implements Comparator<RoutedProvider> {

    public int compare(RoutedProvider o1, RoutedProvider o2) {
        if(null == o1.getConditionalOfferDate() && null == o2.getConditionalOfferDate()) return 0;
        if(null == o1.getConditionalOfferDate()) return -1;
        if(null == o2.getConditionalOfferDate()) return 1;        
        return o1.getConditionalOfferDate().compareTo(o2.getConditionalOfferDate());
    }
}
