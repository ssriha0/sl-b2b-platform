package com.newco.marketplace.persistence.iDao.powerbuyer;

import java.util.List;

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
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.9 $ $Author: glacy $ $Date: 2008/04/26 00:40:25 $
 */

/*
 * Maintenance History: See bottom of file
 */
public interface IPowerBuyerFilterDao {

	/**
	 * Returns a List of PBFilterVO's that are available for the provided buyer.  The results
	 * are sorted by the sort_order defined in the database
	 * @param buyerId
	 * @return
	 * @throws DataServiceException
	 */
	public List<PBFilterVO> getFiltersByBuyer (Integer buyerId) throws DataServiceException;
	
	/**
	 * Returns a List of PBBuyerFilterSummaryVO's that contain the current count of Service Orders
	 * for each filter the buyer is associated with.  The results are sorted by the sort_order defined 
	 * in the database
	 * @param buyerId
	 * @return
	 * @throws DataServiceException
	 */
	public List<PBBuyerFilterSummaryVO> getBuyerFilterSummary (Integer buyerId, Boolean slAdminInd, Integer buyerRefTypeId, String buyerRefValue, String searchBuyerId) throws DataServiceException;
	
	/**
	 * Loads the power buyer filter summary table for all buyers that are associated to filters
	 * @param buyerId
	 * @throws DataServiceException
	 */
	public void loadFilterSummaryCounts(Integer buyerId) throws DataServiceException;
	
	/**
	 * Returns a list containing the id's of all of the buyers that are currently configured with
	 * power buyer filters.  There is no sorting applied
	 * @return
	 * @throws DataServiceException
	 */
	public List<Integer> getBuyers() throws DataServiceException;
	
	/**
	 * @param claimVO
	 * @return
	 * @throws DataServiceException
	 */
	public ClaimVO claimSO(ClaimVO claimVO) throws DataServiceException;	
	
	/**
	 * @param claimVO
	 * @return
	 * @throws DataServiceException
	 */
	public ClaimVO unClaimSO(ClaimVO claimVO) throws DataServiceException;
	
	/**
	 * @param buyerId
	 * @return
	 * @throws DataServiceException
	 */
	public List<ClaimVO> getSOClaimed(Integer buyerId) throws DataServiceException;
	
	/**
	 * Returns a list of Service Order Ids that meet the search criteria for the power buyer filter
	 * @param searchVO
	 * @return
	 * @throws DataServiceException
	 */
	public List<String> getServiceOrderByPBFilterId(PBSearchVO searchVO) throws DataServiceException;
	
	/**
	 * Returns the PBFilterVO object for the provided id
	 * @param filterId
	 * @return
	 * @throws DataServiceException
	 */
	public PBFilterVO getFilterByFilterId(Integer filterId) throws DataServiceException;
	
	/**
	 * Returns the next service order id for the filter.  
	 * @param filterId
	 * @param buyerId
	 * @return <code>ClaimVO</code>
	 * @throws DataServiceException
	 */
	public ClaimVO getNextUnclaimedSOByFilterId(Integer filterId, Integer buyerId, Integer buyerRefTypeId, String buyerRefValue, String searchBuyerId) throws DataServiceException;
	
	/**
	 *  Returns the list of the queues associated with a particular buyerId and soId
	 * @param buyerId
	 * @param soId
	 * @return
	 */
	public List<WFMBuyerQueueVO> getWFMQueueDetails(String buyerId, String soId) throws DataServiceException;
	
	
/**
 * 
 * updateCompleteIndicator
 * int The number of records updated. 
 * @param requeueSOVO
 * @return
 * @throws BusinessServiceException
 */
	public int updateCompleteIndicator(RequeueSOVO requeueSOVO)
			throws DataServiceException;

	public void updatePOSCancellationIndicator(String soId)
	throws DataServiceException;	
	/**
	 * 
	 * updateRequeueDateTime
	 * int The number of records updated. 
	 * @param requeueSOVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public int updateRequeueDateTime(RequeueSOVO requeueSOVO)
			throws DataServiceException;

	public int updateRequeueDateTimeFollowUp(RequeueSOVO requeueSOVO)
	throws DataServiceException;
	
	public int insertNewCallBackQueue(RequeueSOVO requeueSOVO_in) throws DataServiceException;
	
	
	/**
	 * 
	 * getWFMQueueAndTasks
	 * List<QueueTasksGroupVO> 
	 * @param buyerId
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public QueueTasksGroupVO getWFMQueueAndTasks(String buyerId, String soId) throws DataServiceException;
	
	
	/**
	 * 
	 * getWFMQueueAndTasks
	 * List<QueueTasksGroupVO> 
	 * @param buyerId
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public QueueTasksGroupVO getWFMQueueAndTasks(String buyerId, String soId, String groupId) throws DataServiceException;
	
	
	/**
	 * 
	 * getWFMCallBackQueueAndTasks
	 * List<WFMSOTasksVO> 
	 * @param buyerId
	 * @return
	 * @throws DataServiceException
	 */
	
	 public List<WFMSOTasksVO> getWFMCallBackQueueAndTasks(String buyerId) throws DataServiceException ;
	/**
	 * This method determines if there are ay pending queues
	 * @param soID
	 * @param groupSOID
	 * @param resourceId
	 * @return
	 * @throws DataServiceException
	 */
	public boolean checkIfPendingQueues(String soID, String groupSOID, Integer resourceId, Integer companyId) throws DataServiceException;
	
	/** This method determines if action has been performed on PrimaryQueue */
	public boolean primaryQueueActionTaken(String soID, String groupSOId, Integer resourceId) throws DataServiceException;
	
	
	/**
	 * Runs the backend job for synchronizing wfm_so_queues table for various queues. 
	 */
	public void updateWFMQueues() throws DataServiceException;
	
	public PBFilterVO getDestinationTabForSO(String soId) throws DataServiceException;
	
	public boolean isMaxFollowUpCountReached(RequeueSOVO requeueSOVO) throws DataServiceException;
	
	public String getGroupId(String soId) throws DataServiceException;

	public int updateCompleteIndicatorForFollowUp(RequeueSOVO requeueSOVO) throws DataServiceException;
	
	//SL-19820
	public String getGroupedId(String soId) throws DataServiceException;
	
	// SLT-1613 START
	public List<WFMQueueVO> getWfmQueueDetails() throws DataServiceException;

	public void saveWfmBuyerQueues(List<BuyerQueueVO> wfmBuyerQueues) throws DataServiceException;

	public void deleteWfmBuyerQueues(Integer buyerId, List<Integer> queueIdList) throws DataServiceException;
	
	public void deleteAllWfmBuyerQueues(Integer buyerId) throws DataServiceException;
	// SLT-1613 END
	
	//SLT-1892 START
	public List<Integer> checkClaimedQueue(Integer buyerId,List<Integer> queueIdList) throws DataServiceException;
	//SLT-1892 END
	
	//SLT-1894 START
	public List<WFMQueueVO> getWfmBuyerQueueDetails(Integer buyerId) throws DataServiceException;
	//SLT-1894 END
	
}
/*
 * Maintenance History
 * $Log: IPowerBuyerFilterDao.java,v $
 * Revision 1.9  2008/04/26 00:40:25  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.7.22.1  2008/04/23 11:42:14  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.8  2008/04/23 05:02:13  hravi
 * Reverting to build 247.
 *
 * Revision 1.7  2008/01/21 23:17:55  mhaye05
 * needed to add the buyer id to the methods calls that are used when claiming the next service order
 *
 * Revision 1.6  2008/01/18 01:59:06  mhaye05
 * added logic to claim the next service order
 *
 * Revision 1.5  2008/01/17 22:10:10  rambewa
 * *** empty log message ***
 *
 * Revision 1.4  2008/01/15 19:57:50  mhaye05
 * flushed out query to get so ids for power buyer filter
 *
 * Revision 1.3  2008/01/15 17:37:40  mhaye05
 * added getServiceOrderByPBFilterId()
 *
 * Revision 1.2  2008/01/12 00:56:08  rambewa
 * *** empty log message ***
 *
 * Revision 1.1  2008/01/11 18:44:36  mhaye05
 * Initial check in
 *
 */