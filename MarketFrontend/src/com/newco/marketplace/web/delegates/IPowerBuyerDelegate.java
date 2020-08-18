package com.newco.marketplace.web.delegates;

import java.util.List;

import com.newco.marketplace.dto.vo.powerbuyer.ClaimVO;
import com.newco.marketplace.dto.vo.powerbuyer.PBBuyerFilterSummaryVO;
import com.newco.marketplace.dto.vo.powerbuyer.PBFilterVO;
import com.newco.marketplace.dto.vo.powerbuyer.RequeVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * @author rambewa
 *
 * $Revision: 1.12 $ $Author: glacy $ $Date: 2008/04/26 01:13:51 $
 */

/*
 * Maintenance History
 * $Log: IPowerBuyerDelegate.java,v $
 * Revision 1.12  2008/04/26 01:13:51  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.10.20.1  2008/04/23 11:41:48  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.11  2008/04/23 05:19:40  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.10  2008/01/25 18:13:55  rambewa
 * resonCode
 *
 * Revision 1.9  2008/01/25 00:14:50  rambewa
 * added isCurrentUserTheClaimedUser
 *
 * Revision 1.8  2008/01/24 20:37:50  pkoppis
 * update claim specific so
 *
 * Revision 1.7  2008/01/23 04:48:56  rambewa
 * removed unclaim method
 *
 * Revision 1.6  2008/01/18 22:39:29  mhaye05
 * updated to make sure we use the buyer id when claiming the next so
 *
 * Revision 1.5  2008/01/18 01:59:10  mhaye05
 * added logic to claim the next service order
 *
 * Revision 1.4  2008/01/17 22:15:54  rambewa
 * *** empty log message ***
 *
 * Revision 1.3  2008/01/16 21:59:41  mhaye05
 * updated for workflow monitor tab
 *
 * Revision 1.2  2008/01/16 15:53:31  mhaye05
 * added logic to retrieve the claimed service orders
 *
 */
public interface IPowerBuyerDelegate {
	
	/**
	 * @param resourceId
	 * @param soId
	 * @return
	 */
	public ClaimVO claimSO(ClaimVO claimVO) throws BusinessServiceException;
	
	
	/**
	 * Unclaim on reque
	 * @param requeVO
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean requeSO(RequeVO requeVO) throws BusinessServiceException;
	
	/**
	 * @param resourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<ClaimVO> getClaimedSO(Integer resourceId) throws BusinessServiceException;;
	
	/**
	 * @param soId
	 * @param buyerId
	 * @param resourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	
	
	public ClaimVO claimSpecificSO(String soId, Integer buyerId,Integer resourceId) throws BusinessServiceException;;
	
	public PBFilterVO getDestinationTabForSO(String soId) throws BusinessServiceException;
	
	
	/**
	 * @param buyerId
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<PBBuyerFilterSummaryVO> getBuyerFilterSummaryCounts(Integer buyerId, Boolean slAdminInd, Integer buyerRefTypeId, String buyerRefValue, String searchBuyerId) throws BusinessServiceException;;
	
	/**
	 * @param filterId
	 * @param buyerId
	 * @param resourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public ClaimVO claimNextSO (Integer filterId, Integer buyerId, Integer resourceId, Integer buyerRefTypeId, String buyerRefValue, String searchBuyerId) throws BusinessServiceException;

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
	 * Checks to see if the resource passed in is the claimer of the Service Order
	 * @param soId
	 * @param resourceId
	 * @return
	 * @throws DataServiceException
	 */
	public boolean isCurrentUserTheClaimedUser(String soId, Integer resourceId) throws BusinessServiceException;
	
	/**Checks to see if any other queue needs action */
	public boolean checkIfPendingQueues(String soId, String groupSOID, Integer resourceId, Integer companyId) throws BusinessServiceException; 
	/**
	 * Check to see if user has worked on Primary Queue
	 * @param soId
	 * @param groupSOId
	 * @param resourceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public boolean primaryQueueActionTaken(String soId, String groupSOId, Integer resourceId) throws BusinessServiceException;
	
}
