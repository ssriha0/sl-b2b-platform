package com.servicelive.spn.dao.buyer.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.buyer.BuyerProviderFirmNote;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.buyer.BuyerProviderFirmNoteDao;


/**
 * @author hoza
 *
 */
public class BuyerProviderFirmNoteDaoImpl extends AbstractBaseDao implements  BuyerProviderFirmNoteDao {

	@SuppressWarnings("unchecked")
	@Transactional
	public List<BuyerProviderFirmNote> getProviderFirmNotes(Integer buyerId, Integer providerFirmId) throws Exception {
			String hql = "from BuyerProviderFirmNote o where o.buyerId.buyerId = :buyerId and o.providerFirmId.id = :providerFirmId";
			Query query = super.getEntityManager().createQuery(hql);
			query.setParameter("buyerId", buyerId);
			query.setParameter("providerFirmId", providerFirmId);
			return query.getResultList();
	}

	@Transactional
	public BuyerProviderFirmNote persistProviderFirmNote(BuyerProviderFirmNote buyerProviderFirmNote) throws Exception {
		super.save(buyerProviderFirmNote);
		super.refresh(buyerProviderFirmNote);
		return buyerProviderFirmNote;
	}
}
