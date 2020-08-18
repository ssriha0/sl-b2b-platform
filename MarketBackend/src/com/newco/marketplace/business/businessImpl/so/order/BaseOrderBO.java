package com.newco.marketplace.business.businessImpl.so.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.daoImpl.powerbuyer.PowerBuyerFilterDao;
import com.newco.marketplace.persistence.iDao.logging.ILoggingDao;
import com.newco.marketplace.persistence.iDao.pagination.PaginationDao;
import com.newco.marketplace.persistence.iDao.so.buyer.BuyerDao;
import com.newco.marketplace.persistence.iDao.so.order.IServiceOrderSearchDAO;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.business.ABaseBO;
import com.sears.os.service.ServiceConstants;

public abstract class BaseOrderBO extends ABaseBO implements OrderConstants,
		ServiceConstants {
	
	private BuyerDao buyerDao;
	private ServiceOrderDao serviceOrderDao;
	private ILoggingDao loggingDao;
	private IServiceOrderSearchDAO soSearchDAO;
	private PaginationDao paginationDao;
	private PowerBuyerFilterDao powerBuyerDAO;
	
	public ILoggingDao getLoggingDao() {
		return loggingDao;
	}
	public void setLoggingDao(ILoggingDao loggingDao) {
		this.loggingDao = loggingDao;
	}
		
	public BuyerDao getBuyerDao() {
		return buyerDao;
	}
	public void setBuyerDao(BuyerDao buyerDao) {
		this.buyerDao = buyerDao;
	}
		
	public ServiceOrderDao getServiceOrderDao() {
		return serviceOrderDao;
	}
	public void setServiceOrderDao(ServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}
		
	public IServiceOrderSearchDAO getSoSearchDAO() {
		return soSearchDAO;
	}
	public void setSoSearchDAO(IServiceOrderSearchDAO soSearchDAO) {
		this.soSearchDAO = soSearchDAO;
	}

	public boolean isAuthorizedBuyer(Integer buyerID,ServiceOrder serviceOrder){
		if(serviceOrder.getBuyer().getBuyerId() == null) {
			logger.error("The BuyerId in ServiceOrder is null - not authorized !!!");
			return false;
		} else if(serviceOrder.getBuyer().getBuyerId().equals(buyerID)) {
			return true;
		} else {
			logger.error("The BuyerId in ServiceOrder = ["+serviceOrder.getBuyer().getBuyerId() +
					"] is not matching with buyerId passed in business method = [" + buyerID + "] - not authorized !!!");
			return false;
		}
	}
	
	public boolean isAuthorizedProvider(Integer providerID,ServiceOrder serviceOrder){
		
		if(serviceOrder.getAcceptedVendorId() == null) {
			logger.error("The AcceptedVendorId in ServiceOrder is null - not authorized !!!");
			return false;
		}
		else if(serviceOrder.getAcceptedVendorId().equals(providerID)) {
			return true;
		} else {
			logger.error("The AcceptedVendorId in ServiceOrder = ["+serviceOrder.getAcceptedVendorId() +
					"] is not matching with providerId passed in business method = [" + providerID + "] - not authorized !!!");
			return false;
		}
	}
	
	public boolean isAuthorizedVendorResource(Integer resourceID,ServiceOrder serviceOrder){
		
		if(serviceOrder.getAcceptedResource().getResourceId() == null) {
			logger.error("The ResourceId in ServiceOrder is null - not authorized !!!");
			return false;
		} else if(serviceOrder.getAcceptedResource().getResourceId().equals(resourceID)) {
			return true;
		} else {
			logger.error("The ResourceId in ServiceOrder = ["+serviceOrder.getAcceptedResource().getResourceId() +
					"] is not matching with resourceId passed in business method = [" + resourceID + "] - not authorized !!!");
			return false;
		}
	}

	public boolean isRoutedResource(Integer resourceId, ArrayList<HashMap> routedSOList){
		for(int i=0; i<routedSOList.size(); i++) {
			HashMap hm = routedSOList.get(i);
			Integer resrceId = ((Long)hm.get("resource_id")).intValue();
			if(resrceId.equals(resourceId))
				return true;
		}
		return false;
	}
	
	public ProcessResponse setErrorMsg(ProcessResponse processResponse,String msg) {
		
		return setErrorMsg(processResponse, msg, null);
	}
	
	public ProcessResponse setErrorMsg(ProcessResponse processResponse,String msg,String serviceOrderID){
		processResponse.setCode(USER_ERROR_RC);
		processResponse.setSubCode(USER_ERROR_RC);
		List<String> arr = new ArrayList<String>();
		arr.add(msg);
		processResponse.setMessages(arr);
		processResponse.setObj(serviceOrderID);
		return processResponse;
	}
	public PaginationDao getPaginationDao() {
		return paginationDao;
	}
	public void setPaginationDao(PaginationDao paginationDao) {
		this.paginationDao = paginationDao;
	}
	public PowerBuyerFilterDao getPowerBuyerDAO() {
		return powerBuyerDAO;
	}
	public void setPowerBuyerDAO(PowerBuyerFilterDao powerBuyerDAO) {
		this.powerBuyerDAO = powerBuyerDAO;
	}
	
}
