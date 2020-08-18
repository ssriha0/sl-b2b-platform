/**
 *
 */
package com.servicelive.spn.dao.lookup.impl;

import java.util.List;

import com.servicelive.domain.lookup.LookupSPNApprovalCriteria;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupSPNApprovalCriteriaDao;

/**
 * @author hoza
 *
 */
public class LookupSPNApprovalCriteriaDaoImpl extends AbstractBaseDao implements
		LookupSPNApprovalCriteriaDao {
	
	public List<LookupSPNApprovalCriteria> getApprovalCriteriaForDescription(String description) throws Exception
	{
		List<LookupSPNApprovalCriteria> approvalCriteria = findByProperty("description", description);
		return approvalCriteria;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.lookup.LookupSPNApprovalCriteriaDao#findAll(int[])
	 */
	@SuppressWarnings("unchecked")
	public List<LookupSPNApprovalCriteria> findAll(int... rowStartIdxAndCount)
			throws Exception {
		return (List<LookupSPNApprovalCriteria>)super.findAll("LookupSPNApprovalCriteria", rowStartIdxAndCount);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.lookup.LookupSPNApprovalCriteriaDao#findById(java.lang.Integer)
	 */
	public LookupSPNApprovalCriteria findById(Integer id) throws Exception {
		return (LookupSPNApprovalCriteria) super.findById(LookupSPNApprovalCriteria.class, id);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.lookup.LookupSPNApprovalCriteriaDao#findByProperty(java.lang.String, java.lang.Object, int[])
	 */
	@SuppressWarnings("unchecked")
	public List<LookupSPNApprovalCriteria> findByProperty(String propertyName,
			Object value, int... rowStartIdxAndCount) throws Exception {
		return (List<LookupSPNApprovalCriteria>)super.findByProperty("LookupSPNApprovalCriteria", propertyName, value, rowStartIdxAndCount);
	}


}
