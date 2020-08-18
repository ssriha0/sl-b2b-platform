package com.servicelive.spn.dao.lookup.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.servicelive.domain.lookup.LookupVendorCredentialType;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupVendorCredentialTypeDao;
import static  com.servicelive.spn.common.SPNBackendConstants.PROVIDER_FIRM_INSURANCE_CRED_ID;

/**
 * @author Carlos Garcia
 *
 */
@Repository ("lookupVendorCredentialTypeDao")
public class LookupVendorCredentialTypeDaoImpl extends AbstractBaseDao implements LookupVendorCredentialTypeDao {

	@SuppressWarnings("unchecked")
	public List<LookupVendorCredentialType> findAll(int... rowStartIdxAndCount)
			throws Exception {
		return (List <LookupVendorCredentialType> )super.findAll("LookupVendorCredentialType", rowStartIdxAndCount);
	}

	public LookupVendorCredentialType findById(Integer id) throws Exception {
		return (LookupVendorCredentialType) super.findById(LookupVendorCredentialType.class, id);
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
	public List<LookupVendorCredentialType> findByProperty(String propertyName, Object value, int... rowStartIdxAndCount) throws Exception {
		return (List<LookupVendorCredentialType>) super.findByProperty("LookupVendorCredentialType",propertyName,value, rowStartIdxAndCount);
	}

	public List<LookupVendorCredentialType> getVendorCredentialTypes() throws Exception
	{
		return findAll();
	}

	@SuppressWarnings("unchecked")
	public List<LookupVendorCredentialType> findAllbyRemovingInsurance()
			throws Exception {
		String hql = "from LookupVendorCredentialType o where o.id != "+ PROVIDER_FIRM_INSURANCE_CRED_ID +" ORDER BY o.description";
		Query query = super.getEntityManager().createQuery(hql);
		return query.getResultList(); 
	}
	
	
}
