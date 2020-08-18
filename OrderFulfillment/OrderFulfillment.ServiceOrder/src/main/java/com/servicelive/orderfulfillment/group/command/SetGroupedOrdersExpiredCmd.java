package com.servicelive.orderfulfillment.group.command;

import java.util.Map;

import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;
/**
 * Created by IntelliJ IDEA.
 * User: Yunus Burhani
 * Date: Sep 18, 2010
 * Time: 10:45:42 AM
 */
public class SetGroupedOrdersExpiredCmd extends GroupSignalSOCmd {

	@Override
	protected void handleGroup(SOGroup soGroup, Map<String, Object> processVariables) {
		logger.info("Inside command handleGroup : ****SetGroupedOrdersExpiredCmd****** ");
		/*String editedSoId = (String) processVariables
				.get(ProcessVariableUtil.PVKEY_SERVICE_ORDER_IDS_FOR_GROUP_PROCESS);
		ServiceOrder eSO = serviceOrderDao.getServiceOrder(editedSoId);*/
		ServiceOrder eSO = null;
		for(ServiceOrder so : soGroup.getServiceOrders()){
			if(so.getWfStateId().equals(OrderConstants.EXPIRED_STATUS)){	
				logger.info("@@@@@@@@@inside if of SetGroupedOrdersExpiredCmd@@@@@@@@");
				eSO = so;	
				logger.info("*******edited service order****** "+eSO.getSoId());
				logger.info("*******edited service order status****** "+eSO.getWfStateId());
			}
			
			
			if(!eSO.getSoId().equals(so.getSoId()) && !eSO.getWfStateId().equals(so.getWfStateId())){
				so.setWfStateId(eSO.getWfStateId());
				serviceOrderDao.update(so); 
				
				logger.info("%%%%%%% calling signal EXPIRE_ORDER_MANUALLY %%%%%%%");
				
				this.sendSignalToSOProcess(so, SignalType.EXPIRE_ORDER_MANUALLY, null, getProcessVariableForSendEmail(false));
			}
		}

	}

	@Override
	protected void handleServiceOrder(ServiceOrder so, Map<String, Object> processVariables) { 

		logger.info("Inside command handleServiceOrder : ****SetGroupedOrdersExpiredCmd****** ");
	}

}
