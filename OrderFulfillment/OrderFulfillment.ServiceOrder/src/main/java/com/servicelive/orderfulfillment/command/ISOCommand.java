package com.servicelive.orderfulfillment.command;

import java.util.Map;

import com.servicelive.orderfulfillment.common.IWalletGateway;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;
import com.servicelive.orderfulfillment.dao.IServiceOrderProcessDao;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public interface ISOCommand {
	public String getSoId(Map<String, Object> processVariables);
	public ServiceOrder getServiceOrder(Map<String, Object> processVariables) ;
	public String getUserName(Map<String, Object> processVariables);
	public void setServiceOrderDao(IServiceOrderDao serviceOrderDao);	
	public void setServiceOrderProcessDao(IServiceOrderProcessDao serviceOrderProcessDao);
	public void setGateWay(IWalletGateway walletGateway);
	public abstract void execute(Map<String, Object> processVariables);
//	public IServiceOrderDao getServiceOrderDao();
//	public IServiceOrderProcessDao getServiceOrderProcessDao();
//	public IWalletGateway getGateWay();
}
