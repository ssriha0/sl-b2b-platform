/**
 * 
 */
package com.servicelive.spn.dao.network;

import java.util.List;

import com.servicelive.domain.spn.network.SPNDocument;
import com.servicelive.spn.dao.BaseDao;



/**
 * @author hoza
 *
 */
public interface SPNDocumentDao extends BaseDao {
	/**
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void delete( SPNDocument entity) throws Exception;
	/**
	 * 
	 * @param id
	 * @return SPNDocument
	 * @throws Exception
	 */
	public SPNDocument findById(Integer id) throws Exception;
	
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<SPNDocument> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;
}
