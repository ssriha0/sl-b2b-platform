package com.servicelive.spn.dao.network.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.spn.network.SPNProviderFirmStateHistory;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.network.SPNProviderFirmStateHistoryDao;

/**
 * 
 * @author svanloon
 *
 */
public class SPNProviderFirmStateHistoryDaoImpl extends AbstractBaseDao implements SPNProviderFirmStateHistoryDao {

	@SuppressWarnings("unchecked")
	@Transactional
	public List<SPNProviderFirmStateHistory> findProviderFirmStatusHistory(Integer buyerId, Integer providerFirmId) {
		StringBuilder hql = new StringBuilder();
		hql.append("select s from SPNProviderFirmStateHistory s ");
		hql.append(", SPNBuyer b ");
		hql.append("where ");
		hql.append("s.providerFirm.id = :providerFirmId ");
		hql.append("and ");
		hql.append("b.buyerId.buyerId = :buyerId ");
		hql.append("and ");
		hql.append("b.spnId.spnId = s.spnHeader.spnId ");
		hql.append("and ");
		hql.append("b = some elements(s.spnHeader.buyer) ");
		hql.append("order by s.modifiedDate desc");
		Query query = getEntityManager().createQuery(hql.toString());
		query.setParameter("buyerId", buyerId);
		query.setParameter("providerFirmId", providerFirmId);
		List<SPNProviderFirmStateHistory> list = query.getResultList();
		if(list.size() > 0 ) {
			list.get(0).getLookupSPNStatusOverrideReason(); 
		}
		return list;
	}
}
