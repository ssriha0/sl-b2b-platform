/*
 * @(#)SoTierRoutinHistoryDAOImpl.java
 *
 * Copyright 2009 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 *  
 */
package com.newco.marketplace.persistence.daoImpl.routing.tiered;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.routing.tiered.SoTierRoutingHistoryDAO;
import com.sears.os.dao.impl.ABaseImplDao;
import com.servicelive.routing.tiered.vo.SoTierRoutingHistoryVO;

public class SoTierRoutingHistoryDAOImpl extends ABaseImplDao implements SoTierRoutingHistoryDAO {

	//Create the logger
	private static final Logger logger = Logger.getLogger(SoTierRoutingHistoryDAOImpl.class.getName());
	
	/**
	 * This method corresponds to the database table so_tier_route_history
	 * 
	 */
	public SoTierRoutingHistoryDAOImpl() {
		super();
	}

	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.daoImpl.routing.tiered.SoTierRoutinHistoryDAO#insert(com.servicelive.routing.tiered.vo.SoTierRoutinHistoryVO)
	 */
	public SoTierRoutingHistoryVO insert(SoTierRoutingHistoryVO history)
		throws DataServiceException {
		Integer soTierRoutingHistoryId = null;
		try {
			soTierRoutingHistoryId = (Integer) insert("soTierRoutingHistory.insert",
					history);
		} catch (Exception e) {
			throw new DataServiceException(
					"Error inserting so tier routing history for " + 
					history.getSoId() + " and " + history.getTierId(), e);
		}
		history.setSoTierRoutingHistId(soTierRoutingHistoryId);
		return history;
	}
	

	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.daoImpl.routing.tiered.SoTierRoutinHistoryDAO#getSoTierRoutingHist(com.servicelive.routing.tiered.vo.SoTierRoutinHistoryVO)
	 */
	@SuppressWarnings("unchecked")
	public List<SoTierRoutingHistoryVO> getSoTierRoutingHist(SoTierRoutingHistoryVO history) throws DataServiceException{
		try {
			List<SoTierRoutingHistoryVO> list = queryForList("soTierRoutingHistory.select", history);
			return list;
		} catch (Exception e) {
			throw new DataServiceException(
					"Error selecting so tier routing history" + 
					history.getSoId() + " and " + history.getTierId(), e);
		}
	}
	

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.daoImpl.routing.tiered.SoTierRoutinHistoryDAO#updateSoTierRoutingHist(com.servicelive.routing.tiered.vo.SoTierRoutinHistoryVO)
	 */
	public Integer updateSoTierRoutingHist(SoTierRoutingHistoryVO history)
			throws DataServiceException {
		Integer updateCount = null;
		try {
			updateCount = (Integer) update("soTierRoutingHistory.update",history);
		} catch (Exception e) {
			throw new DataServiceException(
					"Error updating so tier routing history for " + 
					history.getSoId() + " and " + history.getTierId(), e);
		}
		return updateCount;
	}

	
}