/*
 * @(#)SoTierRoutingHistoryBOImpl.java
 *
 * Copyright 2009 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.newco.marketplace.business.businessImpl.routing.tiered;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.routing.tiered.SoTierRoutingHistoryBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.routing.tiered.SoTierRoutingHistoryDAO;
import com.servicelive.routing.tiered.vo.SoTierRoutingHistoryVO;

/**
 * @author Mahmud Khair
 *
 */
public class SoTierRoutingHistoryBOImpl implements SoTierRoutingHistoryBO {
	
	private SoTierRoutingHistoryDAO soTierRoutingHistoryDao;
	//Create the logger
	private static final Logger logger = Logger.getLogger(SoTierRoutingHistoryBOImpl.class.getName());
	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.routing.tiered.SoTierRoutingHistoryBO#createSoTierRoutingHist(java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Boolean)
	 */
	public SoTierRoutingHistoryVO createSoTierRoutingHist(String soId,
			Integer tierId, Integer reasonCode, Boolean routingStatusInd)
			throws BusinessServiceException {
		SoTierRoutingHistoryVO tierVo = new SoTierRoutingHistoryVO();
		tierVo.setSoId(soId);
		tierVo.setTierId(tierId);
		tierVo.setReasonCode(reasonCode);
		tierVo.setRouteStatusInd(routingStatusInd);
		try {
			tierVo = soTierRoutingHistoryDao.insert(tierVo);
		} catch (DataServiceException e) {
			logger.error(e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return tierVo;
	}

	
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.routing.tiered.SoTierRoutingHistoryBO#getSoTierRoutingHistBySoIdAndTierId(java.lang.String, java.lang.Integer)
	 */
	public SoTierRoutingHistoryVO getSoTierRoutingHistBySoIdAndTierId(
			String soId, Integer tierId) throws BusinessServiceException {
		SoTierRoutingHistoryVO tierVo = new SoTierRoutingHistoryVO();
		tierVo.setSoId(soId);
		tierVo.setTierId(tierId);
		try {
			List<SoTierRoutingHistoryVO> tierVoList = soTierRoutingHistoryDao.getSoTierRoutingHist(tierVo);
			if (tierVoList != null && tierVoList.size()> 0) {
				tierVo = tierVoList.get(0);
			}
			else
				tierVo = null;
			
		} catch (DataServiceException e) {
			logger.error(e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return tierVo;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.routing.tiered.SoTierRoutingHistoryBO#updateRoutingStatusInd(java.lang.Integer)
	 */
	public Integer updateRoutingStatusInd(String soId,
			Integer tierId, Boolean routingStatusInd)
			throws BusinessServiceException {
		Integer updateCount;
		SoTierRoutingHistoryVO tierVo = new SoTierRoutingHistoryVO();
		tierVo.setSoId(soId);
		tierVo.setTierId(tierId);
		tierVo.setRouteStatusInd(routingStatusInd);
		try {
			updateCount = soTierRoutingHistoryDao.updateSoTierRoutingHist(tierVo);
		} catch (DataServiceException e) {
			logger.error(e.getMessage());
			throw new BusinessServiceException(e.getMessage());
		}
		return updateCount
		;

	}


	/**
	 * @param soTierRoutingHistoryDao the soTierRoutingHistoryDao to set
	 */
	public void setSoTierRoutingHistoryDao(
			SoTierRoutingHistoryDAO soTierRoutingHistoryDao) {
		this.soTierRoutingHistoryDao = soTierRoutingHistoryDao;
	}

	
}
