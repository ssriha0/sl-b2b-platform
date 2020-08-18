package com.newco.marketplace.persistence.daoImpl.so.order;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.criteria.FilterCriteria;
import com.newco.marketplace.criteria.OrderCriteria;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.criteria.SortCriteria;
import com.newco.marketplace.dto.vo.provider.ProviderFirmVO;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderMonitorVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderStatusInput;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderStatusVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.so.ServiceOrderMonitorDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.ServiceOrderMonitorResultVO;
import com.newco.marketplace.vo.mobile.SOStatusVO;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * $Revision: 1.3 $ $Author: glacy $ $Date: 2008/04/26 00:40:26 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class ServiceOrderMonitorDaoImpl extends ABaseImplDao implements
		ServiceOrderMonitorDao {
	private static final Logger logger = Logger.getLogger(ServiceOrderMonitorDaoImpl.class.getName());

	public ArrayList getInactiveServiceOrders(String buyerId, int substatus,
			int startIndex, int endIndex) throws DataServiceException {
		ArrayList al = null;

		try {
			ServiceOrderMonitorVO myServiceOrderVO = new ServiceOrderMonitorVO();
			//myServiceOrderVO.setWorkFlowStatusIds(myStatus);
			myServiceOrderVO.setBuyerId(buyerId);
			myServiceOrderVO.setSoSubStatus(new Integer(substatus));
			myServiceOrderVO.setStartIndex(startIndex);
			myServiceOrderVO.setNumberOfRecords(endIndex - startIndex + 1);
			al = (ArrayList) queryForList("soSearch.queryByStatuses",
					myServiceOrderVO);
		} catch (Exception exception) {
			throw new DataServiceException(
					"ServiceOrderMonitorDao - getInactiveServiceOrders query Failed",
					exception);
		}
		return al;

	}

	//TODO: keep it
	public ServiceOrderMonitorResultVO getAllServiceOrdersByCriteria(
			CriteriaMap criteraMap) throws DataServiceException {
		logger.info(" Inside getAllServiceOrdersByCriteria()");
			List al = null;
			try {
				ServiceOrderMonitorVO myServiceOrderVO = buildMonitorVO(criteraMap);
				myServiceOrderVO.setWorkFlowStatusIds(null);
				al = queryForList("soServiceOrderGrid.queryServiceOrdersByStatuses",
						myServiceOrderVO);
			} catch (Exception exception) {
				throw new DataServiceException(
						"ServiceOrderMonitorDao - getAllServiceOrdersByCriteria query Failed",
						exception);
			}
			ServiceOrderMonitorResultVO vo = new ServiceOrderMonitorResultVO();
			vo.setServiceOrderResults(al);
			return vo;
	}

	
	public ArrayList<ServiceOrderSearchResultsVO> getBuyerPostedServiceOrder(
			Integer buyerId) throws DataServiceException {
		// TODO Auto-generated method stub

		ArrayList<ServiceOrderSearchResultsVO> soSearchList = new ArrayList<ServiceOrderSearchResultsVO>();
		try {
			soSearchList = (ArrayList<ServiceOrderSearchResultsVO>) queryForList(
					"soSearch.queryBuyerPosted", buyerId);
		} catch (Exception ex) {
			logger.info("[ServiceOrderSearchDAOImpl.query - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return soSearchList;
	}

	
	public ServiceOrderMonitorResultVO getInactiveServiceOrders(
			CriteriaMap criteraMap, ArrayList<Integer> statusCodes)
			throws DataServiceException {
		ArrayList al = null;
		try {
			ServiceOrderMonitorVO myServiceOrderVO = buildMonitorVO(criteraMap);
			Integer [] types = null;
			if(statusCodes != null)
			{
				types = statusCodes.toArray(new Integer[statusCodes.size()]);
				myServiceOrderVO.setWorkFlowStatusIds( types );
			}
			else
			{
				myServiceOrderVO.setWorkFlowStatusIds( null );
			}
			
			al = (ArrayList) queryForList("soSearch.queryByStatuses",
					myServiceOrderVO);
		} catch (Exception exception) {
			throw new DataServiceException(
					"ServiceOrderMonitorDao - getInactiveServiceOrders query Failed",
					exception);
		}
		ServiceOrderMonitorResultVO vo = new ServiceOrderMonitorResultVO();
		vo.setServiceOrderResults(al);
		return vo;
	}

	
	public ServiceOrderMonitorResultVO getServiceOrdersByStatusTypes(
										CriteriaMap criteraMap, ArrayList<Integer> statusCodes)
										throws DataServiceException
	{
		logger.info(" Inside getServiceOrdersByStatusTypes()");
		ArrayList<ServiceOrderSearchResultsVO> al = null;
		try {
			ServiceOrderMonitorVO myServiceOrderVO = buildMonitorVO(criteraMap);
			if(statusCodes != null && statusCodes.size() > 0)
			{
				myServiceOrderVO.setWorkFlowStatusIds( (Integer[])statusCodes.toArray(new Integer[statusCodes.size()]));
			}
			al = (ArrayList) 
					queryForList("soServiceOrderGrid.queryServiceOrdersByStatuses", myServiceOrderVO);
			
		} catch (Exception exception) {
			throw new DataServiceException(
					"ServiceOrderMonitorDao - getServiceOrdersByStatusTypes query Failed",
					exception);
		}
		ServiceOrderMonitorResultVO vo = new ServiceOrderMonitorResultVO();
		vo.setServiceOrderResults(al);
		return vo;
	}
	
	private FilterCriteria getFilterCritera( CriteriaMap criteraMap )
	{	
		if(criteraMap != null)
		{
			Object filterObj = criteraMap.get(OrderConstants.FILTER_CRITERIA_KEY);
			return (FilterCriteria)filterObj;
		}
		return null;
	}
	
	private SortCriteria getSortCritera( CriteriaMap criteraMap )
	{
		if(criteraMap != null)
		{
			Object sortObj = criteraMap.get(OrderConstants.SORT_CRITERIA_KEY);
			return (SortCriteria)sortObj;
		}
		return null;
	}
	
	private PagingCriteria getPagingCritera( CriteriaMap criteraMap )
	{
		if(criteraMap != null)
		{
			Object pageObj = criteraMap.get(OrderConstants.PAGING_CRITERIA_KEY);		
			return(PagingCriteria) pageObj;
		}
		return null;
	}
	
	private OrderCriteria getOrderCritera( CriteriaMap criteraMap )
	{
		if(criteraMap != null)
		{
			Object orderObj = criteraMap.get(OrderConstants.ORDER_CRITERIA_KEY);
			return(OrderCriteria) orderObj;
		}
		return null;
	}
	
	public ServiceOrderMonitorVO buildMonitorVO( CriteriaMap map )
	{
		FilterCriteria fc = getFilterCritera( map);
		SortCriteria   sc = getSortCritera(map);
		PagingCriteria pc = getPagingCritera(map);
		OrderCriteria oc = getOrderCritera( map );
		
		
		ServiceOrderMonitorVO myServiceOrderVO = new ServiceOrderMonitorVO();
		if(oc != null)
		{
			if( oc.getVendBuyerResId() != null )
			{
				myServiceOrderVO.setBuyerId(oc.getVendBuyerResId().toString());
			}
		}
		myServiceOrderVO.setSoSubStatus(oc.getSubStatusId());
		if(pc != null)
		{	
			myServiceOrderVO.setStartIndex(pc.getStartIndex());
			//myServiceOrderVO.set(pc.getStartIndex());
			myServiceOrderVO.setNumberOfRecords(pc.getPageSize());
			
		}
		if(sc != null)
		{
			//myServiceOrderVO.
		}
		if(fc != null)
		{
/*			if(fc.getStatus() != null)
			{
				myServiceOrderVO.setSoStatus( fc.getStatus() );
			}*/
			if(fc.getSubStatus() != null && fc.getSubStatus().length() > 0)
			{
				myServiceOrderVO.setSoSubStatus(new Integer(fc.getSubStatus()));
			}
			else
			{
				myServiceOrderVO.setSoSubStatus(null);
			}
		}
		return myServiceOrderVO;
	}
	
	public List getSOSubStatusForStatusId(Integer statusIds[]) throws DataServiceException {
		List serviceOrderStatusVOList = null;
		ServiceOrderStatusInput input = new ServiceOrderStatusInput();
		input.setStatusIds(statusIds);
		try {
			serviceOrderStatusVOList = (List)queryForList("statusSubstatus.query",
					input);
			
		} catch (Exception exception) {
			logger.error("ServiceOrderMonitorDao - getSOSubStatusForStatusId query Failed due to "+ StackTraceHelper.getStackTrace(exception));
			throw new DataServiceException("ServiceOrderMonitorDao - getSOSubStatusForStatusId query Failed",exception);
		}
		return serviceOrderStatusVOList;
		
	}
	public List getSOSubStatusProblemForStatusId(Integer statusIds[]) throws DataServiceException {
		List serviceOrderStatusVOList = null;
		ServiceOrderStatusInput input = new ServiceOrderStatusInput();
		input.setStatusIds(statusIds);
		try {
			serviceOrderStatusVOList = (List)queryForList("statusProblemSubstatus.query",
					input);
			
		} catch (Exception exception) {
			throw new DataServiceException(
					"ServiceOrderMonitorDao - getSOSubStatusForStatusId query Failed",
					exception);
		}
		return serviceOrderStatusVOList;
		
	}

	public List<ServiceOrderStatusVO> getAllSoStatus() throws DataServiceException {
		List<ServiceOrderStatusVO> soStatusList=null;
		try{
			soStatusList = (List<ServiceOrderStatusVO>)queryForList("getAllSoStatus.query");
		}catch (Exception e) {
			logger.error("ServiceOrderMonitorDao - getAllSoStatus query Failed due to "+ StackTraceHelper.getStackTrace(e));
			throw new DataServiceException("ServiceOrderMonitorDao - getAllSoStatus query Failed",e);
		}
		return soStatusList;
	}
	
}
/*
 * Maintenance History
 * $Log: ServiceOrderMonitorDaoImpl.java,v $
 * Revision 1.3  2008/04/26 00:40:26  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.1.28.1  2008/04/23 11:42:18  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.2  2008/04/23 05:02:08  hravi
 * Reverting to build 247.
 *
 * Revision 1.1  2007/12/04 15:09:17  mhaye05
 * moved packages
 *
 */