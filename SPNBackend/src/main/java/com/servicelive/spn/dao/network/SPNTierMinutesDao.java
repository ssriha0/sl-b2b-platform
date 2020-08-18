/**
 * 
 */
package com.servicelive.spn.dao.network;

import java.util.List;

import com.servicelive.domain.spn.network.SPNTierMinutes;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author cgarcia
 *
 */
public interface SPNTierMinutesDao extends BaseDao {
	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<SPNTierMinutes> findAll ( int... rowStartIdxAndCount) throws Exception;
	/**
	 * 
	 * @param id
	 * @return SPNBuyer
	 * @throws Exception
	 */
	public SPNTierMinutes findById(Integer id) throws Exception;
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<SPNTierMinutes> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;
	/**
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void save(SPNTierMinutes entity) throws Exception;
	/**
	 * 
	 * @param entity
	 * @return SPNBuyer
	 * @throws Exception
	 */
	public SPNTierMinutes update(SPNTierMinutes entity) throws Exception;
	/**
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void delete(SPNTierMinutes entity) throws Exception;
	
	/**
	 * 
	 * @param spnId
	 * @param tierId
	 * @return
	 * @throws Exception
	 */
	public int deleteTierMinutes(Integer spnId, Integer tierId) throws Exception;
	/**
	 * 
	 * @param spnId
	 * @return
	 * @throws Exception
	 */
	public int deleteTierMinutes(Integer spnId) throws Exception;
	
	
}
