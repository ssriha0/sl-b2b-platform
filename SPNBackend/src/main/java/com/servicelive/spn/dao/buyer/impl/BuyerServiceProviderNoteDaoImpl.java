package com.servicelive.spn.dao.buyer.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.buyer.BuyerProviderFirmNote;
import com.servicelive.domain.buyer.BuyerServiceProNote;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.buyer.BuyerServiceProviderNoteDao;

/**
 * @author hoza
 *
 */
public class BuyerServiceProviderNoteDaoImpl extends AbstractBaseDao implements BuyerServiceProviderNoteDao {

	@SuppressWarnings("unchecked")
	@Transactional
	public List<BuyerServiceProNote> getServiceProNotes(Integer buyerId, Integer serviceProId) throws Exception {
		String hql = "from BuyerServiceProNote o where o.buyerId.buyerId = :buyerId and o.serviceProId.id = :serviceProId";
		Query query = super.getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);
		query.setParameter("serviceProId", serviceProId);
		return query.getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<BuyerServiceProNote> getServiceProviderNotesForProviderFirm(Integer buyerId, Integer providerFirmId) throws Exception {
			String hql = "from BuyerServiceProNote o where o.buyerId.buyerId = :buyerId and  o.serviceProId.providerFirm.id = :providerFirmId";
			Query query = super.getEntityManager().createQuery(hql);
			query.setParameter("buyerId", buyerId);
			query.setParameter("providerFirmId", providerFirmId);
			return query.getResultList();
	}

	@Transactional
	public BuyerServiceProNote persistServiceProNote(BuyerServiceProNote buyerServiceProNote) throws Exception {
		super.save(buyerServiceProNote);
		super.refresh(buyerServiceProNote);
		return buyerServiceProNote;
	}
}
