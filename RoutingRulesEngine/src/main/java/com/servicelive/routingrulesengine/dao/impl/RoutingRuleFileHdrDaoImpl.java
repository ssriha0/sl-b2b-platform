package com.servicelive.routingrulesengine.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.routingrules.RoutingRuleFileHdr;
import com.servicelive.routingrulesengine.dao.RoutingRuleFileHdrDao;
import com.servicelive.routingrulesengine.services.RoutingRulesPaginationService;
import com.servicelive.routingrulesengine.vo.RoutingRulesPaginationVO;

public class RoutingRuleFileHdrDaoImpl extends AbstractBaseDao implements
		RoutingRuleFileHdrDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.routingrulesengine.dao.RoutingRuleFileHdrDao#fetchFilesForProcessing()
	 *      fetch all routing rule file details in the scheduled status from the
	 *      routing_rule_file_hdr table.
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<RoutingRuleFileHdr> fetchFilesForProcessing() {
		String hql = "select r from RoutingRuleFileHdr r  where r.fileStatus = :fileStatus";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("fileStatus", "Scheduled");
		try {
			@SuppressWarnings("unchecked")
			List<RoutingRuleFileHdr> scheduledFiles = (List<RoutingRuleFileHdr>) query
					.getResultList();
			return scheduledFiles;
		} catch (NoResultException e) {
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.routingrulesengine.dao.RoutingRuleFileHdrDao#save(com.servicelive.domain.routingrules.RoutingRuleFileHdr)
	 *      saves the file details in the routing_rule_file_hdr table
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public RoutingRuleFileHdr save(RoutingRuleFileHdr routingRuleFileHdr)
			throws Exception {
		getEntityManager().persist(routingRuleFileHdr);
		flush();
		return routingRuleFileHdr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.routingrulesengine.dao.RoutingRuleFileHdrDao#save(com.servicelive.domain.routingrules.RoutingRuleFileHdr)
	 *      updates the file details in the routing_rule_file_hdr table
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public RoutingRuleFileHdr update(RoutingRuleFileHdr routingRuleFileHdr)
			throws Exception {
		super.update(routingRuleFileHdr);
		flush();
		return routingRuleFileHdr;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.routingrulesengine.dao.RoutingRuleFileHdrDao#changeStatusToProccessing(java.util.List)
	 *      Sets the file status to "Processing"
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void changeStatusToProccessing(List<Integer> fileIds)
			throws Exception {
		String hql = "UPDATE routing_rule_file_hdr SET file_status = 'Processing' where routing_rule_file_hdr_id in (:fileIds) ";
		Query query = getEntityManager().createNativeQuery(hql);
		query.setParameter("fileIds", fileIds);
		query.executeUpdate();
	}
	
	/**
	 * Get the uploaded files for the user
	 * @param pagingCriteria
	 * @return List<RoutingRuleFileHdr>
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<RoutingRuleFileHdr> getUploadedFilesForBuyer(
		RoutingRulesPaginationVO pagingCriteria, Integer buyerId) throws Exception{	
		
		// Handle Sorting
		String sortColumn = "";
		switch (pagingCriteria.getSortCol()) {			 
			case 1:
				sortColumn = "rrFileHdr.routingRuleFileName"; //File Name
				break; 
			case 2: 
				sortColumn = "rrFileHdr.createdDate"; // createdDate
				break;
			case 3:
				sortColumn = "rrFileHdr.processCompletedTime"; // Process completed date
				break; 
		}
		
		String sortOrder = "";
		switch (pagingCriteria.getSortOrd()) {
			case 0: sortOrder = " desc"; break;
			case 1: sortOrder = " asc"; break;
		}
		
		// Form JPA Query to load RoutingRuleFileHdr objects
		String hql = "select rrFileHdr "
				+ "from RoutingRuleFileHdr rrFileHdr where rrFileHdr.uploadedBy.buyer.buyerId=:buyerId" ;
		
		// Default sort is on the date asc 
		if(StringUtils.isEmpty(sortColumn))
			sortColumn = "rrFileHdr.createdDate";
		if(StringUtils.isEmpty(sortOrder))
			sortOrder = " desc";
		
		if (StringUtils.isNotBlank(sortColumn) && 
				StringUtils.isNotBlank(sortOrder)) {
			hql += " order by " + sortColumn + sortOrder;
		}
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);
		// Handle Pagination
		Integer pageRequested = pagingCriteria.getPageRequested();
		Integer pageIndex = (pageRequested-1)*
			RoutingRulesPaginationService.DEFAULT_RULES_PAGE_SIZE;
		query.setFirstResult(pageIndex);
		query.setMaxResults(RoutingRulesPaginationService.
			DEFAULT_RULES_PAGE_SIZE);
		List<RoutingRuleFileHdr> routingRuleFileHdrList = 
			query.getResultList();
		return routingRuleFileHdrList;
	}

	/**
	 * Check if the file exists
	 * @param fileName
	 * @return boolean
	 */
	@Transactional(propagation=Propagation.SUPPORTS)
	public boolean checkIfFileExists(String fileName, Integer buyerId) throws Exception {
		String hql = "select count(rrFileHdr.routingRuleFileHdrId) "
				+"from RoutingRuleFileHdr rrFileHdr "
				+"where rrFileHdr.routingRuleFileName in (:fileNames) and rrFileHdr.uploadedBy.buyer.buyerId=:buyerId";
		List<String> fileNames = new ArrayList<String>();		
		fileNames.add(fileName+".xls");
		fileNames.add(fileName+".xlsx");
		
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("fileNames", fileNames);
		query.setParameter("buyerId", buyerId);
		Number fileNameCount = (Number)query.getSingleResult();
		if(fileNameCount.intValue() > 0)
			return true;
		else 
			return false;
	}	
	
	/**
	 * Get the total number of files uploaded
	 * @return Integer
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.SUPPORTS)
	public Integer getUploadedFilesCount(Integer buyerId) throws Exception {
		String hql = "select count(rrFileHdr.routingRuleFileHdrId) "
				+"from RoutingRuleFileHdr rrFileHdr where rrFileHdr.uploadedBy.buyer.buyerId=:buyerId";
		
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);
	    Number fileNameCount = (Number)query.getSingleResult();
		Integer count = (Integer)fileNameCount.intValue();
		return count;
	}

}
