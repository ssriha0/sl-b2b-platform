/*
 * @(#)SoTierRoutinHistoryDAO.java
 *
 * Copyright 2009 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 *  
 */
package com.newco.marketplace.persistence.iDao.routing.tiered;

import java.util.List;

import com.newco.marketplace.exception.core.DataServiceException;
import com.servicelive.routing.tiered.vo.SoTierRoutingHistoryVO;

/**
 * @author Mahmud Khair
 * @since 09/15/2009
 *
 */
public interface SoTierRoutingHistoryDAO {

	/**
	 * Inserts the history into so_tier_route_history
	 * 
	 */
	SoTierRoutingHistoryVO insert(SoTierRoutingHistoryVO record) throws DataServiceException;

	/**
	 * Gets the SO tier routing history for specified parameter
	 * 
	 */
	List<SoTierRoutingHistoryVO> getSoTierRoutingHist(SoTierRoutingHistoryVO example) throws DataServiceException;

	/**
	 * Update the SO tier routing history with new reason code or route status indicator
	 * for the specified so ID and tier ID
	 * 
	 */
	Integer updateSoTierRoutingHist(SoTierRoutingHistoryVO record) throws DataServiceException;

}