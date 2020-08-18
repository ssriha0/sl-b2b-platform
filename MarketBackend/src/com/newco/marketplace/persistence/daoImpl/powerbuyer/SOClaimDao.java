package com.newco.marketplace.persistence.daoImpl.powerbuyer;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.ApplicationPropertiesVO;
import com.newco.marketplace.dto.vo.powerbuyer.ClaimHistoryVO;
import com.newco.marketplace.dto.vo.powerbuyer.ClaimVO;
import com.newco.marketplace.dto.vo.powerbuyer.RequeVO;
import com.newco.marketplace.exception.core.DataNotFoundException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.applicationproperties.IApplicationPropertiesDao;
import com.newco.marketplace.persistence.iDao.powerbuyer.ISOClaimDao;
import com.sears.os.dao.impl.ABaseImplDao;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.11 $ $Author: glacy $ $Date: 2008/04/26 00:40:28 $
 */

/*
 * Maintenance History: See bottom of file
 */

public class SOClaimDao extends ABaseImplDao implements ISOClaimDao {
	private static final Logger logger = Logger.getLogger(SOClaimDao.class);

	protected IApplicationPropertiesDao applicationPropertiesDao = null;
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.powerbuyer.ISOClaimDao#getClaimedSerivceOrders(java.lang.Integer)
	 */
	public List<ClaimVO> getClaimedSerivceOrders(Integer resourceId)
			throws DataServiceException {
		
		List<ClaimVO> results = new ArrayList<ClaimVO>();
		
		try {
			results = queryForList("powerbuyer.query.claim.getClaimedByResource", resourceId);
		} catch (DataAccessException e) {
			logger.error(e);
			throw new DataServiceException(e.getMessage(),e);
		}
		return results;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.powerbuyer.ISOClaimDao#getClaimedSerivceOrderByServiceOrderId(java.lang.String)
	 */
	public ClaimVO getClaimedSerivceOrderByServiceOrderId(String soId)
			throws DataServiceException {
		
		ClaimVO vo = null;
		try {
			vo = (ClaimVO)queryForObject("powerbuyer.query.claim.getClaimedBySoId", soId);
			if(vo == null) {
				vo = (ClaimVO)queryForObject("powerbuyer.query.claim.getClaimedByParentGroupId", soId);
			}
		} catch (DataAccessException e) {
			logger.error(e);
			// it is valid for no records to be returned
		}
		return vo;
	}
	
	private ClaimVO getClaimedSerivceOrderByParentGroupId(String soId)
			throws DataServiceException {

		ClaimVO vo = null;
		try {
			vo = (ClaimVO) queryForObject(
					"powerbuyer.query.claim.getClaimedByParentGroupId", soId);
		} catch (DataAccessException e) {
			// it is valid for no records to be returned
		}
		return vo;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.powerbuyer.ISOClaimDao#getQueuedServiceOrder(java.lang.String, java.lang.String)
	 */
	public List<ClaimVO> getQueuedServiceOrders(ClaimVO claimVO)
			throws DataServiceException {
		List<ClaimVO> list = null;
		try {
			list = (List<ClaimVO>) queryForList("powerbuyer.query.getQueuedSOs", claimVO);
		} catch (DataAccessException e) {
			logger.error(e);
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.powerbuyer.ISOClaimDao#getQueuedServiceOrder(com.newco.marketplace.dto.vo.powerbuyer.ClaimVO)
	 */
	public ClaimVO getQueuedServiceOrder(ClaimVO claimVO)
			throws DataServiceException {
		List<ClaimVO> list = getQueuedServiceOrders(claimVO);
		return (list == null || list.size() == 0) ? null : list.get(0);
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.powerbuyer.ISOClaimDao#addServiceOrderToQueue(java.lang.String, java.lang.Integer)
	 */
	public void addServiceOrderToQueue(ClaimVO claimVO)
		throws DataServiceException {
		insert("powerbuyer.insert.SOQueue", claimVO);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.powerbuyer.ISOClaimDao#isCurrentUserTheClaimedUser(java.lang.String, java.lang.Integer)
	 */
	public boolean isCurrentUserTheClaimedUser(String soId, Integer resourceId)
			throws DataServiceException {
		
		boolean isClaimer = false;
		
		ClaimVO vo = getClaimedSerivceOrderByServiceOrderId(soId);
		if (null!= vo && vo.getResourceId().intValue() == resourceId.intValue()) {
			isClaimer = true;
		}
		return isClaimer;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.powerbuyer.ISOClaimDao#claimSerivceOrder(com.newco.marketplace.dto.vo.powerbuyer.ClaimVO)
	 */
	public ClaimVO claimSerivceOrder(ClaimVO claimVO)
			throws DataServiceException {
		update("powerbuyer.update.claimSO", claimVO);
		
		ClaimVO dataToReturn;
		//if (claimVO.getParentGroupId() == null)
			dataToReturn = getClaimedSerivceOrderByServiceOrderId(claimVO.getSoId());
		//else
		//	dataToReturn = getClaimedSerivceOrderByParentGroupId(claimVO.getParentGroupId());
			
		return dataToReturn;
	}

	
	/**
	 * @param claimVO
	 * @return
	 * @throws DataServiceException
	 */
	public ClaimVO claimSO(ClaimVO claimVO) throws DataServiceException {
		claimVO=(ClaimVO) insert("powerbuyer.insert.claim.claimSO", claimVO);
		return claimVO;
	}
	
	
	public int updateClaimSO(ClaimVO claimVO) throws DataServiceException {
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.powerbuyer.ISOClaimDao#unClaimSO(com.newco.marketplace.dto.vo.powerbuyer.ClaimVO)
	 */
	public int unClaimSO(ClaimVO claimVO) throws DataServiceException {
		int res;
		logger.info("===***=== DEBUG_UNCLAIM=== " + (claimVO==null?"claimVO is null":claimVO.toString()));
		
		if (claimVO.getParentGroupId() == null)
			res = update("powerbuyer.update.unClaimSO", claimVO.getSoId());
		else
			res = update("powerbuyer.update.unClaimSOGroup", claimVO.getParentGroupId());
		return res;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.powerbuyer.ISOClaimDao#unClaimByResource(java.lang.Integer)
	 */
	public int unClaimByResource(Integer resourceId) {
		logger.info("===***=== DEBUG_UNCLAIM=== unclaim by resourceId: " + resourceId);
		return update("powerbuyer.update.unClaimByResource", resourceId);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.powerbuyer.ISOClaimDao#requeSO(com.newco.marketplace.dto.vo.powerbuyer.RequeVO)
	 */
	public RequeVO requeSO(RequeVO requeVO) throws DataServiceException {
		int update = update("powerbuyer.update.requeSO", requeVO);
		return requeVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.powerbuyer.ISOClaimDao#logClaimHistory(com.newco.marketplace.dto.vo.powerbuyer.ClaimHistoryVO)
	 */
	public ClaimHistoryVO logClaimHistory(ClaimHistoryVO claimHistoryVO)
			throws DataServiceException {
		claimHistoryVO=(ClaimHistoryVO) insert("powerbuyer.insert.claimHistory.logClaimHistory", claimHistoryVO);
		return claimHistoryVO;
	}

	/**
	 * @return
	 * @throws DataServiceException
	 */
	public List<ClaimVO> getExpiredClaims() throws DataServiceException {
		try {
			ApplicationPropertiesVO prop = applicationPropertiesDao.query(Constants.AppPropConstants.PB_CLAIM_EXPIRY_TIME_INTERVAL);
			Long timeInterval=new Long(prop.getAppValue());
			List<ClaimVO> results = new ArrayList<ClaimVO>();
			results=queryForList("powerbuyer.select.exiredclaims", timeInterval);
			return results;
		} catch (DataNotFoundException e) {
			throw new DataServiceException(e.getMessage(),e);
		} catch (DataAccessException e) {
			throw new DataServiceException(e.getMessage(),e);
		}
	}

	public IApplicationPropertiesDao getApplicationPropertiesDao() {
		return applicationPropertiesDao;
	}

	public void setApplicationPropertiesDao(
			IApplicationPropertiesDao applicationPropertiesDao) {
		this.applicationPropertiesDao = applicationPropertiesDao;
	}

	
}
/*
 * Maintenance History
 * $Log: SOClaimDao.java,v $
 * Revision 1.11  2008/04/26 00:40:28  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.9.20.1  2008/04/23 11:42:20  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.10  2008/04/23 05:02:00  hravi
 * Reverting to build 247.
 *
 * Revision 1.9  2008/01/25 02:36:16  dmill03
 * workflow update
 *
 * Revision 1.8  2008/01/24 01:17:30  mhaye05
 * added removeReQueue() method
 *
 * Revision 1.7  2008/01/23 04:41:48  rambewa
 * expired claims
 *
 * Revision 1.6  2008/01/21 23:01:57  rambewa
 * expired claims
 *
 * Revision 1.5  2008/01/19 03:54:24  rambewa
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
 * Revision 1.1  2008/01/16 15:47:20  mhaye05
 * Initial check in
 *
 */