package com.servicelive.orderfulfillment.group.command;

import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.ServiceOrderProcess;

public class GroupProcessEndCmd extends SOGroupCmd {

	private Logger logger = Logger.getLogger(getClass());
	
	private void makeGroupProcessInactive(String soId) {
		ServiceOrderProcess sop = serviceOrderProcessDao.getServiceOrderProcess(soId);
		sop.setGroupingSearchable(false);
        sop.setGroupProcessId(null);//this is needed in case if we release order and come back to grouping
        sop.setGroupId(null);
		serviceOrderProcessDao.save(sop);
	}

	@Override
	public void handleGroup(SOGroup soGroup,
			Map<String, Object> processVariables) {
		String groupId = soGroup.getGroupId();
		for(ServiceOrder so : soGroup.getServiceOrders()){
			logger.debug("removing grouping from service order " + so.getSoId());
			makeGroupProcessInactive(so.getSoId());
			so.setSoGroup(null);
			so.setOrignalGroupId(groupId);
			serviceOrderDao.save(so);
		}		
	}

	@Override
	public void handleServiceOrder(ServiceOrder so,	Map<String, Object> processVariables) {
		logger.debug("making service order group process inactive " + so.getSoId());
		makeGroupProcessInactive(so.getSoId());
	}

}
