package com.servicelive.routingrulesengine.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.so.BuyerReferenceType;
import com.servicelive.routingrulesengine.dao.BuyerReferenceTypeDao;

/**
 * 
 * @author svanloon
 *
 */
public class BuyerReferenceTypeDaoImpl extends AbstractBaseDao implements BuyerReferenceTypeDao {

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<BuyerReferenceType> findByBuyerId(Integer buyerId){
		String hql = "from " + BuyerReferenceType.class.getSimpleName()+  " where activeInd = true and buyer.buyerId = :buyerId and buyerInput = true order by sortOrder";
		Query query = this.getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);
		return query.getResultList();
	}
	
	/**
	 * The method is used to validate a list of custom references
	 * 
	 * @param List
	 *            <String> custRefs :custom references to get validated
	 * @param buyerId
	 * @return List<String>
	 */
	public List<String> validateCustRefs(List<String> custRefs,
			Integer buyerId) {
		String hql = "select brt.refType from BuyerReferenceType brt where brt.buyer.buyerId = :buyerId and brt.refType in(:custRefCodes)";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("custRefCodes", custRefs);
		query.setParameter("buyerId", buyerId);
		try {
			@SuppressWarnings("unchecked")
			List<String> validCustRefs = query.getResultList();
			return validCustRefs;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<String> getBuyerCustomReferences(Integer buyerId) {
		String hql = "select brt.refType from BuyerReferenceType brt where brt.buyer.buyerId = :buyerId and activeInd = true and buyerInput = true";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);
		try {
			@SuppressWarnings("unchecked")
			List<String> validCustRefs = query.getResultList();
			return validCustRefs;
		} catch (NoResultException e) {
			return null;
		}
	}
}
