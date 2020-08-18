/*
 * @(#)CampaignHeaderDaoImpl.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.servicelive.spn.dao.campaign.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.spn.campaign.CampaignHeader;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.campaign.CampaignHeaderDao;

/**
 * @author Mahmud Khair
 *
 */
@Repository ("campaignHeaderDao")
public class CampaignHeaderDaoImpl extends AbstractBaseDao implements CampaignHeaderDao {

	
	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.campaign.CampaignHeaderDao#delete(com.servicelive.domain.spn.campaign.CampaignHeader)
	 */
	public void delete(CampaignHeader entity) throws Exception {
			super.delete(entity);
	}

	
	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.campaign.CampaignHeaderDao#findAll(int[])
	 */
	@SuppressWarnings("unchecked")
	public List<CampaignHeader> findAll(int... rowStartIdxAndCount) throws Exception {
		return ( List <CampaignHeader> )super.findAll("CampaignHeader", rowStartIdxAndCount);
	}

	
	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.campaign.CampaignHeaderDao#findById(java.lang.Integer)
	 */
	public CampaignHeader findById(Integer campaignId) throws Exception {
		return ( CampaignHeader) super.findById(CampaignHeader.class, campaignId);
	}

	@SuppressWarnings("unchecked")
	public List<CampaignHeader> findBySpnId(Integer spnId) throws Exception {
		String hql = "select c from CampaignHeader c, CampaignSPN cspn where cspn.spn.spnId = :spnId and cspn in elements (c.campaignSPN)";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("spnId", spnId);
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.campaign.CampaignHeaderDao#findByProperty(java.lang.String, java.lang.Object, int[])
	 */
	@SuppressWarnings("unchecked")
	public List<CampaignHeader> findByProperty(String propertyName, Object value,
			int... rowStartIdxAndCount) throws Exception {
		return ( List<CampaignHeader>) super.findByProperty("CampaignHeader",propertyName,value, rowStartIdxAndCount);
	}

	
	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.campaign.CampaignHeaderDao#save(com.servicelive.domain.spn.campaign.CampaignHeader)
	 */
	public void save(CampaignHeader entity) throws Exception {
		super.save(entity);
		super.getEntityManager().flush();
		logger.debug("Save executed successfully for " + this.getClass().getName());
	}

	
	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.campaign.CampaignHeaderDao#update(com.servicelive.domain.spn.campaign.CampaignHeader)
	 */
	public CampaignHeader update(CampaignHeader entity) throws Exception {
		CampaignHeader campaignHeader = (CampaignHeader)super.update(entity);
		logger.debug("Update executed successfully for " + this.getClass().getName());
		super.getEntityManager().flush();
		return campaignHeader;
	}


	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.campaign.CampaignHeaderDao#removeExistingApprovalCriteria(java.lang.Integer)
	 */
	@Transactional (propagation = Propagation.REQUIRED)
	public int removeExistingApprovalCriteria(Integer campaignId)
			throws Exception {
		Query query = getEntityManager().createQuery("delete from CampaignApprovalCriteria o where o.campaignId =  :campaignHeader");
		
		CampaignHeader header = new CampaignHeader();
		header.setCampaignId(campaignId);
		query.setParameter("campaignHeader", header);
		int deleted = query.executeUpdate();
		logger.debug("Deleted " + deleted+ " CampaignApproval Criteria");
		return deleted;
	}

	@Transactional ( propagation = Propagation.REQUIRED)
	public int removeExistingCampaignSPNs(CampaignHeader campaignHeader, List<SPNHeader> spnList) throws Exception {
		Query query = getEntityManager().createQuery("delete from CampaignSPN o where o.campaign =  :campaignHeader" +
				" and o.spn in (:spnList)");
		query.setParameter("campaignHeader", campaignHeader);
		query.setParameter("spnList", spnList);
		int deleted = query.executeUpdate();
		logger.debug("Deleted " + deleted+ " CampaignSPN");
		return deleted;
	}

	@Transactional ( readOnly = false, propagation = Propagation.REQUIRED)	
	public int removeExistingProviderFirms(Integer campaignId) throws Exception
	{
		Query query = getEntityManager().createQuery("delete from CampaignProviderFirm cpf where cpf.campaign =  :campaignHeader");
		
		CampaignHeader header = new CampaignHeader();
		header.setCampaignId(campaignId);
		query.setParameter("campaignHeader", header);
		int deleted = query.executeUpdate();
		logger.debug("Deleted " + deleted+ " CampaignProviderFirms where campaignId=" +  campaignId);
		return deleted;		
	}
	
	
}
