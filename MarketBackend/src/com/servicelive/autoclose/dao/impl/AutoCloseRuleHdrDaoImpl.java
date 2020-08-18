package com.servicelive.autoclose.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.autoclose.dao.AutoCloseRuleHdrDao;
import com.servicelive.domain.autoclose.AutoCloseRuleCriteria;
import com.servicelive.domain.autoclose.AutoCloseRuleCriteriaHistory;
import com.servicelive.domain.autoclose.AutoCloseRuleFirmAssoc;
import com.servicelive.domain.autoclose.AutoCloseRuleFirmAssocHistory;
import com.servicelive.domain.autoclose.AutoCloseRuleProviderAssocHistory;
import com.servicelive.domain.autoclose.AutoCloseRuleHdr;
import com.servicelive.domain.autoclose.AutoCloseRuleProviderAssoc;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.provider.ServiceProvider;

/**
 * 
 * @author Infosys
 * 
 */
public class AutoCloseRuleHdrDaoImpl extends AbstractBaseDao implements AutoCloseRuleHdrDao {
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<AutoCloseRuleHdr> findByBuyerId(Integer buyerId) {
		String hql = "from AutoCloseRuleHdr where buyerId = :buyerId) ";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);
		try {
		
			@SuppressWarnings("unchecked")
			List<AutoCloseRuleHdr> autoCloseRuleHdrList = query.getResultList();
			return autoCloseRuleHdrList;
		} catch (NoResultException e) {
			logger.info("AutoCloseRuleHdrDaoImpl.findByBuyerId NoResultException");
			return null;
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public AutoCloseRuleCriteria findAutoCloseRuleCriteriaValue(
			Integer autoCloseRuleHdrId) {
		String hql = "from AutoCloseRuleCriteria where autoCloseRuleHdrId = :autoCloseRuleHdrId) ";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("autoCloseRuleHdrId", autoCloseRuleHdrId);
		AutoCloseRuleCriteria result;
		try {
			result = (AutoCloseRuleCriteria) query.getSingleResult();
		} catch (NoResultException e) {
			logger.info("AutoCloseRuleHdrDaoImpl.findAutoCloseRuleCriteriaValue NoResultException");
			result = null;
		}
		return result;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public AutoCloseRuleCriteria findAutoCloseRuleCriteriaById(
			Integer criteriaId) {
		return (AutoCloseRuleCriteria) super.findById(AutoCloseRuleCriteria.class, criteriaId);
	}

	public void saveAutoCloseRuleCriteria(
			AutoCloseRuleCriteria autoCloseRuleCriteria) {
		try {
			super.update(autoCloseRuleCriteria);
		} catch (Exception e) {
			logger.info("AutoCloseRuleHdrDaoImpl.saveAutoCloseRuleCriteria "+e);
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public void saveAutoCloseRuleCriteriaHistory(
			AutoCloseRuleCriteria autoCloseRuleCriteria) {
		AutoCloseRuleCriteriaHistory autoCloseRuleCriteriaHistory = new AutoCloseRuleCriteriaHistory();
		try {
			BeanUtils.copyProperties(autoCloseRuleCriteriaHistory, autoCloseRuleCriteria);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		try {
			super.save(autoCloseRuleCriteriaHistory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<AutoCloseRuleCriteriaHistory> findAutoCloseRuleCriteriaHistoryByCriteria(
			Integer autoCloseRuleCriteriaId) {
		/*List<AutoCloseRuleCriteriaHistory> criteriaHistoryList = (List<AutoCloseRuleCriteriaHistory>) super.findByProperty(AutoCloseRuleCriteriaHistory.class.getName(), "autoCloseRuleCriteriaId", autoCloseRuleCriteriaId);
		return criteriaHistoryList;*/
		
		String hql = "from AutoCloseRuleCriteriaHistory where autoCloseRuleCriteriaId = :autoCloseRuleCriteriaId order by autoCloseRuleCriteriaHistId desc) ";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("autoCloseRuleCriteriaId", autoCloseRuleCriteriaId);
		try {
		
			@SuppressWarnings("unchecked")
			List<AutoCloseRuleCriteriaHistory> criteriaHistoryList = query.getResultList();
			return criteriaHistoryList;
		} catch (NoResultException e) {
			logger.info("AutoCloseRuleHdrDaoImpl.findAutoCloseRuleFirmExclusionList NoResultException");
			return null;
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<AutoCloseRuleFirmAssoc> findAutoCloseRuleFirmExclusionList(Integer autoCloseRuleHdrId,
			boolean activeInd) {
		String hql = "from AutoCloseRuleFirmAssoc where autoCloseRuleHdrId = :autoCloseRuleHdrId and active = :activeInd order by modifiedDate desc";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("autoCloseRuleHdrId", autoCloseRuleHdrId);
		query.setParameter("activeInd", activeInd);
		try {
		
			@SuppressWarnings("unchecked")
			List<AutoCloseRuleFirmAssoc> autoCloseRuleFirmAssocList = query.getResultList();
			return autoCloseRuleFirmAssocList;
		} catch (NoResultException e) {
			logger.info("AutoCloseRuleHdrDaoImpl.findAutoCloseRuleFirmExclusionList NoResultException");
			return null;
		}
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<AutoCloseRuleProviderAssoc> findAutoCloseRuleProvExclusionList(Integer autoCloseRuleHdrId,
			boolean activeInd) {
		String hql = "from AutoCloseRuleProviderAssoc where autoCloseRuleHdrId = :autoCloseRuleHdrId and active = :activeInd order by modifiedDate desc";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("autoCloseRuleHdrId", autoCloseRuleHdrId);
		query.setParameter("activeInd", activeInd);
		try {
		
			@SuppressWarnings("unchecked")
			List<AutoCloseRuleProviderAssoc> autoCloseRuleProvAssocList = query.getResultList();
			return autoCloseRuleProvAssocList;
		} catch (NoResultException e) {
			logger.info("AutoCloseRuleHdrDaoImpl.findAutoCloseRuleProvExclusionList NoResultException");
			return null;
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public AutoCloseRuleFirmAssoc findAutoCloseRuleFirmAssocById(
			Integer ruleAssocId) {
		return (AutoCloseRuleFirmAssoc) super.findById(AutoCloseRuleFirmAssoc.class, ruleAssocId);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public AutoCloseRuleFirmAssoc saveAutoCloseRuleFirmAssoc(
			AutoCloseRuleFirmAssoc autoCloseRuleFirmAssoc) {
		AutoCloseRuleFirmAssoc autoCloseRuleFirmAssocUpdated = null;
		try {
			autoCloseRuleFirmAssocUpdated = (AutoCloseRuleFirmAssoc) super.update(autoCloseRuleFirmAssoc);

		} catch (Exception e) {
			logger.info("AutoCloseRuleHdrDaoImpl.saveAutoCloseRuleFirmAssoc "+e);
		}
		return autoCloseRuleFirmAssocUpdated; 
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public AutoCloseRuleProviderAssoc findAutoCloseRuleProviderAssocById(
			Integer ruleAssocId) {
		return (AutoCloseRuleProviderAssoc) super.findById(AutoCloseRuleProviderAssoc.class, ruleAssocId);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public AutoCloseRuleProviderAssoc saveAutoCloseRuleProviderAssoc(
			AutoCloseRuleProviderAssoc autoCloseRuleProviderAssoc) {
		AutoCloseRuleProviderAssoc autoCloseRuleProviderAssocUpdated = null;
		try {
			autoCloseRuleProviderAssocUpdated = (AutoCloseRuleProviderAssoc) super.update(autoCloseRuleProviderAssoc);
		} catch (Exception e) {
			logger.info("AutoCloseRuleHdrDaoImpl.saveAutoCloseRuleProviderAssoc "+e);
		}
		return autoCloseRuleProviderAssocUpdated;
	}
	
	public List<ProviderFirm> searchByFirmName(String firmName){
		String hql = "from ProviderFirm where businessName LIKE :proFirmName ";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("proFirmName", "%" + firmName + "%");
		query.setFirstResult(0);
		query.setMaxResults(30);
		try {
		
			@SuppressWarnings("unchecked")
			List<ProviderFirm> firmList = query.getResultList();
			return firmList;
		} catch (NoResultException e) {
			logger.info("AutoCloseRuleHdrDaoImpl.searchByFirmName NoResultException");
			return null;
		}
	}
	
	public List<ProviderFirm> searchByFirmIds(List<Integer> firmIds){
		String hql = "from ProviderFirm where id IN (:providerFirmIds) ";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("providerFirmIds", firmIds);
		try {
		
			@SuppressWarnings("unchecked")
			List<ProviderFirm> firmList = query.getResultList();
			return firmList;
		} catch (NoResultException e) {
			logger.info("AutoCloseRuleHdrDaoImpl.searchByFirmIds NoResultException");
			return null;
		}
	}
	
	public List<ServiceProvider> searchByProvName(String provName){
		String hql = "from ServiceProvider where (contact.lastName LIKE :provName) OR (contact.firstName LIKE :provName) ";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("provName", "%" + provName + "%");
		query.setFirstResult(0);
		query.setMaxResults(30);
		try {
		
			@SuppressWarnings("unchecked")
			List<ServiceProvider> provList = query.getResultList();
			return provList;
		} catch (NoResultException e) {
			logger.info("AutoCloseRuleHdrDaoImpl.searchByFirmName NoResultException");
			return null;
		}
	}
	
	public List<ServiceProvider> searchByProvIds(List<Integer> provIds){
		String hql = "from ServiceProvider where id IN (:provIds) ";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("provIds", provIds);
		try {
		
			@SuppressWarnings("unchecked")
			List<ServiceProvider> provList = query.getResultList();
			return provList;
		} catch (NoResultException e) {
			logger.info("AutoCloseRuleHdrDaoImpl.searchByFirmIds NoResultException");
			return null;
		}
	}

	/*public List<Integer> getExcludedFirmIds(List<ProviderFirm> firmList) {
		String hql = "select model.firm.id from AutoCloseRuleFirmAssoc model where model.firm IN (:firms) and active = :activeInd order by autoCloseRuleFirmAssocId desc";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("firms", firmList);
		query.setParameter("activeInd", false);
		try {
		
			@SuppressWarnings("unchecked")
			List<Integer> excludedFirmList = query.getResultList();
			return excludedFirmList;
		} catch (NoResultException e) {
			logger.info("AutoCloseRuleHdrDaoImpl.getExcludedFirmIds NoResultException");
			return null;
		}
	}*/
	
	public List<Integer> getExcludedFirmIds(List<ProviderFirm> firmList , String buyerId) {
		String nativeQuery="select acr.firm_id from auto_close_rule_firm_assoc acr inner join auto_close_rule_hdr acrh on "
				+"acr.auto_close_rule_hdr_id = acrh.auto_close_rule_hdr_id where acrh.buyer_id = :buyerId and "
				+"acr.firm_id in(:firms) and acr.active_ind= :activeInd";
		Query query = getEntityManager().createNativeQuery(nativeQuery);
		query.setParameter("firms", firmList);
		query.setParameter("buyerId", buyerId);
		query.setParameter("activeInd", false);
		try {
		
			@SuppressWarnings("unchecked")
			List<Integer> excludedFirmList = query.getResultList();
			return excludedFirmList;
		} catch (NoResultException e) {
			logger.info("AutoCloseRuleHdrDaoImpl.getExcludedFirmIds NoResultException");
			return null;
		}
	}

	/*public List<Integer> getExcludedProvIds(List<ServiceProvider> provList) {
		String hql = "select model.provider.id from AutoCloseRuleProviderAssoc model where model.provider IN (:providers) and active = :activeInd order by autoCloseRuleProvAssocId desc";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("providers", provList);
		query.setParameter("activeInd", false);
		try {
		
			@SuppressWarnings("unchecked")
			List<Integer> excludedFirmList = query.getResultList();
			return excludedFirmList;
		} catch (NoResultException e) {
			logger.info("AutoCloseRuleHdrDaoImpl.getExcludedProvIds NoResultException");
			return null;
		}
	}
	*/
	public List<Integer> getExcludedProvIds(List<ServiceProvider> provList , String buyerId) {
		String nativeQuery="select acr.provider_id from auto_close_rule_provider_assoc acr inner join auto_close_rule_hdr acrh on "
				+"acr.auto_close_rule_hdr_id = acrh.auto_close_rule_hdr_id where acrh.buyer_id = :buyerId and "
				+"acr.provider_id in(:providers) and acr.active_ind= :activeInd";
		Query query = getEntityManager().createNativeQuery(nativeQuery);
		query.setParameter("providers", provList);
		query.setParameter("buyerId", buyerId);
		query.setParameter("activeInd", false);
		try {
		
			@SuppressWarnings("unchecked")
			List<Integer> excludedFirmList = query.getResultList();
			return excludedFirmList;
		} catch (NoResultException e) {
			logger.info("AutoCloseRuleHdrDaoImpl.getExcludedProvIds NoResultException");
			return null;
		}
	}

	public void saveAutoCloseRuleFirmAssocHistory(
			AutoCloseRuleFirmAssoc autoCloseRuleFirmAssoc) {
		AutoCloseRuleFirmAssocHistory autoCloseRuleFirmAssocHistory = new AutoCloseRuleFirmAssocHistory();
		try {
			BeanUtils.copyProperties(autoCloseRuleFirmAssocHistory, autoCloseRuleFirmAssoc);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		try {
			super.save(autoCloseRuleFirmAssocHistory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveAutoCloseRuleProviderAssocHistory(
			AutoCloseRuleProviderAssoc autoCloseRuleProviderAssoc) {
		AutoCloseRuleProviderAssocHistory autoCloseRuleProviderAssocHistory = new AutoCloseRuleProviderAssocHistory();
		try {
			BeanUtils.copyProperties(autoCloseRuleProviderAssocHistory, autoCloseRuleProviderAssoc);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		try {
			super.save(autoCloseRuleProviderAssocHistory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<AutoCloseRuleFirmAssocHistory> findAutoCloseRuleFirmExclusionHistory(
			Integer autoCloseRuleAssocId) {
		String hql = "from AutoCloseRuleFirmAssocHistory where autoCloseRuleFirmAssocId  = :autoCloseRuleAssocId order by modifiedDate desc";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("autoCloseRuleAssocId", autoCloseRuleAssocId);
		try {
		
			@SuppressWarnings("unchecked")
			List<AutoCloseRuleFirmAssocHistory> firmAssocHistory = query.getResultList();
			return firmAssocHistory;
		} catch (NoResultException e) {
			logger.info("AutoCloseRuleHdrDaoImpl.findAutoCloseRuleFirmExclusionHistory NoResultException");
			return null;
		}
	}

	public List<AutoCloseRuleProviderAssocHistory> findAutoCloseRuleProviderExclusionHistory(
			Integer autoCloseRuleAssocId) {
		String hql = "from AutoCloseRuleProviderAssocHistory where autoCloseRuleProvAssocId = :autoCloseRuleAssocId order by modifiedDate desc";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("autoCloseRuleAssocId", autoCloseRuleAssocId);
		try {
		
			@SuppressWarnings("unchecked")
			List<AutoCloseRuleProviderAssocHistory> providerAssocHistory = query.getResultList();
			return providerAssocHistory;
		} catch (NoResultException e) {
			logger.info("AutoCloseRuleHdrDaoImpl.findAutoCloseRuleFirmExclusionHistory NoResultException");
			return null;
		}
	}

	public AutoCloseRuleFirmAssoc findAutoCloseRuleFirmAssocByFirmId(
			Integer exclusionId, Integer id) {
		String hql = "from AutoCloseRuleFirmAssoc where autoCloseRuleHdrId = :autoCloseRuleHdrId and firm.id = :id";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("autoCloseRuleHdrId", exclusionId);
		query.setParameter("id", id);
		try {
		
			@SuppressWarnings("unchecked")
			AutoCloseRuleFirmAssoc autoCloseRuleFirmAssoc = (AutoCloseRuleFirmAssoc) query.getSingleResult();
			return autoCloseRuleFirmAssoc;
		} catch (NoResultException e) {
			logger.info("AutoCloseRuleHdrDaoImpl.findAutoCloseRuleFirmAssocByFirmId NoResultException");
			return null;
		}
	}

	public AutoCloseRuleProviderAssoc findAutoCloseRuleProvAssocByProvId(
			Integer exclusionId, Integer id) {
		String hql = "from AutoCloseRuleProviderAssoc where autoCloseRuleHdrId = :autoCloseRuleHdrId and provider.id = :id";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("autoCloseRuleHdrId", exclusionId);
		query.setParameter("id", id);
		try {
		
			@SuppressWarnings("unchecked")
			AutoCloseRuleProviderAssoc autoCloseRuleProviderAssoc = (AutoCloseRuleProviderAssoc) query.getSingleResult();
			return autoCloseRuleProviderAssoc;
		} catch (NoResultException e) {
			logger.info("AutoCloseRuleHdrDaoImpl.findAutoCloseRuleProvAssocByProvId NoResultException");
			return null;
		}
	}

}
