package com.servicelive.orderfulfillment.group.command;

import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.common.TransitionNotAvailableException;
import com.servicelive.orderfulfillment.domain.GroupRoutedProvider;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Yunus Burhani
 * Date: Sep 13, 2010
 * Time: 9:07:25 AM
 */
public class UnPostGroupCmd extends GroupSignalSOCmd {

    @Override
    protected void handleGroup(SOGroup soGroup, Map<String, Object> processVariables) {
        for(ServiceOrder so : soGroup.getServiceOrders()){
            logger.debug("Calling UN-ROUTE signal for so " + so.getSoId());
            try{
            this.sendSignalToSOProcess(so, SignalType.UNPOST_ORDER, null, getProcessVariableForSendEmail(false));
            }catch(TransitionNotAvailableException tnae){
                //ignore the exception because the newly added service order may not be posted
        }
        }

        //also delete the group routed providers if exists
        if (null != soGroup.getGroupRoutedProviders()){
            for(GroupRoutedProvider grp : soGroup.getGroupRoutedProviders()){
                serviceOrderDao.delete(grp);
    }
            soGroup.getGroupRoutedProviders().clear();
            serviceOrderDao.update(soGroup);
        }
    }

    @Override
    protected void handleServiceOrder(ServiceOrder so, Map<String, Object> processVariables) {
        logger.debug("Calling UN-ROUTE signal for so " + so.getSoId());
        try{
            this.sendSignalToSOProcess(so, SignalType.UNPOST_ORDER, null, getProcessVariableForSendEmail(false));
        }catch(TransitionNotAvailableException tnae){
            //ignore the exception because the newly added service order may not be posted
    }
    }
}
