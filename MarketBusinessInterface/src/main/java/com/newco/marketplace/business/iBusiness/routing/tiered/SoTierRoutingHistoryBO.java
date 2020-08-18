/*
 * @(#)SoTierRoutingHistoryBO.java
 *
 * Copyright 2009 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.newco.marketplace.business.iBusiness.routing.tiered;

//Service Live Library
import java.util.List;

import com.newco.marketplace.exception.core.BusinessServiceException;
import com.servicelive.routing.tiered.vo.SoTierRoutingHistoryVO;

/**
 * Interface for SoTierRoutingHistory
 * @author Mahmud Khair
 *
 */
public interface SoTierRoutingHistoryBO {
	
	/**
	 * Gets the So Tier Routing History
	 * @param soId
	 * @param tierId
	 * @return SoTierRoutingHistoryVO for the specified SO ID and Tier ID
	 * @throws BusinessServiceException
	 */
	SoTierRoutingHistoryVO getSoTierRoutingHistBySoIdAndTierId(String soId, Integer tierId) throws BusinessServiceException;

    
    /**
     * Updates the routing status indicator of SoTierRoutingHistory
     * @param soId
     * @param tierId
     * @param routingStatusInd
     * @return The number or rows updated
     * @throws BusinessServiceException
     */
    public Integer updateRoutingStatusInd(String soId, Integer tierId, Boolean routingStatusInd) throws BusinessServiceException;
    
   
    /**
     * Creates a new entry for So Tier Routing History. 
     * @param soId
     * @param tierId
     * @param reasonCode
     * @param routingStatusInd
     * @return SoTierRoutingHistoryVO
     * @throws BusinessServiceException
     */
    public SoTierRoutingHistoryVO createSoTierRoutingHist(String soId, Integer tierId, 
    		Integer reasonCode, Boolean routingStatusInd) throws BusinessServiceException;

}
