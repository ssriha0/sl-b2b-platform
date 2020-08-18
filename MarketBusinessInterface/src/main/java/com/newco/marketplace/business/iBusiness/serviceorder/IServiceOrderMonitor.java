package com.newco.marketplace.business.iBusiness.serviceorder;

import java.util.ArrayList;

import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.serviceOrderTabsVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.ServiceOrderMonitorResultVO;

public interface IServiceOrderMonitor {

	public ServiceOrderMonitorResultVO getInactiveServiceOrders(CriteriaMap criteraMap) throws Exception;
	
	public ServiceOrderMonitorResultVO getAllServiceOrdersByCriteria( CriteriaMap criteraMap ) throws Exception;
	
	public ServiceOrderMonitorResultVO getServiceOrdersByStatusTypes( CriteriaMap criteraMap) throws Exception;
	
	public ArrayList<ServiceOrderSearchResultsVO> getBuyerPostedServiceOrder(CriteriaMap criteraMap) throws DataServiceException ;
	
	public ArrayList<ServiceOrderSearchResultsVO> getBuyerPostedServiceOrder(serviceOrderTabsVO request) throws DataServiceException ;
	
	public ArrayList getSOSubStatusForStatusId(int statusId, Integer roleId) ;
	public ArrayList getSOSubStatusProblemForStatusId(int statusId,Integer roleId);
	
}
