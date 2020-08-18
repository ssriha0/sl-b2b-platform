package com.newco.marketplace.persistence.iDao.so;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderStatusVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.ServiceOrderMonitorResultVO;

public interface ServiceOrderMonitorDao {
	
	public ArrayList getInactiveServiceOrders(String buyerId, int substatus, int startIndex, int endIndex) throws DataServiceException;
	
	public ServiceOrderMonitorResultVO getInactiveServiceOrders(CriteriaMap criteraMap, ArrayList <Integer>statusCodes ) throws DataServiceException;
	
	public ServiceOrderMonitorResultVO getAllServiceOrdersByCriteria( CriteriaMap criteraMap ) throws DataServiceException;
	
	public ServiceOrderMonitorResultVO getServiceOrdersByStatusTypes( CriteriaMap criteraMap, ArrayList <Integer>statusCodes ) throws DataServiceException;

	public ArrayList<ServiceOrderSearchResultsVO> getBuyerPostedServiceOrder(Integer buyerId) throws DataServiceException;
	
	public List getSOSubStatusForStatusId(Integer statusIds[])throws DataServiceException;
	
	public List getSOSubStatusProblemForStatusId(Integer statusIds[])throws DataServiceException;

	public List<ServiceOrderStatusVO> getAllSoStatus()throws DataServiceException;;
	
}
