package com.newco.marketplace.persistence.daoImpl.so.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.business.businessImpl.so.pdf.SOPDFUtils;
import com.newco.marketplace.constants.Constants;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.criteria.DisplayTabCriteria;
import com.newco.marketplace.criteria.FilterCriteria;
import com.newco.marketplace.criteria.OrderCriteria;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.criteria.SearchCriteria;
import com.newco.marketplace.criteria.SearchWordsCriteria;
import com.newco.marketplace.criteria.SortCriteria;
import com.newco.marketplace.dto.vo.BuyerSkuVO;
import com.newco.marketplace.dto.vo.incident.AssociatedIncidentVO;
import com.newco.marketplace.dto.vo.powerbuyer.PBFilterVO;
import com.newco.marketplace.dto.vo.provider.ProviderFirmVO;
import com.newco.marketplace.dto.vo.provider.SearchFirmsResponseVO;
import com.newco.marketplace.dto.vo.provider.SearchFirmsVO;
import com.newco.marketplace.dto.vo.provider.ServiceOfferingsVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.dto.vo.serviceorder.SearchFilterVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceDatetimeSlot;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderMonitorVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchHelper;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTaskDetail;
import com.newco.marketplace.dto.vo.serviceorder.serviceOrderTabsVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerFeatureSetDAO;
import com.newco.marketplace.persistence.iDao.powerbuyer.IPowerBuyerFilterDao;
import com.newco.marketplace.persistence.iDao.so.order.IServiceOrderSearchDAO;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.ServiceOrderMonitorResultVO;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * $Revision: 1.35 $ $Author: akashya $ $Date: 2008/05/21 22:54:37 $
 */
public class ServiceOrderSearchDAOImpl extends ABaseImplDao implements
		IServiceOrderSearchDAO {

	private static final Logger logger = Logger
			.getLogger(ServiceOrderSearchDAOImpl.class.getName());

	private IPowerBuyerFilterDao powerBuyerDAO;

	private static final String SORT_COLUMN_KEY = "sort_column_key";
	private static final String SORT_ORDER_KEY = "sort_order_key";
	private IBuyerFeatureSetDAO buyerFeatureSetDao;
	
	
	
	public IBuyerFeatureSetDAO getBuyerFeatureSetDao() {
		return buyerFeatureSetDao;
	}

	public void setBuyerFeatureSetDao(IBuyerFeatureSetDAO buyerFeatureSetDao) {
		this.buyerFeatureSetDao = buyerFeatureSetDao;
	}

	public List<String> getServiceOrderBySoID(
			ServiceOrderSearchResultsVO soSearchVO) throws DataServiceException {
		
		List<String> soSearchList = new ArrayList<String>();
		try {
			Map<String, String> sort = ensureSort(soSearchVO.getSortColumnName(), soSearchVO.getSortOrder(), null);
			soSearchVO.setSortColumnName(sort.get(SORT_COLUMN_KEY));
			soSearchVO.setSortOrder(sort.get(SORT_ORDER_KEY));
			ServiceOrderSearchResultsVO soSearchResultVO = (ServiceOrderSearchResultsVO)queryForObject("soSearch.queryBySoID", soSearchVO);
			if (null != soSearchResultVO && StringUtils.isNotEmpty(soSearchResultVO.getSoId())) {
				soSearchList.add(soSearchResultVO.getSoId());
			}
		} catch (Exception ex) {
			String msg = "Unexpected error in ServiceOrderSearchDAOImpl.getServiceOrderBySoID(); query: soSearch.queryBySoID";
			logger.error(msg, ex);
			throw new DataServiceException(msg, ex);
		}
		return soSearchList;
	}

	public List<String> getServiceOrderByEndUserName(
			ServiceOrderSearchResultsVO soSearchVO) throws DataServiceException {

		List<String> results = new ArrayList<String>();
		try {
			Map<String, String> sort = ensureSort(soSearchVO.getSortColumnName(), soSearchVO.getSortOrder(), null);
			soSearchVO.setSortColumnName(sort.get(SORT_COLUMN_KEY));
			soSearchVO.setSortOrder(sort.get(SORT_ORDER_KEY));
			soSearchVO.setSearchByValue(StringUtils.lowerCase(soSearchVO.getSearchByValue().trim()));
			@SuppressWarnings("unchecked")
			List<ServiceOrderSearchResultsVO> soSearchList = (List<ServiceOrderSearchResultsVO>) queryForList(
					"soSearch.queryBySearchName", soSearchVO);
			for (ServiceOrderSearchResultsVO so : soSearchList) {
				results.add(so.getSoId());
			}
		} catch (Exception ex) {
			String msg = "Unexpected error in ServiceOrderSearchDAOImpl.getServiceOrderByEndUserName(); query: soSearch.queryBySearchName";
			logger.error(msg, ex);
			throw new DataServiceException(msg, ex);
		}

		return results;
	}

	@SuppressWarnings("unchecked")
	public List<ServiceOrderSearchResultsVO> getServiceOrderPagedResults(
			ServiceOrderSearchResultsVO soSearchVO, SearchCriteria searchCriteria)
			throws DataServiceException {
		if (soSearchVO.getSoIds().isEmpty()) {
			return null;
		}		
		List<ServiceOrderSearchResultsVO> soSearchList = null;
		
		if (!soSearchVO.getSoIds().isEmpty()) {
			try {
				Map<String, String> sort = ensureSort(soSearchVO.getSortColumnName(), soSearchVO.getSortOrder(), null);
				soSearchVO.setSortColumnName(sort.get(SORT_COLUMN_KEY));
				soSearchVO.setSortOrder(sort.get(SORT_ORDER_KEY));
				
				if(null != searchCriteria.getSearchType() && "8".equals(searchCriteria.getSearchType())){
					soSearchVO.setSortColumnName(OrderConstants.SORT_COLUMN_SOM_STATUS);
					soSearchVO.setSortOrder(OrderConstants.SORT_ORDER_ASC);
				}
				
				if(OrderConstants.SORT_COLUMN_SOM_SERVICEDATE.equalsIgnoreCase(soSearchVO.getSortColumnName())){
					soSearchVO.setServiceDateSort(true);
				}
				String groupId = searchCriteria.getGroupId();
				if (StringUtils.isNotBlank(groupId)) {
					soSearchVO.setGroupId(groupId);
				} else {
					// Log debug info for tab/page level search i.e. groupId = null
					logger.info(new StringBuilder("PagedResults: ORDER BY ")
						.append(soSearchVO.getSortColumnName()).append(" ").append(soSearchVO.getSortOrder()).toString());
				}
				
				// fix sort order for search by queue
				if (soSearchVO.getFilterId() != null && soSearchVO.getFilterId()!=0 ) {
					PBFilterVO filterVO = powerBuyerDAO.getFilterByFilterId(soSearchVO.getFilterId());
					if(null==soSearchVO.getSortColumnName()){
						soSearchVO.setSortColumnName("wsq." + filterVO.getSortByColumnName());
					}
					if(soSearchVO.getSortOrder()==null){
						soSearchVO.setSortOrder(filterVO.getSortOrder());
					}	
				}
				if(null != searchCriteria.getPageSize()&& null != searchCriteria.getPageLimit())
				{
					soSearchVO.setPageLimit(searchCriteria.getPageLimit());
					soSearchVO.setPageNumber(searchCriteria.getPageNumber());
					soSearchVO.setPageSize(searchCriteria.getPageSize());
				}
				soSearchList = (List<ServiceOrderSearchResultsVO>) queryForList(
						"soSearch.queryServiceOrdersByIds", soSearchVO);
				
				String assurantFeature = "";
				if(soSearchVO.getRoleId() != null && 
						(OrderConstants.BUYER_ROLEID == soSearchVO.getRoleId().intValue()) &&
						soSearchVO.getVendorId() != null) {
					assurantFeature = buyerFeatureSetDao.getFeature(soSearchVO.getVendorId(), "ASSURANT_ALERTS");
				}
				// for admin get the incident details, this is not the perfect way to do
				if(StringUtils.isNotBlank(assurantFeature) || (OrderConstants.NEWCO_ADMIN_ROLEID == soSearchVO.getRoleId().intValue())){
					for (ServiceOrderSearchResultsVO searchVO : soSearchList) {
						List<AssociatedIncidentVO> incidents = (List<AssociatedIncidentVO>)queryForList("somGrid.queryAssociatedIncidents.storeproc", searchVO);
						searchVO.setAssociatedIncidents(incidents);
					}
				}
				// changed code - start
				if(soSearchList.size() > 0 ){
					ProviderFirmVO providerFirmVO = null;
					String appendProviderFirm = null;
					String appendProviderFirmBusinessMob = null;
					logger.info("Number of rows :" + soSearchList.size());
					//Iterate through the list.
					//Get the associated firm details from the accepted resource Id
					for (ServiceOrderSearchResultsVO serviceOrderSearchResultsVO: soSearchList) {
						appendProviderFirm = "";
						appendProviderFirmBusinessMob = "";
					    if(null != serviceOrderSearchResultsVO.getAcceptedVendorId()){
					    	logger.info("So Id :" + serviceOrderSearchResultsVO.getSoId());
					    	logger.info("Accepted Vendor Id :" + serviceOrderSearchResultsVO.getAcceptedVendorId());
					    	logger.info("Accepted resource Id :" + serviceOrderSearchResultsVO.getAcceptedResourceId());
					    	
					    	//Fetch the vendor id and its details
					    	providerFirmVO = new ProviderFirmVO();
							if(null != serviceOrderSearchResultsVO.getAcceptedVendorId() && (serviceOrderSearchResultsVO.getSoStatus() == 150 || 
									serviceOrderSearchResultsVO.getSoStatus() == 155 || serviceOrderSearchResultsVO.getSoStatus() == 170
									|| serviceOrderSearchResultsVO.getSoStatus() == 180 ||  serviceOrderSearchResultsVO.getSoStatus() == 120
									|| serviceOrderSearchResultsVO.getSoStatus() == 165 )){
								try {
									//providerFirmVO = (ProviderFirmVO) queryForObject("getAcceptedFirmDetailsSOM.query", serviceOrderSearchResultsVO.getSoId() );
									providerFirmVO = (ProviderFirmVO) queryForObject("getAcceptedFirmDetls.query", serviceOrderSearchResultsVO.getAcceptedVendorId() );
								}catch (Exception ex) {
									logger.error(ex.getMessage(), ex);
									throw new DataServiceException(ex.getMessage(), ex);
								}
								
								logger.info("providerFirmVO.getVendorID()     :" + providerFirmVO.getVendorID());
								logger.info("providerFirmVO.getBusinessName() :" + providerFirmVO.getBusinessName());
								logger.info("providerFirmVO.getBusinessPhoneNumber() :" + providerFirmVO.getBusinessPhoneNumber());
								serviceOrderSearchResultsVO.setFirmBusinessName(providerFirmVO.getBusinessName());
								serviceOrderSearchResultsVO.setFirmBusinessPhoneNumber(providerFirmVO.getBusinessPhoneNumber());
								//serviceOrderSearchResultsVO.setAcceptedVendorId(providerFirmVO.getVendorID());
								
								appendProviderFirm = providerFirmVO.getBusinessName() + "(" + providerFirmVO.getVendorID() + ")";
								//appendProviderFirmBusinessMob =  providerFirmVO.getBusinessPhoneNumber() +  " (Main)";
								appendProviderFirmBusinessMob =  SOPDFUtils.
										formatPhoneNumber(providerFirmVO.getBusinessPhoneNumber()) +  " (Main)";
								serviceOrderSearchResultsVO.setFirmBusinessName(appendProviderFirm);
								serviceOrderSearchResultsVO.setFirmBusinessPhoneNumber(appendProviderFirmBusinessMob);
								//serviceOrderSearchResultsVO.setAcceptedVendorId(providerFirmVO.getVendorID());
							} else{
								serviceOrderSearchResultsVO.setFirmBusinessName("");
								serviceOrderSearchResultsVO.setFirmBusinessPhoneNumber("");
							}

					    }
					}
				}
				// changed code - end

			} catch (Exception ex) {
				logger.info("[ServiceOrderSearchDAOImpl.query - Exception] "
						+ StackTraceHelper.getStackTrace(ex));
				throw new DataServiceException("Error", ex);
			}
		}
		return soSearchList;
	}

	public List<String> getServiceOrderByPhoneNumber(
			ServiceOrderSearchResultsVO soSearchVO) throws DataServiceException{
		
		List<String> results = new ArrayList<String>();
		try {
			Map<String, String> sort = ensureSort(soSearchVO.getSortColumnName(), soSearchVO.getSortOrder(), null);
			soSearchVO.setSortColumnName(sort.get(SORT_COLUMN_KEY));
			soSearchVO.setSortOrder(sort.get(SORT_ORDER_KEY));
			@SuppressWarnings("unchecked")
			List<ServiceOrderSearchResultsVO> soSearchList = (List<ServiceOrderSearchResultsVO>) queryForList("soSearch.queryByPhoneNumber", soSearchVO);
			if (soSearchList != null) {
				for (ServiceOrderSearchResultsVO so : soSearchList) {
					results.add(so.getSoId());
				}
			}
		} catch (Exception ex) {
			String msg = "Unexpected error in ServiceOrderSearchDAOImpl.getServiceOrderByPhoneNumber(); query: soSearch.queryByPhoneNumber";
			logger.error(msg, ex);
			throw new DataServiceException(msg, ex);
		}
		return results;
	}

	public List<String> getServiceOrderByZipCode(
			ServiceOrderSearchResultsVO soSearchVO) throws DataServiceException {

		List<String> results = new ArrayList<String>();
		try {
			Map<String, String> sort = ensureSort(soSearchVO.getSortColumnName(), soSearchVO.getSortOrder(), null);
			soSearchVO.setSortColumnName(sort.get(SORT_COLUMN_KEY));
			soSearchVO.setSortOrder(sort.get(SORT_ORDER_KEY));
			@SuppressWarnings("unchecked")
			List<ServiceOrderSearchResultsVO> soSearchList = (List<ServiceOrderSearchResultsVO>) queryForList("soSearch.queryByZipCode", soSearchVO);
			if (soSearchList != null) {
				for (ServiceOrderSearchResultsVO so : soSearchList) {
					results.add(so.getSoId());
				}
			}
		} catch (Exception ex) {
			String msg = "Unexpected error in ServiceOrderSearchDAOImpl.getServiceOrderByZipCode(); query: soSearch.queryByZipCode";
			logger.error(msg, ex);
			throw new DataServiceException(msg, ex);
		}

		return results;
	}

	public List<String> getServiceOrderByAddress(ServiceOrderSearchResultsVO soSearchVO) throws DataServiceException {

		List<String> results = new ArrayList<String>();
		try {
			Map<String, String> sort = ensureSort(soSearchVO.getSortColumnName(), soSearchVO.getSortOrder(), null);
			soSearchVO.setSortColumnName(sort.get(SORT_COLUMN_KEY));
			soSearchVO.setSortOrder(sort.get(SORT_ORDER_KEY));
			soSearchVO.setSearchByValue("%"+soSearchVO.getSearchByValue().toLowerCase()+"%");
			@SuppressWarnings("unchecked")
			List<ServiceOrderSearchResultsVO> soSearchList = (List<ServiceOrderSearchResultsVO>) queryForList("soSearch.queryByAddress", soSearchVO);
			if (soSearchList != null) {
				for (ServiceOrderSearchResultsVO so : soSearchList) {
					results.add(so.getSoId());
				}
			}
		} catch (Exception ex) {
			String msg = "Unexpected error in ServiceOrderSearchDAOImpl.getServiceOrderByAddress(); query: soSearch.queryByAddress";
			logger.error(msg, ex);
			throw new DataServiceException(msg, ex);
		}

		return results;
	}

	@SuppressWarnings("unchecked")
	public List<String> getServiceOrderByTechnicianID(ServiceOrderSearchResultsVO soSearchVO) throws DataServiceException {
		List<String> results = new ArrayList<String>();
		try {
			Map<String, String> sort = ensureSort(soSearchVO.getSortColumnName(), soSearchVO.getSortOrder(), null);
			soSearchVO.setSortColumnName(sort.get(SORT_COLUMN_KEY));
			soSearchVO.setSortOrder(sort.get(SORT_ORDER_KEY));
			
			List<ServiceOrderSearchResultsVO> soSearchList = queryForList("soSearch.queryByTechnicianID", soSearchVO);
			if (soSearchList != null) {
				for (ServiceOrderSearchResultsVO so : soSearchList) {
					results.add(so.getSoId());
				}
			}
		} catch (Exception ex) {
			String msg = "Unexpected error in ServiceOrderSearchDAOImpl.getServiceOrderByTechnicianID(); query: soSearch.queryByTechnicianID";
			logger.error(msg, ex);
			throw new DataServiceException(msg, ex);
		}
		return results;
	}
	
	public List<String> getServiceOrderByProviderFirmID(ServiceOrderSearchResultsVO soSearchVO) throws DataServiceException {
		List<String> results = new ArrayList<String>();
		try {
			soSearchVO.setSortColumnName(OrderConstants.SORT_COLUMN_SOM_STATUS);
			soSearchVO.setSortOrder(OrderConstants.SORT_ORDER_ASC);			
			@SuppressWarnings("unchecked")
			List<ServiceOrderSearchResultsVO> soSearchList = (List<ServiceOrderSearchResultsVO>) queryForList("soSearch.queryByProviderFirmID", soSearchVO);
			if (soSearchList != null) {
				for (ServiceOrderSearchResultsVO so : soSearchList) {
					results.add(so.getSoId());
				}
			}
		} catch (Exception ex) {
			String msg = "Unexpected error in ServiceOrderSearchDAOImpl.getServiceOrderByProviderFirmID(); query: soSearch.queryByProviderFirmID";
			logger.error(msg, ex);
			throw new DataServiceException(msg, ex);
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	public List<String> getServiceOrderByTechnicianName(ServiceOrderSearchResultsVO soSearchVO) throws DataServiceException {
		
		List<String> results = new ArrayList<String>();
		try {
			Map<String, String> sort = ensureSort(soSearchVO.getSortColumnName(), soSearchVO.getSortOrder(), null);
			soSearchVO.setSortColumnName(sort.get(SORT_COLUMN_KEY));
			soSearchVO.setSortOrder(sort.get(SORT_ORDER_KEY));
			soSearchVO.setSearchByValue(StringUtils.lowerCase(soSearchVO.getSearchByValue().trim()));
			
			List<ServiceOrderSearchResultsVO> soSearchList = queryForList("soSearch.queryByTechnicianName", soSearchVO);
			if (soSearchList != null) {
				for (ServiceOrderSearchResultsVO so : soSearchList) {
					results.add(so.getSoId());
				}
			}
		} catch (Exception ex) {
			String msg = "Unexpected error in ServiceOrderSearchDAOImpl.getServiceOrderByTechnicianName(); query: soSearch.queryByTechnicianName";
			logger.error(msg, ex);
			throw new DataServiceException(msg, ex);
		}
		return results;
	}
	
	public List<ServiceOrderSearchResultsVO> getSObySubState(
			serviceOrderTabsVO request) throws DataServiceException {
		return null;
	}

	//NOT USED
	public ServiceOrderSearchResultsVO getBuyerUserName(
			ServiceOrderSearchResultsVO request) throws DataServiceException {
		ServiceOrderSearchResultsVO soNames = new ServiceOrderSearchResultsVO();
		soNames.getBuyerLastName();
		soNames.getBuyerLastName();
		return soNames=(ServiceOrderSearchResultsVO) queryForObject("soSearch.queryForBuyerNames", request);
	}

	//
	public List<ServiceOrderSearchResultsVO> getSObyWFStateForBatch(
			serviceOrderTabsVO request) throws DataServiceException {

		try {
			@SuppressWarnings("unchecked")
			List<ServiceOrderSearchResultsVO> soSearchList = (List<ServiceOrderSearchResultsVO>) queryForList("soSearch.queryByStatusForBatch", request);
			return soSearchList;
		} catch (Exception ex) {
			String msg = "Unexpected error in ServiceOrderSearchDAOImpl.getSObyWFState(); query: soSearch.queryByStatusForBatch";
			logger.error(msg, ex);
			throw new DataServiceException(msg, ex);
		}
	}
	
	public ServiceDatetimeSlot getDateTimeSlotForOrder(ServiceOrderSearchResultsVO serviceOrderSearchResultsVO)  throws DataServiceException{
		String serviceOrderID = serviceOrderSearchResultsVO.getSoId();
		ServiceDatetimeSlot serviceDatetimeSlot = (ServiceDatetimeSlot)queryForObject("getDatetimeSlotForBatchJob.query", serviceOrderID);
		return serviceDatetimeSlot;
	}
	public List<ServiceOrderSearchResultsVO> getSOforConditionalRouting(
			serviceOrderTabsVO request) throws DataServiceException {
		
		try {
			@SuppressWarnings("unchecked")
			List<ServiceOrderSearchResultsVO> soSearchList = (List<ServiceOrderSearchResultsVO>) queryForList("soSearch.queryForConditionalRouting", request);
			return soSearchList;
		} catch (Exception ex) {
			String msg = "Unexpected error in ServiceOrderSearchDAOImpl.getSOforConditionalRouting(); query: soSearch.queryForConditionalRouting";
			logger.error(msg, ex);
			throw new DataServiceException(msg, ex);
		}
	}

	public List<ServiceOrderSearchResultsVO> setBuyerAndProviderName(ServiceOrderSearchResultsVO soSearchVO,List<ServiceOrderSearchResultsVO> soSearchList)
	{
		ServiceOrderSearchHelper soHelper = new ServiceOrderSearchHelper();

		if (soSearchList != null && soSearchList.size() > 0) {
			soHelper.setSearchList(soSearchList);
			soHelper.setRoleType(soSearchVO.getRoleType());
			@SuppressWarnings("unchecked")
			List<ServiceOrderSearchHelper> soNames = (List<ServiceOrderSearchHelper>) queryForList("soSearch.queryForNames", soHelper);
			if (soNames != null) {
				for (int i = 0; i < soNames.size(); i++) {
					// Check if the Contact Location is of Buyer(i.e 30 ) or
					// Provider(i.e 50)
					if (soNames.get(i).getContactLocationTypeID().intValue() == 30
							|| soNames.get(i).getContactLocationTypeID()
									.intValue() == 50) {
						for (int j = 0; j < soSearchList.size(); j++) {
							if (soSearchList.get(j).getSoId().equals(
									soNames.get(i).getSoID())) {
								if (soNames.get(i).getContactLocationTypeID()
										.intValue() == 30) {
									soSearchList.get(j).setBuyerFirstName(
											soNames.get(i).getFirstName());
									soSearchList.get(j).setBuyerLastName(
											soNames.get(i).getLastName());
								} else if (soNames.get(i)
										.getContactLocationTypeID().intValue() == 50) {
									soSearchList.get(j).setProviderFirstName(
											soNames.get(i).getFirstName());
									soSearchList.get(j).setProviderLastName(
											soNames.get(i).getLastName());
								}
	
								soSearchList.get(j).setRoleType(soSearchVO.getRoleType());
								soSearchList.get(j).setSearchByType(soSearchVO.getSearchByType());
								soSearchList.get(j).setSearchByValue(soSearchVO.getSearchByValue());
							}
						}
					}
				}
			}
		}
		return soSearchList;
	}
	
	

	//SOM
	public ServiceOrderMonitorResultVO getServiceOrdersByStatusTypes(CriteriaMap criteraMap,boolean loadSOMswitch)
			throws DataServiceException{

		Integer roleId = null;
		String roleType = null;
		Integer statusCodes[] = null;
		String displayTab = null;

		ServiceOrderMonitorResultVO vo = new ServiceOrderMonitorResultVO();
		try {
			// Changes to accomodate Bulletin Board.
			DisplayTabCriteria dtc = (DisplayTabCriteria)criteraMap.get(OrderConstants.DISPLAY_TAB_CRITERIA_KEY);

			if(dtc!=null){
				displayTab = dtc.getDisplayTab();
			}

			if(displayTab!=null && displayTab.equals(OrderConstants.BULLETIN_BOARD_STRING)){
				// Re-Set paging criteria when its bulletin board.
				// This code should be removed once the paging is implemented for bulletin board.
				PagingCriteria pc = getPagingCritera(criteraMap);
				if(pc!=null){
					pc.setStartIndex(1);
					pc.setEndIndex(100);
					pc.setPageSize(100);
				}
			}

			ServiceOrderMonitorVO myServiceOrderVO = buildMonitorVO(criteraMap);
			if(criteraMap != null){
				OrderCriteria aOrderCriteria = (OrderCriteria)criteraMap.get(OrderConstants.ORDER_CRITERIA_KEY);
				roleId = aOrderCriteria.getRoleId();
				roleType = aOrderCriteria.getRoleType();
				statusCodes = aOrderCriteria.getStatusId();
			}				
			if(statusCodes != null && statusCodes.length > 0){
				myServiceOrderVO.setWorkFlowStatusIds(statusCodes);

				int statusCount = statusCodes.length;
				myServiceOrderVO.setRoutedTab(false);
				for(int i=0; i<statusCount; i++){
					if(statusCodes[i] != null && OrderConstants.ROUTED_STATUS == statusCodes[i]){
						logger.debug("getServiceOrdersByStatusTypes()-->RoutedTab");
						if(displayTab!=null && displayTab.equals(OrderConstants.BULLETIN_BOARD_STRING)){
							myServiceOrderVO.setBulletinBoardTab(true);
						}else{
							myServiceOrderVO.setRoutedTab(true);
						}
						break;
					}
				}
			}

			myServiceOrderVO.setRoleId(roleId);

			//BUYER

			if(OrderConstants.SORT_COLUMN_SOM_SERVICEDATE.equalsIgnoreCase(myServiceOrderVO.getSortColumnName())){
				myServiceOrderVO.setServiceDateSort(true);
			}
			if(roleId != null && OrderConstants.BUYER_ROLEID == roleId.intValue() || OrderConstants.SIMPLE_BUYER_ROLEID == roleId.intValue()){
				@SuppressWarnings("unchecked")
				
			
				List<ServiceOrderSearchResultsVO> al = new ArrayList<ServiceOrderSearchResultsVO>();
				if(loadSOMswitch){
					long start1 = System.currentTimeMillis();
					al = (List<ServiceOrderSearchResultsVO>)queryForList("somGrid.querySOByStatusesForBuyer", myServiceOrderVO);
					long end1 = System.currentTimeMillis();
					logger.info("Total time taken for new SOM query execution2: "+(end1 - start1));
				}else{
					long start2 = System.currentTimeMillis();
					al = (List<ServiceOrderSearchResultsVO>)queryForList("somGrid.querySOByStatusesForBuyerOld", myServiceOrderVO);
					long end2 = System.currentTimeMillis();
					logger.info("Total time taken for old SOM query execution2: "+(end2 - start2));
				}
				
				////SL-18830 Populate Firm details for all order which are accepted
				logger.info("Check if there are more than one row)");
				if(al.size() > 0){
					ProviderFirmVO providerFirmVO = null;
					String appendProviderFirm = null;
					String appendProviderFirmBusinessMob = null;
					logger.info("Number of rows :" + al.size());
					//Iterate through the list.
					//Get the associated firm details from the accepted resource Id
					for (ServiceOrderSearchResultsVO serviceOrderSearchResultsVO: al) {
						appendProviderFirm = "";
						appendProviderFirmBusinessMob = "";
					    if(null != serviceOrderSearchResultsVO.getAcceptedVendorId()){
					    	logger.info("So Id :" + serviceOrderSearchResultsVO.getSoId());
					    	logger.info("Accepted Vendor Id :" + serviceOrderSearchResultsVO.getAcceptedVendorId());
					    	logger.info("Accepted resource Id :" + serviceOrderSearchResultsVO.getAcceptedResourceId());
					    	
					    	//Fetch the vendor id and its details
					    	providerFirmVO = new ProviderFirmVO();
							if(null != serviceOrderSearchResultsVO.getAcceptedVendorId() && (serviceOrderSearchResultsVO.getSoStatus() == 150 || 
									serviceOrderSearchResultsVO.getSoStatus() == 155 || serviceOrderSearchResultsVO.getSoStatus() == 170
									|| serviceOrderSearchResultsVO.getSoStatus() == 180 ||  serviceOrderSearchResultsVO.getSoStatus() == 120
									|| serviceOrderSearchResultsVO.getSoStatus() == 165 )){
								try {
									//providerFirmVO = (ProviderFirmVO) queryForObject("getAcceptedFirmDetailsSOM.query", serviceOrderSearchResultsVO.getSoId() );
									providerFirmVO = (ProviderFirmVO) queryForObject("getAcceptedFirmDetls.query", serviceOrderSearchResultsVO.getAcceptedVendorId() );
								}catch (Exception ex) {
									logger.error(ex.getMessage(), ex);
									throw new DataServiceException(ex.getMessage(), ex);
								}
								
								logger.info("providerFirmVO.getVendorID()     :" + providerFirmVO.getVendorID());
								logger.info("providerFirmVO.getBusinessName() :" + providerFirmVO.getBusinessName());
								logger.info("providerFirmVO.getBusinessPhoneNumber() :" + providerFirmVO.getBusinessPhoneNumber());
								serviceOrderSearchResultsVO.setFirmBusinessName(providerFirmVO.getBusinessName());
								serviceOrderSearchResultsVO.setFirmBusinessPhoneNumber(providerFirmVO.getBusinessPhoneNumber());
								//serviceOrderSearchResultsVO.setAcceptedVendorId(providerFirmVO.getVendorID());
								
								appendProviderFirm = providerFirmVO.getBusinessName() + "(" + providerFirmVO.getVendorID() + ")";
								appendProviderFirmBusinessMob = SOPDFUtils.formatPhoneNumber(providerFirmVO.getBusinessPhoneNumber()) +  " (Main)";
								serviceOrderSearchResultsVO.setFirmBusinessName(appendProviderFirm);
								serviceOrderSearchResultsVO.setFirmBusinessPhoneNumber(appendProviderFirmBusinessMob);
								//serviceOrderSearchResultsVO.setAcceptedVendorId(providerFirmVO.getVendorID());
							}
							//check assignmentType is null or not.If null fetch it
							if(null==serviceOrderSearchResultsVO.getAssignmentType()){
								String assignmentType= (String) queryForObject("getAssignmentType.query",serviceOrderSearchResultsVO.getSoId());
								serviceOrderSearchResultsVO.setAssignmentType(assignmentType);
								
							}
					    }
					}
				}
				//SL-18830 End
				
				if(OrderConstants.BUYER_ROLEID == roleId.intValue()) {
					for (ServiceOrderSearchResultsVO searchVO : al) {
						@SuppressWarnings("unchecked")
						List<AssociatedIncidentVO> incidents = (List<AssociatedIncidentVO>)queryForList("somGrid.queryAssociatedIncidents.storeproc", searchVO);
						searchVO.setAssociatedIncidents(incidents);
					}
				}
				vo.setServiceOrderResults(al);
			}
			//PROVIDER
			if(roleId != null && OrderConstants.PROVIDER_ROLEID == roleId.intValue()){
				OrderCriteria oc = getOrderCritera( criteraMap );
				// SL-11776 The OrderCriteria should have been set much higher up in the hierachy, but this will force the issue.
				// This issue appears to only happen when going to the second page.
				if(displayTab!=null && (displayTab.equals("Bid") || displayTab.equals(OrderConstants.BID_REQUESTS_STRING))) {
					oc.setPriceModel(Constants.PriceModel.ZERO_PRICE_BID);
				}

				if (Constants.PriceModel.ZERO_PRICE_BID.equals(oc.getPriceModel()))
				{
					myServiceOrderVO.setBidTab(true);
					myServiceOrderVO.setRoutedTab(false);
				}


				if (myServiceOrderVO.isBidTab()) {
					@SuppressWarnings("unchecked")
					List<ServiceOrderSearchResultsVO> al = (List<ServiceOrderSearchResultsVO>)queryForList("somGrid.querySOByStatusesForProviderBidTab", myServiceOrderVO);
					for (ServiceOrderSearchResultsVO result : al) {

						@SuppressWarnings("unchecked")
						List<ServiceOrderTaskDetail> tasks = (List<ServiceOrderTaskDetail>) queryForList("somGrid.bidTab.tasks", result.getSoId());
						result.setTasks(tasks);
					}
					vo.setServiceOrderResults(al);
				}else if(myServiceOrderVO.isBulletinBoardTab()){
					@SuppressWarnings("unchecked")
					List<ServiceOrderSearchResultsVO> al = (List<ServiceOrderSearchResultsVO>)queryForList("somGrid.querySOByStatusesForProviderBulletinBoardTab", myServiceOrderVO);
					vo.setServiceOrderResults(al);
				}else if(displayTab.equalsIgnoreCase(OrderConstants.ACCEPTED_DESC)){
					@SuppressWarnings("unchecked")
					List<ServiceOrderSearchResultsVO> al = (List<ServiceOrderSearchResultsVO>)queryForList("somGrid.queryAcceptedSOForProvider", myServiceOrderVO);
					vo.setServiceOrderResults(al);
				}else{
					@SuppressWarnings("unchecked")
					List<ServiceOrderSearchResultsVO> al = (List<ServiceOrderSearchResultsVO>)queryForList("somGrid.querySOByStatusesForProvider", myServiceOrderVO);
					vo.setServiceOrderResults(al);
				}
			}

			ServiceOrderSearchResultsVO soSearchVO = new ServiceOrderSearchResultsVO();
			soSearchVO.setRoleType(roleType);
			@SuppressWarnings("unchecked")
			List<ServiceOrderSearchResultsVO> al = vo.getServiceOrderResults();
			al = setBuyerAndProviderName(soSearchVO,al);

			// Added to set resourceId in case of manageSO flag when provider only sees SOs assigned to him/her 
			if(myServiceOrderVO.isManageSOFlag() && !al.isEmpty()) {
				for(ServiceOrderSearchResultsVO result: al) {
					if(!result.getAvailableProviders().isEmpty()) {
						// there will be only one resource available in the provider list
						ProviderResultVO provider = result.getAvailableProviders().get(0); 
						result.setRoutedResourceId(Integer.toString(provider.getResourceId()));
						result.setProviderFirstName(provider.getProviderFirstName());
						result.setProviderLastName(provider.getProviderLastName());
						result.setDistanceInMiles(provider.getDistanceFromBuyer());
						result.setResStreet1(provider.getStreet1());
						result.setResStreet2(provider.getStreet2());
						result.setResCity(provider.getCity());
						result.setResStateCd(provider.getState());
						result.setZip(provider.getZip());
					}
				}

			}
		} 
		catch (Exception ex) {
			String msg = "Unexpected error in ServiceOrderSearchDAOImpl.getServiceOrdersByStatusTypes()";
			logger.error(msg, ex);
			throw new DataServiceException(msg, ex);
		}
		return vo;
	}

	
	
	
	
	
	
	
	
	
	protected ServiceOrderMonitorVO buildMonitorVO( CriteriaMap map )
	{
		FilterCriteria fc = getFilterCritera( map);
		SortCriteria   sc = getSortCritera(map);
		PagingCriteria pc = getPagingCritera(map);
		OrderCriteria oc = getOrderCritera( map );
		SearchWordsCriteria swc = getSearchWordsCritera(map);
		
		
		ServiceOrderMonitorVO myServiceOrderVO = new ServiceOrderMonitorVO();
		if(oc != null)
		{
			if( oc.getRoleId() != null && (OrderConstants.BUYER_ROLEID == oc.getRoleId().intValue() || OrderConstants.SIMPLE_BUYER_ROLEID == oc.getRoleId().intValue()))
			{
				myServiceOrderVO.setBuyerId(oc.getCompanyId().toString());
			}
			else if( oc.getRoleId() != null && OrderConstants.PROVIDER_ROLEID == oc.getRoleId().intValue() )
			{
				myServiceOrderVO.setVendorId(oc.getCompanyId());
			}
			
		}
		
		myServiceOrderVO.setSoSubStatus(oc.getSubStatusId());
		if(pc != null)
		{	
			myServiceOrderVO.setStartIndex(pc.getStartIndex()-1);
			myServiceOrderVO.setNumberOfRecords(pc.getPageSize());
			
		}
		
		Map<String, String> sort = ensureSort(sc, oc);
		myServiceOrderVO.setSortColumnName(sort.get(SORT_COLUMN_KEY));
		myServiceOrderVO.setSortOrder(sort.get(SORT_ORDER_KEY));
		
		if(fc != null) {
			if(fc.getPriceModel() != null && fc.getPriceModel().length() > 0) {
				if(fc.getPriceModel().equals(Constants.PriceModel.ZERO_PRICE_SEALED_BID)){
					myServiceOrderVO.setPriceModel(Constants.PriceModel.ZERO_PRICE_BID);
					myServiceOrderVO.setSearchSealedBid(true);					
				}else{
				myServiceOrderVO.setPriceModel(fc.getPriceModel());
					myServiceOrderVO.setFilterByPriceModal(true);
				}				
			} else {
				myServiceOrderVO.setPriceModel(null);
			}
			if(fc.getSubStatus() != null && fc.getSubStatus().length() > 0) {
				myServiceOrderVO.setSoSubStatus(new Integer(fc.getSubStatus()));
			} else {
				myServiceOrderVO.setSoSubStatus(null);
			}
			if(fc.getServiceProName() != null && fc.getServiceProName().length() >0 ){ 
            	myServiceOrderVO.setServiceProName(fc.getServiceProName()); 
            }else{ 
            	myServiceOrderVO.setServiceProName(null); 
            }
			
			if(fc.getBuyerRoleId() != null){ 
            	myServiceOrderVO.setBuyerRoleId(fc.getBuyerRoleId());
            }else{ 
            	myServiceOrderVO.setBuyerRoleId(null); 
            } 
			
            if(fc.getMarketName() != null && fc.getMarketName().length() >0 ){ 
            	myServiceOrderVO.setMarketName(fc.getMarketName()); 
            }else{ 
            	myServiceOrderVO.setMarketName(null); 
            } 

            myServiceOrderVO.setManageSOFlag(fc.isManageSOFlag());
            myServiceOrderVO.setResourceId(fc.getResourceId());
		}
		
		SearchCriteria searchCriteria = (SearchCriteria)map.get(OrderConstants.SEARCH_CRITERIA_KEY);
		if (searchCriteria != null) {
			String groupId = searchCriteria.getGroupId();
			if (StringUtils.isNotBlank(groupId)) {
				myServiceOrderVO.setGroupId(groupId);
			}
		}

		/**
		 * The search words are taken from the criteria and put in the VO.
		 */
		if (swc != null) {
			String searchWords = swc.getSearchWords();
			if (searchWords != null) {
				myServiceOrderVO.setSearchWords(searchWords);
			}
		}
		
		return myServiceOrderVO;
	}
	
	public List<ServiceOrderSearchResultsVO> getConditionalOffers(Timestamp currentDateTime) throws DataServiceException{
		try {
			@SuppressWarnings("unchecked")
			List<ServiceOrderSearchResultsVO> soSearchList = (List<ServiceOrderSearchResultsVO>) queryForList(
					"soSearch.queryConditionalOfferForExipration", currentDateTime);
			return soSearchList;
		} catch (Exception ex) {
			String msg = "Unexpected error in ServiceOrderSearchDAOImpl.getConditionalOffers(); query: soSearch.queryConditionalOfferForExipration";
			logger.error(msg, ex);
			throw new DataServiceException(msg, ex);
		}
	}
	/**
	 * ensureSort sets the database fields that sorting will be performed on based on 
	 * data in the input parameter.  A Map is returned containing the key and sort order 
	 * @param sortColumn
	 * @param sortOrder
	 * @param statusId
	 * @return
	 */
	protected Map<String, String> ensureSort (String sortColumn, String sortOrder, Integer statusId[]) {
		Map<String, String> sort = new HashMap<String, String>();
		boolean sortOrderSet = false;

		if( StringUtils.isNotEmpty(sortColumn) && !StringUtils.equals(sortColumn, OrderConstants.NULL_STRING) ) {
				if("status".equalsIgnoreCase(sortColumn)){
					sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_STATUS);
				} else if("SoId".equalsIgnoreCase(sortColumn)){
					//sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_SOID);
					sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_SO_GRP_ID);
				} else if("ServiceDate".equalsIgnoreCase(sortColumn)){
					sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_SERVICEDATE);
				} else if("SpendLimit".equalsIgnoreCase(sortColumn)){
					//sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_SPENDLIMIT);
					sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_SO_GRP_SPENDLIMIT);
				} else if("TimeToAppointment".equalsIgnoreCase(sortColumn)){
					sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_SERVICEDATE);
				} else if("AgeOfOrder".equalsIgnoreCase(sortColumn)){
					sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_CREATEDDATE);
				} else if("ProviderLastName".equalsIgnoreCase(sortColumn)){
					sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_ROUTED_RESOURCE_LAST_NAME);
				} else if("City".equalsIgnoreCase(sortColumn)){
					sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_CITY);
				} else if("SpendLimitTotal".equalsIgnoreCase(sortColumn)){
					sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_SPEND_LIMIT_TOTAL);					
				} else if("SERVICEDATE_TZ".equalsIgnoreCase(sortColumn)){
					sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_SERVICEDATE_TZ);
					sort.put(SORT_ORDER_KEY, OrderConstants.SORT_ORDER_ASC);					
				}else {
					sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_SERVICEDATE);
					sort.put(SORT_ORDER_KEY, OrderConstants.SORT_ORDER_DESC);
					sortOrderSet = true;
				}
		} else {
			sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_SERVICEDATE);
			sort.put(SORT_ORDER_KEY, OrderConstants.SORT_ORDER_DESC);
			sortOrderSet = true;
			
			//For Draft - default sort should be Created Date
			if(statusId != null && statusId.length > 0){
				int statusCount = statusId.length;
				for(int i=0; i<statusCount; i++){
					if(statusId[i] != null && OrderConstants.DRAFT_STATUS == statusId[i]){
						logger.debug("getServiceOrdersByStatusTypes()-->DraftTab");
						sort.put(SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_SOM_CREATEDDATE);
						break;
					}
				}
			}
		}
		
		if (!sortOrderSet) {
			if( StringUtils.isNotEmpty(sortOrder) && !StringUtils.equals(sortColumn, OrderConstants.NULL_STRING) ){
				sort.put(SORT_ORDER_KEY, sortOrder);
			} else {
				sort.put(SORT_ORDER_KEY, OrderConstants.SORT_ORDER_DESC);
			}
		}

		return sort;
	}
	
	/**
	 * ensureSort sets the database fields that sorting will be performed on based on 
	 * data in the input parameter.  A Map is returned containing the key and sort order  
	 * @param sortCriteria
	 * @param orderCriteria
	 * @return
	 */
	protected Map<String, String> ensureSort (SortCriteria sortCriteria, OrderCriteria orderCriteria ) {
		Map<String, String> sort;
		
		if (null != sortCriteria) {
			sort = ensureSort(sortCriteria.getSortColumnName(), sortCriteria.getSortOrder(), orderCriteria.getStatusId());
		} else{
			sort = ensureSort(StringUtils.EMPTY, StringUtils.EMPTY, orderCriteria.getStatusId());
		}
		
		return sort;
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
	
	/**
	 * Any free text search from bulletin board - nothing but search term.
	 * @param criteraMap
	 * @return
	 */
	private SearchWordsCriteria getSearchWordsCritera( CriteriaMap criteraMap )
	{
		if(criteraMap != null)
		{
			Object searchWords = criteraMap.get(OrderConstants.SEARCH_WORDS_CRITERIA_KEY);		
			return(SearchWordsCriteria) searchWords;
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

	@SuppressWarnings("unchecked")
	public List<String> getServiceOrderIDsByCustomRefID(
			ServiceOrderSearchResultsVO soSearchVO) throws DataServiceException {
		List<String> results = new ArrayList<String>();
		try {
			Map<String, String> sort = ensureSort(soSearchVO
					.getSortColumnName(), soSearchVO.getSortOrder(), null);
			soSearchVO.setSortColumnName(sort.get(SORT_COLUMN_KEY));
			soSearchVO.setSortOrder(sort.get(SORT_ORDER_KEY));
			
			List<ServiceOrderSearchResultsVO> soSearchList = queryForList("soSearch.queryByCustomRef", soSearchVO);
			if (soSearchList != null) {
				for (ServiceOrderSearchResultsVO so : soSearchList) {
					results.add(so.getSoId());
				}
			}
		} catch (Exception ex) {
			String msg = "Unexpected error in ServiceOrderSearchDAOImpl.getServiceOrderIDsByCustomRefID(); query: queryByCustomRef";
			logger.error(msg, ex);
			throw new DataServiceException(msg, ex);
		}
		return results; 
	}
	
	
	/**
	 *  (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.so.order.IServiceOrderSearchDAO#getNotScheduledSO()
	 */
	public List<ServiceOrderSearchResultsVO> getNotScheduledSO() throws DataServiceException {
		try {
			@SuppressWarnings("unchecked")
			List<ServiceOrderSearchResultsVO> soSearchList = (List<ServiceOrderSearchResultsVO>) queryForList("soSearch.queryNotScheduledSO", null);
			return soSearchList;
		} catch (Exception ex) {
			String msg = "Unexpected error in ServiceOrderSearchDAOImpl.getNotScheduledSO(); query: queryNotScheduledSO";
			logger.error(msg, ex);
			throw new DataServiceException(msg, ex);
		}
	}
	
	/**
	 * Method to  retrieve the list of service orders for the given check number
	 * @param soSearchVO
	 * @return List
	 * @throws DataServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<String> getServiceOrderByCheckNumber(ServiceOrderSearchResultsVO soSearchVO) 
			throws DataServiceException {
		List<String> results = new ArrayList<String>();
		try {
			Map<String, String> sort = ensureSort(soSearchVO
					.getSortColumnName(), soSearchVO.getSortOrder(), null);
			soSearchVO.setSortColumnName(sort.get(SORT_COLUMN_KEY));
			soSearchVO.setSortOrder(sort.get(SORT_ORDER_KEY));
			soSearchVO.setPaymentType(OrderConstants.UPSELL_PAYMENT_TYPE_CHECK);

			List<ServiceOrderSearchResultsVO> soSearchList = queryForList("soSearch.queryByCheckNumber", soSearchVO);
			if (soSearchList != null) {
				for (ServiceOrderSearchResultsVO so : soSearchList) {
					results.add(so.getSoId());
				}
			}
		} catch (Exception ex) {
			String msg = "Unexpected error in ServiceOrderSearchDAOImpl.getServiceOrderByCheckNumber(); query: soSearch.queryByCheckNumber";
			logger.error(msg, ex);
			throw new DataServiceException(msg, ex);
		}
		return results; 
	}
	/**
	 * Method to  retrieve the list of service orders for the given check number
	 * @param soSearchVO
	 * @return List
	 * @throws DataServiceException
	 */
	@SuppressWarnings("unchecked")
	public List<String> getServiceOrderByAdvanceSearch(ServiceOrderSearchResultsVO soSearchVO) 
			throws DataServiceException {
		List<String> results = new ArrayList<String>();
		List<ServiceOrderSearchResultsVO> soSearchList = new ArrayList<ServiceOrderSearchResultsVO>();
	
			Map<String, String> sort = ensureSort(soSearchVO
					.getSortColumnName(), soSearchVO.getSortOrder(), null);
			soSearchVO.setSortColumnName(sort.get(SORT_COLUMN_KEY));
			soSearchVO.setSortOrder(sort.get(SORT_ORDER_KEY));
			
			try{				
				List<String> soIds = soSearchVO.getSelectedServiceOrderIds();
				if(soIds!= null && !soIds.isEmpty()) {
					// SO id flow
					soSearchList = queryForList("soSearch.advanceSearchQuery", soSearchVO);
					if(soSearchList == null) {
						soSearchList = new ArrayList<ServiceOrderSearchResultsVO>();
					}
					
					soSearchVO.setCheckGroupedOrders(true);
					List<ServiceOrderSearchResultsVO> groupedSOSearchList = queryForList("soSearch.advanceSearchQuery", soSearchVO);
					soSearchVO.setCheckGroupedOrders(false);

					if(groupedSOSearchList != null && !groupedSOSearchList.isEmpty()) {
						soSearchList.addAll(groupedSOSearchList);
					}					
				} else {
					//Regular flow
					soSearchList = queryForList("soSearch.advanceSearchQuery", soSearchVO);	
				}
				
				
			if (soSearchList != null && !soSearchList.isEmpty()) {
				for (ServiceOrderSearchResultsVO so : soSearchList) {
					results.add(so.getSoId());					
				}
			}
			logger.debug("FINAL QUERY RESULTS -------###"+results.size()+ "----------->"+results);
			}catch (Exception ex) {
					logger.debug("Exception in Adv Search Query::::"+ex);
			}			
			return results; 
	}
	public SearchFilterVO saveSearchFilter(SearchFilterVO searchFilterVO)throws DataServiceException{
		Integer id = null;
		try {
			id = (Integer) getSqlMapClient().insert("insert.search.filter", searchFilterVO);
			if(null != id){
				searchFilterVO.setSearchFilterId(id);
			}
		}catch(Exception e){
			logger.debug("Exception in saving search filter");
		}
		return searchFilterVO;
	}
	public List<SearchFilterVO> getSearchFilters(SearchFilterVO searchFilterVO)throws DataServiceException{
		List<SearchFilterVO> savedFilters = new ArrayList<SearchFilterVO>();
		try {
			savedFilters = queryForList("query.search.filter", searchFilterVO);
			
		}catch(Exception e){
			logger.debug("Exception in saving search filter");
		}
		return savedFilters;
	}
	public SearchFilterVO getSelectedSearchFilter(SearchFilterVO searchFilterVO) throws DataServiceException{
		SearchFilterVO savedFilter = new SearchFilterVO();
		try {
			savedFilter = (SearchFilterVO)queryForObject("query.selected.search.filter", searchFilterVO);
			
		}catch(Exception e){
			logger.debug("Exception in getting search filter");
		}
		return savedFilter;
	}
	public SearchFilterVO deleteSearchFilter(SearchFilterVO searchFilterVO)throws DataServiceException{
		
		try {
			getSqlMapClient().update("delete.search.filter", searchFilterVO);
			
		}catch(Exception e){
			logger.debug("Exception in saving search filter");
		}
		return searchFilterVO;
	}
	public void setPowerBuyerDAO(IPowerBuyerFilterDao powerBuyerDAO) {
		this.powerBuyerDAO = powerBuyerDAO;
}
	
	public List<String> getAutocloseFailedByRule(ServiceOrderSearchResultsVO soSearchVO){
		List<String> results = new ArrayList<String>();
		
			Map<String, String> sort = ensureSort(soSearchVO
					.getSortColumnName(), soSearchVO.getSortOrder(), null);
			soSearchVO.setSortColumnName(sort.get(SORT_COLUMN_KEY));
			soSearchVO.setSortOrder(sort.get(SORT_ORDER_KEY));
			try{
			List<ServiceOrderSearchResultsVO> soSearchList = queryForList("soSearch.autocloseFailedByRule", soSearchVO);			
			if (soSearchList != null) {
				for (ServiceOrderSearchResultsVO so : soSearchList) {
					results.add(so.getSoId());					
				}
			}
			
			}catch (Exception ex) {
					logger.debug("Exception in autoclose Search Query::::"+ex);
			}			
			return results;
		
	}
	
	public List<ServiceOrderSearchResultsVO> getServiceOrdersProviderPhones(
			Map<String, Object> paramMap){
		List<ServiceOrderSearchResultsVO> soSearchList = new ArrayList<ServiceOrderSearchResultsVO>();
		try{
			soSearchList = queryForList("somGrid.providerphonebysoid", paramMap);			
		
			}catch (Exception ex) {
					logger.debug("Exception in autoclose Search Query::::"+ex);
			}			
			return soSearchList;
	}

	public ProviderFirmVO getAcceptedFirmDetails(String soId) {
		ProviderFirmVO providerFirmVO = new ProviderFirmVO();
		try {
			providerFirmVO = (ProviderFirmVO) queryForObject("getAcceptedFirmDetailsSOM.query", soId);
		} catch (Exception ex) {
			logger.info("Exception in getting Firm level Details");
		}
		return providerFirmVO;
	}
	
	//R12_1
	//SL-20379
	public ServiceOrderMonitorResultVO getServiceOrdersByStatusTypes(CriteriaMap criteraMap, String tab,boolean loadSOMswitch)throws DataServiceException{

		Integer roleId = null;
		String roleType = null;
		Integer statusCodes[] = null;
		String displayTab = null;
		
		ServiceOrderMonitorResultVO vo = new ServiceOrderMonitorResultVO();
		try {
			// Changes to accomodate Bulletin Board.
			DisplayTabCriteria dtc = (DisplayTabCriteria)criteraMap.get(OrderConstants.DISPLAY_TAB_CRITERIA_KEY);
		
			if(dtc!=null){
				displayTab = dtc.getDisplayTab();
			}
		
			if(displayTab!=null && displayTab.equals(OrderConstants.BULLETIN_BOARD_STRING)){
				// Re-Set paging criteria when its bulletin board.
				// This code should be removed once the paging is implemented for bulletin board.
				PagingCriteria pc = getPagingCritera(criteraMap);
				if(pc!=null){
					pc.setStartIndex(1);
					pc.setEndIndex(100);
					pc.setPageSize(100);
				}
			}
		
			ServiceOrderMonitorVO myServiceOrderVO = buildMonitorVO(criteraMap, tab);
			if(criteraMap != null){
				OrderCriteria aOrderCriteria = (OrderCriteria)criteraMap.get(OrderConstants.ORDER_CRITERIA_KEY+"_"+tab);
				roleId = aOrderCriteria.getRoleId();
				roleType = aOrderCriteria.getRoleType();
				statusCodes = aOrderCriteria.getStatusId();
			}				
			if(statusCodes != null && statusCodes.length > 0){
				myServiceOrderVO.setWorkFlowStatusIds(statusCodes);
		
				int statusCount = statusCodes.length;
				myServiceOrderVO.setRoutedTab(false);
				for(int i=0; i<statusCount; i++){
					if(statusCodes[i] != null && OrderConstants.ROUTED_STATUS == statusCodes[i]){
						logger.debug("getServiceOrdersByStatusTypes()-->RoutedTab");
						if(displayTab!=null && displayTab.equals(OrderConstants.BULLETIN_BOARD_STRING)){
							myServiceOrderVO.setBulletinBoardTab(true);
						}else{
							myServiceOrderVO.setRoutedTab(true);
						}
						break;
					}
				}
			}
		
			myServiceOrderVO.setRoleId(roleId);
		
			//BUYER
		
			if(OrderConstants.SORT_COLUMN_SOM_SERVICEDATE.equalsIgnoreCase(myServiceOrderVO.getSortColumnName())){
				myServiceOrderVO.setServiceDateSort(true);
			}
			if(roleId != null && OrderConstants.BUYER_ROLEID == roleId.intValue() || OrderConstants.SIMPLE_BUYER_ROLEID == roleId.intValue()){
				@SuppressWarnings("unchecked")
				List<ServiceOrderSearchResultsVO> al= new ArrayList<ServiceOrderSearchResultsVO>();
				//boolean switchSOM=false;
				if(loadSOMswitch){
					long start1 = System.currentTimeMillis();
					al = (List<ServiceOrderSearchResultsVO>)queryForList("somGrid.querySOByStatusesForBuyer", myServiceOrderVO);
					long end1 = System.currentTimeMillis();
					logger.info("Total time taken for new SOM query execution1: "+(end1 - start1));
				}else{
					long start2 = System.currentTimeMillis();
					al = (List<ServiceOrderSearchResultsVO>)queryForList("somGrid.querySOByStatusesForBuyerOld", myServiceOrderVO);
					long end2 = System.currentTimeMillis();
					logger.info("Total time taken for old SOM query execution1: "+(end2 - start2));
				}
				
				////SL-18830 Populate Firm details for all order which are accepted
				logger.info("Check if there are more than one row)");
				if(al.size() > 0){
					ProviderFirmVO providerFirmVO = null;
					String appendProviderFirm = null;
					String appendProviderFirmBusinessMob = null;
					logger.info("Number of rows :" + al.size());
					//Iterate through the list.
					//Get the associated firm details from the accepted resource Id
					for (ServiceOrderSearchResultsVO serviceOrderSearchResultsVO: al) {
						appendProviderFirm = "";
						appendProviderFirmBusinessMob = "";
					    if(null != serviceOrderSearchResultsVO.getAcceptedVendorId()){
					    	logger.info("So Id :" + serviceOrderSearchResultsVO.getSoId());
					    	logger.info("Accepted Vendor Id :" + serviceOrderSearchResultsVO.getAcceptedVendorId());
					    	logger.info("Accepted resource Id :" + serviceOrderSearchResultsVO.getAcceptedResourceId());
					    	
					    	//Fetch the vendor id and its details
					    	providerFirmVO = new ProviderFirmVO();
							if(null != serviceOrderSearchResultsVO.getAcceptedVendorId() && (serviceOrderSearchResultsVO.getSoStatus() == 150 || 
									serviceOrderSearchResultsVO.getSoStatus() == 155 || serviceOrderSearchResultsVO.getSoStatus() == 170
									|| serviceOrderSearchResultsVO.getSoStatus() == 180 ||  serviceOrderSearchResultsVO.getSoStatus() == 120
									|| serviceOrderSearchResultsVO.getSoStatus() == 165 )){
								try {
									//providerFirmVO = (ProviderFirmVO) queryForObject("getAcceptedFirmDetailsSOM.query", serviceOrderSearchResultsVO.getSoId() );
									providerFirmVO = (ProviderFirmVO) queryForObject("getAcceptedFirmDetls.query", serviceOrderSearchResultsVO.getAcceptedVendorId() );
								}catch (Exception ex) {
									logger.error(ex.getMessage(), ex);
									throw new DataServiceException(ex.getMessage(), ex);
								}
								
								logger.info("providerFirmVO.getVendorID()     :" + providerFirmVO.getVendorID());
								logger.info("providerFirmVO.getBusinessName() :" + providerFirmVO.getBusinessName());
								logger.info("providerFirmVO.getBusinessPhoneNumber() :" + providerFirmVO.getBusinessPhoneNumber());
								serviceOrderSearchResultsVO.setFirmBusinessName(providerFirmVO.getBusinessName());
								serviceOrderSearchResultsVO.setFirmBusinessPhoneNumber(providerFirmVO.getBusinessPhoneNumber());
								//serviceOrderSearchResultsVO.setAcceptedVendorId(providerFirmVO.getVendorID());
								
								appendProviderFirm = providerFirmVO.getBusinessName() + "(" + providerFirmVO.getVendorID() + ")";
								appendProviderFirmBusinessMob = SOPDFUtils.formatPhoneNumber(providerFirmVO.getBusinessPhoneNumber()) +  " (Main)";
								serviceOrderSearchResultsVO.setFirmBusinessName(appendProviderFirm);
								serviceOrderSearchResultsVO.setFirmBusinessPhoneNumber(appendProviderFirmBusinessMob);
								//serviceOrderSearchResultsVO.setAcceptedVendorId(providerFirmVO.getVendorID());
							}
							//check assignmentType is null or not.If null fetch it
							if(null==serviceOrderSearchResultsVO.getAssignmentType()){
								String assignmentType= (String) queryForObject("getAssignmentType.query",serviceOrderSearchResultsVO.getSoId());
								serviceOrderSearchResultsVO.setAssignmentType(assignmentType);
								
							}
					    }
					}
				}
				//SL-18830 End
				
				if(OrderConstants.BUYER_ROLEID == roleId.intValue()) {
					for (ServiceOrderSearchResultsVO searchVO : al) {
						@SuppressWarnings("unchecked")
						List<AssociatedIncidentVO> incidents = (List<AssociatedIncidentVO>)queryForList("somGrid.queryAssociatedIncidents.storeproc", searchVO);
						searchVO.setAssociatedIncidents(incidents);
					}
				}
				vo.setServiceOrderResults(al);
			}
			//PROVIDER
			if(roleId != null && OrderConstants.PROVIDER_ROLEID == roleId.intValue()){
				OrderCriteria oc = getOrderCritera( criteraMap, tab );
				// SL-11776 The OrderCriteria should have been set much higher up in the hierachy, but this will force the issue.
				// This issue appears to only happen when going to the second page.
				if(displayTab!=null && (displayTab.equals("Bid") || displayTab.equals(OrderConstants.BID_REQUESTS_STRING))) {
					oc.setPriceModel(Constants.PriceModel.ZERO_PRICE_BID);
				}
		
				if (Constants.PriceModel.ZERO_PRICE_BID.equals(oc.getPriceModel()))
				{
					myServiceOrderVO.setBidTab(true);
					myServiceOrderVO.setRoutedTab(false);
				}
		
		
				if (myServiceOrderVO.isBidTab()) {
					@SuppressWarnings("unchecked")
					List<ServiceOrderSearchResultsVO> al = (List<ServiceOrderSearchResultsVO>)queryForList("somGrid.querySOByStatusesForProviderBidTab", myServiceOrderVO);
					for (ServiceOrderSearchResultsVO result : al) {
		
						@SuppressWarnings("unchecked")
						List<ServiceOrderTaskDetail> tasks = (List<ServiceOrderTaskDetail>) queryForList("somGrid.bidTab.tasks", result.getSoId());
						result.setTasks(tasks);
					}
					vo.setServiceOrderResults(al);
				}else if(myServiceOrderVO.isBulletinBoardTab()){
					@SuppressWarnings("unchecked")
					List<ServiceOrderSearchResultsVO> al = (List<ServiceOrderSearchResultsVO>)queryForList("somGrid.querySOByStatusesForProviderBulletinBoardTab", myServiceOrderVO);
					vo.setServiceOrderResults(al);
				}else if(displayTab.equalsIgnoreCase(OrderConstants.ACCEPTED_DESC)){
					@SuppressWarnings("unchecked")
					List<ServiceOrderSearchResultsVO> al = (List<ServiceOrderSearchResultsVO>)queryForList("somGrid.queryAcceptedSOForProvider", myServiceOrderVO);
					vo.setServiceOrderResults(al);
				}else{
					@SuppressWarnings("unchecked")
					List<ServiceOrderSearchResultsVO> al = (List<ServiceOrderSearchResultsVO>)queryForList("somGrid.querySOByStatusesForProvider", myServiceOrderVO);
					vo.setServiceOrderResults(al);
				}
			}
		
			ServiceOrderSearchResultsVO soSearchVO = new ServiceOrderSearchResultsVO();
			soSearchVO.setRoleType(roleType);
			@SuppressWarnings("unchecked")
			List<ServiceOrderSearchResultsVO> al = vo.getServiceOrderResults();
			al = setBuyerAndProviderName(soSearchVO,al);
		
			// Added to set resourceId in case of manageSO flag when provider only sees SOs assigned to him/her 
			if(myServiceOrderVO.isManageSOFlag() && !al.isEmpty()) {
				for(ServiceOrderSearchResultsVO result: al) {
					if(!result.getAvailableProviders().isEmpty()) {
						// there will be only one resource available in the provider list
						ProviderResultVO provider = result.getAvailableProviders().get(0); 
						result.setRoutedResourceId(Integer.toString(provider.getResourceId()));
						result.setProviderFirstName(provider.getProviderFirstName());
						result.setProviderLastName(provider.getProviderLastName());
						result.setDistanceInMiles(provider.getDistanceFromBuyer());
						result.setResStreet1(provider.getStreet1());
						result.setResStreet2(provider.getStreet2());
						result.setResCity(provider.getCity());
						result.setResStateCd(provider.getState());
						result.setZip(provider.getZip());
					}
				}
		
			}
		} 
		catch (Exception ex) {
			String msg = "Unexpected error in ServiceOrderSearchDAOImpl.getServiceOrdersByStatusTypes()";
			logger.error(msg, ex);
			throw new DataServiceException(msg, ex);
		}
		return vo;
		}
	
	//R12_1
	//SL-20379 : The following methods are added for resolving SOM session issue
	protected ServiceOrderMonitorVO buildMonitorVO( CriteriaMap map, String tab )
	{
		FilterCriteria fc = getFilterCritera( map, tab);
		SortCriteria   sc = getSortCritera(map, tab);
		PagingCriteria pc = getPagingCritera(map, tab);
		OrderCriteria oc = getOrderCritera( map, tab );
		SearchWordsCriteria swc = getSearchWordsCritera(map);
		
		
		ServiceOrderMonitorVO myServiceOrderVO = new ServiceOrderMonitorVO();
		if(oc != null)
		{
			if( oc.getRoleId() != null && (OrderConstants.BUYER_ROLEID == oc.getRoleId().intValue() || OrderConstants.SIMPLE_BUYER_ROLEID == oc.getRoleId().intValue()))
			{
				myServiceOrderVO.setBuyerId(oc.getCompanyId().toString());
			}
			else if( oc.getRoleId() != null && OrderConstants.PROVIDER_ROLEID == oc.getRoleId().intValue() )
			{
				myServiceOrderVO.setVendorId(oc.getCompanyId());
			}
			
		}
		
		myServiceOrderVO.setSoSubStatus(oc.getSubStatusId());
		if(pc != null)
		{	
			myServiceOrderVO.setStartIndex(pc.getStartIndex()-1);
			myServiceOrderVO.setNumberOfRecords(pc.getPageSize());
			
		}
		
		Map<String, String> sort = ensureSort(sc, oc);
		myServiceOrderVO.setSortColumnName(sort.get(SORT_COLUMN_KEY));
		myServiceOrderVO.setSortOrder(sort.get(SORT_ORDER_KEY));
		
		if(fc != null) {
			if(fc.getPriceModel() != null && fc.getPriceModel().length() > 0) {
				if(fc.getPriceModel().equals(Constants.PriceModel.ZERO_PRICE_SEALED_BID)){
					myServiceOrderVO.setPriceModel(Constants.PriceModel.ZERO_PRICE_BID);
					myServiceOrderVO.setSearchSealedBid(true);					
				}else{
				myServiceOrderVO.setPriceModel(fc.getPriceModel());
					myServiceOrderVO.setFilterByPriceModal(true);
				}				
			} else {
				myServiceOrderVO.setPriceModel(null);
			}
			if(fc.getSubStatus() != null && fc.getSubStatus().length() > 0) {
				myServiceOrderVO.setSoSubStatus(new Integer(fc.getSubStatus()));
			} else {
				myServiceOrderVO.setSoSubStatus(null);
			}
			if(fc.getServiceProName() != null && fc.getServiceProName().length() >0 ){ 
            	myServiceOrderVO.setServiceProName(fc.getServiceProName()); 
            }else{ 
            	myServiceOrderVO.setServiceProName(null); 
            }
			
			if(fc.getBuyerRoleId() != null){ 
            	myServiceOrderVO.setBuyerRoleId(fc.getBuyerRoleId());
            }else{ 
            	myServiceOrderVO.setBuyerRoleId(null); 
            } 
			
            if(fc.getMarketName() != null && fc.getMarketName().length() >0 ){ 
            	myServiceOrderVO.setMarketName(fc.getMarketName()); 
            }else{ 
            	myServiceOrderVO.setMarketName(null); 
            } 

            myServiceOrderVO.setManageSOFlag(fc.isManageSOFlag());
            myServiceOrderVO.setResourceId(fc.getResourceId());
		}
		
		SearchCriteria searchCriteria = (SearchCriteria)map.get(OrderConstants.SEARCH_CRITERIA_KEY+"_"+tab);
		if (searchCriteria != null) {
			String groupId = searchCriteria.getGroupId();
			if (StringUtils.isNotBlank(groupId)) {
				myServiceOrderVO.setGroupId(groupId);
			}
		}

		/**
		 * The search words are taken from the criteria and put in the VO.
		 */
		if (swc != null) {
			String searchWords = swc.getSearchWords();
			if (searchWords != null) {
				myServiceOrderVO.setSearchWords(searchWords);
			}
		}
		
		return myServiceOrderVO;
	}
	
	private FilterCriteria getFilterCritera( CriteriaMap criteraMap, String tab )
	{	
		if(criteraMap != null)
		{
			Object filterObj = criteraMap.get(OrderConstants.FILTER_CRITERIA_KEY+"_"+tab);
			return (FilterCriteria)filterObj;
		}
		return null;
	}
	
	private SortCriteria getSortCritera( CriteriaMap criteraMap, String tab )
	{
		if(criteraMap != null)
		{
			Object sortObj = criteraMap.get(OrderConstants.SORT_CRITERIA_KEY+"_"+tab);
			return (SortCriteria)sortObj;
		}
		return null;
	}
	
	private PagingCriteria getPagingCritera( CriteriaMap criteraMap, String tab )
	{
		if(criteraMap != null)
		{
			Object pageObj = criteraMap.get(OrderConstants.PAGING_CRITERIA_KEY+"_"+tab);		
			return(PagingCriteria) pageObj;
		}
		return null;
	}
	
	private OrderCriteria getOrderCritera( CriteriaMap criteraMap, String tab )
	{
		if(criteraMap != null)
		{
			Object orderObj = criteraMap.get(OrderConstants.ORDER_CRITERIA_KEY+"_"+tab);
			return(OrderCriteria) orderObj;
		}
		return null;
	}

	/*  SL-21308 : Standard Service Offerings Search API
	 */
	@SuppressWarnings("unchecked")
	public List<ServiceOfferingsVO> getSearchFirmsResult(
			SearchFirmsVO searchFirmsVO) throws DataServiceException {
		List<ServiceOfferingsVO> searchFirmsResponseList = null;
		try{
			searchFirmsResponseList = getSqlMapClient().queryForList("getServiceOfferings.query", searchFirmsVO);   
		}
		catch(Exception e){
			throw new DataServiceException("Exception in ServiceOrderSearchDaoImpl.getSearchFirmsResult.query() due to "	+ e.getMessage(), e);
		}
		return searchFirmsResponseList;
	}
	

	@SuppressWarnings("unchecked")
	public List<String> getZipList(String zip, String radius)
			throws DataServiceException {
		List<String> zipcodeList=null;
			try{
				Map<String,String> params = new HashMap<String, String>();
				params.put("zipCode", zip);
				params.put("radius", radius);
				zipcodeList=(ArrayList<String>)getSqlMapClient().queryForList("serviceOfferings.zipcodeList",params);
				return zipcodeList;
			} catch(Exception e) { 
				logger.debug("Exception in ServiceOrderSearchDaoImpl.zipcodeList due to "+e.getMessage()); 
				throw new DataServiceException(e.getMessage(),e);
			}	
	}

	@SuppressWarnings("unchecked")
	public List<ServiceOfferingsVO> getAvailableFirmSKUList(
			SearchFirmsVO searchFirmsVO) throws DataServiceException {
		List<ServiceOfferingsVO> availableFirmsSKUList = null;
		try{
			availableFirmsSKUList = getSqlMapClient().queryForList("getAvailableFirmsSKUList.query", searchFirmsVO);   
		}
		catch(Exception e){
			throw new DataServiceException("Exception in ServiceOrderSearchDaoImpl.getAvailableFirmSKUList.query() due to "	+ e.getMessage(), e);
		}
		return availableFirmsSKUList;
	}

	@SuppressWarnings("unchecked")
	public List<ServiceOfferingsVO> getOfferingAvailabilityList(
			SearchFirmsVO searchFirmsVO) throws DataServiceException {
		List<ServiceOfferingsVO> availablityList = null;
		try{
			availablityList = getSqlMapClient().queryForList("getOfferingAvailabilityList.query", searchFirmsVO);   
		}
		catch(Exception e){
			throw new DataServiceException("Exception in ServiceOrderSearchDaoImpl.getOfferingAvailabilityList.query() due to "	+ e.getMessage(), e);
		}
		return availablityList;
	}
	
	@SuppressWarnings("unchecked")
	public Map<Integer, BigDecimal> getAggregateRating(List<Integer> firmIds)throws DataServiceException{
		Map<Integer, BigDecimal> aggregateRating = null;
		try{
			aggregateRating = (Map<Integer, BigDecimal>)getSqlMapClient().queryForMap(
					"getProviderFirmRating.query", firmIds,
					"vendor_id", "rating");
		} 
		catch(Exception e){
			throw new DataServiceException("Exception in ServiceOrderSearchDaoImpl.getAggregateRating.query() due to "	+ e.getMessage(), e);
		}
		return aggregateRating;
	}

	@SuppressWarnings("unchecked")
	public Map<Long, String> getFirmNames (List<Integer> firmIds)throws DataServiceException{
		Map<Long, String> firmNames = null;
		try{
			firmNames = (Map<Long, String>)getSqlMapClient().queryForMap(
					"getProviderFirmNames.query", firmIds,
					"firmId", "businessName");
		} 
		catch(Exception e){
			throw new DataServiceException("Exception in ServiceOrderSearchDaoImpl.getFirmNames.query() due to "	+ e.getMessage(), e);
		}
		return firmNames;
	}

	
	
}

	

/*
 * Maintenance History
 * $Log: ServiceOrderSearchDAOImpl.java,v $
 * Revision 1.35  2008/05/21 22:54:37  akashya
 * I21 Merged
 *
 * Revision 1.34.2.3  2008/05/15 15:52:11  gjacks8
 * added simple buyer to buyer check
 *
 * Revision 1.34.2.2  2008/05/14 23:04:09  gjacks8
 * added simple buyer to use the buyer context
 *
 * Revision 1.34.2.1  2008/05/14 14:20:49  gjacks8
 * removed high-bit chars from comments
 *
 * Revision 1.34  2008/05/02 23:38:23  mkhair
 * Modified the method name from getNotScheduledPromisedDateSO() to getNotScheduledDeliveryDateSO()
 *
 * Revision 1.33  2008/05/02 21:23:47  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.32  2008/04/26 00:40:27  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.28.12.1  2008/04/23 11:42:18  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.31  2008/04/23 05:02:08  hravi
 * Reverting to build 247.
 *
 * Revision 1.29  2008/04/15 17:51:00  glacy
 * Shyam: Merged I18_THREE_ENH branch
 *
 * Revision 1.28.30.2  2008/04/28 23:17:40  mkhair
 * Changed method name from getNotRescheduledPromisedDateSO() to getNotScheduledPromisedDateSO().
 *
 * Revision 1.28.30.1  2008/04/24 22:09:06  mkhair
 * Added implementation for getNotRescheduledPromisedDateSO() method.
 *
 * Revision 1.28.24.1  2008/04/09 19:38:32  sgopin2
 * Sears00045181 - To add search mechanism by 'Provider Firm Id' in SOM->Search tab for buyer login
 *
 * Revision 1.28  2008/01/31 16:53:44  usawant
 * Story 22599: Added sorting for admin member search
 *
 * Revision 1.27  2008/01/03 15:20:28  bgangaj
 * Added method getConditionalOffers() to get the list of conditional offers
 *
 * Revision 1.26  2007/12/04 17:25:34  mhaye05
 * fixed search by soId and search by tech name
 *
 * Revision 1.25  2007/12/04 03:05:25  mhaye05
 * added logic for SOM data grid sorting
 *
 * Revision 1.24  2007/12/01 17:45:04  schavda
 * som queries - sorting
 *
 * Revision 1.23  2007/11/28 23:43:38  mhaye05
 * updated search for status filtering
 *
 * Revision 1.22  2007/11/27 22:45:34  mhaye05
 * updated for search pagination
 *
 * Revision 1.21  2007/11/27 00:23:49  mhaye05
 * updated for search paging
 *
 * Revision 1.20  2007/11/21 19:54:59  mhaye05
 * updated to match parameter change in parent class
 *
 * Revision 1.19  2007/11/20 22:15:36  mhaye05
 * added code to perform the lower function on query parameters instead of having it done in the SQL
 *
 * Revision 1.18  2007/11/20 19:37:42  mhaye05
 * updated for search by zip code
 *
 * Revision 1.17  2007/11/20 01:59:45  mhaye05
 * fixed methods for search by phone number and search by so id
 *
 * Revision 1.16  2007/11/17 18:42:32  mhaye05
 * changed method signatures so that the return type is a List instead of ArrayList
 *
 */
