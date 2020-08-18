package com.servicelive.orderfulfillment.command;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.wallet.serviceinterface.vo.WalletResponseVO;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: yburhani
 * Date: Sep 22, 2010
 * Time: 8:48:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class SearsHSRCloseOrderCmd extends CloseOrderCmd {

    protected BigDecimal getAddonPrice(ServiceOrder serviceOrder){
         return serviceOrder.getTotalAddon(true);
    }

}
