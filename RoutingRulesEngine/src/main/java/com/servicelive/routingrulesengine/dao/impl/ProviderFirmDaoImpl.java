package com.servicelive.routingrulesengine.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.routingrulesengine.dao.ProviderFirmDao;
/**
 * 
 * @author svanloon
 *
 */
public class ProviderFirmDaoImpl extends AbstractBaseDao implements ProviderFirmDao {

	@Transactional(propagation=Propagation.SUPPORTS)
	public ProviderFirm findById(Integer id) {
		return (ProviderFirm) super.findById(ProviderFirm.class, id);
	}
	
	
	/**
	 * method to validate provider firm ids
	 * 
	 * @param providerFirmIds:List
	 *            of firm ids to be validated
	 * @return List<Integer>:List of valid firm ids
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Integer> validateFirmIds(List<Integer> providerFirmIds) {
		String hql = "select firm.id from ProviderFirm as firm where firm.id in (:firmIds)";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("firmIds", providerFirmIds);
		try {
			@SuppressWarnings("unchecked")
			List<Integer> validFirmList = query.getResultList();
			return validFirmList;
		} catch (NoResultException e) {
			return null;
		}
	}
}
