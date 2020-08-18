package com.newco.marketplace.persistence.daoImpl.powerbuyer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.dto.vo.BuyerQueueVO;
import com.newco.marketplace.dto.vo.WFMBuyerQueueVO;
import com.newco.marketplace.dto.vo.WFMQueueVO;
import com.newco.marketplace.dto.vo.WFMSOTasksVO;
import com.newco.marketplace.dto.vo.group.QueueTasksGroupVO;
import com.newco.marketplace.dto.vo.powerbuyer.ClaimVO;
import com.newco.marketplace.dto.vo.powerbuyer.PBBuyerFilterSummaryVO;
import com.newco.marketplace.dto.vo.powerbuyer.PBFilterVO;
import com.newco.marketplace.dto.vo.powerbuyer.PBSearchVO;
import com.newco.marketplace.dto.vo.powerbuyer.RequeueSOVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.powerbuyer.IPowerBuyerFilterDao;
import com.sears.os.dao.impl.ABaseImplDao;
/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.12 $ $Author: glacy $ $Date: 2008/04/26 00:40:28 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class PowerBuyerFilterDao extends ABaseImplDao implements
		IPowerBuyerFilterDao, OrderConstants {
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.powerbuyer.IPowerBuyerFilterDao#getBuyerFilterSummary(java.lang.Integer)
	 */
	public List<PBBuyerFilterSummaryVO> getBuyerFilterSummary(Integer buyerId, Boolean slAdminInd, Integer buyerRefTypeId, String buyerRefValue, String searchBuyerId)
			throws DataServiceException {
		
		if (null == buyerId) {
			throw new DataServiceException("Buyer Id cannot be null");
		}
		
		List<PBBuyerFilterSummaryVO> results;
		try {
			PBBuyerFilterSummaryVO vo = new PBBuyerFilterSummaryVO();
//			if(slAdminInd) {
//				vo.setBuyerId(LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION);
//			} else {
				vo.setBuyerId(buyerId);
//			}
			vo.setBuyerRefTypeId(buyerRefTypeId);
			vo.setBuyerRefValue(buyerRefValue);
			vo.setSearchBuyerId(searchBuyerId);
			results = queryForList("powerbuyer.query.getFilterCountsByBuyer", vo);
		} catch (DataAccessException e) {
			throw new DataServiceException(e.getMessage(),e);
		}

		return results;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.powerbuyer.IPowerBuyerFilterDao#getFiltersByBuyer(java.lang.Integer)
	 */
	public List<PBFilterVO> getFiltersByBuyer(Integer buyerId)
			throws DataServiceException {
		
		if (null == buyerId) {
			throw new DataServiceException("Buyer Id cannot be null");
		}
		
		List<PBFilterVO> results;
		try {
			results = queryForList("powerbuyer.query.getFiltersByBuyer", buyerId);
		} catch (DataAccessException e) {
			throw new DataServiceException(e.getMessage(),e);
		}
		
		return results;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.powerbuyer.IPowerBuyerFilterDao#loadFilterSummaryCounts(java.lang.Integer)
	 */
	public void loadFilterSummaryCounts(Integer buyerId) throws DataServiceException {
		
		try {
			// get all of the filters for this buyer
			List<PBFilterVO> filters = getFiltersByBuyer(buyerId);
			
			// loop through each buyer and load count into summary count table
			PBSearchVO searchVO = null;
			for(PBFilterVO vo : filters) {
				searchVO = new PBSearchVO();
				searchVO.setBuyerId(buyerId);
				searchVO.setViewName(vo.getDbQueryRoot());
				Integer records = new Integer(0);
				if (buyerId == LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION  && !vo.getDbQueryRoot().equals("vw_pb_queue_follow_up_admin")){
					records = (Integer) queryForObject("powerbuyer.admin.FilterCount", searchVO);
				}else if (buyerId == LedgerConstants.ENTITY_ID_SERVICELIVE_OPERATION && vo.getDbQueryRoot().equals("vw_pb_queue_follow_up_admin")){
					records = (Integer)queryForObject("powerbuyer.admin.FollowUpFilterCount", searchVO);
				}else{
					records = (Integer) queryForObject("powerbuyer.query.FilterCount", searchVO);
				}
				
				PBBuyerFilterSummaryVO summaryVO = new PBBuyerFilterSummaryVO();
				summaryVO.setBuyerId(buyerId);
				summaryVO.setFilterId(vo.getFilterId());
				summaryVO.setCount1(records);
				
				int rowsUpdated = update("powerbuyer.updateBuyerFilterSummaryRecord", summaryVO);
				if (rowsUpdated == 0) {
					insert("powerbuyer.insertBuyerFilterSummaryRecord", summaryVO);
				}
			}
		} catch (DataAccessException e) {
			throw new DataServiceException(e.getMessage(),e);
		}
		return;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.powerbuyer.IPowerBuyerFilterDao#getBuyers()
	 */
	public List<Integer> getBuyers() throws DataServiceException {
		
		List<Integer> results;
		try {
			results = queryForList("powerbuyer.query.getBuyers", null);
		} catch (DataAccessException e) {
			throw new DataServiceException(e.getMessage(),e);
		}
		
		return results;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.powerbuyer.IPowerBuyerFilterDao#getServiceOrderByPBFilterId(com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO)
	 */
	public List<String> getServiceOrderByPBFilterId(
			PBSearchVO searchVO) throws DataServiceException {
		List<String> results = new ArrayList<String>();
		
		PBFilterVO filterVO = getFilterByFilterId(searchVO.getFilterId());
		
		searchVO.setViewName(filterVO.getDbQueryRoot());
		
		// if the user has not provided sort criteria use the default for the Power Buyer Filter
		if (StringUtils.isEmpty(searchVO.getSortColumnName())) {
			searchVO.setSortColumnName(filterVO.getSortByColumnName());
		}
		
		if (StringUtils.isEmpty(searchVO.getSortOrder())) {
			searchVO.setSortOrder(filterVO.getSortOrder());
		}

		Map<String, String> sort = configureGridSort(searchVO.getSortColumnName(), searchVO.getSortOrder());
		searchVO.setSortColumnName(sort.get(OrderConstants.SORT_COLUMN_KEY));
		searchVO.setSortOrder(sort.get(OrderConstants.SORT_ORDER_KEY));

		List<ServiceOrderSearchResultsVO> tempResults = queryForList("powerbuyer.query.SerivceOrderIdsByFilter", searchVO);
		if (null != tempResults) {
			for (ServiceOrderSearchResultsVO vo : tempResults) {
				results.add(vo.getSoId());
			}
		}
		
		return results;
	}

	//sl-13522
	public List<String> getActiveServiceOrderByProvider(
				PBSearchVO searchVO) throws DataServiceException {
			List<String> results = new ArrayList<String>();
	
			Map<String, String> sort = configureGridSort(searchVO.getSortColumnName(), searchVO.getSortOrder());
			searchVO.setSortColumnName(sort.get(OrderConstants.SORT_COLUMN_KEY));
			searchVO.setSortOrder(sort.get(OrderConstants.SORT_ORDER_KEY));
			//sl-13522-
			List<ServiceOrderSearchResultsVO> tempResults=new ArrayList<ServiceOrderSearchResultsVO>();
			if(searchVO.getWfm_sodFlag()!=null && searchVO.getWfm_sodFlag().equals("Provider"))
				//query to fetch the active sos of the current provider.
				tempResults= queryForList("query.SerivceOrderIdsByProvider", searchVO);
			
			else if (searchVO.getWfm_sodFlag()!=null && searchVO.getWfm_sodFlag().equals("Firm"))
				//query to fetch the active sos of the current providerFirm.
				tempResults= queryForList("query.SerivceOrderIdsByProviderFirm", searchVO);
			
			if (null != tempResults) {
				for (ServiceOrderSearchResultsVO vo : tempResults) {
					results.add(vo.getSoId());
				}
			}
			
			return results;
			
		}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.powerbuyer.IPowerBuyerFilterDao#getFilterByFilterId(java.lang.Integer)
	 */
	public PBFilterVO getFilterByFilterId(Integer filterId)
			throws DataServiceException {
		
		PBFilterVO vo;
		try {
			vo = (PBFilterVO)queryForObject("powerbuyer.query.getFilterByFilterId", filterId);
		} catch (DataAccessException e) {
			throw new DataServiceException(e.getMessage(),e);
		}
		
		return vo;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.powerbuyer.IPowerBuyerFilterDao#claimSO(com.newco.marketplace.dto.vo.powerbuyer.ClaimVO)
	 */
	public ClaimVO claimSO(ClaimVO claimVO) throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.powerbuyer.IPowerBuyerFilterDao#getSOClaimed(java.lang.Integer)
	 */
	public List<ClaimVO> getSOClaimed(Integer buyerId)
			throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.powerbuyer.IPowerBuyerFilterDao#unClaimSO(com.newco.marketplace.dto.vo.powerbuyer.ClaimVO)
	 */
	public ClaimVO unClaimSO(ClaimVO claimVO) throws DataServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.powerbuyer.IPowerBuyerFilterDao#getNextUnclaimedSOByFilterId(java.lang.Integer, java.lang.Integer)
	 */
	public ClaimVO getNextUnclaimedSOByFilterId(Integer filterId, Integer buyerId, Integer buyerRefTypeId, String buyerRefValue, String searchBuyerId)
			throws DataServiceException {
		
		// get filter
		PBFilterVO filter = getFilterByFilterId(filterId);
		
		ClaimVO claimVo = new ClaimVO();
		try {
			filter.setBuyerId(buyerId);
			filter.setBuyerRefTypeId(buyerRefTypeId);
			filter.setBuyerRefValue(buyerRefValue);
			filter.setSearchBuyerId(searchBuyerId);
			//SL-20142: changing the sort order for ATT Completion queue with SO completed_date in ascending order
			//for this particular scenario, the queueId=94 and buyer=512353 is hard coded, hence if queue id is wrong, then this flow might not work.
			if(buyerId == 512353 && filterId == 94){
				claimVo = (ClaimVO)queryForObject("powerbuyer.query.nextSOByFilter.att.completionqueue", filter);
			}else{
				claimVo = (ClaimVO)queryForObject("powerbuyer.query.nextSOByFilter", filter);
			}
			
			if (claimVo == null)
				return null;

			claimVo.setQueueId(filterId);
			claimVo.setQueueDestinationTab(filter.getDestinationTab());
			claimVo.setQueueDestinationSubTab(filter.getDestinationSubTab());
		} catch (DataAccessException e) {
			// it is valid for no rows to be returned
		}
		return claimVo;
	}

	/**
	 * configureGridSort sets the database fields that sorting will be performed on based on 
	 * data in the input parameter.  A Map is returned containing the key and sort order 
	 * @param sortColumn
	 * @param sortOrder
	 * @param statusId
	 * @param source
	 * @return
	 */
	protected Map<String, String> configureGridSort (String sortColumn, String sortOrder) {
		
		Map<String, String> sort = new HashMap<String, String>();

		if( StringUtils.isNotEmpty(sortColumn) && 
			!StringUtils.equals(sortColumn, OrderConstants.NULL_STRING) ) {
			if("status".equalsIgnoreCase(sortColumn)){
				sort.put(OrderConstants.SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_PB_STATUS);
				sort.put(OrderConstants.SORT_ORDER_KEY, sortOrder);
			} else if("SoId".equalsIgnoreCase(sortColumn)){
				sort.put(OrderConstants.SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_PB_SOID);
				sort.put(OrderConstants.SORT_ORDER_KEY, sortOrder);
			} else if("ServiceDate".equalsIgnoreCase(sortColumn)){
				sort.put(OrderConstants.SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_PB_SERVICEDATE);
				sort.put(OrderConstants.SORT_ORDER_KEY, sortOrder);
			} else {
				sort.put(OrderConstants.SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_PB_SERVICEDATE);
				sort.put(OrderConstants.SORT_ORDER_KEY, OrderConstants.SORT_ORDER_DESC);
			}
		} else {
			sort.put(OrderConstants.SORT_COLUMN_KEY, OrderConstants.SORT_COLUMN_PB_SERVICEDATE);
			sort.put(OrderConstants.SORT_ORDER_KEY, OrderConstants.SORT_ORDER_DESC);
		}
		
		return sort;
	}
	
	/**
	 * @deprecated use getWFMQueueAndTask(String buyerId, String soId) instead.
	 * Returns the list of the queues associated with a particular buyerId and soId
	 * @param buyerId The buyer_id
	 * @param soId The service order id
	 * @return the ArrayList of value object 'WFMBuyerQueueVO'
	 */
	public List<WFMBuyerQueueVO> getWFMQueueDetails(String buyerId, String soId) throws DataServiceException{
		List buyerQueuesVO = new ArrayList<WFMBuyerQueueVO>();
				
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("buyerId", buyerId);
			paramMap.put("soId", soId);
			buyerQueuesVO = queryForList("wfmSoQueue.query.wfmBuyerQueueSelect", paramMap);
		
		} catch (DataAccessException e) {
			throw new DataServiceException(e.getMessage(),e);
		}
		
		return buyerQueuesVO;
		
	}
	
	
	/**
	 * Returns the list of the queues associated with a particular buyerId and soId and task for a all queues
	 * @param buyerId The buyer_id
	 * @param soId The service order id
	 * @return the ArrayList of value object 'WFMBuyerQueueVO'
	 */
	public QueueTasksGroupVO getWFMQueueAndTasks(String buyerId, String soId) throws DataServiceException{
		List buyerQueuesVO = new ArrayList<WFMBuyerQueueVO>();
		List wfmSOTasksVO = new ArrayList<WFMSOTasksVO>();
		QueueTasksGroupVO queueTasksGroupVO = new QueueTasksGroupVO();
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("buyerId", buyerId);
			paramMap.put("soId", soId);
			paramMap.put("followUpQueueId", OrderConstants.CallBack_QueueId);
			buyerQueuesVO = queryForList("wfmSoQueue.query.buyerQueuesSelect", paramMap);

			queueTasksGroupVO.setWFMBuyerQueueVO(buyerQueuesVO);
			
		
		} catch (DataAccessException e) {
			throw new DataServiceException(e.getMessage(),e);
		}

		try {
					
			wfmSOTasksVO = queryForList("wfmSoQueue.query.wfmAllQueuesTasksSelect", new HashMap<String, Object>());
		
			queueTasksGroupVO.setWFMSOTasksVO(wfmSOTasksVO);
		
		} catch (DataAccessException e) {
			throw new DataServiceException(e.getMessage(),e);
		}
	
		
		
		return queueTasksGroupVO;
		
	}
	
	
	
	
	
	/**
	 * Returns the list of the queues associated with a particular buyerId and soId and task for a all queues
	 * @param buyerId The buyer_id
	 * @param soId The service order id
	 * @return the ArrayList of value object 'WFMBuyerQueueVO'
	 */
	@SuppressWarnings("unchecked")
	public QueueTasksGroupVO getWFMQueueAndTasks(String buyerId, String soId, String groupId) throws DataServiceException{
		List <WFMBuyerQueueVO> buyerQueuesVO = new ArrayList<WFMBuyerQueueVO>();
		WFMBuyerQueueVO vo = new WFMBuyerQueueVO();
		List<WFMSOTasksVO> wfmSOTasksVO = new ArrayList<WFMSOTasksVO>();
		QueueTasksGroupVO queueTasksGroupVO = new QueueTasksGroupVO();
		Integer qId = null;
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		try {
			paramMap.put("buyerId", buyerId);
			paramMap.put("soId", soId);
			paramMap.put("groupId", groupId);
			paramMap.put("followUpQueueId", OrderConstants.CallBack_QueueId);
			/*Integer iCount = (Integer) queryForObject("wfmSoQueue.query.callUpQueuesToFetch",paramMap);
			if(iCount == null || iCount<0){
				iCount=0;
			}
			paramMap.put("callUpQueuesToFetch", iCount);
			*/
			buyerQueuesVO = queryForList("wfmSoQueue.query.buyerQueuesSelect", paramMap);
			queueTasksGroupVO.setWFMBuyerQueueVO(buyerQueuesVO);
			
		
		} catch (DataAccessException e) {
			throw new DataServiceException(e.getMessage(),e);
		}

		try {
			if(null!= buyerQueuesVO && buyerQueuesVO.size()>0){	
			List<Integer>queueIdList=new ArrayList<Integer>();
	       // SL-18377:Added for fetching all wfmTasks for all queues
			  for(WFMBuyerQueueVO queueVo:buyerQueuesVO){	
				  if(null!=queueVo.getQId() ){			
					queueIdList.add(queueVo.getQId());
				  }
			}
			wfmSOTasksVO = queryForList("wfmSoQueue.query.wfmAllQueuesTasksSelect", queueIdList);
			}
			queueTasksGroupVO.setWFMSOTasksVO(wfmSOTasksVO);
		
		} catch (DataAccessException e) {
			throw new DataServiceException(e.getMessage(),e);
		}
	
		
		
		return queueTasksGroupVO;
		
	}
	
	
	public List<WFMSOTasksVO> getWFMCallBackQueueAndTasks(String buyerId) throws DataServiceException{
		List wfmSOTasksVOList = new ArrayList<WFMSOTasksVO>();
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		try {
			if((buyerId.equals(String.valueOf(OrderConstants.FACILITIES_BUYER)))||(buyerId.equals(String.valueOf(OrderConstants.ATnT)))){
				paramMap.put("followUpQueueId", OrderConstants.CallBack_QueueId_facilities);
			}
			else
			{
			paramMap.put("followUpQueueId", OrderConstants.CallBack_QueueId);
			}
			wfmSOTasksVOList = queryForList("wfmSoQueue.query.wfmCallBackQueueTasksSelect", paramMap);
			
		
		} catch (DataAccessException e) {
			throw new DataServiceException(e.getMessage(),e);
		}
		return wfmSOTasksVOList;
		
	}

	
	/**
	 * 
	 * updateCompleteIndicator
	 * int The number of records updated. 
	 * @param requeueSOVO
	 * @return The number of records updated. 
	 * @throws BusinessServiceException
	 */
	public int updateCompleteIndicator(RequeueSOVO requeueSOVO)
			throws DataServiceException {
		
		int rowsUpdated = update("powerbuyer.update.completeIndicator", requeueSOVO);
		return rowsUpdated;
	}
	
	public void updatePOSCancellationIndicator(String soId)
			throws DataServiceException {
		int result = update("powerbuyer.update.posCancellationIndicator", soId);		
	}	
	

	/**
	 * 
	 * updateRequeueDateTime
	 * int The number of records updated. 
	 * @param requeueSOVO
	 * @return The number of records updated. 
	 * @throws BusinessServiceException
	 */
	public int updateRequeueDateTime(RequeueSOVO requeueSOVO)
			throws DataServiceException {
		// set the complete indicator to zero again, if its a requeue action
		requeueSOVO.setCompletedInd(OrderConstants.COMPELETE_INDICATOR_ZERO);
		int rowsUpdated = update("powerbuyer.update.requeueDateTime", requeueSOVO);
		return rowsUpdated;
	}
	
	public int updateRequeueDateTimeFollowUp(RequeueSOVO requeueSOVO)
	throws DataServiceException {
		// set the complete indicator to zero again, if its a requeue action
		requeueSOVO.setCompletedInd(OrderConstants.COMPELETE_INDICATOR_ZERO);
		int rowsUpdated = update("powerbuyer.update.requeueDateTime.followUp", requeueSOVO);
		return rowsUpdated;
	}
	public int insertNewCallBackQueue(RequeueSOVO requeueSOVO_in) throws DataServiceException {
		
		int result = 0;
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		List <RequeueSOVO>requeueSOVOList = new ArrayList<RequeueSOVO>();
	//	wfmSoQueue.query.SelectSOsForCallBackQueue
		
		if(!StringUtils.isBlank(requeueSOVO_in.getSoId())){
			paramMap.put("soId", requeueSOVO_in.getSoId());
		}else{
			paramMap.put("groupSOId", requeueSOVO_in.getGroupSOId());
		}
		   paramMap.put("buyerId",requeueSOVO_in.getBuyerId());
		   if((requeueSOVO_in.getBuyerId().equals(OrderConstants.FACILITIES_BUYER))||(requeueSOVO_in.getBuyerId().equals(OrderConstants.ATnT))){
			   paramMap.put( "queueId", OrderConstants.CallBack_QueueId_facilities);
		   }
		   else{
			   paramMap.put( "queueId", OrderConstants.CallBack_QueueId);
		   }
		   paramMap.put( "noteId", requeueSOVO_in.getNoteId());
	
		requeueSOVOList = queryForList("wfmSoQueue.query.SelectSOsForCallBackQueue", paramMap);
		
		RequeueSOVO requeueSOVOObj = (RequeueSOVO)queryForObject("wfmSoQueue.query.wfmSoClaimedInfoSelect", paramMap);
		
	
		// TODO : investigate Object o and return appropriately
		Integer completedInd = requeueSOVO_in.getCompletedInd();
		for (RequeueSOVO requeueSOVO : requeueSOVOList) {

			if(completedInd!=null && completedInd.equals(1)){
				requeueSOVO.setCompletedInd(completedInd);
			}
			requeueSOVO.setRequeueDate(requeueSOVO_in.getRequeueDate());
			if(requeueSOVOObj!=null){
				requeueSOVO.setClaimedById(requeueSOVOObj.getClaimedById());
				requeueSOVO.setClaimedDate(requeueSOVOObj.getClaimedDate());
				requeueSOVO.setClaimedFromQueueId(requeueSOVOObj.getClaimedFromQueueId());
				requeueSOVO.setClaimedInd(requeueSOVOObj.getClaimedInd());
				
			}			
			Object o = insert("wfmSoQueue.insert.NewCallBackSOQueue", requeueSOVO);
			
		}
		
			return result;
		
	}
	
	
	
	
	
	
	public boolean checkIfPendingQueues(String soID, String groupSOID, Integer resourceId, Integer companyId) throws DataServiceException {
		ClaimVO claimVO = new ClaimVO();
		claimVO.setResourceId(resourceId);
		claimVO.setBuyerId(companyId);
		Integer countPendingQueues;
		if(groupSOID != null && groupSOID.length() > 1) {
			claimVO.setParentGroupId(groupSOID);
			countPendingQueues = (Integer)queryForObject("powerbuyer.query.countGroupedSOPendingQueues", claimVO);
		} else {
			claimVO.setSoId(soID);
			countPendingQueues = (Integer)queryForObject("powerbuyer.query.countPendingQueues", claimVO);
		}
		return (countPendingQueues>0?true:false);
	}
	
	public boolean primaryQueueActionTaken(String soID, String groupID, Integer resourceId) throws DataServiceException {
		ClaimVO claimVO = new ClaimVO();
		claimVO.setResourceId(resourceId);
		Integer count;
		
		if(groupID != null && groupID.length() > 1 ) {
			claimVO.setParentGroupId(groupID);
			count = (Integer)queryForObject("powerbuyer.query.groupedSOPrimaryQueueActionTaken", claimVO);
		} else {
			claimVO.setSoId(soID);
			count = (Integer)queryForObject("powerbuyer.query.SOPrimaryQueueActionTaken", claimVO);
		}	
		return (count>0?true:false);
	}
	
	public PBFilterVO getDestinationTabForSO(String soId) throws DataServiceException {
		PBFilterVO pbFilterVO;
		pbFilterVO = (PBFilterVO)queryForObject("powerbuyer.query.getDestinationForSO", soId);
		return pbFilterVO;
	}
	
	
	/**
	 * Runs the backend job for synchronizing wfm_so_queues table for various queues. 
	 */
	public void updateWFMQueues() throws DataServiceException {
		try {
			queryForList("synchronize.wfm.queues");
		} catch(DataAccessException e) {
			throw new DataServiceException(e.getMessage(),e);
		}
	}
/*
 * (non-Javadoc)
 * @see com.newco.marketplace.persistence.iDao.powerbuyer.IPowerBuyerFilterDao#isMaxFollowUpCountReached(com.newco.marketplace.dto.vo.powerbuyer.RequeueSOVO)
 */
	public boolean isMaxFollowUpCountReached(RequeueSOVO requeueSOVO_in) throws DataServiceException {
		boolean result = false;
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		if(!StringUtils.isBlank(requeueSOVO_in.getSoId())){
			paramMap.put("soId", requeueSOVO_in.getSoId());
		}else{
			paramMap.put("groupSOId", requeueSOVO_in.getGroupSOId());
		}
		   paramMap.put("buyerId",requeueSOVO_in.getBuyerId());
		   paramMap.put( "queueId", OrderConstants.CallBack_QueueId);
		   paramMap.put( "noteId", requeueSOVO_in.getNoteId());
	
		   Integer iCount = (Integer) queryForObject("wfmSoQueue.query.nextFollowUpQueueSequence",paramMap);
		   
		   if((iCount) > OrderConstants.MAX_FOLLOWUP_ALLOWED){
				result= true;
		   }
		return result;
	}
	
	public String getGroupId(String soId) throws DataServiceException {
		return (String)queryForObject("powerbuyer.query.getGroupIdForSO", soId);
	}

	public int updateCompleteIndicatorForFollowUp(RequeueSOVO requeueSOVO) throws DataServiceException{

		int rowsUpdated = update("powerbuyer.update.completeIndicator.followUp", requeueSOVO);
		return rowsUpdated;
	}
	
	//SL-19820
	public String getGroupedId(String soId) throws DataServiceException {
		return (String)queryForObject("powerbuyer.query.getGroupId", soId);
	}
	
	// SLT-1613 START
	@Override
	public List<WFMQueueVO> getWfmQueueDetails() throws DataServiceException {

		List<WFMQueueVO> wfmDetails = queryForList("wfm.wfmQueueSelect");
		return wfmDetails;
	}

	@Override
	public void saveWfmBuyerQueues(List<BuyerQueueVO> wfmBuyerQueues) throws DataServiceException {

		batchInsert("wfm.saveWfmBuyerQueue", wfmBuyerQueues);
	}

	@Override
	public void deleteWfmBuyerQueues(Integer buyerId, List<Integer> queueIdList) throws DataServiceException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("buyerId", buyerId);
		params.put("queueIdList", queueIdList);
		delete("wfm.deleteWfmBuyerQueue", params);

	}
	@Override
	public void deleteAllWfmBuyerQueues(Integer buyerId) throws DataServiceException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("buyerId", buyerId);
		delete("wfm.deleteAllWfmBuyerQueue", params);
	}
	// SLT-1613 END
	
	// SLT-1892 START
	@Override
	public List<Integer> checkClaimedQueue(Integer buyerId, List<Integer> queueIdList) throws DataServiceException {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("buyerId", buyerId);
		params.put("queueIdList", queueIdList);
		List<Integer> queueIds = queryForList("wfm.checkClaimedQueue", params);
		return queueIds;
	}
	// SLT-1892 END

	//SLT-1894 START
	@Override
	public List<WFMQueueVO> getWfmBuyerQueueDetails(Integer buyerId) throws DataServiceException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("buyerId", buyerId);
		List<WFMQueueVO> buyerQueues = queryForList("wfm.wfmQueueBuyerSelect", params);
		return buyerQueues;
	}
	//SLT-1894 END
}
	
	
/*
 * Maintenance History
 * $Log: PowerBuyerFilterDao.java,v $
 * Revision 1.12  2008/04/26 00:40:28  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.10.20.1  2008/04/23 11:42:21  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.11  2008/04/23 05:02:00  hravi
 * Reverting to build 247.
 *
 * Revision 1.10  2008/01/29 22:01:21  mhaye05
 * fixed the sorting of power buyer filter search results
 *
 * Revision 1.9  2008/01/21 23:17:55  mhaye05
 * needed to add the buyer id to the methods calls that are used when claiming the next service order
 *
 * Revision 1.8  2008/01/18 01:59:06  mhaye05
 * added logic to claim the next service order
 *
 * Revision 1.7  2008/01/17 22:10:10  rambewa
 * *** empty log message ***
 *
 * Revision 1.6  2008/01/15 19:57:50  mhaye05
 * flushed out query to get so ids for power buyer filter
 *
 * Revision 1.5  2008/01/15 17:37:40  mhaye05
 * added getServiceOrderByPBFilterId()
 *
 * Revision 1.4  2008/01/14 20:38:26  mhaye05
 * check in point where filter summary load complete
 *
 * Revision 1.3  2008/01/12 00:56:01  rambewa
 * *** empty log message ***
 *
 * Revision 1.2  2008/01/11 20:54:20  mhaye05
 * added additional queries
 *
 * Revision 1.1  2008/01/11 18:43:59  mhaye05
 * Initial check in
 *
 */
