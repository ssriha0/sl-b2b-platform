package com.servicelive.spn.dao.network.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.spn.network.SPNMeetAndGreetState;
import com.servicelive.domain.spn.network.SPNMeetAndGreetStatePk;
import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.network.SPNMeetAndGreetStateDao;

/**
 * 
 * @author svanloon
 *
 */
public class SPNMeetAndGreetStateDaoImpl extends AbstractBaseDao implements SPNMeetAndGreetStateDao {

	public Boolean isMeetAndGreetRequired(Integer spnId) {
		StringBuilder hql = new StringBuilder();
		hql.append("select count(ac.value) from SPNApprovalCriteria ac, LookupSPNApprovalCriteria lac where lac.description = :criteriaDescription  and ac.criteriaId = lac and ac.spnId.spnId = :spnId and ac.value = :value");
		Query query = getEntityManager().createQuery(hql.toString());
		query.setParameter("spnId", spnId);
		query.setParameter("criteriaDescription", SPNBackendConstants.CRITERIA_MEETING_REQUIRED);
		query.setParameter("value", Boolean.TRUE.toString().toUpperCase());
		Long count = (Long) query.getSingleResult();
		if(count == null) {
			return Boolean.FALSE;
		}
		return Boolean.valueOf(count.longValue() > 0);
	}

	public SPNMeetAndGreetState findById(SPNMeetAndGreetStatePk pk) {
		return (SPNMeetAndGreetState) super.findById(SPNMeetAndGreetState.class, pk);
	}

	@SuppressWarnings("unchecked")
	public List<SPNMeetAndGreetState> findByBuyerIdAndProviderFirmIdExcludingSpnId(Integer buyerId, Integer spnId, Integer providerFirmId) {
		StringBuilder hql = new StringBuilder();
		hql.append("select o from SPNMeetAndGreetState o ");
		hql.append(", SPNBuyer b ");
		hql.append("where ");
		hql.append("o.meetAndGreetStatePk.providerFirm.id = :providerFirmId ");
		hql.append("and ");
		hql.append("b.buyerId.buyerId = :buyerId ");
		hql.append("and ");
		hql.append("b.spnId.spnId != :spnId ");
		hql.append("and ");
		hql.append("o.meetAndGreetStatePk.spnHeader.spnId != :spnId and o.meetAndGreetStatePk.spnHeader.isAlias=0");
		hql.append("and ");
		hql.append("b = some elements(o.meetAndGreetStatePk.spnHeader.buyer) ");

		Query query = getEntityManager().createQuery(hql.toString());
		query.setParameter("buyerId", buyerId);
		query.setParameter("spnId", spnId);
		query.setParameter("providerFirmId", providerFirmId);
		return query.getResultList();
	}

	@Transactional ( propagation = Propagation.REQUIRED)	
	public SPNMeetAndGreetState update(SPNMeetAndGreetState state) throws Exception
	{
		if( findById(state.getMeetAndGreetStatePk()) == null) {
			this.save(state);
			return findById(state.getMeetAndGreetStatePk());
		}
		SPNMeetAndGreetState updatedState = (SPNMeetAndGreetState)super.update(state);
		logger.debug("Update executed successfully for " + this.getClass().getName());
		super.getEntityManager().flush();
		return updatedState;
	}

}
