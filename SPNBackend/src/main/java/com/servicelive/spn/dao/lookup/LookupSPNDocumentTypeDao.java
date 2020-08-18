/**
 *
 */
package com.servicelive.spn.dao.lookup;

import java.util.List;

import com.servicelive.domain.lookup.LookupSPNDocumentType;
import com.servicelive.spn.dao.BaseDao;


/**
 * @author hoza
 * 
 */
public interface LookupSPNDocumentTypeDao extends BaseDao {
	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupSPNDocumentType> findAll ( int... rowStartIdxAndCount) throws Exception;
	/**
	 * 
	 * @param id
	 * @return LookupSPNDocumentType
	 * @throws Exception
	 */
	public LookupSPNDocumentType findById(Integer id) throws Exception ;
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupSPNDocumentType> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;
	
}
