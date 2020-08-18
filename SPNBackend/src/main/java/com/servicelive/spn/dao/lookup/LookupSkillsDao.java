/**
 *
 */
package com.servicelive.spn.dao.lookup;

import java.util.List;
import java.util.Set;

import com.servicelive.domain.lookup.LookupSkills;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author hoza
 *
 */
public interface LookupSkillsDao extends BaseDao {
	/**
	 * 
	 * @param nodeId
	 * @return LookupSkills
	 * @throws Exception
	 */
	public LookupSkills getSkillNodeForId(Integer nodeId)  throws Exception;
	/**
	 * 
	 * @return List
	 * @throws Exception
	 */
	public List<LookupSkills> getRootNodes() throws Exception;
	/**
	 * 
	 * @param parentNodeId
	 * @return List
	 * @throws Exception
	 */
	public List<LookupSkills> getSubCategoriesFromParentNodeId(Integer parentNodeId) throws Exception;
	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupSkills> findAll ( int... rowStartIdxAndCount) throws Exception;
	/**
	 * 
	 * @param id
	 * @return LookupSkills
	 * @throws Exception
	 */
	public LookupSkills findById(Integer id) throws Exception;
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupSkills> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;
	/**
	 * 
	 * @param nodeIds
	 * @return List
	 * @throws Exception
	 */
	public List<LookupSkills> findAllSkillsByList (Set<Integer> nodeIds )  throws Exception; 
}
