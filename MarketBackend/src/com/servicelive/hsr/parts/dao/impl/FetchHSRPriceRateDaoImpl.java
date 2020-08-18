package com.servicelive.hsr.parts.dao.impl;

import com.servicelive.hsr.parts.dao.FetchHSRPriceRateDao;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.autoclose.dao.impl.AbstractBaseDao;
import com.servicelive.domain.hsr.parts.BuyerPartsPriceCalcValues;

public class FetchHSRPriceRateDaoImpl extends AbstractBaseDao implements FetchHSRPriceRateDao{

	public BuyerPartsPriceCalcValues getReimbursementRate(String partCoverageType,String partSourceType) {
		
		
		String hql = "from BuyerPartsPriceCalcValues where partCoverageTypeId = :partCoverageType and partSourcingLevelId = :partSourceType";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("partCoverageType", Integer.valueOf(partCoverageType));
		query.setParameter("partSourceType", Integer.valueOf(partSourceType));		
		// TODO Auto-generated method stub
		
		try {			
			@SuppressWarnings("unchecked")
		    BuyerPartsPriceCalcValues buyerPartsPriceCalcValues =  (BuyerPartsPriceCalcValues) query.getSingleResult();
			return buyerPartsPriceCalcValues;
		}
		catch (NoResultException e) {
			logger.error("ManageReasonCodeDaoImpl.getAllCancelReasonCodes NoResultException");
			return null;
		}

	}
}
