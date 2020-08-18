package com.servicelive.routingrulesengine.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.RoutingRuleHdrHist;
import com.servicelive.routingrulesengine.dao.RoutingRuleHdrHistDao;
import com.servicelive.routingrulesengine.services.RoutingRulesPaginationService;
import com.servicelive.routingrulesengine.vo.RoutingRulesPaginationVO;

public class RoutingRuleHdrHistDaoImpl extends AbstractBaseDao implements
		RoutingRuleHdrHistDao {

	/*
	 * Method to fetch routing rule history @param buyerId @param
	 * RoutingRulesPaginationVO pagingCriteria @param boolean archiveIndicator
	 * @return List<RoutingRuleHdrHist>
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<RoutingRuleHdrHist> findByBuyerId(Integer buyerId,
			RoutingRulesPaginationVO pagingCriteria, boolean archiveIndicator) {
		Integer pageRequested = pagingCriteria.getPageRequested();
		Integer pageIndex = (pageRequested - 1)
				* RoutingRulesPaginationService.DEFAULT_RULES_PAGE_SIZE;

		// Handle Sorting
		String sortColumn = "";
		switch (pagingCriteria.getSortCol()) {
		case 0:
			case 2: sortColumn = "a.modifiedDate"; break; // modifiedDate
			case 1: sortColumn = "a.ruleName"; break; // Name
			case 3:  sortColumn = "a.modifiedBy"; break; // Contact
			case 4:  sortColumn = "a.ruleAction"; break; // Action
		}
		String sortOrder = "";
		switch (pagingCriteria.getSortOrd()) {
			case 0: sortOrder = " desc"; break;
			case 1: sortOrder = " asc"; break;
		}

		String hql = "";

		if (archiveIndicator) {
			hql = "select a from RoutingRuleHdrHist a where a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId";
		}

		else {
			hql = "select a from RoutingRuleHdrHist a where a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId "
					+ "and a.routingRuleHdr.ruleStatus != 'ARCHIVED'";
		}
		
		if (StringUtils.isNotBlank(sortColumn)
				&&  sortColumn =="a.ruleName") 
		{
			sortColumn = "TRIM(" +sortColumn +")";
		}
		if (StringUtils.isNotBlank(sortColumn)
				&& StringUtils.isNotBlank(sortOrder)) {
			hql += " order by " + sortColumn + sortOrder;
		}
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);
		query.setFirstResult(pageIndex);
		query
				.setMaxResults(RoutingRulesPaginationService.DEFAULT_RULES_PAGE_SIZE);
		try {
			List<RoutingRuleHdrHist> routingRuleHdrList = query.getResultList();
			return routingRuleHdrList;
		} catch (NoResultException e) {
			return null;
		}

	}

	/**
	 * To get BuyerResource 
	 * @param userName
	 * @return
	 */
	public BuyerResource getBuyerResource(String userName)
	{
		String hql = "select buyer from BuyerResource buyer where buyer.user.username = :username";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("username", userName);
		try {
			BuyerResource buyer = (BuyerResource) query.getSingleResult();
			return buyer;
		} catch (NoResultException e) {
			return null;
		}
	}
}
