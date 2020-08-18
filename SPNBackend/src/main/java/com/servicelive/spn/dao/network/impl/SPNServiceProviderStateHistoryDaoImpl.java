package com.servicelive.spn.dao.network.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.spn.network.SPNServiceProviderStateHistory;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.network.SPNServiceProviderStateHistoryDao;

public class SPNServiceProviderStateHistoryDaoImpl extends AbstractBaseDao implements SPNServiceProviderStateHistoryDao {

	@SuppressWarnings("unchecked")
	@Transactional
	public List<SPNServiceProviderStateHistory> findServiceProviderStateHistory(Integer buyerId, Integer serviceProviderId) {
		StringBuilder hql = new StringBuilder();
		hql.append("select s from SPNServiceProviderStateHistory s ");
		hql.append(", SPNBuyer b ");
		hql.append("where ");
		hql.append("s.serviceProvider.id = :serviceProviderId ");
		hql.append("and ");
		hql.append("b.buyerId.buyerId = :buyerId ");
		hql.append("and ");
		hql.append("b.spnId.spnId = s.spnHeader.spnId ");
		hql.append("and ");
		hql.append("b = some elements(s.spnHeader.buyer) ");
		hql.append("order by s.modifiedDate desc");
		Query query = getEntityManager().createQuery(hql.toString());
		query.setParameter("buyerId", buyerId);
		query.setParameter("serviceProviderId", serviceProviderId);
		return query.getResultList();
	}
}
