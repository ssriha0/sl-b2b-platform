package com.newco.marketplace.business.businessImpl.serviceorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.businessImpl.ABaseCriteriaHandler;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderMonitor;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderStatusVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSubStatusVO;
import com.newco.marketplace.dto.vo.serviceorder.serviceOrderTabsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.so.ServiceOrderMonitorDao;
import com.newco.marketplace.persistence.iDao.so.order.IServiceOrderSearchDAO;
import com.newco.marketplace.vo.PaginationVO;
import com.newco.marketplace.vo.ServiceOrderMonitorResultVO;

/**  
* ServiceOrderMonitor.java - ServiceOrderMonitor represents the services that are required to 
* 							provide data to Service Order Monitor application.    
* 
* $Revision: 1.17 $ $Author: glacy $ $Date: 2008/04/26 00:40:24 $ 
*/

public class ServiceOrderMonitorBO implements IServiceOrderMonitor {
	
	private static final Logger logger = Logger.getLogger(ServiceOrderMonitorBO.class.getName());
	private ServiceOrderMonitorDao serviceOrderMonitorDao ;
	private IServiceOrderSearchDAO soSearchDAO;


	public IServiceOrderSearchDAO getSoSearchDAO() {
		return soSearchDAO;
	}

	public void setSoSearchDAO(IServiceOrderSearchDAO soSearchDAO) {
		this.soSearchDAO = soSearchDAO;
	}
	/*
	 * This method returns all service orders that are inactive for the buyer. This method
	 * returns a maximum number of results set by the parameter pageSize( 25, 50 or 100). It accepts the
	 * a start index and an end Index that are received from the user request. The subStatus represents the 
	 * filter status chosen by the user apart from Inactive records 
	 * @param String buyerId
	 * @param String subStatus
	 * @param int startIndex
	 * @param int endIndex
	 * @param int pageSize
	 * @return ServiceOrderMonitorVO Pagination object
	 */	
	public ServiceOrderMonitorResultVO getInactiveServiceOrders(CriteriaMap criteriaMap) throws Exception {
	
		PaginationVO  pagination= null;
		ServiceOrderMonitorResultVO serviceOrderResults = null;
		ArrayList inactiveOrderList = null ;

		try {
			/*
			// The status Ids that represents inactive are 120, 125 and 130
			Integer validatedStatusCode[]=getValidatedStatusCodes(getOrderCriteria(criteriaMap).getStatusId());
			if(validatedStatusCode != null || validatedStatusCode.length > 0)
			{
				
			}
			
			if(criteriaMap != null)
			{
				doCommonQueryInit(criteriaMap);
			}
			
			// COMMON PAGINATION CODE
			int totalRecordCount = _getResultsSetCount("STATUS_COUNT", validatedStatusCode,getOrderCriteria(criteriaMap).getSubStatusId() );
			pagination = _getPaginationDetail(totalRecordCount, getClientPageSize(), getClientStartIndex(), getClientEndIndex());
			
			serviceOrderResults = new ServiceOrderMonitorResultVO();
			
			int startIndex=pagination.getStartIndex();
			int endIndex=pagination.getEndIndex();
			inactiveOrderList = 
					serviceOrderMonitorDao.getInactiveServiceOrders(getBuyerId(), new Integer(getSubStatus()).intValue(), startIndex, endIndex);
			
			pagination.setResultSetObjects(inactiveOrderList);
			
			serviceOrderResults.setPaginationVO(pagination);
			serviceOrderResults.setInactiveOrderList( inactiveOrderList);
			*/
		}
		catch(Exception e){
		
			
		}
		return serviceOrderResults;
	}
		
	public ServiceOrderMonitorResultVO getAllServiceOrdersByCriteria(CriteriaMap criteriaMap) throws Exception {
		
		PaginationVO  pagination= null;
		ServiceOrderMonitorResultVO serviceOrderResults = null;
		/*
		if(criteriaMap != null)
		{
			doCommonQueryInit(criteriaMap);
		}
		int totalRecordCount = _getResultsSetCount("STATUS_COUNT", getValidatedStatusCodes(getOrderCriteria(criteriaMap).getStatusId()),getOrderCriteria(criteriaMap).getSubStatusId());
		pagination 			 = _getPaginationDetail(totalRecordCount, 
													getClientPageSize(),
													getClientStartIndex(),
													getClientEndIndex());
		
		serviceOrderResults
					= serviceOrderMonitorDao.getAllServiceOrdersByCriteria(criteriaMap);
		
		serviceOrderResults.setPaginationVO(pagination);
		*/
		return serviceOrderResults;
		
	}

	
	public ArrayList<ServiceOrderSearchResultsVO>
			getBuyerPostedServiceOrder(CriteriaMap criteriaMap) throws DataServiceException {
		/*
		PaginationVO  pagination= null;
		ServiceOrderMonitorResultVO serviceOrderResults = null;
		if(criteriaMap != null)
		{
			doCommonQueryInit(criteriaMap);
		}
		int totalRecordCount = _getResultsSetCount("STATUS_COUNT", getValidatedStatusCodes(getOrderCriteria(criteriaMap).getStatusId()),
				getOrderCriteria(criteriaMap).getSubStatusId());
		pagination 			 = _getPaginationDetail(totalRecordCount, 
													getClientPageSize(),
													getClientStartIndex(),
													getClientEndIndex());
		
		serviceOrderResults
					= serviceOrderMonitorDao.getAllServiceOrdersByCriteria(criteriaMap);
		
		serviceOrderResults.setPaginationVO(pagination);
		*/
		return null;
	}

	public ServiceOrderMonitorResultVO getServiceOrdersByStatusTypes(CriteriaMap criteriaMap) throws Exception {
		
		PaginationVO  pagination= null;
		ServiceOrderMonitorResultVO serviceOrderResults = null;
		/*
		if(criteriaMap != null)
		{
			doCommonQueryInit(criteriaMap);
		}
		int totalRecordCount 	= _getResultsSetCount("STATUS_COUNT", getValidatedStatusCodes(getOrderCriteria(criteriaMap).getStatusId()),
				getOrderCriteria(criteriaMap).getSubStatusId());
		pagination 				= _getPaginationDetail(totalRecordCount, 
													   getClientPageSize(),
													   getClientStartIndex(),
													   getClientEndIndex());
		
		serviceOrderResults
			= serviceOrderMonitorDao.getAllServiceOrdersByCriteria(criteriaMap);
		
		
		serviceOrderResults.setPaginationVO(pagination);
		*/
		return serviceOrderResults;
	}
	
	public ArrayList<ServiceOrderSearchResultsVO> getBuyerPostedServiceOrder(serviceOrderTabsVO request) throws DataServiceException {
		ServiceOrderSearchResultsVO soVO = new ServiceOrderSearchResultsVO();
		
		soVO.setRoleType("BUYER");
		
		ArrayList<ServiceOrderSearchResultsVO> soSearchList1 = new ArrayList<ServiceOrderSearchResultsVO>();
		
		ServiceOrderSearchResultsVO singleSearchResult = new ServiceOrderSearchResultsVO();
		soSearchList1 = serviceOrderMonitorDao.getBuyerPostedServiceOrder(Integer.parseInt(request.getBuyerId()));
		soSearchList1 = (ArrayList)soSearchDAO.setBuyerAndProviderName(soVO,soSearchList1);

		
/*		int j = soSearchList1.size();
		for(int i=0; i<j; i++)
		{			
			singleSearchResult=getSoSearchDAO().getBuyerUserName(soSearchList1.get(i));
			soSearchList1.get(i).setBuyerFirstName(singleSearchResult.getBuyerFirstName());
			soSearchList1.get(i).setBuyerLastName(singleSearchResult.getBuyerLastName());
		
			
		}
*/		return soSearchList1;
	}
	
	
	public ArrayList getSOSubStatusForStatusId(int statusId, Integer roleId) {
		ArrayList serviceOrderStatusVOList = null;
		Integer validatedStatusIds[] = new ABaseCriteriaHandler().getValidatedStatusCodes(new Integer(statusId), roleId);
		try {
			serviceOrderStatusVOList = (ArrayList) getServiceOrderMonitorDao().getSOSubStatusForStatusId(validatedStatusIds);
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return serviceOrderStatusVOList;
	} 
	public ArrayList getSOSubStatusProblemForStatusId(int statusId,Integer roleId){
		ArrayList serviceOrderStatusVOList = null;
		Integer validatedStatusIds[] = new ABaseCriteriaHandler().getValidatedStatusCodes(new Integer(statusId), roleId);
		try {
			serviceOrderStatusVOList = (ArrayList) getServiceOrderMonitorDao().getSOSubStatusProblemForStatusId(validatedStatusIds);	
		} catch (DataServiceException e) {
			logger.info("Caught Exception and ignoring",e);
		}
		return serviceOrderStatusVOList;
	}
	
	public ServiceOrderMonitorDao getServiceOrderMonitorDao() {
		return serviceOrderMonitorDao;
	}
	
	
	public void setServiceOrderMonitorDao(
			ServiceOrderMonitorDao serviceOrderMonitorDao) {
		this.serviceOrderMonitorDao = serviceOrderMonitorDao;
	}


	public static void main(String h[]){
		ServiceOrderMonitorBO serviceOrderMonitor = (ServiceOrderMonitorBO)MPSpringLoaderPlugIn.getCtx().getBean("serviceOrderMonitorBO");
/*		try {
			ArrayList list= serviceOrderMonitor.getSOSubStatusForStatusId(9000);
			for (int o=0;o<list.size();o++){
				ServiceOrderStatusVO ss = (ServiceOrderStatusVO)list.get(o);
				System.out.println(ss.toString());
				
			}
		} catch(Exception s){s.printStackTrace();}
*/		
	}

	/**
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<ServiceOrderStatusVO> getAllSoStatus() throws BusinessServiceException {
		List<ServiceOrderStatusVO> soStatus = null;
		try{
			soStatus = serviceOrderMonitorDao.getAllSoStatus();
		}catch (Exception e) {
			logger.error("Exception in getAllSoStatus () getting all so status",e);
			throw new BusinessServiceException(e);
		}
		return soStatus;
	}

	public Map<Integer, List<ServiceOrderSubStatusVO>> getAllSubStatus(Integer[] statusIdArray)throws BusinessServiceException {
		Map<Integer, List<ServiceOrderSubStatusVO>> serviceOrderStatusVOMap = null;
		List<ServiceOrderStatusVO> serviceOrderStatusVOList =null;
		try{
		  serviceOrderStatusVOList =  getServiceOrderMonitorDao().getSOSubStatusForStatusId(statusIdArray);
			 if(null!= serviceOrderStatusVOList && !serviceOrderStatusVOList.isEmpty()){
				 serviceOrderStatusVOMap = new HashMap<Integer, List<ServiceOrderSubStatusVO>>();
				 for(int i=0;i< statusIdArray.length;i++){
					 Integer soStatusId = statusIdArray[i];
					 for(ServiceOrderStatusVO statusVO : serviceOrderStatusVOList){
						 if(null!= statusVO && null!= soStatusId && soStatusId.intValue()== statusVO.getStatusId()){
							 serviceOrderStatusVOMap.put(soStatusId, statusVO.getServiceOrderSubStatusVO());
						 }
					 }
				 }
			  }
		}catch (Exception e) {
			logger.error("Exception in getAllSubStatus() getting all so sub status",e);
			throw new BusinessServiceException(e);
		}
		return serviceOrderStatusVOMap;
	}

	


	

}
