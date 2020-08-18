package com.servicelive.routingrulesengine.dao.impl;

import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.Query;

import com.servicelive.domain.so.BuyerSkuTaskAssoc;
import com.servicelive.routingrulesengine.dao.BuyerSkuTaskAssocDao;

/**
 * 
 * @author svanloon
 *
 */
public class BuyerSkuTaskAssocDaoImpl extends AbstractBaseDao implements BuyerSkuTaskAssocDao {

	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> findDistinctJobCodeByBuyerIdAndSpecialtyCode(Integer buyerId, String specialtyCode) {
		String hql = "select distinct a.sku from " + BuyerSkuTaskAssoc.class.getSimpleName() + " a where buyerId = :buyerId and specialtyCode = :specialtyCode";
		Query query = this.getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);
		query.setParameter("specialtyCode", specialtyCode);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> findDistinctSpecialtyCodeByBuyerId(Integer buyerId) {
		String hql = "select distinct specialtyCode from " + BuyerSkuTaskAssoc.class.getSimpleName() + " where buyerId = :buyerId and specialtyCode is not null";
		Query query = this.getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);
		return query.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<BuyerSkuTaskAssoc> findByBuyerIdAndSpecialtyCode(Integer buyerId, String specialtyCode) {
		String hql = "from " + BuyerSkuTaskAssoc.class.getSimpleName() + " where buyerId = :buyerId and specialtyCode = :specialtyCode";
		Query query = this.getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);
		query.setParameter("specialtyCode", specialtyCode);
		return query.getResultList();
	}
}
