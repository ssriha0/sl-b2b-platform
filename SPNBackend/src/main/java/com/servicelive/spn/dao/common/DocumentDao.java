/**
 * 
 */
package com.servicelive.spn.dao.common;

import com.servicelive.domain.common.Document;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author hoza
 *
 */
public interface DocumentDao extends BaseDao {
	/**
	 * 
	 * @param id
	 * @return Document
	 * @throws Exception
	 */
	public Document findById(Integer id) throws Exception;
	public Document findById_spn(Integer id) throws Exception;
	/**
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void save(Document entity) throws Exception;
	/**
	 * 
	 * @param entity
	 * @return Document
	 * @throws Exception
	 */
	public Document update(Document entity) throws Exception;
	/**
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void delete(Document entity) throws Exception;
}
