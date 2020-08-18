/**
 *
 */
package com.servicelive.spn.dao.network.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.spn.network.SPNExclusionsVO;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.network.SPNHeaderDao;

/**
 * @author hoza
 *
 */
@Repository
@Transactional()
public class SPNHeaderDaoImpl extends AbstractBaseDao implements SPNHeaderDao {

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.network.SPNHeaderDao#delete(com.servicelive.domain.spn.network.SPNHeader)
	 */
	public void delete(SPNHeader entity) throws Exception {
		// delete the entries in the child table 
		deleteFirmCredentialsForAliasSpn(entity.getSpnId());
		deleteProviderCredentialsForAliasSpn(entity.getSpnId());

			super.delete(entity);
			super.getEntityManager().flush();
			logger.debug("delete executed successfully for " + this.getClass().getName());
	}

	
	//SL 19036 Method to delete all entry from spnet_provider_firm_credential_status
	@Transactional(propagation = Propagation.REQUIRED)
	   public void deleteFirmCredentialsForAliasSpn(Integer spnId) throws Exception
	   {
		String hql = null;
		Query query = null;
		//Updating routing rule vendor for particular vendor belonging to a particular rule
					hql = "DELETE from spnet_provider_firm_credential_status  WHERE spn_id = :spnId";
							 query = getEntityManager().createNativeQuery(hql);
							 query.setParameter("spnId", spnId);
							
							 query.executeUpdate();
		
	   }
	
	//SL 19036 Method to delete all entry from spnet_service_provider_credential_status
	@Transactional(propagation = Propagation.REQUIRED)
	   public void deleteProviderCredentialsForAliasSpn(Integer spnId) throws Exception
	   {
		String hql = null;
		Query query = null;
		//Updating routing rule vendor for particular vendor belonging to a particular rule
					hql = "DELETE from spnet_service_provider_credential_status  WHERE spn_id = :spnId";
							 query = getEntityManager().createNativeQuery(hql);
							 query.setParameter("spnId", spnId);
							
							 query.executeUpdate();
		
	   }
	
	
	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.network.SPNHeaderDao#findAll(int[])
	 */
	@SuppressWarnings("unchecked")
	public List<SPNHeader> findAll(int... rowStartIdxAndCount) throws Exception {
		return ( List <SPNHeader> )super.findAll("SPNHeader", rowStartIdxAndCount);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.network.SPNHeaderDao#findById(java.lang.Integer)
	 */
	public SPNHeader findById(Integer id) throws Exception {
		return ( SPNHeader) super.findById(SPNHeader.class, id);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.network.SPNHeaderDao#findByProperty(java.lang.String, java.lang.Object, int[])
	 */
	@SuppressWarnings("unchecked")
	public List<SPNHeader> findByProperty(String propertyName, Object value,
			int... rowStartIdxAndCount) throws Exception {
		return ( List<SPNHeader>) super.findByProperty("SPNHeader",propertyName,value, rowStartIdxAndCount);
	}

	@Transactional ( propagation = Propagation.REQUIRED)
	@SuppressWarnings("unchecked")
	public List<SPNHeader> findAliases(Integer spnId) throws Exception {
		String hql = "from SPNHeader h where h.isAlias = :isAlias and h.aliasOriginalSpnId = :spnId";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("spnId", spnId);
		query.setParameter("isAlias", Boolean.TRUE);
		return query.getResultList();
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.network.SPNHeaderDao#save(com.servicelive.domain.spn.network.SPNHeader)
	 */
	@Transactional ( propagation = Propagation.REQUIRED)
	public void save(SPNHeader entity) throws Exception {
		super.save(entity);
		super.getEntityManager().flush();
		logger.debug("Save executed successfully for " + this.getClass().getName());
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.network.SPNHeaderDao#update(com.servicelive.domain.spn.network.SPNHeader)
	 */
	public SPNHeader update(SPNHeader entity) throws Exception {
		SPNHeader hdr = (SPNHeader)super.update(entity);  
		super.getEntityManager().flush();
		return hdr;
	}

	@SuppressWarnings("unchecked")
	@Transactional ( propagation = Propagation.REQUIRED)
	public List<SPNHeader> findExpiredSPNNetworks() throws Exception {
		String hql = "from SPNHeader h where h.isAlias = :isAlias and h.effectiveDate is not null and h.effectiveDate <= :now";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("now", CalendarUtil.getTodayAtMidnight());
		query.setParameter("isAlias", Boolean.TRUE);
		return query.getResultList();
	}
	
	/**
	 * 
	 * @param spnId
	 * @return SPNHeader
	 * @throws Exception
	 */
	public SPNHeader getSpnNetwork(Integer spnId) throws Exception{
		SPNHeader  spnHeader = findById(spnId);
		return spnHeader;
		
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.network.SPNHeaderDao#removeExistingApprovalCriteria(java.lang.Integer)
	 */
	@Transactional ( propagation = Propagation.REQUIRED)
	public int removeExistingApprovalCriteria(Integer spnId) throws Exception {
		
		Query query = getEntityManager().createQuery("delete from SPNApprovalCriteria o where o.spnId =  :spnHeader");
		
		SPNHeader header = new SPNHeader();
		header.setSpnId(spnId);
		query.setParameter("spnHeader", header);
		int deleted = query.executeUpdate();
		logger.debug("Deleted " + deleted+ " SPNApproval Criteria");
		return deleted;
		
	}
	/**
	 * 
	 */
	@Transactional ( propagation = Propagation.REQUIRED)
	public int removeExistingDocuments(Integer spnId) throws Exception {
		Query query = getEntityManager().createQuery("delete from SPNDocument o where o.spn =  :spnHeader");
		
		SPNHeader header = new SPNHeader();
		header.setSpnId(spnId);
		query.setParameter("spnHeader", header);
		int deleted = query.executeUpdate();
		logger.debug("Deleted " + deleted + " SPNDocument");
		return deleted;

	}

	@Override
	public void flush() throws Exception {
		super.flush();
	}
	
	/**
	 * 
	 */
	@Transactional ( propagation = Propagation.REQUIRED)
	public Boolean hasExceptions(Integer spnId)throws Exception {
		String	hql = "SELECT COUNT(cred_exception_id) FROM spnet_credential_exception WHERE spn_id ="+spnId;
		Query query = getEntityManager().createNativeQuery(hql);
		Number  retValue = (Number)query.getSingleResult();
		if(retValue!=null && retValue.intValue()>=1)
		{
			return  true;	
		}
		else
		{
			return false;
		}

	}



	@Transactional ( propagation = Propagation.REQUIRED)
	public void removeExistingExceptions(Integer spnId) throws Exception {
		
		Query query = getEntityManager().createQuery("delete from SPNExclusionsVO o where o.spn =  :spnHeader");
		
		SPNHeader header = new SPNHeader();
		header.setSpnId(spnId);
		query.setParameter("spnHeader", header);
		int deleted = query.executeUpdate();
		logger.debug("Deleted " + deleted+ " SPNExclusionsVO");
		
	}


	@SuppressWarnings("unchecked")
	@Transactional ( propagation = Propagation.REQUIRED)
	public List<SPNExclusionsVO> getSPNExclusions(Integer spnId) {
		
		Query query = getEntityManager().createQuery("SELECT o from SPNExclusionsVO o where o.spn =  :spnHeader");
		
		SPNHeader header = new SPNHeader();
		header.setSpnId(spnId);
		query.setParameter("spnHeader", header);
		return (List<SPNExclusionsVO>)query.getResultList();
		
	}


	public void deleteNetworkOverrideInfo(Integer spnId) throws Exception {
		String hql = "delete from ProviderFirmNetworkStatusOverride o where o.spnHeader.spnId = :spnId";
		Query query = super.getEntityManager().createQuery(hql);
		query.setParameter("spnId", spnId);
		query.executeUpdate();
		
		hql = "delete from ProviderNetworkStatusOverride o where o.spnHeader.spnId = :spnId";
		query = super.getEntityManager().createQuery(hql);
		query.setParameter("spnId", spnId);
		query.executeUpdate();
	}
 
	

}
