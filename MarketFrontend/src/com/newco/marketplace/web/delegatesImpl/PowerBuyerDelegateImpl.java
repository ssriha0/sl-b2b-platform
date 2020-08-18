package com.newco.marketplace.web.delegatesImpl;

import java.util.List;

import com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO;
import com.newco.marketplace.dto.vo.powerbuyer.ClaimVO;
import com.newco.marketplace.dto.vo.powerbuyer.PBBuyerFilterSummaryVO;
import com.newco.marketplace.dto.vo.powerbuyer.PBFilterVO;
import com.newco.marketplace.dto.vo.powerbuyer.RequeVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.web.delegates.IPowerBuyerDelegate;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.11 $ $Author: glacy $ $Date: 2008/04/26 01:13:49 $
 */

/*
 * Maintenance History: See bottom of file
 */
public class PowerBuyerDelegateImpl implements IPowerBuyerDelegate {

	IPowerBuyerBO powerBuyerBO;
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.IPowerBuyerDelegate#claimSO(java.lang.Integer, java.lang.String)
	 */
	public boolean claimSO(Integer resourceId, String soId) {
		boolean result = false;
		
		return result;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.IPowerBuyerDelegate#getClaimedSO(java.lang.Integer)
	 */
	public List<ClaimVO> getClaimedSO(Integer resourceId) throws BusinessServiceException {

		return powerBuyerBO.getClaimedSO(resourceId);
	}


	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.IPowerBuyerDelegate#getBuyerFilterSummaryCounts(java.lang.Integer)
	 */
	public List<PBBuyerFilterSummaryVO> getBuyerFilterSummaryCounts(
			Integer buyerId, Boolean slAdminInd, Integer buyerRefTypeId, String buyerRefValue, String searchBuyerId) throws BusinessServiceException {

		return powerBuyerBO.getBuyerFilterSummaryCounts(buyerId, slAdminInd, buyerRefTypeId, buyerRefValue, searchBuyerId);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.IPowerBuyerDelegate#claimNextSO(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	public ClaimVO claimNextSO(Integer filterId, Integer buyerId, Integer resourceId, Integer buyerRefTypeId, String buyerRefValue, String searchBuyerId)
			throws BusinessServiceException {
		
		return powerBuyerBO.claimNextSO(filterId, buyerId, resourceId, buyerRefTypeId, buyerRefValue, searchBuyerId);
	}

	public IPowerBuyerBO getPowerBuyerBO() {
		return powerBuyerBO;
	}

	public void setPowerBuyerBO(IPowerBuyerBO powerBuyerBO) {
		this.powerBuyerBO = powerBuyerBO;
	}


	public ClaimVO claimSO(ClaimVO claimVO)
			throws BusinessServiceException {
		return powerBuyerBO.claimSO(claimVO);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.IPowerBuyerDelegate#requeSO(com.newco.marketplace.dto.vo.powerbuyer.RequeVO)
	 */
	public boolean requeSO(RequeVO requeVO) throws BusinessServiceException {
		return powerBuyerBO.requeSO(requeVO);
	}

	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.IPowerBuyerDelegate#claimSpecificSO(java.lang.String, java.lang.Integer,java.lang.Integer)
	 */
	public ClaimVO claimSpecificSO(String soId, Integer buyerId,
			Integer resourceId) throws BusinessServiceException {
		return powerBuyerBO.claimSpecificSO(soId, buyerId, resourceId);
	}
	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.IPowerBuyerDelegate#completedClaimTask(java.lang.String, java.lang.Integer)
	 */
	public boolean completedClaimTask(String soId, Integer resourceId,int reasonCode)
			throws BusinessServiceException {
		return powerBuyerBO.completedClaimTask(soId, resourceId,reasonCode);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.delegates.IPowerBuyerDelegate#isCurrentUserTheClaimedUser(java.lang.String, java.lang.Integer)
	 */
	public boolean isCurrentUserTheClaimedUser(String soId, Integer resourceId)
			throws BusinessServiceException {
		return powerBuyerBO.isCurrentUserTheClaimedUser(soId, resourceId);
	}
	
	public boolean checkIfPendingQueues(String soId, String groupSOID, Integer resourceId, Integer companyId) 
		throws BusinessServiceException
	{
		return powerBuyerBO.checkIfPendingQueues(soId, groupSOID, resourceId, companyId);
	}
	
	public boolean primaryQueueActionTaken(String soId, String groupSOId, Integer resourceId) 
	throws BusinessServiceException
	{
		return powerBuyerBO.primaryQueueActionTaken(soId, groupSOId, resourceId);
	}
	
	public PBFilterVO getDestinationTabForSO(String soId) throws BusinessServiceException {
		return powerBuyerBO.getDestinationTabForSO(soId);
	}

}
/*
 * Maintenance History
 * $Log: PowerBuyerDelegateImpl.java,v $
 * Revision 1.11  2008/04/26 01:13:49  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.9.20.1  2008/04/23 11:41:36  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.10  2008/04/23 05:19:43  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.9  2008/01/25 18:14:10  rambewa
 * added reasoncode
 *
 * Revision 1.8  2008/01/25 00:15:03  rambewa
 * added isCurrentUserTheClaimedUser
 *
 * Revision 1.7  2008/01/24 20:37:41  pkoppis
 * update claim specific so
 *
 * Revision 1.6  2008/01/23 04:48:56  rambewa
 * removed unclaim method
 *
 * Revision 1.5  2008/01/18 22:39:29  mhaye05
 * updated to make sure we use the buyer id when claiming the next so
 *
 * Revision 1.4  2008/01/18 01:59:10  mhaye05
 * added logic to claim the next service order
 *
 * Revision 1.3  2008/01/17 22:15:47  rambewa
 * *** empty log message ***
 *
 * Revision 1.2  2008/01/16 21:59:41  mhaye05
 * updated for workflow monitor tab
 *
 * Revision 1.1  2008/01/16 15:53:31  mhaye05
 * added logic to retrieve the claimed service orders
 *
 */