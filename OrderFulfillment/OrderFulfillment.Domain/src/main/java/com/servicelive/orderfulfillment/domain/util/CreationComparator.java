package com.servicelive.orderfulfillment.domain.util;

import com.servicelive.orderfulfillment.domain.SOBase;

import java.util.Comparator;

/**
 * User: Yunus Burhani
 * Date: Jul 12, 2010
 * Time: 12:32:14 PM
 */
public class CreationComparator implements Comparator<SOBase> {

    public int compare(SOBase o1, SOBase o2) {
        return o1.getCreatedDate().compareTo(o2.getCreatedDate());
    }
}
