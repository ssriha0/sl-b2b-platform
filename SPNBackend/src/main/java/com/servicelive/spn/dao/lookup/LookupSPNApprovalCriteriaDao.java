/**
 *
 */
package com.servicelive.spn.dao.lookup;

import java.util.List;

import com.servicelive.domain.lookup.LookupSPNApprovalCriteria;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author hoza
 *
 */
public interface LookupSPNApprovalCriteriaDao extends BaseDao {
	/**
	 * 
	 * @param description
	 * @return List
	 * @throws Exception
	 */
	public List<LookupSPNApprovalCriteria> getApprovalCriteriaForDescription(String description) throws Exception;
	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupSPNApprovalCriteria> findAll ( int... rowStartIdxAndCount) throws Exception;
	/**
	 * 
	 * @param id
	 * @return LookupSPNApprovalCriteria
	 * @throws Exception
	 */
	public LookupSPNApprovalCriteria findById(Integer id) throws Exception ;
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupSPNApprovalCriteria> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;
}
