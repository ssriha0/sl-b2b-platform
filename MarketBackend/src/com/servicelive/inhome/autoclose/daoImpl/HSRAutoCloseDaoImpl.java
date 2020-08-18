package com.servicelive.inhome.autoclose.daoImpl;



import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.inhome.autoclose.InHomeAutoCloseRuleFirmAssoc;
import com.servicelive.domain.inhome.autoclose.InHomeAutoCloseRuleFirmAssocHistory;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.inhome.autoclose.dao.HSRAutoCloseDao;






/**
 * 
 *
 */
public class HSRAutoCloseDaoImpl extends AbstractBaseDao implements HSRAutoCloseDao{
	
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<InHomeAutoCloseRuleFirmAssoc> getAutoCloseFirmOverrideList(boolean activeInd) {
		String hql = "from InHomeAutoCloseRuleFirmAssoc where active = :activeInd order by modifiedDate desc";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("activeInd", activeInd);
		try {
		
			@SuppressWarnings("unchecked")
			List<InHomeAutoCloseRuleFirmAssoc> autoCloseFirmAssocList = query.getResultList();
			return autoCloseFirmAssocList;
		} catch (NoResultException e) {
			logger.info("HSRAutoCloseDaoImpl.getAutoCloseFirmOverrideList NoResultException");
			return null;
		}
	}

	public List<InHomeAutoCloseRuleFirmAssocHistory> getAutoCloseFirmOverrideHistory(Integer inhomeAutoCloseRuleFirmAssocId) {
		String hql = "from InHomeAutoCloseRuleFirmAssocHistory where inhomeAutoCloseRuleFirmAssocId  = :inhomeAutoCloseRuleFirmAssocId order by inHomeAutoCloseRuleFirmAssocHistId desc";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("inhomeAutoCloseRuleFirmAssocId", inhomeAutoCloseRuleFirmAssocId);
		try {
		
			@SuppressWarnings("unchecked")
			List<InHomeAutoCloseRuleFirmAssocHistory> firmAssocHistory = query.getResultList();
			return firmAssocHistory;
		} catch (NoResultException e) {
			logger.info("HSRAutoCloseDaoImpl.getAutoCloseFirmOverrideHistory NoResultException");
			return null;
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public InHomeAutoCloseRuleFirmAssoc getAutoCloseFirmAssocById(Integer ruleAssocId) {
		return (InHomeAutoCloseRuleFirmAssoc) super.findById(InHomeAutoCloseRuleFirmAssoc.class, ruleAssocId);

	}

	
	public List<Double> getReimbursementRateList()
	{
		try{
			String hql ="SELECT  lu_reimbursement_rate_value  FROM lu_reimbursement_rate";
		
				Query query = getEntityManager().createNativeQuery(hql);
				@SuppressWarnings("unchecked")
				List<Double> reimbursementRateList=query.getResultList();
				return reimbursementRateList;
			}catch(Exception e){				
				logger.info("HSRAutoCloseDaoImpl.getReimbursementRateList NoResultException");
				return null;
			}
	}

	public List<ProviderFirm> searchByFirmIds(List<Integer> firmIdList) {
		String hql = "from ProviderFirm where id IN (:providerFirmIds) ";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("providerFirmIds", firmIdList);
		try {
		
			@SuppressWarnings("unchecked")
			List<ProviderFirm> firmList = query.getResultList();
			return firmList;
		} catch (NoResultException e) {
			logger.info("HSRAutoCloseDaoImpl.searchByFirmIds NoResultException");
			return null;
		}
	}

	
	public InHomeAutoCloseRuleFirmAssoc findAutoCloseRuleFirmAssocByFirmId(
			Integer id) {
		String hql = "from InHomeAutoCloseRuleFirmAssoc where firm.id = :id";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("id", id);
		try {
		
			@SuppressWarnings("unchecked")
			InHomeAutoCloseRuleFirmAssoc autoCloseFirmAssoc = (InHomeAutoCloseRuleFirmAssoc) query.getSingleResult();
			return autoCloseFirmAssoc;
		} catch (NoResultException e) {
			logger.info("HSRAutoCloseDaoImpl.findAutoCloseRuleFirmAssocByFirmId NoResultException");
			return null;
		}
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	public InHomeAutoCloseRuleFirmAssoc saveAutoCloseFirmAssoc(InHomeAutoCloseRuleFirmAssoc autoCloseFirmAssoc) {
		InHomeAutoCloseRuleFirmAssoc autoCloseFirmAssocUpdated = null;
			try {
				autoCloseFirmAssocUpdated = (InHomeAutoCloseRuleFirmAssoc) super.update(autoCloseFirmAssoc);

			} catch (Exception e) {
				logger.info("HSRAutoCloseDaoImpl.saveAutoCloseFirmAssoc "+e);
			}
			return autoCloseFirmAssocUpdated; 
	}

	public void saveAutoCloseFirmAssocHistory(InHomeAutoCloseRuleFirmAssoc autoCloseFirmAssoc) {
		InHomeAutoCloseRuleFirmAssocHistory autoCloseFirmAssocHistory = new InHomeAutoCloseRuleFirmAssocHistory();
			try {
				BeanUtils.copyProperties(autoCloseFirmAssocHistory, autoCloseFirmAssoc);
			} catch (IllegalAccessException e) {
				logger.info("HSRAutoCloseDaoImpl.saveAutoCloseFirmAssocHistory "+e);
			} catch (InvocationTargetException e) {
				logger.info("HSRAutoCloseDaoImpl.saveAutoCloseFirmAssocHistory "+e);
			}
			try {
				super.save(autoCloseFirmAssocHistory);
			} catch (Exception e) {
				logger.info("HSRAutoCloseDaoImpl.saveAutoCloseFirmAssocHistory "+e);
			}
		
	}

	public List<ProviderFirm> searchByFirmName(String providerFirmName) {
		String hql = "from ProviderFirm where businessName LIKE :proFirmName ";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("proFirmName", "%" + providerFirmName + "%");
		query.setFirstResult(0);
		query.setMaxResults(30);
		try {
		
			@SuppressWarnings("unchecked")
			List<ProviderFirm> firmList = query.getResultList();
			return firmList;
		} catch (NoResultException e) {
			logger.info("HSRAutoCloseDaoImpl.searchByFirmName NoResultException");
			return null;
		}
	}

	public List<Integer> getOverriddenFirmIds(List<ProviderFirm> firmList) {
		String hql = "select model.firm.id from InHomeAutoCloseRuleFirmAssoc model where model.firm IN (:firms) and active = :activeInd order by inhomeAutoCloseRuleFirmAssocId desc";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("firms", firmList);
		query.setParameter("activeInd", true);
		try {
			@SuppressWarnings("unchecked")
			List<Integer> excludedFirmList = query.getResultList();
			return excludedFirmList;
		} catch (NoResultException e) {
			logger.info("HSRAutoCloseDaoImpl.getExcludedFirmIds NoResultException");
			return null;
		}
	}

	public Double getDefaultReimursementRate(String appKey) {
		Double defaultReimbursementRate = 0.0;
		try{
			String hql ="SELECT app_constant_value FROM application_constants where app_constant_key=:appKey";
		
				Query query = getEntityManager().createNativeQuery(hql);
				query.setParameter("appKey", appKey);
				String defaultReimbursementRateString = (String) query.getSingleResult();
				defaultReimbursementRate = Double.parseDouble(defaultReimbursementRateString);
				return defaultReimbursementRate;
			}catch(Exception e){				
				logger.info("HSRAutoCloseDaoImpl.getDefaultReimursementRate NoResultException");
				return null;
			}
	}
}
