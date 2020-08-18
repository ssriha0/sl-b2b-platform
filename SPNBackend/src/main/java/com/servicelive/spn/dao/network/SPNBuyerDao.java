/**
 * 
 */
package com.servicelive.spn.dao.network;

import java.util.List;

import com.servicelive.domain.spn.network.SPNBuyer;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author hoza
 *
 */
public interface SPNBuyerDao extends BaseDao {
	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<SPNBuyer> findAll ( int... rowStartIdxAndCount) throws Exception;
	/**
	 * 
	 * @param id
	 * @return SPNBuyer
	 * @throws Exception
	 */
	public SPNBuyer findById(Integer id) throws Exception;
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<SPNBuyer> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;
	/**
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void save(SPNBuyer entity) throws Exception;
	/**
	 * 
	 * @param entity
	 * @return SPNBuyer
	 * @throws Exception
	 */
	public SPNBuyer update(SPNBuyer entity) throws Exception;
	/**
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void delete(SPNBuyer entity) throws Exception;
	/**
	 * R11_0 SL_19387
	 * This method returns the value of that feature set.
	 * @param buyerId
	 * @param feature
	 */
	public String getBuyerFeatureSetValue(Integer buyerId, String feature)throws Exception;

}
