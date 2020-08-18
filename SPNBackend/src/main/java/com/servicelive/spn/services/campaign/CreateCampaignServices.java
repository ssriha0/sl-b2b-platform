/*
 * @(#)CreateCampaignServices.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.servicelive.spn.services.campaign;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.spn.campaign.CampaignHeader;
import com.servicelive.domain.spn.campaign.CampaignSPN;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.dao.campaign.CampaignHeaderDao;
import com.servicelive.spn.services.BaseServices;

/**
 * @author Mahmud Khair
 *
 */
@Service
public class CreateCampaignServices extends BaseServices {
	private CampaignHeaderDao campaignHeaderDao;
	
	/**
	 * Saves the Campaign
	 * @param entity
	 * @return CampaignHeader
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public CampaignHeader saveCampaign(CampaignHeader entity) throws Exception {
			handleDates(entity);
			if(entity.getCampaignId() != null) {
				campaignHeaderDao.removeExistingApprovalCriteria(entity.getCampaignId());
				campaignHeaderDao.removeExistingProviderFirms(entity.getCampaignId());
				List<SPNHeader> spnList = new ArrayList<SPNHeader>();
				for (CampaignSPN  campaignSPN: entity.getCampaignSPN()) {
					spnList.add(campaignSPN.getSpn());
				}
				campaignHeaderDao.removeExistingCampaignSPNs(entity, spnList);
				return campaignHeaderDao.update(entity);
			}

			campaignHeaderDao.save(entity);
			return entity;
	}
	
	/**
	 * Gets the Campaign
	 * 
	 * @param campaignId
	 * @return CampaignHeader
	 * @throws Exception
	 */
	public CampaignHeader getCampaign(Integer campaignId) throws Exception {
		 return campaignHeaderDao.findById(campaignId);
	}
	
	/**
	 * Gets the list of saved campaigns
	 * 
	 * @return List of CampaignHeader
	 * @throws Exception
	 */
	public List<CampaignHeader> getAllCampaigns() throws Exception {
		 return campaignHeaderDao.findAll();
	}
	
	/**
	 * @return the campaignHeaderDao
	 */
	public CampaignHeaderDao getCampaignHeaderDao() {
		return campaignHeaderDao;
	}

	/**
	 * @param campaignHeaderao the campaignHeaderDao to set
	 */
	public void setCampaignHeaderDao(CampaignHeaderDao campaignHeaderao) {
		this.campaignHeaderDao = campaignHeaderao;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.services.BaseServices#handleDates(java.lang.Object)
	 */
	@Override
	protected void handleDates(Object entity) {
		CampaignHeader camp = (CampaignHeader) entity;
		if(camp.getCampaignId() == null) {
			camp.setCreatedDate(CalendarUtil.getNow());
		}
		camp.setModifiedDate(CalendarUtil.getNow());
		
	}
}
