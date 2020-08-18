package com.servicelive.spn.dao.lookup.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.servicelive.domain.lookup.LookupVendorCredentialCategory;
import com.servicelive.domain.spn.network.SPNExclusionsVO;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupVendorCredentialCategoryDao;

/**
 * @author Carlos Garcia
 *
 */
@Repository ("lookupVendorCredentialCategoryDao")
public class LookupVendorCredentialCategoryDaoImpl extends AbstractBaseDao implements LookupVendorCredentialCategoryDao {

	@SuppressWarnings("unchecked")
	public List<LookupVendorCredentialCategory> findAll(int... rowStartIdxAndCount)
			throws Exception {
		return (List <LookupVendorCredentialCategory> )super.findAll("LookupVendorCredentialCategory", rowStartIdxAndCount);
	}

	public LookupVendorCredentialCategory findById(Integer id) throws Exception {
		return (LookupVendorCredentialCategory) super.findById(LookupVendorCredentialCategory.class, id);
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
	public List<LookupVendorCredentialCategory> findByProperty(String propertyName, Object value, int... rowStartIdxAndCount) throws Exception {
		return (List<LookupVendorCredentialCategory>) super.findByProperty("LookupVendorCredentialCategory",propertyName,value, rowStartIdxAndCount);
	}

	public List<LookupVendorCredentialCategory> getVendorCredentialCategories() throws Exception
	{
		return findAll();
	}

	/**
	 * @param typeIds
	 * @param networkId
	 * @return
	 * method to fetch exceptions
	 */
	@SuppressWarnings("unchecked")
	public List<SPNExclusionsVO> getExclusionsForCredentialTypes(List<Integer> typeIds, int networkId) throws Exception {
		final String queryString = "select model from " + "SPNExclusionsVO" + " model where model."
				+ "spn" + "= :spnHeader" + " AND model.credentialTypeId  IN (:typeIds) AND model.credentialCategoryId IS NULL AND model.exceptionCredentialType = 'vendor'";
		
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
	 * @param categoryIds
	 * @param networkId
	 * @return
	 * method to fetch exceptions
	 */
	@SuppressWarnings("unchecked")
	public List<SPNExclusionsVO> getExclusionsForCredentialCategories(List<Integer> categoryIds, int networkId)throws Exception {
		final String queryString = "select model from " + "SPNExclusionsVO" + " model where model."
				+ "spn" + "= :spnHeader" + " AND model.credentialCategoryId  IN (:categoryIds) AND model.exceptionCredentialType = 'vendor'";
		
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
