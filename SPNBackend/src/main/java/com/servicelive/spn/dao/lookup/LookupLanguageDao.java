/**
 *
 */
package com.servicelive.spn.dao.lookup;

import java.util.List;

import com.servicelive.domain.lookup.LookupLanguage;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author hoza
 * 
 */
public interface LookupLanguageDao extends BaseDao {
	/**
	 * 
	 * @return List
	 * @throws Exception
	 */
	public List<LookupLanguage> getLanguages() throws Exception;
	/**
	 * 
	 * @param id
	 * @return LookupLanguage
	 * @throws Exception
	 */
	public LookupLanguage getLanguageFromId(Integer id) throws Exception;
	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupLanguage> findAll ( int... rowStartIdxAndCount) throws Exception;
	/**
	 * 
	 * @param id
	 * @return LookupLanguage
	 * @throws Exception
	 */
	public LookupLanguage findById(Integer id) throws Exception;
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupLanguage> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;
}
