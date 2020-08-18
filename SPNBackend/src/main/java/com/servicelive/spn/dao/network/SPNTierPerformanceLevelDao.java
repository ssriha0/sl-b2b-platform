/**
 * 
 */
package com.servicelive.spn.dao.network;

import java.util.List;

import com.servicelive.spn.dao.BaseDao;
import com.servicelive.domain.spn.network.SPNTierPerformanceLevel;

/**
 * @author cgarcia
 *
 */
public interface SPNTierPerformanceLevelDao extends BaseDao {
	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<SPNTierPerformanceLevel> findAll ( int... rowStartIdxAndCount) throws Exception;
	/**
	 * 
	 * @param id
	 * @return SPNBuyer
	 * @throws Exception
	 */
	public SPNTierPerformanceLevel findById(Integer id) throws Exception;
	
	
	
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<SPNTierPerformanceLevel> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;
	/**
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void save(SPNTierPerformanceLevel entity) throws Exception;
	/**
	 * 
	 * @param entity
	 * @return SPNBuyer
	 * @throws Exception
	 */
	public SPNTierPerformanceLevel update(SPNTierPerformanceLevel entity) throws Exception;
	/**
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void delete(SPNTierPerformanceLevel entity) throws Exception;
	/**
	 * 
	 * @param spnId
	 * @param tierId
	 * @throws Exception
	 */
	public int deletePerformanceLevels(Integer spnId, Integer tierId) throws Exception;
	
	/**
	 * 
	 * @param spnId
	 * @return
	 * @throws Exception
	 */
	public int deletePerformanceLevels(Integer spnId) throws Exception;
	
}
