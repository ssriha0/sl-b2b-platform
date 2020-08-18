package com.servicelive.routingrulesengine.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.so.BuyerOrderSku;
import com.servicelive.routingrulesengine.dao.BuyerSkuDao;

public class BuyerSkuDaoImpl extends AbstractBaseDao implements BuyerSkuDao{

	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> findBySpecialtyCodeAndBuyerID(Integer buyerId, String specialtyCode) {
		String hql = "select bos.sku from " + BuyerOrderSku.class.getSimpleName() +
			" bos inner join bos.buyerSkuCategory bsc "+ 		
			" where bos.buyerId = :buyerId and "+
			" bsc.categoryName = :specialtyCode and "+
			" bsc.categoryId = bos.buyerSkuCategory group by bos.sku ";			
		Query query = this.getEntityManager().createQuery(hql);
		query.setParameter("buyerId", new Long(buyerId));
		query.setParameter("specialtyCode", specialtyCode);
		return query.getResultList();
	}
	
	
	/**
	 * The method is used to validate a list of job Codes
	 * 
	 * @param List
	 *            <String> jobCodes :jobCodes to get validated
	 * @param buyerId
	 * @return List<String>
	 */
	public List<String> validateJobCodes(List<String> jobCodeList,
			Integer buyerId) {
		List<String> combinedValidList = new ArrayList<String>();
		//jobcode code should be present either in  BuyerOrderSku or in SpecialtyAddOn
		String hql = "select distinct buyerSku.sku from BuyerOrderSku as buyerSku "
				+ "where buyerSku.sku in(:jobCodes) and "
				+ "buyerSku.buyerId = :buyerId";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("jobCodes", jobCodeList);
		Long buyer = Long.valueOf(buyerId);
		query.setParameter("buyerId", buyer);
		
		String hql1 = "select distinct specialtyaddOn.stockNumber from SpecialtyAddOn as specialtyaddOn "
			+ "where specialtyaddOn.stockNumber in(:jobCodes) and (specialtyaddOn.classificationCode = 'M' or specialtyaddOn.classificationCode = 'A')";
		Query query1 = getEntityManager().createQuery(hql1);
		query1.setParameter("jobCodes", jobCodeList);
		
		try {
			@SuppressWarnings("unchecked")
			List<String> validJobCodes = query.getResultList();
			
			/* SL-17670 : Specialty add-ons are only available for buyer 1000.
			 * For other buyers, the job code entered by the buyer should not be 
			 * validated against the specialty add on table*/
			if(null!=buyerId && 1000 == buyerId.intValue()){
				combinedValidList =query1.getResultList();
			}
			if(null!=validJobCodes && validJobCodes.size()>0){
				combinedValidList.addAll(validJobCodes);
			}
			return combinedValidList;
		} catch (NoResultException e) {
			return null;
		}
	}
}
