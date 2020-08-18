package com.servicelive.orderfulfillment.orderprep.scheduling;

import com.servicelive.orderfulfillment.domain.SOAddon;

import java.util.List;

public class LeadTimeCalculator {
    public static Integer findMinLeadTimeFromAddOnList(List<SOAddon> addOnList) {
        Integer minLeadTime = Integer.MAX_VALUE;
        for (SOAddon addon : addOnList) {
            if (addon.getDispatchDaysOut() != null && addon.getDispatchDaysOut() < minLeadTime) {
                minLeadTime = addon.getDispatchDaysOut();
            }
        }
        return (minLeadTime.equals(Integer.MAX_VALUE) ? 0 : minLeadTime);
    }
}
