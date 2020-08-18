package com.newco.marketplace.persistence.iDao.powerbuyer;

import java.util.List;

import com.newco.marketplace.dto.vo.powerbuyer.ClaimHistoryVO;
import com.newco.marketplace.dto.vo.powerbuyer.ClaimVO;
import com.newco.marketplace.dto.vo.powerbuyer.RequeVO;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.11 $ $Author: glacy $ $Date: 2008/04/26 00:40:25 $
 */

/*
 * Maintenance History
 * $Log: ISOClaimDao.java,v $
 * Revision 1.11  2008/04/26 00:40:25  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.9.20.1  2008/04/23 11:42:14  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.10  2008/04/23 05:02:13  hravi
 * Reverting to build 247.
 *
 * Revision 1.9  2008/01/25 02:36:16  dmill03
 * workflow update
 *
 * Revision 1.8  2008/01/24 01:17:30  mhaye05
 * added removeReQueue() method
 *
 * Revision 1.7  2008/01/23 04:41:34  rambewa
 * expired claims
 *
 * Revision 1.6  2008/01/21 23:01:57  rambewa
 * expired claims
 *
 * Revision 1.5  2008/01/19 04:02:18  rambewa
 * *** empty log message ***
 *
 * Revision 1.4  2008/01/18 01:59:06  mhaye05
 * added logic to claim the next service order
 *
 * Revision 1.3  2008/01/17 22:10:10  rambewa
 * *** empty log message ***
 *
 * Revision 1.2  2008/01/16 22:55:26  mhaye05
 * added methods and attributes to check if passed in user is claimer
 *
 * Revision 1.1  2008/01/16 15:47:39  mhaye05
 * Initial check in
 *
 */
public interface ISOClaimDao {

	/**
	 * Returns a List containing the service orders the resource has claimed as ClaimVOs.  If 
	 * the resource has not claimed any service orders then the List will be empty
	 * @param resourceId
	 * @return
	 * @throws DataServiceException
	 */
	public List<ClaimVO> getClaimedSerivceOrders (Integer resourceId) throws DataServiceException;
	
	/**
	 * Gets the ClaimVO for the provided so Id
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public ClaimVO getClaimedSerivceOrderByServiceOrderId (String soId) throws DataServiceException;
	
	/**
	 * Gets the ClaimVO for the provided soId and queueId
	 * @param claimVO
	 * @return
	 * @throws DataServiceException
	 */
	public List<ClaimVO> getQueuedServiceOrders(ClaimVO claimVO) throws DataServiceException;
	
	/**
	 * Gets the ClaimVO for the provided soId and queueId
	 * @param claimVO
	 * @return
	 * @throws DataServiceException
	 */
	public ClaimVO getQueuedServiceOrder(ClaimVO claimVO) throws DataServiceException;
	
	/**
	 * Add the service order to the provided queue
	 * @param claimVO
	 * @throws DataServiceException
	 */
	public void addServiceOrderToQueue(ClaimVO claimVO) throws DataServiceException;

	/**
	 * Checks to see if the resource passed in is the claimer of the Service Order
	 * @param soId
	 * @param resourceId
	 * @return
	 * @throws DataServiceException
	 */
	public boolean isCurrentUserTheClaimedUser(String soId, Integer resourceId) throws DataServiceException;
	
	/**
	 * Claim service order(s)
	 * @param claimVO
	 * @return
	 * @throws DataServiceException
	 */
	public ClaimVO claimSerivceOrder(ClaimVO claimVO) throws DataServiceException;
	
	/**
	 * @param claimVO
	 * @return
	 * @throws DataServiceException
	 */
	public int unClaimSO(ClaimVO claimVO) throws DataServiceException;
	
	public int unClaimByResource(Integer resourceId);
	
	public int updateClaimSO(ClaimVO claimVO ) throws DataServiceException;
	/**
	 * @param soReque
	 * @return
	 * @throws DataServiceException
	 */
	public RequeVO requeSO(RequeVO soReque) throws DataServiceException;
	
	/**
	 * @param claimHistoryVO
	 * @return
	 * @throws DataServiceException
	 */
	public ClaimHistoryVO logClaimHistory(ClaimHistoryVO claimHistoryVO) throws DataServiceException;

	/**
	 * Get list of expired claims
	 * @return
	 * @throws DataServiceException
	 */
	public List<ClaimVO> getExpiredClaims() throws DataServiceException;
	
}
