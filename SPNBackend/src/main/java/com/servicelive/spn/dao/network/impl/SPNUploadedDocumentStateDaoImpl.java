package com.servicelive.spn.dao.network.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.spn.network.SPNUploadedDocumentState;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.network.SPNUploadedDocumentStateDao;

/**
 * 
 * @author svanloon
 *
 */
@Repository
@Transactional(readOnly = false)
public class SPNUploadedDocumentStateDaoImpl extends AbstractBaseDao implements SPNUploadedDocumentStateDao {

	@SuppressWarnings("unchecked")
	public List<SPNUploadedDocumentState> find(Integer spnId, Integer providerFirmId) {
		StringBuilder hql = new StringBuilder();
		hql.append("from SPNUploadedDocumentState o ");
		hql.append("where ");
		hql.append("o.spnUploadedDocumentStatePk.providerFirm.id = :providerFirmId ");
		//hql.append("and o.spnUploadedDocumentStatePk.buyer.buyerId = :buyerId ");
		hql.append("and o.spnUploadedDocumentStatePk.buyerDocument.documentId in (select d.document.documentId from SPNDocument d where d.spn.spnId = :spnId)");
		
		Query query = getEntityManager().createQuery(hql.toString());
		query.setParameter("spnId", spnId);
		query.setParameter("providerFirmId", providerFirmId);
		return query.getResultList();
	}

	@Transactional ( readOnly = false, propagation = Propagation.REQUIRED)
	public SPNUploadedDocumentState update(SPNUploadedDocumentState entity) throws Exception {
		SPNUploadedDocumentState docState = (SPNUploadedDocumentState)super.update(entity);
		logger.debug("Update executed successfully for " + this.getClass().getName());
		super.getEntityManager().flush();
		return docState;
	}
	

}
