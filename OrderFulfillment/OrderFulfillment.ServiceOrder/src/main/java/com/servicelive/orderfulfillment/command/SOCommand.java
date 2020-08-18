package com.servicelive.orderfulfillment.command;

import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.orderfulfillment.common.IWalletGateway;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;
import com.servicelive.orderfulfillment.dao.IServiceOrderProcessDao;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

// place for convenience methods useful for all commands
// - get SO
// - get order
// - ...
public abstract class SOCommand implements ISOCommand {
	protected final Logger logger = Logger.getLogger(getClass());
	protected IServiceOrderDao serviceOrderDao;
	protected IServiceOrderProcessDao serviceOrderProcessDao;
	protected IWalletGateway walletGateway;

    public abstract void execute(Map<String, Object> processVariables);

	public String getUserName(Map<String, Object> processVariables) {
		if (!processVariables.containsKey(ProcessVariableUtil.PVKEY_USER_NAME)) {
			logger.error("getUserName - no user name variable present");
			throw new ServiceOrderException("getUserName - no user name variable present");
		}
		return (String)processVariables.get(ProcessVariableUtil.PVKEY_USER_NAME);
	}

	public String getSoId(Map<String, Object> processVariables) {
		if (!ProcessVariableUtil.mapContainsOrderId(processVariables)) {
			logger.error("getSoId - no order variable present");
			return null;
		}
		return ProcessVariableUtil.extractServiceOrderId(processVariables);
	}

	public ServiceOrder getServiceOrder(Map<String, Object> processVariables) {
		String soId = getSoId(processVariables);
		if (soId == null) {
			logger.error("getServiceOrder - no order available for So ID: " + soId);
			throw new ServiceOrderException("getServiceOrder - no order available for So ID: " + soId);
		}
		return serviceOrderDao.getServiceOrder(soId);
	}
	
	public void setServiceOrderDao(IServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}
	public void setServiceOrderProcessDao(
			IServiceOrderProcessDao serviceOrderProcessDao) {
		this.serviceOrderProcessDao = serviceOrderProcessDao;
	}
	public void setGateWay(IWalletGateway gateWay) {
		this.walletGateway = gateWay;
	}

//	public IServiceOrderDao getServiceOrderDao() {
//		return serviceOrderDao;
//	}

//	public IServiceOrderProcessDao getServiceOrderProcessDao() {
//		return serviceOrderProcessDao;
//	}

}