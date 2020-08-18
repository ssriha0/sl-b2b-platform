package com.servicelive.orderfulfillment.command;

import com.servicelive.orderfulfillment.domain.ServiceOrder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import com.servicelive.common.util.MoneyUtil;

/**
 * Created by IntelliJ IDEA.
 * User: yburhani
 * Date: Sep 21, 2010
 * Time: 11:31:22 AM
 */
public class SearsHSRCompleteOrderCmd extends CompleteOrderCmd {

    @Override
    protected BigDecimal getTotalSpendLimit(ServiceOrder serviceOrder) {
        //return serviceOrder.getTotalSpendLimitWithPostingFee().add(serviceOrder.getTotalAddon(true)).setScale(2, RoundingMode.HALF_EVEN);
        return MoneyUtil.getRoundedMoneyBigDecimal(serviceOrder.getTotalSpendLimit().add(serviceOrder.getTotalAddon(true)).doubleValue());
    }
    
}
