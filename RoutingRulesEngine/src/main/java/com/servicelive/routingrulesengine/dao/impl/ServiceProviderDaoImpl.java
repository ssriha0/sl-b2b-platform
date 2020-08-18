package com.servicelive.routingrulesengine.dao.impl;

import java.util.List;

import javax.persistence.Query;

import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.routingrulesengine.dao.ServiceProviderDao;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * @author svanloon
 *
 */
public class ServiceProviderDaoImpl extends AbstractBaseDao implements ServiceProviderDao {

	/**
	 * 
	 * @return List<ServiceProvider>
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<ServiceProvider> findByProviderFirmId(Integer providerFirmId) {
		String hql = "from ServiceProvider p where p.providerFirm.id = :providerFirmId and p.mktPlaceInd = 1";
		Query query = this.getEntityManager().createQuery(hql);
		query.setParameter("providerFirmId", providerFirmId);
		return query.getResultList();
	}
}
