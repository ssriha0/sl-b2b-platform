package com.newco.marketplace.business.iBusiness.powerbuyer;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.dto.vo.BuyerQueueVO;
import com.newco.marketplace.dto.vo.WFMBuyerQueueVO;
import com.newco.marketplace.dto.vo.WFMQueueVO;
import com.newco.marketplace.dto.vo.WFMSOTasksVO;
import com.newco.marketplace.dto.vo.group.QueueTasksGroupVO;
import com.newco.marketplace.dto.vo.powerbuyer.ClaimVO;
import com.newco.marketplace.dto.vo.powerbuyer.PBBuyerFilterSummaryVO;
import com.newco.marketplace.dto.vo.powerbuyer.PBFilterVO;
import com.newco.marketplace.dto.vo.powerbuyer.RequeVO;
import com.newco.marketplace.dto.vo.powerbuyer.RequeueSOVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;



/**
 * @author rambewa
 *
 * $Revision$ $Author$ $Date$
 */

/*
 * Maintenance History: See bottom of file
 */
public interface IPowerBuyerBO {
	
	/**
	 * @param claimVO
	 * @return
	 */
	public ClaimVO claimSO(ClaimVO claimVO) throws BusinessServiceException;
	
	
	/**
	 * Reque's and unclaim's the so
	 * @param soReque
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean requeSO(RequeVO soReque) throws BusinessServiceException;	
	
	/**
	 * @param resourceId
	 * @return
	 */
	public List<ClaimVO> getClaimedSO(Integer resourceId) throws BusinessServiceException;
	
	/**
	 * Claims the next un-claimed service order for the resource provided
	 * @param filterId
	 * @param buyerId
	 * @param resourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public ClaimVO claimNextSO(Integer filterId, Integer buyerId, Integer resourceId, Integer buyerRefTypeId, String buyerRefValue, String searchBuyerId) throws BusinessServiceException;
	
	/**
	 * Returns the list of filters currently assigned to the buyer and the number of service orders
	 * within each filter
	 * @param buyerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<PBBuyerFilterSummaryVO> getBuyerFilterSummaryCounts (Integer buyerId, Boolean slAdminInd, Integer buyerRefTypeId, String buyerRefValue, String searchBuyerId) throws BusinessServiceException;
	
	/**
	 * Updates the database with the latest counts for each filter used by all buyers
	 * @throws BusinessServiceException
	 */
	public void updatePowerBuyerFilterSummaryCounts() throws BusinessServiceException;

	/**
	 * unclaim the claims that have expired
	 * @throws BusinessServiceException
	 */
	public void processExpiredClaims()throws BusinessServiceException;
	
	/**
	 * Claims a specific service order for the resource provided
	 * @param soId
	 * @param buyerId
	 * @param resourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public ClaimVO claimSpecificSO(String soId, Integer buyerId, Integer resourceId) throws BusinessServiceException;

	/**
	 * Unclaim on claim task completion
	 * @param soId
	 * @param resourceId
	 * @param reasonCode 
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean completedClaimTask(String soId,Integer resourceId, int reasonCode) throws BusinessServiceException;
	
	/**
	 * Unclaim all service orders claimed by <code>resourceId</code>.
	 * 
	 * @param resourceId
	 */
	public void unClaimByResource(Integer resourceId);

	/**
	 * Checks to see if the resource passed in is the claimer of the Service Order
	 * @param soId
	 * @param resourceId
	 * @return
	 * @throws DataServiceException
	 */
	public boolean isCurrentUserTheClaimedUser(String soId, Integer resourceId) throws BusinessServiceException;
	
	/**
	 *  Returns the list of the queues associated with a particular buyerId and soId
	 * @param buyerId
	 * @param soId
	 * @return
	 */
	public List<WFMBuyerQueueVO> getWFMQueueDetails(String buyerId, String soId) throws BusinessServiceException;
	
	
	public int updateCompleteIndicator(RequeueSOVO requeueSOVO)  throws BusinessServiceException;
	
	public void updatePOSCancellationIndicator(String soId)  throws BusinessServiceException;
	
	public int updateRequeueDateTime(RequeueSOVO requeueSOVO)  throws BusinessServiceException;
	
	public int insertNewCallBackQueue(RequeueSOVO requeueSOVO_in) throws BusinessServiceException;
	
	public QueueTasksGroupVO getWFMQueueAndTasks(String buyerId, String soId) throws BusinessServiceException;
	
	public List<WFMSOTasksVO> getWFMCallBackQueueAndTasks(String buyerId) throws BusinessServiceException;
	
	public QueueTasksGroupVO getWFMQueueAndTasks(String buyerId, String soId, String groupId) throws BusinessServiceException;
	
	/** This metheod checks for any pending queues that need action and returns true. */
	public boolean checkIfPendingQueues(String soID, String groupSOID, Integer resourceId, Integer companyId) throws BusinessServiceException;
	
	/** This method checks if PrimaryQueue action has been taken.*/
	public boolean primaryQueueActionTaken(String soID, String groupSOId, Integer resourceId) throws BusinessServiceException;

	
	/**
	 * Runs the backend job for synchronizing wfm_so_queues table for various queues. 
	 */
	public void updateWFMQueues() throws BusinessServiceException;

	public PBFilterVO getDestinationTabForSO(String soId) throws BusinessServiceException;
	
	public boolean isMaxFollowUpCountReached(RequeueSOVO requeueSOVO) throws BusinessServiceException;
	
	public String getGroupId(String soId) throws BusinessServiceException;


	public String getGroupedId(String soId) throws BusinessServiceException;
	
	// SLT-1613 START
	public Map<String,List<WFMQueueVO>> getWfmQueueDetails(Integer buyerId) throws BusinessServiceException;

	public void saveWfmBuyerQueues(List<BuyerQueueVO> wfmBuyerQueues, List<Integer> queueIdList, Integer buyerId)
			throws BusinessServiceException;

	// SLT-1613 END
	
}
/*
 * Maintenance History
 * $Log: IPowerBuyerBO.java,v $
 * Revision 1.16  2008/04/26 00:40:10  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.14.18.1  2008/04/23 11:41:16  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.15  2008/04/23 05:16:51  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.14  2008/01/25 18:14:29  rambewa
 * added reasoncode
 *
 * Revision 1.13  2008/01/25 00:11:48  rambewa
 * added isCurrentUserTheClaimedUser
 *
 * Revision 1.12  2008/01/23 04:44:47  rambewa
 * removed unclaim method
 *
 * Revision 1.11  2008/01/23 04:33:48  rambewa
 * added comments
 *
 * Revision 1.10  2008/01/22 20:44:07  mhaye05
 * added method claimSpecificSO(String, Integer, Integer)
 *
 * Revision 1.9  2008/01/21 23:01:52  rambewa
 * expired claims
 *
 * Revision 1.8  2008/01/18 22:39:26  mhaye05
 * updated to make sure we use the buyer id when claiming the next so
 *
 * Revision 1.7  2008/01/18 01:59:08  mhaye05
 * added logic to claim the next service order
 *
 * Revision 1.6  2008/01/17 22:13:09  rambewa
 * *** empty log message ***
 *
 * Revision 1.5  2008/01/16 15:53:28  mhaye05
 * added logic to retrieve the claimed service orders
 *
 * Revision 1.4  2008/01/15 17:11:40  mhaye05
 * Moved Maintenance History to bottom of file
 *
 * Revision 1.3  2008/01/15 17:05:15  mhaye05
 * changed parameter name from buyerId to resourceId for the claim and unclaim methods as we need to be working with resources at this level not the company
 *
 * Revision 1.2  2008/01/14 20:37:17  mhaye05
 * added method load summary count
 *
 */