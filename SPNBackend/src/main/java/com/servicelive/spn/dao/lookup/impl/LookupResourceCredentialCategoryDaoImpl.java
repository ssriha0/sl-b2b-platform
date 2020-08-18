package com.servicelive.spn.dao.lookup.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.servicelive.domain.lookup.LookupResourceCredentialCategory;
import com.servicelive.domain.lookup.LookupStates;
import com.servicelive.domain.spn.network.SPNExclusionsVO;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupResourceCredentialCategoryDao;

/**
 * @author Carlos Garcia
 *
 */
@Repository ("lookupResourceCredentialCategoryDao")
public class LookupResourceCredentialCategoryDaoImpl extends AbstractBaseDao implements LookupResourceCredentialCategoryDao {

	@SuppressWarnings("unchecked")
	public List<LookupResourceCredentialCategory> findAll(int... rowStartIdxAndCount)
			throws Exception {
		return (List <LookupResourceCredentialCategory> )super.findAll("LookupResourceCredentialCategory", rowStartIdxAndCount);
	}

	public LookupResourceCredentialCategory findById(Integer id) throws Exception {
		return (LookupResourceCredentialCategory) super.findById(LookupResourceCredentialCategory.class, id);
	}

	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LookupStates> findByProperty(String propertyName, Object value,	int... rowStartIdxAndCount) throws Exception {
		return (List<LookupStates>) super.findByProperty("LookupResourceCredentialCategory",propertyName,value, rowStartIdxAndCount);
	}

	public List<LookupResourceCredentialCategory> getResourceCredentialCategories() throws Exception
	{
		return findAll();
	}

	/**
	 * @param typeIds
	 * @param networkId
	 * @return
	 * method to fetch exceptions
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<SPNExclusionsVO> getExclusionsForCredentialTypes(
			List<Integer> typeIds, int networkId)throws Exception {
		final String queryString = "select model from " + "SPNExclusionsVO" + " model where model."
				+ "spn" + "= :spnHeader" + " AND model.credentialTypeId  IN (:typeIds) AND model.credentialCategoryId IS NULL  AND model.exceptionCredentialType = 'resource'";
		
		Query query = getEntityManager().createQuery(
				queryString);
		SPNHeader header = new SPNHeader();
		header.setSpnId(networkId);
		query.setParameter("spnHeader", header);
		query.setParameter("typeIds", typeIds);
		logger.info(query);
		return (List<SPNExclusionsVO>) query.getResultList();

	}

	/**
	 * @param typeIds
	 * @param networkId
	 * @return
	 * method to fetch exceptions
	 * 
	 */@SuppressWarnings("unchecked")
	public List<SPNExclusionsVO> getExclusionsForCredentialCategories(
			List<Integer> categoryIds, int networkId) throws Exception{
			final String queryString = "select model from " + "SPNExclusionsVO" + " model where model."
					+ "spn" + "= :spnHeader" + " AND model.credentialCategoryId  IN (:categoryIds) AND model.exceptionCredentialType = 'resource'";
			
			Query query = getEntityManager().createQuery(
					queryString);
			SPNHeader header = new SPNHeader();
			header.setSpnId(networkId);
			query.setParameter("spnHeader", header);
			query.setParameter("categoryIds", categoryIds);
			logger.info(query);
			return (List<SPNExclusionsVO>) query.getResultList();
		}
}
