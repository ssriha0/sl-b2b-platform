/**
 *
 */
package com.servicelive.spn.dao.lookup;

import java.util.List;

import com.servicelive.domain.lookup.LookupServiceType;
import com.servicelive.domain.lookup.LookupSkills;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author hoza
 *
 */
public interface LookupServiceTypeDao extends BaseDao {
	/**
	 * 
	 * @param serviceTypeId
	 * @return LookupServiceType
	 * @throws Exception
	 */
	public LookupServiceType getServiceTypeFromId(Integer serviceTypeId) throws Exception;
	/**
	 * 
	 * @param skillNodeId
	 * @return List
	 * @throws Exception
	 */
	public List<LookupServiceType> getServiceTypeFromSkillNodeId(LookupSkills skillNodeId) throws Exception;
	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupServiceType> findAll ( int... rowStartIdxAndCount) throws Exception;
	/**
	 * 
	 * @param id
	 * @return LookupServiceType
	 * @throws Exception
	 */
	public LookupServiceType findById(Integer id) throws Exception;
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupServiceType> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;
	
	
	/**
	 * Get the list of LookupServiceType for the specified skill Ids
	 * @param skillIds
	 * @return List
	 * @throws Exception
	 */
	public List<LookupServiceType> getListFromSkillIds (List<Integer> skillIds)  throws Exception; 
}
