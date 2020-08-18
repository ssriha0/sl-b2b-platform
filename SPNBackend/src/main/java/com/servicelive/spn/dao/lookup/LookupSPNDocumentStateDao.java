package com.servicelive.spn.dao.lookup;

import java.util.List;

import com.servicelive.domain.lookup.LookupSPNDocumentState;
import com.servicelive.spn.dao.BaseDao;

/**
 * 
 * @author svanloon
 *
 */
public interface LookupSPNDocumentStateDao extends BaseDao {
	/**
	 * 
	 * @return List
	 * @throws Exception
	 */
	public List<LookupSPNDocumentState> getActionableSPNDocumentStates() throws Exception;

	/**
	 * 
	 * @param id
	 * @return LookupLanguage
	 * @throws Exception
	 */
	public LookupSPNDocumentState getSPNDocumentStateFromId(String id) throws Exception;
	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupSPNDocumentState> findAll ( int... rowStartIdxAndCount) throws Exception;
	/**
	 * 
	 * @param id
	 * @return LookupLanguage
	 * @throws Exception
	 */
	public LookupSPNDocumentState findById(String id) throws Exception;
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupSPNDocumentState> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;

}
