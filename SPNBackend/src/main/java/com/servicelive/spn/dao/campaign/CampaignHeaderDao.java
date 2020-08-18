/*
 * @(#)CampaignHeaderDao.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.servicelive.spn.dao.campaign;

import java.util.List;

import com.servicelive.domain.spn.campaign.CampaignHeader;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author Mahmud Khair
 *
 */

public interface CampaignHeaderDao  extends BaseDao {
	
	/**
	 * Gets a list of campaign header objects
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<CampaignHeader> findAll (int... rowStartIdxAndCount) throws Exception;
	
	/**
	 * Gets a specific campaign header object
	 * @param campaignId
	 * @return CampaignHeader
	 * @throws Exception
	 */
	public CampaignHeader findById(Integer campaignId) throws Exception;
	
	/**
	 * Gets a list of CampaignHeaders associated with an SPN.
	 * @param spnId
	 * @return List<CampaignHeader>
	 * @throws Exception
	 */
	public List<CampaignHeader> findBySpnId(Integer spnId) throws Exception;

	/**
	 * Gets a list of campain header objects for specific property value
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<CampaignHeader> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;
	
	
	/**
	 * Save the campaign header object in the database
	 * @param entity
	 * @throws Exception
	 */
	public void save(CampaignHeader entity) throws Exception;
	
	/**
	 * Updates the campaign header object
	 * @param entity
	 * @return CampaignHeader
	 * @throws Exception
	 */
	public CampaignHeader update(CampaignHeader entity) throws Exception;
	
	
	/**
	 * Deletes the specific object from the database
	 * @param entity
	 * @throws Exception
	 */
	public void delete(CampaignHeader entity) throws Exception;
	
	/**
	 * Removes the approval criterias for the specified campaign
	 * @param campaignId
	 * @return int
	 * @throws Exception
	 */
	public int removeExistingApprovalCriteria(Integer campaignId) throws Exception;
	
	/**
	 * Removes the CampaignSPNs for the specified campaign
	 * @param campaignHeader
	 * @param spnList
	 * @return int
	 * @throws Exception
	 */
	public int removeExistingCampaignSPNs(CampaignHeader campaignHeader, List<SPNHeader> spnList) throws Exception;
	
	public int removeExistingProviderFirms(Integer campaignId) throws Exception;
	
}
